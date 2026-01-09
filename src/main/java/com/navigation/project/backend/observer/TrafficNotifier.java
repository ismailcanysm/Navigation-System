package com.navigation.project.backend.observer;

import com.navigation.project.backend.model.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * TrafficNotifier - Observer Pattern Subject (Yayıncı)
 *
 * AMAÇ:
 * Observer'ları yönetir ve değişiklikler olduğunda bildirir.
 * Pub/Sub mekanizması sağlar.
 *
 * NE İŞE YARAR:
 * - Observer listesini tutar
 * - Observer ekleme/çıkarma işlemleri yapar
 * - Değişiklik olduğunda tüm observer'lara bildirim gönderir
 * - Command pattern ile entegre çalışır
 *
 * PATTERN: Observer Pattern (Subject/Publisher)
 * İLİŞKİLİ SINIFLAR: ITrafficObserver, BlockRoadCommand, SetSpeedLimitCommand
 *
 * TEMEL METODLAR:
 * - attach(observer): Observer ekler
 * - detach(observer): Observer çıkarır
 * - notifyRoadStatusChanged(edge, msg): Yol durumu bildirimi gönderir
 * - notifySpeedLimitChanged(edge, old, new): Hız değişikliği bildirimi
 * - cleanObservers(): Tüm observer'ları temizler
 * - getObserverCount(): Observer sayısını döner
 *
 * KULLANIM:
 * TrafficNotifier notifier = new TrafficNotifier();
 * notifier.attach(userPanel1);
 * notifier.attach(userPanel2);
 * notifier.notifyRoadStatusChanged(edge, "Yol kapandı");
 * // userPanel1 ve userPanel2'ye bildirim gitti!
 *
 * AVANTAJLAR:
 * - Otomatik bildirim (manuel notify gerekmez)
 * - Gevşek bağlılık (loose coupling)
 * - Dinamik observer ekleme/çıkarma
 */

/**
 * Subject ve Observable rolündedir
 * Trafik değişikliklerini tüm kayıtlı kullanıcılara bildirir
 */

public class TrafficNotifier{

    // observer listesi
    private List<ITrafficObserver> observers;

    // boş observer listesi ile başlanır
    public TrafficNotifier() {
        this.observers = new ArrayList<>();
        System.out.println("TraficNotifier oluşturuldu.");
    }

    // observer ekle
    public void attach(ITrafficObserver observer) {
        // aynı observer iki defa eklenmemeli
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Yeni observer eklendi.");
        }
    }

    // observer çıkar
    public void detach(ITrafficObserver observer) {
        if (observers.remove(observer))
            System.out.println("Observer silindi. Toplam: " + observers.size());
    }

    // Yol durumu değişikliğini bildir
    public void notifyRoadStatusChanged(Edge edge, String message) {
        System.out.println("Bildirim gösteriliyor: " + message);
        System.out.println(" => " + observers.size() + " observer'a bildirim yapılıyor...");

        // bütün observer'ları bilgilendir
        for (ITrafficObserver observer : observers) {
            observer.onRoadStatusChanged(edge, message);
        }

        System.out.println("Tüm observerlar bilgilendirildi.");
    }

    // Hız sınırı değişkliğini bildir
    public void notifySpeedLimitChanged(Edge edge, int oldLimit, int newLimit) {
        String message = String.format("Hız sınırı değişti: %d km/h -> %d km/h", oldLimit, newLimit);
        System.out.println("Bildirim gösteriliyor: " + message);
        System.out.println(" => " + observers.size() + "observer'a bildirim yapılıyor...");

        // bütün observer'ları bilgilendir
        for (ITrafficObserver observer : observers) {
            observer.onSpeedLimitChanged(edge, oldLimit, newLimit);
        }

        System.out.println("Tüm observerlar bilgilendirildi.");
    }

    // tüm observerları temizle
    public void cleanObservers() {
        int count = observers.size();
        observers.clear();
        System.out.println(count + " adet observer temizlendi.");
    }

    // Kaç observer kayıtlı
    public int getObserverCount() {
        return observers.size();
    }
}
