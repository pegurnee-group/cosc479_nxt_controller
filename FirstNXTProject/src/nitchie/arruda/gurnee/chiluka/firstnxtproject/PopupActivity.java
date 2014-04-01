package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

public class PopupActivity extends ListActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.popup_view);
		
		Log.e("hello", "in otherworld");
		
		String[] names = {"eddie", "was", "here", "don'tchu", "worry", "child" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.row_view, R.id.row_lbl, names);
		this.setListAdapter(adapter);
		
		/*
		BluetoothDevice[] devices = (BluetoothDevice[]) BluetoothAdapter
				.getDefaultAdapter().getBondedDevices().toArray();
		
		int limit = devices.length;
		String[] names = new String[limit];
		
		for (int i = 0; i < limit; i++) {
			names[i] = devices[i].getName() + "\n" + devices[i].getAddress();
		}

		*/
		Log.e("hello", "popup viewed");
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(PopupActivity.this, MainActivity.class);
	    this.startActivity(intent);
	}
}
