package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prueba_nacional_bayronsoto.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//prueba de github
//prueba de 2 de git
public class Actualizar_datos extends AppCompatActivity {
    EditText editTextNombre, editTextEdad, editTextCorreo;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        // Obtener datos actuales del Intent (ajusta según tu estructura)
        Intent intent = getIntent();
        String nombreActual = intent.getStringExtra("nombre_actual");
        int edadActual = intent.getIntExtra("edad_actual", 0);  // 0 es el valor predeterminado si no se encuentra
        String correoActual = intent.getStringExtra("correo_actual");

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);


        editTextNombre.setText(nombreActual);
        editTextEdad.setText(String.valueOf(edadActual));
        editTextCorreo.setText(correoActual);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuevoNombre = editTextNombre.getText().toString().trim();
                int nuevaEdad = Integer.parseInt(editTextEdad.getText().toString().trim());
                String nuevoCorreo = editTextCorreo.getText().toString().trim();




                Intent intent = new Intent(Actualizar_datos.this, Perfil.class);
                intent.putExtra("nombre", nuevoNombre);
                intent.putExtra("edad", nuevaEdad);
                intent.putExtra("correo", nuevoCorreo);
                startActivity(intent);
            }
        });
    }
}