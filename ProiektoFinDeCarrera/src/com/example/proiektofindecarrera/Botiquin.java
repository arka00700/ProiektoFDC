package com.example.proiektofindecarrera;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;

import android.widget.Toast;

public class Botiquin extends FragmentActivity implements OnQueryTextListener{
	
	private Button btmedicamentonuevo;
	SearchView searchView; 
	BD_sqlite BDhelper = new BD_sqlite(this);
	FragmentListaMedicamentos medicamentos;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);
        medicamentos= (FragmentListaMedicamentos) getSupportFragmentManager().
        		findFragmentById(R.id.fragment);
		medicamentos.mostrarTodosLosMedicamentos();
        searchView= (SearchView) findViewById(R.id.buscarmedicamento); 
        btmedicamentonuevo = (Button) findViewById(R.id.insertarmedicamento);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				medicamentos.actualizarLista(query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {			
				medicamentos.actualizarLista(newText);
				return false;
			}
		});
        btmedicamentonuevo.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// INSERTAMOS MEDICAMENTOS DE PRUEBA
				BDhelper.insertarMedicamentos("Trombocil", "Anestesico local");
				BDhelper.insertarMedicamentos("Tropical", "Anestesico local");
				BDhelper.insertarMedicamentos("Botarel", "Crema de uso local");
				BDhelper.insertarMedicamentos("Reflex", "Dolor muscular");
				BDhelper.insertarMedicamentos("Sintrom", "Anestesico local");
				BDhelper.insertarMedicamentos("Nitrofurantoina", "Antiseptico Urinario");
				BDhelper.insertarMedicamentos("Amikacina", "Aminoglucosido");
				BDhelper.insertarMedicamentos("Raditina", "Antiacido");
			}
		});
	}
	
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}	
