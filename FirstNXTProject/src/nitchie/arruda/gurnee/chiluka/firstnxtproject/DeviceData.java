package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

public class DeviceData implements Serializable {

	private InputStream is;
	private OutputStream os;
	private UUID theUUID;

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

	public UUID getTheUUID() {
		return theUUID;
	}

	public void setTheUUID(UUID theUUID) {
		this.theUUID = theUUID;
	}

	public static synchronized Object getInstance() {
		if (instance == null)
			instance = new DeviceData();
		return instance;
	}

}
