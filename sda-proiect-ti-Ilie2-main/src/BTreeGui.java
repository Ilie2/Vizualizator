import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import bili.BTree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BTreeGui extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JPanel mainPanel1 = null;

	private BTree bTree;

    private JTextField keyField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton displayButton;
    private JTree tree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;

    public BTreeGui(int i) {
    	mainPanel1 = new JPanel(new BorderLayout());
        bTree = new BTree(i);

        setTitle("B-Tree GUI");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        keyField = new JTextField(10);
        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Key:"));
        controlPanel.add(keyField);
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);
        controlPanel.add(displayButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(tree), BorderLayout.CENTER);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String keyText = keyField.getText();
                    if (!keyText.isEmpty()) {
                        int key = Integer.parseInt(keyText);
                        bTree.insert(key);
                        updateDisplay();
                        keyField.setText("");
                    } else {
                        // Afișați un mesaj sau tratați altfel cazul în care câmpul este gol
                        JOptionPane.showMessageDialog(BTreeGui.this, "Introduceți un număr valid.");
                    }
                } catch (NumberFormatException ex) {
                    // Afișați un mesaj sau tratați altfel cazul în care nu s-a putut face conversia
                    JOptionPane.showMessageDialog(BTreeGui.this, "Introduceți un număr valid.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key = Integer.parseInt(keyField.getText());
                bTree.delete(key);
                updateDisplay();
                keyField.setText("");
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        });

        add(mainPanel);
    }

    public void updateDisplay() {
        rootNode.removeAllChildren();
        buildTree(rootNode, bTree.getRoot());
        treeModel.reload();
    }

    private void buildTree(DefaultMutableTreeNode parent, BTree.Node node) {
        if (node != null) {
            DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(); // Nodul curent

            // Adaugă cheile la nodul curent
            for (int i = 0; i < node.getN(); i++) {
                currentNode.add(new DefaultMutableTreeNode(node.getKeys()[i]));
            }

            parent.add(currentNode);

            // Afișează subarborii pentru nodurile ne-frunză
            if (!node.isLeaf()) {
                for (int i = 0; i <= node.getN(); i++) {
                    if (node.getChildren()[i] != null) {
                        // Apelează recursiv buildTree pentru fiecare copil
                        buildTree(currentNode, node.getChildren()[i]);
                    }
                }
            }
        }
    }

    public JPanel getPanel() {
        return mainPanel1; // Return the JPanel that you want to add to cardPanel
    }

    // În clasa RedBlackTreeGUI
    public BTreeGui getPanel1() {
        return this; // sau orice alt container care conține interfața RedBlackTreeGUI
    }
}
