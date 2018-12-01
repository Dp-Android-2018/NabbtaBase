package dp.com.nabbtabase.utils;

import android.app.Activity;
import android.app.Dialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;


/**
 * Created by DELL on 28/03/2018.
 */

public class CustomUtils {

    private static  CustomUtils customUtils=null;
    private static String selectedTime;
    private Dialog dialog=null;
    private AlertDialog registerDialog=null;
    private CustomUtils(){}
    public static CustomUtils getInstance(){
        if(customUtils==null)
            customUtils=new CustomUtils();

        return customUtils;
    }



    public String encodeImage(Bitmap bm)
    {
        int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
        bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
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
        String picturePath="";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
        }
        return picturePath;
    }


    public  boolean checkIfAlreadyhavePermission(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public  void requestForSpecificPermission(Context context, String[]permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity)context, permissions, requestCode);
    }


    public Boolean isValidMobileNumber(String s){
        Pattern p = Pattern.compile("(0/1)?[0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

//    public void showProgressDialog(Context activity){
//        dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog_layout);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        if (!dialog.isShowing())
//            dialog.show();
//    }

    public void cancelDialog(){
        dialog.dismiss();
    }

    public LoginRegisterContent getSaveUserObject(Context context){

        SharedPrefrenceUtils prefrenceUtils=new SharedPrefrenceUtils(context);
        LoginRegisterContent userData=(LoginRegisterContent) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.SHARED_PREF_NAME, LoginRegisterContent.class);
        return userData;
    }


    public void openCamera(Activity activity){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent,1);
    }

    public void openGallery(Activity activity, boolean checker){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (checker==true){
                pickPhoto.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }

        activity.startActivityForResult(pickPhoto ,1);
    }

    public String firstCharacters(String name){
        String[] splited = name.split("\\s+");
        String workshoptitle="";
        for (int i=0;i<splited.length;i++)
            workshoptitle=workshoptitle+splited[i].toUpperCase().charAt(0);

        return workshoptitle;
    }



    public void playStore(Context context){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +context.getPackageName())));
        }
    }

    public void shareApp(Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" +context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent,"Send To"));
    }

//    public void uploadFireBasePic(StorageReference storageReference, Uri selectedImageUri , TaskMonitor callback){
//        final UploadTask photoRef=storageReference.child(selectedImageUri.getLastPathSegment()).putFile(selectedImageUri);
//        photoRef.addOnSuccessListener(taskSnapshot -> {
//            (taskSnapshot.getMetadata().getReference().getDownloadUrl()).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    callback.taskCompleted(uri.toString());
//                    System.out.println("Activity Result View Model Url :"+uri);
//                }
//            });
//        });
//        photoRef.addOnFailureListener(e ->
//                System.out.println("ERROR UPLOADING :"+e.getMessage()));
//
//
//    }


    public void clearSharedPref(Context context){
        SharedPrefrenceUtils prefrenceUtils=new SharedPrefrenceUtils(context);
        prefrenceUtils.clearToken();
    }



//    public String getFirebaseToken(Context context){
//        final MyFirebaseInstanceIdService mfs=new MyFirebaseInstanceIdService();
//        FirebaseApp.initializeApp(context);
//      //  zzahn.runOnUiThread(() -> mfs.onTokenRefresh());
//        mfs.onTokenRefresh();
//        return MyFirebaseInstanceIdService.TOKEN;
//    }


}
