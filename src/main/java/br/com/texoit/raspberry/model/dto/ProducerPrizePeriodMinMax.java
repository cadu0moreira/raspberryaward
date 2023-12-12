package br.com.texoit.raspberry.model.dto;

import br.com.texoit.raspberry.model.ProducerWinningDto;

import java.util.List;

public class ProducerPrizePeriodMinMax {
    private final List<ProducerWinningDto> min;
    private final List<ProducerWinningDto> max;

    public ProducerPrizePeriodMinMax(List<ProducerWinningDto> min, List<ProducerWinningDto> max) {
        this.min = min;
        this.max = max;
    }

    public List<ProducerWinningDto> getMin() {
        return min;
    }

    public List<ProducerWinningDto> getMax() {
        return max;
    }
}
