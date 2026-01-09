package com.navigation.project.backend.command;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;
import com.navigation.project.backend.observer.TrafficNotifier;

/**
 * Yol kapatma işlemini geri alınabilir şekilde gerçekleştirir.
 * PATTERN: Command Pattern
 *
 * ÇALIŞMA AKIŞI:
 * 1. Constructor: Edge ve eski durumu sakla
 * 2. execute(): Yolu kapat, bildirim gönder
 * 3. undo(): Eski duruma döndür, bildirim gönder
 *
 * ÖRNEK:
 * ICommand cmd = new BlockRoadCommand(edge, notifier);
 * invoker.execute(cmd);  // Yol kapandı
 * invoker.undo();        // Yol eski haline döndü
 */

public class BlockRoadCommand implements ICommand{
    // kapatılacak yol
    private Edge edge;

    // yolun önceki durumu
    private EdgeStatus oldStatus;

    // bildirimleri yönetir
    private TrafficNotifier notifier;

    public BlockRoadCommand(Edge edge, TrafficNotifier notifier) {
        this.edge = edge;
        this.oldStatus = edge.getStatus();
        this.notifier = notifier;
    }

    // yolu kapat ve bildirimleri gönder
    @Override
    public void execute() {
        edge.setStatus(EdgeStatus.UNDER_CONSTRUCTION); // yolun durumunu değiştirilir

        // mesaj üretilir
        String msg = edge.getSource().getName() + " -> " + edge.getDestination().getName() + " kapatıldı.";
        notifier.notifyRoadStatusChanged(edge, msg); // tüm observerlara mesaj iletilir
    }

    // yol durumunu eski haline getir ve bildirim gönder
    @Override
    public void undo() {
        edge.setStatus(oldStatus); // eski haline döndürülür

        // mesaj üretilir
        String msg = edge.getSource().getName() + " -> " + edge.getDestination().getName() + " açıldı";
        notifier.notifyRoadStatusChanged(edge, msg); // mesaj iletilir
    }

    // Log ve debug için kullanılır
    @Override
    public String commitDescription() {
        return "Yolu kapat: " + edge.getSource().getName() + " -> " + edge.getDestination().getName();
    }
}
