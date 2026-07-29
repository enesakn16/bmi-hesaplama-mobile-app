# BMI Hesaplama Mobile App

Kotlin ve Jetpack Compose ile geliştirilen sade, test edilebilir Android BMI uygulaması.

## Özellikler

- Kilo ve boy girişi
- Virgül veya nokta ile ondalık değer desteği
- Gerçekçi kilo ve boy sınırı doğrulaması
- Tek ondalığa yuvarlanmış BMI sonucu
- Kategori eşiklerinde yuvarlama kaynaklı yanlış sınıflandırmayı önleyen ham değer hesabı
- Zayıf, normal, fazla kilolu ve obez kategorileri
- Ekran dönüşlerinde korunan ViewModel tabanlı UI durumu
- Formu tek dokunuşla temizleme
- Tıbbi değerlendirme yerine geçmediğini belirten açık uyarı

## Mimari

```text
Stateless Compose UI
   ↓ events / ↑ state
BmiViewModel
   ↓
BmiCalculator.evaluate
   ↓
BmiEvaluation.Success / Invalid
   ↓
BmiResult + BmiCategory
```

`BmiScreen` yalnızca kendisine verilen durumu gösterir ve kullanıcı olaylarını yukarı iletir. `BmiViewModel` ekran durumunun tek doğruluk kaynağıdır ve yapılandırma değişikliklerinde korunur. Hesaplama mantığı Android sınıflarına bağlı değildir; cihaz veya emülatör açılmadan JVM testleriyle doğrulanabilir.

BMI sonucu kullanıcıya tek ondalık basamakla gösterilir; kategori sınıflandırması ise yuvarlanmamış ham değer üzerinden yapılır. Böylece 18,5, 25 ve 30 eşiklerine çok yakın değerler ekranda yuvarlansa bile yanlış kategoriye geçirilmez.

## Teknolojiler

- Kotlin 2.3.21
- Jetpack Compose
- Compose BOM 2026.06.00
- Android Gradle Plugin 8.13
- Lifecycle ViewModel Compose 2.10.0
- JDK 17
- Minimum Android 7.0 / API 24

Compose Compiler Gradle plugin'i Kotlin 2.0 ve üzerindeki resmi kurulum yaklaşımına uygun olarak kullanılır.

## Doğrulama

```bash
gradle testDebugUnitTest
gradle lintDebug
gradle assembleDebug
```

Pull request ve `main` pushlarında GitHub Actions bu üç komutu otomatik çalıştırır. Başarılı çalışmada debug APK artifact olarak yüklenir.

## Önemli dosyalar

- `app/src/main/java/com/enesakin/bmi/MainActivity.kt`: stateless Compose ekranı ve route
- `app/src/main/java/com/enesakin/bmi/BmiViewModel.kt`: ekran durumu ve kullanıcı olayları
- `app/src/main/java/com/enesakin/bmi/domain/BmiCalculator.kt`: doğrulama ve hesaplama
- `app/src/test/java/com/enesakin/bmi/BmiViewModelTest.kt`: ekran durumu testleri
- `app/src/test/java/com/enesakin/bmi/domain/BmiCalculatorTest.kt`: domain birim testleri
- `.github/workflows/android-ci.yml`: test, lint ve APK üretimi

## Yol haritası

- Erişilebilirlik ve Compose UI testleri
- Geçmiş hesaplamaları yalnızca kullanıcının açık tercihiyle yerel saklama
- İmzalanmamış debug APK yerine sürümlenmiş release akışı
- Material 3 renk ve tipografi sistemini özelleştirme

## Sağlık uyarısı

BMI tek başına tanı aracı değildir. Uygulama sonucu yalnızca genel bilgilendirme amacıyla sunar.

## AI destekli geliştirme

Proje AI destekli araçlarla geliştirilmiştir. Mimari kararlar, test senaryoları, güvenlik kontrolleri ve yayın doğrulaması insan incelemesi gerektirir.
