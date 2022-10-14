@file:Suppress("SpringFacetCodeInspection")

package com.linweiyuan.loggerspringbootstarter.configuration

import com.linweiyuan.loggerspringbootstarter.aop.GlobalExceptionHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExceptionLogConfiguration {
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler::class)
    fun globalExceptionHandler() = GlobalExceptionHandler()
}
