package com.navigation.project.backend.model;

/**
 * EdgeStatus - Yol Durumu Enum
 *
 * AMAÇ:
 * Yol durumlarını tanımlar ve rota hesaplamasında kullanılır.
 *
 * NE İŞE YARAR:
 * - Yolun kullanılabilirliğini belirtir
 * - Dijkstra algoritması sadece OPEN yolları kullanır
 * - Admin yönetim panelinde görüntülenir
 *
 * DEĞERLER:
 * - OPEN: Yol açık ve kullanılabilir
 * - CLOSED: Yol tamamen kapalı
 * - UNDER_CONSTRUCTION: Yolda çalışma/tadilat var
 *
 * KULLANIM:
 * if (edge.getStatus() == EdgeStatus.OPEN) {
 *     // Yol kullanılabilir
 * }
 */

public enum EdgeStatus {
    OPEN,                   // Yol açık
    CLOSED,                 // Yol kapalı (Admin kapattı)
    UNDER_CONSTRUCTION      // İnşaat halinde
}
