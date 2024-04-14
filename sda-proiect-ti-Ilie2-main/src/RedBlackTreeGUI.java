

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import redblack.Node;
import redblack.RedBlackTree;
public class RedBlackTreeGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel mainPanel1 = null;
	private RedBlackTree redBlackTree;
    private TreePanel treePanel;

    public RedBlackTreeGUI() {
    	mainPanel1 = new JPanel(new BorderLayout());
        redBlackTree = new RedBlackTree();

        setTitle("Red-Black Tree GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1200);

        treePanel = new TreePanel(redBlackTree);
        getContentPane().add(treePanel, BorderLayout.CENTER);

        JButton insertButton = new JButton("Insert Node");
        JButton deleteButton = new JButton("Delete Node");
        JButton printTreeButton = new JButton("Print Tree");

        insertButton.addActionListener(e -> handleInsertButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
        printTreeButton.addActionListener(e -> handlePrintTreeButton());

        JPanel controlPanel = new JPanel();
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);
        controlPanel.add(printTreeButton);

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        
        JScrollBar scrollBar = new JScrollBar();
        getContentPane().add(scrollBar, BorderLayout.WEST);
        

    }

    private void handleInsertButton() {
        String input = JOptionPane.showInputDialog("Enter value to insert:");
        try {
            int value = Integer.parseInt(input);
            redBlackTree.insert(value);
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.");
        }
    }

    private void handleDeleteButton() {
        String input = JOptionPane.showInputDialog("Enter value to delete:");
        try {
            int value = Integer.parseInt(input);
            redBlackTree.delete(value);
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.");
        }
    }

    private void handlePrintTreeButton() {
        // Implementați afișarea arborelui și actualizați vizualizarea
        treePanel.repaint();
    }

    private class TreePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final int NODE_RADIUS = 20;
        private RedBlackTree redBlackTree;

        public TreePanel(RedBlackTree redBlackTree) {
            this.redBlackTree = redBlackTree;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, redBlackTree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }

        private void drawTree(Graphics g, Node node, int x, int y, int xOffset) {
            if (node != null) {
                // Desenare nod curent
                g.setColor(node.getColor() == 0 ? Color.BLACK : Color.RED);
                g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(node.getData()), x - 5, y + 5);

                // Desenare copii
                drawTree(g, node.getLeft(), x - xOffset, y + 50, xOffset / 2);
                drawTree(g, node.getRight(), x + xOffset, y + 50, xOffset / 2);
            }
        }
    }

    public void updateDisplay() {
        treePanel.repaint();
    }

    public JPanel getPanel() {
        return mainPanel1; // sau orice alt container care conține interfața BTreeGui
    }

    // În clasa RedBlackTreeGUI
    public RedBlackTreeGUI getPanel1() {
        return this; // sau orice alt container care conține interfața RedBlackTreeGUI
    }
}
