import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
  private Room location;
  private final List<Item> inventory = new ArrayList<>();

  public Player(Room initialLocation) {
    this.location = initialLocation;
  }

  public void lookAround() {
    location.describe();
  }

  public void addItemToInventory(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      System.out.println("Please specify an item name.");
      return;
    }

    if (getItemByName(itemName) != null) {
      System.out.println("Item is already in inventory");
      return;
    }

    Item roomItem = location.getItemByName(itemName);
    if (roomItem == null) {
      System.out.println("Item does not exist or is not in the current room.");
      return;
    }

    inventory.add(roomItem);
    location.removeItem(roomItem);
    System.out.println("Item picked up.");
  }

  public void dropItemFromInventory(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      System.out.println("Please specify an item name.");
      return;
    }

    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i).getName().equalsIgnoreCase(itemName)) {
        Item dropped = inventory.remove(i);
        location.addItem(dropped);
        System.out.println("Item dropped.");
        return;
      }
    }

    System.out.println("Item is not in inventory.");
  }

  public void listInventory() {
    if (inventory.isEmpty()) {
      System.out.println("Your inventory is empty.");
      return;
    }

    List<String> itemNames = new ArrayList<>();
    for (Item item : inventory) {
      itemNames.add(item.getName());
    }
    Collections.sort(itemNames);
    System.out.println(itemNames);
  }

  public void examine(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      System.out.println("Please specify an item name.");
      return;
    }

    Item itemInRoom = location.getItemByName(itemName);
    if (itemInRoom != null) {
      System.out.println(itemInRoom.getDescription());
      return;
    }

    Item itemInInventory = getItemByName(itemName);
    if (itemInInventory != null) {
      System.out.println(itemInInventory.getDescription());
      return;
    }

    System.out.println("Item not found in inventory or room.");
  }

  public void move(String direction) {
    Room destination = location.getExitDestination(direction);
    if (destination == null) {
      System.out.println("You can't move in that direction.");
    } else {
      location = destination;
    }
  }

  public Item getItemByName(String name) {
    for (Item item : inventory) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }

  public Room getLocation() {
    return location;
  }

  public void setLocation(Room location) {
    this.location = location;
  }
}
