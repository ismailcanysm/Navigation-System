package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;

/**
 * IMapManager - Map Yönetim Interface
 *
 * AMAÇ:
 * Harita yönetim işlemleri için ortak ara yüz tanımlar.
 *
 * NE İŞE YARAR:
 * - MapManager ve MapManagerProxy için ortak davranış
 * - Yol kapatma/açma işlemleri
 * - Hız değiştirme işlemleri
 *
 * PATTERN: Proxy Pattern
 * İLİŞKİLİ SINIFLAR: MapManager, MapManagerProxy
 *
 * GEREKLI METODLAR:
 * - blockRoad(edge): Yolu kapatır
 * - openRoad(edge): Yolu açar
 * - changeSpeed(edge, speed): Hız limitini değiştirir
 */

public interface IMapManager {
    void blockRoad(Edge edge); // yolu kapatır
    void openRoad(Edge edge); // yolu aç
    void changeSpeed(Edge edge, int newSpeed);
}
