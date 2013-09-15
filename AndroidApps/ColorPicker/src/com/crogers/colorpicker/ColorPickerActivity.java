package com.crogers.colorpicker;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class ColorPickerActivity extends ColorPickerBaseActivity {
	
	/* Listeners */	
	private OnClickListener m_buttonListener;
    private OnEditorActionListener actionListener;
    
    /* Buttons */
    private Button m_redIncrementButton1;
    private Button m_redDecrementButton1;
    private Button m_greenIncrementButton1;
    private Button m_greenDecrementButton1;
    private Button m_blueIncrementButton1;
    private Button m_blueDecrementButton1;

    private Button m_redIncrementButton10;
    private Button m_redDecrementButton10;
    private Button m_greenIncrementButton10;
    private Button m_greenDecrementButton10;
    private Button m_blueIncrementButton10;
    private Button m_blueDecrementButton10;
    
    /* EditTexts */
    private EditText m_hexCodeField;
    private EditText m_redEditText;
    private EditText m_greenEditText;
    private EditText m_blueEditText;
    
    /* Other Class Members */
    private LinearLayout m_rootLinearLayout;
	
    private ColorPickerView m_cpv;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        setContentView(R.layout.colorpickermain);
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void onInflateObjects() {
		
        m_cpv = (ColorPickerView)findViewById(R.id.cpv);
        
        m_hexCodeField = (EditText)findViewById(R.id.currentColorHexCode);  
        
        m_redIncrementButton1 = (Button)findViewById(R.id.redIncrementButton1);
        m_redDecrementButton1 = (Button)findViewById(R.id.redDecrementButton1);
        m_greenIncrementButton1 = (Button)findViewById(R.id.greenIncrementButton1);
        m_greenDecrementButton1 = (Button)findViewById(R.id.greenDecrementButton1);
        m_blueIncrementButton1 = (Button)findViewById(R.id.blueIncrementButton1);
        m_blueDecrementButton1 = (Button)findViewById(R.id.blueDecrementButton1);  
        
        m_redIncrementButton10 = (Button)findViewById(R.id.redIncrementButton10);
        m_redDecrementButton10 = (Button)findViewById(R.id.redDecrementButton10);
        m_greenIncrementButton10 = (Button)findViewById(R.id.greenIncrementButton10);
        m_greenDecrementButton10 = (Button)findViewById(R.id.greenDecrementButton10);
        m_blueIncrementButton10 = (Button)findViewById(R.id.blueIncrementButton10);
        m_blueDecrementButton10 = (Button)findViewById(R.id.blueDecrementButton10);
        
        m_redEditText = (EditText)findViewById(R.id.redEditText);
        m_greenEditText = (EditText)findViewById(R.id.greenEditText);
        m_blueEditText = (EditText)findViewById(R.id.blueEditText);
        
        m_rootLinearLayout = (LinearLayout)findViewById(R.id.id_colorpickermain_layout);
	} // end inflateObjects
	

	protected void setRGBPickers(int red, int green, int blue) {
		m_redEditText.setText(String.valueOf(red));
		m_greenEditText.setText(String.valueOf(green));
		m_blueEditText.setText(String.valueOf(blue));
	}

	@Override
	protected void onCreateListeners() {
		m_buttonListener = new OnClickListener() {
			public void onClick(View v) {
				int buttonID = v.getId();
				
				switch(buttonID){
					case R.id.redIncrementButton1:
						onRGBpickerChange(m_redEditText, 1);
						break;
					case R.id.greenIncrementButton1:
						onRGBpickerChange(m_greenEditText, 1);
						break;
					case R.id.blueIncrementButton1:
						onRGBpickerChange(m_blueEditText, 1);
						break;
					case R.id.redDecrementButton1:
						onRGBpickerChange(m_redEditText, -1);
						break;
					case R.id.greenDecrementButton1:
						onRGBpickerChange(m_greenEditText, -1);
						break;
					case R.id.blueDecrementButton1:
						onRGBpickerChange(m_blueEditText, -1);
						break;
					case R.id.redIncrementButton10:
						onRGBpickerChange(m_redEditText, 10);
						break;
					case R.id.greenIncrementButton10:
						onRGBpickerChange(m_greenEditText, 10);
						break;
					case R.id.blueIncrementButton10:
						onRGBpickerChange(m_blueEditText, 10);
						break;
					case R.id.redDecrementButton10:
						onRGBpickerChange(m_redEditText, -10);
						break;
					case R.id.greenDecrementButton10:
						onRGBpickerChange(m_greenEditText, -10);
						break;
					case R.id.blueDecrementButton10:
						onRGBpickerChange(m_blueEditText, -10);
						break;
				}
			}
		}; // end OnClickListener
		
		actionListener = new OnEditorActionListener() {			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Only capture event when enter key has been release, not just pressed down
				if(KeyEvent.ACTION_UP == event.getAction()){
					onEditHexCodeChange(v.getText().toString());
				}
				return true;
			}
		}; // end OnEditorActionListener
		
        // Gesture detection
        createSwipeDetection(ColorPickerActivity.this);
        
	} // end createListeners

	@Override
	protected void onSetListeners() {
        m_cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {			
			
        	public void colorChanged(int red, int green, int blue) {
        		onColorChange(red, green, blue);
			}
			
		});
        
        m_cpv.setOnSetClipboardListener(new ColorPickerView.OnSetClipboardListener() {
			
			public CharSequence getText() {
				return m_hexCodeField.getText();
			}
		});
        
        setSwipeDetectionToView(m_rootLinearLayout, null);
        
        m_redIncrementButton1.setOnClickListener(m_buttonListener);
        m_redDecrementButton1.setOnClickListener(m_buttonListener);
        m_greenIncrementButton1.setOnClickListener(m_buttonListener);
        m_greenDecrementButton1.setOnClickListener(m_buttonListener);
        m_blueIncrementButton1.setOnClickListener(m_buttonListener);
        m_blueDecrementButton1.setOnClickListener(m_buttonListener);
        
        m_redIncrementButton10.setOnClickListener(m_buttonListener);
        m_redDecrementButton10.setOnClickListener(m_buttonListener);
        m_greenIncrementButton10.setOnClickListener(m_buttonListener);
        m_greenDecrementButton10.setOnClickListener(m_buttonListener);
        m_blueIncrementButton10.setOnClickListener(m_buttonListener);
        m_blueDecrementButton10.setOnClickListener(m_buttonListener);
        
        m_hexCodeField.setOnEditorActionListener(actionListener);
	} // end setListeners

	@Override
	protected void onStartUpdateUI() {
		updateColorView();
		updateHexCodeField();
		updateRGBpickers();
	}
	
	private void onRGBpickerChange(EditText editText, int incDec) {
		int value = Integer.valueOf(editText.getText().toString());
		value += incDec;
		if(value <= 255 && value >= 0)
		{
			editText.setText(String.valueOf(value));
			String red = m_redEditText.getText().toString();
			String green = m_greenEditText.getText().toString();
			String blue = m_blueEditText.getText().toString();
			onColorChange(red, green, blue);
		}
	}
	
	/**
	 * If ColorPickerView is changing, use this function - it will update the other views in the activity
	 * @param red - integer from the red component of the ColorPickerView
	 * @param green - integer from the green component of the ColorPickerView
	 * @param blue - integer from the blue component of the ColorPickerView
	 */
	private void onColorChange(int red, int green, int blue){
		setRGB(String.valueOf(red), String.valueOf(green), String.valueOf(blue));
		updateHexCodeField();
		updateRGBpickers();
	}
	
	/**
	 * If Hexcode edit text is changing, use this function - it will update the other views in the activity
	 * @param hexCode - String from the Hexcode EditText
	 */
	private void onColorChange(String hexCode){
		setHueHex(hexCode);
		updateColorView();
		updateRGBpickers();
	}
	
	/**
	 * If Color Pickers are changing, use this function - it will update the other views in the activity
	 * @param red - red String from red color picker
	 * @param green - green String from green color picker
	 * @param blue - blue String from blue color picker
	 */
	private void onColorChange(String red, String green, String blue) {
		setRGB(red, green, blue);
		updateColorView();
		updateHexCodeField();
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
			Toast.makeText(ColorPickerActivity.this, R.string.invalidHexCodeFormatWarning, Toast.LENGTH_LONG).show();
			updateHexCodeField();
		}
		else {
			onColorChange(editText);
		}
	}

	private void updateColorView() {
		m_cpv.updateColorView(getRedInt(), getGreenInt(), getBlueInt());
	}
	
	private void updateHexCodeField() {
		
		m_hexCodeField.setText(getHueHex());
		
	}

	private void updateRGBpickers() {

        m_redEditText.setText(String.valueOf(getRedInt()));
		m_greenEditText.setText(String.valueOf(getGreenInt()));
		m_blueEditText.setText(String.valueOf(getBlueInt()));
		
	}
}
