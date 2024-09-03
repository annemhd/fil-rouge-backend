package com.filrougeapp.controller;

import com.filrougeapp.model.Documents;
import com.filrougeapp.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping
    public List<Documents> getAllDocuments() {
        return documentRepository.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createDocument(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        long maxSize = 16 * 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw new IllegalArgumentException("Le document est trop volumineux");
        }

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Documents document = Documents.builder()
                .name(filename)
                .content(multipartFile.getBytes())
                .size(multipartFile.getSize())
                .date(new Date())
                .build();

        documentRepository.save(document);

        return "Upload ok";
    }
}
