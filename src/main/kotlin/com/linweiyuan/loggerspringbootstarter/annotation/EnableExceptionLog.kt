@file:Suppress("unused")

package com.linweiyuan.loggerspringbootstarter.annotation

import com.linweiyuan.loggerspringbootstarter.configuration.ExceptionLogConfiguration
import com.linweiyuan.loggerspringbootstarter.data.Constant
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(value = [ExceptionLogConfiguration::class])
annotation class EnableExceptionLog(
    val showAll: Boolean = false,

    val borderColor: String = Constant.COLOR_RED,
    val borderBackgroundColor: String = Constant.EMPTY,
    val borderBlink: Boolean = false,

    val textColor: String = Constant.COLOR_RED,
    val textBackgroundColor: String = Constant.EMPTY,
    val textBold: Boolean = true,
    val textItalic: Boolean = false,
    val textBlink: Boolean = false,

    val emptySpaceBackgroundColor: String = Constant.EMPTY,

    val allowOverride: Boolean = false,
)
