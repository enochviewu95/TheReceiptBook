package com.example.thereceiptbook.Adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thereceiptbook.R;
import com.example.thereceiptbook.TransactionsClass;

import java.util.List;

public class ActivityFeedAdapter extends
        RecyclerView.Adapter<ActivityFeedAdapter.ViewHolder> {

    private String full_name;
    private String userPhoneNumber;
    private String[] date;
    private int[] user_imageid;
    private int[] customer_imageid;
    private String[] app_text;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //Define the view to be used for each data item

        private CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    public ActivityFeedAdapter(TransactionsClass transactionsClass){
        this.full_name = transactionsClass.getFullName();
        this.userPhoneNumber = transactionsClass.getPhoneNumber();
        this.date = transactionsClass.getDate();
        this.user_imageid = null;
        this.customer_imageid = null;
        this.app_text = transactionsClass.getTransactions();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cv = (CardView)LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activityfeed,viewGroup,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CardView cardView = viewHolder.cardView;
        ImageView userimage = cardView.findViewById(R.id.profile_image);
        //Drawable userDrawable =
                //ContextCompat.getDrawable(cardView.getContext(),user_imageid[i]);
        ImageView customerimage = cardView.findViewById(R.id.customer_image);
        //Drawable customerDrawable =
                //ContextCompat.getDrawable(cardView.getContext(),customer_imageid[i]);
        TextView user_fullname = cardView.findViewById(R.id.user_full_name);
        TextView app_textview = cardView.findViewById(R.id.info_text);
        TextView user_phone_number = cardView.findViewById(R.id.user_phone_number);
        TextView time_of_issue = cardView.findViewById(R.id.time_of_issue);

        //set views
        user_fullname.setText(full_name);
        user_phone_number.setText(userPhoneNumber);
        app_textview.setText(app_text[i]);
        time_of_issue.setText(date[i]);
        //userimage.setImageDrawable(userDrawable);
        //customerimage.setImageDrawable(customerDrawable);

    }


    @Override
    public int getItemCount() {
        return app_text.length;
    }

}
