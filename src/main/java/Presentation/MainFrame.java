package Presentation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {
    private JButton clientButton;
    private JButton produsButton;
    private JButton comandaButton;

    public MainFrame() {
        setTitle("MainFrame");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        clientButton = new JButton("Client");
        produsButton = new JButton("Produs");
        comandaButton = new JButton("Comanda");

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientFrame().setVisible(true);
            }
        });

        produsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdusFrame().setVisible(true);
            }
        });

        comandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ComandaFrame().setVisible(true);
            }
        });

        panel.add(clientButton);
        panel.add(produsButton);
        panel.add(comandaButton);
        add(panel);
    }


}
