# Corrida Solidária — API REST

API REST desenvolvida em Java com Spring Boot para gerenciamento de inscrições em uma corrida solidária.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Maven
- Spring Web
- Spring Validation
- JUnit 5

## Como executar o projeto

### Pré-requisitos

- Java 21 instalado.

### Executar a aplicação

No Windows, abra o terminal na pasta raiz do projeto e execute:

```bash
mvnw.cmd spring-boot:run
```

A API estará disponível em:

`http://localhost:8080`

## Endpoints disponíveis

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/inscricoes` | Cadastrar inscrição |
| GET | `/api/inscricoes` | Listar inscrições |
| GET | `/api/inscricoes/{email}` | Buscar inscrição por e-mail |
| PUT | `/api/inscricoes/{email}` | Atualizar inscrição |
| DELETE | `/api/inscricoes/{email}` | Cancelar inscrição |
| GET | `/api/inscricoes/resumo` | Consultar resumo das inscrições |

## Exemplo de cadastro

**POST** `/api/inscricoes`

Corpo da requisição:

```json
{
  "nome": "Ana Souza",
  "email": "ana.souza@email.com",
  "dataNascimento": "1990-05-20",
  "percurso": "KM_10"
}
```

Exemplo de resposta:

```json
{
  "nome": "Ana Souza",
  "email": "ana.souza@email.com",
  "dataNascimento": "1990-05-20",
  "percurso": "KM_10",
  "idadeNaProva": 36,
  "categoria": "ADULTO"
}
```

## Regras de negócio

- A idade mínima para participar é de 14 anos.
- O percurso de 10 km exige idade mínima de 18 anos.
- A idade é calculada considerando a data da prova: 14/03/2027.
- As categorias são:
    - JUVENIL: 14 a 17 anos.
    - ADULTO: 18 a 39 anos.
    - MASTER: 40 a 59 anos.
    - SENIOR: 60 anos ou mais.
- O e-mail deve ser único entre as inscrições ativas.
- O e-mail não pode ser alterado durante uma atualização.
- O cancelamento libera o e-mail para um novo cadastro.
- A data de nascimento deve ser enviada no formato `yyyy-MM-dd`.

## Resumo das inscrições

**GET** `/api/inscricoes/resumo`

Exemplo de resposta:

```json
{
  "total": 3,
  "km5": 2,
  "km10": 1
}
```

## Tratamento de erros

A API utiliza os seguintes códigos HTTP:

| Código | Significado |
|---|---|
| 200 | Operação realizada com sucesso |
| 201 | Inscrição criada |
| 204 | Inscrição cancelada |
| 400 | Dados inválidos |
| 404 | Inscrição não encontrada |
| 409 | E-mail já cadastrado |
| 500 | Erro interno do servidor |

As respostas de erro seguem o formato:

```json
{
  "mensagem": "Descrição do erro."
}
```

## Testes automatizados

Para executar os testes automatizados:

```bash
mvnw.cmd test
```

Os testes verificam as regras de idade, classificação por categoria, restrições de percurso e integridade dos dados durante atualizações inválidas.

## Armazenamento

Os dados são armazenados em memória utilizando uma lista (`ArrayList`). Portanto, as inscrições são perdidas quando a aplicação é encerrada ou reiniciada.