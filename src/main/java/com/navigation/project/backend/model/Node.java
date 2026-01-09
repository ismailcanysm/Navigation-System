package com.navigation.project.backend.model;

import java.util.Objects;

/**
 * Node - Şehir/Düğüm Veri Modeli
 *
 * AMAÇ:
 * Haritadaki şehirleri (düğümleri) temsil eden immutable veri sınıfı.
 *
 * NE İŞE YARAR:
 * - Şehir ismini tutar
 * - Şehir tipini tutar (CITY)
 * - Equals/hashCode ile karşılaştırma sağlar
 * - Immutable (değiştirilemez) yapı
 *
 * İLİŞKİLİ SINIFLAR: Edge, NodeType, CityMap
 *
 * FIELD'LAR:
 * - name (String): Şehir adı
 * - type (NodeType): Şehir tipi
 *
 * TEMEL METODLAR:
 * - getName(): Şehir adını döner
 * - getType(): Şehir tipini döner
 * - equals(Object): İsim bazlı eşitlik kontrolü
 * - hashCode(): İsim bazlı hash
 * - toString(): "İstanbul (CITY)" formatında string
 *
 * ÖRNEK:
 * Node istanbul = new Node("İstanbul", NodeType.CITY);
 * Node ankara = new Node("Ankara", NodeType.CITY);
 */

public class Node {
    private final String name;
    private final NodeType type;

    public Node(String name, NodeType type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public NodeType getType(){
        return type;
    }

    @Override
    public String toString(){
        return name + " (" + type + ")";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }
}