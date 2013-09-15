package com.crogers.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ColorPickerView extends View {

	private Paint m_paint;
    private Paint m_centerPaint;  
    private int[] m_colors;  
    private OnColorChangedListener m_colorChangedListener;
    private OnSetClipboardListener m_setClipboardListener;

	private boolean m_trackingCenter;  
    private boolean m_highlightCenter;
    
    /* Constants */
    private static final int CENTER_X = 100;
    private static final int CENTER_Y = 100;  
    private static final int CENTER_RADIUS = 32;
    private static final int CENTER_STROKE_WIDTH = 3;
    private static final int COLOR_WHEEL_STROKE_WIDTH = 32;
    private static final float PI = 3.1415926f;
    
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
    public ColorPickerView(Context cxt, AttributeSet attrs){
    	this(cxt, attrs, null, Color.TRANSPARENT, Color.TRANSPARENT);
    }
    
    /**
     * Constructor - Used for creating View through code
     * @param cxt - the context of the caller
     * @param colorChangedListener - a listener call-back
     * @param backgroundColor - for the background
     * @param initialCenterColor - initial color for center of wheel
     */
    public ColorPickerView(final Context cxt, AttributeSet attrs,
    					   OnColorChangedListener colorChangedListener,
    					   int backgroundColor, int initialCenterColor) {
    	super(cxt, attrs);
    	m_cxt = cxt;
    	Log.d("cpv ctor 2", "called");
    	m_colorChangedListener = colorChangedListener;
    	if(m_colorChangedListener == null){
    		// if colorChangedListener is null, set a default one
    		m_colorChangedListener = new OnColorChangedListener() {
				
				public void colorChanged(int red, int green, int blue) {
					Toast.makeText(cxt, "You forgot to set the OnColorChanged Listener!!",
							Toast.LENGTH_LONG).show();
				}
			};
    	}
    	m_colors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
    							0xFFFFFF00, 0xFFFF0000 };
    	Shader s = new SweepGradient(0, 0, m_colors, null);
    	m_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	m_paint.setShader(s);
    	m_paint.setStyle(Paint.Style.STROKE);
    	m_paint.setStrokeWidth(COLOR_WHEEL_STROKE_WIDTH);
    	m_centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	m_centerPaint.setColor(initialCenterColor);
        m_centerPaint.setStrokeWidth(CENTER_STROKE_WIDTH);
        setBackgroundColor(backgroundColor);
	}
    
    @Override
    protected void onDraw(Canvas canvas) {
    	Log.d("DEBUG!", "0");
    	float r = CENTER_X - m_paint.getStrokeWidth()*0.5f;
    	// canvas.translate() is going to move the color wheel to a desired location on the view
    	canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() /2);
    	canvas.drawOval(new RectF(-r, -r, r, r), m_paint);
    	canvas.drawCircle(0, 0, CENTER_RADIUS, m_centerPaint);
    	
    	if(m_trackingCenter) {
    		int c = m_centerPaint.getColor();
    	
    		m_centerPaint.setStyle(Paint.Style.STROKE);
        
    		if(m_highlightCenter)
    			m_centerPaint.setAlpha(0xFF);
    		else
    			m_centerPaint.setAlpha(0x80);
    	
    		canvas.drawCircle(0, 0, CENTER_RADIUS + m_centerPaint.getStrokeWidth(), m_centerPaint);
    		
    		m_centerPaint.setStyle(Paint.Style.FILL);  
    		m_centerPaint.setColor(c);  
    	}
     }  
     
     @Override  
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
    	 setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);  
     }
      
     private int floatToByte(float x) {  
    	 int n = java.lang.Math.round(x);  
    	 return n;  
     }
     
     private int pinToByte(int n) {  
    	 if (n < 0)  
    		 n = 0;
    	 else if (n > 255)
    		 n = 255;
    	 return n;  
     }
     
     private int ave(int s, int d, float p) {  
    	 return s + java.lang.Math.round(p * (d - s));  
     }  
     
     private int interpColor(int colors[], float unit) {  
    	 if (unit <= 0)  
    		 return colors[0];

    	 if (unit >= 1)
    		 return colors[colors.length - 1];
       
    	 float p = unit * (colors.length - 1);  
    	 int i = (int)p;  
    	 p -= i;  
       
    	 // now p is just the fractional part [0...1) and i is the index  
    	 int c0 = colors[i];  
    	 int c1 = colors[i+1];  
    	 int a = ave(Color.alpha(c0), Color.alpha(c1), p);  
    	 int r = ave(Color.red(c0), Color.red(c1), p);  
    	 int g = ave(Color.green(c0), Color.green(c1), p);  
    	 int b = ave(Color.blue(c0), Color.blue(c1), p);  
    	 return Color.argb(a, r, g, b);  
     }  
     
     // TODO: Figure out if we can use this function
     @SuppressWarnings("unused")
	private int rotateColor(int color, float rad) {  
    	 float deg = rad * 180 / PI;  
    	 int r = Color.red(color);  
    	 int g = Color.green(color);  
    	 int b = Color.blue(color);  
    	 ColorMatrix cm = new ColorMatrix();  
    	 ColorMatrix cm_tmp = new ColorMatrix();  
    	 cm.setRGB2YUV();
    	 cm_tmp.setRotate(0, deg);  
    	 cm.postConcat(cm_tmp);  
    	 cm_tmp.setYUV2RGB();  
    	 cm.postConcat(cm_tmp);  
    	 final float[] a = cm.getArray();  
    	 int ir = floatToByte(a[0] * r + a[1] * g + a[2] * b);  
    	 int ig = floatToByte(a[5] * r + a[6] * g + a[7] * b);  
    	 int ib = floatToByte(a[10] * r + a[11] * g + a[12] * b);  
    	 return Color.argb(Color.alpha(color), pinToByte(ir), pinToByte(ig), pinToByte(ib));  
     }  

     @Override
     public boolean onTouchEvent(MotionEvent event) {
    	 //float x = event.getX() - CENTER_X;
    	 float x = event.getX() - (getMeasuredWidth() / 2);
    	 //float y = event.getY() - CENTER_Y;
    	 float y = event.getY() - (getMeasuredHeight() / 2);
    	 
    	 boolean inCenter = android.util.FloatMath.sqrt(x*x + y*y) <= CENTER_RADIUS;  
    	 switch (event.getAction()) {  
         	case MotionEvent.ACTION_DOWN: // When finger is down on screen
         		m_trackingCenter = inCenter;  
         		if (inCenter) {  
         			m_highlightCenter = true;  
         			invalidate();  
         			break;  
         		}  
         	case MotionEvent.ACTION_MOVE: // While finger is down and moving on the screen
         		if (m_trackingCenter) {  
         			if (m_highlightCenter != inCenter) {  
         				m_highlightCenter = inCenter;  
         				invalidate();  
         			}
         		}
         		else {  
         			float angle = (float)java.lang.Math.atan2(y, x);  
         			// need to turn angle [-PI ... PI] into unit [0....1]  
         			float unit = angle/(2*PI);  
         			if (unit < 0) {  
         				unit += 1;  
         			}  
         			m_centerPaint.setColor(interpColor(m_colors, unit));
         			if (m_colorChangedListener != null) {
         				int rgb   = m_centerPaint.getColor();
         				int red   = Color.red(rgb);
         				int green = Color.green(rgb);
         				int blue  = Color.blue(rgb);
         				m_colorChangedListener.colorChanged(red, green, blue);
         			}
         			invalidate();
         		}  
         		break;  
         	case MotionEvent.ACTION_UP: // When finger is released from screen
         		if (m_trackingCenter) {
         			
         			if (inCenter && m_setClipboardListener != null){
         				// Gets a handle to the clipboard service.
         				ClipboardManager clipboard = (ClipboardManager)m_cxt.getSystemService(Context.CLIPBOARD_SERVICE);
         				// Sets clipboard text
         				CharSequence text = m_setClipboardListener.getText();
         				clipboard.setText(text);
         				Toast.makeText(getContext(), "Hexcode copied to clipboard!", Toast.LENGTH_LONG).show();
         			}
         			
         			m_trackingCenter = false;  // so we draw w/o halo  
         			invalidate();  
         		}  
         		break;  
    	 } // end switch 
    	 return true;  
    	 
     } // end onTouchEvent

	void setOnColorChangedListener(OnColorChangedListener listener){
    	 m_colorChangedListener = listener;
     }
	
	void setOnSetClipboardListener(OnSetClipboardListener listener){
		m_setClipboardListener = listener;
	}

	public void updateColorView(int red, int green, int blue) {
		int rgb = Color.rgb(red, green, blue);
		m_centerPaint.setColor(rgb);
		invalidate();
	}
     
   } // end class ColorPickerView