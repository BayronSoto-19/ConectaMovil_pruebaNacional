package com.example.prueba_nacional_bayronsoto.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.prueba_nacional_bayronsoto.Modelo.MqttHandler;
import com.example.prueba_nacional_bayronsoto.R;

public class Mensaje extends AppCompatActivity {


    private  static  final  String BROKER_URL = "tcp//your_broker-url:1883";
    private  static  final  String CLIENT_ID = "your-client-id" ;
    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL,CLIENT_ID);
    }

    @Override
    protected void onDestroy(){
        mqttHandler.disconnect();
        super.onDestroy();
    }

    private void publishMessage(String topic, String message){
        Toast.makeText(this, "Publicando mensaje: " + message, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic,message);
    }

    private void subcribeTopic(String topic){
        Toast.makeText(this, "Subcribrte al topic: " + topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }
}