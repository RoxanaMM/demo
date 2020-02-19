package com.example.demo.service;

import com.example.demo.model.Document;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

@Service
public class FileStorageService {

    public Document checkValidityAndReturnDoc(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extensionPattern = ".+(pdf|txt|doc|docx|png|gif|jpeg|jpg|zip|rar)$";
        Pattern patternChecker = Pattern.compile(extensionPattern);

        if (!fileName.toLowerCase().matches(extensionPattern)) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        if ( file.getSize() / 1024 >= 2500) {
            throw new IOException("Sorry! File too big for upload");
        }
        Document document = new Document();
        document.setDocumentName(file.getName());
        document.setDocumentCategory("finance");
        document.setDocumentData(file.getBytes());
        return document;
    }
//
//    public Resource loadFileAsResource(String fileName, Path fileStorageLocation) throws Exception {
//        Path filePath = fileStorageLocation.resolve(fileName).normalize();
//        Resource resource = new UrlResource(filePath.toUri());
//        if (resource.exists()) {
//            return resource;
//        }
//        return null;
//    }
}
