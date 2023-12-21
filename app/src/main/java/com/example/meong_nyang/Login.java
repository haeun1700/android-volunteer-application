package com.example.meong_nyang;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
  EditText editTextEmail, editTextPassword;
  Button buttonLogin;
  FirebaseAuth mAuth;
  ProgressBar progressBar;
  TextView textView;
  TextView findingId;
  TextView findingPwd;

  @Override
  public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      Intent intent = new Intent(getApplicationContext(), Volunteers.class);
      startActivity(intent);
//            finish();
    }
  }

  public void changeView(Class<?> layout) {
    startActivity(new Intent(this, layout));
//    finish();
  }

  public void getMessage(String message) {
    makeText(this, message, LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mAuth = FirebaseAuth.getInstance();
    editTextEmail = findViewById(R.id.et_email);
    editTextPassword = findViewById(R.id.et_pass);
    findingId = findViewById(R.id.finding_id);
    findingPwd = findViewById(R.id.finding_pw);
    buttonLogin = findViewById(R.id.btn_login);
    progressBar = findViewById(R.id.progressBar);
    textView = findViewById(R.id.btn_register);

    findingId.setOnClickListener((view) -> changeView(Finding_id.class));
    findingPwd.setOnClickListener((view) -> changeView(Finding_id.class));
    textView.setOnClickListener(view -> changeView(Register.class));

    buttonLogin.setOnClickListener((view) -> {

      progressBar.setVisibility(View.VISIBLE);

      Map<String, String> errorMap = new HashMap<>();
      String email = String.valueOf(editTextEmail.getText());
      String password = String.valueOf(editTextPassword.getText());

      errorMap.put("email", email);
      errorMap.put("password", password);

      for (Map.Entry<String, String> checkInput : errorMap.entrySet()) {
        if (TextUtils.isEmpty(checkInput.getValue())) {
          getMessage("Enter " + checkInput.getKey());
          progressBar.setVisibility(View.GONE);
          return;
        }
      }

      mAuth
        .signInWithEmailAndPassword(email, password)
        .addOnCompleteListener((task) -> {
            progressBar.setVisibility(View.GONE);
            if (!task.isSuccessful()) {
              getMessage("Authentication failed.");
              return;
            }
            getMessage("Login Successful");
            changeView(Volunteers.class);
          }
        );
    });
  }
}