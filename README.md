# BMI Hesaplama Mobile App

Kotlin ve Jetpack Compose ile geliştirilen sade, test edilebilir Android BMI uygulaması.

## Özellikler

- Kilo ve boy girişi
- Virgül veya nokta ile ondalık değer desteği
- Gerçekçi kilo ve boy sınırı doğrulaması
- Tek ondalığa yuvarlanmış BMI sonucu
- Zayıf, normal, fazla kilolu ve obez kategorileri
- Tıbbi değerlendirme yerine geçmediğini belirten açık uyarı

## Mimari

```text
Compose UI
   ↓
BmiCalculator.evaluate
   ↓
BmiEvaluation.Success / Invalid
   ↓
BmiResult + BmiCategory
```

Hesaplama mantığı Android sınıflarına bağlı değildir. Bu sayede cihaz veya emülatör açılmadan JVM birim testleriyle doğrulanabilir.

## Teknolojiler

- Kotlin 2.3.21
- Jetpack Compose
- Compose BOM 2026.06.00
- Android Gradle Plugin 8.13
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

- `app/src/main/java/com/enesakin/bmi/MainActivity.kt`: Compose ekranı
- `app/src/main/java/com/enesakin/bmi/domain/BmiCalculator.kt`: doğrulama ve hesaplama
- `app/src/test/java/com/enesakin/bmi/domain/BmiCalculatorTest.kt`: birim testleri
- `.github/workflows/android-ci.yml`: test, lint ve APK üretimi

## Yol haritası

- Ekran durumunu ViewModel'e taşıma
- Geçmiş hesaplamaları yalnızca kullanıcının açık tercihiyle yerel saklama
- Erişilebilirlik ve Compose UI testleri
- İmzalanmamış debug APK yerine sürümlenmiş release akışı

## Sağlık uyarısı

BMI tek başına tanı aracı değildir. Uygulama sonucu yalnızca genel bilgilendirme amacıyla sunar.

## AI destekli geliştirme

Proje AI destekli araçlarla geliştirilmiştir. Mimari kararlar, test senaryoları, güvenlik kontrolleri ve yayın doğrulaması insan incelemesi gerektirir.
