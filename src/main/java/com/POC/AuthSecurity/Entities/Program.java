package com.POC.AuthSecurity.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name; // nutrition, sport, gestion emotionnelle
    @NotNull
    private String description;
    @JsonIgnore
    @Column(unique = true)
    @OneToMany
    List<Challenge> challenges;
}