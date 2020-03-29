package com.task.home.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileWriterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterService.class);
    private static final String CANONICAL_PROJECT_PATH = "src/main/resources/documents/";

    public void writeToDisk(MultipartFile file, String folderCategory) {
        try {
            Path pathToReadFileFrom = Path.of(CANONICAL_PROJECT_PATH, folderCategory);
            if (!Files.isDirectory(pathToReadFileFrom)) {
                File f = new File(pathToReadFileFrom.toString());
                f.mkdir();
            }

            byte[] bytes = file.getBytes();
            pathToReadFileFrom = Path.of(pathToReadFileFrom.toString(), file.getOriginalFilename());
            Files.write(pathToReadFileFrom, bytes);

        } catch (
                IOException e) {
            LOGGER.error("IO/NIO problem");
        }
    }
}
