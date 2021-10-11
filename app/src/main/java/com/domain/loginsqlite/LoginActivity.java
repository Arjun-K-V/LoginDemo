package com.domain.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText usernameLogin;
    EditText passwordLogin;
    Button btnLogin;
    SharedPreferences sp;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            goToMain();
        }

        myDB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"PLEASE ENTER CRED",Toast.LENGTH_LONG).show();
                }else{
                    Boolean resultLogin = myDB.checkUsernamePassword(username,password);
                    if(resultLogin == true){
                        goToMain();
                        sp.edit().putBoolean("logged",true).apply();
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void goToMain() {
        startActivity(new Intent(LoginActivity.this,LandingActivity.class));
        finish();
    }
}