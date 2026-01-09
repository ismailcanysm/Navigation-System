package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;
import com.navigation.project.backend.model.VehicleType;

public class WalkTrip extends TripAlgorithm {

    public WalkTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    //ARAÇ TİPİNİ DÖNER
    @Override
    protected VehicleType getVehicleType() {
        return VehicleType.WALK;
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=====================================");
        System.out.println("        YÜRÜYÜŞ ROTASI");
        System.out.println("=====================================");

        // Rota
        System.out.print("Rota: ");
        for (int i = 0; i < result.getPath().size(); i++) {
            System.out.print(result.getPath().get(i).getName());
            if (i < result.getPath().size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println();

        // Mesafe
        System.out.printf("Toplam Mesafe: %.1f km%n", result.getTotalDistance());

        // Süre
        double sure = result.getTotalDuration();
        int saat = (int) (sure / 60);
        int dakika = (int) (sure % 60);
        System.out.printf("Tahmini Süre: %d saat %d dk%n", saat, dakika);

        // Kalori
        double kalori = result.getTotalDistance() * 50;
        System.out.printf("Yakılan Kalori: %.0f kcal%n", kalori);

        System.out.println("Maliyet: 0 TL (Bedava!)");
        System.out.println("=====================================\n");
    }
}