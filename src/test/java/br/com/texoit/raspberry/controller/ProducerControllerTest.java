package br.com.texoit.raspberry.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith( SpringRunner.class )
public class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProducerPrizePeriods_noPeriodLimitsSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Joel Silver','interval':1,'previousWin':1990,'followingWin':1991}]," +
                "'max':[{'producer':'Matthew Vaughn','interval':13,'previousWin':2002,'followingWin':2015}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_bothPeriodLimitsSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Joel Silver','interval':1,'previousWin':1990,'followingWin':1991}]," +
                "'max':[{'producer':'Buzz Feitshans','interval':9,'previousWin':1985,'followingWin':1994}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=1980&endYear=2014")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_startLimitSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Matthew Vaughn','interval':13,'previousWin':2002,'followingWin':2015}]," +
                "'max':[{'producer':'Matthew Vaughn','interval':13,'previousWin':2002,'followingWin':2015}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=2000")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_endLimitSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Joel Silver','interval':1,'previousWin':1990,'followingWin':1991}]," +
                "'max':[{'producer':'Bo Derek','interval':6,'previousWin':1984,'followingWin':1990}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?endYear=1993")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_bothPeriodLimitsFail() throws Exception {
        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=1999&endYear=1991")).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
