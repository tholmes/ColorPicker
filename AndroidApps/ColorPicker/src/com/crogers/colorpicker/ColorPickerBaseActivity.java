package com.crogers.colorpicker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class ColorPickerBaseActivity extends Activity {

	/* Constants */
	final private String LAST_KNOWN_RGB_VALUES_FILE = "LastKnownRGBValuesFile";
	final private String KEY_HUE_VAL = "key_hue_val";
    
    /* Helper Objects */
    private RGBColors m_colorCombo;
	
    protected GestureDetector m_gestureDetector;
    protected View.OnTouchListener m_gestureListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        
        m_colorCombo = new RGBColors();
        
        /* Initialize-Inflate class objects, variables, and widgets */
        onInflateObjects();
        
        /* Create-Implement widget listeners */
        onCreateListeners();
        
        /* Set-Add listeners to widgets */
        onSetListeners();
    	
    }
    
    @Override
	protected void onStart() {	
		
        super.onStart();
        
        int[] rgb = retrieveRGBValues();

        m_colorCombo.setRGB(rgb[0], rgb[1], rgb[2]);
        
        onStartUpdateUI();
        
	} // end onResume
    
    @Override
	protected void onPause() {
		
		super.onPause();
		/* Persist Last Known RGB Values */
		saveRGBvalues();
    	
	} // end onPause
    
    /* Pure Virtual Functions */
    protected abstract void onInflateObjects();
    protected abstract void onCreateListeners();
    protected abstract void onSetListeners();
    protected abstract void onStartUpdateUI();
	
    /**
     * Swipe Detection initialization. This function MUST be called before setting the swipe
     * 	detection on any View.
     * @param cxt - The context of activity
     */
	protected void createSwipeDetection(Context cxt){

        m_gestureDetector = new GestureDetector(new SwipeDetector(cxt));
        m_gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return m_gestureDetector.onTouchEvent(event);
            }
        };
        
	}
	
	/**
	 * Used for setting the swiping action to any View
	 * @param view - the view you want to set the swipe detection to
	 * @param listener - an onClickListener interface MUST be set for the view for which you
	 * 		want to set the swipe detection. If you don't want an onClickListener associated
	 * 		with the View, you may set this parameter to NULL.
	 */
	protected void setSwipeDetectionToView(View view, OnClickListener listener){

        // TRICKY/KLUDGE: You MUST set on OnClickListener, in order for swipe to work on a "View" -
		//  However, the listener does not have to do anything. It can be NULL, if you so desire.
        //  Another note is that setOnTouchListener(gestureListener) must be set for EVERY "View" that you
        //  want the swipe to work on. I don't encourage having the gestureListener be set to any other Views
        //  than the root layout because this could potentially frustrate users. If the user does a swipe on
        //  an increment button, both the button's onClick and onSwipeDetection are going to get called,
		//  thus changing/saving the color value AND then swiping to the next/previous activity.
		
		if(m_gestureListener != null){ // if m_gestureListener is NULL, make sure you're calling createSwipeDetections first!
			view.setOnClickListener(listener);
			view.setOnTouchListener(m_gestureListener);
		}
		
	}
	
	protected boolean setHueHex(String editText) {
		return m_colorCombo.setHueHex(editText);
	}
	
	protected CharSequence getHueHex() {
		return m_colorCombo.getHueHex();
	}
	
	protected CharSequence getHexStringFromRGB(int r, int g, int b) {		
		String red   = Integer.toHexString(r).toUpperCase();
		String green = Integer.toHexString(g).toUpperCase();
		String blue  = Integer.toHexString(b).toUpperCase();
		
		if (r <= 0xF)
			red = "0" + Integer.toHexString(r).toUpperCase();
		if (g <= 0xF)
			green = "0" + Integer.toHexString(g).toUpperCase();
		if (b <= 0xF)
			blue = "0" + Integer.toHexString(b).toUpperCase();
		
		CharSequence retVal = "#" + red + green +  blue;
		
		return retVal;		
	}
	
	protected int getRedInt() {
		return m_colorCombo.getRedInt();
	}
	
	protected int getGreenInt() {
		return m_colorCombo.getGreenInt();
	}
	
	protected int getBlueInt() {
		return m_colorCombo.getBlueInt();
	}
	
	protected void setRGB(String hexCode) {
		String r = hexCode.substring(1, 3);
		String b = hexCode.substring(3, 5);
		String g = hexCode.substring(5, 7);
		setRGB(Long.decode("0x" + r).intValue(), Long.decode("0x" + b).intValue(), Long.decode("0x" + g).intValue());
	}
	
	protected void setRGB(String red, String green, String blue){
		m_colorCombo.setRGB(red, green, blue);
	}
	
	protected void setRGB(int red, int green, int blue){
		m_colorCombo.setRGB(red, green, blue);
	}

	protected void saveRGBvalues() {
        
		// Calculate single INT for RGB values;
        int red = m_colorCombo.getRedInt();
        int green = m_colorCombo.getGreenInt();
        int blue = m_colorCombo.getBlueInt();
        int hue = Color.rgb(red, green, blue);
		
		// Obtain the application's SharedPreferences object
        SharedPreferences initialValuesPref = this.getSharedPreferences(LAST_KNOWN_RGB_VALUES_FILE, MODE_PRIVATE);
        // Grab the SharedPreferences Editor for changing/adding values
        Editor editor = initialValuesPref.edit();
        
        editor.putInt(KEY_HUE_VAL, hue);
        
        // IMPOROTANT: Don't forget to commit new values!
        editor.commit();
        
	} // end persistRGBValues
	
	private int[] retrieveRGBValues() {
		
		// Get application's "SharedPreferences" Object
        SharedPreferences initialValuesPref = this.getSharedPreferences(LAST_KNOWN_RGB_VALUES_FILE, MODE_PRIVATE);
        // Define a default value in case the key does not exist
        int defValue = 0;

        // Retrieve values
        int hue = initialValuesPref.getInt(KEY_HUE_VAL, defValue);
        
        int[] rgb = new int[3];
        rgb[0] = Color.red(hue);
        rgb[1] = Color.green(hue);
        rgb[2] = Color.blue(hue);
        
        return rgb;
        
	} // end retrieveRGBValues

}
