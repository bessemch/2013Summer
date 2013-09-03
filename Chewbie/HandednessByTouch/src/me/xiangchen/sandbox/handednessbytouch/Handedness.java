package me.xiangchen.sandbox.handednessbytouch;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class Handedness extends Activity {

	public final static String LOGTAG = "Handedness";
	public final static int HANDEDNESSTIMEOUT = 500; // ms
	RelativeLayout layout;
	
	float xTouchDown;
	float yTouchDown;
	long timeTouchDown;
	
	int cntDataPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		layout = new RelativeLayout(this);
		layout.setBackgroundColor(0xFFFFFF00);
		layout.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				PointerCoords curCoord = new PointerCoords();
				event.getPointerCoords(0, curCoord);
				Calendar calendar = Calendar.getInstance();
				long curTime = calendar.getTimeInMillis();
				switch(action) {
				case MotionEvent.ACTION_DOWN:
					xTouchDown = event.getAxisValue(MotionEvent.AXIS_X);// curCoord.x;
					yTouchDown = curCoord.y;
					timeTouchDown = curTime;
					cntDataPoint = 0;
					break;
//				case MotionEvent.ACTION_MOVE:
					default:
					int dt = (int) (curTime - timeTouchDown);
					if(dt < HANDEDNESSTIMEOUT) {
						Log.d(LOGTAG, "[" + cntDataPoint++ + "] " + dt + ": " + event.getOrientation(0) + ", " + (event.getAxisValue(MotionEvent.AXIS_X) - xTouchDown));
					}
					break;
//				case MotionEvent.ACTION_UP:
//					break;
				}
				return true;
			}
		});
		
		setContentView(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.handedness, menu);
		return true;
	}

}
