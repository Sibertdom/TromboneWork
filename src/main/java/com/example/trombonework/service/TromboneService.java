package com.example.trombonework.service;

import com.example.trombonework.model.Composer;
import com.example.trombonework.model.TromboneWork;
import com.example.trombonework.util.SampleDataFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TromboneService {

    private final JsonStorageService storageService = new JsonStorageService();

    // --- Робота з композиторами ---

    public List<Composer> getAllComposers() {
        List<Composer> composers = storageService.loadComposers();
        if (composers.isEmpty()) {
            composers = SampleDataFactory.getInitialComposers();
            storageService.saveComposers(composers);
        }
        return new ArrayList<>(composers);
    }

    public void addComposer(Composer composer) {
        List<Composer> composers = getAllComposers();
        composers.add(composer);
        storageService.saveComposers(composers);
    }

    /**
     * Знаходить існуючого композитора за повним ім'ям або створює нового,
     * якщо його ще немає у файлі composers.json.
     */
    public Composer findOrCreateComposer(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return null;
        }
        String trimmedName = fullName.trim();
        List<Composer> composers = getAllComposers();

        // Шукаємо за повним ім'ям (без урахування регістру)
        for (Composer c : composers) {
            if (c.getFullName().equalsIgnoreCase(trimmedName)) {
                return c;
            }
        }

        // Якщо не знайдено — створюємо нового
        Composer newComposer = new Composer();
        newComposer.setId(System.currentTimeMillis());

        String[] parts = trimmedName.split("\\s+", 2);
        if (parts.length > 1) {
            newComposer.setFirstName(parts[0]);
            newComposer.setLastName(parts[1]);
        } else {
            newComposer.setLastName(parts[0]);
        }

        addComposer(newComposer);
        return newComposer;
    }

    // --- Робота з творами ---

    public List<TromboneWork> getAllWorks() {
        List<Composer> composers = getAllComposers();
        List<TromboneWork> works = storageService.loadWorks();

        if (works.isEmpty()) {
            works = SampleDataFactory.getInitialWorks();
            storageService.saveWorks(works);
        }

        // Швидка Map для пошуку композитора за id
        Map<Long, Composer> composerMap = composers.stream()
                .collect(Collectors.toMap(Composer::getId, Function.identity(), (c1, c2) -> c1));

        // Зв'язуємо об'єкт Composer з кожним твором за його composerId
        for (TromboneWork work : works) {
            if (work.getComposerId() != null) {
                work.setComposer(composerMap.get(work.getComposerId()));
            }
        }

        return new ArrayList<>(works);
    }

    public void addWork(TromboneWork work) {
        if (work.getComposer() != null) {
            Composer resolved = findOrCreateComposer(work.getComposer().getFullName());
            if (resolved != null) {
                work.setComposer(resolved);
                work.setComposerId(resolved.getId());
            }
        }
        List<TromboneWork> works = getAllWorks();
        works.add(work);
        storageService.saveWorks(works);
    }

    public void updateWork(TromboneWork updatedWork) {
        if (updatedWork.getComposer() != null) {
            Composer resolved = findOrCreateComposer(updatedWork.getComposer().getFullName());
            if (resolved != null) {
                updatedWork.setComposer(resolved);
                updatedWork.setComposerId(resolved.getId());
            }
        }
        List<TromboneWork> works = getAllWorks();
        for (int i = 0; i < works.size(); i++) {
            if (works.get(i).getId().equals(updatedWork.getId())) {
                works.set(i, updatedWork);
                break;
            }
        }
        storageService.saveWorks(works);
    }

    public void deleteWork(Long id) {
        List<TromboneWork> works = getAllWorks();
        works.removeIf(work -> work.getId().equals(id));
        storageService.saveWorks(works);
    }

    public List<TromboneWork> filterByType(List<TromboneWork> allWorks, String type) {
        if ("Всі".equalsIgnoreCase(type) || type == null) {
            return allWorks;
        }
        return allWorks.stream()
                .filter(work -> type.equalsIgnoreCase(work.getTypeOfTrombone()))
                .toList();
    }
}