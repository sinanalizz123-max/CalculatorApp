package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext

object BigDecimalCalculator {

    private val mc = MathContext.DECIMAL128

    fun eval(expr: String): BigDecimal {
        val tokens = Regex("(?<=\\D)|(?=\\D)")
            .findAll(expr)
            .map { it.value }
            .toList()

        var result = BigDecimal(tokens[0], mc)
        var i = 1

        while (i < tokens.size) {
            val op = tokens[i]
            val num = BigDecimal(tokens[i + 1], mc)
            result = when (op) {
                "+" -> result.add(num, mc)
                "-" -> result.subtract(num, mc)
                "*" -> result.multiply(num, mc)
                "/" -> result.divide(num, mc)
                else -> result
            }
            i += 2
        }
        return result
    }
}
