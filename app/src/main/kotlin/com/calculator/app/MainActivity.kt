package com.calculator.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var a = ""
    private var b = ""
    private var op = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        fun num(v: String) {
            if (op.isEmpty()) {
                a += v
                display.text = a
            } else {
                b += v
                display.text = "$a $op $b"
            }
        }

        listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9"
        ).forEach { (id, v) ->
            findViewById<Button>(id).setOnClickListener { num(v) }
        }

        fun operator(o: String) {
            if (a.isNotEmpty()) {
                op = o
                display.text = "$a $op"
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { operator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { operator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { operator("×") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operator("÷") }

        findViewById<Button>(R.id.btnEq).setOnClickListener {
            if (a.isNotEmpty() && b.isNotEmpty()) {
                val x = a.toDouble()
                val y = b.toDouble()
                val r = when (op) {
                    "+" -> x + y
                    "-" -> x - y
                    "×" -> x * y
                    "÷" -> if (y != 0.0) x / y else 0.0
                    else -> 0.0
                }
                display.text = r.toString()
                a = r.toString()
                b = ""
                op = ""
            }
        }

        findViewById<Button>(R.id.btnAC).setOnClickListener {
            a = ""; b = ""; op = ""
            display.text = "0"
        }
    }
}
