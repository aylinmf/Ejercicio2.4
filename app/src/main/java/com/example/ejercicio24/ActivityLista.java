package com.example.ejercicio24;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ejercicio24.Configuraciones.BdFirmas;
import com.example.ejercicio24.Configuraciones.SQLiteConexion;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    SQLiteConexion conexion;

    RecyclerView.Adapter adapter;
    RecyclerView Vista;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DatosFirm> firmList;
    Button btnAggfir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        conexion = new SQLiteConexion(this, BdFirmas.NAME_DATABASE,null,1);

        Vista = (RecyclerView) findViewById(R.id.Vista);
        Vista.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        Vista.setLayoutManager(layoutManager);

        firmList = new ArrayList<>();
        getSignaturess();

        adapter = new AdapterFirm(firmList);
        Vista.setAdapter(adapter);

        btnAggfir = (Button) findViewById(R.id.btnVolver);
        btnAggfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLista.this, ActivityAgregar.class));

            }
        });


    }
    private void getSignaturess(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        DatosFirm Dafirm = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ BdFirmas.TABLE_FIRMA,null);
        while (cursor.moveToNext()){
            Dafirm = new DatosFirm();
            Dafirm.setId(cursor.getInt(0));
            Dafirm.setDescripcion(cursor.getString(1));
            Dafirm.setImagen(cursor.getString(2));

            firmList.add(Dafirm);
        }
    }

}