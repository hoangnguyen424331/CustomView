package com.example.customview.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.example.customview.R
import java.util.*

@Suppress("DEPRECATION")
class ClockView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet),
    Handler.Callback {

    private var clockWidth = 0
    private var clockHeight = 0
    private lateinit var paintCircle: Paint
    private lateinit var paintHour: Paint
    private lateinit var paintMinute: Paint
    private lateinit var paintSecond: Paint
    private lateinit var paintText: Paint
    private var calendar: Calendar
    private val clockHandler: Handler = Handler(Looper.myLooper()!!, this)

    init {
        calendar = Calendar.getInstance()
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clockWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        clockHeight = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(clockWidth, clockHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val circleRadius = 400f
        canvas.drawCircle(clockWidth / 2f, clockHeight / 2f, circleRadius, paintCircle)
        canvas.drawCircle(clockWidth / 2f, clockHeight / 2f, 20f, paintCircle)
        for (i in 1..12) {
            canvas.save()
            canvas.rotate(30f * i, clockWidth / 2f, clockHeight / 2f)
            canvas.drawText(
                "$i",
                clockWidth / 2f,
                clockHeight / 2f - circleRadius + 70f,
                paintText
            )
            canvas.drawLine(
                clockWidth / 2f,
                clockHeight / 2f - circleRadius,
                clockWidth / 2f,
                clockHeight / 2f - circleRadius + 30f,
                paintCircle
            )
            canvas.restore()
        }

        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR)
        val sec = calendar.get(Calendar.SECOND)

        //draw minute
        val minuteDegree = minute / 60f * 360f
        canvas.save()
        canvas.rotate(minuteDegree, clockWidth / 2f, clockHeight / 2f)
        canvas.drawLine(
            clockWidth / 2f,
            clockHeight / 2f - 250f,
            clockWidth / 2f,
            clockHeight / 2f ,
            paintMinute
        )
        canvas.restore()

        //draw hour
        val hourDegree = (hour * 60f + minute) / 12f / 60f * 360f
        canvas.rotate(hourDegree, clockWidth / 2f, clockHeight / 2f)
        canvas.save()
        canvas.drawLine(
            clockWidth / 2f,
            clockHeight / 2f - 200f,
            clockWidth / 2f,
            clockHeight / 2f ,
            paintHour
        )
        canvas.restore()

        //draw second
        val secDegree = sec / 60f * 360f
        canvas.save()
        canvas.rotate(secDegree, clockWidth / 2f, clockHeight / 2f)
        canvas.drawLine(
            clockWidth / 2f,
            clockHeight / 2f - 300f,
            clockWidth / 2f,
            clockHeight / 2f ,
            paintSecond
        )
        canvas.restore()
    }

    override fun handleMessage(p0: Message): Boolean {
        when (p0.what) {
            NEED_INVALIDATE -> {
                calendar = Calendar.getInstance()
                invalidate()
                clockHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 1000)
            }
        }
        return true
    }

    private fun initPaint() {

        paintCircle = Paint().apply {
            color = Color.BLACK
            setBackgroundColor(resources.getColor(R.color.brow))
            strokeWidth = 10f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }

        paintHour = Paint().apply {
            color = Color.YELLOW
            strokeWidth = 20f
        }

        paintMinute = Paint().apply {
            color = Color.YELLOW
            strokeWidth = 15f
        }

        paintSecond = Paint().apply {
            color = Color.YELLOW
            strokeWidth = 10f
        }

        paintText = Paint().apply {
            color = Color.BLACK
            strokeWidth = 10f
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        clockHandler.sendEmptyMessage(NEED_INVALIDATE)
    }

    companion object {
        private const val NEED_INVALIDATE = 888
    }
}
