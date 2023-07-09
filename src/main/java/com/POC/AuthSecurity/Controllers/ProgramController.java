package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;
import com.POC.AuthSecurity.Entities.Program;
import com.POC.AuthSecurity.Services.Implementations.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("program")
public class ProgramController {

    ProgramService programService;

    //CREATE
    @PostMapping("addProgram")
    public Program addProgram(@RequestBody Program program) {
        return programService.addProgram(program);
    }

    //READ
    @GetMapping("getAllPrograms")
    public List<Program> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping("getProgramById/{id}")
    public Program getProgramById(@PathVariable int id) {
        return programService.getProgramById(id);
    }

    @GetMapping("getProgramByName/{name}")
    public Program getProgramByName(@PathVariable String name) {
        return programService.getProgramByName(name);
    }

    @GetMapping("getChallengesByProgramName/{name}")
    public List<Challenge> getChallengesByProgramName(@PathVariable String name) {
        return programService.getChallengesByProgramName(name);
    }

    //UPDATE
    @PatchMapping("updateProgramDescriptionByName/{name}")
    public String updateProgramDescriptionByName(@PathVariable String name, @RequestBody Program newProgram) {
        return programService.updateProgramDescriptionByName(name, newProgram);
    }

    @PatchMapping("updateProgramById/{id}")
    public String updateProgramById(@PathVariable int id, @RequestBody Program newProgram) {
        return programService.updateProgramById(id, newProgram);
    }

    //DELETE
    @DeleteMapping("deleteProgramById/{id}")
    public String deleteProgramById(@PathVariable int id) {
        return programService.deleteProgramById(id);
    }

    @DeleteMapping("deleteProgramByName/{name}")
    public String deleteProgramByName(@PathVariable String name) {
        return programService.deleteProgramByName(name);
    }

    @PostMapping("addChallengeToProgramByName/{programName}/{challengeName}")
    public String addChallengeToProgramByName(@PathVariable String programName, @PathVariable String challengeName) {
        return programService.addChallengeToProgramByName(programName, challengeName);
    }

    @PostMapping("addChallengeToProgramById/{programName}/{challengeId}")
    public String addChallengeToProgramById(@PathVariable String programName, @PathVariable int challengeId) {

        return programService.addChallengeToProgramById(programName, challengeId);
    }

    @GetMapping("getChallengesByProgramNamePaginated/{name}/{page}/{size}")
    public List<ChallengeDTO> getChallengesByProgramNamePaginated(@PathVariable String name, @PathVariable int page, @PathVariable int size) {
        return programService.getChallengesByProgramNamePaginated(name, page, size);
    }

}
