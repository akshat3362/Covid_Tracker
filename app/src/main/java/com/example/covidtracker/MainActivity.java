package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    TextView deathcount;
    TextView recoveredcount;
    TextView confirmedcount;
    TextView todaycases;
    TextView todaydeath;
    String confirmed;
    ArrayList<String>arrayList;
    String recovered;
    String Death;
    String cases_today;
    String cases_death;
    JSONArray array;
    Arrays arrays;
        ArrayList<String> images;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        images = new ArrayList<String>();
        spinner = findViewById(R.id.spinner);
        todaycases = findViewById(R.id.todaycasescount);
        todaydeath = findViewById(R.id.todaydeathcount);
        confirmedcount = findViewById(R.id.deathscount);
        recoveredcount = findViewById(R.id.recoveredcount);
        deathcount = findViewById(R.id.confirmedcount);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        cases();
    }
    void pie()
    {
        AnimatedPieView mAnimatedPieView = findViewById(R.id.pie);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(-90)// Starting angle offset
                .addData(new SimplePieInfo(Integer.parseInt(confirmed) ,Color.parseColor("#FFBB86FC"), "Cases"))//Data (bean that implements the IPieInfo interface)
                .addData(new SimplePieInfo(Integer.parseInt(Death), Color.parseColor("#FF6200EE"), "Deaths")).drawText(true)
                .duration(2000);// draw pie animation duration

// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }
        void cases ()
        {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            final String url = "https://corona.lmao.ninja/v2/countries";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null ,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    array = response;
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject responseObj1 = response.getJSONObject(i);
                            JSONObject response2 = responseObj1.getJSONObject("countryInfo");
                            arrayList.add(responseObj1.getString("country"));
                            images.add(response2.getString("flag"));
                            Customadapter customAdapter=new Customadapter(getApplicationContext(),images,arrayList);
                            spinner.setAdapter(customAdapter);
                            //pie();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    deathcount.setText("akshat");
                }
            });

            queue.add(jsonArrayRequest);
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        try {
             JSONObject responseObj = array.getJSONObject(position);
             Death = (responseObj.getString("deaths"));
            recovered = (responseObj.getString("recovered"));
             confirmed = (responseObj.getString("cases"));
             cases_today = responseObj.getString("todayCases");
             cases_death = responseObj.getString("todayDeaths");
            recoveredcount.setText(String.valueOf(recovered));
            confirmedcount.setText(String.valueOf(confirmed));
            todaycases.setText(cases_today);
            todaydeath.setText(cases_death);
            deathcount.setText(String.valueOf(Death));
            pie();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}