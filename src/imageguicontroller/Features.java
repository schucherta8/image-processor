package imageguicontroller;

import imageprocessor.Designs;
import imageprocessor.Filters;

/**
 * The Features interface represents all of the features an Image Processor model should
 * support for a GUI interface View implementation.
 *
 * <p>These features include: functions for loading and saving an image, applying filters and
 * transformations, generating designs, retrieving a modified image, and processing
 * instructions from a text file.</p>
 */
public interface Features {

  /**
   * The loadImage method takes in a filepath defined in its parameter and returns a 3-D int[][][]
   * array that represents the RGB values of each pixel in the image.
   *
   * @param filepath the filepath from which the image should be read.
   * @return a 3-D int[][][] array representing the RGB values of each pixel in the image.
   * @throws IllegalArgumentException if the file cannot be loaded.
   */
  int[][][] loadImage(String filepath);

  /**
   * The processTextInstructions method takes in a Readable object containing text instructions
   * for processing an image, and completes all of the processing.
   *
   * <p>The method does not return anything but will store the modified image in the model, and
   * will display the image result of the processing in the GUI.</p>
   *
   * @param textFile the Readable text file that stores the text instructions to be processed.
   */
  void processTextInstructions(Readable textFile);

  /**
   * The saveImage method takes in a filepath and saves an image-type file to that filepath.
   *
   * <p>The image to be saved must be stored in a 3-D int[][][] array to be properly processed
   * for saving into an image file type. This method uses the ImageUtils class provided by
   * Prof. Shesh to make the conversion.</p>
   *
   * @param filepath the address where the image file should be saved.
   * @throws IllegalArgumentException if the file cannot be saved to the specified address.
   */
  void saveImage(String filepath);

  /**
   * The getModifiedImage method retrieves the most recently modified version of an image made
   * by a Model.
   *
   * @return a 3-D int[][][] array representing the modified image made by the Model.
   */
  int[][][] getModifiedImage();

  /**
   * The applyFilter method takes in a Filter enum and applies the specified filter to an
   * image.
   *
   * @param filters the filter type that should be applied to the image.
   */
  void applyFilter(Filters filters);

  /**
   * The mosaic method applies a mosaic filter to an image.
   *
   * <p>The method takes in no parameters, but will invoke a pop-up to ask the user how many
   * seeds they wish to allocate in the mosaic.</p>
   */
  void mosaic();

  /**
   * The undo method undoes the most recent image filter or transformation and restores the
   * prior image. It displays the prior image in the View.
   *
   * <p>The undo method can be pressed any number of times until there are no more moves to
   * undo. If there are no moves to undo, an error will pop up saying "No moves to undo!"
   * Users may repeat undo-redo operations as many times as they want. Any undo move
   * pushes the current image onto the redo stack of commands, and vice versa.
   * If a user saves an image after some processing, the user can still undo the moves after
   * the save is complete. </p>
   */
  void undo();

  /**
   * The redo method redoes the most recent undo operation and restores the image with that
   * operation applied. It displays the restored image in the View.
   *
   * <p>The redo method can be pressed any number of times until there are no more moves to
   * redo. If there are no moves to redo, an error will pop up saying "No moves to redo!"
   * Users may repeat undo-redo operations as many times as they want. Any redo move pushes
   * the current image onto the undo stack of commands, and vice versa. If a user saves an
   * image after some undos, the user can still redo the moves after the save is complete.</p>
   */
  void redo();

  /**
   * The generateImage method creates a new image based on a Design specified in its parameter.
   *
   * <p>The method does not call for additional information in its parameters, but will invoke
   * a pop-up in the GUI to ask the user to specify the dimensions of the design to be created.</p>
   *
   * @param design the type of design to be generated.
   */
  void generateImage(Designs design);
}
