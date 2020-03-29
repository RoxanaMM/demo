package com.task.home.assignment.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileReaderService {
    private static final String CANONICAL_PROJECT_PATH = "src/main/resources/documents/";

    public ByteArrayResource readFile(String documentName, String documentCategory) throws IOException {
        Path path = Path.of(CANONICAL_PROJECT_PATH, documentCategory, documentName);
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
