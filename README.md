
# ✈️ Uçak Bilet Rezervasyon Sistemi

Bu proje, bir havayolu şirketi için **bilet yönetimi süreçlerini dijitalleştirmek** amacıyla geliştirilmiş, **JavaFX tabanlı bir masaüstü uygulamasıdır**. Kullanıcı dostu arayüzü sayesinde **bilet ekleme, listeleme, güncelleme ve silme (CRUD)** işlemleri kolaylıkla gerçekleştirilebilir.

---

## 🖼️ Uygulama Ekran Görüntüleri

![WhatsApp Image 2025-12-29 at 15 36 02 (5)](https://github.com/user-attachments/assets/84ddfcfe-9027-4c5f-a1cf-2a62d0d52a02)
![WhatsApp Image 2025-12-29 at 15 36 02 (4)](https://github.com/user-attachments/assets/f2b5c006-0d8a-47ab-92c7-eda91ddcd22f)
![WhatsApp Image 2025-12-29 at 15 36 02 (3)](https://github.com/user-attachments/assets/81f3eda0-c1f7-4cf5-8a29-c62d45f451e0)
![WhatsApp Image 2025-12-29 at 15 36 02 (2)](https://github.com/user-attachments/assets/7d73a366-285b-407f-970f-58c8b4b14bb2)
![WhatsApp Image 2025-12-29 at 15 36 02 (1)](https://github.com/user-attachments/assets/ad4b9091-5ac8-4428-a59a-9a209e509b85)
![WhatsApp Image 2025-12-29 at 15 36 01](https://github.com/user-attachments/assets/df8a50a3-bc43-4207-a725-9b279ae8ba86)
![WhatsApp Image 2025-12-29 at 15 36 02](https://github.com/user-attachments/assets/5ff9816a-bca0-4a8e-a115-9670d4e90724)
![WhatsApp Image 2025-12-29 at 15 36 02 (6)](https://github.com/user-attachments/assets/42be0791-3555-41cb-9a5e-2e796f3b4c59)

---

## 🚀 Özellikler

### 🔐 Güvenli Giriş
- Admin yetkilendirmesi ile sisteme güvenli erişim

### 🎫 Bilet Yönetimi (CRUD)
- **Ekleme:** Yolcu bilgileri, uçuş no, koltuk no ve fiyat detaylarıyla bilet kaydı
- **Güncelleme:** Mevcut bilet bilgilerini anlık olarak düzenleme
- **Silme:** Seçili biletleri sistemden kaldırma

### 🔎 Dinamik Sorgulama
- TC Kimlik Numarası veya Uçuş Numarası ile hızlı arama

### 📊 Gelişmiş Sıralama
- Biletleri **tarih, saat, fiyat veya yolcu adına** göre artan/azalan sıralama

### 📝 İşlem Geçmişi (Logging)
- Yapılan her işlemin (ekleme, silme, güncelleme vb.) kaydını tutan takip paneli

### 💾 Veri Kalıcılığı
- Tüm veriler `biletler.txt` dosyasında saklanır
- Uygulama kapansa bile veriler kaybolmaz

### ✅ Girdi Doğrulama
- Hatalı veri girişlerini (örneğin uçuş numarasına harf yazılması) önleyen uyarı mekanizması

---

## 🛠️ Kullanılan Teknolojiler

- **Programlama Dili:** Java 11+
- **Arayüz:** JavaFX (FXML)
- **Stil:** CSS (Modern ve temiz bir UI için özel temalandırma)
- **Veri Yönetimi:** Dosya Giriş/Çıkış (I/O) – `.txt` tabanlı veri saklama
- **Mimari:** MVC (Model – View – Controller)

---

## 📁 Proje Yapısı

```plaintext
├── .idea/                     # IDE ayarları
├── out/                       # Derlenmiş dosyalar
├── BiletEkraniController.java # Uygulamanın ana mantığı ve buton işlevleri
├── Launcher.java              # JavaFX başlatıcı
├── Main.java                  # Uygulama giriş noktası
├── UcakBiletApp.java          # Scene ve Stage yönetimi
├── bilet_ekrani.fxml          # Arayüz tasarımı (FXML)
├── style.css                  # Görsel özelleştirmeler
├── biletler.txt               # Verilerin saklandığı dosya
└── screenshots/               # Uygulama ekran görüntüleri
```

---

## 💻 Kurulum ve Çalıştırma

### 1️⃣ Depoyu Klonlayın

```bash
git clone https://github.com/H-ERDEM/UcakBiletRezervasyonSistemi.git
```

### 2️⃣ IDE ile Açın
- IntelliJ IDEA veya Eclipse kullanarak projeyi içe aktarın

### 3️⃣ SDK Ayarlarını Yapın
- **Java 11 veya üzeri** bir JDK kullandığınızdan emin olun

### 4️⃣ JavaFX Kütüphanesini Ekleyin
- Eğer JavaFX JDK içinde yoksa:
  - JavaFX SDK indirin
  - VM Options içine ekleyin:

```bash
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

### 5️⃣ Çalıştırın
- `Main.java` dosyasını sağ tıklayıp **Run** deyin

---

## 📌 Notlar

- Bu proje **öğrenci ve eğitim amaçlıdır**
- Dosya tabanlı veri yönetimi kullanıldığı için küçük/orta ölçekli uygulamalar için uygundur
- Geliştirilmeye açıktır (veritabanı entegrasyonu, rol bazlı yetkilendirme vb.)

---

## 👤 Geliştirici

**Hayrunnisa Büşra Erdem**  
Bilgisayar Mühendisliği Öğrencisi  

🔗 GitHub: https://github.com/H-ERDEM

---

⭐ Projeyi beğendiyseniz yıldızlamayı unutmayın!

