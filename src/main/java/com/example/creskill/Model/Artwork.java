package com.example.creskill.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artworkId;

    @Column(columnDefinition = "int not null")
    private Integer userid;

    @NotEmpty(message = "Title is mandatory")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;

    @NotEmpty(message = "category is mandatory")
    @Column(columnDefinition = "varchar(50) not null")
    private String category;

    @NotEmpty(message = "description is mandatory")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate uploadDate;

    @Column(columnDefinition = "int ")
    private Integer likes;
}
