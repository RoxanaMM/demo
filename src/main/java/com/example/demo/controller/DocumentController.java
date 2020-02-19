package com.example.demo.controller;

import com.example.demo.category.Category;
import com.example.demo.model.Document;
import com.example.demo.payload.UploadFileResponse;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.FileStorageService;
import io.swagger.annotations.*;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @ApiOperation(value = "Get an Document by category")
    @GetMapping("/documents/{documentCategory}")
    public List<String> getDocumentByCategory(
            @ApiParam(value = "You can choose the category from which you want to retrieve documents. There are quite a few categories here: ", required = true)
            @PathVariable(value = "documentCategory") String documentCategory)
            throws ResourceNotFoundException, SQLException {
        List<String> documentNamesMatchingCategory = new ArrayList<String>();
        documentNamesMatchingCategory = documentRepository.findByCategory(documentCategory);
        return documentNamesMatchingCategory;
    }

    @PostMapping("/upload")
    @Transactional
    @ApiOperation(value = "File upload", notes = "Upload a file to an ID")
    public @ResponseBody
    String uploadFile(MultipartFile file) throws IOException {
        Document document = fileStorageService.checkValidityAndReturnDoc(file);
        documentRepository.save(document);
        return ResponseEntity.ok().toString();
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }


    @ResponseBody
    @RequestMapping(method = POST, path = {"/download"})
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
