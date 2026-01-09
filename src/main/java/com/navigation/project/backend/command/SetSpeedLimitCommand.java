package com.navigation.project.backend.command;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.observer.TrafficNotifier;

/**
 * SetSpeedLimitCommand - Hız Limiti Değiştirme Komutu
 *
 * AMAÇ:
 * Yol hız limitini değiştirmeyi geri alınabilir şekilde gerçekleştirir.
 *
 * NE İŞE YARAR:
 * - Edge hız limitini değiştirir
 * - Eski hız limitini saklar (undo için)
 * - Observer'lara bildirim gönderir
 * - Geri alma işleminde eski hıza döner
 *
 * PATTERN: Command Pattern
 * İLİŞKİLİ SINIFLAR: Edge, TrafficNotifier, CommandInvoker
 *
 * ÇALIŞMA AKIŞI:
 * 1. Constructor: Edge, eski ve yeni hızı sakla
 * 2. execute(): Hızı değiştir, bildirim gönder
 * 3. undo(): Eski hıza döndür, bildirim gönder
 *
 * ÖRNEK:
 * ICommand cmd = new SetSpeedLimitCommand(edge, 100, notifier);
 * invoker.execute(cmd);  // Hız 100'e değişti
 * invoker.undo();        // Hız eski değerine döndü
 */

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
