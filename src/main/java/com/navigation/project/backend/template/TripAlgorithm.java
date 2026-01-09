package com.navigation.project.backend.template;

import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;
import com.navigation.project.backend.model.VehicleType;

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

public abstract class TripAlgorithm {

    protected IRouteStrategy strategy;

    public TripAlgorithm(IRouteStrategy strategy) {
        this.strategy = strategy;
    }

    // ABSTRACT METOD - Alt sınıflar kendi araç tipini döner
    protected abstract VehicleType getVehicleType();

    // Template Method
    public final void executeTrip(Node start, Node end) {
        // 1. Validate
        if (!validate(start, end)) {
            return;
        }

        // 2. Rota hesapla
        RouteCalculationResult result = strategy.calculateRoute(start, end, getVehicleType());

        // 3. Fatura yazdır
        if (result.getPath().isEmpty()) {
            System.out.println("Rota bulunamadı!");
            return;
        }

        printReceipt(result);
    }

    protected boolean validate(Node start, Node end) {
        if (start == null || end == null) {
            System.out.println("Başlangıç ve bitiş noktası boş olamaz!");
            return false;
        }
        if (start.equals(end)) {
            System.out.println("Başlangıç ve bitiş aynı olamaz!");
            return false;
        }
        return true;
    }

    protected abstract void printReceipt(RouteCalculationResult result);
}