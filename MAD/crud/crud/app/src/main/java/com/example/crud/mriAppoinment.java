package com.example.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class mriAppoinment extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener {

  private ImageView profilepc;
  public Uri imageUri;
  private FirebaseStorage storage;
  private StorageReference storageReference;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://echannelling-b9e97-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mri_appoinment);

        // final Spinner Booktype = findViewById(R.id.textView3);
        final EditText pName = findViewById(R.id.editTextTextPersonName);
        final EditText mriPhonenu = findViewById(R.id.mriPNu);
        final EditText moreDetail = findViewById(R.id.et_input);

        final Button saveBtn = findViewById(R.id.bt_save);

        final Button updateBtn = findViewById(R.id.openCam);

        //////////////////////
        DAOEmployee dao = new DAOEmployee();



         //test



        profilepc = findViewById(R.id.profilepc);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        Spinner spinner = findViewById(R.id.textView3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Roll, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from eidttext into string variable
                // final String Booktypetxt = Booktype.getText().toString();
                final String pNametxt = pName.getText().toString();
                final String mriPhonenutxt = mriPhonenu.getText().toString();
                final String moreDetailtxt = moreDetail.getText().toString();

                final Button udateBtn = findViewById(R.id.openCam);


                //check if user fill all the field befor sending data to firebase
                if(pNametxt.isEmpty() || mriPhonenutxt.isEmpty() || moreDetailtxt.isEmpty()){

                    Toast.makeText(mriAppoinment.this,"pleace all the field",Toast.LENGTH_SHORT).show();
                }
                //check if password are matching with each other
                //if not matching with each other then show a tost messsage


                else{
                    databaseReference.child("mridata").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check is phone is phone number is not registered before

                            if(snapshot.hasChild(mriPhonenutxt)){
                                Toast.makeText(mriAppoinment.this, "mri already exist", Toast.LENGTH_SHORT).show();
                            }else {
                                // databaseReference.child("users").addListenerForSingleValueEvent(new);
                                //sendinh data to firebase realtimr database
                                //we are using phone number as unique identity of every user
                                //so all the other details of user comes under the phone number
                                databaseReference.child("mridata").child(mriPhonenutxt).child("pName").setValue(pNametxt);
                                databaseReference.child("mridata").child(mriPhonenutxt).child("moreDetail").setValue(moreDetailtxt);

                                //show a success then finish the activity
                                Toast.makeText(mriAppoinment.this, "insert successfully", Toast.LENGTH_SHORT).show();
                                finish();

                                saveBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(mriAppoinment.this, Home.class));
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



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",pName.getText().toString());
                hashMap.put("Deatil",moreDetail.getText().toString());
                dao.update("mriPhonenutxt",hashMap).addOnSuccessListener(suc->
                {
                    Toast.makeText(mriAppoinment.this, "record is udaterd", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->
                 {
                    Toast.makeText(mriAppoinment.this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });





    }

    private void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageUri = data.getData();
            profilepc.setImageURI(imageUri);
            uploloadPicture();
        }
    }

    private void uploloadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploding image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"image Uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Faield to upload",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPrecent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Presentage" + (int) progressPrecent + "%");
                    }
                });

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