package com.example.proiektofindecarrera;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ParteIncidencias extends ActionBarActivity {
	
	final Calendar calendario= Calendar.getInstance();
	EditText insertarHora,nombreApellido,edad,telefono;
	BD_sqlite BDhelper= new BD_sqlite(this);
	Spinner spinnerLugar,spinnerSuceso,spinnerAsistencia,spinnerResultado;
	RadioGroup sexo;
	Button btguardar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);
        //IDENTIFICACION
        nombreApellido = (EditText) findViewById(R.id.nombreapellidos);
		edad = (EditText) findViewById(R.id.edad);
		telefono = (EditText) findViewById(R.id.telefono);
        insertarHora=(EditText)findViewById(R.id.texthora);
        btguardar=(Button)findViewById(R.id.guardarIncidencias);
        //Spiners de opciones
        spinnerLugar = (Spinner) findViewById(R.id.spinnerlugar);
        spinnerSuceso = (Spinner) findViewById(R.id.spinnersuceso);
        spinnerAsistencia = (Spinner) findViewById(R.id.spinnerAsistencia);
        spinnerResultado = (Spinner) findViewById(R.id.spinnerResultado);
        sexo= (RadioGroup)findViewById(R.id.sexoradiogroup);
        
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
        
        btguardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				registrarIncidencia();
			}
		});
	}
	//TIME PICKER 
	TimePickerDialog.OnTimeSetListener time = new OnTimeSetListener() {
		
		public void onTimeSet(TimePicker view, int h, int m) {
			calendario.set(Calendar.HOUR, h);
 	    	calendario.set(Calendar.MINUTE, m);
			setCurrentTimeOnView();
		}
	};
	
	public void timeOnClick(View view){
		new TimePickerDialog(this, time, calendario.get(Calendar.HOUR), calendario.get(Calendar.MINUTE),
				true).show();
    }
	
	private void setCurrentTimeOnView(){
		String timeFormat="hh:mm";
		SimpleDateFormat stf = new SimpleDateFormat(timeFormat,Locale.UK);
		insertarHora.setText(stf.format(calendario.getTime()).concat(":00"));
	}
	//GUARDAR EN BASE DE DATOS 
	private void registrarIncidencia (){
		//RECOGIDA DE DATOS
		String nombreString = nombreApellido.getText().toString(); //COMPROBAR LOS DATOS QUE ALGUNO FALLA
		String edadString = edad.getText().toString();
		String telefonoString = telefono.getText().toString();
		boolean esHombre=valorRadioGroup();
		String hora = insertarHora.getText().toString();
		int datosSpinner[]=ConversorSpinner(spinnerLugar, spinnerSuceso, spinnerAsistencia, spinnerResultado);
		
		if (estaVacioIncidencias()==true){
			Toast.makeText(getApplicationContext(), "Algún campo en blanco", 2000).show();
		}else if(hora.isEmpty()){
				Toast.makeText(getApplicationContext(),"Inserta una hora", 2000).show();
		/*	}else if (BDhelper.existeParte(nombreString, hora)==false){
				//BDhelper.InsertarParteIncidencias();
				Toast.makeText(getApplicationContext(),"Parte realizado correctamente", 2000).show();
				
				}else{
				
				Toast.makeText(getApplicationContext(),"El usuario ya existe", 2000).show();
				
				*/
				}	
	
			}
	
	private int[] ConversorSpinner(Spinner sp0,Spinner sp1,Spinner sp2, Spinner sp3){
		int[] respuestasSpinner=new int[4] ;
		
			respuestasSpinner[0]=(spinnerLugar.getSelectedItemPosition())+1;
			respuestasSpinner[1]=(spinnerLugar.getSelectedItemPosition())+1;
			respuestasSpinner[2]=(spinnerAsistencia.getSelectedItemPosition())+1;
			respuestasSpinner[3]=(spinnerResultado.getSelectedItemPosition())+1;
		 
		return respuestasSpinner;
		
	}
	private boolean estaVacioIncidencias(){
		boolean vacio = true;
		if(nombreApellido.getText().toString().compareTo("")!=0 && edad.getText().toString().compareTo("")!=0
				&& sexo.getCheckedRadioButtonId()!=-1){
			vacio=false;
		}
		return vacio;
	
	}
	private boolean valorRadioGroup(){
		boolean hombre=true;
		int radioButtonID = sexo.getCheckedRadioButtonId();
		if(radioButtonID==R.id.Mujer){
			hombre=false;
		}
		return hombre;
	}
}
