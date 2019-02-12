package com.snakesAndLadders.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.snakesAndLadders.gui.controller.JoinGameContentController;

import javax.swing.JButton;

public class JoinGameContentPane extends JPanel {

	private JoinGameContentController controller;
	private static final long serialVersionUID = 1L;
	private SnakesAndLaddersUI parentFrame;
	private JLabel titleLBL = new JLabel();
	private JButton joinGameBTN = new JButton();
	private String[] columnNames = { "Spielname", "Spieleranzahl", "IP" };
	private String[][] tableData = { { "IT16a", "2" , "160.85.136.166"}, { "IT16b", "5" , "160.85.119.46"} };
	private JTable gameList = new JTable(tableData, columnNames){
	
		private static final long serialVersionUID = 1L;
		//alternating coloring of the lines in the table
		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				comp.setBackground(row % 2 == 0 ? Color.white : new Color(173, 216, 230));
				if (isRowSelected(row)) {
					comp.setBackground(Color.GREEN);
				}
				return comp;
			}
			//It is not allow to edit something in the table
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
	};
	private JScrollPane listPane = new JScrollPane(gameList);

	public JoinGameContentPane(SnakesAndLaddersUI parentFrame) {
		this.parentFrame = parentFrame;
		
		createGameContentPaneInit();
		titleLBLInit();
		listPaneInit();
		joinGameBTNInit();

		gameList.setRowSelectionAllowed(true);
		gameList.setColumnSelectionAllowed(false);
		gameList.getTableHeader().setReorderingAllowed(false);
		gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		controller = new JoinGameContentController();
	}

	private void createGameContentPaneInit() {
		// Set Size of Window
		parentFrame.setSize(600, 800);
		parentFrame.setTitle(parentFrame.getTitle());

		setBackground(new Color(162, 205, 90));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
	}

	private void titleLBLInit() {
		titleLBL.setText("Spiel beitreten");
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		int width = (int) (parentFrame.getWidth());
		int height = 100;
		titleLBL.setBounds(0, 0, width, height);
		titleLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

		add(titleLBL);
	}

	private void listPaneInit() {
		int width = 500;
		int height = 500;
		int x = (int) ((parentFrame.getWidth()) - width) / 2;
		int y = 100;
		listPane.setBounds(x, y, width, height);
		listPane.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		add(listPane);
	}

	private void joinGameBTNInit() {
		joinGameBTN.setText("Beitreten");
		joinGameBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				joinGameBTNClick();
			}
		});

		int width = 250;
		int height = 75;
		int x = (int) ((parentFrame.getWidth()) - width) / 2;
		int y = 630;
		joinGameBTN.setBounds(x, y, width, height);
		joinGameBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		add(joinGameBTN);
	}

	/* possible threading problem: Server already sends message before 
	 * the handler is set to the Pane?
	 * Tried to solve it by creating the pane before starting the handler*/
	private void joinGameBTNClick() {
			String serverIP = (String) JOptionPane.showInputDialog(parentFrame, "Geben Sie die IP des Server ein.", parentFrame.getTitle(),JOptionPane.QUESTION_MESSAGE);
			
			GameContentPane pane = controller.joingameAndGetNewPane(serverIP, parentFrame);
			parentFrame.changeContentPane(pane);
			pane.waitForStart();
	}

}
