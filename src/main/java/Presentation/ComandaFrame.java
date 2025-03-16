package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import Dao.ClientDAO;
import Dao.ComandaDAO;
import Dao.ProdusDAO;
import ModelClasses.Client;
import ModelClasses.Comanda;
import ModelClasses.Produs;


public class ComandaFrame extends JFrame {
    private JComboBox<String> clientComboBox;
    private JComboBox<String> produsComboBox;
    private JTextField cantitateTextField;
    private JTable comandaTable;
    private DefaultTableModel comandaTableModel;
    private JTextArea displayArea;
    private ComandaDAO comandaDAO = new ComandaDAO();

    public ComandaFrame() {
        ClientDAO clientDAO = new ClientDAO();
        ProdusDAO produsDAO = new ProdusDAO();
        setTitle("Comanda Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Nume Client:"));
        clientComboBox = new JComboBox<>();
        clientComboBox.setPreferredSize(new Dimension(150, 30));
        viewAllClients(clientDAO);
        inputPanel.add(clientComboBox);

        inputPanel.add(new JLabel("Nume Produs:"));
        produsComboBox = new JComboBox<>();
        produsComboBox.setPreferredSize(new Dimension(150, 30));
        viewAllProducts(produsDAO);
        inputPanel.add(produsComboBox);

        inputPanel.add(new JLabel("Cantitate:"));
        cantitateTextField = new JTextField();
        cantitateTextField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(cantitateTextField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        JButton validateButton = new JButton("Validare");
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateOrder();
            }
        });
        buttonPanel.add(validateButton);

        JButton viewAllOrdersButton = new JButton("View All Orders");
        viewAllOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllOrders();
            }
        });
        buttonPanel.add(viewAllOrdersButton);

        add(buttonPanel, BorderLayout.CENTER);

        comandaTableModel = new DefaultTableModel();
        comandaTable = new JTable(comandaTableModel);
        add(new JScrollPane(comandaTable), BorderLayout.SOUTH);
    }

    private void viewAllClients(ClientDAO clientDAO) {
        List<Client> clients = clientDAO.findAll();
        for (Client client : clients) {
            clientComboBox.addItem(client.getName());
        }
    }

    private void viewAllProducts(ProdusDAO produsDAO) {
        List<Produs> produsList = produsDAO.findAll();
        for (Produs produs : produsList) {
            produsComboBox.addItem(produs.getName());
        }
    }

    private void validateOrder() {
        Random random = new Random();
        int randomNumber = random.nextInt(101);

        String clientName = (String) clientComboBox.getSelectedItem();
        String produsName = (String) produsComboBox.getSelectedItem();
        int cantitate = Integer.parseInt(cantitateTextField.getText());

        ProdusDAO produsDAO = new ProdusDAO();
        ComandaDAO comandaDAO = new ComandaDAO();
        Produs produs = produsDAO.findByName(produsName);
        int stoc = produs.getCantitate();

        if (cantitate > stoc) {
            JOptionPane.showMessageDialog(this, "Nu există suficient stoc pentru produsul selectat.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            produs.setCantitate(stoc - cantitate);
            produsDAO.update(produs);
            Comanda comanda = new Comanda(randomNumber, clientName, produsName, cantitate);
            comandaDAO.insert(comanda);
            JOptionPane.showMessageDialog(this, "Comanda a fost validată cu succes.");
            viewAllOrders();
        }
    }

    private void viewAllOrders() {
        comandaTableModel.setRowCount(0);
        List<Comanda> comenzi = comandaDAO.findAll();
        ReflectionUtil.populateTableFromList(comenzi, comandaTableModel);
    }


}
