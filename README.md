# 🗺️ Navigation System - 9 Design Patterns Implementation

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-success.svg)]()
[![Patterns](https://img.shields.io/badge/Design_Patterns-9-blue.svg)]()

[English](#english) | [Türkçe](#turkish)

---

<a name="english"></a>
## 📖 English

### 🎯 Project Overview

A comprehensive **Navigation System** application demonstrating the practical implementation of **9 Design Patterns** in a real-world scenario. The system calculates optimal routes between cities with support for multiple vehicle types (Car, Bus, Walk) and includes an admin panel for dynamic road management.

### ✨ Key Features

- 🚗 **Multi-Vehicle Route Calculation**: Car, Bus, and Walking routes with different speed calculations
- 🗺️ **Dynamic Map Management**: Add/remove cities and roads in real-time
- 🚧 **Road Status Control**: Block roads, set construction zones, manage traffic
- ⏮️ **Undo/Redo System**: Reversible operations with command history
- 📊 **Real-time Notifications**: Observer-based notification system
- 👨‍💼 **Admin Panel**: Role-based access control with proxy pattern
- 🎨 **Modern UI**: JavaFX-based tabbed interface with tables and forms

### 🏗️ Design Patterns Implemented

| # | Pattern | Purpose | Implementation |
|---|---------|---------|----------------|
| 1️⃣ | **Singleton** | Single map instance | `CityMap.getInstance()` |
| 2️⃣ | **Builder** | Fluent map construction | `MapBuilder.addNode().addRoad()` |
| 3️⃣ | **Factory** | Vehicle-specific trip creation | `TripFactory.createTrip()` |
| 4️⃣ | **Strategy** | Swappable routing algorithms | `DijkstraStrategy` |
| 5️⃣ | **Template Method** | Common trip execution flow | `TripAlgorithm.executeTrip()` |
| 6️⃣ | **Command** | Undoable operations | `BlockRoadCommand` |
| 7️⃣ | **Observer** | Auto-notifications | `TrafficNotifier` |
| 8️⃣ | **Proxy** | Access control | `MapManagerProxy` |
| 9️⃣ | **Facade** | Simplified API | `NavigationFacade` |

### 🛠️ Technologies

- **Language**: Java 17
- **UI Framework**: JavaFX 21
- **Build Tool**: Maven
- **Architecture**: MVC Pattern
- **Algorithm**: Dijkstra's Shortest Path

### 📁 Project Structure

```
navigation-system/
├── src/main/java/com/navigation/project/
│   ├── backend/
│   │   ├── builder/           # Builder Pattern
│   │   ├── command/           # Command Pattern
│   │   ├── data/              # Singleton Pattern
│   │   ├── facade/            # Facade Pattern
│   │   ├── factory/           # Factory Pattern
│   │   ├── model/             # Data Models
│   │   ├── observer/          # Observer Pattern
│   │   ├── proxy/             # Proxy Pattern
│   │   ├── strategy/          # Strategy Pattern
│   │   └── template/          # Template Method Pattern
│   ├── ui/                    # JavaFX UI Components
│   └── Main.java              # Console Demo
├── docs/                      # Documentation
├── pom.xml                    # Maven Configuration
├── LICENSE                    # MIT License
└── README.md                  # This file
```

### 🚀 Installation & Setup

#### Prerequisites

- Java 17 or higher
- Maven 3.6+
- JavaFX 21 (included in pom.xml)

#### Steps

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/navigation-system.git
cd navigation-system
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**

**JavaFX UI:**
```bash
mvn javafx:run
```

**Console Demo:**
```bash
java -cp target/classes com.navigation.project.Main
```

### 💡 Usage Examples

#### 1. Route Calculation
```java
NavigationFacade facade = new NavigationFacade();
facade.calculateRoute("Istanbul", "Ankara", VehicleType.CAR);
```

#### 2. Map Building
```java
new MapBuilder()
    .addNode("Istanbul", NodeType.CITY)
    .addNode("Ankara", NodeType.CITY)
    .addRoad("Istanbul", "Ankara", 450, 120)
    .build();
```

#### 3. Road Management
```java
facade.setAdminMode(true);
facade.blockRoad("Istanbul", "Ankara");  // Block road
facade.undoLastCommand();                 // Undo
```

#### 4. Observer System
```java
facade.addObserver(new TrafficObserver() {
    @Override
    public void onRoadStatusChanged(Edge edge, String message) {
        System.out.println("Notification: " + message);
    }
});
```

### 📊 Algorithm Details

**Dijkstra's Algorithm** with vehicle-specific optimizations:

- **Car**: Uses road speed limit
- **Bus**: Max 80 km/h, +30 min per segment (stops)
- **Walk**: Fixed 5 km/h

**Complexity**: O((V+E) log V)
- V: Number of nodes (cities)
- E: Number of edges (roads)

### 🧪 Testing

Run all patterns demo:
```bash
java com.navigation.project.Main
```

Expected output:
```
=== NAVIGATION SYSTEM - 9 DESIGN PATTERNS DEMO ===

PATTERN 1: SINGLETON
✓ map1 == map2: true

PATTERN 2: BUILDER
✓ 4 cities, 8 roads added

PATTERN 4: STRATEGY
[Dijkstra] Route calculated: Istanbul → Izmir
✓ Dijkstra algorithm executed

...

9/9 Patterns successfully executed!
```

### 📚 Documentation

Detailed documentation available in `/docs` folder:

- `PATTERN_DETAYLI_ACIKLAMA.md` - Comprehensive pattern explanations (Turkish)
- `SUNUM_NOTU.md` - Presentation notes
- `KOD_HARITASI_DETAYLI.md` - Detailed code map

### 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### 👨‍💻 Author

- GitHub: [@yourusername](https://github.com/ismailcanysm)
- GitHub: [@yourusername](https://github.com/veysel-47)

### 🙏 Acknowledgments

- Design Patterns: Gang of Four (GoF)
- Algorithm: Edsger W. Dijkstra
- UI Framework: OpenJFX Community

### 📈 Future Enhancements

- [ ] A* Algorithm implementation
- [ ] Real-time traffic data integration
- [ ] GPS coordinates support
- [ ] Multi-language support
- [ ] Database persistence
- [ ] REST API
- [ ] Mobile app (React Native)

---

<a name="turkish"></a>
## 📖 Türkçe

### 🎯 Proje Hakkında

Gerçek dünya senaryosunda **9 Design Pattern'in** pratik uygulamasını gösteren kapsamlı bir **Navigasyon Sistemi** uygulaması. Sistem, şehirler arası optimal rotalar hesaplar ve birden fazla araç tipini (Araba, Otobüs, Yürüyüş) destekler. Dinamik yol yönetimi için admin paneli içerir.

### ✨ Özellikler

- 🚗 **Çoklu Araç Rota Hesaplama**: Araba, Otobüs ve Yürüyüş rotaları farklı hız hesaplamalarıyla
- 🗺️ **Dinamik Harita Yönetimi**: Gerçek zamanlı şehir ve yol ekleme/silme
- 🚧 **Yol Durumu Kontrolü**: Yolları kapatma, tadilat bölgesi ayarlama, trafik yönetimi
- ⏮️ **Geri Al/İleri Al Sistemi**: Komut geçmişi ile geri alınabilir işlemler
- 📊 **Gerçek Zamanlı Bildirimler**: Observer tabanlı bildirim sistemi
- 👨‍💼 **Admin Paneli**: Proxy pattern ile rol tabanlı erişim kontrolü
- 🎨 **Modern UI**: JavaFX tabanlı sekmeli arayüz, tablolar ve formlar

### 🏗️ Uygulanan Tasarım Kalıpları

| # | Kalıp | Amaç | Uygulama |
|---|-------|------|----------|
| 1️⃣ | **Singleton** | Tek harita instance'ı | `CityMap.getInstance()` |
| 2️⃣ | **Builder** | Akıcı harita oluşturma | `MapBuilder.addNode().addRoad()` |
| 3️⃣ | **Factory** | Araca özel yolculuk üretimi | `TripFactory.createTrip()` |
| 4️⃣ | **Strategy** | Değiştirilebilir algoritma | `DijkstraStrategy` |
| 5️⃣ | **Template Method** | Ortak yolculuk akışı | `TripAlgorithm.executeTrip()` |
| 6️⃣ | **Command** | Geri alınabilir işlemler | `BlockRoadCommand` |
| 7️⃣ | **Observer** | Otomatik bildirimler | `TrafficNotifier` |
| 8️⃣ | **Proxy** | Erişim kontrolü | `MapManagerProxy` |
| 9️⃣ | **Facade** | Basitleştirilmiş API | `NavigationFacade` |

### 🛠️ Teknolojiler

- **Dil**: Java 17
- **UI Framework**: JavaFX 21
- **Build Aracı**: Maven
- **Mimari**: MVC Pattern
- **Algoritma**: Dijkstra En Kısa Yol

### 🚀 Kurulum

#### Gereksinimler

- Java 17 veya üzeri
- Maven 3.6+
- JavaFX 21 (pom.xml'de dahil)

#### Adımlar

1. **Projeyi klonlayın**
```bash
git clone https://github.com/kullaniciadi/navigation-system.git
cd navigation-system
```

2. **Projeyi derleyin**
```bash
mvn clean install
```

3. **Uygulamayı çalıştırın**

**JavaFX UI:**
```bash
mvn javafx:run
```

**Konsol Demo:**
```bash
java -cp target/classes com.navigation.project.Main
```

### 💡 Kullanım Örnekleri

#### 1. Rota Hesaplama
```java
NavigationFacade facade = new NavigationFacade();
facade.calculateRoute("İstanbul", "Ankara", VehicleType.CAR);
```

#### 2. Harita Oluşturma
```java
new MapBuilder()
    .addNode("İstanbul", NodeType.CITY)
    .addNode("Ankara", NodeType.CITY)
    .addRoad("İstanbul", "Ankara", 450, 120)
    .build();
```

#### 3. Yol Yönetimi
```java
facade.setAdminMode(true);
facade.blockRoad("İstanbul", "Ankara");  // Yolu kapat
facade.undoLastCommand();                 // Geri al
```

### 📊 Algoritma Detayları

**Dijkstra Algoritması** araç tipine göre optimizasyonlarla:

- **Araba**: Yol hız limitini kullanır
- **Otobüs**: Max 80 km/s, segment başına +30 dk (duraklar)
- **Yürüyüş**: Sabit 5 km/s

**Karmaşıklık**: O((V+E) log V)

### 🤝 Katkıda Bulunma

Katkılar memnuniyetle karşılanır! Lütfen Pull Request göndermekten çekinmeyin.

### 📝 Lisans

Bu proje MIT Lisansı altında lisanslanmıştır - detaylar için [LICENSE](LICENSE) dosyasına bakın.

### 👨‍💻 Geliştirici

**Adınız**
- GitHub: [@kullaniciadi](https://github.com/ismailcanysm)
- GitHub: [@kullaniciadi](https://github.com/veysel-47)

### 📈 Gelecek Geliştirmeler

- [ ] A* Algoritması implementasyonu
- [ ] Gerçek zamanlı trafik verisi entegrasyonu
- [ ] GPS koordinat desteği
- [ ] Çoklu dil desteği
- [ ] Veritabanı kalıcılığı
- [ ] REST API
- [ ] Mobil uygulama (React Native)

---

<div align="center">

**Made with ❤️ and ☕**

If you found this project helpful, please consider giving it a ⭐!

</div>
