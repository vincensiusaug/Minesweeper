import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI {

    private  int n;
    private JFrame frame;
    private Minesweeper minesweeper;
    private JPanel boardPanel;
    private JButton[][] buttons;
    private Cell[][] minesweeperCells;
    private MouseAdapter listener;
    
    private JMenuBar menuBar;
    private JMenu menuMenu;
    private JMenuItem quitItem;
    private JMenuItem newItem;

    private JPanel topPanel;
    private JLabel winCount;
    private JButton resetBtn;
    private JLabel gameName;
    private JLabel loseCount;

    public MinesweeperGUI(int n, double bomb_percent){

        this.n = n;
        frame = new JFrame("Minesweeper");
        minesweeper = new Minesweeper(n, bomb_percent);
        minesweeperCells = minesweeper.GetCells();
        buttons = new JButton[n][n];

        boardPanel = new JPanel();
        listener = new MouseAdapter();

        boardPanel.setLayout(new GridLayout(n,n));

        for(int y = 0; y < n; ++y){
        for(int x = 0; x < n; ++x){
                buttons[y][x] = new JButton("");
                buttons[y][x].addMouseListener(listener);
                boardPanel.add(buttons[y][x]);
            }
        }

        SetupMenuBar();
        frame.setJMenuBar(menuBar);
        SetupGameInfo();
        frame.add(BorderLayout.NORTH, topPanel);
        frame.add(BorderLayout.CENTER, boardPanel);
        frame.setSize((n/2)*100,(n/2)*100);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    private void SetupMenuBar(){
        menuMenu = new JMenu("Menu");
        quitItem = new JMenuItem("Quit");
        quitItem.addMouseListener(listener);
        newItem = new JMenuItem("New Game");
        newItem.addMouseListener(listener);
        menuMenu.add(newItem);
        menuMenu.add(quitItem);
        menuBar = new JMenuBar();
        menuBar.add(menuMenu);
    }

    private void SetupGameInfo(){
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        winCount = new JLabel ("Win: " + minesweeper.getWin() + " ");
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(3, 3, 3, 3);
        topPanel.add (winCount, constraints);

        resetBtn = new JButton("Reset!");
        resetBtn.addMouseListener(listener);
        resetBtn.setBackground(Color.RED);
        resetBtn.setContentAreaFilled(false);
        resetBtn.setOpaque(true);
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(3, 3, 3, 3);
        topPanel.add (resetBtn, constraints);

        gameName = new JLabel ("- Vincent Tampan -");
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(3, 3, 3, 3);
        topPanel.add (gameName, constraints);

        loseCount = new JLabel ("Lost: " + minesweeper.getLoss() + " ");
        constraints.gridy = 1;
        constraints.gridx = 2;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(3, 3, 3, 3);
        topPanel.add (loseCount, constraints);
    }

    private void Update(){
        for(int y = 0; y < this.n; ++y){
            for(int x = 0; x < this.n; ++x){

                if(minesweeperCells[y][x].isOpen()){
                    buttons[y][x].setEnabled(false);
                    if(minesweeperCells[y][x].isMine()){
                        buttons[y][x].setText("*");
                    }
                    else if(minesweeperCells[y][x].getNumber() > 0){
                        buttons[y][x].setText(Integer.toString(minesweeperCells[y][x].getNumber()));
                    }
                    else{
                        buttons[y][x].setText("");
                    }
                }
                
                else{
                    buttons[y][x].setEnabled(true);
                    if(minesweeperCells[y][x].isFlagged()){
                        buttons[y][x].setText("F");
                    }
                    else{
                        buttons[y][x].setText("");
                    }
                }
            }
        }
    }

    private void Restart(){
        minesweeper.Reset();
        winCount.setText("Win: " + minesweeper.getWin() + " ");
        loseCount.setText("Lost: " + minesweeper.getLoss() + " ");
        for(int y = 0; y < n; ++y){
            for(int x = 0; x< n; ++x){
                buttons[y][x].setText("");
            }
        }
    }

    private class MouseAdapter implements MouseListener{

        public void mouseClicked(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}

		public void mousePressed(MouseEvent event) {
        }
        
		public void mouseReleased(MouseEvent event) {
			JComponent click = (JComponent) event.getSource();

			if (click == quitItem){
                System.exit(0);
            }
			
			if (click == newItem){
                frame.dispose();
                Game.StartGame();
			}

			if (click == resetBtn){
                Restart();
            }
            
            for (int y = 0; y < n; ++y) {
			    for (int x = 0; x < n; ++x){
                    if (event.getButton() == 1) {
                        if (click == buttons[y][x]) {
                            if (!minesweeperCells[y][x].isFlagged()){
                                minesweeper.Select(y, x);
                                if(minesweeperCells[y][x].isMine()){
                                    minesweeper.addLoss();
                                    minesweeper.OpenAll();
                                    Update();
                                    JOptionPane.showMessageDialog(null, "Mampos kao!");
                                    Restart();
                                }
                            }
                        }
                        if (minesweeper.isWin()) {
                            minesweeper.OpenAll();
                            Update();
                            JOptionPane.showMessageDialog(null, "Menang ngecit!");
                            minesweeper.addWin();
                            Restart();
                        }
                    }
				}
            }
			for (int y = 0; y < n; ++y) {
                for (int x = 0; x < n; ++x) {
                    if (event.getButton() == 3) {
                        if (click == buttons[y][x]) {
                            if (minesweeperCells[y][x].isFlagged()){
                                minesweeperCells[y][x].setFlagged(false);
                            }
                            else{
                                minesweeperCells[y][x].setFlagged(true);
                            }
                        }
                    }
                }
			}
			Update();
		}   
    }
        
}