import java.util.ArrayList;

public class NPC {
  private Game game;
  private String name;
  private ArrayList<String> catchphrases;
  private Room location;
  private boolean wandering;

  public NPC(Game game, String name, Room initialLocation, boolean wandering, String ... catchphrases) {
    this.game = game;
    this.name = name;
    this.catchphrases = new ArrayList<String>();
    this.wandering = wandering;
    this.location = initialLocation;

    for (String i : catchphrases) {
      addCatchphrase(i);
    }
  }

  public String getName() { return name; }

  public void addCatchphrase(String catchphrase) { catchphrases.add(catchphrase); }

  public Room getLocation() { return location; }
  public void setLocation(Room location) { this.location = location; }

  public void tick() {
    if (game.getPlayer().getLocation() == location && Math.random() < 0.5) {
      talk();
    }

    if (wandering) {
      wander();
    }
  }

  private void wander() {
    if (location.getRandomExit() != null && Math.random() < 0.25) {
      Exit exit = location.getRandomExit();

      // playerLocation is the location of the actual player, not this NPC.
      Room playerLocation = game.getPlayer().getLocation();

      if (playerLocation == location) {
        System.out.println(name + " just left to the " + exit.getDirection());
      } else if (playerLocation == exit.getDestination()) {
        System.out.println(name + " just entered the room.");
      }

      location = exit.getDestination();
    }
  }

  private void talk() {
    if (!catchphrases.isEmpty()) {
      int index = (int) (Math.random() * catchphrases.size());
      String randomCatchphrase = catchphrases.get(index);
      System.out.println(name + " says: " + randomCatchphrase);
    }
  }

  public String toString() { return name; }
}