package com.samsung.android.example.helloaccessoryprovider.service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dme.forecastiolib.FIOAlerts;
import com.dme.forecastiolib.FIOCurrently;
import com.dme.forecastiolib.ForecastIO;
import com.samsung.android.example.helloaccessoryprovider.R;


public class MyActivity extends Activity {
    public static String icona = "";
    public static String info_pass = "hi";


   // public static


    @Override
    protected void onCreate(Bundle savedInstanceState) {
          LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
         final ForecastIO fio = new ForecastIO("515acf5ece2f17123b515b6388b0312c");
          String lat = "";
          String lon = "";
          Location loc;
         loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Double lati = loc.getLatitude();
        Double longi = loc.getLongitude();

         lat = String.valueOf(lati);
       lon = String.valueOf(longi);
        fio.getForecast(lat, lon);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView myWebView = (WebView) findViewById(R.id.webview);


        myWebView.setInitialScale(1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);


        Button button1 = (Button) findViewById(R.id.button);
        //myWebView.setWebViewClient(new WebViewClient());

        myWebView.loadUrl("file:///android_asset/www/index.html"); //load local url

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //turn javascript on
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        Toast.makeText(getApplicationContext(), "Powered by Forecast.io", Toast.LENGTH_LONG).show();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //bad

        StrictMode.setThreadPolicy(policy); //bad
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationListener listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };

//        Location loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//         Double lati = loc.getLatitude();
//        Double longi = loc.getLongitude();
//
//       final String lat = String.valueOf(lati);
//       final String lon = String.valueOf(longi);
//        fio.getForecast(lat, lon);




      //  WebView mWebView = new WebView(com.samsung.android.example.helloaccessoryprovider.service.WebAppInterface.mContext); //may not need

//        ForecastIO fio3 = new ForecastIO("515acf5ece2f17123b515b6388b0312c");
//
//        fio3.getForecast(lat, lon);
//        FIOCurrently cura = new FIOCurrently(fio3);
//
//        icona = cura.get().icon();

         //       com.samsung.android.example.helloaccessoryprovider.service.WebAppInterface.getIcon(icona);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView text1 = (TextView) findViewById(R.id.textView);
                //  text1.setMovementMethod(new ScrollingMovementMethod());

                //   fio.setUnits(ForecastIO.UNITS_SI);
                //String lat = "34.837222";
                //String lon = "-97.6075";
                //  fio.setExcludeURL("hourly,minutely");




                //com.samsung.android.example.helloaccessoryprovider.service.WebAppInterface.getIcon(cur.get().icon());
                FIOCurrently cur = new FIOCurrently(fio);
                double nearest_storm = cur.get().nearestStormDistance();
                FIOAlerts alerts = new FIOAlerts(fio);

                if (nearest_storm != 0)
                    Toast.makeText(getApplicationContext(), "The distance from you is: " + nearest_storm + "miles away", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "There is currently a storm in the area", Toast.LENGTH_LONG).show();

                }
                /* MAKE WEATHER HOURLY */
//                FIOHourly hourly = new FIOHourly(fio);
//                if(hourly.hours()<0)
//                    text1.append("No hourly data.");
//                else
//                    text1.append("\nHourly:\n");
//                //Print hourly data
//                for(int i = 0; i<hourly.hours(); i++){
//                    String [] h = hourly.getHour(i).getFieldsArray();
//                    text1.append("Hour #"+(i+1));
//                    for(int j=0; j<h.length; j++)
//                        text1.append(h[j] + ": " + hourly.getHour(i).getByKey(h[j]));
//                    text1.append("\n\n");
//                }


                if (alerts.NumberOfAlerts() <= 0) {
                    text1.setText("No alerts");
                    // System.out.println("No alerts"); //ie dont send to the watch
                } else {

                    text1.setText("ALERT: ");
                    for (int i = 0; i < alerts.NumberOfAlerts(); i++) {
                        text1.append(alerts.getAlertTitle(i));
                        //      System.out.println(alerts.getAlert(i));  //print alerts out and send to watch
                        text1.append(" EXPIRES: " + alerts.getAlertExpireTime(i));
                    }
                    //  String cur_alerts_des = alerts.getAlertDescription(0);
                    //  String cur_alerts_expire = alerts.getAlertExpireTime(0);
                }
            }
        });


      //  NetworkServiceTask net = new NetworkServiceTask();
        //ForecastService.Request.newBuilder("515acf5ece2f17123b515b6388b0312c");



    }

//    public String getIcon(){
//        ForecastIO fio2 = new ForecastIO("515acf5ece2f17123b515b6388b0312c");
//
//        FIOCurrently cur = new FIOCurrently(fio2);
//        return cur.get().icon();
//    }

//    @JavascriptInterface
//    public String getAIcon(){
//
//       return icona;
//    }

    public static String please ()  {

       // Location location = this.createGeo();
        ForecastIO fio = new ForecastIO("515acf5ece2f17123b515b6388b0312c");
        fio.getForecast("30", "90");
        FIOCurrently curr = new FIOCurrently(fio);
        info_pass = "Nearest storm is: " + String.valueOf(curr.get().nearestStormDistance()) + " miles away";
        if (String.valueOf(curr.get().nearestStormDistance()) == "0") {
        info_pass = "There is currently a storm in your area";
        }
       return info_pass;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

