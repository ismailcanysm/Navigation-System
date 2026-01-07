package com.navigation.project.ui.controller;

import com.navigation.project.backend.data.CityMap;
import com.navigation.project.backend.facade.NavigationFacade;
import com.navigation.project.backend.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * MainController - SENARYO UYUMLU
 * @author Kişi 2
 */
public class MainController {

    private BorderPane root;
    private NavigationFacade facade;
    private CityMap cityMap;

    private TableView<NodeDisplay> adminNodeTable;
    private TableView<EdgeDisplay> adminEdgeTable;
    private TextField nodeNameField;
    private ComboBox<String> edgeSourceCombo;
    private ComboBox<String> edgeDestCombo;
    private TextField edgeDistanceField;
    private TextField edgeSpeedField;
    private ComboBox<String> manageEdgeCombo;

    private TableView<NodeDisplay> userNodeTable;
    private TableView<EdgeDisplay> userEdgeTable;
    private ComboBox<String> startCityCombo;
    private ComboBox<String> endCityCombo;
    private RadioButton carRadio, busRadio, walkRadio;
    private TextArea resultArea;

    private VBox notificationBox;

    public MainController() {
        initializeBackend();
        createUI();
    }

    private void initializeBackend() {
        facade = new NavigationFacade();
        cityMap = CityMap.getInstance();
        cityMap.clearAll();
        System.out.println("[MainController] Boş sistem başlatıldı.");
    }

    private void createUI() {
        root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(createTabPane());
        root.setBottom(createNotificationPanel());
    }

    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        header.setAlignment(Pos.CENTER);

        Label title = new Label("🗺️ NAVİGASYON SİSTEMİ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("Dinamik Node & Edge Yönetimi - 10 Design Pattern Demo");
        subtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #ecf0f1;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab adminTab = new Tab("🔒 ADMİN PANELİ");
        adminTab.setContent(createAdminPanel());

        Tab userTab = new Tab("👤 KULLANICI PANELİ");
        userTab.setContent(createUserPanel());

        tabPane.getTabs().addAll(adminTab, userTab);
        return tabPane;
    }

    private VBox createAdminPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: #ecf0f1;");

        Label nodeLabel = new Label("📍 ŞEHİRLER (NODES)");
        nodeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        adminNodeTable = createNodeTable();
        adminNodeTable.setPrefHeight(150);

        HBox addNodeBox = new HBox(10);
        addNodeBox.setAlignment(Pos.CENTER_LEFT);
        nodeNameField = new TextField();
        nodeNameField.setPromptText("Şehir adı...");
        nodeNameField.setPrefWidth(200);
        Button addNodeBtn = new Button("➕ ŞEHİR EKLE");
        addNodeBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        addNodeBtn.setOnAction(e -> addNode());
        addNodeBox.getChildren().addAll(new Label("Şehir Adı:"), nodeNameField, addNodeBtn);

        Separator separator1 = new Separator();

        Label edgeLabel = new Label("🛣️ YOLLAR (EDGES)");
        edgeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        adminEdgeTable = createEdgeTable();
        adminEdgeTable.setPrefHeight(150);

        HBox addEdgeBox = new HBox(10);
        addEdgeBox.setAlignment(Pos.CENTER_LEFT);
        edgeSourceCombo = new ComboBox<>();
        edgeSourceCombo.setPromptText("Kaynak");
        edgeSourceCombo.setPrefWidth(120);
        edgeDestCombo = new ComboBox<>();
        edgeDestCombo.setPromptText("Hedef");
        edgeDestCombo.setPrefWidth(120);
        edgeDistanceField = new TextField();
        edgeDistanceField.setPromptText("km");
        edgeDistanceField.setPrefWidth(60);
        edgeSpeedField = new TextField();
        edgeSpeedField.setPromptText("km/h");
        edgeSpeedField.setPrefWidth(60);
        Button addEdgeBtn = new Button("➕ YOL EKLE");
        addEdgeBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        addEdgeBtn.setOnAction(e -> addEdge());
        addEdgeBox.getChildren().addAll(
                new Label("Kaynak:"), edgeSourceCombo,
                new Label("→ Hedef:"), edgeDestCombo,
                new Label("Mesafe:"), edgeDistanceField,
                new Label("Hız:"), edgeSpeedField,
                addEdgeBtn
        );

        Separator separator2 = new Separator();

        Label manageLabel = new Label("⚙️ YOL YÖNETİMİ");
        manageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox manageEdgeBox = new HBox(10);
        manageEdgeBox.setAlignment(Pos.CENTER_LEFT);
        manageEdgeCombo = new ComboBox<>();
        manageEdgeCombo.setPromptText("Yol seçin...");
        manageEdgeCombo.setPrefWidth(250);

        Button closedBtn = new Button("🚫 KAPAT");
        closedBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;");
        closedBtn.setOnAction(e -> closeRoad());

        Button constructionBtn = new Button("🚧 TADİLATTA");
        constructionBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        constructionBtn.setOnAction(e -> setConstruction());

        Button openBtn = new Button("✅ AÇ");
        openBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        openBtn.setOnAction(e -> openRoad());

        Button deleteBtn = new Button("🗑️ SİL");
        deleteBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteBtn.setOnAction(e -> deleteEdge());

        manageEdgeBox.getChildren().addAll(
                new Label("Yol Seç:"), manageEdgeCombo,
                closedBtn, constructionBtn, openBtn, deleteBtn
        );

        panel.getChildren().addAll(
                nodeLabel, adminNodeTable, addNodeBox,
                separator1,
                edgeLabel, adminEdgeTable, addEdgeBox,
                separator2,
                manageLabel, manageEdgeBox
        );

        return panel;
    }

    private VBox createUserPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: #ecf0f1;");

        Label nodeLabel = new Label("📍 MEVCUT ŞEHİRLER");
        nodeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        userNodeTable = createNodeTable();
        userNodeTable.setPrefHeight(120);

        Label edgeLabel = new Label("🛣️ MEVCUT YOLLAR");
        edgeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        userEdgeTable = createEdgeTable();
        userEdgeTable.setPrefHeight(120);

        Label routeLabel = new Label("🚗 ROTA HESAPLA");
        routeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox routeBox = new HBox(10);
        routeBox.setAlignment(Pos.CENTER_LEFT);
        startCityCombo = new ComboBox<>();
        startCityCombo.setPromptText("Başlangıç");
        startCityCombo.setPrefWidth(150);
        endCityCombo = new ComboBox<>();
        endCityCombo.setPromptText("Bitiş");
        endCityCombo.setPrefWidth(150);

        ToggleGroup vehicleGroup = new ToggleGroup();
        carRadio = new RadioButton("🚗 Araba");
        carRadio.setToggleGroup(vehicleGroup);
        carRadio.setSelected(true);
        busRadio = new RadioButton("🚌 Otobüs");
        busRadio.setToggleGroup(vehicleGroup);
        walkRadio = new RadioButton("🚶 Yürüyüş");
        walkRadio.setToggleGroup(vehicleGroup);

        Button calculateBtn = new Button("📍 ROTA HESAPLA");
        calculateBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");
        calculateBtn.setOnAction(e -> calculateRoute());

        routeBox.getChildren().addAll(
                new Label("Başlangıç:"), startCityCombo,
                new Label("Bitiş:"), endCityCombo,
                carRadio, busRadio, walkRadio,
                calculateBtn
        );

        Label resultLabel = new Label("📊 ROTA SONUCU");
        resultLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(150);
        resultArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        panel.getChildren().addAll(
                nodeLabel, userNodeTable,
                edgeLabel, userEdgeTable,
                new Separator(),
                routeLabel, routeBox,
                resultLabel, resultArea
        );

        return panel;
    }

    private ScrollPane createNotificationPanel() {
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefHeight(100);
        scroll.setFitToWidth(true);

        notificationBox = new VBox(5);
        notificationBox.setPadding(new Insets(10));
        notificationBox.setStyle("-fx-background-color: #34495e;");

        Label title = new Label("📢 BİLDİRİMLER");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        notificationBox.getChildren().add(title);
        scroll.setContent(notificationBox);

        return scroll;
    }

    private TableView<NodeDisplay> createNodeTable() {
        TableView<NodeDisplay> table = new TableView<>();
        TableColumn<NodeDisplay, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        TableColumn<NodeDisplay, String> nameCol = new TableColumn<>("Şehir Adı");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        table.getColumns().addAll(idCol, nameCol);
        return table;
    }

    private TableView<EdgeDisplay> createEdgeTable() {
        TableView<EdgeDisplay> table = new TableView<>();
        TableColumn<EdgeDisplay, String> routeCol = new TableColumn<>("Rota");
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        routeCol.setPrefWidth(200);
        TableColumn<EdgeDisplay, Double> distCol = new TableColumn<>("Mesafe (km)");
        distCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distCol.setPrefWidth(100);
        TableColumn<EdgeDisplay, Integer> speedCol = new TableColumn<>("Hız (km/h)");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        speedCol.setPrefWidth(100);
        TableColumn<EdgeDisplay, String> statusCol = new TableColumn<>("Durum");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);
        table.getColumns().addAll(routeCol, distCol, speedCol, statusCol);
        return table;
    }

    private void addNode() {
        String name = nodeNameField.getText().trim();
        if (name.isEmpty()) {
            addNotification("❌ Şehir adı boş olamaz!", "#e74c3c");
            return;
        }
        Node newNode = new Node(name, NodeType.CITY);
        cityMap.addNode(newNode);
        nodeNameField.clear();
        refreshAllTables();
        addNotification("✅ Şehir eklendi: " + name, "#27ae60");
    }

    private void addEdge() {
        String source = edgeSourceCombo.getValue();
        String dest = edgeDestCombo.getValue();
        String distStr = edgeDistanceField.getText().trim();
        String speedStr = edgeSpeedField.getText().trim();

        if (source == null || dest == null || distStr.isEmpty() || speedStr.isEmpty()) {
            addNotification("❌ Tüm alanları doldurun!", "#e74c3c");
            return;
        }
        if (source.equals(dest)) {
            addNotification("❌ Kaynak ve hedef aynı olamaz!", "#e74c3c");
            return;
        }

        try {
            double distance = Double.parseDouble(distStr);
            int speed = Integer.parseInt(speedStr);
            Node srcNode = findNode(source);
            Node destNode = findNode(dest);
            if (srcNode == null || destNode == null) {
                addNotification("❌ Şehirler bulunamadı!", "#e74c3c");
                return;
            }
            Edge edge1 = new Edge(srcNode, destNode, distance, speed);
            Edge edge2 = new Edge(destNode, srcNode, distance, speed);
            cityMap.addEdge(edge1);
            cityMap.addEdge(edge2);
            edgeDistanceField.clear();
            edgeSpeedField.clear();
            refreshAllTables();
            addNotification("✅ Yol eklendi: " + source + " ↔ " + dest, "#27ae60");
        } catch (NumberFormatException e) {
            addNotification("❌ Mesafe ve hız sayı olmalı!", "#e74c3c");
        }
    }

    private void closeRoad() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null) {
            addNotification("❌ Yol seçin!", "#e74c3c");
            return;
        }
        String[] parts = selected.split(" → ");
        Edge edge = findEdge(parts[0], parts[1]);
        if (edge != null) {
            edge.setStatus(EdgeStatus.CLOSED);
            refreshAllTables();
            addNotification("🚫 Yol kapatıldı: " + selected, "#c0392b");
        }
    }

    private void setConstruction() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null) {
            addNotification("❌ Yol seçin!", "#e74c3c");
            return;
        }
        String[] parts = selected.split(" → ");
        Edge edge = findEdge(parts[0], parts[1]);
        if (edge != null) {
            edge.setStatus(EdgeStatus.UNDER_CONSTRUCTION);
            refreshAllTables();
            addNotification("🚧 Yol tadilatta: " + selected, "#f39c12");
        }
    }

    private void openRoad() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null) {
            addNotification("❌ Yol seçin!", "#e74c3c");
            return;
        }
        String[] parts = selected.split(" → ");
        Edge edge = findEdge(parts[0], parts[1]);
        if (edge != null) {
            edge.setStatus(EdgeStatus.OPEN);
            refreshAllTables();
            addNotification("✅ Yol açıldı: " + selected, "#27ae60");
        }
    }

    private void deleteEdge() {
        String selected = manageEdgeCombo.getValue();
        if (selected == null) {
            addNotification("❌ Yol seçin!", "#e74c3c");
            return;
        }
        String[] parts = selected.split(" → ");
        Edge edge = findEdge(parts[0], parts[1]);
        if (edge != null) {
            cityMap.removeEdge(edge);
            refreshAllTables();
            addNotification("🗑️ Yol silindi: " + selected, "#95a5a6");
        }
    }

    private void calculateRoute() {
        String start = startCityCombo.getValue();
        String end = endCityCombo.getValue();

        if (start == null || end == null) {
            addNotification("❌ Başlangıç ve bitiş seçin!", "#e74c3c");
            return;
        }
        if (start.equals(end)) {
            addNotification("❌ Başlangıç ve bitiş aynı olamaz!", "#e74c3c");
            return;
        }

        VehicleType vehicle = VehicleType.CAR;
        if (busRadio.isSelected()) vehicle = VehicleType.BUS;
        if (walkRadio.isSelected()) vehicle = VehicleType.WALK;

        Node startNode = findNode(start);
        Node endNode = findNode(end);
        if (startNode == null || endNode == null) {
            addNotification("❌ Şehirler bulunamadı!", "#e74c3c");
            return;
        }

        com.navigation.project.backend.strategy.DijkstraStrategy strategy =
                new com.navigation.project.backend.strategy.DijkstraStrategy();
        com.navigation.project.backend.strategy.RouteCalculationResult result =
                strategy.calculateRoute(startNode, endNode, vehicle);

        if (result.getPath() == null || result.getPath().isEmpty()) {
            resultArea.setText("❌ ROTA BULUNAMADI!\n\nBu iki şehir arasında açık yol yok.");
            addNotification("❌ Rota bulunamadı!", "#e74c3c");
            return;
        }

        double distance = result.getTotalDistance();
        double duration = result.getTotalDuration();

        StringBuilder sb = new StringBuilder();
        sb.append("✅ ROTA BULUNDU!\n\n");
        sb.append("Başlangıç: ").append(start).append("\n");
        sb.append("Bitiş: ").append(end).append("\n");
        sb.append("Araç: ").append(getVehicleName(vehicle)).append("\n\n");
        sb.append("ROTA:\n");

        List<Node> path = result.getPath();
        for (int i = 0; i < path.size(); i++) {
            sb.append((i + 1)).append(". ").append(path.get(i).getName());
            if (i < path.size() - 1) sb.append(" → ");
        }

        sb.append("\n\n");
        sb.append("═══════════════════════════════\n");
        sb.append("📏 Toplam Mesafe: ").append(String.format("%.1f km", distance)).append("\n");
        sb.append("⏱️  Tahmini Süre: ").append(formatTime(duration)).append("\n");
        sb.append("═══════════════════════════════\n");

        resultArea.setText(sb.toString());
        addNotification("✅ Rota hesaplandı: " + String.format("%.1f km, %s", distance, formatTime(duration)), "#27ae60");
    }

    private String formatTime(double minutes) {
        int hours = (int) (minutes / 60);
        int mins = (int) (minutes % 60);
        if (hours > 0) {
            return String.format("%d saat %d dk", hours, mins);
        } else {
            return String.format("%d dk", mins);
        }
    }

    private String getVehicleName(VehicleType vehicle) {
        switch (vehicle) {
            case CAR: return "🚗 Araba";
            case BUS: return "🚌 Otobüs";
            case WALK: return "🚶 Yürüyüş";
            default: return "Bilinmiyor";
        }
    }

    private void refreshAllTables() {
        ObservableList<NodeDisplay> nodeList = FXCollections.observableArrayList();
        int id = 1;
        for (Node node : cityMap.getNodes()) {
            nodeList.add(new NodeDisplay(id++, node.getName()));
        }
        adminNodeTable.setItems(nodeList);
        userNodeTable.setItems(nodeList);

        ObservableList<EdgeDisplay> edgeList = FXCollections.observableArrayList();
        for (Edge edge : cityMap.getEdges()) {
            String route = edge.getSource().getName() + " → " + edge.getDestination().getName();
            String status;
            switch (edge.getStatus()) {
                case OPEN: status = "✅ Açık"; break;
                case CLOSED: status = "🚫 Kapalı"; break;
                case UNDER_CONSTRUCTION: status = "🚧 Tadilatta"; break;
                default: status = "❓ Bilinmiyor";
            }
            edgeList.add(new EdgeDisplay(route, edge.getDistance(), edge.getSpeedLimit(), status));
        }
        adminEdgeTable.setItems(edgeList);
        userEdgeTable.setItems(edgeList);
        updateComboBoxes();
    }

    private void updateComboBoxes() {
        ObservableList<String> cities = FXCollections.observableArrayList();
        for (Node node : cityMap.getNodes()) {
            cities.add(node.getName());
        }
        edgeSourceCombo.setItems(cities);
        edgeDestCombo.setItems(cities);
        startCityCombo.setItems(cities);
        endCityCombo.setItems(cities);

        ObservableList<String> edges = FXCollections.observableArrayList();
        for (Edge edge : cityMap.getEdges()) {
            String route = edge.getSource().getName() + " → " + edge.getDestination().getName();
            if (!edges.contains(route)) edges.add(route);
        }
        manageEdgeCombo.setItems(edges);
    }

    private Node findNode(String name) {
        for (Node node : cityMap.getNodes()) {
            if (node.getName().equals(name)) return node;
        }
        return null;
    }

    private Edge findEdge(String from, String to) {
        Node fromNode = findNode(from);
        Node toNode = findNode(to);
        if (fromNode == null || toNode == null) return null;
        for (Edge edge : cityMap.getEdges()) {
            if (edge.getSource().equals(fromNode) && edge.getDestination().equals(toNode)) {
                return edge;
            }
        }
        return null;
    }

    private void addNotification(String message, String color) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Label notif = new Label("[" + time + "] " + message);
        notif.setStyle("-fx-text-fill: white; -fx-padding: 5; -fx-background-color: " + color + "; -fx-background-radius: 5;");
        notificationBox.getChildren().add(notif);
        if (notificationBox.getChildren().size() > 11) {
            notificationBox.getChildren().remove(1);
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public static class NodeDisplay {
        private final int id;
        private final String name;
        public NodeDisplay(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class EdgeDisplay {
        private final String route;
        private final double distance;
        private final int speed;
        private final String status;
        public EdgeDisplay(String route, double distance, int speed, String status) {
            this.route = route;
            this.distance = distance;
            this.speed = speed;
            this.status = status;
        }
        public String getRoute() { return route; }
        public double getDistance() { return distance; }
        public int getSpeed() { return speed; }
        public String getStatus() { return status; }
    }
}