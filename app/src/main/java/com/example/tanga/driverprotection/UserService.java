package com.example.tanga.driverprotection;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
/*import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.UserInfos;*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private static UserService instance;

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }


    public interface UserServiceCheckUserCallBack{
        void onResponse(User user, boolean isAdded);
        void onFailure(String error);
    }
    public interface UserServiceGetUserCallBack{
        void onResponse(User user);
        void onFailure(String error);
    }



//as2el olfa chbi jé erreur fil user w rasatlek trodou final
    public void checkUser( User user, final UserServiceCheckUserCallBack callBack){
        StringRequest putRequest = new StringRequest(Request.Method.POST, UrlConst.checkUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean createdAccount = jsonObject.getBoolean("created");
                            Log.d("created", createdAccount+" ");
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            User checked = mGson.fromJson(jsonObject.getString("user"), User.class);
                            callBack.onResponse(checked,createdAccount);
                        } catch (JSONException e) {
                            callBack.onFailure(e.toString());
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onFailure(error.toString());

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
              //  params.put("social_id", user.getSocialId());
               // params.put("social_platform", user.getSocialPlatform());
                params.put("first_name", user.getFirstName());
                params.put("last_name", user.getLastName());
                params.put("photo", user.getPhoto());

                return params;
            }

        };

        putRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(putRequest);

    }




    public void getUser(int userId, final UserServiceGetUserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.FIND_A_USER+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                       // List<User> users = Arrays.asList(mGson.fromJson(response, User[].class));
                        User user = mGson.fromJson(response, User.class);
                       // callBack.onResponse(users.get(0));
                        callBack.onResponse(user);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }












}
