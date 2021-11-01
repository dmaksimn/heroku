package com.example.demo.file.impl;

import com.example.demo.configuration.properties.FileStorageProperties;
import com.example.demo.file.FileManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileManagerImpl implements FileManager {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerImpl.class);
    private final FileStorageProperties fileStorageProperties;

    public FileManagerImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public String saveFile(byte[] data) {
        String name = fileStorageProperties.getNamePrefix() + UUID.randomUUID().toString();
        File newFile = new File(fileStorageProperties.getFolder() + "/" + name);
        logger.debug("Creating file in path [{}]", newFile.getPath());
        if (newFile.exists()) {
            throw new RuntimeException("File is already exists");
        }
        try {
            FileUtils.writeByteArrayToFile(newFile, data);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file", e);
        }
        return name;
    }

    @Override
    public byte[] loadFile(String fileName) {
        try {
            File file = new File(fileStorageProperties.getFolder() + "/" + fileName);
            return file.exists() ? FileUtils.readFileToByteArray(file) : null;
        } catch (IOException e) {
            logger.warn("Cannot load file", e);
        }
        return null;
    }
}
