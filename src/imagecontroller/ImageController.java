package imagecontroller;

/**
 * The ImageController interface is an interface containing a method to begin a Controller
 * implementation. This interface is best used in conjunction with a Model and a View in a
 * Model-View-Controller design.
 */
public interface ImageController {


  /**
   * The start() method gives the control of the MVC flow to the Controller. The Controller
   * only relinquishes control when the program ends.
   */
  void start();

}
