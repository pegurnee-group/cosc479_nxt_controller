package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class DeviceData 
{
	
	public BluetoothSocket getSocket() {
		return socket;
	}
	public void setSocket(BluetoothSocket socket) {
		this.socket = socket;
	}
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	// BT Variables
	private BluetoothSocket socket;
	private InputStream is = null;
	private OutputStream os = null;
	
	
	
	
}
