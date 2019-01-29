package dp.com.nabbtabase.servise.model.pojo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dp.com.nabbtabase.utils.NetWorkConnection;

public class ConnectionReceiver extends BroadcastReceiver {

    public static ConnectionReceiverListener connectionReceiverListener;

    public ConnectionReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (connectionReceiverListener != null) {
            connectionReceiverListener.onNetworkConnectionChanged(NetWorkConnection.isConnectingToInternet(context));
        }
    }


    public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
