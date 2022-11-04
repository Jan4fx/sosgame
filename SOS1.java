import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//random documentation https://www.educative.io/answers/how-to-generate-random-numbers-in-java
//https://github.com/Jan4fx/sosgame
//getting multiple values out of a function https://www.techiedelight.com/return-multiple-values-method-java/


//left up diagonal S doesn't match on border


public class SOS1 extends Frame implements ActionListener {
    
    private Label lblTitle;
    private Label redScore;
    private Label gameStatus;
    private Label currentTurn;
    public int gridSize;
    public static int redPoints = 0;
    private Label blueScore;
    public static int bluePoints = 0;
    private Panel pnlMain;
    private static Button[][] buttons;
    //Frame f = new Frame("TextField Example");    
    TextField boardSize; 
    private Button btnNewGame;
    private Button btnExit;
    private int turn;
    private Button S;
    private Button O;   
    private Button twoPlayers;
    private Button onePlayer;
    private Button noPlayers;
    private Button boardSizeEnter;
    private int playerMode = -1;
    private Button generalMode;
    private Button simpleMode;
    private Boolean gameOver = false;
    public int maxTurns;
    public int turnCount;
    //0 is general 1 is simple
    private int gameMode = -1;
    //-1 no pick 0 twoPlayers 1 onePlayer 2 noPlayers
    private String playerInput = "S";
    
    public SOS1() {
        super("SOS");
        setLayout(new BorderLayout());
        lblTitle = new Label("SOS", Label.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        currentTurn = new Label("TURN: RED", Label.LEFT);
        currentTurn.setFont(new Font("Serif", Font.BOLD, 24));
        redScore = new Label("Red:" + redPoints, Label.CENTER);
        redScore.setFont(new Font("Serif", Font.BOLD, 24));
        blueScore = new Label("Blue:" + bluePoints, Label.LEFT);
        blueScore.setFont(new Font("Serif", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);
        add(redScore, BorderLayout.EAST);
        add(blueScore, BorderLayout.WEST);
        //add(currentTurn, BorderLayout.NORTH);
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
        //pnlMiddle.setLayout(new FlowLayout());
        pnlMiddle.setSize(1000,1000);   
        btnNewGame = new Button("New Game");
        S = new Button("S");
        O = new Button("O");
        generalMode = new Button("General Mode");
        simpleMode = new Button("Simple Mode");
        twoPlayers = new Button("Player Vs Player");
        onePlayer = new Button("Player Vs AI");
        noPlayers = new Button("AI Vs AI");
        boardSizeEnter = new Button("Board Size");
        boardSize = new TextField(); 
        boardSize.setVisible(true);
        boardSize.setBounds(200,200,200,200);  
        //String s1 = boardSize.getText(); 
        //int a = Integer.parseInt(s1);
        //System.out.print(a);
        boardSize.setBounds(50, 50, 200, 20);  
        gameStatus = new Label("Select Game Mode" , Label.LEFT);
        gameStatus.setFont(new Font("Serif", Font.BOLD, 24));
        pnlBottom.add(gameStatus); 
        pnlBottom.add(btnNewGame);
        pnlBottom.add(S);
        pnlBottom.add(O);
        pnlMiddle.add(generalMode);
        pnlMiddle.add(simpleMode);
        pnlMiddle.add(twoPlayers);
        pnlMiddle.add(onePlayer);
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
        noPlayers.addActionListener(this);
        boardSizeEnter.addActionListener(this);
        boardSizeEnter.setVisible(true);
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
            newGame();
        }
        else if(e.getSource() == boardSizeEnter){
            /* 
            String s1 = boardSize.getText(); 
            System.out.print(s1);
            gridSize = Integer.parseInt(s1);
            System.out.print(gridSize);
            for(int i = 0; i < gridSize; i++) {
                for(int j = 0; j < gridSize; j++) {
                    buttons[i][j].setVisible(true);
                    //System.out.print(buttons[i][j].getForeground());
                    //System.out.print(buttons[i][j].getActionCommand());
                }
            }
            boardSize.setVisible(false);
            boardSize.setText("");
            boardSizeEnter.setVisible(false);
            gameStatus.setText("Game is LIVE");
            */
            showBoard();
            maxTurns = gridSize * gridSize;
            turnCount = 0;
        }
        else if(e.getSource() == twoPlayers) {
            playerMode = 0;
            //twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( twoPlayers );
            gameStatus.setText("Input Grid Size");
        }
        else if(e.getSource() == onePlayer) {
            playerMode = 1;
            twoPlayers.setVisible(false);
            //onePlayer.setVisible(false);
            noPlayers.setVisible(false);
            //pnlMiddle.remove( onePlayer );
            gameStatus.setText("Input Grid Size");
        }
        else if(e.getSource() == noPlayers) {
            playerMode = 2;
            twoPlayers.setVisible(false);
            onePlayer.setVisible(false);
            //noPlayers.setVisible(false);
            //pnlMiddle.remove( noPlayers );
            gameStatus.setText("Input Grid Size");
        }
        else if(e.getSource() == generalMode){
            gameMode = 0;
            simpleMode.setVisible(false);
            gameStatus.setText("Select # of Players");
        }
        else if(e.getSource() == simpleMode){
            gameMode = 1;
            generalMode.setVisible(false);
            gameStatus.setText("Select # Of Players");
        }
        else if(e.getSource() == btnExit) {
            System.exit(0);
        } 
        else if((redPoints > 0 || bluePoints > 0) && (gameMode == 1) || (turnCount >= maxTurns)){
            gameOver = true;
            if(redPoints > bluePoints){
                gameStatus.setText("RED WON");
            }
            else if(bluePoints > redPoints){
                gameStatus.setText("BLUE WON");
            }
            else{
                gameStatus.setText("Game ends in a TIE");
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
                        if(playerInput == "S") {
                            buttons[i][j].setLabel("S");
                            buttons[i][j].setForeground(Color.BLACK);
                            //values[i][j] = 1;
                        } else {
                            buttons[i][j].setLabel("O");
                            buttons[i][j].setForeground(Color.BLACK);
                            //values[i][j] = -1;
                        }
                        turnCount += 1;
                        // check for SOS
                        int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                        if(addPoints != 0) {
                            if(turn == 1){
                                redPoints += addPoints;
                                redScore.setText("Red:" + redPoints);
                                blueScore.setText("Blue:" + bluePoints);
                            }
                            else{
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
                            currentTurn.setText("TURN: RED");
                        }
                        else{
                            currentTurn.setText("TURN: BLUE"); 
                        
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
                                //values[i][j] = 1;
                            } else {
                                buttons[i][j].setLabel("O");
                                buttons[i][j].setForeground(Color.BLACK);
                                //values[i][j] = -1;
                            }
                            // check for SOS
                            int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                            turnCount += 1;
                            if(addPoints != 0){
                                bluePoints += addPoints;
                                blueScore.setText("Blue:" + bluePoints);
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
                int[] coords = aiBoxDecider(i, j, turn, gridSize);
                buttons[coords[0]][coords[1]].setForeground(Color.BLACK);
                //i j = 0 issue
                int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
                if(addPoints != 0) {
                    redPoints += addPoints;
                    redScore.setText("Red:" + redPoints);
                    // if SOS then give current player an extra turn
                    //turn = turn;
                } else {
                    // otherwise it's the other player's turn
                        turn = -turn;
                    }
                }
                turnCount += 1;
            if(turn == 1){
                currentTurn.setText("TURN: AI");
            }
            else{
                currentTurn.setText("TURN: P1"); 
            }
        }
        else if(playerMode == 2 && gameMode != -1 && gameOver == false){
            int i = 0;
            int j = 0;
            int[] coords = aiBoxDecider(i, j, turn, gridSize);
            buttons[coords[0]][coords[1]].setForeground(Color.BLACK);
            int addPoints = checkForSOS(coords[0], coords[1], turn, bluePoints, redPoints);
            if(addPoints != 0) {
                if(turn == 1){
                    redPoints += addPoints;
                    redScore.setText("Red:" + redPoints);
                    // if SOS then give current player an extra turn
                    //turn = turn;
                }
                else{
                    bluePoints += addPoints;
                    blueScore.setText("Blue:" + bluePoints);
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
            turnCount += 1;
        }

    }

    public void showBoard(){
        String s1 = boardSize.getText(); 
        System.out.print(s1);
        gridSize = Integer.parseInt(s1);
        System.out.print(gridSize);
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                buttons[i][j].setVisible(true);
                //System.out.print(buttons[i][j].getForeground());
                //System.out.print(buttons[i][j].getActionCommand());
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
        int upperbound = 9;
          //generate random values from 1-10
        int int_random = rand.nextInt(upperbound); 
        return int_random + 1;
    }

    public void changeColor(int coordsX, int coordsY, int turn, int bluePoints, int redPoints, Boolean addPoints){
        if(turn == 1){
            buttons[coordsX][coordsY].setForeground(Color.RED);
            if(addPoints == true ){
                redPoints++;
            }
        }
        else{
            buttons[coordsX][coordsY].setForeground(Color.BLUE);
            if(addPoints == true){
                bluePoints++;
            }
        }
    }
    public void newGame(){
        // reset game
        gameStatus.setText("Game is LIVE");
        twoPlayers.setVisible(true);
        onePlayer.setVisible(true);
        noPlayers.setVisible(true);
        boardSize.setVisible(true);
        boardSizeEnter.setVisible(true);
        generalMode.setVisible(true);
        simpleMode.setVisible(true);
        redPoints = 0;
        bluePoints = 0;
        redScore.setText("Red:" + redPoints);
        blueScore.setText("Blue:" + bluePoints);
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
