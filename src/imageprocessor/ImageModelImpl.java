package imageprocessor;

import java.io.IOException;

/**
 * The ImageModelImpl class represents an image object, whose data is stored as a 3-D int[][][]
 * array of pixels.
 *
 * <p>The first array represents the row of the pixel in the image, the second array
 * represents the column, and the third represents the RGB channel values of the specific pixel
 * as {R, G, B}. The image can be based on a pre-existing image uploaded from a filepath, which is
 * created from converting an image file png, jpg, bmp, or other visual image file into
 * a 3-D int[][][] array using the ImageUtil class. The image could also be a new instantiation of
 * a type of pattern or design, with specific dimensions. This class has two constructors, one for
 * converting an existing image file into a 3-D int[][][] array and one for instantiating an array
 * that represents a new pattern or design image.</p>
 */
public class ImageModelImpl implements ImageModelExtension {
  private int[][][] data;
  private int height;
  private int width;
  private int[][][] modifiedData;

  /**
   * This constructor for the ImageModelImpl class instantiates a new 3-D int[][][] image array
   * by using the ImageUtil class to convert a file into the array.
   *
   * <p>The constructor takes in a String parameter specifying the filepath. If the filepath
   * is invalid or the file cannot be read, the constructor throws an
   * IllegalArgumentException to notify the user the instantiation has failed.</p>
   *
   * @param filepath a String representation of the filepath of the image.
   * @throws IllegalArgumentException if the filepath or image stored at the filepath is invalid.
   * @deprecated no longer supported to allow users to create images from non-disk.
   * @link #line 86
   */
  @Deprecated
  public ImageModelImpl(String filepath) throws IllegalArgumentException {
    if (filepath == null) {
      throw new IllegalArgumentException("Error: Invalid filepath.");
    }
    try {
      this.data = ImageUtil.readImage(filepath);
      this.height = ImageUtil.getHeight(filepath);
      this.width = ImageUtil.getWidth(filepath);
      this.modifiedData = data.clone();
    } catch (IOException e) {
      throw new IllegalArgumentException("Error: Invalid filepath.");
    }
  }

  /**
   * This constructor for the ImageModelImpl class instantiates a new 3-D int[][][] image array
   * representing a design or pattern, based on a user-specified pattern/design type and
   * image dimensions.
   *
   * <p>The design or pattern type must be a Designs enum, because the Designs enum class
   * contains all currently supported design types. If the design is null, or if the dimensions
   * are less than one, the constructor will throw an IllegalArgumentException to notify
   * the user.</p>
   *
   * @param designType a Designs enum describing the type of design to be generated.
   * @param height the user's desired height of the image, in pixels.
   * @param width the user's desired width of the image, in pixels.
   * @throws IllegalArgumentException if the design is null or a dimension is less than one.
   */
  public ImageModelImpl(Designs designType, int height, int width)
          throws IllegalArgumentException {
    if (designType == null) {
      throw new IllegalArgumentException("Error: Cannot be null.");
    }
    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("Error: Height and width must be at least 1.");
    }
    this.width = width;
    this.height = height;
    this.modifiedData = generateDesign(designType, height, width);
  }

  /**
   * This constructor for the ImageModelImpl class instantiates a new 3-D int[][][] image array.
   *
   * @param image represents a 3D array of RGB values at a particular pixel.
   * @throws IllegalArgumentException if null is given as input.
   */
  public ImageModelImpl(int[][][] image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Error: Empty Image");
    }
    if (image.length < 1 || image[0].length < 1 || image[0][0].length < 3) {
      throw new IllegalArgumentException("Error: Invalid Image.");
    }
    this.data = image;
    this.height = image.length;
    this.width = image[0].length;
    this.modifiedData = image;
  }

  /**
   * The applyFilter(Filters features) method applies a specified filter, chosen from the
   * Filters enum list, to an image by instantiating a new filter object and retrieving its data.
   *
   * @param filter the Filters enum the user wishes to apply to this image.
   * @throws IllegalArgumentException if the user's chosen filter is null.
   */
  @Override
  public void applyFilter(Filters filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("Sorry, that filter is not supported.");
    }
    Filter newImage;

    switch (filter) {
      case BLUR:
        newImage = new Blur(modifiedData, height, width);
        break;
      case SHARPEN:
        newImage = new Sharpen(modifiedData, height, width);
        break;
      case GREYSCALE:
        newImage = new GreyScale(modifiedData, height, width);
        break;
      case SEPIA:
        newImage = new Sepia(modifiedData, height, width);
        break;
      case DITHER:
        GreyScale greyImage = new GreyScale(modifiedData, height, width);
        newImage = new Dither(greyImage.modifyImage(), height, width);
        break;
      default:
        throw new IllegalArgumentException("Sorry, that filter is not supported.");
    }
    this.modifiedData = newImage.modifyImage();
  }

  /**
   * The generateDesign(Design design, int height, int width) method generates a new
   * 3-D int[][][] array of the specified design type and dimensions by instantiating a new
   * design object and retrieving its array of pixels.
   *
   * <p>The method will only throw an exception if the design type is null. When the method
   * instantiates new design objects, the objects themselves may throw their own exceptions.
   * </p>
   *
   * @param design a Design enum describing which design should be produced.
   * @param height the user's desired height of the design.
   * @param width the user's desired width of the design.
   * @return a 3-D int[][][] array describing the positions and values of pixels in the design.
   * @throws IllegalArgumentException if the design is null.
   */
  private int[][][] generateDesign(Designs design, int height, int width)
          throws IllegalArgumentException {
    if (design == null) {
      throw new IllegalArgumentException("Cannot be null.");
    }
    Design newImage;

    switch (design) {
      case HORIZONTAL_RAINBOW:
        newImage = new Rainbow("horizontal", height, width);
        break;
      case CHECKERBOARD:
        newImage = new CheckerBoard(height, width);
        break;
      case VERTICAL_RAINBOW:
        newImage = new Rainbow( "vertical", height, width);
        break;
      case FRENCH_FLAG:
        newImage = new FrenchFlag(height, width);
        break;
      case GREEK_FLAG:
        newImage = new GreekFlag(height, width);
        break;
      case SWISS_FLAG:
        newImage = new SwissFlag(height, width);
        break;
      default:
        throw new IllegalArgumentException("Sorry, that design is not supported.");
    }
    this.width = newImage.getWidth();
    this.height = newImage.getHeight();
    return newImage.getImage();
  }

  /**
   * The saveModifiedImage(String filename) method saves a 3-D int[][][] image representation to a
   * specified outfile path.
   *
   * <p>The method throws an IllegalArgumentException if the filepath is invalid. It will also
   * provide a message if an IOException occurs.</p>
   *
   * @param filename a String representing the name of the outfile the image should be written to.
   * @throws IllegalArgumentException if the filepath is invalid.
   * @throws IOException if the computer otherwise fails to save the file.
   * @deprecated refer to getModifiedImage() //TODO: What?
   */
  @Override
  public void saveModifiedImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Error: Invalid filepath.");
    }
    try {
      ImageUtil.writeImage(modifiedData, width, height, filename);
    } catch (IOException e) {
      System.out.println("Error when saving file.");
    }
  }

  /**
   * The saveOriginalImage(String filename) method saves the image model's original
   * 3-D int[][][] image representation to a specified outfile path.
   *
   * <p>The method throws an IllegalArgumentException if the filepath is invalid. It will also
   * provide a message if an IOException occurs.</p>
   *
   * @param filename a String representing the name of the outfile the image should be written to.
   * @throws IllegalArgumentException if the filepath is invalid.
   * @deprecated refer to getOriginalImage()
   */
  @Override
  public void saveOriginalImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Error: Invalid filepath.");
    }
    try {
      ImageUtil.writeImage(data, width, height, filename);
    } catch (IOException e) {
      System.out.println("Error when saving file.");
    }
  }

  /**
   * Return a modified image.
   *
   * <p>The method saves a representation of an image to a specified. The image will be what is
   * produced following any modifications, or will be the original image if there have been no
   * modifications.</p>
   *
   * @return a modified image.
   */
  @Override
  public int[][][] getModifiedImage() {
    return modifiedData;
  }

  /**
   * Return the original image.
   *
   * @return an original image.
   */
  @Override
  public int[][][] getOriginalImage() {
    return data;
  }

  /**
   * The method applies a mosaic filter to an image based on the number of seeds the user
   * specifies.
   *
   * @param numOfSeeds the number of seeds to be selected in the Mosaic.
   */
  @Override
  public void mosaic(int numOfSeeds) {
    Filter mosaic = new Mosaic(modifiedData, height, width, numOfSeeds);
    modifiedData = mosaic.modifyImage();
  }
}
