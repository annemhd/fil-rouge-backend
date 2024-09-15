package com.filrougeapp.controller;

import com.filrougeapp.model.Documents;
import com.filrougeapp.repository.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController // indique que cette classe est un contrôleur REST
@RequestMapping("/documents") // spécifie la route de base pour toutes les méthodes de ce contrôleur
public class DocumentsController {

    @Autowired // injection automatique du dépôt de documents
    private DocumentRepository documentRepository;

    // méthode pour récupérer tous les documents stockés dans la base de données
    @GetMapping
    public List<Documents> getAllDocuments() {
        return documentRepository.findAll(); // Récupère et retourne tous les documents
    }

    // méthode pour créer et sauvegarder un nouveau document à partir d'un fichier
    // uploadé
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Documents createDocument(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        long maxSize = 16 * 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw new IllegalArgumentException("Le document est trop volumineux");
        }

        // nettoie le nom du fichier pour éviter les caractères spéciaux non désirés
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // crée un objet Documents en utilisant les données du fichier uploadé
        Documents document = Documents.builder()
                .name(filename)
                .content(multipartFile.getBytes())
                .size(multipartFile.getSize())
                .date(new Date())
                .build();

        documentRepository.save(document);

        return document;
    }

    // Récupérer un document par son ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocumentById(@PathVariable Integer id) {
        Documents document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document non trouvé avec l'ID : " + id));

        // Create HttpHeaders object to set content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(getMediaTypeForFileName(document.getName())));
        headers.setContentLength(document.getSize());

        return new ResponseEntity<>(document.getContent(), headers, HttpStatus.OK);
    }

    // Method to determine the media type based on file extension
    private String getMediaTypeForFileName(String fileName) {
        if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default value
        }
    }
}