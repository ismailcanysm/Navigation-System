package com.navigation.project.ui;

import com.navigation.project.ui.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApp - JavaFX Uygulama Başlatıcı
 *
 * AMAÇ:
 * JavaFX uygulamasını başlatır ve ana pencereyi oluşturur.
 *
 * NE İŞE YARAR:
 * - JavaFX Application sınıfını extend eder
 * - Stage (pencere) oluşturur ve yapılandırır
 * - MainController'ı başlatır
 * - Scene oluşturur ve gösterir
 *
 * İLİŞKİLİ SINIFLAR: MainController
 *
 * TEMEL METODLAR:
 * - start(Stage): JavaFX başlatma metodu
 * - stop(): Uygulama kapatılırken çağrılır
 * - main(String[]): JVM başlangıç noktası
 *
 * PENCERE AYARLARI:
 * - Başlık: "Navigasyon Sistemi - 10 Design Patterns"
 * - Boyut: 1000x700
 * - Minimum Boyut: 800x600
 *
 * KULLANIM:
 * java MainApp
 */

/**
 * MainApp - Uygulama Başlatıcı
 *
 * Yeni UI: Tab bazlı, liste görünümü
 *
 * @author Kişi 2
 */
public class MainApp extends Application {

    private MainController controller;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("[UI] Uygulama başlatılıyor...");

        // Controller oluştur
        controller = new MainController();

        // Scene oluştur
        Scene scene = new Scene(controller.getRoot(), 1000, 700);

        // Stage ayarları
        primaryStage.setTitle("Navigasyon Sistemi - Dinamik Node & Edge Yönetimi");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);

        // Göster
        primaryStage.show();

        System.out.println("[UI] Uygulama hazır!");
    }

    @Override
    public void stop() {
        System.out.println("[UI] Uygulama kapatılıyor...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}