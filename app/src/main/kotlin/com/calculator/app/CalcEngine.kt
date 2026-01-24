package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object CalcEngine {
    private val mc = MathContext(34, RoundingMode.HALF_UP)

    fun eval(expr: String): BigDecimal {
        return Parser(expr).parse()
    }

    private class Parser(val s: String) {
        var i = 0

        fun parse(): BigDecimal {
            var x = term()
            while (i < s.length) {
                when (s[i]) {
                    '+' -> { i++; x = x.add(term(), mc) }
                    '-' -> { i++; x = x.subtract(term(), mc) }
                    else -> return x
                }
            }
            return x
        }

        fun term(): BigDecimal {
            var x = factor()
            while (i < s.length) {
                when (s[i]) {
                    '×' -> { i++; x = x.multiply(factor(), mc) }
                    '÷' -> { i++; x = x.divide(factor(), mc) }
                    '%' -> { i++; x = x.remainder(factor(), mc) }
                    else -> return x
                }
            }
            return x
        }

        fun factor(): BigDecimal {
            val start = i
            while (i < s.length && (s[i].isDigit() || s[i] == '.')) i++
            return BigDecimal(s.substring(start, i), mc)
        }
    }
}
