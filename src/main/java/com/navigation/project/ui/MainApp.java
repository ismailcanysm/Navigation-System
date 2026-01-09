package com.navigation.project.ui;

import com.navigation.project.ui.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * MainApp - JavaFX Uygulama Başlatıcı
 *
 * AMAÇ:
 * JavaFX uygulamasını başlatır ve MainController'a Stage'i iletir.
 *
 * NE İŞE YARAR:
 * - JavaFX Application sınıfını extend eder
 * - Stage (pencere) oluşturur
 * - MainController'ı başlatır (Scene oluşturma MainController'da yapılır)
 *
 * İLİŞKİLİ SINIFLAR: MainController
 *
 * TEMEL METODLAR:
 * - start(Stage): JavaFX başlatma metodu
 * - stop(): Uygulama kapatılırken çağrılır
 * - main(String[]): JVM başlangıç noktası
 *
 * NOT:
 * MainController kendi Scene'ini oluşturur ve Stage'e set eder.
 * Bu sınıf sadece başlatma işlemini yapar.
 *
 * KULLANIM:
 * java MainApp
 */
public class MainApp extends Application {

    private MainController controller;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("[UI] Uygulama başlatılıyor...");

        // MainController oluştur - kendi Scene'ini oluşturur ve Stage'e set eder
        controller = new MainController(primaryStage);

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