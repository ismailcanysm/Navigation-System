package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;
import com.navigation.project.backend.model.VehicleType;

public class CarTrip extends TripAlgorithm {

    public CarTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    // ARAÇ TİPİNİ DÖNER
    @Override
    protected VehicleType getVehicleType() {
        return VehicleType.CAR;
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=====================================");
        System.out.println("        ARABA YOLCULUĞU");
        System.out.println("=====================================");

        // Rota
        System.out.print("Rota: ");
        for (int i = 0; i < result.getPath().size(); i++) {
            System.out.print(result.getPath().get(i).getName());
            if (i < result.getPath().size() - 1) {
                System.out.print(" => ");
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

        // Yakıt
        double yakitMaliyet = result.getTotalDistance() * 2.5;
        System.out.printf("Yakıt Maliyeti: %.2f TL (2.5 TL/km)%n", yakitMaliyet);

        System.out.println("=====================================\n");
    }
}