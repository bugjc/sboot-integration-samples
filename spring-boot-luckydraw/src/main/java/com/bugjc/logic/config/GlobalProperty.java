package com.bugjc.logic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProperty {

    /**中奖概率**/
    @Value("${lucky.dram.probability}")
    public int luckyDramProbability;
}
