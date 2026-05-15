#  Word Guessing Game 

Bu proje, Java programlama dilinin temel prensiplerini, döngüleri ve `String` metotlarını pekiştirmek amacıyla geliştirilmiştir. 

##  Oyun Özellikleri
* **Dinamik İpucu Sistemi:** Kelimenin uzunluğuna göre ilk harf veya hem ilk hem son harf otomatik olarak büyük harfle gösterilir.
* **Hak Sınırı:** Kullanıcının her kelime için toplam **15 tahmin hakkı** bulunmaktadır.
* **Tekrar Oynanabilirlik:** Kullanıcı isterse 5 tura kadar yeni kelimelerle oyuna devam edebilir.
* **Karakter Kontrolü:** Küçük/büyük harf duyarlılığı yönetilerek kullanıcı deneyimi artırılmıştır.

##  Teknik Detaylar
* `java.util.Scanner` ile konsol üzerinden girdi yönetimi.
* `java.util.Random` ile dizi içerisinden rastgele seçim.
* Diziler (Arrays) ve döngüler (Loops) ile algoritma kurgusu.

##  Çalıştırma
Projeyi klonladıktan sonra terminalde şu komutları kullanabilirsiniz:
```bash
javac WordGuessingGame.java
java WordGuessingGame
