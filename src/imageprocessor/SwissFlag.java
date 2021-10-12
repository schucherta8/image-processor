package imageprocessor;

/**
 * The SwissFlag class represents an instantiation of a Swiss flag, of the user's specified flag
 * size. Each Swiss flag is a red square with a white plus-shaped cross in the middle, in
 * accordance with Switzerland's official flag. The height and width of the flag should be the
 * same, forming a square flag.
 */
class SwissFlag implements Design {

  /**
   * The attribute height is an integer representing the height of the flag, in pixels.
   */
  private int height;

  /**
   * The attribute width is an integer representing the width of the flag, in pixels.
   */
  private int width;

  /**
   * The image attribute stores the pixel RGB values of the Swiss flag.
   */
  private int[][][] image;

  /**
   * The colors attribute is a 2D array representing the RGB values of red and white, which
   * will be the Swiss flag pixel values.
   */
  private int[][] colors;

  /**
   * The SwissFlag constructor takes in one argument from the user, an integer representing the flag
   * height, and creates a new SwissFlag object representation of a Swiss flag of that size.
   *
   * <p>In order to be properly formatted, the user's provided height must be a multiple of
   * 32. The height and width must be the same value. This ensures the Swiss flag is created with
   * dimensions in accordance with the official flag of Switzerland. </p>
   *
   * @param height The height of the new flag - this must be divisible by 32.
   * @param width The width of the new flag - this must be the same size as the height.
   * @throws IllegalArgumentException if height is not a proper size or does not equal width.
   */
  public SwissFlag(int height, int width) throws IllegalArgumentException {
    if (height % 32 != 0) {
      throw new IllegalArgumentException("The height must be divisible by 32.");
    }
    else if (height != width) {
      throw new IllegalArgumentException("The height and width must be the same.");
    }
    this.height = height;
    this.width = width;
    this.colors = new int[][]{{255, 0, 0}, {255, 255, 255}};
    this.image = generate();
  }

  /**
   * The generate() method creates a 3-D int[][][] array representing a new SwissFlag image.
   *
   * <p>The method acts as a helper method to the constructor, and references the attributes
   * height and width to determine the size of the array and assignment of each pixel.
   * The method calls an additional helper method, isWhite, to determine whether a pixel should
   * be red or white.</p>
   *
   * @return a 3-D int[][][] array storing the new SwissFlag object.
   */
  @Override
  public int[][][] generate() {
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int color = 0;
        if (isWhite(i, j)) {
          color = 1;
        }
        result[i][j] = this.colors[color];
      }
    }
    return result;
  }

  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in the SwissFlag
   * image attribute and returns them to the user.
   *
   * @return a 3-D array representing the RGB values of each pixel in the SwissFlag image.
   */
  @Override
  public int[][][] getImage() {
    return this.image;
  }

  /**
   * The getHeight() method retrieves the width of the object as an integer and returns it to the
   * user.
   *
   * @return an integer representing the height of the image.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * The getWidth() method retrieves the width of the object as an integer and returns it to the
   * user.
   *
   * @return an integer representing the height of the image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * The isWhite(currentRow, currentColumn) method is a helper method to determine whether a pixel
   * in the Swiss flag should be white.
   *
   * @param currentRow    the row of the current pixel.
   * @param currentColumn the column of the current pixel.
   * @return a boolean representing whether the pixel should be white.
   */
  private boolean isWhite(int currentRow, int currentColumn) {
    double divisor = width / 32.0;
    if (currentColumn >= (6 * divisor) && currentColumn <= (26 * divisor)
            && currentRow >= (13 * divisor) && currentRow <= (19 * divisor)) {
      return true;
    } else if (currentRow >= (6 * divisor) && currentRow <= (26 * divisor)
            && currentColumn >= (13 * divisor) && currentColumn <= (19 * divisor)) {
      return true;
    }
    return false;
  }

}



