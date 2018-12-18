import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

class Game{

    static MinesweeperGUI gui;

    public static void StartGame(){
        if (gui != null) {
            gui = null;
        }
        JFrame frame = new JFrame("Minesweeper");
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(3, 3, 3, 3);
        
        String[] diffChoice = {"Easy", "Medium", "Hard", "Extreme"};
        String[] sizeChoice = {"Small", "Medium", "Large"};
        
        JLabel diffText = new JLabel("Difficulty");
        JLabel sizeText = new JLabel("Size");

        JComboBox<String> diffBox = new JComboBox<>(diffChoice);
        JComboBox<String> sizeBox = new JComboBox<>(sizeChoice);
        constraints.gridy = 0;
        constraints.gridx = 0;
        frame.add(diffText, constraints);
        constraints.gridy = 0;
        constraints.gridx = 1;
        frame.add(diffBox, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        frame.add(sizeText, constraints);
        constraints.gridy = 1;
        constraints.gridx = 1;
        frame.add(sizeBox, constraints);
        JButton okBtn = new JButton("Play");
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        frame.add(okBtn, constraints);

        int[] gridSizePool = {10, 15, 20};
        double[] difficultyPercent = {0.1, 0.2, 0.3, 0.8};
        
        okBtn.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = sizeBox.getSelectedIndex();
                int sizeChoice = gridSizePool[i];
                i = diffBox.getSelectedIndex();
                double diffChoice = difficultyPercent[i];
                gui = new MinesweeperGUI((int)(sizeChoice), (double)(diffChoice));
                frame.dispose();
            }
        });

        frame.setSize(200,150);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        StartGame();
        
    }
}