package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRequestServiceStep1Binding;
import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.repository.UploadImageRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.adapter.GalleryAdapter;
import dp.com.nabbtabase.view.callback.DeleteImageIterface;
import dp.com.nabbtabase.view.callback.UpdateDateListener;
import dp.com.nabbtabase.view.callback.UpdateTimeListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestServiceStep1Activity extends AppCompatActivity implements DeleteImageIterface {

    ActivityRequestServiceStep1Binding binding;
    List<String> mRecyclerImages;
    List<Uri> uris ;
    GalleryAdapter mGalleryAdapter;
    List<File>mFiles;
    private ServiceRequest mServiceRequest;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra(ConfigurationFile.IntentConstants.SERVICE_ID, 0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_service_step1);
        ImagePicker.setMinQuality(600, 600);
        initVariables();
    }

    public void initVariables() {
        mServiceRequest=new ServiceRequest();
        mServiceRequest.setServiceId(id);
        mRecyclerImages = new ArrayList<>();
        mFiles=new ArrayList<>();
        uris = new ArrayList<>();
        mGalleryAdapter = new GalleryAdapter(this);
        resetRecycler();
        binding.rvImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void resetRecycler() {
        mGalleryAdapter.setImageUrls(mRecyclerImages);
        binding.rvImages.setAdapter(mGalleryAdapter);
    }

    public void picImage(View view) {
        if(mRecyclerImages.size()<5) {
            ImagePicker.pickImage(this, "select your Image");
        }else{
            showSnackBar(getResources().getString(R.string.upload_image_erroe_message));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        String path =ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
        if (bitmap != null) {
            binding.circleImageView.setImageBitmap(bitmap);
            if (path!= null) {
                uris.add(Uri.parse(path));
            }
            File file = new File(ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data));
            mFiles.add(file);
            System.out.println("Filename " +file);
            URI uri=file.toURI();
            mRecyclerImages.add(uri.toString());
            resetRecycler();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadImage(View view){
         List<MultipartBody.Part> filesToUpload = new ArrayList<>();
        List<RequestBody> Files=new ArrayList<>();

        for(int i=0;i<uris.size();i++) {
           // Files.add(RequestBody.create(MediaType.parse("image/*"), mFiles.get(i)));
           // filesToUpload.add(MultipartBody.Part.createFormData("images[]", mFiles.get(i).getName(), Files.get(i)));
           // filesToUpload.add(prepareFilePart("images[]", mFiles.get(i)));
            //filesToUpload[i] = prepareFilePart(i,uris.get(i)) ;
            if (prepareFilePart(uris.get(i)) !=null) {
                filesToUpload.add(prepareFilePart(uris.get(i)));
            }  else {
                Toast.makeText(this,"Empty File",Toast.LENGTH_LONG).show();
            }
        }

        UploadImageRepository.getInstance().getImageUrl(this.getApplication(), filesToUpload);
    }
    @Override
    public void deleteImage(int postion) {
        mRecyclerImages.remove(postion);
        mFiles.remove(postion);
        resetRecycler();
    }

    private MultipartBody.Part prepareFilePart(Uri uri) {
        System.out.println("Uri File Path :"+uri.getPath().toString());
         File file = new File(uri.getPath());
         if (file != null) {
             // create RequestBody instance from file
             RequestBody requestFile = RequestBody.create(MediaType.parse("images/*"), file);

             // MultipartBody.Part is used to send also the actual file name
             return MultipartBody.Part.createFormData("images[]", file.getName(), requestFile);
         }else {
                      return null;
         }
    }


    public void next(View view){
        if(ValidationUtils.isEmpty(binding.etSize.getText().toString())||
                ValidationUtils.isEmpty(binding.etDate.getText().toString())
                /*||mRecyclerImages.size()<1*/){
            showSnackBar(getResources().getString(R.string.fill_all_data_error_message));
        }else {
            mServiceRequest.setSize(Float.valueOf(binding.etSize.getText().toString()));
            mServiceRequest.setDate(binding.etDate.getText().toString());
            Intent intent=new Intent(this,RequestServiceStep2Activity.class);
            intent.putExtra(ConfigurationFile.IntentConstants.SERVICE_REQUEST_1STEP_DATA,mServiceRequest);
            startActivity(intent);
        }
    }

    public void setDate(View view){
        CustomUtils.getInstance().showDatePickerDialog(this, selectedDate -> {
            CustomUtils.getInstance().showTimePickerDialog(RequestServiceStep1Activity.this,new UpdateTimeListener() {
                @Override
                public void onTimeSet(String selectedTime) {
                    binding.etDate.setText(new StringBuilder()
                    .append(selectedDate).append(" ").append(selectedTime));
                }
            });
        });
    }

    private void showSnackBar(String message){
        Snackbar.make(binding.clRequestServiceStep1Root,message,Snackbar.LENGTH_LONG).show();

    }


    public Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }
                


}
