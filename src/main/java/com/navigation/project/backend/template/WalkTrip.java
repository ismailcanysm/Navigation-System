package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

/**
 * WalkTrip - Yürüyüş Rotası İmplementasyonu
 *
 * AMAÇ:
 * Yürüyerek yolculuk simülasyonu yapar.
 * TripAlgorithm'in printReceipt metodunu implement eder.
 *
 * NE İŞE YARAR:
 * - Yürüyüş rotasına özel çıktı üretir
 * - Kalori hesabı yapar (50 kcal/km)
 * - Maliyet gösterir (0 TL - bedava)
 *
 * PATTERN: Template Method Pattern
 * PARENT: TripAlgorithm
 *
 * HESAPLAMALAR:
 * - Hız: Sabit 5 km/h
 * - Kalori: distance * 50 kcal
 * - Maliyet: 0 TL (bedava)
 *
 * ÇIKTI ÖRNEĞİ:
 * =====================================
 * YÜRÜYÜŞ ROTASI
 * =====================================
 * Rota: İstanbul → Bursa → İzmir
 * Toplam Mesafe: 350.0 km
 * Tahmini Süre: 70 saat 0 dk
 * Yakılan Kalori: 17500 kcal
 * Maliyet: 0 TL (Bedava!)
 * =====================================
 */

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