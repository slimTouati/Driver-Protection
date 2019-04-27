package com.example.tanga.driverprotection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;



public class UserProfileActivity extends AppCompatActivity {

   /* ImageView avatarView;
    TextView name,followers,following,collabs;
    RatingBar rating;
    CheckBox follow;
    ImageView share;
    User user = new User();

    User current = new User();
    String token;
    ListView recyclerView;
    boolean isFollowed = false;
    int finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        token = SessionManager.getToken(getApplicationContext());
        int id = getIntent().getIntExtra("user", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        finish = getIntent().getIntExtra("finish", 0);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);
        avatarView = findViewById(R.id.user_avatar);
        name = findViewById(R.id.user_name);
        followers = findViewById(R.id.user_followers);
        following = findViewById(R.id.user_following);
        rating = findViewById(R.id.user_rating);
        collabs = findViewById(R.id.user_collabs);
        share = findViewById(R.id.share_user);
        follow = findViewById(R.id.follow);
        recyclerView = findViewById(R.id.user_profile_list);

        UserService.getInstance().getUser(id, new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User u) {
                user = u;
                PostsListAdapter adapter = new PostsListAdapter(UserProfileActivity.this,user.getPosts());
                recyclerView.setAdapter(adapter);
                if(current.getRole().equals("charity"))
                {
                    follow.setVisibility(View.GONE);
                }
                name.setText(user.getFirstName()+ " "+ user.getLastName());
                following.setText(String.valueOf(user.getFollowing().size()));
                followers.setText(String.valueOf(user.getFollowers().size()));
                following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserProfileActivity.this,FollowersFollowingActivity.class);
                        intent.putExtra("liste", 0);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });

                followers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserProfileActivity.this,FollowersFollowingActivity.class);
                        intent.putExtra("liste", 1);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });
                collabs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserProfileActivity.this,UserCollabsListActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });

                if(user.getFollowers().contains(current))
                {
                    //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                    follow.setChecked(true);
                    isFollowed = true;
                }
                else
                {
                    //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                    follow.setChecked(false);
                    isFollowed = false;
                }
                if(user.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), getResources().getColor(R.color.colorAccent));
                    avatarView.setImageDrawable(drawable);
                    //avatarView.setImageDrawable(new LetterAvatar(UserProfileActivity.this, getResources().getColor(R.color.colorAccent), user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), 20));
                }
                else
                {
                    Picasso.get().load(user.getPhoto()).noFade().resize(525, 559).centerCrop().into(avatarView);

                }

                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFollowed)
                        {
                            UserService.getInstance().unfollow(id, current.getId(), new UserService.UserServiceUnfollowCallBack() {
                                @Override
                                public void onResponse() {
                                }
                                @Override
                                public void onFailure(String error) {
                                }
                            });
                            follow.setChecked(false);
                            //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                            user.getFollowers().remove(current);
                            followers.setText(String.valueOf(user.getFollowers().size()));
                            isFollowed =false;
                        }
                        else
                        {
                            UserService.getInstance().follow(id, current.getId(),token, current.getFirstName()+ " "+ current.getLastName(), new UserService.UserServiceFollowCallBack() {
                                @Override
                                public void onResponse() {
                                }

                                @Override
                                public void onFailure(String error) {
                                }
                            });
                            follow.setChecked(true);
                            //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                            user.getFollowers().add(current);
                            followers.setText(String.valueOf(user.getFollowers().size()));
                            isFollowed = true;
                        }
                    }
                });


            }

            @Override
            public void onFailure(String error) {
                Log.d("error", error);
            }

        });
        UserService.getInstance().getInfos(id, new UserService.UserServiceGetInfosCallBack() {
            @Override
            public void onResponse(UserInfos u) {
                infos = u;
                collabs.setText(String.valueOf(infos.getCollaborations()));
                rating.setRating((infos.getRating()/100));
            }

            @Override
            public void onFailure(String error) {

            }

        });
        Log.d("user", user.getFirstName() + " followers");




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, user.getFirstName()+" "+user.getLastName());
                share.putExtra(Intent.EXTRA_TEXT, UrlConst.IMAGES+user.getPhoto());
                UserProfileActivity.this.startActivity(Intent.createChooser(share, "Share link!"));


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
