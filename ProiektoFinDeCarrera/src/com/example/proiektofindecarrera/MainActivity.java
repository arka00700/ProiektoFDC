package com.example.proiektofindecarrera;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{
	
	//private TextView bienvenido;
	//private Button btbotiquin,btcrearpartes,btmodificarpartes;
	//MENU LATERAL
	private DrawerLayout drawerLayout;
    private ListView navList;
    private CharSequence mTitle;
    private ActionBarDrawerToggle drawerToggle;
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      /*Intent de nombre del usuario introducido en el registro
        String usuario =getIntent().getStringExtra("Usuario");
      	bienvenido.setText("Bienvenido "+usuario);*/
      	
      /*btbotiquin = (Button) findViewById(R.id.Botiquín);
      	btcrearpartes= (Button) findViewById(R.id.Crearpartes);
      	btmodificarpartes= (Button) findViewById(R.id.Modificarpartes);
      	bienvenido = (TextView) findViewById(R.id.Bienvenido);*/ 
      
      	mTitle = getTitle();
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navList = (ListView) findViewById(R.id.left_drawer);
        
        //Cargar opcion menu lateral
        final String[] names = getResources().getStringArray(R.array.OpcionesMenuLateral);
		
        // Set previous array as adapter of the list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
        		R.string.open_drawer,R.string.close_drawer);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        
      	/*btcrearpartes.setOnClickListener(new OnClickListener() {
			
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
      			}); */
	}
	//Fuera del ONCREATE
	
	public void onDrawerClosed(View view) {
        getSupportActionBar().setTitle(mTitle);
        // creates call to onPrepareOptionsMenu()
        supportInvalidateOptionsMenu();
    }
	
	public void onDrawerOpened(View drawerView) {
        getSupportActionBar().setTitle("Selecciona una opción");
        // creates call to onPrepareOptionsMenu()
        supportInvalidateOptionsMenu();
    }

	private class DrawerItemClickListener implements
    ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    	switch (position){
    	case 0://Crear Partes
    		lanzarPartesCuras();
    	break;
    	case 1://Modificador de partes
    		Toast.makeText(getApplicationContext(),"Modificar partes sin hacer...", 2000).show();
    	break;
    	case 2://Botiquin
    		lanzarBotiquin();
    	break;
    	default:
    		System.out.println("Error");
    		}
		}
	}
	/*private void selectedItem(int pos){
		mTitle=getResources().getStringArray(R.array.OpcionesMenuLateral)[pos].toString();
		
		
	}*/
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
	  @Override
	public void onConfigurationChanged(Configuration newConfig) {
	        // Called by the system when the device configuration changes while your
	        // activity is running
	    super.onConfigurationChanged(newConfig);
	    drawerToggle.onConfigurationChanged(newConfig);
	 }

	public void lanzarBotiquin(){
		Intent i = new Intent (this,Botiquin.class);
		startActivity(i);
	}
	public void lanzarPartesCuras(){
		Intent i = new Intent (this,ParteIncidencias.class);
		startActivity(i);
	}
	//MENU ALTO
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main_activity, menu);
	    return true;
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        // view
        boolean drawerOpen = drawerLayout.isDrawerOpen(navList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
	}
}
