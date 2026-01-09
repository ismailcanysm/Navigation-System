package com.navigation.project.backend.model;

/**
 * Edge - Yol/Kenar Veri Modeli
 *
 * AMAÇ:
 * İki şehir arasındaki yolu (kenarı) temsil eder.
 * Mesafe, hız limiti ve durum bilgisi tutar.
 *
 * NE İŞE YARAR:
 * - Kaynak ve hedef node'u tutar
 * - Mesafe bilgisi tutar (km)
 * - Hız limiti tutar (km/h)
 * - Yol durumu tutar (OPEN/CLOSED/UNDER_CONSTRUCTION)
 * - Durum ve hız değiştirilebilir
 *
 * İLİŞKİLİ SINIFLAR: Node, EdgeStatus, CityMap
 *
 * FIELD'LAR:
 * - source (Node): Başlangıç şehri
 * - destination (Node): Bitiş şehri
 * - distance (double): Mesafe (km)
 * - speedLimit (int): Hız limiti (km/h)
 * - status (EdgeStatus): Yol durumu
 *
 * TEMEL METODLAR:
 * - getSource(), getDestination(): Node'ları döner
 * - getDistance(): Mesafeyi döner
 * - getSpeedLimit(): Hız limitini döner
 * - getStatus(): Yol durumunu döner
 * - setSpeedLimit(int): Hız limitini değiştirir
 * - setStatus(EdgeStatus): Yol durumunu değiştirir
 *
 * ÖRNEK:
 * Edge road = new Edge(istanbul, ankara, 450, 120);
 * road.setStatus(EdgeStatus.UNDER_CONSTRUCTION);
 */

public class Edge {
    private final Node source;          // Başlangıç
    private final Node destination;     // Bitiş
    private double distance;      // Mesafe (km)
    private int speedLimit;          // Hız sınırı (km/s)

    private EdgeStatus status;          // Yolun durumu (Değişebilir)

    public Edge(Node source, Node destination, double distance, int speedLimit){
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.speedLimit = speedLimit;
        this.status = EdgeStatus.OPEN;  // Yollar varsayılan olarak açık başlar.
    }

    public Node getSource(){
        return source;
    }

    public Node getDestination(){ return destination; }

    public double getDistance(){
        return distance;
    }

    public int getSpeedLimit(){
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) { this.speedLimit = speedLimit; }

    public EdgeStatus getStatus(){
        return status;
    }

    public void setStatus(EdgeStatus status){
        this.status = status;
    }
}
