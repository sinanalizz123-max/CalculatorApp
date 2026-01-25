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

        buttons.forEach {
            it.setOnClickListener {
                haptic(it)
                append((it as android.widget.Button).text.toString())
            }
        }

        b.btnClear.setOnClickListener {
            haptic(it)
            expr = ""
            b.display.text = "0"
        }

        b.btnEqual.setOnClickListener {
            haptic(it)
            try {
                val result = eval(expr)
                b.display.text = result
                expr = result
            } catch (e: Exception) {
                b.display.text = "Error"
                expr = ""
            }
        }
    }

    private fun haptic(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    private fun append(v: String) {
        expr += v
        b.display.text = expr
    }

    // ✔ REAL BigDecimal evaluation (no doubles, no scientific loss)
    private fun eval(input: String): String {
        val tokens = input
            .replace("×", "*")
            .replace("÷", "/")
            .split(Regex("(?=[+\\-*/])|(?<=[+\\-*/])"))

        var result = BigDecimal(tokens[0])
        var i = 1

        while (i < tokens.size) {
            val op = tokens[i]
            val num = BigDecimal(tokens[i + 1])
            result = when (op) {
                "+" -> result.add(num)
                "-" -> result.subtract(num)
                "*" -> result.multiply(num)
                "/" -> result.divide(num, MathContext.DECIMAL128)
                else -> result
            }
            i += 2
        }

        return result.stripTrailingZeros().toPlainString()
    }
}
