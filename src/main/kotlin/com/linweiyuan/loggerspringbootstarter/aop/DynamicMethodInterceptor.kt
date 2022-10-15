package com.linweiyuan.loggerspringbootstarter.aop

import com.fasterxml.jackson.databind.ObjectMapper
import com.linweiyuan.loggerspringbootstarter.annotation.ApiLog
import com.linweiyuan.loggerspringbootstarter.data.Constant
import com.linweiyuan.loggerspringbootstarter.model.LogBorder
import com.linweiyuan.loggerspringbootstarter.model.LogContent
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.logging.Logger
import kotlin.math.max

class DynamicMethodInterceptor(private val objectMapper: ObjectMapper) : MethodInterceptor {
    private val logger = Logger.getLogger(this.javaClass.name)

    override fun invoke(invocation: MethodInvocation): Any? {
        val method = invocation.method

        // use annotation config in method level first
        var apiLogAnnotation = AnnotationUtils.findAnnotation(invocation.method, ApiLog::class.java)
        if (apiLogAnnotation == null) {
            // class level after
            apiLogAnnotation = invocation.method.declaringClass.getAnnotation(ApiLog::class.java)
            if (apiLogAnnotation == null) {
                // no annotation
                return invocation.proceed()
            }
        }

        val servletRequestAttributes = ((RequestContextHolder.getRequestAttributes()) as ServletRequestAttributes)
        val request = servletRequestAttributes.request

        val apiLogList = mutableListOf<String>()

        // request
        val requestLogList = mutableListOf<String>().apply {
            with(mutableListOf<LinkedHashMap<String, Any>>()) {
                method.parameterTypes.forEachIndexed { index, clazz ->
                    /**
                     * e.g. String id = "1";
                     *
                     * String -> argType
                     * id     -> argName
                     * "1"    -> argValue
                     */
                    add(LinkedHashMap<String, Any>().apply {
                        put("argType", clazz.name)
                        put("argName", method.parameters[index].name)
                        put("argValue", invocation.arguments[index])
                    })
                }
                addAll(objectMapper.writeValueAsString(this).lines())
            }

            // e.g. GET /api/users
            add(0, "${request.method}${Constant.CHARACTER_SPACE}${request.requestURI}")
        }
        apiLogList.addAll(requestLogList)

        val response = invocation.proceed()

        // response
        val responseLogList = mutableListOf<String>()

        // handle ResponseEntity response
        val foregroundColor = if (response is ResponseEntity<*>) {
            responseLogList.add("${response.statusCode}")
            responseLogList.addAll(objectMapper.writeValueAsString(response.body).lines().map { it.trimEnd() })

            apiLogList.add(
                Constant.DASH.repeat(max(requestLogList.maxOf { it.length }, responseLogList.maxOf { it.length }))
            )
            apiLogList.addAll(responseLogList)

            // default 2xx, 4xx, 5xx text color mapping
            when (response.statusCodeValue.toString()[0]) {
                '2' -> Constant.COLOR_GREEN
                '4' -> Constant.COLOR_YELLOW
                '5' -> Constant.COLOR_RED

                else -> Constant.COLOR_WHITE
            }
        } else {
            Constant.EMPTY
        }

        val apiLog = getApiLog(apiLogList, apiLogAnnotation, foregroundColor)
        logger.info(apiLog.toString())

        return response
    }

    private fun getApiLog(
        logList: MutableList<String>,
        apiLogAnnotation: ApiLog,
        foregroundColor: String
    ): LogBorder {
        with(apiLogAnnotation) {
            val requestLog = LogBorder().apply {
                color = if (!useDefaultColor) borderColor else foregroundColor
                backgroundColor = if (!useDefaultColor) borderBackgroundColor else Constant.EMPTY

                blink = borderBlink

                logContentList = logList.map {
                    LogContent().apply {
                        color = if (!useDefaultColor) textColor else foregroundColor
                        backgroundColor = if (!useDefaultColor) textBackgroundColor else Constant.EMPTY
                        bold = if (!useDefaultColor) textBold else true
                        italic = textItalic
                        blink = textBlink
                        text = it
                    }
                }

                emptySpaceBackgroundColor = apiLogAnnotation.emptySpaceBackgroundColor
            }
            return requestLog
        }
    }
}
