package cse.underdog.org.underdog_client.memo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cse.underdog.org.underdog_client.R;

public class MemoAdapter extends BaseAdapter {
    LayoutInflater inflater;
    int layout;
    ArrayList<MemoInfo> memoInfos;
    public MemoAdapter(Context context, int layout, ArrayList<MemoInfo> memoInfos){
        this.layout = layout;
        this.memoInfos = memoInfos;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return memoInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return memoInfos.get(i).getContext();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=inflater.inflate(layout, viewGroup, false);
        }

        MemoInfo memoInfo = memoInfos.get(i);

        TextView contextView = (TextView)view.findViewById(R.id.memo_title);
        System.out.println("메모인포다"+memoInfo.getContext());
        contextView.setText(memoInfo.getContext());
        return view;
    }
}
