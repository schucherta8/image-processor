package imageprocessor;

/**
 * The Mosaic class represents a Mosaic image transformation, which can be applied to a
 * 3-D int[][][] array representation of an image.
 *
 * <p> In a Mosaic pattern, a pre-determined number of pixels are randomly selected to
 * be seeds. Each pixel in the image is then matched to the closest seed in the image. The
 * set of all pixels assigned to a particular seed are all transformed so that they all have
 * the same RGB values - the mean of the RGB values of each pixel in the set.
 * The class inherits three attributes from the AbstractFilter class: the img which is
 * a 3-D int[][][] array of RGB values, the height of the image, and the width of the image in
 * pixels.  The class also features an integer called seeds, which stores the number of
 * seeds that should be used when creating a Mosaic image pattern.</p>
 */
class Mosaic extends AbstractFilter {

  /**
   * The seeds attribute is a 2-D int array storing information about each seed in the Mosaic.
   * The first index identifies the seed number, and the second index stores seed information.
   * These attributes include: row, column, R value, G value, B value, and number of pixels
   * assigned to the seed.
   */
  private int[][] seeds;

  /**
   * The constructor of the Mosaic takes in three parameters from AbstractFilter (the image,
   * the image height, and the image width) and one additional parameter, the number of
   * seeds to be used in the image.
   *
   * @param data   the original data set, represented as a 3D array (int[][][]).
   * @param height the height of the data set in pixels, represented as an integer.
   * @param width  the width of the data set in pixels, represented as an integer.
   * @param seedNumber the number of seeds to be present in the Mosaic.
   */
  protected Mosaic(int[][][] data, int height, int width, int seedNumber) {
    super(data, height, width);
    seeds = createSeedArray(seedNumber);
  }

  /**
   * The modifyImage() method does the work of transforming the given image into a Mosaic image.
   *
   * <p> In a Mosaic image transformation, a pre-determined number of pixels are randomly selected
   * to be seeds. Each pixel in the image is then matched to the closest seed in the image. The
   * set of all pixels assigned to a particular seed are all transformed so that they all have
   * the same RGB values - the mean of the RGB values of each pixel in the set.</p>
   *
   * @return a 3-D int[][][] array representing the RGB values of each pixel in the new Mosaic.
   */
  @Override
  public int[][][] modifyImage() {
    int[][] seedAssignments = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int closestSeed = selectNearestSeed(i, j);
        seedAssignments[i][j] = closestSeed;
        updateSeedColor(closestSeed, i, j);
      }
    }
    return constructFinalMosaic(seedAssignments);
  }

  /**
   * The changePixel() method modifies an individual pixel's RGB values.
   *
   * <p>This method ensures the new Mosaic class retains compatibility with Version 1.0. If
   * a user calls changePixel() on a Mosaic object, this method first runs modifyImage to
   * turn the current image to a mosaic, and then returns the value of that pixel.</p>
   *
   * @param row    the row of the pixel in a 3-D int[][][] array storing an image's RGB values.
   * @param column the column of the pixel to be modified in the current pixel.
   * @return an integer representing the new R, G, or B value of the pixel.
   */
  @Override
  public int changePixel(int row, int column, int channel) {
    return modifyImage()[row][column][channel];
  }

  /**
   * The createSeedArray() method serves as a helper method to modifyImage() in the Mosaic class.
   * The method establishes an array of x number of seeds to use.
   *
   * <p>Each seed in the array will store: its x-coordinate, its y-coordinate, its RGB values,
   * and a denominator value representing the number of pixels that have been assigned to that
   * seed.</p>
   *
   * @param seedNumber the number of seeds that are to be used in the Mosaic construction.
   * @return a 2-D int[][] array representing each seed's coordinates and RGB values.
   * @throws IllegalArgumentException when the seed amount is less than 1.
   */
  private int[][] createSeedArray(int seedNumber) throws IllegalArgumentException {
    if (seedNumber < 1) {
      throw new IllegalArgumentException("Error: Not a valid number amount.");
    }
    int[][] seedArray = new int[seedNumber][6];
    int maxHeight = height - 1;
    int maxWidth = width - 1;
    for (int i = 0; i < seedNumber; i++) {
      seedArray[i][0] = (int) (Math.random() * maxHeight); // the seed row
      seedArray[i][1] = (int) (Math.random() * maxWidth);  // the seed column
      seedArray[i][2] = 0; // the seed R value
      seedArray[i][3] = 0; // G value
      seedArray[i][4] = 0; // B value
      seedArray[i][5] = 0; // Denominator
    }
    return seedArray;
  }


  /**
   * The selectNearestSeed method determines which seed in the image is closest to a given
   * pixel.
   *
   * <p>The method iterates through every seed, passes the pixel and each seed into a
   * method that calculates the distance between each pair. The closest seed to the pixel
   * continually updates during the iteration.</p>
   *
   * @param pointRow the row where the current pixel is located.
   * @param pointColumn the column where the current pixel is located.
   * @return an integer representing the index of the closest seed in the seeds attribute.
   */
  private int selectNearestSeed(int pointRow, int pointColumn) {
    double bestDistance = Double.POSITIVE_INFINITY;
    int bestAssignment = 0;
    for (int i = 0; i < seeds.length; i++) {
      double distance = calculateDistance(pointRow, pointColumn, seeds[i]);
      if (distance < bestDistance) {
        bestDistance = distance;
        bestAssignment = i;
      }
    }
    return bestAssignment;
  }

  /**
   * The calculateDistance method calculates the distance between a given pixel and a given seed
   * by calculating the Euclidean distance between their coordinates.
   *
   * @param pixelRow the row of the pixel.
   * @param pixelColumn the column of the pixel.
   * @param seedCoordinates coordinates of the seed, its row coordinate at [0] and column at [1].
   * @return a double representing the distance between the pixel and the seed.
   */
  private double calculateDistance(int pixelRow, int pixelColumn, int[] seedCoordinates) {
    return Math.sqrt(Math.pow(pixelRow - seedCoordinates[0], 2)
            + Math.pow(pixelColumn - seedCoordinates[1], 2));
  }



  /**
   * The updateSeedColor method modifies the RGB values stored at a current seed by updating
   * the mean R, G, and B values based on the addition of a new pixel.
   *
   * <p>Each seed in the seeds attribute contains its position, RGB values, and a denominator
   * attribute representing the number of pixels contributing to the seed's color. This method
   * adds 1 to the denominator and re-calculates the mean RGB values accordingly.</p>
   *
   * @param seedNumber the index of the seed to be updated in the seeds array attribute.
   * @param row the row of the pixel that is now updating the seed's RGB mean values.
   * @param column the column of the pixel that is now updating the seed's RGB mean values.
   */
  private void updateSeedColor(int seedNumber, int row, int column) {
    int[] originalPixelColor = image[row][column];
    double oldDenominator = seeds[seedNumber][5];
    seeds[seedNumber][5]++;
    double newDenominator = seeds[seedNumber][5];
    double r = (seeds[seedNumber][2] * oldDenominator + originalPixelColor[0]) / newDenominator;
    double g = (seeds[seedNumber][3] * oldDenominator + originalPixelColor[1]) / newDenominator;
    double b = (seeds[seedNumber][4] * oldDenominator + originalPixelColor[2]) / newDenominator;
    seeds[seedNumber][2] = clamp(r);
    seeds[seedNumber][3] = clamp(g);
    seeds[seedNumber][4] = clamp(b);
  }

  /**
   * The constructFinalMosaic method takes in the assignments of each pixel to their seed, and
   * constructs a new 3-D int[][][] array where each pixel's RGB values match the RGB values
   * of the seed.
   *
   * @param seedAssignments a 2-D array representing the assignment of each pixel to a seed.
   * @return a new 3-D int[][][] array representing a Mosaic image.
   */
  private int[][][] constructFinalMosaic(int[][] seedAssignments) {
    int[][][] result = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j][0] = seeds[seedAssignments[i][j]][2];
        result[i][j][1] = seeds[seedAssignments[i][j]][3];
        result[i][j][2] = seeds[seedAssignments[i][j]][4];
      }
    }
    return result;
  }

}