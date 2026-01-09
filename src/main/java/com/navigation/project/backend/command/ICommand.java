package com.navigation.project.backend.command;

/**
 * ICommand - Command Pattern Interface
 *
 * AMAÇ:
 * Tüm komut sınıfları için ortak davranış tanımlar.
 * Geri alınabilir işlemler için standart bir yapı sağlar.
 *
 * NE İŞE YARAR:
 * - İşlemleri nesne olarak temsil eder
 * - Execute/Undo mekanizması sunar
 * - Komut geçmişi tutulmasını sağlar
 *
 * PATTERN: Command Pattern
 * IMPLEMENTASYONLAR: BlockRoadCommand, SetSpeedLimitCommand
 *
 * GEREKLI METODLAR:
 * - execute(): Komutu çalıştırır
 * - undo(): Komutu geri alır
 * - commitDescription(): Komut açıklaması döner
 */

public interface ICommand {
    // komutu çalıştır
    void execute();

    // komutu geri al
    void undo();

    // komutun açıklamasını döndür
    String commitDescription();
}
