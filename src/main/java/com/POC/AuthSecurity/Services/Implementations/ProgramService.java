package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;
import com.POC.AuthSecurity.Entities.Program;
import com.POC.AuthSecurity.Repositories.ChallengeRepository;
import com.POC.AuthSecurity.Repositories.ProgramRepository;
import com.POC.AuthSecurity.Services.Interfaces.IProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProgramService implements IProgramService {

    private final ProgramRepository programRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public List<Program> getAllPrograms() {
        try {
            return programRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all programs, here are the details : " + e);
        }
    }

    @Override
    public Program getProgramById(int id) {
        try {
            return programRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting program by id, here are the details : " + e);
        }
    }

    @Override
    public Program getProgramByName(String name) {
        try {
            return programRepository.findProgramByname(name);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting program by name, here are the details : " + e);
        }
    }

    @Override
    public List<Challenge> getChallengesByProgramuid(String programName) {
        try {
            Program p = getProgramByName(programName);
            return p.getChallenges();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting challenges by program name, here are the details : " + e);
        }
    }

    @Override
    public Program addProgram(Program program) {
        try {
            Program p = programRepository.findProgramByname(program.getName());
            if (p != null) throw new RuntimeException("Program already exists");
            program.setChallenges(null);
            return programRepository.save(program);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding program, here are the details : " + e);
        }
    }

    @Override
    public String addChallengeToProgramByName(String programName, String challengeName) {
        try {
            Program p = getProgramByName(programName);
            if (p == null) throw new RuntimeException("Program not found");
            if(p.getChallenges().size()==90) throw new RuntimeException("Program already has 90 challenges");
            Challenge c = challengeRepository.findByChallengeuid(challengeName);
            if (c == null) throw new RuntimeException("Challenge not found");

            if (p.getChallenges().contains(c)) throw new RuntimeException("Challenge already exists in program");
            p.getChallenges().add(c);
            programRepository.save(p);
            return "Challenge added to program successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding challenge to program, here are the details : " + e);
        }
    }

    @Override
    public String addChallengeToProgramById(String programName, int challengeId) {
        try {
            Program p = getProgramByName(programName);
            if (p == null) throw new RuntimeException("Program not found");
            Challenge c = challengeRepository.findById(challengeId).get();
            if (c == null) throw new RuntimeException("Challenge not found");
            if (p.getChallenges().contains(c)) throw new RuntimeException("Challenge already exists in program");
            p.getChallenges().add(c);
            programRepository.save(p);
            return "Challenge added to program successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding challenge to program, here are the details : " + e);
        }
    }

    @Override
    public String updateProgramById(int id, Program newProgram) {
        try {
            Program existingProgram = programRepository.findProgramByname(newProgram.getName());
            if (existingProgram != null) throw new RuntimeException("Program already exists");
            Program programme = getProgramById(id);
            if (newProgram.getName() != null) {
                programme.setName(newProgram.getName());
            }

            if (newProgram.getDescription() != null) {
                programme.setDescription(newProgram.getDescription());
            }

            programRepository.save(programme);

            return "Program updated successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while updating program, here are the details : " + e);
        }
    }

    @Override
    public String updateProgramDescriptionByName(String name, Program newProgram) {
        try {
            Program programme = getProgramByName(name);
            if (newProgram.getDescription() != null) {
                programme.setDescription(newProgram.getDescription());
            }
            programRepository.save(programme);
            return "Program updated successfully";
        }catch (Exception e){
            throw new RuntimeException("Error while updating program, here are the details : " + e);
        }
    }

    @Override
    public String deleteProgramById(int id) {
        try {
            programRepository.deleteById(id);
            return "Program deleted successfully";
        }
        catch (Exception e){
            throw new RuntimeException("Error while deleting program, here are the details : " + e);
        }
    }

    @Override
    public String deleteProgramByName(String name) {
        try {
            programRepository.deleteProgramByName(name);
            return "Program deleted successfully";
        }
        catch (Exception e){
            throw new RuntimeException("Error while deleting program, here are the details : " + e);
        }
    }

    @Override
    public List<ChallengeDTO> getChallengesByProgramNamePaginated(String name, int page, int size) {
        try {
            Program program = getProgramByName(name);
            List<Challenge> challenges = program.getChallenges();
            int start = page * size;
            int end = Math.min(start+size, challenges.size());
            List<Challenge> challengesPaginated = challenges.subList(start, end);
            List<ChallengeDTO> challengesDTO = new ArrayList<>();
            for(Challenge c : challengesPaginated){
                challengesDTO.add(ChallengeDTO.builder().id(c.getId()).name(c.getChallengeuid()).description(c.getDescription()).build());
            }
            return challengesDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting challenges by program name, here are the details : " + e);
        }
    }
}
