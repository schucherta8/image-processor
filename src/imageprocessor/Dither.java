package imageprocessor;

/**
 * The Dither class represents a dithering image transformation, which can be applied to a
 * 3-D int[][][] array of pixels' RGB values.
 *
 * <p>The Dithering method modifies each pixel's RGB value to be either black or white. It also
 * adjusts the adjacent colors of each pixel to improve the detail of the image. In this way,
 * the Dither effect constructs a black-and-white image similar to those that were printed
 * in old newspapers.</p>
 */
class Dither extends AbstractFilter {

  /**
   * The constructor of the AbstractFilter takes in three parameters (the original data set, the
   * image height, and the image width) and constructs an object with those three parameters.
   *
   * @param data   the original data set, represented as a 3D array (int[][][]).
   * @param height the height of the data set in pixels, represented as an integer.
   * @param width  the width of the data set in pixels, represented as an integer.
   */
  protected Dither(int[][][] data, int height, int width) {
    super(data, height, width);
  }

  /**
   * The changePixel() method modifies an individual pixel's RGB value to either black or white.
   *
   * <p>The method references a kernel, applies the kernel to the pixel's surrounding pixels, and
   * calculates what the pixel's new color value should be. If the current value is less than 128,
   * the new value should be black; otherwise, it should be white.</p>
   *
   * @param row     the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column  the column of the pixel to be modified in the current pixel.
   * @param channel the value stored at the current channel of the current pixel.
   * @return an integer representing the new R, G, or B value of the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    int old_color = image[row][column][channel];
    int new_color;
    if (old_color <= 128) {
      new_color = 0;
    } else {
      new_color = 255;
    }
    image[row][column] = new int[]{new_color, new_color, new_color};
    double error = old_color - new_color;

    if (column + 1 < width) {
      image[row][column + 1][channel] =
              Math.round(clamp(image[row][column + 1][channel] + (7 / 16.0 * error)));
    }
    if (row + 1 < height && column - 1 >= 0) {
      image[row + 1][column - 1][channel] =
              Math.round(clamp(image[row + 1][column - 1][channel] + (3 / 16.0 * error)));
    }
    if (row + 1 < height) {
      image[row + 1][column][channel] =
              Math.round(clamp(image[row + 1][column][channel] + (5 / 16.0 * error)));
    }
    if (row + 1 < height && column + 1 < width) {
      image[row + 1][column + 1][channel] =
              Math.round(clamp(image[row + 1][column + 1][channel] + (1 / 16.0 * error)));
    }
    return new_color;
  }
}
