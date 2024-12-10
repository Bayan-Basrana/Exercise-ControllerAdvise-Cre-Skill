package com.example.creskill.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingId;

//userid المستخدم الذي يتم تقييمه
    @Column(columnDefinition = "int not null")
    private Integer ratedUserId;
//userid المستخدم الذي يقوم بالتفييم
    @Column(columnDefinition = "int not null")
    private Integer raterUserId;

    @Column(columnDefinition = "int")
    private Integer projectId;

    @Column(columnDefinition = "int")
    private Integer skillExchangeId;

    @NotEmpty(message = "comments is mandatory")
    @Column(columnDefinition = "varchar(100) not null")
    private String comments;

    @NotNull(message = "rating is mandatory")
    @Min(1)
    @Max(5)
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate createdAt;

}
