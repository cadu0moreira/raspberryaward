package br.com.texoit.raspberry.controller;

import br.com.texoit.raspberry.model.dto.ProducerPrizePeriodMinMax;
import br.com.texoit.raspberry.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/raspberry/producer")
public class ProducerController {
    private final Logger logger = LoggerFactory.getLogger(ProducerController.class);
    private final ProducerService prodService;

    public ProducerController(final ProducerService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/prizes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProducerPrizePeriodMinMax> getProducerPrizePeriods(@RequestParam @Nullable final Integer startYear,
                                                             @RequestParam @Nullable final Integer endYear) {
        if (startYear != null && endYear != null && startYear >= endYear) {
            logger.error("endYear must be strictly greater than startYear");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(prodService.getProducerPrizePeriods(startYear, endYear));
    }
}
