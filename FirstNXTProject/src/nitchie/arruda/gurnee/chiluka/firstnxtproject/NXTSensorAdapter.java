package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import nitchie.arruda.gurnee.chiluka.firstnxtproject.SensorActivity.NXTSensor;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NXTSensorAdapter extends ArrayAdapter<NXTSensor> {

	Context context;
	int layoutResourceId;
	NXTSensor data[] = null;

	public NXTSensorAdapter(Context context, int layoutResourceId,
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
			holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

			row.setTag(holder);
		} else {
			holder = (NXTSensorHolder) row.getTag();
		}

		NXTSensor weather = data[position];
		holder.txtTitle.setText(weather.getTitle());
		holder.imgIcon.setImageResource(weather.getIcon());

		return row;
	}

	static class NXTSensorHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
