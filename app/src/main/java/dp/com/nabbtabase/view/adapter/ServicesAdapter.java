package dp.com.nabbtabase.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ServiceListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.view.viewholder.ServiceViewHolder;

public class ServicesAdapter extends RecyclerView.Adapter<ServiceViewHolder> {

    List<ServiceContent> services;

    public void setServices(List<ServiceContent> services) {
        if(this.services==null)
        {
            this.services = services;
            notifyItemRangeInserted(0,services.size());
        }else {
            DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ServicesAdapter.this.services.size();
                }

                @Override
                public int getNewListSize() {
                    return services.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ServicesAdapter.this.services.get(oldItemPosition).getId()==
                            services.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
                {
                    ServiceContent newService=services.get(newItemPosition);
                    ServiceContent oldService=services.get(oldItemPosition);

                    return newService.getId()==oldService.getId()
                            &&Objects.equals(newService.getName(),oldService.getName());
                }
            });
            this.services=services;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ServiceListItemBinding binding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.service_list_item,viewGroup,false);
        return new ServiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
        serviceViewHolder.bindService(services.get(i));
    }

    @Override
    public int getItemCount() {
        return services==null?0:services.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
