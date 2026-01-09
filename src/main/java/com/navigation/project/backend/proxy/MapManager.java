package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;

/**
 * MapManager - Gerçek Harita Yöneticisi (Real Subject)
 *
 * AMAÇ:
 * Harita yönetim işlemlerinin gerçek implementasyonunu yapar.
 * Yetki kontrolü yapmaz, sadece işlemi gerçekleştirir.
 *
 * NE İŞE YARAR:
 * - Edge durumunu değiştirir
 * - Hız limitini değiştirir
 * - Console'a log yazar
 * - Proxy tarafından çağrılır
 *
 * PATTERN: Proxy Pattern (Real Subject)
 * İLİŞKİLİ SINIFLAR: MapManagerProxy, Edge
 *
 * TEMEL METODLAR:
 * - blockRoad(edge): edge.setStatus(UNDER_CONSTRUCTION)
 * - openRoad(edge): edge.setStatus(OPEN)
 * - changeSpeed(edge, speed): edge.setSpeedLimit(speed)
 *
 * NOT:
 * Bu sınıf doğrudan kullanılmaz, MapManagerProxy üzerinden erişilir.
 */

public class MapManager implements IMapManager{
    @Override
    public void blockRoad(Edge edge) {
        edge.setStatus(EdgeStatus.UNDER_CONSTRUCTION);
        System.out.println("Yol kapatıldı: " + edge.getSource().getName() + " -> " + edge.getDestination().getName());
    }

    @Override
    public void openRoad(Edge edge) {
        edge.setStatus(EdgeStatus.OPEN);
        System.out.println("Yol açıldı: " + edge.getSource().getName() + " -> " + edge.getDestination().getName());
    }

    @Override
    public void changeSpeed(Edge edge, int newSpeed) {
        edge.setSpeedLimit(newSpeed);
        System.out.println("Hız değişti: " + newSpeed + " km/h");
    }
}
