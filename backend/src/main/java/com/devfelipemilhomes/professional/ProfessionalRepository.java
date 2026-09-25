package com.devfelipemilhomes.professional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    public boolean existsByCpf(String cpf);
    public Professional findByCpf(String cpf);
}
