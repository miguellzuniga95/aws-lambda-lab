package com.belatrix.demo.receiver;

import com.belatrix.demo.domain.ContentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContentApi {

    /**
     * Service that contains data about News
     * @param id Content id
     * @param key Service key
     * @return Content data
     */
    @GET("some/path/{id}")
    Call<ContentResponse> getContentInfo(@Path("id") String id, @Query("key") String key);
}
