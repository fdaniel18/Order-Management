package Presentation;

import Dao.ProdusDAO;
import ModelClasses.Client;
import ModelClasses.Produs;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdusFrame extends JFrame {
    private ProdusDAO produsDAO;
    private JTextField idField, nameField, priceField, cantitateField;
    private JTable produsTable;
    private DefaultTableModel produsTableModel;

    public ProdusFrame() {
        produsDAO = new ProdusDAO();
        setTitle("Produs Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Cantitate:"));
        cantitateField = new JTextField();
        inputPanel.add(cantitateField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Produs");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProdus();
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Produs");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProdus();
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Produs");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProdus();
            }
        });
        buttonPanel.add(deleteButton);

        JButton viewAllButton = new JButton("View All Products");
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllProdus();
            }
        });
        buttonPanel.add(viewAllButton);

        add(buttonPanel, BorderLayout.CENTER);

        produsTableModel = new DefaultTableModel();
        produsTable = new JTable(produsTableModel);
        add(new JScrollPane(produsTable), BorderLayout.SOUTH);
    }

    private void addProdus() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText());
            int cantitate = Integer.parseInt(cantitateField.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produs produs = new Produs(id, name, price, cantitate);
            produsDAO.insert(produs);
            JOptionPane.showMessageDialog(this, "Produs added successfully.");
            clearFields();
            viewAllProdus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editProdus() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText());
            int cantitate = Integer.parseInt(cantitateField.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produs produs = produsDAO.findById(id);
            if (produs == null) {
                JOptionPane.showMessageDialog(this, "Produs not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            produs.setName(name);
            produs.setPrice(price);
            produs.setCantitate(cantitate);
            produsDAO.update(produs);
            JOptionPane.showMessageDialog(this, "Produs updated successfully.");
            clearFields();
            viewAllProdus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProdus() {
        try {
            int id = Integer.parseInt(idField.getText());
            Produs produs = produsDAO.findById(id);
            if (produs == null) {
                JOptionPane.showMessageDialog(this, "Produs not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            produsDAO.delete(produs);
            JOptionPane.showMessageDialog(this, "Produs deleted successfully.");
            clearFields();
            viewAllProdus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllProdus() {
        produsTableModel.setRowCount(0);
        List<Produs> produse = produsDAO.findAll();
        ReflectionUtil.populateTableFromList(produse, produsTableModel);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        cantitateField.setText("");
    }
}
