package com.task.home.assignment.service;

import com.google.common.io.Files;
import com.task.home.assignment.exception.handler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.AllowedFileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileUploadValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadValidationService.class);

    public void checkDocumentValidity(Optional<String> fileName, Optional<String> category) throws CustomExceptionHandlerInternalServerError {
        fileName.orElseThrow(() -> new CustomExceptionHandlerInternalServerError("Please insert a not-null fileName! "));
        category.orElseThrow(() -> new CustomExceptionHandlerInternalServerError("Please insert a non-null category!"));
    }

    public void checkFileValidy(Optional<MultipartFile> file) throws CustomExceptionHandlerInternalServerError {
        MultipartFile multipartFile = file.orElseThrow(() -> new CustomExceptionHandlerInternalServerError("Please insert a file that is not empty!"));

        if (multipartFile.getSize() / 1024 >= 2500) {
            throw new CustomExceptionHandlerInternalServerError("Sorry! File too big for upload");
        }
        String inputFileExtension = Files.getFileExtension(multipartFile.getOriginalFilename());
        Stream<AllowedFileExtension> allowedFileExtensionStream = Arrays.stream(AllowedFileExtension.values());
        if (allowedFileExtensionStream.noneMatch(extension -> (extension.name().equalsIgnoreCase(inputFileExtension)))) {
            LOGGER.info("Invalid file path name");
            throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + multipartFile.getName());
        }
    }
}
