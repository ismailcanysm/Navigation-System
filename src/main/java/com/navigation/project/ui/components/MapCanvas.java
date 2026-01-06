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
 * MapCanvas - Harita Çizim Komponenti
 *
 * Graph yapısını görselleştirir:
 * - Şehirler (Node) → Mavi daireler
 * - Yollar (Edge) → Renkli çizgiler
 * - Kapalı yollar → Kırmızı
 * - Normal yollar → Gri
 * - Seçili rota → Yeşil
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

    /**
     * Şehirlerin canvas üzerindeki konumlarını belirle
     * Graph yapısı: İstanbul ve Ankara üstte, Bursa ve İzmir altta
     */
    private void initializeCityPositions() {
        // Üst satır
        cityPositions.put("İstanbul", new Point(150, 100));
        cityPositions.put("Ankara", new Point(450, 100));

        // Alt satır
        cityPositions.put("Bursa", new Point(150, 300));
        cityPositions.put("İzmir", new Point(450, 300));
    }

    /**
     * Haritayı çiz
     */
    public void drawMap(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        redraw();
    }

    /**
     * Rotayı vurgula (yeşil)
     */
    public void highlightRoute(List<Node> route) {
        this.highlightedRoute = route;
        redraw();
    }

    /**
     * Vurgulamayı temizle
     */
    public void clearHighlight() {
        this.highlightedRoute.clear();
        redraw();
    }

    /**
     * Canvas'ı yeniden çiz
     */
    private void redraw() {
        GraphicsContext gc = getGraphicsContext2D();

        // 1. Arka planı temizle
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        // 2. Grid çiz (opsiyonel)
        drawGrid(gc);

        // 3. Tüm yolları çiz (graph edges)
        drawAllEdges(gc);

        // 4. Vurgulanan rotayı çiz (seçili rota - YEŞİL)
        drawHighlightedRoute(gc);

        // 5. Şehirleri çiz (graph nodes)
        drawNodes(gc);

        // 6. Legend çiz (açıklama)
        drawLegend(gc);
    }

    /**
     * Grid çiz (arka plan)
     */
    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.rgb(240, 240, 240));
        gc.setLineWidth(0.5);

        // Dikey çizgiler
        for (int i = 0; i < getWidth(); i += 50) {
            gc.strokeLine(i, 0, i, getHeight());
        }

        // Yatay çizgiler
        for (int i = 0; i < getHeight(); i += 50) {
            gc.strokeLine(0, i, getWidth(), i);
        }
    }

    /**
     * TÜM YOLLARI ÇİZ (Graph Edges)
     * Kapalı yollar → KIRMIZI
     * Normal yollar → GRİ
     */
    private void drawAllEdges(GraphicsContext gc) {
        for (Edge edge : edges) {
            Point p1 = cityPositions.get(edge.getSource().getName());
            Point p2 = cityPositions.get(edge.getDestination().getName());

            if (p1 != null && p2 != null) {
                // Yol durumuna göre renk ve kalınlık belirle
                if (edge.getStatus() == EdgeStatus.CLOSED ||
                        edge.getStatus() == EdgeStatus.UNDER_CONSTRUCTION) {
                    // KAPALI YOL → KIRMIZI
                    gc.setStroke(Color.RED);
                    gc.setLineWidth(4);
                } else {
                    // NORMAL YOL → GRİ
                    gc.setStroke(Color.DARKGRAY);
                    gc.setLineWidth(3);
                }

                // Çizgiyi çiz
                gc.strokeLine(p1.x, p1.y, p2.x, p2.y);

                // Yol bilgilerini göster (mesafe, hız)
                drawEdgeInfo(gc, edge, p1, p2);
            }
        }
    }

    /**
     * Yol bilgilerini göster (mesafe, hız)
     */
    private void drawEdgeInfo(GraphicsContext gc, Edge edge, Point p1, Point p2) {
        double midX = (p1.x + p2.x) / 2;
        double midY = (p1.y + p2.y) / 2;

        // Bilgi metni
        String info = String.format("%.0f km, %d km/h",
                edge.getDistance(), edge.getSpeedLimit());

        // Arka plan (beyaz kutu)
        gc.setFill(Color.WHITE);
        gc.fillRect(midX - 35, midY - 12, 70, 16);

        // Metin
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        gc.fillText(info, midX - 30, midY);

        // Durum göstergesi (kapalıysa)
        if (edge.getStatus() == EdgeStatus.CLOSED) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("🚧 KAPALI", midX - 30, midY + 15);
        } else if (edge.getStatus() == EdgeStatus.UNDER_CONSTRUCTION) {
            gc.setFill(Color.ORANGE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("⚠️ İNŞAAT", midX - 30, midY + 15);
        }
    }

    /**
     * VURGULANAN ROTAYI ÇİZ (Seçili Rota - YEŞİL)
     */
    private void drawHighlightedRoute(GraphicsContext gc) {
        if (highlightedRoute.size() < 2) return;

        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(6);

        double totalDistance = 0;

        // Rota çizgilerini çiz
        for (int i = 0; i < highlightedRoute.size() - 1; i++) {
            Point p1 = cityPositions.get(highlightedRoute.get(i).getName());
            Point p2 = cityPositions.get(highlightedRoute.get(i + 1).getName());

            if (p1 != null && p2 != null) {
                // Yeşil kalın çizgi
                gc.strokeLine(p1.x, p1.y, p2.x, p2.y);

                // Mesafeyi hesapla
                for (Edge edge : edges) {
                    if (edge.getSource().equals(highlightedRoute.get(i)) &&
                            edge.getDestination().equals(highlightedRoute.get(i + 1))) {
                        totalDistance += edge.getDistance();
                        break;
                    }
                }
            }
        }

        // START ve END işaretleri
        drawRouteMarkers(gc);

        // Toplam mesafe göster
        if (totalDistance > 0) {
            gc.setFill(Color.rgb(0, 150, 0));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            gc.fillText(String.format("📍 Rota: %.1f km", totalDistance), 10, getHeight() - 40);
        }
    }

    /**
     * START ve END işaretleri
     */
    private void drawRouteMarkers(GraphicsContext gc) {
        if (highlightedRoute.isEmpty()) return;

        Point start = cityPositions.get(highlightedRoute.get(0).getName());
        Point end = cityPositions.get(highlightedRoute.get(highlightedRoute.size() - 1).getName());

        // START - Yeşil
        if (start != null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(start.x - 12, start.y - 12, 24, 24);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("START", start.x - 16, start.y + 30);
        }

        // END - Kırmızı
        if (end != null) {
            gc.setFill(Color.RED);
            gc.fillOval(end.x - 12, end.y - 12, 24, 24);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            gc.fillText("END", end.x - 12, end.y + 30);
        }
    }

    /**
     * ŞEHİRLERİ ÇİZ (Graph Nodes)
     */
    private void drawNodes(GraphicsContext gc) {
        for (Node node : nodes) {
            Point p = cityPositions.get(node.getName());

            if (p != null) {
                // Dış çember (gölge)
                gc.setFill(Color.rgb(70, 130, 180, 0.3));
                gc.fillOval(p.x - 22, p.y - 22, 44, 44);

                // Ana daire
                gc.setFill(Color.STEELBLUE);
                gc.fillOval(p.x - 18, p.y - 18, 36, 36);

                // Çerçeve
                gc.setStroke(Color.DARKBLUE);
                gc.setLineWidth(3);
                gc.strokeOval(p.x - 18, p.y - 18, 36, 36);

                // Şehir adı (üstte)
                gc.setFill(Color.BLACK);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                double textWidth = node.getName().length() * 7;
                gc.fillText(node.getName(), p.x - textWidth / 2, p.y - 25);
            }
        }
    }

    /**
     * LEGEND ÇİZ (Açıklama)
     */
    private void drawLegend(GraphicsContext gc) {
        double x = 10;
        double y = 20;

        // Arka plan
        gc.setFill(Color.rgb(255, 255, 255, 0.9));
        gc.fillRect(x, y, 180, 100);
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, 180, 100);

        // Başlık
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gc.fillText("🗺️ LEGEND", x + 10, y + 20);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 11));

        // Normal yol
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x + 10, y + 30, 20, 3);
        gc.setFill(Color.BLACK);
        gc.fillText("Normal Yol", x + 35, y + 35);

        // Kapalı yol
        gc.setFill(Color.RED);
        gc.fillRect(x + 10, y + 45, 20, 3);
        gc.setFill(Color.BLACK);
        gc.fillText("Kapalı Yol", x + 35, y + 50);

        // Seçili rota
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(x + 10, y + 60, 20, 4);
        gc.setFill(Color.BLACK);
        gc.fillText("Seçili Rota", x + 35, y + 65);

        // Şehir
        gc.setFill(Color.STEELBLUE);
        gc.fillOval(x + 15, y + 75, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Şehir (Node)", x + 35, y + 82);
    }

    /**
     * Yardımcı Point sınıfı
     */
    private static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}