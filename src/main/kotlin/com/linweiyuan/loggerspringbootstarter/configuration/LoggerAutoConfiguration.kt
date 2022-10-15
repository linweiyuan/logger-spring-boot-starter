package com.linweiyuan.loggerspringbootstarter.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.linweiyuan.loggerspringbootstarter.aop.DynamicMethodInterceptor
import com.linweiyuan.loggerspringbootstarter.aop.PointcutAdvisor
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(ExceptionLogConfigurationProperties::class)
@Configuration
class LoggerAutoConfiguration {
    @Bean
    fun pointcutAdvisor() = PointcutAdvisor().apply {
        advice = DynamicMethodInterceptor(objectMapper())
    }

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
    }
}
