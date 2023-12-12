package br.com.texoit.raspberry.service;

import br.com.texoit.raspberry.model.ProducerWinningDto;
import br.com.texoit.raspberry.model.dto.ProducerPrizePeriodMinMax;
import br.com.texoit.raspberry.repository.ProducerWinningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

@Service
public class ProducerService {
    private final ProducerWinningRepository prodWinRepo;

    public ProducerService(final ProducerWinningRepository prodWinRepo) {
        this.prodWinRepo = prodWinRepo;
    }

    /**
     * Return list of prizes for minimum and maximum intervals.
     * The search period can be defined by any combination of <i>startYear</i> and <i>endYear</i> params.
     * If none is defined, then the entire database is considered.
     *
     * @param startYear
     * @param endYear
     * @return
     */
    public ProducerPrizePeriodMinMax getProducerPrizePeriods(final Integer startYear, final Integer endYear) {
        final List<ProducerWinningDto> allIntervals;
        if (startYear == null && endYear == null) {
            allIntervals = prodWinRepo.getAllPrizeIntervals();
        } else if (startYear != null && endYear != null) {
            allIntervals = prodWinRepo.getPrizeIntervalsByStartYearAndEndYear(startYear, endYear);
        } else if (startYear != null) {
            allIntervals = prodWinRepo.getPrizeIntervalsByStartYear(startYear);
        } else {
            allIntervals = prodWinRepo.getPrizeIntervalsByEndYear(endYear);
        }

        if (allIntervals != null && allIntervals.size() > 0) {
            final Integer minInterval = allIntervals.get(0).getInterval();
            final Integer maxInterval = allIntervals.get(allIntervals.size() - 1).getInterval();

            return allIntervals.stream()
                    .filter(interval -> interval.getInterval() == minInterval || interval.getInterval() == maxInterval)
                    .sorted(Comparator.comparing(ProducerWinningDto::getProducer))
                    .collect(Collector.of(() -> new ProducerPrizePeriodMinMax(new ArrayList(), new ArrayList()),
                            (acc, interval) -> {
                                if (interval.getInterval() == minInterval) {
                                    acc.getMin().add(interval);
                                }
                                if (interval.getInterval() == maxInterval) {
                                    acc.getMax().add(interval);
                                }
                            },
                            (minMax1, minMax2) -> null)); // combiner has no effect here
        } else {
            return new ProducerPrizePeriodMinMax(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        }
    }
}
