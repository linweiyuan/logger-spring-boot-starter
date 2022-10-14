package com.linweiyuan.loggerspringbootstarter.model

import com.linweiyuan.loggerspringbootstarter.data.Constant
import com.linweiyuan.loggerspringbootstarter.enum.BorderEnum
import com.linweiyuan.loggerspringbootstarter.enum.ColorEnum
import com.linweiyuan.loggerspringbootstarter.extension.toColor

class LogBorder : Log() {
    var emptySpaceBackgroundColor = Constant.COLOR_EMPTY
    lateinit var logContentList: List<LogContent>

    private fun getHorizontalLine(maxLength: Int) = BorderEnum.HORIZONTAL.value.repeat(maxLength + 2)

    private fun StringBuilder.generateLineStart() = append(Constant.NEW_LINE).append(getStyle())

    private fun StringBuilder.generateTopBorder(horizontalLine: String) {
        generateLineStart()
            .append(BorderEnum.TOP_LEFT.value).append(horizontalLine)
            .append(BorderEnum.TOP_RIGHT.value).append(ColorEnum.RESET.value)
    }

    private fun getEmptySpace(maxLength: Int, textLength: Int) =
        Constant.CHARACTER_SPACE.repeat(maxLength - textLength)

    private fun StringBuilder.generateBottomBorder(horizontalLine: String) {
        generateLineStart()
            .append(BorderEnum.BOTTOM_LEFT.value).append(horizontalLine)
            .append(BorderEnum.BOTTOM_RIGHT.value).append(ColorEnum.RESET.value)
    }

    override fun toString() = StringBuilder().apply {
        val maxLength = logContentList.maxOf { it.text.length }
        val horizontalLine = getHorizontalLine(maxLength)

        generateTopBorder(horizontalLine)

        logContentList.forEach {
            generateLineStart()
                .append(BorderEnum.VERTICAL.value).append(Constant.CHARACTER_SPACE).append(it.toString())
                .append(emptySpaceBackgroundColor.toColor(true))
                .append(getEmptySpace(maxLength, it.text.length)).append(ColorEnum.RESET.value)
                .append(getStyle()).append(Constant.CHARACTER_SPACE).append(BorderEnum.VERTICAL.value)
                .append(ColorEnum.RESET.value)
        }

        generateBottomBorder(horizontalLine)
    }.toString()
}
