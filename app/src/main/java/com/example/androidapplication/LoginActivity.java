package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText mUsername, mPassword;
    Button btnlogin;
    CheckBox mCheckBox;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username1);
        mPassword = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        DB = new DBHelper(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        checkSharedPreferences();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = mUsername.getText().toString();
                String pass = mPassword.getText().toString();

                if (mCheckBox.isChecked()) {
                    editor.putString(getString(R.string.checkbox), "True");
                    editor.commit();

                    editor.putString(getString(R.string.username), user);
                    editor.commit();

                    editor.putString(getString(R.string.password), pass);
                    editor.commit();
                } else {
                    editor.putString(getString(R.string.checkbox), "False");
                    editor.commit();

                    editor.putString(getString(R.string.username), "");
                    editor.commit();

                    editor.putString(getString(R.string.password), "");
                    editor.commit();
                }

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass){
                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), GameActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkSharedPreferences() {
        String checkbox = sharedPreferences.getString(getString(R.string.checkbox), "False");
        String username = sharedPreferences.getString(getString(R.string.username), "");
        String password = sharedPreferences.getString(getString(R.string.password), "");

        mUsername.setText(username);
        mPassword.setText(password);

        if (checkbox.equals("True")) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }
    }
}