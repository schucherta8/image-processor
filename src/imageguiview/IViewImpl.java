package imageguiview;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;

import imageguicontroller.Features;
import imageprocessor.Designs;
import imageprocessor.Filters;

/**
 * The IViewImpl class represents an implementation of the IView to be used as a GUI in a
 * model-view-controller implementation of the Image Processor.
 *
 * <p>The IViewImpl uses JSwing to establish an interface of panes and menus that users
 * can intuitively use to upload, generate, load, and save images. Users may also type in
 * commands for the Image Processor to follow, or may upload a text file of commands.
 * The IViewImpl establishes ActionListener lambda-type methods so that the Controller can
 * receive and process information about what the user clicks in the GUI.</p>
 */
public class IViewImpl extends JFrame implements IView {

  /**
   * The imageLabel attribute is a JLabel representing the label of an image.
   */
  private JLabel imageLabel;

  /**
   * The instructions attribute is a JTextArea where users can type in commands to be run.
   */
  private JTextArea instructions;

  /**
   * The JMenuItem load is a button to be pressed in the command bar to load an image.
   */
  private JMenuItem load;

  /**
   * The JMenuItem save is a button to be pressed in the command bar to save an image.
   */
  private JMenuItem save;

  /**
   * The JMenuItem quit is a button to be pressed in the command bar to quit the program.
   */
  private JMenuItem quit;

  /**
   * The JMenuItem undo is a button to be pressed in the command bar to undo a change to the image.
   */
  private JMenuItem undo;

  /**
   * The JMenuItem redo is a button to be pressed in the command bar to redo a change to the image.
   */
  private JMenuItem redo;

  /**
   * The JMenuItem uploadCommands is a button to be pressed in the command bar to upload commands.
   */
  private JMenuItem uploadCommands;

  /**
   * The JMenuItem runCommands is a button to be pressed in the command bar to run user commands.
   */
  private JMenuItem runCommands;

  /**
   * The JMenuItem sharpen represents a sharpen filtering operation.
   */
  private JMenuItem sharpen;

  /**
   * The JMenuItem blur represents a blur filtering operation.
   */
  private JMenuItem blur;

  /**
   * The JMenuItem dither represents a dither filtering operation.
   */
  private JMenuItem dither;

  /**
   * The JMenuItem sepia represents a sepia filtering operation.
   */
  private JMenuItem sepia;

  /**
   * The JMenuItem greyScale represents a greyScale filtering operation.
   */
  private JMenuItem greyScale;

  /**
   * The JMenuItem mosaic represents a mosaic filtering operation.
   */
  private JMenuItem mosaic;

  /**
   * The JMenuItem checkerboard represents a checkerboard design that may be generated.
   */
  private JMenuItem checkerboard;

  /**
   * The JMenuItem swiss represents a Swiss flag design that may be generated.
   */
  private JMenuItem swiss;

  /**
   * The JMenuItem greek represents a Greek flag design that may be generated.
   */
  private JMenuItem greek;

  /**
   * The JMenuItem french represents a French flag design that may be generated.
   */
  private JMenuItem french;

  /**
   * The JMenuItem horizontalRainbow represents a horizontal rainbow design that may be generated.
   */
  private JMenuItem horizontalRainbow;

  /**
   * The JMenuItem verticalRainbow represents a vertical rainbow design that may be generated.
   */
  private JMenuItem verticalRainbow;

  /**
   * Constructs and initializes the GUI for the Image Processor.
   *
   * <p>This constructor sets the dimensions, layouts, panels, and menu bars for the GUI.</p>
   *
   * @param caption represents the title for the GUI application.
   */
  public IViewImpl(String caption) {
    super(caption);

    setSize(800, 800);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);


    this.setLayout(new FlowLayout());
    JSplitPane panels = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JSplitPane(),
            new JSplitPane());
    this.add(panels, BorderLayout.CENTER);
    panels.setResizeWeight(0.5);
    JPanel text = new JPanel();
    JPanel image = new JPanel();
    panels.setTopComponent(image);
    panels.setBottomComponent(text);

    setImagePanel(image);
    setScriptPanel(text);

    //Create the menu bar.
    JMenuBar menuBar = new JMenuBar();
    setFileMenu(menuBar);
    setEditMenu(menuBar);
    setCommandsMenu(menuBar);
    setDesignTypeMenu(menuBar);
    setFilterTypeMenu(menuBar);

    this.setJMenuBar(menuBar);
    pack();
    setVisible(true);

  }

  /**
   * The setFeatures(Features features) method adds ActionListeners to various items in the
   * view that the user may click. Each ActionListener will send a signal to the Controller,
   * most typically via methods in the Features interface.
   *
   * @param features the set of feature method callbacks as a Features object.
   */
  @Override
  public void setFeatures(Features features) {
    setLoadScriptFeature(features);
    setLoadFileFeature(features);
    setSaveFileFeature(features);
    quit.addActionListener(e -> System.exit(0));
    setEditFeatures(features);
    setDesignTypeFeatures(features);
    setFilterTypeFeatures(features);
  }

  /**
   * This method sets a JPanel to be in charge of displaying images.
   *
   * @param image represents a JPanel that will display an image.
   */
  private void setImagePanel(JPanel image) {
    imageLabel = new JLabel();
    JScrollPane imageScroll = new JScrollPane(imageLabel);
    imageScroll.setPreferredSize(new Dimension(600, 500));
    image.add(imageScroll, BorderLayout.CENTER);
  }

  /**
   * This method sets a JPanel to be in charge of displaying script operations.
   *
   * @param text represents a JPanel that will display script operations.
   */
  private void setScriptPanel(JPanel text) {
    instructions = new JTextArea(5, 20);
    text.add(new JScrollPane(instructions));
    JLabel areaLabel = new JLabel("Enter text commands here");
    text.add(areaLabel);
  }

  /**
   * This method sets a menu to hold all of the filter type operations.
   *
   * @param menu represents the JMenuBar that this new Filter JMenu should be added to.
   */
  private void setFilterTypeMenu(JMenuBar menu) {
    JMenu filterMenu = new JMenu("Apply Filter");
    blur = new JMenuItem("Blur");
    blur.setActionCommand("Blur");
    filterMenu.add(blur);

    sharpen = new JMenuItem("Sharpen");
    sharpen.setActionCommand("Sharpen");
    filterMenu.add(sharpen);

    greyScale = new JMenuItem("GreyScale");
    greyScale.setActionCommand("GreyScale");
    filterMenu.add(greyScale);

    sepia = new JMenuItem("Sepia");
    sepia.setActionCommand("Sepia");
    filterMenu.add(sepia);

    dither = new JMenuItem("Dither");
    dither.setActionCommand("Dither");
    filterMenu.add(dither);

    filterMenu.addSeparator();

    mosaic = new JMenuItem("Mosaic");
    filterMenu.add(mosaic);
    menu.add(filterMenu);
  }

  /**
   * This method sets a menu to hold all of the File type operations.
   *
   * @param menu represents the JMenuBar that this new File JMenu should be added to.
   */
  private void setFileMenu(JMenuBar menu) {
    JMenu fileMenu = new JMenu("File");
    load = new JMenuItem("Load Image...");
    fileMenu.add(load);
    save = new JMenuItem("Save Image...");
    fileMenu.add(save);
    fileMenu.addSeparator();
    quit = new JMenuItem("Quit");
    fileMenu.add(quit);
    menu.add(fileMenu);
  }

  /**
   * This method sets a menu to hold all of the Edit type operations.
   *
   * @param menu represents the JMenuBar that this new Edit JMenu should be added to.
   */
  private void setEditMenu(JMenuBar menu) {
    JMenu editMenu = new JMenu("Edit");
    undo = new JMenuItem("Undo");
    editMenu.add(undo);
    redo = new JMenuItem("Redo");
    editMenu.add(redo);
    menu.add(editMenu);
  }


  /**
   * This method sets a menu to hold all of the Run Command type operations.
   *
   * <p>These include methods for running a text file containing commands and for running commands
   * the user has provided in the text area at the bottom of the GUI.</p>
   *
   * @param menu represents the JMenuBar that this new Edit JMenu should be added to.
   */
  private void setCommandsMenu(JMenuBar menu) {
    JMenu commandsMenu = new JMenu("Run Commands");
    uploadCommands = new JMenuItem("Upload Instructions");
    commandsMenu.add(uploadCommands);
    runCommands = new JMenuItem("Run Commands in Textbox");
    commandsMenu.add(runCommands);
    menu.add(commandsMenu);
  }

  /**
   * This method sets up a menu to hold all of the Design Generation type operations.
   *
   * @param menu represents the JMenuBar that this new Generate Design JMenu should be added to.
   */
  private void setDesignTypeMenu(JMenuBar menu) {
    JMenu generateMenu = new JMenu("Generate Design");
    horizontalRainbow = new JMenuItem("Horizontal Rainbow...");
    generateMenu.add(horizontalRainbow);
    verticalRainbow = new JMenuItem("Vertical Rainbow...");
    generateMenu.add(verticalRainbow);
    checkerboard = new JMenuItem("Checkerboard...");
    generateMenu.add(checkerboard);
    JMenu flag = new JMenu("Flag");
    swiss = new JMenuItem("Swiss Flag...");
    flag.add(swiss);
    greek = new JMenuItem("Greek Flag...");
    flag.add(greek);
    french = new JMenuItem("French Flag...");
    flag.add(french);
    generateMenu.add(flag);
    menu.add(generateMenu);
  }

  /**
   * This method takes in the set of callbacks from the controller, and sets up an
   * ActionListener for the Save Image JMenuItem.
   *
   * <p>Specifically, the setSaveFileFeature method pops up a new JFileChooser object for the
   * user to select a filepath to save to. Afterward, the method will pass the filepath to
   * the Controller under the Features class.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setSaveFileFeature(Features features) {
    save.addActionListener(e -> {
      final JFileChooser jfc = new JFileChooser();
      jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, GIF, PNG Images", "jpg", "gif", "png");
      jfc.setFileFilter(filter);
      int retValue = jfc.showSaveDialog(IViewImpl.this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = jfc.getSelectedFile();
        features.saveImage(f.getAbsolutePath());
      }
    });
  }

  /**
   * This method takes in the set of callbacks from the controller, and sets up an
   * ActionListener for the Load Text Instructions JMenuItem.
   *
   * <p>The Upload Instructions JMenuItem is a feature where users can select a .txt filepath
   * of instructions to be loaded and operated. Specifically, the setLoadScriptFeature method pops
   * up a new JFileChooser object for the user to select a .txt filepath to load. Afterward, the
   * method will pass the filepath to the Controller under the Features class.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setLoadScriptFeature(Features features) {
    uploadCommands.addActionListener(e -> {
      final JFileChooser jfc = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Txt files", "txt");
      jfc.setFileFilter(filter);
      int retValue = jfc.showOpenDialog(IViewImpl.this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = jfc.getSelectedFile();
        try {
          FileReader stream = new FileReader(f);
          features.processTextInstructions(stream);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
      }
    });
    runCommands.addActionListener(e -> {
      Readable commands = new StringReader(instructions.getText());
      String inst = instructions.getText();
      features.processTextInstructions(commands);
    });
  }

  /**
   * This method takes in the set of callbacks from the controller, and establishes an
   * ActionListener method for the Load JMenuItem.
   *
   * <p>Specifically, the setSaveFileFeature method pops up a new JFileChooser object for the
   * user to select a filepath of an image to load. Afterward, the method passes the filepath to
   * the Controller under the Features class.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setLoadFileFeature(Features features) {
    load.addActionListener(e -> {
      final JFileChooser jfc = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, GIF, PNG Images", "jpg", "gif", "png");
      jfc.setFileFilter(filter);
      int retValue = jfc.showOpenDialog(IViewImpl.this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = jfc.getSelectedFile();
        int[][][] image = features.loadImage(f.getAbsolutePath());
        displayImage(image);
      }
    });
  }

  /**
   * This method takes in the set of callbacks from the controller, and establishes an
   * ActionListener method for each Design Generation-type JMenuItem.
   *
   * <p>Specifically, the setDesignTypeFeatures method adds an action listener for every design
   * by passing the click's design type to the Controller under the Features class, so that the
   * Controller can generate a new Design image.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setDesignTypeFeatures(Features features) {
    checkerboard.addActionListener(e -> features.generateImage(Designs.CHECKERBOARD));
    greek.addActionListener(e -> features.generateImage(Designs.GREEK_FLAG));
    swiss.addActionListener(e -> features.generateImage(Designs.SWISS_FLAG));
    french.addActionListener(e -> features.generateImage(Designs.FRENCH_FLAG));
    horizontalRainbow.addActionListener(e -> features.generateImage(Designs.HORIZONTAL_RAINBOW));
    verticalRainbow.addActionListener(e -> features.generateImage(Designs.VERTICAL_RAINBOW));
  }

  /**
   * This method takes in the set of callbacks from the controller, and establishes an
   * ActionListener method for each Filter-type JMenuItem.
   *
   * <p>Specifically, the setDesignTypeFeatures method adds an action listener for every filter
   * by passing the click's filter type to the Controller under the Features class, so that the
   * Controller can generate a new filtered image based on the selected filter type.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setFilterTypeFeatures(Features features) {
    blur.addActionListener(e -> features.applyFilter(Filters.BLUR));
    sharpen.addActionListener(e -> features.applyFilter(Filters.SHARPEN));
    sepia.addActionListener(e -> features.applyFilter(Filters.SEPIA));
    greyScale.addActionListener(e -> features.applyFilter(Filters.GREYSCALE));
    dither.addActionListener(e -> features.applyFilter(Filters.DITHER));
    mosaic.addActionListener(e -> features.mosaic());
  }

  /**
   * This method takes in the set of callbacks from the controller, and establishes an
   * ActionListener method for the Undo and Redo JMenuItems.
   *
   * <p>Specifically, the setDesignTypeFeatures method adds an action listener for undo and redo
   * by passing the click information to the Controller, so that the Controller can complete
   * an undo or redo operation under the Features class.</p>
   *
   * @param features the set of feature callbacks as a Features object.
   */
  private void setEditFeatures(Features features) {
    undo.addActionListener(e -> features.undo());
    redo.addActionListener(e -> features.redo());
  }

  /**
   * The displayImage method takes a 3-D int[][][] array of RGB values of an image, and converts
   * it into an image displayed in the GUI interface for a user to see. It returns nothing
   * but shows the user the image in the display.
   *
   * @param image the 3-D int[][][] array to be displayed to the user.
   */
  public void displayImage(int[][][] image) {
    BufferedImage output = new BufferedImage(image[0].length,
            image.length, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        int r = image[i][j][0];
        int g = image[i][j][1];
        int b = image[i][j][2];

        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    imageLabel.setIcon(new ImageIcon(output));
  }

  /**
   * The displayError method takes a String error message and displays a
   * pop-up Error message to the user, with the text for the error message.
   *
   * @param error the error message the pop-up should display.
   */
  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * The getInput method creates a pop-up window where a user is prompted a message for a specified
   * input.
   *
   * @param message represents the the message displayed to the user.
   * @return a String representing the user's input of how many seeds they wish for.
   */
  @Override
  public String getInput(String message) {
    return JOptionPane.showInputDialog(this,message);
  }
}
