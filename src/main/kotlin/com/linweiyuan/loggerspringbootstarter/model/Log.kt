package com.linweiyuan.loggerspringbootstarter.model

import com.linweiyuan.loggerspringbootstarter.data.Constant
import com.linweiyuan.loggerspringbootstarter.enum.ColorEnum
import com.linweiyuan.loggerspringbootstarter.extension.toColor

open class Log {
    var color = Constant.COLOR_EMPTY
    var backgroundColor = Constant.COLOR_EMPTY

    var blink: Boolean = false

    open fun getStyle() = StringBuilder().apply {
        append(color.toColor()).append(backgroundColor.toColor(true))
        if (blink) {
            append(ColorEnum.BLINK.value)
        }
    }
}
