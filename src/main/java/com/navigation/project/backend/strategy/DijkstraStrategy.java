package com.navigation.project.backend.strategy;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;
import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.model.VehicleType;

import java.util.*;

/**
 * DijkstraStrategy - Dijkstra Algoritması İmplementasyonu
 *
 * AMAÇ:
 * Dijkstra algoritması ile en kısa yol hesaplar.
 * Araç tipine göre süre hesaplaması yapar.
 *
 * NE İŞE YARAR:
 * - Weighted graph'te en kısa yolu bulur
 * - OPEN olmayan yolları kullanmaz
 * - Araç tipine göre hız ve süre hesaplar
 * - Priority Queue kullanarak optimize eder
 *
 * PATTERN: Strategy Pattern
 * İLİŞKİLİ SINIFLAR: CityMap, Node, Edge, RouteCalculationResult
 *
 * ALGORİTMA ADIMLARI:
 * 1. Başlangıç node'unun mesafesini 0 yap
 * 2. Diğer tüm node'ların mesafesini sonsuz yap
 * 3. Priority Queue kullan (en yakın node önce)
 * 4. Her node için:
 *    - Komşuları gez
 *    - Sadece OPEN yolları kullan
 *    - Daha kısa yol varsa güncelle
 * 5. Hedefe ulaşınca dur
 * 6. Previous map'inden yolu geri sar
 * 7. Araç tipine göre süre hesapla
 *
 * SÜRE HESAPLAMA:
 * - CAR: Yol hız limiti kullanılır
 * - BUS: Max 80 km/h, her segment +5 dk durak
 * - WALK: Sabit 5 km/h
 * - Formül: time = (distance / effectiveSpeed) * 60 (dakika)
 *
 * TEMEL METODLAR:
 * - calculateRoute(start, end, vehicle): Ana metod, RouteCalculationResult döner
 * - buildResult(): Sonuç nesnesini oluşturur
 * - calculateDuration(): Toplam süreyi hesaplar
 * - findEdge(): İki node arası edge bulur
 * - getOutgoingEdges(): Bir node'dan çıkan edge'leri döner
 *
 * KARMAŞIKLIK: O((V+E) log V)
 * - V: Node sayısı
 * - E: Edge sayısı
 */

/**
 * DijkstraStrategy - Dijkstra Algoritması
 *
 * En kısa mesafe + Süre hesaplama (Zaman = Mesafe / Hız)
 * Araç tipine göre süre hesaplar
 */
public class DijkstraStrategy implements IRouteStrategy {

    @Override
    public RouteCalculationResult calculateRoute(Node startNode, Node endNode) {
        // Default: Araba (geriye uyumluluk)
        return calculateRoute(startNode, endNode, VehicleType.CAR);
    }

    /**
     * Rota hesapla - ARAÇ TİPİNE GÖRE SÜRE HESAPLA
     * Zaman = Mesafe / Hız formülü
     */
    public RouteCalculationResult calculateRoute(Node startNode, Node endNode, VehicleType vehicle) {
        if (startNode == null || endNode == null) {
            System.err.println("HATA: Başlangıç ve Bitiş noktası boş olamaz!");
            return new RouteCalculationResult(Collections.emptyList(), 0, 0);
        }

        System.out.println("[Dijkstra] Rota hesaplanıyor: " + startNode.getName() + " → " + endNode.getName());
        System.out.println("[Dijkstra] Araç tipi: " + vehicle);

        // 1. Singleton Haritayı çağır
        CityMap map = CityMap.getInstance();

        // Algoritma Değişkenleri
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        // Öncelik Kuyruğu
        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingDouble(distances::get)
        );

        // Başlangıç Ayarları
        for (Node node : map.getNodes()) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(startNode, 0.0);
        queue.add(startNode);

        // 2. Dijkstra Algoritması
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited.contains(current)) continue;
            visited.add(current);

            if (current.equals(endNode)) {
                break;
            }

            for (Edge edge : getOutgoingEdges(map, current)) {
                // Kapalı ve çalışma olan yolları kullanma
                if (edge.getStatus() != EdgeStatus.OPEN) {
                    System.out.println("[Dijkstra]   Yol atlandı (Kapalı/Çalışma): " +
                            edge.getSource().getName() + " → " + edge.getDestination().getName());
                    continue;
                }

                Node neighbour = edge.getDestination();
                double newDist = distances.get(current) + edge.getDistance();

                if (newDist < distances.get(neighbour)) {
                    distances.put(neighbour, newDist);
                    previousNodes.put(neighbour, current);
                    queue.remove(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        // 3. Rotayı Oluştur ve Süreyi Hesapla
        return buildResult(previousNodes, distances, endNode, vehicle);
    }

    /**
     * Sonuç oluştur - SÜRE HESAPLAMASI DAHİL
     */
    private RouteCalculationResult buildResult(
            Map<Node, Node> previousNodes,
            Map<Node, Double> distances,
            Node endNode,
            VehicleType vehicle) {

        List<Node> path = new ArrayList<>();
        Node step = endNode;

        // Yol bulunamadı
        if (distances.get(endNode) == Double.MAX_VALUE) {
            System.out.println("[Dijkstra] UYARI: Hedefe giden uygun bir yol bulunamadı.");
            return new RouteCalculationResult(Collections.emptyList(), 0, 0);
        }

        // Rotayı oluştur (tersten)
        while (step != null) {
            path.add(step);
            step = previousNodes.get(step);
        }
        Collections.reverse(path);

        // Mesafe
        double totalDistance = distances.get(endNode);

        // Süreyi hesapla (Zaman = Mesafe / Hız)
        double totalDuration = calculateDuration(path, vehicle);

        System.out.println(String.format("[Dijkstra] Rota bulundu: %.1f km, %.1f dk (%s)",
                totalDistance, totalDuration, vehicle));

        return new RouteCalculationResult(path, totalDistance, totalDuration);
    }

    /**
     * SÜRE HESAPLAMA - ARAÇ TİPİNE GÖRE
     * Zaman = Mesafe / Hız (dakika)
     */
    private double calculateDuration(List<Node> path, VehicleType vehicle) {
        if (path.size() < 2) return 0;

        CityMap map = CityMap.getInstance();
        double totalTime = 0; // dakika

        System.out.println("[Dijkstra] Süre hesaplanıyor:");

        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);

            Edge edge = findEdge(map, from, to);
            if (edge != null) {
                double distance = edge.getDistance();
                int edgeSpeedLimit = edge.getSpeedLimit();
                double effectiveSpeed;

                switch (vehicle) {
                    case CAR:
                        // Araba edge'deki hız limitini kullanır
                        effectiveSpeed = edgeSpeedLimit;
                        break;

                    case BUS:
                        if (edgeSpeedLimit <= 80)
                            effectiveSpeed = edgeSpeedLimit - 10;
                        else
                            effectiveSpeed = 80;
                        totalTime += 30; // Her segment için 30 dk durak süresi
                        break;

                    case WALK:
                        // Yürüyüş sabit 5 km/h
                        effectiveSpeed = 5;
                        break;

                    default:
                        effectiveSpeed = 100;
                }

                // Zaman = Mesafe / Hız (saat) * 60 = dakika
                double segmentTime = (distance / effectiveSpeed) * 60;
                totalTime += segmentTime;

                System.out.println(String.format("[Dijkstra]   %s → %s: %.1f km @ %.0f km/h = %.1f dk",
                        from.getName(), to.getName(), distance, effectiveSpeed, segmentTime));
            }
        }

        System.out.println(String.format("[Dijkstra] Toplam süre: %.1f dakika", totalTime));
        return totalTime;
    }

    /**
     * İki node arasındaki edge'i bul
     */
    private Edge findEdge(CityMap map, Node from, Node to) {
        for (Edge edge : map.getEdges()) {
            if (edge.getSource().equals(from) && edge.getDestination().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Node'dan çıkan edge'leri bul
     */
    private List<Edge> getOutgoingEdges(CityMap map, Node source) {
        List<Edge> result = new ArrayList<>();
        for (Edge edge : map.getEdges()) {
            if (edge.getSource().equals(source)) {
                result.add(edge);
            }
        }
        return result;
    }
}