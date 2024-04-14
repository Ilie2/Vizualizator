import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel cardPanel;
    private CardLayout cardLayout;

    private BTreeGui bTreeGui;
    private RedBlackTreeGUI redBlackTreeGui;
    private BinarySearchTreeGUI bstgui;

    public Main() {
        setTitle("Tree Visualization App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        bTreeGui = new BTreeGui(2);
        redBlackTreeGui = new RedBlackTreeGUI();
        bstgui = new BinarySearchTreeGUI();

        cardPanel.add(bTreeGui.getPanel(), "BTreePanel");
        cardPanel.add(redBlackTreeGui.getPanel(), "RedBlackTreePanel");
        cardPanel.add(bstgui.getPanel(), "BSTPanel");

        JButton bTreeButton = new JButton("B-Tree Interface");
        bTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "BTreePanel");
                bTreeGui.setVisible(true);
                bTreeGui.updateDisplay();
            }
        });

        JButton redBlackTreeButton = new JButton("Red-Black Tree Interface");
        redBlackTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "RedBlackTreePanel");
                redBlackTreeGui.setVisible(true);
                redBlackTreeGui.updateDisplay();
            }
        });

        JButton bstguiButton = new JButton("BST Interface");
        bstguiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bstgui != null) {
                    cardLayout.show(cardPanel, "BSTPanel");
                    bstgui.createAndShowGUI();
                    bstgui.updateDisplay();
                }
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bTreeButton);
        buttonPanel.add(redBlackTreeButton);
        buttonPanel.add(bstguiButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainFrame = new Main();
            mainFrame.setVisible(true);
        });
    }
}
