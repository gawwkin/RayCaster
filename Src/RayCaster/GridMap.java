
package raycaster;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import javax.swing.JPanel;


public class GridMap extends JPanel implements KeyListener{
    
   
    private int mSize = 64;
    private int mapX = 8;
    public static int[] distances = new int[513];
    private int mapY = 8;
    private int pSize = mSize / 2;
    private int[] map = {1,1,1,1,1,1,1,1,
                         1,0,0,0,0,0,0,1,
                         1,0,0,0,0,0,0,1,
                         1,0,0,1,0,1,0,1,
                         1,0,0,0,0,0,0,1,
                         1,0,0,0,0,0,0,1,
                         1,0,0,0,0,0,0,1,
                         1,1,1,1,1,1,1,1,};
    private int px, py;
    private double Pa = 290;
    private double dir = Pa/ 2;
    private double sA = 60.0 / 512.0;
    private double pdx, pdy;
    private int[] xWalls = new int[map.length];
    private int[] yWalls = new int[map.length];
    private double dh, dv;
    public static int distanceToProjectionPlane =  (int) ((512 / 2) / Math.tan(Math.toRadians(30)));
   
    
    public GridMap(){
        this.setMaximumSize(new Dimension(Frame.sWidth, Frame.sHeight));
        this.setVisible(true);
        
        
        px = 100;
        py = 200;
        pdx  = (Math.cos(Math.toRadians(Pa)) * 15);
        pdy = (Math.sin(Math.toRadians(Pa)) * -15);
        
 
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        // Draw Player
        drawMap(g);
        
        // Draw Player
          drawPlayer(g);
          
          // Cast Rays
          Graphics2D g2 = (Graphics2D ) g;
          castRays(g2);
          
        
    }
    
    public void castRays(Graphics2D g2){
        
        double hrx,hry, vrx, vry;
        double ra;
        double  dx, dy;
        double xo, yo;
        boolean foundH, foundV;
        int rt;
        
        // Remember to check if angle is 90 or 270 ( Horizontal) 
            ra = Pa - 60;
            
            dh = 0;
            dv = 0;
            rt =0;
            
          // Loop for rays 
            for(ra = ra; ra<=Pa; ra+= sA){
                
                
                
                // Horizontal Line Check
                    hrx = 0; hry = 0; dx=0; dy = 0; xo = 0; yo = 0; foundH = false;
                    if(ra < 180 && ra > 0 ){
                        
                        hrx = (px + 5) + Math.round((Math.cos(Math.toRadians(ra))) * 15);   // center ray in cube / player
                        hry = (py + 5) + Math.round((Math.sin(Math.toRadians(ra))) * -15);
                         
                        yo = findDH((int)hry, ra);
                        xo = Math.round (yo / Math.tan(Math.toRadians(ra)));
                        hry = hry + (yo * -1);
                        hrx = hrx + xo;
                        while(!foundH){
                            if(ra == 90){
                                for(int cnt = 0; cnt < map.length; cnt++){
                                    if(map[cnt] == 1 && hry == yWalls[cnt] && hrx <= xWalls[cnt] && hrx >= xWalls[cnt] - mSize){
                                        foundH = true;
                                        break;

                                } 
                                }
                                if(!foundH && hrx <= Frame.sWidth && hrx >= 0 && hry >= 0 && hry <= Frame.sHeight )
                                    hry = hry + (mSize * -1);
                                else
                                    foundH = true;
                            }
                            else { 
                                
                                dy = (mSize * -1);
                                dx = ((Math.round(dy / Math.tan(Math.toRadians(ra)))) * -1);
                                for(int cnt = 0; cnt < map.length; cnt++){
                                    if(map[cnt] == 1 && hry == yWalls[cnt] && hrx <= xWalls[cnt] && hrx >= xWalls[cnt] - mSize){
                                        foundH = true;
                                        break;

                                } 
                                }
                                if(!foundH && hrx <= Frame.sWidth && hrx >= 0 && hry >= 0 && hry <= Frame.sHeight ){
                                    hrx = hrx + dx;
                                    hry = hry + dy;
                                }
                                else
                                    foundH = true;
                                
                            }
                        }
                    }
                    else if(ra > 180 || ra <= 360){
                        hrx = (px + 5) + Math.round((Math.cos(Math.toRadians(ra))) * 15);   // center ray in cube / player
                        hry = (py + 5) + Math.round((Math.sin(Math.toRadians(ra))) * -15);
                         
                        yo = findDH((int)hry, ra);
                        xo = (Math.round (yo / Math.tan(Math.toRadians(ra))) * -1);
                        hry = hry + (yo);
                        hrx = hrx + xo;
                        while(!foundH){
                            if(ra == 270){
                                for(int cnt = 0; cnt < map.length; cnt++){
                                    if(map[cnt] == 1 && hry == yWalls[cnt] -mSize && hrx <= xWalls[cnt] && hrx >= xWalls[cnt] - mSize){
                                        foundH = true;
                                        break;

                                } 
                                }
                                if(!foundH && hrx <= Frame.sWidth && hrx >= 0 && hry >= 0 && hry <= Frame.sHeight )
                                    hry = hry + (mSize);
                                else
                                    foundH = true;
                            }
                            else { 
                                
                                dy = (mSize);
                                dx = ((Math.round(dy / Math.tan(Math.toRadians(ra)))) * -1);
                                for(int cnt = 0; cnt < map.length; cnt++){
                                    if(map[cnt] == 1 && hry == yWalls[cnt] -mSize && hrx <= xWalls[cnt] && hrx >= xWalls[cnt] - mSize){
                                        foundH = true;
                                        break;

                                } 
                                }
                                if(!foundH && hrx <= Frame.sWidth && hrx >= 0 && hry >= 0 && hry <= Frame.sHeight ){
                                    hrx = hrx + dx;
                                    hry = hry + dy;
                                }
                                else
                                    foundH = true;
                                
                            }
                        }
                    }
                   
                    
                    
                        // End of Horizontal Check should call the distance function to find out distance of horizontal rays from players position
                        dh = calcDistance(hrx, hry);
                        
                      // Start of Vertical Line Checks
                            vrx = 0; vry = 0; dx=0; dy = 0; xo = 0; yo = 0; foundV = false;
                      if(ra != 90 && ra != 270 && (ra <90 || ra > 270)){
                          vrx = (px + 5) + Math.round((Math.cos(Math.toRadians(ra))) * 15);   // center ray in cube / player
                          vry = (py + 5) + Math.round((Math.sin(Math.toRadians(ra))) * -15);
                          
                          xo = findDV((int) vrx, ra);          // calculate closest distance from the rays current x position
                          yo = Math.round(xo * Math.tan(Math.toRadians(ra)));        // find y-OFFSET to adjust to the ray's y position 
                          vrx = vrx + xo;
                          vry = vry + (yo * -1);              // invert y OFFSET
                          
                          while(!foundV){
                            if(ra >=360 || ra == 0){
                                for(int cnt = 0; cnt< map.length; cnt++){
                                    if(map[cnt] == 1 && vrx == xWalls[cnt] - mSize && vry <= yWalls[cnt] && vry >= yWalls[cnt] -mSize){
                                        foundV =true;
                                        break;
                                    }
                                }
                                if(!foundV && vrx <= Frame.sWidth && vrx >= 0 && vry >= 0 && vry <= Frame.sHeight)
                                    vrx = vrx + mSize;
                                else
                                    foundV = true;
                            }
                            else{
                                dx = mSize;
                                dy = (Math.round(dx * Math.tan(Math.toRadians(ra))) * -1);
                                for(int cnt = 0; cnt< map.length; cnt++){
                                    if(map[cnt] == 1 && vrx == xWalls[cnt] - mSize && vry <= yWalls[cnt] && vry >= yWalls[cnt] -mSize){
                                        foundV =true;
                                        break;
                                    }
                                }
                                if(!foundV && vrx <= Frame.sWidth && vrx >= 0 && vry >= 0 && vry <= Frame.sHeight){
                                    vrx = vrx + dx;
                                    vry = vry + dy;
                                }
                                else
                                    foundV = true;
                            }
                         
                         
                          
                          
                      }
                          
                      }
                      else if(ra != 90 && ra != 270 && (ra >90 || ra <270)){
                          vrx = (px + 5) + Math.round((Math.cos(Math.toRadians(ra))) * 15);   // center ray in cube / player
                          vry = (py + 5) + Math.round((Math.sin(Math.toRadians(ra))) * -15);
                          
                          xo = findDV((int) vrx, ra);          // calculate closest distance from the rays current x position
                          yo = Math.round(xo * Math.tan(Math.toRadians(ra)));        // find y-OFFSET to adjust to the ray's y position 
                          vrx = vrx + (xo * -1);
                          vry = vry + (yo);              // invert y OFFSET
                          
                          while(!foundV){
                            if(ra == 180){
                                for(int cnt = 0; cnt< map.length; cnt++){
                                    if(map[cnt] == 1 && vrx == xWalls[cnt] && vry <= yWalls[cnt] && vry >= yWalls[cnt] -mSize){
                                        foundV =true;
                                        break;
                                    }
                                }
                                if(!foundV && vrx <= Frame.sWidth && vrx >= 0 && vry >= 0 && vry <= Frame.sHeight)
                                    vrx = vrx + (mSize * -1);
                                else
                                    foundV = true;
                            }
                            else{
                                dx = (mSize * -1);
                                dy = (Math.round(dx * Math.tan(Math.toRadians(ra))) * -1);
                                for(int cnt = 0; cnt< map.length; cnt++){
                                    if(map[cnt] == 1 && vrx == xWalls[cnt] && vry <= yWalls[cnt] && vry >= yWalls[cnt] -mSize){
                                        foundV =true;
                                        break;
                                    }
                                }
                                if(!foundV && vrx <= Frame.sWidth && vrx >= 0 && vry >= 0 && vry <= Frame.sHeight){
                                    vrx = vrx + dx;
                                    vry = vry + dy;
                                }
                                else
                                    foundV = true;
                            }
                          
                      }
                          
                      }
                      
                       // End of Vertical Check should call the distance function to find out distance of horizontal rays from players position
                        dv = calcDistance(vrx, vry);
                        
                        //System.out.println("Distance for Horizontal : " + dh + " \n Distance for Vertical :" + dv );
                        
                        
                            // Check which ray has the smaller distance to be drawn
                            
                            g2.setStroke(new BasicStroke(0.0f));
                            if(dv ==dh){
                                g2.setColor(Color.YELLOW);
                                distances[rt] = (int) dv;//Math.abs(Math.round(dv * Math.cos(Math.toRadians((ra)))));
                                g2.draw(new Line2D.Double(px + 5, py + 5, vrx, vry));
                            }
                            else if (dv > dh && hrx != 0 && hry != 0){
                                g2.setColor(Color.PINK);
                                distances[rt] = (int) dh; //Math.round(Math.abs(dh  * Math.sin(Math.toRadians((ra)))));
                                g2.draw(new Line2D.Double(px + 5, py + 5, hrx, hry));
                            }
                            else if ( dh > dv && vrx != 0 && vry !=0 ){
                                g2.setColor(Color.MAGENTA);
                                distances[rt] = (int)dv; //Math.abs(Math.round(dv * Math.cos(Math.toRadians((ra)))));
                                g2.draw(new Line2D.Double(px + 5, py + 5, vrx, vry));
                            }
                            rt++;
                            //System.out.println("TOTAL RAYS DRAWN = " + rt);
                        
                        
            }
               
                Caster.thisCaster.repaint();
              Caster.draw(Caster.thisCaster.getGraphics());
            
            
            
         
    }
    
    public double calcDistance(double rx, double ry){
        
        double distance, dy, dx;
        distance = 0;
        dy = 0; dx = 0;
        
        dy = Math.abs((py + 5) - ry);
        dx = Math.abs((px + 5) - rx);
        
        
        distance  = Math.sqrt((dy * dy) + (dx * dx));
        return distance;
        
        
        
    }
    
    
    
    public int findDH(int ry, double ra){
        
        int distance;
        distance  = 0;
            
        if(ra < 180 && ra > 0){
            for(int x = 0; x  < mapX; x++){
                if(yWalls[x * mapX] > ry){
                    distance = ry - ((yWalls[x * mapX]) - mSize);
                    break;
                }
            }
        }
        else if( ra > 180 || ra < 0){
            for(int x = 0; x < mapX; x++){
                if(yWalls[x * mapX] > ry){
                    distance = yWalls[x * mapX] - ry;
                    break;
                }
            }
        }
        return distance;
        
    }
    
    public int findDV(int rx, double ra){
        int distance;
        distance = 0;
        
        if(Math.cos(Math.toRadians(ra)) > 0){
            for(int x = 0; x<mapX; x++){
                if(xWalls[x] > rx){
                    distance = xWalls[x] - rx;
                    break;
                    
                }
            }
            
        }
        else{
            for(int x = 0; x<mapX; x++){
                if(xWalls[x] > rx){
                    distance = rx - (xWalls[x] - mSize);
                    break;
                }
            }
        }
        
        
        
        return distance;
    }
    
    
    
    public void drawMap(Graphics g){
        int innerCount = 0;
        
        // Draw the Grid Layout
            for(int y = 0; y < mapY; y++){
                for(int x = 0; x< mapX; x++){
                    if(map[y * mapX + x] == 1){ g.setColor(Color.BLACK);} else{g.setColor(Color.LIGHT_GRAY);}
                    xWalls[innerCount] = (x + 1) * mSize;
                    yWalls[innerCount] = (y + 1) * mSize;
                    g.drawRect(x * mSize, y * mSize, mSize, mSize);
                    g.fillRect(x * mSize, y * mSize, mSize -1 , mSize - 1);
                    innerCount++;
                }
            }
            
    }
 
    
    public void drawPlayer(Graphics g){
            
            // Drawing the Player
                g.setColor(Color.red);
                g.drawRect(px, py, 10, 10);
                g.fillRect(px, py, 10, 10);
           // Draw Players Direction Vector
                g.setColor(Color.ORANGE);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3.5f));
                g2.draw(new Line2D.Double(px + 5, py + 5, px + 5 + pdx, py + 5 + pdy));
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'a'){
            Pa += 5;
            if(Pa > 360){Pa -= 360;}
            pdx  = (Math.cos(Math.toRadians(Pa)) * 15);
            pdy = (Math.sin(Math.toRadians(Pa)) * -15);
            super.repaint();
        }
        else if(e.getKeyChar() == 'd'){
            Pa -= 5;
            if(Pa<=0){Pa += 360;}
            pdx  = (Math.cos(Math.toRadians(Pa)) * 15);
            pdy = (Math.sin(Math.toRadians(Pa)) * -15);
            super.repaint();
        }
        else if(e.getKeyChar() == 'w'){
            px = px + ((int) pdx/ 2);
            py = py + ((int) pdy /2) ;
            super.repaint();
            
        }
        else if (e.getKeyChar() == 's'){
            px = px + (((int) pdx /2) * -1);
            py = py + (((int) pdy / 2) * -1);
            super.repaint();
            
        }
        }
    

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
