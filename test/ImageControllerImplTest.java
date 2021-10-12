import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import imageprocessor.Designs;
import imageprocessor.Filters;
import imageprocessor.ImageModelExtension;
import imageprocessor.ImageModelImpl;
import imageprocessor.ImageUtil;

import imagecontroller.ImageController;
import imagecontroller.ImageControllerImpl;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * This is a test class for the ImageControllerImpl. It ensures the Controller can correctly
 * interpret and execute an extensive variety of user commands.
 */
public class ImageControllerImplTest {

  @Test
  public void testDitherNewController() throws IOException {
    ImageModelExtension m = new ImageModelImpl("res/city.png");
    int width = ImageUtil.getWidth("res/city.png");
    int height = ImageUtil.getHeight("res/city.png");
    m.applyFilter(Filters.DITHER);
    ImageUtil.writeImage(m.getModifiedImage(), width, height,
            "res/city-dither-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null, new FileReader("res/testFile.txt"));
    testController.start();
    int[][][] firstCityDither = ImageUtil.readImage("res/city-dither-firstMethod.png");
    int[][][] secondCityDither = ImageUtil.readImage("res/city-dither.png");
    for (int i = 0; i < firstCityDither.length; i++) {
      for (int j = 0; j < firstCityDither.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstCityDither[i][j][k], secondCityDither[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testGenerateSave() throws IOException {
    ImageModelExtension n = new ImageModelImpl(Designs.VERTICAL_RAINBOW, 10, 7);
    ImageUtil.writeImage(n.getModifiedImage(), 7, 10,
            "res/generateSave-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null,
                    new FileReader("res/testGenerateSave.txt"));
    testController.start();
    int[][][] firstMethod = ImageUtil.readImage("res/generateSave-firstMethod.png");
    int[][][] secondMethod = ImageUtil.readImage("res/testGenerateVerticalRainbow.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstMethod[i][j][k], secondMethod[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testBadOrderSaveLoad() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testSaveBeforeLoad.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Image not found.", e.getMessage());
    }
  }

  @Test
  public void testBadOrderApplyLoad() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testApplyBeforeLoad.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Image not found.", e.getMessage());
    }
  }

  @Test
  public void testBlurTwiceNewController() throws IOException {
    ImageModelExtension m = new ImageModelImpl("res/blur.png");
    int width = ImageUtil.getWidth("res/blur.png");
    int height = ImageUtil.getHeight("res/blur.png");
    m.applyFilter(Filters.BLUR);
    ImageUtil.writeImage(m.getModifiedImage(), width, height,
            "res/blurTwice-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null,
                    new FileReader("res/testLoadApplyApplySave.txt"));
    testController.start();
    int[][][] firstMethod = ImageUtil.readImage("res/blurTwice-firstMethod.png");
    int[][][] secondMethod = ImageUtil.readImage("res/testblur2.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstMethod[i][j][k], secondMethod[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testSavingMultipleFilesNewController() throws IOException {
    ImageModelExtension m = new ImageModelImpl(Designs.HORIZONTAL_RAINBOW, 7, 10);
    ImageUtil.writeImage(m.getModifiedImage(), 10, 7,
            "res/rainbow-firstMethod.png");
    ImageModelExtension n = new ImageModelImpl(Designs.CHECKERBOARD, 8, 8);
    ImageUtil.writeImage(n.getModifiedImage(), 8, 8,
            "res/checkerboard-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null,
                    new FileReader("res/testGenerateSaveFiles.txt"));
    testController.start();
    int[][][] firstMethod = ImageUtil.readImage("res/rainbow-firstMethod.png");
    int[][][] secondMethod = ImageUtil.readImage("res/testGenerateHorizontalRainbow.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod.length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstMethod[i][j][k], secondMethod[i][j][k]);
        }
      }
    }
    int[][][] firstCheckerboard = ImageUtil.readImage("res/checkerboard-firstMethod.png");
    int[][][] secondCheckerboard = ImageUtil.readImage("res/testGenerateCheckerBoard.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstCheckerboard[i][j][k], secondCheckerboard[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testLoadApplyGenerate() throws IOException {
    ImageModelExtension n = new ImageModelImpl(Designs.CHECKERBOARD, 10, 10);
    ImageUtil.writeImage(n.getModifiedImage(), 10, 10,
            "res/loadApplyGenerate-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null,
                    new FileReader("res/testLoadApplyGenerateSave.txt"));
    testController.start();
    int[][][] firstMethod = ImageUtil.readImage("res/loadApplyGenerate-firstMethod.png");
    int[][][] secondMethod = ImageUtil.readImage("res/testOverwriteImages.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstMethod[i][j][k], secondMethod[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testLoadGenerateApply() throws IOException {
    ImageModelExtension n = new ImageModelImpl(Designs.VERTICAL_RAINBOW, 10, 7);
    n.applyFilter(Filters.SEPIA);
    ImageUtil.writeImage(n.getModifiedImage(), 7, 10,
            "res/sepiaRainbow-firstMethod.png");
    ImageController testController =
            new ImageControllerImpl(null,
                    new FileReader("res/testLoadGenerateApplySave.txt"));
    testController.start();
    int[][][] firstMethod = ImageUtil.readImage("res/sepiaRainbow-firstMethod.png");
    int[][][] secondMethod = ImageUtil.readImage("res/testSepiaRainbow.png");
    for (int i = 0; i < firstMethod.length; i++) {
      for (int j = 0; j < firstMethod[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(firstMethod[i][j][k], secondMethod[i][j][k]);
        }
      }
    }
  }

  @Test
  public void invalidCommand() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testInvalidCommand.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid command.", e.getMessage());
    }
  }

  @Test
  public void invalidDesign() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testInvalidDesign.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid design type.", e.getMessage());
    }
  }

  @Test
  public void invalidFilter() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testInvalidFilter.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid filter type.", e.getMessage());
    }
  }

  @Test
  public void missingArgs() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testMissingArguments.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Missing design type, "
              + "height, or width dimension.", e.getMessage());
    }
  }

  @Test
  public void noArgs() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testNoArguments.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Missing filter type.", e.getMessage());
    }
  }

  @Test
  public void tooManyArgs() throws FileNotFoundException {
    try {
      ImageController testController =
              new ImageControllerImpl(null,
                      new FileReader("res/testTooManyArguments.txt"));
      testController.start();
      fail("An error should have been thrown.");
    }
    catch (IllegalArgumentException e) {
      Assert.assertEquals("Error: Not a valid command.", e.getMessage());
    }
  }
}