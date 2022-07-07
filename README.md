# Luizalabs Challenge

Esse projeto é um desafio para conseguir uma vaga no Luizalabs.

## Executando a aplicação

1. Clone o repo: `git clone https://github.com/patrickpiresdev/luizalabs-challenge.git`
2. Configure a aplicação para se conectar a um servidor do mongodb
   1. Para isso é preciso colocar a uri de conexão e o nome do banco desejados para persistir os dados no arquivo `application.properties`
    ```properties
    mongodb.uri=<mongodb-uri-connection>
    mongodb.database=<database>
    ```
3. Agora basta subir a aplicação
   1. Vá para o diretório raiz do projeto
   2. Execute `mvn install`
   3. Execute `java -jar target/luizalabs-challenge-0.0.1-SNAPSHOT.jar`

## Requisições

Para manipular a wishlist, existem 4 requisições possíveis:
1. Adicionar um produto na Wishlist do cliente: `POST wishlist/{productId}`
2. Remover um produto da Wishlist do cliente: `DELETE wishlist/{productId}`
3. Consultar todos os produtos da Wishlist do cliente: `GET /wishlist`
4. Consultar se um determinado produto está na Wishlist do
  cliente: `GET wishlist/{productId}`

> Obs. 1: A URL base das requisições é http://localhost:8080
 
> Obs. 2: No lugar de `{productId}` devemos colocar o valor associado ao campo `_id` do produto no mongodb

## Dados

Para conseguirmos manipular a wishlist, necessitamos de produtos (é claro) para que os mesmo possam ser adicionados, consultados e removidos da wishlist.

Dito isso, é necessário popular o banco com alguns produtos. Para isso:
1. Crie a coleção `product`
2. Popule a mesma com dados de produtos no formato `{ name: <product-name> description: <product-description> }`

Alternativamente, deixarei aqui abaixo um código pronto para apenas ser executado para popolar o mongo
1. `use <database>` -> Aqui em `<database>` coloque o mesmo database doo `application.properties`
2. ```javascript
   db.getCollection('product').insertMany([
        { name: "product 1", description: "product 1 description" },
        { name: "product 2", description: "product 2 description" },
        { name: "product 3", description: "product 3 description" },
        { name: "product 4", description: "product 4 description" }
   ])
   ```