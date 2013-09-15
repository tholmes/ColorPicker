package com.crogers.colorpicker;

import android.os.Bundle;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class QuickSelectGradientActivity extends ColorPickerBaseActivity {
    
	private OnEditorActionListener actionListener;
	private EditText m_hexCodeField;
	private OnClickListener m_clickListener;
	
    /* Other Class Members */
    private LinearLayout m_rootLinearLayout;
    
    TextView m_tv1, m_tv2, m_tv3, m_tv4, m_tv5, m_tv6, m_tv7, m_tv8, m_tv9, m_tv10, m_tv11;
    TextView m_hex1, m_hex2, m_hex3, m_hex4, m_hex5, m_hex6, m_hex7, m_hex8, m_hex9, m_hex10, m_hex11;
    TextView m_previewPane;
    Button m_applyPreviewButton;
    Button m_resetHexCodeButton;
    
    String m_startingHexCode;
    
    private boolean m_updateGradients = true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.quickselectgradientmain);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onInflateObjects() {
        
		m_previewPane = (TextView)findViewById(R.id.gradientPreviewPane);
		
		m_applyPreviewButton = (Button)findViewById(R.id.applyPreview);
		m_resetHexCodeButton = (Button)findViewById(R.id.resetHexCode);
		
		m_hexCodeField = (EditText)findViewById(R.id.currentColorHexCode);
        m_rootLinearLayout = (LinearLayout)findViewById(R.id.id_quickselectgradientmain_layout);
        
        m_tv1 = (TextView)findViewById(R.id.lumEx1);
        m_hex1 = (TextView)findViewById(R.id.hexLumEx1);
        
        m_tv2 = (TextView)findViewById(R.id.lumEx2);
        m_hex2 = (TextView)findViewById(R.id.hexLumEx2);
        
        m_tv3 = (TextView)findViewById(R.id.lumEx3);
        m_hex3 = (TextView)findViewById(R.id.hexLumEx3);
        
        m_tv4 = (TextView)findViewById(R.id.lumEx4);
        m_hex4 = (TextView)findViewById(R.id.hexLumEx4);
        
        m_tv5 = (TextView)findViewById(R.id.lumEx5);
        m_hex5 = (TextView)findViewById(R.id.hexLumEx5);
        
        m_tv6 = (TextView)findViewById(R.id.lumEx6);
        m_hex6 = (TextView)findViewById(R.id.hexLumEx6);
        
        m_tv7 = (TextView)findViewById(R.id.lumEx7);
        m_hex7 = (TextView)findViewById(R.id.hexLumEx7);
        
        m_tv8 = (TextView)findViewById(R.id.lumEx8);
        m_hex8 = (TextView)findViewById(R.id.hexLumEx8);
        
        m_tv9 = (TextView)findViewById(R.id.lumEx9);
        m_hex9 = (TextView)findViewById(R.id.hexLumEx9);
        
        m_tv10 = (TextView)findViewById(R.id.lumEx10);
        m_hex10 = (TextView)findViewById(R.id.hexLumEx10);
        
        m_tv11 = (TextView)findViewById(R.id.lumEx11);
        m_hex11 = (TextView)findViewById(R.id.hexLumEx11);
	}

	@Override
	protected void onCreateListeners() {
		
		m_clickListener = new OnClickListener() {
			public void onClick(View v) {
				int whichView = v.getId();
				
				switch(whichView) {
				    case R.id.applyPreview:
				    	getLuminance();
				    	break;
				    case R.id.resetHexCode:
				    	setRGB(m_startingHexCode);
				    	m_previewPane.setBackgroundColor(Color.rgb(getRedInt(), getGreenInt(), getBlueInt()));
				    	setHueHex(m_startingHexCode);
				    	m_hexCodeField.setText(m_startingHexCode);
				    	getLuminance();
				    	break;
					case R.id.lumEx1:
						gradientSelected(m_hex1.getText().toString());
						break;
					case R.id.lumEx2:
						gradientSelected(m_hex2.getText().toString());
						break;
					case R.id.lumEx3:
						gradientSelected(m_hex3.getText().toString());
						break;
					case R.id.lumEx4:
						gradientSelected(m_hex4.getText().toString());
						break;
					case R.id.lumEx5:
						gradientSelected(m_hex5.getText().toString());
						break;
					case R.id.lumEx6:
						gradientSelected(m_hex6.getText().toString());
						break;
					case R.id.lumEx7:
						gradientSelected(m_hex7.getText().toString());
						break;
					case R.id.lumEx8:
						gradientSelected(m_hex8.getText().toString());
						break;
					case R.id.lumEx9:
						gradientSelected(m_hex9.getText().toString());
						break;
					case R.id.lumEx10:
						gradientSelected(m_hex10.getText().toString());
						break;
					case R.id.lumEx11:
						gradientSelected(m_hex11.getText().toString());
						break;
				}
			}
		};
		
        // Gesture detection
		createSwipeDetection(QuickSelectGradientActivity.this);
		
		actionListener = new OnEditorActionListener() {			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Only capture event when enter key has been release, not just pressed down
				if(KeyEvent.ACTION_UP == event.getAction()){
					onEditHexCodeChange(v.getText().toString());
				}
				return true;
			}
		}; // end OnEditorActionListener	
	}
	
	@Override
	protected void onStartUpdateUI() {
		updateHexCodeField();
		m_startingHexCode = getHueHex().toString();
		getLuminance();
		m_previewPane.setBackgroundColor(Color.rgb(getRedInt(), getGreenInt(), getBlueInt()));
	}

	@Override
	protected void onSetListeners() {
		setSwipeDetectionToView(m_rootLinearLayout, null);		
		
		m_hexCodeField.setOnEditorActionListener(actionListener);
		
		m_applyPreviewButton.setOnClickListener(m_clickListener);
		m_resetHexCodeButton.setOnClickListener(m_clickListener);
		
		m_tv1.setOnClickListener(m_clickListener);
		m_tv2.setOnClickListener(m_clickListener);
		m_tv3.setOnClickListener(m_clickListener);
		m_tv4.setOnClickListener(m_clickListener);
		m_tv5.setOnClickListener(m_clickListener);
		m_tv6.setOnClickListener(m_clickListener);
		m_tv7.setOnClickListener(m_clickListener);
		m_tv8.setOnClickListener(m_clickListener);
		m_tv9.setOnClickListener(m_clickListener);
		m_tv10.setOnClickListener(m_clickListener);
		m_tv11.setOnClickListener(m_clickListener);
	}
	
	/**
	 * Use this update if the m_hexCodeField has changed
	 *   and you want the rest of the screen to reflect the changes.
	 * @param editText - the unsanitary text string from the m_hexCodeField.
	 */
	private void onEditHexCodeChange(String editText) {
		boolean valid = setHueHex(editText);
		// Either revert hexCodeField to what it was before the user entered an invalid string
		//  or update to sanitized string
		
		if (!valid) { 
			Toast.makeText(QuickSelectGradientActivity.this, R.string.invalidHexCodeFormatWarning, Toast.LENGTH_LONG).show();
			updateHexCodeField();
		}
		else {
			onColorChange(editText);
		}
		
		if (m_updateGradients)
			getLuminance();
	}
	
	/**
	 * use getSuggestions() to calculate the 10 different quick suggestions
	 */
	private void getLuminance() {
		// break up hex into ints for r, g, and b
		// calculate 5 steps in each r, g and b direction to 0
		// calculate 5 steps in each r, g, and b direction to 255
		// store result
		int currentRed = getRedInt();
		int currentBlue = getBlueInt();
		int currentGreen = getGreenInt();
		
		int stepsRedtoMax = -1;
		stepsRedtoMax = (255 - currentRed) / 5;
		
		int stepsBluetoMax = -1;
		stepsBluetoMax = (255 - currentBlue) / 5;
		
		int stepsGreentoMax = -1;
		stepsGreentoMax = (255 - currentGreen) / 5;
		
		int stepsRedtoMin = -1;
		stepsRedtoMin = currentRed / 5;
		
		int stepsBluetoMin = -1;
		stepsBluetoMin = currentBlue / 5;
		
		int stepsGreentoMin = -1;
		stepsGreentoMin = currentGreen / 5;
		
		m_tv1.setBackgroundColor(Color.WHITE);
		m_tv2.setBackgroundColor(Color.rgb(currentRed + 4 * stepsRedtoMax, currentGreen + 4 * stepsGreentoMax, currentBlue + 4 * stepsBluetoMax));
		m_tv3.setBackgroundColor(Color.rgb(currentRed + 3 * stepsRedtoMax, currentGreen + 3 * stepsGreentoMax, currentBlue + 3 * stepsBluetoMax));
		m_tv4.setBackgroundColor(Color.rgb(currentRed + 2 * stepsRedtoMax, currentGreen + 2 * stepsGreentoMax, currentBlue + 2 * stepsBluetoMax));
		m_tv5.setBackgroundColor(Color.rgb(currentRed + 1 * stepsRedtoMax, currentGreen + 1 * stepsGreentoMax, currentBlue + 1 * stepsBluetoMax));
		m_tv6.setBackgroundColor(Color.rgb(currentRed, currentGreen, currentBlue));
		m_tv7.setBackgroundColor (Color.rgb(currentRed - 1 * stepsRedtoMin, currentGreen - 1 * stepsGreentoMin, currentBlue - 1 * stepsBluetoMin));
		m_tv8.setBackgroundColor (Color.rgb(currentRed - 2 * stepsRedtoMin, currentGreen - 2 * stepsGreentoMin, currentBlue - 2 * stepsBluetoMin));
		m_tv9.setBackgroundColor (Color.rgb(currentRed - 3 * stepsRedtoMin, currentGreen - 3 * stepsGreentoMin, currentBlue - 3 * stepsBluetoMin));
		m_tv10.setBackgroundColor(Color.rgb(currentRed - 4 * stepsRedtoMin, currentGreen - 4 * stepsGreentoMin, currentBlue - 4 * stepsBluetoMin));
		m_tv11.setBackgroundColor(Color.BLACK);
		
		m_hex1.setText("#FFFFFF");
		
		m_hex2.setText(getHexStringFromRGB(currentRed + 4 * stepsRedtoMax, currentGreen + 4 * stepsGreentoMax, currentBlue + 4 * stepsBluetoMax));
		m_hex3.setText(getHexStringFromRGB(currentRed + 3 * stepsRedtoMax, currentGreen + 3 * stepsGreentoMax, currentBlue + 3 * stepsBluetoMax));
		m_hex4.setText(getHexStringFromRGB(currentRed + 2 * stepsRedtoMax, currentGreen + 2 * stepsGreentoMax, currentBlue + 2 * stepsBluetoMax));
		m_hex5.setText(getHexStringFromRGB(currentRed + 1 * stepsRedtoMax, currentGreen + 1 * stepsGreentoMax, currentBlue + 1 * stepsBluetoMax));
		m_hex6.setText(getHueHex());
		m_hex7.setText(getHexStringFromRGB(currentRed - 1 * stepsRedtoMin, currentGreen - 1 * stepsGreentoMin, currentBlue - 1 * stepsBluetoMin));
		m_hex8.setText(getHexStringFromRGB(currentRed - 2 * stepsRedtoMin, currentGreen - 2 * stepsGreentoMin, currentBlue - 2 * stepsBluetoMin));
		m_hex9.setText(getHexStringFromRGB(currentRed - 3 * stepsRedtoMin, currentGreen - 3 * stepsGreentoMin, currentBlue - 3 * stepsBluetoMin));
		m_hex10.setText(getHexStringFromRGB(currentRed - 4 * stepsRedtoMin, currentGreen - 4 * stepsGreentoMin, currentBlue - 4 * stepsBluetoMin));
		
		m_hex11.setText("#000000");
		
		m_hex1.setBackgroundColor(Color.BLACK);
		m_hex2.setBackgroundColor(Color.BLACK);
		m_hex3.setBackgroundColor(Color.BLACK);
		m_hex4.setBackgroundColor(Color.BLACK);
		m_hex5.setBackgroundColor(Color.BLACK);
		m_hex6.setBackgroundColor(Color.BLACK);
		m_hex7.setBackgroundColor(Color.BLACK);
		m_hex8.setBackgroundColor(Color.BLACK);
		m_hex9.setBackgroundColor(Color.BLACK);
		m_hex10.setBackgroundColor(Color.BLACK);
		m_hex11.setBackgroundColor(Color.BLACK);
	}
	
	private void gradientSelected(String newHexCode) {
		Toast.makeText(QuickSelectGradientActivity.this, "Selected: " + newHexCode, Toast.LENGTH_SHORT).show();
		m_updateGradients = false;
		m_hexCodeField.setText(newHexCode);
		setHueHex(newHexCode);
		m_previewPane.setBackgroundColor(Color.rgb(getRedInt(), getGreenInt(), getBlueInt()));
		m_updateGradients = true;
	}
	
	/**
	 * If Hexcode edit text is changing, use this function - it will update the other views in the activity
	 * @param hexCode - String from the Hexcode EditText
	 */
	private void onColorChange(String hexCode) {
		setHueHex(hexCode);
	}
	
	private void updateHexCodeField() {
		m_hexCodeField.setText(getHueHex());
	}
}
