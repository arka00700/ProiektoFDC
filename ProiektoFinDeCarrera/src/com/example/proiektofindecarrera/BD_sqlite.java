package com.example.proiektofindecarrera;

import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BD_sqlite extends SQLiteOpenHelper {
	private static BD_sqlite miLaBD;
	String crearUsuarios = "CREATE TABLE Usuarios (User TEXT PRIMARY KEY NOT NULL, Password TEXT NOT NULL," +
			"Playa TEXT NOT NULL,Recurso SMALLINT NOT NULL)";
	String crearMedicamentos = "CREATE TABLE Medicamentos (Nombre TEXT PRIMARY KEY NOT NULL," +
			"Indicaciones TEXT NOT NULL)";
	String crearPartesIncidencias = "CREATE TABLE ParteIncidencias (NombreApellido TEXT NOT NULL," +
			"Sexo BOOLEAN NOT NULL,Edad TINYINT NOT NULL,Telefono INT,Hora TIME NOT NULL,Lugar TINYINT NOT NULL," +
			"Suceso TINYINT NOT NULL,Asistencia TINYINT NOT NULL,Resultado TINYINT NOT NULL," +
			"Observaciones VARCHAR(200))";
	String crearPartesDiarios = "CREATE TABLE ParteDiarios (FechaDiarios DATE NOT NULL," +
			"PlayaDiarios TEXT NOT NULL,RecursoHumanos TEXT NOT NULL," +
			"CalidadAgua TINYINT NOT NULL,Hora TIME NOT NULL,TemperaturaAgua TYNYINT NOT NULL," +
			"Grados11 TINYINT,Hora11 TIME,Grados13 TINYINT,Hora13 TIME,Grados15 TINYINT,Hora15 TIME," +
			"Grados17 TINYINT,Hora17 TIME,Grados19 TINYINT,Hora19 TIME,Hora20 TIME," +
			"Bandera TEXT NOT NULL,BalizaLugar TEXT,BalizaHora TIME,CartelLugar TEXT," +
			"CartelHora TIME,Orgánico TINYINT,Papel TINYINT,Plástico TINYINT,Incidencias VARCHAR(200))";
	
	//CONSTRUCTORES (CREAR Y EL SEGUNDO PARA MODIFICAR PASANDO LA VERSION)
	public BD_sqlite(Context context) {
		super(context, "BD.sqlite", null, 1);
		
	}
	private BD_sqlite (Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
	}
	//CONSTRUCTOR PARA FRAGMENT_LIST
	@SuppressLint("NewApi") 
	public BD_sqlite(FragmentLista fl) {
		super(fl.getActivity(),"BD.sqlite", null, 1);
	}
	
	public static BD_sqlite getMiBD(Context context){
		if (miLaBD==null){
			miLaBD = new BD_sqlite(context,"BD.sqlite", null, 1);
		}
		return miLaBD;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(crearUsuarios);
		db.execSQL(crearMedicamentos);
		db.execSQL(crearPartesIncidencias);
		db.execSQL(crearPartesDiarios);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion>oldVersion){
			db.execSQL("DROP TABLE IF EXISTS Usuarios");
			db.execSQL("DROP TABLE IF EXISTS Medicamentos");
			db.execSQL("DROP TABLE IF EXISTS ParteIncidencias");
			db.execSQL("DROP TABLE IF EXISTS ParteDiarios");
			onCreate(db);
			}
	
	}
	public void escribir(){
		this.getWritableDatabase();
	}
	
	public void leer(){
		this.getReadableDatabase();
	}
	
	public void cerrar(){
		this.close();
	}

	public void insertarUsuarios(String usr, String pass,String playa,int recurso){
		ContentValues valores = new ContentValues();
		valores.put("User",usr);
		valores.put("Password",pass);
		valores.put("Playa", playa);
		valores.put("Recurso",recurso);
		this.getWritableDatabase().insert("Usuarios", null, valores);
	}
	public Cursor leerIndicaciones (String md){
		String sql = "SELECT Indicaciones FROM Medicamentos WHERE Nombre='"+md+"'";
		return this.getWritableDatabase().rawQuery(sql, null);
		
	}
	
	public void insertarMedicamentos (String nombre, String indicaciones){
		ContentValues valores = new ContentValues();
		valores.put("Nombre",nombre);
		valores.put("Indicaciones",indicaciones);
		this.getWritableDatabase().insert("Medicamentos", null, valores);
	}
	public Cursor leerMedicamentos(){
		String sql="SELECT Nombre FROM Medicamentos";
		return this.getReadableDatabase().rawQuery(sql, null);
		/*String nombres[]= new String[c.getCount()];
		c.moveToFirst();
		int iNombre;
		int contador =0;
		iNombre = c.getColumnIndex("Nombre");
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			nombres[contador]=c.getString(iNombre);
		}*/
	}
	public String playaUsuario(String usr){
		String result = null;
		String sql ="SELECT Playa FROM Usuarios WHERE User='"+usr+"'";
		Cursor c = this.getReadableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		int p;
		p=c.getColumnIndex("Playa");
		result=c.getString(p);
		return result;
	}
	
	public Boolean existeUsuario(String usr){
		Boolean estausuario=true;
		String sql = "SELECT * FROM Usuarios WHERE User='"+usr+"'";
		Cursor c= this.getReadableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		if(c.getCount()<=0){
			estausuario = false;
		}
		return estausuario;
	}

		public String[] buscarUsuario(String usr){
			String result []= new String [2];
			String sql = "SELECT * FROM Usuarios WHERE User='"+usr+"'";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			c.moveToFirst();
			
			//String columnas[]={"User","Password"};
			//Cursor c = this.getWritableDatabase().query("Usuarios", columnas, null, null, null, null, null);
			int iu,ip;
		
			iu=c.getColumnIndex("User");
			ip=c.getColumnIndex("Password");
		
		//int contador=0; SI QUISIERAMOS LEER TODA LA TABLA
		//for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result[0]=c.getString(iu);
			result[1]=c.getString(ip);
			//contador++;
		//}
		return result;
	}

		public void InsertarParteIncidencias(String nomApel,Boolean sexo,int edad,int telefono,String hora,
				int lugar,int suceso,int asistencia,int resultado,String observaciones){
			ContentValues valores = new ContentValues();
			valores.put("NombreApellido",nomApel);
			valores.put("Sexo",sexo);
			valores.put("Edad",edad);
			valores.put("Telefono",telefono);
			valores.put("Hora",hora);
			valores.put("Lugar",lugar);
			valores.put("Suceso",suceso);
			valores.put("Asistencia",asistencia);
			valores.put("Resultado",resultado);
			valores.put("Observaciones", observaciones);
			this.getWritableDatabase().insert("ParteIncidencias", null, valores);
		}
		
		public Boolean existeParteIncidencias(String nombreApellido,String hora){
			Boolean estaParte=true;
			String sql = "SELECT * FROM ParteIncidencias WHERE NombreApellido='"+nombreApellido+"'" +
					" AND Hora='"+hora+"'";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			c.moveToFirst();
			if(c.getCount()<=0){
				estaParte = false;
			}
			return estaParte;
		}
		//SIN TERMINAR
		public void insertarParteDiarios(String fecha, String playa,int rcsh,String calidadAgua,
				String hora,int tmpagua,int g11,String h11,int g13,
				String h13,int g15,String h15,int g17,String h17,int g19,
				String h19,String h20,String bandera){
			ContentValues valores = new ContentValues();
			valores.put("FechaDiarios",fecha);
			valores.put("PlayaDiarios",playa);
			valores.put("RecursoHumanos",rcsh);
			valores.put("CalidadAgua",calidadAgua);
			valores.put("Hora",hora);
			valores.put("TemperaturaAgua",tmpagua);
			valores.put("Grados11",g11);
			valores.put("Hora11",h11);
			valores.put("Grados13",g13);
			valores.put("Hora13",h13);
			valores.put("Grados15",g15);
			valores.put("Hora15",h15);
			valores.put("Grados17",g17);
			valores.put("Hora17",h17);
			valores.put("Grados19",g19);
			valores.put("Hora19",h19);
			valores.put("Hora20",h20);
			valores.put("Bandera",bandera);
			this.getWritableDatabase().insert("ParteDiarios", null, valores);
		}
		
		public Boolean existeParteDiarios(String fecha){
		Boolean estaParte = true;
		String sql = "SELECT * FROM ParteDiarios WHERE FechaDiarios'"+fecha+"'";
		Cursor c= this.getReadableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		if(c.getCount()<=0){
			estaParte = false;
		}
		return estaParte;
		}
	
}
