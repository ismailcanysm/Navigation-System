package com.navigation.project.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * NotificationPanel - Bildirim Paneli (KULLANILMIYOR)
 *
 * AMAÇ:
 * Bildirimleri gösterir (alternatif implementasyon).
 *
 * NOT:
 * Bu sınıf mevcut uygulamada kullanılmıyor.
 * Yerine MainController'da VBox kullanılıyor.
 * Alternatif bildirim sistemi için saklanıyor.
 *
 * NE İŞE YARAR (ALTERNATİF KULLANIM):
 * - ScrollPane içinde bildirimler gösterir
 * - Renkli etiketler kullanır
 * - Zaman damgası ekler
 * - Max 10 bildirim tutar
 *
 * İLİŞKİLİ SINIFLAR: MainController
 */

/**
 * NotificationPanel - Bildirim Paneli
 *
 * Observer Pattern'den gelen bildirimleri gösterir.
 *
 * @author Kişi 2
 */
public class NotificationPanel extends ScrollPane {

    private VBox notificationBox;
    private int maxNotifications = 10;

    public NotificationPanel() {
        // Notification container
        notificationBox = new VBox(5);
        notificationBox.setPadding(new Insets(10));
        notificationBox.setStyle("-fx-background-color: #f5f5f5;");

        setContent(notificationBox);
        setFitToWidth(true);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setHbarPolicy(ScrollBarPolicy.NEVER);

        // Başlangıç mesajı
        addNotification("🟢 Sistem hazır", Color.GREEN);
    }

    /**
     * Bildirim ekle
     */
    public void addNotification(String message, Color color) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Label notification = new Label("[" + time + "] " + message);
        notification.setFont(Font.font(12));
        notification.setTextFill(color);
        notification.setWrapText(true);
        notification.setPadding(new Insets(5));
        notification.setStyle("-fx-background-color: white; -fx-border-color: " +
                toHex(color) + "; -fx-border-width: 1; -fx-border-radius: 3;");

        // En üste ekle
        notificationBox.getChildren().add(0, notification);

        // Maksimum sayıyı aşarsa en eskiyi sil
        if (notificationBox.getChildren().size() > maxNotifications) {
            notificationBox.getChildren().remove(maxNotifications);
        }
    }

    /**
     * Tüm bildirimleri temizle
     */
    public void clearNotifications() {
        notificationBox.getChildren().clear();
        addNotification("🗑️ Bildirimler temizlendi", Color.GRAY);
    }

    /**
     * Color'ı hex string'e çevir
     */
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}