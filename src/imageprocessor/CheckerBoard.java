package imageprocessor;

/**
 * The CheckerBoard class represents an instantiation of a checkerboard pattern, of the user's
 * specified square size. Each checkerboard is an 8x8 series of squares which alternate black and
 * white.
 */
class CheckerBoard implements Design {

  /**
   * The height of the checkerboard is equal to the square size specified by the user multiplied
   * by 8.
   */
  private int height;

  /**
   * The width of the checkerboard is equal to the square size specified by the user multiplied
   * by 8.
   */
  private int width;

  /**
   * The image attribute stores the pixel RGB values of the CheckerBoard object.
   */
  private int[][][] image;

  /**
   * The colors attribute is a 2D array representing the RGB values of black and white.
   */
  private int[][] colors;

  /**
   * The CheckerBoard constructor takes in two argument from the user, integers representing the
   * height and width of a square, and creates a new CheckerBoard object.
   *
   * <p>The user-specified size will determine how many pixels wide each square of the checkerboard
   * will be. It creates a new checkerboard object where height and width both equal the square
   * size multiplied by 8. The user must have entered the same height and width values to produce a
   * CheckerBoard, or the method will throw an IllegalArgumentException to notify the user that the
   * dimensions are not legal.</p>
   *
   * @param height the user-specified height, the number of pixels wide each square should be.
   * @param width the user-specified width - this should be the same as the height of the square.
   * @throws IllegalArgumentException if the square size is less than 1 or height is not width.
   */
  public CheckerBoard(int height, int width) throws IllegalArgumentException {
    if (height < 1) {
      throw new IllegalArgumentException("The size of a square must be a positive integer.");
    }
    else if (height != width) {
      throw new IllegalArgumentException("The height must be the same as the width.");
    }
    this.height = height * 8;
    this.width = height * 8;
    this.colors = new int[][]{{0, 0, 0}, {255, 255, 255}};
    this.image = generate();
  }

  /**
   * The generate() method creates a 3-D int[][][] array representing a new CheckerBoard object.
   *
   * <p>The method acts as a helper method to the constructor, and references the attributes
   * height and width to determine the size of the array and RGB assignments of each pixel.</p>
   *
   * @return a 3-D int[][][] array to be held in the new CheckerBoard object.
   */
  public int[][][] generate() {
    int[][][] result = new int[height][width][3];
    int color = 0;
    for (int i = 0; i < height; i++) {
      if (isBoundary(i)) {
        color++;
      }
      for (int j = 0; j < width; j++) {
        if (isBoundary(j)) {
          color++;
        }
        result[i][j] = colors[color % 2];
      }
    }
    return result;
  }

  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in the CheckerBoard
   * and returns them to the user.
   *
   * @return a 3-D array representing the RGB values of each pixel in the CheckerBoard image.
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
   * The isBoundary(int value) method determines whether a particular pixel is a boundary where the
   * subsequent pixels should change color from black to white or vice versa.
   *
   * <p>This method acts as a helper method to the generate() method. The method will return
   * true if the pixel is positioned at a 1/8 division of the image. At each 1/8 division, the color
   * of the checkerboard should change. </p>
   *
   * @param value the integer value of the height or width coordinate of the pixel.
   * @return a boolean representing whether the color should change at this pixel.
   */
  private boolean isBoundary(int value) {
    int divisor = (int) Math.ceil(height / 8.0);
    return (value % divisor == 0);
  }
}
