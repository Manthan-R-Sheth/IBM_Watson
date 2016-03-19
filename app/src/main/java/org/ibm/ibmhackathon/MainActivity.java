package org.ibm.ibmhackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<DocumentFeatures> {

    Button fileList;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uisetup();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiMethods apiMethods= retrofit.create(ApiMethods.class);
        Call<DocumentFeatures> call=apiMethods.getFeatures("");
        call.enqueue(this);

        fileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void uisetup() {
        fileList = (Button)findViewById(R.id.filelist);
    }

    @Override
    public void onResponse(Call<DocumentFeatures> call, Response<DocumentFeatures> response) {

    }

    @Override
    public void onFailure(Call<DocumentFeatures> call, Throwable t) {
        Log.d("Error network call",t.toString());
    }
}
