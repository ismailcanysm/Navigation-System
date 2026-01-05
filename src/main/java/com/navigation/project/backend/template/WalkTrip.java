package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

public class WalkTrip extends TripAlgorithm {

    public WalkTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=== [YÜRÜYÜŞ ROTASI] ===");
        System.out.println("Rota: " + result.getPath());
        System.out.println("Mesafe: " + result.getTotalDistance() + " km");

        // Yürüyüşte kalori hesabı yapabiliriz (km başına 50 kalori)
        double kalori = result.getTotalDistance() * 50;
        System.out.println("Yakılacak Kalori: " + kalori + " kcal");
        System.out.println("Maliyet: 0 TL (Bedava!)");
        System.out.println("========================\n");
    }
}