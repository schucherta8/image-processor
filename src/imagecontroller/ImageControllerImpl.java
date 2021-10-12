package imagecontroller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import imageprocessor.Designs;
import imageprocessor.Filters;
import imageprocessor.ImageModelExtension;
import imageprocessor.ImageFactory;
import imageprocessor.ImageUtil;

/**
 * This class represents a Controller in a Model-View-Controller design for an Image Processor.
 * The class implements the ImageController interface and tells the Model what to do based on
 * input from the View, and changes the View when appropriate.
 *
 * <p>The controller acts a mediator between the ImageModel and View. It calls the View to
 * retrieve the next command from the user, and executes the command parsed along with the
 * necessary arguments. The program will terminate if an error is produced by the input or if
 * the View returns a Quit command.</p>
 */
public class ImageControllerImpl implements ImageControllerExtension {

  /**
   * The attribute model is an ImageModelExtension instantiation, which will be the model
   * in the MVC implementation of the Image Processor program.
   */
  private ImageModelExtension model;

  /**
   * The attribute view is a View instantiation, which will be the view in the MVC
   * implementation of the Image Processor program.
   */
  private Readable input;

  /**
   * The constructor for ImageControllerImpl takes in two parameters, an ImageModel model
   * implementation and a View implementation, and constructs a new ImageControllerImpl that
   * will interact with those model and view objects.
   *
   * @param model the ImageModelExtension implementation to be used in the current MVC design.
   * @param input the View implementation to be used in the current MVC design.
   */
  public ImageControllerImpl(ImageModelExtension model, Readable input) {
    this.model = model;
    this.input = input;
  }

  /**
   * In the start() method, the controller requests information from View and then
   * manipulates the Model. The program will terminate if the View delivers an input of Quit,
   * or if the input from the View is null.
   *
   * @throws IllegalArgumentException if there is an error in formatting or reading the input.
   */
  @Override
  public void start() throws IllegalArgumentException {
    Scanner sc = new Scanner(input);
    try {
      while (sc.hasNext()) {
        String commandString = sc.next();
        Commands command;
        try {
          command = Commands.valueOf(commandString.toUpperCase());
        }
        catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Error: Not a valid command.");
        }
        switch (command) {
          case LOAD:
            loadFile(sc);
            break;
          case APPLY:
            applyFilters(sc);
            break;
          case GENERATE:
            generateDesignType(sc);
            break;
          case SAVE:
            saveFile(sc);
            break;
          default:
            throw new IllegalArgumentException("Error: Not a valid command.");
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: File is empty.");
    }
    sc.close();
  }

  /**
   * The loadFile method acts as a helper method to the Controller in loading an image.
   *
   * <p>It reads the image into a 3-D int[][][] array using the ImageUtil class and establishes a
   * new model using ImageFactory.</p>
   *
   * @param input the Scanner input to be converted into a 3-D array; most likely a file path.
   * @throws IllegalArgumentException if the file cannot be loaded.
   */
  private void loadFile(Scanner input) throws IllegalArgumentException {
    try {
      int[][][] image = ImageUtil.readImage(input.next());
      model = ImageFactory.createImage(image);
    } catch (NoSuchElementException | IOException e) {
      throw new IllegalArgumentException("Error: Cannot load file.");
    }
  }

  /**
   * The applyFilters method acts as a helper method to the Controller. It reads the filter type
   * that the user would like to apply, then tells the model to apply the filter.
   *
   * <p>The method will throw an exception if a user tries to apply a filter without a loaded
   * file; specifically, it will throw "Error: Image not found."</p>
   *
   * @param input the user's input; this should include the filter type.
   * @throws IllegalArgumentException if the filter type does not exist or image does not exist.
   */
  private void applyFilters(Scanner input) throws IllegalArgumentException {
    try {
      String filter = input.next();
      if (filter.equalsIgnoreCase("MOSAIC")) {
        model.mosaic(input.nextInt());
      }
      else {
        model.applyFilter(Filters.valueOf(filter.toUpperCase()));
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: Missing filter type.");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Error: Image not found.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error: Not a valid filter type.");
    }
  }

  /**
   * The generateDesignType method acts as a helper method to the Controller in creating Designs.
   *
   * <p>It matches the type of design the user wishes to generate with an available Design pattern,
   * and tells the model to construct that Design with the given parameters.</p>
   *
   * @param input the input from the user; this should contain Design type and parameters.
   * @throws IllegalArgumentException if the Design parameters are invalid.
   */
  private void generateDesignType(Scanner input) throws IllegalArgumentException {
    try {
      model = ImageFactory.generateImage(Designs.valueOf(input.next().toUpperCase()),
              input.nextInt(), input.nextInt());
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: Missing design type, height, or width "
              + "dimension.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error: Not a valid design type.");
    }
  }

  /**
   * The saveFile method acts as a helper method to the Controller in saving out an image file.
   *
   * <p>It calls upon the ImageUtil class to write the image based on the modified image
   * obtained from the model.</p>
   *
   * @param input the user's input; this should contain the desired filepath of the outfile.
   * @throws IllegalArgumentException if the image or filepath cannot be found.
   */
  private void saveFile(Scanner input) throws IllegalArgumentException {
    try {
      ImageUtil.writeImage(model.getModifiedImage(),model.getModifiedImage()[0].length,
              model.getModifiedImage().length,input.next());
    } catch (NoSuchElementException | IOException e) {
      throw new IllegalArgumentException("Error: File path could not be written to.");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Error: Image not found.");
    }
  }

  /**
   * The getModel() method retrieves the model that this controller acts upon.
   *
   * @return an ImageModelExtension instantiation, the model this controller acts upon.
   */
  @Override
  public ImageModelExtension getModel() {
    return this.model;
  }
}