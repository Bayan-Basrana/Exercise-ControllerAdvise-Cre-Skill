package com.example.creskill.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    @NotEmpty(message = "Name is mandatory")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is mandatory")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @ElementCollection
    private List<String> skillsOffered;
    @ElementCollection
    private List<String> skillsWanted;
    @NotEmpty(message = "bio is mandatory")
    @Column(columnDefinition = "varchar(500) not null")
    private String bio;

    @Column(columnDefinition = "boolean not null")
    private Boolean isVerified=false;

    @Column(columnDefinition = "int")
    private Integer points;
}
