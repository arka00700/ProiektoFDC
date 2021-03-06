package com.example.proiektofindecarrera;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registro extends ActionBarActivity{
	
	private Button bRegistrar;
	private EditText usrId,passId,passId2,usrRecurso;
	private Spinner spinnerPlayas;
	BD_sqlite BDhelper= new BD_sqlite(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        
      //IDENTIFICACION
        bRegistrar = (Button) findViewById(R.id.rfinalizarregistro);
        usrId = (EditText) findViewById(R.id.rusuario);
		passId = (EditText) findViewById(R.id.rcontraseña);
		passId2 = (EditText) findViewById(R.id.rrepetircontraseña);
		spinnerPlayas = (Spinner) findViewById(R.id.spinnerplaya);
		usrRecurso=(EditText) findViewById(R.id.numrecurso);
	  //SPINNER
		ArrayAdapter<CharSequence> adapterPlayas = ArrayAdapter.createFromResource(this,
        	     R.array.Playas, android.R.layout.simple_spinner_item);
		
		adapterPlayas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayas.setPrompt("Selecciona un Lugar");
        spinnerPlayas.setAdapter(adapterPlayas);
        
      //LISTENER Y ABRIR BASE DE DATOS EN MODO ESCRITURA
		bRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				Registrar();	
			}});
	}
	public void Registrar (){
		String usr = usrId.getText().toString(); 
		String pass = passId.getText().toString();
		String pass2 = passId2.getText().toString();
		String playa = spinnerPlayas.getSelectedItem().toString();
		String editvalue=usrRecurso.getText().toString();
		Resources res=getResources();
		
		if (estaVacioRegistro()==true){
			Toast.makeText(getApplicationContext(),res.getString(R.string.CampoEnBlanco), 2000).show();
		}else if (pass.equals(pass2)==false){
				Toast.makeText(getApplicationContext(),res.getString(R.string.passDiferente), 2000).show();
		}else if(numeroCorrecto()==false){
				Toast.makeText(getApplicationContext(),res.getString(R.string.numRecursoMal), 2000).show();
			}else if (BDhelper.existeUsuario(usr)==false){
				int recurso = Integer.parseInt(editvalue);
				BDhelper.insertarUsuarios(usr, pass,playa,recurso);
				Toast.makeText(getApplicationContext(),res.getString(R.string.registroFinalizado), 2000).show();
				lanzarLogin();
				}else{
				Toast.makeText(getApplicationContext(),res.getString(R.string.usrYaExiste), 2000).show();
				}	
			}
	
	private boolean estaVacioRegistro(){
		boolean vacio = true;
		if(usrId.getText().toString().compareTo("")!=0 && passId.getText().toString().compareTo("")!=0
				&& passId2.getText().toString().compareTo("")!=0 && usrRecurso.getText().toString().compareTo("")!=0){
			vacio=false;
		}
		return vacio;
	}
	public boolean numeroCorrecto(){
		int r=Integer.parseInt(usrRecurso.getText().toString());
		boolean correcto = true;
		if(r<=0||r>=250){
		correcto=false;
		}
		return correcto;
	} 
	private void lanzarLogin(){
	    	Intent i = new Intent (this, Login.class);
	    	startActivity(i);
	    	}

}


