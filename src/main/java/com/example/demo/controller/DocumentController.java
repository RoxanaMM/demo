package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// atentie la faptul ca scriptul de data.sql nu ruleaza la fiecare build
//adaugare de teste
//cod coverage


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

    @ApiOperation(value = "Get an Document by category")
    @GetMapping("/documents/{documentCategory}")
    public List<Document> getDocumentByCategory(
            @ApiParam(value = "Document category from which the document objects will retrieved", required = true)
            @PathVariable(value = "documentCategory") String documentCategory)
            throws ResourceNotFoundException {
        List<Document> documents = new ArrayList<Document>();
        documents = documentRepository.findByCategory(documentCategory);
        //aici poate bubui daca nu gaseste nimic
        // atentie
        return documents;
    }

    @ApiOperation(value = "Add a document")
    @PostMapping("/upload")
    public Document createDocument(
            @ApiParam(value = "Upload a document", required = true)
            @Valid @RequestBody Document document) {
        return documentRepository.save(document);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = {"/download"})
    public ResponseEntity<InputStreamResource> downloadFile(@RequestBody String pathFile) throws IOException {
        // out.println(pathFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(pathFile));
        File fileToDownload = resource.getFile();


        //atentie aici ca nu stiu tipul de fisier pe care ei vor sa il incarce

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileToDownload.getName())
                .contentLength(fileToDownload.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);
    }

//    @PostMapping(value = "/downloads", produces = APPLICATION_MS_WORD_VALUE)
//    public ResponseEntity<byte[]> downloadFile1(@RequestBody String pathFile) throws IOException {
//        byte[] content = null;downloadDocumentService.downloadFile(pathFile);
//
//        return ResponseEntity.ok()
//                .contentLength(content.length)
//                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_MS_WORD_VALUE)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "File.docx"))
//                             .body(null);
//    }

}
