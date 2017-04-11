package app.ar.labtiassistant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.adapter.AdapterJadwal;
import app.ar.labtiassistant.app.MyPreferences;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Jadwal;
import app.ar.labtiassistant.models.Transaksi;


public class FragmentHome extends Fragment {

    private TextView tvRekapan;
    private TextView tvtotal;
    private TextView textView;
    private ListView listView;
    private MyPreferences preferences;
    DatabaseHandler db;
    String toDay;
    SwipeRefreshLayout refreshLayout;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData();
                refreshLayout.setRefreshing(false);
            }
        });
        tvRekapan = (TextView) v.findViewById(R.id.tv_rekapan);
        tvtotal = (TextView) v.findViewById(R.id.tv_total);
        listView = (ListView) v.findViewById(R.id.list_jadwal);
        textView = (TextView) v.findViewById(R.id.tv_no_jadwal);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        toDay = dayFormat.format(calendar.getTime());

        preferences = new MyPreferences(getActivity());
        db = new DatabaseHandler(getActivity());

        getHomeData();

        return v;
    }

    private void getHomeData() {

        List<Transaksi> transaksiList = db.getAllTransaksi("null");
        int total = 0;
        for (Transaksi t : transaksiList) {
            total = total + Integer.parseInt(t.get_upah());
        }

        tvtotal.setText("Rp. " + String.valueOf(total));

        String rekapan = preferences.getKeyRekapan();
        tvRekapan.setText("Rp. " + rekapan);

        List<Jadwal> jadwalList = db.getAllJadwal(toDay);
        if (jadwalList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        }else {
            AdapterJadwal adapter = new AdapterJadwal(getActivity(), R.layout.fragment_home, jadwalList);
            listView.setAdapter(adapter);
        }
    }


}