package com.navigation.project.backend.iterator;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.model.NodeType;

import java.util.List;

/**
 * interface yardımı ile node'ları gezer
 * sadece gezinme işlemini yapar
 * farklı gezinme işlemleri eklenebilir
 */

public class GraphIterator implements INodeIterator {
    private Node[] nodeArray; // iç yapıyı tutar

    private int currentPosition; // güncel pozisyon

    public GraphIterator() {
        // Singleton dan veri alınır
        CityMap cityMap = CityMap.getInstance();

        // List'i diziye çevir
        List<Node> nodeList = cityMap.getNodes();
        this.nodeArray = nodeList.toArray(new Node[0]);
        this.currentPosition = 0;

        System.out.println("Graph iterator oluşturuldu.");
        System.out.println("Toplam node: " + nodeArray.length);
    }

    // sadece belirli tipteki node'ları gez
    public GraphIterator(NodeType filterType) {
        CityMap cityMap = CityMap.getInstance();
        List<Node> allNodes = cityMap.getNodes();

        // kaç tane filtrelenmiş node var say
        int count = 0;
        for (Node node : allNodes) {
            if (node.getType() == filterType)
                count++;
        }

        // Dizi oluşturuldu
        this.nodeArray = new Node[count];
        int index = 0;

        // Filtrelenmiş nodelar diziye eklenir
        for (Node node : allNodes) {
            if (node.getType() == filterType) {
                nodeArray[index] = node;
                index++;
            }
        }

        this.currentPosition = 0;

        System.out.println("Filtrelenmiş iterator oluşturuldu.");
        System.out.println("Filtre tipi: " + filterType);
        System.out.println("Bulunan node: " + nodeArray.length);
    }

    // gezilecek node var mı kontrolü

    @Override
    public boolean hasNext() {
        return currentPosition < nodeArray.length;
    }

    // bir sonraki node'u döndür

    @Override
    public Node next() {
        // sonraki eleman kontrolü
        if (!hasNext()) {
            System.err.println("HATA: Daha eleman yok!!!");
            return null;
        }

        // Mevcut node al
        Node currentNode = nodeArray[currentPosition];

        // ilerle
        currentPosition++;

        return currentNode;
    }

    // Başa sar, yeniden başlat

    @Override
    public void reset() {
        this.currentPosition = 0;
        System.out.println("Iterator başa sarıldı.");
    }
}
