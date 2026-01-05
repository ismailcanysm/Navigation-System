package com.navigation.project.backend.observer;

import com.navigation.project.backend.model.Edge;

import java.util.ArrayList;
import java.util.List;

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
