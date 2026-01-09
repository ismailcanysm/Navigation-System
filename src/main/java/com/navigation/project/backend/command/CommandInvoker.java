package com.navigation.project.backend.command;

import java.util.ArrayList;
import java.util.List;

/**
 * CommandInvoker - Komut Yöneticisi
 *
 * AMAÇ:
 * Komutları çalıştırır ve komut geçmişini yönetir.
 * Undo/Redo işlemlerini koordine eder.
 *
 * Komutları çalıştırır (execute)
 * Komut geçmişini tutar (history)
 * Geri alma işlemi yapar (undo)
 * Stack mantığıyla son komutu yönetir
 *
 * TEMEL METODLAR:
 * execute(command): Komutu çalıştır ve geçmişe ekle
 * undo(): Son komutu geri al
 * clear(): Geçmişi temizle
 * size(): Geçmiş boyutunu döner
 *
 * ÖRNEK:
 * CommandInvoker invoker = new CommandInvoker();
 * invoker.execute(cmd1);
 * invoker.execute(cmd2);
 * invoker.undo();  // cmd2 geri alındı
 * invoker.undo();  // cmd1 geri alındı
 */

// Komutları çalıştırır ve geçmişi tutar
public class CommandInvoker {
    // Komut geçmişi (Stack mantığı ile çalışır)
    // son eklenen komut ilk geri alınacak komut olacak
    private List<ICommand> history;

    // Boş geçmiş ile başlanır
    public CommandInvoker() {
        this.history = new ArrayList<>();
    }

    public void execute(ICommand command) {
        command.execute();

        history.add(command); // geçmişe eklenir
    }

    public boolean undo() {
        if (history.isEmpty()) {
            System.out.println("Geri alınacak bir komut yok!!");
            return false;
        }

        // Son komut alınır
        int lastIndex = history.size() - 1;
        ICommand lastCommand = history.get(lastIndex);

        history.remove(lastIndex); // geçmişten çıkar

        lastCommand.undo(); // komutu geri al

        return true;
    }

    // history'i temizler
    public void clear() {
        history.clear();
    }

    // history'nin boyutunu döndürür
    public int size() {
        return history.size();
    }
}
