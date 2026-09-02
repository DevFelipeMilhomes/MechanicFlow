# MechanicFlow — Regras de Negócio

**Data:** 01/09/2026  
**Versão:** v1.0

---

## 1. Objetivo

Este documento define as regras de negócio que determinam o funcionamento da oficina no contexto do MechanicFlow.

As regras representam comportamentos e restrições do negócio.

# 2. Clientes e veículos

### RN-001 — Associação entre cliente e veículo

Um cliente pode possuir um ou mais veículos.

### RN-002 — Associação do veículo

Um veículo deve possuir um proprietário registrado.

O proprietário do veículo não precisa ser necessariamente o cliente responsável pelo atendimento.

### RN-003 — Cliente e proprietário

O cliente que solicita o atendimento pode ser diferente do proprietário do veículo.

### RN-004 — Dados obrigatórios do cliente

O cadastro de um cliente deve possuir, obrigatoriamente:

- nome;
- telefone;
- CPF.

### RN-005 — Dados obrigatórios do veículo

O cadastro de um veículo deve possuir, obrigatoriamente:

- marca;
- modelo;
- placa;
- identificação do proprietário.

### RN-006 — Registro do proprietário

O sistema deve armazenar a identificação do proprietário do veículo no momento em que o veículo for cadastrado ou quando seu proprietário for atualizado.

### RN-007 — Alteração do proprietário

O proprietário registrado de um veículo pode ser alterado quando houver mudança de propriedade ou necessidade de correção do cadastro.

A alteração do proprietário do veículo não deve modificar informações já registradas em ordens de serviço anteriores.

### RN-008 — Histórico do proprietário

A alteração do proprietário de um veículo não deve modificar retroativamente o proprietário registrado nas ordens de serviço já existentes.

---

# 3. Profissionais da oficina

### RN-009 — Função do profissional

Todo profissional cadastrado deve possuir uma função dentro da oficina.

Na versão 1.0, são consideradas as funções:

- mecânico;
- recepcionista;
- gerente.

### RN-010 — Permissões por função

As atividades que um profissional pode realizar no sistema devem ser determinadas pela sua função.

### RN-011 — Cadastro e consulta de clientes e veículos

Mecânicos e recepcionistas podem cadastrar e consultar clientes e veículos.

### RN-012 — Gerenciamento das próprias ordens

Um mecânico pode criar e consultar suas ordens de serviço e alterar o estado das ordens pelas quais é responsável.

### RN-013 — Ordem pertencente a outro mecânico

Um mecânico não pode assumir diretamente uma ordem de serviço criada por outro mecânico.

### RN-014 — Transferência de atendimento entre mecânicos

Quando for necessário que outro mecânico execute o atendimento de uma ordem criada por outro mecânico, uma nova ordem de serviço poderá ser criada para o novo responsável, desde que o cliente autorize o procedimento.

A ordem de serviço anterior deverá ser cancelada.

### RN-015 — Administração do estoque

Somente o gerente pode realizar alterações administrativas no estoque.

### RN-016 — Administração de clientes e veículos

Somente o gerente pode alterar ou excluir dados cadastrais de clientes e veículos.

### RN-017 — Administração de profissionais

Somente o gerente pode criar, alterar ou excluir profissionais da oficina.

---

# 4. Ordem de serviço

### RN-018 — Associação da ordem de serviço

Toda ordem de serviço deve estar associada a um veículo e ao cliente responsável pelo atendimento.

### RN-019 — Estado da ordem de serviço

Toda ordem de serviço deve possuir um estado que represente sua situação atual no processo de atendimento.

### RN-020 — Responsável pela execução

Uma ordem de serviço em execução deve possuir pelo menos um profissional responsável.

### RN-021 — Estados da ordem de serviço

A ordem de serviço deve seguir um fluxo de estados definido pelo processo de atendimento.

Os estados considerados inicialmente são:

```text
ABERTA
   ↓
EM_EXECUCAO
   ↓
CONCLUIDA
```

Uma ordem de serviço também pode assumir o estado:

```text
CANCELADA
```

### RN-022 — Transições de estado

Uma ordem de serviço somente pode realizar transições de estado permitidas pelo seu ciclo de vida.

### RN-023 — Imutabilidade da ordem após criação

Após sua criação, os dados da ordem de serviço não podem ser alterados.

Somente o estado da ordem de serviço pode ser alterado durante seu ciclo de vida, respeitando as transições permitidas.

### RN-024 — Registro imutável

Os dados registrados em uma ordem de serviço representam as informações do atendimento no momento de sua criação e devem permanecer preservados.

### RN-025 — Novas informações após criação

Quando uma nova necessidade, problema, serviço ou situação não estiver contemplada na ordem de serviço existente, deverá ser criada uma nova ordem de serviço em vez de alterar a ordem original.

### RN-026 — Ordem concluída

Uma ordem de serviço somente pode ser concluída após o cumprimento das condições necessárias para sua finalização.

### RN-027 — Imutabilidade após conclusão

Uma ordem de serviço concluída não pode retornar para um estado anterior.

### RN-028 — Reabertura

Uma ordem de serviço concluída não pode ser reaberta.

### RN-029 — Cancelamento

Uma ordem de serviço pode ser cancelada conforme as condições previstas no processo de atendimento.

### RN-030 — Imutabilidade após cancelamento

Uma ordem de serviço cancelada não pode retornar para um estado anterior.

### RN-031 — Persistência de ordens canceladas

Ordens de serviço canceladas devem permanecer armazenadas no sistema.

---

# 5. Diagnóstico

### RN-032 — Necessidade de diagnóstico

Quando o problema apresentado pelo cliente não puder ser identificado durante a avaliação inicial, o veículo deve passar por um processo de diagnóstico.

### RN-033 — Ordem de serviço de diagnóstico

Quando houver necessidade de diagnóstico, deve ser criada uma ordem de serviço destinada ao diagnóstico do problema.

### RN-034 — Registro do diagnóstico

O resultado do diagnóstico deve ser registrado antes da criação da ordem de serviço referente aos serviços decorrentes do diagnóstico.

### RN-035 — Serviços decorrentes do diagnóstico

Após a conclusão do diagnóstico, os serviços necessários para solucionar o problema identificado devem ser definidos em uma nova ordem de serviço.

### RN-036 — Conclusão da ordem de diagnóstico

A ordem de serviço de diagnóstico deve ser concluída após a criação da ordem de serviço referente aos serviços autorizáveis decorrentes do diagnóstico.

### RN-037 — Isenção da ordem de diagnóstico

Quando o cliente autorizar a realização de serviços decorrentes do diagnóstico, o valor referente à ordem de serviço de diagnóstico não será cobrado.

### RN-038 — Diagnóstico sem autorização

Caso o cliente não autorize a realização dos serviços decorrentes do diagnóstico, a ordem de serviço de diagnóstico será concluída sem que uma nova ordem de execução seja criada.

---

# 6. Autorização dos serviços

### RN-039 — Necessidade de autorização

Serviços identificados após o diagnóstico devem ser autorizados pelo cliente antes de sua execução.

### RN-040 — Autorização total ou parcial

O cliente pode autorizar todos os serviços propostos ou somente parte deles.

### RN-041 — Serviço não autorizado

Um serviço que não tenha sido autorizado pelo cliente não pode ser executado.

### RN-042 — Registro da recusa

A recusa do cliente em realizar um ou mais serviços deve ser registrada no atendimento.

### RN-043 — Nova ordem após autorização parcial

Quando o cliente autorizar somente parte dos serviços propostos, deverá ser criada uma nova ordem de serviço contendo exclusivamente os serviços autorizados.

### RN-044 — Execução após autorização

A execução dos serviços somente pode ocorrer após o registro da autorização correspondente.

### RN-045 — Alteração após autorização

Após a autorização de uma ordem de serviço, novos serviços não podem ser adicionados à ordem já criada.

Caso sejam identificados serviços adicionais, uma nova ordem de serviço deverá ser criada e submetida à autorização do cliente.

---

# 7. Execução dos serviços

### RN-046 — Registro dos serviços executados

Os serviços efetivamente realizados devem estar registrados na ordem de serviço correspondente.

### RN-047 — Registro das peças utilizadas

As peças efetivamente utilizadas durante a execução dos serviços devem estar registradas na ordem de serviço correspondente.

### RN-048 — Quantidade da peça utilizada

Toda peça registrada como utilizada deve possuir uma quantidade correspondente.

### RN-049 — Novo problema durante a execução

Caso um novo problema seja identificado durante a execução de uma ordem de serviço, o cliente deve ser informado.

### RN-050 — Autorização para novo problema

O novo serviço decorrente do problema identificado durante a execução somente poderá ser realizado após autorização do cliente.

### RN-051 — Nova ordem para serviço adicional

Quando o cliente autorizar o serviço decorrente de um novo problema, deverá ser criada uma nova ordem de serviço para representar esse novo atendimento.

A ordem original não deve ser alterada para incorporar o novo problema ou serviço.

---

# 8. Peças e estoque

## 8.1 Estados da peça na ordem de serviço

### RN-052 — Reserva de peças

Uma peça associada a uma ordem de serviço pode assumir a condição de **reservada** antes de sua utilização efetiva.

### RN-053 — Quantidade reservada

Toda peça reservada deve possuir uma quantidade definida.

### RN-054 — Disponibilidade para reserva

Uma peça somente pode ser reservada caso exista quantidade disponível suficiente no estoque.

### RN-055 — Utilização de peça reservada

Uma peça reservada deve ser marcada como utilizada quando efetivamente for aplicada na execução de um serviço.

### RN-056 — Quantidade utilizada

A quantidade efetivamente utilizada deve ser registrada separadamente da quantidade reservada.

### RN-057 — Peça não utilizada

Uma peça reservada que não tenha sido utilizada deve permanecer disponível para devolução ao estoque.

---

## 8.2 Estoque

### RN-058 — Estoque não negativo

A quantidade disponível de uma peça no estoque não pode ser inferior a zero.

### RN-059 — Entrada de estoque

A entrada de peças deve aumentar a quantidade disponível correspondente no estoque.

### RN-060 — Saída de estoque

Uma saída de estoque deve reduzir a quantidade disponível correspondente.

### RN-061 — Reserva de estoque

Quando uma peça for reservada para uma ordem de serviço, a quantidade correspondente deve deixar de estar disponível para novas reservas.

### RN-062 — Utilização de peça

Quando uma peça reservada for efetivamente utilizada, sua utilização deve ser registrada na ordem de serviço.

### RN-063 — Cancelamento de ordem com peças reservadas

Quando uma ordem de serviço for cancelada, as quantidades de peças reservadas que não tenham sido utilizadas devem voltar a ficar disponíveis no estoque.

### RN-064 — Peças utilizadas antes do cancelamento

Peças que já tenham sido efetivamente utilizadas antes do cancelamento de uma ordem de serviço não devem ser devolvidas ao estoque.

---

# 9. Cancelamento

### RN-065 — Estado de cancelamento

Uma ordem de serviço cancelada deve assumir o estado `CANCELADA`.

### RN-066 — Motivo do cancelamento

O cancelamento de uma ordem de serviço deve possuir um motivo registrado.

### RN-067 — Cancelamento e estoque

O cancelamento de uma ordem deve liberar as peças reservadas que não tenham sido utilizadas.

### RN-068 — Cancelamento e serviços executados

Serviços que já tenham sido executados antes do cancelamento não devem ser considerados como não executados apenas em razão do cancelamento.

### RN-069 — Histórico de ordens canceladas

Ordens canceladas devem permanecer armazenadas para preservar o histórico dos atendimentos e das decisões realizadas.

---

# 10. Histórico

### RN-070 — Persistência do histórico

As ordens de serviço concluídas devem permanecer registradas para consulta do histórico do veículo.

### RN-071 — Histórico de ordens canceladas

As ordens de serviço canceladas também devem permanecer registradas no sistema.

### RN-072 — Integridade histórica

Informações registradas em uma ordem de serviço não devem ser modificadas retroativamente por alterações realizadas no cadastro do cliente, veículo ou proprietário.

### RN-073 — Histórico do veículo

O histórico de atendimento deve permanecer associado ao veículo.

### RN-074 — Histórico e alteração do proprietário

A alteração do proprietário atual do veículo não deve alterar o proprietário registrado em atendimentos anteriores.

---

# 11. Exclusão e retenção de dados

### RN-075 — Prazo mínimo de retenção

Os dados de clientes e veículos não poderão ser excluídos enquanto houver uma ordem de serviço relacionada a eles dentro do período de retenção definido pela oficina.

### RN-076 — Prazo de cinco anos

A exclusão de um cliente ou veículo somente poderá ocorrer quando tiverem transcorrido pelo menos cinco anos desde a última ordem de serviço relacionada ao cliente ou ao veículo.

### RN-077 — Cliente com histórico recente

Um cliente que tenha gerado ou participado de uma ordem de serviço nos últimos cinco anos não poderá ser excluído.

### RN-078 — Veículo com histórico recente

Um veículo que possua ordem de serviço nos últimos cinco anos não poderá ser excluído.

### RN-079 — Exclusão administrativa

A exclusão de clientes e veículos deve ser realizada exclusivamente pelo gerente.

### RN-080 — Preservação de registros

A exclusão de um cliente ou veículo não deve resultar na alteração ou descaracterização de registros históricos que devam permanecer armazenados.

---

# 12. Regras de autorização e responsabilidade

### RN-081 — Autoridade do gerente

O gerente possui autoridade administrativa superior aos demais profissionais para as operações de gerenciamento previstas neste documento.

### RN-082 — Responsabilidade do mecânico

Um mecânico somente pode alterar o estado das ordens de serviço pelas quais seja responsável.

### RN-083 — Responsabilidade exclusiva

Um mecânico não pode executar ou alterar diretamente uma ordem de serviço pertencente a outro mecânico.

### RN-084 — Novo responsável

Quando o atendimento precisar ser transferido para outro mecânico, deverá ser criada uma nova ordem de serviço conforme as regras de autorização e cancelamento.
