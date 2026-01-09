package com.navigation.project.ui.components;

import com.navigation.project.backend.model.Edge;
import com.navigation.project.backend.model.EdgeStatus;
import com.navigation.project.backend.model.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

/**
 * MapCanvas - Harita Canvas Bileşeni (KULLANILMIYOR)
 *
 * AMAÇ:
 * Haritayı görsel olarak çizer (şehirler ve yollar).
 *
 * NOT:
 * Bu sınıf mevcut uygulamada kullanılmıyor.
 * Yerine tab-based TableView sistemi kullanılıyor.
 * Gelecekteki görsel harita özelliği için saklanıyor.
 *
 * NE İŞE YARAR (GELECEKTEKİ KULLANIM):
 * - Canvas üzerinde harita çizer
 * - Şehirleri mavi dairelerle gösterir
 * - Yolları çizgilerle gösterir
 * - Rota vurgulaması yapar (yeşil)
 * - Yol durumlarını renklerle gösterir
 *
 * İLİŞKİLİ SINIFLAR: Node, Edge
 *
 * RENK KODLARI:
 * - OPEN: Gri çizgi
 * - CLOSED: Kırmızı çizgi
 * - UNDER_CONSTRUCTION: Turuncu çizgi
 * - Highlighted Route: Yeşil kalın çizgi
 * - Nodes: Mavi daireler
 */

/**
 * MapCanvas - Harita Çizim Komponenti
 *
 * SENARYO UYUMLU: 3 EdgeStatus gösterimi
 * - OPEN (Açık) → Gri
 * - CLOSED (Kapalı) → Kırmızı
 * - UNDER_CONSTRUCTION (Tadilatta) → Turuncu
 *
 * @author Kişi 2
 */
public class MapCanvas extends Canvas {

    private Map<String, Point> cityPositions;
    private List<Node> nodes;
    private List<Edge> edges;
    private List<Node> highlightedRoute;

    public MapCanvas(double width, double height) {
        super(width, height);
        this.cityPositions = new HashMap<>();
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.highlightedRoute = new ArrayList<>();
        initializeCityPositions();
    }

    private void initializeCityPositions() {
        cityPositions.put("İstanbul", new Point(150, 100));
        cityPositions.put("Ankara", new Point(450, 100));
        cityPositions.put("Bursa", new Point(150, 300));
        cityPositions.put("İzmir", new Point(450, 300));
    }

    public void drawMap(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        redraw();
    }

    public void highlightRoute(List<Node> route) {
        this.highlightedRoute = route;
        redraw();
    }

    public void clearHighlight() {
        this.highlightedRoute.clear();
        redraw();
    }

    private void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        drawGrid(gc);
        drawAllEdges(gc);
        drawHighlightedRoute(gc);
        drawNodes(gc);
        drawLegend(gc);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.rgb(240, 240, 240));
        gc.setLineWidth(0.5);
        for (int i = 0; i < getWidth(); i += 50) {
            gc.strokeLine(i, 0, i, getHeight());
        }
        for (int i = 0; i < getHeight(); i += 50) {
            gc.strokeLine(0, i, getWidth(), i);
        }
    }

    /**
     * TÜM YOLLARI ÇİZ - SENARYO UYUMLU (3 DURUM)
     */
    private void drawAllEdges(GraphicsContext gc) {
        for (Edge edge : edges) {
            Point p1 = cityPositions.get(edge.getSource().getName());
            Point p2 = cityPositions.get(edge.getDestination().getName());

            if (p1 != null && p2 != null) {
                // SENARYO: Yol durumuna göre renk
                switch (edge.getStatus()) {
                    case CLOSED:
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(4);
                        break;
                    case UNDER_CONSTRUCTION:
                        gc.setStroke(Color.ORANGE);
                        gc.setLineWidth(4);
                        break;
                    case OPEN:
                    default:
                        gc.setStroke(Color.DARKGRAY);
                        gc.setLineWidth(3);
                        break;
                }

                gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
                drawEdgeInfo(gc, edge, p1, p2);
            }
        }
    }

    /**
     * Yol bilgilerini göster - SENARYO UYUMLU
     */
    private void drawEdgeInfo(GraphicsContext gc, Edge edge, Point p1, Point p2) {
        double midX = (p1.x + p2.x) / 2;
        double midY = (p1.y + p2.y) / 2;

        String info = String.format("%.0f km, %d km/h", edge.getDistance(), edge.getSpeedLimit());

        gc.setFill(Color.WHITE);
        gc.fillRect(midX - 35, midY - 12, 70, 16);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        gc.fillText(info, midX - 30, midY);

        // SENARYO: Durum göstergesi
        if (edge.getStatus() == EdgeStatus.CLOSED) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("🚫 KAPALI", midX - 30, midY + 15);
        } else if (edge.getStatus() == EdgeStatus.UNDER_CONSTRUCTION) {
            gc.setFill(Color.ORANGE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("🚧 TADİLAT", midX - 30, midY + 15);
        }
    }

    private void drawHighlightedRoute(GraphicsContext gc) {
        if (highlightedRoute.size() < 2) return;

        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(6);

        double totalDistance = 0;

        for (int i = 0; i < highlightedRoute.size() - 1; i++) {
            Point p1 = cityPositions.get(highlightedRoute.get(i).getName());
            Point p2 = cityPositions.get(highlightedRoute.get(i + 1).getName());

            if (p1 != null && p2 != null) {
                gc.strokeLine(p1.x, p1.y, p2.x, p2.y);

                for (Edge edge : edges) {
                    if (edge.getSource().equals(highlightedRoute.get(i)) &&
                            edge.getDestination().equals(highlightedRoute.get(i + 1))) {
                        totalDistance += edge.getDistance();
                        break;
                    }
                }
            }
        }

        drawRouteMarkers(gc);

        if (totalDistance > 0) {
            gc.setFill(Color.rgb(0, 150, 0));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            gc.fillText(String.format("📍 Rota: %.1f km", totalDistance), 10, getHeight() - 40);
        }
    }

    private void drawRouteMarkers(GraphicsContext gc) {
        if (highlightedRoute.isEmpty()) return;

        Point start = cityPositions.get(highlightedRoute.get(0).getName());
        Point end = cityPositions.get(highlightedRoute.get(highlightedRoute.size() - 1).getName());

        if (start != null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(start.x - 12, start.y - 12, 24, 24);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("START", start.x - 16, start.y + 30);
        }

        if (end != null) {
            gc.setFill(Color.RED);
            gc.fillOval(end.x - 12, end.y - 12, 24, 24);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("END", end.x - 12, end.y + 30);
        }
    }

    private void drawNodes(GraphicsContext gc) {
        for (Node node : nodes) {
            Point p = cityPositions.get(node.getName());

            if (p != null) {
                gc.setFill(Color.rgb(70, 130, 180, 0.3));
                gc.fillOval(p.x - 22, p.y - 22, 44, 44);

                gc.setFill(Color.STEELBLUE);
                gc.fillOval(p.x - 18, p.y - 18, 36, 36);

                gc.setStroke(Color.DARKBLUE);
                gc.setLineWidth(3);
                gc.strokeOval(p.x - 18, p.y - 18, 36, 36);

                gc.setFill(Color.BLACK);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                double textWidth = node.getName().length() * 7;
                gc.fillText(node.getName(), p.x - textWidth / 2, p.y - 25);
            }
        }
    }

    /**
     * LEGEND - SENARYO UYUMLU (3 DURUM)
     */
    private void drawLegend(GraphicsContext gc) {
        double x = 10;
        double y = 20;

        gc.setFill(Color.rgb(255, 255, 255, 0.9));
        gc.fillRect(x, y, 180, 110);
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, 180, 110);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gc.fillText("🗺️ LEGEND", x + 10, y + 20);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 11));

        // Normal yol (OPEN)
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x + 10, y + 30, 20, 3);
        gc.setFill(Color.BLACK);
        gc.fillText("Açık Yol", x + 35, y + 35);

        // Kapalı yol (CLOSED)
        gc.setFill(Color.RED);
        gc.fillRect(x + 10, y + 45, 20, 3);
        gc.setFill(Color.BLACK);
        gc.fillText("Kapalı Yol", x + 35, y + 50);

        // Tadilat (UNDER_CONSTRUCTION)
        gc.setFill(Color.ORANGE);
        gc.fillRect(x + 10, y + 60, 20, 3);
        gc.setFill(Color.BLACK);
        gc.fillText("Tadilat", x + 35, y + 65);

        // Seçili rota
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(x + 10, y + 75, 20, 4);
        gc.setFill(Color.BLACK);
        gc.fillText("Seçili Rota", x + 35, y + 80);

        // Şehir
        gc.setFill(Color.STEELBLUE);
        gc.fillOval(x + 15, y + 90, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Şehir (Node)", x + 35, y + 97);
    }

    private static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}