package com.vutuanchien.demologin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vutuanchien.demologin.models.Models;
import com.vutuanchien.demologin.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    List<User> data = new ArrayList<>();

    public boolean checkUser(String name, String pass) {
        boolean kq = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).user.equals(name) && (data.get(i).pass.equals(pass))) {
                kq = true;
                break;
            }
        }
        return kq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnclick = (Button) findViewById(R.id.btnLogin);
        final EditText edUser = (EditText) findViewById(R.id.email);
        final EditText edPass = (EditText) findViewById(R.id.password);
        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.106/").build();
                API apiInterface = restAdapter.create(API.class);
                apiInterface.getData(new Callback<Models>() {
                    @Override
                    public void success(Models models, Response response) {

                        data.clear();
                        data = models.users;
                        if (checkUser(edUser.getText().toString(), edPass.getText().toString()))
                            Toast.makeText(getBaseContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getBaseContext(), "Khong Thanh Cong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getBaseContext(), "Loi tai file Json", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                });
            }
        });

    }
}
