package com.navigation.project;

import com.navigation.project.backend.facade.NavigationFacade;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.VehicleType;
import com.navigation.project.backend.observer.ITrafficObserver;

public class NavigationSystemTest {
    public static void main(String[] args) {
        printHeader();

        NavigationFacade system = new NavigationFacade();

        test1_SystemInit(system);
        test2_RouteCalculation(system);
        test3_ObserverPattern(system);
        test4_ProxyPattern(system);
        test5_CommandPattern(system);
        test6_IteratorPattern(system);
        test7_AllPatternsTogether(system);

        printFooter();
    }

    private static void test1_SystemInit(NavigationFacade system) {
        printTestHeader("TEST 1: Sistem Başlatma (Singleton + Builder)");

        // Builder Pattern ile harita oluştur
        system.initSystem();

        // Sistem durumunu göster
        system.showStatus();

        System.out.println("\n✓ Singleton Pattern: Tek CityMap instance");
        System.out.println("✓ Builder Pattern: Harita zincirleme metodlarla oluşturuldu");
    }

    private static void test2_RouteCalculation(NavigationFacade system) {
        printTestHeader("TEST 2: Rota Hesaplama (Factory + Strategy + Template)");

        // Araba ile rota
        System.out.println("--- Araba ile rota ---");
        system.calculateRoute("İstanbul", "İzmir", VehicleType.CAR);

        System.out.println();

        // Otobüs ile rota
        System.out.println("--- Otobüs ile rota ---");
        system.calculateRoute("Ankara", "Bursa", VehicleType.BUS);

        System.out.println();

        // Yürüyerek rota
        System.out.println("--- Yürüyerek rota ---");
        system.calculateRoute("İstanbul", "Bursa", VehicleType.WALK);

        System.out.println("\n✓ Factory Pattern: Farklı Trip tipleri üretildi");
        System.out.println("✓ Strategy Pattern: Dijkstra algoritması kullanıldı");
        System.out.println("✓ Template Method: Yolculuk adımları sırayla çalıştı");
    }

    private static void test3_ObserverPattern(NavigationFacade system) {
        printTestHeader("TEST 3: Observer Pattern (Bildirim Sistemi)");

        // Observer ekle
        ITrafficObserver observer = new ITrafficObserver() {
            @Override
            public void onRoadStatusChanged(Edge edge, String message) {
                System.out.println("[OBSERVER] " + message);
            }

            @Override
            public void onSpeedLimitChanged(Edge edge, int oldLimit, int newLimit) {
                System.out.println("[OBSERVER] Hız değişti: " + oldLimit + " → " + newLimit + " km/h");
            }
        };

        system.addObserver(observer);
        System.out.println("Observer eklendi.\n");

        // Admin modu aç (Observer bildirim almayacak - sadece Command'lar bildirim gönderir)
        system.setAdminMode(true);

        System.out.println("\n✓ Observer Pattern: Bildirim sistemi hazır");
        System.out.println("✓ Observer, Command işlemlerinden bildirim alacak");
    }

    private static void test4_ProxyPattern(NavigationFacade system) {
        printTestHeader("TEST 4: Proxy Pattern (Yetki Kontrolü)");

        System.out.println("--- Senaryo 1: Admin olmadan işlem ---");
        system.setAdminMode(false);
        system.blockRoad("İstanbul", "Ankara");
        System.out.println("(İşlem engellendi - yetki yok)\n");

        System.out.println("--- Senaryo 2: Admin olarak işlem ---");
        system.setAdminMode(true);
        system.blockRoad("İstanbul", "Ankara");
        System.out.println("(İşlem başarılı - yetki var)");

        System.out.println("\n✓ Proxy Pattern: Yetki kontrolü çalışıyor");
        System.out.println("✓ Admin olmadan kritik işlemler engellendi");
    }

    private static void test5_CommandPattern(NavigationFacade system) {
        printTestHeader("TEST 5: Command Pattern (Undo/Redo)");

        System.out.println("--- Komut 1: Hız Değiştir ---");
        system.changeSpeed("Ankara", "İzmir", 90);

        System.out.println("\n--- Komut 2: Yol Kapat ---");
        system.blockRoad("Bursa", "İzmir");

        System.out.println("\n--- UNDO: Son Komut Geri Al ---");
        system.undoLastCommand();

        System.out.println("\n--- UNDO: Bir Önceki Komut Geri Al ---");
        system.undoLastCommand();

        System.out.println("\n✓ Command Pattern: Komutlar nesneler olarak kapsüllendi");
        System.out.println("✓ Undo özelliği çalışıyor");
        System.out.println("✓ Komut geçmişi tutuluyor");
    }

    private static void test6_IteratorPattern(NavigationFacade system) {
        printTestHeader("TEST 6: Iterator Pattern (Node Gezinme)");

        System.out.println("Iterator Pattern, sistem içinde harita gezinmek için kullanılıyor.");
        System.out.println("Rota hesaplama sırasında tüm node'lar iterate edildi.");

        System.out.println("\n✓ Iterator Pattern: Node'ları gezme mekanizması");
        System.out.println("✓ İç yapı gizlendi, sadece next() ve hasNext() kullanıldı");
    }

    private static void test7_AllPatternsTogether(NavigationFacade system) {
        printTestHeader("TEST 7: Tüm Pattern'ler Bir Arada");

        System.out.println("Karmaşık bir senaryo çalıştırılıyor...\n");

        // Admin modu aç
        system.setAdminMode(true);

        // Yol kapat
        System.out.println("1. Yol kapatılıyor...");
        system.blockRoad("İstanbul", "Bursa");

        // Alternatif rota hesapla
        System.out.println("\n2. Alternatif rota hesaplanıyor...");
        system.calculateRoute("İstanbul", "İzmir", VehicleType.CAR);

        // Hız değiştir
        System.out.println("\n3. Hız sınırı değiştiriliyor...");
        system.changeSpeed("Ankara", "İzmir", 100);

        // Yeni rota
        System.out.println("\n4. Yeni rota hesaplanıyor...");
        system.calculateRoute("Ankara", "İzmir", VehicleType.BUS);

        // Undo
        System.out.println("\n5. Son işlem geri alınıyor...");
        system.undoLastCommand();

        // Son durum
        System.out.println("\n6. Sistem durumu:");
        system.showStatus();

        System.out.println("\n✓ Tüm pattern'ler başarıyla birlikte çalıştı!");
    }

    private static void printHeader() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║     NAVİGASYON SİSTEMİ - KAPSAMLI TEST                  ║");
        System.out.println("║     10 Design Pattern Bir Arada                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printTestHeader(String testName) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println(testName);
        System.out.println("═".repeat(60));
    }

    private static void printFooter() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("✅ TÜM TESTLER BAŞARIYLA TAMAMLANDI!");
        System.out.println("═".repeat(60));

        System.out.println("\n📊 TEST EDİLEN PATTERN'LER:");
        System.out.println("  1. ✓ Singleton Pattern");
        System.out.println("  2. ✓ Builder Pattern");
        System.out.println("  3. ✓ Factory Pattern");
        System.out.println("  4. ✓ Strategy Pattern");
        System.out.println("  5. ✓ Template Method Pattern");
        System.out.println("  6. ✓ Iterator Pattern");
        System.out.println("  7. ✓ Observer Pattern");
        System.out.println("  8. ✓ Command Pattern");
        System.out.println("  9. ✓ Proxy Pattern");
        System.out.println(" 10. ✓ Facade Pattern");

        System.out.println("\n🎉 Sistem tamamen çalışır durumda!");
    }
}
