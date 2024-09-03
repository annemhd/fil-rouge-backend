package com.filrougeapp.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 512, nullable = false, unique = true)
    private String name;

    private Long size;

    private Date date;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
}