package com.example.tanga.driverprotection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder> {
    private List<Message> MessageList;
    public Context context ;
    int lastPosition = -1;
    User current = new User();

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView message;
        public ImageView image;
        public CardView card;




        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.nickname);
            message = (TextView) view.findViewById(R.id.message);
            image = view.findViewById(R.id.user_pic);
            card = view.findViewById(R.id.card_msg);
        }
    }

    public ChatRoomAdapter(List<Message>MessagesList, Context c) {

        this.MessageList = MessagesList;
        context = c;
        /*current.setId(1);
        current.setFirstName("slim");
        current.setLastName("touati");
        current.setPhoto("image.jpg");*/

        SessionManager sessionManager = new SessionManager(context);
        sessionManager.getLogin(current);


    }

    @Override public int getItemCount() {
        return MessageList.size();
    }

    @Override public ChatRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        animation.setDuration(500);
        itemView.startAnimation(animation);

        return new ChatRoomAdapter.MyViewHolder(itemView);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override public void onBindViewHolder(final ChatRoomAdapter.MyViewHolder holder, final int position) {

        Message m = MessageList.get(position);

        UserService.getInstance().getUser(Integer.parseInt(m.getNickname()), new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(final User user) {
                holder.username.setText(user.getFirstName() + " "+ user.getLastName());
                holder.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if(current.getRole().equals("user"))
                        {*/
                           if(current.getId() != user.getId())
                            {
                            Intent intent = new Intent(context, Profile.class);
                            intent.putExtra("user", user.getId());

                                ((Activity) context).startActivity(intent);
                          //  ((Activity) context).startActivityForResult(intent, 2);


                        }
                        else{
                               Intent intent = new Intent(context, Profile.class);
                               intent.putExtra("user", current.getId());
                             //  ((Activity) context).startActivityForResult(intent, 2);
                               ((Activity) context).startActivity(intent);
                           }
                    //}
                    }
                });
                if(user.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
                    holder.image.setImageDrawable(drawable);
                    }
                else
                {
                    Picasso.get().load(UrlConst.IMAGES+user.getPhoto()).noFade().resize(250, 250).centerCrop().into(holder.image);

                }
            }

            @Override
            public void onFailure(String error) {

            }

        });

        holder.message.setText(m.getMessage() );
        if(m.getNickname().equals(String.valueOf(current.getId())))
        {
            holder.card.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
        }

        setAnimation(holder.card, position);


    }



}