# MechanicFlow — Requisitos Funcionais

**Data:** 01/09/2026  
**Versão:** v1.0

---

## 1. Objetivo

Este documento define os requisitos funcionais da versão 1.1 do MechanicFlow.

Os requisitos descrevem as funcionalidades que o sistema deve disponibilizar para apoiar o gerenciamento das operações de uma oficina mecânica.

Os requisitos foram definidos com base no domínio descrito em [`/docs/domain/model.md`](../domain/model.md) e nas regras de negócio definidas em [`business-rules.md`](./business-rules.md).

---

## 2. Convenção de identificação

Os requisitos funcionais são identificados por códigos únicos.

- **RF-XXX** — requisito funcional principal.
- **RF-XXX.X** — requisito funcional subordinado.
- **RF-XXX.X.X** — requisito funcional de terceiro nível, quando necessário.

---

# 3. Gerenciamento de clientes

## RF-001 — Gerenciar clientes

O sistema deve permitir o cadastro e o gerenciamento dos clientes da oficina.

### RF-001.1 — Cadastrar cliente

O sistema deve permitir cadastrar um novo cliente.

### RF-001.1.1 — Informar dados obrigatórios

O sistema deve permitir registrar, no mínimo:

- nome;
- telefone;
- CPF.

### RF-001.1.2 — Validar cadastro

O sistema deve validar os dados obrigatórios antes da conclusão do cadastro.

### RF-001.1.3 — Impedir cadastro inválido

O sistema não deve concluir o cadastro quando os dados obrigatórios forem inválidos ou estiverem ausentes.

### RF-001.2 — Consultar clientes

O sistema deve permitir consultar clientes cadastrados.

### RF-001.2.1 — Pesquisar cliente

O sistema deve permitir localizar um cliente por seus dados cadastrais.

### RF-001.2.2 — Visualizar cliente

O sistema deve permitir visualizar os dados cadastrais do cliente.

### RF-001.2.3 — Visualizar veículos relacionados

O sistema deve permitir visualizar os veículos associados ao cliente.

### RF-001.2.4 — Visualizar histórico relacionado

O sistema deve permitir consultar os atendimentos relacionados ao cliente.

### RF-001.3 — Alterar cliente

O sistema deve permitir que o gerente altere os dados cadastrais de um cliente.

### RF-001.4 — Excluir cliente

O sistema deve permitir que o gerente exclua um cliente somente quando forem atendidas as condições de retenção definidas pelas regras de negócio.

### RF-001.4.1 — Validar prazo de retenção

O sistema deve verificar se transcorreram pelo menos cinco anos desde a última ordem de serviço relacionada ao cliente.

### RF-001.4.2 — Impedir exclusão antes do prazo

O sistema não deve permitir a exclusão de um cliente que possua ordem de serviço relacionada dentro do período de cinco anos.

---

# 4. Gerenciamento de veículos

## RF-002 — Gerenciar veículos

O sistema deve permitir o cadastro e o gerenciamento dos veículos atendidos pela oficina.

### RF-002.1 — Cadastrar veículo

O sistema deve permitir cadastrar um veículo.

### RF-002.1.1 — Informar dados obrigatórios

O sistema deve permitir registrar, no mínimo:

- marca;
- modelo;
- placa;
- proprietário.

### RF-002.1.2 — Registrar proprietário

O sistema deve permitir registrar o proprietário do veículo no momento do cadastro.

### RF-002.1.3 — Permitir proprietário diferente do cliente

O sistema deve permitir que o proprietário registrado do veículo seja diferente do cliente responsável pelo atendimento.

### RF-002.1.4 — Validar cadastro

O sistema deve validar os dados obrigatórios antes da conclusão do cadastro.

### RF-002.2 — Consultar veículos

O sistema deve permitir consultar veículos cadastrados.

### RF-002.2.1 — Pesquisar veículo

O sistema deve permitir localizar um veículo por seus dados de identificação.

### RF-002.2.2 — Visualizar veículo

O sistema deve permitir visualizar os dados cadastrais do veículo.

### RF-002.2.3 — Visualizar proprietário

O sistema deve permitir visualizar o proprietário atualmente registrado para o veículo.

### RF-002.2.4 — Visualizar histórico

O sistema deve permitir consultar as ordens de serviço relacionadas ao veículo.

### RF-002.3 — Alterar veículo

O sistema deve permitir que o gerente altere os dados cadastrais do veículo.

### RF-002.3.1 — Alterar proprietário

O sistema deve permitir que o gerente altere o proprietário atualmente registrado para o veículo.

### RF-002.3.2 — Preservar histórico do proprietário

A alteração do proprietário atual não deve modificar o proprietário registrado em ordens de serviço anteriores.

### RF-002.4 — Excluir veículo

O sistema deve permitir que o gerente exclua um veículo somente quando forem atendidas as condições de retenção definidas pelas regras de negócio.

### RF-002.4.1 — Validar prazo de retenção

O sistema deve verificar se transcorreram pelo menos cinco anos desde a última ordem de serviço relacionada ao veículo.

### RF-002.4.2 — Impedir exclusão antes do prazo

O sistema não deve permitir a exclusão de um veículo que possua ordem de serviço relacionada dentro do período de cinco anos.

---

# 5. Gerenciamento de profissionais

## RF-003 — Gerenciar profissionais

O sistema deve permitir o gerenciamento dos profissionais da oficina.

### RF-003.1 — Cadastrar profissional

O sistema deve permitir que o gerente cadastre um profissional.

### RF-003.1.1 — Definir função

O sistema deve permitir definir a função do profissional.

### RF-003.2 — Consultar profissionais

O sistema deve permitir consultar profissionais cadastrados.

### RF-003.2.1 — Pesquisar profissional

O sistema deve permitir localizar um profissional.

### RF-003.2.2 — Visualizar dados do profissional

O sistema deve permitir visualizar seus dados cadastrais e função.

### RF-003.3 — Alterar profissional

O sistema deve permitir que o gerente altere os dados de um profissional.

### RF-003.4 — Excluir profissional

O sistema deve permitir que o gerente exclua um profissional conforme as condições definidas pelas regras de negócio.

### RF-003.5 — Controlar permissões por função

O sistema deve restringir as operações disponíveis conforme a função do profissional.

---

# 6. Gerenciamento de serviços

## RF-004 — Gerenciar serviços

O sistema deve permitir o gerenciamento dos serviços oferecidos pela oficina.

### RF-004.1 — Cadastrar serviço

O sistema deve permitir cadastrar um serviço.

### RF-004.2 — Consultar serviços

O sistema deve permitir consultar os serviços cadastrados.

### RF-004.3 — Alterar serviço

O sistema deve permitir alterar um serviço.

### RF-004.4 — Inativar serviço

O sistema deve permitir inativar um serviço sem remover seu histórico de utilização.

---

# 7. Gerenciamento de peças

## RF-005 — Gerenciar peças

O sistema deve permitir o gerenciamento das peças utilizadas pela oficina.

### RF-005.1 — Cadastrar peça

O sistema deve permitir cadastrar uma peça.

### RF-005.2 — Consultar peças

O sistema deve permitir consultar as peças cadastradas.

### RF-005.3 — Alterar peça

O sistema deve permitir alterar os dados de uma peça.

### RF-005.4 — Inativar peça

O sistema deve permitir inativar uma peça sem remover seu histórico de utilização.

---

# 8. Gerenciamento de estoque

## RF-006 — Gerenciar estoque

O sistema deve permitir controlar a quantidade de peças disponíveis na oficina.

### RF-006.1 — Consultar estoque

O sistema deve permitir consultar a quantidade disponível de cada peça.

### RF-006.1.1 — Identificar peças sem estoque

O sistema deve permitir identificar peças com quantidade disponível igual a zero.

### RF-006.1.2 — Identificar estoque baixo

O sistema deve permitir identificar peças abaixo da quantidade mínima configurada.

### RF-006.2 — Registrar entrada de estoque

O sistema deve permitir que o gerente registre a entrada de peças.

### RF-006.2.1 — Informar quantidade recebida

O sistema deve permitir informar a quantidade adicionada.

### RF-006.2.2 — Atualizar quantidade disponível

O sistema deve atualizar a quantidade disponível após o registro da entrada.

### RF-006.3 — Reservar peças

O sistema deve permitir reservar peças para uma ordem de serviço.

### RF-006.3.1 — Informar quantidade reservada

O sistema deve permitir informar a quantidade que será reservada.

### RF-006.3.2 — Validar disponibilidade

O sistema deve verificar se existe quantidade disponível suficiente para realizar a reserva.

### RF-006.3.3 — Atualizar quantidade disponível para reserva

O sistema deve impedir que a quantidade reservada ultrapasse a quantidade disponível para novas reservas.

### RF-006.4 — Registrar utilização da peça

O sistema deve permitir registrar a utilização efetiva de uma peça reservada.

### RF-006.4.1 — Informar quantidade utilizada

O sistema deve permitir registrar a quantidade efetivamente utilizada.

### RF-006.4.2 — Diferenciar quantidade reservada e utilizada

O sistema deve manter separadas as quantidades reservadas e efetivamente utilizadas.

### RF-006.5 — Liberar peças não utilizadas

O sistema deve permitir liberar peças reservadas que não tenham sido utilizadas.

### RF-006.5.1 — Liberar após cancelamento

Ao cancelar uma ordem, o sistema deve liberar as peças reservadas que não tenham sido utilizadas.

### RF-006.5.2 — Atualizar disponibilidade

O sistema deve tornar novamente disponíveis para reserva as peças liberadas.

---

# 9. Gerenciamento de ordens de serviço

## RF-007 — Criar ordem de serviço

O sistema deve permitir criar uma nova ordem de serviço associada a um veículo e ao cliente responsável pelo atendimento.

### RF-007.1 — Identificar cliente

O sistema deve registrar o cliente responsável pelo atendimento.

### RF-007.2 — Identificar veículo

O sistema deve registrar o veículo relacionado ao atendimento.

### RF-007.3 — Registrar informações iniciais

O sistema deve permitir registrar as informações necessárias para caracterizar o atendimento no momento de sua criação.

### RF-007.4 — Identificar profissional responsável

O sistema deve registrar o profissional responsável pela ordem de serviço.

### RF-007.5 — Registrar serviços

O sistema deve permitir registrar os serviços correspondentes à ordem no momento apropriado do fluxo.

### RF-007.6 — Registrar peças

O sistema deve permitir registrar as peças associadas à ordem conforme as regras de reserva.

---

## RF-008 — Consultar ordens de serviço

O sistema deve permitir consultar as ordens de serviço armazenadas.

### RF-008.1 — Pesquisar ordem

O sistema deve permitir localizar uma ordem de serviço por seus dados de identificação.

### RF-008.2 — Filtrar por estado

O sistema deve permitir consultar ordens de acordo com seu estado atual.

### RF-008.3 — Filtrar por veículo

O sistema deve permitir consultar as ordens relacionadas a um veículo.

### RF-008.4 — Filtrar por profissional responsável

O sistema deve permitir consultar as ordens relacionadas a um profissional.

### RF-008.5 — Visualizar detalhes

O sistema deve permitir visualizar os dados registrados na ordem.

---

## RF-009 — Controlar imutabilidade da ordem de serviço

O sistema deve impedir a alteração dos dados da ordem de serviço após sua criação.

### RF-009.1 — Impedir alteração de dados fundamentais

O sistema não deve permitir alterar os dados registrados na criação da ordem.

### RF-009.2 — Permitir alteração de estado

O sistema deve permitir somente as alterações de estado previstas no ciclo de vida da ordem.

### RF-009.3 — Impedir alteração retroativa

O sistema não deve modificar os dados de uma ordem já existente em razão de alterações posteriores em clientes, veículos ou proprietários.

### RF-009.4 — Criar nova ordem para novas necessidades

Quando houver novo problema, serviço ou situação que não esteja contemplada na ordem existente, o sistema deve permitir a criação de uma nova ordem em vez de alterar a ordem original.

---

# 10. Diagnóstico

## RF-010 — Gerenciar diagnóstico

O sistema deve permitir registrar o processo de diagnóstico de um veículo quando necessário.

### RF-010.1 — Criar ordem de diagnóstico

O sistema deve permitir criar uma ordem destinada ao diagnóstico.

### RF-010.2 — Iniciar diagnóstico

O sistema deve permitir colocar a ordem em estado de diagnóstico.

### RF-010.3 — Registrar procedimentos

O sistema deve permitir registrar os procedimentos realizados durante o diagnóstico.

### RF-010.4 — Registrar observações

O sistema deve permitir registrar observações relacionadas ao diagnóstico.

### RF-010.5 — Registrar resultado

O sistema deve permitir registrar o resultado do diagnóstico.

### RF-010.6 — Registrar serviços recomendados

O sistema deve permitir registrar os serviços identificados como necessários após o diagnóstico.

### RF-010.7 — Registrar peças recomendadas

O sistema deve permitir registrar as peças necessárias para os serviços identificados.

### RF-010.8 — Criar ordem de execução

Após a conclusão do diagnóstico, o sistema deve permitir criar uma nova ordem de serviço contendo os serviços decorrentes do diagnóstico.

### RF-010.8.1 — Copiar somente dados necessários

A nova ordem deve conter somente as informações necessárias para representar o novo atendimento.

### RF-010.9 — Concluir ordem de diagnóstico

O sistema deve permitir concluir a ordem de diagnóstico após a criação da ordem de execução.

---

# 11. Autorização de serviços

## RF-011 — Gerenciar autorização

O sistema deve permitir registrar a decisão do cliente sobre os serviços propostos.

### RF-011.1 — Apresentar serviços para autorização

O sistema deve permitir visualizar os serviços propostos para autorização.

### RF-011.2 — Apresentar peças necessárias

O sistema deve permitir visualizar as peças relacionadas aos serviços propostos.

### RF-011.3 — Autorizar ordem inteira

O sistema deve permitir registrar a autorização de todos os serviços propostos.

### RF-011.4 — Autorizar parcialmente

O sistema deve permitir que o cliente autorize somente parte dos serviços propostos.

### RF-011.5 — Registrar serviços recusados

O sistema deve registrar quais serviços não foram autorizados.

### RF-011.6 — Criar nova ordem para autorização parcial

Quando houver autorização parcial, o sistema deve permitir criar uma nova ordem contendo somente os serviços autorizados.

### RF-011.7 — Impedir execução sem autorização

O sistema não deve permitir a execução de serviço que não tenha sido autorizado.

### RF-011.8 — Registrar recusa

O sistema deve registrar a recusa do cliente em relação aos serviços não autorizados.

---

# 12. Execução dos serviços

## RF-012 — Gerenciar execução

O sistema deve permitir acompanhar a execução dos serviços autorizados.

### RF-012.1 — Iniciar execução

O sistema deve permitir iniciar a execução de uma ordem autorizada.

### RF-012.2 — Registrar profissional responsável

O sistema deve identificar o profissional responsável pela execução.

### RF-012.3 — Registrar serviço executado

O sistema deve permitir registrar a realização dos serviços.

### RF-012.4 — Registrar peça utilizada

O sistema deve permitir registrar as peças efetivamente utilizadas.

### RF-012.5 — Registrar quantidade utilizada

O sistema deve permitir registrar a quantidade utilizada de cada peça.

### RF-012.6 — Registrar observações da execução

O sistema deve permitir registrar informações relacionadas à execução dos serviços.

### RF-012.7 — Concluir serviço

O sistema deve permitir marcar um serviço como concluído.

---

# 13. Novo problema durante a execução

## RF-013 — Gerenciar problemas adicionais

O sistema deve permitir registrar a identificação de um novo problema durante a execução de um serviço.

### RF-013.1 — Registrar novo problema

O sistema deve permitir registrar o novo problema identificado.

### RF-013.2 — Informar cliente

O sistema deve permitir registrar que o cliente foi informado sobre o novo problema.

### RF-013.3 — Registrar autorização

O sistema deve permitir registrar a autorização ou recusa do cliente.

### RF-013.4 — Criar nova ordem

Quando o cliente autorizar o novo serviço, o sistema deve permitir criar uma nova ordem de serviço.

### RF-013.5 — Impedir alteração da ordem original

O sistema não deve alterar a ordem de serviço original para incorporar o novo problema.

---

# 14. Responsabilidade entre mecânicos

## RF-014 — Gerenciar responsabilidade da ordem

O sistema deve controlar o profissional responsável por cada ordem de serviço.

### RF-014.1 — Consultar responsável

O sistema deve permitir identificar o mecânico responsável pela ordem.

### RF-014.2 — Restringir acesso do mecânico

O sistema deve impedir que um mecânico altere diretamente o estado de uma ordem pertencente a outro mecânico.

### RF-014.3 — Solicitar transferência

O sistema deve permitir iniciar o processo de transferência do atendimento para outro mecânico conforme as regras do negócio.

### RF-014.4 — Registrar autorização do cliente

O sistema deve permitir registrar a autorização do cliente para a transferência.

### RF-014.5 — Criar nova ordem para o novo mecânico

O sistema deve permitir criar uma nova ordem de serviço para o novo mecânico.

### RF-014.6 — Cancelar ordem anterior

Após a criação da nova ordem, o sistema deve permitir cancelar a ordem anterior conforme as regras de negócio.

---

# 15. Estados da ordem de serviço

## RF-015 — Controlar estado da ordem

O sistema deve controlar o estado atual de cada ordem de serviço.

### RF-015.1 — Definir estado inicial

Uma nova ordem deve iniciar em um estado apropriado para o início do processo.

### RF-015.7 — Impedir transições inválidas

O sistema não deve permitir transições incompatíveis com o ciclo de vida da ordem.

### RF-015.8 — Impedir reabertura

O sistema não deve permitir reabrir uma ordem concluída.

### RF-015.9 — Impedir retorno de estado

O sistema não deve permitir que uma ordem concluída ou cancelada retorne para um estado anterior.

---

# 16. Finalização da ordem de serviço

## RF-016 — Finalizar ordem de serviço

O sistema deve permitir finalizar uma ordem de serviço quando os serviços previstos tiverem sido concluídos.

### RF-016.1 — Validar serviços

O sistema deve verificar se os serviços previstos foram concluídos antes da finalização.

### RF-016.2 — Registrar conclusão

O sistema deve registrar a conclusão da ordem.

### RF-016.3 — Preservar dados

O sistema deve manter os dados da ordem sem permitir alterações posteriores.

---

# 17. Cancelamento da ordem de serviço

## RF-017 — Cancelar ordem de serviço

O sistema deve permitir cancelar uma ordem de serviço nas situações permitidas pelas regras de negócio.

### RF-017.1 — Registrar motivo

O sistema deve permitir registrar o motivo do cancelamento.

### RF-017.2 — Alterar estado

O sistema deve alterar o estado da ordem para `CANCELADA`.

### RF-017.3 — Liberar peças reservadas

O sistema deve devolver à disponibilidade do estoque as peças que tenham sido reservadas e não utilizadas.

### RF-017.4 — Preservar peças utilizadas

O sistema não deve devolver ao estoque peças que já tenham sido efetivamente utilizadas.

### RF-017.5 — Preservar ordem cancelada

O sistema deve manter a ordem cancelada armazenada.

---

# 18. Histórico

## RF-018 — Consultar histórico

O sistema deve permitir consultar o histórico de atendimentos relacionados a um veículo.

### RF-018.1 — Consultar ordens concluídas

O sistema deve permitir visualizar ordens de serviço concluídas.

### RF-018.2 — Consultar ordens canceladas

O sistema deve permitir visualizar ordens de serviço canceladas.

### RF-018.3 — Consultar diagnósticos

O sistema deve permitir visualizar diagnósticos realizados anteriormente.

### RF-018.4 — Consultar serviços

O sistema deve permitir visualizar serviços realizados em atendimentos anteriores.

### RF-018.5 — Consultar peças

O sistema deve permitir visualizar as peças utilizadas em atendimentos anteriores.

### RF-018.6 — Consultar profissionais

O sistema deve permitir identificar os profissionais relacionados aos atendimentos anteriores.

### RF-018.7 — Preservar proprietário histórico

O sistema deve manter o proprietário registrado no momento de cada ordem de serviço, independentemente de alterações posteriores no cadastro atual do veículo.

---

# 19. Controle de acesso

## RF-019 — Controlar acesso por função

O sistema deve limitar as funcionalidades disponíveis conforme a função do profissional.

### RF-019.1 — Mecânico

O sistema deve permitir que o mecânico:

- cadastre clientes e veículos;
- consulte clientes e veículos;
- crie suas próprias ordens;
- consulte suas próprias ordens;
- altere o estado de suas próprias ordens;
- execute serviços pelos quais seja responsável.

### RF-019.2 — Recepcionista

O sistema deve permitir que o recepcionista:

- cadastre clientes e veículos;
- consulte clientes e veículos.

### RF-019.3 — Gerente

O sistema deve permitir que o gerente:

- gerencie clientes;
- gerencie veículos;
- gerencie profissionais;
- gerencie estoque;
- gerencie as operações administrativas previstas para as ordens de serviço;
- execute as operações administrativas permitidas pelas regras de negócio.

---

# 20. Retenção e exclusão de dados

## RF-020 — Controlar retenção de clientes e veículos

O sistema deve controlar as condições necessárias para exclusão de clientes e veículos.

### RF-020.1 — Identificar última ordem do cliente

O sistema deve permitir determinar a data da última ordem de serviço relacionada ao cliente.

### RF-020.2 — Identificar última ordem do veículo

O sistema deve permitir determinar a data da última ordem de serviço relacionada ao veículo.

### RF-020.3 — Validar período de cinco anos

O sistema deve verificar se o período mínimo de cinco anos foi atingido.

### RF-020.4 — Impedir exclusão durante retenção

O sistema não deve permitir a exclusão enquanto existirem ordens relacionadas dentro do período de retenção.

### RF-020.5 — Permitir exclusão após retenção

O sistema deve permitir a exclusão quando as condições de retenção forem satisfeitas.

---

# 21. Escopo não contemplado nesta versão

Não fazem parte da versão 1.1:

- processamento de pagamentos;
- controle financeiro;
- emissão de cobranças;
- integração com WhatsApp;
- envio automático de mensagens;
- notificações externas;
- integrações com sistemas de terceiros;
- assinatura digital de ordens de serviço.

Esses recursos poderão ser especificados em versões futuras.

---

