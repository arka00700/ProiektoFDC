package com.example.proiektofindecarrera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends ActionBarActivity{
	
	private Button bRegistrar;
	private EditText usrId,passId,passId2;
	BD_sqlite BDhelper= new BD_sqlite(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        
      //IDENTIFICACION
        bRegistrar = (Button) findViewById(R.id.rfinalizarregistro);
        usrId = (EditText) findViewById(R.id.rusuario);
		passId = (EditText) findViewById(R.id.rcontraseña);
		passId2 = (EditText) findViewById(R.id.rrepetircontraseña);
		
      //LISTENER Y ABRIR BASE DE DATOS EN MODO ESCRITURA
		bRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//CAPTACION DE DATOS
				
				Registrar();
				
			}
		});
	}
	public void Registrar (){
		String usr = usrId.getText().toString(); 
		String pass = passId.getText().toString();
		String pass2 = passId2.getText().toString();
		
		if (estaVacio()==true){
			Toast.makeText(getApplicationContext(),"Algun campo en blanco", 2000).show();
		}else if (pass.equals(pass2)==false){
				Toast.makeText(getApplicationContext(),"Contraseñas diferentes", 2000).show();
			}else if (BDhelper.existeUsuario(usr)==false){
				BDhelper.insertarUsuarios(usr, pass);
				Toast.makeText(getApplicationContext(),"Registro realizado correctamente", 2000).show();
				lanzarLogin();
				}else{
				
				Toast.makeText(getApplicationContext(),"El usuario ya existe", 2000).show();
				
				
				}	
	
			}
	public boolean estaVacio(){
		boolean vacio = true;
		if(usrId.getText().toString().compareTo("")!=0 && passId.getText().toString().compareTo("")!=0 && passId2.getText().toString().compareTo("")!=0){
			vacio=false;
		}
		return vacio;
	}
	
	/*public boolean existe(){
		boolean existe = true;
		//String x[]=LaBD.getMiBD(getApplicationContext()).leerUsuarios();
		//Toast.makeText(getApplicationContext(),x[0], 2000).show();
		
		return false;
	}*/
	 
	private void lanzarLogin(){
	    	Intent i = new Intent (this, Login.class);
	    	startActivity(i);
	    	}

}


