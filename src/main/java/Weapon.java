public class Weapon {
  private final String name;
  private final double hitProbability;
  private final int minDamage;
  private final int maxDamage;

  public Weapon(String name, double hitProbability, int minDamage, int maxDamage) {
    this.name = name;
    this.hitProbability = hitProbability;
    this.minDamage = minDamage;
    this.maxDamage = maxDamage;
  }

  public String toString() {
    return name;
  }

  public int calcDamage() {
    if (Math.random() < hitProbability) {
      return Random.randomInt(minDamage, maxDamage);
    }
    return 0;
  }
}