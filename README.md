[ucak_bilet_rezervasyon_sistemi_readme.md](https://github.com/user-attachments/files/24368254/ucak_bilet_rezervasyon_sistemi_readme.md)
# ✈️ Uçak Bilet Rezervasyon Sistemi

Bu proje, bir havayolu şirketi için **bilet yönetimi süreçlerini dijitalleştirmek** amacıyla geliştirilmiş, **JavaFX tabanlı bir masaüstü uygulamasıdır**. Kullanıcı dostu arayüzü sayesinde **bilet ekleme, listeleme, güncelleme ve silme (CRUD)** işlemleri kolaylıkla gerçekleştirilebilir.

---

## 🖼️ Uygulama Ekran Görüntüleri

Aşağıda projeye ait örnek ekran görüntülerini ekleyebilirsiniz. Görselleri `screenshots/` klasörü altına koymanız önerilir.

```
📁 screenshots/
├── login.png
├── dashboard.png
├── ticket_add.png
├── ticket_list.png
├── ticket_update.png
├── search.png
├── sorting.png
└── logs.png
```

Markdown kullanım örneği:

```md
![Login Screen](screenshots/login.png)
![Dashboard](screenshots/dashboard.png)
![Ticket Add](screenshots/ticket_add.png)
![Ticket List](screenshots/ticket_list.png)
![Ticket Update](screenshots/ticket_update.png)
![Search](screenshots/search.png)
![Sorting](screenshots/sorting.png)
![Logs](screenshots/logs.png)
```

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

