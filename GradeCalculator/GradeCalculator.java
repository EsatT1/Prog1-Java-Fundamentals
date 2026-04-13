
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner giris = new Scanner(System.in);
        int notlar[][] = new int[3][2];

        for (int i = 0; i < notlar.length; i++) {
            System.out.println("--- " + (i + 1) + ". Öğrenci ---");
            System.out.print("Vize Gir: ");
            notlar[i][0] = giris.nextInt();

            System.out.print("Final Gir: ");
            notlar[i][1] = giris.nextInt();
        }
        System.out.println("*******************************");
        System.out.println("         SONUÇ LİSTESİ         ");
        System.out.println("*******************************");

        for (int i = 0; i < notlar.length; i++) {
            // Vizenin %40'ı ve Finalin %60'ı alinacak
            double ortalama = (notlar[i][0] * 0.4) + (notlar[i][1] * 0.6);
            
            String durum;
            
            // Ortalama 35 ve üstüyse geçti 
            if (ortalama >= 35 && notlar[i][1] >= 35) {
                durum = "GEÇTİ";
            } else {
                durum = "KALDI";
            }

            System.out.println((i+1) + ". Öğrenci -> Vize: " + notlar[i][0] + 
                               " | Final: " + notlar[i][1] + 
                               " | Ortalama: " + ortalama + 
                               " -> Durum: " + durum);
        }

    }
}
