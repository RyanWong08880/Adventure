public class Combatant {
  private final String name;
  private final String possessivePronoun;
  private int health;
  private int magic;
  private Weapon weapon;
  private final int maxHealth;

  public Combatant(String name, String possessivePronoun) {
    this.name = name;
    this.possessivePronoun = possessivePronoun;
    health = Random.randomInt(20, 30);
    maxHealth = health;
    magic = Random.randomInt(10, 20);
  }

  public void wield(Weapon weapon) {
    if (this.weapon != weapon) {
      this.weapon = weapon;
      String wieldConjugated = name.equals("You") ? "wield" : "wields";
      System.out.println(name + " " + wieldConjugated + " " + possessivePronoun + " " + weapon + "!");
    }
  }

  public String getName() {
    return name;
  }

  public void printStats() {
    System.out.println("<" + name + "> Health: " + health + " Magic: " + magic);
  }

  public boolean isAlive() {
    return health > 0;
  }

  public void takeDamage(int damage) {
    health -= damage;
  }

  public void heal() {
    if (magic >= 3) {
      health += Random.randomInt(1, 6);
      magic -= 3;
      if (health > maxHealth) {
        health = maxHealth;
      }
      System.out.println("Health has been increased to " + health);
    } else {
      System.out.println("ERROR! NOT ENOUGH MAGIC");
    }
  }

  public void attack(Combatant target) {
    if (weapon == null) {
      System.out.println(name + " has no weapon to attack!");
      return;
    }

    int damage = weapon.calcDamage();
    if (damage == 0) {
      System.out.println(name + " missed with " + possessivePronoun + " " + weapon + ".");
      return;
    }

    target.takeDamage(damage);
    String targetName = target.getName().equals("You") ? "you" : target.getName();
    System.out.println(name + " hit " + targetName + " with " + possessivePronoun + " " + weapon
        + " for " + damage + " health!");
  }
}