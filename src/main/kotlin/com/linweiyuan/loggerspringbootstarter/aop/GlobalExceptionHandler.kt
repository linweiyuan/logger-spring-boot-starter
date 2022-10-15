@file:Suppress("unused", "GrazieInspection")

package com.linweiyuan.loggerspringbootstarter.aop

import com.linweiyuan.loggerspringbootstarter.annotation.EnableExceptionLog
import com.linweiyuan.loggerspringbootstarter.configuration.ExceptionLogConfigurationProperties
import com.linweiyuan.loggerspringbootstarter.model.LogBorder
import com.linweiyuan.loggerspringbootstarter.model.LogContent
import org.springframework.beans.factory.getBeansWithAnnotation
import org.springframework.context.ApplicationContext
import org.springframework.util.ClassUtils
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.logging.Logger
import javax.annotation.Resource

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = Logger.getLogger(this.javaClass.name)

    @Resource
    private lateinit var exceptionLogConfigurationProperties: ExceptionLogConfigurationProperties

    @Resource
    private lateinit var applicationContext: ApplicationContext

    @ExceptionHandler
    fun handleException(exception: Exception) {
        // get the Application bean with this annotation to get packageName for filtering non-framework stack trace
        val applicationBean = applicationContext.getBeansWithAnnotation<EnableExceptionLog>()
            .values
            .elementAt(0)
            .javaClass
        val enableExceptionLogAnnotation = ClassUtils.getUserClass(applicationBean)
            .getAnnotation(EnableExceptionLog::class.java)

        // if use `exception.stackTraceToString()`, the exception message will be gone after filtering
        val stackTraceList = exception.stackTrace
            .map { it.toString() }
            .filter { if (enableExceptionLogAnnotation.showAll) true else it.contains(applicationBean.packageName) }
            .toMutableList()
            .apply { add(0, exception.toString()) }

        val exceptionLog = getExceptionLog(stackTraceList, enableExceptionLogAnnotation)
        logger.severe(exceptionLog.toString())
    }

    private fun getExceptionLog(
        stackTraceList: MutableList<String>,
        enableExceptionLogAnnotation: EnableExceptionLog
    ): LogBorder {
        with(enableExceptionLogAnnotation) {
            var borderColor = borderColor
            var borderBackgroundColor = borderBackgroundColor
            var borderBlink = borderBlink

            var textColor = textColor
            var textBackgroundColor = textBackgroundColor
            var textBold = textBold
            var textItalic = textItalic
            var textBlink = textBlink

            var emptySpaceBackgroundColor = emptySpaceBackgroundColor

            // use values from application.properties or application.yml first
            if (allowOverride) {
                exceptionLogConfigurationProperties.let {
                    borderColor = it.borderColor
                    borderBackgroundColor = it.borderBackgroundColor
                    borderBlink = it.borderBlink

                    textColor = it.textColor
                    textBackgroundColor = it.textBackgroundColor
                    textBold = it.textBold
                    textItalic = it.textItalic
                    textBlink = it.textBlink

                    emptySpaceBackgroundColor = it.emptySpaceBackgroundColor
                }
            }

            val exceptionLog = LogBorder().apply {
                color = borderColor
                backgroundColor = borderBackgroundColor

                blink = borderBlink

                logContentList = stackTraceList.map {
                    LogContent().apply {
                        color = textColor
                        backgroundColor = textBackgroundColor
                        bold = textBold
                        italic = textItalic
                        blink = textBlink
                        text = it
                    }
                }.toList()

                this.emptySpaceBackgroundColor = emptySpaceBackgroundColor
            }
            return exceptionLog
        }
    }
}
