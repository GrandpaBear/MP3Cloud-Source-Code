package MP3Cloud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;

/* Author: Jianying Chiang		Date: 2019-04-02
 * 		The class UploadPanel extends JPanel. It is used to perform
 * upload animations.
 * */
public class UploadPanel extends JPanel {
	//variables
	private String command;
	private int bounce=1;
	private double remove=0.0;
	private float progress = 0.0f; // a number between 0.0 and 1.0
	private boolean validFile=true;
	private boolean tested=false;
	
	//set methods
	public void setCommand(String A) {command = A;}	
	public void setValidFile(boolean A) {validFile=A;}
	public void setTested(boolean A) {tested=A;}
	
	//get method
	public boolean getValidFile() {return validFile;}
	
	//paint variables
	double sz;
	double cx;
	double cy;
	double or;
	double ir;
	int angle;
	int degree;
	Shape inner;
	Shape outer;
	Shape sector;
	Area foreground;
	Area background;
	Area hole;
	int barRectWidth;
	int barRectHeight;
	Graphics2D g2d;
	
	//animation methods
	
	/* Author: Jianying Chiang		Date: 2019-04-02
	 * 		The purpose of animate method is to use a timer to animate. 
	 * @param: none
	 * @return: none
	 * */
    public void animate() {
    	if (command.equals("load")) {
            final int animationTime = 1200;
            int framesPerSecond = 90;
            int delay = 1000 / framesPerSecond;
            final long start = System.currentTimeMillis();
            final Timer timer = new Timer(delay, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final long now = System.currentTimeMillis();
                    final long elapsed = now - start;
                    progress = (float) elapsed / animationTime;
                    progress *= 450;
                    repaint();
                    if (elapsed >= animationTime) {
                        timer.stop();
                    }
                }
            });
            timer.start();
    	}
    	else if (command.equals("restart")){
    		repaint();
    	}
    	else if (progress==0){
    		int animationTime;
    		if (command.equals("hover")) {
    			animationTime = 50;
    		}
    		else {
    			animationTime = 100;
    		}
            int framesPerSecond = 90;
            int delay = 1000 / framesPerSecond;
            final long start = System.currentTimeMillis();
            final Timer timer = new Timer(delay, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final long now = System.currentTimeMillis();
                    final long elapsed = now - start;
                    repaint();
                    if (elapsed >= animationTime) {
                        timer.stop();
                    }
                }
            });
            timer.start();
    	}
    	bounce=1;
		remove=0.0;
		progress = 0.0f; // a number between 0.0 and 1.0
    } //----------------------- End of animate() method -----------------------

  	/* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintComponent method is paint UploadPanels.
  	 * @param: Graphics
  	 * @return: none
  	 * */
    @Override
    protected void paintComponent(Graphics g) {     
        g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        barRectWidth = 302 - 20;
        barRectHeight = 302 - 20;
        if (barRectWidth <= 0 || barRectHeight <= 0) {
          return;
        }
        
        if (command.equals("load")) {
            angle = (int) progress;
            degree = angle;
            if (degree < 365) {
                  paintLoad(new Color(0,187,229));
            }
            else {
                  if (bounce<=5) {
                        paintBlueGrow();
	                  }
                  else if (remove<=5) {
                        paintShrink();
                  }
                  else {
                	  if (tested==false) {
                  		paintLoad(new Color(0,187,229));
                  	}   
                  	else if (tested==true && validFile==true) {
                  		paintLoad(new Color(46,204,113));
                  	}
                  	else {
                  		paintLoad(new Color(204, 0, 51));
                  	}
                  }
            } 
        }
        else if (command.equals("hover")) {
        	paintBlueGrow();
        }
        else if (command.equals("unhover") && remove<=5) {
        	paintShrink();
        }
        else {
        	degree=360;
        	paintLoad(new Color(0,187,229));
        	degree=0;
        }
    } //----------------------- End of paintComponent() method -----------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintLoad method is perform loading animation of UploadPanels.
  	 * @param: Color
  	 * @return: none
  	 * */
    public void paintLoad(Color colorCode) {
    	sz = Math.min(barRectWidth, barRectHeight);
        cx = 10 + barRectWidth * .5;
        cy = 10 + barRectHeight * .5;
        or = sz * .5;
        
        ir = or * .9;
        inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
        outer = new Ellipse2D.Double(-75,-75,500,500);
        sector = new Arc2D.Double(cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
        foreground = new Area(sector);
        background = new Area(outer);
        hole = new Area(inner);

        foreground.subtract(hole);
        background.subtract(foreground);
        
        //draw the track
        g2d.setPaint(new Color(255,255,255));
        g2d.fill(background);
        
        // draw the circular sector
        g2d.setPaint(colorCode);
        g2d.fill(foreground);
        g2d.dispose();
    } //----------------------- End of paintLoad() method -----------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintGrow method is perform growing animation of UploadPanels.
  	 * @param: none
  	 * @return: none
  	 * */
    public void paintBlueGrow() {
    	sz = Math.min(barRectWidth, barRectHeight);
        cx = 10 + barRectWidth * .5;
        cy = 10 + barRectHeight * .5;
        or = sz * .5;
        ir = or * .9;
        
        inner = new Ellipse2D.Double(cx - ir-bounce, cy - ir-bounce, ir * 2+bounce*2, ir * 2+bounce*2);
        outer = new Ellipse2D.Double(-75,-75,500,500);
        sector = new Ellipse2D.Double(cx - or-bounce, cy - or-bounce, sz+bounce*2, sz+bounce*2);
        background = new Area(outer);
        foreground = new Area(sector);
        hole = new Area(inner);
        
        foreground.subtract(hole);
        background.subtract(foreground);
        
        //draw inner white
        g2d.setPaint(new Color(255,255,255));
        g2d.fill(hole);
        		
     // draw the circular sector
        g2d.setPaint(new Color(0,187,229));
        g2d.fill(foreground);
        
      //draw the outer white
        g2d.setPaint(new Color(255,255,255));
        g2d.fill(background);
        g2d.dispose();
  
        bounce++;        	
    } //----------------------- End of paintGrow() method -----------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintShrink method is perform shrinking animation of UploadPanels.
  	 * @param: none
  	 * @return: none
  	 * */
    public void paintShrink() {
    	sz = Math.min(barRectWidth, barRectHeight);
        cx = 10 + barRectWidth * .5;
        cy = 10 + barRectHeight * .5;
        or = sz * .5;
        ir = or * .9;
        
        inner = new Ellipse2D.Double(cx - or-5+remove, cy - or-5+remove, sz+(5-remove)*2, sz+(5-remove)*2);
        outer = new Ellipse2D.Double(-75,-75,500,500);
        sector = new Ellipse2D.Double(cx - ir-5+remove, cy - ir-5+remove, ir * 2+(5-remove)*2, ir * 2+(5-remove)*2);
        background = new Area(sector);
        foreground = new Area(outer);
        hole = new Area(inner);
        
        foreground.subtract(hole);
        hole.subtract(background);
       
        //draw inner white hole
        g2d.setPaint(new Color(255,255,255));
        g2d.fill(background);
        
        //draw inner circular sector
        paintColor();
        g2d.fill(hole);
        
        //draw the outer white
        g2d.setPaint(new Color(255,255,255));
        g2d.fill(foreground);
        g2d.dispose();
        remove+=0.50;
        bounce++;
    } //----------------------- End of paintShrink() method -----------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintColor method is set colour of UploadPanels.
  	 * @param: none
  	 * @return: none
  	 * */
    public void paintColor() {
    	if (tested==false) {
    		g2d.setPaint(new Color(0,187,229));
    	}   
    	else if (tested==true && validFile==true) {
    		g2d.setPaint(new Color(46,204,113));
    	}
    	else {
    		g2d.setPaint(new Color(204, 0, 51));
    	}
    } //----------------------- End of paintColor() method -----------------------
} //----------------------- End of UploadPanel class -----------------------