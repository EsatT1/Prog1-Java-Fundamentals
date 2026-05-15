#  E-Ticaret Mağazası Envanter Yönetim Sistemi

Bu proje, Java kullanılarak geliştirilmiş, uzak sunucudan veri çekebilen ve dosya işlemleri (File I/O) yapabilen kapsamlı bir envanter yönetim simülasyonudur.

##  Öne Çıkan Özellikler
* [cite_start]**API Entegrasyonu:** `DummyJSON API` kullanılarak canlı ürün verileri (ID, Ürün Adı, Marka/Kategori, Fiyat, Stok) sunucudan çekilmektedir.
   [cite_start]Güvenlik duvarı engellerini aşmak için bağlantı koduna `User-Agent` ayarı entegre edilmiştir.
* [cite_start]**Kalıcı Veri Depolama (File I/O):** Çekilen veriler işlenerek `magaza_envanter.txt` dosyasına kayıt edilir.
  [cite_start]Tüm CRUD (Okuma, Listeleme, Güncelleme, Silme) işlemleri doğrudan bu dosya üzerinden gerçekleştirilir.
* [cite_start]**Güvenlik (Admin Girişi):** Sistem doğrudan açılmaz; yetkisiz erişimi engellemek için kullanıcıdan Admin adı ve Şifre (admin/12345) talep eder.
* [cite_start]**Dinamik Hata Loglama:** Programın çalışması sırasındaki hatalar (bağlantı kopukluğu, hatalı tuşlama, dosya bulunamaması vb.)
  `try-catch` bloklarıyla yakalanır ve zaman damgasıyla birlikte `hata_loglari.txt` dosyasına kaydedilir.

##  Kullanılan Teknolojiler
* Java (Temel I/O, Scanner, ArrayList, Exception Handling)
* `java.net.HttpURLConnection` (API istekleri için)
* [cite_start]Temel String metotları (`split`, `indexOf`, `substring`) ile veri ayrıştırma (parsing).

##  Çalıştırma Talimatları
1. Projeyi klonlayın.
2. `.java` dosyasını derleyip çalıştırın.
3. Giriş ekranında istenen bilgiler:
   * **Kullanıcı Adı:** admin
   * **Şifre:** 12345
