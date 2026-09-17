import java.util.*;

public class Room {
  private String name, description;
  private ArrayList<Exit> exits = new ArrayList<Exit>();
  private ArrayList<Item> items = new ArrayList<Item>();
  private static ArrayList<Room> allRooms = new ArrayList<Room>();


  public Room(String name, String description, @Deprecated Object ... things) {
    this.name = name;
    this.description = description;

    for (int i = 0;  i < things.length; i++) {
      
      
      if (things[i] instanceof String) {
        addExit((String) things[i], (Room) things[i + 1]);
      }

      
      if (things[i] instanceof Item) {
        addItem((Item) things[i]);
      }
    }

    allRooms.add(this);
    
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public Item getItemByName(String name) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  public void addExit(String direction, Room destination) {
    exits.add(new Exit(direction, destination));
  }

  public void describe() {
    System.out.println(name);
    System.out.println();
    System.out.println(description);
    for (Exit exit : exits) {
      System.out.println("You see " + exit.getDestination() + " to the " + exit.getDirection() + ".");
    }
    for (Item item : items) {
      System.out.println("You see a " + item.getName() + " here.");
    }
  }

  public String getName() { return name; }
  public String getDescription() { return description; }

  public Room getExitDestination(String direction) {
    for (Exit exit : exits) {
      if (exit.getDirection().equals(direction)) {
        return exit.getDestination();
      }
    }
    return null;
  }

  public Exit getRandomExit() {
    if (exits.isEmpty()) {
      return null;
    } else {
      return exits.get((int) (Math.random() * exits.size()));
    }
  }


  public static ArrayList<Room> getAllRooms() {
    return new ArrayList<Room>(allRooms);
  }

  public ArrayList<Item> getItems() {
    return new ArrayList<Item>(items);
  }

  
  public String toString() { return name; }
}
