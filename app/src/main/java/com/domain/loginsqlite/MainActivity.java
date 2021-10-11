package com.domain.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username,password,repassword;
    Button btnSignIn,btnSignUp;
    DBHelper myDB;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("registered",MODE_PRIVATE);

        if(sp.getBoolean("registered",false)){
            goToMain();
        }


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        myDB = new DBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("")||pass.equals("")||repass.equals("")){
                    Toast.makeText(MainActivity.this,"Fill ALl the Fields",Toast.LENGTH_LONG).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean userCheckResult = myDB.checkusername(user);
                        if(userCheckResult == false){
                            Boolean regResult = myDB.insertData(user,pass);
                            if(regResult == true){
                                Toast.makeText(MainActivity.this,"Reg Success",Toast.LENGTH_LONG).show();
                                sp.edit().putBoolean("registered",true).apply();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"Reg Failed",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"User Exists\n Please SignIn", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"PAsswords Doesnot Match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToMain() {
        startActivity(new Intent(MainActivity.this,LandingActivity.class));
    }
}