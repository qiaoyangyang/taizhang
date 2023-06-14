package com.meiling.account;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;


public class MyFlipOver extends View {
    private Bitmap foreImage;//第一页图片
    private Bitmap bgImage;//第二页图片
    private PointF touchPt;
    private int screenWidth;
    private int screenHeight;
    private GradientDrawable shadowDrawableRL;
    private GradientDrawable shadowDrawableLR;
    private ColorMatrixColorFilter mColorMatrixFilter;
    private Scroller mScroller;
    private int lastTouchX;
    private int canTouchWidth = 50;//右侧可滑动的区域,通过初始手指落下区域,判定是否拦截消耗
    private Paint mPaint_back;
    private Paint mPaint_front;
    private Paint mPaint_line;
    private View bgView;
    private boolean canCanvas;//达到滑动距离条件才可以绘制
    private float startX, startY;
    private Context context;

    private Path bgPath;
    private Matrix matrix;
    private Paint backPaint;
    private long touchUpTime;//手指抬起时间
    private boolean canTouch = true;//是否响应触摸

    private Bitmap background;
    private int clearHandlerWhat = 0x11;

    private void init(Context context) {
        this.context = context;
        touchPt = new PointF(-1, -1);

        //ARGB A(0-透明,255-不透明)
        int[] color = {0xb0bababa, 0x00bababa};
        shadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, color);
        shadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        shadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color);
        shadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

//        float array[] = {0.55f, 0, 0, 0, 80.0f
//                , 0.55f, 0, 0, 80.0f
//                , 0, 0.55f, 0, 80.0f
//                , 0, 0, 0.2f, 0, 0, 0.2f, 0};

        //颜色矩阵,R,G,B ,A(透明度)
        float array[] = {1.0f, 0, 0, 0, 0
                , 1.0f, 0, 0, 0, 0
                , 1.0f, 0, 0, 0, 0
                , 0.4f, 0, 0, 0, 0};
        ColorMatrix cm = new ColorMatrix();
        cm.set(array);

        mColorMatrixFilter = new ColorMatrixColorFilter(cm);

        //利用滚动条来实现接触点放开后的动画效果
        mScroller = new Scroller(context);
        mPaint_back = new Paint();
        mPaint_front = new Paint();
        mPaint_line = new Paint();

        bgPath = new Path();
        matrix = new Matrix();
        backPaint = new Paint();
        backPaint.setColor(0xffffffff);
//        scroller = new MyScroller(new MyScroller.OnPageListener() {
//            @Override
//            public void end() {
////                clear();
//            }
//
//            @Override
//            public void node(float startData1) {
//                touchPt.x = startData1;
//                postInvalidate();
//            }
//        });

    }

    boolean scrollerIsEnd = true;
    long scrollEndTime;

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            touchPt.x = mScroller.getCurrX();
            touchPt.y = mScroller.getCurrY();
            postInvalidate();
//            Log.d("ssss", "computeScroll: " + touchPt.x + " ssss " + screenWidth);
            if ((Math.abs(touchPt.x) > screenWidth - 50 && System.currentTimeMillis() - scrollEndTime > 500) && scrollerIsEnd) {
//                Log.d("ssss", "computeScroll: 滚动已经完成");
                scrollerIsEnd = false;
//                postInvalidateDelayed(1000);//延迟一秒更新界面
                scrollEndTime = System.currentTimeMillis();
//                clear();

            }
        } else {
//            touchPt.x = -1;
//            touchPt.y = -1;
        }
        super.computeScroll();
    }

    /**
     * 设置翻页背景
     */
    public void setBackground(int background) {
        this.background = BitmapFactory.decodeResource(getResources(), background).copy(Bitmap.Config.ALPHA_8, true);

    }

    /**
     * 重置绘制状态
     */
    public void clear() {
        //重置paint
        mPaint_front.reset();
        //把画笔颜色设置为透明，重绘
        mPaint_front.setColor(Color.TRANSPARENT);

        mPaint_back.reset();
        mPaint_back.setColor(Color.TRANSPARENT);

        mPaint_line.reset();
        mPaint_line.setColor(Color.TRANSPARENT);
        postInvalidate();

//        Log.d("ssss", "run: 重置");
        canCanvas = false;//重置可绘制状态
        canTouch = true;//标为可触摸

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (canCanvas) {
            drawPageEffect(canvas);
        }
    }

    /**
     * 画前景图片
     *
     * @param canvas
     */
    private void drawForceImage(Canvas canvas) {
        // TODO Auto-generated method stub
//        Paint mPaint = new Paint();

        if (foreImage != null) {
            canvas.drawBitmap(foreImage, 0, 0, mPaint_front);
        }
    }


    /**
     * 画背景图片
     *
     * @param canvas
     */
    private void drawBgImage(Canvas canvas, Path path) {
        // TODO Auto-generated method stub
//        Paint mPaint = new Paint();

        if (bgImage != null) {
            canvas.save();
            //只在与路径相交处画图
            canvas.clipPath(path, Region.Op.INTERSECT);
            canvas.drawBitmap(bgImage, 0, 0, mPaint_back);
            canvas.restore();
//            Log.d("ssss", "drawBgImage: 有绘制背面");
        }

    }


    /**
     * 画翻页效果
     *
     * @param canvas
     */
    private void drawPageEffect(Canvas canvas) {
        // TODO Auto-generated method stub
        drawForceImage(canvas);//画前景图片

//        if (touchPt.x != -1 && touchPt.y != -1) {
        //翻页左侧书边
        canvas.drawLine(touchPt.x, 0, touchPt.x, screenHeight, mPaint_line);
        //左侧书边画阴影
        shadowDrawableRL.setBounds((int) touchPt.x - 20, 0, (int) touchPt.x, screenHeight);
        shadowDrawableRL.draw(canvas);

        float halfCut;
        int backLeft;//翻页背面的左侧
        //翻页对折处
        if (down_x < screenWidth / 2) {//点击的左侧,表示向右滑
//                halfCut = touchPt.x + (screenWidth - touchPt.x) / 2;
            halfCut = touchPt.x;
            backLeft = (int) (touchPt.x - (screenWidth - touchPt.x));
        } else {
            halfCut = touchPt.x + (screenWidth - touchPt.x) / 2;
            backLeft = (int) touchPt.x;
        }
        //对折左侧阴影(从左往右翻)
        shadowDrawableRL.setBounds((int) backLeft - 20, 0, (int) backLeft + 20, screenHeight);
        shadowDrawableRL.draw(canvas);


        canvas.drawLine(halfCut, 0, halfCut, screenHeight, mPaint_line);
        //对折处左侧画翻页页图片背面
        Rect backArea = new Rect(backLeft, 0, (int) halfCut, screenHeight);

        canvas.drawRect(backArea, backPaint);

        //将翻页图片正面进行处理水平翻转并平移到touchPt.x点

        mPaint_line.setColorFilter(mColorMatrixFilter);

        matrix.reset();
        matrix.preScale(-1, 1);
        matrix.postTranslate(foreImage.getWidth() + touchPt.x, 0);
        canvas.save();
        canvas.clipRect(backArea);
        canvas.drawBitmap(foreImage, matrix, mPaint_line);
        canvas.restore();

        //对折处画左侧阴影
        shadowDrawableRL.setBounds((int) halfCut - 50, 0, (int) halfCut, screenHeight);
        shadowDrawableRL.draw(canvas);


        bgPath.reset();
        //可以显示背景图的区域
        bgPath.addRect(new RectF(halfCut, 0, screenWidth, screenHeight), Path.Direction.CW);
        //对折出右侧画背景
        drawBgImage(canvas, bgPath);
        //对折处画右侧阴影
        shadowDrawableLR.setBounds((int) halfCut, 0, (int) halfCut + 50, screenHeight);
        shadowDrawableLR.draw(canvas);
//        }
    }

    private float down_x;//点下的坐标 用以判断左移还是右移
    private boolean isTouchRight = false;

    private float a = 0;

    /**
     * 笔 悬浮判定
     */
    public boolean onHoverEvent(MotionEvent event) {
        if (isTouchRight) {//如果笔悬浮时 手指处于翻页状态 就直接清空绘板
            isTouchRight = false;
            clear();
        }
        return super.onHoverEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
//        Log.d("ssss", "onTouchEvent: " + event.getToolType(0));
        //橡皮擦和笔不拦截 如果正在翻页就直接翻过
        if (event.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS || event.getToolType(0) == MotionEvent.TOOL_TYPE_ERASER) {
            if (isTouchRight) {
                scrollerIsEnd = true;
                isTouchRight = false;//重置起始区域判定
                int upX = (int) event.getX();
                int dx, dy;
                dy = 0;
                if (lastTouchX < touchPt.x && (upX > getWidth() / 2)) {
                    dx = foreImage.getWidth() - (int) touchPt.x + 30;
                } else if (lastTouchX > touchPt.x && (upX < getWidth() / 2)) {
                    dx = -(int) touchPt.x - foreImage.getWidth();
                } else {
                    if (upX < touchPt.x) {
                        dx = foreImage.getWidth() - (int) touchPt.x + 30;
                    } else {
                        dx = -(int) touchPt.x - foreImage.getWidth();
                    }
                }
                Log.d("ssss", "onTouchEvent: 继续滚动");
                mScroller.startScroll((int) touchPt.x, (int) touchPt.y, dx, dy, 300);
                postInvalidate();//将正在翻页的动作执行完毕 避免翻页卡主
                //笔和橡皮擦 不拦截
                return false;
            } else {
//                Log.d("ssss", "onTouchEvent: 不拦截");
                //笔和橡皮擦 不拦截
                return false;
            }
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (!canTouch && System.currentTimeMillis() - touchUpTime < 2000) {//不可触摸且时间小于3秒 就不响应
            return true;
        }

//        Log.d("ssss", "能触摸: "+(!canTouch)+" : "+(System.currentTimeMillis() - touchUpTime < 1500));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.d("ssss", "onTouchEvent: 点击  " + isTouchRight);
                down_x = event.getX();


                if (((x < canTouchWidth && y > screenHeight / 4) || (x > screenWidth - canTouchWidth && y > screenHeight / 4))) {

                    if (x < canTouchWidth) {
                        foreImage = getBgBitmap();
                        bgImage = getBitmap(bgView);
                    } else {
                        bgImage = getBgBitmap();
                        foreImage = getBitmap(bgView);
                    }
                    mPaint_back.reset();
                    mPaint_back.setColor(Color.WHITE);
                    mPaint_front.reset();
                    mPaint_front.setColor(Color.WHITE);
                    mPaint_line.reset();
                    //把画笔颜色设置为透明，重绘
                    mPaint_line.setColor(Color.WHITE);
                    isTouchRight = true;//点击的是翻页区域
                    if (x < foreImage.getWidth() / 2) {//点击的左侧,说明要往右滑动
                        touchPt.x = x - foreImage.getWidth();
                    } else {
                        touchPt.x = x;
                    }
//                touchPt.x = event.getX();
                    touchPt.y = event.getY();
                    startX = event.getX();
                    startY = event.getY();
                    postInvalidate();
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:

                if (isTouchRight) {
                    lastTouchX = (int) touchPt.x;

                    touchPt.x = event.getX();
                    touchPt.y = event.getY();
                    if (Math.abs(touchPt.x - startX) > 50) {
                        canCanvas = true;
                    }
                    postInvalidate();
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
                if (isTouchRight) {


                    touchUpTime = System.currentTimeMillis();
                    canTouch = false;
                    isTouchRight = false;//重置起始区域判定
                    int upX = (int) event.getX();
                    int dx, dy;
                    dy = 0;
                    if (foreImage == null) {
                        return true;
                    }
                    a = 0;
                    int nextType = 2;

                    if (down_x < screenWidth / 2 && Math.abs(down_x - upX) > 50) {
                        dx = foreImage.getWidth() - (int) touchPt.x + 30;
                        nextType = 0;
                        a = -foreImage.getWidth();
                    } else if (down_x > screenWidth / 2 && Math.abs(down_x - upX) > 50) {
                        dx = -(int) touchPt.x - foreImage.getWidth();
                        nextType = 1;
                        a = foreImage.getWidth();
                    } else {
                        if (upX < touchPt.x) {
                            dx = foreImage.getWidth() - (int) touchPt.x + 30;
                        } else {
                            dx = -(int) touchPt.x - foreImage.getWidth();
                        }

                    }


                    if (Math.abs(down_x - upX) < 150) {
                        canCanvas = false;
                        Log.d("ssss", "不满足翻页要求: " + Math.abs(down_x - upX));
                        clear();
                        return true;
                    }
//                    scroller.start(touchPt.x, a);
//                    Log.d("ssss", "手指抬起:     " + nextType);
                    mScroller.startScroll((int) touchPt.x, (int) touchPt.y, dx, dy, 300);
                    postInvalidate();
                    if (nextType == 0) {//左滑
                        onPageListener.OnPreviousPage();
                    } else if (nextType == 1) {//右滑
                        onPageListener.OnNextPage();
                    }

                    return true;
                } else {
                    return false;
                }

        }
        //必须为true，否则无法获取ACTION_MOVE及ACTION_UP事件
        return false;
    }

    /**
     * 设置可点击区域
     */
    public void setBgView(View view, OnPageListener onPageListener) {
        this.bgView = view;
        this.onPageListener = onPageListener;
    }

    private Bitmap getBgBitmap() {

//        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        int windowWidth = bgView.getWidth();
        int windowHeight = bgView.getHeight();
        if (background == null) {
//            background = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_goods).copy(Bitmap.Config.ALPHA_8, true);
        }

        int width = background.getWidth();
        int height = background.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale((float) windowWidth / width, (float) windowHeight / height);
//        Log.d("ssss", "图片高度: " + width + ":" + height);
        //获取新的bitmap
        return Bitmap.createBitmap(background, 0, 0, width, height, matrix, true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        //getMeasuredHeight需要在setMeasuredDimension()之后,否则可能为0
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();
    }

    public Bitmap getBitmap(View view) {
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-view.getScrollX(), -view.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        view.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }


    private OnPageListener onPageListener;

    public MyFlipOver(Context context) {
        super(context);
        init(context);
    }

    public MyFlipOver(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyFlipOver(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public interface OnPageListener {
        /**
         * 点击item
         */
        void OnPreviousPage();//上一页

        void OnNextPage();//下一页

    }
}

