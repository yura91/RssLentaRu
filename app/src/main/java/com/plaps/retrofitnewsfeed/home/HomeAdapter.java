package com.plaps.retrofitnewsfeed.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.plaps.retrofitnewsfeed.R;
import com.plaps.retrofitnewsfeed.models.RssFeedItem;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<RssFeedItem> data;
    private Context context;

    public HomeAdapter(Context context, List<RssFeedItem> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvTittle.setText(data.get(position).getTitle());
        holder.tvDesc.setText(data.get(position).getDescription());

if (data.get(position).getEnclosure() != null) {
    Glide.with(context)
            .load(data.get(position).getEnclosure().getUrl())
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .skipMemoryCache(true)
            .into(holder.background);
}
        else
{
    holder.background.setImageDrawable(null);
}

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(RssFeedItem Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTittle, tvDesc;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tittle);
            tvDesc = (TextView) itemView.findViewById(R.id.desc);
            background = (ImageView) itemView.findViewById(R.id.image);

        }


        public void click(final RssFeedItem rssListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(rssListData);
                }
            });
        }
    }


}
