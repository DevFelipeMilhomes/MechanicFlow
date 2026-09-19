package com.devfelipemilhomes.client;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.OffsetDateTime;

@Entity
@Table(name="client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String phone;

    private String email;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

}
