import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Odev_2321032067 {

    public static void main(String[] args) {
        Scanner giris = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("      E-TİCARET SİSTEMİNE GİRİŞ          ");
        System.out.println("=========================================");
        System.out.print("Kullanıcı Adı: ");
        String kullanici = giris.nextLine();
        System.out.print("Şifre: ");
        String sifre = giris.nextLine();
        
        if (!kullanici.equals("admin") || !sifre.equals("12345")) {
            System.out.println("--> GÜVENLİK İHLALİ: Hatalı giriş yaptınız. Sistem kapatılıyor.");
            logTut("Hatalı sisteme giriş denemesi yapıldı. Kullanıcı: " + kullanici);
            return; // Programı sonlandırır
        }

        int secilenIslem = 0;

        do {
            System.out.println("\n=========================================");
            System.out.println("   E-TİCARET MAĞAZASI ENVANTER SİSTEMİ   ");
            System.out.println("=========================================");
            System.out.println("[1] - API'den Güncel Ürünleri Çek");
            System.out.println("[2] - Envanteri Listele");
            System.out.println("[3] - Ürün Bilgisi Güncelle");
            System.out.println("[4] - Ürün Sil");
            System.out.println("[5] - Hata Loglarını Görüntüle (Ekstra)");
            System.out.println("[6] - Sistemi Kapat");
            System.out.print("Lütfen yapmak istediğiniz işlemi seçin: ");
            
            try {
                secilenIslem = giris.nextInt();
                giris.nextLine(); 
            } catch (Exception e) {
                System.out.println("--> Lütfen sadece rakam giriniz!");
                logTut("Menü seçiminde rakam harici hatalı tuşlama yapıldı.");
                giris.nextLine(); // Hatalı girişi temizle
                continue;
            }

            switch (secilenIslem) {
                case 1:
                    apiDenUrunleriGetir();
                    break;
                case 2:
                    envanterListele(giris);
                    break;
                case 3:
                    urunGuncelle(giris);
                    break;
                case 4:
                    urunSil(giris);
                    break;
                case 5:
                    loglariGoruntule();
                    break;
                case 6:
                    System.out.println("--> Sistemden güvenli bir şekilde çıkış yapılıyor. İyi çalışmalar!");
                    break;
                default:
                    System.out.println("--> HATA: Lütfen sadece 1 ile 6 arasında bir rakam tuşlayın!");
            }
        } while (secilenIslem != 6);
    }

    //  API'DEN veri çekme 
    public static void apiDenUrunleriGetir() {
        System.out.println("Sunucuya bağlanılıyor, E-Ticaret verileri indiriliyor...");
        try {
            String baglantiAdresi = "https://dummyjson.com/products";
            URI uri = URI.create(baglantiAdresi);
            URL url = uri.toURL();
            HttpURLConnection baglanti = (HttpURLConnection) url.openConnection();
            baglanti.setRequestMethod("GET");
            baglanti.setRequestProperty("User-Agent", "Mozilla/5.0"); 

            BufferedReader okuyucu = new BufferedReader(new InputStreamReader(baglanti.getInputStream(), "UTF-8"));
            String satir;
            String tumVeri = "";
            while ((satir = okuyucu.readLine()) != null) {
                tumVeri += satir;
            }
            okuyucu.close();

            FileWriter dosyaYazici = new FileWriter("magaza_envanter.txt", false); 
            String[] urunBloklari = tumVeri.split("\"id\":"); 
            int kaydedilenUrunSayisi = 0;
            
            for (int i = 1; i < urunBloklari.length; i++) { 
                if (kaydedilenUrunSayisi >= 30) break; 
                String urun = urunBloklari[i];
                
                String id = urun.substring(0, urun.indexOf(",")).trim();
                String ad = etiketlerArasiniBul(urun, "\"title\":\"", "\"");
                
                // Marka yoksa kategoriyi al (gıda ürünleri için 'e' hatası giderildi)
                String marka = etiketlerArasiniBul(urun, "\"brand\":\"", "\"");
                if (marka.equals("Bulunamadi")) {
                    marka = etiketlerArasiniBul(urun, "\"category\":", ",");
                    marka = marka.replace("\"", "").trim();
                }
                
                String fiyat = etiketlerArasiniBul(urun, "\"price\":", ",");
                String stok = etiketlerArasiniBul(urun, "\"stock\":", ",");
                
                dosyaYazici.write(id + ";" + ad + ";" + marka + ";" + fiyat + ";" + stok + "\n");
                kaydedilenUrunSayisi++;
            }
            dosyaYazici.close();
            System.out.println("--> BAŞARILI! " + kaydedilenUrunSayisi + " adet ürün dosyaya kaydedildi.");
        } catch (Exception hata) {
            System.out.println("--> SUNUCU HATASI: İnternet bağlantınızı kontrol edin.");
            logTut("API Veri Çekme Hatası: " + hata.getMessage());
        }
    }

    //  2. Listeleme
    public static void envanterListele(Scanner giris) {
        System.out.println("\n--- ALT MENÜ: LİSTELEME SEÇENEKLERİ ---");
        System.out.println("A - Markaya / Kategoriye Göre Listele");
        System.out.println("B - Belirli Bir Fiyatın Altındaki Ürünleri Listele");
        System.out.println("C - Tüm Envanteri Listele");
        System.out.print("Lütfen bir filtreleme yöntemi seçin (A/B/C): ");
        
        String altSecim = giris.nextLine().toUpperCase();
        String arananMarka = "";
        double maksimumFiyat = 0;
        
        if (altSecim.equals("A")) {
            System.out.print("Aranacak markayı/kategoriyi girin: ");
            arananMarka = giris.nextLine().toUpperCase();
        } else if (altSecim.equals("B")) {
            System.out.print("Maksimum bütçenizi girin: ");
            try {
                maksimumFiyat = giris.nextDouble();
                giris.nextLine(); 
            } catch (Exception e) {
                System.out.println("--> HATA: Lütfen geçerli bir sayı girin.");
                logTut("Filtrelemede sayı yerine harf girildi.");
                giris.nextLine();
                return;
            }
        } else if (!altSecim.equals("C")) {
            System.out.println("--> HATA: Geçersiz seçim.");
            return;
        }

        try {
            BufferedReader oku = new BufferedReader(new java.io.FileReader("magaza_envanter.txt"));
            System.out.println("\n----------------------------------------------------------------------------------");
            System.out.printf("%-5s | %-32s | %-18s | %-8s | %-5s%n", "ID", "ÜRÜN ADI", "MARKA/KATEGORİ", "FİYAT", "STOK");
            System.out.println("----------------------------------------------------------------------------------");
            
            String satir;
            int listelenenSayisi = 0;
            
            while ((satir = oku.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length == 5) {
                    double fiyat = Double.parseDouble(p[3]);
                    boolean kosul = false;
                    
                    if (altSecim.equals("C")) kosul = true;
                    else if (altSecim.equals("A") && p[2].toUpperCase().contains(arananMarka)) kosul = true;
                    else if (altSecim.equals("B") && fiyat <= maksimumFiyat) kosul = true;
                    
                    if (kosul) {
                        String ad = (p[1].length() > 30) ? p[1].substring(0, 30) + ".." : p[1];
                        System.out.printf("%-5s | %-32s | %-18s | %-8.2f | %-5s%n", p[0], ad, p[2], fiyat, p[4]);
                        listelenenSayisi++;
                    }
                }
            }
            oku.close();
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Toplam " + listelenenSayisi + " adet ürün listelendi.");
            
        } catch (Exception e) {
            System.out.println("--> DOSYA HATASI: Önce '1' ile verileri API'den çekmelisiniz.");
            logTut("Listeleme yapılamadı, magaza_envanter.txt bulunamadı.");
        }
    }

    // 3. Güncelleme
    public static void urunGuncelle(Scanner giris) {
        System.out.print("Güncellemek istediğiniz ürünün ID numarasını girin: ");
        String arananID = giris.nextLine();
        List<String> satirlar = dosyayiListeyeOku();
        boolean bulundu = false;

        for (int i = 0; i < satirlar.size(); i++) {
            String[] veriler = satirlar.get(i).split(";");
            if (veriler[0].equals(arananID)) {
                bulundu = true;
                System.out.println("Bulunan Ürün: " + veriler[1] + " (Fiyat: " + veriler[3] + ")");
                System.out.print("Bu ürünü güncellemek istiyor musunuz(e/h)? ");
                if (giris.nextLine().equalsIgnoreCase("e")) {
                    System.out.print("Yeni Fiyat: ");
                    String yeniFiyat = giris.nextLine();
                    System.out.print("Yeni Stok: ");
                    String yeniStok = giris.nextLine();
                    
                    satirlar.set(i, veriler[0] + ";" + veriler[1] + ";" + veriler[2] + ";" + yeniFiyat + ";" + yeniStok);
                    listeyiDosyayaYaz(satirlar);
                    System.out.println("--> Ürün başarıyla güncellendi.");
                }
                break;
            }
        }
        if (!bulundu) System.out.println("--> Ürün bulunamadı!");
    }

    // 4. Silme
    public static void urunSil(Scanner giris) {
        System.out.print("Silmek istediğiniz ürünün ID numarasını girin: ");
        String arananID = giris.nextLine();
        List<String> satirlar = dosyayiListeyeOku();
        boolean bulundu = false;

        for (int i = 0; i < satirlar.size(); i++) {
            if (satirlar.get(i).split(";")[0].equals(arananID)) {
                bulundu = true;
                System.out.println("Bulunan Ürün: " + satirlar.get(i));
                System.out.print("Bu ürünü silmek istediğinize emin misiniz(e/h)? ");
                if (giris.nextLine().equalsIgnoreCase("e")) {
                    satirlar.remove(i); 
                    listeyiDosyayaYaz(satirlar);
                    System.out.println("--> Ürün kalıcı olarak silindi.");
                }
                break;
            }
        }
        if (!bulundu) System.out.println("--> Ürün bulunamadı!");
    }

    // EKSTRA ÖZELLİK 2: HATA LOGLARI TUTMA 
    public static void logTut(String mesaj) {
        try {
            FileWriter fw = new FileWriter("hata_loglari.txt", true); // true ile sonuna ekleme yapar
            fw.write(new Date().toString() + " | HATA: " + mesaj + "\n");
            fw.close();
        } catch (Exception e) {}
    }

    // 5. MENÜ İŞLEMİ: LOGLARI GÖRÜNTÜLEME 
    public static void loglariGoruntule() {
        System.out.println("\n--- SİSTEM HATA LOGLARI ---");
        try {
            BufferedReader oku = new BufferedReader(new java.io.FileReader("hata_loglari.txt"));
            String satir;
            boolean logVarMi = false;
            while ((satir = oku.readLine()) != null) {
                System.out.println(satir);
                logVarMi = true;
            }
            oku.close();
            if(!logVarMi) System.out.println("Sistemde kayıtlı hiçbir hata bulunamadı.");
        } catch (Exception e) {
            System.out.println("Sistemde henüz kayıtlı bir hata dosyası oluşmamış.");
        }
    }

    // --- YARDIMCI METOTLAR ---
    public static List<String> dosyayiListeyeOku() {
        List<String> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader("magaza_envanter.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) liste.add(satir);
        } catch (Exception e) {}
        return liste;
    }

    public static void listeyiDosyayaYaz(List<String> liste) {
        try (FileWriter fw = new FileWriter("magaza_envanter.txt", false)) { 
            for (String s : liste) fw.write(s + "\n");
        } catch (Exception e) {}
    }

    public static String etiketlerArasiniBul(String yazi, String bas, String bit) {
        try {
            int b = yazi.indexOf(bas) + bas.length();
            return yazi.substring(b, yazi.indexOf(bit, b)).trim();
        } catch (Exception e) { return "Bulunamadi"; }
    }
}