
package raycaster;

import java.awt.Dimension;
import javax.swing.JFrame;


public class Frame extends JFrame{
    
    private GridMap map;
    public static int sWidth, sHeight;
    
    
    
    public  void creatGridMap(){
        map = new GridMap();
        sWidth = 550;
        sHeight = 550;
        this.setTitle("GRID MAP OF PLAYER");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(sWidth,sHeight));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(map);
        this.addKeyListener(map);
        this.setVisible(true);
        
    }
    
   
    
    
}
