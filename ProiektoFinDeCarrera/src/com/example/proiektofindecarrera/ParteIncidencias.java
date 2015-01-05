package com.example.proiektofindecarrera;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ParteIncidencias extends ActionBarActivity {
	//DECLARACION
	final Calendar calendario= Calendar.getInstance();
	EditText insertarHora,nombreApellido,edad,telefono,observaciones;
	BD_sqlite BDhelper= new BD_sqlite(this);
	Spinner spinnerLugar,spinnerSuceso,spinnerAsistencia,spinnerResultado;
	RadioGroup sexo;
	Button btguardar;
	boolean sex;//SUB
	int lugar,suceso,asistencia,resultado;
	int[] cargarSpinners={0,0,0,0};
	String años,nomApel,tlf,hora,obs;//SUB
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);
        //IDENTIFICACION
        nombreApellido = (EditText) findViewById(R.id.nombreapellidos);
		edad = (EditText) findViewById(R.id.edad);
		telefono = (EditText) findViewById(R.id.telefono);
        insertarHora=(EditText)findViewById(R.id.horaincidencia);
        observaciones=(EditText)findViewById(R.id.observaciones);
        btguardar=(Button)findViewById(R.id.guardarIncidencias);
        //Spiners de opciones
        spinnerLugar = (Spinner) findViewById(R.id.spinnerlugar);
        spinnerSuceso = (Spinner) findViewById(R.id.spinnersuceso);
        spinnerAsistencia = (Spinner) findViewById(R.id.spinnerAsistencia);
        spinnerResultado = (Spinner) findViewById(R.id.spinnerResultado);
        sexo= (RadioGroup)findViewById(R.id.sexoradiogroup);
        //CARGAR EL INTENT si es para modificar un parte ya existente
        if(getIntent().getStringExtra("NombreApellido")!=null){
        cargarSpinners=CargarIntent();
        }
       
        //Crear el array adaptador asignandole el array
        ArrayAdapter<CharSequence> adapterLugar = ArrayAdapter.createFromResource(this,
        	     R.array.LugarDeSuceso, android.R.layout.simple_spinner_item);
       		
        ArrayAdapter<CharSequence> adapterSuceso = ArrayAdapter.createFromResource(this,
       	     R.array.TipoDeSuceso, android.R.layout.simple_spinner_item);
        
        ArrayAdapter<CharSequence> adapterAsistencia = ArrayAdapter.createFromResource(this,
       	     R.array.AsistenciaPrestada, android.R.layout.simple_spinner_item);
        
        ArrayAdapter<CharSequence> adapterResultado = ArrayAdapter.createFromResource(this,
          	     R.array.Resultado, android.R.layout.simple_spinner_item);
        
        //Cargar los adapter y ejecutarlos
        
        adapterLugar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLugar.setAdapter(adapterLugar);
        spinnerLugar.setSelection(cargarSpinners[0]);
      
        adapterSuceso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuceso.setAdapter(adapterSuceso);
        spinnerSuceso.setSelection(cargarSpinners[1]);
        
        adapterAsistencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAsistencia.setAdapter(adapterAsistencia);
        spinnerAsistencia.setSelection(cargarSpinners[2]);
        
        adapterResultado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResultado.setAdapter(adapterResultado);
        spinnerResultado.setSelection(cargarSpinners[3]);
        
        btguardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				guardarIncidencia();
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
		insertarHora.setText(stf.format(calendario.getTime()));
	}
	//GUARDAR EN BASE DE DATOS 
	
	private void guardarIncidencia (){
		//RECOGIDA DE DATOS
		String nombreString = nombreApellido.getText().toString(); 
		String edadString = edad.getText().toString();
		String telefonoString = telefono.getText().toString();
		String datephone = getDatePhone();
		boolean esHombre=valorRadioSexo();
		
		String hora = insertarHora.getText().toString();
		String observacionesString = observaciones.getText().toString();
		int datosSpinner[]=ConversorSpinner(spinnerLugar, spinnerSuceso, 
				spinnerAsistencia, spinnerResultado);
		int edadInt = 0,telefonoInt = 0;
		
		if (estaVacioIncidencias()==true){
			Toast.makeText(getApplicationContext(), "Algún campo en blanco", 2000).show();
		}else if(hora.isEmpty()){
				Toast.makeText(getApplicationContext(),"Inserta una hora", 2000).show();
		}else if (BDhelper.existeParteIncidencias(nombreString, hora)==false){
			hora.concat(":00");
			edadInt =Integer.parseInt(edadString);
			
			if (telefonoString.isEmpty()==false){	
				telefonoInt=Integer.parseInt(telefonoString);
			}else{
				telefonoInt=0;
			}
			 
			BDhelper.InsertarParteIncidencias(nombreString,datephone,esHombre, edadInt, telefonoInt,
					hora, datosSpinner[0], datosSpinner[1], datosSpinner[2], datosSpinner[3], observacionesString);
			Toast.makeText(getApplicationContext(),"Parte realizado correctamente", 2000).show();
			IrMainActivity();
			}else{
			Toast.makeText(getApplicationContext(),"El parte de incidencias ya existe", 2000).show();
			}	
	
			}
	
	private int[] ConversorSpinner(Spinner sp0,Spinner sp1,Spinner sp2, Spinner sp3){
		int[] respuestasSpinner=new int[4] ;
		
			respuestasSpinner[0]=(spinnerLugar.getSelectedItemPosition())+1;
			respuestasSpinner[1]=(spinnerSuceso.getSelectedItemPosition())+1;
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
	
	private boolean valorRadioSexo(){
		boolean hombre=true;
		int radioButtonID = sexo.getCheckedRadioButtonId();
		if(radioButtonID==R.id.Mujer){
			hombre=false;
		}
		return hombre;
	}
	
	public void IrMainActivity(){
    	Intent i = new Intent (this, MainActivity.class);
    	startActivity(i);
    	}
	
	private String getDatePhone(){//DUPLICADO 

	    Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();
	    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	    String formatteDate = df.format(date);
	    return formatteDate;
	}
	private int[] CargarIntent (){
		int[] spinners = new int[4];
		nomApel=getIntent().getStringExtra("NombreApellido");
	    sex=getIntent().getBooleanExtra("Sexo", true);
	    años=getIntent().getStringExtra("Edad");
	    nomApel=getIntent().getStringExtra("NombreApellido");
	    tlf= getIntent().getStringExtra("Telefono");
	    hora= getIntent().getStringExtra("Hora"); 
	    lugar=getIntent().getIntExtra("Lugar", 0);
	    suceso=getIntent().getIntExtra("Suceso", 0);
	    asistencia=getIntent().getIntExtra("Asistencia", 0);
	    resultado=getIntent().getIntExtra("Resultado", 0);
	    obs=getIntent().getStringExtra("Observaciones");
	    
	       if (sex==true){
	        sexo.check(R.id.Hombre);	
	        }else{
	        sexo.check(R.id.Mujer);
	        }
	        edad.setText(años);
	        nombreApellido.setText(nomApel);
	        insertarHora.setText(hora);
	      if (tlf.equals("0")!=true){
	    	   telefono.setText(tlf);
	       }
	      	lugar=lugar-1;
		    suceso= suceso -1;
		    asistencia=asistencia-1;
		    resultado=resultado-1;
		    spinners[0]=lugar;
		    spinners[1]=suceso;
		    spinners[2]=asistencia;
		    spinners[3]=resultado;
		    observaciones.setText(obs);
	      return spinners;
	}
}
