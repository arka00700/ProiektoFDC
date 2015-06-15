package com.example.proiektofindecarrera;

import java.util.HashMap;
import java.util.Locale;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

	
public class Login extends ActionBarActivity {
	private Button btlogin,btregistration;
	private EditText usrId,passId;
	private TextView registro,eus,cas;
	public String playausr;
	BD_sqlite BDhelper= new BD_sqlite(this);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        
        //PARA ACTUALIZAR LA BASE DE DATOS
		SQLiteDatabase db = BDhelper.getWritableDatabase();
		//Log.i(this.getClass().toString(), "Probando BD");
		BDhelper.onUpgrade(db, 1, 2);
		
        btlogin = (Button) findViewById(R.id.login);
        btregistration= (Button) findViewById(R.id.registrarse);
        
        usrId = (EditText) findViewById(R.id.loginusuario);
		passId = (EditText) findViewById(R.id.loginpass);
        registro=(TextView) findViewById(R.id.registro); 
        eus = (TextView)findViewById(R.id.euskera);
        cas = (TextView)findViewById(R.id.castellano);
        
        //ARRAY DE LENGUAJES
        final HashMap<String, String> hmLocales = new HashMap<String, String>();
        hmLocales.put(getString(R.string.euskera), "eu"); // Euskera
        hmLocales.put(getString(R.string.castellano), "es"); // Castellano
        
        //AÑADIR LISTENER Y CREAR BASE DE DATOS
		btlogin.setOnClickListener(new OnClickListener() {	
			@Override
		public void onClick(View v) {
		
				//COMPROVACION DE USER Y PASSWORD CORRECTOS
				String usr = usrId.getText().toString(); 
				String pass = passId.getText().toString();
				if(estaVacio()==true){
					Resources res=getResources();
					Toast.makeText(getApplicationContext(),res.getString(R.string.CampoEnBlanco), 2000).show();
				}else if(BDhelper.existeUsuario(usr)==false){
					Resources res1=getResources();
					Toast.makeText(getApplicationContext(),res1.getString(R.string.usrNoExiste), 2000).show();
				}else{
					String comparar[]= new String[2];
					comparar=BDhelper.buscarUsuario(usr);
					if(comparar[0].equals(usr) && comparar[1].equals(pass)){
						playausr=BDhelper.playaUsuario(comparar[0]);
						BDhelper.añadirconexion(usr);
						BDhelper.cerrar();
						lanzarMainActivity();	
					}else if (comparar[0].equals(usr)==false || comparar[1].equals(pass)==false){
						Resources res2=getResources();
						Toast.makeText(getApplicationContext(),res2.getString(R.string.noCoinciden), 2000).show();	
					}
				}
			}
		});
		btregistration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btregistration.getBackground();
				lanzarAcercaDe();
			}}); 
		
		registro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lanzarRegistration();	
			}});
		eus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Locale myLocale = new Locale("eu"); 
			    Resources res = getResources(); 
			    DisplayMetrics dm = res.getDisplayMetrics(); 
			    Configuration conf = res.getConfiguration(); 
			    conf.locale = myLocale; 
			    res.updateConfiguration(conf, dm); 
			    Intent i = new Intent (getApplicationContext(), Login.class);
			    startActivity(i); 
			    Toast.makeText(getApplicationContext(), "Euskera hautatuta", 2000).show();		
			}});
		cas.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Locale myLocale = new Locale("es"); 
			    Resources res = getResources(); 
			    DisplayMetrics dm = res.getDisplayMetrics(); 
			    Configuration conf = res.getConfiguration(); 
			    conf.locale = myLocale; 
			    res.updateConfiguration(conf, dm); 
			    Intent i = new Intent (getApplicationContext(), Login.class);
			    startActivity(i); 
			 
				Toast.makeText(getApplicationContext(), "Castellano seleccionado", 2000).show();
			}
		});
    }//FIN ONCREATE
    
   private void lanzarMainActivity(){
    	Intent i = new Intent (Login.this,MainActivity.class);
		i.putExtra("Usuario", usrId.getText()+"");
		i.putExtra("Playa",playausr);
    	startActivity(i);
    	}
  
    private void lanzarRegistration(){
    	Intent i = new Intent (this, Registro.class);
    	startActivity(i);
    	}
    private void lanzarAcercaDe(){
    	Intent i = new Intent (this, AcercaDe.class);
    	startActivity(i);
    	}

    public boolean estaVacio(){
		boolean vacio = true;
		if(usrId.getText().toString().compareTo("")!=0 && passId.getText().toString().compareTo("")!=0){
			vacio=false;
		}
		return vacio;
	}   
}
