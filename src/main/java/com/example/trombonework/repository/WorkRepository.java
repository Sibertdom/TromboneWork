package com.example.trombonework.repository;

import com.example.trombonework.model.TromboneWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<TromboneWork, Long> {
}
