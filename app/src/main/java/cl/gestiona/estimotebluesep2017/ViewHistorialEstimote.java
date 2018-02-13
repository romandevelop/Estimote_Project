package cl.gestiona.estimotebluesep2017;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cl.gestiona.estimotebluesep2017.model.Historial;
import cl.gestiona.estimotebluesep2017.model.HistorialCrud;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewHistorialEstimote extends Fragment {


    public ListView listView;
    private HistorialCrud crud;


    public ViewHistorialEstimote() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        crud = new HistorialCrud(getActivity());
        View view = inflater.inflate(R.layout.fragment_view_historial_estimote, container, false);
        listView = (ListView) view.findViewById(R.id.listviewHistorial);
        AdaptadorListView adapter = new AdaptadorListView(getContext(), crud.getHistorialList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // toast("hi");
            }
        });
        return view;
    }

    public void toast(String m){
        Toast.makeText(getContext(),m,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        AdaptadorListView adapter = new AdaptadorListView(getContext(), crud.getHistorialList());
        listView.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()){
            if (!isVisibleToUser){
                Log.e("INFO","Fragmento Historial ya no esta visible se puede destruir un servicio");
            }else{
                Log.e("INFO","Fragmento Historial  esta visible se puede iniciar un servicio");
            }
        }
    }
}
