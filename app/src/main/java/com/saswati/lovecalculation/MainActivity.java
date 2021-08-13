package com.saswati.lovecalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    EditText partner,name;
    Button btn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        partner=findViewById(R.id.partner);
        name=findViewById(R.id.Name);
        btn=findViewById(R.id.btn);
        text=findViewById(R.id.Show);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"I am Saswati",Toast.LENGTH_SHORT).show();
                String string1=partner.getText().toString();
                String string2=name.getText().toString();

                FetchData(string1,string2);
            }
        });
    }

    public void FetchData(String string1,String string2)
    {

        Request request = new Request.Builder()
                .url("https://love-calculator.p.rapidapi.com/getPercentage?fname="+string1+"&sname="+string2)
                .get()
               .addHeader("x-rapidapi-key", BuildConfig.API_KEY
               )

                .addHeader("x-rapidapi-host", "love-calculator.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
               e.printStackTrace();
                //Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String resp= Objects.requireNonNull(response.body()).string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject=new JSONObject(resp);
                                String string1=jsonObject.getString("percentage");
                                String string2=jsonObject.getString("result");

                                text.setText("Percentage: "+string1+"\n\n"+ "Remark: "+ string2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });



    }
}