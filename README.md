<img width="1470" height="956" alt="giriş panel" src="https://github.com/user-attachments/assets/5fdb976f-5cb1-4041-840c-618468f8b6a0" />✈️ Uçak Bilet Rezervasyon Sistemi
Bu proje, bir havayolu şirketi için bilet yönetimi süreçlerini dijitalleştirmek amacıyla geliştirilmiş, JavaFX tabanlı bir masaüstü uygulamasıdır. Kullanıcı dostu arayüzü sayesinde bilet ekleme, listeleme, güncelleme ve silme (CRUD) işlemleri kolaylıkla gerçekleştirilebilir.

<img width="1470" height="956" alt="işlem geçmişi" src="https://github.com/user-attachments/assets/bbe472df-bb94-40a7-8c6a-07e1886f3395" />
<img width="1470" height="956" alt="hata mesajı" src="https://github.com/user-attachments/assets/fcfe5094-374a-4c26-a946-e2972b529452" />
<img width="1470" height="956" alt="giriş şifre" src="https://github.com/user-attachments/assets/d23017b4-490b-4777-84cf-17a0373162b4" />
<img width="1470" height="956" alt="bilet ekle güncelle" src="https://github.com/user-attachments/assets/1a9090dc-c0fa-4d21-94ea-0ee0b6ab39e2" />
<img width="1470" height="956" alt="ana ekran" src="https://github.com/user-attachments/assets/cccf6a88-e576-49c8-991a-e408ef869939" /><img width="1470" height="956" alt="bilet sorgulama" src="https://github.com/user-attachments/assets/a378a7fe-2d06-496e-b8f5-46b2f942ae66" />

bi<img width="1470" height="956" alt="bilet listele" src="https://github.com/user-attachments/assets/c5712382-43ff-4870-9c3c-e74f3b89a4bf" />

🚀 Özellikler
Güvenli Giriş: Admin yetkilendirmesi ile sisteme güvenli erişim.

Bilet Yönetimi (CRUD):

Ekleme: Yolcu bilgileri, uçuş no, koltuk no ve fiyat detaylarıyla bilet kaydı.

Güncelleme: Mevcut bilet bilgilerini anlık olarak düzenleme.

Silme: Seçili biletleri sistemden kaldırma.

Dinamik Sorgulama: TC Kimlik Numarası veya Uçuş Numarası ile biletler arasında hızlı arama.

Gelişmiş Sıralama: Biletleri tarih, saat, fiyat veya yolcu adına göre artan/azalan sırada görüntüleme.

İşlem Geçmişi (Logging): Yapılan her işlemin (ekleme, silme vb.) kaydını tutan takip paneli.

Veri Kalıcılığı: Tüm veriler biletler.txt dosyasında saklanır; uygulama kapansa bile veriler kaybolmaz.

Girdi Doğrulama: Hatalı veri girişlerini (örneğin uçuş numarasına harf yazılması) önleyen uyarı mekanizması.

🛠️ Kullanılan Teknolojiler
Dil: Java 11+

Arayüz: JavaFX (FXML)

Stil: CSS (Modern ve temiz bir UI için özel temalandırma)

Veri Yönetimi: Dosya Giriş/Çıkış (I/O) İşlemleri (.txt tabanlı veritabanı mantığı)

Mimari: MVC (Model-View-Controller) prensiplerine uygun yapı.

📁 Proje Yapısı
Plaintext

├── .idea/                  # IDE ayarları
├── out/                    # Derlenmiş dosyalar
├── BiletEkraniController.java # Uygulamanın ana mantığı ve buton işlevleri
├── Launcher.java           # JavaFX başlatıcı
├── Main.java               # Uygulama giriş noktası
├── UcakBiletApp.java       # Sahne (Scene) ve Stage yönetimi
├── bilet_ekrani.fxml       # Arayüz tasarımı (XML formatında)
├── style.css               # Görsel özelleştirmeler
└── biletler.txt            # Verilerin saklandığı dosya
💻 Kurulum ve Çalıştırma
Depoyu Klonlayın:

Bash

git clone https://github.com/H-ERDEM/UcakBiletRezervasyonSistemi.git
IDE ile Açın: IntelliJ IDEA veya Eclipse kullanarak projeyi içe aktarın.

SDK Ayarlarını Yapın: Java 11 veya üzeri bir JDK kullandığınızdan emin olun.

JavaFX Kütüphanesini Ekleyin: Eğer SDK içinde yoksa, JavaFX kütüphanelerini projenize dahil edin.

Çalıştırın: Main.java dosyasını sağ tıklayıp "Run" deyin.
