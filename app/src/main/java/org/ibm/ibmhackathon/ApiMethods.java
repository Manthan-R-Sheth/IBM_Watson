package org.ibm.ibmhackathon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manthan on 19/3/16.
 */

public interface ApiMethods {

    @GET("/get/curators.json")
    Call<DocumentFeatures> getFeatures(
            @Query("api_key") String key
    );
}
