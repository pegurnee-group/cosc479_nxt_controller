package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class DriveActivity extends Activity implements OnTouchListener
{
	
	final String tag = "BtTry";
	final String ROBOTNAME = "LegoNXT";
	
	// BT Variables
	private BluetoothAdapter btInterface;
	private Set<BluetoothDevice> pairedDevices;
	private BluetoothSocket socket;
	private BluetoothDevice bd;
	private InputStream is = null;
	private OutputStream os = null;
	private int mpower;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drive_view);
		
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
		
		SeekBar powerSeekBar = (SeekBar) findViewById(R.id.seekBar1);
        powerSeekBar.setProgress(30);
        powerSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                mpower = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }            
        });
	}
	
	public boolean onTouch(View view,MotionEvent event)
	{
		int action;
		switch(view.getId())
		{
			case R.id.button1:
					Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
					 action = event.getAction();
			         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
			         if (action == MotionEvent.ACTION_DOWN) {
			        	 Log.i("NXT", "Action1 started " );
			        	 MoveMotor(1, 75, 0x20);
			        	 MoveMotor(2, 75, 0x20);
			            
			         } else if ((action == MotionEvent.ACTION_UP) ) {
			        	 Log.i("NXT", "Action1 Stopped " ); 
			        	 MoveMotor(1, 75, 0x00);
			        	 MoveMotor(2, 75, 0x00);
			         }
			         break;
			         
			case R.id.button2:
				Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        	 Log.i("NXT", "Action2 started " ); 
		        	 MoveMotor(1, 75, 0x20);
		        	 MoveMotor(2, 75, 0x20);
		            
		         } else if ((action == MotionEvent.ACTION_UP) ) {
		        	 Log.i("NXT", "Action2 Stopped " ); 
		        	 MoveMotor(1, 75, 0x00);
		        	 MoveMotor(2, 75, 0x00);
		         }
		         break;
	         
		case R.id.button3:
				Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        	 Log.i("NXT", "Action3 started " ); 
		        	 MoveMotor(1, 75, 0x20);
		        	 MoveMotor(2, 75, 0x20);
		            
		         } else if ((action == MotionEvent.ACTION_UP) ) {
		        	 Log.i("NXT", "Action3 Stopped " );
		        	 MoveMotor(1, 75, 0x00);
		        	 MoveMotor(2, 75, 0x00);
		         }
		         break;
		         
		case R.id.button4:
				Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
				 action = event.getAction();
		         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
		         if (action == MotionEvent.ACTION_DOWN) {
		        	 Log.i("NXT", "Action4 started " );
		        	 MoveMotor(1, 75, 0x20);
		        	 MoveMotor(2, 75, 0x20);
		            
		         } else if ((action == MotionEvent.ACTION_UP) ) {
		        	 Log.i("NXT", "Action4 Stopped " ); 
		        	 MoveMotor(1, 75, 0x00);
		        	 MoveMotor(2, 75, 0x00);
		         }
		         break;
		         
		case R.id.button5:
			Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
			 action = event.getAction();
	         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
	         if (action == MotionEvent.ACTION_DOWN) {
	        	 Log.i("NXT", "Action4 started " );
	        	 MoveMotor(3, 75, 0x20);
	            
	         } else if ((action == MotionEvent.ACTION_UP) ) {
	        	 Log.i("NXT", "Action4 Stopped " ); 
	        	 MoveMotor(3, 75, 0x00);
	         }
	         break;
	         
		case R.id.button6:
			Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
			 action = event.getAction();
	         //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
	         if (action == MotionEvent.ACTION_DOWN) {
	        	 Log.i("NXT", "Action4 started " );
	        	 MoveMotor(3, 75, 0x20);
	        	 
	            
	         } else if ((action == MotionEvent.ACTION_UP) ) {
	        	 Log.i("NXT", "Action4 Stopped " ); 
	        	 MoveMotor(3, 75, 0x00);
	        	
	         }
	         break;
			         
		}
	         return true;
			
	}
	
	public void connectNXT(View v) {
		try	{
    		btInterface = BluetoothAdapter.getDefaultAdapter();
    		////Log.i(tag,"Local BT Interface name is [" + btInterface.getName() + "]");
    		pairedDevices = btInterface.getBondedDevices();
    		//Log.i(tag,"Found [" + pairedDevices.size() + "] devices.");
    		
    		//Improve later -> use list for selection
    		Iterator<BluetoothDevice> it = pairedDevices.iterator();
    		while (it.hasNext()) {
       			bd = it.next();
    			Log.i(tag,"Name of peer is [" + bd.getName() + "]");
    			if (bd.getName().equalsIgnoreCase(ROBOTNAME)) {
    				//Log.i(tag,"Found Robot!");
    				//Log.i(tag,bd.getAddress());
    				Log.i(tag, "Found "+ bd.getName() + " with ID " + bd.getAddress());
    				//Log.i(tag,bd.getBluetoothClass().toString());
    				try {			
    					socket = bd.createRfcommSocketToServiceRecord(java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
    					socket.connect();
    				}
    				catch (Exception e) {
    					Log.e(tag,"Error interacting with remote device -> " + e.getMessage()); 
    				}
    				
        			try {
        				is = socket.getInputStream();
        				os = socket.getOutputStream();
        			} catch (Exception e) {
        				is = null;
        				os = null;
        				disconnectNXT(null);
        			}

    				return;
    			}
    		}
		} 
		catch (Exception e) 	{
			Log.e(tag,"Failed in finding NXT -> " + e.getMessage());
		}
	}
	
	private void MoveMotor(int motor,int speed, int state) {
		try {
			Log.i(tag,"Attempting to move [" + motor + " @ " + speed + "]");
			
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

			os.write(buffer);
			os.flush();
			
		}
		catch (Exception e) {
			Log.e(tag,"Error in MoveForward(" + e.getMessage() + ")");
		}		
	}
	
	public void disconnectNXT(View v) {
		try {
			Log.i(tag,"Attempting to break BT connection of " + bd.getName());
			socket.close();
			is.close();
			os.close();
			Log.i(tag, "BT connection of " + bd.getName() + " is disconnected");
		}
		catch (Exception e)	{
			Log.e(tag,"Error in disconnect -> " + e.getMessage());
		}
	}
	
	
}
