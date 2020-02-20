package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.FileStorageService;
import io.swagger.annotations.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


// atentie la faptul ca scriptul de data.sql nu ruleaza la fiecare build
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

    @PostMapping("/upload")
    @Transactional
    @ApiOperation(value = "File upload", notes = "Upload a file to an ID")
    public @ResponseBody
    Document uploadFile(MultipartFile file) throws IOException, SQLException {
        Document document = fileStorageService.checkValidityAndReturnDoc(file);
        documentRepository.saveDocument(document, file);
        return document;
    }

    // THE BLOB ALTERS THE DATA THAT IS RETRIEVED AT DOWNLOAD TIME
    //IT CANNOT BE UNDERSTOOD


    // file download
    @RequestMapping(path = "download4", method = RequestMethod.GET)
    public ResponseEntity<Resource> download7(String param) throws IOException, SQLException {

        File file = new File(param);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        Document document = documentRepository.findByName(param);
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType(document.getDocumentMediaType())).body(resource);
    }

    // file download
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "File download", notes = "Type in the name of a file you previously uploaded. After you click " +
            "on the Execute button, the Download file button will appear. The downloaded file will be placed in the " +
            "folder in which the project is ( you can see it inside the project too, under the target folder)")
    public ResponseEntity<Resource> download(String documentName) throws IOException, SQLException {

        File file = new File(documentName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Document document = documentRepository.findByName(documentName);
        ByteArrayResource resource = new ByteArrayResource(document.getDocumentData());

        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType(document.getDocumentMediaType())).body(resource);
    }


}
