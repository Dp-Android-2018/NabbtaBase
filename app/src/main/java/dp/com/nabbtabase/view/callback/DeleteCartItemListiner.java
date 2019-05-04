package dp.com.nabbtabase.view.callback;

import androidx.lifecycle.LiveData;

public interface DeleteCartItemListiner {

    void itemDeleted(LiveData<Integer> code , int cardId,double total);
}
