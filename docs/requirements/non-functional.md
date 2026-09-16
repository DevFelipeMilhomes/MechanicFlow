# MechanicFlow — Requisitos Não Funcionais

**Data:** 01/09/2026  
**Versão:** v1.0

---

## 1. Objetivo

Este documento define os **requisitos não funcionais** da versão inicial do MechanicFlow.

Os requisitos não funcionais estabelecem características, restrições e critérios de qualidade que o sistema deve atender, independentemente das funcionalidades oferecidas.

Eles complementam os requisitos funcionais definidos em [`functional.md`](./functional.md) e devem ser considerados durante a definição da arquitetura, implementação, testes e implantação do sistema.

---

## 2. Convenção de identificação

Os requisitos não funcionais são identificados por códigos únicos:

- **RNF-XXX** — requisito não funcional principal;
- **RNF-XXX.X** — requisito não funcional subordinado.

Os requisitos são organizados por categorias de qualidade e restrição.

---

# 3. Segurança

## RNF-001 — Autenticação

O sistema deve exigir autenticação para que profissionais tenham acesso às funcionalidades protegidas.

### RNF-001.1 — Identificação do usuário

Cada profissional que utilizar o sistema deve possuir uma identificação individual.

### RNF-001.2 — Credenciais

As credenciais utilizadas para autenticação não devem ser armazenadas em formato que permita recuperar diretamente a senha original.

### RNF-001.3 — Encerramento de sessão

O sistema deve permitir o encerramento da sessão autenticada.

### RNF-001.4 — Sessão inativa

O sistema deve encerrar ou invalidar sessões após um período de inatividade definido na configuração da aplicação.

---

## RNF-002 — Autorização

O sistema deve controlar o acesso às funcionalidades de acordo com a função do profissional.

### RNF-002.1 — Restrição por função

Um profissional não deve conseguir executar operações que não estejam permitidas para sua função.

### RNF-002.2 — Validação no servidor

As restrições de autorização devem ser aplicadas no backend, não somente na interface do sistema.

### RNF-002.3 — Proteção contra acesso direto

Um usuário que tente acessar diretamente um recurso para o qual não possui permissão deve ter a operação recusada.

---

## RNF-003 — Proteção de credenciais

As credenciais dos profissionais devem ser protegidas durante armazenamento e transmissão.

### RNF-003.1 — Senhas

As senhas devem ser armazenadas utilizando mecanismo de hash adequado para armazenamento de credenciais.

### RNF-003.2 — Comunicação

Quando o sistema estiver implantado em ambiente que transmita dados por rede não confiável, a comunicação deve utilizar HTTPS/TLS.

---

# 4. Integridade e consistência dos dados

## RNF-004 — Integridade dos dados

O sistema deve preservar a consistência dos dados armazenados.

### RNF-004.1 — Integridade referencial

Relacionamentos entre entidades devem impedir referências para registros inexistentes.

### RNF-004.2 — Dados obrigatórios

O sistema deve impedir o armazenamento de registros que não possuam os dados obrigatórios definidos pelos requisitos funcionais e regras de negócio.

### RNF-004.3 — Operações consistentes

Operações que alterem múltiplos registros relacionados devem ser executadas de forma que não deixem o sistema em estado parcialmente atualizado.

---

## RNF-005 — Consistência do estoque

As operações relacionadas a reservas, utilizações, entradas, saídas e devoluções de peças devem preservar a consistência das quantidades armazenadas.

### RNF-005.1 — Não negativação

O sistema não deve permitir que uma operação resulte em estoque disponível negativo.

### RNF-005.2 — Operações concorrentes

O sistema deve tratar operações concorrentes sobre uma mesma peça de forma que duas operações simultâneas não possam utilizar ou reservar a mesma quantidade disponível.

---

## RNF-006 — Imutabilidade das ordens

O sistema deve preservar a integridade histórica das ordens de serviço.

### RNF-006.1 — Proteção contra alteração

Os dados imutáveis de uma ordem de serviço não devem poder ser alterados após sua criação.

### RNF-006.2 — Proteção contra alteração indireta

Alterações posteriores realizadas em clientes, veículos ou proprietários não devem modificar os dados históricos armazenados nas ordens de serviço.

### RNF-006.3 — Preservação após conclusão

Os dados de uma ordem concluída devem permanecer disponíveis sem alteração.

### RNF-006.4 — Preservação após cancelamento

Os dados de uma ordem cancelada devem permanecer disponíveis sem alteração, exceto pelo registro do próprio estado de cancelamento e informações explicitamente permitidas pelo modelo.

---

# 5. Desempenho

## RNF-007 — Tempo de resposta

Para operações comuns, o sistema deve responder em tempo adequado para utilização durante o atendimento da oficina.

### RNF-007.1 — Consultas comuns

Consultas simples de clientes, veículos, peças e ordens de serviço devem apresentar resposta em até **2 segundos** em condições normais de operação.

### RNF-007.2 — Operações de cadastro

Operações comuns de cadastro e atualização devem apresentar resposta em até **2 segundos** em condições normais de operação.

### RNF-007.3 — Operações de estoque

Operações de reserva, utilização, entrada e saída de peças devem apresentar resposta em até **2 segundos** em condições normais de operação.

---

## RNF-008 — Concorrência

O sistema deve permitir que múltiplos profissionais utilizem a aplicação simultaneamente.

### RNF-008.1 — Operações simultâneas

Operações simultâneas não devem causar perda, duplicação ou corrupção de dados.

### RNF-008.2 — Concorrência no estoque

Operações simultâneas de reserva ou utilização de uma mesma peça devem respeitar a quantidade efetivamente disponível.

---

# 6. Disponibilidade e recuperação

## RNF-009 — Disponibilidade

O sistema deve permanecer disponível durante o horário de funcionamento da oficina, considerando as limitações do ambiente de infraestrutura utilizado na versão inicial.

### RNF-009.1 — Inicialização

A aplicação deve poder ser iniciada de forma previsível e sem necessidade de intervenção manual recorrente.

### RNF-009.2 — Recuperação após falha

Após uma falha inesperada da aplicação, o sistema deve ser capaz de retornar à operação sem corromper os dados persistidos.

---

## RNF-010 — Backup

Os dados persistentes do sistema devem possuir mecanismo de backup.

### RNF-010.1 — Recuperação

Deve ser possível restaurar os dados a partir de um backup válido.

### RNF-010.2 — Periodicidade

A periodicidade dos backups deverá ser definida conforme o ambiente de implantação.

---

# 7. Auditabilidade e rastreabilidade

## RNF-011 — Rastreabilidade das operações relevantes

O sistema deve permitir identificar o profissional responsável por operações relevantes realizadas sobre os registros.

### RNF-011.1 — Identificação do profissional

Operações relevantes devem possuir identificação do profissional responsável quando aplicável.

### RNF-011.2 — Alterações de estado

As alterações de estado das ordens de serviço devem ser rastreáveis.

### RNF-011.3 — Operações de estoque

Operações administrativas de estoque devem ser rastreáveis.

### RNF-011.4 — Cancelamentos

O cancelamento de uma ordem de serviço deve ser rastreável, incluindo o profissional responsável e o motivo informado.

---

# 8. Usabilidade

## RNF-012 — Interface consistente

A interface deve apresentar comportamento e organização consistentes entre as diferentes funcionalidades do sistema.

### RNF-012.1 — Navegação

As principais funcionalidades devem ser acessíveis por uma estrutura de navegação previsível.

### RNF-012.2 — Feedback das operações

O sistema deve informar ao usuário o resultado de operações realizadas, incluindo sucesso, falha e validações.

### RNF-012.3 — Mensagens de erro

As mensagens de erro devem informar de forma clara o problema identificado e, quando possível, indicar como corrigi-lo.

---

## RNF-013 — Prevenção de operações inválidas

A interface deve impedir ou alertar o usuário antes da realização de operações que violem regras conhecidas do sistema.

### RNF-013.1 — Operações não permitidas

Funcionalidades sem permissão para o usuário não devem ser disponibilizadas para execução normal pela interface.

### RNF-013.2 — Validação de dados

Campos obrigatórios e formatos inválidos devem ser identificados antes do envio da operação.

---

# 9. Manutenibilidade

## RNF-014 — Código organizado

O código-fonte deve ser organizado de forma modular, permitindo a evolução independente das principais partes do sistema.

### RNF-014.1 — Separação de responsabilidades

Cada componente deve possuir responsabilidades claramente definidas.

### RNF-014.2 — Baixo acoplamento

Os componentes devem evitar dependências desnecessárias entre si.

### RNF-014.3 — Alta coesão

Cada módulo deve concentrar funcionalidades relacionadas ao mesmo contexto.

---

## RNF-015 — Testabilidade

O sistema deve ser desenvolvido de forma que as principais regras e comportamentos possam ser testados automaticamente.

### RNF-015.1 — Regras de negócio

As regras de negócio críticas devem poder ser verificadas por testes automatizados.

### RNF-015.2 — Serviços

Os principais serviços da aplicação devem poder ser testados independentemente da interface gráfica.

### RNF-015.3 — Persistência

Operações críticas de persistência e integridade de dados devem possuir testes apropriados.

---

## RNF-016 — Documentação técnica

As partes relevantes da arquitetura e do comportamento do sistema devem estar documentadas.

### RNF-016.1 — Arquitetura

As principais decisões arquiteturais devem estar documentadas.

### RNF-016.2 — Modelo de dados

A estrutura e os relacionamentos relevantes do banco de dados devem ser documentados.

### RNF-016.3 — APIs

Endpoints públicos da aplicação devem possuir documentação suficiente para utilização e manutenção.

---

# 10. Compatibilidade e portabilidade

## RNF-017 — Compatibilidade com navegadores

A aplicação web deve funcionar corretamente nas versões recentes dos principais navegadores utilizados no ambiente da oficina.

A lista exata de navegadores suportados deverá ser definida durante a implantação.

---

## RNF-018 — Ambiente de execução

A aplicação deve possuir configuração documentada para execução em ambiente de desenvolvimento e em ambiente de implantação.

### RNF-018.1 — Configuração

Configurações específicas do ambiente não devem depender de valores fixos diretamente no código-fonte.

### RNF-018.2 — Variáveis de ambiente

Informações sensíveis e configurações dependentes do ambiente devem poder ser fornecidas por mecanismos apropriados de configuração.

---

# 11. Escalabilidade

## RNF-019 — Crescimento de dados

O sistema deve ser capaz de continuar operando com o crescimento gradual da quantidade de clientes, veículos, ordens de serviço, peças e registros históricos sem necessidade de alteração da lógica de negócio.

### RNF-019.1 — Crescimento do histórico

O aumento do número de ordens de serviço não deve invalidar o histórico existente.

### RNF-019.2 — Crescimento do estoque

O aumento do número de peças cadastradas não deve exigir alteração estrutural da lógica de gerenciamento do estoque.

---

# 12. Privacidade e proteção de dados

## RNF-020 — Proteção de dados pessoais

Os dados pessoais armazenados pelo sistema devem ser acessíveis somente a profissionais autorizados.

### RNF-020.1 — Controle de acesso

Dados de clientes e proprietários não devem ser disponibilizados a usuários sem permissão correspondente.

### RNF-020.2 — Minimização de exposição

O sistema não deve exibir dados pessoais além daqueles necessários para a operação atual.

### RNF-020.3 — Exclusão

Operações de exclusão de dados devem obedecer às regras de retenção definidas no domínio.

---

# 13. Versionamento e evolução

## RNF-021 — Compatibilidade com evolução dos requisitos

A implementação deve permitir que requisitos sejam alterados ou adicionados sem exigir reestruturação completa da aplicação.

### RNF-021.1 — Novos estados da OS

A estrutura responsável pelo ciclo de vida da ordem deve permitir a inclusão ou alteração controlada de estados.

### RNF-021.2 — Novas funções

O controle de acesso deve permitir a inclusão de novas funções ou alterações de permissões sem reescrever completamente as funcionalidades existentes.

### RNF-021.3 — Novos módulos

A arquitetura deve permitir a inclusão futura de módulos como pagamentos e integração com WhatsApp.

---
