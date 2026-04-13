# Öğrenci Not Hesaplama Sistemi (Grade Calculator)

Bu proje, Java kullanılarak geliştirilmiş konsol tabanlı bir not hesaplama uygulamasıdır. Kullanıcıdan alınan vize ve final notlarını belirli ağırlıklara göre hesaplar
ve geçme/kalma durumunu belirler. 

Özellikle üniversite sistemlerindeki baraj puanı mantığını simüle etmek için tasarlanmıştır.

## Hesaplama Mantığı ve Kurallar
Program, girilen notları şu kriterlere göre değerlendirir:

* **Vize Ağırlığı:** %40
* **Final Ağırlığı:** %60
* **Geçme Barajı:** 35 puan.
* **Özel Şart:** Öğrencinin genel ortalaması 35 veya üzeri olsa bile, **Final notu 35'in altındaysa** sistem öğrenciyi doğrudan **KALDI** olarak işaretler.

## Teknik Özellikler
* **Veri Yapısı:** 3 öğrenci ve 2 farklı sınav türü için `int[3][2]` boyutunda çok boyutlu diziler kullanıldı.
* **Hata Yönetimi:** Hesaplamaların küsuratlı çıkabilme ihtimaline karşı ortalama değişkeni `double` tipinde tanımlandı.
* **Mantıksal Denetim:** Geçme şartı `&&` (mantıksal VE) operatörü kullanılarak iki aşamalı kontrol edildi.
