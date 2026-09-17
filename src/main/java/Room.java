import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Room {
  private static final List<String> DIRECTION_ORDER = List.of("north", "south", "east", "west");
  private static final Map<String, Integer> DIRECTION_INDEX = createDirectionIndex();
  private static final List<Room> ALL_ROOMS = new ArrayList<>();
  private static int[][] adjacencyMatrix = new int[0][0];

  private final String name;
  private final String description;
  private final Map<String, Room> exits = new HashMap<>();
  private final List<Item> items = new ArrayList<>();

  public Room(String name, String description, Object... things) {
    this.name = name;
    this.description = description;
    registerRoom(this);

    if (things == null) {
      return;
    }

    for (int i = 0; i < things.length; i++) {
      if (things[i] instanceof String) {
        if (i + 1 < things.length && things[i + 1] instanceof Room) {
          addExit((String) things[i], (Room) things[i + 1]);
          i++;
        }
      } else if (things[i] instanceof Item) {
        addItem((Item) things[i]);
      }
    }
  }

  private static Map<String, Integer> createDirectionIndex() {
    Map<String, Integer> index = new HashMap<>();
    for (int i = 0; i < DIRECTION_ORDER.size(); i++) {
      index.put(DIRECTION_ORDER.get(i), i);
    }
    return index;
  }

  private static void registerRoom(Room room) {
    if (room == null || ALL_ROOMS.contains(room)) {
      return;
    }

    ALL_ROOMS.add(room);
    int[][] expandedMatrix = new int[ALL_ROOMS.size()][ALL_ROOMS.size()];
    for (int row = 0; row < adjacencyMatrix.length; row++) {
      System.arraycopy(adjacencyMatrix[row], 0, expandedMatrix[row], 0, adjacencyMatrix[row].length);
    }
    adjacencyMatrix = expandedMatrix;
  }

  private void updateAdjacencyMatrix(Room destination) {
    int sourceIndex = ALL_ROOMS.indexOf(this);
    int destinationIndex = ALL_ROOMS.indexOf(destination);
    if (sourceIndex >= 0 && destinationIndex >= 0) {
      adjacencyMatrix[sourceIndex][destinationIndex] = 1;
      adjacencyMatrix[destinationIndex][sourceIndex] = 1;
    }
  }

  public static String normalizeDirection(String direction) {
    if (direction == null) {
      return "";
    }
    return direction.trim().toLowerCase(Locale.US);
  }

  public void addItem(Item item) {
    if (item != null) {
      items.add(item);
    }
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
    if (destination == null) {
      return;
    }

    String normalizedDirection = normalizeDirection(direction);
    if (normalizedDirection.isEmpty()) {
      return;
    }

    registerRoom(destination);
    exits.put(normalizedDirection, destination);
    updateAdjacencyMatrix(destination);
  }

  public void describe() {
    System.out.println(name);
    System.out.println();
    System.out.println(description);

    for (Map.Entry<String, Room> entry : exits.entrySet()) {
      System.out.println("You see " + entry.getValue().getName() + " to the " + entry.getKey() + ".");
    }

    for (Item item : items) {
      System.out.println("You see a " + item.getName() + " here.");
    }
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Room getExitDestination(String direction) {
    return exits.get(normalizeDirection(direction));
  }

  public boolean hasExit(String direction) {
    return exits.containsKey(normalizeDirection(direction));
  }

  public Exit getRandomExit() {
    if (exits.isEmpty()) {
      return null;
    }

    List<String> directions = new ArrayList<>(exits.keySet());
    String randomDirection = directions.get((int) (Math.random() * directions.size()));
    return new Exit(randomDirection, exits.get(randomDirection));
  }

  public static ArrayList<Room> getAllRooms() {
    return new ArrayList<>(ALL_ROOMS);
  }

  public static int[][] getAdjacencyMatrix() {
    int[][] copy = new int[adjacencyMatrix.length][];
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      copy[i] = adjacencyMatrix[i].clone();
    }
    return copy;
  }

  public ArrayList<Item> getItems() {
    return new ArrayList<>(items);
  }

  public String toString() {
    return name;
  }
}
