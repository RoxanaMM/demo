package com.example.demo.service;

import com.example.demo.model.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

@Service
public class FileStorageService {

    public void checkValidityOfDoc(MultipartFile file, String category) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extensionPattern = ".+(pdf|txt|doc|docx|png|gif|jpeg|jpg|zip|rar)$";
        Pattern patternChecker = Pattern.compile(extensionPattern);

        if (!fileName.toLowerCase().matches(extensionPattern)) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        if (file.getSize() / 1024 >= 2500) {
            throw new IOException("Sorry! File too big for upload");
        }
    }
}
