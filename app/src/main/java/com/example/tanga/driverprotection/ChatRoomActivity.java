package com.example.tanga.driverprotection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public ChatRoomAdapter chatBoxAdapter;
    public EditText messagetxt ;
    public ImageButton send ;
    private Socket socket;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
      /* user.setId(1);
user.setFirstName("slim");
user.setLastName("touati");
user.setPhoto("image.jpg");*/

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setTitle("Report an accident");
        messagetxt = (EditText) findViewById(R.id.message) ;
        send = findViewById(R.id.send);

        try {

            socket = IO.socket(UrlConst.SERVER2);
            socket.connect();
            socket.emit("join",user.getId());
            Log.d("chat room joined", "onCreate: ");
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        MessageList = new ArrayList<>();
        myRecylerView = (RecyclerView) findViewById(R.id.messagelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());

         getMessages();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messagetxt.getText().toString().isEmpty()){
                    socket.emit("messagedetection",String.valueOf(user.getId()),messagetxt.getText().toString());
                    Log.d("el message",String.valueOf(user.getId())+"   " + messagetxt.getText().toString());
                    addMessage(String.valueOf(user.getId()),messagetxt.getText().toString());
                    messagetxt.setText(" ");
                }


            }
        });

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatRoomActivity.this,data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatRoomActivity.this,data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {

                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");
                            Message m = new Message(nickname,message);

                            MessageList.add(m);
                            //chatBoxAdapter = new ChatRoomAdapter(MessageList, ChatRoomActivity.this);
                            chatBoxAdapter.notifyDataSetChanged();
                            myRecylerView.setAdapter(chatBoxAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addMessage(String idUser, String msg) {

        StringRequest sr = new StringRequest(Request.Method.POST, UrlConst.addorgetMessage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(" new message : ",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("message howa : ",jsonObject.getString("message"));


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatRoomActivity.this, "error someweher in the request", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("idUser", idUser);
                params2.put("message", msg);


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

        AppController.getInstance().addToRequestQueue(sr);

    }
    private void getMessages(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.addorgetMessage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("okay msg", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                MessageList.addAll(Arrays.asList(mGson.fromJson(response, Message[].class)));


                chatBoxAdapter = new ChatRoomAdapter(MessageList, ChatRoomActivity.this);
                myRecylerView.setAdapter(chatBoxAdapter);

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
