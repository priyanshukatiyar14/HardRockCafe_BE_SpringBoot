package com.hardrockbe.hardrock.repos;


import com.hardrockbe.hardrock.model.auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface authrepository extends JpaRepository<auth, Long> {
}