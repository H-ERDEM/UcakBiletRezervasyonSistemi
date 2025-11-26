import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class BiletEkraniController {

    @FXML private TextField txtAd, txtSoyad, txtTc, txtKoltuk, txtTarih, txtSaat, txtFiyat;
    @FXML private Button btnAdd, btnDelete, btnSave, btnLoad, btnSearch, btnUpdate, btnSort, btnHistory;
    @FXML private TableView<Bilet> tblBiletler;
    @FXML private TableColumn<Bilet, String> colAd, colSoyad, colTc, colKoltuk, colTarih, colSaat, colFiyat;

    private final BiletBST biletAgaci = new BiletBST();
    private final ObservableList<Bilet> biletListesi = FXCollections.observableArrayList();
    private final ArrayList<String> islemGecmisi = new ArrayList<>();

    @FXML
    public void initialize() {

        colAd.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().yolcu.ad));
        colSoyad.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().yolcu.soyad));
        colTc.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().yolcu.tcNo));
        colKoltuk.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().yolcu.koltukNo));
        colTarih.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().tarih));
        colSaat.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().saat));
        colFiyat.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().fiyat)));

        tblBiletler.setItems(biletListesi);

        btnAdd.setOnAction(e -> biletEkle());
        btnDelete.setOnAction(e -> biletSil());
        btnSearch.setOnAction(e -> biletSorgula());
        btnUpdate.setOnAction(e -> biletGuncelle());
        btnSort.setOnAction(e -> sirala());
        btnHistory.setOnAction(e -> gecmisiGoster());
    }

    // ---------------------------------------------------------
    // Bilet Ekle
    // ---------------------------------------------------------
    private void biletEkle() {
        try {
            String ad = txtAd.getText();
            String soyad = txtSoyad.getText();
            String tc = txtTc.getText();
            String koltuk = txtKoltuk.getText();
            String tarih = txtTarih.getText();
            String saat = txtSaat.getText();
            double fiyat = Double.parseDouble(txtFiyat.getText());

            Yolcu y = new Yolcu(ad, soyad, tc, koltuk);
            Bilet b = new Bilet("", tarih, saat, fiyat, y);

            biletAgaci.ekle(b);
            biletListesi.add(b);

            islemGecmisi.add("Bilet eklendi (TC: " + tc + ")");

            temizle();

        } catch (Exception ex) {
            uyari("Hata", "Bilet eklenemedi!");
        }
    }

    // ---------------------------------------------------------
    // Bilet Sil
    // ---------------------------------------------------------
    private void biletSil() {
        Bilet secili = tblBiletler.getSelectionModel().getSelectedItem();
        if (secili != null) {
            biletAgaci.sil(secili.yolcu.tcNo);
            biletListesi.remove(secili);

            islemGecmisi.add("Bilet silindi (TC: " + secili.yolcu.tcNo + ")");
        }
    }

    // ---------------------------------------------------------
    // Bilet Sorgula
    // ---------------------------------------------------------
    private void biletSorgula() {
        String tc = txtTc.getText();
        if (tc.isEmpty()) {
            uyari("Uyarı", "TC No girin!");
            return;
        }

        Bilet sonuc = biletAgaci.ara(tc);
        if (sonuc == null) {
            uyari("Sonuç", "Bu TC ile bilet bulunamadı!");
            return;
        }

        txtAd.setText(sonuc.yolcu.ad);
        txtSoyad.setText(sonuc.yolcu.soyad);
        txtKoltuk.setText(sonuc.yolcu.koltukNo);
        txtTarih.setText(sonuc.tarih);
        txtSaat.setText(sonuc.saat);
        txtFiyat.setText(String.valueOf(sonuc.fiyat));

        islemGecmisi.add("Bilet sorgulandı (TC: " + tc + ")");
    }

    // ---------------------------------------------------------
    // Bilet Güncelle
    // ---------------------------------------------------------
    private void biletGuncelle() {
        String tc = txtTc.getText();
        if (tc.isEmpty()) {
            uyari("Uyarı", "Önce TC No girip sorgulama yap!");
            return;
        }

        biletAgaci.sil(tc);

        // Yeniden ekleme mantığı
        biletEkle();

        islemGecmisi.add("Bilet güncellendi (TC: " + tc + ")");
    }

    // ---------------------------------------------------------
    // Sıralama
    // ---------------------------------------------------------
    private void sirala() {
        FXCollections.sort(biletListesi, (a, b) -> a.tarih.compareTo(b.tarih));

        islemGecmisi.add("Biletler tarihe göre sıralandı.");
    }

    // ---------------------------------------------------------
    // İşlem Geçmişi Göster
    // ---------------------------------------------------------
    private void gecmisiGoster() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("İşlem Geçmişi");
        alert.setHeaderText("Yapılan İşlemler");

        StringBuilder sb = new StringBuilder();
        for (String s : islemGecmisi)
            sb.append(s).append("\n");

        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    // ---------------------------------------------------------
    // Yardımcılar
    // ---------------------------------------------------------
    private void temizle() {
        txtAd.clear();
        txtSoyad.clear();
        txtTc.clear();
        txtKoltuk.clear();
        txtTarih.clear();
        txtSaat.clear();
        txtFiyat.clear();
    }

    private void uyari(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(baslik);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}
