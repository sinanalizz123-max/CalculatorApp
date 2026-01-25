package com.calculator.app

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.calculator.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var expr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val nums = listOf(
            b.btn0,b.btn1,b.btn2,b.btn3,b.btn4,
            b.btn5,b.btn6,b.btn7,b.btn8,b.btn9,b.btnDot
        )

        val ops = mapOf(
            b.btnPlus to "+",
            b.btnMinus to "-",
            b.btnMul to "×",
            b.btnDiv to "÷"
        )

        nums.forEach { btn ->
            btn.setOnClickListener {
                haptic(it)
                expr += btn.text.toString()
                b.display.text = expr
            }
        }

        ops.forEach { (btn, op) ->
            btn.setOnClickListener {
                if (expr.isEmpty() || expr.last().isOperator()) {
                    invalid()
                } else {
                    haptic(it)
                    expr += op
                    b.display.text = expr
                }
            }
        }

        b.btnPercent.setOnClickListener {
            if (expr.isEmpty() || expr.last().isOperator()) {
                invalid()
            } else {
                haptic(it)
                expr += "%"
                b.display.text = expr
            }
        }

        b.btnBack.setOnClickListener {
            haptic(it)
            if (expr.isNotEmpty()) {
                expr = expr.dropLast(1)
                b.display.text = if (expr.isEmpty()) "0" else expr
            }
        }

        b.btnClear.setOnClickListener {
            haptic(it)
            expr = ""
            b.display.text = "0"
        }

        b.btnEqual.setOnClickListener {
            try {
                haptic(it)
                val r = eval(expr)
                b.display.text = r
                expr = r
            } catch (_: Exception) {
                invalid()
            }
        }
    }

    private fun haptic(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    private fun invalid() {
        b.root.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        b.root.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    private fun Char.isOperator() = this in "+-×÷%"

    private fun eval(input: String): String {
        val normalized = input
            .replace("×", "*")
            .replace("÷", "/")
            .replace("%", "/100")

        return BigDecimalCalculator.eval(normalized)
            .stripTrailingZeros()
            .toPlainString()
    }
}
