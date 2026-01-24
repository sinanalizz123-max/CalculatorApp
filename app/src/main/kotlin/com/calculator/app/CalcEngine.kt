package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext

object CalcEngine {
    private val mc = MathContext.UNLIMITED

    fun eval(expr: String): BigDecimal {
        val tokens = Regex("([+\\-×÷%])").split(expr)
        val ops = Regex("[^+\\-×÷%]").replace(expr, "").toCharArray()

        var result = BigDecimal(tokens[0], mc)
        for (i in ops.indices) {
            val next = BigDecimal(tokens[i + 1], mc)
            result = when (ops[i]) {
                '+' -> result.add(next, mc)
                '-' -> result.subtract(next, mc)
                '×' -> result.multiply(next, mc)
                '÷' -> result.divide(next, mc)
                '%' -> result.remainder(next, mc)
                else -> result
            }
        }
        return result
    }
}
