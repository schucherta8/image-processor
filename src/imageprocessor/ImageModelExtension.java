package imageprocessor;

/**
 * The ImageModelExtension interface contains methods for applying a mosaic filter to an image and
 * for saving an image.
 */
public interface ImageModelExtension extends ImageModel {
  /**
   * Return a modified image.
   *
   * <p>The method saves a representation of an image to a specified. The image will be what is
   * produced following any modifications, or will be the original image if there have been no
   * modifications.</p>
   *
   * @return a modified image.
   */
  int[][][] getModifiedImage();

  /**
   * Return the original image.
   *
   * @return an original image.
   */
  int[][][] getOriginalImage();

  /**
   * The method applies a mosaic filter to an image based on the number of seeds the user specifies.
   * @param numOfSeeds the number of seeds to produce
   */
  void mosaic(int numOfSeeds);
}
