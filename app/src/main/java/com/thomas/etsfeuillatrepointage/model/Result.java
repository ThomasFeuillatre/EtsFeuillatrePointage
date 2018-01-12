package com.thomas.etsfeuillatrepointage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 05/01/2018.
 */
public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("id_worker")
    private int idWorker;

    @SerializedName("statut")
    private boolean statut;

    public Result(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    //Constructor for login activity
    public Result(Boolean error, String message, int idWorker) {
        this.error = error;
        this.message = message;
        this.idWorker = idWorker;
    }

    //Constructor for login activity
    public Result(Boolean error, String message, boolean statut) {
        this.error = error;
        this.message = message;
        this.statut = statut;
    }




    public boolean getStatut() {
        return statut;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getIdWorker() {
        return idWorker;
    }
}