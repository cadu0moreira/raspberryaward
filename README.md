# Golden Raspberry Award
Este projeto possibilita a pesquisa dos produtores de filmes que venceram o prêmio de pior filme Golden Raspberry Awards, em uma base de dados simulada. O objetivo é exercitar conceitos de Spring, REST API, JPA, H2 e testes de integração.

## Requisitos
- Java 11
- maven 3.8.2

## Instalação
Após clonar o repositório compile o código através do comando abaixo, a partir do branch "main":

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
            "followingWin": 2015,
            "interval": 13,
            "previousWin": 2002,
            "producer": "Matthew Vaughn"
        }
    ],
    "min": [
        {
            "followingWin": 1991,
            "interval": 1,
            "previousWin": 1990,
            "producer": "Joel Silver"
        }
    ]
}
```
```
$ curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?startYear=2000' | python -m json.tool

{
    "max": [
        {
            "followingWin": 2015,
            "interval": 13,
            "previousWin": 2002,
            "producer": "Matthew Vaughn"
        }
    ],
    "min": [
        {
            "followingWin": 2015,
            "interval": 13,
            "previousWin": 2002,
            "producer": "Matthew Vaughn"
        }
    ]
}
```
```
curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?endYear=1993' | python -m json.tool

{
    "max": [
        {
            "followingWin": 1990,
            "interval": 6,
            "previousWin": 1984,
            "producer": "Bo Derek"
        }
    ],
    "min": [
        {
            "followingWin": 1991,
            "interval": 1,
            "previousWin": 1990,
            "producer": "Joel Silver"
        }
    ]
}
```
```
curl --location --request GET 'http://localhost:8080/raspberry/producer/prizes?startYear=1980&endYear=2014' | python -m json.tool

{
    "max": [
        {
            "followingWin": 1994,
            "interval": 9,
            "previousWin": 1985,
            "producer": "Buzz Feitshans"
        }
    ],
    "min": [
        {
            "followingWin": 1991,
            "interval": 1,
            "previousWin": 1990,
            "producer": "Joel Silver"
        }
    ]
}
```
