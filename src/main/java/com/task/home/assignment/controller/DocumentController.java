package com.task.home.assignment.controller;

import com.task.home.assignment.exception.handler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;
import com.task.home.assignment.repository.DocumentRepository;
import com.task.home.assignment.service.FileGeneratorService;
import com.task.home.assignment.service.FileReaderService;
import com.task.home.assignment.service.FileUploadValidationService;
import com.task.home.assignment.service.FileWriterService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Api(value = "Document Controller")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private FileGeneratorService fileGeneratorService;
    @Autowired
    private FileUploadValidationService fileUploadValidationService;
    @Autowired
    private FileWriterService fileWriterService;
    @Autowired
    private FileReaderService fileReaderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @ApiOperation(value = "List of available documents", response = List.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")})
    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @ApiOperation(value = "List of documents filtered by category")
    @GetMapping("/documents/{documentCategory}")
    public List<Document> getDocumentByCategory(
            @ApiParam(value = " ", required = true)
            @PathVariable(value = "documentCategory") String documentCategory)
            throws SQLException {
        List<Document> documentsMatchingCategory = documentRepository.findByCategory(documentCategory);
        LOGGER.info("documents were found");
        return documentsMatchingCategory;
    }

    @ApiOperation(value = "Upload a file and a category for it")
    @Transactional
    @PostMapping(value = "/documents/upload")
    public @ResponseBody
    Document uploadFile(MultipartFile file, String folderCategory) throws SQLException, CustomExceptionHandlerInternalServerError {
        fileUploadValidationService.checkFileValidy(file);
        Document document = fileGeneratorService.generateDocument(file, folderCategory);
        documentRepository.saveDocument(document, file.getOriginalFilename());
        LOGGER.info("file valid, saved in db");
        fileWriterService.writeToDisk(file, folderCategory);
        return document;
    }

    @ApiOperation(value = "Download a file with a category")
    @GetMapping("/documents/download")
    public ResponseEntity<Resource> download(String documentName, String documentCategory) throws IOException, SQLException, CustomExceptionHandlerInternalServerError {
        fileUploadValidationService.checkValidDocumentDescription(documentName, documentCategory);
        Document document = documentRepository.findByName(documentName);
        ByteArrayResource resource = fileReaderService.readFile(documentName, documentCategory);

        File file = new File(documentName);
        return ResponseEntity.ok().contentLength(file.length())
                .contentType(MediaType.parseMediaType(document.getDocumentMimeType())).body(resource);
    }


}
