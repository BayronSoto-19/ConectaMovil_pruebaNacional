package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

public class mensaje extends AppCompatActivity {

    private List<Usuario> listUsuario = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    EditText Nombre, Edad, Correo, Contraseña2;
    Button registrar, limpiar;

    ListView listV_usuario;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Usuario usuarioSelect;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);



        ImageButton imageButton = findViewById(R.id.imgPerfil);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mensaje.this, Perfil.class);

                startActivity(intent);
            }
        });


        listV_usuario = findViewById(R.id.lv_datosUsuarios);

        inicializarfirebase();
        listarDatos();

        listV_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSelect = (Usuario) parent.getItemAtPosition(position);

                Intent intent = new Intent(mensaje.this, Perfil.class);
                startActivity(intent);
            }
        });
    }


    private void listarDatos() {

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listUsuario.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    listaUsuario();
                    Usuario u = objSnaptshot.getValue(Usuario.class);
                    listUsuario.add(u);

                    arrayAdapterUsuario = new ArrayAdapter<Usuario>(mensaje.this, android.R.layout.simple_list_item_1, listUsuario);
                    listV_usuario.setAdapter(arrayAdapterUsuario);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


            private void listaUsuario() {
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