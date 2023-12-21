package com.example.meong_nyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Finding_pw extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스 서버 연결

    Button btn_findPw;
    TextView btn_findId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_pw);
        btn_findId = (TextView) findViewById(R.id.finding_login);

        btn_findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 찾기 화면으로 이동
                Intent intent = new Intent(Finding_pw.this, Finding_id.class);
                startActivity(intent);
            }
        });
    }
}

