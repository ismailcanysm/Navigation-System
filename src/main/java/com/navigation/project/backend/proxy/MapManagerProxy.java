package com.navigation.project.backend.proxy;

import com.navigation.project.backend.model.Edge;

public class MapManagerProxy implements IMapManager{
    private MapManager realManager;
    private boolean isAdmin;

    public MapManagerProxy() {
        this.realManager = new MapManager();
        this.isAdmin = false; // başlangıçta admin değil
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        System.out.println("Admin modu: " + (isAdmin ? "AÇIK" : "KAPALI"));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void blockRoad(Edge edge) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }

        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.blockRoad(edge);
    }

    @Override
    public void openRoad(Edge edge) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }

        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.openRoad(edge);
    }

    @Override
    public void changeSpeed(Edge edge, int newSpeed) {
        if (!isAdmin) {
            System.out.println("Engellendi! Admin yetkisi gerekli!!!");
            return;
        }
        System.out.println("Yetki var. İşlem yapılıyor...");
        realManager.changeSpeed(edge, newSpeed);
    }
}
