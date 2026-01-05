package com.navigation.project.backend.strategy;

import com.navigation.project.backend.model.Node;

import java.util.Collections;
import java.util.List;

public class RouteCalculationResult {
    private final List<Node> path;              // İzlenen yol (Şehirlerin listesi)
    private final double totalDistance;         // Toplam mesafe (km)
    private final double totalDuration;         // Toplam süre (dk veya saat)

    public RouteCalculationResult(List<Node> path, double totalDistance, double totalDuration){
        this.path = path;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
    }

    public List<Node> getPath(){
        return path == null ? Collections.emptyList() : path;
    }

    public double getTotalDistance(){
        return totalDistance;
    }

    public double getTotalDuration(){
        return totalDuration;
    }

    @Override
    public String toString(){
        return "Rota Sonucu => Mesafe: " + totalDistance + " birim, Süre: " + totalDuration + " birim";
    }
}