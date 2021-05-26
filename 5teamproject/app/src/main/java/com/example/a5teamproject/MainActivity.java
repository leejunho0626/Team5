package com.example.a5teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    login();
                    break;
                case R.id.btn_register:
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void login() {
        String id = ((EditText) findViewById(R.id.login_id)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        if (id.length() > 0 && password.length() > 0) {

            firebaseAuth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                                startActivity(intent);
                            } else {
                                if (task.getException() != null) {
                                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }

}

