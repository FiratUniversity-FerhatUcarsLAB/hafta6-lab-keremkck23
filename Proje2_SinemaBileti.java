AD Soyad : Mehmet Kerem Küçük
Öğrenci No : 250541097
Proje Adı : Sinema Bileti Sistemi
Tarih : 20.11.2025

Bu projenin amacı sinema için tasarlanan biletlerin kişiye göre fiyatını belirtmektir.


import java.util.Scanner;

/**
 * Sinema Bileti Fiyatını Kullanıcıdan Alınan Verilerle Hesaplayan Sistem (Döngüsüz Versiyon).
 * NOT: Girdi hatalarını (örneğin 1-7 dışı gün girişi) kontrol etmez.
 */
public class SinemaBileti {

    // --- Sabitler ---
    private static final double DISCOUNT_OGRENCI_HAFTAICI = 0.20;
    private static final double DISCOUNT_OGRENCI_HAFTASONU = 0.15;
    private static final double DISCOUNT_YAS_65_PLUS = 0.30;
    private static final double DISCOUNT_YAS_12_ALTI = 0.25;
    private static final double DISCOUNT_OGRETMEN_CARSAMBA = 0.35;

    // --- Zorunlu Metotlar ---

    /** 1. Hafta sonu mu kontrol eder. */
    public boolean isWeekend(int gun) {
        return gun == 6 || gun == 7;
    }

    /** 2. Matine mi kontrol eder. */
    public boolean isMatinee(int saat) {
        return saat < 12;
    }

    /** 3. Temel bilet fiyatını hesaplar. */
    public double calculateBasePrice(int gun, int saat) {
        boolean isWknd = isWeekend(gun);
        boolean isMat = isMatinee(saat);
        double basePrice = 0.0;

        if (!isWknd && !isMat) {
            basePrice = 65.0; // Hafta içi normal
        } else if (!isWknd && isMat) {
            basePrice = 45.0; // Hafta içi matine
        } else if (isWknd && isMat) {
            basePrice = 55.0; // Hafta sonu matine
        } else if (isWknd && !isMat) {
            basePrice = 85.0; // Hafta sonu normal
        }
        return basePrice;
    }

    /** 4. Uygulanacak indirimi hesaplar (max indirim oranı). */
    public double calculateDiscount(int yas, int meslek, int gun) {
        double maxDiscount = 0.0;

        // Yaşa Bağlı İndirimler (if/else if kullanılarak döngü yerine kontrol edilir)
        if (yas >= 65) {
            maxDiscount = Math.max(maxDiscount, DISCOUNT_YAS_65_PLUS);
        } else if (yas < 12) {
            maxDiscount = Math.max(maxDiscount, DISCOUNT_YAS_12_ALTI);
        }

        // Mesleğe Bağlı İndirimler (switch-case kullanılarak döngü yerine kontrol edilir)
        switch (meslek) {
            case 1: // Öğrenci
                // if/else ile gün kontrolü (döngüsüz)
                if (gun >= 1 && gun <= 4) {
                    maxDiscount = Math.max(maxDiscount, DISCOUNT_OGRENCI_HAFTAICI);
                } else {
                    maxDiscount = Math.max(maxDiscount, DISCOUNT_OGRENCI_HAFTASONU);
                }
                break;
            case 2: // Öğretmen
                if (gun == 3) { // Çarşamba kontrolü (döngüsüz)
                    maxDiscount = Math.max(maxDiscount, DISCOUNT_OGRETMEN_CARSAMBA);
                }
                break;
        }

        return maxDiscount;
    }

    /** 5. Film formatına bağlı ekstra ücreti getirir. */
    public double getFormatExtra(int filmTuru) {
        double extraFee = 0.0;
        switch (filmTuru) {
            case 2: extraFee = 25.0; break; // 3D
            case 3: extraFee = 35.0; break; // IMAX
            case 4: extraFee = 50.0; break; // 4DX
            case 1: extraFee = 0.0; break;  // 2D
        }
        return extraFee;
    }

    /** 6. Nihai bilet fiyatını hesaplar. */
    public double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double basePrice = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslek, gun);
        double discountedPrice = basePrice * (1.0 - discountRate);
        double formatExtra = getFormatExtra(filmTuru);
        double finalPrice = discountedPrice + formatExtra;

        return Math.round(finalPrice * 100.0) / 100.0;
    }

    // --- Yardımcı Metotlar (Bilet özeti için) ---
    private String getGunAdi(int gun) {
        return switch (gun) {
            case 1 -> "Pazartesi";
            case 2 -> "Salı";
            case 3 -> "Çarşamba";
            case 4 -> "Perşembe";
            case 5 -> "Cuma";
            case 6 -> "Cumartesi";
            case 7 -> "Pazar";
            default -> "Geçersiz Gün";
        };
    }
    private String getMeslekAdi(int meslek) {
        return switch (meslek) {
            case 1 -> "Öğrenci";
            case 2 -> "Öğretmen";
            case 3 -> "Diğer";
            default -> "Bilinmiyor";
        };
    }
    private String getFilmTuruAdi(int filmTuru) {
        return switch (filmTuru) {
            case 1 -> "2D";
            case 2 -> "3D";
            case 3 -> "IMAX";
            case 4 -> "4DX";
            default -> "Bilinmiyor";
        };
    }

    /** 7. Bilet bilgilerini özetleyen bir metin oluşturur. */
    public String generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        double basePrice = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslek, gun);
        double formatExtra = getFormatExtra(filmTuru);
        double finalPrice = calculateFinalPrice(gun, saat, yas, meslek, filmTuru);

        // Bilet Bilgileri Özeti
        return String.format("""
            --- Bilet Bilgileri Özeti ---
            Gün/Saat: **%s, %02d:00**
            Film Formatı: **%s**
            Yaş/Meslek: %d / %s
            ----------------------------
            Temel Fiyat: %.2f TL
            İndirim Oranı: **%.0f%%**
            Format Ek Ücreti: +%.2f TL
            ----------------------------
            **ÖDENECEK NİHAİ FİYAT: %.2f TL**
            ----------------------------
            """, getGunAdi(gun), saat, getFilmTuruAdi(filmTuru), yas, getMeslekAdi(meslek),
                basePrice, discountRate * 100, formatExtra, finalPrice);
    }

    // --- Kullanıcıdan Veri Alınması (Döngü Kullanılmadan) ---

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       SinemaBileti system = new SinemaBileti();

        System.out.println(" Sinema Bileti Fiyat Hesaplama Sistemi (Döngüsüz Versiyon)");
        System.out.println("-------------------------------------------------------");

        // Kullanıcı Girişleri (Tek Seferde Alınır)
        System.out.println("Lütfen tüm bilgileri aşağıdaki formatlara uygun girin.");

        System.out.println("\n1. Gün Seçimi (1=Pzt, 2=Sal, ..., 7=Paz):");
        System.out.print(" -> Gün numarasını girin (1-7): ");
        int gun = scanner.nextInt();

        System.out.println("\n2. Seans Saat Seçimi (Örn: 11, 14):");
        System.out.print(" -> Tam saatini girin (0-23): ");
        int saat = scanner.nextInt();

        System.out.println("\n3. Yaş Girişi:");
        System.out.print(" -> Yaşınızı girin: ");
        int yas = scanner.nextInt();

        System.out.println("\n4. Meslek Seçimi (1=Öğrenci, 2=Öğretmen, 3=Diğer):");
        System.out.print(" -> Meslek numarasını girin (1-3): ");
        int meslek = scanner.nextInt();

        System.out.println("\n5. Film Türü Seçimi (1=2D, 2=3D, 3=IMAX, 4=4DX):");
        System.out.print(" -> Film türü numarasını girin (1-4): ");
        int filmTuru = scanner.nextInt();

        System.out.println("\n=======================================================");

        // Nihai Hesaplama
        System.out.println(system.generateTicketInfo(gun, saat, yas, meslek, filmTuru));

        scanner.close();
    }
}
