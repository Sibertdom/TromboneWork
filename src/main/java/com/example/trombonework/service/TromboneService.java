package com.example.trombonework.service;

import com.example.trombonework.model.Composer;
import com.example.trombonework.model.TromboneWork;
import com.example.trombonework.repository.ComposerRepository;
import com.example.trombonework.repository.WorkRepository;
import com.example.trombonework.util.SampleDataFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TromboneService {

    private final WorkRepository workRepository;
    private final ComposerRepository composerRepository;

    public TromboneService(WorkRepository workRepository, ComposerRepository composerRepository) {
        this.workRepository = workRepository;
        this.composerRepository = composerRepository;
    }

    // Автоматично заповнюємо базу початковими даними при першому запуску, якщо вона порожня
    @PostConstruct
    public void initData() {
        if (workRepository.count() == 0) {
            List<Composer> initialComposers = SampleDataFactory.getInitialComposers();
            composerRepository.saveAll(initialComposers);

            List<TromboneWork> initialWorks = SampleDataFactory.getInitialWorks();
            workRepository.saveAll(initialWorks);
        }
    }

    // --- Робота з композиторами ---

    public List<Composer> getAllComposers() {
        return composerRepository.findAll();
    }

    public void addComposer(Composer composer) {
        composerRepository.save(composer);
    }

    /**
     * Знаходить існуючого композитора за повним ім'ям або створює нового,
     * якщо його ще немає в базі даних.
     */
    public Composer findOrCreateComposer(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return null;
        }
        String trimmedName = fullName.trim();
        List<Composer> composers = getAllComposers();

        // Шукаємо за повним ім'ям (без урахування регістру)
        for (Composer c : composers) {
            String fullCompName = (c.getFirstName() != null ? c.getFirstName() + " " : "") + c.getLastName();
            if (fullCompName.trim().equalsIgnoreCase(trimmedName)) {
                return c;
            }
        }

        // Якщо не знайдено — створюємо нового
        Composer newComposer = new Composer();
        String[] parts = trimmedName.split("\\s+", 2);
        if (parts.length > 1) {
            newComposer.setFirstName(parts[0]);
            newComposer.setLastName(parts[1]);
        } else {
            newComposer.setLastName(parts[0]);
        }

        return composerRepository.save(newComposer);
    }

    // --- Робота з творами ---

    public List<TromboneWork> getAllWorks() {
        return workRepository.findAll();
    }

    public void addWork(TromboneWork work) {
        if (work.getComposer() != null && work.getComposer().getLastName() != null) {
            String fullName = (work.getComposer().getFirstName() != null ? work.getComposer().getFirstName() + " " : "") + work.getComposer().getLastName();
            Composer resolved = findOrCreateComposer(fullName);
            if (resolved != null) {
                work.setComposer(resolved);
            }
        }
        workRepository.save(work);
    }

    public void updateWork(TromboneWork updatedWork) {
        if (updatedWork.getComposer() != null && updatedWork.getComposer().getLastName() != null) {
            String fullName = (updatedWork.getComposer().getFirstName() != null ? updatedWork.getComposer().getFirstName() + " " : "") + updatedWork.getComposer().getLastName();
            Composer resolved = findOrCreateComposer(fullName);
            if (resolved != null) {
                updatedWork.setComposer(resolved);
            }
        }
        workRepository.save(updatedWork);
    }

    public void deleteWork(Long id) {
        workRepository.deleteById(id);
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