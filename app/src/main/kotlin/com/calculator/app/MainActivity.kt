package com.calculator.app

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Button
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

        val buttons: List<Button> = listOf(
            b.btn0,b.btn1,b.btn2,b.btn3,b.btn4,
            b.btn5,b.btn6,b.btn7,b.btn8,b.btn9,
            b.btnPlus,b.btnMinus,b.btnMul,b.btnDiv,
            b.btnDot,b.btnPercent
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                haptic(it)
                append(btn.text.toString())
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
                val r = eval(expr)
                b.display.text = r
                expr = r
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

    private fun eval(s: String): String {
        return BigDecimal(
            s.replace("×","*").replace("÷","/")
        ).stripTrailingZeros().toPlainString()
    }
}
