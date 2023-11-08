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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Max(99999999)
    @Min(10000000)
    @Column(name = "dni", nullable = false)
    private Integer dni;

    @NotEmpty
    @Column(name = "first_names", nullable = false)
    private String firstNames;

    @NotEmpty
    @Column(name = "last_names", nullable = false)
    private String lastNames;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "cellphone_number", nullable = false)
    @Max(999999999)
    @Min(100000000)
    private Integer cellphoneNumber;

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

    public Client() {
    }

    public Client(int dni, String firstNames, String lastNames, String email, int cellphoneNumber) {
        this.dni = dni;
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.email = email;
        this.cellphoneNumber = cellphoneNumber;
    }

    public Long getId() {
        return id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public Integer getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(Integer cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
