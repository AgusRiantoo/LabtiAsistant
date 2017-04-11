package app.ar.labtiassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.activity.AddTransaksi;
import app.ar.labtiassistant.adapter.AdapterTransaksi;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Transaksi;

/**
 * Created by ghost on 26/03/17.
 */

public class FragmentTransaksi extends Fragment {

    SwipeRefreshLayout refreshLayout;
    DatabaseHandler db;
    private ListView listView;

    public static FragmentTransaksi newInstance(){
        return new FragmentTransaksi();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, parent, false);

        listView = (ListView) view.findViewById(R.id.list_history);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTransaksi();
                refreshLayout.setRefreshing(false);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.f_add_transaksi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddTransaksi.class);
                startActivity(i);
            }
        });

        db = new DatabaseHandler(getActivity());
        getTransaksi();
        return view;
    }

    private void getTransaksi() {
        List<Transaksi> transaksiList = db.getAllTransaksi("null");

        AdapterTransaksi adapter = new AdapterTransaksi(getActivity(),R.layout.layout_transaksi, transaksiList);
        listView.setAdapter(adapter);
    }
}
