package com.navigation.project.backend.command;

/**
 * Tüm komutların uygulaması gereken metodlar tanımlanır
 * her komut 1 işlemi temsil eder ve geri alınabilir
 * işlemleri nesneler olarak kapsüller
 */

public interface ICommand {
    // komutu çalıştır
    void execute();

    // komutu geri al
    void undo();

    // komutun açıklamasını döndür
    String commitDescription();
}
