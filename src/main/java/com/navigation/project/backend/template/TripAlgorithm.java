package com.navigation.project.backend.template;

import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

/**
 * TripAlgorithm - Template Method Pattern (Abstract Base Class)
 *
 * AMAÇ:
 * Yolculuk simülasyonunun iskeletini tanımlar.
 * Ortak adımları implement eder, değişen detayları alt sınıflara bırakır.
 *
 * NE İŞE YARAR:
 * - Yolculuk akışını standartlaştırır
 * - Ortak kodları tekrarlamayı önler
 * - Alt sınıflar sadece farklı kısımları implement eder
 * - Template method final olduğu için değiştirilemez
 *
 * PATTERN: Template Method Pattern
 * ALT SINIFLAR: CarTrip, BusTrip, WalkTrip
 *
 * TEMPLATE METHOD AKIŞI (executeTrip):
 * 1. validate(start, end) - Ortak (implemented)
 * 2. calculateRoute() - Ortak (implemented)
 * 3. printReceipt(result) - Farklı (abstract, alt sınıflar implement eder)
 *
 * TEMEL METODLAR:
 * - executeTrip(start, end): FINAL - Template method, değiştirilemez
 * - validate(start, end): Node kontrolü yapar
 * - printReceipt(result): ABSTRACT - Alt sınıflar implement etmeli
 *
 * KULLANIM:
 * TripAlgorithm trip = new CarTrip(strategy);
 * trip.executeTrip(istanbul, ankara);
 * // Sırayla: validate → calculate → printReceipt (CarTrip versiyonu)
 *
 * AVANTAJLAR:
 * - Kod tekrarı yok
 * - Yeni araç eklemek kolay
 * - İskelet korunur, detaylar değişir
 */

// [PATTERN: Template Method]
// Algoritmanın iskeletini kurar. Alt sınıflar sadece "Fiş Yazdırma" (printReceipt) kısmını değiştirir.
public abstract class TripAlgorithm {
    protected IRouteStrategy strategy;

    public TripAlgorithm(IRouteStrategy strategy){
        this.strategy = strategy;
    }

    // --- ŞABLON METOT (Template Method) ---
    // final: Alt sınıflar bu akışı değiştiremez!
    public final void executeTrip(Node startNode, Node endNode){

        // Adım 1: Validasyon (Doğrulama)
        if (!validate(startNode, endNode)){
            System.err.println("HATA: Yolculuk için başlangıç veya bitiş noktası geçersiz.");
            return;
        }
        System.out.println("--- " + this.getClass().getSimpleName() + " Başlatılıyor ---");

        // Adım 2: Hesaplama (Strategy Pattern burada devreye girer)
        RouteCalculationResult result = strategy.calculateRoute(startNode, endNode);

        // Adım 3: Sonuç Gösterme (Her araç tipi bunu kendine göre yapar)
        if (result != null){
            printReceipt(result);
        }
    }

    // Ortak doğrulama mantığı
    protected boolean validate(Node start, Node end){
        return start != null && end != null && !start.equals(end);
    }

    // Alt sınıfların doldurması gereken soyut metot
    protected abstract void printReceipt(RouteCalculationResult result);


}









