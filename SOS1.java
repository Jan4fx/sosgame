import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//random documentation https://www.educative.io/answers/how-to-generate-random-numbers-in-java
//https://github.com/Jan4fx/sosgame`
//getting multiple values out of a function https://www.techiedelight.com/return-multiple-values-method-java/


//left up diagonal S doesn't match on border


public class SOS1 extends Frame implements ActionListener {
    
    private Label lblTitle;
    private Label redScore;
    private Label currentTurn;
    public static int redPoints = 0;
    private Label blueScore;
    public static int bluePoints = 0;
    private Panel pnlMain;
    private static Button[][] buttons;
    private Button btnNewGame;
    private Button btnExit;
    private int turn;
    private int[][] values;
    private Button S;
    private Button O;   
    private Button twoPlayers;
    private Button onePlayer;
    private Button noPlayers;
    private int playerMode = -1;
    //-1 no pick 0 twoPlayers 1 onePlayer 2 noPlayers
    private String playerInput = "S";
    
    public SOS1() {
        super("SOS");
        setLayout(new BorderLayout());
        lblTitle = new Label("SOS", Label.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        currentTurn = new Label("TURN: RED", Label.LEFT);
        currentTurn.setFont(new Font("Serif", Font.BOLD, 24));
        redScore = new Label("Red: " + redPoints, Label.CENTER);
        redScore.setFont(new Font("Serif", Font.BOLD, 24));
        blueScore = new Label("Blue: " + bluePoints, Label.LEFT);
        blueScore.setFont(new Font("Serif", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);
        add(redScore, BorderLayout.EAST);
        add(blueScore, BorderLayout.WEST);
        //add(currentTurn, BorderLayout.NORTH);
        pnlMain = new Panel();
        pnlMain.setLayout(new GridLayout(10, 10));
        buttons = new Button[10][10];
        values = new int[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                buttons[i][j] = new Button();
                pnlMain.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
            }
        }
        add(pnlMain, BorderLayout.CENTER);
        Panel pnlBottom = new Panel();
        Panel pnlMiddle = new Panel();
        pnlBottom.setLayout(new FlowLayout());
        pnlMiddle.setLayout(new FlowLayout());
        btnNewGame = new Button("New Game");
        S = new Button("S");
        O = new Button("O");
        twoPlayers = new Button("Player Vs Player");
        onePlayer = new Button("Player Vs AI");
        noPlayers = new Button("AI Vs AI");
        pnlBottom.add(btnNewGame);
        pnlBottom.add(S);
        pnlBottom.add(O);
        pnlMiddle.add(twoPlayers);
        pnlMiddle.add(onePlayer);
        pnlMiddle.add(noPlayers);
        btnNewGame.addActionListener(this);
        S.addActionListener(this);
        O.addActionListener(this);
        twoPlayers.addActionListener(this);
        onePlayer.addActionListener(this);
        noPlayers.addActionListener(this);
        btnExit = new Button("Exit");
        pnlBottom.add(btnExit);
        btnExit.addActionListener(this);
        pnlBottom.add(currentTurn);
        add(pnlBottom, BorderLayout.SOUTH);
        add(pnlMiddle, BorderLayout.NORTH);
        turn = 1; // 1 for blue, -1 for red
        setSize(1000, 1000);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNewGame) {
            // reset game
            turn = 1;
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    buttons[i][j].setLabel("");
                    buttons[i][j].setForeground(Color.BLACK);
                    //System.out.print(buttons[i][j].getForeground());
                    System.out.print(buttons[i][j].getActionCommand());
                    values[i][j] = 0;
                }
            }
        } 
        else if(e.getSource() == S) {
            playerInput = "S";
        }
        else if(e.getSource() == O) {
            playerInput = "O";
        }
        else if(e.getSource() == twoPlayers) {
            playerMode = 0;
            twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( twoPlayers );
        }
        else if(e.getSource() == onePlayer) {
            playerMode = 1;
            twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( onePlayer );
        }
        else if(e.getSource() == noPlayers) {
            playerMode = 2;
            twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( noPlayers );
        }
        else if(e.getSource() == btnExit) {
            System.exit(0);
        } else if(playerMode == 0){
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(e.getSource() == buttons[i][j] && buttons[i][j].getLabel() == "") {
                        // place an S or an O in the grid for the current player's turn
                        if(playerInput == "S") {
                            buttons[i][j].setLabel("S");
                            buttons[i][j].setForeground(Color.BLACK);
                            //values[i][j] = 1;
                        } else {
                            buttons[i][j].setLabel("O");
                            buttons[i][j].setForeground(Color.BLACK);
                            //values[i][j] = -1;
                        }
                        // check for SOS
                        int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                        if(addPoints != 0) {
                            if(turn == 1){
                                redPoints += addPoints;
                                redScore.setText("Red: " + redPoints);
                                blueScore.setText("Blue: " + bluePoints);
                            }
                            else{
                                bluePoints += addPoints;
                                blueScore.setText("Blue: " + bluePoints);
                                redScore.setText("Red: " + redPoints);
                            }
                            // if SOS then give current player an extra turn
                            //turn = turn;
                        } else {
                            // otherwise it's the other player's turn
                            turn = -turn;
                            }
                        }
                        if(turn == 1){
                            currentTurn.setText("TURN: RED");
                        }
                        else{
                            currentTurn.setText("TURN: BLUE"); 
                        
                    }
                }
            }
        }
        else if(playerMode == 1){
            if(turn == -1){
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        if(e.getSource() == buttons[i][j] && buttons[i][j].getLabel() == "") {
                            // place an S or an O in the grid for the current player's turn
                            if(playerInput == "S") {
                                buttons[i][j].setLabel("S");
                                buttons[i][j].setForeground(Color.BLACK);
                                //values[i][j] = 1;
                            } else {
                                buttons[i][j].setLabel("O");
                                buttons[i][j].setForeground(Color.BLACK);
                                //values[i][j] = -1;
                            }
                            // check for SOS
                            int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                            if(addPoints != 0){
                                bluePoints += addPoints;
                                blueScore.setText("Blue: " + bluePoints);
                            }
                            else{
                                turn = -turn;
                            }
                        }
                    }
                }           
            }
            else{
            //AI TURN
                int i = 0;
                int j = 0;
                int[] coords = aiBoxDecider(i, j, turn);
                //i j = 0 issue
                int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
                if(addPoints != 0) {
                    redPoints += addPoints;
                    redScore.setText("Red: " + redPoints);
                    // if SOS then give current player an extra turn
                    //turn = turn;
                } else {
                    // otherwise it's the other player's turn
                        turn = -turn;
                    }
                }
            if(turn == 1){
                currentTurn.setText("TURN: AI");
            }
            else{
                currentTurn.setText("TURN: P1"); 
            }
        }
        else if(playerMode == 2){
            int i = 0;
            int j = 0;
            int[] coords = aiBoxDecider(i, j, turn);
            //i j = 0 issue
            int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
            if(addPoints != 0) {
                if(turn == 1){
                    redPoints += addPoints;
                    redScore.setText("Red: " + redPoints);
                    // if SOS then give current player an extra turn
                    //turn = turn;
                }
                else{
                    bluePoints += addPoints;
                    redScore.setText("Blue: " + bluePoints);
                }
            } else {
                // otherwise it's the other player's turn
                    turn = -turn;
                }
            if(turn == 1){
                currentTurn.setText("TURN: AI1");
            }
            else{
                currentTurn.setText("TURN: AI2"); 
            }
        }

    }

    
    
    public static String whichLetter(int x, int y){
        if(buttons[x][y].getLabel() == "S"){
            return "O";
        }
        else{
            return "S";
        }

    }

    public static int[] aiBoxDecider(int i, int j, int turn) {
        int[] coords = new int[2];
        for(int x = 0; x < 10; x++) {
                for(int y = 0; y < 10; y++) {
                    if(buttons[x][y].getLabel() != ""){
                        try{
                            if(buttons[x-1][y+1].getLabel() == ""){
                                buttons[x-1][y+1].setLabel(whichLetter(x, y));
                                coords[0] = x-1;
                                coords[1] = y+1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x][y+1].getLabel() == ""){
                                buttons[x][y+1].setLabel(whichLetter(x, y));
                                coords[0] = x;
                                coords[1] = y+1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x+1][y+1].getLabel() == ""){
                                buttons[x+1][y+1].setLabel(whichLetter(x, y));
                                coords[0] = x+1;
                                coords[1] = y+1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x-1][y].getLabel() == ""){
                                buttons[x-1][y].setLabel(whichLetter(x, y));
                                coords[0] = x-1;
                                coords[1] = y;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x][y-1].getLabel() == ""){
                                buttons[x][y-1].setLabel(whichLetter(x, y));
                                coords[0] = x;
                                coords[1] = y-1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x+1][y-1].getLabel() == ""){
                                buttons[x+1][y-1].setLabel(whichLetter(x, y));
                                coords[0] = x+1;
                                coords[1] = y-1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x+1][y].getLabel() == ""){
                                buttons[x+1][y].setLabel(whichLetter(x, y));
                                coords[0] = x+1;
                                coords[1] = y;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                        try{
                            if(buttons[x-1][y-1].getLabel() == ""){
                                buttons[x-1][y-1].setLabel(whichLetter(x, y));
                                coords[0] = x-1;
                                coords[1] = y-1;
                                return coords;
                            }
                        }
                        catch(Exception E){
                            //out of border
                        }
                    }
                }
        }
        //First move of the game
        while(true){
            int random1 = randomGenerator();
            int random2 = randomGenerator();
            if(buttons[random1][random2].getLabel() == ""){
                buttons[random1][random2].setLabel("S"); 
                coords[0] = random1;
                coords[1] = random2;
                return coords;
            }

        }
    }

    public static int randomGenerator(){
        Random rand = new Random(); //instance of random class
        int upperbound = 10;
          //generate random values from 0-10
        int int_random = rand.nextInt(upperbound); 
        return int_random;
    }

    public void changeColor(int i, int j, int turn, int bluePoints, int redPoints, Boolean addPoints){
        if(turn == 1){
            buttons[i][j].setForeground(Color.RED);
            if(addPoints == true ){
                redPoints++;
            }
        }
        else{
            buttons[i][j].setForeground(Color.BLUE);
            if(addPoints == true){
                bluePoints++;
            }
        }
    }
    
    // check for SOS at location (i, j)
    public int checkForSOS(int i, int j, int turn, int bluePoints, int redPoints) {
        System.out.print(buttons[i][j].getForeground());
        int addPoints = 0;
        if(buttons[i][j].getLabel() == "S"){
            //leftrow win
            try{
                if(buttons[i-1][j].getLabel() == "O" && buttons[i-2][j].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j].getForeground() == Color.BLACK || buttons[i-2][j].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j, turn, bluePoints, redPoints, false);
                    changeColor(i-2, j, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightrow win
            try{
                if(buttons[i+1][j].getLabel() == "O" && buttons[i+2][j].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i+1][j].getForeground() == Color.BLACK || buttons[i+2][j].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i+1, j, turn, bluePoints, redPoints, false);
                    changeColor(i+2, j, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //upcollumn win
            try{
                if(buttons[i][j+1].getLabel() == "O" && buttons[i][j+2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i][j+1].getForeground() == Color.BLACK || buttons[i][j+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i, j+1, turn, bluePoints, redPoints, false);
                    changeColor(i, j+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //downcollumn win
            try{
                if(buttons[i][j-1].getLabel() == "O" && buttons[i][j-2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i][j-1].getForeground() == Color.BLACK || buttons[i][j-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i, j-1, turn, bluePoints, redPoints, false);
                    changeColor(i, j-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftupdiagonal win
            try{
                if(buttons[i-1][j+1].getLabel() == "O" && buttons[i-2][j+2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j+1].getForeground() == Color.BLACK || buttons[i-2][j+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j+1, turn, bluePoints, redPoints, false);
                    changeColor(i-2, j+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightdowndiagonal win
            try{
                if(buttons[i+1][j-1].getLabel() == "O" && buttons[i+2][j-2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i+1][j-1].getForeground() == Color.BLACK || buttons[i+2][j-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i+1, j-1, turn, bluePoints, redPoints, false);
                    changeColor(i+2, j-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftdownndiagonal win
            try{
                if(buttons[i-1][j-1].getLabel() == "O" && buttons[i-2][j-2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j-1].getForeground() == Color.BLACK || buttons[i-2][j-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j-1, turn, bluePoints, redPoints, false);
                    changeColor(i-2,j-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightupdiagonal win
            try{
                if(buttons[i+1][j+1].getLabel() == "O" && buttons[i+2][j+2].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i+1][j+1].getForeground() == Color.BLACK || buttons[i+2][j+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i+1, j+1, turn, bluePoints, redPoints, false);
                    changeColor(i+2, j+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
        }
        if(buttons[i][j].getLabel() == "O"){
            //row win
            try{
                if(buttons[i-1][j].getLabel() == "S" && buttons[i+1][j].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j].getForeground() == Color.BLACK || buttons[i+1][j].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j, turn, bluePoints, redPoints, false);
                    changeColor(i+1, j, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //collum win
            try{
                if(buttons[i][j+1].getLabel() == "S" && buttons[i][j-1].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i][j+1].getForeground() == Color.BLACK || buttons[i][j-1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i, j+1, turn, bluePoints, redPoints, false);
                    changeColor(i, j-1, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftdiagonal win
            try{
                if(buttons[i-1][j+1].getLabel() == "S" && buttons[i+1][j-1].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j+1].getForeground() == Color.BLACK || buttons[i-1][j+1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j+1, turn, bluePoints, redPoints, false);
                    changeColor(i+1, j-1, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightdiagonal win
            try{
                if(buttons[i-1][j-1].getLabel() == "S" && buttons[i+1][j+1].getLabel() == "S"){
                    if(buttons[i][j].getForeground() == Color.BLACK || buttons[i-1][j-1].getForeground() == Color.BLACK || buttons[i+1][j+1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(i, j, turn, bluePoints, redPoints, true);
                    changeColor(i-1, j-1, turn, bluePoints, redPoints, false);
                    changeColor(i+1, j+1, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
        }
        return addPoints;
    }
    
    public static void main(String[] args) {
        SOS1 game = new SOS1();
    }
}
