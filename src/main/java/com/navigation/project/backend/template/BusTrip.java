package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;
import com.navigation.project.backend.model.VehicleType;

public class BusTrip extends TripAlgorithm {

    public BusTrip(IRouteStrategy strategy) {
        super(strategy);
    }

    //ARAÇ TİPİNİ DÖNER
    @Override
    protected VehicleType getVehicleType() {
        return VehicleType.BUS;
    }

    @Override
    protected void printReceipt(RouteCalculationResult result) {
        System.out.println("\n=====================================");
        System.out.println("        OTOBÜS YOLCULUĞU");
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

        //SÜRE - Dijkstra'nın hesabı
        double dijkstraSure = result.getTotalDuration();

        int saat = (int) (dijkstraSure / 60);
        int dakika = (int) (dijkstraSure % 60);

        System.out.printf("Tahmini Süre: %d saat %d dk (Durak bekleme dahil)%n", saat, dakika);

        // Bilet
        System.out.println("Bilet Fiyatı: 15 TL (Sabit)");
        System.out.println("=====================================\n");
    }
}