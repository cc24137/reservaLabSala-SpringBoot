# API de Reserva de Laboratórios e Salas de Aula

API RESTful desenvolvida em Java com Spring Boot para gerenciamento e agendamento de salas de aula e laboratórios.

## 🛠️ Tecnologias
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![SQL Server](https://img.shields.io/badge/Microsoft%20SQL%20Server-CC292B?style=for-the-badge&logo=microsoftsqlserver&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Insomnia](https://img.shields.io/badge/Insomnia-5849BE?style=for-the-badge&logo=insomnia&logoColor=white)
  
## 📌 Funcionalidades

* **Usuários:** Cadastro completo (CPF, nome, data de aniversário, celular e e-mail), login e consultas por e-mail e aniversário.
* **Recursos (Salas e Laboratórios):** Cadastro de recursos com código, nome, capacidade e localização. Consultas com filtros por nome, capacidade e localização.
* **Status do Recurso:** Cadastro e controle do estado atual dos recursos.
* **Reservas:** Agendamento informando datas, horários, usuário e recurso. Consulta avançada com combinação de filtros (recurso, período, horário, usuário e status).

## 🔄 Regras de Status

 Status | Descrição |
| :--- | :--- |
| **Livre** | O recurso está disponível para agendamento. |
| **Reservado** | Agendamento confirmado, aguardando início de uso. |
| **Ocupado** | O recurso está em uso na data e horário atual. |
| **Bloqueado** | Recurso em manutenção, indisponível para reservas. |

## ⚙️ Como Executar

1. **Clonar o Repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/reserva-salas-api.git](https://github.com/seu-usuario/reserva-salas-api.git)
   ```
2. **Configurar o Banco de Dados:**
   Ajuste as credenciais do SQL Server no arquivo `src/main/resources/application.properties`.
3. **Executar a Aplicação:**
   ```bash
   mvn spring-boot:run
   ```
4. **Testar os Endpoints:**
   Acesse a interface do Swagger no navegador ou importe as requisições no Insomnia.

## 👥 Autores

* **Eduardo Artigiani Lima Tribst** - [GitHub](https://github.com/EduardoTribst)
* **Júlio Pacheco Stein** - [GitHub](https://github.com/cc24137)
