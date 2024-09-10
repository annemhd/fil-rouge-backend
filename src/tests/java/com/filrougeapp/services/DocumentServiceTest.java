package com.filrougeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.filrougeapp.model.Document;
import com.filrougeapp.repository.DocumentRepository;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    public void testCreateDocument() {
        Document document = new Document();
        document.setTitle("Document Title");

        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Document createdDocument = documentService.createDocument(document);
        assertNotNull(createdDocument);
        assertEquals("Document Title", createdDocument.getTitle());
    }

    @Test
    public void testGetDocumentById() {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Document Title");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        Document foundDocument = documentService.getDocumentById(1L);
        assertNotNull(foundDocument);
        assertEquals("Document Title", foundDocument.getTitle());
    }

    @Test
    public void testUpdateDocument() {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Document Title");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        document.setTitle("Updated Title");
        Document updatedDocument = documentService.updateDocument(1L, document);
        assertNotNull(updatedDocument);
        assertEquals("Updated Title", updatedDocument.getTitle());
    }

    @Test
    public void testDeleteDocument() {
        Document document = new Document();
        document.setId(1L);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        documentService.deleteDocument(1L);
        verify(documentRepository).deleteById(1L);
    }
}