import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Yolcu {
    String ad, soyad, tcNo, koltukNo;
    public Yolcu(String ad, String soyad, String tcNo, String koltukNo) {
        this.ad = ad; this.soyad = soyad; this.tcNo = tcNo; this.koltukNo = koltukNo;
    }
    @Override
    public String toString() {
        return ", Yolcu: " + ad + " " + soyad + ", TC: " + tcNo + ", Koltuk: " + koltukNo;
    }
}

class Bilet {
    String ucusNo, tarih, saat;
    double fiyat;
    Yolcu yolcu;
    public Bilet(String ucusNo, String tarih, String saat, double fiyat, Yolcu yolcu) {
        this.ucusNo = ucusNo; this.tarih = tarih; this.saat = saat; this.fiyat = fiyat; this.yolcu = yolcu;
    }
    @Override
    public String toString() {
        return "Bilet - Uçuş Numarası: " + ucusNo + ", Tarih: " + tarih + ", Saat: " + saat + ", Fiyat: " + fiyat + yolcu;
    }
}

// BST Node
class BSTNode {
    String tcNo;
    Bilet bilet;
    BSTNode sol, sag;
    public BSTNode(Bilet bilet) { this.tcNo = bilet.yolcu.tcNo; this.bilet = bilet; sol = sag = null; }
}

// BST
class BiletBST {
    BSTNode root;
    public void ekle(Bilet b) { root = ekleRec(root, b); }
    private BSTNode ekleRec(BSTNode node, Bilet b) {
        if(node==null) return new BSTNode(b);
        if(b.yolcu.tcNo.compareTo(node.tcNo)<0) node.sol = ekleRec(node.sol, b);
        else if(b.yolcu.tcNo.compareTo(node.tcNo)>0) node.sag = ekleRec(node.sag, b);
        else node.bilet = b;
        return node;
    }
    public Bilet ara(String tc) { return araRec(root, tc); }
    private Bilet araRec(BSTNode node, String tc) {
        if(node==null) return null;
        if(tc.equals(node.tcNo)) return node.bilet;
        if(tc.compareTo(node.tcNo)<0) return araRec(node.sol, tc);
        return araRec(node.sag, tc);
    }
    public void sil(String tc) { root = silRec(root, tc); }
    private BSTNode silRec(BSTNode node, String tc) {
        if(node==null) return null;
        if(tc.compareTo(node.tcNo)<0) node.sol = silRec(node.sol, tc);
        else if(tc.compareTo(node.tcNo)>0) node.sag = silRec(node.sag, tc);
        else {
            if(node.sol==null) return node.sag;
            if(node.sag==null) return node.sol;
            BSTNode minNode = minValueNode(node.sag);
            node.tcNo = minNode.tcNo; node.bilet = minNode.bilet;
            node.sag = silRec(node.sag, minNode.tcNo);
        }
        return node;
    }
    private BSTNode minValueNode(BSTNode node) {
        BSTNode current = node; while(current.sol!=null) current=current.sol; return current;
    }
}

public class Main {
    static List<Bilet> biletListesi = new LinkedList<>();
    static Stack<String> islemGecmisi = new Stack<>();
    static BiletBST bst = new BiletBST();
    static Scanner scanner = new Scanner(System.in);
    static String veriDosyasi = "veritabani.txt";

    public static void main(String[] args) {
        dosyadanYukle();
        int secim;
        do {
            System.out.println("\nUçak Bilet Rezervasyon Sistemi");
            System.out.println("1. Bilet Ekle");
            System.out.println("2. Bilet Sil");
            System.out.println("3. Bilet Güncelle");
            System.out.println("4. Biletleri Listele");
            System.out.println("5. Biletleri Sırala");
            System.out.println("6. Bilet Sorgula");
            System.out.println("7. İşlem Geçmişini Göster");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            if(scanner.hasNextInt()){ secim=scanner.nextInt(); scanner.nextLine(); }
            else { System.out.println("Geçersiz giriş!"); scanner.nextLine(); secim=-1; continue; }

            switch(secim){
                case 1->biletEkle();
                case 2->biletSil();
                case 3->biletGuncelle();
                case 4->{ listele(); islemGecmisi.push("Biletler listelendi"); }
                case 5->{ sirala(); islemGecmisi.push("Biletler sıralandı"); }
                case 6->{ sorgula(); islemGecmisi.push("Bilet sorgulama yapıldı"); }
                case 7->islemGecmisiniGoster();
                case 0->System.out.println("Programdan çıkılıyor...");
                default->System.out.println("Geçersiz seçim!");
            }
        }while(secim!=0);
        dosyayaKaydet();
    }

    // Dosya işlemleri
    public static void dosyadanYukle(){
        File file = new File(veriDosyasi);
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String satir;
            while((satir=br.readLine())!=null){
                String[] d = satir.split(";");
                if(d.length!=8) continue;
                Yolcu y = new Yolcu(d[4],d[5],d[6],d[7]);
                Bilet b = new Bilet(d[0],d[1],d[2],Double.parseDouble(d[3]),y);
                biletListesi.add(b); bst.ekle(b);
            }
        } catch(Exception e){ System.out.println("Dosya okuma hatası: "+e.getMessage()); }
    }

    public static void dosyayaKaydet(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(veriDosyasi))){
            for(Bilet b:biletListesi){
                bw.write(b.ucusNo+";"+b.tarih+";"+b.saat+";"+b.fiyat+";"+b.yolcu.ad+";"+b.yolcu.soyad+";"+b.yolcu.tcNo+";"+b.yolcu.koltukNo);
                bw.newLine();
            }
        }catch(Exception e){ System.out.println("Dosyaya yazma hatası: "+e.getMessage()); }
    }

    // Kontroller
    public static boolean isimKontrol(String isim){ return isim.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+"); }
    public static boolean tcKontrol(String tc){ return tc.matches("\\d{11}"); }
    public static boolean ucusNoKontrol(String ucusNo){ return ucusNo.matches("\\d+"); }
    public static boolean koltukKontrol(String koltuk){ return koltuk.matches("\\d{1,2}[A-D]"); }
    public static boolean tarihKontrol(String tarih){
        if(!tarih.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) return false;
        try{
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate t = LocalDate.parse(tarih,f);
            return !t.isBefore(LocalDate.now());
        }catch(Exception e){ return false; }
    }
    public static boolean saatKontrol(String saat){
        if(!saat.matches("\\d{2}\\.\\d{2}")) return false;
        String[] p=saat.split("\\.");
        int h=Integer.parseInt(p[0]), m=Integer.parseInt(p[1]);
        return h>=0 && h<=23 && m>=0 && m<=59;
    }
    public static boolean koltukDoluMu(String ucus, String koltuk){
        for(Bilet b:biletListesi) if(b.ucusNo.equals(ucus) && b.yolcu.koltukNo.equals(koltuk)) return true;
        return false;
    }

    // Bilet Ekle
    public static void biletEkle(){
        String ad, soyad, tcNo, ucusNo, koltuk, tarih, saat;
        double fiyat;
        while(true){ System.out.print("Adınız: "); ad=scanner.nextLine(); if(isimKontrol(ad)) break; else System.out.println("Hatalı ad!"); }
        while(true){ System.out.print("Soyadınız: "); soyad=scanner.nextLine(); if(isimKontrol(soyad)) break; else System.out.println("Hatalı soyad!"); }
        while(true){ System.out.print("TC Kimlik No: "); tcNo=scanner.nextLine(); if(tcKontrol(tcNo)) break; else System.out.println("Hatalı TC!"); }
        while(true){ System.out.print("Uçuş No: "); ucusNo=scanner.nextLine(); if(ucusNoKontrol(ucusNo)) break; else System.out.println("Hatalı uçuş numarası!"); }
        while(true){ System.out.print("Koltuk (1-2 rakam + A-D): "); koltuk=scanner.nextLine(); if(koltukKontrol(koltuk)&&!koltukDoluMu(ucusNo,koltuk)) break; else System.out.println("Hatalı veya dolu koltuk!"); }
        while(true){ System.out.print("Tarih (gg.aa.yyyy): "); tarih=scanner.nextLine(); if(tarihKontrol(tarih)) break; else System.out.println("Hatalı tarih!"); }
        while(true){ System.out.print("Saat (ss.mm): "); saat=scanner.nextLine(); if(saatKontrol(saat)) break; else System.out.println("Hatalı saat!"); }
        while(true){ System.out.print("Fiyat: "); if(scanner.hasNextDouble()){ fiyat=scanner.nextDouble(); scanner.nextLine(); if(fiyat>=0) break; else System.out.println("Negatif fiyat olamaz!"); } else { System.out.println("Hatalı giriş!"); scanner.nextLine(); } }

        Yolcu y = new Yolcu(ad,soyad,tcNo,koltuk);
        Bilet b = new Bilet(ucusNo,tarih,saat,fiyat,y);
        biletListesi.add(b); bst.ekle(b); islemGecmisi.push("Bilet eklendi -> " + b); dosyayaKaydet();
        System.out.println("Bilet eklendi!");
    }

    // Bilet Sil
    public static void biletSil(){
        System.out.print("Silinecek uçuş numarası: "); String ucus = scanner.nextLine();
        System.out.print("Silinecek biletin TC Kimliği: "); String tc = scanner.nextLine();
        Iterator<Bilet> it = biletListesi.iterator();
        while(it.hasNext()){
            Bilet b = it.next();
            if(b.ucusNo.equals(ucus) && b.yolcu.tcNo.equals(tc)){
                islemGecmisi.push("Bilet silindi -> " + b);
                it.remove();
                bst.sil(tc);
                dosyayaKaydet();
                System.out.println("Bilet silindi!");
                return;
            }
        }
        System.out.println("Bilet bulunamadı!");
    }

    // Bilet Güncelle
    public static void biletGuncelle(){
        System.out.print("Güncellenecek uçuş numarası: "); String ucus = scanner.nextLine();
        System.out.print("Güncellenecek biletin TC Kimliği: "); String tc = scanner.nextLine();
        for(Bilet b:biletListesi){
            if(b.ucusNo.equals(ucus) && b.yolcu.tcNo.equals(tc)){
                islemGecmisi.push("Bilet güncellendi (eski hali) -> " + b);

                String ad, soyad, tcNo, ucusNo, koltuk, tarih, saat;
                double fiyat;
                while(true){ System.out.print("Yeni Ad: "); ad=scanner.nextLine(); if(isimKontrol(ad)) break; else System.out.println("Hatalı ad!"); }
                while(true){ System.out.print("Yeni Soyad: "); soyad=scanner.nextLine(); if(isimKontrol(soyad)) break; else System.out.println("Hatalı soyad!"); }
                while(true){ System.out.print("Yeni TC: "); tcNo=scanner.nextLine(); if(tcKontrol(tcNo)) break; else System.out.println("Hatalı TC!"); }
                while(true){ System.out.print("Yeni Uçuş No: "); ucusNo=scanner.nextLine(); if(ucusNoKontrol(ucusNo)) break; else System.out.println("Hatalı uçuş!"); }
                while(true){ System.out.print("Yeni Koltuk (1-2 rakam + A-D): "); koltuk=scanner.nextLine(); if(koltukKontrol(koltuk)&&!koltukDoluMu(ucusNo,koltuk)) break; else System.out.println("Hatalı veya dolu koltuk!"); }
                while(true){ System.out.print("Yeni Tarih (gg.aa.yyyy): "); tarih=scanner.nextLine(); if(tarihKontrol(tarih)) break; else System.out.println("Hatalı tarih!"); }
                while(true){ System.out.print("Yeni Saat (ss.mm): "); saat=scanner.nextLine(); if(saatKontrol(saat)) break; else System.out.println("Hatalı saat!"); }
                while(true){ System.out.print("Yeni Fiyat: "); if(scanner.hasNextDouble()){ fiyat=scanner.nextDouble(); scanner.nextLine(); if(fiyat>=0) break; else System.out.println("Negatif fiyat olamaz!"); } else { System.out.println("Hatalı giriş!"); scanner.nextLine(); } }

                bst.sil(b.yolcu.tcNo);
                b.yolcu.ad=ad; b.yolcu.soyad=soyad; b.yolcu.tcNo=tcNo;
                b.ucusNo=ucusNo; b.yolcu.koltukNo=koltuk; b.tarih=tarih; b.saat=saat; b.fiyat=fiyat;
                bst.ekle(b);
                dosyayaKaydet();
                System.out.println("Bilet güncellendi!");
                return;
            }
        }
        System.out.println("Bilet bulunamadı!");
    }

    // Listele
    public static void listele(){
        if(biletListesi.isEmpty()){ System.out.println("Kayıtlı bilet yok!"); return; }
        for(Bilet b:biletListesi) System.out.println(b);
    }

    // Sırala
    public static void sirala(){
        if(biletListesi.isEmpty()){ System.out.println("Liste boş!"); return; }
        System.out.println("1. Tarihe göre\n2. Saate göre\n3. Fiyata göre\nSeçiminiz: ");
        int secim;
        if(scanner.hasNextInt()){ secim=scanner.nextInt(); scanner.nextLine(); }
        else { System.out.println("Geçersiz seçim!"); scanner.nextLine(); return; }
        switch(secim){
            case 1->{
                DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                biletListesi.sort(Comparator.comparing(b->LocalDate.parse(b.tarih,f)));
            }
            case 2->biletListesi.sort(Comparator.comparing(b->b.saat));
            case 3->biletListesi.sort(Comparator.comparingDouble(b->b.fiyat));
            default->System.out.println("Geçersiz seçim!");
        }
        listele();
    }

    // Sorgula
    public static void sorgula(){
        System.out.println("1. TC ile sorgula\n2. Uçuş Numarası ile sorgula\nSeçiminiz: ");
        int secim;
        if(scanner.hasNextInt()){ secim=scanner.nextInt(); scanner.nextLine(); }
        else{ System.out.println("Geçersiz seçim!"); scanner.nextLine(); return; }
        switch(secim){
            case 1->{
                System.out.print("TC: "); String tc = scanner.nextLine();
                Bilet b = bst.ara(tc);
                if(b!=null) System.out.println(b);
                else System.out.println("Bu TC'ye ait bilet yok.");
            }
            case 2->{
                System.out.print("Uçuş No: "); String ucus = scanner.nextLine();
                boolean bulundu=false;
                for(Bilet b:biletListesi) if(b.ucusNo.equals(ucus)){ System.out.println(b); bulundu=true; }
                if(!bulundu) System.out.println("Bu uçuş numarasına ait bilet yok.");
            }
            default->System.out.println("Geçersiz seçim!");
        }
    }

    // İşlem geçmişi
    public static void islemGecmisiniGoster(){
        if(islemGecmisi.isEmpty()){ System.out.println("İşlem geçmişi boş!"); return; }
        for(String s:islemGecmisi) System.out.println(s);
    }
}