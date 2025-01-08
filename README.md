# 🌟 MaxDocServer

MaxDocServer é uma API desenvolvida em Java 17 para gerenciar documentos de forma eficiente. Utilizando o poder do Docker para o banco de dados, a configuração e execução são simples e rápidas, garantindo uma experiência de uso otimizada.

---

## 📋 Requisitos
Para rodar o MaxDocServer, certifique-se de que seu ambiente está configurado corretamente com os seguintes requisitos:

- **Java 17**: Garanta que o Java 17 esteja instalado. Verifique a versão instalada com:
  ```bash
  java -version
  ```
  Caso precise instalar o Java, acesse: [Oracle Java Downloads](https://www.oracle.com/java/technologies/javase-downloads.html) ou [OpenJDK](https://openjdk.org/).

- **Maven**: Certifique-se de que o Maven esteja instalado para construir o projeto. Verifique a versão com:
  ```bash
  mvn -version
  ```
  Instale o Maven, se necessário, em: [Maven Downloads](https://maven.apache.org/download.cgi).

- **Docker**: O Docker é necessário apenas para o banco de dados. Verifique a versão instalada com:
  ```bash
  docker --version
  ```
  Caso precise instalar, acesse: [Docker Downloads](https://www.docker.com/products/docker-desktop).

- **Docker Compose**: Certifique-se de que o Docker Compose está instalado. Verifique com:
  ```bash
  docker-compose --version
  ```

---

## 🚀 Configuração e Execução

Siga as etapas abaixo para configurar e executar o MaxDocServer:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/edurib17/max-doc-server.git
   cd maxdocserver
   ```

2. **Inicie o banco de dados com Docker Compose**:
   Certifique-se de que o arquivo `docker-compose.yml` está configurado corretamente. Em seguida, execute:
   ```bash
   docker-compose up -d
   ```

3. **Opções para executar o projeto**:

    - **Usando Maven**:
      Utilize o Maven para compilar e executar o projeto:
      ```bash
      mvn clean spring-boot:run
      ```

    - **Usando IntelliJ IDEA**:
        1. Importe o projeto na IntelliJ IDEA.
        2. Certifique-se de que as dependências foram resolvidas (Maven sync).
        3. Localize a classe principal `Main` ou `Application`.
        4. Clique com o botão direito e selecione "Run" para iniciar a aplicação.

4. **Acesse a API**:
   Por padrão, a API estará acessível em:
   ```
   http://localhost:8080/
   ```

5. **Documentação da API**:
   A documentação interativa da API está disponível via Swagger. Acesse:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

💻 **MaxDocServer - Simplifique a gestão de documentos!**

