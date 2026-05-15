import java.util.Scanner;
import java.util.Random;

public class Odev_2321032067 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] kelimeler = {"klavye", "telefon", "bisiklet", "kalemlik", "kutuphane", "sandalye", "pencere", "otobus", "defter", "buzdolabi"};
        
        int oyunSayisi = 0;
        String oncekiKelime = ""; 
        boolean devamEt = true;
        
        while (oyunSayisi < 5 && devamEt) {
            String secilenKelime = "";
            
            while (true) {
                secilenKelime = rndKelime(kelimeler);
                
                if (!secilenKelime.equals(oncekiKelime)) {
                    break;
                }
            }
            
            oncekiKelime = secilenKelime; 
            
            int uzunluk = secilenKelime.length();
            System.out.println("Kelime " + uzunluk + " karakterlidir.");
            
            char[] yeniBicim = new char[uzunluk];
            for (int i = 0; i < uzunluk; i++) {
                yeniBicim[i] = '*';
            }
            
            if (uzunluk == 6 || uzunluk == 7) {
                yeniBicim[0] = Character.toUpperCase(secilenKelime.charAt(0));
            }
            
            if (uzunluk >= 8) {
                yeniBicim[0] = Character.toUpperCase(secilenKelime.charAt(0));
                yeniBicim[uzunluk - 1] = Character.toUpperCase(secilenKelime.charAt(uzunluk - 1));
            }
            
            int hak = 15;
            boolean acildi = false;
            
          while (hak > 0) {
                for (int i = 0; i < uzunluk; i++) {
                    System.out.print(yeniBicim[i]);
                }
                System.out.println();
                
                System.out.print("Bir harf giriniz: ");
                String giris = scanner.next();
                
                char tahmin = Character.toLowerCase(giris.charAt(0));
                
                boolean harfBulundu = false;
                
                // Kelimenin içinde harfi arıyoruz
                for (int i = 0; i < uzunluk; i++) {
                    if (secilenKelime.charAt(i) == tahmin) {
                        yeniBicim[i] = Character.toUpperCase(tahmin);
                        harfBulundu = true;
                    }
                }
                
                if (!harfBulundu) {
                    System.out.println("harf yok");
                }

                hak--;
                
                acildi = true; // Başlangıçta tüm harflerin açıldığını varsayalım
                for (int i = 0; i < uzunluk; i++) {
                    if (yeniBicim[i] == '*') {
                        acildi = false;
                        break; 
                    }
                }
                
                if (acildi) {
                    break; 
                }
            }
            
            if (acildi) {
                for (int i = 0; i < uzunluk; i++) {
                    System.out.print(yeniBicim[i]);
                }
                System.out.println("\nTebrikler");
            } else {
                System.out.println("Bilemediniz");
            }
            
            oyunSayisi++;
            
            if (oyunSayisi < 5) {
                System.out.print("Tekrar oynamak istiyormusunuz(e/h)? ");
                String cevap = scanner.next();
                if (cevap.equalsIgnoreCase("h")) {
                    devamEt = false;
                }
            }
        }
    }
    
    public static String rndKelime(String[] kelimeDizisi) {
        Random rnd = new Random();
        int rndIndeks = rnd.nextInt(kelimeDizisi.length);
        return kelimeDizisi[rndIndeks];
    }
}