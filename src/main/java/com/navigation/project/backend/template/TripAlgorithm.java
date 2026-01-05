package com.navigation.project.backend.template;

import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.strategy.RouteCalculationResult;

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









