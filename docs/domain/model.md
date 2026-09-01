# MechanicFlow — Domínio do Software

**Data:** 01/09/2026  
**Versão:** v1.0

## 1. Visão geral

O **MechanicFlow** é um sistema destinado ao gerenciamento das principais operações de uma oficina mecânica.

Na versão inicial, o sistema tem como objetivo organizar e centralizar informações relacionadas a:

- clientes;
- veículos;
- profissionais da oficina;
- ordens de serviço;
- serviços realizados;
- peças;
- estoque.

O sistema representa o funcionamento de uma oficina mecânica na qual diferentes profissionais participam do atendimento e da execução dos serviços.

A primeira versão terá como foco o **controle operacional da oficina**, não contemplando inicialmente funcionalidades financeiras ou integrações externas.

---

## 2. Contexto do domínio

O domínio do sistema é uma **oficina mecânica** responsável por receber veículos, identificar problemas, realizar diagnósticos, executar serviços de manutenção ou reparo e registrar as peças utilizadas.

A oficina pode possuir diversos profissionais, com responsabilidades diferentes dentro do processo de atendimento.

Os principais envolvidos no domínio são:

### Cliente

Pessoa responsável pelo veículo que solicita atendimento à oficina.

Um cliente pode possuir um ou mais veículos.

### Veículo

Automóvel pertencente a um cliente e submetido aos serviços da oficina.

Um veículo possui informações de identificação e características necessárias para seu acompanhamento durante os atendimentos.

### Profissional

Pessoa que trabalha na oficina e utiliza o sistema para executar ou acompanhar atividades relacionadas ao atendimento.

Na primeira versão, os profissionais podem possuir diferentes responsabilidades, como:

- mecânico;
- gerente;
- recepcionista.

Os detalhes das permissões de cada função serão definidos conforme as regras de acesso do sistema.

### Ordem de Serviço

Registro que representa um atendimento ou conjunto de atividades relacionadas a um veículo dentro da oficina.

A ordem de serviço permite registrar o motivo do atendimento, diagnóstico, serviços a serem realizados, peças necessárias, responsáveis e a situação atual do atendimento.

### Serviço

Atividade realizada sobre o veículo, como troca de óleo, substituição de componentes, reparo mecânico, diagnóstico, entre outras.

### Peça

Componente ou material utilizado na realização de um serviço.

### Estoque

Conjunto de peças disponíveis na oficina e suas respectivas quantidades.

---

## 3. Principais relacionamentos do domínio

As principais relações identificadas inicialmente são:

- um **cliente** pode possuir vários **veículos**;
- um **veículo** pode possuir vários registros de **ordem de serviço** ao longo do tempo;
- uma **ordem de serviço** pertence a um **veículo** e está associada ao respectivo cliente;
- uma **ordem de serviço** pode possuir um ou mais **serviços**;
- uma **ordem de serviço** pode utilizar uma ou mais **peças**;
- uma **peça** pode ser utilizada em várias ordens de serviço;
- um **profissional** pode ser responsável pela execução ou acompanhamento de uma ordem de serviço;
- uma **peça** possui controle de quantidade no estoque.

Essas relações representam o modelo inicial do domínio e poderão ser refinadas conforme novas regras de negócio forem identificadas.

---

## 4. Fluxo operacional

O fluxo abaixo representa um cenário de atendimento em que o problema apresentado pelo cliente exige um diagnóstico mais detalhado.

### 4.1 Entrada do veículo

1. O cliente leva o veículo até a oficina.
2. O cliente informa o problema observado ou o serviço que deseja realizar.
3. Um profissional da oficina avalia inicialmente a situação.

### 4.2 Cadastro

4. Caso o cliente ainda não possua cadastro, seus dados são registrados no sistema.
5. Caso o veículo ainda não esteja cadastrado, seus dados são associados ao cliente e registrados no sistema.

### 4.3 Diagnóstico

6. Quando o problema não pode ser identificado apenas por uma inspeção inicial e exige uma investigação mais detalhada, é criada uma **ordem de serviço de diagnóstico**.
7. O profissional realiza os procedimentos necessários para identificar a causa do problema.
8. O diagnóstico e as observações relevantes são registrados na ordem de serviço.
9. O cliente pode aguardar na oficina ou deixar o veículo para posteriormente receber o resultado do diagnóstico.

### 4.4 Orçamento e autorização

10. Após a conclusão do diagnóstico, são definidos os serviços necessários para solucionar o problema.
11. Quando necessário, são identificadas as peças que deverão ser substituídas.
12. Os serviços e peças necessários são registrados no atendimento.
13. O cliente é informado sobre o que deverá ser realizado.
14. A execução dos serviços depende da autorização do cliente.

### 4.5 Execução

15. Após a autorização, os serviços são executados pelos profissionais responsáveis.
16. As peças utilizadas são registradas na ordem de serviço.
17. A utilização das peças deve refletir a quantidade disponível no estoque.
18. O progresso da execução pode ser acompanhado por meio do status da ordem de serviço.

### 4.6 Finalização

19. Após a conclusão dos serviços, o profissional registra a finalização da ordem de serviço.
20. A ordem de serviço passa para o estado correspondente à conclusão do atendimento.
21. O histórico do atendimento permanece associado ao veículo e ao cliente.