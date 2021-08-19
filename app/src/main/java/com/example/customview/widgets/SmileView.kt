package com.example.customview.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class SmileView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var mCirclePaint: Paint
    private lateinit var mEyeAndMouthPaint: Paint
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mRadius = 0f

    init {
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenterX = w / 2f
        mCenterY = h / 2f
        mRadius = min(w, h) / 2f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        // draw square
        val mBounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRect(mBounds, mEyeAndMouthPaint)

        //draw face
        canvas.drawCircle(mCenterX, mCenterY, mRadius * 0.9f, mCirclePaint)

        //draw eyes
        val eyeRadius = mRadius / 5f
        val eyeOffsetX = mRadius / 3f
        val eyeOffsetY = mRadius / 3f
        canvas.drawCircle(
            mCenterX - eyeOffsetX,
            mCenterY - eyeOffsetY,
            eyeRadius,
            mEyeAndMouthPaint
        )
        canvas.drawCircle(
            mCenterX + eyeOffsetX,
            mCenterY - eyeOffsetY,
            eyeRadius,
            mEyeAndMouthPaint
        )

        //draw mouth
        val mBounds1 = RectF(mCenterX / 2f, mCenterY, mCenterX * 1.5f, mCenterY * 1.4f)
        canvas.drawArc(mBounds1, 25f, 120f, false, mEyeAndMouthPaint)

        //draw nose
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY * 1.2f, mEyeAndMouthPaint)
    }

    private fun initPaint() {
        mCirclePaint = Paint().apply {
            Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL_AND_STROKE
            color = Color.YELLOW
            strokeWidth = 6f * resources.displayMetrics.density
        }

        mEyeAndMouthPaint = Paint().apply {
            Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.STROKE
            color = Color.BLACK
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 8f * resources.displayMetrics.density
        }
    }
}
