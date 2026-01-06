package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;

public interface IMapManager {
    void blockRoad(Edge edge); // yolu kapatır
    void openRoad(Edge edge); // yolu aç
    void changeSpeed(Edge edge, int newSpeed);
}
