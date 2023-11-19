package com.template.springbootbackend.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Column(name = "modificated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate modificatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDate.now();
        this.modificatedAt = LocalDate.now();
    }

    public Region() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getModificatedAt() {
        return modificatedAt;
    }

    public void setModificatedAt(LocalDate modificatedAt) {
        this.modificatedAt = modificatedAt;
    }
}
