package com.example.customview.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.customview.R

class CircularProgressBar(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private var paint: Paint
    private var ringColor: Int
    private var loadColor: Int
    private var ringWidth: Int
    private var progress: Int = 0
    private var progressType: Int
    private var rect: Rect
    private var textColor: Int
    private var textFontSize: Int
    private var isShowText: Int

    init {
        val typeArray =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressBar, 0, 0)
        typeArray.apply {
            ringColor = getColor(R.styleable.CircleProgressBar_ringColor, Color.GRAY)
            loadColor = getColor(R.styleable.CircleProgressBar_loadColor, Color.YELLOW)
            ringWidth = getDimensionPixelSize(
                R.styleable.CircleProgressBar_ringWidth,
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics
                ).toInt()
            )
            progress = getInt(R.styleable.CircleProgressBar_progress, 1)
            progressType = getInt(R.styleable.CircleProgressBar_progressType, 0)
            textColor = getColor(R.styleable.CircleProgressBar_textColor, Color.BLACK)
            textFontSize = getDimensionPixelSize(
                R.styleable.CircleProgressBar_textSize, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics
                ).toInt()
            )
            isShowText = getInt(R.styleable.CircleProgressBar_isShowText, 1)
            recycle()
        }
        rect = Rect()
        paint = Paint().apply {
            isAntiAlias = true
            getTextBounds(PERCENT_TEXT, 0, PERCENT_TEXT.length, rect)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = centerX - ringWidth / 2f
        paint.apply {
            strokeWidth = ringWidth.toFloat()
            textSize = textFontSize.toFloat()
            style = Paint.Style.STROKE
            color = ringColor
        }
        canvas?.drawCircle(centerX, centerY, radius, paint)
        paint.apply {
            color = loadColor
            strokeWidth = ringWidth.toFloat()
        }
        when (progressType) {
            STROKE -> {
                paint.style = Paint.Style.STROKE
                val ovalStroke =
                    RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
                canvas?.drawArc(ovalStroke, -90f, -progress * 360f / MAX_PROGRESS, false, paint)
            }
            FILL -> {
                paint.style = Paint.Style.FILL
                paint.color = Color.BLUE
                val ovalFill = RectF(
                    centerX - radius + ringWidth / 2f,
                    centerY - radius + ringWidth / 2f,
                    centerX + radius - ringWidth / 2f,
                    centerY + radius - ringWidth / 2f
                )
                canvas?.drawArc(ovalFill, -90f, progress * 360f / MAX_PROGRESS, true, paint)
            }
        }
        if (isShowText == 1)
            return
        val percent = progress
        paint.apply {
            color = textColor
            style = Paint.Style.FILL
            strokeWidth = 3f
        }
        val measureTextWidth = paint.measureText("$percent$PERCENT_TEXT")
        canvas?.drawText(
            "$percent $PERCENT_TEXT",
            centerX - measureTextWidth / 2f,
            centerY + measureTextWidth / 2f,
            paint
        )
    }

    fun setProgress(value: Int) {
        progress = value
        invalidate()
    }

    companion object {
        private const val FILL = 0
        private const val STROKE = 1
        private const val MAX_PROGRESS: Int = 100
        private const val PERCENT_TEXT = "%"
    }
}
