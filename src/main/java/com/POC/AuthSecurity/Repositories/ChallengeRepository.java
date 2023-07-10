package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository  extends JpaRepository<Challenge, Integer> {
    Challenge findByChallengeuid(String name);
    void deleteByChallengeuid(String name);
    List<Challenge> findByCompletedChallengeByUser_Id(Integer userId);
    List<Challenge> findByCompletedChallengeByUser_IdNot(Integer userId);


}