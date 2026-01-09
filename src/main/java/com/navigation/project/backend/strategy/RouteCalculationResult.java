package com.navigation.project.backend.strategy;

import com.navigation.project.backend.model.Node;

import java.util.Collections;
import java.util.List;

/**
 * RouteCalculationResult - Rota Hesaplama Sonucu
 *
 * AMAÇ:
 * Rota hesaplama sonucunu immutable olarak tutar.
 *
 * NE İŞE YARAR:
 * - Hesaplanan rotayı (node listesi) tutar
 * - Toplam mesafeyi tutar
 * - Toplam süreyi tutar
 * - Immutable (değiştirilemez) yapı
 *
 * İLİŞKİLİ SINIFLAR: DijkstraStrategy, Node
 *
 * FIELD'LAR:
 * - path (List<Node>): Rota (sıralı şehir listesi)
 * - totalDistance (double): Toplam mesafe (km)
 * - totalDuration (double): Toplam süre (dakika)
 *
 * TEMEL METODLAR:
 * - getPath(): Rotayı döner
 * - getTotalDistance(): Mesafeyi döner
 * - getTotalDuration(): Süreyi döner
 * - toString(): Özet string döner
 *
 * ÖRNEK:
 * RouteCalculationResult result = strategy.calculateRoute(...);
 * List<Node> path = result.getPath();  // [İstanbul, Bursa, İzmir]
 * double distance = result.getTotalDistance();  // 350.0 km
 * double duration = result.getTotalDuration();  // 201.0 dakika
 */

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