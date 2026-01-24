package com.calculator.app

import java.math.BigDecimal
import java.math.MathContext

object CalcEngine {
    private val mc = MathContext.DECIMAL128
    var value = BigDecimal.ZERO

    fun set(v: BigDecimal) {
        value = v
    }

    fun add(v: BigDecimal) { value = value.add(v, mc) }
    fun sub(v: BigDecimal) { value = value.subtract(v, mc) }
    fun mul(v: BigDecimal) { value = value.multiply(v, mc) }
    fun div(v: BigDecimal) { value = value.divide(v, mc) }

    fun display(): String {
        return value.stripTrailingZeros().toEngineeringString()
    }
}
