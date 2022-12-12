import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//random documentation https://www.educative.io/answers/how-to-generate-random-numbers-in-java
//https://github.com/Jan4fx/sosgame
//getting multiple values out of a function https://www.techiedelight.com/return-multiple-values-method-java/
import java.io.*;

//figure out if you can reformat autamitcally all of the buttons when some are hidden and some are shown

public class SOS1 extends Frame implements ActionListener {
    
    public static Label lblTitle;
    public static Label redScore;
    public static Label gameStatus;
    public static Label currentTurn;
    public static int gridSize;
    public static int redPoints = 0;
    private Label blueScore;
    public static Button recordReplay;
    public static Boolean recording = false;
    //recordingStage
    //0 no recording
    //1 recording
    //2 play recording 
    public static int recordingStage = 0;
    public static int bluePoints = 0;
    private Panel pnlMain;
    private static Button[][] buttons;   
    TextField boardSize; 
    public static Button btnNewGame;
    public static Button btnExit;
    public static int turn;
    public static Button S;
    public static Button O;   
    public static Button twoPlayers;
    public static Button onePlayer;
    public static Button redbluePlayer;
    public static Boolean playerIsRed = true;
    public static Button noPlayers;
    public static Button boardSizeEnter;
    //0 two players 1 one player 2 ai vs ai
    public static int playerMode = -1;
    public static Button generalMode;
    public static Button simpleMode;
    public static Boolean gameOver = false;
    public static int maxTurns;
    public static int turnCount;
    //0 is general 1 is simple
    public static int gameMode = -1;
    //-1 no pick 0 twoPlayers 1 onePlayer 2 noPlayers
    public static String playerInput = "S";
    int i;
    int j;
   ArrayList<String> recordList = new ArrayList<String>();
   //the AI workflow does not know if it inputed S or O so there will need to be a boolean for
   //the record function to process it
   public static String AIPickedLetter;
    
    public SOS1() {
        super("SOS");
        setLayout(new BorderLayout());
        lblTitle = new Label("SOS", Label.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        currentTurn = new Label("Go Red", Label.LEFT);
        currentTurn.setFont(new Font("Serif", Font.BOLD, 24));
        redScore = new Label("Red:" + redPoints, Label.CENTER);
        redScore.setFont(new Font("Serif", Font.BOLD, 24));
        blueScore = new Label("Blue:" + bluePoints, Label.LEFT);
        blueScore.setFont(new Font("Serif", Font.BOLD, 24));
        recordReplay = new Button("Record Game");
        recordReplay.setVisible(true);
        add(lblTitle, BorderLayout.NORTH);
        add(redScore, BorderLayout.EAST);
        add(blueScore, BorderLayout.WEST);
        pnlMain = new Panel();
        pnlMain.setLayout(new GridLayout(10, 10));
        buttons = new Button[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                buttons[i][j] = new Button();
                pnlMain.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setLabel("");
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].setVisible(false);
            }
        }
        add(pnlMain, BorderLayout.CENTER);
        Panel pnlBottom = new Panel();
        Panel pnlMiddle = new Panel();
        pnlBottom.setLayout(new FlowLayout());
        pnlMiddle.setSize(1000,1000);   
        btnNewGame = new Button("New Game");
        S = new Button("S");
        O = new Button("O");
        generalMode = new Button("General Mode");
        simpleMode = new Button("Simple Mode");
        twoPlayers = new Button("Player Vs Player");
        onePlayer = new Button("Player Vs AI");
        redbluePlayer = new Button("Player is Red");
        redbluePlayer.setVisible(false);
        noPlayers = new Button("AI Vs AI");
        boardSizeEnter = new Button("Board Size");
        boardSize = new TextField(); 
        boardSize.setVisible(true);
        boardSize.setBounds(200,200,200,200);  
        boardSize.setBounds(50, 50, 200, 20);  
        gameStatus = new Label("Select Game Mode" , Label.LEFT);
        gameStatus.setFont(new Font("Serif", Font.BOLD, 24));
        pnlBottom.add(recordReplay); 
        pnlBottom.add(gameStatus); 
        pnlBottom.add(btnNewGame);
        pnlBottom.add(S);
        pnlBottom.add(O);
        pnlMiddle.add(generalMode);
        pnlMiddle.add(simpleMode);
        pnlMiddle.add(twoPlayers);
        pnlMiddle.add(onePlayer);
        pnlMiddle.add(redbluePlayer);
        pnlMiddle.add(noPlayers);
        pnlMiddle.add(boardSizeEnter);
        pnlMiddle.add(boardSize);
        btnNewGame.addActionListener(this);
        S.addActionListener(this);
        O.addActionListener(this);
        generalMode.addActionListener(this);
        simpleMode.addActionListener(this);
        twoPlayers.addActionListener(this);
        onePlayer.addActionListener(this);
        redbluePlayer.addActionListener(this);
        noPlayers.addActionListener(this);
        boardSizeEnter.addActionListener(this);
        boardSizeEnter.setVisible(true);
        recordReplay.setVisible(true);
        recordReplay.addActionListener(this);
        btnExit = new Button("Exit");
        pnlBottom.add(btnExit);
        btnExit.addActionListener(this);
        pnlBottom.add(currentTurn);
        add(pnlBottom, BorderLayout.SOUTH);
        add(pnlMiddle, BorderLayout.NORTH);
        turn = 1; // 1 for blue, -1 for red unless AI VS PLAYER
        setSize(1000, 1000);
        setVisible(true);
        currentTurn.setVisible(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNewGame) {
            newGame();
            
        }
        else if(e.getSource() == recordReplay){
            if(recording == false){
                recording = true;
                recordingStage = 1;
                recordReplay.setLabel("Recording");
            }
            if(recordingStage == 2){
                //playReplay();
                writeToFile(recordList);
            }
        }
        else if(e.getSource() == boardSizeEnter){
            showBoard();
            maxTurns = gridSize * gridSize;
            turnCount = 0;
            if(gameMode == 0){
                currentTurn.setText("Go Red");
            }
            else if(gameMode == 1){
                currentTurn.setText("Go P1");
            }
            else if(gameMode == 2){
                currentTurn.setText("Go AI1");
            }
        }
        else if(e.getSource() == twoPlayers) {
            recordList.add("Two Players Game Mode");
            playerMode = 0;
            //twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( twoPlayers );
            gameStatus.setText("Input Grid Size");
            currentTurn.setVisible(true);
            currentTurn.setText("Then Click Boad Size");
        }
        else if(e.getSource() == onePlayer) {
            recordList.add("One Player Game Mode");
            playerMode = 1;
            redbluePlayer.setVisible(true);
            twoPlayers.setVisible(false);
            //onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( onePlayer );
            currentTurn.setVisible(true);
            gameStatus.setText("Input Grid Size");
            currentTurn.setText("Then Click Boad Size");
        }
        else if(e.getSource() == redbluePlayer){
            if(playerIsRed == true){
                playerIsRed = false;
                redbluePlayer.setLabel("Player is Blue");
                recordList.add("Player is Blue");

            }
            else{
                playerIsRed = true;
                redbluePlayer.setLabel("Player is Red");
                recordList.add("Player is Red");
            }

        }
        else if(e.getSource() == noPlayers) {
            playerMode = 2;
            recordList.add("No Players");
            twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            currentTurn.setVisible(true);
            gameStatus.setText("Input Grid Size");
            currentTurn.setText("Then Click Boad Size");
        }
        else if(e.getSource() == generalMode){
            recordList.add("General Game Mode");
            gameMode = 0;
            simpleMode.setVisible(false);
            gameStatus.setText("Select # of Players");
        }
        else if(e.getSource() == simpleMode){
            recordList.add("Simple Game Mode");
            gameMode = 1;
            generalMode.setVisible(false);
            gameStatus.setText("Select # Of Players");
        }
        else if(e.getSource() == btnExit) {
            System.exit(0);
        } 
        else if(checkForActualGameOver(redPoints, bluePoints, gameMode, turnCount, maxTurns, recording, recordingStage) == true){
            gameOver = true;
            if(redPoints > bluePoints){
                gameStatus.setText("RED WON");
                recordList.add("RED WON");
                
            }
            else if(bluePoints > redPoints){
                gameStatus.setText("BLUE WON");
                recordList.add("BLUE WON");
                
            }
            else{
                gameStatus.setText("TIE GAME");
                recordList.add("TIE GAME");
                
            }
        }
        else if(e.getSource() == S) {
            playerInput = "S";
        }
        else if(e.getSource() == O) {
            playerInput = "O";
        }
        else if(playerMode == 0 && gameMode != -1 && gameOver == false){
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(e.getSource() == buttons[i][j] && buttons[i][j].getLabel() == "") {
                        // place an S or an O in the grid for the current player's turn
                        if(recording == false){
                            recordReplay.setVisible(false);
                        }
                        if(playerInput == "S") {
                            buttons[i][j].setLabel("S");
                            buttons[i][j].setForeground(Color.BLACK);
                            

                            if(turn == 1){
                                recordList.add("Red inputed S into " + "collumn " + i + " row " + j);
                            }
                            else{
                                recordList.add("Blue inputed S into " + "collumn " + i + " row " + j);
                            }
                            turnCount += 1;
                        } else {
                            buttons[i][j].setLabel("O");
                            buttons[i][j].setForeground(Color.BLACK);
                            if(turn == 1){
                                recordList.add("Red inputed O into " + "collumn " + i + " row " + j);
                            }
                            else{
                                recordList.add("Blue inputed O into " + "collumn " + i + " row " + j);
                            }
                            turnCount += 1;
                        }
                        int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                        if(addPoints != 0) {
                            if(turn == 1){
                                recordList.add("Red player now has " + redPoints + " points");
                                redPoints += addPoints;
                                redScore.setText("Red:" + redPoints);
                                blueScore.setText("Blue:" + bluePoints);
                            }
                            else{
                                recordList.add("Blue player now has " + bluePoints + " points");
                                bluePoints += addPoints;
                                blueScore.setText("Blue:" + bluePoints);
                                redScore.setText("Red:" + redPoints);
                            }
                            // if SOS then give current player an extra turn
                            //turn = turn;
                        } else {
                            // otherwise it's the other player's turn
                            turn = -turn;
                            }
                        }
                        if(turn == 1){
                            currentTurn.setText("Go Red");
                        }
                        else{
                            currentTurn.setText("Go Blue"); 
                        
                    }
                }
            }
        }
        else if(playerMode == 1 && gameMode != -1 && gameOver == false){
            if(turn == -1){
                for(int i = 0; i < gridSize; i++) {
                    for(int j = 0; j < gridSize; j++) {
                        if(e.getSource() == buttons[i][j] && buttons[i][j].getLabel() == "") {
                            // place an S or an O in the grid for the current player's turn
                            if(playerInput == "S") {
                                buttons[i][j].setLabel("S");
                                buttons[i][j].setForeground(Color.BLACK);
                                turnCount += 1;
                                recordList.add("Player inputed S into " + "collumn " + i + " row " + j);
                            } else {
                                buttons[i][j].setLabel("O");
                                recordList.add("Player inputed O into " + "collumn " + i + " row " + j);
                                buttons[i][j].setForeground(Color.BLACK);
                                turnCount += 1;
                            }
                            // check for SOS
                            int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                            
                            if(addPoints != 0){
                                if(playerIsRed == true){
                                    redPoints += addPoints;
                                    redScore.setText("Red:" + redPoints);
                                    recordList.add("Red now has " + redPoints + " Points");
                                }
                                else{
                                    bluePoints += addPoints;
                                    blueScore.setText("Blue:" + bluePoints);
                                    recordList.add("Blue now has " + bluePoints + " Points");
                                }
                            }
                            else{
                                turn = -turn;
                                recordList.add("AI Turn");
                                //AI TURN
                                i = 0;
                                j = 0;
                                int[] coords = aiBoxDecider(i, j, turn, gridSize);
                                System.out.print("\nAI PLACED here" + buttons[coords[0]] + " "  + buttons[coords[1]]);
                                recordList.add("AI inputed O into " + "collumn " + coords[0] + " row " + coords[1]);
                                buttons[coords[0]][coords[1]].setForeground(Color.BLACK);
                                //i j = 0 issue
                                addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
                                //keep track of how many turns left
                                turnCount += 1;
                                if(addPoints != 0) {
                                    if(playerIsRed == false){
                                        redPoints += addPoints;
                                        redScore.setText("Red:" + redPoints);
                                        recordList.add("AI now has " + redPoints + "points");
                                    }
                                    else{
                                        bluePoints += addPoints;
                                        blueScore.setText("Blue:" + bluePoints);
                                        recordList.add("AI now has " + bluePoints + "points");
                                    }
                                // if SOS then give current player an extra turn
                                //turn = turn;
                                } else {
                                // otherwise it's the other player's turn
                                    turn = -turn;
                                    currentTurn.setText("Go P1"); 
                                }
                            }
                        }
                    }
                }           
            }
            else{
            //AI TURN
                i = 0;
                j = 0;
                int[] coords = aiBoxDecider(i, j, turn, gridSize);
                //commented out for when debugging needed
                //System.out.print("\nAI PLACED here" + buttons[coords[0]] + " "  + buttons[coords[1]]);
                recordList.add("AI inputed O into " + "collumn " + coords[0] + " row " + coords[1]);
                buttons[coords[0]][coords[1]].setForeground(Color.BLACK);
                //i j = 0 issue
                int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
                turnCount += 1;
                if(addPoints != 0) {
                    if(playerIsRed == false){
                        redPoints += addPoints;
                        redScore.setText("Red:" + redPoints);
                        recordList.add("AI now has " + redPoints + "points");
                    }
                    else{
                        bluePoints += addPoints;
                        blueScore.setText("Blue:" + bluePoints);
                        recordList.add("AI now has " + bluePoints + "points");
                    }
                    // if SOS then give current player an extra turn
                    //turn = turn;
                } else {
                    // otherwise it's the other player's turn
                        turn = -turn;
                        currentTurn.setText("Go P1"); 
                    }
            }
        }
        else if(playerMode == 2 && gameMode != -1 && gameOver == false){
            i = 0;
            j = 0;
            int[] coords = aiBoxDecider(i, j, turn, gridSize);
            buttons[coords[0]][coords[1]].setForeground(Color.BLACK);
            int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
            if(buttons[coords[0]][coords[1]].getLabel() == "S"){
                AIPickedLetter = "S";
            }
            else{
                AIPickedLetter = "O";
            }
            recordList.add("AI1 inputed O into " + "collumn " + coords[0] + " row " + coords[1]);
            if(turn == 1){
                recordList.add("AI1 inputed " + AIPickedLetter +  " into " + "collumn " + coords[0] + " row " + coords[1]);
            }
            else{
                recordList.add("AI2 inputed " + AIPickedLetter +  " into " + "collumn " + coords[0] + " row " + coords[1]);
            }
            if(addPoints != 0) {
                if(turn == 1){
                    redPoints += addPoints;
                    redScore.setText("Red:" + redPoints);
                    recordList.add("AI1 now has " + redPoints + " points");
                    // if SOS then give current player an extra turn
                    //turn = turn;
                }
                else{
                    bluePoints += addPoints;
                    blueScore.setText("Blue:" + bluePoints);
                    recordList.add("AI2 now has " + bluePoints + " points");
                }
            } else {
                // otherwise it's the other player's turn
                    turn = -turn;
                }
            if(turn == 1){
                currentTurn.setText("Go AI1");
            }
            else{
                currentTurn.setText("Go AI2"); 
            }
            turnCount += 1;
        }

    }
        public static boolean checkForActualGameOver(int redPoints, int bluePoints, int gameMode, int turnCount, int maxTurns, boolean recording, int recordingStages){
            int filledBoxCount = 0;
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(buttons[i][j].getLabel() == "S" || buttons[i][j].getLabel() == "O"){
                        if(buttons[i][j].isVisible() == true){
                            filledBoxCount += 1;
                        }
                    }
                }
            }
            System.out.println("\filledBoxCount:" + filledBoxCount + " maxTurns: " + maxTurns);
            if(filledBoxCount >= maxTurns){
                if(redPoints > bluePoints){
                    gameStatus.setText("RED WON");
                    
                }
                else if(bluePoints > redPoints){
                    gameStatus.setText("BLUE WON");
                    
                }
                else{
                    gameStatus.setText("TIE GAME");
                    
                }
                if(recording == true){
                    recordReplay.setLabel("Play Replay");
                    recordingStage = 2;
                }
                else{
                    currentTurn.setText("Press New Game");
                }
                return true;
            }

            if((redPoints > 0 || bluePoints > 0) && (gameMode == 1)){
                //gameOver = true;
                if(redPoints > bluePoints){
                    gameStatus.setText("RED WON");
                    
                }
                else if(bluePoints > redPoints){
                    gameStatus.setText("BLUE WON");
                    
                }
                else{
                    gameStatus.setText("TIE GAME");
                    
                }
                if(recording == true){
                    recordReplay.setLabel("Play Replay");
                    recordingStage = 2;
                }
                else{
                    currentTurn.setText("Press New Game");
                }
                return true;
            }
            else{
                return false; 
            }
        }

    public static boolean checkForGameOver(int redPoints, int bluePoints, int gameMode, int turnCount, int maxTurns, boolean recording){
        //buttons[i][j].getLabel() == ""
        if((redPoints > 0 || bluePoints > 0) && (gameMode == 1) || (turnCount >= maxTurns)){
            //gameOver = true;
            if(redPoints > bluePoints){
                gameStatus.setText("RED WON");
                
            }
            else if(bluePoints > redPoints){
                gameStatus.setText("BLUE WON");
                
            }
            else{
                gameStatus.setText("TIE GAME");
                
            }
            if(recording == true){
                recordReplay.setLabel("Play Replay");
                recordingStage = 2;
            }
            else{
                currentTurn.setText("Press New Game");
            }
            return true;
        }
        else{
            return false; 
        }
    }

    public static void writeToFile(ArrayList<String> recordList){
        //"C:\\Users\\username\\example\\output.txt"
        try {
            ///Users/joshuanewton/Desktop/SOSPROJECT
            //BufferedWriter writer = new BufferedWriter(new FileWriter("Users/myUser/Desktop/SOSPROJECT/sosreplay.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("sosreplay.txt"));
            for (String records : recordList){
                writer.write("\n" + records);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showBoard(){
        //display the parts of the board that are requested 
        String s1 = boardSize.getText(); 
        System.out.print(s1);
        gridSize = Integer.parseInt(s1);
        System.out.print(gridSize);
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                buttons[i][j].setVisible(true);
            }
        }
        boardSize.setVisible(false);
        boardSize.setText("");
        boardSizeEnter.setVisible(false);
        gameStatus.setText("Game is LIVE");
    }
    
    
    public static String whichLetter(int x, int y){
        if(buttons[x][y].getLabel() == "S"){
            return "O";
        }
        else{
            return "S";
        }

    }

    public static int[] aiBoxDecider(int i, int j, int turn, int gridSize) {
        int[] coords = new int[2];
        for(int x = 0; x < gridSize; x++) {
                for(int y = 0; y < gridSize; y++) {
                    if(buttons[x][y].getLabel() != "" && buttons[x][y].isVisible() == true){
                        try{
                            if(buttons[x-1][y+1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x][y+1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x+1][y+1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x-1][y].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x][y-1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x+1][y-1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x+1][y].getLabel() == "" && buttons[x][y].isVisible() == true){
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
                            if(buttons[x-1][y-1].getLabel() == "" && buttons[x][y].isVisible() == true){
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
        int upperbound = gridSize;
          //generate random values from 1-10
        int int_random = rand.nextInt(upperbound); 
        return int_random - 1;
    }

    public void changeColor(int coordsX, int coordsY, int turn, int bluePoints, int redPoints, Boolean addPoints){
        //onePlayer player is turn = -1
        //onePlayer playerMode == 1
        //System.out.println("\nGame Mode:" + playerMode + "Turn: " + turn + "playerIsRed: " + playerIsRed);
        if(playerMode == 1){
            if(turn == -1 && playerIsRed == true){
                buttons[coordsX][coordsY].setForeground(Color.RED);
                if(addPoints == true ){
                    redPoints++;
                }
            }
            else if(turn == -1 && playerIsRed == false){
                buttons[coordsX][coordsY].setForeground(Color.BLUE);
                if(addPoints == true){
                    bluePoints++;
                }
            }
            else if(turn == 1 && playerIsRed == true){
                buttons[coordsX][coordsY].setForeground(Color.BLUE);
                if(addPoints == true){
                    bluePoints++;
                }
            }
            else if(turn == 1 && playerIsRed == false){
                buttons[coordsX][coordsY].setForeground(Color.RED);
                if(addPoints == true ){
                    redPoints++;
                }
            }
            else{
                //error
            }
        }
        if(turn == 1 && playerMode != 1){
            buttons[coordsX][coordsY].setForeground(Color.RED);
            if(addPoints == true ){
                redPoints++;
            }
        }
        else if(playerMode != 1){
            buttons[coordsX][coordsY].setForeground(Color.BLUE);
            if(addPoints == true){
                bluePoints++;
            }
        }
    }
    public void newGame(){
        // reset game
        recordList.clear();
        gameStatus.setText("Input Grid Size");
        currentTurn.setVisible(false);
        twoPlayers.setVisible(true);
        onePlayer.setVisible(true);
        redbluePlayer.setLabel("Player is Red");
        redbluePlayer.setVisible(false);
        playerIsRed = true;
        noPlayers.setVisible(true);
        boardSize.setVisible(true);
        boardSizeEnter.setVisible(true);
        generalMode.setVisible(true);
        simpleMode.setVisible(true);
        recordReplay.setVisible(true);
        redPoints = 0;
        bluePoints = 0;
        recording = false;
        recordingStage = 0;
        recordReplay.setLabel("Record Game");
        redScore.setText("Red:" + redPoints);
        blueScore.setText("Blue:" + bluePoints);
        //0 two players 1 one player 2 ai vs ai
        playerMode = -1;
        gameMode = -1;
        gameOver = false;
        turn = 1;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                buttons[i][j].setLabel("");
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].setVisible(false);
                //System.out.print(buttons[i][j].getForeground());
                //System.out.print(buttons[i][j].getActionCommand());
            }
        }
    }
    
    // check for SOS at location (i, j)
    public int checkForSOS(int coordsX, int coordsY, int turn, int bluePoints, int redPoints) {
        //System.out.print(buttons[coordsX][coordsY].getForeground());
        int addPoints = 0;
        if(buttons[coordsX][coordsY].getLabel() == "S"){
            //leftrow win
            try{
                if(buttons[coordsX-1][coordsY].getLabel() == "O" && buttons[coordsX-2][coordsY].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY].getForeground() == Color.BLACK || buttons[coordsX-2][coordsY].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY, turn, bluePoints, redPoints, false);
                    changeColor(coordsX-2, coordsY, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightrow win
            try{
                if(buttons[coordsX+1][coordsY].getLabel() == "O" && buttons[coordsX+2][coordsY].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX+1][coordsY].getForeground() == Color.BLACK || buttons[coordsX+2][coordsY].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX+1, coordsY, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+2, coordsY, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //upcollumn win
            try{
                if(buttons[coordsX][coordsY+1].getLabel() == "O" && buttons[coordsX][coordsY+2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX][coordsY+1].getForeground() == Color.BLACK || buttons[coordsX][coordsY+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX, coordsY+1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX, coordsY+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //downcollumn win
            try{
                if(buttons[coordsX][coordsY-1].getLabel() == "O" && buttons[coordsX][coordsY-2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX][coordsY-1].getForeground() == Color.BLACK || buttons[coordsX][coordsY-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX, coordsY-1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX, coordsY-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftupdiagonal win
            try{
                if(buttons[coordsX-1][coordsY+1].getLabel() == "O" && buttons[coordsX-2][coordsY+2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY+1].getForeground() == Color.BLACK || buttons[coordsX-2][coordsY+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY+1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX-2, coordsY+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightdowndiagonal win
            try{
                if(buttons[coordsX+1][coordsY-1].getLabel() == "O" && buttons[coordsX+2][coordsY-2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX+1][coordsY-1].getForeground() == Color.BLACK || buttons[coordsX+2][coordsY-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX+1, coordsY-1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+2, coordsY-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftdownndiagonal win
            try{
                if(buttons[coordsX-1][coordsY-1].getLabel() == "O" && buttons[coordsX-2][coordsY-2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY-1].getForeground() == Color.BLACK || buttons[coordsX-2][coordsY-2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY-1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX-2,coordsY-2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightupdiagonal win
            try{
                if(buttons[coordsX+1][coordsY+1].getLabel() == "O" && buttons[coordsX+2][coordsY+2].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX+1][coordsY+1].getForeground() == Color.BLACK || buttons[coordsX+2][coordsY+2].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX+1, coordsY+1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+2, coordsY+2, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
        }
        if(buttons[coordsX][coordsY].getLabel() == "O"){
            //row win
            try{
                if(buttons[coordsX-1][coordsY].getLabel() == "S" && buttons[coordsX+1][coordsY].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY].getForeground() == Color.BLACK || buttons[coordsX+1][coordsY].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+1, coordsY, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //collum win
            try{
                if(buttons[coordsX][coordsY+1].getLabel() == "S" && buttons[coordsX][coordsY-1].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX][coordsY+1].getForeground() == Color.BLACK || buttons[coordsX][coordsY-1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX, coordsY+1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX, coordsY-1, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //leftdiagonal win
            try{
                if(buttons[coordsX-1][coordsY+1].getLabel() == "S" && buttons[coordsX+1][coordsY-1].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY+1].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY+1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY+1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+1, coordsY-1, turn, bluePoints, redPoints, false);
                }
            }
            catch(Exception E){
                //skip
            }
            //rightdiagonal win
            try{
                if(buttons[coordsX-1][coordsY-1].getLabel() == "S" && buttons[coordsX+1][coordsY+1].getLabel() == "S"){
                    if(buttons[coordsX][coordsY].getForeground() == Color.BLACK || buttons[coordsX-1][coordsY-1].getForeground() == Color.BLACK || buttons[coordsX+1][coordsY+1].getForeground() == Color.BLACK){
                        addPoints++;
                    }
                    changeColor(coordsX, coordsY, turn, bluePoints, redPoints, true);
                    changeColor(coordsX-1, coordsY-1, turn, bluePoints, redPoints, false);
                    changeColor(coordsX+1, coordsY+1, turn, bluePoints, redPoints, false);
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
