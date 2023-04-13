package com.meiling.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewDivider : RecyclerView.ItemDecoration
{

    private var mDivider: Drawable? = null
    private var mDividerHeight = 0
    private var mPaint: Paint? = null
    private var solidLine:Boolean?=true
    private var mOrientation: Int = 0

    private val mBounds = Rect()
    private var mMarginBounds: Rect? = null

    constructor()
    {
        mOrientation = LinearLayout.VERTICAL
    }

    constructor(isVertical: Boolean)
    {
        if (isVertical)
        {
            mOrientation = LinearLayout.VERTICAL
        } else
        {
            mOrientation = LinearLayout.HORIZONTAL

        }
    }

    fun setSolidLine(solidLine:Boolean){
        this.solidLine=solidLine
    }

    fun setDividerHeight(px: Int)
    {
        this.mDividerHeight = px
    }

    fun setDividerHeight(c: Context, dp: Int)
    {
        this.mDividerHeight = (dp * c.resources.displayMetrics.density).toInt()
    }

    fun setDrawable(drawable: Drawable)
    {
        mDivider = drawable
    }

    fun setColor(color: Int)
    {
        mPaint = Paint()
        mPaint!!.color = color
    }

    fun setColor(c: Context, @ColorRes colorId: Int)
    {
        setColor(ContextCompat.getColor(c, colorId))
    }

    fun setMargin(left: Int, top: Int, right: Int, bottom: Int)
    {
        mMarginBounds = Rect()
        mMarginBounds!!.left = left
        mMarginBounds!!.top = top
        mMarginBounds!!.right = right
        mMarginBounds!!.bottom = bottom
    }

    fun setMargin(c: Context, leftDp: Int, topDp: Int, rightDp: Int, bottomDp: Int)
    {
        val density = c.resources.displayMetrics.density
        mMarginBounds = Rect()
        mMarginBounds!!.left = (leftDp * density).toInt()
        mMarginBounds!!.top = (topDp * density).toInt()
        mMarginBounds!!.right = (rightDp * density).toInt()
        mMarginBounds!!.bottom = (bottomDp * density).toInt()
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        if (parent.layoutManager == null)
        {
            return
        }
        if (mOrientation == LinearLayout.VERTICAL)
        {
            drawVertical(c, parent)
        } else
        {
            drawHorizontal(c, parent)
        }
    }

    @SuppressLint("NewApi")
    private fun drawVertical(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding)
        {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else
        {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child))
            var top = bottom - mDividerHeight
            if (null != mDivider)
            {
                val sicWidth = mDivider!!.intrinsicWidth
                if (sicWidth > 0)
                {
                    top = bottom - sicWidth
                }
                if (null != mMarginBounds)
                {
                    mDivider!!.setBounds(
                        left + mMarginBounds!!.left, top + mMarginBounds!!.top,
                        right - mMarginBounds!!.right, bottom - mMarginBounds!!.bottom
                    )
                } else
                {
                    mDivider!!.setBounds(left, top, right, bottom)
                }
                mDivider!!.draw(canvas)
            }
            if (mPaint != null)
            {
                if (null != mMarginBounds)
                {
                    if(solidLine==false){
                        val effects: PathEffect = DashPathEffect(floatArrayOf(8f, 4f, 8f, 4f), 5f)
                        mPaint!!.setPathEffect(effects)
                        canvas.drawLine(
                            (left + mMarginBounds!!.left).toFloat(), (top + mMarginBounds!!.top).toFloat(),
                            (right - mMarginBounds!!.right).toFloat(), (bottom - mMarginBounds!!.bottom).toFloat(), mPaint!!
                        )
                    }else{
                        canvas.drawRect(
                            (left + mMarginBounds!!.left).toFloat(), (top + mMarginBounds!!.top).toFloat(),
                            (right - mMarginBounds!!.right).toFloat(), (bottom - mMarginBounds!!.bottom).toFloat(), mPaint!!
                        )
                    }

                } else
                {
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint!!)
                }
            }
        }
        canvas.restore()
    }

    @SuppressLint("NewApi")
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding)
        {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else
        {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(ViewCompat.getTranslationX(child))
            var left = right - mDividerHeight
            if (null != mDivider)
            {
                val sicWidth = mDivider!!.intrinsicWidth
                if (sicWidth > 0)
                {
                    left = right - sicWidth
                }
                if (null != mMarginBounds)
                {
                    mDivider!!.setBounds(
                        left + mMarginBounds!!.left, top + mMarginBounds!!.top,
                        right - mMarginBounds!!.right, bottom - mMarginBounds!!.bottom
                    )
                } else
                {
                    mDivider!!.setBounds(left, top, right, bottom)
                }
                mDivider!!.draw(canvas)
            }
            if (mPaint != null)
            {
                if (null != mMarginBounds)
                {
                    canvas.drawRect(
                        (left + mMarginBounds!!.left).toFloat(), (top + mMarginBounds!!.top).toFloat(),
                        (right - mMarginBounds!!.right).toFloat(), (bottom - mMarginBounds!!.bottom).toFloat(), mPaint!!
                    )
                } else
                {
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint!!)
                }
            }
        }
        canvas.restore()
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == LinearLayout.VERTICAL)
        {
            if (0 == mDividerHeight && null != mDivider)
            {
                outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)

            } else
            {
                outRect.set(0, 0, 0, mDividerHeight)
            }
        } else
        {
            if (0 == mDividerHeight && null != mDivider)
            {
                outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
            } else
            {
                outRect.set(0, 0, mDividerHeight, 0)

            }
        }
    }
}
