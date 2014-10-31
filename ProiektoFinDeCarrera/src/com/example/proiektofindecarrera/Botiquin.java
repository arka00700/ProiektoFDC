package com.example.proiektofindecarrera;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Botiquin extends ActionBarActivity{
	
	private Button btmedicamentonuevo;
	BD_sqlite BDhelper = new BD_sqlite(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);
        
        btmedicamentonuevo = (Button) findViewById(R.id.insertarmedicamento);
		
		btmedicamentonuevo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BDhelper.insertarMedicamentos("gelocatil", "droga fuerte");
			}
		});

	}
}
