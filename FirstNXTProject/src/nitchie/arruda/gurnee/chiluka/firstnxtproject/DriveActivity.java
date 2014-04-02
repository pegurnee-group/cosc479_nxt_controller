package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DriveActivity extends Activity implements OnTouchListener{
	
	int mpower1 = 40;
	int mpower2 = 40;
	boolean flag = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.drive_view);
		
		driveDirections();
	}

	//Set up Drive View Controls
	public void driveDirections()
	{
		Button goFwd = (Button) findViewById(R.id.button1);
		goFwd.setOnTouchListener(this);

		Button goBwd = (Button) findViewById(R.id.button2);
		goBwd.setOnTouchListener(this);
		
		Button goRight = (Button) findViewById(R.id.button3);
		goRight.setOnTouchListener(this);
		
		Button goLeft = (Button) findViewById(R.id.button4);
		goLeft.setOnTouchListener(this);

		Button goFwd2 = (Button) findViewById(R.id.button5);
		goFwd2.setOnTouchListener(this);
		
		Button goBwd2 = (Button) findViewById(R.id.button6);
		goBwd2.setOnTouchListener(this);
		
		SeekBar powerSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        powerSeekBar1.setProgress(mpower1);
        
		powerSeekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
            	mpower1 = progress;
            	 //Log.i("NXT", "Action started "+ progressChanged );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            	//Log.i("NXT", "Action started "+ mpower1);
            }            
        });
		
		
		SeekBar powerSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        powerSeekBar2.setProgress(mpower2);
        
		powerSeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
            	mpower2 = progress;
            	 //Log.i("NXT", "Action started "+ progressChanged );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            	//Log.i("NXT", "Action started "+ mpower2);
            }            
        });
		Log.e("Check2","Check");
	}
	
	public boolean onTouch(View view, MotionEvent event )
	{
		int action;
		
		switch(view.getId())
		{
			//Go Fwd
			case R.id.button1:
				action = event.getAction();
		        //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		        if (action == MotionEvent.ACTION_DOWN) {
		       	
		       	 
			       	 if(flag==false)
			       	 {
			       		 MoveMotor(1, -mpower1, 0x20);
			       		 MoveMotor(2, -mpower1, 0x20);
			       	 }       	 
			       	 flag = true;
		  
		        } else if ((action == MotionEvent.ACTION_UP) ) {
			       	
			       	 flag = false;
			       	 MoveMotor(1, -mpower1, 0x00);
			       	 MoveMotor(2, -mpower1, 0x00);
		        }
			         break;
			//Go Rev         
			case R.id.button2:
				
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        
		        	 if(flag==false)
			       	 {
			       		 MoveMotor(1, mpower1, 0x20);
			       		 MoveMotor(2, mpower1, 0x20);
			       	 }       	 
			       	 flag = true;
		  
		        } else if ((action == MotionEvent.ACTION_UP) ) {
			       	 Log.i("NXT", "Action1 Stopped " ); 
			       	 flag = false;
			       	 MoveMotor(1, mpower1, 0x00);
			       	 MoveMotor(2, mpower1, 0x00);
		        }
		         break;
	     
		//Go Right         
		case R.id.button3:
				
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        	 Log.i("NXT", "Action3 started " ); 
		        	 if(flag==false)
			       	 {
			       		 MoveMotor(1, -mpower1, 0x20);
			       		 MoveMotor(2, mpower1, 0x20);
			       	 }       	 
			       	 flag = true;
		  
		        } else if ((action == MotionEvent.ACTION_UP) ) {
			       	 Log.i("NXT", "Action1 Stopped " ); 
			       	 flag = false;
			       	 MoveMotor(1, -mpower1, 0x00);
			       	 MoveMotor(2, mpower1, 0x00);
		         }
		         break;
		
		 //Go Left
		case R.id.button4:
				
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        	 Log.i("NXT", "Action4 started " );
		        	 if(flag==false)
			       	 {
			       		 MoveMotor(1, mpower1, 0x20);
			       		 MoveMotor(2, -mpower1, 0x20);
			       	 }       	 
			       	 flag = true;
		  
		        } else if ((action == MotionEvent.ACTION_UP) ) {
			       	 Log.i("NXT", "Action1 Stopped " ); 
			       	 flag = false;
			       	 MoveMotor(1, mpower1, 0x00);
			       	 MoveMotor(2, -mpower1, 0x00);
		         }
		         break;
		         
		case R.id.button5:
			
			 action = event.getAction();
	         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
	         if (action == MotionEvent.ACTION_DOWN) {
	        	 Log.i("NXT", "Action4 started " );
	        	 if(flag==false)
		       	 {
		       		 MoveMotor(3, -mpower2, 0x20);
		       		
		       	 }       	 
		       	 flag = true;
	  
	        } else if ((action == MotionEvent.ACTION_UP) ) {
		       	 Log.i("NXT", "Action1 Stopped " ); 
		       	 flag = false;
		       	 MoveMotor(3, -mpower2, 0x00);
		  
	         }
	         break;
	         
		case R.id.button6:
		
			 action = event.getAction();
			 if (action == MotionEvent.ACTION_DOWN) {
				 Log.i("NXT", "Action4 started " );
			 if(flag==false)
	       	 {
	       		 MoveMotor(3, mpower2, 0x20);	
	       	 }       	 
	       	 flag = true;
  
        } else if ((action == MotionEvent.ACTION_UP) ) {
	       	 Log.i("NXT", "Action4 Stopped " ); 
	       	 flag = false;
	       	 MoveMotor(3, mpower2, 0x00); 
	         }
	         break;
			         
		}
		return true;
		
	}
	
	
	private void MoveMotor(int motor,int speed, int state) {
		try {
			//Log.i(TAG,"Attempting to move [" + motor + " @ " + speed + "]");
			
			byte[] buffer = new byte[15];
			
			buffer[0] = (byte) (15-2);			// length lsb
			buffer[1] = 0;						// length msb
			buffer[2] =  0;						// direct command (with response)
			buffer[3] = 0x04;					// set output state
			buffer[4] = (byte) motor;			// output 1 (motor B)
			buffer[5] = (byte) speed;			// power
			buffer[6] = 1 + 2;					// motor on + brake between PWM
			buffer[7] = 0;						// regulation
			buffer[8] = 0;						// turn ration??
			buffer[9] = (byte) state; //0x20;	// run state
			buffer[10] = 0;
			buffer[11] = 0;
			buffer[12] = 0;
			buffer[13] = 0;
			buffer[14] = 0;

			DeviceData myObject = (DeviceData) DeviceData.getInstance();
			myObject.getOs().write(buffer);
			myObject.getOs().flush();
		
			
		}
		catch (Exception e) {
			//Log.e(tag,"Error in MoveForward(" + e.getMessage() + ")");
		}		
	}

}
