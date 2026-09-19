package com.devfelipemilhomes.professional;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "professional")
@Data
public class Professional {

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
