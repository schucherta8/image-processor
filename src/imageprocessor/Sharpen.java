package imageprocessor;

/**
 * The Sharpen class represents a Sharpen filter, which can be applied to an image file.
 *
 * <p>The class inherits three attributes from the AbstractFilter class: the img which is
 * a 3-D int[][][] array of RGB values, the height of the image, and the width of the image in
 * pixels.  The class's sole unique attribute is a 2-D array called kernel, which stores the
 * coefficients which should be applied to each pixel's surrounding pixels while sharpening an
 * image. </p>
 */
class Sharpen extends AbstractFilter {

  /**
   * The kernel attribute is a 2-D int[][] array that stores information about how each pixel should
   * be modified when sharpening an image.
   *
   * <p>Here's an explanation of how a kernel works for a filter: When a filter is applied to an
   * image, each pixel's new RGB values are calculated based on the preexisting values of its
   * surrounding pixels. In the calculation, each new pixel is calculated as the sum of itself and
   * all of its vertical, horizontal, and diagonal adjacent pixels, where each pixel is multiplied
   * by a specific coefficient. The kernel stores the coefficients that each pixel should be mapped
   * onto for the calculation. </p>
   */
  private static double[][] kernel
          = {{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};

  /**
   * The constructor for a Sharpen object takes in the parameters of an original image's pixels and
   * its height and width. The Sharpen object will store those parameters as attributes, and can
   * use them in conjunction with its static kernel attribute.
   *
   * @param img a 3-D int[][][] array of the RGB values of each pixel in an image.
   * @param height        an integer representing the height of the image in pixels.
   * @param width         an integer representing the width of the image in pixels.
   */
  public Sharpen(int[][][] img, int height, int width) {
    super(img, height, width);
  }

  /**
   * The changePixel(row, column, channel) method takes in a pixel from a 3-D int[][][]
   * representation of an image's pixel set, and returns a pixel that has been modified suitably
   * for the Sharpen filter.
   *
   * <p>The method references the kernel attribute, which stores the coefficient that each
   * neighboring pixel should be multiplied by to compute the current pixel's value. After
   * determining the new value of the pixel the method calls clamp in order to clamp it to an
   * appropriate value. </p>
   *
   * @param row     the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column  the column of the pixel in the 3-D array storing the image's RGB values
   * @param channel the specific channel (R, G, or B) being modified on the current pixel.
   * @return an integer representing the new R, G, or B value to be stored for the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    double newValue = 0;
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        if (row + (i - 2) >= 0 && row + (i - 2) < height
                && column + (j - 2) >= 0 && column + (j - 2) < width) {
          newValue += kernel[i][j]
                  * image[row + (i - 2)][column + (j - 2)][channel];
        }
      }
    }
    return clamp(newValue);
  }

}