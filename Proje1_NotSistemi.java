Öğrenci No : 250541097
Ad Soyad : Mehmet Kerem Küçük
Tarih : 15.11.2025
 Görev 1 - Sınav Notları Girilen bir öğrencinin raporunu yazdıran program




import java.util.Scanner;


public class NotSistemi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Öğrenci Notları ");
        System.out.println("======================");
        
        //Kullanıcıdan Veri Girişi
        System.out.print("Vize Notu Girin: ");
        double Vize = input.nextDouble();

        System.out.print("Final Notu Girin: ");
        double Final = input.nextDouble();

        System.out.print("Ödev Notu Girin: ");
        double Odev = input.nextDouble();

        //Yazdırma Ve Metot Çağırma
        System.out.println("====================");
        System.out.println("Öğrenci not Raporu");
        System.out.println("====================");
        System.out.println("Vize Notu:  " + Vize);
        System.out.println("Final Notu:  " + Final);
        System.out.println("Ödev Notu: " + Odev);
        System.out.println("__________________________");
        double ortalama = calculateAvarage(Vize, Final, Odev);
        System.out.printf("Ortalama: %.2f \n", ortalama);
        System.out.println("Harf Notu: " + getLetterGrade(ortalama));
        System.out.println("Durum : " + ((isPassingGrade(ortalama) ? "GEÇTİ" : "KALDI")));
        System.out.println("Onur Listesi: " + ((isHonorList(ortalama,Vize,Final,Odev) ? "Evet" : "Hayır ")));
        System.out.println("Bütünleme Hakkı: "+ ((hasRetakeRight(ortalama)? "Yok" : "Var")));



    }
    //Metot Tanmları

    public static double calculateAvarage(double Vize, double Final, double Odev) {
        return Vize * 0.3 + Final * 0.4 + Odev * 0.3;
    }

    public static boolean isPassingGrade(double ortalama) {
        if (ortalama >= 50) {
            return true;

        } else {
            return false;

        }


    }

    public static String getLetterGrade(double ortalama) {
        String harfNotu = "";
        if (ortalama >= 90 && ortalama <= 100) {
            harfNotu = "A";
        } else if (ortalama >= 80 && ortalama < 90) {
            harfNotu = "B";
        } else if (ortalama >= 70 && ortalama < 80) {
            harfNotu = "C";
        } else if (ortalama >= 60 && ortalama < 70) {
            harfNotu = "D";
        } else {
            harfNotu = "F";

        }
        return harfNotu;
    }

    public static boolean isHonorList(double ortalama , double Final , double Odev , double Vize) {
        if (ortalama >= 85 && Final > 70 && Odev > 70 && Vize > 70) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasRetakeRight(double ortalama) {
        if (ortalama >= 40 && ortalama < 50) {
            return true;
        } else {
            return false;
        }
    }

}









