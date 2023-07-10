package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;
import com.POC.AuthSecurity.Entities.Program;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProgramService {

    //CREATE
    Program addProgram(Program program);
    String addChallengeToProgramByName(String programName, String challengeName);
    String addChallengeToProgramById(String programName, int challengeId);
    //READ
    List<Program> getAllPrograms();
    Program getProgramById(int id);
    Program getProgramByName(String name);
    List<Challenge> getChallengesByProgramuid(String name);

    //UPDATE
    String updateProgramDescriptionByName(String name, Program newProgram);
    String updateProgramById(int id, Program newProgram);

    //DELETE
    String deleteProgramById(int id);
    String deleteProgramByName(String name);

    List<ChallengeDTO> getChallengesByProgramNamePaginated(String name, int page, int size);
}