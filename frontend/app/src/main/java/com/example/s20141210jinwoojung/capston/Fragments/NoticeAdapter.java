package com.example.s20141210jinwoojung.capston.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s20141210jinwoojung.capston.R;
import com.example.s20141210jinwoojung.capston.model.SnsContent;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    Context context;
    ArrayList<SnsContent> noticeList; //공지사항 정보 담겨있음

    public NoticeAdapter(Context context, ArrayList<SnsContent> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageview.setImageResource(noticeList.get(i).getmImage());
        viewHolder.subject.setText(noticeList.get(i).getmSubject());
        viewHolder.content.setText(noticeList.get(i).getContent());
    }
    /*
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HashMap<String,String> noticeItem = noticeList.get(position);
        holder.tv_writer.setText(noticeItem.get("writer")); //작성자
        Log.e("[writer]", noticeItem.get("writer"));
        holder.tv_title.setText(noticeItem.get("title")); //제목
        holder.tv_content.setText(noticeItem.get("content")); //내용 일부
        holder.tv_date.setText(noticeItem.get("regist_day")); //작성일
    }*/

    @Override
    public int getItemCount() {
        return this.noticeList.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageview;
        public TextView subject;
        public TextView content;

        public ViewHolder(View v) {
            super(v);
            imageview  = v.findViewById(R.id.imageView);
            subject = v.findViewById(R.id.tv_title);
            content = v.findViewById(R.id.tv_date);
        }

    }
}

