package app.pantry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class IntroScreen extends Activity implements OnGestureListener{
	private GestureDetector gestureScanner;
	View.OnTouchListener gestureListener;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro);
        
        gestureScanner = new GestureDetector(this);
	}

	@Override  
	public boolean onTouchEvent(MotionEvent event) {  
	  if (gestureScanner.onTouchEvent(event)){
	    return true;  
	  }
	  else  
	    return false;  
	}  
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		Intent intent = new Intent(getBaseContext(), PantryProjectActivity.class);
		startActivity(intent);
		return false;
	}
}
