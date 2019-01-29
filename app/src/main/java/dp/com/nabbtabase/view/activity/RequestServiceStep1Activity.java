package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRequestServiceStep1Binding;
import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.adapter.GalleryAdapter;
import dp.com.nabbtabase.view.callback.DeleteImageIterface;
import dp.com.nabbtabase.view.callback.TaskMonitor;
import dp.com.nabbtabase.view.callback.UpdateTimeListener;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;

public class RequestServiceStep1Activity extends BaseActivity implements DeleteImageIterface, TaskMonitor {

    ActivityRequestServiceStep1Binding binding;
    List<String> mRecyclerImages;
    List<Uri> uris;
    GalleryAdapter mGalleryAdapter;
    List<File> mFiles;
    private ServiceRequest mServiceRequest;
    List<String> imagesUrls;
    int id;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra(ConfigurationFile.IntentConstants.SERVICE_ID, 0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_service_step1);
        initVariables();
    }

    public void initVariables() {
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://nabbta-7f58f.appspot.com");
        mServiceRequest=new ServiceRequest();
        mServiceRequest.setServiceId(id);
        mRecyclerImages = new ArrayList<>();
        mFiles = new ArrayList<>();
        uris = new ArrayList<>();
        imagesUrls = new ArrayList<>();
        mGalleryAdapter = new GalleryAdapter(this);
        resetRecycler();
        binding.rvImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,false,true));
        if(CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
    }

    public void resetRecycler() {
        mGalleryAdapter.setImageUrls(mRecyclerImages);
        binding.rvImages.setAdapter(mGalleryAdapter);
    }

    public void picImage(View view) {
        if (mRecyclerImages.size() < 5) {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.NONE) // set whether pick and / or camera action should return immediate result or not.
                    .toolbarImageTitle("Tap to select") // image selection title
                    .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                    .multi() // multi mode (default mode)
                    .limit(5) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    .start(); // start image picker activity with request code
        } else {
            showSnackBar(getResources().getString(R.string.upload_image_erroe_message));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            Picasso.with(this).load(new File(images.get(0).getPath())).into(binding.circleImageView);
            for (Image image : images) {
                uris.add(Uri.fromFile(new File(image.getPath())));
                File file = new File(image.getPath());
                URI uri = file.toURI();
                mRecyclerImages.add(uri.toString());
            }
            resetRecycler();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadImage() {
        CustomUtils.getInstance().showProgressDialog(this);
        System.out.println("uris size is : "+uris.size());
            CustomUtils.getInstance().uploadFireBasePic(mStorageRef,uris, this);
    }

    @Override
    public void deleteImage(int postion) {
        mRecyclerImages.remove(postion);
        uris.remove(postion);
        resetRecycler();
    }


    public void next(View view) {
        if (ValidationUtils.isEmpty(binding.etSize.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etDate.getText().toString())||
                uris.size()<1){
            showSnackBar(getResources().getString(R.string.fill_all_data_error_message));
        } else {
            uploadImage();
        }
    }

    public void setDate(View view) {
        CustomUtils.getInstance().showDatePickerDialog(this, selectedDate -> {
            CustomUtils.getInstance().showTimePickerDialog(RequestServiceStep1Activity.this, new UpdateTimeListener() {
                @Override
                public void onTimeSet(String selectedTime) {
                    binding.etDate.setText(new StringBuilder()
                            .append(selectedDate).append(" ").append(selectedTime));
                }
            });
        });
    }

    private void showSnackBar(String message) {
        Snackbar.make(binding.clRequestServiceStep1Root, message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void taskCompleted(List<String> photoUrl) {
        System.out.println("image url is : " + photoUrl.size());
        mServiceRequest.setSize(Float.valueOf(binding.etSize.getText().toString()));
        mServiceRequest.setDate(binding.etDate.getText().toString());
        mServiceRequest.setImages(photoUrl);
        CustomUtils.getInstance().cancelDialog();
            Intent intent = new Intent(RequestServiceStep1Activity.this, RequestServiceStep2Activity.class);
            intent.putExtra(ConfigurationFile.IntentConstants.SERVICE_REQUEST_1STEP_DATA, mServiceRequest);
            startActivity(intent);
    }
}
