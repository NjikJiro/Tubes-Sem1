import java.util.Scanner;

public class RAEVapor{

    Scanner input = new Scanner(System.in);
    Toko toko = new Toko();
}
class User{

    String[] usernames = {"admin", "NjikJiro", "Eru", "Akbar"};
    String[] passwords = {"admin", "powermode", "Eru", "Akbar"};

    public static boolean login(String inputUsername,  String inputPassword) {
        for (int i = 0; i < usernames.length; i++){
            if (inputUsername.equals(usernames[i]) && inputPassword.equals(passwords[i])) {
                return true;
            }
        }
        return false;
    }

}

class Toko{

}