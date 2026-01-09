package com.navigation.project.backend.model;

/**
 * VehicleType - Araç Tipi Enum
 *
 * AMAÇ:
 * Araç tiplerini tanımlar ve Factory pattern'de kullanılır.
 *
 * NE İŞE YARAR:
 * - TripFactory'de doğru Trip nesnesini üretir
 * - Süre hesaplamasında farklı hızlar kullanılır
 * - Her araç tipi farklı maliyet hesaplama yapar
 *
 * DEĞERLER:
 * - CAR: Araba (hız limiti kullanır, yakıt maliyeti)
 * - BUS: Otobüs (max 80 km/h, +15 dk durak, sabit bilet)
 * - WALK: Yürüyüş (5 km/h sabit, kalori hesabı, bedava)
 *
 * KULLANIM:
 * TripAlgorithm trip = TripFactory.createTrip(VehicleType.CAR, strategy);
 */

public enum VehicleType {
    CAR,
    BUS,
    WALK
}
