package com.navigation.project.backend.data;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// [PATTERN: Singleton]
// Bu sınıf harita verisini tutan TEKİL depodur.
public class CityMap {

    // 1. Kendi türünden statik, tekil bir değişken
    private static CityMap instance;

    // Harita Verileri (Noktalar ve Yollar)
    private final List<Node> nodes;
    private final List<Edge> edges;

    // 2. Constructor PRIVATE yapılır.
    // Böylece kimse dışarıdan "new CityMap()" diyemez.
    private CityMap(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    // 3. Dış dünyaya açılan TEK kapı
    // İlk çağrıldığında oluşturur, sonrakilerde var olanı döndürür.
    public static synchronized CityMap getInstance(){
        if(instance == null){
            instance = new CityMap();
        }
        return instance;
    }

    // --- Veri Okuma Metotları ---

    public void addNode(Node node){
        // Aynı isimli şehir var mı diye bakmak iyi olur ama şimdilik basit tutalım
        if (!nodes.contains(node)){
            nodes.add(node);
        }
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    // --- Veri Okuma Metotları ---

    // Dışarıdan listeyi değiştiremesinler diye "Unmodifiable" döndürüyoruz (Güvenlik)
    public List<Node> getNodes(){
        return Collections.unmodifiableList(nodes);
    }

    public List<Edge> getEdges(){
        return Collections.unmodifiableList(edges);
    }

    // Haritayı sıfırlamak için (Testlerde işe yarar)
    public void clear(){
        nodes.clear();
        edges.clear();
    }

}


















