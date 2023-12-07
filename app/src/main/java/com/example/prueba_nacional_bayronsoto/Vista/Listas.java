package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.prueba_nacional_bayronsoto.Modelo.Usuario;
import com.example.prueba_nacional_bayronsoto.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Listas extends AppCompatActivity {
    private List<Usuario> listUsuario = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    ListView listV_usuario_Listas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        listV_usuario_Listas = findViewById(R.id.lv_datosUsuarios2);

        inicializarfirebase();
        listarDatos();
    }

    private void listarDatos() {
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUsuario.clear();

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Usuario u = objSnaptshot.getValue(Usuario.class);
                    listUsuario.add(u);
                }

                // Mueve estas líneas fuera del bucle
                arrayAdapterUsuario = new ArrayAdapter<Usuario>(Listas.this, android.R.layout.simple_list_item_1, listUsuario);
                listV_usuario_Listas.setAdapter(arrayAdapterUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores aquí
            }
        });
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}