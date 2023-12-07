package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba_nacional_bayronsoto.Modelo.Usuario;
import com.example.prueba_nacional_bayronsoto.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Crud extends AppCompatActivity {

    EditText buscar, Id, nombre, edad, correo, contra;

    Button botonbuscar, botonagregar, botonmodificar, volver;

    Usuario usuarioSelect;

    private List<Usuario> listUsuario = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    ListView listV_usuario_Listas_crud;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        buscar = findViewById(R.id.editTextBuscar);
        Id = findViewById(R.id.editTextIdCrud);
        nombre = findViewById(R.id.editTextNombreCrud);
        edad = findViewById(R.id.editTextEdadCrud);
        correo = findViewById(R.id.editTextCorreoCrud);
        contra = findViewById(R.id.editTextContra4);

        botonbuscar = findViewById(R.id.btnBuscar);
        botonagregar = findViewById(R.id.btnAgregarUsuario);

        volver = findViewById(R.id.btnvolver);

        listV_usuario_Listas_crud = findViewById(R.id.Lista_crud_Usuario);


        botonbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario();
            }
        });

        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Antes de configurar el Adapter y el OnItemClickListener, asegúrate de que listUsuario tenga datos.
// ...

        arrayAdapterUsuario = new ArrayAdapter<Usuario>(Crud.this, android.R.layout.simple_list_item_1, listUsuario);
        listV_usuario_Listas_crud.setAdapter(arrayAdapterUsuario);

        listV_usuario_Listas_crud.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSelect = (Usuario) parent.getItemAtPosition(position);
                Id.setText(usuarioSelect.getId());
                nombre.setText(usuarioSelect.getNombre());
                edad.setText(usuarioSelect.getEdad());
                correo.setText(usuarioSelect.getCorreo());
                contra.setText(usuarioSelect.getContraseña());
            }
        });



        inicializarfirebase();
        listarDatos();
    }

    private void listarDatos() {
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUsuario.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    listaUsuario();
                    Usuario u = objSnaptshot.getValue(Usuario.class);
                    listUsuario.add(u);

                    arrayAdapterUsuario = new ArrayAdapter<Usuario>(Crud.this, android.R.layout.simple_list_item_1, listUsuario);
                    listV_usuario_Listas_crud.setAdapter(arrayAdapterUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Crud", "Error al obtener datos de Firebase: " + error.getMessage());
            }
        });


    }

    private void buscarUsuario() {
        String idBuscar = buscar.getText().toString().trim();
        Log.d("Crud", "ID a buscar: " + idBuscar);  // Agregado para verificar el ID


        if (!idBuscar.isEmpty()) {
            // Buscar el usuario en Firebase por su ID
            databaseReference.child("Usuario").child(idBuscar).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // El usuario existe, obtén los datos y llénalos en los EditText
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        if (usuario != null) {
                            Id.setText(usuario.getId() != null ? usuario.getId().toString() : "");
                            nombre.setText(usuario.getNombre() != null ? usuario.getNombre() : "");
                            edad.setText(usuario.getEdad() != null ? usuario.toString() : "");
                            correo.setText(usuario.getCorreo() != null ? usuario.getCorreo() : "");
                            contra.setText(usuario.getContraseña() != null ? usuario.getContraseña() : "");
                        }
                    } else {
                        // El usuario no existe, puedes manejarlo como desees (por ejemplo, mostrar un mensaje)
                        Toast.makeText(Crud.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Manejar errores de lectura desde Firebase
                    Log.e("Crud", "Error al buscar usuario en Firebase: " + error.getMessage());
                }
            });
        } else {
            // Mostrar un mensaje indicando que el campo de búsqueda está vacío
            Toast.makeText(Crud.this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show();
        }
    }

    private void listaUsuario() {
        // Puedes implementar lógica adicional aquí si es necesario
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true); // Mover esta línea al inicio
        databaseReference = firebaseDatabase.getReference();
    }

    private void agregar() {
        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id.getText().toString().trim().isEmpty()
                        || nombre.getText().toString().trim().isEmpty()
                        || edad.getText().toString().trim().isEmpty()
                        || correo.getText().toString().trim().isEmpty()
                        || contra.getText().toString().trim().isEmpty()) {

                    Toast.makeText(Crud.this, "Atencion, complete los campos que le restan", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(Id.getText().toString());
                    String nombreUsuario = nombre.getText().toString();
                    int Edad2 = Integer.parseInt(edad.getText().toString());
                    String correoUsuario = correo.getText().toString();
                    String contraseña2 = contra.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("Usuario");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Usuario usuario = new Usuario(id, nombreUsuario, Edad2, correoUsuario, contraseña2);
                            dbref.push().setValue(usuario);
                            Toast.makeText(Crud.this, "Atencion, se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                            Id.setText("");
                            nombre.setText("");
                            edad.setText("");
                            correo.setText("");
                            contra.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Manejar errores de lectura desde Firebase
                            Toast.makeText(Crud.this, "Error al registrar el usuario: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}