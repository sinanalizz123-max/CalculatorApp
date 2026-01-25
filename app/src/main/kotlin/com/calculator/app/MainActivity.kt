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

        buttons.forEach { btn ->
            btn.setOnClickListener {
                haptic(it)
                expr += btn.text.toString()
                b.display.text = expr
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

    // SAFE BigDecimal math (GitHub-proven approach)
    private fun eval(s: String): String {
        val clean = s.replace("×","*").replace("÷","/")
        val parts = Regex("([+\\-*/])").split(clean)
        val ops = Regex("[^0-9.]").findAll(clean).map { it.value }.toList()

        var result = BigDecimal(parts[0], MathContext.DECIMAL128)
        for (i in ops.indices) {
            val n = BigDecimal(parts[i+1], MathContext.DECIMAL128)
            result = when (ops[i]) {
                "+" -> result.add(n)
                "-" -> result.subtract(n)
                "*" -> result.multiply(n)
                "/" -> result.divide(n, MathContext.DECIMAL128)
                else -> result
            }
        }
        return result.stripTrailingZeros().toPlainString()
    }
}
