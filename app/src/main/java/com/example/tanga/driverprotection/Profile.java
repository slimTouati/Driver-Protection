package com.example.tanga.driverprotection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    public ImageView PicProfile;
    public TextView Name, Level, Model, Brand,Plate;
    public LinearLayout Color;
    User userco = new User();
    User user = new User();
    Vehicle v = new Vehicle();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(userco);
        int id = getIntent().getIntExtra("user", 4);
      //  requestUser(id);
        //requestJsonObject();
      /*  PicProfile = (ImageView) findViewById(R.id.ImgPic);
       Name  = (TextView) findViewById(R.id.tv_name);
        Level = (TextView) findViewById(R.id.tv_Level);
        Model = (TextView) findViewById(R.id.tv_model);
        Brand = (TextView) findViewById(R.id.tv_brand);
        Color = (LinearLayout) findViewById(R.id.carcolor);
        Plate = (TextView) findViewById(R.id.tv_plate);*/
        //Name.setText(user.getFirstName());



    }
    private void requestJsonObject(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.getVehicule + String.valueOf(user.getVehicle()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("okay", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                v= mGson.fromJson(response, Vehicle.class);
                Model.setText(v.getModel());
                Brand.setText(v.getBrand());
                Color.setBackgroundColor(v.getColor());
                Plate.setText(v.getPlates());





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
    private void requestUser(int id){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.FIND_A_USER + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("okay", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                user= mGson.fromJson(response, User.class);
                Name.setText(user.getFirstName() + " " + user.getLastName());
                if(user.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
                    PicProfile.setImageDrawable(drawable);
                }
                else
                {
                    Picasso.get().load(UrlConst.IMAGES+user.getPhoto()).noFade().resize(250, 250).centerCrop().into(PicProfile);

                }




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
}
