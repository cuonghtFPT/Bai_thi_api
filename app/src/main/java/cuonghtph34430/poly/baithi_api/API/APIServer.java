package cuonghtph34430.poly.baithi_api.API;

        import java.util.List;

        import cuonghtph34430.poly.baithi_api.DTO.XemayDTO;
        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.PUT;
        import retrofit2.http.Path;
        import retrofit2.http.Query;

public interface APIServer {
    String DOMAIN = "http://192.168.0.104:3000";

    @GET("/api/get-list-xeMay")
    Call<List<XemayDTO>> getXemay();

    @POST("/api/port-xeMay")
    Call<XemayDTO> createXemay(@Body XemayDTO xemayDTO);

    /// cái cũ
    @GET("/api/get-xeMay/{id}")
    Call<List<XemayDTO>> getThongtin(@Path("id") String id);

    //Demo
    @GET("/api/get-xeMay/{id}")
    Call<XemayDTO> getThongtin2(@Path("id") String id);

    @GET("/api/search-shoe")
    Call<List<XemayDTO>> searchShoe(@Query("key") String key);

    @DELETE("/api/delete-xeMay-by-id/{id}")
    Call<XemayDTO> deleteXemay(@Path("id") String id);

    @PUT("/api/update-xeMay-by-id/{id}")
    Call<XemayDTO> updateXemay(@Path("id") String id, @Body XemayDTO xemay);

}
