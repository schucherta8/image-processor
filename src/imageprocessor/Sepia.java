package imageprocessor;

/**
 * The Sepia class represents a Sepia image transformation, which can be applied to a 3-D int[][][]
 * array representation of an image.
 *
 * <p>The class inherits three attributes from the AbstractFilter class: the img which is
 * a 3-D int[][][] array of RGB values, the height of the image, and the width of the image in
 * pixels.  The class also features a static 2-D array called transformation, which stores the
 * coefficients which should be applied to an image file when transforming each pixel into a
 * Sepia-toned image.</p>
 */
class Sepia extends AbstractFilter {


  /**
   * The transformation attribute is a 2-D int[][] array that stores information about how each
   * pixel should be modified when applying a Sepia filter to an image.
   *
   * <p>Transformation specifically contains a set of coefficients for the R, G, and B value
   * of each pixel that must be applied to convert the pixel's color to a Sepia value. </p>
   */
  private static double[][] transformation = {{0.393, 0.349, 0.272}, {0.769, 0.686, 0.534},
      {0.189, 0.168, 0.131}};

  /**
   * The Sepia constructor inherits the attributes img, height, and width from the
   * AbstractFilter class. The class stores these attributes for use with its transformation
   * feature when modifying pixel values.
   *
   * @param img the original 3-D int[][][] array representing the original image.
   * @param height        the height of the original image, measured in pixels.
   * @param width         the width of the original image, measured in pixels.
   */
  public Sepia(int[][][] img, int height, int width) {
    super(img, height, width);
  }

  /**
   * The changePixel method transforms a pixel's RGB values into a Sepia-toned color by referencing
   * the Sepia object's transformation int[][] matrix to multiply the pixel's RGB values by
   * particular coefficients. After determining the new value, the method calls clamp to clamp the
   * pixel value to an appropriate integer between 0 and 255.
   *
   * @param row     the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column  the column of the pixel in the 3-D array storing the image's RGB values
   * @param channel the specific channel (R, G, or B) to be modified in the current pixel.
   * @return an integer representing the new R, G, or B value of the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    int red = image[row][column][0];
    int green = image[row][column][1];
    int blue = image[row][column][2];
    return Math.round(clamp(transformation[0][channel] * red
            + transformation[1][channel] * green
            + transformation[2][channel] * blue));
  }

}




