
package raycaster;

import java.awt.Dimension;
import javax.swing.JFrame;


public class RayCaster {

    
    public static void main(String[] args) {
        
        // Create the Frame / GridMap
        Frame theFrame = new Frame();
        theFrame.creatGridMap();
        
        
        // Create the Caster for Player
        JFrame frame = new JFrame();
        Caster cast = new Caster();
        frame.setTitle("CASTER OF PLAYER");
        frame.setLocation(0, 0);
        frame.setMinimumSize(new Dimension(512, 512));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cast);
        frame.setVisible(true);
        
    }
    
}
