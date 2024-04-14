import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import bst.BinarySearchTree;
public class BinarySearchTreeGUI {
    private BinarySearchTree bst;
    private JTree tree;
    private JPanel panel;

    public BinarySearchTreeGUI() {
        this.bst = new BinarySearchTree();
        panel = new JPanel();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Binary Search Tree Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton insertButton = new JButton("Insert");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");

        JTextField valueTextField = new JTextField();
        valueTextField.setPreferredSize(new Dimension(100, 30));
        valueTextField.setToolTipText("Enter a value");

        tree = new JTree();
        JScrollPane treeScrollPane = new JScrollPane(tree);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(valueTextField.getText());
                    bst.insert(value);
                    refreshTree();
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input. Please enter a valid integer.");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(valueTextField.getText());
                    boolean result = bst.search(value);
                    showAlert("Search for value " + value + ": " + result);
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input. Please enter a valid integer.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(valueTextField.getText());
                    bst.delete(value);
                    refreshTree();
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input. Please enter a valid integer.");
                }
            }
        });

        panel.add(valueTextField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(insertButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(treeScrollPane, BorderLayout.SOUTH);

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }


    private void refreshTree() {
        DefaultTreeModel treeModel = bst.getTreeModel();
        if (treeModel != null) {
            tree.setModel(treeModel);
        }
    }

    private void showAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinarySearchTreeGUI().createAndShowGUI();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

	public void updateDisplay() {
	    refreshTree();
	}

}