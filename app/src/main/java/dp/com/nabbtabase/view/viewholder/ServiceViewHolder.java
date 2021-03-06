package dp.com.nabbtabase.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dp.com.nabbtabase.databinding.ServiceListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.viewmodel.ServiceListItemViewModel;

public class ServiceViewHolder extends RecyclerView.ViewHolder {
    ServiceListItemBinding binding;
    public ServiceViewHolder(@NonNull ServiceListItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindService(ServiceContent service){
        if (binding.getViewModel()==null){
            binding.setViewModel(new ServiceListItemViewModel(service,itemView.getContext()));
        }else {
            binding.getViewModel().setService(service);
        }
    }
}
