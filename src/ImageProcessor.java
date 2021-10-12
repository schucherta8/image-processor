
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import imagecontroller.ImageControllerExtension;
import imagecontroller.ImageControllerImpl;
import imageguicontroller.Controller;
import imageguiview.IView;
import imageguiview.IViewImpl;
import imageprocessor.ImageModelExtension;

import static java.lang.System.exit;

/**
 * The ImageProcessor class contains the main method for establishing a new Model, View, and
 * Controller objects for an ImageProcessor implementation.
 *
 * <p>The ImageProcessor class is able to support command-line calls for either an interactive
 * GUI or a text-based image processing command. Users must begin the ImageProcessor by opening
 * the JAR file for this ImageProcessor in the command line.
 *
 * To open a GUI, the user would type:
 * java -jar ImageProcessing.jar -interactive
 *
 * And to run a text file of commands, the user would type:
 * java -jar ImageProcessing.jar -script full-path-of-script-file
 *
 * Warning: If a user runs a text file command, the user must enter the full filepath of
 * any file they wish to load or save. They can find the full filepath of the current directory
 * by typing "pwd" on most command lines.
 * </p>
 */
public class ImageProcessor {

  /**
   * The main function is the point of contact with the user when the user starts up a new
   * Image Processing program via the command line.
   *
   * <p>The main function includes two cases for the user entering either "-interactive" or
   * "-script filepath" on the command line. If the user enters "interactive", the main will
   * launch the GUI image processor. If the user enters "script" and a valid full filepath,
   * the main will launch an ImageProcessorImpl without a GUI and complete the user's script.
   * Otherwise, if the user enters an invalid command, the program will print an error and exit.
   * </p>
   *
   * @param args default argument for a main function.
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      if (args[0].equals("-interactive")) {
        ImageModelExtension model = null;
        IView view = new IViewImpl("Image Processor");
        Controller controller = new Controller(model, view);
      } else if (args[0].equals("-script")) {
        ImageModelExtension model = null;
        File filepath = new File(args[1]);
        try {
          BufferedReader input = new BufferedReader(new FileReader(filepath));
          ImageControllerExtension impl = new ImageControllerImpl(model, input);
          impl.start();
        }
        catch (IOException e) {
          System.out.print("We could not find that file.\n");
          exit(0);
        }
      } else {
        System.out.print("Invalid filepath. Please enter -interactive to use the GUI, or -script"
                + " and a filepath to run a text file processor without a GUI. Must use a filepath"
                + " in the res folder and specify with 'res/file'.\n");
        exit(0);
      }
    }
  }
}

