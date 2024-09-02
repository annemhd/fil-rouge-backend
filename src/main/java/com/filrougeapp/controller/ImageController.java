package com.filrougeapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filrougeapp.model.Image;
import com.filrougeapp.repository.ImageRepository;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;
    private static final String UPLOADED_FOLDER = "uploads/";

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    private void init() {
        // Création du répertoire uploads au démarrage de l'application
        File dir = new File(UPLOADED_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // Nom du fichier et chemin complet
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.write(path, file.getBytes());

            // Créer l'URL de l'image
            String imageUrl = "/images/" + fileName;

            // Sauvegarder l'URL dans la base de données
            Image image = Image.builder()
                    .url(imageUrl)
                    .build();
            Image savedImage = imageRepository.save(image);

            return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        return imageRepository.findById(id)
                .map(image -> {
                    try {
                        String fileName = image.getUrl().replace("/images/", "");
                        Path path = Paths.get(UPLOADED_FOLDER + fileName);
                        byte[] imageBytes = Files.readAllBytes(path);

                        HttpHeaders headers = new HttpHeaders();
                        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg"); 
                        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
