package cl.gestiona.estimotebluesep2017;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewStateEstimote extends Fragment {

    public ImageView img;
    public TextView txt;
    public RelativeLayout relativeLayout;
    public String textValue;
    public int fondo;

    public ViewStateEstimote() {
        textValue="No hay conexión :(";
        fondo = R.drawable.fondo_sin_conexion;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_state_estimote, container, false);

        img = (ImageView) view.findViewById(R.id.beacon);
        txt = (TextView) view.findViewById(R.id.txt_state);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.viewstate);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        txt.setText(textValue);
        relativeLayout.setBackgroundResource(fondo);
    }
}
