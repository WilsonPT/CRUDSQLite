package com.ida.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btRegistrar, btBuscar, btModificar, btEliminar;
    private EditText etCodigo, etDescripcion, etPrecio;
    private String codigo, descripcion, precio;
    private ConexionSQLiteHelper conn;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        btRegistrar = findViewById(R.id.btRegistrar);
        btBuscar = findViewById(R.id.btBuscar);
        btModificar = findViewById(R.id.btModificar);
        btEliminar = findViewById(R.id.btEliminar);
        etCodigo = findViewById(R.id.etCodigo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        conn = new ConexionSQLiteHelper(this, "administracion", null, 1);
        contentValues = new ContentValues();
    }
    public void registrar(View view){
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        descripcion = etDescripcion.getText().toString();
        precio = etPrecio.getText().toString();
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            contentValues.put("id", codigo);
            contentValues.put("descripcion", descripcion);
            contentValues.put("precio", precio);
            db.insert("articulo", null, contentValues);
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");
            Toast.makeText(this, "Articulo registrado correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Debes llenar todos los campos para realizar el registro", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void buscar(View view){
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        if (!codigo.isEmpty()){
            Cursor cursor = db.rawQuery("select descripcion, precio from articulo where id = "+codigo, null);
            if (cursor.moveToFirst()){
                etDescripcion.setText(cursor.getString(0));
                etPrecio.setText(cursor.getString(1));
            }else {
                etDescripcion.setText("");
                etPrecio.setText("");
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void eliminar(View view){
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        if (!codigo.isEmpty()){
            int retorno = 0;
            retorno = db.delete("articulo", "id = " + codigo, null);
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");
            if (retorno == 1){
                Toast.makeText(this, "Articulo eliminado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
                Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void modificar(View view){
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        descripcion = etDescripcion.getText().toString();
        precio = etPrecio.getText().toString();
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            contentValues.put("id", codigo);
            contentValues.put("descripcion", descripcion);
            contentValues.put("precio", precio);
            int retorno = 0;
            retorno = db.update("articulo", contentValues, "id = "+ codigo, null);
            if (retorno == 1){
                Toast.makeText(this, "Articulo actualizado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void irReporte(View view){
        Intent intent = new Intent(this, ReporteActivity.class);
        startActivity(intent);
    }

}