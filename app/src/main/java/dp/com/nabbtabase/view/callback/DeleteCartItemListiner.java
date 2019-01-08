package dp.com.nabbtabase.view.callback;

import android.arch.lifecycle.LiveData;

public interface DeleteCartItemListiner {

    void itemDeleted(LiveData<Integer> code , int cardId,double total);
}
