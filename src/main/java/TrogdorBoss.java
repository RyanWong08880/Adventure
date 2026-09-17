import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrogdorBoss {
  private final Scanner scanner;
  private final Combatant trogdor = new Combatant("Trogdor", "his");
  private final Combatant player = new Combatant("You", "your");
  private final List<Weapon> trogdorsWeapons = new ArrayList<>();
  private final List<Weapon> playersWeapons = new ArrayList<>();

  public TrogdorBoss(Scanner scanner) {
    this.scanner = scanner;
    setupWeapons();
  }

  private void setupWeapons() {
    trogdorsWeapons.add(new Weapon("burninating breath", 0.3, 3, 12));
    trogdorsWeapons.add(new Weapon("scaly tail", 0.2, 1, 6));
    trogdorsWeapons.add(new Weapon("beefcake fist", 0.5, 4, 8));
    trogdorsWeapons.add(new Weapon("EXTRA ULTIMATE ATTACK", 0.1, 20, 50));

    playersWeapons.add(new Weapon("wooden sword", 0.6, 1, 3));
    playersWeapons.add(new Weapon("stone sword", 0.6, 4, 6));
    playersWeapons.add(new Weapon("iron sword", 0.6, 7, 9));
    playersWeapons.add(new Weapon("diamond sword", 0.6, 10, 12));
    playersWeapons.add(new Weapon("axe", 0.4, 5, 20));
    player.wield(playersWeapons.get(0));
  }

  public boolean fight() {
    System.out.println("You are facing Trogdor the Burninator in mortal combat!");
    System.out.println("Only you can save the townspeople from being burninated!");

    while (player.isAlive() && trogdor.isAlive()) {
      player.printStats();
      trogdor.printStats();
      System.out.print("> ");

      if (!scanner.hasNextLine()) {
        System.out.println("The battle ends because no combat command was provided.");
        return false;
      }

      String command = scanner.nextLine().trim().toLowerCase();
      if (command.equals("quit")) {
        System.out.println("Trogdor laughs as you flee in terror!");
        return false;
      }

      if (command.equals("attack")) {
        player.attack(trogdor);
      } else if (command.equals("heal")) {
        player.heal();
      } else if (command.equals("wield")) {
        wield();
      } else if (command.equals("help")) {
        System.out.println("Commands include attack, heal, wield, quit");
        continue;
      } else {
        System.out.println("I didn't understand! Type help for commands.");
        continue;
      }

      if (!trogdor.isAlive()) {
        System.out.println("You have killed Trogdor the Burninator!");
        System.out.println("The townspeople emerge from their thatched-roof cottages to celebrate!");
        return true;
      }

      trogdor.wield(trogdorsWeapons.get(Random.randomInt(0, trogdorsWeapons.size() - 1)));
      trogdor.attack(player);
    }

    System.out.println("Trogdor has killed you! Trogdor burninates the countryside to celebrate!");
    return false;
  }

  private void wield() {
    System.out.println("Enter the slot of the weapon you want to wield (1-" + playersWeapons.size() + "):");
    System.out.println(playersWeapons);

    if (!scanner.hasNextLine()) {
      System.out.println("No weapon slot was provided.");
      return;
    }

    String input = scanner.nextLine().trim();
    try {
      int slot = Integer.parseInt(input) - 1;
      if (slot >= 0 && slot < playersWeapons.size()) {
        player.wield(playersWeapons.get(slot));
      } else {
        System.out.println("INVALID SLOT");
      }
    } catch (NumberFormatException exception) {
      System.out.println("INVALID SLOT");
    }
  }
}