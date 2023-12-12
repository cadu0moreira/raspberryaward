DROP TABLE IF EXISTS ProducerPrize;
CREATE TABLE ProducerPrize
(
    ID LONG PRIMARY KEY,
    PRODUCER VARCHAR(55),
    WINNING_YEAR INT,
    UNIQUE KEY year (WINNING_YEAR)
)
AS SELECT * FROM CSVREAD('src/main/resources/producer_prizes.csv');