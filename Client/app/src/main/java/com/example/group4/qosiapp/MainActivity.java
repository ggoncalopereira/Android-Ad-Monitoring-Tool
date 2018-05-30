package com.example.group4.qosiapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    Button start_button,stop_button;
    int counterI,counterS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(counterI == 0){
                    StartClient myStartClient = new StartClient();
                    myStartClient.execute();
                    counterI++;
                }else{
                    System.out.println("Serviço Start já ativo");
                }
            }
        });

        stop_button = findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(counterS == 0){
                    StopClient myStopClient = new StopClient();
                    myStopClient.execute();
                    counterS++;
                }else{
                    System.out.println("Serviço Stop já ativo");
                }
            }
        });





    }

}