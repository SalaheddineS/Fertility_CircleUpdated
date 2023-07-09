package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    Program findProgramByname(String name);
    void deleteProgramByName(String name);
}