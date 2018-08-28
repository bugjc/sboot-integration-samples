package com.bugjc.cj.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GlobalProperty {

    /**中奖概率**/
    @Value("${lucky.draw.probability}")
    private int luckyDrawProbability;
}
