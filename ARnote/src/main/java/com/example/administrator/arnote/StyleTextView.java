
/*
package com.example.administrator.arnote;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static android.content.res.Resources.*;


public class StyleTextView extends View {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    protected String text = "点击设置";
    private float mScale = 1f;
    private Context mContext;
    // private Paint.FontMetrics mFontMetrics;
    private Resources.Theme mTheme = new Resources.Theme();
    private float mSpacing = 0f;
    protected int padding = 50;
    private float mTextSize = 50;
    private int mTextColor = 0xffffffff;
    private int mBgColor = 0x00ffffff;
    private int mStrokeColor = 0xffffffff;

    private BgStyle mBgStyle;
    protected boolean isShowStroke = false;
    private boolean isMultiLine;
    private int orientation = ORIENTATION_HORIZONTAL;
    // private int orientation = ORIENTATION_VERTICAL;
    private int strokeWidth = 5;
    protected TextPaint mTextPaint;
    private int viewWidth,viewHeight;
    private Bitmap bmp_suofang,bmp_delete;

    protected Paint mStrokePaint,mBgPaint;

    private Paint.Align mTextAlign = Paint.Align.CENTER;

    public StyleTextView(Context context) {
        this(context, null, 0);
    }

    public StyleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
// mFontMetrics = mTextPaint.getFontMetrics();

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        mStrokePaint.setStrokeWidth(strokeWidth);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);

        bmp_suofang = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_suofang);
        bmp_delete = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_delete);
        bmp_suofang = Bitmap.createScaledBitmap(bmp_suofang, padding, padding, true);
        bmp_delete = Bitmap.createScaledBitmap(bmp_delete,padding,padding,true);

        initDrawValue();
        setBgStyle(BgStyle.Rect);
// mTheme = new Theme();
    }

    public int getPadding() {
        return padding;
    }

    private Path bgPath;
    private Rect rect;
    private RectF rectF,left,right;

    private void initDrawValue(){
        bgPath = new Path();
        rect = new Rect();
        rectF = new RectF();
        left = new RectF();
        right = new RectF();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getBackground() ==null) {
            int w = 0, h = 0;
            int[] wh = getWidthAndHeight();
            viewWidth = wh[0];
            viewHeight = wh[1];
        }else {
            viewWidth = getBackground().getIntrinsicWidth();
            viewHeight = getBackground().getIntrinsicHeight();
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    public int checkTextSize(String text, float width, float height){
        TextPaint tp = new TextPaint();
        tp.setTypeface(mTextPaint.getTypeface());
        int textSize = 0;
        for (int i = 0; i < 200; i++) {
            tp.setTextSize(i);
            int w = (int) tp.measureText(text);
            int h = (int) (tp.descent() - tp.ascent());
// MyLog.v("ww :"+w);
// MyLog.v("hh :"+h);
            if ((w > width - 5 && w < width)){
                textSize = i;
                break;
            }
        }
        return textSize;
    }

    private int[] getWidthAndHeight(){
        int[] wh = new int[2];
        int w=0,h=0;
        int lineCount = 1;

        if (text.contains("\n")){
            isMultiLine = true;
        }else {
            isMultiLine = false;
        }

        if (isMultiLine){
            String[] texts = text.split("\n");
            int fontHeight = (int) (-mTextPaint.ascent() + mTextPaint.descent());
            if (orientation == ORIENTATION_HORIZONTAL){
                for (int i = 0; i < texts.length; i++) {
                    String textItem = texts[i];
                    int itemLength = (int) mTextPaint.measureText(textItem);
                    if (itemLength > w){
                        w = itemLength;
                    }
                }
                lineCount = texts.length;
                h = (int)mSpacing * (lineCount - 1);
            }else {
                for (int i = 0; i < texts.length; i++) {
                    String textItem = texts[i];
// int maxW = 0;
// for (int j = 0; j < textItem.length(); j++) {
// String s = text.substring(i,i+1);
// int ww = (int) mTextPaint.measureText(s);
// if (ww > maxW){
// maxW = ww;
// }
// }
                    int itemCount = textItem.length();
                    if (itemCount > lineCount){
                        lineCount = itemCount;
                    }
                }
                w = (int) mTextPaint.measureText("国") * texts.length + (int)mSpacing * (texts.length - 1);
            }
            w = w + getPaddingLeft() + getPaddingRight();
            h = h + fontHeight*lineCount + getPaddingTop() + getPaddingBottom();
        }else {
            if (orientation == ORIENTATION_HORIZONTAL) {
                w = (int) (((int) mTextPaint.measureText(text) + getPaddingLeft() + getPaddingRight())* 1);
                h = (int) (((int) (-mTextPaint.ascent() + mTextPaint.descent()) + getPaddingTop() + getPaddingBottom())* 1);
            }else {

                for (int i = 0; i < text.length(); i++) {
                    String s = text.substring(i,i+1);
                    int ww = (int) mTextPaint.measureText(s);
                    if (ww > w){
                        w = ww;
                    }
                }

                w = (int) ((w + getPaddingLeft() + getPaddingRight()) * mScale);
                h = (int) (((int) ((-mTextPaint.ascent() + mTextPaint.descent())*text.length()) + getPaddingTop() + getPaddingBottom())*mScale);
            }
        }
        wh[0] = w;
        wh[1] = h;
        return wh;
    }

    public void setScale(float scale){
        mScale = mScale * scale;
        setScaleX(mScale);
        setScaleY(mScale);


    }

    public void fontBold(){
// mTextPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
// Typeface bold = Typeface.DEFAULT_BOLD;
// mTextPaint.setTypeface(bold);
        invalidate();
    }

    public void setScaleByTextSize(float f) {
        if (isMultiLine && orientation == ORIENTATION_HORIZONTAL) {
            mSpacing = mSpacing * f;
        }
        mTextSize = mTextSize * f ;
        mTextPaint.setTextSize(mTextSize);

        requestLayout();
    }


    protected void setNewPadding(int newPadding){
        padding = newPadding;
        setPadding(newPadding, newPadding - 5, newPadding, newPadding - 5);
        requestLayout();
    }
    public TextPaint getTextPaint(){
        return mTextPaint;
    }

    public void initText(){
        text = "点击设置";
        requestLayout();
    }
    public int[] setText(String text) {
// mTheme.setContent(text);
        this.text = text;
        int[] wh = getWidthAndHeight();
        if (wh[0] == viewWidth && wh[1] == viewHeight){
            invalidate();
        }else {
            requestLayout();
        }
        return wh;
    }

    public String getText() {
        return text;
    }



    public void setSpacing(float mSpacing) {
        this.mSpacing = mSpacing;
        requestLayout();
    }

    public float getSpacing() {
        return mSpacing;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
        requestLayout();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setTextColorBean(ColorBean colorBean) {
        mTheme.setTextColor(colorBean);
        mTextColor = colorBean.getArgbColor();
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setTextAlpha(int a){
        ColorBean colorBean = mTheme.getTextColor();
        colorBean.setAlpha(a);
        mTextColor = colorBean.getArgbColor();
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        mBgPaint.setColor(mBgColor);
        invalidate();
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgStyle(BgStyle mBgStyle) {
        this.mBgStyle = mBgStyle;
        if (mBgStyle == BgStyle.TwoRect){
            setPadding(padding * 3,padding,padding * 3,padding);
        }else {
            setPadding(padding,padding,padding,padding);
            invalidate();
        }
// invalidate();
    }

    public BgStyle getBgStyle() {
        return mBgStyle;
    }

    public void setFont(String path){
        Typeface typeFace;
        if (!path.equalsIgnoreCase("")) {
            typeFace = Typeface.createFromAsset(mContext.getAssets(), path);
        }else {
            typeFace = Typeface.SERIF;
        }
// mTheme.setTextFont(path);
        mTextPaint.setTypeface(typeFace);
        requestLayout();
    }

    public void setTextAlign(Paint.Align mTextAlign) {
        this.mTextAlign = mTextAlign;
        invalidate();
    }

// public void setTheme(Theme mTheme) {
// this.mTheme = mTheme;
// }

// public void notifyData(){
// if (!mTheme.getContent().equalsIgnoreCase("")){
// text = mTheme.getContent();
// }
// setSpacing(mTheme.getTextLineSpacing());
// alignment = mTheme.getTextAlign();
// mTextPaint.setColor(mTheme.getTextColor().getArgbColor());
//
// mBgStyle = mTheme.getTextBgStyle();
// mBgColor = mTheme.getColorBean().getArgbColor();
// mBgPaint.setColor(mBgColor);
//
// setFont(mTheme.getTextFont());
//
// invalidate();
// }

// public Theme getTheme() {
// return mTheme;
// }

    protected void drawStroke(Canvas canvas){
        canvas.drawRect(getPaddingLeft()/2, getPaddingTop()/2, getWidth()-getPaddingRight()/2, getHeight()-getPaddingBottom()/2, mStrokePaint);
    };

    public void showStroke(){
        isShowStroke = true;
        invalidate();
    }

    public void hideStroke(){
        isShowStroke = false;
        invalidate();
    }

    public boolean isShowStroke(){
        return isShowStroke;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBgStyle == BgStyle.Rect) {

            rect.set(strokeWidth*2, strokeWidth*2, getWidth()-strokeWidth*2, getHeight()-strokeWidth*2);
            canvas.drawRect(rect, mBgPaint);
        }else if (mBgStyle == BgStyle.RectF){
            rectF.set(strokeWidth*2, strokeWidth*2, getWidth()-strokeWidth*2, getHeight()-strokeWidth*2);
            canvas.drawRoundRect(rectF,20,20,mBgPaint);
        }else {
            int sideLength = viewHeight/3;
            bgPath.reset();
            bgPath.moveTo(strokeWidth * 2, strokeWidth * 2);
            bgPath.lineTo(strokeWidth * 2, viewHeight - strokeWidth * 2 - sideLength);
            bgPath.lineTo(strokeWidth * 2 + sideLength, viewHeight - strokeWidth * 2);
            bgPath.lineTo(viewWidth - strokeWidth * 2, viewHeight - strokeWidth * 2);
            bgPath.lineTo(viewWidth - strokeWidth * 2, strokeWidth * 2 + sideLength);
            bgPath.lineTo(viewWidth - strokeWidth * 2 - sideLength, strokeWidth * 2);
            bgPath.close();

            canvas.drawPath(bgPath, mBgPaint);
            left.set(strokeWidth * 2, viewHeight - strokeWidth * 2 - sideLength * 2,
                    strokeWidth * 2 + sideLength * 2, viewHeight - strokeWidth * 2);

            right.set(viewWidth - strokeWidth * 2 - sideLength * 2,strokeWidth * 2 ,
                    viewWidth - strokeWidth * 2 ,strokeWidth*2 + sideLength * 2);

            canvas.drawArc(left,90,90,true,mBgPaint);
            canvas.drawArc(right,270,90,true,mBgPaint);
        }

        if (isShowStroke){
            drawStroke(canvas);

            canvas.drawBitmap(bmp_delete, 0, 0, mStrokePaint);
            canvas.drawBitmap(bmp_suofang, getWidth() - padding, getHeight() - padding, mStrokePaint);
        }

        int textWidth = (int) mTextPaint.measureText(text);
        int textHeight = (int) (-mTextPaint.ascent() + mTextPaint.descent());
        int l =( canvas.getWidth() - textWidth )/2;
        int t = (canvas.getHeight() - textHeight)/2;

// RectF rectF = new RectF(l,t,l+textWidth,t+textHeight);
// canvas.drawRect(rectF, mBgPaint);

// mStrokePaint.setStrokeWidth(strokeWidth / 2);
// canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, mStrokePaint);
// canvas.scale(mScale,mScale);

        if (isMultiLine){
            String[] texts = text.split("\n");
            int lineCount = texts.length;
            if (orientation == ORIENTATION_HORIZONTAL){
                int baseX;
                if (mTextAlign == Paint.Align.CENTER){
                    baseX = canvas.getWidth() / 2;
                }else if (mTextAlign == Paint.Align.LEFT){
                    baseX = getPaddingLeft();
                }else {
                    baseX = canvas.getWidth() - getPaddingRight();
                }
                mTextPaint.setTextAlign(mTextAlign);

                for (int i = 0; i < lineCount; i++) {
                    String textItem = texts[i];
                    canvas.save();
                    canvas.translate(0, i * textHeight + mSpacing * i);
                    canvas.drawText(textItem, baseX, getPaddingTop() + textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
// Rect re = new Rect();
// mTextPaint.getTextBounds(textItem, 0, 1, re);
// canvas.drawRect(re,mStrokePaint);
                    canvas.restore();
                }
            }else {
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                int itemWidth = (int) mTextPaint.measureText("国");
                for (int i = 0; i < lineCount; i++) {
                    String textItem = texts[i];
                    for (int j = 0; j <textItem.length(); j++) {
                        String s = textItem.substring(j, j + 1);
                        canvas.save();
                        canvas.translate(getPaddingLeft() + (lineCount - 1 - i) * (itemWidth + mSpacing), getPaddingTop() + j * textHeight);
                        canvas.drawText(s, itemWidth / 2, textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
                        canvas.restore();
                    }
                }
            }
        }else {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            if (orientation == ORIENTATION_HORIZONTAL) {
                int textY = canvas.getHeight() / 2 + (int) (textHeight / 2 - mTextPaint.descent());
                canvas.drawText(text, canvas.getWidth() / 2, textY, mTextPaint);
            } else {
                for (int i = 0; i < text.length(); i++) {
                    String s = text.substring(i, i + 1);
                    canvas.save();
                    canvas.translate(0, getPaddingTop() + i * textHeight);
                    canvas.drawText(s, canvas.getWidth() / 2, textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
                    canvas.restore();
                }
            }
        }


    }

    public boolean isAboveScale(int x,int y){
        int scaleX = getRight() - padding;
        int titlebarH = (int)getResources().getDimension(R.dimen.titlebar_height);
        int scaleY = titlebarH+getBottom() - padding;
        return (x > scaleX && x < getRight() && y > scaleY && y < getBottom()+titlebarH);
    }

    public boolean isAboveDelete(int x,int y){
        int titlebarH = (int)getResources().getDimension(R.dimen.titlebar_height);
        return (x > getLeft() && x < getLeft() + padding && y > getTop()+titlebarH && y < getTop() + padding+titlebarH);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void changeOrientation(){
        if (orientation == ORIENTATION_HORIZONTAL){
            orientation = ORIENTATION_VERTICAL;
        }else {
            orientation = ORIENTATION_HORIZONTAL;
        }
        requestLayout();
    }

    public enum BgStyle{
        Rect,RectF,TwoRect
    }
}
*/