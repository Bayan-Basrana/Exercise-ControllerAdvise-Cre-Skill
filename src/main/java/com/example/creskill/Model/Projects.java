package com.example.creskill.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;
//role= project owner
    @Column(columnDefinition = "int not null")
    private Integer userid;
    @NotEmpty(message = "Title is mandatory")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @NotEmpty(message = "Description is mandatory")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;
    @ElementCollection
    private List<String> requiredSkills;
    @NotNull(message = "Budget is mandatory")
    @Column(columnDefinition = "double not null")
    private Double budget;

    @Pattern(regexp = "open|inprogress|completed|delivered")
    @Column(columnDefinition = "varchar(20)")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate createdAt;
//talent in user id
    @Column(columnDefinition = "int")
    private Integer talentId;
}
