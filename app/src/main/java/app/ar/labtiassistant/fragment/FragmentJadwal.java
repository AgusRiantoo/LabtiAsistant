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
import app.ar.labtiassistant.activity.AddJadwal;
import app.ar.labtiassistant.adapter.AdapterJadwal;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Jadwal;

/**
 * Created by ghost on 26/03/17.
 */

public class FragmentJadwal extends Fragment {

    SwipeRefreshLayout refreshLayout;
    DatabaseHandler db;
    AdapterJadwal adapter;
    private ListView listView;

    public static FragmentJadwal newInstance(){
        return new FragmentJadwal();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal, parent, false);

        db = new DatabaseHandler(getActivity());

        listView = (ListView) view.findViewById(R.id.list_jadwal);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJadwal();
                refreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.f_add_jadwal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddJadwal.class);
                startActivity(i);
            }
        });

        getJadwal();
        return view;
    }

    private void getJadwal() {
        final List<Jadwal> jadwalList = db.getAllJadwal("null");
        adapter = new AdapterJadwal(getActivity(),R.layout.layout_jadwal, jadwalList);
        listView.setAdapter(adapter);
    }

}
