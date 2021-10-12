package imageprocessor;

/**
 * The AbstractFilter class is an abstract class that implements the Filter interface.
 *
 * <p>The AbstractFilter class contains the shared methods clamp and modifyImage, which can be
 * implemented by its child classes. The AbstractFilter also contains the attributes of
 * a 3-D array which stores the original image's data, an integer representing the height of
 * the image, and an integer representing the width of the image.</p>
 */
abstract class AbstractFilter implements Filter {

  /**
   * The first attribute of the AbstractFilter, int[][][] image, stores the channel
   * information of each pixel in an array as such: [height][width][red value, green value, blue
   * value].
   */
  protected int[][][] image;

  /**
   * The height attribute of the AbstractFilter stores the image's height in pixels as an integer.
   */
  protected int height;

  /**
   * The width attribute of the AbstractFilter stores the image's width in pixels as an integer.
   */
  protected int width;

  /**
   * The constructor of the AbstractFilter takes in three parameters (the original data set, the
   * image height, and the image width) and constructs an object with those three parameters.
   *
   * @param data   the original data set, represented as a 3D array (int[][][]).
   * @param height the height of the data set in pixels, represented as an integer.
   * @param width  the width of the data set in pixels, represented as an integer.
   */
  protected AbstractFilter(int[][][] data, int height, int width) {
    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("The height and width must be at least 1.");
    } else if (data == null) {
      throw new IllegalArgumentException("Original image could not be loaded.");
    }
    this.image = data;
    this.height = height;
    this.width = width;
  }

  /**
   * The clamp method checks whether a particular number exceeds the limits of an RGB value, which
   * must be between 0 and 255.
   *
   * <p>The method takes in a double number and returns a clamped integer. If the value is
   * lower than 0, the clamp changes the value to 0. If the value is above 255, the clamp changes
   * the value to 255. The clamp then truncates the double value to an integer by removing any
   * decimals. </p>
   *
   * @param value the number to be clamped to meet the constraints of an R, G, or B value.
   * @return an integer representing the clamped R, G, or B value.
   */
  public int clamp(double value) {
    if (value > 255) {
      value = 255.0;
    } else if (value < 0) {
      value = 0;
    }
    return (int) value;
  }

  /**
   * The modifyImage() method applies a filter to an existing image's int[][][] pixel array and
   * returns a modified int[][][] array representing the filtered image.
   *
   * <p>The method calls changePixel(int i, int j, int k) to change individual pixel values.
   * Children of the AbstractFilter must have changePixel methods to use this method.</p>
   *
   * @return a 3-D int[][][] array representing the RGB values of each pixel in a modified image.
   */
  @Override
  public int[][][] modifyImage() {
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          result[i][j][k] = changePixel(i, j, k);
        }
      }
    }
    return result;
  }
}
