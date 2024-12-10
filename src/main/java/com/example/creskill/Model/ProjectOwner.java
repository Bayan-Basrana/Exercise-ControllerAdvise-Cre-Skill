package com.example.creskill.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ProjectOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ownerId;

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

    @NotEmpty(message = "location is mandatory")
    @Column(columnDefinition = "varchar(10) not null")
    private String location;

}
