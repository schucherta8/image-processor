package imageprocessor;

/**
 * Represents an ImageFactory to create and generate images.
 */
public class ImageFactory {

  /**
   * Returns a new image.
   *
   * <p>This method creates new ImageModelImpl object to apply filters.</p>
   *
   * @param image represents an image as a 3D RGB array.
   * @return a new image
   */
  public static ImageModelExtension createImage(int[][][] image) {
    return new ImageModelImpl(image);
  }

  /**
   * Returns a new design type image.
   *
   * @param designType represents the type of design of the image.
   * @param height the height of the image.
   * @param width the width of the image.
   * @return a new design type image.
   */
  public static ImageModelExtension generateImage(Designs designType,int height, int width) {
    return new ImageModelImpl(designType,height,width);
  }
}