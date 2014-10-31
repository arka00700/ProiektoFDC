package com.example.proiektofindecarrera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

	 
	public class FragmentLista extends android.support.v4.app.ListFragment{
	
	private String[] sistemas = {"Android","IOS","Ubuntu","MacOX","Windows"};

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//Adaptar a la lista de fragment
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,sistemas));
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
		Toast.makeText(getActivity(), "Has pulsado"+sistemas[position], 2000).show();
	}


}
