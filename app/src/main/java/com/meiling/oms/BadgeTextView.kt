package com.meiling.oms

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

class BadgeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var badgeCount = 0

    init {
        badgePaint.color = Color.RED
        badgePaint.textSize = resources.getDimension(R.dimen.dp_12)
        badgePaint.typeface = Typeface.DEFAULT_BOLD
        badgePaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (badgeCount <= 0) {
            return
        }

        // 计算角标圆圈的半径和位置
        val radius = resources.getDimension(R.dimen.dp_12)
        val cx = (width + paddingRight).toFloat() - radius
        val cy = paddingTop.toFloat() + radius

        // 绘制角标圆圈
        canvas?.drawCircle(cx, cy, radius, badgePaint)

        // 绘制角标文本
        val badgeText = badgeCount.toString()
        val textBounds = Rect()
        badgePaint.getTextBounds(badgeText, 0, badgeText.length, textBounds)
        val textX = cx
        val textY = cy + textBounds.height() / 2f
        canvas?.drawText(badgeText, textX, textY, badgePaint)
    }

    fun setBadgeCount(count: Int) {
        this.badgeCount = count
        invalidate() // 重绘控件
    }
}
