package imageprocessor;

/**
 * This class serves as a driver for ImageModel. It produces res of different filter types as
 * well as generating res to a particular filepath.
 */
public class ImageDriver {

  /**
   * The main(String[] args) method is the driver for the ImageDriver class.
   * @param args a default parameter for the driver.
   */
  public static void main1(String[] args) {

    ImageModel cityBlur = new ImageModelImpl("res/city.png");
    cityBlur.applyFilter(Filters.BLUR);
    cityBlur.saveModifiedImage("res/blur.png");
    cityBlur.applyFilter(Filters.BLUR);
    cityBlur.saveModifiedImage("res/blurtimestwo.png");

    ImageModel citySharpen = new ImageModelImpl("res/city.png");
    citySharpen.applyFilter(Filters.SHARPEN);
    citySharpen.saveModifiedImage("res/sharpen.png");
    citySharpen.applyFilter(Filters.SHARPEN);
    citySharpen.saveModifiedImage("res/sharpentimestwo.png");

    ImageModel citySepia = new ImageModelImpl("res/city.png");
    citySepia.applyFilter(Filters.SEPIA);
    citySepia.saveModifiedImage("res/sepia.png");

    ImageModel cityGreyScale = new ImageModelImpl("res/city.png");
    cityGreyScale.applyFilter(Filters.GREYSCALE);
    cityGreyScale.saveModifiedImage("res/greyscale.png");

    ImageModel rainbowVertical
            = new ImageModelImpl(Designs.VERTICAL_RAINBOW, 500, 700);
    rainbowVertical.saveModifiedImage("res/rainbow_vertical.png");

    ImageModel rainbowHorizontal
            = new ImageModelImpl(Designs.HORIZONTAL_RAINBOW, 500, 700);
    rainbowHorizontal.saveModifiedImage("res/rainbow_horizontal.png");

    ImageModel checkerboard = new ImageModelImpl(Designs.CHECKERBOARD, 320, 320);
    checkerboard.saveModifiedImage("res/checkerboard.png");

    ImageModel frenchFlag = new ImageModelImpl(Designs.FRENCH_FLAG, 540, 540);
    frenchFlag.saveModifiedImage("res/french_flag.png");

    ImageModel swissFlag = new ImageModelImpl(Designs.SWISS_FLAG, 540, 540);
    swissFlag.saveModifiedImage("res/swiss_flag.png");

    ImageModel greekFlag = new ImageModelImpl(Designs.GREEK_FLAG,540,540);
    greekFlag.saveModifiedImage("res/greek_flag.png");

  }
}
