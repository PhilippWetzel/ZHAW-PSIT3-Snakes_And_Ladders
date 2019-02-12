package com.snakesAndLadders.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class job is to animate the pieces when they are being moved on the field
 */
public class PieceAnimator {
    private final static Logger logger = Logger.getLogger(PieceAnimator.class.getName());

    private Timer timer;
    private Point targetPoint;
    private Point startPoint;
    private double runTime;
    private Piece animatedPiece;
    private Long startTime;

    private int gameWidth;
    private int gameHeight;

    private volatile boolean isAnimating = false;
    private JPanel gui;

    public PieceAnimator(int gameWidth, int gameHeight, JPanel gui){
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gui = gui;

    }
    /**
     * Method to move a player Use method for all kinds of movements!
     *
     * @param p
     * @param fieldNr
     */
    public void changePlayerPosition(Piece p, int fieldNr, double speed) {
        new Thread(() -> {
            logger.setLevel(Level.SEVERE);
            while(isAnimating){
                try{
                    wait();
                }catch(Exception e){
                    logger.severe("an error occured during waiting:" +e.getMessage());
                }
            }
            isAnimating=true;

            //Waiting 500ms in between the animations
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startPoint = new Point(p.getPieceLBL().getLocation());
            targetPoint = calculateTargetPoint(p.getPlayerId(), fieldNr, speed);
            animatedPiece = p;

            timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (targetPoint.getX() == animatedPiece.getPieceLBL().getX() && targetPoint.getY() == animatedPiece.getPieceLBL().getY()) {
                        isAnimating = false;
                        timer.stop();
                    }

                    long duration = System.currentTimeMillis() - startTime;
                    double progress = duration / runTime;

                    if (progress >= 1.0) {
                        progress = 1.0;
                        isAnimating = false;
                        timer.stop();
                    }

                    int x = (int) (startPoint.x + ((targetPoint.x - startPoint.x) * progress));
                    int y = (int) (startPoint.y + ((targetPoint.y - startPoint.y) * progress));
                    animatedPiece.getPieceLBL().setBounds(x, y, 64, 64);
                    gui.repaint();
                    if(!isAnimating){
                        //TODO: normally the timer falls into this part and stops, but sometimes it keeps repeating
                        timer.stop();
                        logger.info("should stop now:"+this.toString());
                        timer.setRepeats(false);
                    }
                }
            });
            double distance = Math.sqrt(Math.pow((startPoint.x - targetPoint.x), 2) + Math.pow((startPoint.y - targetPoint.y),2));
            runTime = distance / (double) speed;
            timer.stop();
            startTime = System.currentTimeMillis();
            timer.start();
            p.setFieldNr(fieldNr);
        }).start();

    }

    /**
     * calculated target point for player
     *
     * @param playerId
     * @param fieldNr
     * @param speed
     * @return target point
     */
    private Point calculateTargetPoint(int playerId , int fieldNr, double speed){
        int offset = playerId * 10; //damit spieler nebeneinander dargestellt werden
        int newFieldNr = fieldNr -1; //somit felder von 0 - 99, Pro linie 0 - 9, 10 - 19, etc...
        int lineNr = ((int) (newFieldNr) / 10) ; //Zeile in der wir uns befinden berechnen

        double xOneStepSize = (gameWidth / 10);
        double yOneStepSize = (gameHeight / 10);

        int xMoveCount = ((newFieldNr) % 10);
        int yMoveCount = (((newFieldNr) / 10) % 10);

        int x = 0; // start Field 0
        int y = gameHeight; // Start Field 0

        if (lineNr % 2 == 1) {
            // Zeile 2, 4, 6,...
            x = (int) ((gameWidth - xOneStepSize) - xOneStepSize * xMoveCount);
        } else {
            // Zeile 1, 3, 5,...
            x = (int) (x + xOneStepSize * xMoveCount);
        }
        //add offset for each player
        x += offset;

        y = (int) ((y - yOneStepSize) - yOneStepSize * yMoveCount);
        return new Point(x + 8,y + 5); //+9 und +5 für korrektur zum zeichnen
    }


}
