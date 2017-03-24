package drrino.com.learningdemo.api;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dell1 on 2017/3/23.
 */

public interface GankApi {
    String HOST = "http://gank.io/api/";


    //@GET("/day/{year}/{month}/{day}") Observable<GankData> getGankData(@Path("year") int year,
    //                                                                   @Path("month") int month, @Path("day") int day);
    //
    //@GET("/data/福利/{pagesize}/{page}") Observable<GirlData> getGirlData(
    //    @Path("pagesize") int pagesize, @Path("page") int page);
    //
    //@GET("/data/Android/{pagesize}/{page}") Observable<BlogData> getAndroidData(
    //    @Path("pagesize") int pagesize, @Path("page") int page);
    //
    //@GET("/data/iOS/{pagesize}/{page}") Observable<BlogData> getIOSData(
    //    @Path("pagesize") int pagesize, @Path("page") int page);
}
