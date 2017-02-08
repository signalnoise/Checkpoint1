import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

class Visualisation extends JFrame {

    private VisualisationPanel vis;
    private ChangeListener listener;
    private ActionListener textListener;
    private JTextField textField;
    private JSlider slider;


    Visualisation(int width, int height) {

        listener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                // update text field when the slider value changes
                JSlider source = (JSlider) event.getSource();
                double doubleValue = (double)source.getValue()/100.;
                textField.setText(" " + doubleValue);
                pack();
            }
        };

        textListener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTextField source = (JTextField) e.getSource();
                try {
                    double doubleValue = 100 * Double.parseDouble(source.getText());
                    int intValue = (int)doubleValue;
                    slider.setValue(intValue);
                    pack();
                } catch (NumberFormatException exception){
                    double doubleValue = (double)slider.getValue()/100;
                    textField.setText(" "+doubleValue);
                }

            }
        };


        vis = new VisualisationPanel(width, height);
        JPanel panel = new JPanel();
        slider = new JSlider(1, 400);
        slider.addChangeListener(listener);
        setLayout(new BorderLayout(0,0));
        panel.add(slider, BorderLayout.CENTER);
        Double init = ((double) slider.getValue()/100.);
        textField = new JTextField(Double.toString(init));
        textField.addActionListener(textListener);
        panel.add(textField, BorderLayout.LINE_END);
        add(panel, BorderLayout.PAGE_END);
        add(vis, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {System.exit(0);}});
    }

    public double getTemperature(){
        return (double)slider.getValue()/100;
    }

    void set(int column, int row, Color color) {
        vis.set(column, row, color);
    }

    void draw() {
        vis.draw();
    }

}
