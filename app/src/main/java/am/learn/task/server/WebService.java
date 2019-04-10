package am.learn.task.server;


import am.learn.task.server.model.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {




        @GET(ConsAPI.GET_SEARCH)
        Call<Data> getSearch(
                @Query("api-key") String key,
                @Query("format") String format,
                @Query("show-fields") String showFields,
                @Query("page") int page
    );

}
