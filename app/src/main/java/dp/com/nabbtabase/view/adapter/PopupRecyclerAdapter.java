package dp.com.nabbtabase.view.adapter;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.City;
import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import dp.com.nabbtabase.view.viewholder.PopupRecyclerViewHolder;

public class PopupRecyclerAdapter extends RecyclerView.Adapter<PopupRecyclerViewHolder> {
    private List<Country>countries=new ArrayList<>();
    private CloseCountryDialogInterface closeDialog;
    private List<City>cities=new ArrayList<>();
    private int select;


    public PopupRecyclerAdapter(CloseCountryDialogInterface closeDialog) {
       this.closeDialog=closeDialog;
       //Log.i("Countries size adapter",""+countries.getValue().size());

    }

    @NonNull
    @Override
    public PopupRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.popup_recycler_list_item,viewGroup,false);
        PopupRecyclerViewHolder viewHolder=new PopupRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopupRecyclerViewHolder popupRecyclerViewHolder, int i) {
       String name;
        if(select==1){
            name=countries.get(i).getName();
        }else {
            name=cities.get(i).getName();
        }

        popupRecyclerViewHolder.textView.setText(name);
        popupRecyclerViewHolder.itemView.setOnClickListener(v -> {
           closeDialog.onListItemClicked(i);
        });
    }

    @Override
    public int getItemCount() {
        if(select==1){
            return countries.size();
        }else {
            return cities.size();
        }

    }

    public void setCountries(List<Country> countries) {
        this.countries.addAll(countries);
        select=1;
    }

    public void setCities(List<City> cities) {
        this.cities.addAll(cities);
        select=2;
    }
}
