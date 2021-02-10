/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

/*
Susan McCartney
February 5, 2021
CSCI 442 - Assignment 1
 */
package SourceCodeFor442Himp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.prefs.Preferences;


class IMP implements MouseListener, ChangeListener {
   JFrame frame;
   JPanel mp;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   //Instance Fields you will be using below
   
   //This will be your height and width of your 2d array
   int height=0, width=0;
   
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pull down
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Image Processing Software by Hunter");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu functions = getFunctions();
      frame.addWindowListener(new WindowAdapter(){
            @Override
              public void windowClosing(WindowEvent ev){quit();}
            });
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      bar.add(file);
      bar.add(functions);
      frame.setSize(600, 600);
      mp = new JPanel();
      mp.setBackground(new Color(0, 0, 0));
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      butPanel.setBackground(Color.black);
      start = new JButton("start");
      start.setEnabled(false);
      start.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){
//                redPanel.drawHistogram();
//                greenPanel.drawHistogram();
//                bluePanel.drawHistogram();
            }
           });
      butPanel.add(start);
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      frame.setVisible(true);      
   }

   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
  private JMenu getFunctions()
  {
     JMenu fun = new JMenu("Functions");
      JMenuItem firstItem = new JMenuItem("MyExample - fun1 method");
      JMenuItem rotateItem = new JMenuItem("Rotate 90 Degrees");
      JMenuItem grayscaleItem = new JMenuItem("Luminosity Grayscale");
      JMenuItem blurItem = new JMenuItem("Blur Image");
      JMenuItem edgeItem = new JMenuItem("Detect Edges");
      JMenuItem histogramItem = new JMenuItem("Histogram Colors");
      JMenuItem equalizeItem = new JMenuItem("Equalization");
      JMenuItem colorItem = new JMenuItem("Track Color");



      firstItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              fun1();
          }
      });
      rotateItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              rotate();
          }
      });
      grayscaleItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              luminosity();
          }
      });
      blurItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              blur();
          }
      });
      edgeItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              edge_detection();
          }
      });
      histogramItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              histogram_colors();
          }
      });
      equalizeItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              equalization();
          }
      });
      colorItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              color_tracker();
          }
      });

//      TODO: Add for quiz **************************
      JMenuItem firstQuizItem = new JMenuItem("Quiz One");
      firstQuizItem.addActionListener((ActionEvent evt) -> {
          firstQuiz();
      });
      JMenuItem secondQuizItem = new JMenuItem("Quiz Two");
      secondQuizItem.addActionListener((ActionEvent evt) -> {
          secondQuiz();
      });
      fun.add(firstQuizItem);
      fun.add(secondQuizItem);
// TODO: *********************************

      fun.add(firstItem);
      fun.add(rotateItem);
      fun.add(grayscaleItem);
      fun.add(blurItem);
      fun.add(edgeItem);
      fun.add(histogramItem);
      fun.add(equalizeItem);
      fun.add(colorItem);
     
      return fun;   

  }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
      Preferences pref = Preferences.userNodeForPackage(IMP.class);
      String path = pref.get("DEFAULT_PATH", "");

      chooser.setCurrentDirectory(new File(path));
     int option = chooser.showOpenDialog(frame);
     
     if(option == JFileChooser.APPROVE_OPTION) {
        pic = chooser.getSelectedFile();
        pref.put("DEFAULT_PATH", pic.getAbsolutePath());
       img = new ImageIcon(pic.getPath());
      }
     width = img.getIconWidth();
     height = img.getIconHeight(); 
     
     JLabel label = new JLabel(img);
     label.addMouseListener(this);
     pixels = new int[width*height];

     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     mp.add(label);
     
     mp.revalidate();
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
      
     
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {
        for(int i = 0; i<width*height; i++)
             pixels[i] = results[i]; 
       Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
     
       mp.revalidate(); 
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
       for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          pixels[i*width+j] = picture[i][j];
      Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
     
       mp.revalidate(); 
   
    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
    }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */
  private void fun1() {
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);

          rgbArray[1] = 0;
          //take three ints for R, G, B and put them back into a single int
           picture[i][j] = getPixels(rgbArray);
        } 
     resetPicture();
  }
  private void rotate() {
        int[][] tempPic = new int[width][height]; //tempPic using proper dimensions
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tempPic[j][height - 1 - i] = picture[i][j]; //moves the pixel into the tempPic placement
            }
        }

        int ht = width; //swap height with width and vice versa
        width = height;
        height = ht;
        picture = new int[height][width]; // resets the length of picture
        picture = tempPic; // move picture to the tempPic spot

        resetPicture(); //rewrites the image
    }
    private void luminosity() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int rgbArray[] = new int[4];

                //get three ints for R, G and B
                rgbArray = getPixelArray(picture[i][j]);


                double gray = .21 * rgbArray[1] + .72* rgbArray[2] + .07*rgbArray[3];

                for(int l = 0; l < 4; l++) {
                    rgbArray[l] = (int)gray;
                }
                //take three ints for R, G, B and put them back into a single int
                picture[i][j] = getPixels(rgbArray);
            }
        resetPicture();
    }

    private void blur() {
        int[][] tempPic = new int[height][width]; //tempPic using proper dimensions

        //goes through all values
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                int rgbArray[] = new int[4];

                //extracts rgb colors
                rgbArray = getPixelArray(picture[i][j]);

                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                //cycles through a 3x3 mask
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (((i + x) >= 0 && (j + y) >= 0 && (i + x) < height && (y + j) < width)) {
                            rgbArray = getPixelArray(picture[i + x][j + y]); //grabs the colors for each of the pixels

                            // sums red, green, and blue
                            sumR += rgbArray[1];
                            sumG += rgbArray[2];
                            sumB += rgbArray[3];
                        }
                    }
                }
                //averages sum from mask
                int red = (sumR /9);
                int green = (sumG /9);
                int blue = (sumB /9);

                //puts average back in array
                rgbArray[1] = red;
                rgbArray[2] = green;
                rgbArray[3] = blue;

                //put the new averaged rgb colors in tempPic
                tempPic[i][j] = getPixels(rgbArray);
            }
        }
        picture = tempPic; // tempPic goes back into original photo
        resetPicture(); //rewrites the image
    }

    private void edge_detection() {
        luminosity();

        int[][] tempPic = new int[height][width]; //tempPic using proper dimensions

        int[][] mask3 = {
                {-1, -1, -1},
                {-1, 8, -1},
                {-1, -1, -1}
        };

        int[][] mask5 = {
                {-1, -1, -1, -1, -1},
                {-1, 0, 0, 0, -1},
                {-1, 0, 16, 0, -1},
                {-1, 0, 0, 0, -1},
                {-1, -1, -1, -1, -1},
        };

        //go through all values
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                int rgbArray[] = new int[4];
                int[][] surroundingArea = new int[3][3]; // get 3x3 array of colors in surrounding area

                //cycles through a 3x3 surroundingArea
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        if (((i - 1 + a) >= 0 && (j - 1 + b) >= 0 && (i - 1 + a) < height && (j - 1 + b) < width)) {
                            surroundingArea[a][b] = getPixelArray(picture[i - 1 + a][j - 1 + b])[1]; //grabs the color of each pixel in the surroundingArea
                        }
                    }
                }
                //average the sum from mask and filter
                int temp3Mask = 0, temp5Mask = 0;
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        temp3Mask += surroundingArea[a][b] * mask3[a][b];
                        temp5Mask += surroundingArea[a][b] * mask5[a][b];
                    }
                }

                if (temp3Mask >= 100) {
                    rgbArray[0] = 255;
                    for (int m = 1; m < 4; m++) {
                        rgbArray[m] = 255;
                    }
                } else {
                    for (int m = 0; m < 4; m++) {
                        rgbArray[m] = 0;
                    }
                }

                //new average rgb colors are put into tempPic
                tempPic[i][j] = getPixels(rgbArray);
            }
        }
        picture = tempPic; // puts tempPic back into original photo

        resetPicture(); //rewrites the image
    }

    private void histogram_colors() {

        int totalPixels = width*height;

        //each color has a frequency counter from 0-255 value
        int[] redFrequency = new int[256];
        int[] greenFrequency = new int[256];
        int[] blueFrequency = new int[256];

        //Gather and calculate frequency data
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++)
            {
                int rgbArray[] = new int[4];

                //get three ints for R, G and B
                rgbArray = getPixelArray(picture[i][j]);

                //current pixel RGB values
                int r = rgbArray[1];
                int g = rgbArray[2];
                int b = rgbArray[3];

                //increasing corresponding frequency values by 1.
                redFrequency[r]++;
                greenFrequency[g]++;
                blueFrequency[b]++;

            }
        }

        //adjust each frequency by dividing by 5 (class suggestion)
        for(int i =0; i< 255; i++)
        {
            redFrequency[i] = redFrequency[i]/5;
            greenFrequency[i] = greenFrequency[i]/5;
            blueFrequency[i] = blueFrequency[i]/5;
        }

        JFrame redFrame = new JFrame("Red");
        redFrame.setSize(305, 600);
        redFrame.setLocation(800, 0);
        JFrame greenFrame = new JFrame("Green");
        greenFrame.setSize(305, 600);
        greenFrame.setLocation(1150, 0);
        JFrame blueFrame = new JFrame("blue");
        blueFrame.setSize(305, 600);
        blueFrame.setLocation(1450, 0);

        MyPanel redPanel = new MyPanel(redFrequency);
        MyPanel greenPanel = new MyPanel(greenFrequency);
        MyPanel bluePanel = new MyPanel(blueFrequency);

        redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
        redFrame.setVisible(true);
        greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
        greenFrame.setVisible(true);
        blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
        blueFrame.setVisible(true);
        start.setEnabled(true);

        resetPicture();
}


    private void equalization() {

       //a frequency counter for each color & 0-255 value
        int[] redFreq = new int[256];
        int[] greenFreq = new int[256];
        int[] blueFreq = new int[256];
        int[][] tempPic = new int[height][width];

        int rgbArray[] = new int[4];

        //data for the histogram
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++)
            {
                rgbArray = getPixelArray(picture[i][j]);

                //increase frequency values
                redFreq[rgbArray[1]]++;
                greenFreq[rgbArray[2]]++;
                blueFreq[rgbArray[3]]++;
            }
        }
        int minRed = 1000;
        int minGreen = 1000;
        int minBlue = 1000;

        int cDfRed = 0;
        int cDfGreen = 0;
        int cDfBlue = 0;

        for(int i = 0; i < redFreq.length; i++){
            if(redFreq[i] < minRed && redFreq[i] != 0){
                minRed = redFreq[i];
            }
            if(greenFreq[i] < minGreen && greenFreq[i] != 0){
                minGreen = greenFreq[i];
            }
            if(blueFreq[i] < minBlue && blueFreq[i] != 0){
                minBlue = blueFreq[i];
            }
        }

        int equalize = height*width;
        int red = 0;
        int green = 0;
        int blue = 0;
        int[] redArray = new int[256];
        int[] greenArray = new int[256];
        int[] blueArray = new int[256];

        for(int i = 0; i < redFreq.length; i++) {
            cDfRed += redFreq[i];
            cDfGreen += greenFreq[i];
            cDfBlue += blueFreq[i];

            // do the math
            red = 255 * (cDfRed - minRed)/(equalize - minRed);
            green = 255 * (cDfGreen - minGreen)/(equalize - minGreen);
            blue = 255 * (cDfBlue - minBlue)/(equalize - minBlue);

            redArray[i] = Math.round(red);
            blueArray[i] = Math.round(blue);
            greenArray[i] = Math.round(green);
        }

        // print out the numbers
        System.out.println();
        int rgb[] = new int[4];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++)
            {
                rgb = getPixelArray(picture[i][j]);

                System.out.println(rgb[2] + " " + rgb[3]);
                System.out.println(greenArray[rgb[2]] + " " + blueArray[rgb[3]]);

                //average goes back into array
                rgbArray[0] = 255;
                rgbArray[1] = redArray[rgb[1]];
                rgbArray[2] = greenArray[rgb[2]];
                rgbArray[3] = blueArray[rgb[3]];

                //new averaged rgb colors goes into a tempPic
                tempPic[i][j] = getPixels(rgbArray);;
            }
        }
        picture = tempPic; // tempPic goes back into original picture

        resetPicture(); //rewrites the image

    }

    private void color_tracker() {
      // from lecture video
        int rL, rH, gL, gH, bL, bH;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));

        JSlider rHSlider = new JSlider(0, 255);
        JSlider gHSlider = new JSlider(0, 255);
        JSlider bHSlider = new JSlider(0, 255);
        JSlider rLSlider = new JSlider(0, 255);
        JSlider gLSlider = new JSlider(0, 255);
        JSlider bLSlider = new JSlider(0, 255);

        rHSlider.setName("rh");
        rLSlider.setName("rl");
        gHSlider.setName("gh");
        gLSlider.setName("gl");
        bHSlider.setName("bh");
        bLSlider.setName("bl");

        rHSlider.addChangeListener(this);
        rLSlider.addChangeListener(this);
        gHSlider.addChangeListener(this);
        gLSlider.addChangeListener(this);
        bHSlider.addChangeListener(this);
        bLSlider.addChangeListener(this);

        panel.add(rLSlider);
        panel.add(rHSlider);
        panel.add(gLSlider);
        panel.add(gHSlider);
        panel.add(bLSlider);
        panel.add(bHSlider);

        int result = JOptionPane.showConfirmDialog(null, panel, "Tracker", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // update color values
        rH = rHSlider.getValue();
        rL = rLSlider.getValue();
        gH = gHSlider.getValue();
        gL = gLSlider.getValue();
        bH = bHSlider.getValue();
        bL = bLSlider.getValue();

        System.out.println(result);
        System.out.println("Red: " + rL + ", " + rH + " : Green: " + gL + ", " + gH + " : Blue: " + bL + ", " + bH);

        for(int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                boolean isMatch = false;
                int rgbArray[] = new int[4];

                //get three ints for R, G and B
                rgbArray = getPixelArray(picture[i][j]);
                //if in red range
                if (rgbArray[1] >= rL && rgbArray[1] <= rH) {
                    //and if in green range
                    if (rgbArray[2] >= gL && rgbArray[2] <= gH) {
                        //and if in blue range
                        if (rgbArray[3] >= bL && rgbArray[3] <= bH) {
                            isMatch = true;
                            //turn matching colors white
                            rgbArray[1] = 255;
                            rgbArray[2] = 255;
                            rgbArray[3] = 255;
                        }
                    }
                }

                if(!isMatch){
                    rgbArray[1] = 0;
                    rgbArray[2] = 0;
                    rgbArray[3] = 0;
                }
                rgbArray[0] = 255;
                picture[i][j] = getPixels(rgbArray);
            }
        }

        resetPicture();
    }

//    TODO: Add for quiz ****************************************
    public void firstQuiz() {System.out.println("one");}
    public void secondQuiz() {System.out.println("two");}
//    TODO: *******************************************************

    private void quit()
  {  
     System.exit(0);
  }

    @Override
   public void mouseEntered(MouseEvent m){}
    @Override
   public void mouseExited(MouseEvent m){}
    @Override
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
    @Override
   public void mousePressed(MouseEvent m){}
    @Override
   public void mouseReleased(MouseEvent m){}
    @Override
    public void stateChanged(ChangeEvent ce) {
        JSlider source = (JSlider)ce.getSource();
        if (!source.getValueIsAdjusting())
        {
            if(source.getName().equals("rl")) {
                int rL = source.getValue();
                System.out.println("rL " + rL);
            } else if (source.getName().equals("rh")) {
                int rH = source.getValue();
                System.out.println("rH " + rH);
            } else if (source.getName().equals("gl")) {
                int gL = source.getValue();
                System.out.println("gL " + gL);
            } else if (source.getName().equals("gh")) {
                int gH = source.getValue();
                System.out.println("gH " + gH);
            } else if (source.getName().equals("bl")) {
                int bL = source.getValue();
                System.out.println("bL " + bL);
            } else if (source.getName().equals("bh")) {
                int bH = source.getValue();
                System.out.println("bH " + bH);
            }
        }
    }

   
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }

}