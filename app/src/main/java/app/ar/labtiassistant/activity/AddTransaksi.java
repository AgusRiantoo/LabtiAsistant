package app.ar.labtiassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Transaksi;

public class AddTransaksi extends AppCompatActivity {

    Spinner sTingkat, sJob;
    EditText etShift, etBaris;
    AppCompatButton bSubmit;
    DatabaseHandler db;


    String job, tanggal, tingkat, date;
    int baris,upah,shift, kali;
    private static final int dap = 2822;
    private static final int dam = 3082;
    private static final int dkp = 3239;
    private static final int dkm = 3500;

    private static final int map = 3762;
    private static final int mam = 3971;
    private static final int mkp = 4127;
    private static final int mkm = 4389;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaksi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHandler(this);

        sJob = (Spinner) findViewById(R.id.s_job);
        sTingkat = (Spinner) findViewById(R.id.s_tingkat);
        etShift = (EditText) findViewById(R.id.et_shift);
        etBaris = (EditText) findViewById(R.id.et_jumlah_baris);
        bSubmit = (AppCompatButton) findViewById(R.id.btn_submit);

        ArrayAdapter<CharSequence> ajob = ArrayAdapter.createFromResource(this,R.array.n_job,android.R.layout.simple_spinner_item);
        ajob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sJob.setAdapter(ajob);

        sJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> atingkat = ArrayAdapter.createFromResource(this,R.array.n_tingkat,android.R.layout.simple_spinner_item);
        atingkat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTingkat.setAdapter(atingkat);

        sTingkat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tingkat = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etBaris.getText().toString().isEmpty() || etShift.getText().toString().isEmpty()){
                    Toast.makeText(AddTransaksi.this, "Please complete the form!", Toast.LENGTH_SHORT).show();
                }else{
                    saveTransaksi();
                }
            }
        });

    }

    private void saveTransaksi() {
        shift = Integer.parseInt(etShift.getText().toString());
        baris = Integer.parseInt(etBaris.getText().toString());

        if (baris <= 2){
            kali = 2;
        }else if (baris <= 3){
            kali = 3;
        }else{
            kali = 4;
        }

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        tanggal = df.format(Calendar.getInstance().getTime());
        date = df2.format(Calendar.getInstance().getTime());
        
        switch (tingkat){
            case "Dasar":
                if (job.equals("Asisten") && shift < 5){
                    upah = dap * 2 * kali;
                }else if (job.equals("Asisten") && shift >= 5){
                    upah = dam * 2 * kali;
                }else if (job.equals("PJ") && shift < 5){
                    upah = dkp * 2 * kali;
                }else{
                    upah = dkm * 2 * kali;
                }
                break;
            case "Menengah":
            case "Lanjut" :
                if (job.equals("Asisten") && shift < 5){
                    upah = map * 2 * kali;
                }else if (job.equals("Asisten") && shift >= 5){
                    upah = mam * 2 * kali;
                }else if (job.equals("PJ") && shift < 5){
                    upah = mkp * 2 * kali;
                }else{
                    upah = mkm * 2 * kali;
                }
                break;
            default:
                upah = 0;
                break;
        }
        Transaksi t = new Transaksi();
        t.set_job(job);
        t.set_tingkat(tingkat);
        t.set_shift(String.valueOf(shift));
        t.set_upah(String.valueOf(upah));
        t.set_tanggal(tanggal);
        t.set_date(date);

        Boolean status = db.addTransaksi(t);
        if (status) {
            finish();
            Toast.makeText(this, "Berhasil menambah transaksi !", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Gagal menambah transaksi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
