package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;
import com.POC.AuthSecurity.Services.Implementations.ChallengeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("challenge")
public class ChallengeController {
    ChallengeService challengeService;

    @GetMapping("getAll")
    public List<ChallengeDTO> getAll(){
        return challengeService.getAll();
    }

    @GetMapping("getById/{id}")
    public ChallengeDTO getById(@PathVariable int id){
        System.out.println(id);
        return challengeService.getById(id);
    }

    @GetMapping("getByName/{name}")
    public ChallengeDTO getByName(@PathVariable String name){
        return challengeService.getByChallengeuid(name);
    }

    @PostMapping("addChallenge")
    public Challenge addChallenge(@RequestBody Challenge challenge){
        return challengeService.addChallenge(challenge);
    }

    @DeleteMapping("deleteById/{id}")
    public String deleteById(@PathVariable int id){
        return challengeService.deleteById(id);
    }

    @DeleteMapping("deleteByChallengeuid/{name}")
    public String deleteByName(@PathVariable String name){
        return challengeService.deleteByChallengeuid(name);
    }

    @PatchMapping("updateChallengeById")
    public String updateChallengeById(@RequestBody Challenge challenge){
        return challengeService.updateChallengeById(challenge);
    }

    @PatchMapping("updateChallengeDescriptionByChallengeuid")
    public String updateChallengeDescriptionByChallengeuid(@RequestBody Challenge challenge){
        return challengeService.updateChallengeDescriptionByChallengeuid(challenge);
    }

    @GetMapping("getCompletedChallengesByUser/{email}")
    public List<ChallengeDTO> getCompletedChallengesByUser(@PathVariable String email){
        return challengeService.getCompletedChallengesByUser(email);
    }

    @PostMapping("addCompletedChallengeToUserByEmailAndChallengeId/{email}/{challengeId}")
    public String addCompletedChallengeToUserByEmailAndChallengeId(@PathVariable String email, @PathVariable int challengeId){
        return challengeService.addCompletedChallengeToUserByEmailAndChallengeId(email, challengeId);
    }

    @PostMapping("addCompletedChallengeToUserByEmailAndChallengeuid/{email}/{challengeName}")
    public String addCompletedChallengeToUserByEmailAndChallengeName(@PathVariable String email, @PathVariable String challengeName){
        return challengeService.addCompletedChallengeToUserByEmailAndChallengeuid(email, challengeName);
    }

    @GetMapping("getUncompletedChallengesByUser/{email}")
    public List<ChallengeDTO> getUncompletedChallengesByUser(@PathVariable String email){
        return challengeService.getUncompletedChallengesByUser(email);
    }
}
