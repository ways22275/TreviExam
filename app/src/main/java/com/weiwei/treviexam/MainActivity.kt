package com.weiwei.treviexam

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_KEY_COLUMN = "column"
        const val BUNDLE_KEY_ROW = "row"
    }

    private var mColumnCount = -1
    private var mRowCount = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        editText_column.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    submit.isEnabled = false
                    return
                }
                val inputCount = s.toString().toInt()
                mColumnCount = when {
                    inputCount < 0 -> 1
                    inputCount > 9 -> 9
                    else -> inputCount
                }
                submit.isEnabled = when (mRowCount) {
                    -1,0 -> false
                    else -> true
                }
            }
        })

        editText_row.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    submit.isEnabled = false
                    return
                }
                val inputCount = s.toString().toInt()
                mRowCount = when {
                    inputCount < 0 -> 1
                    inputCount > 9 -> 9
                    else -> inputCount
                }
                submit.isEnabled = when (mColumnCount) {
                    -1,0 -> false
                    else -> true
                }
            }
        })

        submit.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(BUNDLE_KEY_COLUMN, mColumnCount)
                putInt(BUNDLE_KEY_ROW, mRowCount)
            }

            Intent(this, ColumnRowActivity::class.java).apply {
                putExtras(bundle)
                startActivity(this)
            }
        }
    }
}
