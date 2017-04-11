package app.ar.labtiassistant.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Jadwal;

public class AddJadwal extends AppCompatActivity {

    EditText etRuang, etKelas, etShift, etMatPrak, tAwal,tAkir;
    Spinner sHari, sJob;
    AppCompatButton bSubmit;
    String hari, ruang, job, kelas, shift, jam, matprak;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jadwal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(this);

        etRuang = (EditText) findViewById(R.id.et_ruang);
        etKelas = (EditText) findViewById(R.id.et_kelas);
        etShift = (EditText) findViewById(R.id.et_shift);
        etMatPrak = (EditText) findViewById(R.id.et_mata_praktikum);
        bSubmit = (AppCompatButton) findViewById(R.id.btn_submit);

        sHari = (Spinner) findViewById(R.id.s_hari);
        sHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hari = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> ahari = ArrayAdapter.createFromResource(this,R.array.n_hari,android.R.layout.simple_spinner_item);
        ahari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sHari.setAdapter(ahari);

        sJob = (Spinner) findViewById(R.id.s_job);
        sJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> ajob = ArrayAdapter.createFromResource(this,R.array.n_job,android.R.layout.simple_spinner_item);
        ajob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sJob.setAdapter(ajob);

        tAwal = (EditText) findViewById(R.id.t_awal);
        tAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeDialog("awal");
            }
        });

        tAkir = (EditText) findViewById(R.id.t_akhir);
        tAkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeDialog("akhir");
            }
        });

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJadwal();
            }
        });

    }

    private void saveJadwal() {
        ruang = etRuang.getText().toString();
        kelas = etKelas.getText().toString();
        shift = etShift.getText().toString();
        jam = tAwal.getText().toString() + " - " + tAkir.getText().toString();
        matprak = etMatPrak.getText().toString();

        if (ruang.isEmpty() || kelas.isEmpty() || shift.isEmpty() || jam.length() < 5 || matprak.isEmpty()){
            Toast.makeText(this, "Please complete the form!", Toast.LENGTH_SHORT).show();
        }else {
            Jadwal jadwal = new Jadwal();
            jadwal.set_hari(hari);
            jadwal.set_ruang(ruang);
            jadwal.set_job(job);
            jadwal.set_kelas(kelas);
            jadwal.set_shift(shift);
            jadwal.set_jam(jam);
            jadwal.set_mata_praktikum(matprak);

            Boolean status = db.addJadwal(jadwal);

            if (status) {
                Toast.makeText(this, "Success add schedule", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add schedule", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getTimeDialog(final String t) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String menit;
                if (selectedMinute < 10){
                    menit = "0" + String.valueOf(selectedMinute);
                }else{
                    menit = String.valueOf(selectedMinute);
                }
                if (t.equals("awal")){
                    tAwal.setText(selectedHour + ":" + menit);
                }else{
                    tAkir.setText(selectedHour + ":" + menit);
                }
            }
        }, hour, minute, true);
        mTimePicker.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
