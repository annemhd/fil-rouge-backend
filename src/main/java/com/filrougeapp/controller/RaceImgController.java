package com.filrougeapp.controller;

import com.filrougeapp.model.RaceImg;
import com.filrougeapp.repository.RaceImgRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Allow CORS for this controller
public class RaceImgController {

    private final RaceImgRepository raceImgRepository;
    private static final String UPLOADED_FOLDER = "uploads/";

    public RaceImgController(RaceImgRepository raceImgRepository) {
        this.raceImgRepository = raceImgRepository;
    }
    
    @PostConstruct
    private void init() {
        File dir = new File(UPLOADED_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<RaceImg> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.write(path, file.getBytes());

            String imageUrl = "/images/" + fileName;

            RaceImg raceImg = RaceImg.builder()
                    .url(imageUrl)
                    .build();
            RaceImg savedImage = raceImgRepository.save(raceImg);

            return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
