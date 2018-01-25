package com.thomas.etsfeuillatrepointage.model;

/**
 * Created by Thomas on 25/01/2018.
 */

public class ClockTime {
    int id, idWorker, statut;
    String amStart, amEnd, pmStart, pmEnd, place, observation;

    public ClockTime(int id, int idWorker, int statut, String amStart, String amEnd, String pmStart, String pmEnd, String place, String observation) {
        this.id = id;
        this.idWorker = idWorker;
        this.statut = statut;
        this.amStart = amStart;
        this.amEnd = amEnd;
        this.pmStart = pmStart;
        this.pmEnd = pmEnd;
        this.place = place;
        this.observation = observation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getAmStart() {
        return amStart;
    }

    public void setAmStart(String amStart) {
        this.amStart = amStart;
    }

    public String getAmEnd() {
        return amEnd;
    }

    public void setAmEnd(String amEnd) {
        this.amEnd = amEnd;
    }

    public String getPmStart() {
        return pmStart;
    }

    public void setPmStart(String pmStart) {
        this.pmStart = pmStart;
    }

    public String getPmEnd() {
        return pmEnd;
    }

    public void setPmEnd(String pmEnd) {
        this.pmEnd = pmEnd;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
