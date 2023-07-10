package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;

import java.util.List;

public interface IChallengeService {

    //Lecture
    List<ChallengeDTO> getAll();

    ChallengeDTO getById(int id);

    ChallengeDTO getByChallengeuid(String name);

    //Ajout
    Challenge addChallenge(Challenge challenge);

    //Supprimer
    String deleteById(int id);

    String deleteByChallengeuid(String name);

    //Update
    String updateChallengeById(Challenge challenge);

    String updateChallengeDescriptionByChallengeuid(Challenge challenge);

    List<ChallengeDTO> getCompletedChallengesByUser(String email);
    List<ChallengeDTO> getUncompletedChallengesByUser(String email);

    String addCompletedChallengeToUserByEmailAndChallengeId(String email, int Challengeid);
    String addCompletedChallengeToUserByEmailAndChallengeuid(String email, String ChallengeName);


}
