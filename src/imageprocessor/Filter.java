package imageprocessor;

/**
 * The Filter interface contains methods to apply a filter to a 3D array and to change the color
 * value of a specific pixel.
 *
 * <p>The interface's methods can support 3D arrays representing images as
 * [height][width][red RGB value, green RGB value, blue RGB value] where the height and width are
 * integers above 0 and the red, green, and blue values are integers between 0 and 255. </p>
 */
interface Filter {

  /**
   * The modifyImage() method applies a filter to an existing image's int[][][] pixel array and
   * returns a modified int[][][] array representing the filtered image.
   *
   * @return a 3-D int[][][] array representing the RGB values of each pixel in the image.
   */
  int[][][] modifyImage();

  /**
   * The changePixel() method modifies an individual pixel's RGB values.
   *
   * <p>The method references a kernel, applies the kernel to the pixel's surrounding pixels, and
   * calculates what the pixel's new color value should be.</p>
   *
   * @param row    the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column the column of the pixel to be modified in the current pixel.
   * @return an integer representing the new R, G, or B value of the pixel.
   */
  int changePixel(int row, int column, int channel);

  /**
   * The clamp method checks whether a particular value exceeds the limits of an RGB value, which
   * must be between 0 and 255, and fixes the value if need be.
   *
   * <p>The method takes in a double number and returns a clamped integer. If the value is
   * lower than 0, the clamp changes the value to 0. If the value is above 255, the clamp changes
   * the value to 255. The clamp then truncates the double value to an integer by removing any
   * decimals. </p>
   *
   * @param value the number to be clamped to meet the constraints of an R, G, or B value.
   * @return an integer representing the clamped R, G, or B value.
   */
  int clamp(double value);
}
