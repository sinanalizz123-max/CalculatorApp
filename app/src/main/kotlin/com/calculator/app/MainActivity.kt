package com.calculator.app

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.calculator.app.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.MathContext

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var expr = ""
    private val mc = MathContext.DECIMAL128

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val buttons = listOf(
            b.btn0,b.btn1,b.btn2,b.btn3,b.btn4,
            b.btn5,b.btn6,b.btn7,b.btn8,b.btn9,
            b.btnPlus,b.btnMinus,b.btnMul,b.btnDiv,
            b.btnDot,b.btnPercent
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                pressEffect(btn)
                append(btn.text.toString())
            }
        }

        b.btnClear.setOnClickListener {
            pressEffect(it)
            expr = ""
            b.display.text = "0"
        }

        b.btnBack.setOnClickListener {
            pressEffect(it)
            if (expr.isNotEmpty()) {
                expr = expr.dropLast(1)
                b.display.text = if (expr.isEmpty()) "0" else expr
            }
        }

        b.btnEqual.setOnClickListener {
            pressEffect(it)
            try {
                val result = eval(expr)
                // allow scientific notation only if very large
                b.display.text =
                    if (result.precision() > 16)
                        result.toEngineeringString()
                    else
                        result.stripTrailingZeros().toPlainString()

                expr = b.display.text.toString()
            } catch (e: Exception) {
                b.display.text = "Error"
                expr = ""
            }
        }
    }

    private fun pressEffect(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        v.animate().scaleX(0.96f).scaleY(0.96f).setDuration(80).withEndAction {
            v.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
        }.start()
    }

    private fun append(v: String) {
        expr += v
        b.display.text = expr
    }

    private fun eval(s: String): BigDecimal {
        return object {
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
                return s.substring(start, i).toBigDecimal()
            }
        }.parse()
    }
}
