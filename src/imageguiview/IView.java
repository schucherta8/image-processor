package imageguiview;

import imageguicontroller.Features;

/**
 * The IView interface provides methods that View implementations of a GUI in an Image Processor
 * should support.
 *
 * <p>These methods include a method for establishing the GUI interface (setFeatures), methods
 * for displaying images and errors, and methods for asking for user input via pop-ups for
 * a variety of cases.</p>
 */
public interface IView {

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param features the set of feature callbacks as a Features object
   */
  void setFeatures(Features features);

  /**
   * The displayImage method takes a 3-D int[][][] array of RGB values of an image, and converts
   * it into an image displayed in the GUI interface for a user to see. It returns nothing
   * but shows the user the image in the display.
   *
   * @param image the 3-D int[][][] array to be displayed to the user.
   */
  void displayImage(int[][][] image);

  /**
   * The displayError method takes a String error message and displays a
   * pop-up Error message to the user, with the text for the error message.
   *
   * @param error the error message the pop-up should display.
   */
  void displayError(String error);

  /**
   * The getInput method creates a pop-up window where a user is prompted a message for a specified
   * input.
   *
   * @param message represents the the message displayed to the user.
   * @return a String representing the user's input of how many seeds they wish for.
   */
  String getInput(String message);
}
