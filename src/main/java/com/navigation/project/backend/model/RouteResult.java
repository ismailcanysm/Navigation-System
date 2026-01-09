package com.navigation.project.backend.model;

import java.util.List;

/**
 * RouteResult - Rota Sonucu (ESKİ - KULLANILMIYOR)
 *
 * NOT: Bu sınıf artık kullanılmıyor.
 * Yerine RouteCalculationResult kullanılıyor.
 *
 * Geriye dönük uyumluluk için saklanıyor.
 */

public class RouteResult {
    private List<Node> path;        // İzlenen yol (Şehir listesi)
    private double totalDuration;   // Toplam süre (dk/saat)
    private double totalDistance;   // Toplam mesafe (km)

    // Boş Constructor (İleride Builder ile dolduracağız)
    public RouteResult(){}


    // Getter ve Setter metodları

    public void setPath(List<Node> path){
        this.path = path;
    }
    public List<Node> getPath(){
        return path;
    }

    public void setTotalDuration(double totalDuration){
        this.totalDuration = totalDuration;
    }
    public double getTotalDuration(){
        return totalDuration;
    }

    public void setTotalDistance(double totalDistance){
        this.totalDistance = totalDistance;
    }
    public double getTotalDistance(){
        return totalDistance;
    }
}
