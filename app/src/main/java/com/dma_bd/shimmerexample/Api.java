package com.dma_bd.shimmerexample;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("photos")
    Call<ResponseBody> get_data();
}
