package imageprocessor;

/**
 * The Design interface includes a method to generate a 3-D integer array representing the RGB
 * values of a visual design and to retrieve a set of RGB integer array data.
 */
interface Design {

  /**
   * The generate() method creates a 3-D int[][][] array representing a new set of RGB channels
   * for pixels sorted in rows and columns, representing the pixels' positions in an image.
   */
  int[][][] generate();

  /**
   * The getImage() method retrieves the 3-D array of RGB values for each pixel in a set of
   * rows and columns, and returns them to the user.
   *
   * @return a 3-D array representing the RGB channel values of each pixel in an image.
   */
  int[][][] getImage();

  /**
   * The getHeight() method retrieves the height of an object as an integer and returns it to the
   * user.
   *
   * @return an integer representing the height of the object.
   */
  int getHeight();

  /**
   * The getWidth() method retrieves the width of the object as an integer and returns it to the
   * user.
   *
   * @return an integer representing the width of the object.
   */
  int getWidth();
}
