package com.linweiyuan.loggerspringbootstarter.extension

import com.linweiyuan.loggerspringbootstarter.data.Constant
import com.linweiyuan.loggerspringbootstarter.enum.ColorEnum
import org.springframework.util.StringUtils
import java.awt.Color

fun String.toColor(backgroundColor: Boolean = false): String {
    if (!StringUtils.hasText(this)) {
        return Constant.COLOR_EMPTY
    }

    with(Color.decode(this)) {
        return String.format(
            if (backgroundColor) ColorEnum.BACKGROUND.value else ColorEnum.FOREGROUND.value,
            red, green, blue
        )
    }
}
