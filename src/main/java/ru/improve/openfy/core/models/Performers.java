package ru.improve.openfy.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "performers")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performers {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String name;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "upload_date")
    private LocalDate uploadDate;
}
