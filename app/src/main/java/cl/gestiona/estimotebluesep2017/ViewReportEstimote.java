package cl.gestiona.estimotebluesep2017;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import cl.gestiona.estimotebluesep2017.model.Historial;
import cl.gestiona.estimotebluesep2017.model.HistorialCrud;


public class ViewReportEstimote extends Fragment {
    private BarChart barChart;
    private HistorialCrud crud;

    public ViewReportEstimote() {
        // Required empty public constructorç
        crud = MainActivity.crud;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_report_estimote, container, false);

        barChart = (BarChart) view.findViewById(R.id.barra);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        int fuera=0, entra=0;

        for (Historial h : crud.getHistorialList()){
            if (h.getDescripcion().equalsIgnoreCase("entrada")){
                entra++;
            }else{
                fuera++;
            }
        }

        barEntries.add(new BarEntry(1f,entra));
        barEntries.add(new BarEntry(2f,fuera));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");



        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.setDragEnabled(true);
        barChart.setTouchEnabled(true);
        barChart.setScaleEnabled(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
