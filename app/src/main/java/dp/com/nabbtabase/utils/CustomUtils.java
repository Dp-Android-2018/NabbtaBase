package dp.com.nabbtabase.utils;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.dagger.component.NetworkComponent;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.repository.EndPoints;
import dp.com.nabbtabase.view.activity.ContainerActivity;
import dp.com.nabbtabase.view.activity.LoginActivity;
import dp.com.nabbtabase.view.callback.TaskMonitor;
import dp.com.nabbtabase.view.callback.UpdateDateListener;
import dp.com.nabbtabase.view.callback.UpdateTimeListener;


/**
 * Created by DELL on 28/03/2018.
 */

public class CustomUtils {

    private static CustomUtils customUtils = null;
    private static String selectedTime;
    private static String selectedDate;
    private Dialog dialog = null;
    //private AlertDialog registerDialog = null;
    private SharedPrefrenceUtils pref;

    private CustomUtils() {
    }

    public static CustomUtils getInstance() {
        if (customUtils == null)
            customUtils = new CustomUtils();

        return customUtils;
    }


    public String encodeImage(Bitmap bm) {
        int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
        bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public EndPoints getEndpoint(Application application) {
        NetworkComponent daggerNetworkComponent = ((MyApp) application).getDaggerNetworkComponent();
        EndPoints endPoint = daggerNetworkComponent.getEndPoint();
        return endPoint;
    }

    public void moveToContainer(Context context) {
        Intent intent = new Intent(context, ContainerActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }

    public Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public String uriToFilename(Uri uri, Context context) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        String picturePath = "";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
        }
        return picturePath;
    }


    public boolean checkIfAlreadyhavePermission(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestForSpecificPermission(Context context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }


    public Boolean isValidMobileNumber(String s) {
        Pattern p = Pattern.compile("(0/1)?[0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public void showProgressDialog(Context activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        if (!dialog.isShowing())
            dialog.show();
    }

    public void cancelDialog() {
        dialog.dismiss();
    }

    public LoginRegisterContent getSaveUserObject(Context context) {

        SharedPrefrenceUtils prefrenceUtils = new SharedPrefrenceUtils(context);
        LoginRegisterContent userData = (LoginRegisterContent) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.SHARED_PREF_NAME, LoginRegisterContent.class);
        return userData;
    }

    public void saveDataToPrefs(LoginRegisterContent data, Context context) {
        pref = new SharedPrefrenceUtils(context);
        pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.SHARED_PREF_NAME, data);
    }


    public void openCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, 1);
    }

    public void openGallery(Activity activity, boolean checker) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (checker) {
            pickPhoto.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
            pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }

        activity.startActivityForResult(pickPhoto, 1);
    }

    public String firstCharacters(String name) {
        String[] splited = name.split("\\s+");
        String workshoptitle = "";
        for (int i = 0; i < splited.length; i++)
            workshoptitle = workshoptitle + splited[i].toUpperCase().charAt(0);

        return workshoptitle;
    }


    public void playStore(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "Send To"));
    }

    public void uploadFireBasePic(StorageReference storageReference, List<Uri> selectedImageUri, TaskMonitor callback) {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < selectedImageUri.size(); i++) {
            final UploadTask photoRef = storageReference.child(selectedImageUri.get(i).getLastPathSegment()).putFile(selectedImageUri.get(i));
            //System.out.println("uri is : " + selectedImageUri.size());
            photoRef.addOnSuccessListener(taskSnapshot -> {
                //System.out.println("ERROR UPLOADING : Success");
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUrls.add(uri.toString());
                    //System.out.println("image url  :" + uri);
                    if (imageUrls.size() == selectedImageUri.size()) {
                        callback.taskCompleted(imageUrls);
                    }
                });
            });
            photoRef.addOnFailureListener(e -> {
                //System.out.println("ERROR UPLOADING :" + e.getMessage());
            });
        }
    }

    public void clearSharedPref(Context context) {
        String appLang=getAppLanguage(context);
        SharedPrefrenceUtils prefrenceUtils = new SharedPrefrenceUtils(context);
        prefrenceUtils.clearToken();
        saveAppLanguage(context,appLang);
    }

    public static void showTimePickerDialog(Context context, UpdateTimeListener listener) {
        Calendar mCuurTime = Calendar.getInstance();
        int hour = mCuurTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCuurTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, (view, hourOfDay, minute1) -> {
            selectedTime = ((hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay)) + ":" +
                    (minute1 < 10 ? "0" + minute1 : minute1) + ":00");
            listener.onTimeSet(selectedTime);
        }, hour, minute, true);
        mTimePicker.setTitle(context.getString(R.string.select_time));
        mTimePicker.show();
    }

    public static void showDatePickerDialog(Context context, UpdateDateListener listener) {
        Calendar mCuurDate = Calendar.getInstance();
        int year = mCuurDate.get(Calendar.YEAR);
        int month = mCuurDate.get(Calendar.MONTH) + 1;
        //int day = mCuurDate.get(Calendar.DAY_OF_WEEK);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
            selectedDate =(year1) + "-" + (month1 < 10 ? "0" + month1 : month1) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
            System.out.println("Date on utils : " + selectedDate);
            listener.onDateSet(selectedDate);
        }, year, month, mCuurDate.get(Calendar.DATE));
        mDatePicker.setTitle(context.getString(R.string.select_date));
        mDatePicker.show();
    }

    public void startPlacePicker(Activity activity) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            activity.startActivityForResult(builder.build(activity),
                    ConfigurationFile.Constants.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public String getAppLanguage(Context context){
        SharedPrefrenceUtils sharedPrefrenceUtils=new SharedPrefrenceUtils(context);
        String lang=sharedPrefrenceUtils.getStringFromSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE);
        if (lang == null)
            return "en";
        return lang;
    }

    public void saveAppLanguage(Context context,String lang){
        SharedPrefrenceUtils sharedPrefrenceUtils=new SharedPrefrenceUtils(context);
        sharedPrefrenceUtils.addStringToSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE,lang);
    }

    public void logout(Context context) {
        CustomUtils.getInstance().clearSharedPref(context);
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }

    public void showRegisterAlertDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog;
        builder.setMessage(R.string.you_must_be_Register)
                .setTitle(R.string.alert);
        builder.setPositiveButton(R.string.login, (dialog, which) -> {
           Intent intent=new Intent(context,LoginActivity.class);
           context.startActivity(intent);
            ((Activity)context).finishAffinity();
        });
        builder.setNegativeButton(R.string.not_now, (dialog, which) -> {
            cancelDialog();
        });
        alertDialog= builder.create();
        alertDialog.show();
    }
}
