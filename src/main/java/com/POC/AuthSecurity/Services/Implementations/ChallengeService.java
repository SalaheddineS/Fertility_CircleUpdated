package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.DTO.ChallengeDTO;
import com.POC.AuthSecurity.Entities.Challenge;
import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.ChallengeRepository;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Services.Interfaces.IChallengeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ChallengeService implements IChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;


    @Override
    public List<ChallengeDTO> getAll() {
        List<Challenge> challengeList = challengeRepository.findAll();
        List<ChallengeDTO> challengeDTOList = new ArrayList<>();
        for (Challenge challenge : challengeList) {
            challengeDTOList.add(ChallengeDTO.builder()
                    .id(challenge.getId())
                    .name(challenge.getChallengeuid())
                    .description(challenge.getDescription())
                    .build());
        }
        return challengeDTOList;
    }

    @Override
    public ChallengeDTO getById(int id) {
        Challenge challenge = challengeRepository.findById(id).get();
        return ChallengeDTO.builder()
                .id(challenge.getId())
                .name(challenge.getChallengeuid())
                .description(challenge.getDescription())
                .build();
    }

    @Override
    public ChallengeDTO getByChallengeuid(String name) {
        try {
            Challenge challenge = challengeRepository.findByChallengeuid(name);
            return ChallengeDTO.builder()
                    .id(challenge.getId())
                    .name(challenge.getChallengeuid())
                    .description(challenge.getDescription())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting challenge by name, here are the details : " + e);
        }
    }

    @Override
    public Challenge addChallenge(Challenge challenge) {
        try {
            Challenge search = challengeRepository.findByChallengeuid(challenge.getChallengeuid());
            if (search != null) throw new RuntimeException("Challenge with this name already exists");
            challenge.setCompletedChallengeByUser(null);
            return challengeRepository.save(challenge);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding challenge, here are the details : " + e);
        }
    }

    @Override
    public String deleteById(int id) {
        try {
            challengeRepository.deleteById(id);
            return "challenge deleted";
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting challenge, here are the details : " + e);
        }
    }

    @Override
    public String deleteByChallengeuid(String name) {
        try {
            challengeRepository.deleteByChallengeuid(name);
            return "challenge deleted";
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting challenge, here are the details : " + e);
        }
    }

    @Override
    public String updateChallengeById(Challenge challenge) {
        try {
            Challenge defi = challengeRepository.findById(challenge.getId()).get();
            Challenge search = challengeRepository.findByChallengeuid(challenge.getChallengeuid());
            if (search != null) throw new RuntimeException("Challenge with this name already exists");
            if (challenge.getChallengeuid() != null) {
                defi.setChallengeuid(challenge.getChallengeuid());
            }
            if (challenge.getDescription() != null) {
                defi.setDescription(challenge.getDescription());
            }
            challengeRepository.save(defi);
            return "Challenge updated successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while updating challenge , here are the details : " + e);
        }
    }

    @Override
    public String updateChallengeDescriptionByChallengeuid(Challenge challenge) {
        try {
            Challenge defi = challengeRepository.findByChallengeuid(challenge.getChallengeuid());
            if (defi == null) throw new RuntimeException("Challenge not found");
            if (challenge.getDescription() != null) {
                defi.setDescription(challenge.getDescription());
            }
            challengeRepository.save(defi);
            return "Challenge updated successfully";
        }
        catch (Exception e) {
            throw new RuntimeException("Error while updating challenge , here are the details : " + e);
        }
    }

    @Override
    public List<ChallengeDTO> getCompletedChallengesByUser(String email) {
        try {
            email = email.toLowerCase();
            User user = userRepository.findByEmail(email).get();
            List<Challenge> challengeList = challengeRepository.findByCompletedChallengeByUser_Id(user.getId());
            List<ChallengeDTO> challengeDTOList = new ArrayList<>();
            for (Challenge challenge : challengeList) {
                challengeDTOList.add(ChallengeDTO.builder()
                        .id(challenge.getId())
                        .name(challenge.getChallengeuid())
                        .description(challenge.getDescription())
                        .build());
            }
            return challengeDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting completed challenges by user, here are the details : " + e);
        }

    }

    @Override
    public List<ChallengeDTO> getUncompletedChallengesByUser(String email) {
        try {
            email = email.toLowerCase();
            User user = userRepository.findByEmail(email).get();
            List<Challenge> challengeList = user.getProgram().getChallenges();
            List<Challenge> completedChallenges = challengeRepository.findByCompletedChallengeByUser_Id(user.getId());
            List<Challenge> uncompletedChallenges = new ArrayList<>();
            for (Challenge challenge : challengeList) {
                if (!completedChallenges.contains(challenge)) {
                    uncompletedChallenges.add(challenge);
                }
            }
            List<ChallengeDTO> challengeDTOList = new ArrayList<>();
            for (Challenge challenge : uncompletedChallenges) {
                challengeDTOList.add(ChallengeDTO.builder()
                        .id(challenge.getId())
                        .name(challenge.getChallengeuid())
                        .description(challenge.getDescription())
                        .build());
            }
            return challengeDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting uncompleted challenges by user, here are the details : " + e);
        }
    }

    @Override
    public String addCompletedChallengeToUserByEmailAndChallengeId(String email, int Challengeid) {
        try {
            email = email.toLowerCase();
            User user = userRepository.findByEmail(email).get();
            Challenge challenge = challengeRepository.findById(Challengeid).get();
            if (challenge.getCompletedChallengeByUser().contains(user))
                throw new RuntimeException("Challenge already added");
            challenge.getCompletedChallengeByUser().add(user);
            challengeRepository.save(challenge);
            return "Challenge added successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding completed challenge, here are the details : " + e);
        }
    }

    @Override
    public String addCompletedChallengeToUserByEmailAndChallengeuid(String email, String ChallengeName) {
        try {
            email = email.toLowerCase();
            User user = userRepository.findByEmail(email).get();
            Challenge challenge = challengeRepository.findByChallengeuid(ChallengeName);
            if (challenge == null) throw new RuntimeException("Challenge not found");
            if (challenge.getCompletedChallengeByUser().contains(user))
                throw new RuntimeException("Challenge already added");
            challenge.getCompletedChallengeByUser().add(user);
            challengeRepository.save(challenge);
            return "Challenge added successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding completed challenge, here are the details : " + e);
        }
    }




}