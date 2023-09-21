package com.hardrockbe.hardrock.repos;


import com.hardrockbe.hardrock.model.menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface menurepository extends JpaRepository<menu, Long> {
}