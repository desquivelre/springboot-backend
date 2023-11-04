package com.template.springbootbackend.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni", nullable = false)
    @Size(max = 99999999, min = 00000001, message = "The value of a class attribute is outside of stablished range")
    private int dni;

    @Column(name = "first_names", nullable = false)
    private String firstNames;

    @Column(name = "last_names", nullable = false)
    private String lastNames;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "cellphone_number", nullable = false)
    @Size(max = 999999999, min = 000000001, message = "The value of a class attribute is outside of stablished range")
    private int cellphoneNumber;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Column(name = "modificated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate modificatedAt;

    public Client() {
    }

    public Client(int dni, String firstNames, String lastNames, String email, int cellphoneNumber) {
        this.dni = dni;
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.email = email;
        this.cellphoneNumber = cellphoneNumber;
        this.createdAt = LocalDate.now();
        this.modificatedAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
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

    public int getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(int cellphoneNumber) {
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
