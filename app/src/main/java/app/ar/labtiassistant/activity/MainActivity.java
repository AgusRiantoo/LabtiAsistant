package app.ar.labtiassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.app.MyPreferences;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.fragment.FragmentHome;
import app.ar.labtiassistant.fragment.FragmentJadwal;
import app.ar.labtiassistant.fragment.FragmentTransaksi;
import app.ar.labtiassistant.models.Transaksi;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String rAwal, rAkhir;
    DatabaseHandler db;
    MyPreferences preferences;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment = FragmentHome.newInstance();
                    break;

                case R.id.navigation_jadwal:
                    selectFragment = FragmentJadwal.newInstance();
                    break;

                case R.id.navigation_history:
                    selectFragment = FragmentTransaksi.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);

        preferences = new MyPreferences(getApplicationContext());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new FragmentHome());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.m_about:
                Intent i = new Intent(this, AboutMe.class);
                startActivity(i);
                break;

            case R.id.m_rekap:
                rAwal = "0";
                rAkhir = "0";
                createRekapan("awal");
                break;

            case R.id.m_exit:
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createRekapan(String waktu) {
        if (waktu.equals("awal")){
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.setTitle("Tanggal Awal");
            dialog.show();
        }else{
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.setTitle("Tanggal Akhir");
            dialog.show();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String m,d;
        if (month < 10){
            m = "0" + String.valueOf(month + 1);
        }else {
            m = String.valueOf(month + 1);
        }

        if (dayOfMonth < 10){
            d = "0" + String.valueOf(dayOfMonth);
        }else {
            d = String.valueOf(dayOfMonth);
        }

        if (rAwal.equals("0")){
            rAwal = year+"-"+m+"-"+d;
            createRekapan("akhir");
        }else{
            rAkhir = year+"-"+m+"-"+d;
//            date >= '2017-04-01' and date <= '2017-04-11'"
            String date = "date >= '"+rAwal+"' and date <= '"+rAkhir+"'";

            Log.e("date", date);
            Log.e("date", String.valueOf(month));

            List<Transaksi> transaksiList = db.getAllTransaksi(date);
            int total = 0;
            for (Transaksi t : transaksiList){
                total = total + Integer.parseInt(t.get_upah());
            }
            try {
                preferences.setKeyRekapan(String.valueOf(total));
                Toast.makeText(this, "Total rekapan yang diperoleh adalah : "+String.valueOf(total), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
