package com.navigation.project.backend.observer;

import com.navigation.project.backend.model.Edge;

/**
 * ITrafficObserver - Observer Pattern Interface
 *
 * AMAÇ:
 * Trafik değişikliklerini dinleyen sınıflar için ortak davranış tanımlar.
 *
 * NE İŞE YARAR:
 * - Yol durumu değişikliklerini alır
 * - Hız limiti değişikliklerini alır
 * - Observer'ların implement etmesi gereken metodları tanımlar
 *
 * PATTERN: Observer Pattern
 * İLİŞKİLİ SINIFLAR: TrafficNotifier
 *
 * GEREKLI METODLAR:
 * - onRoadStatusChanged(edge, message): Yol durumu değişti
 * - onSpeedLimitChanged(edge, oldLimit, newLimit): Hız limiti değişti
 */

/**
 * Trafik değişkenlerini dinleyen sınıfları tanımlar
 * Yol kapatılabilir ve hız sınırı değiştirilebilir
 * Obseverlar birbirini tanımaz
 */

public interface ITrafficObserver {

    // Yol durumu değiştiğinde çağırılır
    void onRoadStatusChanged(Edge edge, String message);

    // Hız sınırı değiştiğinde çağırılır
    void onSpeedLimitChanged(Edge edge, int oldLimit, int newLimit);
}
