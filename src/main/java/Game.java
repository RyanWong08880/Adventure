import java.util.Scanner;
import java.util.ArrayList;

public class Game {
  
  private Room compSciRoom = new Room("Computer Science Classroom", "You are in Room 5, the Computer Science classroom.", new Item("laser pointer", "You see a small white laser pointer with several buttons."));
  private Room hall = new Room("Hallway", "You are in the hall.");
  private Room schoolGrounds = new Room("School Grounds", "You are on the school grounds.");
  private Room office = new Room("School Office", "You are in the school office.");
  private Room missionRoad = new Room("Mission Road", "You are standing in the middle of Mission Road.");
  private Room bartStation = new Room("BART Station", "You are at South San Francisco BART station.");
  private Room usHistoryRoom = new Room("United States History Room", "You are in Room 8, the United States History classrooom.", WEST, hall, new Item("globe", "You see a geographical globe."));
  private Room cafeteria = new Room("Cafeteria", "You are in the El Camino Cafeteria.", WEST, schoolGrounds, new Item("key", "You see a key on the table."));
  private Room secretRoom = new Room("Dark and Scary Room", "You are in a dark and scary room.", new Item ("tracker", "You see a tracker on the floor."));
  
  
  private Player player = new Player(compSciRoom);
  
  public Player getPlayer() { return player; }
  
  private boolean playing = true;

  public Game() {
    wireMap();
    createNPCs();
  }

  private static final String NORTH = "north";
  private static final String WEST  = "west";
  private static final String EAST  = "east";
  private static final String SOUTH = "south";
  
  

  private ArrayList<NPC> npcs = new ArrayList<NPC>();

  private void createNPCs() {
    npcs.add(new NPC(this, "Ms. Molina", compSciRoom, true, "Should I jump on the stage at Bad Bunny?", "What did you think of that quiz?", "There is a unit test coming up!", "Do you feel ready for the AP Exam?"));
    npcs.add(new NPC(this, "Chris", compSciRoom, false, "Can I get a volunteer to answer the question?"));
    npcs.add(new NPC(this, "Gary", compSciRoom, false, "Check out this webcomic about Insertion Sort!"));
    npcs.add(new NPC(this, "Station Agent", bartStation, false, "The train is coming soon.", "The system is experiencing delays."));
    npcs.add(new NPC(this, "Lunch Lady", cafeteria, false, "Hello!"));
    npcs.add(new NPC(this, "Random Pedestrian", missionRoad, false, "Hey! Watch where you're going!"));
    npcs.add(new NPC(this, "Random APCS Student", hall, true, "What did you get on the quiz?"));
    for (int i = 1; i <= 10; i++) {
      npcs.add(new NPC(this, "Monster " + i, secretRoom, true, ">:("));
    }
  }

  private void wireMap() {
    
    compSciRoom.addExit(EAST, hall);

    hall.addExit(WEST, compSciRoom);
    hall.addExit(SOUTH, schoolGrounds);
    hall.addExit(EAST, usHistoryRoom);

    schoolGrounds.addExit(NORTH, hall);
    schoolGrounds.addExit(WEST, missionRoad);
    schoolGrounds.addExit(SOUTH, office);
    schoolGrounds.addExit(EAST, cafeteria);
    
    office.addExit(NORTH, schoolGrounds);

    missionRoad.addExit(EAST, schoolGrounds);
    missionRoad.addExit(WEST, bartStation);

    bartStation.addExit(EAST, missionRoad);
  }

  private void doRoomSpecificActions() {
    if (player.getLocation() == missionRoad) {
      if (Math.random() < 0.1) {
        System.out.println();
        System.out.println("Careful! A speeding car almost hit you!");
        System.out.println("Maybe it's best to get out of the middle of the street!");
      }
    }
    
    if (player.getLocation() == compSciRoom) {
      if (player.getLocation().getExitDestination(WEST) == null) {
        System.out.println("There's a door that leads to a dark and scary room, but it's locked. Hmmmm.");
      }
    }
    
    
  }

  private void doNPCActions() {
    for (NPC npc : npcs) {
      npc.tick();
    }
  }

  private void help() {
    System.out.println("Valid commands are:");
    System.out.println("north, south, east, west, inventory, get, drop, help, quit" + (player.getLocation() == compSciRoom? ", unlock" : "") + (player.getItemByName("tracker") != null? "track" : ""));
  }

  public void play() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to ELCO ADVENTURE!");
    System.out.println("------- -- ---- ----------");
    System.out.println();
    while (playing) {
      player.lookAround();
      
      for (NPC npc :  npcs) {
        if (npc.getLocation() == player.getLocation()) {
          System.out.println("You see " + npc.getName() + " here.");
        }
      }
      
      doRoomSpecificActions();
      doNPCActions();
      
      System.out.print("> ");
      String[] command = scanner.nextLine().split(" ");
      switch(command[0]) {
        case "help": help(); break;
        case "north": player.move(NORTH); break;
        case "south": player.move(SOUTH); break;
        case "east": player.move(EAST); break;
        case "west": player.move(WEST); break;
        case "inventory": player.listInventory(); break;
        case "get": player.addItemToInventory(sum(1, command)); break;
        case "drop": player.dropItemFromInventory(sum(1, command)); break;
        case "examine": player.examine(sum(1, command)); break;
        case "unlock": unlock(); break;
        case "track": track(); break;
        case "quit": playing = false; break;
        default: System.out.println("I don't understand."); break;
      }
      System.out.println();
    }
  }

  public void unlock() {
    if (player.getLocation() != compSciRoom) {
      System.out.println("I don't understand.");
    } else if (player.getItemByName("key") == null) {
      System.out.println("Failed to unlock the door! You might need something to help you open it.");
    } else {
      System.out.println("Door unlocked!");
      compSciRoom.addExit(WEST, secretRoom);
      secretRoom.addExit(EAST, compSciRoom);
    }
      
  }

  public void track() {
    if (player.getItemByName("tracker") == null) {
      System.out.println("You don't have a tracker."); return;
    }

    ArrayList<Room> allRooms = new ArrayList<Room>(Room.getAllRooms());
    for (Room i : allRooms) {
      ArrayList<Item> items = new ArrayList<Item>(i.getItems());
      for (Item j : items) {
        System.out.println(j.getName() + " is in " + i.toString());
      }
      
    }

    System.out.println();


    for (NPC i : npcs) {
      System.out.println(i.getName() + " is in " + i.getLocation().toString());
    }


    System.out.println();
      
    
  }

  public String sum(int startIndex, String[] a) {
    String content = "";
    for (int i = startIndex; i < a.length; i++) {
      content += a[i] + " ";
    }
    return content.substring(0, content.length() - 1);
  }
}
