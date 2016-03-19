package org.ibm.ibmhackathon;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alchemyapi.api.AlchemyAPI;
import com.alchemyapi.api.AlchemyAPI_KeywordParams;
import com.alchemyapi.api.AlchemyAPI_NamedEntityParams;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<DocumentFeatures> {

    Button fileList;
    Retrofit retrofit;
    TextView textView;
    final static String AlchemyAPI_Key = "";

    String text_to_parse = "There appears to be increased uncertainty surrounding the forthcoming referendum " +
            "on UK membership of the European Union. That uncertainty is likely to have been a significant driver " +
            "of the decline in sterling. It may also delay some spending decisions and depress growth in aggregate " +
            "demand in the near term.";

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
                new GetFeatureTask().execute(null,null,null);

            }
        });
    }

    private void uisetup() {
        fileList = (Button)findViewById(R.id.filelist);
        textView = (TextView)findViewById(R.id.textView);
    }

    @Override
    public void onResponse(Call<DocumentFeatures> call, Response<DocumentFeatures> response) {

    }

    @Override
    public void onFailure(Call<DocumentFeatures> call, Throwable t) {
        Log.d("Error network call", t.toString());
    }



    private class GetFeatureTask extends AsyncTask<String,Integer,DocumentFeatures> {

        @Override
        protected DocumentFeatures doInBackground(String... params) {
            try {
                SendAlchemyCallInBackground();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(DocumentFeatures documentFeatures){

        }
    }

    public void SendAlchemyCallInBackground(){

        Document doc = null;
        com.alchemyapi.api.AlchemyAPI api = null;

        try
        {
            api = AlchemyAPI.GetInstanceFromString(AlchemyAPI_Key);
        }
        catch( IllegalArgumentException ex )
        {
            textView.setText("Error loading AlchemyAPI.  " +
                    "Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable." +
                    "Keys available at alchemyapi.com.");
            return;
        }
        try {
            AlchemyAPI_KeywordParams keywordParams = new AlchemyAPI_KeywordParams();
            api.TextGetRankedKeywords(text_to_parse, keywordParams);

        }catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
