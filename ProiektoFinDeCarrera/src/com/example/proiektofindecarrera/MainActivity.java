package com.example.proiektofindecarrera;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity{
	
	//MENU LATERAL
	private DrawerLayout drawerLayout; //en activity_main.xml layout principal
    private ListView navListLeft,navListRight; // en activity_main id del menu izquierdo
    private CharSequence mTitle; //secuencia para coger titulo de la app
    private ActionBarDrawerToggle drawerToggle,drawerToggleRight; //
    private ArrayList<NavDrawerItem> navDrawerItems,navDrawerItemsRight; //clase navDrawerItem.java como compartimento
    private TypedArray navMenuIcons,navMenuIconsRight; //para cargar iconos del navigation_drawer
    private NavDrawerListAdapter adapter;
    private NavDrawerListAdapterRight adapterRight;
    String playa;
    private Button ordenar;
    BD_sqlite BDhelper= new BD_sqlite(this);
    private FragmentListaIncidencias parteincidencias;
    private FragmentListaDiarios partediarios;
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        parteincidencias= (FragmentListaIncidencias)getSupportFragmentManager().findFragmentById(R.id.fragmentincidencias);
		partediarios = (FragmentListaDiarios) getSupportFragmentManager().findFragmentById(R.id.fragmentdiarios);
        parteincidencias.mostrarPartesPorFecha();
        partediarios.mostrarPartesPorFecha();
		
		/*Intent de nombre del usuario introducido en el registro
        String usuario =getIntent().getStringExtra("Usuario");
      	bienvenido.setText("Bienvenido "+usuario);*/
        playa = getIntent().getStringExtra("Playa"); 
      	mTitle = getTitle();
      	
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navListLeft = (ListView) findViewById(R.id.left_drawer);
        this.navListRight = (ListView) findViewById(R.id.right_drawer);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);//cargar
        navMenuIconsRight= getResources().obtainTypedArray(R.array.nav_drawer_icons_right);
        
        //Cargar opciones Navigation_drawer
        final String[] names = getResources().getStringArray(R.array.OpcionesMenuLateral);//Titulos
        final String[] namesright = getResources().getStringArray(R.array.OpcionesMenuLateralRight);
        
        //MENU LATERAL IZQUIERDO
        navDrawerItems = new ArrayList<NavDrawerItem>(); 
        //con la clase navDrawerItem cargamos (nombre,icono)
        navDrawerItems.add(new NavDrawerItem(names[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(names[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(names[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(names[3], navMenuIcons.getResourceId(3, -1)));
       
        // Metemos en el adapter toda la configuracion con iconos y titulos
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        navListLeft.setAdapter(adapter);//cargamos el adaptador en el menu lateral
        navListLeft.setOnItemClickListener(new DrawerItemClickListenerLeft());
        //listener de cerrar y abrir el navigation menu
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
        		R.string.open_drawer,R.string.close_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        
        //MENU LATERAL DERECHO
        navDrawerItemsRight = new ArrayList<NavDrawerItem>(); 
        navDrawerItemsRight.add(new NavDrawerItem(namesright[0], navMenuIconsRight.getResourceId(0, -1),"VISIBLE"));
        navDrawerItemsRight.add(new NavDrawerItem(namesright[1], navMenuIconsRight.getResourceId(1, -1),"INVISIBLE"));
        navDrawerItemsRight.add(new NavDrawerItem(namesright[2], navMenuIconsRight.getResourceId(2, -1),"INVISIBLE"));
        
        crearAdaptador();
        navListRight.setOnItemClickListener(new DrawerItemClickListenerRight());
        
        drawerToggleRight = new ActionBarDrawerToggle(this, drawerLayout,
        		R.string.open_drawer,R.string.close_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggleRight);
        
        //Listener del boton ordenar
        ordenar=(Button) findViewById(R.id.ordenar);
        ordenar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				drawerLayout.openDrawer(Gravity.RIGHT);
			}
		});
        
	}
	//Fuera del ONCREATE
	
	private void crearAdaptador() {
		
		adapterRight = new NavDrawerListAdapterRight(getApplicationContext(),navDrawerItemsRight);
        navListRight.setAdapter(adapterRight);
		
	}
	
	private void cambiarTicks(int position){
		String visibility=navDrawerItemsRight.get(position).getIconVisible();
        if (visibility.equals("INVISIBLE")){
        	navDrawerItemsRight.get(position).setIconVisible("VISIBLE");
        	switch (position){
        	case 0:
        		navDrawerItemsRight.get(1).setIconVisible("INVISIBLE");
            	navDrawerItemsRight.get(2).setIconVisible("INVISIBLE");
        	break;
        	case 1:
        		navDrawerItemsRight.get(0).setIconVisible("INVISIBLE");
        		navDrawerItemsRight.get(2).setIconVisible("INVISIBLE");
        	break;
        	case 2:
        		navDrawerItemsRight.get(0).setIconVisible("INVISIBLE");
            	navDrawerItemsRight.get(1).setIconVisible("INVISIBLE");
            break;
        	}
        }
        crearAdaptador();
	}

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
	
	//OPCIONES MENU IZQUIERDO
	private class DrawerItemClickListenerLeft implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    	switch (position){
    	case 0://Crear Partes
    		lanzarPartesCuras();
    	break;
    	case 1:
    		lanzarPartesDiarios();
    	break;
    	case 2://Modificador de partes
    		Intent i = new Intent(getApplicationContext(),PartePulseras.class);
    		i=crgIntentPuls(i);
    		startActivity(i);		
    		//---> pasar el intent con las pulseras existentes.
    	break;
    	case 3://Botiquin
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
	//OPCIONES MENU DERECHO
	private class DrawerItemClickListenerRight implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			
    	switch (position){
    	case 0://Ordenar por fecha de creación
            cambiarTicks(position);
			parteincidencias.mostrarPartesPorFecha();
    	break;
    	case 1://Ordenar por titulo
            cambiarTicks(position);
            parteincidencias.mostrarPartesPorTitulo();
    	break;
    	case 2://Ordenar por ultima modificacion
    		cambiarTicks(position);
    		parteincidencias.mostraPorUltimaMod();
    	break;
    	default:
    		System.out.println("Error");
    		}
		}
	}
	public Intent crgIntentPuls(Intent i){
		Cursor c = BDhelper.cargarPulseras();
		int nP,nTC,nM,nR,contador = 0;
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			nP=c.getColumnIndex("NumPulsera");
			nTC=c.getColumnIndex("NumContacto");
			nM=c.getColumnIndex("NomMenor");
			nR=c.getColumnIndex("NomResponsable");
			i.putExtra("numPuls"+contador, c.getString(nP));
			i.putExtra("numTlfContac"+contador, c.getString(nTC));
			i.putExtra("nomMenor"+contador, c.getString(nM));
			i.putExtra("nomRespo"+contador, c.getString(nR));
			contador++;
		}
		i.putExtra("contador", contador);
		return i;
	}
	
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
	public void lanzarPartesDiarios(){
		Intent i = new Intent (this,ParteDiarios.class);
		startActivity(i);
	}
	private void lanzarPartesPulseras() {
		Intent i = new Intent (this,PartePulseras.class);
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
        boolean drawerOpen = drawerLayout.isDrawerOpen(navListLeft);
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
