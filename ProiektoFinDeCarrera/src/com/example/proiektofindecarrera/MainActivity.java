package com.example.proiektofindecarrera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{
	
	private TextView bienvenido;
	private Button btbotiquin,btcrearpartes,btmodificarpartes;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //IDENTIFICACION
      	btbotiquin = (Button) findViewById(R.id.Botiquín);
      	btcrearpartes= (Button) findViewById(R.id.Crearpartes);
      	btmodificarpartes= (Button) findViewById(R.id.Modificarpartes);
      	bienvenido = (TextView) findViewById(R.id.Bienvenido); 
      		
      	String usuario =getIntent().getStringExtra("Usuario");
      	bienvenido.setText("Bienvenido "+usuario);
      		
      	btbotiquin.setOnClickListener(new OnClickListener() {
      				
      				@Override
      				public void onClick(View v) {
      					lanzarbotiquin();
      					
      				}
      			}); 	
		} 
	public void lanzarbotiquin(){
		Intent i = new Intent (this,Botiquin.class);
		startActivity(i);
	}


}
