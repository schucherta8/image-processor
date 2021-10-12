
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import imageprocessor.Designs;
import imageprocessor.Filters;
import imageprocessor.ImageModel;
import imageprocessor.ImageModelExtension;
import imageprocessor.ImageModelImpl;
import imageprocessor.ImageUtil;

import java.io.IOException;

/**
 * This is a test class for the ImageModelImpl class.
 */
public class TestModel {

  @Test
  public void testWrongFilePathInModel() {
    try {
      ImageModel m = new ImageModelImpl("adwada/city.png");
      fail("Exception not thrown.");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testNullFilter() {
    try {
      ImageModel m = new ImageModelImpl("res/city.png");
      m.applyFilter(null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Sorry, that filter is not supported.", e.getMessage());
    }
  }

  @Test
  public void testNonExistingFilter() {
    try {
      ImageModel m = new ImageModelImpl("res/city.png");
      m.applyFilter(null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Sorry, that filter is not supported.", e.getMessage());
    }
  }

  @Test
  public void testBlurJaison() {
    try {
      ImageModel m = new ImageModelImpl("res/jaison.jpg");
      m.applyFilter(Filters.BLUR);
      m.saveModifiedImage("res/blurJaison.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testSharpenJaison() {
    try {
      ImageModel m = new ImageModelImpl("res/jaison.jpg");
      m.applyFilter(Filters.SHARPEN);
      m.saveModifiedImage("res/sharpenJaison.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testBlurSumeet() {
    try {
      ImageModel m = new ImageModelImpl("res/sumeet.jpg");
      m.applyFilter(Filters.BLUR);
      m.saveModifiedImage("res/blurSumeet.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testSharpenSumeet() {
    try {
      ImageModel m = new ImageModelImpl("res/sumeet.jpg");
      m.applyFilter(Filters.SHARPEN);
      m.saveModifiedImage("res/sharpenSumeet.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testSepiaSumeet() {
    try {
      ImageModel m = new ImageModelImpl("res/sumeet.jpg");
      m.applyFilter(Filters.SEPIA);
      m.saveModifiedImage("res/sepiaSumeet.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testGreyScaleSumeet() {
    try {
      ImageModel m = new ImageModelImpl("res/sumeet.jpg");
      m.applyFilter(Filters.GREYSCALE);
      m.saveModifiedImage("res/greySumeet.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testSepiaJaison() {
    try {
      ImageModel m = new ImageModelImpl("res/jaison.jpg");
      m.applyFilter(Filters.SEPIA);
      m.saveModifiedImage("res/sepiaJaison.jpg");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testBlurTimesTwo() throws IOException {
    int[][][] website = ImageUtil.readImage("res/websiteblurtimestwo.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.BLUR);
    m.applyFilter(Filters.BLUR);
    m.saveModifiedImage("res/blurtimestwo.png");
    int[][][] pixelSepia = ImageUtil.readImage("res/blurtimestwo.png");
    for (int i = 0; i < website.length; i++) {
      for (int j = 0; j < website.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(website[i][j][k], pixelSepia[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testSharpen() throws IOException {
    int[][][] website = ImageUtil.readImage("res/websitesharpen.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.SHARPEN);
    m.saveModifiedImage("res/sharpen.png");
    int[][][] pixelSepia = ImageUtil.readImage("res/sharpen.png");
    for (int i = 0; i < website.length; i++) {
      for (int j = 0; j < website.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(website[i][j][k], pixelSepia[i][j][k]);
        }
      }
    }
  }


  @Test
  public void testSharpenTimesTwo() throws IOException {
    int[][][] website = ImageUtil.readImage("res/websitesharpentimestwo.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.SHARPEN);
    m.applyFilter(Filters.SHARPEN);
    m.saveModifiedImage("res/sharpentimes2.png");
    int[][][] pixelSepia = ImageUtil.readImage("res/sharpentimes2.png");
    for (int i = 0; i < website.length; i++) {
      for (int j = 0; j < website.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(website[i][j][k], pixelSepia[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testCitySepia() throws IOException {
    int[][][] website = ImageUtil.readImage("res/websitesepia.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.SEPIA);
    m.saveModifiedImage("res/sepia.png");
    int[][][] pixelSepia = ImageUtil.readImage("res/sepia.png");
    for (int i = 0; i < website.length; i++) {
      for (int j = 0; j < website.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(website[i][j][k], pixelSepia[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testCityGrayScale() throws IOException {
    int[][][] website = ImageUtil.readImage("res/websitegreyscale.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.GREYSCALE);
    m.saveModifiedImage("res/greyscale.png");
    int[][][] pixelGreyScale = ImageUtil.readImage("res/greyscale.png");
    for (int i = 0; i < website.length; i++) {
      for (int j = 0; j < website.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(website[i][j][k], pixelGreyScale[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testSaveImageToNull() {
    try {
      ImageModel m = new ImageModelImpl("res/city.png");
      m.applyFilter(Filters.GREYSCALE);
      m.saveModifiedImage(null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Invalid filepath.", e.getMessage());
    }
  }

  @Test
  public void testFrenchWidthDimension() {
    try {
      ImageModel frenchFlag = new ImageModelImpl(Designs.FRENCH_FLAG, 20, 30);
      frenchFlag.saveModifiedImage("res/french_flag.png");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }


  @Test
  public void testSwiss() {
    try {
      ImageModel swissFlag = new ImageModelImpl(Designs.SWISS_FLAG, 64, 64);
      swissFlag.saveModifiedImage("res/swissFlag.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testGreek() {
    try {
      ImageModel greekFlag = new ImageModelImpl(Designs.GREEK_FLAG, 45, 68);
      greekFlag.saveModifiedImage("images/greekFlag.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testRainbow() {
    try {
      ImageModel rainbow = new ImageModelImpl(Designs.VERTICAL_RAINBOW, 10, 7);
      rainbow.saveModifiedImage("res/funkyRainbow.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testRainbowSmallerFinalStripe() {
    try {
      ImageModel rainbow = new ImageModelImpl(Designs.VERTICAL_RAINBOW, 10, 19);
      rainbow.saveModifiedImage("res/smallerRainbowStripe.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testHorizontalRainbow() {
    try {
      ImageModel rainbow = new ImageModelImpl(Designs.HORIZONTAL_RAINBOW, 13, 5);
      rainbow.saveModifiedImage("res/funkyRainbow2.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testCheckerboard() {
    try {
      ImageModel checkerboard = new ImageModelImpl(Designs.CHECKERBOARD, 18, 18);
      checkerboard.saveModifiedImage("res/funkyCheckerboard.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void saveOriginalImage() {
    try {
      ImageModel saveCity = new ImageModelImpl("res/city.png");
      saveCity.saveOriginalImage("res/copyOfCity.png");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid dimension.", e.getMessage());
    }
  }

  @Test
  public void testCityDither() throws IOException {
    int[][][] website = ImageUtil.readImage("res/city.png");
    ImageModel m = new ImageModelImpl("res/city.png");
    m.applyFilter(Filters.GREYSCALE);
    m.applyFilter(Filters.DITHER);
    m.saveModifiedImage("res/dither.png");
    int[][][] pixelDither = ImageUtil.readImage("res/dither.png");
    for (int i = 0; i < pixelDither.length; i++) {
      for (int j = 0; j < pixelDither.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelDither[i][j][k], pixelDither[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testCityMosaic() throws IOException {
    int[][][] website = ImageUtil.readImage("res/city.png");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(1000);
    int width = ImageUtil.getWidth("res/city.png");
    int height = ImageUtil.getWidth("res/city.png");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/cityMosaic.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/cityMosaic.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testCityMosaic4000() throws IOException { // took 5.7 seconds
    int[][][] website = ImageUtil.readImage("res/city.png");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(4000);
    int width = ImageUtil.getWidth("res/city.png");
    int height = ImageUtil.getWidth("res/city.png");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/cityMosaic4000.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/cityMosaic4000.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testCityMosaic15000() throws IOException { // took 17.2 seconds
    int[][][] website = ImageUtil.readImage("res/city.png");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(15000);
    int width = ImageUtil.getWidth("res/city.png");
    int height = ImageUtil.getWidth("res/city.png");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/cityMosaic15000.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/cityMosaic15000.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }


  @Test
  public void testSumeetMosaic() throws IOException {
    int[][][] website = ImageUtil.readImage("res/sumeet.jpg");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(1000);
    int width = ImageUtil.getWidth("res/city.png");
    int height = ImageUtil.getWidth("res/city.png");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/sumeetMosaic.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/sumeetMosaic.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }


  @Test
  public void testJaisonMosaic() throws IOException {
    int[][][] website = ImageUtil.readImage("res/jaison.jpg");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(1000);
    int width = ImageUtil.getWidth("res/jaison.jpg");
    int height = ImageUtil.getHeight("res/jaison.jpg");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/jaisonMosaic.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/jaisonMosaic.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testRainbowMosaic() throws IOException {
    int[][][] website = ImageUtil.readImage("res/rainbow.png");
    ImageModelExtension m = new ImageModelImpl(website);
    m.mosaic(1000);
    int width = ImageUtil.getWidth("res/rainbow.png");
    int height = ImageUtil.getHeight("res/rainbow.png");
    ImageUtil.writeImage(m.getModifiedImage(),width,height,"res/rainbowMosaic.png");
    int[][][] pixelMosaic = ImageUtil.readImage("res/rainbowMosaic.png");
    for (int i = 0; i < pixelMosaic.length; i++) {
      for (int j = 0; j < pixelMosaic.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMosaic[i][j][k], pixelMosaic[i][j][k]);
        }
      }
    }
  }
}
