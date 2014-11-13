package com.example.proiektofindecarrera;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class ParteIncidencias extends ActionBarActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);
        //Spiners de opciones
        Spinner spinnerLugar = (Spinner) findViewById(R.id.spinnerlugar);
        Spinner spinnerSuceso = (Spinner) findViewById(R.id.spinnersuceso);
        Spinner spinnerAsistencia = (Spinner) findViewById(R.id.spinnerAsistencia);
        Spinner spinnerResultado = (Spinner) findViewById(R.id.spinnerResultado);
        
        //Crear el array adaptador asignandole el array
        ArrayAdapter<CharSequence> adapterLugar = ArrayAdapter.createFromResource(this,
        	     R.array.LugarDeSuceso, android.R.layout.simple_spinner_item);
        
        ArrayAdapter<CharSequence> adapterSuceso = ArrayAdapter.createFromResource(this,
       	     R.array.TipoDeSuceso, android.R.layout.simple_spinner_item);
       
        ArrayAdapter<CharSequence> adapterAsistencia = ArrayAdapter.createFromResource(this,
       	     R.array.AsistenciaPrestada, android.R.layout.simple_spinner_item);
       
        ArrayAdapter<CharSequence> adapterResultado = ArrayAdapter.createFromResource(this,
          	     R.array.Resultado, android.R.layout.simple_spinner_item);
        //Enlazar para la opcion de no seleccion
       
        
        //Cargar los adapter y ejecutarlos
        adapterLugar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLugar.setPrompt("Selecciona un Lugar");
        spinnerLugar.setAdapter(adapterLugar);
        
        
        adapterSuceso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuceso.setAdapter(adapterSuceso);
        
        adapterAsistencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAsistencia.setAdapter(adapterAsistencia);
        
        adapterResultado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResultado.setAdapter(adapterResultado);
        
        
	}
}

