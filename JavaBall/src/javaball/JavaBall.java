/**
 * 
 * @author Zain
 *This is the Main Class which will create GUI (VieW-Controller) and the Model (Tournament)
 */
package javaball;

public class JavaBall {

    public static void main(String[] args) {
        Tournament tournament = new Tournament();
        Gui g = new Gui(tournament);
    }
    
}
