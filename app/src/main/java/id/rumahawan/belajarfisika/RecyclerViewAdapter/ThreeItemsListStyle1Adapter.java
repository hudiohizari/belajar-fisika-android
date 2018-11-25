package id.rumahawan.belajarfisika.RecyclerViewAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.R;

public class ThreeItemsListStyle1Adapter extends RecyclerView.Adapter<ThreeItemsListStyle1Adapter.ThreeItemsViewHolder> {
    private ArrayList<ThreeItems> dataList;

    public ThreeItemsListStyle1Adapter(ArrayList<ThreeItems> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ThreeItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_three_items_style1, parent, false);
        return new ThreeItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreeItemsViewHolder holder, int position) {
        holder.ivMain.setImageResource(R.drawable.ic_launcher_foreground);
        holder.tvId.setText(dataList.get(position).getId());
        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvSubtitle.setText(dataList.get(position).getSubtile());
        holder.tvSubSubtitle.setText(dataList.get(position).getSubsubtitle());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class ThreeItemsViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMain;
        private TextView tvId, tvTitle, tvSubtitle, tvSubSubtitle;

        ThreeItemsViewHolder(View itemView) {
            super(itemView);

            ivMain = itemView.findViewById(R.id.ivMain);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvSubSubtitle = itemView.findViewById(R.id.tvSubSubtitle);
        }
    }
}
