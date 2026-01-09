package com.navigation.project.ui.controller;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.facade.NavigationFacade;
import com.navigation.project.backend.model.*;
import com.navigation.project.backend.observer.ITrafficObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MainController - Observer Pattern ile güncellenmiş UI kontrolcüsü
 *
 * GÜNCELLEMELER:
 * - ITrafficObserver interface implement eder
 * - Constructor'da facade.addObserver(this) ile sisteme kaydolur
 * - onRoadStatusChanged() ve onSpeedLimitChanged() metodları otomatik bildirim gösterir
 * - Command pattern ile entegre çalışır
 */
public class MainController implements ITrafficObserver {

    private Stage stage;
    private NavigationFacade facade;
    private CityMap map;

    // Admin Panel Components
    private TextField nodeNameField;
    private ComboBox<NodeType> nodeTypeCombo;
    private ComboBox<String> edgeFromCombo, edgeToCombo;
    private TextField edgeDistanceField, edgeSpeedField;
    private ComboBox<String> manageEdgeCombo;
    private TableView<NodeDisplay> adminNodeTable;
    private TableView<EdgeDisplay> adminEdgeTable;

    // User Panel Components
    private ComboBox<String> startCityCombo, endCityCombo;
    private RadioButton carRadio, busRadio, walkRadio;
    private TextArea resultArea;
    private TableView<NodeDisplay> userNodeTable;
    private TableView<EdgeDisplay> userEdgeTable;

    // Notification Panel
    private VBox notificationBox;

    public MainController(Stage stage) {
        this.stage = stage;
        initializeBackend();
        createUI();

        // ★ OBSERVER PATTERN: MainController'ı observer olarak kaydet ★
        facade.addObserver(this);
        System.out.println("MainController Observer olarak kaydedildi");
    }

    // =========================================================================
    // BACKEND INITIALIZATION
    // =========================================================================

    private void initializeBackend() {
        facade = new NavigationFacade();
        map = CityMap.getInstance();
        map.clearAll();
        System.out.println("Backend başlatıldı.");
    }

    // =========================================================================
    // UI CREATION
    // =========================================================================

    private void createUI() {
        BorderPane root = new BorderPane();

        // Top: Title
        Label titleLabel = new Label("NAVİGASYON SİSTEMİ - 9 DESIGN PATTERNS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-padding: 10; -fx-background-color: #2196F3; -fx-text-fill: white;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        root.setTop(titleLabel);

        // Center: TabPane
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                createAdminTab(),
                createUserTab()
        );
        root.setCenter(tabPane);

        // Bottom: Notification Panel
        notificationBox = new VBox(5);
        notificationBox.setPadding(new Insets(10));
        notificationBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        Label notifTitle = new Label("📢 BİLDİRİMLER");
        notifTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        ScrollPane notifScroll = new ScrollPane(notificationBox);
        notifScroll.setFitToWidth(true);
        notifScroll.setPrefHeight(120);
        notifScroll.setStyle("-fx-background-color: transparent;");

        VBox bottomBox = new VBox(5, notifTitle, notifScroll);
        bottomBox.setPadding(new Insets(10));
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Navigasyon Sistemi");
        stage.show();
    }

    // =========================================================================
    // ADMIN TAB
    // =========================================================================

    private Tab createAdminTab() {
        Tab tab = new Tab("👨‍💼 Admin Paneli");
        tab.setClosable(false);

        VBox content = new VBox(15);
        content.setPadding(new Insets(15));

        // Node Management
        content.getChildren().add(createNodeManagementSection());

        // Edge Management
        content.getChildren().add(createEdgeManagementSection());

        // Road Management
        content.getChildren().add(createRoadManagementSection());

        // Tables
        HBox tables = new HBox(10);
        tables.getChildren().addAll(
                createAdminNodeTable(),
                createAdminEdgeTable()
        );
        content.getChildren().add(tables);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        tab.setContent(scroll);

        return tab;
    }

    private VBox createNodeManagementSection() {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: #2196F3; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: #E3F2FD;");

        Label title = new Label("🏙️ ŞEHİR YÖNETİMİ");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox inputBox = new HBox(10);
        nodeNameField = new TextField();
        nodeNameField.setPromptText("Şehir adı");
        nodeNameField.setPrefWidth(200);

        nodeTypeCombo = new ComboBox<>();
        nodeTypeCombo.getItems().addAll(NodeType.values());
        nodeTypeCombo.setValue(NodeType.CITY);

        Button addBtn = new Button("➕ Şehir Ekle");
        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        addBtn.setOnAction(e -> addNode());

        inputBox.getChildren().addAll(
                new Label("Şehir:"), nodeNameField,
                new Label("Tip:"), nodeTypeCombo,
                addBtn
        );

        box.getChildren().addAll(title, inputBox);
        return box;
    }

    private VBox createEdgeManagementSection() {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: #FF9800; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: #FFF3E0;");

        Label title = new Label("🛣️ YOL YÖNETİMİ");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox inputBox = new HBox(10);
        edgeFromCombo = new ComboBox<>();
        edgeFromCombo.setPromptText("Başlangıç");
        edgeFromCombo.setPrefWidth(150);

        edgeToCombo = new ComboBox<>();
        edgeToCombo.setPromptText("Bitiş");
        edgeToCombo.setPrefWidth(150);

        edgeDistanceField = new TextField();
        edgeDistanceField.setPromptText("Mesafe (km)");
        edgeDistanceField.setPrefWidth(100);

        edgeSpeedField = new TextField();
        edgeSpeedField.setPromptText("Hız (km/h)");
        edgeSpeedField.setPrefWidth(100);

        Button addBtn = new Button("➕ Yol Ekle");
        addBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        addBtn.setOnAction(e -> addEdge());

        inputBox.getChildren().addAll(
                new Label("Başlangıç:"), edgeFromCombo,
                new Label("Bitiş:"), edgeToCombo,
                new Label("Mesafe:"), edgeDistanceField,
                new Label("Hız:"), edgeSpeedField,
                addBtn
        );

        box.getChildren().addAll(title, inputBox);
        return box;
    }

    private VBox createRoadManagementSection() {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: #F44336; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: #FFEBEE;");

        Label title = new Label("⚙️ YOL DURUMU YÖNETİMİ");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox inputBox = new HBox(10);
        manageEdgeCombo = new ComboBox<>();
        manageEdgeCombo.setPromptText("Yol seçin");
        manageEdgeCombo.setPrefWidth(300);

        Button closeBtn = new Button("🚫 KAPAT");
        closeBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> closeRoad());

        Button constructionBtn = new Button("🚧 TADİLATTA");
        constructionBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        constructionBtn.setOnAction(e -> setConstruction());

        Button openBtn = new Button("✅ AÇ");
        openBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        openBtn.setOnAction(e -> openRoad());

        Button deleteBtn = new Button("🗑️ SİL");
        deleteBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteBtn.setOnAction(e -> deleteEdge());

        Button undoBtn = new Button("↩️ GERİ AL");
        undoBtn.setStyle("-fx-background-color: #673AB7; -fx-text-fill: white; -fx-font-weight: bold;");
        undoBtn.setOnAction(e -> undoCommand());

        inputBox.getChildren().addAll(
                new Label("Yol:"), manageEdgeCombo,
                closeBtn, constructionBtn, openBtn, deleteBtn, undoBtn
        );

        box.getChildren().addAll(title, inputBox);
        return box;
    }

    private VBox createAdminNodeTable() {
        VBox box = new VBox(5);
        Label title = new Label("📋 Şehirler");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        adminNodeTable = new TableView<>();
        TableColumn<NodeDisplay, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<NodeDisplay, String> nameCol = new TableColumn<>("Şehir Adı");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<NodeDisplay, String> typeCol = new TableColumn<>("Tip");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(100);

        adminNodeTable.getColumns().addAll(idCol, nameCol, typeCol);
        adminNodeTable.setPrefHeight(200);

        box.getChildren().addAll(title, adminNodeTable);
        return box;
    }

    private VBox createAdminEdgeTable() {
        VBox box = new VBox(5);
        Label title = new Label("📋 Yollar");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        adminEdgeTable = new TableView<>();
        TableColumn<EdgeDisplay, String> routeCol = new TableColumn<>("Rota");
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        routeCol.setPrefWidth(200);

        TableColumn<EdgeDisplay, String> distCol = new TableColumn<>("Mesafe");
        distCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distCol.setPrefWidth(80);

        TableColumn<EdgeDisplay, String> speedCol = new TableColumn<>("Hız");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        speedCol.setPrefWidth(80);

        TableColumn<EdgeDisplay, String> statusCol = new TableColumn<>("Durum");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);

        adminEdgeTable.getColumns().addAll(routeCol, distCol, speedCol, statusCol);
        adminEdgeTable.setPrefHeight(200);

        box.getChildren().addAll(title, adminEdgeTable);
        return box;
    }

    // =========================================================================
    // USER TAB
    // =========================================================================

    private Tab createUserTab() {
        Tab tab = new Tab("👤 Kullanıcı Paneli");
        tab.setClosable(false);

        VBox content = new VBox(15);
        content.setPadding(new Insets(15));

        // Route Calculation
        content.getChildren().add(createRouteCalculationSection());

        // Result Area
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(150);
        resultArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        content.getChildren().add(resultArea);

        // Tables
        HBox tables = new HBox(10);
        tables.getChildren().addAll(
                createUserNodeTable(),
                createUserEdgeTable()
        );
        content.getChildren().add(tables);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        tab.setContent(scroll);

        return tab;
    }

    private VBox createRouteCalculationSection() {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: #E8F5E9;");

        Label title = new Label("🗺️ ROTA HESAPLAMA");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox cityBox = new HBox(10);
        startCityCombo = new ComboBox<>();
        startCityCombo.setPromptText("Başlangıç şehri");
        startCityCombo.setPrefWidth(200);

        endCityCombo = new ComboBox<>();
        endCityCombo.setPromptText("Bitiş şehri");
        endCityCombo.setPrefWidth(200);

        cityBox.getChildren().addAll(
                new Label("Başlangıç:"), startCityCombo,
                new Label("Bitiş:"), endCityCombo
        );

        HBox vehicleBox = new HBox(15);
        vehicleBox.setAlignment(Pos.CENTER_LEFT);
        Label vehicleLabel = new Label("Araç Tipi:");

        ToggleGroup vehicleGroup = new ToggleGroup();
        carRadio = new RadioButton("🚗 Araba");
        carRadio.setToggleGroup(vehicleGroup);
        carRadio.setSelected(true);

        busRadio = new RadioButton("🚌 Otobüs");
        busRadio.setToggleGroup(vehicleGroup);

        walkRadio = new RadioButton("🚶 Yürüyüş");
        walkRadio.setToggleGroup(vehicleGroup);

        vehicleBox.getChildren().addAll(vehicleLabel, carRadio, busRadio, walkRadio);

        Button calculateBtn = new Button("🔍 ROTA HESAPLA");
        calculateBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        calculateBtn.setPrefWidth(200);
        calculateBtn.setOnAction(e -> calculateRoute());

        box.getChildren().addAll(title, cityBox, vehicleBox, calculateBtn);
        return box;
    }

    private VBox createUserNodeTable() {
        VBox box = new VBox(5);
        Label title = new Label("📋 Mevcut Şehirler");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        userNodeTable = new TableView<>();
        TableColumn<NodeDisplay, String> nameCol = new TableColumn<>("Şehir");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<NodeDisplay, String> typeCol = new TableColumn<>("Tip");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(100);

        userNodeTable.getColumns().addAll(nameCol, typeCol);
        userNodeTable.setPrefHeight(200);

        box.getChildren().addAll(title, userNodeTable);
        return box;
    }

    private VBox createUserEdgeTable() {
        VBox box = new VBox(5);
        Label title = new Label("📋 Mevcut Yollar");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        userEdgeTable = new TableView<>();
        TableColumn<EdgeDisplay, String> routeCol = new TableColumn<>("Rota");
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        routeCol.setPrefWidth(200);

        TableColumn<EdgeDisplay, String> distCol = new TableColumn<>("Mesafe (km)");
        distCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distCol.setPrefWidth(100);

        TableColumn<EdgeDisplay, String> statusCol = new TableColumn<>("Durum");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);

        userEdgeTable.getColumns().addAll(routeCol, distCol, statusCol);
        userEdgeTable.setPrefHeight(200);

        box.getChildren().addAll(title, userEdgeTable);
        return box;
    }

    // =========================================================================
    // ADMIN OPERATIONS
    // =========================================================================

    private void addNode() {
        String name = nodeNameField.getText().trim();
        NodeType type = nodeTypeCombo.getValue();

        if (name.isEmpty()) {
            addNotification("⚠️ Şehir adı boş olamaz!", "#F44336");
            return;
        }

        Node node = new Node(name, type);
        map.addNode(node);

        nodeNameField.clear();
        refreshAllTables();
        updateComboBoxes();
        addNotification("✅ Şehir eklendi: " + name, "#4CAF50");
    }

    private void addEdge() {
        String fromName = edgeFromCombo.getValue();
        String toName = edgeToCombo.getValue();
        String distStr = edgeDistanceField.getText().trim();
        String speedStr = edgeSpeedField.getText().trim();

        if (fromName == null || toName == null || distStr.isEmpty() || speedStr.isEmpty()) {
            addNotification("⚠️ Tüm alanları doldurun!", "#F44336");
            return;
        }

        try {
            double distance = Double.parseDouble(distStr);
            int speedLimit = Integer.parseInt(speedStr);

            Node from = findNode(fromName);
            Node to = findNode(toName);

            if (from == null || to == null) {
                addNotification("⚠️ Şehirler bulunamadı!", "#F44336");
                return;
            }

            Edge edge1 = new Edge(from, to, distance, speedLimit);
            Edge edge2 = new Edge(to, from, distance, speedLimit);
            map.addEdge(edge1);
            map.addEdge(edge2);

            edgeDistanceField.clear();
            edgeSpeedField.clear();
            refreshAllTables();
            updateComboBoxes();
            addNotification("✅ Çift yönlü yol eklendi: " + fromName + " ↔ " + toName, "#4CAF50");

        } catch (NumberFormatException e) {
            addNotification("⚠️ Geçersiz sayı formatı!", "#F44336");
        }
    }

    private void closeRoad() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null || selected.isEmpty()) {
            addNotification("⚠️ Yol seçin!", "#F44336");
            return;
        }

        String[] parts = selected.split(" → ");
        String from = parts[0];
        String to = parts[1].split(" \\(")[0];

        // ★ COMMAND + OBSERVER PATTERN ★
        facade.setAdminMode(true);
        facade.blockRoad(from, to);

        // Observer otomatik bildirim gönderir!
        // refreshAllTables() Observer içinde yapılıyor
    }

    private void setConstruction() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null || selected.isEmpty()) {
            addNotification("⚠️ Yol seçin!", "#F44336");
            return;
        }

        String[] parts = selected.split(" → ");
        String from = parts[0];
        String to = parts[1].split(" \\(")[0];

        // ★ COMMAND + OBSERVER PATTERN ★
        facade.setAdminMode(true);
        facade.blockRoad(from, to);  // UNDER_CONSTRUCTION yapar

        // Observer otomatik bildirim gönderir!
    }

    private void openRoad() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null || selected.isEmpty()) {
            addNotification("⚠️ Yol seçin!", "#F44336");
            return;
        }

        String[] parts = selected.split(" → ");
        String from = parts[0];
        String to = parts[1].split(" \\(")[0];

        Edge edge = findEdge(from, to);
        if (edge == null) {
            addNotification("⚠️ Yol bulunamadı!", "#F44336");
            return;
        }

        edge.setStatus(EdgeStatus.OPEN);
        refreshAllTables();
        addNotification("✅ Yol açıldı: " + from + " → " + to, "#4CAF50");
    }

    private void deleteEdge() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null || selected.isEmpty()) {
            addNotification("⚠️ Yol seçin!", "#F44336");
            return;
        }

        String[] parts = selected.split(" → ");
        String from = parts[0];
        String to = parts[1].split(" \\(")[0];

        Edge edge1 = findEdge(from, to);
        Edge edge2 = findEdge(to, from);

        if (edge1 != null) map.removeEdge(edge1);
        if (edge2 != null) map.removeEdge(edge2);

        refreshAllTables();
        updateComboBoxes();
        addNotification("🗑️ Yol silindi: " + from + " ↔ " + to, "#9E9E9E");
    }

    private void undoCommand() {
        boolean success = facade.undoLastCommand();
        if (success) {
            refreshAllTables();
            addNotification("↩️ Son işlem geri alındı", "#673AB7");
        } else {
            addNotification("⚠️ Geri alınacak işlem yok!", "#F44336");
        }
    }

    // =========================================================================
    // USER OPERATIONS
    // =========================================================================

    private void calculateRoute() {
        String startName = startCityCombo.getValue();
        String endName = endCityCombo.getValue();

        if (startName == null || endName == null) {
            addNotification("⚠️ Başlangıç ve bitiş şehri seçin!", "#F44336");
            return;
        }

        VehicleType vehicle = VehicleType.CAR;
        if (busRadio.isSelected()) vehicle = VehicleType.BUS;
        else if (walkRadio.isSelected()) vehicle = VehicleType.WALK;

        resultArea.clear();
        resultArea.appendText("═══════════════════════════════════════\n");
        resultArea.appendText("  ROTA HESAPLANIYOR...\n");
        resultArea.appendText("═══════════════════════════════════════\n\n");

        // Facade calculateRoute içinde console'a yazdırıyor
        // Sonucu yakalamak için geçici çözüm:
        facade.calculateRoute(startName, endName, vehicle);

        resultArea.appendText("\n✓ Hesaplama tamamlandı!\n");
        resultArea.appendText("Detaylar console'da görüntülenir.\n");

        addNotification("🔍 Rota hesaplandı: " + startName + " → " + endName, "#2196F3");
    }

    // =========================================================================
    // HELPER METHODS
    // =========================================================================

    private void refreshAllTables() {
        refreshAdminNodeTable();
        refreshAdminEdgeTable();
        refreshUserNodeTable();
        refreshUserEdgeTable();
        updateComboBoxes();
    }

    private void refreshAdminNodeTable() {
        ObservableList<NodeDisplay> data = FXCollections.observableArrayList();
        int id = 1;
        for (Node node : map.getNodes()) {
            data.add(new NodeDisplay(id++, node.getName(), node.getType().toString()));
        }
        adminNodeTable.setItems(data);
    }

    private void refreshAdminEdgeTable() {
        ObservableList<EdgeDisplay> data = FXCollections.observableArrayList();
        for (Edge edge : map.getEdges()) {
            String route = edge.getSource().getName() + " → " + edge.getDestination().getName();
            String distance = edge.getDistance() + " km";
            String speed = edge.getSpeedLimit() + " km/h";
            String status = getStatusText(edge.getStatus());
            data.add(new EdgeDisplay(route, distance, speed, status));
        }
        adminEdgeTable.setItems(data);
    }

    private void refreshUserNodeTable() {
        ObservableList<NodeDisplay> data = FXCollections.observableArrayList();
        int id = 1;
        for (Node node : map.getNodes()) {
            data.add(new NodeDisplay(id++, node.getName(), node.getType().toString()));
        }
        userNodeTable.setItems(data);
    }

    private void refreshUserEdgeTable() {
        ObservableList<EdgeDisplay> data = FXCollections.observableArrayList();
        for (Edge edge : map.getEdges()) {
            String route = edge.getSource().getName() + " → " + edge.getDestination().getName();
            String distance = edge.getDistance() + " km";
            String speed = edge.getSpeedLimit() + " km/h";
            String status = getStatusText(edge.getStatus());
            data.add(new EdgeDisplay(route, distance, speed, status));
        }
        userEdgeTable.setItems(data);
    }

    private void updateComboBoxes() {
        ObservableList<String> nodeNames = FXCollections.observableArrayList();
        for (Node node : map.getNodes()) {
            nodeNames.add(node.getName());
        }

        edgeFromCombo.setItems(nodeNames);
        edgeToCombo.setItems(nodeNames);
        startCityCombo.setItems(nodeNames);
        endCityCombo.setItems(nodeNames);

        ObservableList<String> edgeNames = FXCollections.observableArrayList();
        for (Edge edge : map.getEdges()) {
            String name = edge.getSource().getName() + " → " + edge.getDestination().getName() +
                    " (" + edge.getDistance() + " km)";
            edgeNames.add(name);
        }
        manageEdgeCombo.setItems(edgeNames);
    }

    private void addNotification(String message, String color) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Label notifLabel = new Label("⏰ " + timestamp + " - " + message);
        notifLabel.setStyle("-fx-padding: 5; -fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-background-radius: 3; -fx-font-size: 11;");
        notifLabel.setMaxWidth(Double.MAX_VALUE);

        notificationBox.getChildren().add(0, notifLabel);

        // Max 10 bildirim
        if (notificationBox.getChildren().size() > 10) {
            notificationBox.getChildren().remove(10, notificationBox.getChildren().size());
        }
    }

    private String getStatusText(EdgeStatus status) {
        switch (status) {
            case OPEN: return "✅ AÇIK";
            case CLOSED: return "🚫 KAPALI";
            case UNDER_CONSTRUCTION: return "🚧 TADİLATTA";
            default: return status.toString();
        }
    }

    private Node findNode(String name) {
        for (Node node : map.getNodes()) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    private Edge findEdge(String fromName, String toName) {
        Node from = findNode(fromName);
        Node to = findNode(toName);
        if (from == null || to == null) return null;

        for (Edge edge : map.getEdges()) {
            if (edge.getSource().equals(from) && edge.getDestination().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    // =========================================================================
    // ★ OBSERVER PATTERN IMPLEMENTATION ★
    // =========================================================================

    /**
     * Observer Pattern - Yol durumu değiştiğinde otomatik çağrılır
     * Command pattern ile entegre çalışır
     */
    @Override
    public void onRoadStatusChanged(Edge edge, String message) {
        // JavaFX UI thread'inde çalıştır
        Platform.runLater(() -> {
            // Bildirim göster (OTOMATİK!)
            String routeInfo = edge.getSource().getName() + " → " + edge.getDestination().getName();
            addNotification("🚧 " + message + " (" + routeInfo + ")", "#FF9800");

            // Tabloları güncelle
            refreshAllTables();
        });
    }

    /**
     * Observer Pattern - Hız limiti değiştiğinde otomatik çağrılır
     */
    @Override
    public void onSpeedLimitChanged(Edge edge, int oldLimit, int newLimit) {
        // JavaFX UI thread'inde çalıştır
        Platform.runLater(() -> {
            // Bildirim göster (OTOMATİK!)
            String routeInfo = edge.getSource().getName() + " → " + edge.getDestination().getName();
            addNotification("⚡ Hız limiti değişti: " + oldLimit + " → " + newLimit + " km/h (" + routeInfo + ")", "#2196F3");

            // Tabloları güncelle
            refreshAllTables();
        });
    }

    // =========================================================================
    // INNER CLASSES - TABLE DATA MODELS
    // =========================================================================

    public static class NodeDisplay {
        private int id;
        private String name;
        private String type;

        public NodeDisplay(int id, String name, String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getType() { return type; }
    }

    public static class EdgeDisplay {
        private String route;
        private String distance;
        private String speed;
        private String status;

        public EdgeDisplay(String route, String distance, String speed, String status) {
            this.route = route;
            this.distance = distance;
            this.speed = speed;
            this.status = status;
        }

        public String getRoute() { return route; }
        public String getDistance() { return distance; }
        public String getSpeed() { return speed; }
        public String getStatus() { return status; }
    }
}