package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import android.bluetooth.BluetoothDevice;

@SuppressWarnings("serial")
public class DeviceData implements Serializable {

	private InputStream is;
	private OutputStream os;
	private BluetoothDevice bt;
	private boolean connected;

	public static DeviceData instance;

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

	public BluetoothDevice getBt() {
		return bt;
	}

	public void setBt(BluetoothDevice bt) {
		this.bt = bt;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public static synchronized Object getInstance() {
		if (instance == null)
			instance = new DeviceData();
		return instance;
	}

}
