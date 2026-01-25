package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext

object BigDecimalCalculator {

    private val mc = MathContext.DECIMAL128

    fun eval(expr: String): BigDecimal {
        var i = 0
        var result = readNumber(expr, ::i)

        while (i < expr.length) {
            val op = expr[i]
            i++
            val next = readNumber(expr, ::i)

            result = when (op) {
                '+' -> result.add(next, mc)
                '-' -> result.subtract(next, mc)
                '*' -> result.multiply(next, mc)
                '/' -> result.divide(next, mc)
                else -> result
            }
        }
        return result
    }

    private fun readNumber(s: String, idx: () -> Int): BigDecimal {
        var i = idx()
        val start = i
        while (i < s.length && (s[i].isDigit() || s[i] == '.')) i++
        setIdx(idx, i)
        return BigDecimal(s.substring(start, i), mc)
    }

    private fun setIdx(get: () -> Int, value: Int) {
        val field = get.javaClass.getDeclaredField("value")
        field.isAccessible = true
        field.setInt(get, value)
    }
}
