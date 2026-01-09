package com.navigation.project.backend.factory;

import com.navigation.project.backend.model.VehicleType;
import com.navigation.project.backend.strategy.IRouteStrategy;
import com.navigation.project.backend.template.BusTrip;
import com.navigation.project.backend.template.CarTrip;
import com.navigation.project.backend.template.TripAlgorithm;
import com.navigation.project.backend.template.WalkTrip;

/**
 * Araç tipine göre farklı nesneler oluşturmamıza yarar
 * Template ile bağlantılı çalışarak bu işlevi gerçekleştirir
 * Templeate oluşturulacak nesnelerin iskeletini oluşturur
 */

// [PATTERN: Factory Method]
// İstemci (com.navigation.project.backend.Main veya Facade), "new CarTrip()" demek zorunda kalmaz.
// Sadece tipi söyler (CAR), fabrika ona hazır nesneyi verir.
public class TripFactory {

    // Statik Yaratıcı Metot
    public static TripAlgorithm createTrip(VehicleType type, IRouteStrategy strategy) {
        if (type == null) {
            throw new IllegalArgumentException("Araç tipi boş olamaz!");
        }

        switch (type) {
            case CAR:
                // Araba nesnesi üret
                return new CarTrip(strategy);
            case BUS:
                // Otobüs nesnesi üret
                return new BusTrip(strategy);
            case WALK:
                // Yürüyüş nesnesi üret (Senin eklediğin)
                return new WalkTrip(strategy);
            default:
                throw new IllegalArgumentException("Desteklenmeyen araç tipi: " + type);
        }
    }
}