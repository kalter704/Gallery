package com.yad.vasilii.gallery.data.network;

import com.yad.vasilii.gallery.data.network.models.*;

import io.reactivex.*;
import retrofit2.http.*;

public interface PixabayApiService {

    String BASE_URL = "https://pixabay.com/";

    @GET("api/")
    Single<PixabayApiResponse> getImages(@Query("q") String queryString, @Query("page") int page,
            @Query("per_page") int perPage);

}
