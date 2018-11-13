package com.example.os150.tabhost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton google_Login;
    private static final int RC_SIGN_IN = 1000;
    private GoogleApiClient GoogleApiClient;
    private EditText editTextLEmail;
    private EditText editTextLPassword;
    private Button btnlogin;
    private TextView textViewFindpassword;
    private TextView textviewerrorMessage;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLEmail = (EditText) findViewById(R.id.editTextLEmail);
        editTextLPassword = (EditText) findViewById(R.id.editTextLPassword);
        textViewFindpassword = (TextView) findViewById(R.id.textViewFindpassword);
        textviewerrorMessage = (TextView) findViewById(R.id.textviewerrorMessage);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));

        }




        GoogleSignInOptions googleSigninO = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        GoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSigninO).build();
        google_Login = findViewById(R.id.google_Login);
        google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SigninIntent = Auth.GoogleSignInApi.getSignInIntent(GoogleApiClient);
                startActivityForResult(SigninIntent,RC_SIGN_IN);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                    String email = editTextLEmail.getText().toString().trim();
//                    String password = editTextLPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(editTextLEmail.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "이메일 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(editTextLPassword.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressDialog.setMessage("로그인 중....");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(editTextLEmail.getText().toString().trim(), editTextLPassword.getText().toString().trim()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Fail..", Toast.LENGTH_SHORT).show();
                                textviewerrorMessage.setText("실패 이유 ..\n- password를 잘못입력 하셨습니다.\n- 서버 오류");

                            }
                        }
                    });
            }
        });
        textViewFindpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, FindActivity.class));

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }else{
                Toast.makeText(this,"로그인 실패..",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acnt) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acnt.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "인증 성공", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        public void onConnectionFailed(ConnectionResult connectionResult){
    }
}
