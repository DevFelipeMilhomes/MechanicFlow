package com.devfelipemilhomes.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>, QueryByExampleExecutor<Client> {
    boolean existsByCpf(String cpf);
    Client findByCpf(String cpf);
}
