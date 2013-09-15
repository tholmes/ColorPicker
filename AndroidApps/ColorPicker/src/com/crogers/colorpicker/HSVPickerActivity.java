package com.crogers.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class HSVPickerActivity extends ColorPickerBaseActivity {
    
	/* Listeners */
	private OnEditorActionListener m_actionListener;
    
    /* Other Class Members */
    private LinearLayout m_rootLinearLayout;
	private SquareGradientView m_sqv;
	private EditText m_hexCodeField;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.hsvpickermain);
        super.onCreate(savedInstanceState);
        
    } // end onCreate

	@Override
	protected void onInflateObjects() {
		
		m_sqv = (SquareGradientView)findViewById(R.id.sqv);
		
	    m_rootLinearLayout = (LinearLayout)findViewById(R.id.id_hsvpickermain_layout);
	    
	    m_hexCodeField = (EditText)findViewById(R.id.sqvHexCodeField);
	    
	} // end inflateObjects

	@Override
	protected void onCreateListeners() {
 
		m_actionListener = new OnEditorActionListener() {			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Only capture event when enter key has been release, not just pressed down
				if (KeyEvent.ACTION_UP == event.getAction()) {
					Toast.makeText(HSVPickerActivity.this, "that tickles", Toast.LENGTH_SHORT).show();
					onEditHexCodeChange(v.getText().toString());
				}
				return true;
			}
		}; // end OnEditorActionListener
		
        // Gesture detection
        createSwipeDetection(HSVPickerActivity.this);
		
	} // end initializeListeners

	@Override
	protected void onSetListeners() {
       
		m_sqv.setOnColorChangedListener(new SquareGradientView.OnColorChangedListener() {			
			
        	public void colorChanged(int red, int green, int blue) {
        		onColorChange(red, green, blue);
			}
			
		});
		
		m_sqv.setOnSetClipboardListener(new SquareGradientView.OnSetClipboardListener() {
			
			public CharSequence getText() {
				return m_hexCodeField.getText();
			}
		});		
		
		m_hexCodeField.setOnEditorActionListener(m_actionListener);
		
        setSwipeDetectionToView(m_rootLinearLayout, null);
        
	} // end setListeners

	@Override
	protected void onStartUpdateUI() {
		updateHexCodeField();
		updateSquareGradientView();
	}
	
	/**
	 * Use this update if the m_hexCodeField has changed
	 *   and you want the rest of the screen to reflect the changes.
	 * @param editText - the unsanitary text string from the m_hexCodeField.
	 */
	private void onEditHexCodeChange(String editText){
		boolean valid = setHueHex(editText);
		// Either revert hexCodeField to what it was before the user entered an invalid string
		//  or update to sanitized string
		
		if (!valid) { 
			Toast.makeText(HSVPickerActivity.this, R.string.invalidHexCodeFormatWarning, Toast.LENGTH_LONG).show();
			updateHexCodeField();
		}
		else {
			onColorChange(editText);
		}
	}
	
	/**
	 * If SquareGradientView is changing, use this function - it will update the other views in the activity
	 * @param red - integer from the red component of the ColorPickerView
	 * @param green - integer from the green component of the ColorPickerView
	 * @param blue - integer from the blue component of the ColorPickerView
	 */
	private void onColorChange(int red, int green, int blue){
		setRGB(String.valueOf(red), String.valueOf(green), String.valueOf(blue));
		updateHexCodeField();
	}
	
	/**
	 * If Hexcode edit text is changing, use this function - it will update the other views in the activity
	 * @param hexCode - String from the Hexcode EditText
	 */
	private void onColorChange(String hexCode){
		setHueHex(hexCode);
		updateSquareGradientView();
	}
	
	/**
	 * If Color Pickers are changing, use this function - it will update the other views in the activity
	 * @param red - red String from red color picker
	 * @param green - green String from green color picker
	 * @param blue - blue String from blue color picker
	 */
	private void onColorChange(String red, String green, String blue) {
		setRGB(red, green, blue);
		updateSquareGradientView();
		updateHexCodeField();
	}
	
	private void updateHexCodeField() {
		
		m_hexCodeField.setText(getHueHex());
		
	}
	
	private void updateSquareGradientView() {
		m_sqv.updateColor(getRedInt(), getGreenInt(), getBlueInt());
	}
	
	
} // end class ColorPickerActivity