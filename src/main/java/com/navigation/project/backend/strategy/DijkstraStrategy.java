package com.navigation.project.backend.strategy;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;
import com.navigation.project.backend.model.Node;

import java.util.*;

public class DijkstraStrategy implements IRouteStrategy {

    @Override
    public RouteCalculationResult calculateRoute(Node startNode, Node endNode){
        if (startNode == null || endNode == null){
            System.err.println("HATA: Başlangıç ve Bitiş noktası boş olamaz!");
            return new RouteCalculationResult(Collections.emptyList(), 0, 0);
        }

        // 1. Singleton Haritayı çağır
        CityMap map = CityMap.getInstance();

        // Algoritma Değişkenleri
        Map<Node, Double> distances = new HashMap<>();      // Her düğüme olan en kısa mesafe
        Map<Node, Node> previousNodes = new HashMap<>();    // Rotayı geri sarmak için iz
        Set<Node> visited = new HashSet<>();                // Ziyaret edilenler

        // Öncelik Kuyruğu: En kısa mesafesi olan düğümü en öne koyar
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Başlangıç Ayarları. Her yer sonsuz uzaklıkta, başlangıç 0
        for (Node node : map.getNodes()){
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(startNode, 0.0);
        queue.add(startNode);

        // 2. Algoritma Döngüsü
        while (!queue.isEmpty()){
            Node current = queue.poll();

            // Zaten ziyaret ettiysek atla
            if (visited.contains(current)) continue;
            visited.add(current);

            // Hedefe ulaştıysak döngüyü kırabiliriz (Optimizasyon)
            if(current.equals(endNode)){
                break;
            }

            // Komşuları gez
            for(Edge edge : getOutgoingEdges(map, current)){
                // KONTROL: Yol kapalıysa veya inşaat varsa geçme!
                if (edge.getStatus() != EdgeStatus.OPEN){
                    continue;
                }

                Node neighbour = edge.getDestination();
                double newDist = distances.get(current) + edge.getDistance();   // Mesafeye göre hesap

                if(newDist < distances.get(neighbour)){
                    distances.put(neighbour, newDist);
                    previousNodes.put(neighbour, current);

                    // Kuyruğu güncellemek için çıkarıp ekliyoruz
                    queue.remove(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        // 3. Rotayı Oluştur (Tersten giderek: End => Start)
        return buildResult(previousNodes, distances, endNode);
    }

    // Haritadan ilgili düğümden çıkan yolları bulan yardımcı metot
    private List<Edge> getOutgoingEdges(CityMap map, Node source){
        List<Edge> result = new ArrayList<>();
        for (Edge edge : map.getEdges()){
            if(edge.getSource().equals(source)){
                result.add(edge);
            }
        }
        return result;
    }

    // Sonuç objesini inşa eden yardımcı metot
    private RouteCalculationResult buildResult(Map<Node, Node> previousNodes, Map<Node, Double> distances, Node endNode){
        List<Node> path = new ArrayList<>();
        Node step = endNode;

        // Eğerhedefe hiç yol bulunamadıysa (Mesafe hala sonsuzsa)
        if(distances.get(endNode) == Double.MAX_VALUE){
            System.out.println("UYARI: Hedefe giden uygun bir yol bulunamadı.");
            return new RouteCalculationResult(Collections.emptyList(), 0, 0);
        }

        // Geriye doğru iz sür
        while(step != null){
            path.add(step);
            step = previousNodes.get(step);
        }
        Collections.reverse(path);  // Listeyi ters çevir (Start => End)

        return new RouteCalculationResult(path, distances.get(endNode), 0.0);
    }
}
