package com.task.home.assignment.service;

import com.task.home.assignment.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileGeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileGeneratorService.class);

    public Document generateDocument(MultipartFile file, String category) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Document document = new Document(fileName);
        document.setDocumentId(document.getDocumentId());
        document.setDocumentName(file.getName());
        document.setDocumentCategory(category);
        document.setDocumentMimeType(file.getContentType());
        LOGGER.info("Document was successfully created");
        return document;
    }
}
