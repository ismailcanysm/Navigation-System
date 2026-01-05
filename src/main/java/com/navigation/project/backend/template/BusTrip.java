package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

public class BusTrip extends TripAlgorithm {

    public BusTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=== [OTOBÜS YOLCULUĞU] ===");
        System.out.println("Rota: " + result.getPath());

        // Otobüs duraklarda durduğu için süreye +15 dk ekliyoruz
        double toplamSure = result.getTotalDuration() + 15;
        System.out.println("Tahmini Süre: " + toplamSure + " dk (Durak bekleme dahil)");

        // Sabit Bilet Fiyatı
        System.out.println("Bilet Fiyatı: 15.00 TL (Sabit)");
        System.out.println("==========================\n");
    }
}