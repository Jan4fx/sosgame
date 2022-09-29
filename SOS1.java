import java.awt.*;
import java.awt.event.*;
//https://github.com/Jan4fx/sosgame`

//left up diagonal S doesn't match on border


public class SOS1 extends Frame implements ActionListener {
    
    private Label lblTitle;
    private Label redScore;
    private Label currentTurn;
    public static int redPoints = 0;
    private Label blueScore;
    public static int bluePoints = 0;
    private Panel pnlMain;
    private Button[][] buttons;
    private Button btnNewGame;
    private Button btnExit;
    private int turn;
    private int[][] values;
    private Button S;
    private Button O;
    private String playerInput = "S";
    
    public SOS1() {
        super("SOS");
        setLayout(new BorderLayout());
        lblTitle = new Label("SOS", Label.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        currentTurn = new Label("TURN: RED", Label.LEFT);
        currentTurn.setFont(new Font("Serif", Font.BOLD, 24));
        redScore = new Label("Red Pts: " + redPoints, Label.CENTER);
        redScore.setFont(new Font("Serif", Font.BOLD, 24));
        blueScore = new Label("Blue Pts: " + bluePoints, Label.LEFT);
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
        pnlBottom.setLayout(new FlowLayout());
        btnNewGame = new Button("New Game");
        S = new Button("S");
        O = new Button("O");
        pnlBottom.add(btnNewGame);
        pnlBottom.add(S);
        pnlBottom.add(O);
        btnNewGame.addActionListener(this);
        S.addActionListener(this);
        O.addActionListener(this);
        btnExit = new Button("Exit");
        pnlBottom.add(btnExit);
        btnExit.addActionListener(this);
        pnlBottom.add(currentTurn);
        add(pnlBottom, BorderLayout.SOUTH);
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
                    buttons[i][j].setForeground(Color.BLUE);
                    //System.out.print(buttons[i][j].getForeground());
                    System.out.print(buttons[i][j].getActionCommand());
                    if(buttons[i][j].getForeground() == Color.BLUE){
                        System.out.print("TRUE");
                    }
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
        else if(e.getSource() == btnExit) {
            System.exit(0);
        } else {
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(e.getSource() == buttons[i][j] && buttons[i][j].getLabel() == "") {
                        // place an S or an O in the grid for the current player's turn
                        if(playerInput == "S") {
                            buttons[i][j].setLabel("S");
                            buttons[i][j].setForeground(Color.BLACK);
                            values[i][j] = 1;
                        } else {
                            buttons[i][j].setLabel("O");
                            buttons[i][j].setForeground(Color.BLACK);
                            values[i][j] = -1;
                        }
                        // check for SOS
                        int addPoints = checkForSOS(i, j, turn, bluePoints, redPoints);
                        if(addPoints != 0) {
                            if(turn == 1){
                                redPoints += addPoints;
                                redScore.setText("Red Pts: " + redPoints);
                                blueScore.setText("Blue Pts: " + bluePoints);
                            }
                            else{
                                bluePoints += addPoints;
                                blueScore.setText("Blue Pts: " + bluePoints);
                                redScore.setText("Red Pts: " + redPoints);
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
        Boolean sosMatch = false;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
                    sosMatch = true;
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
