package app.ar.labtiassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.app.MyPreferences;

public class UserSetPin extends AppCompatActivity {

    EditText et1,et2;
    MyPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set_pin);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Please Set Your PIN !");
        pref = new MyPreferences(this);

        et1 = (EditText) findViewById(R.id.et_input_pin_1);
        et2 = (EditText) findViewById(R.id.et_input_pin_2);
        Button submit = (Button) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin1, pin2;
                pin1 = et1.getText().toString();
                pin2 = et2.getText().toString();
                if (pin1.length() < 6 || pin1.length() < 6){
                    Toast.makeText(UserSetPin.this, "Enter 6 digit !", Toast.LENGTH_SHORT).show();
                }else {
                    if (pin1.equals(pin2)){
                        pref.setKeyHasSetPin(true);
                        pref.setKeyPin(pin1);
                        Log.e("save pin", pin1);
                        Log.e("save pin", pin2);
                        Toast.makeText(UserSetPin.this, "Succes set pin.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),UserLogin.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(UserSetPin.this, "Pin doesn't match !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
