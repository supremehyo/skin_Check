package com.example.s20141210jinwoojung.capston.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s20141210jinwoojung.capston.R;
import com.example.s20141210jinwoojung.capston.model.ImageUrl;
import com.example.s20141210jinwoojung.capston.service.GetTokenService;
import com.example.s20141210jinwoojung.capston.service.RetrofitInstance;
import com.example.s20141210jinwoojung.capston.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

//import xebia.ismail.e_learning.R;

/**
 * Created by Admin on 5/25/2016.
 */

public class TabGeoCone extends Fragment {
    String data =null;
    String filename = null;
    String path = "http://13.209.21.106:8010/";
    private static final int PICK_FROM_FILE = 100;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static String b;
    private SubmitButton sBtnLoading, sBtnProgress;
    private Button btnSucceed;
    private MyTask task;
    private Switch mSwitch;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public float color2;
    //Button btn_take_Pickture;
    Button btn_gallery;
    SharedPrefManager sharedPrefManager;
    ImageView picasooImageView ;
    ImageView color;
    TextView value;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upload_layout, container, false);
        verifyStoragePermissions(getActivity());
        sharedPrefManager = new SharedPrefManager(getActivity());
     //   btn_take_Pickture = v.findViewById(R.id.button4);
        picasooImageView= v.findViewById(R.id.uploadimage);
        color = v.findViewById(R.id.uploadimage2);
        mSwitch = v.findViewById(R.id.switch1);
        value= v.findViewById(R.id.value);
        sBtnLoading = v.findViewById(R.id.sbtn_loading);
        sBtnProgress = v.findViewById(R.id.sbtn_progress);

        //tv_message = v.findViewById(R.id.debug1);

        mSwitch.setVisibility(View.INVISIBLE);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sBtnLoading.setVisibility(View.GONE);
                    sBtnProgress.setVisibility(View.VISIBLE);
                    sBtnLoading.reset();
                } else {
                    sBtnLoading.setVisibility(View.VISIBLE);
                    sBtnProgress.setVisibility(View.GONE);
                    if (task != null && !task.isCancelled()) {
                        task.cancel(true);
                        sBtnProgress.reset();
                    }
                }
            }
        });
        sBtnLoading.setOnClickListener(new SubmitButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent chooser = Intent.createChooser(intent,"1a2a3a");
                startActivityForResult(chooser, PICK_FROM_FILE);



            }

        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_FILE){
            if(resultCode == RESULT_OK){
                Uri selectImage = data.getData();
                uploadImage(selectImage);
            }
        }

    }


    public void uploadImage(Uri uri){

//        GetTokenService service = RetrofitInstance.getService();
//        File file = new File(getRealPathFromURI(uri));
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
//        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "");
//        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "");
//        Call<User> call = service.editUser(sharedPrefManager.getSPToken(), fbody, name);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });



        final String[] a = new String[1];
        GetTokenService service = RetrofitInstance.getService();
        final File file = new File(getRealPathFromURI(uri));
        MultipartBody.Part body1 = prepareFilePart("image", uri);
        RequestBody description = createPartFromString("hello, this is description speaking");

        Call<ImageUrl> call2 = service.uploadFile2(description, body1,sharedPrefManager.getSPToken() );
        call2.enqueue(new Callback<ImageUrl>() {
            @Override
            public void onResponse(Call<ImageUrl> call, Response<ImageUrl> response) {
                if(response.code()==200) {

                    if (task == null || task.isCancelled()) {
                        task = new MyTask();
                        task.execute();
                    }

                    if (mSwitch.isChecked()) {
                        sBtnProgress.doResult(true);
                    } else {
                        sBtnLoading.doResult(true);
                    }

                    filename =response.body().getFilename();
                    data = path.concat(filename);
                    Picasso.get().load(data).into(picasooImageView);




                }
                Handler delayHandler = new Handler();
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sBtnLoading.reset();
                    }
                }, 2000);


            }

            @Override
            public void onFailure(Call<ImageUrl> call, Throwable t) {
                sBtnLoading.reset();
            }
        });


        Call<ResponseBody> call = service.uploadFile(description, body1,sharedPrefManager.getSPToken() );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()!=404) {
                    try {
                        a[0] =response.body().string();
                       String [] array = a[0].split("/");
                        String [] array2 = array[1].split("]");
                        b=array2[0].substring(0,array2[0].length()-1);
                        String [] array3 = b.split(":");
                        color2 =  Float.parseFloat(array3[1]);
                        value.setText(b);
                        if(color2>6)
                        {
                            color.setImageResource(R.drawable.red);
                        }
                        else if(color2 >4 &&color2<6)
                        {
                            color.setImageResource(R.drawable.yellow);
                        }
                        else if(color2 <2)
                        {
                            color.setImageResource(R.drawable.green);
                        }



                      // Toast.makeText(getActivity() , a[0], Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
     //   tv_message.setText(call.request().url().toString()); //todo 디버깅용



    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // save file

            } else {
                Toast.makeText(getActivity(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            int i = 0;
            while (i <= 100) {
                if (isCancelled()) {
                    return null;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                publishProgress(i);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == null) {
                sBtnProgress.reset();
            }
            sBtnProgress.doResult(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            sBtnProgress.setProgress(values[0]);
        }
    }


}
