package com.snakesAndLadders.gui;

import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakesAndLaddersUI extends JFrame{
	private static SnakesAndLaddersUI frame;
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(SnakesAndLaddersUI.class.getName());
	/**
	 * starts Snakes and Ladders
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new SnakesAndLaddersUI();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.warning(e.getMessage());
				}
			}
		});
	}

	/**
	 * Constructor for SnakesAndLaddersUI
	 */
	public SnakesAndLaddersUI() {
		snakesAndLaddersUIInit();
		setDefaultContentPane();
	}

	/**
	 * Initialized frame TextanalyseGUI
	 */
	private void snakesAndLaddersUIInit(){
		setResizable(false);
		setTitle("Snakes and Ladders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(100, 100);
		setLocationRelativeTo(null);
	}

	/**
	 * Set  the default contentpane to MainContentPane
	 */
	private void setDefaultContentPane(){
		setContentPane(new MainContentPane(this));
	}

	public void changeContentPane(JPanel newContentPane){
		setContentPane(newContentPane);
		validate();
		repaint();
	}

	@Override
	public void setSize(int width, int height){
		super.setSize(width,height);
		setLocationRelativeTo(null);
	}

}
