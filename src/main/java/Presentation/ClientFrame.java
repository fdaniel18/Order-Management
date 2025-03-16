package Presentation;

import Dao.ClientDAO;
import ModelClasses.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class ClientFrame extends JFrame {
    private ClientDAO clientDAO;
    private JTextField idField, nameField, addressField, emailField, ageField;
    private JTable clientsTable;
    private DefaultTableModel clientsTableModel;

    public ClientFrame() {
        clientDAO = new ClientDAO();
        setTitle("Client Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Client");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Client");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editClient();
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Client");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });
        buttonPanel.add(deleteButton);

        JButton viewAllButton = new JButton("View All Clients");
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllClients();
            }
        });
        buttonPanel.add(viewAllButton);

        add(buttonPanel, BorderLayout.CENTER);

        clientsTableModel = new DefaultTableModel();
        clientsTable = new JTable(clientsTableModel);
        add(new JScrollPane(clientsTable), BorderLayout.SOUTH);
    }

    private void addClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText());

            if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client(id, name, address, email, age);
            clientDAO.insert(client);
            JOptionPane.showMessageDialog(this, "Client added successfully.");
            clearFields();
            viewAllClients();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText());

            if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = clientDAO.findById(id);
            if (client == null) {
                JOptionPane.showMessageDialog(this, "Client not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            client.setName(name);
            client.setAddress(address);
            client.setEmail(email);
            client.setAge(age);
            clientDAO.update(client);
            JOptionPane.showMessageDialog(this, "Client updated successfully.");
            clearFields();
            viewAllClients();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            Client client = clientDAO.findById(id);
            if (client == null) {
                JOptionPane.showMessageDialog(this, "Client not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            clientDAO.delete(client);
            JOptionPane.showMessageDialog(this, "Client deleted successfully.");
            clearFields();
            viewAllClients();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllClients() {
        clientsTableModel.setRowCount(0);
        List<Client> clients = clientDAO.findAll();
        ReflectionUtil.populateTableFromList(clients, clientsTableModel);
    }


    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
        ageField.setText("");
    }
}