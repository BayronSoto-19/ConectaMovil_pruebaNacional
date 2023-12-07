package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba_nacional_bayronsoto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import io.reactivex.rxjava3.annotations.Nullable;

public class Perfil extends AppCompatActivity {
    TextView nombreTextView, edadTextView, correoTextView, contraseñaTextView;
    private Uri imagenSeleccionadaUri;
    private static final int REQUEST_SELECT_IMAGE = 100;
    private ImageView imageView;

    Button btnSeleccionarImagen, btnActualizarDatos, btnMenu;
    private FirebaseAuth mAuth;
    private DatabaseReference usuarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreTextView = findViewById(R.id.nombreTextView);
        edadTextView = findViewById(R.id.edadTextView);
        correoTextView = findViewById(R.id.correoTextView);
        contraseñaTextView = findViewById(R.id.contraseñaTextView);

        btnMenu = findViewById(R.id.btnMnu);
        btnActualizarDatos = findViewById(R.id.btnActualizarPerfil);

        btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear el Intent para ir de Menu a Actualizar_datos
                Intent intent = new Intent(Perfil.this, Actualizar_datos.class);

                // Iniciar la actividad Actualizar_datos
                startActivity(intent);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Menu.class);


                startActivity(intent);

            }
        });






        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            int edad = intent.getIntExtra("edad", 0);
            String correo = intent.getStringExtra("correo");
            String contraseña = intent.getStringExtra("contraseña");

            // Mostrar los datos en los TextViews
            nombreTextView.setText("Nombre: " + nombre);
            edadTextView.setText("Edad: " + edad);
            correoTextView.setText("Correo: " + correo);
            contraseñaTextView.setText("Contraseña: " + contraseña);
        }



        imageView = findViewById(R.id.imageView);
        btnSeleccionarImagen = findViewById(R.id.btnImagen);

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada
            imagenSeleccionadaUri = data.getData();

            // Puedes mostrar la imagen en un ImageView si lo deseas
            imageView.setImageURI(imagenSeleccionadaUri);

            // También puedes trabajar con la URI de la imagen, por ejemplo, subirla a Firebase
            // Implementa la lógica para subir la imagen a tu servidor o almacenamiento en la nube aquí
        }
    }
}
