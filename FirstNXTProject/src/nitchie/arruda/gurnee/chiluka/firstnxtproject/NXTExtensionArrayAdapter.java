package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import nitchie.arruda.gurnee.chiluka.firstnxtproject.SensorListFragment.NXTExtension;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NXTExtensionArrayAdapter extends ArrayAdapter<NXTExtension> {

	Context context;
	int layoutResourceId;
	NXTExtension data[] = null;

	public NXTExtensionArrayAdapter(Context context, int layoutResourceId,
			NXTExtension[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		NXTExtensionHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new NXTExtensionHolder();
			holder.imgIcon = (ImageView) row.findViewById(R.id.sensor_image);
			holder.txtTitle = (TextView) row.findViewById(R.id.list_number);

			row.setTag(holder);
		} else {
			holder = (NXTExtensionHolder) row.getTag();
		}

		NXTExtension extension = data[position];
		holder.txtTitle.setText(extension.getTitle());
		holder.imgIcon.setImageResource(extension.getIcon());

		return row;
	}

	static class NXTExtensionHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
