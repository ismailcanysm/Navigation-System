package com.navigation.project.backend.facade;

import com.navigation.project.backend.builder.MapBuilder;
import com.navigation.project.backend.command.BlockRoadCommand;
import com.navigation.project.backend.command.CommandInvoker;
import com.navigation.project.backend.command.SetSpeedLimitCommand;
import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.factory.TripFactory;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.model.NodeType;
import com.navigation.project.backend.model.VehicleType;
import com.navigation.project.backend.observer.ITrafficObserver;
import com.navigation.project.backend.observer.TrafficNotifier;
import com.navigation.project.backend.proxy.MapManagerProxy;
import com.navigation.project.backend.strategy.DijkstraStrategy;
import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.template.TripAlgorithm;

/**
 * NavigationFacade - Facade Pattern İmplementasyonu
 *
 * AMAÇ:
 * Karmaşık backend sistemini basit ve kullanıcı dostu bir API ile sunar.
 * 10 farklı design pattern'i koordine eder ve tek noktadan erişim sağlar.
 */

public class NavigationFacade {
    private CityMap map;
    private TrafficNotifier notifier;
    private CommandInvoker invoker;
    private MapManagerProxy proxy;
    private IRouteStrategy strategy;

    public NavigationFacade() {
        this.notifier = new TrafficNotifier();
        this.invoker = new CommandInvoker();
        this.proxy = new MapManagerProxy();
        this.strategy = new DijkstraStrategy();
        this.map = CityMap.getInstance();
    }

    // Sistemi başlat
    public void initSystem() {
        new MapBuilder().addNode("A", NodeType.CITY)
                .addNode("B", NodeType.CITY)
                .addNode("C", NodeType.CITY)
                .addRoad("A", "B", 10, 60)
                .addRoad("B", "C", 15, 50)
                .build();

        //this.map = CityMap.getInstance();
        System.out.println("Sistem hazır.");
    }

    // Rota hesapla
    public void calculateRoute(String startName, String endName, VehicleType vehicle) {
        System.out.println("Rota hesaplanıyor...");

        Node start = findNode(startName);
        Node end = findNode(endName);

        if (start == null || end == null) {
            System.out.println("Hata: şehir bulunamadı!!!");
            return;
        }

        TripAlgorithm trip = TripFactory.createTrip(vehicle, strategy);
        trip.executeTrip(start, end);
    }

    public void setAdminMode(boolean isAdmin) {
        proxy.setAdmin(isAdmin);
    }

    public void blockRoad(String fromName, String toName) {
        Edge edge = findEdge(fromName, toName);
        if (edge != null) {
            invoker.execute(new BlockRoadCommand(edge, notifier));
        }
    }

    public void changeSpeed(String fromName, String toName, int newSpeed) {
        Edge edge = findEdge(fromName, toName);
        if (edge != null) {
            invoker.execute(new SetSpeedLimitCommand(edge, newSpeed, notifier));
        }
    }

    // son komutu geri al
    public void undoLastCommand() {
        invoker.undo();
    }

    public void addObserver(ITrafficObserver observer) {
        notifier.attach(observer);
    }

    // yardımcı methodlar
    private Node findNode(String name) {
        for (Node node : map.getNodes()) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    private Edge findEdge(String fromName, String toName) {
        Node from = findNode(fromName);
        Node to = findNode(toName);

        if (from == null || to == null) return null;

        for (Edge edge : map.getEdges()) {
            if (edge.getSource().equals(from) && edge.getDestination().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    public void showStatus() {
        System.out.println("\n[Facade] Sistem Durumu:");

        if (map != null) {
            System.out.println("  → Şehir sayısı: " + map.getNodes().size());
            System.out.println("  → Yol sayısı: " + map.getEdges().size());
        } else {
            System.out.println("  → Harita: Henüz başlatılmadı");
        }

        System.out.println("  → Admin modu: " + (proxy.isAdmin() ? "AÇIK" : "KAPALI"));
        System.out.println("  → Komut geçmişi: " + invoker.size());
        System.out.println("  → Observer sayısı: " + notifier.getObserverCount());
    }
}
