//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Fish Tank
// Course: CS 300 Fall 2021
//
// Author: Hyunmin Park
// Email: hpark377@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
////////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class makes a fish tank that contain decoration and buttons and add or remove tank objects
 */
public class FishTank extends PApplet {

  private PImage backgroundImage; // PImage object which represents the background image
  protected ArrayList<TankListener> objects; // list storing interactive objects
  protected Random randGen; // Generator of random numbers
  private TankObject flower;
  private TankObject log;
  private TankObject shell;
  private TankObject ship;

  /**
   * sets the size of this PApplet to 800 width x 600 height
   */
  @Override
  public void settings() {
    size(800, 600);
  }

  /**
   * Defines initial environment properties such as screen size and
   * loads the background image and fonts as the program starts.
   * It also initializes all data fields.
   * 
   */
  @Override
  public void setup() {
    // Set and display the title of the display window
    this.getSurface().setTitle("Fish Tank 3000");
    // Set the location from which images are drawn to CENTER
    this.imageMode(PApplet.CENTER);
    // Set the location from which rectangles are drawn.
    this.rectMode(PApplet.CORNERS);
    // rectMode(CORNERS) interprets the first two parameters of rect() method
    // as the location of one corner, and the third and fourth parameters as
    // the location of the opposite corner.
    // rect() method draws a rectangle to the display window

    this.focused = true; // Confirms that our Processing program is focused,
    // meaning that it is active and will accept mouse or keyboard input.

    // sets the text alignment to center
    this.textAlign(PApplet.CENTER, PApplet.CENTER);

    // TODO load the background image and store the loaded image to backgroundImage
    // Note that you can call the loadImage() method directly (this.loadImage())
    this.backgroundImage = this.loadImage("images" + File.separator + "background.png");


    // TODO create an empty array list of objects
    this.objects = new ArrayList<>();

    // TODO set randGen to the reference of a new Random objects
    this.randGen = new Random();
    
    //sets the static field tank  
    TankObject.setProcessing(this);
    Button.setProcessing(this);
    
    this.flower = new TankObject(430, 60, "images" + File.separator + "flower.png");
    this.log = new TankObject(580, 470, "images" + File.separator + "log.png");
    this.shell = new TankObject(65, 520, "images" + File.separator + "shell.png");
    this.ship = new TankObject(280, 535, "images" + File.separator + "ship.png");
    
    //add decorations inside array list
    this.objects.add(flower);
    this.objects.add(log);
    this.objects.add(shell);
    this.objects.add(ship);
    // add 2 black fish 
    this.objects.add(new BlackFish(log, flower));
    this.objects.add(new BlackFish(shell, flower));
    this.objects.add(new AddBlueFishButton(43, 16));
    this.objects.add(new AddOrangeFishButton(129, 16));
    this.objects.add(new AddYellowFishButton(215, 16));
    this.objects.add(new ClearTankButton(301, 16));
    
  }
  
  /**
   * Continuously draws and updates the application display window
   */
  @Override
  public void draw() {
    // TODO clear the display window by drawing the background image
     this.image(backgroundImage, this.width / 2, this.height / 2);

    // TODO traverse the objects list and draw each of the objects to this display window
    for (int i = 0; i < objects.size(); i++) {
      objects.get(i).draw();  
    }
  }
  
  /**
   * Callback method called each time the user presses the mouse
   */ 
  @Override
  public void mousePressed() {
    // TODO traverse the objects list and call mousePressed method
    // of the first object being clicked in the list
    for(int i = 0; i < objects.size(); i++) {
      if(objects.get(i).isMouseOver()) {
        objects.get(i).mousePressed();
        return;
      }
    }  
  }

  /**
   * Callback method called each time the mouse is released
   */
  @Override
  public void mouseReleased() {
    // TODO traverse the objects list and call each object's mouseReleased() method
    for(int i = 0; i < objects.size(); i++) {
      objects.get(i).mouseReleased();
    }  
  }

  /**
   * adds an instance of TankListener passed as input to the objects array list
   * @param object : the tankObject that will be added in array list
   */
  public void addObject(TankListener object) {
    this.objects.add(object);


  }

  /**
   * Callback method called each time the user presses a key
   */
  @Override
  public void keyPressed() {

    switch (Character.toUpperCase(this.key)) {
      case 'O': //add orange fish
        this.addObject(new Fish());
        break;
        
      case 'Y': //add yellow fish
        this.addObject(new Fish(2, "images" + File.separator + "yellow.png"));
        break;
        
      case 'B': //add blue fish
        this.addObject(new BlueFish());
        break;
        
      
      case 'R': // delete the fish that the mouse is over
        for (int i = 4; i < objects.size(); i++) {
          if (objects.get(i).isMouseOver()){
            objects.remove(i);
            i--;
            break;
          }
        }
        break;
          
      
      case 'S': // start swimming
        for (int i = 0; i < objects.size(); i++) {
          if(objects.get(i) instanceof Fish) {
            ((Fish) objects.get(i)).startSwimming();
          } 
        }
        break;

      case 'X': // freeze (stop swimming)
        for (int i = 0; i < objects.size(); i++) {
          if(objects.get(i) instanceof Fish) {
            ((Fish) objects.get(i)).stopSwimming();
          } 
        }
        break;
        
      case 'C': // Removes instances of the class Fish from this tank
        this.clear();
        break;
    }

  }
  
  /**
   * removes all the instance of the class Fish from the tank
   */
  public void clear() {
    for (int i = 0; i < objects.size(); i++) {
      if(objects.get(i) instanceof Fish) {
        objects.remove(i);
        i--;
      } 
    }
  }

  /**
   * This main method starts the application
   * @param args
   */
  public static void main(String[] args) {
    // The PApplet.main() method takes a String input parameter which represents
    // the name of your PApplet class.
    PApplet.main("FishTank");
  }

}
