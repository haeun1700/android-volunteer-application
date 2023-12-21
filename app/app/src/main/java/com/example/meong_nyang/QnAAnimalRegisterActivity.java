package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class QnAAnimalRegisterActivity extends AppCompatActivity {
    private String TAG = QnAHealthInfoActivity.class.getSimpleName();

    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_animalregister);

        //add toolbar
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화
        ActionBar actionBar = getSupportActionBar(); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기

        webView = (WebView) findViewById(R.id.animalregister_webview);

        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setLoadWithOverviewMode(true);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
        webView.getSettings().setUseWideViewPort(true);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

        webView.getSettings().setBuiltInZoomControls(true);  // 줌 확대/축소 버튼 여부
        webView.getSettings().setSupportZoom(true);  // 줌 설정 여부

        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부

        webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지 (localStorage) 사용여부

        //웹페이지 호출
        webView.loadUrl("https://www.animal.go.kr/front/awtis/faq/faqList.do?menuNo=5000000016");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //bottomNavigation 전환
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(QnAAnimalRegisterActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(QnAAnimalRegisterActivity.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(QnAAnimalRegisterActivity.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(QnAAnimalRegisterActivity.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(QnAAnimalRegisterActivity.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //툴바 뒤로가기버튼 눌렸을 때 동작

                Intent intent = new Intent(getApplicationContext(), QnAActivity.class);
                startActivity(intent);

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}