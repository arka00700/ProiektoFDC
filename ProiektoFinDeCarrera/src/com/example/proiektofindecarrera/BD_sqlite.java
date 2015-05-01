package com.example.proiektofindecarrera;

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
			"Playa TEXT NOT NULL,Recurso SMALLINT NOT NULL,conectado BOOLEAN)";
	
	String crearMedicamentos = "CREATE TABLE Medicamentos (Nombre TEXT PRIMARY KEY NOT NULL," +
			"Indicaciones TEXT NOT NULL)";
	
	String crearPartesIncidencias = "CREATE TABLE ParteIncidencias (NombreApellido TEXT NOT NULL," +
			"FechaIncidencias DATE NOT NULL,Sexo BOOLEAN NOT NULL,Edad TINYINT NOT NULL," +
			"Telefono INT,Hora TIME NOT NULL,Lugar TINYINT NOT NULL," +
			"Suceso TINYINT NOT NULL,Asistencia TINYINT NOT NULL,Resultado TINYINT NOT NULL," +
			"Observaciones VARCHAR(200))";//AÑADIR LA PLAYA
	
	String crearPartesDiarios = "CREATE TABLE ParteDiarios (FechaDiarios DATE NOT NULL," +
			"PlayaDiarios TEXT NOT NULL,RecursoHumanos TEXT NOT NULL," +
			"CalidadAgua TINYINT NOT NULL,Hora TIME NOT NULL,TemperaturaAgua TYNYINT NOT NULL," +
			"Grados11 TINYINT,Hora11 TIME,Grados13 TINYINT,Hora13 TIME,Grados15 TINYINT,Hora15 TIME," +
			"Grados17 TINYINT,Hora17 TIME,Grados19 TINYINT,Hora19 TIME,Hora20 TIME," +
			"Bandera0 TEXT NOT NULL,horaBandera0 TEXT NOT NULL,Bandera1 TEXT,horaBandera1 TEXT" +
			",Bandera2 TEXT,horaBandera2 TEXT,Bandera3 TEXT,horaBandera3 TEXT" +
			",Bandera4 TEXT,horaBandera4 TEXT,BalizaLugar0 TEXT,BalizaHora0 TIME,BalizaLugar1 TEXT" +
			",BalizaHora1 TEXT,BalizaLugar2 TEXT,BalizaHora2 TEXT,BalizaLugar3 TEXT,BalizaHora3 TEXT" +
			",BalizaLugar4 TEXT,BalizaHora4 TEXT,CartelLugar0 TEXT,CartelHora0 TIME" +
			",CartelLugar1 TEXT,CartelHora1 TIME,CartelLugar2 TEXT,CartelHora2 TIME" +
			",CartelLugar3 TEXT,CartelHora3 TIME,CartelLugar4 TEXT,CartelHora4 TIME" +
			",torre0 TEXT,puesto0 TIME,orilla0 TEXT,horaRot0 TIME,torre1 TEXT,puesto1 TIME,orilla1 TEXT,horaRot1 TIME" +
			",torre2 TEXT,puesto2 TIME,orilla2 TEXT,horaRot2 TIME,torre3 TEXT,puesto3 TIME,orilla3 TEXT,horaRot3 TIME" +
			",torre4 TEXT,puesto4 TIME,orilla4 TEXT,horaRot4 TIME,Orgánico TINYINT,Papel TINYINT,Plástico TINYINT,Incidencias VARCHAR(200))";
	
	String crearPartesPulseras = "CREATE TABLE PartePulseras (NumPulsera INT NOT NULL,NumContacto TEXT NOT NULL," +
			"NomMenor TEXT NOT NULL,NomResponsable TEXT NOT NULL)";
	//CONSTRUCTORES (CREAR Y EL SEGUNDO PARA MODIFICAR PASANDO LA VERSION)
	public BD_sqlite(Context context) {
		super(context, "BD.sqlite", null, 1);
		
	}
	private BD_sqlite (Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
	}
	//CONSTRUCTOR PARA FRAGMENT_LIST
	@SuppressLint("NewApi") 
	public BD_sqlite(FragmentListaMedicamentos fl) {
		super(fl.getActivity(),"BD.sqlite", null, 1);
	}
	
	public BD_sqlite(FragmentListaDiarios fl) {
		super(fl.getActivity(),"BD.sqlite", null, 1);
	}
	public BD_sqlite(FragmentListaIncidencias fl) {
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
		db.execSQL(crearPartesPulseras);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion>oldVersion){
			db.execSQL("DROP TABLE IF EXISTS Usuarios");
			db.execSQL("DROP TABLE IF EXISTS Medicamentos");
			db.execSQL("DROP TABLE IF EXISTS ParteIncidencias");
			db.execSQL("DROP TABLE IF EXISTS ParteDiarios");
			db.execSQL("DROP TABLE IF EXISTS PartePulseras");
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
	public void añadirconexion(String usr){
		String sql="UPDATE Usuarios SET conectado='0'";
		String sql2 ="UPDATE Usuarios SET conectado='1' WHERE User='"+usr+"'";
		this.getWritableDatabase().execSQL(sql);
		this.getWritableDatabase().execSQL(sql2);
	}
	public String[] usrConectado(){
		String result []= new String [2];
		String sql ="SELECT User,Playa FROM Usuarios WHERE conectado='1'";
		Cursor c=this.getWritableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		int iu,ip;
		iu=c.getColumnIndex("User");
		ip=c.getColumnIndex("Playa");
		result[0]=c.getString(iu);
		result[1]=c.getString(ip);
		return result;
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

		public void InsertarParteIncidencias(String nomApel,String fecha,Boolean sexo,int edad,int telefono,String hora,
				int lugar,int suceso,int asistencia,int resultado,String observaciones){
			ContentValues valores = new ContentValues();
			valores.put("NombreApellido",nomApel);
			valores.put("FechaIncidencias",fecha);
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
		
		public Cursor recuperarIncidencias(String NombreApellido,String fecha){
			String sql ="SELECT NombreApellido,FechaIncidencias,Edad,Sexo,Telefono,Hora," +
					"Lugar,Suceso,Asistencia,Resultado,Observaciones FROM ParteIncidencias WHERE NombreApellido='"+NombreApellido+"' AND FechaIncidencias='"+fecha+"'";
			return this.getReadableDatabase().rawQuery(sql, null);
		}
		
		public Cursor recuperarDiarios(String FechaDiarios){
			String sql ="SELECT FechaDiarios,RecursoHumanos,CalidadAgua,Hora,TemperaturaAgua," +
					"Grados11,Hora11,Grados13,Hora13,Grados15,Hora15,Grados17,Hora17,Grados19,Hora19," +
					"Hora20,Bandera0,horaBandera0,Bandera1,horaBandera1,Bandera2,horaBandera2,Bandera3,horaBandera3," +
					"Bandera4,horaBandera4,BalizaLugar0,BalizaHora0,BalizaLugar1,BalizaHora1" +
					",BalizaLugar2,BalizaHora2,BalizaLugar3,BalizaHora3,BalizaLugar4,BalizaHora4" +
					",CartelLugar0,CartelHora0,CartelLugar1,CartelHora1,CartelLugar2,CartelHora2" +
					",CartelLugar3,CartelHora3,CartelLugar4,CartelHora4,torre0,puesto0,orilla0,horaRot0" +
					",torre1,puesto1,orilla1,horaRot1,torre2,puesto2,orilla2,horaRot2" +
					",torre3,puesto3,orilla3,horaRot3,torre4,puesto4,orilla4,horaRot4,Orgánico,Papel," +
					"Plástico,Incidencias FROM ParteDiarios WHERE FechaDiarios='"+FechaDiarios+"'";
			return this.getReadableDatabase().rawQuery(sql, null);
		}
		//Cojemos los datos por fecha (mismo orden que se introdujeron)
		public Cursor leerPartePorUltMod(){
			String sql="SELECT NombreApellido,FechaIncidencias FROM ParteIncidencias";
			return this.getReadableDatabase().rawQuery(sql, null);
		}
		public Cursor leerPartePorTitulo(){
			String sql="SELECT NombreApellido,FechaIncidencias FROM ParteIncidencias ORDER BY NombreApellido";
			return this.getReadableDatabase().rawQuery(sql, null);	
		}
		public Cursor leerPartePorFecha(){//QUITAR LA BANDERA DESPUES
			String sql="SELECT NombreApellido,FechaIncidencias FROM ParteIncidencias ORDER BY FechaIncidencias ASC";
			return this.getReadableDatabase().rawQuery(sql, null);
		}
		public Cursor leerParteDiarios(){//QUITAR LA BANDERA DESPUES
			String sql="SELECT FechaDiarios,PlayaDiarios FROM ParteDiarios ORDER BY FechaDiarios";
			return this.getReadableDatabase().rawQuery(sql, null);
		}
		//SIN TERMINAR (DATOS DINAMICOS)
		public void insertarParteDiarios(String fecha, String playa,int rcsh,int calidadAgua,
			String hora,int tmpagua,int g11,String h11,int g13,
			String h13,int g15,String h15,int g17,String h17,int g19,
			String h19,String h20,String bandera0,String horaBandera0,String bandera1
			,String horaBandera1,String bandera2,String horaBandera2,String bandera3
			,String horaBandera3,String bandera4,String horaBandera4,String balizalugar0,
			String balizahora0,String balizalugar1,String balizahora1,String balizalugar2,
			String balizahora2,String balizalugar3,String balizahora3,String balizalugar4,
			String balizahora4,String cartelugar0,String cartelhora0,String cartelugar1,String cartelhora1
			,String cartelugar2,String cartelhora2,String cartelugar3,String cartelhora3
			,String cartelugar4,String cartelhora4,String torre0,String puesto0,String orilla0,String horaRot0
			,String torre1,String puesto1,String orilla1,String horaRot1,String torre2,String puesto2
			,String orilla2,String horaRot2,String torre3,String puesto3,String orilla3,String horaRot3
			,String torre4,String puesto4,String orilla4,String horaRot4,int organico,int papel,
			int plastico,String incidencias){
			
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
			valores.put("Bandera0",bandera0);
			valores.put("horaBandera0",horaBandera0);
			valores.put("Bandera1",bandera1);
			valores.put("horaBandera1",horaBandera1);
			valores.put("Bandera2",bandera2);
			valores.put("horaBandera2",horaBandera2);
			valores.put("Bandera3",bandera3);
			valores.put("horaBandera3",horaBandera3);
			valores.put("Bandera4",bandera4);
			valores.put("horaBandera4",horaBandera4);
			valores.put("BalizaLugar0",balizalugar0);
			valores.put("BalizaHora0",balizahora0);
			valores.put("BalizaLugar1",balizalugar1);
			valores.put("BalizaHora1",balizahora1);
			valores.put("BalizaLugar2",balizalugar2);
			valores.put("BalizaHora2",balizahora2);
			valores.put("BalizaLugar3",balizalugar3);
			valores.put("BalizaHora3",balizahora3);
			valores.put("BalizaLugar4",balizalugar4);
			valores.put("BalizaHora4",balizahora4);
			valores.put("CartelLugar0",cartelugar0);
			valores.put("CartelHora0",cartelhora0);
			valores.put("CartelLugar1",cartelugar1);
			valores.put("CartelHora1",cartelhora1);
			valores.put("CartelLugar2",cartelugar2);
			valores.put("CartelHora2",cartelhora2);
			valores.put("CartelLugar3",cartelugar3);
			valores.put("CartelHora3",cartelhora3);
			valores.put("CartelLugar4",cartelugar4);
			valores.put("CartelHora4",cartelhora4);
			valores.put("torre0",torre0);
			valores.put("puesto0",puesto0);
			valores.put("orilla0",orilla0);
			valores.put("horaRot0",horaRot0);
			valores.put("torre1",torre1);
			valores.put("puesto1",puesto1);
			valores.put("orilla1",orilla1);
			valores.put("horaRot1",horaRot1);
			valores.put("torre2",torre2);
			valores.put("puesto2",puesto2);
			valores.put("orilla2",orilla2);
			valores.put("horaRot2",horaRot2);
			valores.put("torre3",torre3);
			valores.put("puesto3",puesto3);
			valores.put("orilla3",orilla3);
			valores.put("horaRot3",horaRot3);
			valores.put("torre4",torre4);
			valores.put("puesto4",puesto4);
			valores.put("orilla4",orilla4);
			valores.put("horaRot4",horaRot4);
			valores.put("Orgánico",organico);
			valores.put("Papel",papel);
			valores.put("Plástico",plastico);
			valores.put("Incidencias", incidencias);
			this.getWritableDatabase().insert("ParteDiarios", null, valores);
		}
		
		public Boolean existeParteIncidencias(String fecha,String hora){
			Boolean estaParte=true;
			String sql = "SELECT * FROM ParteIncidencias WHERE FechaIncidencias='"+fecha+"'" +
					" AND Hora='"+hora+"'";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			c.moveToFirst();
			if(c.getCount()<=0){
				estaParte = false;
			}
			return estaParte;
		}
		
		public Boolean existeParteIncidenciaNombre(String nom,String fecha){
			Boolean estaParte=true;
			String sql ="SELECT * FROM ParteIncidencias WHERE NombreApellido='"+nom+"' AND FechaIncidencias='"+fecha+"'";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			c.moveToFirst();
			if(c.getCount()<=0){
				estaParte = false;
			}
			return estaParte;
		}
		
		public Boolean existeParteDiarios(String fecha){
		Boolean estaParte = true;
		String sql = "SELECT * FROM ParteDiarios WHERE FechaDiarios='"+fecha+"'";
		Cursor c= this.getReadableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		if(c.getCount()<=0){
			estaParte = false;
		}
		return estaParte;
		}
		
		public void borrarDiarioPorFecha (String fecha){
			String sql ="DELETE FROM ParteDiarios WHERE FechaDiarios='"+fecha+"'";
			this.getWritableDatabase().execSQL(sql);
		}
		
		public void borrarIncidenciaPorNombreyFecha (String nombre,String fecha){
			String sql ="DELETE FROM ParteIncidencias WHERE NombreApellido='"+nombre+"' AND FechaIncidencias='"+fecha+"'";
			this.getWritableDatabase().execSQL(sql);
		}
		//PARTE PULSERAS
		public void insertarPulsera(int numPuls,String numTlfContac,String nomMenor,String nomRespo){
			ContentValues valores = new ContentValues();
			valores.put("NumPulsera",numPuls);
			valores.put("NumContacto",numTlfContac);
			valores.put("NomMenor", nomMenor);
			valores.put("NomResponsable",nomRespo);
			this.getWritableDatabase().insert("PartePulseras", null, valores);
		}
		
		public Boolean existePulsera (int numPuls){
			Boolean estaPuls = true;
			String sql = "SELECT * FROM PartePulseras WHERE NumPulsera='"+numPuls+"'";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			c.moveToFirst();
			if(c.getCount()<=0){
				estaPuls = false;
			}
			return estaPuls;
			}
		
		public void borrarPulsera (int numPulsera){
			String sql ="DELETE FROM PartePulseras WHERE NumPulsera='"+numPulsera+"'";
			this.getWritableDatabase().execSQL(sql);
		}
		public Cursor cargarPulseras (){
			String sql = "SELECT * FROM PartePulseras";
			Cursor c= this.getReadableDatabase().rawQuery(sql, null);
			return c;
		}
}
