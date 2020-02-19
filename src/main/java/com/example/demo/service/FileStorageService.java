package com.example.demo.service;

import com.example.demo.category.Category;
import com.example.demo.model.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class FileStorageService {

    private static final List<Category> VALUES =
            Collections.unmodifiableList(Arrays.asList(Category.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public Document checkValidityAndReturnDoc(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extensionPattern = ".+(pdf|txt|doc|docx|png|gif|jpeg|jpg|zip|rar)$";
        Pattern patternChecker = Pattern.compile(extensionPattern);

        if (!fileName.toLowerCase().matches(extensionPattern)) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        if (file.getSize() / 1024 >= 2500) {
            throw new IOException("Sorry! File too big for upload");
        }

        Document document = new Document();
        document.setDocumentName(file.getName());
        document.setDocumentCategory(VALUES.get(RANDOM.nextInt(SIZE)).name());
        document.setDocumentData(file.getBytes());
        return document;
    }
}
