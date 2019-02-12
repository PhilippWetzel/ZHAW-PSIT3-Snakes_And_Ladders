package com.snakesAndLadders.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.snakesAndLadders.gui.controller.CreateGameContentController;
import com.snakesAndLadders.questionlist.QuestionList;
import com.snakesAndLadders.questionlist.QuestionListLoader;

public class CreateGameContentPane extends JPanel {
	private SnakesAndLaddersUI parentFrame;
	private JLabel titleLBL = new JLabel();
	private JLabel nameLBL = new JLabel();
	private JLabel playerNumberLBL = new JLabel();
	private JLabel extendedGameModeLBL = new JLabel();
	private JLabel questionListLBL = new JLabel();
	private JTextField nameTXT = new JTextField();
	private JComboBox<Integer> playerCountCB = new JComboBox<>();
	private JCheckBox extendedGameModeCB = new JCheckBox();
	private JTextField questionListTXT = new JTextField();
	private JButton importQuestionListBTN = new JButton();
	private JButton downloadTemplateBTN = new JButton();
	private JButton createGameBTN = new JButton();
	private int playerNumber;
	private QuestionList questions = null;

	private static final long serialVersionUID = 1L;
	private CreateGameContentController controller;

	public CreateGameContentPane(SnakesAndLaddersUI parentFrame) {
		this.parentFrame = parentFrame;
		createGameContentPaneInit();
		titleLBLInit();
		nameLBLInit();
		playerNumberLBLInit();
		extendedGameModeLBLInit();
		questionListLBLInit();
		nameTXTInit();
		playerCountCBInit();
		extendedGameModeCBInit();
		questionListTXTInit();
		importQuestionListBTNInit();
		downloadTemplateBTNInit();
		createGameBTNInit();
		controller = new CreateGameContentController();
	}

	private void createGameContentPaneInit() {
		// Set Size of Window
		parentFrame.setSize(1200, 750);

		setBackground(new Color(162, 205, 90));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
	}

	private void titleLBLInit() {
		titleLBL.setText("Spiel erstellen");
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		int width = (int) (parentFrame.getWidth());
		int height = 100;
		titleLBL.setBounds(0, 0, width, height);
		titleLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

		add(titleLBL);
	}

	private void nameLBLInit() {
		nameLBL.setText("Name:");
		int x = 150;
		int y = 200;
		int width = 250;
		int height = 50;
		nameLBL.setBounds(x, y, width, height);
		nameLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		add(nameLBL);
	}

	private void nameTXTInit() {
		int x = 400;
		int y = 200;
		int width = 400;
		int height = 50;
		nameTXT.setBounds(x, y, width, height);
		nameTXT.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		nameTXT.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				enableCreateGameBTN();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				enableCreateGameBTN();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				enableCreateGameBTN();
			}
		});

		add(nameTXT);
	}

	private void enableCreateGameBTN() {
		if(!nameTXT.getText().isEmpty() && !extendedGameModeCB.isSelected()) {
			createGameBTN.setEnabled(true);
		}

		else if(extendedGameModeCB.isSelected() && !questionListTXT.getText().isEmpty() && !nameTXT.getText().isEmpty())  {
			createGameBTN.setEnabled(true);
		}
		else {
			createGameBTN.setEnabled(false);
		}
	}

	private void playerNumberLBLInit() {
		playerNumberLBL.setText("Spieleranzahl:");
		int x = 150;
		int y = 260;
		int width = 250;
		int height = 50;
		playerNumberLBL.setBounds(x, y, width, height);
		playerNumberLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		add(playerNumberLBL);
	}

	private void playerCountCBInit() {
		playerCountCB.setToolTipText("Wähle die Anzahl Spieler");
		int x = 400;
		int y = 260;
		int width = 100;
		int height = 50;
		playerCountCB.setBounds(x, y, width, height);
		playerCountCB.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		int numbers_to_add_max = 8;
		for (int i = 1; i <= numbers_to_add_max; i++) {
			playerCountCB.addItem(new Integer(i));
		}

		add(playerCountCB);
	}

	private void extendedGameModeLBLInit() {
		extendedGameModeLBL.setText("<html>Erweiterter<p/> Spiel-Modus:</html>");
		int x = 150;
		int y = 320;
		int width = 250;
		int height = 75;
		extendedGameModeLBL.setBounds(x, y, width, height);
		extendedGameModeLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		add(extendedGameModeLBL);
	}

	private void extendedGameModeCBInit() {
		extendedGameModeCB.setBackground(new Color(162, 205, 90));
		int x = 400;
		int y = 320;
		int width = 75;
		int height = 50;
		extendedGameModeCB.setBounds(x, y, width, height);

		// Set default icon for checkbox
		extendedGameModeCB.setIcon(new ImageIcon(this.getClass().getResource("/checkbox_empty.png")));
		// Set selected icon when checkbox state is selected
		extendedGameModeCB.setSelectedIcon(new ImageIcon(this.getClass().getResource("/checkbox_full.png")));


		extendedGameModeCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				enableExtendedMode(extendedGameModeCB.isSelected());
			}
		});
		enableExtendedMode(false);
		add(extendedGameModeCB);
	}

	public void enableExtendedMode(boolean enable) {
		questionListTXT.setEnabled(enable);
		importQuestionListBTN.setEnabled(enable);
		downloadTemplateBTN.setEnabled(enable);

		enableCreateGameBTN();
	}

	private void questionListLBLInit() {
		questionListLBL.setText("Frageliste:");
		int x = 150;
		int y = 380;
		int width = 250;
		int height = 50;
		questionListLBL.setBounds(x, y, width, height);
		questionListLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		add(questionListLBL);
	}

	private void questionListTXTInit() {
		questionListTXT.setToolTipText("Test");
		int x = 400;
		int y = 380;
		int width = 400;
		int height = 50;
		questionListTXT.setEditable(false);
		questionListTXT.setBounds(x, y, width, height);
		questionListTXT.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		add(questionListTXT);
	}

	private void importQuestionListBTNInit() {
		importQuestionListBTN.setText("Eigene Frageliste importieren");
		int x = 810;
		int y = 380;
		int width = 250;
		int height = 50;
		importQuestionListBTN.setBounds(x, y, width, height);
		importQuestionListBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		importQuestionListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importQuestionListBTNClick(questionListTXT);
				enableCreateGameBTN();
			}
		});

		add(importQuestionListBTN);
	}

	private void importQuestionListBTNClick(JTextField textField) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV files", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			questions = QuestionListLoader.loadQuestionList(file.getAbsolutePath());
			textField.setText(file.getName());
		}
	}

	private void downloadTemplateBTNInit() {
		downloadTemplateBTN.setText("Vorlage herunterladen");
		int x = 810;
		int y = 440;
		int width = 250;
		int height = 50;
		downloadTemplateBTN.setBounds(x, y, width, height);
		downloadTemplateBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		downloadTemplateBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				downloadTemplateBTNClick();
			}
		});

		add(downloadTemplateBTN);
	}

	private void downloadTemplateBTNClick() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File folder = chooser.getSelectedFile();
			QuestionListLoader.createTemplateList(folder.getAbsolutePath());
		}
	}

	private void createGameBTNInit() {
		createGameBTN.setText("Erstellen");
		int width = 250;
		int height = 75;
		int x = (int) ((parentFrame.getWidth()) - width)/2;
		int y = 550;
		createGameBTN.setBounds(x, y, width, height);
		createGameBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

		createGameBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createGameBTNClick();
			}
		});

		createGameBTN.setEnabled(false);
		add(createGameBTN);
	}

	@SuppressWarnings("static-access")
	private void createGameBTNClick() {
		String name = nameTXT.getText();
		playerNumber = (Integer) playerCountCB.getSelectedItem();
		boolean extendedMode = extendedGameModeCB.isSelected();

		controller.startServer(name, extendedMode, playerNumber,questions);

		waitToStopServerDialog();
	}

	private void waitToStopServerDialog(){
		//Enabled all elements on pane
		for (Component cp : this.getComponents()){
			cp.setEnabled(false);
		}

		JDialog dialog = new JDialog();
		ImageIcon loading = new ImageIcon(this.getClass().getResource("/loader.gif"));

		JLabel waitLBL = new JLabel("Spielserver läuft... IP: " + controller.getServerIP(), loading, JLabel.CENTER);
		waitLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitLBL.setBorder(BorderFactory.createLineBorder(Color.black));

		JButton stopServerBTN = new JButton();
		stopServerBTN.setText("Server beenden");
		stopServerBTN.setBounds(250, 75, 300, 0);
		stopServerBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		stopServerBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});

		JPanel pan = new JPanel();
		pan.setBackground(this.getBackground());
		pan.setBorder(new LineBorder(Color.black, 4));
		pan.setLayout(new FlowLayout());
		pan.add(waitLBL);
		pan.add(stopServerBTN);
		dialog.add(pan);
		dialog.setSize(800, 80);
		dialog.setTitle(parentFrame.getTitle());
		dialog.setUndecorated(true);
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setBackground(parentFrame.getBackground());
		dialog.setVisible(true);

		parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING));
	}

}
