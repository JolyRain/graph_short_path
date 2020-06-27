package frame;

import graph.TypeRoad;

import javax.swing.*;
import java.awt.*;

public class App {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int ZERO = 0;
    private static final int LEFT_PANEL_WIDTH = 250;
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private JFrame frame;
    private PaintGraphPanel graphicPanel;
    private JPanel leftPanel;
    private FileManager fileManager;

    public App() {
        createFrame();
        initElements();
        fileManager = new FileManager(graphicPanel);

    }

    private void createFrame() {
        frame = new JFrame("Freight map");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        createLeftPanel();
        leftPanel.setBounds(ZERO, ZERO, LEFT_PANEL_WIDTH, frame.getHeight());
        graphicPanel = new PaintGraphPanel();
        graphicPanel.setLayout(null);
        graphicPanel.setBounds(LEFT_PANEL_WIDTH, ZERO, WIDTH - LEFT_PANEL_WIDTH, HEIGHT);
        frame.add(graphicPanel);
        frame.add(leftPanel);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        JRadioButton creatingButton = new JRadioButton("Create vertex", true);
        creatingButton.setFont(FONT_BUTTON);
        creatingButton.addActionListener(e -> graphicPanel.setCreatingMode());
        leftPanel.add(creatingButton);

        JRadioButton connectingButton = new JRadioButton("Connect vertex", false);
        connectingButton.setFont(FONT_BUTTON);
        connectingButton.addActionListener(e -> graphicPanel.setModeConnecting());
        leftPanel.add(connectingButton);

        JRadioButton deletingButton = new JRadioButton("Deleting mode", false);
        deletingButton.setFont(FONT_BUTTON);
        deletingButton.addActionListener(e -> graphicPanel.setDeletingMode());
        leftPanel.add(deletingButton);

        JRadioButton chooseVertexMode = new JRadioButton("Choose vertices", false);
        chooseVertexMode.setFont(FONT_BUTTON);
        chooseVertexMode.addActionListener(e -> graphicPanel.setChooseVertexMode());
        leftPanel.add(chooseVertexMode);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(creatingButton);
        buttonGroup.add(connectingButton);
        buttonGroup.add(deletingButton);
        buttonGroup.add(chooseVertexMode);

        JButton showQuick = new JButton("Show quick path");
        showQuick.setFont(FONT_BUTTON);
        showQuick.addActionListener(e -> graphicPanel.showPath(TypeRoad.QUICK));
        leftPanel.add(showQuick);

        JButton showShort = new JButton("Show short path");
        showShort.setFont(FONT_BUTTON);
        showShort.addActionListener(e -> graphicPanel.showPath(TypeRoad.SHORT));
        leftPanel.add(showShort);

        JButton showCheaper = new JButton("Show cheap path");
        showCheaper.setFont(FONT_BUTTON);
        showCheaper.addActionListener(e -> graphicPanel.showPath(TypeRoad.CHEAP));
        leftPanel.add(showCheaper);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(FONT_BUTTON);
        clearButton.addActionListener(e -> graphicPanel.clear());
        leftPanel.add(clearButton);

        JButton saveButton = new JButton("Save image");
        saveButton.setFont(FONT_BUTTON);
        saveButton.addActionListener(e -> fileManager.saveImage());
        leftPanel.add(saveButton);

        JButton saveFileButton = new JButton("Save file");
        saveFileButton.setFont(FONT_BUTTON);
        saveFileButton.addActionListener(e -> fileManager.saveGraphToFile());
        leftPanel.add(saveFileButton);

        JButton readFileButton = new JButton("Read file");
        readFileButton.setFont(FONT_BUTTON);
        readFileButton.addActionListener(e -> fileManager.readGraphFromFile());
        leftPanel.add(readFileButton);

        JButton helpButton = new JButton("Instruction");
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "In vertex creation mode - draw a node with a mouse click.\n" +
                        "In connect mode - click on the node, then on the node you want to join.\n" +
                        "In deletion mode - by clicking the mouse, delete the elements of the graph.\n" +
                        "In vertex selection mode - select the start and end vertices," +
                        " then click the \"show ... path\" button, \n" + "to reset selected nodes, click on any other.",
                "How to use", JOptionPane.INFORMATION_MESSAGE));
        helpButton.setFont(FONT_BUTTON);
        leftPanel.add(helpButton);

        JCheckBox nameCheck = new JCheckBox("Show names");
        nameCheck.setFont(FONT_BUTTON);
        nameCheck.addActionListener(e -> {
            if (nameCheck.isSelected()) graphicPanel.setShowName(true);
            else graphicPanel.setShowName(false);
            graphicPanel.repaint();
        });
        leftPanel.add(nameCheck);

        JCheckBox weightCheck = new JCheckBox("Show characteristics");
        weightCheck.setFont(FONT_BUTTON);
        weightCheck.addActionListener(e -> {
            if (weightCheck.isSelected()) graphicPanel.setShowWeight(true);
            else graphicPanel.setShowWeight(false);
            graphicPanel.repaint();
        });
        leftPanel.add(weightCheck);

    }

}
