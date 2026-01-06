package com.navigation.project.backend.command;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.observer.TrafficNotifier;

// bir yolun hız sınırını değiştirmek için kullanılır
public class SetSpeedLimitCommand implements ICommand{
    private Edge edge;
    private int oldSpeedLimit;
    private int newSpeedLimit;

    private TrafficNotifier notifier;

    // Parametreleri al
    public SetSpeedLimitCommand(Edge edge, int newSpeedLimit, TrafficNotifier notifier) {
        this.edge = edge;
        this.newSpeedLimit = newSpeedLimit;
        this.oldSpeedLimit = edge.getSpeedLimit();
        this.notifier = notifier;
    }

    @Override
    public void execute() {
        edge.setSpeedLimit(newSpeedLimit);
        notifier.notifySpeedLimitChanged(edge, oldSpeedLimit, newSpeedLimit);
    }

    @Override
    public void undo() {
        edge.setSpeedLimit(oldSpeedLimit);
        notifier.notifySpeedLimitChanged(edge, newSpeedLimit, oldSpeedLimit);
    }

    @Override
    public String commitDescription() {
        return "Hız: " + edge.getSource().getName() + " (" + oldSpeedLimit + " -> " + newSpeedLimit + " km/h)";
    }
}
