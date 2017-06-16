package com.firstbit.sportsklub.homescreen.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstbit.sportsklub.R;
import com.firstbit.sportsklub.homescreen.data.Feed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hp-pc on 20-05-2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {
    private ArrayList<Feed> dataSet;
    private Context context;
    private HomeFilter filter;
    private AdapterView.OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, ArrayList<Feed> homeResponse, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataSet = homeResponse;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        final Feed feed = dataSet.get(position);
        String thumbnail = feed.getImage().trim();
        if (thumbnail != null && !thumbnail.isEmpty() )
            Picasso.with(context).load(thumbnail).fit().centerCrop().into(holder.ivThumbnail);
        holder.tvPostTitle.setText(feed.getName());
        holder.tvPostDate.setText(feed.getFormattedDate());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new HomeFilter(this, dataSet);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivThumbnail;
        public TextView  tvPostTitle;
        public TextView  tvPostDate;
        public View view;
        public CardView viewItem;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            tvPostTitle = (TextView) itemView.findViewById(R.id.tv_post_title);
            tvPostDate = (TextView) itemView.findViewById(R.id.tv_game_date);
            viewItem = (CardView) itemView.findViewById(R.id.card_view);
            viewItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
    private class HomeFilter extends Filter {

        private final HomeAdapter adapter;
        private final List<Feed> originalList;
        private final List<Feed> filteredList;

        private HomeFilter(HomeAdapter adapter, List<Feed> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (charSequence.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Feed item : originalList) {
                    if (item.getName().toLowerCase().contains(filterPattern) ){
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataSet.clear();
            dataSet.addAll((ArrayList<Feed>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }
}
