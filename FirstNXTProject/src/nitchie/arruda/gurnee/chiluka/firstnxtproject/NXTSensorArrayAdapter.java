package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import nitchie.arruda.gurnee.chiluka.firstnxtproject.SelectSensorPopupActivity.NXTSensor;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NXTSensorArrayAdapter extends ArrayAdapter<NXTSensor> {

	Context context;
	int layoutResourceId;
	NXTSensor[] data = null;

	public NXTSensorArrayAdapter(Context context, int layoutResourceId,
			NXTSensor[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		NXTSensorHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new NXTSensorHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.select_sensor_image_imageView);
			holder.imgIcon = (ImageView) row.findViewById(R.id.select_sensor_name_lbl);

			row.setTag(holder);
		} else {
			holder = (NXTSensorHolder) row.getTag();
		}

		NXTSensor sensor = data[position];
		holder.txtTitle.setText(sensor.getTitle());
		holder.imgIcon.setImageResource(sensor.getIcon());

		return row;
	}

	static class NXTSensorHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
