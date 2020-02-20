package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.FileStorageService;
import io.swagger.annotations.*;
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

//adaugare de teste
//cod coverage


@RestController
@RequestMapping("/api/v1")
@Api(value = "Document Controller")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private FileStorageService fileStorageService;
    private static final String canonicProjectPath = "src/main/resources/documents/";

    @ApiOperation(value = "View a list of available documents", response = List.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @ApiOperation(value = "Get Documents filtered by a category")
    @GetMapping("/documents/{documentCategory}")
    public List<Document> getDocumentByCategory(
            @ApiParam(value = "You can choose the category from which you want to retrieve documents. There are quite a few categories here: ", required = true)
            @PathVariable(value = "documentCategory") String documentCategory)
            throws ResourceNotFoundException, SQLException {
        List<Document> documentsMatchingCategory = new ArrayList<Document>();
        documentsMatchingCategory = documentRepository.findByCategory(documentCategory);
        return documentsMatchingCategory;
    }

    @PostMapping(value = "/upload")
    @Transactional
    @ApiOperation(value = "File upload", notes = "Upload a file to an ID")
    public @ResponseBody
    Document uploadFile(MultipartFile file, String folderCategory) throws IOException, SQLException {
        Document document = new Document();
        if (file.isEmpty()) {
            throw new IOException("Please insert a file that is not empty!");
        }
        try {
            fileStorageService.checkValidityOfDoc(file, folderCategory);
            documentRepository.saveDocument(file.getOriginalFilename());

            byte[] bytes = file.getBytes();
            File f = new File(canonicProjectPath + folderCategory).getCanonicalFile();
            if (!f.exists()) {
                f.mkdir();
            }

            File canonicalFile = new File(canonicProjectPath + folderCategory + "/" + file.getOriginalFilename()).getCanonicalFile();
            Path path = Paths.get(String.valueOf(canonicalFile));
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


    @RequestMapping(path = "download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String documentName, String documentCategory) throws IOException, SQLException {

        File file = new File(documentName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        File canonicalFile = new File(canonicProjectPath + documentCategory + "/" + documentName).getCanonicalFile();
        Path path = Paths.get(String.valueOf(canonicalFile));
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        Document document = documentRepository.findByName(documentName);

        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }


}
