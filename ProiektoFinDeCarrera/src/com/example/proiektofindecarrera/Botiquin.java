package com.example.proiektofindecarrera;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Botiquin extends FragmentActivity{
	
	private Button btmedicamentonuevo;
	BD_sqlite BDhelper = new BD_sqlite(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);
        FragmentLista medicamentos= (FragmentLista) getSupportFragmentManager().findFragmentById(R.id.fragment);
		medicamentos.mostrarTodosLosMedicamentos();
        
        btmedicamentonuevo = (Button) findViewById(R.id.insertarmedicamento);
		btmedicamentonuevo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BDhelper.insertarMedicamentos("Trombocil", "Anestesico local");
				BDhelper.insertarMedicamentos("Botarel", "Crema de uso local");
				BDhelper.insertarMedicamentos("Reflex", "Dolor muscular");
				BDhelper.insertarMedicamentos("Sintrom", "Anestesico local");
				BDhelper.insertarMedicamentos("Nitrofurantoina", "Antiseptico Urinario");
				BDhelper.insertarMedicamentos("Amikacina", "Aminoglucosido");
				BDhelper.insertarMedicamentos("Raditina", "Antiacido");
			}
		});

	}
}
