package com.example.leaf;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Leaf_disease_detection extends AppCompatActivity implements View.OnClickListener{
    Button b1;
    ImageView img;
    SharedPreferences sh;
    String url="";
    TableLayout tl;

    ImageView im_dis;
    TextView tv_name, tv_desc;

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    Uri picUri;
    String imageName="";
    byte[] imageBytes=null;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf_disease_detection);
        img=(ImageView)findViewById(R.id.imageView3);
        im_dis=(ImageView)findViewById(R.id.imageView4);
        tv_name=(TextView) findViewById(R.id.textView3);
        tv_desc=(TextView) findViewById(R.id.textView6);
        b1=(Button)findViewById(R.id.button);
        tl=(TableLayout) findViewById(R.id.tl1);
        tl.setVisibility(View.INVISIBLE);
        im_dis.setVisibility(View.INVISIBLE);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "and_detect";

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }catch (Exception e){}


        b1.setOnClickListener(this);
        img.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        if (img == view) {


            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);

        } else if (b1 == view) {

//            Toast.makeText(this, "innn", Toast.LENGTH_SHORT).show();
            new Serv().execute();

        }

    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == IMAGE_RESULT) {

                String filePath = getImageFilePath(data);
                //  Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    img.setImageBitmap(selectedImage);

                    try {
                        File fl = new File(filePath);
                        int ln = (int) fl.length();
                        imageName = fl.getName();
                        InputStream inputStream = new FileInputStream(fl);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] b = new byte[ln];
                        int bytesRead = 0;
                        while ((bytesRead = inputStream.read(b)) != -1)
                        {
                            bos.write(b, 0, bytesRead);
                        }
                        inputStream.close();
                        imageBytes = bos.toByteArray();

                    }catch (Exception e){}
                }
            }

        }

    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(),Home.class));
    }

    class  Serv extends AsyncTask<Void,Void,String> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd=new ProgressDialog(Leaf_disease_detection.this);
            pd.setMessage("uploading");
            pd.show();
        }


        @Override
        protected String doInBackground(Void... voids) {


            String status="";
            try {
                FileUpload client = new FileUpload(url);
                client.connectForMultipart();





//                client.addFormPart("lid",sh.getString("lid",""));


                client.addFilePart("file",imageName, imageBytes);
                client.finishMultipart();

                Thread.sleep(5000);



                String res = client.getResponse();
                JSONObject jsonObject=new JSONObject(res);
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                status = jsonObject.getString("status");
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("1")) {
                    String name=jsonObject.getString("name");
                    String photo=jsonObject.getString("photo");
                    String descr=jsonObject.getString("descr");

                    tl.setVisibility(View.VISIBLE);
                    im_dis.setVisibility(View.VISIBLE);
                    tv_name.setTextColor(Color.BLACK);
                    tv_desc.setTextColor(Color.BLACK);
                    tv_name.setText(name);
                    tv_desc.setText(descr);
                    try
                    {
                        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String url1=sh.getString("url1","")+photo;
                        Picasso.with(getApplicationContext()).load(url1.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new com.example.counciloor_app.CircleTransform()).error(R.drawable.ic_menu_gallery).into(im_dis);

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }catch (Exception e){
               // Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
            return status;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pd.dismiss();
            Toast.makeText(Leaf_disease_detection.this,s,Toast.LENGTH_LONG).show();
          //  Toast.makeText(Leaf_disease_detection.this, "success", Toast.LENGTH_SHORT).show();
            if(s.equalsIgnoreCase("1")) {
                Toast.makeText(Leaf_disease_detection.this, "success", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getApplicationContext(),Home.class));
            }
            else{
                Toast.makeText(Leaf_disease_detection.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
