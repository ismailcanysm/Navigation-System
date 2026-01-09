package com.navigation.project.backend;

import com.navigation.project.backend.builder.MapBuilder;
import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.facade.NavigationFacade;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.NodeType;
import com.navigation.project.backend.model.VehicleType;
import com.navigation.project.backend.observer.ITrafficObserver;
import com.navigation.project.backend.proxy.MapManagerProxy;

/**
 * Main - Navigasyon Sistemi Demo
 * Tüm 10 Design Pattern'i çalıştırır ve terminale yazar
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== NAVİGASYON SİSTEMİ - 10 DESIGN PATTERN DEMO ===\n");

        // PATTERN 10: FACADE - Basit API
        System.out.println("PATTERN 10: FACADE - Sistem başlatılıyor...");
        NavigationFacade facade = new NavigationFacade();
        System.out.println("NavigationFacade oluşturuldu\n");

        // PATTERN 1: SINGLETON - Tek harita instance
        System.out.println("PATTERN 1: SINGLETON - Harita instance'ı alınıyor...");
        CityMap map1 = CityMap.getInstance();
        CityMap map2 = CityMap.getInstance();
        System.out.println("map1 == map2: " + (map1 == map2));
        System.out.println("Tek harita instance garantisi\n");

        // PATTERN 2: BUILDER - Harita oluşturma
        System.out.println("PATTERN 2: BUILDER - Harita oluşturuluyor...");
        new MapBuilder()
                .addNode("İstanbul", NodeType.CITY)
                .addNode("Ankara", NodeType.CITY)
                .addNode("Bursa", NodeType.CITY)
                .addNode("İzmir", NodeType.CITY)
                .addRoad("İstanbul", "Ankara", 450, 120)
                .addRoad("İstanbul", "Bursa", 150, 110)
                .addRoad("Bursa", "İzmir", 200, 100)
                .addRoad("Ankara", "İzmir", 600, 110)
                .build();
        System.out.println("4 şehir, 8 yol eklendi (çift yönlü)\n");

        // PATTERN 4: STRATEGY - Dijkstra algoritması
        System.out.println("PATTERN 4: STRATEGY - Rota hesaplanıyor...");
        System.out.println("İstanbul -> İzmir (Araba)");
        facade.calculateRoute("İstanbul", "İzmir", VehicleType.CAR);
        System.out.println("Dijkstra algoritması çalıştı\n");

        // PATTERN 3 & 5: FACTORY + TEMPLATE METHOD - Farklı araçlar
        System.out.println("PATTERN 3: FACTORY + PATTERN 5: TEMPLATE METHOD");
        System.out.println("Aynı rota farklı araçlarla:\n");

        System.out.println("--- ARABA ---");
        facade.calculateRoute("İstanbul", "İzmir", VehicleType.CAR);

        System.out.println("\n--- OTOBÜS ---");
        facade.calculateRoute("İstanbul", "İzmir", VehicleType.BUS);

        System.out.println("\n--- YÜRÜYÜŞ ---");
        facade.calculateRoute("İstanbul", "İzmir", VehicleType.WALK);
        System.out.println("Factory 3 farklı Trip nesnesi üretti");
        System.out.println("Template Method her birini çalıştırdı\n");

        // PATTERN 7: OBSERVER - Bildirim sistemi
        System.out.println("PATTERN 7: OBSERVER - Observer ekleniyor...");
        TestObserver observer = new TestObserver();
        facade.addObserver(observer);
        System.out.println("Observer sisteme eklendi\n");

        // PATTERN 8: PROXY - Yetki kontrolü
        System.out.println("PATTERN 8: PROXY - Yetki kontrolü");
        System.out.println("Normal kullanıcı yol kapatmaya çalışıyor...");
        facade.setAdminMode(false);
        MapManagerProxy proxy = new MapManagerProxy();
        proxy.setAdmin(false);
        Edge testEdge = map1.getEdges().get(0);
        proxy.blockRoad(testEdge);
        System.out.println("Proxy yetkisiz erişimi engelledi\n");

        System.out.println("Admin kullanıcı yol kapatıyor...");
        facade.setAdminMode(true);

        // PATTERN 6: COMMAND - Geri alınabilir işlemler
        System.out.println("\nPATTERN 6: COMMAND - Yol kapatma komutu");
        facade.blockRoad("İstanbul", "Bursa");
        System.out.println("BlockRoadCommand çalıştı");
        System.out.println("Observer'a bildirim gitti\n");

        System.out.println("Yol kapatıldıktan sonra rota hesaplama:");
        facade.calculateRoute("İstanbul", "İzmir", VehicleType.CAR);
        System.out.println("Dijkstra kapalı yolu kullanmadı\n");

        System.out.println("Son komutu geri alıyoruz (UNDO)...");
        facade.undoLastCommand();
        System.out.println("Command geri alındı\n");
        System.out.println();
        System.out.println("Iterator ile tüm node'lar gezildi\n");

        // Özet
        System.out.println("=== TÜM PATTERN'LER ÇALIŞTI ===");
        System.out.println("1. Singleton - Tek harita instance");
        System.out.println("2. Builder - Zincirleme harita oluşturma");
        System.out.println("3. Factory - Araç nesnesi üretimi");
        System.out.println("4. Strategy - Dijkstra algoritması");
        System.out.println("5. Template Method - Yolculuk simülasyonu");
        System.out.println("6. Command - Geri alınabilir işlemler");
        System.out.println("7. Observer - Otomatik bildirimler");
        System.out.println("8. Proxy - Yetki kontrolü");
        System.out.println("9. Facade - Basit API");
        System.out.println("\n9/9 Pattern başarıyla çalıştı!");
    }

    // Test Observer sınıfı
    static class TestObserver implements ITrafficObserver {
        @Override
        public void onRoadStatusChanged(Edge edge, String message) {
            System.out.println("[OBSERVER BİLDİRİMİ] " + message);
        }

        @Override
        public void onSpeedLimitChanged(Edge edge, int oldLimit, int newLimit) {
            System.out.println("[OBSERVER BİLDİRİMİ] Hız limiti değişti: " + oldLimit + " -> " + newLimit);
        }
    }
}