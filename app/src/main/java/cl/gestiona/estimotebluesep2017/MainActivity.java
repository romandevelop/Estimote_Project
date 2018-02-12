package cl.gestiona.estimotebluesep2017;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cl.gestiona.estimotebluesep2017.model.Cronometro;
import cl.gestiona.estimotebluesep2017.model.Historial;
import cl.gestiona.estimotebluesep2017.model.HistorialCrud;


public class MainActivity extends AppCompatActivity {
    //ESTIMOTE
    private BeaconManager manager;
    private BluetoothAdapter bluetoothAdapter;
    //TABS Y COMPONENTES
    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabs;
    private AdaptadorPager adaptadorPager;
    //FRAGMENTOS
    private ViewReportEstimote viewReportEstimote;
    private ViewHistorialEstimote viewHistorialEstimote;
    private ViewStateEstimote viewStateEstimote;
    private ViewCronometer viewCronometer;
    //DRAWER
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigation;
    //CRUD
    public static HistorialCrud crud;
    //TASK CRONOMETRO
    public static Cronometro cronometro;
    //TIEMPO QUE DEMORO
    public String tiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigation = (NavigationView) findViewById(R.id.navigation);

        setSupportActionBar(toolbar);
        adaptadorPager = new AdaptadorPager(getSupportFragmentManager());
        pager.setAdapter(adaptadorPager);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_tab_1);
        tabs.getTabAt(1).setIcon(R.drawable.ic_tab_2);
        tabs.getTabAt(2).setIcon(R.drawable.ic_tab_3);
        tabs.getTabAt(3).setIcon(R.drawable.ic_tab_4);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        eventoDrawer();
        crud = new HistorialCrud(this);

        viewHistorialEstimote = new ViewHistorialEstimote();
        viewReportEstimote = new ViewReportEstimote();
        viewStateEstimote = new ViewStateEstimote();
        viewCronometer = new ViewCronometer();

        activaBluetooth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("si");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            }else{
                System.out.println("no");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);

            }
        }
        configEstimote();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private void eventoDrawer() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_drawer_1:
                        break;
                    case R.id.item_drawer_2:
                        break;
                    case R.id.item_drawer_3:
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }

    private void activaBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    private void configEstimote() {

        manager = new BeaconManager(getApplicationContext());

        manager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                manager.startMonitoring(new BeaconRegion("region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        61864, 19406));
            }

        });


        manager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                /*showNotification(
                        "Your gate closes in 47 minutes.",
                        "Current security wait time is 15 minutes, "
                                + "and it's a 5 minute walk from security to the gate. "
                                + "Looks like you've got plenty of time!");
                Log.e("INFO INFO","----------ENTER");
                */
                //txt.setText("Has Entrado en la zona de Ice");
                if (viewHistorialEstimote!=null && viewStateEstimote!=null) {
                    viewStateEstimote.textValue = "Conectado a Estimote Ice";
                    viewStateEstimote.txt.setText(viewStateEstimote.textValue);
                    viewStateEstimote.fondo = R.drawable.fondo_activo;
                    viewStateEstimote.relativeLayout.setBackgroundResource(viewStateEstimote.fondo);
                    //HORA Y FECHA
                    Date date = new Date();
                    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Historial historial = new Historial(0, "Entrada", dateFormat.format(date), hourFormat.format(date), "Entrada");
                    crud.insertar(historial);
                    viewHistorialEstimote.onResume();
                    //SI VUELVE A LA ZONA SE CALCULA EL TIEMPO QUE DEMORO ESTO VALIDANDO
                    //QUE NO ESTE CANCELADO
                    if (cronometro != null){
                        cronometro.cancel(true);
                        tiempo = viewCronometer.txthora.getText().toString()+":";
                        tiempo = tiempo.concat(viewCronometer.txtmin.getText().toString()+":");
                        tiempo = tiempo.concat(viewCronometer.txtseg.getText().toString()+":");
                        tiempo = tiempo.concat(viewCronometer.txtmil.getText().toString());
                        viewCronometer.txtfinal.setText(tiempo);
                    }
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                // Log.e("INFO INFO","----------EXIT");
                // txt.setText("Has salido de la zona de Ice");
                if (viewHistorialEstimote!=null && viewStateEstimote!=null) {
                    viewStateEstimote.textValue = "Desconectado de Estimote Ice";
                    viewStateEstimote.txt.setText(viewStateEstimote.textValue);
                    viewStateEstimote.fondo = R.drawable.fondo_disactivate;
                    viewStateEstimote.relativeLayout.setBackgroundResource(viewStateEstimote.fondo);
                    //HORA Y FECHA
                    Date date = new Date();
                    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Historial historial = new Historial(0, "Salida", dateFormat.format(date), hourFormat.format(date), "Salida");
                    crud.insertar(historial);
                    viewHistorialEstimote.onResume();
                    //SI SALE DE LA REGION SE ACTIVA Y PARTE EL CRONOMETRO
                    if(cronometro==null){
                        cronometro = new Cronometro(viewCronometer);
                        cronometro.execute();
                    }else if(cronometro.isCancelled()){
                        cronometro = new Cronometro(viewCronometer);
                        cronometro.execute();
                    }
                }
            }
        });
    }

    public class AdaptadorPager extends FragmentPagerAdapter {

        public AdaptadorPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return viewStateEstimote;
                case 1:
                    return viewHistorialEstimote;
                case 2:
                    return viewReportEstimote;
                case 3:
                    return viewCronometer;

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
                case 3:
                    return "";
            }
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.disable();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu1:
                delete();
                return true;
        }
        return true;
    }

    public void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Borrar Historial")
                .setMessage("Confirmas que quieres borrar el historial?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                crud.deleteAllHistorial();
                viewHistorialEstimote.onResume();
                toast("Historial eliminado");
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
                .create().show();
    }



    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
