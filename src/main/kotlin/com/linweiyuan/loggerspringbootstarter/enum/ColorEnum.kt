package com.linweiyuan.loggerspringbootstarter.enum

enum class ColorEnum(val value: String) {
    RESET("\u001b[0m"),

    BOLD("\u001b[1m"),
    ITALIC("\u001b[3m"),
    BLINK("\u001b[5m"),

    FOREGROUND("\u001b[38;2;%d;%d;%dm"),
    BACKGROUND("\u001b[48;2;%d;%d;%dm"),
}
