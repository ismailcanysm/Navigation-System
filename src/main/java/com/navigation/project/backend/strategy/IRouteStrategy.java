package com.navigation.project.backend.strategy;

import com.navigation.project.backend.model.Node;

/**
 * IRouteStrategy - Strategy Pattern Interface
 *
 * AMAÇ:
 * Rota hesaplama algoritmaları için ortak ara yüz tanımlar.
 *
 * NE İŞE YARAR:
 * - Algoritma değiştirilebilirliği sağlar
 * - Farklı rota algoritmaları implement edilebilir
 * - Runtime'da algoritma seçimi yapılabilir
 *
 * PATTERN: Strategy Pattern
 * IMPLEMENTASYONLAR: DijkstraStrategy
 * GELECEKTEKİ: AStarStrategy, BellmanFordStrategy
 *
 * GEREKLI METODLAR:
 * - calculateRoute(start, end): Rota hesaplar ve sonuç döner
 */

public interface IRouteStrategy {
    // Başlangıç ve Bitiş noktalarını al, hesaplama sonucunu döndür
    RouteCalculationResult calculateRoute(Node startNode, Node endNode);
}
