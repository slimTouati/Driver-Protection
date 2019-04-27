package com.example.tanga.driverprotection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    LinearLayout textsplash, texthome, menus;
    ImageView bgapp, clover;
    SessionManager session;
    User u ;
    TextView xx,hour,tarhib,toprofile;
    Animation frombottom;
    int begin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        u = new User();
        session=new SessionManager(this);
        session.getLogin(u);
       // Toast.makeText(this,"hello "+u.getFirstName(),Toast.LENGTH_SHORT).show();
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = (ImageView) findViewById(R.id.bgapp);
     //   clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
      //  menus = (LinearLayout) findViewById(R.id.menus);
        xx = (TextView) findViewById(R.id.xx);
        hour = (TextView) findViewById(R.id.day);
        tarhib = (TextView) findViewById(R.id.tarhib);
       // toprofile = (TextView) findViewById(R.id.hometoprofile);
        begin = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        bgapp.animate().translationY(-2400).setDuration(1500).setStartDelay(300);
//        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        if (begin >=6 && begin <= 12){
            hour.setText("Good morning");

        }
        else if (begin >12 && begin <= 19){
            hour.setText("Good afternoon");

        }
        else {
            hour.setText("Good evening");
        }
        xx.setText(u.getFirstName());
        tarhib.setText(hour.getText()+" "+xx.getText() + ",");
       /* toprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);
            }
        });*/
        textsplash.animate().translationY(140).alpha(0).setDuration(3000).setStartDelay(300);


        texthome.startAnimation(frombottom);
//        menus.startAnimation(frombottom);
      /* Handler handler = new Handler();
        int delay = 3000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                Intent intent = new Intent(HomeActivity.this, Home2Activity.class);
                startActivity(intent);
                handler.postDelayed(this, delay);
            }
        }, delay);*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Intent intent = new Intent(HomeActivity.this, Home2Activity.class);
                startActivity(intent);
            }
        }, 2000);


       // CircleMenu circleMenu = findViewById(R.id.circlemenu);
       /* final String[] menus = {
                "chat",
                "ambulance",
                "diagnostic",
                "notification"

        };*/

  /*      circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_add, R.drawable.ic_clear)
                .addSubMenu( Color.parseColor("#303F9F"), R.drawable.chatt)
                .addSubMenu(Color.parseColor("#4CAF50"), R.drawable.traficaccident)
                .addSubMenu(Color.parseColor("#E5E5E5"), R.drawable.diagnos)
                .addSubMenu(Color.parseColor("#ffa219"), R.drawable.notif)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {

                        if(i==0){
                            //Toast.makeText(HomeActivity.this, "You click " + menus[i], Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, ChatRoomActivity.class);
                            startActivity(intent);
                            }
                       else if(i==3)
                        {
                            Intent intent = new Intent(HomeActivity.this, PredictionActivity.class);
                            startActivity(intent);
                        }
                    }
                });*/

    }
}
