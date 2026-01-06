package com.navigation.project.backend.command;

import java.util.ArrayList;
import java.util.List;

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
