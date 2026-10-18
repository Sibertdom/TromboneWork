package com.example.trombonework.repository;

import com.example.trombonework.model.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposerRepository extends JpaRepository<Composer, Long> {
}
