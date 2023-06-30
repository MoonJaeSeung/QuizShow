package com.example.gp.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter @Setter
public class Celeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String fileName;

    private String fileOriName;

    private String fileUrl;

    public void updateCeleb(String filename, String fileOriName, String fileUrl) {
        this.fileName = filename;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }
}
