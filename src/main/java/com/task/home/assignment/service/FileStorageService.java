package com.task.home.assignment.service;

import com.task.home.assignment.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageService.class);

    public Document checkValidityAndReturnDoc(MultipartFile file, String category) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extensionPattern = ".+(pdf|txt|doc|docx|png|gif|jpeg|jpg|zip|rar)$";

        if (!patternChecker.asPredicate().test(fileName.toLowerCase())) {
            LOGGER.info("Invalid file path name");
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        if (file.getSize() / 1024 >= 2500) {
            throw new IOException("Sorry! File too big for upload");
        }

        Document document = new Document(fileName);
        document.setDocumentId(document.getDocumentId());
        document.setDocumentName(file.getName());
        document.setDocumentCategory(category);
        document.setDocumentMimeType(file.getContentType());
        LOGGER.info("Document was successfully created");

        return document;
    }
}
