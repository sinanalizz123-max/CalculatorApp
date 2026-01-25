package com.calculator.app

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.calculator.app.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var expr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val nums = listOf(b.btn0,b.btn1,b.btn2,b.btn3,b.btn4,b.btn5,b.btn6,b.btn7,b.btn8,b.btn9,b.btnDot)
        val ops = listOf(b.btnPlus,b.btnMinus,b.btnMul,b.btnDiv,b.btnPercent)

        nums.forEach {
            it.setOnClickListener { press(it); expr += it.text; b.display.text = expr }
        }

        ops.forEach {
            it.setOnClickListener {
                if (expr.isEmpty() || "+-×÷%".contains(expr.last())) {
                    errorHaptic(it); return@setOnClickListener
                }
                press(it); expr += it.text; b.display.text = expr
            }
        }

        b.btnClear.setOnClickListener {
            press(it); expr=""; b.display.text="0"
        }

        b.btnEqual.setOnClickListener {
            press(it)
            try {
                val r = BigDecimal(expr.replace("×","*").replace("÷","/"))
                b.display.text = r.stripTrailingZeros().toPlainString()
                expr = b.display.text.toString()
            } catch (e: Exception) {
                errorHaptic(it); b.display.text="Error"; expr=""
            }
        }
    }

    private fun press(v: View) =
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

    private fun errorHaptic(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        v.postDelayed({ v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP) }, 60)
    }
}
