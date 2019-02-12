package com.snakesAndLadders.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.snakesAndLadders.gui.controller.GameContentController;
import com.snakesAndLadders.network.client.ClientNetworkHandler;
import com.snakesAndLadders.util.ImgIcon;
import com.snakesAndLadders.util.Style;

public class GameContentPane extends JPanel {
	private final static Logger logger = Logger.getLogger(GameContentPane.class.getName());
	private static final long serialVersionUID = 1L;
	private JLabel gameFieldLBL = new JLabel();
	private JButton rollTheDiceBTN = new JButton();
	private JLabel rollTheDiceValueLBL = new JLabel();
	private JList<String> playerJList = new JList<>();
	private PlayerListRenderer renderer = new PlayerListRenderer();

	private SnakesAndLaddersUI parentFrame;
	private List<Piece> pieceList = new ArrayList<>();
	private boolean readyFlag = false;
	private PieceAnimator pieceAnimator;
	private boolean isExtendedMode = false;
	private GameContentController controller;

	public GameContentPane(SnakesAndLaddersUI parentFrame, ClientNetworkHandler handler) {
		this.parentFrame = parentFrame;

		controller = new GameContentController(this, handler);
		handler.addObserver(controller);

		playerJList = new JList<>();
		playerJList.setCellRenderer(getRenderer());

		gameContentPaneInit();
		gameFieldLBLInit();
		rollTheDiceBTNInit();
		rollTheDiceValueLBLInit();
		playerJListInit();
		pieceAnimator = new PieceAnimator(gameFieldLBL.getWidth(), gameFieldLBL.getHeight(),this);
		initListener();

	}

	private void initListener() {
		this.parentFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				controller.shutdownHandler();
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});

	}


	/**
	 * Method opens Swingworker to show a waiting-Dialog while waiting for
	 * Players-Joined notifications from the server
	 * 
	 * Once all the Players joined, the ready flag will be set and the dialog is
	 * closed again
	 * 
	 * 
	 */
	public void waitForStart() {
		JDialog dialog = new JDialog();
		ImageIcon loading = new ImageIcon(this.getClass().getResource("/loader.gif"));
		JLabel waitLBL = new JLabel("Warte auf weitere Spieler... ", loading, JLabel.CENTER);
		waitLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitLBL.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JButton stopClientrBTN = new JButton();
		stopClientrBTN.setText("Abbrechen");
		stopClientrBTN.setBounds(250, 75, 300, 0);
		stopClientrBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		stopClientrBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
				parentFrame.dispose();
			}
		});
		
		JPanel pan = new JPanel();
		pan.setBackground(this.getBackground());
		pan.setBorder(new LineBorder(Color.black, 4));
		pan.setLayout(new FlowLayout());
		pan.add(waitLBL);
		pan.add(stopClientrBTN);
		
		dialog.add(pan);
		dialog.setSize(800, 80);
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setUndecorated(true);
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(null);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {

					while (!getReadyFlag()) {
						java.util.concurrent.TimeUnit.MILLISECONDS.sleep(500);
					}
					changeGamefieldToQuestions();

				} catch (InterruptedException e) {
					logger.warning(e.getMessage());
				}
				return null;
			}

			@Override
			protected void done() {
				dialog.dispose();
			}

		};
		worker.execute();
		dialog.setVisible(true);

	}

	/**
	 * @param isExtendedMode the isExtendedMode to set
	 */
	public void setExtendedMode(boolean isExtendedMode) {
		this.isExtendedMode = isExtendedMode;
	}

	/**
	 * Initialized panel contentPane
	 */
	private void gameContentPaneInit() {
		// Set Size of Window
		parentFrame.setSize(1500, 760);

		setBackground(new Color(162, 205, 90));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
	}

	/**
	 * Initialized gameFieldLBL
	 */
	private void gameFieldLBLInit() {
		gameFieldLBL.setText("");

		int x = (int) (parentFrame.getWidth() * 0.25);
		int y = (int) (parentFrame.getWidth() * 0.01);
		int width = 1056;
		int height = 662;
		gameFieldLBL.setBounds(x, y, width, height);

		ImageIcon logo = new ImageIcon(this.getClass().getResource("/GameFiled_01.png"), "Gamefield");
		logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		gameFieldLBL.setIcon(logo);
		add(gameFieldLBL);
	}
	
	public void changeGamefieldToQuestions(){	
		 SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		            if(isExtendedMode){
		            	int width = 1056;
		        		int height = 662;
		        		ImageIcon logo = new ImageIcon(this.getClass().getResource("/GameFiled_01_question.png"), "Gamefield");
		        		logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		        		gameFieldLBL.setIcon(logo);
		            }
		        }
		    });
	}

	/**
	 * Initialized button rollTheDiceBTN
	 */
	private void rollTheDiceBTNInit() {
		rollTheDiceBTN.setText("würfeln");
		rollTheDiceBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rollTheDiceBTNClick();
			}
		});

		int x = 40;
		int y = 40;
		rollTheDiceBTN.setBounds(x, y, 140, 40);
		rollTheDiceBTN.setFont(Style.getDefaultFont());
		setRollTheDiceBTNEnable(false);
		add(rollTheDiceBTN);
	}

	public void setRollTheDiceBTNEnable(boolean arg0) {
		rollTheDiceBTN.setEnabled(arg0);
	}

	/**
	 * Event mouseClicked on rollTheDiceBTN
	 */
	private void rollTheDiceBTNClick() {
		controller.sendNewTurn();
		setRollTheDiceBTNEnable(false);
	}

	/**
	 * Initialized rollTheDiceValueLBL
	 */
	private void rollTheDiceValueLBLInit() {
		setRollTheDiceValueLBL(0);

		int x = 200;
		int y = 40;
		int width = 140;
		int height = 40;
		rollTheDiceValueLBL.setBounds(x, y, width, height);
		rollTheDiceValueLBL.setFont(Style.getDefaultFont());
		add(rollTheDiceValueLBL);
	}

	private void setRollTheDiceValueLBL(int value) {
		rollTheDiceValueLBL.setText("Wert: " + value);
	}

	/**
	 * Initialized playerJList
	 */
	private void playerJListInit() {
		// Spielerdaten abfüllen
		DefaultListModel<String> pieceData = new DefaultListModel<String>();
		for (Piece p : getPiecesList()) {
			pieceData.addElement(p.getPlayerName());
			logger.info("piecedata added: " + p.getPlayerName());
		}
		getJList().setModel(pieceData);

		int x = 40;
		int y = 100;
		getJList().setBounds(x, y, 300, 575);
		getJList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getJList().setSelectedIndex(0);
		add(getJList());
		logger.info("initPlayerList done! #pieces: " + getPiecesList().size());
	}

	public void piecesInit() {
		for (Piece p : getPiecesList()) {
			// set start position
			pieceAnimator.changePlayerPosition(p, 1, 1.0); //1.0 speed = fast
			// Add pawn to gameField
			gameFieldLBL.add(p.getPieceLBL());
		}
	}

	/**
	 * Ask a Question with a simple input box for the answer
	 * 
	 * @param question
	 * @return answer
	 */
	public String askQuestion(String question) {
		return JOptionPane.showInputDialog(parentFrame, question, parentFrame.getTitle() + " - Question",
				JOptionPane.QUESTION_MESSAGE);
	}


	public void showResultOfQuestion(boolean positive){
		StringBuilder sb = new StringBuilder();
		sb.append("Die Antwort war ");
		if (positive){
			sb.append("korrekt, Sie dürfen auf dem Feld bleiben.");
			JOptionPane.showConfirmDialog(parentFrame, sb.toString(), parentFrame.getTitle(),JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			sb.append("leider falsch, Sie gehen zurück. Viel Glück beim nächsten Versuch.");
			JOptionPane.showConfirmDialog(parentFrame, sb.toString(), parentFrame.getTitle(),JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void dieRolledInformation(String playerName, int rollValue) {
		StringBuilder sb = new StringBuilder();

		Object[] options = { "OK" };

		sb.append(playerName);
		sb.append(" hat eine ");
		sb.append(rollValue);
		sb.append(" gewürfelt.");
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/die.png"));
		
		JOptionPane.showOptionDialog(parentFrame,new JLabel(sb.toString(), icon, JLabel.LEFT) , parentFrame.getTitle(), JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, options, options[0]);
	}

	public void winInformation(String playerName) {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler ");
		sb.append(playerName);
		sb.append(" hat gewonnen! Herzliche Gratulation");

		JOptionPane.showConfirmDialog(parentFrame, sb.toString(), parentFrame.getTitle(),JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void changeTitleToName(String string) {
		this.parentFrame.setTitle(parentFrame.getTitle() + " - Player "+string);
	}

	public void handleMovePlayerMessage(String message) {
		String[] token = controller.splitMessage(message);

		try {
			int playerId = Integer.parseInt(token[1]);
			int number = Integer.parseInt(token[2]);

			Piece p = pieceList.get(playerId);
			pieceAnimator.changePlayerPosition(p, number, 0.1); //slow movement 0.1

		} catch (NumberFormatException e) {
			logger.warning("Wrong command format! expected number");
		}

	}

	public void showNumberOfDice(String message) {
		String[] token = controller.splitMessage(message);

		try {
			int playerId = Integer.parseInt(token[1]);
			int number = Integer.parseInt(token[2]);

			String name = playerJList.getModel().getElementAt(playerId);
			dieRolledInformation(name, number);

		} catch (NumberFormatException e) {
			logger.warning("Wrong command format! expected number");
		}
	}

	public void addNewPlayer(String message) {
		String[] token = controller.splitMessage(message);
		int id = Integer.parseInt(token[1]);
		Piece p = new Piece(Integer.parseInt(token[1]), "Player " + id, ImgIcon.getColorList().get(id));
		getPiecesList().add(p);
		logger.info("recieved new Player");
		playerJListInit();
		gameFieldLBLInit();
	}

	private List<Piece> getPiecesList() {
		return this.pieceList;
	}

	private boolean getReadyFlag() {
		return this.readyFlag;
	}

	public void setReadyFlag() {
		this.readyFlag = true;
	}

	private PlayerListRenderer getRenderer() {
		return this.renderer;
	}

	private JList<String> getJList() {
		return playerJList;
	}

	private class PlayerListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("rawtypes")
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(getPiecesList().get(index).getPieceLBL().getIcon());
			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setFont(Style.getDefaultFont());
			label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			return label;
		}
	}

}
