import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class BiletEkraniController {

    @FXML private StackPane rootStack;
    @FXML private Pane loginPane, menuPane, eklePane, siralaPane, sorgulaPane, listePane, gecmisPane;

    // Login
    @FXML private TextField txtLoginKullanici;
    @FXML private PasswordField txtLoginSifre;
    @FXML private Label lblLoginHata;

    // Mesaj kutusu
    @FXML private Label lblMesaj;

    // Ekle formu
    @FXML private TextField txtAdEkle, txtSoyadEkle, txtTcEkle,
            txtUcusNoEkle, txtKoltukEkle, txtTarihEkle, txtSaatEkle, txtFiyatEkle;

    // Sıralama
    @FXML private ChoiceBox<String> cbSiralaKriter;

    // Sorgu
    @FXML private RadioButton rbSorguTc, rbSorguUcusNo;
    @FXML private ToggleGroup sorguGrup;
    @FXML private TextField txtSorguDeger;
    @FXML private TextArea taSorguSonuc;

    // Liste / tablo
    @FXML private TableView<Bilet> tblBiletler;
    @FXML private TableColumn<Bilet, String> colAd, colSoyad, colTc, colUcusNo, colKoltuk, colTarih, colSaat, colFiyat;

    // Geçmiş
    @FXML private TextArea taGecmis;

    // Veri yapıları
    private final ObservableList<Bilet> biletListesi = FXCollections.observableArrayList();
    private final BiletBST biletAgaci = new BiletBST();
    private final ArrayList<String> islemGecmisi = new ArrayList<>();

    // Güncellenecek seçili bilet referansı
    private Bilet guncellenenBilet = null;

    // Dosya adı
    private final String DOSYA_ADI = "biletler.txt";

    @FXML
    public void initialize() {
        // Tablo kolonları
        colAd.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().yolcu.ad));
        colSoyad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().yolcu.soyad));
        colTc.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().yolcu.tcNo));
        colUcusNo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().ucusNo));
        colKoltuk.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().yolcu.koltukNo));
        colTarih.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().tarih));
        colSaat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().saat));
        colFiyat.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().fiyat)));

        tblBiletler.setItems(biletListesi);

        // Sıralama kriterleri
        cbSiralaKriter.getItems().addAll("Tarihe göre", "Saate göre", "Fiyata göre");
        cbSiralaKriter.getSelectionModel().selectFirst();

        // Dosyadan yükleme
        yukleBiletler();

        // Başlangıç ekranı: login
        showPane(loginPane);
        clearMessage();
    }

    // -------------------- GENEL YARDIMCILAR -------------------- //

    private void showPane(Pane pane) {
        for (Node n : rootStack.getChildren()) {
            n.setVisible(false);
            n.setManaged(false);
        }
        pane.setVisible(true);
        pane.setManaged(true);
        clearMessage();
    }

    private void showError(String msg) {
        if (lblMesaj != null) {
            lblMesaj.getStyleClass().setAll("message-text", "error-message");
            lblMesaj.setText(msg);
        }
    }

    private void showInfo(String msg) {
        if (lblMesaj != null) {
            lblMesaj.getStyleClass().setAll("message-text", "info-message");
            lblMesaj.setText(msg);
        }
    }

    private void clearMessage() {
        if (lblMesaj != null) {
            lblMesaj.getStyleClass().setAll("message-text");
            lblMesaj.setText("");
        }
    }

    private boolean koltukDoluMu(String ucusNo, String koltuk) {
        for (Bilet b : biletListesi) {
            if (b.ucusNo.equals(ucusNo) && b.yolcu.koltukNo.equalsIgnoreCase(koltuk)) {
                return true;
            }
        }
        return false;
    }

    // -------------------- EKRAN GEÇİŞLERİ -------------------- //

    @FXML private void goMenu() { showPane(menuPane); }
    @FXML private void goEkle() { guncellenenBilet = null; temizleEkleFormu(); showPane(eklePane); }
    @FXML private void goSirala() { showPane(siralaPane); }
    @FXML private void goSorgula() { taSorguSonuc.clear(); txtSorguDeger.clear(); rbSorguTc.setSelected(true); showPane(sorgulaPane); }
    @FXML private void goListe() { tblBiletler.refresh(); showPane(listePane); }
    @FXML private void goGecmis() {
        StringBuilder sb = new StringBuilder();
        for (String s : islemGecmisi) sb.append(s).append("\n");
        taGecmis.setText(sb.toString());
        showPane(gecmisPane);
    }
    @FXML private void handleCikis() {
        txtLoginKullanici.clear();
        txtLoginSifre.clear();
        lblLoginHata.setText("");
        showPane(loginPane);
    }

    // -------------------- LOGIN -------------------- //
    @FXML private void handleLogin() {
        String k = txtLoginKullanici.getText().trim();
        String s = txtLoginSifre.getText().trim();
        if (k.equals("admin") && s.equals("1234")) {
            lblLoginHata.setText("");
            showPane(menuPane);
            showInfo("Hoş geldin, " + k + "!");
        } else {
            lblLoginHata.setText("Kullanıcı adı veya şifre hatalı!");
        }
    }

    // -------------------- BİLET EKLE / GÜNCELLE -------------------- //
    @FXML
    private void handleBiletEkle() {
        try {
            String ad = txtAdEkle.getText().trim();
            String soyad = txtSoyadEkle.getText().trim();
            String tc = txtTcEkle.getText().trim();
            String ucusNo = txtUcusNoEkle.getText().trim();
            String koltuk = txtKoltukEkle.getText().trim();
            String tarih = txtTarihEkle.getText().trim();
            String saat = txtSaatEkle.getText().trim();
            String fiyatStr = txtFiyatEkle.getText().trim();

            // Kontroller (Main fonksiyonlarını kullanabilirsiniz)
            if (!Main.isimKontrol(ad)) { showError("Hatalı ad!"); return; }
            if (!Main.isimKontrol(soyad)) { showError("Hatalı soyad!"); return; }
            if (!Main.tcKontrol(tc)) { showError("Hatalı TC!"); return; }
            if (!Main.ucusNoKontrol(ucusNo)) { showError("Hatalı uçuş numarası!"); return; }
            if (!Main.koltukKontrol(koltuk)) { showError("Hatalı koltuk!"); return; }
            if (koltukDoluMu(ucusNo, koltuk) && guncellenenBilet == null) { showError("Bu koltuk dolu!"); return; }
            if (!Main.tarihKontrol(tarih)) { showError("Hatalı tarih!"); return; }
            if (!Main.saatKontrol(saat)) { showError("Hatalı saat!"); return; }

            double fiyat = Double.parseDouble(fiyatStr);
            if (fiyat < 0) { showError("Negatif fiyat olamaz!"); return; }

            if (guncellenenBilet == null) {
                Yolcu y = new Yolcu(ad, soyad, tc, koltuk);
                Bilet b = new Bilet(ucusNo, tarih, saat, fiyat, y);
                biletListesi.add(b);
                biletAgaci.ekle(b);
                islemGecmisi.add("Bilet eklendi -> " + b);
                showInfo("Bilet başarıyla eklendi.");
            } else {
                biletAgaci.sil(guncellenenBilet.yolcu.tcNo);
                guncellenenBilet.yolcu.ad = ad;
                guncellenenBilet.yolcu.soyad = soyad;
                guncellenenBilet.yolcu.tcNo = tc;
                guncellenenBilet.yolcu.koltukNo = koltuk;
                guncellenenBilet.ucusNo = ucusNo;
                guncellenenBilet.tarih = tarih;
                guncellenenBilet.saat = saat;
                guncellenenBilet.fiyat = fiyat;
                biletAgaci.ekle(guncellenenBilet);
                tblBiletler.refresh();
                islemGecmisi.add("Bilet güncellendi -> " + guncellenenBilet);
                showInfo("Bilet başarıyla güncellendi.");
                guncellenenBilet = null;
            }

            temizleEkleFormu();
            kaydetBiletler(); // değişiklik sonrası kaydet

        } catch (Exception ex) {
            showError("Bilet eklenemedi/güncellenemedi: " + ex.getMessage());
        }
    }

    private void temizleEkleFormu() {
        txtAdEkle.clear(); txtSoyadEkle.clear(); txtTcEkle.clear();
        txtUcusNoEkle.clear(); txtKoltukEkle.clear(); txtTarihEkle.clear();
        txtSaatEkle.clear(); txtFiyatEkle.clear();
    }

    @FXML
    private void handleListeSil() {
        Bilet secili = tblBiletler.getSelectionModel().getSelectedItem();
        if (secili == null) { showError("Lütfen bilet seçin."); return; }

        biletListesi.remove(secili);
        biletAgaci.sil(secili.yolcu.tcNo);
        islemGecmisi.add("Bilet silindi -> " + secili);
        showInfo("Bilet silindi.");
        kaydetBiletler(); // değişiklik sonrası kaydet
    }

    @FXML
    private void handleListeGuncelle() {
        Bilet secili = tblBiletler.getSelectionModel().getSelectedItem();
        if (secili == null) { showError("Önce bilet seçin."); return; }

        guncellenenBilet = secili;
        txtAdEkle.setText(secili.yolcu.ad);
        txtSoyadEkle.setText(secili.yolcu.soyad);
        txtTcEkle.setText(secili.yolcu.tcNo);
        txtUcusNoEkle.setText(secili.ucusNo);
        txtKoltukEkle.setText(secili.yolcu.koltukNo);
        txtTarihEkle.setText(secili.tarih);
        txtSaatEkle.setText(secili.saat);
        txtFiyatEkle.setText(String.valueOf(secili.fiyat));

        showPane(eklePane);
        showInfo("Seçili bilet düzenleme için yüklendi.");
    }

    // -------------------- SIRALAMA -------------------- //
    @FXML
    private void handleSirala() {
        String kriter = cbSiralaKriter.getSelectionModel().getSelectedItem();
        if (kriter == null) { showError("Önce kriter seçin."); return; }

        switch (kriter) {
            case "Tarihe göre" -> {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                biletListesi.sort(Comparator.comparing(b -> LocalDate.parse(b.tarih, f)));
            }
            case "Saate göre" -> { biletListesi.sort(Comparator.comparing(b -> b.saat)); }
            case "Fiyata göre" -> { biletListesi.sort(Comparator.comparingDouble(b -> b.fiyat)); }
        }

        islemGecmisi.add("Biletler \"" + kriter + "\" sırasına göre sıralandı.");
        showInfo("Biletler " + kriter + " sırasına göre sıralandı.");
        tblBiletler.refresh();
    }

    // -------------------- SORGULAMA -------------------- //
    @FXML
    private void handleSorgula() {
        String deger = txtSorguDeger.getText().trim();
        if (deger.isEmpty()) { showError("Arama değeri boş olamaz."); return; }

        StringBuilder sb = new StringBuilder();
        if (rbSorguTc.isSelected()) {
            Bilet b = biletAgaci.ara(deger);
            if (b != null) sb.append(b.toString());
            else sb.append("Bu TC'ye ait bilet bulunamadı.");
            islemGecmisi.add("Bilet sorgulama (TC) -> " + deger);
        } else {
            boolean bulundu = false;
            for (Bilet b : biletListesi) {
                if (b.ucusNo.equals(deger)) { sb.append(b.toString()).append("\n"); bulundu = true; }
            }
            if (!bulundu) sb.append("Bu uçuş numarasına ait bilet bulunamadı.");
            islemGecmisi.add("Bilet sorgulama (Uçuş no) -> " + deger);
        }
        taSorguSonuc.setText(sb.toString());
        showInfo("Sorgulama tamamlandı.");
    }

    // -------------------- DOSYA İŞLEMLERİ -------------------- //
    private void kaydetBiletler() {
        try (PrintWriter pw = new PrintWriter(DOSYA_ADI)) {
            for (Bilet b : biletListesi) {
                pw.println(b.yolcu.ad + ";" + b.yolcu.soyad + ";" + b.yolcu.tcNo + ";" +
                        b.yolcu.koltukNo + ";" + b.ucusNo + ";" + b.tarih + ";" +
                        b.saat + ";" + b.fiyat);
            }
        } catch (Exception e) { showError("Kaydetme hatası: " + e.getMessage()); }
    }

    private void yukleBiletler() {
        File f = new File(DOSYA_ADI);
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(";");
                if (parts.length != 8) continue;
                Yolcu y = new Yolcu(parts[0], parts[1], parts[2], parts[3]);
                Bilet b = new Bilet(parts[4], parts[5], parts[6], Double.parseDouble(parts[7]), y);
                biletListesi.add(b);
                biletAgaci.ekle(b);
            }
        } catch (Exception e) {
            showError("Bilet yükleme hatası: " + e.getMessage());
        }
    }
}
