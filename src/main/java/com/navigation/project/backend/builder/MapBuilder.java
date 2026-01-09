package com.navigation.project.backend.builder;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.model.NodeType;


/**
 * Şehir ve yol eklememize olanak tanır
 * Her fonk. sonunda this döner ki zincirleme bir biçimde node ve yol oluşturalım
 */

// [PATTERN: Builder]
// Karmaşık harita oluşturma sürecini, adım adım ve zincirleme (fluent) metodlarla basitleştirir.
public class MapBuilder {

    private final CityMap map;

    public MapBuilder(){
        // İnşaata başlamadan önce Singleton haritayı çağırıyoruz
        this.map = CityMap.getInstance();
    }

    // 1. Düğüm Ekleme Zinciri
    public MapBuilder addNode(String name, NodeType type){
        Node newNode = new Node(name, type);
        map.addNode(newNode);
        return this;    // "this" döndürerek zincirin devam etmesini sağlıyoruz (.add().add())
    }

    // 2. Yol Ekleme Zinciri (İsimden isme yol çeker)
    public MapBuilder addRoad(String fromName, String toName, double distance, int speedLimit){
        Node source = findNode(fromName);
        Node destination = findNode(toName);

        if (source != null && destination != null){
            // Şehirler arası yollar genelde gidiş-dönüştür.
            // Bu yüzden iki yönlü ekliyoruz (A -> B ve B -> A)

            // Gidiş Yolu
            map.addEdge(new Edge(source, destination, distance, speedLimit));

            // Dönüş Yolu
            map.addEdge(new Edge(destination, source, distance, speedLimit));
        }
        else{
            System.err.println("UYARI: Yol eklenemedi çünkü şehirler bulunamadı: " + fromName + " -> " + toName);
        }
        return this;
    }

    // Yardımcı Metot: İsme göre Node bulmak için
    private Node findNode(String name){
        for(Node node : map.getNodes()){
            if (node.getName().equals(name)){
                return node;
            }
        }
        return null;
    }

    // 3. İnşaatı bitir
    public CityMap build(){
        return map;
    }
}