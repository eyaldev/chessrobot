/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.eyalgames.chess.camera.ImagePanel;
import com.eyalgames.chess.camera.RgbCalculator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A general place to test new algorithms
 */
public class TestDrawing {

    public TestDrawing() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testDrawing() {
        BufferedImage img = null;
        BufferedImage imgs[] = new BufferedImage[2];

        try {
            imgs[0] = ImageIO.read(new File("../captures/4.jpg")); // eventually C:\\ImageTest\\pic2.jpg
            imgs[1] = ImageIO.read(new File("../captures/5.jpg")); // eventually C:\\ImageTest\\pic2.jpg
            img = ImageIO.read(new File("../captures/5.jpg")); // eventually C:\\ImageTest\\pic2.jpg
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    int rgbDiff=RgbCalculator.absoluteDifference(imgs[0].getRGB(x, y), imgs[1].getRGB(x, y));
                    img.setRGB(x, y, rgbDiff);
                }
            }

            Graphics2D g2d = img.createGraphics();
            g2d.setBackground(Color.BLUE);
            g2d.setColor(Color.RED);

            Vector3D[] corners = new Vector3D[]{
                new Vector3D(new double[]{172, 78, 0}),
                new Vector3D(new double[]{455, 71, 0}),
                new Vector3D(new double[]{507, 350, 0}),
                new Vector3D(new double[]{143, 362, 0})
            };
            //draw lines from corner to corner
            for (int c = 0; c < corners.length; c++) {
                Vector3D v1 = corners[c];
                Vector3D v2 = corners[(c + 1) % corners.length];
                g2d.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
            }

            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    Vector3D currentPos = new Vector3D(new double[]{x, y, 0});
                    double[] normalizedCoordinates = new double[2];
                    {
                        //normalize x coordinate
                        Line floor = new Line(corners[0], corners[3], 0.5);
                        Line roof = new Line(corners[1], corners[2], 0.5);
                        double floorDistance = floor.distance(currentPos) * 1 / corners[0].distance(corners[3]);
                        double roofDistance = roof.distance(currentPos) * 1 / corners[1].distance(corners[2]);
                        normalizedCoordinates[0] = interpolate(0, 1, floorDistance / (floorDistance + roofDistance));
                    }
                    {
                        //normalize y coordinate
                        Line floor = new Line(corners[0], corners[1], 0.5);
                        Line roof = new Line(corners[2], corners[3], 0.5);
                        double floorDistance = floor.distance(currentPos) * 1 / corners[0].distance(corners[1]);
                        double roofDistance = roof.distance(currentPos) * 1 / corners[2].distance(corners[3]);
                        normalizedCoordinates[1] = interpolate(0, 1, floorDistance / (floorDistance + roofDistance));
                    }

                    boolean isEdge = false;
                    for (int d = 0; d < 2; d++) {
                        double val = normalizedCoordinates[d];
                        if (Math.abs((double) ((int) (val * 8)) - (val * 8)) < 0.05) {
                            isEdge = true;
                        }
                    }
                    if (isEdge) {
                        //color corners red:
                        img.setRGB(x, y, Color.RED.getRGB());
                    }

                }

            }

            JFrame dialog = new JFrame();
            ImagePanel imagePanel = new ImagePanel(img);
            dialog.add(imagePanel);
            imagePanel.setSize(100, 100);
            dialog.pack();
            dialog.setSize(300, 300);
            dialog.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue("hello", true);
    }

    private double interpolate(double v1, double v2, double i) {
        return v1 + (v2 - v1) * i;
    }
}
