package imageprocessor;

/**
 * The Rainbow class represents an instantiation of an image of rainbow stripes, where the
 * height and width of the rainbow object are user-specified values.
 *
 * <p>Each rainbow pattern is a series of seven stripes which have the values (red, orange,
 * yellow, green, blue, indigo, violet). The stripes may be either vertical or horizontal,
 * and the stripe direction is a parameter in the constructor. The stripes must either
 * all be the same width, or must all be the same except for the sixth stripe which may be
 * up to six fewer pixels wide. </p>
 */
class Rainbow implements Design {

  /**
   * The attribute height represents the height of the image, measured in pixels.
   */
  private int height;

  /**
   * The attribute width represents the width of the image, measured in pixels.
   */
  private int width;

  /**
   * The attribute direction represents whether the stripes of the rainbow should be horizontal or
   * vertical.
   *
   * <p>The input must match either "horizontal" or "vertical". If the direction
   * is specified as horizontal, the ROYGBIV colors will proceed from Red on the left to Violet on
   * the right. If the direction is specified as vertical, the colors will proceed from Red on the
   * top to Violet on the bottom.</p>
   */
  private String direction;

  /**
   * The attribute image stores the pixel RGB values of the Rainbow object.
   */
  private int[][][] image;

  /**
   * The colors attribute stores the RGB values of the ROYGBIV colors to be represented in the
   * rainbow: the standard RGB values for red, orange, yellow, green, blue, indigo, and violet.
   */
  private int[][] colors;

  /**
   * The Rainbow constructor creates a new Rainbow object of the user-specified height, width,
   * and stripe direction.
   *
   * <p>In order to be properly formatted, the height and width must be at least 1 and the
   * stripe direction must be either "horizontal" or "vertical". If the stripe direction is
   * vertical, the width must be either divisible by 7 or be divisible into six even stripes with
   * a seventh stripe that has no more than six fewer pixels. The same rule applies if the stripe
   * direction is horizontal: in that case, the image height must be either divisible by 7 or
   * divisible into six even stripes with a seventh stripe that has no more than six fewer pixels.
   * </p>
   *
   * @param height    The height of the new Rainbow object in pixels.
   * @param width     The width of the new Rainbow object in pixels.
   * @param direction The direction of the rainbow stripes.
   * @throws IllegalArgumentException if the height, width, or direction is not valid.
   */
  public Rainbow(String direction, int height, int width) throws IllegalArgumentException {
    if (!(direction.equals("vertical") || direction.equals("horizontal"))) {
      throw new IllegalArgumentException("The specified direction must be "
              + "horizontal or vertical.");
    }
    else if (invalidWidth(direction, height, width)) {
      throw new IllegalArgumentException("The stripes must either divide evenly or leave the"
              + " last stripe no more than 6 pixels shorter than the other stripes.");
    }

    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Each dimension must be at least one.");
    }
    this.height = height;
    this.width = width;
    this.direction = direction;
    this.colors = new int[][]{{255, 0, 0}, {255, 127, 0}, {255, 255, 0}, {0, 255, 0},
        {0, 0, 255}, {75, 0, 130}, {148, 0, 211}};
    this.image = generate();
  }

  /**
   * The generate() method creates a 3-D int[][][] array representing a new Rainbow object.
   *
   * <p>The method acts as a helper method to the constructor, and references the parameters
   * height and width to determine the size of the array and assignment of each pixel. The
   * method calls specific methods, makeVerticalRainbow and makeHorizontalRainbow, as
   * appropriate for the stripe direction.</p>
   *
   * @return a 3-D int[][][] array storing the new Rainbow object.
   */
  @Override
  public int[][][] generate() {
    if (this.direction.equals("vertical")) {
      return makeVerticalRainbow();
    } else {
      return makeHorizontalRainbow();
    }
  }

  /**
   * The makeVerticalRainbow() method acts as a private helper method to the generate() function
   * in the Rainbow class.
   *
   * <p>The method establishes a new int[][][] array of the image's specified height
   * and width, iterates through each value of the array to establish the appropriate value
   * of the pixel, and makes a determination of the pixel value based on which horizontal
   * stripe color the pixel should be. It uses a helper method, is Divisor(), to determine
   * when a pixel should be of a new color.</p>
   *
   * @return a 3D int[][][] array storing the pixel values of a new vertical Rainbow object.
   */
  private int[][][] makeVerticalRainbow() {
    int[][][] rainbow = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      int color = 0;
      for (int j = 0; j < width; j++) {
        if (isDivisor(j, width)) {
          color++;
        }
        rainbow[i][j] = colors[color];
      }
    }
    return rainbow;
  }

  /**
   * The makeHorizontalRainbow() method acts as a private helper method to the generate() function
   * in the Rainbow class.
   *
   * <p>The method establishes a new int[][][] array of the image's specified height
   * and width, iterates through each value of the array to establish the appropriate value
   * of the pixel, and makes a determination of the pixel value based on which horizontal
   * stripe color the pixel should be. It uses a helper method, isDivisor(), to determine
   * when a pixel should be of a new color.</p>
   *
   * @return a 3D int[][][] array storing the pixel values of a new horizontal Rainbow object.
   */
  private int[][][] makeHorizontalRainbow() {
    int[][][] rainbow = new int[height][width][3];
    int color = 0;
    for (int i = 0; i < height; i++) {
      if (isDivisor(i, height)) {
        color++;
      }
      for (int j = 0; j < width; j++) {
        rainbow[i][j] = colors[color];
      }
    }
    return rainbow;
  }

  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in the Rainbow and
   * returns them to the user.
   *
   * @return a 3-D array representing the RGB values of each pixel in the Rainbow image.
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
   * @return an integer representing the width of the image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * The isDivisor(int currentValue, int widthOrHeight) method determines whether a particular
   * pixel is a boundary where the subsequent pixels should change color.
   *
   * <p>This method acts as a helper method to the generate() method. The method will return
   * true if the pixel is positioned at a 1/7 division of the image's width when constructing a
   * vertical rainbow, or when the pixel is positioned at a 1/7 division of the image's height
   * when constructing a vertical rainbow. In either case, at each 1/7 division,
   * the color of the rainbow should change to the next ROYGBIV value. </p>
   *
   * @param currentValue the integer value of the height or width coordinate of the pixel.
   * @param widthOrHeight the width or height of the rainbow.
   * @return a boolean representing whether the color should change at this pixel.
   */
  private boolean isDivisor(int currentValue, int widthOrHeight) {
    int divisor = (int) Math.ceil(widthOrHeight / 7.0);
    return (currentValue % divisor == 0) && (currentValue != 0);
  }

  /**
   * The invalidWidth(String direction, int height, int width) method determines whether the
   * specified pixel dimensions are valid or invalid for constructing a new vertical or
   * horizontal Rainbow object.
   *
   * <p>The dimensions are valid if all 7 stripes are the same width or if the seventh stripe
   * is no more than six pixels shorter than the other stripes.</p>
   *
   * @param direction a String representing whether the rainbow stripes are horizontal or vertical.
   * @param height the height of the proposed Rainbow, in pixels.
   * @param width the width of the proposed Rainbow, in pixels.
   * @return a boolean representing whether the specified dimensions are valid to make a Rainbow.
   */
  private boolean invalidWidth(String direction, int height, int width) {
    if (direction.equals("vertical")) {
      if (width % 7 != 0) {
        int higherSevenMultiple = (7 * ((width / 7) + 1));
        return (higherSevenMultiple - width >= (higherSevenMultiple / 7));
      }
    }
    else {
      if (height % 7 != 0) {
        int higherSevenMultiple = 7 * ((height / 7) + 1);
        return (higherSevenMultiple - height >= (higherSevenMultiple / 7));
      }
    }
    return false;
  }
}

