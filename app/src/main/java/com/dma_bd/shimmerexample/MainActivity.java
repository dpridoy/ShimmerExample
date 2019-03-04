package com.dma_bd.shimmerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<ModelClass> cartList;
    private MyAdapter mAdapter;

    private RecyclerView rList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private ArrayList<ModelClass> receivedList;
    private MyAdapter receivedAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShimmerViewContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        rList=(RecyclerView)findViewById(R.id.recycler_view);
        receivedList=new ArrayList<>();
        receivedAdapter=new MyAdapter(getApplicationContext(),receivedList);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration=new DividerItemDecoration(rList.getContext(),linearLayoutManager.getOrientation());

        rList.setHasFixedSize(true);
        rList.setLayoutManager(linearLayoutManager);
        rList.addItemDecoration(dividerItemDecoration);
        rList.setAdapter(receivedAdapter);

        getData();
    }

    private void getData(){
        retrofit2.Call<ResponseBody> call= RetrofitClient
                .getInstance()
                .getApi()
                .get_data();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s=null;
                try {
                    s=response.body().string();
                    Log.e("Response",s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s!=null){

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //textView.append(jsonObject.getString("machine_status")+" "+jsonObject.getString("machine_details")+"\n");

                            ModelClass rClass=new ModelClass();
                            rClass.setId(jsonObject.getString("id"));
                            rClass.setTitle(jsonObject.getString("title"));
                            rClass.setUrl(jsonObject.getString("url"));
                            rClass.setThumbnail(jsonObject.getString("thumbnailUrl"));
                            //Log.e("Response",jsonObject.getString("id")+jsonObject.getString("uid"));
                            receivedList.add(rClass);
                        }

                        receivedAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
