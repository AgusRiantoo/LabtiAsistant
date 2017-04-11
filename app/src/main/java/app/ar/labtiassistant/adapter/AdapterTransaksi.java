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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.ar.labtiassistant.R;
import app.ar.labtiassistant.database.DatabaseHandler;
import app.ar.labtiassistant.models.Transaksi;

/**
 * Created by ghost on 06/04/17.
 */

public class AdapterTransaksi extends ArrayAdapter<Transaksi> {
    DatabaseHandler db;
    Transaksi t;
    public AdapterTransaksi(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public AdapterTransaksi(@NonNull Context context, @LayoutRes int resource, @NonNull List<Transaksi> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.layout_transaksi, null);
        }

        db = new DatabaseHandler(getContext());
        t = getItem(position);

        if (t != null){
            ImageView iv = (ImageView) v.findViewById(R.id.iv_tingkat);
            TextView tv1 = (TextView) v.findViewById(R.id.tv_job);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_upah);
            TextView tv3 = (TextView) v.findViewById(R.id.tv_tanggal);


            switch (t.get_tingkat()){
                case "Dasar":
                    iv.setImageResource(R.mipmap.ic_dasar);
                    break;
                case "Menengah":
                    iv.setImageResource(R.mipmap.ic_menengah);
                    break;
                case "Lanjut":
                    iv.setImageResource(R.mipmap.ic_lanjut);
                    break;
            }
            tv1.setText(t.get_job());
            tv2.setText("Rp. "+ t.get_upah());
            tv3.setText(t.get_tanggal());
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want delete this history?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteTransaksi(t.get_id());
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
