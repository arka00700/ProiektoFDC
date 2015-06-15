package com.example.proiektofindecarrera;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
        
        //Spiners de opciones
        spinnerLugar = (Spinner) findViewById(R.id.spinnerlugar);
        spinnerSuceso = (Spinner) findViewById(R.id.spinnersuceso);
        spinnerAsistencia = (Spinner) findViewById(R.id.spinnerAsistencia);
        spinnerResultado = (Spinner) findViewById(R.id.spinnerResultado);
        sexo= (RadioGroup)findViewById(R.id.sexoradiogroup);
       
        //CARGAR EL INTENT si es para modificar un parte ya existente
        if(getIntent().getStringExtra("NombreApellido")!=null){
        cargarSpinners=crgIntenInc();
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
		String telefonoString = telefono.getText().toString();
		String datephone = getDatePhone();
		String horaOld = null;
		String horaNew=insertarHora.getText().toString();
		if(getIntent().getStringExtra("Hora")!=null){
			datephone=getIntent().getStringExtra("FechaIncidencias");
			horaOld =getIntent().getStringExtra("Hora");
		}
		boolean esHombre=valorRadioSexo();
		String observacionesString = observaciones.getText().toString();
		int datosSpinner[]=ConversorSpinner(spinnerLugar, spinnerSuceso, 
				spinnerAsistencia, spinnerResultado);
		int edadInt = 0,telefonoInt = 0;
		Resources res=getResources();
		if (estaVacioIncidencias()==true){
			Toast.makeText(getApplicationContext(), res.getString(R.string.CampoEnBlanco), 2000).show();
		}else if(horaNew.isEmpty()){
				Toast.makeText(getApplicationContext(),res.getString(R.string.insertarHora), 2000).show();
				//HAY UNA PERSONA QUE SE LLAMA IGUAL Y YA HA SIDO ATENTIDA HOY
		}else if(BDhelper.existeParteIncidenciaNombre(nombreString,datephone) && getIntent().getStringExtra("Hora")==null){ 
			Toast.makeText(getApplicationContext(),res.getString(R.string.personaAtendida), 2000).show();
			
			//SE HA CLICADO DESDE EL MAIN PERO NO SE HA MODIFICADO LA HORA Y EL NOMBRE
		}else if (BDhelper.existeParteIncidencias(datephone,horaOld) && getIntent().getStringExtra("Hora")!=null){
				alertaSobreescritura();
		
		}else if (BDhelper.existeParteIncidencias(datephone, horaNew)==false){
			horaNew.concat(":00");
			edadInt =Integer.parseInt(edad.getText().toString());
			
			if (telefonoString.isEmpty()==false){	
				telefonoInt=Integer.parseInt(telefonoString);
			}else{
				telefonoInt=0;
			}
			 
			BDhelper.InsertarParteIncidencias(nombreString,datephone,esHombre, edadInt, telefonoInt,
					horaNew, datosSpinner[0], datosSpinner[1], datosSpinner[2], datosSpinner[3], observacionesString);
			BDhelper.cerrar();
			Toast.makeText(getApplicationContext(),res.getString(R.string.TodoOk), 2000).show();
			irMainActivity();
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
	
	public void irMainActivity(){
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
	private int[] crgIntenInc (){
		int[] spinners = new int[4];
	    sex=getIntent().getBooleanExtra("Sexo", true);
	    tlf= getIntent().getStringExtra("Telefono");
	    hora= getIntent().getStringExtra("Hora"); 
	    lugar=getIntent().getIntExtra("Lugar", 0);
	    suceso=getIntent().getIntExtra("Suceso", 0);
	    asistencia=getIntent().getIntExtra("Asistencia", 0);
	    resultado=getIntent().getIntExtra("Resultado", 0);
	    
	       if (sex==true){
	        sexo.check(R.id.Hombre);	
	        }else{
	        sexo.check(R.id.Mujer);
	        }
	        edad.setText(getIntent().getStringExtra("Edad"));
	        nombreApellido.setText(getIntent().getStringExtra("NombreApellido"));
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
		    observaciones.setText(getIntent().getStringExtra("Observaciones"));
	      return spinners;
	}
	public void alertaSobreescritura(){
		AlertDialog.Builder alertaSobEsc = new AlertDialog.Builder(ParteIncidencias.this);
		alertaSobEsc.setTitle("Alerta de modificacion");
		alertaSobEsc.setMessage("¿Quiere guardar el parte ya existente con las nuevas modificaciones?");
		alertaSobEsc.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BDhelper.borrarIncidenciaPorNombreyFecha(getIntent().getStringExtra("NombreApellido")
						,getIntent().getStringExtra("FechaIncidencias"));
						guardarIncidencia();
			}
		});
		alertaSobEsc.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertaSobEsc.create();
		alertaSobEsc.show();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.guardar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
       
        return super.onPrepareOptionsMenu(menu);
    }
	public boolean onOptionsItemSelected(MenuItem item) {
       //Si se selecciona algun boton del action bar
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.actionBarGuardar:
			if(getIntent().getStringExtra("Hora")!=null){
				alertaSobreescritura();
			}else{
				guardarIncidencia();
			}
		break;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
	}
}