package com.navigation.project.backend.model;

import java.util.Objects;

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