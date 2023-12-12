# Golden Raspberry Award
Este projeto possibilita a pesquisa dos produtores de filmes que venceram o prêmio de pior filme Golden Raspberry Awards, em uma base de dados simulada. O objetivo é exercitar conceitos de Spring, REST API, JPA, H2 e testes de integração.

## Requisitos
- Java 11
- maven 3.8.2

## Instalação
Após clonar o repositório compile o código através do comando abaixo, a partir do branch "master":

`$ mvn clean install`

Para execução dos testes de integração execute:

`$ mvn verify`

Para inicialização do servidor execute:

`$ mvn spring-boot:run`

## Banco de Dados da Premiação
O arquivo CSV contendo a base de dados da premiação encontra-se em: `src/main/resources/producer_prizes.csv`. É possível alterá-lo à vontade para atualizar a base com novos, e mais recentes, filmes premiados, todavia recomenda-se manter a estrutura da base de dados atual (vide `src/main/resources/data.sql`) para evitar erros de carregamento de banco durante o inicialização do servidor. Uma vez que esse projeto faz uso de um banco de dados em memória, as informações do banco retornam ao estado inicial (presente no arquivo CSV) a cada inicialização do servidor.

## Utilização
A aplicação suporta somente um endpoint: `GET raspberry/producer/prizes`, que suporta dois query parâmetros: `startYear` e `endYear`, que podem ser utilizados em qualquer combinação, e mesmo totalmente omitidos. Eles devem receber o ano inicial e final da premiação dos filmes, se omitidos a base inteira é considerada na pesquisa. A única restrição no uso dos parâmetros é o ano inicial ser inferior ao ano final, do contrário um HTTP Status 400 (Bad Request) é retornado.
Veja os exemplos  abaixo:
```
$ curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes' | python -m json.tool

{
    "max": [
        {
            "followingWin": 2001,
            "interval": 10,
            "previousWin": 1991,
            "producer": "John"
        }
    ],
    "min": [
        {
            "followingWin": 1996,
            "interval": 1,
            "previousWin": 1995,
            "producer": "Bob"
        },
        {
            "followingWin": 2001,
            "interval": 1,
            "previousWin": 2000,
            "producer": "John"
        }
    ]
}
```
```
$ curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?startYear=1999' | python -m json.tool

{
    "max": [
        {
            "followingWin": 2001,
            "interval": 1,
            "previousWin": 2000,
            "producer": "John"
        }
    ],
    "min": [
        {
            "followingWin": 2001,
            "interval": 1,
            "previousWin": 2000,
            "producer": "John"
        }
    ]
}

```
```
curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?endYear=1995' | python -m json.tool

{
    "max": [
        {
            "followingWin": 1995,
            "interval": 5,
            "previousWin": 1990,
            "producer": "Bob"
        }
    ],
    "min": [
        {
            "followingWin": 1994,
            "interval": 2,
            "previousWin": 1992,
            "producer": "Lisa"
        }
    ]
}
```
```
curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?startYear=1992&endYear=1998' | python -m json.tool

{
    "max": [
        {
            "followingWin": 1997,
            "interval": 4,
            "previousWin": 1993,
            "producer": "Josh"
        }
    ],
    "min": [
        {
            "followingWin": 1996,
            "interval": 1,
            "previousWin": 1995,
            "producer": "Bob"
        }
    ]
}
```
