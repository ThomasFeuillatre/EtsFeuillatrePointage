package com.thomas.etsfeuillatrepointage.api;

import com.thomas.etsfeuillatrepointage.model.Result;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Thomas on 05/01/2018.
 */

public interface APIService {

    //The login call
    @FormUrlEncoded
    @POST("login")
    Call<Result> loginUser(
            @Field("name") String name,
            @Field("password") String password
    );

    //The save call
    @FormUrlEncoded
    @POST("saveTimeClock")
    Call<Result> saveTimeClock(
            @Field("id_worker") int idWorker,
            @Field("day_time")  Date dayTime,
            @Field("am_start") String amStart,
            @Field("am_end") String amEnd,
            @Field("pm_start") String pmStart,
            @Field("pm_end") String pmEnd,
            @Field("place") String place,
            @Field("statut") int statut,
            @Field("observation") String observation
    );

    @FormUrlEncoded
    @POST("checkStatut")
    Call<Result> checkStatut(
            @Field("id_worker") int idWorker,
            @Field("day_time") Date dayTyme
    );

    //get Data of clockpoint
    @FormUrlEncoded
    @POST("getDataClockPoint")
    Call<Result> getDataClockPoint(
            @Field("day_time") Date date,
            @Field("id_worker") int idWorker
    );
}
