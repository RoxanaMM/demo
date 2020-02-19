package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Document Management System")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @ApiOperation(value = "View a list of available documents", response = List.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @ApiOperation(value = "Get an Document by Id")
    @GetMapping("/documents/{category}")
    public ResponseEntity<Document> getDocumentById(
            @ApiParam(value = "Document category from which the document objects will retrieved", required = true)
            @PathVariable(value = "documentCategory") Long documentId)
            throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));
        return ResponseEntity.ok().body(document);
    }

    @ApiOperation(value = "Add a document")
    @PostMapping("/documents")
    public Document createDocument(
            @ApiParam(value = "Document object store in database table", required = true)
            @Valid @RequestBody Document document) {
        return documentRepository.save(document);
    }

    @ApiOperation(value = "Update a document")
    @PutMapping("/documents/{id}")
    public ResponseEntity<Document> updateDocument(
            @ApiParam(value = "Document Id to update Document object", required = true)
            @PathVariable(value = "id") Long documentId,
            @ApiParam(value = "Update Document object", required = true)
            @Valid @RequestBody Document documentDetails) throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentDetails.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));

        document.setDocumentId(documentDetails.getDocumentId());
        document.setDocumentName(documentDetails.getDocumentName());
        document.setDocumentExtension(documentDetails.getDocumentExtension());
        final Document updatedDocument = documentRepository.save(document);
        return ResponseEntity.ok(updatedDocument);
    }

    @ApiOperation(value = "Delete an document")
    @DeleteMapping("/documents/{id}")
    public Map<String, Boolean> deleteDocument(
            @ApiParam(value = "Document Id from which Documentobject will delete from database table", required = true)
            @PathVariable(value = "id") Long documentId)
            throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));

        documentRepository.delete(document);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
