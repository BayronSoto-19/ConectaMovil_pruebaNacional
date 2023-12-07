package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {

    private List<Usuario> listUsuario = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    EditText Id, Nombre, Edad, Correo, Contraseña2;
    Button registrar, limpiar;

    ListView listV_usuario;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Id = findViewById(R.id.txtId);
        Nombre = findViewById(R.id.txtnombre);
        Edad = findViewById(R.id.txtedad);
        Correo = findViewById(R.id.txtcorreo2);
        Contraseña2 = findViewById(R.id.txtcontraseña2);

        registrar = findViewById(R.id.btnActualizarPerfil);
        limpiar = findViewById(R.id.btnAgregarImg);

        listV_usuario = findViewById(R.id.lv_datosUsuarios);

        inicializarfirebase();
        listarDatos();
        registrar();
        limpiar();




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

                    arrayAdapterUsuario = new ArrayAdapter<Usuario>(Register.this, android.R.layout.simple_list_item_1, listUsuario);
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

    private void registrar() {
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id.getText().toString().trim().isEmpty()
                        || Nombre.getText().toString().trim().isEmpty()
                        || Edad.getText().toString().trim().isEmpty()
                        || Correo.getText().toString().trim().isEmpty()
                        || Contraseña2.getText().toString().trim().isEmpty()) {

                    Toast.makeText(Register.this, "Atencion, complete los campos que le restan", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(Id.getText().toString());
                    String nombre = Nombre.getText().toString();
                    int Edad2 = Integer.parseInt(Edad.getText().toString());
                    String correo = Correo.getText().toString();
                    String contraseña2 = Contraseña2.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference(Usuario.class.getSimpleName());

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Usuario usuario = new Usuario(id, nombre, Edad2, correo, contraseña2);
                            dbref.push().setValue(usuario);
                            Toast.makeText(Register.this, "Atencion, se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                            // Crear un Intent para pasar datos a la actividad del perfil
                            Intent intent = new Intent(Register.this, Perfil.class);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("edad", Edad2);
                            intent.putExtra("correo", correo);
                            intent.putExtra("contraseña", contraseña2);
                            startActivity(intent);

                            // Limpiar los campos después de agregar el usuario
                            Id.setText("");
                            Nombre.setText("");
                            Edad.setText("");
                            Correo.setText("");
                            Contraseña2.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Manejar errores
                        }
                    });
                }
            }
        });
    }


    private void limpiar(){
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id.setText("");
                Nombre.setText("");
                Edad.setText("");
                Correo.setText("");
                Contraseña2.setText("");
            }
        });
    }


}


