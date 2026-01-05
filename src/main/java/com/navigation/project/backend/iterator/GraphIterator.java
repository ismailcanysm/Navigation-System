package com.navigation.project.backend.iterator;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.model.Node;
import com.navigation.project.backend.model.NodeType;

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

        this.nodeArray = cityMap.getAllNodes().toArray(new Node[0]);
    }
}
