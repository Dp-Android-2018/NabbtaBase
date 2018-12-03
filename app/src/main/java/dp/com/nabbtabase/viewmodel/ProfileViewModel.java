package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ProfileViewModel extends AndroidViewModel {
    public ObservableField<String> firstName;
    public ObservableField<String>lastName;
    public ObservableField<String>email;
    public ObservableField<String>phone;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }


    public void done(View view){
        if (ValidationUtils.isEmpty(firstName.get())||
                ValidationUtils.isEmpty(lastName.get())||
                ValidationUtils.isEmpty(email.get())||
                ValidationUtils.isEmpty(phone.get())){
            // TODO: 11/28/2018 call fill all data
        }
    }
}
