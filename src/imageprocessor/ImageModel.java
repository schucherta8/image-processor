package imageprocessor;

/**
 * The ImageModel interface contains methods for applying a filter to an image and for saving an
 * image to a specified outfile address.
 */
public interface ImageModel {

  /**
   * The applyFilter(Filter type) method can be used to apply a filter to an image, which is
   * typically represented as a 3-D int[][][] array of the image pixels' channel values.
   *
   * @param filter a String representing the type of filter to be applied to the image.
   * @throws IllegalArgumentException if the specified filter is not a valid Features enum value.
   */
  void applyFilter(Filters filter) throws IllegalArgumentException;

  /**
   * The saveModifiedImage(filename) method saves a representation of an image to a specified
   * outfile address. The image will be what is produced following any modifications, or will
   * be the original image if there have been no modifications.
   *
   * @param filename a String representing the name of the outfile the image should be written to.
   * @throws IllegalArgumentException if the file fails to save.
   */
  @Deprecated
  void saveModifiedImage(String filename) throws IllegalArgumentException;

  /**
   * The saveOriginalImage(String filename) method saves the image model's original
   * 3-D int[][][] image representation to a specified outfile path.
   *
   * <p>The method throws an IllegalArgumentException if the filepath is invalid. It will also
   * provide a message if an IOException occurs.</p>
   *
   * @param filename a String representing the name of the outfile the image should be written to.
   * @throws IllegalArgumentException if the filepath is invalid.
   */
  @Deprecated
  void saveOriginalImage(String filename) throws IllegalArgumentException;
}
