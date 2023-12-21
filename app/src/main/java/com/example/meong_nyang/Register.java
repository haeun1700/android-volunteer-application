package com.example.meong_nyang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

  EditText editTextEmail, editTextPassword, editTextName , editTextPhone, editTextRegion;
  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference databaseReference = firebaseDatabase.getReference();
  Button buttonReg;
  FirebaseAuth mAuth;
  ProgressBar progressBar;
  TextView textView;
//
//  @Override
//  public void onStart() {
//    super.onStart();
//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();
//    if (currentUser != null) {
//      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//      startActivity(intent);
//      finish();
//    }
//  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    mAuth = FirebaseAuth.getInstance();
    editTextEmail = findViewById(R.id.et_email);
    editTextPassword = findViewById(R.id.et_pass);
    editTextName = findViewById(R.id.name);
    editTextPhone = findViewById(R.id.phoneNo);
    editTextRegion = findViewById(R.id.region);
    buttonReg = findViewById(R.id.btn_register);
    progressBar = findViewById(R.id.progressBar);
    textView = findViewById(R.id.btn_login);
    textView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
//        finish();
      }
    });

    buttonReg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //가입 정보 가져오기
        progressBar.setVisibility(View.VISIBLE);
        String email, password, name, phone, region;
        email = String.valueOf(editTextEmail.getText());
        password = String.valueOf(editTextPassword.getText());
        name = String.valueOf(editTextName.getText());
        phone = String.valueOf(editTextPhone.getText());
        region = String.valueOf(editTextRegion.getText());

        if (TextUtils.isEmpty(email)) {
          Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
          return;
        }

        if (TextUtils.isEmpty(password)) {
          Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
          return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              FirebaseUser user = mAuth.getCurrentUser();

              HashMap<String,Object> hashMap = new HashMap<>();

              hashMap.put("email", user.getEmail());
              hashMap.put("name", editTextName.getText().toString().trim());
              hashMap.put("phone", editTextPhone.getText().toString().trim());
              hashMap.put("region", editTextRegion.getText().toString().trim());

              databaseReference.child("straydog/UserAccount/" + user.getUid()).updateChildren(hashMap)/*.setValue(hashMap)*/;
              Toast.makeText(Register.this, "Account created.",
                Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(Register.this, Login.class);
              startActivity(intent);
              // finish();
            } else {
              // If sign in fails, display a message to the user.
              Toast.makeText(Register.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
            }

          });
      }
    });
  }
}
