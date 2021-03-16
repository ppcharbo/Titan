package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SystemPlanetMain extends JPanel
{
    Model model;
    Planet[] Planet = new Planet[9];
    
    final static int DELAY = 500; 
    double size = 1;
    BufferedImage[] imgs = new BufferedImage[9];

    boolean stop = false;
    int clicked = -1;
        
    public SystemPlanetMain()
    {
          model = new Model();
          model.setPreferredSize(new Dimension(1200, 1200));
          add(model);
          
            Planet[0] = new Planet(255, 255, 0, 600, 600, 12.0, 1.0, 1.0, 1000); 
            Planet[1] = new Planet(102, 102, 102, 600, 700.9, 12.0, 2.0, 2.0, 1000);
            Planet[2] = new Planet(153, 102, 0, 600, 304.9, 12.0, 1.0, 1.0, 1000);
            Planet[3] = new Planet(0, 0, 255, 600, 800, 12.0, 1.0, 1.0, 1000);
            Planet[4] = new Planet(153, 0, 0, 600, 404.9, 12.0, 1.0, 1.0, 1000);
            Planet[5] = new Planet(255, 204, 51, 600, 504.9, 12.0, 1.0, 1.0, 1000);
            Planet[6] = new Planet(255, 153, 0, 600, 904.9, 12.0, 1.0, 1.0, 1000);
            Planet[7] = new Planet(51, 204, 255, 600, 304.9, 12.0, 1.0, 1.0, 1000);
            Planet[8] = new Planet(0, 0, 153, 600, 304.9, 12.0, 1.0, 1.0, 1000);
            
                      
                    
          setBackground(Color.BLACK);
         
          
           imgs[0] = getImage(""); //stackOverflow
           imgs[1] = getImage("");
           imgs[2] = getImage("");
           imgs[3] = getImage("");
           imgs[4] = getImage("");
           imgs[5] = getImage("");
           imgs[6] = getImage("");
           imgs[7] = getImage("");
           imgs[8] = getImage("");
           

          Thread thread =  new Thread() {
     
            @Override
             public void run() {
                gameLoop();
             }
          }; 
          
          thread.start();
    }
   
    
    public static BufferedImage getImage(String ref) {  //loading the image
        BufferedImage bimg = null;  
        try {  
  
            bimg = ImageIO.read(new File(ref));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bimg;  
    }  
    private void gameLoop() {
    	
      while (true) {
            if (!stop)
            {
                for(int i = 0; i < Planet.length-1; i++)
                {
                	Planet[i].updatePlanet(Planet[8].getX(),Planet[8].getY(),Planet[8].getMass());
                }
            }
         repaint();

         try {
            Thread.sleep(DELAY);
         } catch (InterruptedException ex) { }
      }
   }
    

    class Model extends JPanel implements KeyListener, MouseListener {
      public Model() {

         setFocusable(true); 
         requestFocus();
         addKeyListener(this);
         addMouseListener(this);
      }

      
      public void paintComponent(Graphics g) {


         for(Planet body : Planet)
            body.drawPlanet(g,size);
         
         
         for (int i = 0; i < Planet.length; i++)
         {
             if (Planet[i].isVisible())
            	 Planet[i].dispDesc(g,size);
         }
         /*
         if (clicked > -1)
         {
             g.drawImage(imgs[clicked],0,0,200,200,Color.WHITE,null);
             g.setFont(new Font("Arial", Font.PLAIN, 20));
             g.setColor(Color.WHITE);
             for(int i = 0; i < description[clicked].length; i++)
             {
                 g.drawString(description[clicked][i], 0, 210+i*30);
             }
         }
         */
         
         
         Planet[0].dispDesc(g,size);
         Planet[1].dispDesc(g,size);
         Planet[2].dispDesc(g,size);
         Planet[3].dispDesc(g,size);
         Planet[4].dispDesc(g,size);
         Planet[5].dispDesc(g,size);
         Planet[6].dispDesc(g,size);
         Planet[7].dispDesc(g,size);
         Planet[8].dispDesc(g,size);

      }
      
      public void keyTyped(KeyEvent e) {
    	  
      }
      public void mousePressed(MouseEvent e) {
    	  
      }
      public void mouseReleased(MouseEvent e) {
          for(int i = 0; i < Planet.length; i++)
              if (Planet[i].collision(e.getX(), e.getY(), size))
              {
                  
            	  Planet[i].visibalityChange(!Planet[i].isVisible());
                  if(Planet[i].isVisible()) {
                	  clicked = i;
                  }
                  else  {
                	  clicked = -1;
                  }
              }
      }
      public void mouseEntered(MouseEvent e) { 
    	  
      }
      public void mouseExited(MouseEvent e) { 
    	  
      }
      public void mouseClicked(MouseEvent e) { 
    	  
      }
      
      public void keyPressed(KeyEvent e) {
    	  
      }
      
      @Override
      public void keyReleased(KeyEvent e) { 

          if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS)
        	  size += .1;
          
    	  if(e.getKeyCode() == KeyEvent.VK_MINUS && size > 0)
        	  size -= .1;
    	  
          if(e.getKeyCode() == KeyEvent.VK_SPACE)
          {
              stop = !stop;
          }
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          {
              System.exit(0);
          }
         
      }
   

   }
    
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JFrame frame = new JFrame("The Unknown Space, the road to Titan");
            frame.setContentPane(new SystemPlanetMain());  
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);            
         }
      });
    }
}