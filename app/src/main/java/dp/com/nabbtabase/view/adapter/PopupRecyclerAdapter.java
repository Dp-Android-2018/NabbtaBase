package dp.com.nabbtabase.view.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.City;
import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import dp.com.nabbtabase.view.viewholder.PopupRecyclerViewHolder;

public class PopupRecyclerAdapter extends RecyclerView.Adapter<PopupRecyclerViewHolder> {
    private List<Country> countries;
    private CloseCountryDialogInterface closeDialog;
    private List<City> cities = new ArrayList<>();
    private int select;


    public PopupRecyclerAdapter(CloseCountryDialogInterface closeDialog) {
        this.closeDialog = closeDialog;
        //Log.i("Countries size adapter",""+countries.getValue().size());
    }

    public void setCountries(List<Country> countries) {
        if (this.countries == null) {
            this.countries = countries;
            notifyItemRangeInserted(0, countries.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PopupRecyclerAdapter.this.countries.size();
                }

                @Override
                public int getNewListSize() {
                    return countries.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PopupRecyclerAdapter.this.countries.get(oldItemPosition).getId() ==
                            countries.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Country newCountry = countries.get(newItemPosition);
                    Country oldCountry = countries.get(oldItemPosition);

                    return newCountry.getId() == oldCountry.getId()
                            && Objects.equals(newCountry.getName(), oldCountry.getName());
                }
            });
            this.countries = countries;
            result.dispatchUpdatesTo(this);
        }

        select = 1;
    }

    @NonNull
    @Override
    public PopupRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.popup_recycler_list_item, viewGroup, false);
        PopupRecyclerViewHolder viewHolder = new PopupRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopupRecyclerViewHolder popupRecyclerViewHolder, int i) {
        String name;
        if (select == 1) {
            name = countries.get(i).getName();
        } else {
            name = cities.get(i).getName();
        }

        popupRecyclerViewHolder.textView.setText(name);
        popupRecyclerViewHolder.itemView.setOnClickListener(v -> {
            closeDialog.onListItemClicked(i);
        });
    }

    @Override
    public int getItemCount() {
        if (select == 1) {
            return countries.size();
        } else {
            return cities.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        select = 2;
    }
}
