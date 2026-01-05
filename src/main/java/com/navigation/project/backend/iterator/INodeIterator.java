package com.navigation.project.backend.iterator;

import com.navigation.project.backend.model.Node;

/**
 * Burada Node'lar üzerinde gezinme işlemi tanımlanır
 * iç yapıyı gizler
 * Elemenlara sıralı erişim sağlar
 * Farklı gezinme işlemleri uygulanabilir
 */

public interface INodeIterator {
    boolean hasNext(); // eleman var mı kontrolü
    Node next(); // bir sonraki elemana geç
    void reset(); // Iterator'ı başa sar. Koleksiyon baştan tekrar gezilir
}
