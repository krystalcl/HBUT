package com.example.hao.hbut.model.api;

import com.example.hao.hbut.model.bean.LogInfo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hao on 2016/10/25.
 */

public interface HbutApi {

    String Account_HOST = "http://run.hbut.edu.cn/Account/";
    //登录
    //http://run.hbut.edu.cn/Account/LogOnForJson?Mobile=1&UserName=1310200128&Password=1310200128&Role=Student
    @GET("LogOnForJson?Mobile=1")
    Observable<LogInfo> logOn(@Query("UserName") String UserName,
                              @Query("Password") String Password,
                              @Query("Role") String Role);

    //最近学期成绩
    //http://run.hbut.edu.cn/StuGrade/IndexRecentSemesterForJson/?id=1110321229&Mobile=1
    //所有成绩
    //http://run.hbut.edu.cn/StuGrade/IndexAllSemesterForJson?Id=1310200128&Mobile=1
    String StuGrade_HOST = "http://run.hbut.edu.cn/StuGrade/IndexRecentSemesterForJson/";
    String StuAllGrade_HOST = "http://run.hbut.edu.cn/StuGrade/IndexAllSemesterForJson/";
    @GET("?")
    Observable<ResponseBody> getRecent(@Query("id") String id,
                                       @Query("Mobile") String m);
    @GET("?")
    Observable<ResponseBody> getAllGrade(@Query("id") String id,
                                         @Query("Mobile") String m);

    /*
    * 课表 带cookie
    * @Semester
     */
    //http://run.hbut.edu.cn/ArrangeTask/MyselfScheduleForJson?Semester=20151&Id=1310200128&Role=Student
    String Schedule_Host = "http://run.hbut.edu.cn/ArrangeTask/MyselfScheduleForJson/";
    @GET("?")
    Observable<ResponseBody> getSchedule(@Query("Semester") String semester,
                                         @Query("Id") String id,
                                         @Query("Role") String role);

    String Jsoup_Host = "http://run.hbut.edu.cn/StuGrade/Index/";
    @GET("?")
    Observable<ResponseBody> getHtml();

    // 图书馆
    String verifyUrl = "http://202.114.181.3:8080/reader/captcha.php";
    String Cookie_Host = "http://202.114.181.3:8080/reader/redr_verify.php";

    @GET("?")
    Observable<ResponseBody> getCode();

    @GET("?")
    Observable<Response> getCookie();


}
