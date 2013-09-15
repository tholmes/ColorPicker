package com.crogers.colorpicker;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorInfoActivity extends ColorPickerBaseActivity {
	
	/* TextViews */
	private TextView m_H_editText;		private int m_H_value;
	private TextView m_S_editText;		private int m_S_value;
	private TextView m_V_editText;		private int m_V_value;

	private TextView m_R_editText;		private int m_R_value;
	private TextView m_G_editText;		private int m_G_value;
	private TextView m_B_editText;		private int m_B_value;

	private TextView m_C_editText;		private int m_C_value;
	private TextView m_M_editText;		private int m_M_value;
	private TextView m_Y_editText;		private int m_Y_value;
	private TextView m_K_editText;		private int m_K_value;
	
	/* EditTexts */
	private EditText m_colorEditText;
    
    /* Other Class Members */
    private LinearLayout m_rootLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.colorinfomain);
		super.onCreate(savedInstanceState);
        
	}

	@Override
	protected void onInflateObjects() {

		m_R_editText = (TextView)findViewById(R.id.id_r_value);
		m_G_editText = (TextView)findViewById(R.id.id_g_value);
		m_B_editText = (TextView)findViewById(R.id.id_b_value);
		
		m_H_editText = (TextView)findViewById(R.id.id_h_value);
		m_S_editText = (TextView)findViewById(R.id.id_s_value);
		m_V_editText = (TextView)findViewById(R.id.id_v_value);

		m_C_editText = (TextView)findViewById(R.id.id_c_value);
		m_M_editText = (TextView)findViewById(R.id.id_m_value);
		m_Y_editText = (TextView)findViewById(R.id.id_y_value);
		m_K_editText = (TextView)findViewById(R.id.id_k_value);
		
		m_colorEditText = (EditText)findViewById(R.id.id_colorinfo_edittext);
        
        m_rootLinearLayout = (LinearLayout)findViewById(R.id.id_colorinfomain_layout);
        
	}

	@Override
	protected void onCreateListeners() {
		
        // Gesture detection
		createSwipeDetection(ColorInfoActivity.this);
	}

	@Override
	protected void onSetListeners() {

		setSwipeDetectionToView(m_rootLinearLayout, null);
		
	}

	@Override
	protected void onStartUpdateUI() {
        
        getSavedColor();        
        convertToCMYK();        
        convertToHSV();
		
		m_R_editText.setText(String.valueOf(m_R_value));
		m_G_editText.setText(String.valueOf(m_G_value));
		m_B_editText.setText(String.valueOf(m_B_value));

		m_H_editText.setText(String.valueOf(m_H_value));
		m_S_editText.setText(String.valueOf(m_S_value) + "%");
		m_V_editText.setText(String.valueOf(m_V_value) + "%");

		m_C_editText.setText(String.valueOf(m_C_value));
		m_M_editText.setText(String.valueOf(m_M_value));
		m_Y_editText.setText(String.valueOf(m_Y_value));
		m_K_editText.setText(String.valueOf(m_K_value));
		
		m_colorEditText.setText(getHueHex());
	}
	
	private void getSavedColor(){
		m_R_value = getRedInt();
		m_G_value = getGreenInt();
		m_B_value = getBlueInt();
	}

	private void convertToCMYK() {

		int RED = 0; int GREEN = 1; int BLUE = 2;
        int[] rgb = normalizeRGB(m_R_value, m_G_value, m_B_value);
		int black = minimum(rgb);
		try{
			m_C_value = ((rgb[RED] - black)*100)/(100 - black);
		}
		catch(Exception ex){ // Divide by ZERO exception handled
			m_C_value = 0;
		}
		try{
			m_M_value = ((rgb[GREEN] - black)*100)/(100 - black);
		}
		catch(Exception ex){ // Divide by ZERO exception handled
			m_M_value = 0;
		}
		try{
			m_Y_value = ((rgb[BLUE] - black)*100)/(100 - black);
		}
		catch(Exception ex){ // Divide by ZERO exception handled
			m_Y_value = 0;
		}
		m_K_value = black;
		
	}

	private int[] normalizeRGB(int red, int green, int blue) {
		
		int[] rgbNorm = new int[3];
		rgbNorm[0] = 100 - ((red*100)/255);
		rgbNorm[1] = 100 - ((green*100)/255);
		rgbNorm[2] = 100 - ((blue*100)/255);
		return rgbNorm;
		
	}
	
	private int minimum(int[] rgb){
		
		int min = 256;
		for(int x = 0; x < rgb.length; x++){
			if(rgb[x] < min)
				min = rgb[x];
		}
		return min;
		
	}
	
	private void convertToHSV(){
		
		float[] hsv = new float[3];
		Color.RGBToHSV(m_R_value, m_G_value, m_B_value, hsv);

		m_H_value = Math.round(hsv[0]);
		m_S_value = Math.round(hsv[1] * 100);
		m_V_value = Math.round(hsv[2] * 100);
	}

}
