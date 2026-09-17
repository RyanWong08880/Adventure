public class Random {
  public static int randomInt(int minValue, int maxValue) {
    return minValue + (int) (Math.random() * (maxValue - minValue + 1));
  }
}