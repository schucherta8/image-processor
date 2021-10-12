package imageprocessor;

/**
 * The GreyScale class represents a GreyScale transformation, which can be applied to an image
 * 3-D int[][][] array.
 *
 * <p>The class inherits three attributes from the AbstractFilter class: the img which is
 * a 3-D int[][][] array of RGB values, the height of the image, and the width of the image in
 * pixels.  The class's sole unique attribute is a 2-D array called transformation, which stores the
 * coefficients which should be applied to an image file when transforming each pixel into a
 * GreyScale-toned image.</p>
 */
class GreyScale extends AbstractFilter {

  /**
   * The transformation attribute is a 2-D int[][] array that stores information about how each
   * pixel should be modified when applying a GreyScale filter to an image.
   *
   * <p>The transformation attribute contains a set of coefficients for the R, G, and B value
   * of each pixel that must be applied to convert the pixel's color to a GrayScale value. </p>
   */
  private static double[] transformation = {0.2126, 0.7152, .0722};

  /**
   * The GreyScale constructor inherits the attributes img, height, and width from the
   * AbstractFilter class. The class stores these attributes for use with its transformation
   * feature when modifying pixel values
   *
   * @param img the original 3-D int[][][] array representing the original image.
   * @param height        the height of the original image, measured in pixels.
   * @param width         the width of the original image, measured in pixels.
   */
  public GreyScale(int[][][] img, int height, int width) {
    super(img, height, width);
  }

  /**
   * The changePixel(row, column, channel) method changes an R, G, or B value of a pixel into a
   * grey-scale value by applying the transformation matrix to the pixel and calculating a
   * new R, G, or B value.
   *
   * @param row     the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column  the column of the pixel in the 3-D array storing the image's RGB values.
   * @param channel the specific channel (R, G, or B) to be modified in the current pixel.
   * @return an integer representing the assigned value of the R, G, or B channel in the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    int red = image[row][column][0];
    int green = image[row][column][1];
    int blue = image[row][column][2];
    return Math.round(clamp(transformation[0] * red
            + transformation[1] * green + transformation[2] * blue));
  }

}

