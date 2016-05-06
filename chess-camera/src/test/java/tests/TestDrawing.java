/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.eyalgames.chess.camera.ImagePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Boris
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

        try {
            img = ImageIO.read(new File("../captures/1.jpg")); // eventually C:\\ImageTest\\pic2.jpg
            JFrame dialog = new JFrame();
            ImagePanel imagePanel = new ImagePanel(img);
            dialog.add(imagePanel);
            imagePanel.setSize(100,100);
            dialog.pack();
            dialog.setSize(300,300);
            dialog.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue("hello", true);
    }
}
