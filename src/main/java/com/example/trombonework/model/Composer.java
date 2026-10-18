package com.example.trombonework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "composers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private Integer birthYear;
    private Integer deathYear;
    private String era;
    @Column(length = 1000)
    private String biography;
    private String portraitUrl;

    // Порожній конструктор для Jackson (JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Composer() {}

    private Composer(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.nationality = builder.nationality;
        this.birthYear = builder.birthYear;
        this.deathYear = builder.deathYear;
        this.era = builder.era;
        this.biography = builder.biography;
        this.portraitUrl = builder.portraitUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Зручний метод для отримання повного імені
    @JsonIgnore
    public String getFullName() {
        if (firstName == null || firstName.isBlank()) return lastName != null ? lastName : "";
        if (lastName == null || lastName.isBlank()) return firstName;
        return firstName + " " + lastName;
    }



    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

    public String getPortraitUrl() { return portraitUrl; }
    public void setPortraitUrl(String portraitUrl) { this.portraitUrl = portraitUrl; }

    @Override
    public String toString() {
        return getFullName();
    }

    // Builder Pattern
    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String nationality;
        private Integer birthYear;
        private Integer deathYear;
        private String era;
        private String biography;
        private String portraitUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder nationality(String nationality) { this.nationality = nationality; return this; }
        public Builder birthYear(Integer birthYear) { this.birthYear = birthYear; return this; }
        public Builder deathYear(Integer deathYear) { this.deathYear = deathYear; return this; }
        public Builder era(String era) { this.era = era; return this; }
        public Builder biography(String biography) { this.biography = biography; return this; }
        public Builder portraitUrl(String portraitUrl) { this.portraitUrl = portraitUrl; return this; }

        public Composer build() {
            return new Composer(this);
        }
    }
}
