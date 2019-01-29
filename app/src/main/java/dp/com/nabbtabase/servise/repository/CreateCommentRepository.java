package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import javax.security.auth.callback.Callback;

import dp.com.nabbtabase.servise.model.request.CreateCommentRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateCommentRepository {

    private static CreateCommentRepository instance;
    private CallBackInterface callBackInterface;
    private CreateCommentRepository() {
    }

    public static CreateCommentRepository getInstance() {
        if (instance==null){
            instance=new CreateCommentRepository();
        }
        return instance;
    }

    public void createComment(Application application, CreateCommentRequest request,String token){
        CustomUtils.getInstance().getEndpoint(application).createComment(
               token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    System.out.println("Create Comment Code :"+stringResponseResponse.code());
                    callBackInterface.updateUi(stringResponseResponse.code());
                }, throwable -> {
                    System.out.println("Create Comment error :"+throwable.getMessage());
                });
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }
}
