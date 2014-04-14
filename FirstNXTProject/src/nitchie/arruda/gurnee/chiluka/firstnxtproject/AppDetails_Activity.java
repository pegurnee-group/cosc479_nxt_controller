package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class AppDetails_Activity extends Activity {
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.about_app);
			
			TextView mytext = (TextView) findViewById(R.id.details);
			
		
			mytext.setText("Product Design: 	COSC479/Group3\nProduct Engineering: Michael Nitchie,\t\t\t\t\t" +
					"		updated by: Eddie Gurnee\nUI Design: Nicole Arruda,\n"+
					"				updated by: Anjana Chiluka");
			

			
		}

		
}
