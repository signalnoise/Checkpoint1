import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by owen on 08/02/17.
 */
public class VisualisationPanel extends JPanel{

        private BufferedImage foreground;
        private BufferedImage background;


        VisualisationPanel(int width, int height) {
            foreground = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            setVisible(true);
            Dimension dimension = new Dimension(500,500);
            setPreferredSize(dimension);
        }

        void set(int column, int row, Color color) {
            background.setRGB(column, row, color.getRGB());
        }

        void draw() {
            foreground.setData(background.getData());
            getGraphics().drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
        }

        public void paint(Graphics graphics) {
            graphics.drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
      }
}



