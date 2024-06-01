
package raycaster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Caster extends JPanel{
    
public static JPanel thisCaster;
public static BufferedImage walls;
public static BufferedImage floor;

    
    
    public Caster(){
        this.setMinimumSize(new Dimension(512,512));
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        
        String imagePath = "texture.png";
        String floorPath = "floor.png";
        System.out.println("READING IMAGE ");
        try{
            walls = ImageIO.read(new File(imagePath));
            floor =  ImageIO.read(new File(floorPath));
        }catch(IOException e){
            e.printStackTrace();
        }
               
        
        
        thisCaster = this;
      
        
        
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        // Draw Rays
        draw(g);
        
    }
    
    public static void draw(Graphics g){
        
      
       long nT;
       
         
        double wH;
        double o;
        Graphics2D g2 = (Graphics2D) g;
        
        
        
        for(int cnt =0; cnt < GridMap.distances.length; cnt++){
            wH = calcWallHeight(GridMap.distances[cnt]);
            o = calcOffset(wH);
            
            
            
            // Draw Walls
            g2.setColor(Color.MAGENTA);
            g2.draw(new Line2D.Double(cnt, o, cnt, (o + wH)));
            
               //g2.drawImage(walls, cnt, (int) o, 5, (int) wH, thisCaster);
            
            
            
            
            
            
             //Draw Ceiling
            g2.setColor(Color.GRAY);
            g2.draw(new Line2D.Double(cnt, 0, cnt, o));
               // g2.drawImage(floor, cnt, 0, 10, (int) o, thisCaster);
            
            
            // Draw the Floor
            g2.setColor(Color.BLACK);
            g2.draw(new Line2D.Double(cnt, (o + wH), cnt, 512));
            //g2.drawImage(floor, cnt, (int) (o + wH), 1, (int) ( 512 - (o + wH)), thisCaster);
            
            
        }
        
        
                    
           
         
             
            
            
           
        
    }
    
    
    public static double calcWallHeight(int distance){
        
        double wH;
        wH = 0;
        
        wH = Math.round((64.0/ distance) * GridMap.distanceToProjectionPlane);
        
        
        return wH;
    }
    
    public static double calcOffset(double wH){
        double offset;
        offset = 0;
        
        offset = Math.round((512.0 / 2.0) - (wH/ 2.0));
        
        
        
        return offset;
    }
    
    
}
