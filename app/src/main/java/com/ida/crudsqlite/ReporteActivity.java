package com.ida.crudsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.ida.crudsqlite.modelo.Articulo;

import java.util.ArrayList;
import java.util.HashMap;

public class ReporteActivity extends AppCompatActivity {
    private ListView listView;
    private ConexionSQLiteHelper conn;
    private SQLiteDatabase db;
    private ArrayList<Articulo> listaArticulos;
    private ArrayList<String> listaInformacion;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        init();
    }

    private void init(){
        listView = findViewById(R.id.lvArticulos);
        consultarListaArticulos();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener( (adapter, view, pos, l) ->{
            String informacion = "ID: "+ listaArticulos.get(pos).getId() + "\n";
            informacion += "Descripcion: "+ listaArticulos.get(pos).getDescripcion() + "\n";
            informacion += "Precio: "+ listaArticulos.get(pos).getPrecio() + "\n";
            Toast.makeText(this, informacion, Toast.LENGTH_LONG).show();
        });
    }

    private void consultarListaArticulos() {
        conn = new ConexionSQLiteHelper(this, "administracion", null, 1);
        db = conn.getReadableDatabase();
        Articulo articulo = null;
        listaArticulos = new ArrayList<Articulo>();
        Cursor cursor = db.rawQuery("select * from articulo", null);
        while (cursor.moveToNext()){
            articulo = new Articulo();
            articulo.setId(cursor.getString(0));
            articulo.setDescripcion(cursor.getString(1));
            articulo.setPrecio(cursor.getString(2));
            listaArticulos.add(articulo);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i = 0 ; i < listaArticulos.size(); i++){
            listaInformacion.add(listaArticulos.get(i).getId()+ "\t   " + listaArticulos.get(i).getDescripcion());
        }

        if (listaArticulos.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Sin registro de articulos", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

}