package ru.improve.openfy.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.improve.openfy.core.track.enums.MusicFormat;

@Entity
@Table(name = "tracks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    private MusicFormat format;

    private long size;

    private String hash;

    @Column(name = "uploader")
    private long uploaderId;
}
