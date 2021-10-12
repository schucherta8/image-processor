package imageguicontroller;

import imagecontroller.ImageControllerExtension;
import imagecontroller.ImageControllerImpl;
import imageguiview.IView;
import imageprocessor.Designs;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

import imageprocessor.Filters;
import imageprocessor.ImageFactory;
import imageprocessor.ImageModelExtension;
import imageprocessor.ImageUtil;


/**
 *  The Controller class represents a controller in a Model-View-Controller design pattern for
 *  an Image Processor.
 *
 *  <p>The Controller interacts with a model and a view. Rather than directly accessing the
 *  Model's methods, the Controller provides a FeaturesImpl private static class within itself to
 *  cut down direct interaction with the Model. This enables the Controller class to act with all
 *  the methods provided in the Features interface, including loading or saving files, modifying
 *  files, and reading text instructions.</p>
 */
public class Controller {

  /**
   * The Controller constructor establishes a new Controller object with a specified model and view
   * from a user.
   *
   * <p>The Controller may take in a defined model or a null model. The View parameter must be
   * valid; if the parameter is null, the constructor will throw an exception.</p>
   *
   * @param model the Model implementation to be used by this Controller.
   * @param view  the View implementation to be used by this Controller.
   * @throws IllegalArgumentException if the view attribute is null.
   */
  public Controller(ImageModelExtension model, IView view) {
    if (view == null) {
      throw new IllegalArgumentException("The view cannot be null.");
    }
    view.setFeatures(new FeatureImpl(model,view));

  }

  /**
   * The static class FeatureImpl is a class that implements the Features interface. It allows the
   * Controller to call methods typically associated with the Model without trespassing into the
   * Model's domain.
   *
   * <p>FeatureImpl contains a constructor with one attribute, the View, and methods for
   * processing images and commands. FeatureImpl is held within the Controller class, so it has
   * access to Controller's static attributes model, undo, and redo.</p>
   */
  private static class FeatureImpl implements Features {

    /**
     * The first attribute, model, is an ImageModelExtension object representing the model.
     */
    private ImageModelExtension model;

    /**
     * The attribute view is an IView implementation of a view.
     */
    private IView view;

    /**
     * The undo attribute is a stack that holds reverted image representations which can be popped
     * off the stack to restore a prior version of a image.
     */
    private Stack<int[][][]> undo;

    /**
     * The redo attribute is a stack where, when undo is pressed, the most recent image is pushed.
     * The stack holds more recent versions of image representations so that images can be restored
     * after having clicked undo.
     */
    private Stack<int[][][]> redo;

    /**
     * The constructor for FeatureImpl establishes a new FeatureImpl with a view, model, and new
     * undo and redo stacks.
     *
     * @param view the view on which the features should be implemented.
     */
    public FeatureImpl(ImageModelExtension model, IView view) {
      this.model = model;
      this.view = view;
      undo = new Stack<>();
      redo = new Stack<>();
    }

    /**
     * The loadImage method takes in a filepath defined in its parameter and returns a 3-D int[][][]
     * array that represents the RGB values of each pixel in the image.
     *
     * @param filepath the filepath from which the image should be read.
     * @return a 3-D int[][][] array representing the RGB values of each pixel in the image.
     * @throws IllegalArgumentException if the file cannot be loaded.
     */
    @Override
    public int[][][] loadImage(String filepath) {
      try {
        int[][][] image = ImageUtil.readImage(filepath);
        model = ImageFactory.createImage(image);
      } catch (NoSuchElementException | IOException e) {
        view.displayError("Cannot load file.");
      }
      return model.getModifiedImage();
    }

    /**
     * The processTextInstructions method takes in a Readable object containing text instructions
     * for processing an image, and completes all of the processing.
     *
     * <p>The method does not return anything but will store the modified image in the model, and
     * will display the image result of the processing in the GUI.</p>
     *
     * @param textFile the Readable text file that stores the text instructions to be processed.
     */
    @Override
    public void processTextInstructions(Readable textFile) {
      ImageControllerExtension newController = new ImageControllerImpl(model, textFile);
      newController.start();
      model = newController.getModel();
      view.displayImage(model.getModifiedImage());
    }

    /**
     * The saveImage method takes in a filepath and saves an image-type file to that filepath.
     *
     * <p>The image to be saved must be stored in a 3-D int[][][] array to be properly processed
     * for saving into an image file type. This method uses the ImageUtils class provided by Prof.
     * Shesh to make the conversion.</p>
     *
     * @param filepath the address where the image file should be saved.
     * @throws IllegalArgumentException if the file cannot be saved to the specified address.
     */
    @Override
    public void saveImage(String filepath) {
      try {
        ImageUtil.writeImage(model.getModifiedImage(), model.getModifiedImage()[0].length,
                model.getModifiedImage().length, filepath);
        undo.clear();
        redo.clear();
      } catch (NoSuchElementException | IOException e) {
        view.displayError("File path could not be written to.");
      } catch (NullPointerException e) {
        view.displayError("Image not detected.");
      }
    }

    /**
     * The getModifiedImage method retrieves the most recently modified version of an image made by
     * a Model.
     *
     * @return a 3-D int[][][] array representing the modified image made by the Model.
     */
    @Override
    public int[][][] getModifiedImage() {
      return model.getModifiedImage();
    }

    /**
     * The applyFilter method takes in a Filter enum and applies the specified filter to an image.
     *
     * @param filters the filter type that should be applied to the image.
     */
    @Override
    public void applyFilter(Filters filters) {
      try {
        undo.push(this.getModifiedImage());
        model.applyFilter(filters);
        int[][][] image = model.getModifiedImage();
        view.displayImage(image);
      } catch (NullPointerException e) {
        view.displayError("No Image detected.");
      } catch (IllegalArgumentException e) {
        view.displayError(e.getMessage());
      }
    }

    /**
     * The undo method undoes the most recent image filter or transformation and restores the prior
     * image.
     *
     * <p>It displays the prior image in the View. It stores the most recent image version
     * in a stack as a 3-D int array. Storing images as arrays in the stack cuts down the amount of
     * space the stack requires, as compared to storing models in the stack. The undo method can be
     * pressed any number of times until there are no more moves to undo. If there are no moves to
     * undo, an error will pop up saying "No moves to undo!"
     * Users may repeat undo-redo operations
     * as many times as they want. Any undo move pushes the current image onto the redo stack of
     * commands, and vice versa. The user will not be able to undo any moves after saving or
     * loading a new image. </p>
     */
    @Override
    public void undo() {
      if (model != null) {
        try {
          int[][][] img = this.getModifiedImage();
          int[][][] oldImage = undo.pop();
          model = ImageFactory.createImage(oldImage);
          view.displayImage(model.getModifiedImage());
          redo.push(img);
        } catch (EmptyStackException e) {
          view.displayError("No moves to undo!");
        }
      } else {
        view.displayError("No moves to undo!");
      }
    }

    /**
     * The redo method redoes the most recent undo operation and restores the image with that
     * operation applied.
     *
     * <p>It displays the restored image in the View. It stores the most recent
     * undone image in a stack as a 3-D int[][][] array. Storing images as arrays in the stack cuts
     * down on the amount of space the stack requires, as opposed to storing models in the stack.
     * The redo method can be pressed any number of times until there are no more moves to redo. If
     * there are no moves to redo, an error will pop up saying "No moves to redo!" Users may repeat
     * undo-redo operations as many times as they want.
     * Any redo move pushes the current image onto
     * the undo stack of commands, and vice versa. If a user loads or saves an image, they will not
     * be able to redo any preceding undos.</p>
     */
    @Override
    public void redo() {
      if (model != null) {
        try {
          int[][][] img = this.getModifiedImage();
          int[][][] oldImage = redo.pop();
          model = ImageFactory.createImage(oldImage);
          view.displayImage(model.getModifiedImage());
          undo.push(img);
        } catch (EmptyStackException e) {
          view.displayError("No moves to redo!");
        }
      } else {
        view.displayError("No moves to redo!");
      }
    }

    /**
     * The mosaic method applies a mosaic filter to an image.
     *
     * <p>The method takes in no parameters, but will invoke a pop-up to ask the user how many
     * seeds they wish to allocate in the mosaic.</p>
     */
    @Override
    public void mosaic() {
      try {
        undo.push(this.getModifiedImage());
        String numOfSeeds = view.getInput("Enter number of seeds: ");
        if (numOfSeeds != null) {
          int seeds = Integer.parseInt(numOfSeeds);
          model.mosaic(seeds);
          int[][][] image = model.getModifiedImage();
          view.displayImage(image);
        }
      } catch (NullPointerException e) {
        view.displayError("No image detected.");
      } catch (IllegalArgumentException e) {
        view.displayError(e.getMessage());
      }
    }

    /**
     * The generateImage method creates a new image based on a Design specified in its parameter.
     *
     * <p>The method does not call for additional information in its parameters, but will invoke
     * a pop-up in the GUI to ask the user to specify the dimensions of the design to be created.
     * The method implements a case-switch strategy to determine which pop-up should be displayed in
     * accordance with which image is selected. The method passes the responsibility of calling the
     * ImageFactory to make the image, and of displaying the image in the View, to a helper
     * method.</p>
     *
     * @param designType the type of design to be generated.
     */
    @Override
    public void generateImage(Designs designType) {
      try {
        switch (designType) {
          case VERTICAL_RAINBOW:
          case HORIZONTAL_RAINBOW:
            String heightInput = view.getInput("Height:");
            String widthInput = view.getInput("Width:");
            if (heightInput != null || widthInput != null) {
              int height = Integer.parseInt(heightInput);
              int width = Integer.parseInt(widthInput);
              uploadImage(designType, height, width);
            }
            break;
          case CHECKERBOARD:
            String sizeInput = view.getInput("Enter the checkerboard square size:");
            if (sizeInput != null) {
              int size = Integer.parseInt(sizeInput);
              uploadImage(designType, size, size);
            }
            break;
          case GREEK_FLAG:
          case FRENCH_FLAG:
            heightInput = view.getInput("Height:");
            if (heightInput != null) {
              int height = Integer.parseInt(heightInput);
              uploadImage(designType, height, (int) (height * 1.5));
            }
            break;
          case SWISS_FLAG:
            sizeInput = view.getInput("Height:");
            if (sizeInput != null) {
              int size = Integer.parseInt(sizeInput);
              uploadImage(designType, size, size);
            }
            break;
          default:
            //Will not reach here
        }
        undo.clear();
        redo.clear();
      } catch (IllegalArgumentException e) {
        view.displayError(e.getMessage());
      }
    }

    /**
     * The uploadImage method is a helper method to generateImage which does the common work of
     * establishing a new image with the ImageFactory, retrieving a 3-D int[][][] array from the
     * model, and then displaying the image in the View.
     *
     * @param type   the type of design to generate, a Designs enum.
     * @param height the height of the new image.
     * @param width  the width of the new image.
     */
    private void uploadImage(Designs type, int height, int width) {
      model = ImageFactory.generateImage(type, height, width);
      int[][][] image = model.getModifiedImage();
      view.displayImage(image);
      undo.clear();
      redo.clear();
    }
  }
}