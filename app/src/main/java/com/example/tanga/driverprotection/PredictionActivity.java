package com.example.tanga.driverprotection;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.github.anastr.speedviewlib.AwesomeSpeedometer;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PredictionActivity extends AppCompatActivity {
    ConstraintLayout constraintpred ;
    AwesomeSpeedometer speedometer;
    TextView hourinterval,city,meteo,summary,pred;
    int begin,end,dayofmonth,month,year,situation,pt_km,cityval=0;
    String day,DARK_SKY_API_KEY,option_list,transformedmonth,ad;
    double longitude=10.471516,latitude=36.635198,longitudegp1=10.183097,latitudegp1=36.789149;
    int drunk=0;
    double prediction=0;
     MediaPlayer stopspeed ;
    SessionManager session;
    User u ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        u = new User();
        session=new SessionManager(this);
        session.getLogin(u);
        stopspeed = MediaPlayer.create(this, R.raw.stopspeed);
         DARK_SKY_API_KEY = "efc5d7378dc028e5969fe0085c7a7866";
         option_list = "exclude=currently,minutely,hourly,alerts&units=si";
         constraintpred = (ConstraintLayout) findViewById(R.id.constraintprediction);
        speedometer = findViewById(R.id.awesomeSpeedometer);
        final int min = 20;
        final int max = 99;
        final int random = new Random().nextInt((max - min) + 1) + min;
        speedometer.speedTo(random);
        meteo = findViewById(R.id.tempval);
        pred=findViewById(R.id.predictionvalue);
        begin = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        end = begin +1 ;
        dayofmonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        if(month<10)
        {
            transformedmonth = "0"+String.valueOf(month);
        }
        else transformedmonth = String.valueOf(month);
        year = Calendar.getInstance().get(Calendar.YEAR);

        day = String.valueOf(year)+"-"+transformedmonth+"-"+String.valueOf(dayofmonth)+"T00:00:00";

        situationcheck(speedometer);
        ad=getAdress(longitude,latitude);
        pt_km  = getkm(latitude,longitude,latitudegp1,longitude);
        Log.d("km num : ",String.valueOf(pt_km));
        citycheck(ad);
        getWeather();
        getPrediction();
       /*  meteo = findViewById(R.id.tempval);
         summary = findViewById(R.id.weathersummary);
         pred=findViewById(R.id.predictionvalue);
         begin = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
         end = begin +1 ;
         dayofmonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
         month = Calendar.getInstance().get(Calendar.MONTH);
         if(month<10)
         {
             transformedmonth = "0"+String.valueOf(month);
         }
         else transformedmonth = String.valueOf(month);
         year = Calendar.getInstance().get(Calendar.YEAR);

        day = String.valueOf(year)+"-"+transformedmonth+"-"+String.valueOf(dayofmonth)+"T00:00:00";
        city = findViewById(R.id.city);
        speedometer = findViewById(R.id.awesomeSpeedometer);
        hourinterval = findViewById(R.id.hourinterval);
        hourinterval.setText(String.valueOf(begin)+"H-"+String.valueOf(begin+1)+"H");
        final int min = 20;
        final int max = 99;
        final int random = new Random().nextInt((max - min) + 1) + min;
        speedometer.speedTo(random);
        getWeather();
        situationcheck(speedometer);
        ad=getAdress(longitude,latitude);
        city.setText(ad);
        pt_km  = getkm(latitude,longitude,latitudegp1,longitude);
        Log.d("km num : ",String.valueOf(pt_km));
        citycheck(ad);*/
     /*   Handler handler = new Handler();
        int delay = 7000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                getPrediction();
                handler.postDelayed(this, delay);
            }
        }, delay);
*/


       // getPrediction();

    }
    private void situationcheck(Speedometer speedometer)
    {
        if(speedometer.getSpeed()>80)
        {
            situation = 1;
            constraintpred.setBackgroundResource(R.drawable.predictionbgspeed);
        }
        if(drunk==1)
        {
            situation=2;
        }
    }
    private void citycheck(String ad)
    {
        if(ad.toUpperCase().equals("BEN AROUS,TUNISIA"))
        {
            cityval = 0 ;
        }
       else if(ad.toUpperCase().equals("SOUSSE,TUNISIA"))
        {
            cityval =1;
        }
        else if(ad.toUpperCase().equals("SFAX,TUNISIA"))
        {
            cityval =2;
        }
        else if(ad.toUpperCase().equals("GABES,TUNISIA"))
        {
            cityval =3;
        }
        else if(ad.toUpperCase().equals("NABEUL,TUNISIA"))
        {
            cityval =4;
        }
        else if(ad.toUpperCase().equals("MEDNIN,TUNISIA"))
        {
            cityval =5;
        }
        else if(ad.toUpperCase().equals("MONASTIR,TUNISIA"))
        {
            cityval =6;
        }
        else if(ad.toUpperCase().equals("MAHDIA,TUNISIA"))
        {
            cityval =7;
        }

    }

    private void getWeather(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://api.darksky.net/forecast/"+DARK_SKY_API_KEY+"/"+latitude+","+longitude+","+day+"?"+option_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   // JSONArray dataArray = new JSONArray(response);
                  JSONObject obj = new JSONObject(response);
                  JSONObject daily = obj.getJSONObject("daily");
                  JSONArray data = daily.getJSONArray("data");
                  JSONObject dkhoul = data.getJSONObject(0);
                  String temp = dkhoul.getString("temperatureMax");
                  if(dkhoul.has("precipType"))
                  {
                      if (dkhoul.getString("precipType").equals("rain"))
                      {

                            prediction+=0.05;
                            if(situation==1)
                            {constraintpred.setBackgroundResource(R.drawable.predictionspeedandrain);}
                            else if(situation==2)
                            {}
                            else
                            {
                                constraintpred.setBackgroundResource(R.drawable.predictionbgrain);
                            }
                      }
                  }
                  String sum = dkhoul.getString("summary");
                  meteo.setText(temp + "°C");
                //  summary.setText(sum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              /*  Log.d("okay msg", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                MessageList.addAll(Arrays.asList(mGson.fromJson(response, Message[].class)));


                chatBoxAdapter = new ChatRoomAdapter(MessageList, ChatRoomActivity.this);
                myRecylerView.setAdapter(chatBoxAdapter);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hell no", "Error " + error.getMessage());

            }
        }
        );
        Log.d("nchoufouha",stringRequest.toString());
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public String getAdress(double longitude,double latitude)
    {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        Log.d("haw chetjib :",address + ",city: "+city+ ",country: "+country+" "+state+" "+postalCode+ " "+knownName);
        return state+ ","+country;

    }
        public int getkm(double latA,double lngA,double latB,double lngB)
        {
            Location locationA = new Location("point A");

            locationA.setLatitude(latA);
            locationA.setLongitude(lngA);

            Location locationB = new Location("point B");

            locationB.setLatitude(latB);
            locationB.setLongitude(lngB);

            float distance = locationA.distanceTo(locationB);
            float distancekm = distance / 1000;
            Log.d("km float :",String.valueOf(distance));
            return (int)distancekm+1;

        }
    private void getPrediction(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,UrlConst.Prediction+"km="+pt_km+"&situation="+situation+"&city="+cityval+"&weather="+meteo.getText().toString().substring(0,5) +"&fin="+end+"&debut="+begin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // JSONArray dataArray = new JSONArray(response);
                    JSONObject obj = new JSONObject(response);

                     prediction += obj.getDouble("prediction");
                    if(prediction*100<70 && prediction*100>60)
                    {
                        pred.setTextColor(Color.parseColor("#FFDE03"));
                    }
                    if(prediction*100>=70)
                    {
                      pred.setTextColor(Color.parseColor("#B00020"));
                        stopspeed.start();

                    }
                    String predval = String.valueOf(prediction*100).substring(0,5);
                    pred.setText(predval+" %");
                    if(prediction*100>=80)
                    {
                        addPrediction(u.getId(),pred.getText().toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
              /*  Log.d("okay msg", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                MessageList.addAll(Arrays.asList(mGson.fromJson(response, Message[].class)));


                chatBoxAdapter = new ChatRoomAdapter(MessageList, ChatRoomActivity.this);
                myRecylerView.setAdapter(chatBoxAdapter);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hell no", "Error " + error.getMessage());

            }
        }
        );
        Log.d("nchoufouha",stringRequest.toString());
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    private void addPrediction(int userId, String predictionval) {

        StringRequest sr = new StringRequest(Request.Method.POST, UrlConst.addPrediction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(" new danger : ",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("el id mta3 l prediction",jsonObject.getString("_id"));
                  //  id_newuser = Integer.parseInt(jsonObject.getString("_id"));

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PredictionActivity.this, "error someweher in the prediction request", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("idUser", String.valueOf(userId));
                params2.put("prediction", predictionval);
                if(situation==0)
                {  params2.put("reason","driver in danger" );}
               else if(situation==1)
                {  params2.put("reason", "driver speeding" );}
                else
                {  params2.put("reason","drunk driver" );}

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };

           /* stringRequest.addMultipartParam("id_user","x-www-form-urlencoded",Integer.toString(id));
            stringRequest.addMultipartParam("id_cause","x-www-form-urlencoded",Integer.toString(c.getId()));
            AppController.getInstance().addToRequestQueue(stringRequest);*/
        Log.d("el requette",sr.toString());
        AppController.getInstance().addToRequestQueue(sr);
    }

    }



