package com.calculator.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.script.ScriptEngineManager

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var expr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val map = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to ".",
            R.id.btnAdd to "+", R.id.btnSub to "-",
            R.id.btnMul to "*", R.id.btnDiv to "/"
        )

        map.forEach { (id, v) ->
            findViewById<Button>(id).setOnClickListener {
                expr += v
                display.text = expr
            }
        }

        findViewById<Button>(R.id.btnAC).setOnClickListener {
            expr = ""
            display.text = "0"
        }

        findViewById<Button>(R.id.btnEq).setOnClickListener {
            try {
                val engine = ScriptEngineManager().getEngineByName("rhino")
                val result = engine.eval(expr).toString()
                expr = result
                display.text = result
            } catch (e: Exception) {
                display.text = "Error"
                expr = ""
            }
        }
    }
}
