package com.ida.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
    }
    public void registrar(View view){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        descripcion = etDescripcion.getText().toString();
        precio = etPrecio.getText().toString();
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues contentValues = new ContentValues();
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
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        codigo = etCodigo.getText().toString();
        if (!codigo.isEmpty()){
            Cursor cursor = db.rawQuery("select descripcion, precio from articulo where id = "+codigo, null);
            if (cursor.moveToFirst()){
                etDescripcion.setText(cursor.getString(0));
                etPrecio.setText(cursor.getString(1));
            }else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}