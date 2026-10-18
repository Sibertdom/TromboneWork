package com.example.trombonework.service;

import com.example.trombonework.model.Composer;
import com.example.trombonework.model.TromboneWork;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStorageService {

    private static final String WORKS_FILE_PATH = "works.json";
    private static final String COMPOSERS_FILE_PATH = "composers.json";

    private final ObjectMapper mapper;

    public JsonStorageService() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // --- Робота з композиторами ---

    public void saveComposers(List<Composer> composers) {
        saveToFile(COMPOSERS_FILE_PATH, composers);
    }

    public List<Composer> loadComposers() {
        return loadFromFile(COMPOSERS_FILE_PATH, new TypeReference<List<Composer>>() {});
    }

    // --- Робота з творами ---

    public void saveWorks(List<TromboneWork> works) {
        saveToFile(WORKS_FILE_PATH, works);
    }

    public List<TromboneWork> loadWorks() {
        return loadFromFile(WORKS_FILE_PATH, new TypeReference<List<TromboneWork>>() {});
    }

    // --- Допоміжні загальні методи ---

    private <T> void saveToFile(String filePath, T data) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            mapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> loadFromFile(String filePath, TypeReference<List<T>> typeReference) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}