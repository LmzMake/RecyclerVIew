package com.walxy.recyclerview.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.walxy.recyclerview.R;
import com.walxy.recyclerview.bean.Bean;

/**
 * 作者：王兵洋  2017/8/24 14:35
 * 类的用途：RecyclerView的适配器
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private Bean bean;
    private OnItemClickLitener mOnItemClickLitener;

    public MyAdapter(Context context, Bean bean) {
        this.context = context;
        this.bean = bean;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        //加载动画 渐变
        ObjectAnimator.ofFloat(holder.itemView, "alpha", 1f, 0f, 1f)
                .setDuration(1000)
                .start();

        holder.ms.setText(bean.data.get(position).introduction);
        holder.age.setText(bean.data.get(position).userAge + "");
        holder.work.setText(bean.data.get(position).occupation);

        Glide.with(context).load(bean.data.get(position).userImg).into(holder.img);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bean.data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView age, work, ms;
        ImageView img;

        public MyHolder(View itemView) {
            super(itemView);
            age = itemView.findViewById(R.id.age);
            work = itemView.findViewById(R.id.work);
            img = itemView.findViewById(R.id.img);
            ms = itemView.findViewById(R.id.ms);
        }
    }
}
