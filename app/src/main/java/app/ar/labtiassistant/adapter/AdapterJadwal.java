package app.ar.labtiassistant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Jadwal;

/**
 * Created by ghost on 06/04/17.
 */

public class AdapterJadwal extends ArrayAdapter<Jadwal> {

    DatabaseHandler db;
    Jadwal jadwal;
    public AdapterJadwal(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public AdapterJadwal(@NonNull Context context, @LayoutRes int resource, @NonNull List<Jadwal> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.layout_jadwal, null);
        }

        db = new DatabaseHandler(getContext());
        jadwal = getItem(position);

        if (jadwal != null){
            TextView tv1 = (TextView) v.findViewById(R.id.tv_hari);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_waktu);
            TextView tv3 = (TextView) v.findViewById(R.id.tv_job);

            tv1.setText(jadwal.get_hari() + " / " + jadwal.get_ruang());
            tv2.setText(jadwal.get_jam());
            tv3.setText(jadwal.get_job() + " / " + jadwal.get_kelas() + " / " + jadwal.get_mata_praktikum());
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want delete this schedule?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteJadwal(jadwal.get_id());
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return v;
    }
}
