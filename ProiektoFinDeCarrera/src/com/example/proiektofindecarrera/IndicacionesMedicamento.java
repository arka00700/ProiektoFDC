package com.example.proiektofindecarrera;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class IndicacionesMedicamento extends ActionBarActivity {
	
	private TextView bienvenidoMedicamento,indicaciones;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicacionesmedicamento);
        //IDENTIFICACIÓN
        bienvenidoMedicamento = (TextView) findViewById(R.id.bienvenidoMedicamento); 
        indicaciones = (TextView) findViewById(R.id.indicaciones); 
        
        String medicamento =getIntent().getStringExtra("NombreMedicamento");
      	bienvenidoMedicamento.setText("Medicamento: "+medicamento);
      	InsertarIndicaciones(medicamento,indicaciones);
	}
	
	public void InsertarIndicaciones(String mdc,TextView t){
		Cursor c=BD_sqlite.getMiBD(getApplicationContext()).leerIndicaciones(mdc);
		c.moveToFirst();
		String indicaciones="";
		indicaciones = c.getString(0);
		t.setText("El siguiente farmaco se utiliza para: "+indicaciones);
	}
}

