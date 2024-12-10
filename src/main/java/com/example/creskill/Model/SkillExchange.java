package com.example.creskill.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class SkillExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillExchangeId;
    @Column(columnDefinition = "int not null")
    private Integer userId;
    @Column(columnDefinition = "int null")
    private Integer partnerId;
    @NotEmpty(message = "talent skill is mandatory")
    @Column(columnDefinition = "varchar(100) not null")
    private String talentSkill;
    @Column(columnDefinition = "varchar(100) not null")
    private String partnerSkill;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate date;
    @NotEmpty(message = "location is mandatory")
    @Column(columnDefinition = "varchar(20) not null")
@Pattern(regexp = "online|physical")
    private String location;
    @Column(columnDefinition = "varchar(200)")
    private String sessionLink;

    @Column(columnDefinition = "varchar(15) not null")
    @Pattern(regexp = "pending|completed|started")
    private String status;
}
