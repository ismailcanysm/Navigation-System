package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

public class CarTrip extends TripAlgorithm {

    public CarTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=== [ARABA YOLCULUĞU] ===");
        System.out.println("Rota: " + result.getPath());
        System.out.println("Mesafe: " + result.getTotalDistance() + " km");

        // Benzin Hesabı: km başına 2.5 TL yaktığını varsayalım
        double yakitMaliyeti = result.getTotalDistance() * 2.5;
        System.out.println("Tahmini Yakıt: " + yakitMaliyeti + " TL");
        System.out.println("=========================\n");
    }
}