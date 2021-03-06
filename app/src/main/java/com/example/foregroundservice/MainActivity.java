package com.example.foregroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText inputext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputext = findViewById(R.id.edit_test_input);

    }

    public void  startService(View view)
    {
        String input = inputext.getText().toString();

        Intent serviceIntent = new Intent(this,ExampleService.class);

        serviceIntent.putExtra("inputExtra",input);

        ContextCompat.startForegroundService(this,serviceIntent);
    }


    public void  stopService(View view)
    {
        Intent serIntent = new Intent(this,ExampleService.class);
        stopService(serIntent);
    }
}