package com.navigation.project.backend.strategy;

import com.navigation.project.backend.model.Node;

public interface IRouteStrategy {
    // Başlangıç ve Bitiş noktalarını al, hesaplama sonucunu döndür
    RouteCalculationResult calculateRoute(Node startNode, Node endNode);
}
