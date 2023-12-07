package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prueba_nacional_bayronsoto.R;

import java.util.List;

public class Menu extends AppCompatActivity {
    private Button Lista, crud, perfil, mensaje,cerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        perfil = findViewById(R.id.btnVerPerfil);
        crud = findViewById(R.id.btnCrudUsuarios);
        Lista = findViewById(R.id.btnListaUsuarios);
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        mensaje = findViewById(R.id.btnMensajeria);
        // Vincula los demás botones aquí...

        // Configurar eventos de clic
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad VerPerfil
                Intent intent = new Intent(Menu.this, Perfil.class);
                startActivity(intent);
            }
        });
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Crud.class);
                startActivity(intent);
            }
        });
        Lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Listas.class);
                startActivity(intent);
            }
        });

        mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Mensajeria.class);
                startActivity(intent);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });
    }

}