package com.crogers.colorpicker;

public class RGBColors {
	/* Constants */
	private final int HEX_BASE = 16;
	
	private String m_red = "FF";
	private String m_green = "00";
	private String m_blue = "00";
	
	/** Setters */
	/**
	 * Takes the EditText string and sets the RGB & Hue values.
	 * @param hexCode - EditText string
	 * @return True - if the string was valid, False - otherwise
	 */
	public boolean setHueHex(String hexCode){
		hexCode = sanitizeHexString(hexCode);
		if(hexCode == null)
			return false;
		
		setRed(hexCode.substring(0, 2));
		setGreen(hexCode.substring(2, 4));
		setBlue(hexCode.substring(4, 6));

		return true;
	}
	
	private void setRed(String newRed) {
		if (newRed.length() == 1)
			m_red = "0" + newRed;
		else
			m_red = newRed;
		
		m_red = m_red.toUpperCase();
	}
	
	private void setGreen(String newGreen) {
		if (newGreen.length() == 1)
			m_green = "0" + newGreen;
		else
			m_green = newGreen;
		
		m_green = m_green.toUpperCase();
	}
	
	private void setBlue(String newBlue) {
		if (newBlue.length() == 1)
			m_blue = "0" + newBlue;
		else
			m_blue = newBlue;
	
		m_blue = m_blue.toUpperCase();
	}

	private void setRedInt(int newRed) {
		setRed(Integer.toHexString(newRed));
	}
	
	private void setGreenInt(int newGreen) {
		setGreen(Integer.toHexString(newGreen));
	}
	
	private void setBlueInt(int newBlue) {
		setBlue(Integer.toHexString(newBlue));
	}

	/** Getters */
	public String getHueHex(){
		return "#" + getRed() + getGreen() + getBlue();
	}
	
	private String getRed() {
		return m_red;
	}
	
	private String getGreen() {
		return m_green;
	}
	
	private String getBlue() {
		return m_blue;
	}
	
	public int getRedInt() {
		return Integer.parseInt(m_red, 16);
	}
	
	public int getGreenInt() {
		return Integer.parseInt(m_green, 16);
	}
	
	public int getBlueInt() {
		return Integer.parseInt(m_blue, 16);
	}

	/* returns null if there was a problem with the input */
	private String sanitizeHexString(String editField) {

		if (editField.length() != 7) 	
			return null;
		else if (!editField.substring(0, 1).equals("#")) 	
			return null;
		else if (!isHexCode(editField.substring(1,2))) 	
			return null;
		else if (!isHexCode(editField.substring(2,3))) 	
			return null;
		else if (!isHexCode(editField.substring(3,4))) 	
			return null;
		else if (!isHexCode(editField.substring(4,5))) 	
			return null;
		else if (!isHexCode(editField.substring(5,6))) 	
			return null;
		else if (!isHexCode(editField.substring(6,7))) 	
			return null;
		
		// we're 7 characters, first is a '#', and the rest are all hex values 0-9 and A-F
		return editField.substring(1);
		
	} // end sanitizeEditField
	
	private boolean isHexCode(String arg) {
		try {
			Integer.parseInt(arg, HEX_BASE);
		}
		catch(Throwable err)  {
			return false;
		}		
		return true;
	} // end isHexCode

	public void setRGB(String red, String green, String blue) {
		setRedInt(Integer.valueOf(red));
		setGreenInt(Integer.valueOf(green));
		setBlueInt(Integer.valueOf(blue));
	}
	
	public void setRGB(int red, int green, int blue){
		setRedInt(red);
		setGreenInt(green);
		setBlueInt(blue);
	}
}
