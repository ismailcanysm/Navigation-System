package com.navigation.project.backend.observer;

import com.navigation.project.backend.model.Edge;

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
