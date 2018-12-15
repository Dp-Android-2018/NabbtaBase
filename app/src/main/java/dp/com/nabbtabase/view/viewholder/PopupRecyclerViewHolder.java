package dp.com.nabbtabase.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dp.com.nabbtabase.R;

public class PopupRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public PopupRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.tv_country_name);

    }
}
