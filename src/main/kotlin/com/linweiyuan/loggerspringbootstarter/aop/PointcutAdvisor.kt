@file:Suppress("SpellCheckingInspection")

package com.linweiyuan.loggerspringbootstarter.aop

import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor

class PointcutAdvisor : AbstractBeanFactoryPointcutAdvisor() {
    override fun getPointcut() = AspectJExpressionPointcut().apply {
        expression =
            "@within(com.linweiyuan.loggerspringbootstarter.annotation.ApiLog) or @annotation(com.linweiyuan.loggerspringbootstarter.annotation.ApiLog)"
    }
}
