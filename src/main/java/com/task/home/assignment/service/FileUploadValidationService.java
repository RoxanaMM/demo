package com.task.home.assignment.service;

import com.google.common.io.Files;
import com.task.home.assignment.exception.handler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.AllowedFileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class FileUploadValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadValidationService.class);

    public void checkValidDocumentDescription(String fileName, String category) throws CustomExceptionHandlerInternalServerError {
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(category)) {
            throw new CustomExceptionHandlerInternalServerError("File name and cathegory should not be empty. The inserted fileName is: " +
                    fileName + "and the inserted category is: " + category);
        }
    }

    public void checkFileValidy(MultipartFile file) throws CustomExceptionHandlerInternalServerError {
        if (file == null || file.isEmpty()) {
            throw new CustomExceptionHandlerInternalServerError("Please insert a file that is not empty!");
        }

        if (file.getSize() / 1024 >= 2500) {
            throw new CustomExceptionHandlerInternalServerError("Sorry! File too big for upload");
        }
        String inputFileExtension = Files.getFileExtension(file.getOriginalFilename());
        Stream<AllowedFileExtension> allowedFileExtensionStream = Arrays.stream(AllowedFileExtension.values());
        if (allowedFileExtensionStream.noneMatch(extension -> (extension.name().equals(inputFileExtension)))) {
            LOGGER.info("Invalid file path name");
            throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + file.getName());
        }
    }
}
