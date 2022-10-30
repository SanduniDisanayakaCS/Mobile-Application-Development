package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://echannelling-b9e97-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText rphonenu = findViewById(R.id.rphonenu);
        final EditText rlPassword = findViewById(R.id.rlPassword);
        final Button lbutton = findViewById(R.id.lbutton);
        final TextView RegisterLinktxt = findViewById(R.id.RegisterLink_2);

        lbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phonetext = rphonenu.getText().toString();
                final String rPasswordtxt = rlPassword.getText().toString();

                if (phonetext.isEmpty() || rPasswordtxt.isEmpty()){
                    Toast.makeText(MainActivity.this, "pleace enter your mobile or password", Toast.LENGTH_SHORT).show();
                }else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override //
                        public void onDataChange(@NonNull DataSnapshot snapshot) {//
                            //check if mobile phone is exist in firebase database
                            if(snapshot.hasChild(phonetext)){//

                                //mobile is exist in firebase database

                                //now get password of user from firebase data and match with entered password

                                final String getPassword = snapshot.child(phonetext).child("rlPassword").getValue(String.class);

                                if(getPassword.equals(rPasswordtxt)){
                                    Toast.makeText(MainActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, Home.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }//
                        }//

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });//

                }

            }

        });

        RegisterLinktxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });


        {


        }
    }
}