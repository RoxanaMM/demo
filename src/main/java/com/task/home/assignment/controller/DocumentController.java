package com.task.home.assignment.controller;

import com.task.home.assignment.exceptionHandler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;
import com.task.home.assignment.repository.DocumentRepository;
import com.task.home.assignment.service.FileStorageService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Api(value = "Document Controller")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private final static String CANONIC_PROJECT_PATH = "src/main/resources/documents/";

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
    Document uploadFile(MultipartFile file, String folderCategory) throws IOException, SQLException, CustomExceptionHandlerInternalServerError {
        Document document = new Document();
        if (file == null || file.isEmpty()) {
            throw new CustomExceptionHandlerInternalServerError("Please insert a file that is not empty!");
        }
        try {
            document = fileStorageService.checkValidityAndReturnDoc(file, folderCategory);
            documentRepository.saveDocument(document, file.getOriginalFilename());
            LOGGER.info("file valid, saved in db");

            byte[] bytes = file.getBytes();
            File f = new File(CANONIC_PROJECT_PATH + folderCategory).getCanonicalFile();
            if (!f.exists()) {
                f.mkdir();
            }

            File canonicalFile = new File(CANONIC_PROJECT_PATH + folderCategory + "/" + file.getOriginalFilename()).getCanonicalFile();
            Path path = Paths.get(String.valueOf(canonicalFile));
            Files.write(path, bytes);

        } catch (IOException e) {
            LOGGER.error("IO/NIO problem");
        }
        return document;
    }

    @ApiOperation(value = "Download a file with a category")
    @GetMapping("/documents/download")
    public ResponseEntity<Resource> download(String documentName, String documentCategory) throws IOException, SQLException, CustomExceptionHandlerInternalServerError {

        if (documentName == null || documentCategory == null) {
            throw new CustomExceptionHandlerInternalServerError("Please insert values for document name and/or document category");
        }
        File file = new File(documentName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        File canonicalFile = new File(CANONIC_PROJECT_PATH + documentCategory + "/" + documentName).getCanonicalFile();
        Path path = Paths.get(String.valueOf(canonicalFile));
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        Document document = documentRepository.findByName(documentName);

        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType(document.getDocumentMimeType())).body(resource);
    }


}
