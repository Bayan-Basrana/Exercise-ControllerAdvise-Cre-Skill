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
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;

    @Column(columnDefinition = "int not null")
    private Integer projectId;

    @Column(columnDefinition = "int not null")
    private Integer userid;

    @NotEmpty(message = "message is mandatory")
    @Column(columnDefinition = "varchar(1000) not null")
    private String message;

    @NotNull(message = "budget is mandatory")
    @Positive(message = "budget must be positive")
    @Column(columnDefinition = "int not null")
    private Integer budget;

    @Column(columnDefinition = "varchar(20) default 'pending'")
    @Pattern(regexp = "accepted|pending|completed|rejected")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate createdAt;
}
