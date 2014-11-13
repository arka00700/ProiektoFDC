package com.example.proiektofindecarrera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{
	
	private TextView bienvenido;
	private Button btbotiquin,btcrearpartes,btmodificarpartes;
	//MENU LATERAL
	
   // private DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*    
      //IDENTIFICACION DE ELEMENTOS
        	//Menu lateral
        final String[] mPlanetTitles = {"Opcion 1","Opcion 2","Opcion 3"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.menulateral, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(MainActivity.this, "Pulsado: " + mPlanetTitles[position], Toast.LENGTH_SHORT).show();
				mDrawerLayout.closeDrawers();
			}
		
        
        });*/
        
      	btbotiquin = (Button) findViewById(R.id.Botiquín);
      	btcrearpartes= (Button) findViewById(R.id.Crearpartes);
      	btmodificarpartes= (Button) findViewById(R.id.Modificarpartes);
      	bienvenido = (TextView) findViewById(R.id.Bienvenido); 
      	
      	
			
    	
		String usuario =getIntent().getStringExtra("Usuario");
      	bienvenido.setText("Bienvenido "+usuario);
      	
      	btcrearpartes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lanzarPartesCuras();
			}
		});
      	
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
	public void lanzarPartesCuras(){
		Intent i = new Intent (this,ParteIncidencias.class);
		startActivity(i);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
