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

    private val mc = MathContext.DECIMAL128
    private var accumulator = BigDecimal.ZERO
    private var currentInput = ""
    private var pendingOp: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val numButtons = listOf(
            b.btn0, b.btn1, b.btn2, b.btn3, b.btn4,
            b.btn5, b.btn6, b.btn7, b.btn8, b.btn9, b.btnDot
        )

        numButtons.forEach { btn ->
            btn.setOnClickListener {
                press(it)
                currentInput += btn.text.toString()
                b.display.text = currentInput
            }
        }

        fun operator(op: Char) {
            press(b.root)
            if (currentInput.isNotEmpty()) {
                val value = BigDecimal(currentInput)
                accumulator = if (pendingOp == null) value else apply(accumulator, value)
                currentInput = ""
            }
            pendingOp = op
            b.display.text = accumulator.toEngineeringString()
        }

        b.btnPlus.setOnClickListener { operator('+') }
        b.btnMinus.setOnClickListener { operator('-') }
        b.btnMul.setOnClickListener { operator('×') }
        b.btnDiv.setOnClickListener { operator('÷') }

        b.btnEqual.setOnClickListener {
            press(it)
            if (currentInput.isNotEmpty() && pendingOp != null) {
                val value = BigDecimal(currentInput)
                accumulator = apply(accumulator, value)
            }
            currentInput = ""
            pendingOp = null
            b.display.text = accumulator.toEngineeringString()
        }

        b.btnClear.setOnClickListener {
            press(it)
            accumulator = BigDecimal.ZERO
            currentInput = ""
            pendingOp = null
            b.display.text = "0"
        }
    }

    private fun apply(a: BigDecimal, b: BigDecimal): BigDecimal {
        return when (pendingOp) {
            '+' -> a.add(b, mc)
            '-' -> a.subtract(b, mc)
            '×' -> a.multiply(b, mc)
            '÷' -> a.divide(b, mc)
            else -> a
        }
    }

    private fun press(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        v.animate().scaleX(0.96f).scaleY(0.96f).setDuration(80).withEndAction {
            v.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
        }.start()
    }
}
