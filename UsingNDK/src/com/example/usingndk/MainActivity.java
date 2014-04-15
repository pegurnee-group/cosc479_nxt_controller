package com.example.usingndk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;

public class MainActivity extends Activity {
	  // load the library - name matches jni/Android.mk
	  static {
	    System.loadLibrary("myndk");
	  }
	  
	  // declare the native code function - must match native.c
	  private native String invokeNativeFunction();
	  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // this is where we call the native code
        String hello = invokeNativeFunction();
 
        new AlertDialog.Builder(this).setMessage(hello).show();
    }
}