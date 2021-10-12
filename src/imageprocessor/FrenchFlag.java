package imageprocessor;

/**
 * The FrenchFlag class represents an instantiation of a French flag image.
 * Each French flag is proportioned as height : width = 2 : 3, with three equally sized
 * vertical stripes: blue, white, and red, in accordance with France's official flag.
 */
class FrenchFlag implements Design {

  /**
   * The attribute height is an integer representing the height of the flag, in pixels.
   */
  private int height;

  /**
   * The attribute width is an integer representing the width of the flag, in pixels.
   */
  private int width;

  /**
   * The image attribute stores the pixel RGB values of the French flag.
   */
  private int[][][] image;

  /**
   * The colors attribute is a 2D array representing the RGB values of blue, red, and white to
   * be used in the flag.
   */
  private int[][] colors;

  /**
   * The FrenchFlag constructor takes in two arguments from the user, integers representing height
   * and width, and creates a new FrenchFlag object representation of a French flag of that size.
   *
   * <p>To be properly formatted, the user's height input must be divisible by 2. This ensures
   * the three stripes in the flag will be equally sized. In addition, the height to width
   * ratio of the user's specified values must be 2 to 3.</p>
   *
   * @param height The height of the FrenchFlag object: this must be a positive int divisible by 2.
   * @param width The width of the FrenchFlag: this must compose a height:width ratio of 2:3.
   * @throws IllegalArgumentException if the height or height to width ratio is improper.
   */
  public FrenchFlag(int height, int width) throws IllegalArgumentException {
    if (height % 2 != 0 || height < 1) {
      throw new IllegalArgumentException("The height must be a positive integer divisible by 2.");
    }
    else if (width != (int) Math.round(height * 1.5)) {
      throw new IllegalArgumentException("The height : width ratio must be 2 : 3");
    }
    this.height = height;
    this.width = width;
    this.colors = new int[][]{{0, 35, 149}, {255, 255, 255}, {237, 41, 57}};
    this.image = generate();
  }

  /**
   * The generate() method creates a 3-D int[][][] array representing a new FrenchFlag object.
   * The first array stores the height, or row; the second array stores the width, or column;
   * the third array stores three RGB channel integer values as {R, G, B}.
   *
   * <p>The method acts as a helper method to the constructor, and references the attributes
   * height and width to determine the size of the array and RGB assignments of each pixel.</p>
   *
   * @return a 3-D int[][][] array to be held in the new FrenchFlag object.
   */
  @Override
  public int[][][] generate() {
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      int color = 0;
      for (int j = 0; j < width; j++) {
        if (isBoundary(j, width)) {
          color++;
        }
        result[i][j] = colors[color];
      }
    }
    return result;
  }

  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in the FrenchFlag
   * and returns them to the user.
   *
   * @return a 3-D array representing the RGB values of each pixel in the FrenchFlag image.
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
   * The isBoundary(int value, int width) method determines whether a particular pixel is a
   * boundary where the subsequent pixels should change color.
   *
   * <p>This method acts as a helper method to the generate() method. The method will return
   * true if the pixel is positioned at a 1/3 division of the image's width. At each 1/3 division,
   * the color of the French flag should change. </p>
   *
   * @param currentValue the integer value of the height or width coordinate of the pixel.
   * @param width        the width of the flag.
   * @return a boolean representing whether the color should change at this pixel.
   */
  private boolean isBoundary(int currentValue, int width) {
    int divisor = (int) Math.ceil(width / 3.0);
    return (currentValue % divisor == 0) && (currentValue != 0);
  }

}


