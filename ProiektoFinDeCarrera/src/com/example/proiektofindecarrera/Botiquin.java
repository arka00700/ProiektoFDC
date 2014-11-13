package com.example.proiektofindecarrera;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Botiquin extends FragmentActivity implements OnQueryTextListener{
	
	private Button btmedicamentonuevo;
	//private SearchView searchView;
	BD_sqlite BDhelper = new BD_sqlite(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);
        FragmentLista medicamentos= (FragmentLista) getSupportFragmentManager().findFragmentById(R.id.fragment);
		medicamentos.mostrarTodosLosMedicamentos();
       // searchView= (SearchView) findViewById(R.id.buscador); 
        btmedicamentonuevo = (Button) findViewById(R.id.insertarmedicamento);
		btmedicamentonuevo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// INSERTAMOS MEDICAMENTOS DE PRUEBA
				BDhelper.insertarMedicamentos("Trombocil", "Anestesico local");
				BDhelper.insertarMedicamentos("Botarel", "Crema de uso local");
				BDhelper.insertarMedicamentos("Reflex", "Dolor muscular");
				BDhelper.insertarMedicamentos("Sintrom", "Anestesico local");
				BDhelper.insertarMedicamentos("Nitrofurantoina", "Antiseptico Urinario");
				BDhelper.insertarMedicamentos("Amikacina", "Aminoglucosido");
				BDhelper.insertarMedicamentos("Raditina", "Antiacido");
			}
		});

	}
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.botiquin, menu);
       MenuItem item = menu.findItem(R.id.search);
       SearchView searchView = (SearchView) item.getActionView();
       searchView.setOnQueryTextListener(this);
       
        return true;
    }
	public boolean onOptionsItemSelected(MenuItem item) {
       
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public boolean onQueryTextChange(String msg) {
		Log.d("Searchbar", msg);
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
