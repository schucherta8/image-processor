package imageprocessor;

/**
 * The GreekFlag class represents an instantiation of a Greek flag, of the user's specified
 * width. Each Greek flag is a set of nine horizontal blue and white stripes, with a plus-shaped
 * blue and white cross in the top left corner, in accordance with Greece's official flag.
 */
class GreekFlag implements Design {

  /**
   * The attribute height is an integer representing the height of the flag, in pixels.
   */
  private int height;

  /**
   * The attribute height is an integer representing the width of the flag, in pixels.
   */
  private int width;

  /**
   * The image attribute stores the pixel RGB values of the Greek flag.
   */
  private int[][][] image;

  /**
   * The colors attribute is a 2D array representing the RGB values of blue and white, which
   * will be the values of the pixels in the image array.
   */
  private int[][] colors;

  /**
   * The GreekFlag constructor takes in two arguments from the user, integers representing width
   * and height, and creates a new GreekFlag object representation of a Greek flag of that size.
   *
   * <p>To be properly formatted, the user's specified height must be divisible by 9. This
   * ensures that all nine horizontal stripes of the flag will be included in the new image.
   * In addition, the height to width ratio must be 2 to 3.</p>
   *
   * @param height The height of the flag - it must be divisible by 9.
   * @param width The width of the flag - this must be 3/2 times the height.
   * @throws IllegalArgumentException if the height is not divisible by 9 or is less than 1.
   */
  public GreekFlag(int height, int width) throws IllegalArgumentException {
    if (height % 9 != 0 || height < 1) {
      throw new IllegalArgumentException("The height must be a positive integer divisible by 9.");
    }
    else if (width != (int) Math.round((height / 2.0) * 3)) {
      throw new IllegalArgumentException("The ratio from height to width must be 2 : 3");
    }
    this.height = height;
    this.width = width;
    this.colors = new int[][]{{13, 94, 175}, {255, 255, 255}};
    this.image = generate();
  }

  /**
   * The generate() method creates a 3-D int[][][] array representing a new GreekFlag object.
   *
   * <p>The method acts as a helper method to the constructor, and references the attributes
   * height and width to determine the size of the array and RGB assignments of each pixel.</p>
   *
   * @return a 3-D int[][][] array storing the array to be held in the GreekFlag object.
   */
  @Override
  public int[][][] generate() {
    int[][][] result = new int[height][width][3];
    int color = 0;
    for (int i = 0; i < height; i++) {
      if (isRowBoundary(i, height)) {
        color++;
      }
      for (int j = 0; j < width; j++) {
        result[i][j] = colors[color % 2];
      }
    }
    result = addTopLeftCorner(result);
    return result;
  }

  /**
   * The addTopLeftCorner() method is a helper method to the generate() method of the
   * Greek flag. It modifies the top left corner of the flag to place a plus-sign shaped cross
   * in the top left corner, in accordance with the official Greek flag.
   *
   * @param fullFlag The int[][][] data set of the full Greek flag whose corner should be modified.
   * @return a new int[][][] array that has been modified with a cross in the top left corner.
   */
  public int[][][] addTopLeftCorner(int[][][] fullFlag) {
    for (int i = 0; i < (int) Math.ceil(height / 9.0) * 5; i++) {
      for (int j = 0; j < (int) Math.ceil(width / 13.5) * 5; j++) {
        int color = 0;
        if (isWhite(i, j)) {
          color = 1;
        }
        fullFlag[i][j] = this.colors[color];
      }
    }
    return fullFlag;
  }


  /**
   * The isWhite(currentRow, currentColumn) method is a helper method to determine whether a pixel
   * in the top left corner area of the Greek flag should be white. This method is called by
   * the addTopLeftCorner() method when constructing the top-left plus sign of the flag.
   *
   * @param currentRow    the row of the current pixel.
   * @param currentColumn the column of the current pixel.
   * @return a boolean representing whether the pixel should be white.
   */
  private boolean isWhite(int currentRow, int currentColumn) {
    double rowDivisor = height / 9.0;
    double columnDivisor = width / 13.5;
    if (currentRow >= (2 * rowDivisor) && currentRow <= (3 * rowDivisor)) {
      return true;
    } else if (currentColumn >= (2 * columnDivisor) && currentColumn <= (3 * columnDivisor)) {
      return true;
    }
    return false;
  }


  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in the GreekFlag
   * and returns them to the user.
   *
   * @return a 3-D array representing the RGB values of each pixel in the GreekFlag image.
   */
  @Override
  public int[][][] getImage() {
    return this.image;
  }

  /**
   * The getHeight() method retrieves the height of the object as an integer and returns it to the
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
   * @return an integer representing the width of the image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * The isRowBoundary(int value, int height) method determines whether a particular pixel is a
   * row boundary, where the subsequent pixels should change color.
   *
   * <p>This method acts as a helper method to the generate() method. The method will return
   * true if the pixel is positioned at a place where the flag should change color from white to
   * blue, as a new horizontal line, or vice versa. </p>
   *
   * @param currentValue the integer value of the height or width coordinate of the pixel.
   * @param height       the height of the flag.
   * @return a boolean representing whether the color should change at this pixel.
   */
  private boolean isRowBoundary(int currentValue, int height) {
    int divisor = (int) Math.ceil(height / 9.0);
    return (currentValue % divisor == 0) && (currentValue != 0);
  }

}


