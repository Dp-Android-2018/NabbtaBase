package dp.com.nabbtabase.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.activity.ServiceDetailedActivity;

public class ServiceListItemViewModel {

    private ServiceContent service;
    private Context context;

    public ServiceListItemViewModel(ServiceContent service,Context context) {
        this.service = service;
        this.context=context;
    }

    public void setService(ServiceContent service) {
        this.service = service;
    }

    public String getName(){
        return service.getName();
    }

    public String getImageUrl(){
        return service.getImageUrl();
    }

    public void onItemClick(View view){
        Intent intent=new Intent(context,ServiceDetailedActivity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.SERVICE_DATA,service);
        context.startActivity(intent);
    }
}
