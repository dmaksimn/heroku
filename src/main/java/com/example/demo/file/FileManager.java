package com.example.demo.file;

public interface FileManager {
    String saveFile(byte[] file);

    byte[] loadFile(String fileName);
}
