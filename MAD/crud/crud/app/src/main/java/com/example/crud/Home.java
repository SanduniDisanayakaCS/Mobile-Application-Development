package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView textView,textView111,textView21,textView16,textView22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       textView = findViewById(R.id.textView);
         textView111 = findViewById(R.id.textView111);
         textView21 = findViewById(R.id.textView21);
        textView16 = findViewById(R.id.textView16);
       textView22 = findViewById(R.id.textView22);


    }


    public void onResume() {
        super.onResume();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, mriAppoinment.class);
                startActivity(intent);
            }
        });
        textView111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, mriAppoinment.class);
                startActivity(intent);
            }
        });
        textView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, mriAppoinment.class);
                startActivity(intent);
            }
        });
        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, menstrualPeriodCalculate.class);
                startActivity(intent);
            }
        });
        textView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, mriAppoinment.class);
                startActivity(intent);
            }
        });
    }
}