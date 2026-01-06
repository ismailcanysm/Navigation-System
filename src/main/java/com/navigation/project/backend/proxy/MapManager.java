package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;

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
