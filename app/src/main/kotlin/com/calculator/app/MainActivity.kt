package com.calculator.app

import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatActivity
import com.calculator.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var expr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val buttons = listOf(
            b.btn0, b.btn1, b.btn2, b.btn3, b.btn4,
            b.btn5, b.btn6, b.btn7, b.btn8, b.btn9,
            b.btnPlus, b.btnMinus, b.btnMul, b.btnDiv,
            b.btnDot, b.btnPercent
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                press(btn)
                append(btn.text.toString())
            }
        }

        b.btnClear.setOnClickListener {
            press(b.btnClear)
            expr = ""
            b.display.text = "0"
        }

        b.btnEqual.setOnClickListener {
            press(b.btnEqual)
            try {
                val r = eval(expr)
                b.display.text = r.toString()
                expr = r.toString()
            } catch (e: Exception) {
                b.display.text = "Error"
                expr = ""
            }
        }
    }

    private fun press(v: android.view.View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        v.animate().scaleX(0.96f).scaleY(0.96f).setDuration(80).withEndAction {
            v.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
        }.start()
    }

    private fun append(s: String) {
        expr += s
        b.display.text = expr
    }

    private fun eval(s: String): Double {
        return object {
            var i = 0
            fun parse(): Double {
                var x = term()
                while (i < s.length) {
                    when (s[i]) {
                        '+' -> { i++; x += term() }
                        '-' -> { i++; x -= term() }
                        else -> return x
                    }
                }
                return x
            }
            fun term(): Double {
                var x = factor()
                while (i < s.length) {
                    when (s[i]) {
                        '×' -> { i++; x *= factor() }
                        '÷' -> { i++; x /= factor() }
                        '%' -> { i++; x %= factor() }
                        else -> return x
                    }
                }
                return x
            }
            fun factor(): Double {
                val start = i
                while (i < s.length && (s[i].isDigit() || s[i] == '.')) i++
                return s.substring(start, i).toDouble()
            }
        }.parse()
    }
}
