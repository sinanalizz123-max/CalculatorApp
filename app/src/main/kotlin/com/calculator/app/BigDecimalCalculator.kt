package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext

object BigDecimalCalculator {

    private val mc = MathContext.DECIMAL128

    fun eval(expr: String): BigDecimal {
        require(expr.isNotEmpty()) { "Empty expression" }
        require(expr.last().isDigit()) { "Expression ends with operator" }

        var result = BigDecimal.ZERO
        var current = BigDecimal.ZERO
        var op = '+'

        var i = 0
        while (i < expr.length) {
            val c = expr[i]

            if (c.isDigit() || c == '.') {
                val start = i
                while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) i++
                current = BigDecimal(expr.substring(start, i), mc)
                continue
            }

            when (op) {
                '+' -> result = result.add(current, mc)
                '-' -> result = result.subtract(current, mc)
                '*' -> result = result.multiply(current, mc)
                '/' -> result = result.divide(current, mc)
            }

            op = c
            i++
        }

        when (op) {
            '+' -> result = result.add(current, mc)
            '-' -> result = result.subtract(current, mc)
            '*' -> result = result.multiply(current, mc)
            '/' -> result = result.divide(current, mc)
        }

        return result
    }
}
