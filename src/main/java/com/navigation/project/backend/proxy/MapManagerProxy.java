package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;

/**
 * MapManagerProxy - Harita Yönetim Proxy'si
 *
 * AMAÇ:
 * MapManager'a erişimi kontrol eder ve yetki kontrolü yapar.
 * Sadece admin kullanıcıların işlem yapmasını sağlar.
 *
 * NE İŞE YARAR:
 * - Admin yetkisi kontrolü yapar
 * - Yetkisiz erişimi engeller
 * - Yetkili kullanıcıları MapManager'a yönlendirir
 * - Security katmanı sağlar
 *
 * PATTERN: Proxy Pattern (Protection Proxy)
 * İLİŞKİLİ SINIFLAR: MapManager, IMapManager
 *
 * TEMEL METODLAR:
 * - setAdmin(isAdmin): Admin modunu ayarlar
 * - isAdmin(): Admin durumunu döner
 * - blockRoad(edge): Yetki kontrolü → realManager.blockRoad()
 * - openRoad(edge): Yetki kontrolü → realManager.openRoad()
 * - changeSpeed(edge, speed): Yetki kontrolü → realManager.changeSpeed()
 *
 * ÇALIŞMA AKIŞI:
 * 1. İstemci proxy.blockRoad() çağırır
 * 2. Proxy isAdmin kontrolü yapar
 * 3. Admin değilse: "Yetki yok" mesajı, return
 * 4. Admin ise: realManager.blockRoad() çağrılır
 *
 * KULLANIM:
 * MapManagerProxy proxy = new MapManagerProxy();
 * proxy.setAdmin(false);
 * proxy.blockRoad(edge);  // "YETKİ YOK!" (engellendi)
 *
 * proxy.setAdmin(true);
 * proxy.blockRoad(edge);  // Yol kapandı (izin verildi)
 *
 * AVANTAJLAR:
 * - Merkezi yetki kontrolü
 * - Gerçek nesneyi korur
 * - İstemci kodu yetki kontrolü yapmak zorunda değil
 */

public class MapManagerProxy implements IMapManager{
    private MapManager realManager;
    private boolean isAdmin;

    public MapManagerProxy() {
        this.realManager = new MapManager();
        this.isAdmin = false; // başlangıçta admin değil
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        System.out.println("Admin modu: " + (isAdmin ? "AÇIK" : "KAPALI"));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void blockRoad(Edge edge) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }

        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.blockRoad(edge);
    }

    @Override
    public void openRoad(Edge edge) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }

        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.openRoad(edge);
    }

    @Override
    public void changeSpeed(Edge edge, int newSpeed) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }
        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.changeSpeed(edge, newSpeed);
    }
}
