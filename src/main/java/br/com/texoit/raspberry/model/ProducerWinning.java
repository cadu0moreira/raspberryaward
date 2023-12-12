package br.com.texoit.raspberry.model;

import javax.persistence.*;

@NamedNativeQuery(name = "ProducerWinning.getAllPrizeIntervals",
        query = ProducerWinning.COMMON_PRIZE_INTERVAL + ProducerWinning.ORDER_PRIZE_INTERVAL,
        resultSetMapping = "Mapping.ProducerWinningDto")
@NamedNativeQuery(name = "ProducerWinning.getPrizeIntervalsByStartYear",
        query = ProducerWinning.COMMON_PRIZE_INTERVAL
                + ProducerWinning.START_YEAR_CRITERIA
                + ProducerWinning.ORDER_PRIZE_INTERVAL,
        resultSetMapping = "Mapping.ProducerWinningDto")
@NamedNativeQuery(name = "ProducerWinning.getPrizeIntervalsByEndYear",
        query = ProducerWinning.COMMON_PRIZE_INTERVAL
                + ProducerWinning.END_YEAR_CRITERIA
                + ProducerWinning.ORDER_PRIZE_INTERVAL,
        resultSetMapping = "Mapping.ProducerWinningDto")
@NamedNativeQuery(name = "ProducerWinning.getPrizeIntervalsByStartYearAndEndYear",
        query = ProducerWinning.COMMON_PRIZE_INTERVAL
                + ProducerWinning.START_YEAR_CRITERIA
                + ProducerWinning.END_YEAR_CRITERIA
                + ProducerWinning.ORDER_PRIZE_INTERVAL,
        resultSetMapping = "Mapping.ProducerWinningDto")
@SqlResultSetMapping(name = "Mapping.ProducerWinningDto",
        classes = @ConstructorResult(targetClass = ProducerWinningDto.class,
                columns = {@ColumnResult(name = "NAME"),
                        @ColumnResult(name = "PERIOD_BETWEEN_PRIZES"),
                        @ColumnResult(name = "PREVIOUS_WIN"),
                        @ColumnResult(name = "FOLLOWING_WIN")}))
@Entity
@Table(name = "producerprize")
public class ProducerWinning {
    final static String COMMON_PRIZE_INTERVAL = "SELECT p1.PRODUCER as NAME," +
            "   (p2.WINNING_YEAR - p1.WINNING_YEAR) as PERIOD_BETWEEN_PRIZES," +
            "   p1.WINNING_YEAR as PREVIOUS_WIN," +
            "   p2.WINNING_YEAR as FOLLOWING_WIN " +
            "FROM PRODUCERPRIZE p1 " +
            "JOIN PRODUCERPRIZE p2 " +
            "ON p1.PRODUCER = p2.PRODUCER " +
            "WHERE p1.ID != p2.ID" +
            "   AND p1.WINNING_YEAR < p2.WINNING_YEAR";
    final static String ORDER_PRIZE_INTERVAL = " ORDER BY PERIOD_BETWEEN_PRIZES ASC";
    final static String START_YEAR_CRITERIA =  " AND p1.WINNING_YEAR >= :startYear";
    final static String END_YEAR_CRITERIA =  " AND p2.WINNING_YEAR <= :endYear";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String producer;

    @Column(name="WINNING_YEAR")
    private Integer winningYear;

    public ProducerWinning() { }

    public ProducerWinning(long id, String producer, Integer winningYear) {
        this.id = id;
        this.producer = producer;
        this.winningYear = winningYear;
    }

    public long getId() {
        return id;
    }

    public String getProducer() {
        return producer;
    }

    public Integer getWinningYear() {
        return winningYear;
    }
}
