import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Binomial.Heap;

public class BinomialHeapSwing extends JFrame {
    private static final long serialVersionUID = 1L;
    private Heap<Integer> binomialHeap = new Heap<>();
    private JTextField insertField;
    private JLabel minLabel;
    private JTextArea heapDisplay;

    public BinomialHeapSwing() {
        setTitle("Binomial Heap GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        insertField = new JTextField();
        insertField.setColumns(10);

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertKey();
            }
        });

        JButton extractMinButton = new JButton("Extract Min");
        extractMinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractMin();
            }
        });

        minLabel = new JLabel("Min: ");
        heapDisplay = new JTextArea();
        heapDisplay.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(insertField);
        inputPanel.add(insertButton);
        inputPanel.add(extractMinButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(minLabel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(heapDisplay), BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void insertKey() {
        try {
            int key = Integer.parseInt(insertField.getText());
            binomialHeap.insereaza(key);
            insertField.setText("");
            updateMinLabel();
            updateHeapDisplay();
        } catch (NumberFormatException e) {
            // Ignoră introducerile invalide
        }
    }

    private void extractMin() {
        Integer min = binomialHeap.extractMin();
        if (min != null) {
            updateMinLabel();
            updateHeapDisplay();
        }
    }

    private void updateMinLabel() {
        Integer min = binomialHeap.extractMin();
        if (min != null) {
            minLabel.setText("Min: " + min);
        } else {
            minLabel.setText("Min: ");
        }
    }

    private void updateHeapDisplay() {
        heapDisplay.setText(binomialHeap.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BinomialHeapSwing();
            }
        });
    }
}
