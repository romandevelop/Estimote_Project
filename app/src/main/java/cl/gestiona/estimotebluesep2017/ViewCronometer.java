package cl.gestiona.estimotebluesep2017;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cl.gestiona.estimotebluesep2017.model.Cronometro;


public class ViewCronometer extends Fragment {

    public  TextView txthora, txtmin, txtseg, txtmil, txtfinal;
    public  Button bt,bt2;


    public ViewCronometer() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_cronometer, container, false);
        txthora = (TextView) view.findViewById(R.id.txthora);
        txtmin = (TextView) view.findViewById(R.id.txtmin);
        txtseg = (TextView) view.findViewById(R.id.txtseg);
        txtmil = (TextView) view.findViewById(R.id.txtmil);
        txtfinal = (TextView) view.findViewById(R.id.tiempofinal);






        // Inflate the layout for this fragment
        return view;
    }


    public void toast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()){
            if (!isVisibleToUser){
                Log.e("INFO","Fragmento Cronometeer ya no esta visible se puede destruir un servicio");
            }else{
                Log.e("INFO","Fragmento Cronometeer  esta visible se puede iniciar un servicio");
            }
        }
    }
}
