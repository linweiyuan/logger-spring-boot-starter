package com.linweiyuan.loggerspringbootstarter.model

import com.linweiyuan.loggerspringbootstarter.enum.ColorEnum

class LogContent : Log() {
    var bold: Boolean = false
    var italic: Boolean = false

    lateinit var text: String

    override fun getStyle() = StringBuilder(super.getStyle()).apply {
        if (bold) {
            append(ColorEnum.BOLD.value)
        }
        if (italic) {
            append(ColorEnum.ITALIC.value)
        }
    }

    override fun toString() = ColorEnum.RESET.value + getStyle() + text + ColorEnum.RESET.value
}
