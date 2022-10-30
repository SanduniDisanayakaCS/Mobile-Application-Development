package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

//create object of database references class to access firebase's realtime datebase

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://echannelling-b9e97-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        setContentView(R.layout.activity_main2);

        final EditText rFullName = findViewById(R.id.rFullName);
        final EditText rPassword = findViewById(R.id.rPassword);
        final EditText cPassword = findViewById(R.id.cPassword);
       // final Spinner rollSpin = findViewById(R.id.rollSpin);
        final EditText rphonenu = findViewById(R.id.rphonenu);

        final Button rbutton1 = findViewById(R.id.rbutton1);
       // final TextView loginnowbtn  = findViewById(R.id.RegisterLink_2);



        rbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from eidttext into string variable
                final String rFullNametxt = rFullName.getText().toString();
                final String rPasswordtxt = rPassword.getText().toString();
                final String cPasswordtxt = cPassword.getText().toString();
               // final String rollSpintxt = rollSpin.getText().toString();
                final String rphonenutxt = rphonenu.getText().toString();

                //check if user fill all the field befor sending data to firebase
                if(rFullNametxt.isEmpty() || rPasswordtxt.isEmpty() || cPasswordtxt.isEmpty()  || rphonenutxt.isEmpty()){

                    Toast.makeText(MainActivity2.this,"pleace all the field",Toast.LENGTH_SHORT).show();
                }
                //check if password are matching with each other
                //if not matching with each other then show a tost messsage
                else if(!rPasswordtxt.equals(cPasswordtxt)){
                    Toast.makeText(MainActivity2.this, "password are not matching", Toast.LENGTH_SHORT).show();

                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check is phone is phone number is not registered before

                           if(snapshot.hasChild(rphonenutxt)){
                               Toast.makeText(MainActivity2.this, "phone is already registered", Toast.LENGTH_SHORT).show();
                           }else {
                              // databaseReference.child("users").addListenerForSingleValueEvent(new);
                               //sendinh data to firebase realtimr database
                               //we are using phone number as unique identity of every user
                               //so all the other details of user comes under the phone number
                               databaseReference.child("users").child(rphonenutxt).child("rFullName").setValue(rFullNametxt);
                               databaseReference.child("users").child(rphonenutxt).child("rPassword").setValue(rPasswordtxt);

                               //show a success then finish the activity
                               Toast.makeText(MainActivity2.this, "user register successfully", Toast.LENGTH_SHORT).show();
                               finish();

                               rbutton1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       startActivity(new Intent(MainActivity2.this, MainActivity.class));
                                   }
                               });

                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });








        Spinner spinner = findViewById(R.id.rollSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

    }
}