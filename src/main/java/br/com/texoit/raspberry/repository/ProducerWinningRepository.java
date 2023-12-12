package br.com.texoit.raspberry.repository;

import br.com.texoit.raspberry.model.ProducerWinning;
import br.com.texoit.raspberry.model.ProducerWinningDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerWinningRepository extends JpaRepository<ProducerWinning, Long> {

    @Query(nativeQuery = true)
    List<ProducerWinningDto> getAllPrizeIntervals();

    @Query(nativeQuery = true)
    List<ProducerWinningDto> getPrizeIntervalsByStartYearAndEndYear(final Integer startYear, final Integer endYear);

    @Query(nativeQuery = true)
    List<ProducerWinningDto> getPrizeIntervalsByStartYear(final Integer startYear);

    @Query(nativeQuery = true)
    List<ProducerWinningDto> getPrizeIntervalsByEndYear(final Integer endYear);
}
