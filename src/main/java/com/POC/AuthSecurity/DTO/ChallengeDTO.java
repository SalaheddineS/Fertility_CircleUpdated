package com.POC.AuthSecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChallengeDTO {
    private Integer id;
    private String name;
    private String description;

}
