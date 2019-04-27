package com.example.tanga.driverprotection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home2Activity extends AppCompatActivity {
Button pred , diag , decclare,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        pred = (Button) findViewById(R.id.predbut);
        diag=(Button) findViewById(R.id.diagnosbut);
        decclare = (Button) findViewById(R.id.decclareacc);
        profile = (Button) findViewById(R.id.profilebut);
       /* pred.setVisibility(View.INVISIBLE);

        diag.setVisibility(View.INVISIBLE);
        decclare.setVisibility(View.INVISIBLE);
        profile.setVisibility(View.INVISIBLE);
*/

        pred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2Activity.this, PredictionActivity.class);
                startActivity(intent);
            }
        });
    }
}
