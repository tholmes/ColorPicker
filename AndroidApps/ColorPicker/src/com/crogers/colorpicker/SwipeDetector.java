package com.crogers.colorpicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

class SwipeDetector extends SimpleOnGestureListener{

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private Context m_cxt;
    private Class<?> m_currActivity;
	
	SwipeDetector(Context cxt){
		m_cxt = cxt;
		// Get the Activity we are currently on so we can figure out where we are.
		m_currActivity =  m_cxt.getClass();
	}
	
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
    		
            /** LEFT <- RIGHT
             * Switch to next screen on your right.
             * */
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                moveToTheRight();
            }
            /** LEFT -> RIGHT
             * Switch to previous screen on your left.
             * */
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                moveToTheLeft();
            }
        }
        catch (Exception e) {
        	Toast.makeText(m_cxt, "Ex-onFling: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
       
        return false;
    }

	private void moveToTheLeft() {
		/** If/Else statements listed from class on right moving to the left */
		if(m_currActivity == HSVPickerActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, ColorPickerActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == ColorPickerActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, QuickSelectGradientActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == QuickSelectGradientActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, ColorInfoActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == ColorInfoActivity.class){
			/* Do nothing - since there are no activities on left of ColorInforActivity */
		}
	}

	private void moveToTheRight() {
		/** If/Else statements listed from class on left moving to the right */
		if(m_currActivity == ColorInfoActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, QuickSelectGradientActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == QuickSelectGradientActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, ColorPickerActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == ColorPickerActivity.class){
			((Activity)m_cxt).finish();
			Intent myIntent = new Intent(m_cxt, HSVPickerActivity.class);
			((Activity)m_cxt).startActivity(myIntent);
		}
		else if(m_currActivity == HSVPickerActivity.class){
			/* Do nothing - since there are no activities on right of HSVPickerActivity */
		}
	}
}
