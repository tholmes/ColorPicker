package com.crogers.colorpicker;

import com.crogers.colorpicker.ColorPickerView.OnColorChangedListener;
import com.crogers.colorpicker.ColorPickerView.OnSetClipboardListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//public class ColorPickerDialog extends Dialog {

public class SquareGradientView extends View {

	private OnColorChangedListener mColorListener;
	private OnSetClipboardListener mClipListener;
	private int mInitialColor, mDefaultColor, mCurrentColor;
	//private String mKey; - i think we can remove this, not used
    private Paint mPaint;
    private float mCurrentHue = 0;
    private int mCurrentX = 0, mCurrentY = 0;
    private final int[] mHueBarColors = new int[258];
    private int[] mMainColors = new int[65536];

    private Context m_cxt;
    
	public interface OnColorChangedListener {
		void colorChanged(int red, int green, int blue);
	}
	
	public interface OnSetClipboardListener {
		CharSequence getText();
	}
    
    /**
     * Constructor - Used for creating View through XML
     * @param cxt - the context of the caller
     * @param attrs - AttributeSet of XML parameters
     */
    public SquareGradientView(Context cxt, AttributeSet attrs) {
    	this(cxt, attrs, Color.TRANSPARENT, null, Color.TRANSPARENT);
    }

    /**
     * ctor for when you create this object through code
     * @param c
     * @param attrs
     * @param color
     * @param defaultColor
     * @param listener
     */
    
    SquareGradientView(final Context c, AttributeSet attrs, int color, OnColorChangedListener colorListener, int defaultColor) {
        super(c, attrs);
        
		// if colorChangedListener is null, set a default one
        mColorListener = new OnColorChangedListener() {
			
			public void colorChanged(int red, int green, int blue) {
				Toast.makeText(c, "You forgot to set the OnColorChanged Listener!!",
						Toast.LENGTH_LONG).show();
			}
		};
		
		m_cxt = c;
	
		mColorListener = colorListener;
        mDefaultColor = defaultColor;
        mCurrentColor = color;

        updateMainColors();

        // Initializes the Paint that will draw the View
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

     // Update the main field colors depending on the current selected hue
    private void updateMainColors() {
        int mainColor = mCurrentColor; // todo: this was a getMainColor() function
        int index = 0;
        int[] topColors = new int[256];
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                if (y == 0) {
                    mMainColors[index] = Color.rgb(
                            255 - (255 - Color.red(mainColor)) * x / 255,
                            255 - (255 - Color.green(mainColor)) * x / 255,
                            255 - (255 - Color.blue(mainColor)) * x / 255);
                    topColors[x] = mMainColors[index];
                } else
                    mMainColors[index] = Color.rgb(
                            (255 - y) * Color.red(topColors[x]) / 255,
                            (255 - y) * Color.green(topColors[x]) / 255,
                            (255 - y) * Color.blue(topColors[x]) / 255);
                index++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Display the main field colors using LinearGradient
        for (int x = 0; x < 256; x++) {
            int[] colors = new int[2];
            colors[0] = mMainColors[x];
            colors[1] = Color.BLACK;
            Shader shader = new LinearGradient(0, 50, 0, 306, colors, null,
                    Shader.TileMode.REPEAT);
            mPaint.setShader(shader);
            canvas.drawLine(x + 10, 50, x + 10, 306, mPaint);
        }
        mPaint.setShader(null);

        // Display the circle around the currently selected color in the
        // main field
        if (mCurrentX != 0 && mCurrentY != 0) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
            canvas.drawCircle(mCurrentX, mCurrentY, 10, mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(276, 366);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return true;
        float x = event.getX();
        float y = event.getY();

        // If the touch event is located in the main field
        if (x > 10 && x < 266 && y > 50 && y < 306) {
            mCurrentX = (int) x;
            mCurrentY = (int) y;
            int transX = mCurrentX - 10;
            int transY = mCurrentY - 60;
            int index = 256 * (transY - 1) + transX;
            if (index > 0 && index < mMainColors.length && mClipListener != null) {
            	
 				// Gets a handle to the clipboard service.
 				ClipboardManager clipboard = (ClipboardManager)m_cxt.getSystemService(Context.CLIPBOARD_SERVICE);
 				// Sets clipboard text
 				CharSequence text = mClipListener.getText();
 				clipboard.setText(text);

                // Update the current color
                mCurrentColor = mMainColors[index];
                int red   = Color.red(mCurrentColor);
                int green = Color.green(mCurrentColor);
                int blue  = Color.blue(mCurrentColor);
                
                mColorListener.colorChanged(red, green, blue);                

                invalidate();
            }
        }


        return true;
    }
    
	void setOnColorChangedListener(OnColorChangedListener listener) {
		mColorListener = listener;
    }
	
	void setOnSetClipboardListener(OnSetClipboardListener listener) {
		mClipListener = listener;
	}
    
    public void updateColor(int red, int green, int blue) {
		mCurrentColor = Color.rgb(red, green, blue);
		updateMainColors();
		invalidate();
	}
}