package com.example.proiektofindecarrera;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

	 
	@SuppressLint("NewApi") 
	public class FragmentLista extends android.support.v4.app.ListFragment{
	
	private ArrayList<String> sistemas,actsistemas;
	private SearchView search;
	View myFragmentView;
	
	public void onCreate(Bundle savedInstanceState){
		sistemas= new ArrayList<String>();
		super.onCreate(savedInstanceState);
		//Adaptar a la lista de fragment
		setListAdapter(new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1,sistemas));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment, container, false);
	}

	
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getActivity(), "Has pulsado "+ sistemas.get(position), 2000).show();
		Intent i = new Intent(getActivity(),IndicacionesMedicamento.class);
		i.putExtra("NombreMedicamento", sistemas.get(position));
		startActivity(i);
	}
	
	public void mostrarTodosLosMedicamentos(){
		Cursor c = BD_sqlite.getMiBD(getActivity()).leerMedicamentos();
		sistemas.clear();
		String nombre="";
		if(c.moveToFirst()){
			do{
				nombre=c.getString(0);
				sistemas.add(nombre);
			}while(c.moveToNext());
		}
	}
	//RECIBIR EL TEXTO METIDO EN EL SEARCHVIEW Y HACER CONSULTA PARA ACTUALIZAR SISTEMAS
	public void cambiarlista(){
		
		
		sistemas= new ArrayList<String>();
		sistemas.add("hola");
		sistemas.add("hwwe23");
		//Adaptar a la lista de fragment
		setListAdapter(new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1,sistemas));
		}
	
	public void actualizarLista(String texto){
		sistemas= new ArrayList<String>();
		Cursor c = BD_sqlite.getMiBD(getActivity()).leerMedicamentos();
		int largo = texto.length();
		Toast.makeText(getActivity(), "Largura:"+ largo, 2000).show();
		String nombre="";
		if(c.moveToFirst()){
			do{
				nombre=c.getString(0);
				if(nombre.length()>=largo){
				if(nombre.substring(0, largo).equalsIgnoreCase(texto))
				sistemas.add(nombre);
				}
			}while(c.moveToNext());
		}
		setListAdapter(new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1,sistemas));
	}
		
		
}
