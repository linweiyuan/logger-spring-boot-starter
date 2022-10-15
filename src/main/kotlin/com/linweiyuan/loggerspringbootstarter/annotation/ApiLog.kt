package com.linweiyuan.loggerspringbootstarter.annotation

import com.linweiyuan.loggerspringbootstarter.data.Constant

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
)
annotation class ApiLog(
    val borderColor: String = Constant.EMPTY,
    val borderBackgroundColor: String = Constant.EMPTY,
    val borderBlink: Boolean = false,

    val textColor: String = Constant.EMPTY,
    val textBackgroundColor: String = Constant.EMPTY,
    val textBold: Boolean = false,
    val textItalic: Boolean = false,
    val textBlink: Boolean = false,

    val emptySpaceBackgroundColor: String = Constant.EMPTY,

    val useDefaultColor: Boolean = false,
)
