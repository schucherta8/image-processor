package imageprocessor;

/**
 * The Blur class represents a blur filter that extends the AbstractFilter class.
 *
 * <p>In addition to the features of the AbstractFilter, the Blur class contains a kernel which
 * specifies the coefficients for the calculation of how each pixel in an image should be modified
 * in order to blur the image. The kernel is a 3 by 3 array of double values which act as
 * coefficients for the surrounding pixels. </p>
 */
class Blur extends AbstractFilter {

  /**
   * The kernel attribute is a 2-D int[][] array that stores information about how each pixel
   * should be modified when blurring an image.
   *
   * <p>Here's an explanation of how a kernel works for a filter: When a filter is applied to an
   * image, each pixel's new RGB values are calculated based on the preexisting values of its
   * surrounding pixels. In the calculation, each new pixel is calculated as the sum of itself and
   * all of its vertical, horizontal, and diagonal adjacent pixels, where each pixel is multiplied
   * by a specific coefficient. The kernel stores the coefficients that each pixel should be mapped
   * onto for the calculation. </p>
   */
  private static double[][] kernel
          = {{.0625, .125, .0625}, {.125, .25, .125}, {.0625, .125, .0625}};

  /**
   * The constructor of a Blur object takes in the parameters of an original image's pixels and
   * its height and width. The Blur object will store those parameters as attributes.
   *
   * @param img a 3-D int[][][] array of the RGB values of each pixel in an image.
   * @param height        an integer representing the height of the image in pixels.
   * @param width         an integer representing the width of the image in pixels.
   */
  public Blur(int[][][] img, int height,int width) {
    super(img, height,width);
  }

  /**
   * This method applies the Blur kernel to a specific pixel in order to determine what the
   * modified pixel's R, G, or B value should be. After calculating the new value of the pixel's
   * channel, the method calls the clamp method to assign an appropriate clamped value to the
   * pixel.
   *
   * @param row     the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column  the column of the pixel in the 3-D array storing the image's RGB values
   * @param channel the specific channel (R, B, or G) of the pixel.
   * @return an integer representing the new R, B, or G value of the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    double sum = 0;
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        if (row + (i - 1) >= 0 && row + (i - 1) < height
                && column + (j - 1) >= 0 && column + (j - 1) < width) {
          sum += kernel[i][j] * image[row + (i - 1)][column + (j - 1)][channel];
        }
      }
    }
    return clamp(sum);
  }
}
