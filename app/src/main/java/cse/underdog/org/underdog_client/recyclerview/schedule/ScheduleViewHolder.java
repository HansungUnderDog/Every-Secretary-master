package cse.underdog.org.underdog_client.recyclerview.schedule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.R;

public class ScheduleViewHolder extends ViewHolder{

    @Nullable
    @BindView(R.id.textView)
    public TextView textView;


    public ScheduleViewHolder(View itemView){
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


}
