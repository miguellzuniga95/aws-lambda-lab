package com.belatrix.demo.receiver;

import com.amazonaws.services.lambda.runtime.Context;
import com.belatrix.demo.domain.ContentResponse;
import com.belatrix.demo.util.Constants;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Receiver class encharge of calling endpoint to retrieve content
 * @author mzuniga
 */
public class ContentService {

    private final String id;
    private final Context context;
    private ContentApi api;

    /**
     * Initialize retrofit client
     * @param id Value passed from external AWS Events
     * @param context AWS context
     */
    public ContentService(String id, Context context) {
        this.id = id;
        this.context = context;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        api = retrofit.create(ContentApi.class);

    }

    /**
     * Make the call the the Content endpoint and save it on ContentResponse object
     * Proceed to {@link com.belatrix.demo.domain.ContentResponse} class for more info on the fields
     * @return Return null if no id is found to call the service
     */
    public ContentResponse callService() {

        ContentResponse contentResponse = null;

        if (StringUtils.isBlank(id)) {
            context.getLogger().log("id field can't be null!!!");
            return contentResponse;
        }

        try {
            contentResponse = api.getContentInfo(id, Constants.SERVICE_KEY).execute().body();
        } catch (IOException e) {
            context.getLogger().log("Throwing exception : " + e.getMessage());
        }

        return contentResponse;
    }

}
