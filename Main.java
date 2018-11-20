package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import board.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Main extends JFrame implements MouseListener {
    private static final int Height = 640;
    private static final int Width = 1210;
    private Player White = null, Black = null;
    private JPanel board = new JPanel(new GridLayout(7, 9));
    private JPanel wdetails = new JPanel(new GridLayout(3, 3));
    private JPanel bdetails = new JPanel(new GridLayout(3, 3));
    private JPanel wcombopanel = new JPanel();
    private JPanel bcombopanel = new JPanel();
    private JPanel controlPanel, WhitePlayer, BlackPlayer, temp, displayTime, showPlayer, time;
    private JTextField command = new JTextField(15);
    private JSplitPane split;
    private JLabel label, mov;
    private static JLabel CHNC;
    private Time timer;
    public static Main Mainboard;
    private boolean selected = false, end = false;
    private Container content = null;
    private ArrayList<Player> wplayer, bplayer;
    private ArrayList<String> Wnames = new ArrayList<String>();
    private ArrayList<String> Bnames = new ArrayList<String>();
    private JComboBox<String> wcombo, bcombo;
    private String wname = null, bname = null, winner = null;
    static String move;
    private Player tempPlayer;
    private JScrollPane wscroll, bscroll;
    private String[] WNames = {}, BNames = {};
    private JSlider timeSlider;
    private BufferedImage image;
    private Cell c, previous;
    private Button start, wselect, bselect, WNewPlayer, BNewPlayer, Continue, Quit,start2;
    public static int timeRemaining = 60;
    private int chance=0;
    public int commandmode=0;
    private JTextField COMMANDINPUT;
    GameBoard b;
    private ArrayList<Cell> destinationlist = new ArrayList<Cell>(100);

    public static void main(String[] args) {
        Mainboard = new Main();
        Mainboard.setVisible(true);
        Mainboard.setResizable(false);
    }

    private Main() {
        b = new GameBoard();
        chance = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                board.add(b.boardstate[i][j]);
                b.boardstate[i][j].addMouseListener(this);
            }
        }
        COMMANDINPUT = new JTextField(15);
        COMMANDINPUT.addActionListener(new s());

        timeRemaining = 60;
        timeSlider = new JSlider();
        move = "White";
        wname = null;
        bname = null;
        winner = null;
        bcombopanel = new JPanel();
        wcombopanel = new JPanel();
        board.setMinimumSize(new Dimension(900, 600));
        //fetch the player information;
        wplayer = Player.fetch_players();
        Iterator<Player> witr = wplayer.iterator();
        while (witr.hasNext())
            Wnames.add(witr.next().name());

        bplayer = Player.fetch_players();
        Iterator<Player> bitr = bplayer.iterator();
        while (bitr.hasNext())
            Bnames.add(bitr.next().name());
        WNames = Wnames.toArray(WNames);
        BNames = Bnames.toArray(BNames);
        //绘制开头的表格
        board.setBorder(BorderFactory.createLoweredBevelBorder());
        content = getContentPane();
        setSize(Width, Height);
        setTitle("JUNGLE GAME");
        content.setBackground(Color.black);
        controlPanel = new JPanel();
        content.setLayout(new BorderLayout());
        controlPanel.setLayout(new GridLayout(3, 3));
        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "INFORMATION", TitledBorder.TOP, TitledBorder.CENTER, new Font("times new roman", Font.PLAIN, 20), Color.black));
        //绘制下面的用户
        WhitePlayer = new JPanel();
        WhitePlayer.setBorder(BorderFactory.createTitledBorder(null, "White Player", TitledBorder.TOP, TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.RED));
        WhitePlayer.setLayout(new BorderLayout());

        BlackPlayer = new JPanel();
        BlackPlayer.setBorder(BorderFactory.createTitledBorder(null, "Black Player", TitledBorder.TOP, TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.BLUE));
        BlackPlayer.setLayout(new BorderLayout());

        // 页面跳转
        JPanel whitestats = new JPanel(new GridLayout(3, 3));
        JPanel blackstats = new JPanel(new GridLayout(3, 3));
        wcombo = new JComboBox<>(WNames);
        bcombo = new JComboBox<>(BNames);
        wscroll = new JScrollPane(wcombo);
        bscroll = new JScrollPane(bcombo);
        wcombopanel.setLayout(new FlowLayout());
        bcombopanel.setLayout(new FlowLayout());
        wselect = new Button("Select");
        bselect = new Button("Select");
        wselect.addActionListener(new SelectHandler(0));
        bselect.addActionListener(new SelectHandler(1));
        WNewPlayer = new Button("New Player");
        BNewPlayer = new Button("New Player");
        WNewPlayer.addActionListener(new Handler(0));
        BNewPlayer.addActionListener(new Handler(1));
        wcombopanel.add(wscroll);
        wcombopanel.add(wselect);
        wcombopanel.add(WNewPlayer);
        bcombopanel.add(bscroll);
        bcombopanel.add(bselect);
        bcombopanel.add(BNewPlayer);
        WhitePlayer.add(wcombopanel, BorderLayout.NORTH);
        BlackPlayer.add(bcombopanel, BorderLayout.NORTH);
        whitestats.add(new JLabel("Name   :"));
        whitestats.add(new JLabel("Played :"));
        whitestats.add(new JLabel("Won    :"));
        blackstats.add(new JLabel("Name   :"));
        blackstats.add(new JLabel("Played :"));
        blackstats.add(new JLabel("Won    :"));
        WhitePlayer.add(whitestats, BorderLayout.WEST);
        BlackPlayer.add(blackstats, BorderLayout.WEST);
        controlPanel.add(WhitePlayer);
        controlPanel.add(BlackPlayer);

        showPlayer = new JPanel(new FlowLayout());
        showPlayer.add(timeSlider);
        JLabel setTime = new JLabel("Set Timer: you must move in limited minutes:");
        start = new Button("Start");
        start.setBackground(Color.black);
        start.setForeground(Color.white);
        start.addActionListener(new START());
        start2 = new Button("COMMAND MODE ");
        start2.setBackground(Color.black);
        start2.setForeground(Color.white);
        start2.addActionListener(new COMMAND1());
        //start.setPreferredSize(new Dimension(120, 40));
        Continue = new Button("continue");
        Continue.setBackground(Color.black);
        Continue.setForeground(Color.white);
        Continue.addActionListener(new CONTINUE());
        //Continue.setPreferredSize(new Dimension(120, 40));
        Quit = new Button("Quit");
        Quit.setBackground(Color.black);
        Quit.setForeground(Color.white);
        Quit.addActionListener(new QUIT());
        // Quit.setPreferredSize(new Dimension(120, 40));

        setTime.setFont(new Font("Arial", Font.BOLD, 16));
        label = new JLabel("Time Starts now", JLabel.CENTER);
        label.setFont(new Font("SERIF", Font.BOLD, 30));
        displayTime = new JPanel(new FlowLayout());
        time = new JPanel(new GridLayout(4, 2));
        time.add(setTime);
        time.add(showPlayer);
        displayTime.add(start);
        displayTime.add(Continue);
        displayTime.add(Quit);
        displayTime.add(start2);
        time.add(displayTime);
        controlPanel.add(time);
        board.setMinimumSize(new Dimension(800, 700));
        timeSlider.setMinimum(1);
        timeSlider.setMaximum(15);
        timeSlider.setValue(1);
        timeSlider.setMajorTickSpacing(2);
        timeSlider.setPaintLabels(true);
        timeSlider.setPaintTicks(true);
        timeSlider.addChangeListener(new TimeChange());

        //The Left Layout When Game is inactive
        temp = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                try {
                    image = ImageIO.read(this.getClass().getResource("clash.jpg"));

                } catch (IOException ex) {
                    System.out.println("not found");
                }

                g.drawImage(image, 0, 0, null);
            }
        };

        temp.setMinimumSize(new Dimension(800, 700));
        controlPanel.setMinimumSize(new Dimension(285, 700));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);

        content.add(split);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    class QUIT implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            int[][] save = new int[7][9];

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    save[i][j] = 100;//random
                    if (b.boardstate[i][j].getPiece()!= null) {

                        save[i][j] = b.boardstate[i][j].getPiece().index;
                    }
                        else
                            save[i][j]=-1;

                }

            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 7; i++)//for each row
            {
                for (int j = 0; j < 9; j++)//for each column
                {
                    builder.append(save[i][j] + "");//append to the output string
                    if (j < 8)//if this is not the last row element
                        builder.append(",");//then add comma (if you don't like commas you can use spaces)
                }
                builder.append("\n");//append new line at the end of the row
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"))) {
                writer.write(builder.toString());//save the string representation of the board//
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);

        }
    }

    class CONTINUE implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0)  {
            String savedGameFile = "save.txt";
            int[][] save = new int[7][9];
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(savedGameFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line = "";
            int row = 0;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "
                    int col = 0;
                    for (String c : cols) {
                        save[row][col] = Integer.parseInt(c);
                        col++;
                    }
                    row++;
                }
            } catch (java.io.IOException e) {
                return;
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            GameBoard b2 = new GameBoard(save);
            for(int i=0;i<7;i++)
            {
                for(int j=0;j<9;j++)
                {
                    if(b.boardstate[i][j].getPiece()!=null)
                        b.boardstate[i][j].removePiece();
                    if(b2.boardstate[i][j].getPiece()!=null) {
                        b.boardstate[i][j].setPiece(b2.boardstate[i][j].getPiece());
                    }
                }
            }


            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    board.add(b.boardstate[i][j]);

                }
            }
            if (White == null || Black == null) {
                JOptionPane.showMessageDialog(controlPanel, "PLEASE INPUT THE USER");
                return;
            }
            White.updateGamesPlayed();
            White.Update_Player();
            Black.updateGamesPlayed();
            Black.Update_Player();
            WNewPlayer.disable();
            BNewPlayer.disable();
            wselect.disable();
            bselect.disable();
            displayTime.remove(Continue);


            split.remove(temp);
            split.add(board);

            showPlayer.remove(timeSlider);
            mov = new JLabel("Move:");
            mov.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            mov.setForeground(Color.red);
            showPlayer.add(command);
            CHNC = new JLabel(move);
            CHNC.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            CHNC.setForeground(Color.blue);
            showPlayer.add(CHNC);
            displayTime.remove(start);
            displayTime.add(label);
            displayTime.add(command);
            timer = new Time(label);
            timer.start();

        }

    }
    class COMMAND1 implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            commandmode = 1;
        }
    }

    class START implements ActionListener {

        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (White == null || Black == null) {
                JOptionPane.showMessageDialog(controlPanel, "PLEASE INPUT THE USER");
                return;
            }
            White.updateGamesPlayed();
            White.Update_Player();
            Black.updateGamesPlayed();
            Black.Update_Player();
            WNewPlayer.disable();
            BNewPlayer.disable();
            wselect.disable();
            bselect.disable();
            displayTime.remove(Continue);


            split.remove(temp);
            split.add(board);

            showPlayer.remove(timeSlider);
            mov = new JLabel("Move:");
            mov.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            mov.setForeground(Color.red);
            CHNC = new JLabel(move);
            CHNC.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            CHNC.setForeground(Color.blue);
            showPlayer.add(CHNC);
            displayTime.remove(start);
            displayTime.remove(start2);
            displayTime.add(label);
            timer = new Time(label);
            timer.start();
            if (commandmode == 1) {
                displayTime.add(COMMANDINPUT);

            }
        }

    }
class s implements  ActionListener {
    public void actionPerformed(ActionEvent e) {

        String inputCmd=COMMANDINPUT.getText();
       String[] input = inputCmd.split(" ");
        inputCmd = input[0];

        if (inputCmd.equalsIgnoreCase("MOVE"))
        {
                    String[] position= new String[2];
                    position[0]=input[1];
                    position[1]=input[2];
                    if (position[0].charAt(0) <= 'G' && position[0].charAt(0) >= 'A' && position[1].charAt(0) <= 'G' && position[1].charAt(0) >= 'A' && position[0].charAt(1) <= '9' && position[0].charAt(1) >= '1' && position[1].charAt(1) <= '9' && position[0].charAt(0) >= '1') {
                        int oldx = position[0].charAt(0) - 65;
                        int oldy = position[0].charAt(1) - 49;
                        int newx = position[1].charAt(0) - 65;
                        int newy = position[1].charAt(1) - 49;
                        destinationlist.clear();
                        if(b.boardstate[oldx][oldy].getPiece() != null) {
                            destinationlist = b.boardstate[oldx][oldy].move(b);
                            highlightdestinations(destinationlist);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(board,"invaild please enter again");
                        }
                        if (b.boardstate[newx][newy].ispossibledestination()&&b.boardstate[oldx][oldy].getPiece().side==chance) {

                            if (b.boardstate[newx][newy].getPiece() != null) {
                                b.boardstate[newx][newy].removePiece();
                            }
                            b.boardstate[newx][newy].setPiece(b.boardstate[oldx][oldy].getPiece());
                            b.boardstate[oldx][oldy].removePiece();
                            board.setVisible(false);
                            board.setVisible(true);
                            cleandestinations(destinationlist);
                            destinationlist.clear();
                            if (!haswon(b.boardstate[newx][newy])) {
                                timer.reset();
                                timer.start();
                            } else
                                gameend();
                            changechance();
                        }
                        else {
                            JOptionPane.showMessageDialog(board, "invaild !please input again");
                            cleandestinations(destinationlist);
                        }
                    }
                }


                if(inputCmd.equalsIgnoreCase("SAVE")){
                    int[][] save = new int[7][9];

                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            save[i][j] = 100;//random
                            if (b.boardstate[i][j].getPiece() != null) {

                                save[i][j] = b.boardstate[i][j].getPiece().index;
                            } else
                                save[i][j] = -1;

                        }

                    }
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 7; i++)//for each row
                    {
                        for (int j = 0; j < 9; j++)//for each column
                        {
                            builder.append(save[i][j] + "");//append to the output string
                            if (j < 8)//if this is not the last row element
                                builder.append(",");//then add comma (if you don't like commas you can use spaces)
                        }
                        builder.append("\n");//append new line at the end of the row
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"))) {
                        writer.write(builder.toString());//save the string representation of the board//
                    } catch (IOException f) {
                        f.printStackTrace();
                    }
                    System.exit(0);

                }

            }
    }


    public void changechance() {
        previous = null;
        if (chance == 0)
            chance = 1;
        else
            chance = 0;
        if (!end && timer != null) {
            timer.reset();
            timer.start();
            showPlayer.remove(CHNC);
            if (Main.move == "White")
                Main.move = "Black";
            else
                Main.move = "White";
            CHNC.setText(Main.move);
            showPlayer.add(CHNC);
        }
    }

    private void highlightdestinations(ArrayList<Cell> destlist) {
        ListIterator<Cell> it = destlist.listIterator();
        while (it.hasNext())
            it.next().setpossibledestination();
    }

    private void cleandestinations(ArrayList<Cell> destlist)      //Function to clear the last move's destinations
    {
        ListIterator<Cell> it = destlist.listIterator();
        while (it.hasNext())
            it.next().removepossibledestination();
    }



    @Override
    public void mouseClicked(MouseEvent arg0) {
        c = (Cell) arg0.getSource();
        if (previous == null) { //select the one that want to move
            if (c.getPiece() != null) { // if choose right;

                if (c.getPiece().side != chance) //choose opponent's piece
                    return;
                c.select();
                previous = c;
                destinationlist.clear();
                destinationlist = c.move(b);
                highlightdestinations(destinationlist);

            }
        } else //need to move now
        {
            if (c.x == previous.x && c.y == previous.y)//if choose the same then deselect
            {
                c.deselect();
                previous = null;
                cleandestinations(destinationlist);
                destinationlist.clear();
            } else if (c.getPiece() == null || c.getPiece().side != previous.getPiece().side) {
                if (c.ispossibledestination()) {
                    if (c.getPiece() != null) {
                        c.removePiece();
                    }

                    c.setPiece(previous.getPiece());


                    previous.deselect();
                    previous.removePiece();


                    cleandestinations(destinationlist);
                    destinationlist.clear();
                    if (!haswon(c)) {
                        timer.reset();
                        timer.start();
                    }
                    else
                        gameend();
                    changechance();
                } else {
                    JOptionPane.showMessageDialog(board, "invaild !!! ");
                    return;
                }
            } else if (c.getPiece() != null && c.getPiece().side == previous.getPiece().side)
                return;
        }
    }


    //Other Irrelevant abstract function. Only the Click Event is captured.
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    class SelectHandler implements ActionListener {
        private int color;

        SelectHandler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            tempPlayer = null;
            String n = (color == 0) ? wname : bname;
            JComboBox<String> jc = (color == 0) ? wcombo : bcombo;
            JComboBox<String> ojc = (color == 0) ? bcombo : wcombo;
            ArrayList<Player> pl = (color == 0) ? wplayer : bplayer;
            ArrayList<Player> otherPlayer = (color == 0) ? bplayer : wplayer;
            ArrayList<Player> opl = Player.fetch_players();
            if (opl.isEmpty())
                return;
            JPanel det = (color == 0) ? wdetails : bdetails;
            JPanel PL = (color == 0) ? WhitePlayer : BlackPlayer;
            if (selected == true)
                det.removeAll();
            n = (String) jc.getSelectedItem();
            Iterator<Player> it = pl.iterator();
            Iterator<Player> oit = opl.iterator();
            while (it.hasNext()) {
                Player p = it.next();
                if (p.name().equals(n)) {
                    tempPlayer = p;
                    break;
                }
            }
            while (oit.hasNext()) {
                Player p = oit.next();
                if (p.name().equals(n)) {
                    opl.remove(p);
                    break;
                }
            }

            if (tempPlayer == null)
                return;
            if (color == 0)
                White = tempPlayer;
            else
                Black = tempPlayer;
            bplayer = opl;
            ojc.removeAllItems();
            for (Player s : opl)
                ojc.addItem(s.name());
            det.add(new JLabel(" " + tempPlayer.name()));
            det.add(new JLabel(" " + tempPlayer.gamesplayed()));
            det.add(new JLabel(" " + tempPlayer.gameswon()));

            PL.revalidate();
            PL.repaint();
            PL.add(det);
            selected = true;
        }

    }

    class Handler implements ActionListener {
        private int color;

        Handler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String n;
            JPanel j = (color == 0) ? WhitePlayer : BlackPlayer;
            ArrayList<Player> N = Player.fetch_players();
            Iterator<Player> it = N.iterator();
            JPanel det = (color == 0) ? wdetails : bdetails;
            n = JOptionPane.showInputDialog(j, "Enter your name");

            if (n != null) {

                while (it.hasNext()) {
                    if (it.next().name().equals(n)) {
                        JOptionPane.showMessageDialog(j, "Player exists");
                        return;
                    }
                }

                if (n.length() != 0) {
                    Player tem = new Player(n);
                    tem.Update_Player();
                    if (color == 0)
                        White = tem;
                    else
                        Black = tem;
                } else return;
            } else
                return;
            det.removeAll();
            det.add(new JLabel(" " + n));
            det.add(new JLabel(" 0"));
            det.add(new JLabel(" 0"));
            j.revalidate();
            j.repaint();
            j.add(det);
            selected = true;
        }
    }

    class TimeChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent arg0) {
            timeRemaining = timeSlider.getValue() * 60;
        }
    }

    private void gameend() {
        cleandestinations(destinationlist);
        displayTime.disable();
        timer.countdownTimer.stop();
        if(previous!=null)
            previous.removePiece();
        if(chance==0)
        {	White.updateGamesWon();
            White.Update_Player();
            winner=White.name();
        }
        else
        {
            Black.updateGamesWon();
            Black.Update_Player();
            winner=Black.name();
        }
        JOptionPane.showMessageDialog(board," "+winner+" wins");
        WhitePlayer.remove(wdetails);
        BlackPlayer.remove(bdetails);
        displayTime.remove(label);

        displayTime.add(start);
        showPlayer.remove(mov);
        showPlayer.remove(CHNC);
        showPlayer.revalidate();
        showPlayer.add(timeSlider);

        split.remove(board);
        split.add(temp);
        WNewPlayer.enable();
        BNewPlayer.enable();
        wselect.enable();
        bselect.enable();
        end=true;
        Mainboard.disable();
        Mainboard.dispose();
        Mainboard = new Main();
        Mainboard.setVisible(true);
        Mainboard.setResizable(false);

    }

    public boolean haswon(Cell c) {

        if (c.getPiece() != null) {
            {
                if (c.getx() == 3 && c.gety() == 0 && chance == 1)
                    return true;
                if (c.getx() == 3 && c.gety() == 8 && chance == 0)
                    return true;
                return false;

            }
        }
        else
            return false;
    }
}




