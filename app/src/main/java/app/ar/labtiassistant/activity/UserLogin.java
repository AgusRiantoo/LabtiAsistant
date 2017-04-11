package app.ar.labtiassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.app.MyPreferences;

public class UserLogin extends AppCompatActivity {

    MyPreferences preferences;
    EditText etPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        preferences = new MyPreferences(this);
        if (!preferences.getKeyHasSetPin()){
            Intent i = new Intent(this, UserSetPin.class);
            startActivity(i);
            finish();
        }
        etPin = (EditText) findViewById(R.id.et_input_pin);
        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    if (s.toString().equals(preferences.getKeyPin())) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(UserLogin.this, "Wrong PIN !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
