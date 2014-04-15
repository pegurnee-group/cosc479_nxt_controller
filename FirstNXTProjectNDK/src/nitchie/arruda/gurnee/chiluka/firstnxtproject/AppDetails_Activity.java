package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;



public class AppDetails_Activity extends Activity {
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.about_app);
			
			EditText mytext = (EditText) findViewById(R.id.details);

			mytext.setText("Product Design: 	COSC479/Group3\nProduct Engineering: Michael Nitchie,\t\t\t\t\t" +
					"		updated by: Eddie Gurnee\nUI Design: Nicole Arruda,\n"+
					"				updated by: Anjana Chiluka");

		}

		
}
