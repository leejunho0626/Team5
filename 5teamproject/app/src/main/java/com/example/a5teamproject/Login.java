package com.example.a5teamproject;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_save).setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_save:
                    signUp();
                    break;
            }
        }
    };
    private  void signUp(){
        String id = ((EditText)findViewById(R.id.user_id)).getText().toString();
        String password = ((EditText)findViewById(R.id.user_password)).getText().toString();

        if(id.length()>0 && password.length() > 0){
            firebaseAuth.createUserWithEmailAndPassword(id,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        if (task.getException() != null) {
                            Toast.makeText(getApplicationContext(), "이미 등록된 아이디입니다..", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }

}
