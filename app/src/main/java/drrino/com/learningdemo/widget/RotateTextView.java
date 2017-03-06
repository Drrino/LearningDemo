package drrino.com.learningdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dell1 on 2017/3/2.
 */

public class RotateTextView extends TextView {
    public RotateTextView(Context context) {
        super(context);
    }


    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override protected void onDraw(Canvas canvas) {
        canvas.rotate(-15, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
}
