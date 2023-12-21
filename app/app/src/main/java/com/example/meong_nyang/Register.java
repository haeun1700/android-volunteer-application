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

  EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextRegion;
  Button buttonReg;
  FirebaseAuth mAuth;
  ProgressBar progressBar;
  TextView textView;
  private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private final DatabaseReference databaseReference = firebaseDatabase.getReference();
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

  //회원가입화면에서 정보들을 입력한 후 데이터베이스에 바로 저장하는 코드이다.

  //이미지는 Storage에 저장되고 사용자 정보는 DB에 저장된다.
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
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        name = editTextName.getText().toString();
        phone = editTextPhone.getText().toString();
        region = editTextRegion.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
          Toast.makeText(Register.this, "정보를 바르게 입력해 주세요", Toast.LENGTH_SHORT).show();
          return;
        }
        try {
          // Authentication에 email,pw 생성
          mAuth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) { //회원가입 성공시
// Sign in success, update UI with the signed-in user's information
                      FirebaseUser user = mAuth.getCurrentUser();

                      HashMap<String, Object> hashMap = new HashMap<>();

                      hashMap.put("email", user.getEmail());
                      hashMap.put("name", editTextName.getText().toString().trim());
                      hashMap.put("phone", editTextPhone.getText().toString().trim());
                      hashMap.put("region", editTextRegion.getText().toString().trim());
                      //database에 저장.
                      databaseReference.child("straydog/UserAccount/" + user.getUid()).updateChildren(hashMap);// 클래스형태를 집어 넣을 경우 데이터 구조 자체가 변경
                      Toast.makeText(Register.this, "Account created.",
                              Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(Register.this, Login.class);
                      startActivity(intent);
//              finish();
                    } else { //회원가입 실패시
                      // If sign in fails, display a message to the user.
                      Toast.makeText(Register.this, "Authentication failed.",
                              Toast.LENGTH_SHORT).show();
                    }

                  });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}