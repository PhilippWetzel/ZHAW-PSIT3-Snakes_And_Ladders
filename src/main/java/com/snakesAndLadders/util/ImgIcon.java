package com.snakesAndLadders.util;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Util functions which are used for the pawns on the gui
 */
public class ImgIcon {
	public static ImageIcon getImageIconByColor(String color){
		ImageIcon icon = new javax.swing.ImageIcon(ImgIcon.class.getResource("/pawn/pawn-48_"+ color +".png"));
		icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		return icon;
	}

	public static ArrayList<String> getColorList(){
		ArrayList<String> colorList = new ArrayList<>();
		colorList.add("barbie");
		colorList.add("black");
		colorList.add("blue");
		colorList.add("caribbean");
		colorList.add("guacamole");
		colorList.add("orange");
		colorList.add("soylent");
		colorList.add("yellow");
		return colorList;
	}
}
