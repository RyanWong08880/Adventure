import java.util.*;

public class Player {
  private Room location;
  private ArrayList<Item> inventory = new ArrayList<Item>();

  public Player(Room initialLocation) {
    location = initialLocation;
  }

  public void lookAround() {
    location.describe();
  }

  public void addItemToInventory(String itemName) {
    if (getItemByName(itemName) != null) {
      System.out.println("Item is already in inventory");
    } else if (location.getItemByName(itemName) == null) {
      System.out.println("Item does not exist or is not in the current room.");
    } else {
      inventory.add(location.getItemByName(itemName));
      location.removeItem(location.getItemByName(itemName));
      System.out.println("Item picked up.");
    }
    
  }

  public void dropItemFromInventory(String itemName) {
    boolean found = false;
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i).getName().equals(itemName)) {
        found = true;
        location.addItem(inventory.get(i));
        inventory.remove(i);
      }
    }
    System.out.println(found? "Item dropped." : "Item is not in inventory.");
    
  }

  public void listInventory() {
    ArrayList<String> temp = new ArrayList<String>();
    for (Item i : inventory) {
      temp.add(i.getName());
    }
 
    for (int i = 0; i < temp.size() - 1; i++){

      int min = i;
      for (int j = i+1; j < temp.size(); j++) {
        if (temp.get(j).compareTo(temp.get(min)) < 0) {
          min = j;
        }
      }
      
      String tempp = temp.get(min);
      temp.set(min, temp.get(i));
      temp.set(i, tempp);
    }


    

    System.out.println(temp);
  }

  @Deprecated
  class Sorter implements Comparator<Item> {
    public int compare(Item a, Item b) {
      return a.getName().compareTo(b.getName());
    }
  }

  public void examine (String itemName) {
    if (location.getItemByName(itemName) != null) {
      System.out.println(location.getItemByName(itemName).getDescription());
      return;
    }
    
    if (getItemByName(itemName) != null) {
      System.out.println(getItemByName(itemName).getDescription());
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

  public Room getLocation() { return location; }
  public void setLocation(Room location) { this.location = location; }
}
