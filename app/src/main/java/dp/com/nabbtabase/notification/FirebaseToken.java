package dp.com.nabbtabase.notification;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.reactivex.annotations.NonNull;

public class FirebaseToken {

    private static FirebaseToken instance;

    private FirebaseToken() {
    }

    public static FirebaseToken getInstance() {
        if(instance==null){
            instance=new FirebaseToken();
        }
        return instance;
    }

    public LiveData<String> getFirebaseToken(){
        MutableLiveData<String> deviceToken=new MutableLiveData<>();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //  Log.w(TAG, "getInstanceId failed", task.getException());
                        System.out.println("device Token 1: Failed");
                        return;
                    }
                    // Get new Instance ID token
                     deviceToken.setValue(task.getResult().getToken());
                    System.out.println("device Token 1:"+deviceToken);
                });
        return deviceToken;
    }

}
