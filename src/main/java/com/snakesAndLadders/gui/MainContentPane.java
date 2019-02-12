package com.snakesAndLadders.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.snakesAndLadders.util.Style;

public class MainContentPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton createGameBTN = new JButton();
	private JButton joinGameBTN = new JButton();
	private JLabel logoLBL = new JLabel();
	private SnakesAndLaddersUI parentFrame;

	public MainContentPane(SnakesAndLaddersUI parentFrame) {
		this.parentFrame = parentFrame;
		mainContentPaneInit();
		createGameBTNInit();
		joinGameBTNInit();
		logoLBLInit();
	}

	/**
	 * Initialized panel contentPane
	 */
	private void mainContentPaneInit() {
		// Set Size of Window
		parentFrame.setSize(600, 300);

		setBackground(new Color(162, 205, 90));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
	}

	/**
	 * Initialized button createGameBTN
	 */
	private void createGameBTNInit() {
		createGameBTN.setText("Spiel erstellen");
		createGameBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createGameBTNClick();
			}
		});

		createGameBTN.setBounds(360, 50, 180, 40);
		createGameBTN.setFont(Style.getDefaultFont());
		add(createGameBTN);
	}

	/**
	 * Event mouseClicked on createGameBTN
	 */
	private void createGameBTNClick() {
		parentFrame.changeContentPane(new CreateGameContentPane(parentFrame));
	}

	/**
	 * Initialized button joinGameBTN
	 */
	private void joinGameBTNInit() {
		joinGameBTN.setText("Spiel beitreten");
		joinGameBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				joinGameBTNClick();
			}
		});

		joinGameBTN.setBounds(360, 160, 180, 40);
		joinGameBTN.setFont(Style.getDefaultFont());
		add(joinGameBTN);
	}

	/**
	 * Event mouseClicked on joinGameBTN
	 */
	private void joinGameBTNClick() {
		parentFrame.changeContentPane(new JoinGameContentPane(parentFrame));
	}

	/**
	 * Initialized logoLBL
	 */
	private void logoLBLInit() {
		logoLBL.setText("");
		int width = 300;
		int height = 270;
		logoLBL.setBounds(60, 0, width, height);

		ImageIcon logo = new ImageIcon(this.getClass().getResource("/logo_klein.png"),"Snakes & Laders Logo");
		logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		logoLBL.setIcon(logo);

		add(logoLBL);
	}
}
