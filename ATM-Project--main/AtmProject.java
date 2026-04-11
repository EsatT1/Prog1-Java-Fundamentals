import java.util.Scanner;

public class AtmProjesi {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
       
        int bakiye = 1000 ;
        int secim, miktar;

        System.out.println("ATM'ye HOŞGELDİNİZ !");
        System.out.println("*******************");
        System.out.println("Güncel Bakiyeniz: "+ bakiye + "TL");

        while (bakiye>0){
            
            System.out.println("\nYapmak istediginiz islemi giriniz:");
            System.out.println("1-Para Yatir");
             System.out.println("2-Para Cek");
             System.out.println("3-Bakiye Sorgula");
             System.out.println("4-Cikis Yap");

            secim =  scan.nextInt();

            switch(secim){
            case 1:
                System.out.print("Yatirmak istediginiz miktari giriniz: ");
                miktar = scan.nextInt();
                bakiye += miktar ;
                System.out.println("Yeni Bakiyeniz:" +bakiye+ "TL");
                break;
            case 2:
                System.out.print("Cekmek istediginiz miktari giriniz: ");
                miktar = scan.nextInt();

                if (miktar > bakiye) {
                    System.out.println("Yetersiz bakiye!");
                } else {
                    bakiye -= miktar;
                    System.out.println("Yeni bakiyeniz: " + bakiye + " TL");
                }
                break;

            case 3:
                System.out.println("Bakiyeniz: " + bakiye + " TL");
                break;
            case 4:
                 System.out.println("Programdan cikiliyor...");
                 System.exit(0);    // tüm programrandan çıkmak için
                 
        
             default:
                System.out.println("Geçersiz seçim!");


        }

        }

        
    }
}
