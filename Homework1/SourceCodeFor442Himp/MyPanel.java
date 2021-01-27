
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel
{
 
int startX, flag, startY, endX, endY;

    BufferedImage grid;
    Graphics2D gc;

	public MyPanel()
	{
	   startX = startY = 0;
           endX = endY = 100;
 	}

     public void clear()
    {
       grid = null;
       repaint();
    }
    public void paintComponent(Graphics g)
    {  
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;
         if(grid == null){
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage)(this.createImage(w,h));
            gc = grid.createGraphics();

         }
         g2.drawImage(grid, null, 0, 0);
     }
    public void drawing()
    {
        
        gc.drawLine(startX, startY, endX, endY);
        repaint();
    }

//    public void drawHistogram (MyPanel red, MyPanel green, MyPanel blue) {
//        JFrame redFrame = new JFrame("Red");
//        redFrame.setSize(305, 600);
//        redFrame.setLocation(800, 0);
//        JFrame greenFrame = new JFrame("Green");
//        greenFrame.setSize(305, 600);
//        greenFrame.setLocation(1150, 0);
//        JFrame blueFrame = new JFrame("blue");
//        blueFrame.setSize(305, 600);
//        blueFrame.setLocation(1450, 0);
//        MyPanel redPanel = new MyPanel(red);
//        MyPanel greenPanel = new MyPanel(green);
//        MyPanel bluePanel = new MyPanel(blue);
//        redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
//        redFrame.setVisible(true);
//        greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
//        greenFrame.setVisible(true);
//        blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
//        blueFrame.setVisible(true);
//        start.setEnabled(true);
//
//    }
   
}
