import java.text.DecimalFormat;
import java.util.Scanner;

public class RAEVapor{
    public static void main(String[] args){

        String[] namaKami = {"Adyadma Renjiro", "Putra Erlang Duawa", "Alif Akbar Ramadhan"};
        String[] nimKami = {"103082400013", "103082400023", "103082400035"};
        System.out.println("------------------------------------------");
        System.out.printf("%-4s %-24s %-15s%n", "No.", "Nama", "NIM");
        System.out.println("------------------------------------------");
        for (int i = 0; i < namaKami.length; i++) {
            System.out.printf("%-4s %-24s %-15s%n", 
                (i + 1),
                namaKami[i],
                nimKami[i]
            );
        }

        Scanner input = new Scanner(System.in);
        Store store = new Store();

        System.out.println("\n====== RAE Vapor ======");

        // Login
        boolean isLogin = false;
        while(!isLogin){
            System.out.print("Masukkan Username : ");
            String inputUsername = input.nextLine();
            System.out.print("Masukkan Password : ");
            String inputPassword = input.nextLine();

            if (User.login(inputUsername, inputPassword)) {
                System.out.println("Login Berhasil");
                 System.out.println("----------------");
                isLogin = true;
            } else {
                System.out.println("Username atau Password anda salah, silahkan coba lagi!");
                System.out.println("----------------\n");
            }
        }

        // Menu Utama
        boolean isRunning = false;
        if (isLogin) {
            isRunning = true;
        }
        while(isRunning){
            System.out.println("\nMenu Utama");
            System.out.println("1. Membeli Barang");
            System.out.println("2. Melihat Riwayat Transaksi");
            System.out.println("3. Log Out");

            System.out.print("Pilih menu: ");
            int menu = input.nextInt();

            switch(menu) {

                // Membeli Barang
                case 1 -> {
                    store.tampilkanBarang();

                    System.out.print("Pilih nomor barang: ");
                    int pilihan = input.nextInt();
                    System.out.print("Jumlah barang: ");
                    int jumlah = input.nextInt();

                    store.beliBarang(pilihan, jumlah, input);
                }

                // Menampilkan Riwayat Transaksi
                case 2 -> store.tampilkanRiwayatTransaksi();

                // Keluar Aplikasi
                case 3 -> {
                    System.out.println("Anda berhasil keluar! Terima Kasih :)");
                    isRunning = false;
                }

                default -> System.out.println("Menu tidak valid! Coba lagi!");
            }
        }
    }
}


class User{

    // Atribut User
    static String[] usernames = {"admin", "NjikJiro", "Eru", "Akbar"};
    static String[] passwords = {"admin", "powermode", "Eru", "Akbar"};

    // Method Login User
    public static boolean login(String inputUsername,  String inputPassword) {
        for (int i = 0; i < usernames.length; i++){
            if (inputUsername.equals(usernames[i]) && inputPassword.equals(passwords[i])) {
                return true;
            }
        }
        return false;
    }

}

class Store{

    DecimalFormat formatter = new DecimalFormat("#,###");

    // Atribut Store
    String[] namaBarang = {"liquid Freebase Creamy", "liquid Freebase Fruity", "liquid Saltnic Fruity & Creamy", "Device AIO", "Device MOD", "Device POD"};
    int[] hargaBarang = {140000, 130000, 110000, 650000, 500000, 300000};
    int[] stokBarang = {30, 30, 30, 15, 15, 15};

    // Jumlah Riwayat Transaksi
    String[][] riwayatTransaksi = new String[35][5];
    int jumlahTransaksi = 0;

    // Method Menampilkan Barang
    public void tampilkanBarang(){
        System.out.println("\nDaftar Barang");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-5s %-30s %-15s %-6s%n", "No", "Nama", "Harga(Rp.)", "Stok");
        for (int i = 0; i < namaBarang.length; i++ ) {
            System.out.printf("%-5s %-30s %-15s %-6s%n",
                (i+1),
                namaBarang[i],
                formatter.format(hargaBarang[i]),
                stokBarang[i]
            );
        }
        System.out.println("-----------------------------------------------------------");
    }

    // Method Beli Barangg
    public boolean beliBarang(int pilihan, int jumlah, Scanner input){

        // Cek apakah barang yang dipilih itu ada
        if (pilihan < 1 || pilihan > namaBarang.length) {
            System.out.println("Barang tidak tersedia!");
            return false;
        }

        int index = pilihan - 1;
        int totalHarga = jumlah * hargaBarang[index];

        if (stokBarang[index] >= jumlah) {

            System.out.println("Total yang harus dibayar adalah : Rp. " + formatter.format(totalHarga));
            System.out.print("Masukkan uang pembayaran: ");
            int uangDiberikan = input.nextInt();

            // Cek apakah uang yang diberikan itu cukup
            if (uangDiberikan >= totalHarga) {
                stokBarang[index] -= jumlah;
                int kembalian = uangDiberikan - totalHarga;

                // Memasukkan ke dalam riwayat transaksi
                riwayatTransaksi[jumlahTransaksi][0] = namaBarang[index];
                riwayatTransaksi[jumlahTransaksi][1] = String.valueOf(jumlah);
                riwayatTransaksi[jumlahTransaksi][2] = "Rp. " + formatter.format(totalHarga);
                riwayatTransaksi[jumlahTransaksi][3] = "Rp. " + formatter.format(uangDiberikan);
                riwayatTransaksi[jumlahTransaksi][4] = "Rp. " + formatter.format(kembalian);
                jumlahTransaksi++;

                // Bukti pembelian / struk
                System.out.println("\nTransaksi Berhasil!");
                System.out.println("======= STRUK TRANSAKSI =======");
                System.out.println("Barang      : " + namaBarang[index]);
                System.out.println("Jumlah      : " + jumlah);
                System.out.println("Total Harga : Rp. " + formatter.format(totalHarga));
                System.out.println("Uang Dibayar: Rp. " + formatter.format(uangDiberikan));
                System.out.println("Kembalian   : Rp. " + formatter.format(kembalian));
                System.out.println("===============================");

                return true;
            } else {
                System.out.println("Uang anda tidak cukup! Transaksi dibatalkan.");
                return false;
            }
            
        } else {
            System.out.println("Stok tidak mencukupi! Transaksi dibatalkan.");
            return false;
        }
    }

    // Method tampilkan riwayat transaksi
    public void tampilkanRiwayatTransaksi(){
        if (jumlahTransaksi == 0) {
            System.out.println("---------------------------");
            System.out.println("Belum ada riwayat transaksi.");
            System.out.println("---------------------------");
        } else {
            System.out.println("\n========= Riwayat Transaksi =========");
            System.out.printf("%-5s %-34s %-10s %-15s %-15s %-15s%n", 
                "No", "Nama Barang", "Jumlah", "Total Harga", "Uang Dibayar", "Kembalian");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (int i = 0; i < jumlahTransaksi; i++) {
                System.out.printf("%-5d %-34s %-10s %-15s %-15s %-15s%n", 
                    (i + 1), 
                    riwayatTransaksi[i][0], 
                    riwayatTransaksi[i][1],
                    riwayatTransaksi[i][2],
                    riwayatTransaksi[i][3],
                    riwayatTransaksi[i][4]
                );
            }
            System.out.println("---------------------------------------------------------------------------------------------\n");
        }
    }
}
