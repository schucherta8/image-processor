package imagecontroller;

import imageprocessor.ImageModelExtension;

/**
 * The ImageControllerExtension interface is an extension of the ImageController interface.
 * The interface provides one additional method, getModel().
 */
public interface ImageControllerExtension extends ImageController {

  /**
   * The getModel() method retrieves the model that the ImageController acts upon.
   *
   * @return the ImageModelExtension object that the Controller acts upon.
   */
  ImageModelExtension getModel();
}
