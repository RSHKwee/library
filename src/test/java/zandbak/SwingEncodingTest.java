package zandbak;

import javax.swing.*;
import java.awt.*;

public class SwingEncodingTest {
  public static void main(String[] args) {
    JFrame frame = new JFrame("UTF-8 Test    © Copyright 2025");
    JLabel label = new JLabel("© Copyright 2025");
    label.setFont(new Font("Arial", Font.PLAIN, 20)); // Kies een Unicode-compatibel lettertype

    frame.add(label);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 100);
    frame.setVisible(true);
  }
}
