#  Movie Catalog System

Sistema de **catálogo de filmes** desenvolvido em Java utilizando **JPA/Hibernate** para persistência de dados.
O projeto permite gerenciar **filmes, atores e avaliações**, simulando uma pequena plataforma de avaliação de filmes.

##  Funcionalidades

O sistema permite:

###  Gerenciamento de Filmes

* Cadastrar novos filmes
* Atualizar informações de filmes
* Listar filmes cadastrados
* Remover filmes
* Associar atores ao elenco

###  Gerenciamento de Atores

* Cadastrar atores
* Atualizar dados dos atores
* Listar atores cadastrados
* Remover atores
* Visualizar atores que ainda não participam de determinado filme

###  Sistema de Avaliações

* Avaliar filmes
* Registrar comentário e nota
* Calcular média de avaliações
* Listar filmes ainda não avaliados por um usuário

---

##  Arquitetura do Projeto

O projeto foi estruturado em camadas para separar responsabilidades e melhorar a organização do código.

```
src
 ├── controllers
 ├── excessoes
 ├── infra
 ├── models
 └── view
```

###  Models

Representam as **entidades do banco de dados**.

* `FilmeModel`
* `AutorModel`
* `AvaliacaoModel`

Relacionamentos utilizados:

* **ManyToMany** → Filmes e atores
* **OneToMany** → Filmes e avaliações
* **ManyToOne** → Avaliação e filme

---

### ⚙️ Infra

Responsável pelo **acesso ao banco de dados**.

Contém um **DAO genérico** que implementa operações CRUD:

* Inserir
* Ler
* Atualizar
* Remover

Também possui DAOs específicos:

* `DAOatores`
* `DAOfilmes`

---

###  Controllers

Contém regras de validação e controle da lógica do sistema.

Exemplos:

* Validação de intervalos numéricos
* Verificação de existência de IDs
* Tratamento de exceções personalizadas

---

###  View

Interface baseada em **menu no console**, permitindo interação com o sistema.

Principais módulos:

* `AtoresView`
* `FilmesView`
* `AvaliacoesView`

---

## 🗄️ Tecnologias Utilizadas

* Java
* JPA
* Hibernate
* Maven
* Banco de dados relacional

---

##  Modelo de Relacionamento

Filme possui:

* vários atores
* várias avaliações

Atores podem participar de:

* vários filmes

Cada avaliação pertence a:

* um filme

---

##  Como executar o projeto

1. Clonar o repositório

```
git clone https://github.com/seu-usuario/movie-catalog.git
```

2. Abrir o projeto em uma IDE Java (IntelliJ ou Eclipse)

3. Configurar o banco de dados no arquivo de persistência.

4. Executar a aplicação.

---

##  Objetivo do Projeto

Este projeto foi desenvolvido com o objetivo de praticar:

* Programação orientada a objetos
* Persistência de dados com JPA/Hibernate
* Modelagem de banco de dados
* Estruturação de projetos Java em camadas
* Implementação de CRUD completo

---

## 👨‍💻 Autor

Gabriel Raniere
