package dp.com.nabbtabase.viewmodel;

import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;

public class ListItemServiceHistoryViewModel {

    private ServiceHistoryItem serviceHistoryItem;

    public ListItemServiceHistoryViewModel(ServiceHistoryItem serviceHistoryItem) {
        this.serviceHistoryItem = serviceHistoryItem;
    }

    public void setServiceHistoryItem(ServiceHistoryItem serviceHistoryItem) {
        this.serviceHistoryItem = serviceHistoryItem;
    }

    public String getName()
    {
        return serviceHistoryItem.getName();
    }

    public String getCode(){
        return serviceHistoryItem.getCode();
    }

    public String getStatus(){
        return serviceHistoryItem.getStatus();
    }

    public String getDate(){
        return serviceHistoryItem.getDate();
    }
}
