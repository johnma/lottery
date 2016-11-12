package com.iware.lottery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by johnma on 2016/11/2.
 */@Configuration
@EnableJpaRepositories(basePackages = {"com.iware.lottery"})
public class DataJpaConfig {
}