package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        input.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        input.text = "";
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDot) {
            input.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = input.text.toString()
            var prefix = ""

            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                when {
                    value.contains("-") -> {
                        val splitValue = value.split("-")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input.text = removeZeroAfterDecimal((one.toDouble() - two.toDouble()).toString())
                    }
                    value.contains("+") -> {
                        val splitValue = value.split("+")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input.text = removeZeroAfterDecimal((one.toDouble() + two.toDouble()).toString())
                    }
                    value.contains("*") -> {
                        val splitValue = value.split("*")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input.text = removeZeroAfterDecimal((one.toDouble() * two.toDouble()).toString())
                    }
                    value.contains("/") -> {
                        val splitValue = value.split("/")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input.text = removeZeroAfterDecimal((one.toDouble() / two.toDouble()).toString())
                    }
                }
            }
            catch (e: ArithmeticException) {
                e.printStackTrace();
            }
        }
    }

    private fun removeZeroAfterDecimal(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length-2)
        }

        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(input.text.toString())) {
            input.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        }
        else {
            (value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-"))
        }
    }
}