package cl.gestiona.estimotebluesep2017.model;

import android.os.AsyncTask;
import android.util.Log;

import cl.gestiona.estimotebluesep2017.MainActivity;
import cl.gestiona.estimotebluesep2017.ViewCronometer;

/**
 * Created by roman on 04-10-17.
 */

public class Cronometro extends AsyncTask<Void, Integer, Void> {

    ViewCronometer viewCronometer;

    public Cronometro(ViewCronometer viewCronometer){
        this.viewCronometer = viewCronometer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        for (int h=0;h<=59;h++){
            for (int m=0; m<=59;m++){
                for (int s=0; s<=59; s++){
                    for (int ms=0; ms<=9; ms++){
                        try {
                            Thread.sleep(50);
                            //System.out.println(ms);
                            if (isCancelled()){
                                //break;
                                ms = s = m = h = 100;
                            }
                            publishProgress(h,m,s,ms);
                        } catch (InterruptedException e) {
                            Log.e("Interruption","Se ha interrumpido el cronometro");
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        viewCronometer.txthora.setText((values[0]<10)?"0".concat(String.valueOf(values[0])):String.valueOf(values[0]));
        viewCronometer.txtmin.setText((values[1]<10)?"0".concat(String.valueOf(values[1])):String.valueOf(values[1]));
        viewCronometer.txtseg.setText((values[2]<10)?"0".concat(String.valueOf(values[2])):String.valueOf(values[2]));
        viewCronometer.txtmil.setText(String.valueOf(values[3]));

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
