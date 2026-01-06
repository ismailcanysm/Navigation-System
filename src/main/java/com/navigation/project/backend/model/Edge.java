package com.navigation.project.backend.model;

public class Edge {
    private final Node source;          // Başlangıç
    private final Node destination;     // Bitiş
    private double distance;      // Mesafe (km)
    private int speedLimit;          // Hız sınırı (km/s)

    private EdgeStatus status;          // Yolun durumu (Değişebilir)

    public Edge(Node source, Node destination, double distance, int speedLimit){
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.speedLimit = speedLimit;
        this.status = EdgeStatus.OPEN;  // Yollar varsayılan olarak açık başlar.
    }

    public Node getSource(){
        return source;
    }

    public Node getDestination(){ return destination; }

    public double getDistance(){
        return distance;
    }

    public int getSpeedLimit(){
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) { this.speedLimit = speedLimit; }

    public EdgeStatus getStatus(){
        return status;
    }

    public void setStatus(EdgeStatus status){
        this.status = status;
    }
}
