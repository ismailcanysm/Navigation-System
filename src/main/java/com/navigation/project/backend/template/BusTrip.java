package com.navigation.project.backend.template;

import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

/**
 * BusTrip - Otobüs Yolculuğu İmplementasyonu
 *
 * AMAÇ:
 * Otobüs ile yolculuk simülasyonu yapar.
 * TripAlgorithm'in printReceipt metodunu implement eder.
 *
 * NE İŞE YARAR:
 * - Otobüs yolculuğuna özel çıktı üretir
 * - Durak bekleme süresi ekler (+15 dk)
 * - Sabit bilet fiyatı gösterir (15 TL)
 *
 * PATTERN: Template Method Pattern
 * PARENT: TripAlgorithm
 *
 * HESAPLAMALAR:
 * - Hız: min(yol_hız_limiti, 80 km/h)
 * - Süre: Hesaplanan süre + 15 dk (durak bekleme)
 * - Maliyet: 15 TL (sabit bilet fiyatı)
 *
 * ÇIKTI ÖRNEĞİ:
 * =====================================
 * OTOBÜS YOLCULUĞU
 * =====================================
 * Rota: İstanbul → Bursa → İzmir
 * Toplam Mesafe: 350.0 km
 * Tahmini Süre: 4 saat 33 dk (durak bekleme dahil)
 * Bilet Fiyatı: 15 TL
 * =====================================
 */

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