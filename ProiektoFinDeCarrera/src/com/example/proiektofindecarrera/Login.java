package com.example.proiektofindecarrera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends ActionBarActivity {
	private Button btlogin,btregistration;
	private EditText usrId,passId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        final BD_sqlite BDhelper= new BD_sqlite(getApplicationContext());
		
		Log.i(this.getClass().toString(), "Probando login 1");
		//BDhelper.onUpgrade(db, 1, 2);
        btlogin = (Button) findViewById(R.id.login);
        btregistration= (Button) findViewById(R.id.registrarse);
        
        usrId = (EditText) findViewById(R.id.editText1);
		passId = (EditText) findViewById(R.id.editText2);
                
        //AÑADIR LISTENER Y CREAR BASE DE DATOS
		btlogin.setOnClickListener(new OnClickListener() {
	
			@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
				//COMPROVACION DE USER Y PASSWORD CORRECTOS
				String usr = usrId.getText().toString(); 
				String pass = passId.getText().toString();
				if(estaVacio()==true){
					Toast.makeText(getApplicationContext(),"Algun campo en blanco", 2000).show();
				}else if(BDhelper.existeUsuario(usr)==false){
					Toast.makeText(getApplicationContext(),"El usuario no existe", 2000).show();
				}else{
					String comparar[]= new String[2];
					comparar=BDhelper.buscarUsuario(usr);
					if(comparar[0].equals(usr) && comparar[1].equals(pass)){
						lanzarMainActivity();	
					}else if (comparar[0].equals(usr)==false || comparar[1].equals(pass)==false){
						Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos", 2000).show();	
					}
				}
			}
		});
		btregistration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lanzarRegistration();
				
			}
		}); 
    }

   private void lanzarMainActivity(){
    	Intent i = new Intent (Login.this,MainActivity.class);
		i.putExtra("Usuario", usrId.getText()+"");
    	startActivity(i);
    	}

    private void lanzarRegistration(){
    	Intent i = new Intent (this, Registration.class);
    	startActivity(i);
    	}
   

    public boolean estaVacio(){
		boolean vacio = true;
		if(usrId.getText().toString().compareTo("")!=0 && passId.getText().toString().compareTo("")!=0){
			vacio=false;
		}
		return vacio;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
