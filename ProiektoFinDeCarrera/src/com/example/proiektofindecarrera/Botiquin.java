package com.example.proiektofindecarrera;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Botiquin extends ActionBarActivity implements OnQueryTextListener{
	
	
	SearchView searchView; 
	BD_sqlite BDhelper = new BD_sqlite(this);
	FragmentListaMedicamentos medicamentos;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);
        medicamentos= (FragmentListaMedicamentos) getSupportFragmentManager().
        		findFragmentById(R.id.fragment);
        
		medicamentos.mostrarTodosLosMedicamentos();
        searchView= (SearchView) findViewById(R.id.buscarmedicamento); 
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				medicamentos.actualizarLista(query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {			
				medicamentos.actualizarLista(newText);
				return false;
			}
		});
	}
	
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	   // getMenuInflater().inflate(R.menu.main_activity, menu);
	    return true;
	}
}	
