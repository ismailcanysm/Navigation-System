package com.navigation.project.backend.iterator;

import com.navigation.project.backend.model.Node;

/**
 * INodeIterator - Iterator Pattern Interface
 *
 * AMAÇ:
 * Node koleksiyonunu iç yapısından bağımsız şekilde gezinme yeteneği sağlar.
 *
 * NE İŞE YARAR:
 * - Koleksiyonun iç yapısını gizler
 * - Standart gezinme ara yüzü sunar
 * - List mi Array mi bilmeye gerek kalmaz
 *
 * PATTERN: Iterator Pattern
 * IMPLEMENTASYONLAR: GraphIterator
 *
 * GEREKLI METODLAR:
 * - hasNext(): Sonraki eleman var mı?
 * - next(): Sonraki elemana geç ve döner
 * - reset(): İteratoru başa sarar
 */

public interface INodeIterator {
    boolean hasNext(); // eleman var mı kontrolü
    Node next(); // bir sonraki elemana geç
    void reset(); // Iterator'ı başa sar. Koleksiyon baştan tekrar gezilir
}
