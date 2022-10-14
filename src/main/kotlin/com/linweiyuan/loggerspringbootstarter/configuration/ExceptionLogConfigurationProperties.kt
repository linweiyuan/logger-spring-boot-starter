package com.linweiyuan.loggerspringbootstarter.configuration

import com.linweiyuan.loggerspringbootstarter.data.Constant
import com.linweiyuan.loggerspringbootstarter.exception.ColorFormatException
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.regex.Pattern

@Configuration
@ConfigurationProperties(prefix = "linweiyuan.logger.exception")
class ExceptionLogConfigurationProperties {
    var borderColor = Constant.COLOR_EMPTY
        set(value) {
            checkColorFormat(value)
            field = value
        }
    var borderBackgroundColor = Constant.COLOR_EMPTY
        set(value) {
            checkColorFormat(value)
            field = value
        }
    var borderBlink = false

    var textColor = Constant.COLOR_EMPTY
        set(value) {
            checkColorFormat(value)
            field = value
        }
    var textBackgroundColor = Constant.COLOR_EMPTY
        set(value) {
            checkColorFormat(value)
            field = value
        }
    var textBold = false
    var textItalic = false
    var textBlink = false

    var emptySpaceBackgroundColor = Constant.COLOR_EMPTY
        set(value) {
            checkColorFormat(value)
            field = value
        }

    fun checkColorFormat(colorFormat: String) {
        if (!Pattern.matches(Constant.COLOR_REG_EXP, colorFormat)) {
            throw ColorFormatException("Color is not correct. e.g. #abcdef")
        }
    }
}
