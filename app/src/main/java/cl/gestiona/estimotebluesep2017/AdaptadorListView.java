package cl.gestiona.estimotebluesep2017;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cl.gestiona.estimotebluesep2017.model.Historial;

/**
 * Created by roman on 27-09-17.
 */

public class AdaptadorListView extends ArrayAdapter<Historial>{

    public AdaptadorListView(Context context, List<Historial> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }
        TextView txt0 = (TextView) convertView.findViewById(R.id.item_list_circle);
        TextView txt1 = (TextView) convertView.findViewById(R.id.item_list_titulo);
        TextView txt2 = (TextView) convertView.findViewById(R.id.item_list_subtitulo);
        TextView txt3 = (TextView) convertView.findViewById(R.id.item_list_detalle);

        Historial historial = getItem(position);

        Drawable shape = txt0.getBackground();
        String color = (historial.getDescripcion().equalsIgnoreCase("Entrada")?"#009688":"#F44336");
        shape.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC);

        txt1.setText(historial.getTitular());
        txt2.setText(historial.getFecha());
        txt3.setText(historial.getHora());


        return convertView;
    }
}
