package com.devfelipemilhomes.client;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String phone;

    private String email;

    @Column(name = "created_at", updatable = false, nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(cpf, client.cpf) && Objects.equals(phone, client.phone) && Objects.equals(email, client.email) && Objects.equals(createdAt, client.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, phone, email, createdAt);
    }
}
