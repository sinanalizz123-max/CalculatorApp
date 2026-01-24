package com.calculator.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.calculator.app.databinding.ActivityMainBinding
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var expression = ""

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

        buttons.forEach {
            it.setOnClickListener { add(it.text.toString()) }
        }

        b.btnClear.setOnClickListener {
            expression = ""
            b.display.text = "0"
        }

        b.btnEqual.setOnClickListener {
            try {
                val result = eval(expression)
                b.display.text = result.toString()
                expression = result.toString()
            } catch (e: Exception) {
                b.display.text = "Error"
                expression = ""
            }
        }
    }

    private fun add(value: String) {
        expression += value
        b.display.text = expression
    }

    // SIMPLE MATH PARSER (Android-safe)
    private fun eval(expr: String): Double {
        return object {
            var i = 0
            fun parse(): Double {
                var x = term()
                while (i < expr.length) {
                    when (expr[i]) {
                        '+' -> { i++; x += term() }
                        '-' -> { i++; x -= term() }
                        else -> return x
                    }
                }
                return x
            }
            fun term(): Double {
                var x = factor()
                while (i < expr.length) {
                    when (expr[i]) {
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
                while (i < expr.length && (expr[i].isDigit() || expr[i]=='.')) i++
                return expr.substring(start, i).toDouble()
            }
        }.parse()
    }
}
