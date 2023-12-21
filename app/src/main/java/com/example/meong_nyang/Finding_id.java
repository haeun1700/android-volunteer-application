package com.example.meong_nyang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Finding_id extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_SEND_SMS = 2323;

  TextView btn_findPw;
  Button btn_findId;
  private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
  private DatabaseReference mDatabaseRef; //실시간 데이터 베이스 서버 연결

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mFirebaseAuth = FirebaseAuth.getInstance();
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("straydog");

    setContentView(R.layout.activity_finding_id);
    btn_findId = findViewById(R.id.finding_id);
    btn_findPw = findViewById(R.id.finding_pw);
//
//        btn_findPw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //비밀번호 찾기 화면으로 이동
//                Intent intent = new Intent(Finding_id.this, Finding_pw.class);
//                startActivity(intent);
//            }
//        });
    btn_findId.setOnClickListener(view -> {
      Intent email = new Intent(Intent.ACTION_SEND);
      email.setType("plain/text");
      String[] address = {"email@address.com"};
      email.putExtra(Intent.EXTRA_EMAIL, address);
      email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
      email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
      startActivity(email);

    });
  }

//  private void sendMail() {
//    if (ContextCompat.checkSelfPermission(this,
//      android.Manifest.permission.SEND_SMS)
//      != PackageManager.PERMISSION_GRANTED) {//권한이 없다면
//
//      ActivityCompat.requestPermissions(this,
//        new String[]{Manifest.permission.SEND_SMS},
//        PERMISSIONS_REQUEST_SEND_SMS);
//    }else { //권한이 있다면 SMS를 보낸다.
//      PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE);
//      PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), PendingIntent.FLAG_IMMUTABLE);
//
//      SmsManager smsManager = SmsManager.getDefault();
//      try {
//        smsManager.sendTextMessage("01082003855", "01082003855", "asdf", sendIntent, deliveredIntent);
//      } catch (Exception ex) {
//        ex.printStackTrace();
//        Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//      }
//    }
//  }

}