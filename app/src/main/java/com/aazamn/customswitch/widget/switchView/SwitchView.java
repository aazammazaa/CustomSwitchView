package com.aazamn.customswitch.widget.switchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aazamn.customswitch.R;


public class SwitchView extends View {

    private int layoutHeight = 0;
    private int layoutWidth = 0;

    private float minAllow;
    private float  maxAllow;
    private float  currentPoint;

    private float radus;
    private float smallRadus;

    private float top;
    private float bottom;

    private boolean isOn = false;


    Path clipPath;

    private Paint innerCircle;
    private Paint circle;
    private Paint leftRect;
    private Paint rightRect;
    private Paint background;

    private  Bitmap bitmap;
    private  Bitmap bitmapLeft;
    private  Bitmap bitmapRight;
    private int layoutRatio = 4;

    private  Drawable buttonImg;
    private  Drawable leftImg;
    private  Drawable rightImg;

    private int buttonColor = Color.parseColor("#FFB5FF14");
    private int leftColor = Color.parseColor("#FF14A1FF");
    private int rightColor = Color.parseColor("#FFFF5137");
    private int shadowColor = Color.parseColor("#FFFFEF14");

    private float extraRadus = 10;

    private  OnCheckedChangeListener mOnCheckedChangeListener;


    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground(null);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.switchView));

    }
    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        buttonImg = a.getDrawable(R.styleable.switchView_buttonImage);
        leftImg = a.getDrawable(R.styleable.switchView_leftImage);
        rightImg = a.getDrawable(R.styleable.switchView_rightColor);

        buttonColor = a.getColor(R.styleable.switchView_buttonColor,buttonColor);
        leftColor = a.getColor(R.styleable.switchView_leftColor,leftColor);
        rightColor = a.getColor(R.styleable.switchView_rightColor,rightColor);
        shadowColor = a.getColor(R.styleable.switchView_shadowColor,shadowColor);

        isOn =a.getBoolean(R.styleable.switchView_isOn,isOn);

        extraRadus = a.getDimension(R.styleable.switchView_extraRadius,extraRadus);

        layoutRatio = a.getInt(R.styleable.switchView_ratio,layoutRatio);

       /* barWidth = (int) a.getDimension(R.styleable.ProgressWheel_pwBarWidth, barWidth);
        rimWidth = (int) a.getDimension(R.styleable.ProgressWheel_pwRimWidth, rimWidth);
        spinSpeed = (int) a.getDimension(R.styleable.ProgressWheel_pwSpinSpeed, spinSpeed);
        barLength = (int) a.getDimension(R.styleable.ProgressWheel_pwBarLength, barLength);

        delayMillis = a.getInteger(R.styleable.ProgressWheel_pwDelayMillis, delayMillis);
        if (delayMillis < 0) { delayMillis = 10; }

        // Only set the text if it is explicitly defined
        if (a.hasValue(R.styleable.ProgressWheel_pwText)) {
            setText(a.getString(R.styleable.ProgressWheel_pwText));
        }

        barColor = a.getColor(R.styleable.ProgressWheel_pwBarColor, barColor);
        textColor = a.getColor(R.styleable.ProgressWheel_pwTextColor, textColor);
        rimColor = a.getColor(R.styleable.ProgressWheel_pwRimColor, rimColor);
        circleColor = a.getColor(R.styleable.ProgressWheel_pwCircleColor, circleColor);
        contourColor = a.getColor(R.styleable.ProgressWheel_pwContourColor, contourColor);

        textSize = (int) a.getDimension(R.styleable.ProgressWheel_pwTextSize, textSize);
        contourSize = a.getDimension(R.styleable.ProgressWheel_pwContourSize, contourSize);*/

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The first thing that happen is that we call the superclass
        // implementation of onMeasure. The reason for that is that measuring
        // can be quite a complex process and calling the super method is a
        // convenient way to get most of this complexity handled.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // We can’t use getWidth() or getHight() here. During the measuring
        // pass the view has not gotten its final size yet (this happens first
        // at the start of the layout pass) so we have to use getMeasuredWidth()
        // and getMeasuredHeight().
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // Finally we have some simple logic that calculates the size of the view
        // and calls setMeasuredDimension() to set that size.
        // Before we compare the width and height of the view, we remove the padding,
        // and when we set the dimension we add it back again. Now the actual content
        // of the view will be square, but, depending on the padding, the total dimensions
        // of the view might not be.
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (heightMode != MeasureSpec.UNSPECIFIED && widthMode != MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding /layoutRatio > heightWithoutPadding) {
                size = heightWithoutPadding *layoutRatio ;
            } else {
                size = widthWithoutPadding;
            }
        } else {
            size = Math.max(heightWithoutPadding, widthWithoutPadding);
        }
        //final int extraRadus = (int) this.extraRadus;
        //setPadding(getPaddingLeft()+extraRadus,getPaddingTop()+extraRadus,getPaddingRight()+extraRadus,getPaddingBottom()+extraRadus);

        // If you override onMeasure() you have to call setMeasuredDimension().
        // This is how you report back the measured size.  If you don’t call
        // setMeasuredDimension() the parent will throw an exception and your
        // application will crash.
        // We are calling the onMeasure() method of the superclass so we don’t
        // actually need to call setMeasuredDimension() since that takes care
        // of that. However, the purpose with overriding onMeasure() was to
        // change the default behaviour and to do that we need to call
        // setMeasuredDimension() with our own values.
        setMeasuredDimension(
                size + getPaddingLeft() + getPaddingRight(),
                size/layoutRatio + getPaddingTop() + getPaddingBottom());
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        layoutWidth = newWidth;
        layoutHeight = newHeight;

        setUpBound();
        setUpPaint();
        invalidate();

    }



    private void setUpBound(){
        //setPadding(3,3,3,3);
        radus = layoutHeight/2f;
        if(extraRadus>radus-20){
            extraRadus = radus-20;
        }
        radus = -extraRadus+ radus;
        smallRadus = radus *10f/ 11f;
        clipPath = new Path();
        //RectF rectF = new RectF();
        top = getPaddingTop();
        bottom = getPaddingTop()+layoutHeight;
        clipPath.addCircle(getPaddingLeft()+radus+extraRadus,getPaddingTop()+radus+extraRadus,radus, Path.Direction.CW);
        clipPath.addRect(getPaddingLeft()+radus+extraRadus,getPaddingTop()+extraRadus,layoutWidth-getPaddingRight()-radus-extraRadus,getPaddingTop()+layoutHeight-extraRadus, Path.Direction.CW);
        clipPath.addCircle(layoutWidth-getPaddingRight()-radus-extraRadus,getPaddingTop()+radus+extraRadus,radus,Path.Direction.CW);
        radus = radus+extraRadus;
        minAllow = getPaddingLeft()+radus;
        maxAllow = layoutWidth-getPaddingRight()-radus;
        if(isOn)
            currentPoint =maxAllow;
        else
            currentPoint =minAllow;

        final float midPoint = ((maxAllow+minAllow)/2f);
        Canvas canvas ;

        if(buttonImg!=null){
            bitmap =Bitmap.createBitmap(2 * (int) radus, 2 * (int) radus, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            buttonImg.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            buttonImg.draw(canvas);
        }else {
            bitmap = Bitmap.createBitmap(2 * (int) radus, 2 * (int) radus, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawColor(buttonColor);
        }
        //set up bitmap left
        bitmapLeft = Bitmap.createBitmap(layoutWidth, layoutHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmapLeft);
        if(leftImg!=null){
            leftImg.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            leftImg.draw(canvas);
        }else {
            canvas.drawColor(leftColor);

        }
        bitmapRight =Bitmap.createBitmap(layoutWidth, layoutHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmapRight);
        if(rightImg!=null){
            rightImg.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            rightImg.draw(canvas);
        }else {
            canvas.drawColor(rightColor);
        }


        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.im);
       /* Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.drawleft);
        bitmapLeft =Bitmap.createBitmap(layoutWidth, layoutHeight, Bitmap.Config.ARGB_8888);
        canvass = new Canvas(bitmapLeft);
        drawable.setBounds(0, 0, canvass.getWidth(), canvass.getHeight());
        drawable.draw(canvass);
        drawable = ContextCompat.getDrawable(getContext(),R.drawable.drawright );
        bitmapRight =Bitmap.createBitmap(layoutWidth, layoutHeight, Bitmap.Config.ARGB_8888);
        canvass = new Canvas(bitmapRight);
        drawable.setBounds(0, 0, canvass.getWidth(), canvass.getHeight());
        drawable.draw(canvass);*/


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        circle.setShadowLayer(radus+30, /*minAllow*/0.0f, /*top+radus*/0.0f, /*0xFF000000*/shadowColor);
                        invalidate();
                        //Toast.makeText(this,"move down",Toast.LENGTH_SHORT).
                        //Log.i("motion","move down");
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(event.getX()>=minAllow && event.getX()<=maxAllow) {
                            currentPoint=event.getX();
                            invalidate();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        //Log.i("motion","move up");
                        circle.setShadowLayer(0f, /*minAllow*/0.0f, /*top+radus*/0.0f, /*0xFF000000*/Color.TRANSPARENT);
                        if(currentPoint>midPoint) {
                            move(true);
                            isOn = true;
                        }
                        else if(currentPoint<midPoint) {
                            move(false);
                            isOn = false;
                        }
                        else
                           move(isOn);
                        return true;
                }
                return true;
            }
        });


    }

    private  void setUpPaint(){
        circle = new Paint();
        circle.setColor(buttonColor);
        circle.setAntiAlias(true);
        circle.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, circle);

        innerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircle.setColor(buttonColor);
        innerCircle.setAntiAlias(true);
        innerCircle.setStyle(Paint.Style.FILL);


        rightRect = new Paint();
        rightRect.setColor(Color.RED);
        rightRect.setAntiAlias(true);
        rightRect.setStyle(Paint.Style.FILL);

        leftRect = new Paint();
        leftRect.setColor(Color.BLUE);
        leftRect.setAntiAlias(true);
        leftRect.setStyle(Paint.Style.FILL);

        background = new Paint();
        background.setColor(Color.GRAY);
        background.setAntiAlias(true);
        background.setStyle(Paint.Style.STROKE);
        background.setStrokeCap(Paint.Cap.BUTT);
        background.setStrokeWidth(20);
    }

    private void move(boolean on)  {
        if(on){
            while (currentPoint < maxAllow  ) {
                currentPoint++;
                invalidate();
            }
            setCheckCallback(true);

        }else {
            while (currentPoint > minAllow) {
                currentPoint--;
                invalidate();
            }
            setCheckCallback(false);
        }
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
        final float subRadus=radus-smallRadus;
        //canvas.drawRect(new RectF(currentPoint,getPaddingTop(),currentPoint+radus+layoutWidth,bottom),rightRect);
        //canvas.drawRect(new RectF(currentPoint-radus-layoutWidth,getPaddingTop(),currentPoint,bottom),leftRect);
        canvas.drawBitmap(bitmapRight,null,new RectF(currentPoint,getPaddingTop(),currentPoint+radus+layoutWidth,bottom),rightRect);
        canvas.drawBitmap(bitmapLeft,null,new RectF(currentPoint-radus-layoutWidth,getPaddingTop(),currentPoint,bottom),leftRect);

        final RectF rectF =new RectF(currentPoint-radus,getPaddingTop(),currentPoint+radus,bottom);
        canvas.drawArc(new RectF(currentPoint-radus,getPaddingTop(),currentPoint+radus,bottom),0f,360,true, circle);
        //final RectF rectF =new RectF(currentPoint-smallRadus,getPaddingTop()+subRadus,currentPoint+smallRadus,bottom-subRadus);
        //canvas.drawArc(rectF,0f,360,true, innerCircle);
        canvas.restore();
        //canvas.save();
        final Path path=new Path();
        path.addCircle(rectF.left+((rectF.right-rectF.left)/2f),top+radus,radus, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawArc(new RectF(currentPoint-radus,getPaddingTop(),currentPoint+radus,bottom),0f,360,true, innerCircle);
        canvas.drawBitmap(bitmap, null, rectF, null);
        //canvas.restore();
    }


    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener listener){
        mOnCheckedChangeListener = listener;
    }

    private void setCheckCallback(boolean isChecked){
        //check is state is changed or not

        if(isOn!=isChecked && mOnCheckedChangeListener!=null){
            mOnCheckedChangeListener.onCheckedChanged(this, isChecked);
        }
    }
    public void setCheck(boolean check){
        if(isOn!=check) {
            isOn=check;
            move(check);
        }
    }
    public interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param switchView The switch button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(SwitchView switchView, boolean isChecked);
    }
}
