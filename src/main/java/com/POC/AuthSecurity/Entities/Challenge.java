package com.POC.AuthSecurity.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @Column(unique = true)
    private String challengeuid;
    @NotNull
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    Set<User>completedChallengeByUser;
}