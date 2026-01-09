package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

/**
 * CarTrip - Araba Yolculuğu İmplementasyonu
 *
 * AMAÇ:
 * Araba ile yolculuk simülasyonu yapar.
 * TripAlgorithm'in printReceipt metodunu implement eder.
 *
 * NE İŞE YARAR:
 * - Araba yolculuğuna özel çıktı üretir
 * - Yakıt maliyeti hesaplar (2.5 TL/km)
 * - Console'a araba yolculuğu detaylarını yazar
 *
 * PATTERN: Template Method Pattern
 * PARENT: TripAlgorithm
 *
 * HESAPLAMALAR:
 * - Hız: Yol hız limiti kullanılır
 * - Maliyet: distance * 2.5 TL
 *
 * ÇIKTI ÖRNEĞİ:
 * =====================================
 * ARABA YOLCULUĞU
 * =====================================
 * Rota: İstanbul → Bursa → İzmir
 * Toplam Mesafe: 350.0 km
 * Tahmini Süre: 3 saat 21 dk
 * Yakıt Maliyeti: 875.0 TL
 * =====================================
 */

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