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
                "'min':[{'producer':'Bob','interval':1,'previousWin':1995,'followingWin':1996}," +
                        "{'producer':'John','interval':1,'previousWin':2000,'followingWin':2001}]," +
                "'max':[{'producer':'John','interval':10,'previousWin':1991,'followingWin':2001}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_bothPeriodLimitsSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Bob','interval':1,'previousWin':1995,'followingWin':1996}]," +
                "'max':[{'producer':'Bob','interval':1,'previousWin':1995,'followingWin':1996}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=1994&endYear=2000")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_startLimitSuccess() throws Exception {
        final String expectedPrizes = "{" +
                "'min':[{'producer':'Bob','interval':1,'previousWin':1995,'followingWin':1996}," +
                        "{'producer':'John','interval':1,'previousWin':2000,'followingWin':2001}]," +
                "'max':[{'producer':'Josh','interval':4,'previousWin':1993,'followingWin':1997}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=1993")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_endLimitSuccess() throws Exception {
        final String expectedPrizes = "{'min':[{'producer':'Bob','interval':1,'previousWin':1995,'followingWin':1996}]," +
                "'max':[{'producer':'Bob','interval':6,'previousWin':1990,'followingWin':1996}]}";

        this.mockMvc.perform(get("/raspberry/producer/prizes?endYear=1996")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPrizes));
    }

    @Test
    public void getProducerPrizePeriods_bothPeriodLimitsFail() throws Exception {
        this.mockMvc.perform(get("/raspberry/producer/prizes?startYear=1999&endYear=1991")).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
