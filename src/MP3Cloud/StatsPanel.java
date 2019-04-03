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
 * 		The class StatsPanel extends JPanel. It is used to perform
 * stats animations.
 * */
public class StatsPanel extends JPanel {
	//variables
	private float progress = 0.0f; // a number between 0.0 and 1.0
	private double percentageGenre;
	
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
	
	//set method
	public void setPercentageGenre(double A) {percentageGenre=A;}
	
	//animation methods
	
	/* Author: Jianying Chiang		Date: 2019-04-02
	 * 		The purpose of animate method is to use a timer to animate. 
	 * @param: none
	 * @return: none
	 * */
    public void animate() {
    	//animation variables
        final int animationTime = 600;
        int framesPerSecond = 120;
        int delay = 1000 / framesPerSecond;
        final long start = System.currentTimeMillis();
        final Timer timer = new Timer(delay, null);
        //actionListener
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final long now = System.currentTimeMillis();
                final long elapsed = now - start;
                progress = (float) elapsed / animationTime;
                progress *= percentageGenre;
                
                repaint();
                if (elapsed >= animationTime) {
                    timer.stop();
                }
            }
        });
        timer.start();
    } //----------------------- End of animate() method -----------------------     

    /* Author: Jianying Chiang		Date: 2019-04-02
  	 * 		The purpose of paintComponent method is paint StatsPanels.
  	 * @param: Graphics
  	 * @return: none
  	 * */
    @Override
    protected void paintComponent(Graphics g) {     
        g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        barRectWidth = 290 - 20;
        barRectHeight = 145 - 20;
        if (barRectWidth <= 0 || barRectHeight <= 0) {
          return;
        }
        angle = (int) progress;
        degree = angle;
        sz = Math.min(barRectWidth, barRectHeight);
        cx = 10 + barRectWidth * .5;
        cy = 10 + barRectHeight * .5;
        or = sz * .5;
        
        ir = or * .6;
        Shape backPane = new Ellipse2D.Double(-75,-75,500,500);
        inner = new Ellipse2D.Double(30, 30, 230, 230);
        outer = new Ellipse2D.Double(0, 0, 290, 290);
        sector = new Arc2D.Double(0, 0, 290, 290, degree, -degree, Arc2D.PIE);
        foreground = new Area(sector);
        background = new Area(outer);
        hole = new Area(inner);
        Area back = new Area(backPane);

        back.subtract(background);
        foreground.subtract(hole);
        background.subtract(hole);
        
        
        //draw backPane and hole
        g2d.setPaint(new Color(68, 102, 140));
        g2d.fill(back);
        g2d.fill(hole);
        
        //draw the track
        g2d.setPaint(new Color(104,104,104));
        g2d.fill(background);
        
        // draw the circular sector
        g2d.setPaint(new Color(234, 168, 0));
        g2d.fill(foreground);
        g2d.dispose();
    } //----------------------- End of paintComponent() method -----------------------	
} //----------------------- End of StatsPanel class -----------------------
