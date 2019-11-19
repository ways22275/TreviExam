package com.weiwei.treviexam

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.weiwei.treviexam.MainActivity.Companion.BUNDLE_KEY_COLUMN
import com.weiwei.treviexam.MainActivity.Companion.BUNDLE_KEY_ROW
import kotlinx.android.synthetic.main.activity_column_row.*
import java.util.*
import kotlin.random.Random

class ColumnRowActivity : AppCompatActivity() {

    private var mColumnCount = 1
    private var mRowCount = 1

    private var mHandler : Handler? = null
    private var mTask : TimerTask? = null
    private var mTimer : Timer? = null

    private var mCurrentTextView : TextView? = null
    private var mCurrentTextViewBottom : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_column_row)

        mColumnCount = intent.getIntExtra(BUNDLE_KEY_COLUMN, 1)
        mRowCount = intent.getIntExtra(BUNDLE_KEY_ROW, 1)
        initView()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Timer", "start")
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Timer", "stop")
        mTimer?.cancel()
    }

    private fun initView() {
        gridLayout.columnCount = mColumnCount
        gridLayout.rowCount = mRowCount
        gridLayout.useDefaultMargins = true

        for (i in 1..mRowCount + 1) {
            for (j in 1..mColumnCount) {

                val layoutParams = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }

                lateinit var textView : TextView
                if (i == mRowCount + 1) {
                    textView = TextView(this).apply {
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.confirm)
                        setTextColor(Color.BLACK)
                        setBackgroundColor(Color.GRAY)
                    }
                } else {
                    textView = TextView(this).apply {
                        alpha = 0.2f
                        tag = j - 1
                        setBackgroundColor(Color.GREEN)
                    }
                }

                gridLayout.addView(textView, layoutParams)
            }
        }

        mTimer = Timer()
        mHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                resetCurrentTextView()
                val randomNext = Random.nextInt(0, mColumnCount * mRowCount)
                val textView = (gridLayout.getChildAt(randomNext) as TextView).apply {
                    gravity = Gravity.CENTER
                    setTextColor(Color.BLACK)
                    text = resources.getString(R.string.random)
                    alpha = 1f
                }
                mCurrentTextView = textView

                val textViewBottom = (gridLayout.getChildAt((textView.tag as Int) + mColumnCount * mRowCount) as TextView).apply {
                    setTextColor(Color.WHITE)
                    setOnClickListener{
                        resetCurrentTextView()
                    }
                }
                mCurrentTextViewBottom = textViewBottom
                super.handleMessage(msg)
            }
        }
    }

    private fun startTimer() {
        mTask = object : TimerTask() {
            override fun run() {
                mHandler?.sendMessage(Message())
            }
        }

        mTimer?.schedule(mTask, 1000, 1000)
    }

    private fun resetCurrentTextView() {
        mCurrentTextView?.apply {
            text = ""
            alpha = 0.2f
        }
        mCurrentTextViewBottom?.apply {
            setTextColor(Color.BLACK)
            setOnClickListener(null)
        }
    }
}
