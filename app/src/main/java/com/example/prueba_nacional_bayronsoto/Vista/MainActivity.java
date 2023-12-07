package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba_nacional_bayronsoto.R;

public class MainActivity extends AppCompatActivity {

    Button botonInicio, botonRegistrar, botonEliminar;
    EditText campoCorreo, campoContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInicio = findViewById(R.id.btnInicio);
        botonRegistrar = findViewById(R.id.btnReg);


        campoCorreo = findViewById(R.id.txtCorreo);
        campoContraseña = findViewById(R.id.txtContraseña);


        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = campoCorreo.getText().toString().trim();
                String contraseña= campoContraseña.getText().toString().trim();

                if(correo.equals("") || contraseña.equals("")){
                    Toast.makeText(MainActivity.this, "Debe de rellenar los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(MainActivity.this, mensaje.class));
                }
            }
        });



        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });


    }
}