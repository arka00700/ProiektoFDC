package com.example.proiektofindecarrera;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemSelectedListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinnerActivity extends ActionBarActivity implements OnItemSelectedListener{

	 public void onItemSelected(AdapterView<?> parent, View view, 
	            int position, long id) {
	        // An item was selected. You can retrieve the selected item using
	        // parent.getItemAtPosition(pos)
		 Toast.makeText(this, "Has pulsado "+ parent.getItemAtPosition(position), 2000).show();
	 
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }

		@Override
		public void onItemSelected(AdapterViewCompat<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterViewCompat<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}


