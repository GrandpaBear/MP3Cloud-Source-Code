package MP3Cloud;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

//mp3agic created by mpatric, external library usage
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

//javazoom external library usage
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/** @author Jianying Chiang
 *  @version 1
 * 	Date: 2019-04-02
 * 		
 * 		The class Application is the core desktop application for MP3Cloud.
 * 	It handles most of the Swing UI/UX designs and actionListeners. It also
 *  has many different types of methods such as for table manipulations, 
 *  file manipulations, and player capabilities.
 */
public class Application extends JFrame {
	//frame movement variables 
    Dimension dimension;
    int xMouse;
    int yMouse;
    
    //SQLite connection variable
    Database connector;

    //default constructor
    public Application() {
        dimension = Toolkit.getDefaultToolkit().getScreenSize();   
        
        initComponents();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mp3guiimages/logo.jpg")));
        setLocation(dimension.width/2-getSize().width/2, dimension.height/2-getSize().height/2);
        
        connector = new Database(songTable);
        resetTable();
        try {
        	homeStatsAnimate();
        } catch(NullPointerException e) {e.printStackTrace();}
    }
    
    //set table method
    public void set_songTable(JTable A) {songTable=A;}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code.
     */
    @SuppressWarnings("unchecked")  
    private void initComponents() {

        mainWindow = new JPanel();
        homePane = new JPanel();
        factNumSongsPane = new JPanel();
        factNumSongsBar = new JPanel();
        factNumSongsLabel = new JLabel();
        factNumSongs = new JLabel();
        factTopGenrePane = new JPanel();
        factTopGenreBar = new JPanel();
        factTopGenreLabel = new JLabel();
        factTopGenre = new JLabel();
        factTopGenrePercentage = new JLabel();
        factTopGenreLoad = new StatsPanel();
        factLongestSongPane = new JPanel();
        factLongestSongBar = new JPanel();
        factLongestSongLabel = new JLabel();
        factLongestSongTime = new JLabel();
        factLongestSongArtist = new JLabel();
        factLongestSongTitle = new JLabel();
        aboutPanel = new JPanel();
        logoText = new JLabel();
        welcomeText = new JLabel();
        libraryPane = new JPanel();
        libArtPane = new JPanel();
        artCover = new JLabel();
        tableHeader = new JPanel();
        headerRowTab = new JPanel();
        headerRow = new JLabel();
        headerTitleTab = new JPanel();
        headerTitle = new JLabel();
        headerArtistTab = new JPanel();
        headerArtist = new JLabel();
        headerAlbumTab = new JPanel();
        headerAlbum = new JLabel();
        headerLengthTab = new JPanel();
        headerLength = new JLabel();
        scrollTable = new javax.swing.JScrollPane();
        songTable = new JTable() {
        	public String getToolTipText(MouseEvent evt) {
                return cellMouseEntered(evt);
            }
        };
        libGenreLabel = new JLabel();
        libTitleLabel = new JLabel();
        libArtistLabel = new JLabel();
        libAlbumLabel = new JLabel();
        libAlbumArtistLabel = new JLabel();
        libTrackNoLabel = new JLabel();
        libYearLabel = new JLabel();
        libDelButton = new JPanel();
        delIcon = new JLabel();
        libResButton = new JPanel();
        restartIcon = new JLabel();
        libPlayPane = new JPanel();
        playIcon = new JLabel();
        libAftPane = new JPanel();
        nextIcon = new JLabel();
        libPrePane = new JPanel();
        previousIcon = new JLabel();
        libSearchPane = new JPanel();
        libSearchField = new javax.swing.JTextField();
        libRefButton = new JPanel();
        refreshIcon = new JLabel();
        autoPane = new JPanel();
        autoField1Pane = new JPanel();
        autoTitleField = new javax.swing.JTextField();
        autoArtistField = new javax.swing.JTextField();
        titleAutoLabel = new JLabel();
        autoArtistLabel = new JLabel();
        field1AutoBar = new JPanel();
        field1AutoLabel = new JLabel();
        commitButtonAuto = new JPanel();
        commitLabelAuto = new JLabel();
        field2Pane1 = new JPanel();
        field2Bar1 = new JPanel();
        field2Label1 = new JLabel();
        field2AutoUploadPane = new UploadPanel();
        autoField2Icon = new JLabel();
        manualPane = new JPanel();
        field1Pane = new JPanel();
        titleField = new javax.swing.JTextField();
        albumField = new javax.swing.JTextField();
        albumArtistField = new javax.swing.JTextField();
        trackField = new javax.swing.JTextField();
        artistField = new javax.swing.JTextField();
        yearField = new javax.swing.JTextField();
        genreField = new javax.swing.JTextField();
        titleLabel = new JLabel();
        artistLabel = new JLabel();
        albumLabel = new JLabel();
        albumArtistLabel = new JLabel();
        trackLabel = new JLabel();
        yearLabel = new JLabel();
        genreLabel = new JLabel();
        field1Bar = new JPanel();
        field1Label = new JLabel();
        commitButton = new JPanel();
        commitLabel = new JLabel();
        field3Pane = new JPanel();
        field3Bar = new JPanel();
        field3Label = new JLabel();
        field3UploadPane = new UploadPanel();
        field3Icon = new JLabel();
        field2Pane = new JPanel();
        field2Bar = new JPanel();
        field2Label = new JLabel();
        field2UploadPane = new UploadPanel();
        field2Icon = new JLabel();
        sidePane = new JPanel();
        homeBar = new JPanel();
        homeLabel = new JLabel();
        homeTab = new JPanel();
        homePic = new JLabel();
        autoBar = new JPanel();
        autoLabel = new JLabel();
        autoPic = new JLabel();
        autoTab = new JPanel();
        libraryBar = new JPanel();
        libraryLabel = new JLabel();
        libraryPic = new JLabel();
        libraryTab = new JPanel();
        manualBar = new JPanel();
        manualLabel = new JLabel();
        manualPic = new JLabel();
        manualTab = new JPanel();
        nameLabel = new JLabel();
        logoLabel = new JLabel();
        windowBar = new JPanel();
        closeButton = new JLabel();
        minusButton = new JLabel();
        autoCommitResultIcon = new JLabel();
        manualCommitResultIcon = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1750, 1000));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        mainWindow.setBackground(new Color(255, 255, 255));
        mainWindow.setLayout(new java.awt.CardLayout());

        homePane.setBackground(new Color(69, 151, 214));

        factNumSongsPane.setBackground(new Color(47, 103, 138));
        factNumSongsPane.setPreferredSize(new Dimension(410, 410));

        factNumSongsBar.setBackground(new Color(0, 204, 102));

        factNumSongsLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        factNumSongsLabel.setForeground(new Color(255, 255, 255));
        factNumSongsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factNumSongsLabel.setText("# of Songs in Library");

        javax.swing.GroupLayout factNumSongsBarLayout = new javax.swing.GroupLayout(factNumSongsBar);
        factNumSongsBar.setLayout(factNumSongsBarLayout);
        factNumSongsBarLayout.setHorizontalGroup(
            factNumSongsBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factNumSongsBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factNumSongsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        factNumSongsBarLayout.setVerticalGroup(
            factNumSongsBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factNumSongsBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factNumSongsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        factNumSongs.setFont(new java.awt.Font("Segoe UI Semibold", 0, 100)); // NOI18N
        factNumSongs.setForeground(new Color(255, 255, 255));
        factNumSongs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factNumSongs.setText("0");

        javax.swing.GroupLayout factNumSongsPaneLayout = new javax.swing.GroupLayout(factNumSongsPane);
        factNumSongsPane.setLayout(factNumSongsPaneLayout);
        factNumSongsPaneLayout.setHorizontalGroup(
            factNumSongsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(factNumSongsBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(factNumSongsPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factNumSongs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        factNumSongsPaneLayout.setVerticalGroup(
            factNumSongsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factNumSongsPaneLayout.createSequentialGroup()
                .addComponent(factNumSongsBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(factNumSongs)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        factTopGenrePane.setBackground(new Color(68, 102, 140));
        factTopGenrePane.setPreferredSize(new Dimension(410, 410));

        factTopGenreBar.setBackground(new Color(234, 168, 0));

        factTopGenreLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        factTopGenreLabel.setForeground(new Color(255, 255, 255));
        factTopGenreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factTopGenreLabel.setText("Top Genre in Library");

        javax.swing.GroupLayout factTopGenreBarLayout = new javax.swing.GroupLayout(factTopGenreBar);
        factTopGenreBar.setLayout(factTopGenreBarLayout);
        factTopGenreBarLayout.setHorizontalGroup(
            factTopGenreBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factTopGenreBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factTopGenreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        factTopGenreBarLayout.setVerticalGroup(
            factTopGenreBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factTopGenreBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factTopGenreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        factTopGenre.setBackground(new Color(255, 255, 255));
        factTopGenre.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        factTopGenre.setForeground(new Color(255, 255, 255));
        factTopGenre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factTopGenre.setText("None");

        factTopGenrePercentage.setBackground(new Color(255, 255, 255));
        factTopGenrePercentage.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        factTopGenrePercentage.setForeground(new Color(255, 255, 255));
        factTopGenrePercentage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factTopGenrePercentage.setText("??%");

        factTopGenreLoad.setBackground(new Color(68, 102, 140));

        javax.swing.GroupLayout factTopGenreLoadLayout = new javax.swing.GroupLayout(factTopGenreLoad);
        factTopGenreLoad.setLayout(factTopGenreLoadLayout);
        factTopGenreLoadLayout.setHorizontalGroup(
            factTopGenreLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        factTopGenreLoadLayout.setVerticalGroup(
            factTopGenreLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 145, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout factTopGenrePaneLayout = new javax.swing.GroupLayout(factTopGenrePane);
        factTopGenrePane.setLayout(factTopGenrePaneLayout);
        factTopGenrePaneLayout.setHorizontalGroup(
            factTopGenrePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(factTopGenreBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(factTopGenrePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(factTopGenrePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(factTopGenre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(factTopGenrePercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, factTopGenrePaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(factTopGenreLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        factTopGenrePaneLayout.setVerticalGroup(
                factTopGenrePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(factTopGenrePaneLayout.createSequentialGroup()
                    .addComponent(factTopGenreBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(factTopGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(factTopGenreLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(factTopGenrePercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
         );

        factLongestSongPane.setBackground(new Color(37, 95, 142));
        factLongestSongPane.setPreferredSize(new Dimension(410, 410));

        factLongestSongBar.setBackground(new Color(0, 153, 204));

        factLongestSongLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        factLongestSongLabel.setForeground(new Color(255, 255, 255));
        factLongestSongLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factLongestSongLabel.setText("Longest Song in Library");

        javax.swing.GroupLayout factLongestSongBarLayout = new javax.swing.GroupLayout(factLongestSongBar);
        factLongestSongBar.setLayout(factLongestSongBarLayout);
        factLongestSongBarLayout.setHorizontalGroup(
            factLongestSongBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factLongestSongBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factLongestSongLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        factLongestSongBarLayout.setVerticalGroup(
            factLongestSongBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factLongestSongBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(factLongestSongLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        factLongestSongTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 100)); // NOI18N
        factLongestSongTime.setForeground(new Color(255, 255, 255));
        factLongestSongTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factLongestSongTime.setText("?:??");

        factLongestSongArtist.setBackground(new Color(255, 255, 255));
        factLongestSongArtist.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        factLongestSongArtist.setForeground(new Color(255, 255, 255));
        factLongestSongArtist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factLongestSongArtist.setText("Null Artist");

        factLongestSongTitle.setBackground(new Color(255, 255, 255));
        factLongestSongTitle.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        factLongestSongTitle.setForeground(new Color(255, 255, 255));
        factLongestSongTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        factLongestSongTitle.setText("Null Title");

        javax.swing.GroupLayout factLongestSongPaneLayout = new javax.swing.GroupLayout(factLongestSongPane);
        factLongestSongPane.setLayout(factLongestSongPaneLayout);
        factLongestSongPaneLayout.setHorizontalGroup(
            factLongestSongPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(factLongestSongBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(factLongestSongPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(factLongestSongPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(factLongestSongTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(factLongestSongTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(factLongestSongArtist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        factLongestSongPaneLayout.setVerticalGroup(
            factLongestSongPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factLongestSongPaneLayout.createSequentialGroup()
                .addComponent(factLongestSongBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(factLongestSongTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(factLongestSongTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(factLongestSongArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        aboutPanel.setBackground(new Color(255,255,255,200));

        logoText.setBackground(new Color(0, 0, 0));
        logoText.setFont(new java.awt.Font("Bauhaus 93", 0, 200)); // NOI18N
        logoText.setForeground(new Color(51, 51, 51));
        logoText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoText.setText("mp3Cloud");
        logoText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                logoTextMouseClicked(evt);
            }
        });

        welcomeText.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        welcomeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeText.setText("Welcome to");

        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(welcomeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aboutPanelLayout.setVerticalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aboutPanelLayout.createSequentialGroup()
                .addComponent(welcomeText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoText)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout homePaneLayout = new javax.swing.GroupLayout(homePane);
        homePane.setLayout(homePaneLayout);
        homePaneLayout.setHorizontalGroup(
            homePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePaneLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(homePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(homePaneLayout.createSequentialGroup()
                        .addComponent(factNumSongsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(factLongestSongPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(factTopGenrePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        homePaneLayout.setVerticalGroup(
            homePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePaneLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(homePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(factTopGenrePane, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addComponent(factLongestSongPane, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addComponent(factNumSongsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(aboutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        mainWindow.add(homePane, "card2");

        libraryPane.setBackground(new Color(255, 255, 255));
        libraryPane.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(36, 36, 78)));
        libraryPane.setPreferredSize(new Dimension(1430, 950));
        libraryPane.setLayout(null);

        libArtPane.setMaximumSize(new Dimension(237, 237));
        libArtPane.setMinimumSize(new Dimension(237, 237));
        libArtPane.setPreferredSize(new Dimension(237, 237));

        artCover.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/defaultCover.jpg"))); // NOI18N

        javax.swing.GroupLayout libArtPaneLayout = new javax.swing.GroupLayout(libArtPane);
        libArtPane.setLayout(libArtPaneLayout);
        libArtPaneLayout.setHorizontalGroup(
            libArtPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libArtPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(artCover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        libArtPaneLayout.setVerticalGroup(
            libArtPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(artCover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        libraryPane.add(libArtPane);
        libArtPane.setBounds(50, 50, 400, 400);

        tableHeader.setBackground(new Color(36, 36, 78));
        tableHeader.setLayout(null);

        headerRowTab.setBackground(new Color(36, 36, 78));
        headerRowTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                headerRowTabMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                headerRowTabMouseReleased(evt);
            }
        });

        headerRow.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        headerRow.setForeground(new Color(255, 255, 255));
        headerRow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerRow.setText("#");

        javax.swing.GroupLayout headerRowTabLayout = new javax.swing.GroupLayout(headerRowTab);
        headerRowTab.setLayout(headerRowTabLayout);
        headerRowTabLayout.setHorizontalGroup(
            headerRowTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerRowTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerRowTabLayout.setVerticalGroup(
            headerRowTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerRowTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerRow, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableHeader.add(headerRowTab);
        headerRowTab.setBounds(0, 0, 40, 50);

        headerTitleTab.setBackground(new Color(36, 36, 78));
        headerTitleTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                headerTitleTabMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                headerTitleTabMouseReleased(evt);
            }
        });

        headerTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        headerTitle.setForeground(new Color(255, 255, 255));
        headerTitle.setText("Title");

        javax.swing.GroupLayout headerTitleTabLayout = new javax.swing.GroupLayout(headerTitleTab);
        headerTitleTab.setLayout(headerTitleTabLayout);
        headerTitleTabLayout.setHorizontalGroup(
            headerTitleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerTitleTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
        );
        headerTitleTabLayout.setVerticalGroup(
            headerTitleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerTitleTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableHeader.add(headerTitleTab);
        headerTitleTab.setBounds(40, 0, 370, 50);

        headerArtistTab.setBackground(new Color(36, 36, 78));
        headerArtistTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                headerArtistTabMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                headerArtistTabMouseReleased(evt);
            }
        });

        headerArtist.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        headerArtist.setForeground(new Color(255, 255, 255));
        headerArtist.setText("Artist");

        javax.swing.GroupLayout headerArtistTabLayout = new javax.swing.GroupLayout(headerArtistTab);
        headerArtistTab.setLayout(headerArtistTabLayout);
        headerArtistTabLayout.setHorizontalGroup(
            headerArtistTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerArtistTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerArtist, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
        );
        headerArtistTabLayout.setVerticalGroup(
            headerArtistTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerArtistTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableHeader.add(headerArtistTab);
        headerArtistTab.setBounds(410, 0, 360, 50);

        headerAlbumTab.setBackground(new Color(36, 36, 78));
        headerAlbumTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                headerAlbumTabMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                headerAlbumTabMouseReleased(evt);
            }
        });

        headerAlbum.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        headerAlbum.setForeground(new Color(255, 255, 255));
        headerAlbum.setText("Album");

        javax.swing.GroupLayout headerAlbumTabLayout = new javax.swing.GroupLayout(headerAlbumTab);
        headerAlbumTab.setLayout(headerAlbumTabLayout);
        headerAlbumTabLayout.setHorizontalGroup(
            headerAlbumTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerAlbumTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerAlbum, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
        );
        headerAlbumTabLayout.setVerticalGroup(
            headerAlbumTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerAlbumTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableHeader.add(headerAlbumTab);
        headerAlbumTab.setBounds(770, 0, 370, 50);

        headerLengthTab.setBackground(new Color(36, 36, 78));
        headerLengthTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                headerLengthTabMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                headerLengthTabMouseReleased(evt);
            }
        });

        headerLength.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        headerLength.setForeground(new Color(255, 255, 255));
        headerLength.setText("Length");

        javax.swing.GroupLayout headerLengthTabLayout = new javax.swing.GroupLayout(headerLengthTab);
        headerLengthTab.setLayout(headerLengthTabLayout);
        headerLengthTabLayout.setHorizontalGroup(
            headerLengthTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLengthTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLength, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
        );
        headerLengthTabLayout.setVerticalGroup(
            headerLengthTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLengthTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headerLength, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableHeader.add(headerLengthTab);
        headerLengthTab.setBounds(1140, 0, 180, 50);

        libraryPane.add(tableHeader);
        tableHeader.setBounds(50, 490, 1330, 50);

        scrollTable.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        songTable.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        songTable.setModel(new DefaultTableModel(
            null,
            new String [] {
                "#", "Title", "Artist", "Album", "Length"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        songTable.setFillsViewportHeight(true);
        songTable.setFocusable(false);
        songTable.setMaximumSize(new Dimension(2147483647, 460));
        songTable.setMinimumSize(new Dimension(75, 455));
        songTable.setRowHeight(50);
        songTable.setSelectionBackground(new Color(153, 153, 153));
        songTable.setShowVerticalLines(false);
        scrollTable.setViewportView(songTable);
        if (songTable.getColumnModel().getColumnCount() > 0) {
            songTable.getColumnModel().getColumn(0).setResizable(false);
            songTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            songTable.getColumnModel().getColumn(1).setResizable(false);
            songTable.getColumnModel().getColumn(1).setPreferredWidth(350);
            songTable.getColumnModel().getColumn(2).setResizable(false);
            songTable.getColumnModel().getColumn(2).setPreferredWidth(350);
            songTable.getColumnModel().getColumn(3).setResizable(false);
            songTable.getColumnModel().getColumn(3).setPreferredWidth(350);
            songTable.getColumnModel().getColumn(4).setResizable(false);
            songTable.getColumnModel().getColumn(4).setPreferredWidth(145);
        }
        songTable.setShowGrid(false);
        songTable.setShowVerticalLines(false);
        
        songTable.getTableHeader().setPreferredSize(new Dimension(1330,0));
        
        songTable.setRowSelectionAllowed(true);
        songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        songTable.addMouseMotionListener(new MouseAdapter() {
        	@Override
        	public void mouseDragged(MouseEvent evt) {
        		JComponent c = (JComponent) evt.getComponent();
        		Optional.ofNullable(c.getTransferHandler())
        				   .ifPresent(th -> th.exportAsDrag(c,evt,TransferHandler.COPY));
        	}
        });
        
        songTable.addMouseListener(new MouseAdapter() {
        	@Override
    		public void mousePressed(MouseEvent evt) {
                songTableMousePressed(evt);
            }
    	});

        libraryPane.add(scrollTable);
        scrollTable.setBounds(50, 540, 1330, 400);
        scrollTable.setBorder(null);

        libGenreLabel.setBackground(new Color(36, 36, 78));
        libGenreLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libGenreLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libGenreLabel);
        libGenreLabel.setBounds(1030, 140, 340, 60);

        libTitleLabel.setBackground(new Color(36, 36, 78));
        libTitleLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libTitleLabel);
        libTitleLabel.setBounds(650, 60, 340, 60);

        libArtistLabel.setBackground(new Color(36, 36, 78));
        libArtistLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libArtistLabel);
        libArtistLabel.setBounds(650, 140, 340, 60);

        libAlbumLabel.setBackground(new Color(36, 36, 78));
        libAlbumLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libAlbumLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libAlbumLabel);
        libAlbumLabel.setBounds(650, 220, 340, 60);

        libAlbumArtistLabel.setBackground(new Color(36, 36, 78));
        libAlbumArtistLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libAlbumArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libAlbumArtistLabel);
        libAlbumArtistLabel.setBounds(650, 300, 340, 60);

        libTrackNoLabel.setBackground(new Color(36, 36, 78));
        libTrackNoLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libTrackNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libTrackNoLabel);
        libTrackNoLabel.setBounds(650, 380, 340, 60);

        libYearLabel.setBackground(new Color(36, 36, 78));
        libYearLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        libYearLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPane.add(libYearLabel);
        libYearLabel.setBounds(1030, 60, 340, 60);

        libDelButton.setBackground(new Color(204, 0, 51));
        libDelButton.setForeground(new Color(255, 255, 255));
        libDelButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libDelButtonMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libDelButtonMouseReleased(evt);
            }
        });

        delIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/delete.jpg"))); // NOI18N

        javax.swing.GroupLayout libDelButtonLayout = new javax.swing.GroupLayout(libDelButton);
        libDelButton.setLayout(libDelButtonLayout);
        libDelButtonLayout.setHorizontalGroup(
            libDelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libDelButtonLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(delIcon)
                .addContainerGap())
        );
        libDelButtonLayout.setVerticalGroup(
            libDelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libDelButtonLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(delIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        libraryPane.add(libDelButton);
        libDelButton.setBounds(930, 430, 60, 60);

        libResButton.setBackground(new Color(36, 36, 78));
        libResButton.setForeground(new Color(255, 255, 255));
        libResButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libResButtonMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libResButtonMouseReleased(evt);
            }
        });

        restartIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/restart.jpg"))); // NOI18N

        javax.swing.GroupLayout libResButtonLayout = new javax.swing.GroupLayout(libResButton);
        libResButton.setLayout(libResButtonLayout);
        libResButtonLayout.setHorizontalGroup(
            libResButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libResButtonLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(restartIcon)
                .addGap(15, 15, 15))
        );
        libResButtonLayout.setVerticalGroup(
            libResButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libResButtonLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(restartIcon)
                .addGap(16, 16, 16))
        );

        libraryPane.add(libResButton);
        libResButton.setBounds(1320, 430, 60, 60);

        libPlayPane.setBackground(new Color(112, 163, 26));
        libPlayPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libPlayPaneMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libPlayPaneMouseReleased(evt);
            }
        });

        playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/play.jpg"))); // NOI18N

        javax.swing.GroupLayout libPlayPaneLayout = new javax.swing.GroupLayout(libPlayPane);
        libPlayPane.setLayout(libPlayPaneLayout);
        libPlayPaneLayout.setHorizontalGroup(
            libPlayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libPlayPaneLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(playIcon)
                .addGap(29, 29, 29))
        );
        libPlayPaneLayout.setVerticalGroup(
            libPlayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libPlayPaneLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(playIcon)
                .addContainerGap())
        );

        libraryPane.add(libPlayPane);
        libPlayPane.setBounds(450, 210, 90, 90);

        libAftPane.setBackground(new Color(0, 187, 229));
        libAftPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libAftPaneMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libAftPaneMouseReleased(evt);
            }
        });

        nextIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/next.jpg"))); // NOI18N

        javax.swing.GroupLayout libAftPaneLayout = new javax.swing.GroupLayout(libAftPane);
        libAftPane.setLayout(libAftPaneLayout);
        libAftPaneLayout.setHorizontalGroup(
            libAftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libAftPaneLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(nextIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        libAftPaneLayout.setVerticalGroup(
            libAftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libAftPaneLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(nextIcon)
                .addContainerGap())
        );

        libraryPane.add(libAftPane);
        libAftPane.setBounds(450, 300, 70, 70);

        libPrePane.setBackground(new Color(0, 187, 229));
        libPrePane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libPrePaneMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libPrePaneMouseReleased(evt);
            }
        });

        previousIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/previous.jpg"))); // NOI18N

        javax.swing.GroupLayout libPrePaneLayout = new javax.swing.GroupLayout(libPrePane);
        libPrePane.setLayout(libPrePaneLayout);
        libPrePaneLayout.setHorizontalGroup(
            libPrePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libPrePaneLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(previousIcon)
                .addGap(13, 13, 13))
        );
        libPrePaneLayout.setVerticalGroup(
            libPrePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libPrePaneLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(previousIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        libraryPane.add(libPrePane);
        libPrePane.setBounds(450, 140, 70, 70);

        libSearchPane.setBackground(new Color(255, 255, 255));
        libSearchPane.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0), 3));
        libSearchPane.setForeground(new Color(255, 255, 255));

        libSearchField.setFont(new java.awt.Font("Arial", 0, 22)); // NOI18N
        libSearchField.setForeground(new Color(153, 153, 153));
        libSearchField.setText("Search");
        libSearchField.setBorder(null);
        libSearchField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                libSearchFieldMouseClicked(evt);
            }
        });
        libSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                libSearchFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout libSearchPaneLayout = new javax.swing.GroupLayout(libSearchPane);
        libSearchPane.setLayout(libSearchPaneLayout);
        libSearchPaneLayout.setHorizontalGroup(
            libSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(libSearchField, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );
        libSearchPaneLayout.setVerticalGroup(
            libSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(libSearchField, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        libraryPane.add(libSearchPane);
        libSearchPane.setBounds(1050, 430, 270, 60);

        libRefButton.setBackground(new Color(36, 36, 78));
        libRefButton.setForeground(new Color(255, 255, 255));
        libRefButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libRefButtonMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                libRefButtonMouseReleased(evt);
            }
        });

        refreshIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/refresh.jpg"))); // NOI18N

        javax.swing.GroupLayout libRefButtonLayout = new javax.swing.GroupLayout(libRefButton);
        libRefButton.setLayout(libRefButtonLayout);
        libRefButtonLayout.setHorizontalGroup(
            libRefButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(libRefButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refreshIcon)
                .addGap(15, 15, 15))
        );
        libRefButtonLayout.setVerticalGroup(
            libRefButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libRefButtonLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(refreshIcon)
                .addGap(16, 16, 16))
        );

        libraryPane.add(libRefButton);
        libRefButton.setBounds(990, 430, 60, 60);

        mainWindow.add(libraryPane, "card2");

        autoPane.setBackground(new Color(229, 242, 255));
        autoPane.setPreferredSize(new Dimension(1430, 950));

        autoField1Pane.setBackground(new Color(255, 255, 255));

        autoTitleField.setBackground(new Color(229, 242, 255));
        autoTitleField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        autoTitleField.setBorder(new LineBorder(new Color(229, 242, 255), 12, true));
        autoTitleField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        autoTitleField.setMinimumSize(new Dimension(14, 34));

        autoArtistField.setBackground(new Color(229, 242, 255));
        autoArtistField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        autoArtistField.setBorder(new LineBorder(new Color(229, 242, 255), 12, true));
        autoArtistField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        titleAutoLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        titleAutoLabel.setText("TITLE");

        autoArtistLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        autoArtistLabel.setText("ARTIST");

        field1AutoBar.setBackground(new Color(0, 187, 229));

        javax.swing.GroupLayout field1AutoBarLayout = new javax.swing.GroupLayout(field1AutoBar);
        field1AutoBar.setLayout(field1AutoBarLayout);
        field1AutoBarLayout.setHorizontalGroup(
            field1AutoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        field1AutoBarLayout.setVerticalGroup(
            field1AutoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        field1AutoLabel.setBackground(new Color(255, 255, 255));
        field1AutoLabel.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        field1AutoLabel.setForeground(new Color(0, 187, 229));
        field1AutoLabel.setText("FIELD 1:");

        javax.swing.GroupLayout autoField1PaneLayout = new javax.swing.GroupLayout(autoField1Pane);
        autoField1Pane.setLayout(autoField1PaneLayout);
        autoField1PaneLayout.setHorizontalGroup(
            autoField1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(field1AutoBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(autoField1PaneLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(autoField1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(field1AutoLabel)
                    .addComponent(autoArtistLabel)
                    .addComponent(titleAutoLabel)
                    .addComponent(autoTitleField, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(autoArtistField))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        autoField1PaneLayout.setVerticalGroup(
            autoField1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(autoField1PaneLayout.createSequentialGroup()
                .addComponent(field1AutoBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(field1AutoLabel)
                .addGap(45, 45, 45)
                .addComponent(titleAutoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(autoArtistLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoArtistField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        commitButtonAuto.setBackground(new Color(0, 187, 229));
        commitButtonAuto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        commitButtonAuto.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                commitButtonAutoMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                commitButtonAutoMouseReleased(evt);
            }
        });

        commitLabelAuto.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        commitLabelAuto.setForeground(new Color(255, 255, 255));
        commitLabelAuto.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/commit.jpg"))); // NOI18N
        commitLabelAuto.setText("  Commit Tags");

        javax.swing.GroupLayout commitButtonAutoLayout = new javax.swing.GroupLayout(commitButtonAuto);
        commitButtonAuto.setLayout(commitButtonAutoLayout);
        commitButtonAutoLayout.setHorizontalGroup(
            commitButtonAutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commitButtonAutoLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(commitLabelAuto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        commitButtonAutoLayout.setVerticalGroup(
            commitButtonAutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(commitLabelAuto, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        field2Pane1.setBackground(new Color(255, 255, 255));

        field2Bar1.setBackground(new Color(0, 187, 229));

        javax.swing.GroupLayout field2Bar1Layout = new javax.swing.GroupLayout(field2Bar1);
        field2Bar1.setLayout(field2Bar1Layout);
        field2Bar1Layout.setHorizontalGroup(
            field2Bar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        field2Bar1Layout.setVerticalGroup(
            field2Bar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        field2Label1.setBackground(new Color(255, 255, 255));
        field2Label1.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        field2Label1.setForeground(new Color(0, 187, 229));
        field2Label1.setText("FIELD 2:");

        field2AutoUploadPane.setBackground(new Color(255, 255, 255));
        field2AutoUploadPane.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                field2AutoUploadPaneMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                field2AutoUploadPaneMouseExited(evt);
            }
        });
        
        new  FileDrop( field2AutoUploadPane, new FileDrop.Listener()
        {   public void  filesDropped( File[] files )
            {   
                field2AutoUploadPaneFileDropped(files[0]);
            }   // end filesDropped
        }); // end FileDrop.Listener
         

        javax.swing.GroupLayout field2AutoUploadPaneLayout = new javax.swing.GroupLayout(field2AutoUploadPane);
        field2AutoUploadPane.setLayout(field2AutoUploadPaneLayout);
        field2AutoUploadPaneLayout.setHorizontalGroup(
            field2AutoUploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, field2AutoUploadPaneLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(autoField2Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        field2AutoUploadPaneLayout.setVerticalGroup(
            field2AutoUploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field2AutoUploadPaneLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(autoField2Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        javax.swing.GroupLayout field2Pane1Layout = new javax.swing.GroupLayout(field2Pane1);
        field2Pane1.setLayout(field2Pane1Layout);
        field2Pane1Layout.setHorizontalGroup(
            field2Pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(field2Bar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(field2Pane1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(field2Label1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, field2Pane1Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(field2AutoUploadPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
        );
        field2Pane1Layout.setVerticalGroup(
            field2Pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field2Pane1Layout.createSequentialGroup()
                .addComponent(field2Bar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(field2Label1)
                .addGap(18, 18, 18)
                .addComponent(field2AutoUploadPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        
        autoCommitResultIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        autoCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/close.jpg"))); // NOI18N
        autoCommitResultIcon.setPreferredSize(new Dimension(76, 76));

        javax.swing.GroupLayout autoPaneLayout = new javax.swing.GroupLayout(autoPane);
        autoPane.setLayout(autoPaneLayout);
        autoPaneLayout.setHorizontalGroup(
            autoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(autoPaneLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(autoField1Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(field2Pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(autoPaneLayout.createSequentialGroup()
                    .addGap(186, 186, 186)
                    .addComponent(commitButtonAuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(autoCommitResultIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            autoPaneLayout.setVerticalGroup(
                autoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(autoPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addGroup(autoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(autoField1Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(field2Pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 374, Short.MAX_VALUE)
                    .addGroup(autoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(commitButtonAuto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(autoCommitResultIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(50, 50, 50))
            );

        mainWindow.add(autoPane, "card2");

        manualPane.setBackground(new Color(232, 240, 250));

        field1Pane.setBackground(new Color(255, 255, 255));

        titleField.setBackground(new Color(232, 240, 250));
        titleField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titleField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        titleField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        titleField.setMinimumSize(new Dimension(14, 34));

        albumField.setBackground(new Color(232, 240, 250));
        albumField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        albumField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        albumField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        albumArtistField.setBackground(new Color(232, 240, 250));
        albumArtistField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        albumArtistField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        albumArtistField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        trackField.setBackground(new Color(232, 240, 250));
        trackField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        trackField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        trackField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        artistField.setBackground(new Color(232, 240, 250));
        artistField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        artistField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        artistField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        yearField.setBackground(new Color(232, 240, 250));
        yearField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        yearField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        yearField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        genreField.setBackground(new Color(232, 240, 250));
        genreField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        genreField.setBorder(new LineBorder(new Color(232, 240, 250), 12, true));
        genreField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        titleLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        titleLabel.setText("TITLE");

        artistLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        artistLabel.setText("ARTIST");

        albumLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        albumLabel.setText("ALBUM");

        albumArtistLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        albumArtistLabel.setText("ALBUM ARTIST");

        trackLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        trackLabel.setText("TRACK NUMBER");

        yearLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        yearLabel.setText("CREATED YEAR");

        genreLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        genreLabel.setText("GENRE");

        field1Bar.setBackground(new Color(0, 187, 229));

        javax.swing.GroupLayout field1BarLayout = new javax.swing.GroupLayout(field1Bar);
        field1Bar.setLayout(field1BarLayout);
        field1BarLayout.setHorizontalGroup(
            field1BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        field1BarLayout.setVerticalGroup(
            field1BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        field1Label.setBackground(new Color(255, 255, 255));
        field1Label.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        field1Label.setForeground(new Color(0, 187, 229));
        field1Label.setText("FIELD 1:");

        javax.swing.GroupLayout field1PaneLayout = new javax.swing.GroupLayout(field1Pane);
        field1Pane.setLayout(field1PaneLayout);
        field1PaneLayout.setHorizontalGroup(
            field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(field1Bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(field1PaneLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(artistField)
                    .addComponent(albumField)
                    .addComponent(albumArtistField)
                    .addComponent(field1Label)
                    .addComponent(albumArtistLabel)
                    .addComponent(albumLabel)
                    .addComponent(artistLabel)
                    .addComponent(titleLabel)
                    .addGroup(field1PaneLayout.createSequentialGroup()
                        .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(trackLabel)
                            .addComponent(trackField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genreField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genreLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        field1PaneLayout.setVerticalGroup(
            field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field1PaneLayout.createSequentialGroup()
                .addComponent(field1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(field1Label)
                .addGap(45, 45, 45)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(artistLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artistField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(albumLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(albumField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(albumArtistLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(albumArtistField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trackLabel)
                    .addComponent(yearLabel)
                    .addComponent(genreLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(field1PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trackField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(genreField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        commitButton.setBackground(new Color(0, 187, 229));
        commitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        commitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                commitButtonMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                commitButtonMouseReleased(evt);
            }
        });

        commitLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        commitLabel.setForeground(new Color(255, 255, 255));
        commitLabel.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/commit.jpg"))); // NOI18N
        commitLabel.setText("  Commit Tags");

        javax.swing.GroupLayout commitButtonLayout = new javax.swing.GroupLayout(commitButton);
        commitButton.setLayout(commitButtonLayout);
        commitButtonLayout.setHorizontalGroup(
            commitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commitButtonLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(commitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        commitButtonLayout.setVerticalGroup(
            commitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(commitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        field3Pane.setBackground(new Color(255, 255, 255));

        field3Bar.setBackground(new Color(0, 187, 229));

        javax.swing.GroupLayout field3BarLayout = new javax.swing.GroupLayout(field3Bar);
        field3Bar.setLayout(field3BarLayout);
        field3BarLayout.setHorizontalGroup(
            field3BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        field3BarLayout.setVerticalGroup(
            field3BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        field3Label.setBackground(new Color(255, 255, 255));
        field3Label.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        field3Label.setForeground(new Color(0, 187, 229));
        field3Label.setText("FIELD 3:");

        field3UploadPane.setBackground(new Color(255, 255, 255));
        field3UploadPane.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                field3UploadPaneMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                field3UploadPaneMouseExited(evt);
            }
        });
        
        new  FileDrop( field3UploadPane, new FileDrop.Listener()
        {   public void  filesDropped( File[] files )
            { 
                field3UploadPaneFileDropped(files[0]);
                
            }   // end filesDropped
        }); // end FileDrop.Listener

        javax.swing.GroupLayout field3UploadPaneLayout = new javax.swing.GroupLayout(field3UploadPane);
        field3UploadPane.setLayout(field3UploadPaneLayout);
        field3UploadPaneLayout.setHorizontalGroup(
            field3UploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, field3UploadPaneLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(field3Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        field3UploadPaneLayout.setVerticalGroup(
            field3UploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field3UploadPaneLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(field3Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        javax.swing.GroupLayout field3PaneLayout = new javax.swing.GroupLayout(field3Pane);
        field3Pane.setLayout(field3PaneLayout);
        field3PaneLayout.setHorizontalGroup(
            field3PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(field3Bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(field3PaneLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(field3Label)
                .addContainerGap(503, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, field3PaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(field3UploadPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
        );
        field3PaneLayout.setVerticalGroup(
            field3PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field3PaneLayout.createSequentialGroup()
                .addComponent(field3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(field3Label)
                .addGap(16, 16, 16)
                .addComponent(field3UploadPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        field2Pane.setBackground(new Color(255, 255, 255));
        field2Pane.setPreferredSize(new Dimension(640, 400));

        field2Bar.setBackground(new Color(0, 187, 229));

        javax.swing.GroupLayout field2BarLayout = new javax.swing.GroupLayout(field2Bar);
        field2Bar.setLayout(field2BarLayout);
        field2BarLayout.setHorizontalGroup(
            field2BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        field2BarLayout.setVerticalGroup(
            field2BarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        field2Label.setBackground(new Color(255, 255, 255));
        field2Label.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        field2Label.setForeground(new Color(0, 187, 229));
        field2Label.setText("FIELD 2: ");

        field2UploadPane.setBackground(new Color(255, 255, 255));
        field2UploadPane.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                field2UploadPaneMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                field2UploadPaneMouseExited(evt);
            }
        });
        
        new  FileDrop( field2UploadPane, new FileDrop.Listener()
        {   public void  filesDropped( File[] files )
            { 
                field2UploadPaneFileDropped(files[0]);
                
            }   // end filesDropped
        }); // end FileDrop.Listener

        javax.swing.GroupLayout field2UploadPaneLayout = new javax.swing.GroupLayout(field2UploadPane);
        field2UploadPane.setLayout(field2UploadPaneLayout);
        field2UploadPaneLayout.setHorizontalGroup(
            field2UploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, field2UploadPaneLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(field2Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        field2UploadPaneLayout.setVerticalGroup(
            field2UploadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field2UploadPaneLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(field2Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        javax.swing.GroupLayout field2PaneLayout = new javax.swing.GroupLayout(field2Pane);
        field2Pane.setLayout(field2PaneLayout);
        field2PaneLayout.setHorizontalGroup(
            field2PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(field2Bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(field2PaneLayout.createSequentialGroup()
                .addGroup(field2PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(field2PaneLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(field2Label))
                    .addGroup(field2PaneLayout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(field2UploadPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        field2PaneLayout.setVerticalGroup(
            field2PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(field2PaneLayout.createSequentialGroup()
                .addComponent(field2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(field2Label)
                .addGap(18, 18, 18)
                .addComponent(field2UploadPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        
        manualCommitResultIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manualCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/close.jpg"))); // NOI18N
        manualCommitResultIcon.setPreferredSize(new Dimension(76, 76));

        javax.swing.GroupLayout manualPaneLayout = new javax.swing.GroupLayout(manualPane);
        manualPane.setLayout(manualPaneLayout);
        manualPaneLayout.setHorizontalGroup(
            manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manualPaneLayout.createSequentialGroup()
                .addGroup(manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manualPaneLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(field1Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manualPaneLayout.createSequentialGroup()
                            .addGap(186, 186, 186)
                            .addComponent(commitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(manualCommitResultIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(50, 50, 50)
                    .addGroup(manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(field2Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(field3Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(50, 50, 50))
            );
            manualPaneLayout.setVerticalGroup(
                manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(manualPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addGroup(manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(manualPaneLayout.createSequentialGroup()
                            .addComponent(field2Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                            .addComponent(field3Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(manualPaneLayout.createSequentialGroup()
                            .addComponent(field1Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(manualPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(commitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(manualCommitResultIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGap(50, 50, 50))
            );

        mainWindow.add(manualPane, "card2");

        getContentPane().add(mainWindow);
        mainWindow.setBounds(320, 50, 1430, 950);

        sidePane.setBackground(new Color(36, 36, 78));
        sidePane.setForeground(new Color(229, 229, 229));
        sidePane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sidePane.setLayout(null);

        homeBar.setBackground(new Color(0, 187, 229));
        homeBar.setForeground(new Color(240, 240, 240));
        homeBar.setFocusCycleRoot(true);
        homeBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                homeBarMousePressed(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                homeBarMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                homeBarMouseExited(evt);
            }
        });

        homeLabel.setBackground(new Color(0, 0, 0));
        homeLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        homeLabel.setForeground(new Color(240, 240, 240));
        homeLabel.setText("Home");

        homeTab.setBackground(new Color(255, 255, 255));

        javax.swing.GroupLayout homeTabLayout = new javax.swing.GroupLayout(homeTab);
        homeTab.setLayout(homeTabLayout);
        homeTabLayout.setHorizontalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        homeTabLayout.setVerticalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        homePic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/homeON.jpg"))); // NOI18N

        javax.swing.GroupLayout homeBarLayout = new javax.swing.GroupLayout(homeBar);
        homeBar.setLayout(homeBarLayout);
        homeBarLayout.setHorizontalGroup(
            homeBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeBarLayout.createSequentialGroup()
                .addComponent(homeTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(homePic)
                .addGap(42, 42, 42)
                .addComponent(homeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        homeBarLayout.setVerticalGroup(
            homeBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeBarLayout.createSequentialGroup()
                .addGroup(homeBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(homeTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homeBarLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(homeBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(homePic)
                            .addComponent(homeLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidePane.add(homeBar);
        homeBar.setBounds(0, 210, 320, 70);

        autoBar.setBackground(new Color(36, 36, 78));
        autoBar.setForeground(new Color(229, 229, 229));
        autoBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                autoBarMousePressed(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                autoBarMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                autoBarMouseExited(evt);
            }
        });

        autoLabel.setBackground(new Color(0, 0, 0));
        autoLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        autoLabel.setForeground(new Color(240, 240, 240));
        autoLabel.setText("Auto Tag");

        autoPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/autoOFF.jpg"))); // NOI18N

        autoTab.setBackground(new Color(36, 36, 78));

        javax.swing.GroupLayout autoTabLayout = new javax.swing.GroupLayout(autoTab);
        autoTab.setLayout(autoTabLayout);
        autoTabLayout.setHorizontalGroup(
            autoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        autoTabLayout.setVerticalGroup(
            autoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout autoBarLayout = new javax.swing.GroupLayout(autoBar);
        autoBar.setLayout(autoBarLayout);
        autoBarLayout.setHorizontalGroup(
            autoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, autoBarLayout.createSequentialGroup()
                .addComponent(autoTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(autoPic)
                .addGap(40, 40, 40)
                .addComponent(autoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        autoBarLayout.setVerticalGroup(
            autoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, autoBarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(autoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(autoLabel)
                    .addComponent(autoPic))
                .addGap(22, 22, 22))
            .addComponent(autoTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        sidePane.add(autoBar);
        autoBar.setBounds(0, 350, 320, 70);

        libraryBar.setBackground(new Color(36, 36, 78));
        libraryBar.setForeground(new Color(229, 229, 229));
        libraryBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                libraryBarMousePressed(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                libraryBarMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                libraryBarMouseExited(evt);
            }
        });

        libraryLabel.setBackground(new Color(0, 0, 0));
        libraryLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        libraryLabel.setForeground(new Color(240, 240, 240));
        libraryLabel.setText("Collection");

        libraryPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/libraryOFF.jpg"))); // NOI18N

        libraryTab.setBackground(new Color(36, 36, 78));

        javax.swing.GroupLayout libraryTabLayout = new javax.swing.GroupLayout(libraryTab);
        libraryTab.setLayout(libraryTabLayout);
        libraryTabLayout.setHorizontalGroup(
            libraryTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        libraryTabLayout.setVerticalGroup(
            libraryTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout libraryBarLayout = new javax.swing.GroupLayout(libraryBar);
        libraryBar.setLayout(libraryBarLayout);
        libraryBarLayout.setHorizontalGroup(
            libraryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libraryBarLayout.createSequentialGroup()
                .addComponent(libraryTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(libraryPic)
                .addGap(41, 41, 41)
                .addComponent(libraryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        libraryBarLayout.setVerticalGroup(
            libraryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, libraryBarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(libraryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(libraryLabel)
                    .addComponent(libraryPic))
                .addGap(22, 22, 22))
            .addComponent(libraryTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        sidePane.add(libraryBar);
        libraryBar.setBounds(0, 280, 320, 70);

        manualBar.setBackground(new Color(36, 36, 78));
        manualBar.setForeground(new Color(229, 229, 229));
        manualBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                manualBarMousePressed(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                manualBarMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                manualBarMouseExited(evt);
            }
        });

        manualLabel.setBackground(new Color(0, 0, 0));
        manualLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        manualLabel.setForeground(new Color(240, 240, 240));
        manualLabel.setText("Manual Tag");

        manualPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/manualOFF.jpg"))); // NOI18N

        manualTab.setBackground(new Color(36, 36, 78));

        javax.swing.GroupLayout manualTabLayout = new javax.swing.GroupLayout(manualTab);
        manualTab.setLayout(manualTabLayout);
        manualTabLayout.setHorizontalGroup(
            manualTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        manualTabLayout.setVerticalGroup(
            manualTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout manualBarLayout = new javax.swing.GroupLayout(manualBar);
        manualBar.setLayout(manualBarLayout);
        manualBarLayout.setHorizontalGroup(
            manualBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manualBarLayout.createSequentialGroup()
                .addComponent(manualTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(manualPic)
                .addGap(38, 38, 38)
                .addComponent(manualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        manualBarLayout.setVerticalGroup(
            manualBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manualBarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(manualBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(manualLabel)
                    .addComponent(manualPic))
                .addGap(22, 22, 22))
            .addComponent(manualTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        sidePane.add(manualBar);
        manualBar.setBounds(0, 420, 320, 70);

        nameLabel.setFont(new java.awt.Font("Bauhaus 93", 0, 36)); // NOI18N
        nameLabel.setForeground(new Color(240, 240, 240));
        nameLabel.setText("mp3Cloud");
        nameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                nameLabelMouseClicked(evt);
            }
        });
        sidePane.add(nameLabel);
        nameLabel.setBounds(80, 140, 170, 70);

        logoLabel.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/logo.jpg"))); // NOI18N
        logoLabel.setText("jLabel1");
        logoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                logoLabelMouseClicked(evt);
            }
        });
        sidePane.add(logoLabel);
        logoLabel.setBounds(100, 50, 120, 120);

        getContentPane().add(sidePane);
        sidePane.setBounds(0, 0, 320, 1000);

        windowBar.setBackground(new Color(36, 36, 78));
        windowBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                windowBarMouseDragged(evt);
            }
        });
        windowBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                windowBarMousePressed(evt);
            }
        });

        closeButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeButton.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/close.jpg"))); // NOI18N
        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        minusButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minusButton.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/minus.jpg"))); // NOI18N
        minusButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                minusButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout windowBarLayout = new javax.swing.GroupLayout(windowBar);
        windowBar.setLayout(windowBarLayout);
        windowBarLayout.setHorizontalGroup(
            windowBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, windowBarLayout.createSequentialGroup()
                .addContainerGap(1678, Short.MAX_VALUE)
                .addComponent(minusButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addGap(15, 15, 15))
        );
        windowBarLayout.setVerticalGroup(
            windowBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(windowBarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(windowBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minusButton)
                    .addComponent(closeButton))
                .addGap(17, 17, 17))
        );

        getContentPane().add(windowBar);
        windowBar.setBounds(0, 0, 1750, 50);

        pack();
    } //end of swing ui design application creation                
    
    //swing ui actionListener methods
    private void homeBarMousePressed(MouseEvent evt) {                                     
        defaultPanes();
        homePane.setVisible(true);
        homeBar.setBackground(new Color(0,187,229));
        homeTab.setBackground(new Color(240,240,240));
        homePic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/homeON.jpg")));
        try {
        	homeStatsAnimate();
        } catch(NullPointerException e) {e.printStackTrace();}
        onHome = true;
    }                                    

    private void autoBarMousePressed(MouseEvent evt) {                                     
        defaultPanes();
        resetFields();
        autoPane.setVisible(true);
        autoBar.setBackground(new Color(0,187,229));
        autoTab.setBackground(new Color(240,240,240));
        autoPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/autoON.jpg")));
        field2AutoUploadPane.setCommand("restart");
        field2AutoUploadPane.animate();
        autoField2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
        autoCommitResultIcon.setToolTipText(null);
        autoCommitResultIcon.setIcon(null);
        onAuto = true;
    }                                    

    private void libraryBarMousePressed(MouseEvent evt) {                                        
        defaultPanes();
        refreshTable();
        selectPlayingRow();
        libraryPane.setVisible(true);
        libraryBar.setBackground(new Color(0,187,229));
        libraryTab.setBackground(new Color(240,240,240));
        libraryPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/libraryON.jpg")));
        onLibrary = true;
    }                                       

    private void homeBarMouseEntered(MouseEvent evt) {                                     
        if (!onHome) {
            homeBar.setBackground(new Color(0,165,207));
            homeTab.setBackground(new Color(0,165,207));
            homePic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/homeON.jpg")));
        }
    }                                    

    private void homeBarMouseExited(MouseEvent evt) {                                    
        if (!onHome) {
            homeBar.setBackground(new Color(36,36,78));
            homeTab.setBackground(new Color(36,36,78));
            homePic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/homeOFF.jpg")));
        }
    }                                   

    private void autoBarMouseEntered(MouseEvent evt) {                                     
           if (!onAuto) {
            autoBar.setBackground(new Color(0,165,207));
            autoTab.setBackground(new Color(0,165,207));
            autoPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/autoON.jpg")));
        }
    }                                    

    private void autoBarMouseExited(MouseEvent evt) {                                    
        if (!onAuto) {
            autoBar.setBackground(new Color(36,36,78));
            autoTab.setBackground(new Color(36,36,78));
            autoPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/autoOFF.jpg")));
        }
    }                                   

    private void libraryBarMouseEntered(MouseEvent evt) {                                        
        if (!onLibrary) {
            libraryBar.setBackground(new Color(0,165,207));
            libraryTab.setBackground(new Color(0,165,207));
            libraryPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/libraryON.jpg")));
        }
    }                                       

    private void libraryBarMouseExited(MouseEvent evt) {                                       
        if (!onLibrary) {
            libraryBar.setBackground(new Color(36,36,78));
            libraryTab.setBackground(new Color(36,36,78));
            libraryPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/libraryOFF.jpg")));
        }
    }                                      

    private void manualBarMousePressed(MouseEvent evt) {                                       
        defaultPanes();
        resetFields();
        manualPane.setVisible(true);
        manualBar.setBackground(new Color(0,187,229));
        manualTab.setBackground(new Color(240,240,240));
        manualPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/manualON.jpg")));
        field2UploadPane.setCommand("restart");
        field2UploadPane.animate();
        field2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
        field3UploadPane.setCommand("restart");
        field3UploadPane.animate();
        field3Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
        manualCommitResultIcon.setToolTipText(null);
        manualCommitResultIcon.setIcon(null);
        onManual = true;
    }                                      

    private void manualBarMouseEntered(MouseEvent evt) {                                       
        if (!onManual) {
            manualBar.setBackground(new Color(0,165,207));
            manualTab.setBackground(new Color(0,165,207));
            manualPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/manualON.jpg")));
        }
    }                                      

    private void manualBarMouseExited(MouseEvent evt) {                                      
        if (!onManual) {
            manualBar.setBackground(new Color(36,36,78));
            manualTab.setBackground(new Color(36,36,78));
            manualPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/manualOFF.jpg")));
        }
    }                                     

    private void closeButtonMouseClicked(MouseEvent evt) {                                         
        System.exit(0);
    }                                        

    private void minusButtonMouseClicked(MouseEvent evt) {                                         
        setState(ICONIFIED);
    }                                        
    
    private void windowBarMousePressed(MouseEvent evt) {                                       
        xMouse = evt.getX();
        yMouse = evt.getY();
    }                                      

    private void windowBarMouseDragged(MouseEvent evt) {                                       
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        setLocation(x - xMouse, y - yMouse);
    }                                      

    private void libSearchFieldMouseClicked(MouseEvent evt) {                                            
        if (libSearchField.getText().equals("Search")) {
            libSearchField.setForeground(new Color(0,0,0));
            libSearchField.setText("");
        }
    }                                           

    private void libSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {
    	String typed = libSearchField.getText();
    	if (typed.equals("")) {
    		try {
            	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs");
            } catch(ClassNotFoundException e) {
            	e.printStackTrace();
            } catch(SQLException e) {
            	e.printStackTrace();
            }
    	}
    	else {
	        try {
	        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs WHERE ("
	        									+ "title LIKE '%" + typed + "%' OR " + "artist LIKE '%" + typed + "%' OR " 
	        									+ "album LIKE '%" + typed + "%')");
	        } catch(ClassNotFoundException e) {
	        	e.printStackTrace();
	        } catch(SQLException e) {
	        	e.printStackTrace();
	        }
    	}
    	selectPlayingRow();
    }                                       

    private void libDelButtonMousePressed(MouseEvent evt) { 
    	System.gc();
        libDelButton.setBackground(new Color(153,0,51));
    }                                         

    private void libDelButtonMouseReleased(MouseEvent evt) {                                           
        libDelButton.setBackground(new Color(204,0,51));
        String field="";
        String command="";
        File f=null;
        file=null;
        if (player!=null) {
			player.close();
			player=null;
			try {
				if (fis!=null) {
					fis.close();
				}
				if (bis!=null) {
					bis.close();
				}
				if (mp3!=null) {
					mp3=null;
				}
			} catch (IOException e) {e.printStackTrace();}
        }
        try {
	        Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
			Statement stat = conn.createStatement();
	    	if (row>=0 && col>=0) {
	    		field = songTable.getValueAt(row, 1).toString();
	    		command = "DELETE FROM songs WHERE title='"+field+"'";
	    	}
			stat.executeUpdate(command);
			f = new File(System.getProperty("user.dir") + "//songs//"+ field+".mp3");
			f.delete();
			conn.close();
        } catch(ClassNotFoundException | SQLException e) {e.printStackTrace();}
        artCover.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/defaultCover.jpg")));
        libTitleLabel.setText(null);
		libArtistLabel.setText(null);
		libAlbumLabel.setText(null);
		libAlbumArtistLabel.setText(null);
		libTrackNoLabel.setText(null);
		libYearLabel.setText(null);
		libGenreLabel.setText(null);
		refreshTable();
    }                                          

    private void libRefButtonMousePressed(MouseEvent evt) {                                          
        libRefButton.setBackground(new Color(20,20,62));
    }                                         

    private void libRefButtonMouseReleased(MouseEvent evt) {                                           
        libRefButton.setBackground(new Color(36,36,78));
        refreshTable();
    }                                          

    private void libResButtonMousePressed(MouseEvent evt) {                                          
        libResButton.setBackground(new Color(20,20,62));
    }                                         

    private void libResButtonMouseReleased(MouseEvent evt) {                                           
        libResButton.setBackground(new Color(36,36,78));
        resetTable();
    }                                          

    private void libPrePaneMousePressed(MouseEvent evt) {                                        
        libPrePane.setBackground(new Color(0,165,207));
    }                                       

    private void libPrePaneMouseReleased(MouseEvent evt) {                                         
        libPrePane.setBackground(new Color(0,187,229));
        playPrevious();
    }                                        

    private void libPlayPaneMouseReleased(MouseEvent evt) {     	
        if (playing==true) {
            libPlayPane.setBackground(new Color(112,163,26));
            playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/play.jpg")));
            if (player!=null) {
				new Thread() {
					public void run() {
						try {
							pause = fis.available();
						} catch (IOException e) {e.printStackTrace();}
						player.close();
					}
				}.start();
            }

            
            playing = false;
        }
        else {
            libPlayPane.setBackground(new Color(215,125,137));
            playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/pause.jpg")));
            try {
				fis = new FileInputStream(mp3);
    			bis = new BufferedInputStream(fis);
    			player = new Player(bis);

            	if (pause!=0) {
            		fis.skip(totalLength-pause-20000);
            	}
            	else {
            		totalLength = fis.available();
            	}

				new Thread() {
					public void run() {
						try {
							player.play();
						} catch(JavaLayerException e) {e.printStackTrace();}
					}
				}.start();
			} catch (JavaLayerException | IOException e) {
				e.printStackTrace();
			}
            playing = true;
        }
    }                                         

    private void libPlayPaneMousePressed(MouseEvent evt) {                                         
        if (playing==true) {
            libPlayPane.setBackground(new Color(148,82,96));
        }
        else {
            libPlayPane.setBackground(new Color(86,125,19));
        }
    }                                        

    private void libAftPaneMousePressed(MouseEvent evt) {                                        
        libAftPane.setBackground(new Color(0,165,207));
    }                                       

    private void libAftPaneMouseReleased(MouseEvent evt) {                                         
        libAftPane.setBackground(new Color(0,187,229));
        playNext();
    }           
    
    private String cellMouseEntered(MouseEvent evt) {
    	String tip = null;
    	Point cursorPoint = evt.getPoint();
    	int rowNo= songTable.rowAtPoint(cursorPoint);
    	int colNo = songTable.columnAtPoint(cursorPoint);
    	try {
    		tip = songTable.getValueAt(rowNo,colNo).toString();
    	} catch (RuntimeException e) {e.printStackTrace();}
    	return tip;    	
    }

    private void headerRowTabMousePressed(MouseEvent evt) {                                          
        headerRowTab.setBackground(new Color(20,20,62));
    }                                         

    private void headerRowTabMouseReleased(MouseEvent evt) {                                           
        headerRowTab.setBackground(new Color(36,36,78));
        try {
        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs ORDER BY rowid");
        	selectPlayingRow();
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }                                          

    private void headerTitleTabMousePressed(MouseEvent evt) {                                            
        headerTitleTab.setBackground(new Color(20,20,62));
    }                                           

    private void headerTitleTabMouseReleased(MouseEvent evt) {                                             
        headerTitleTab.setBackground(new Color(36,36,78));
        try {
        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs ORDER BY title COLLATE NOCASE");
        	selectPlayingRow();
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }                                            

    private void headerArtistTabMousePressed(MouseEvent evt) {                                             
        headerArtistTab.setBackground(new Color(20,20,62));
    }                                            

    private void headerArtistTabMouseReleased(MouseEvent evt) {                                              
        headerArtistTab.setBackground(new Color(36,36,78));
        try {
        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs ORDER BY artist COLLATE NOCASE");
        	selectPlayingRow();
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }                                             

    private void headerAlbumTabMousePressed(MouseEvent evt) {                                            
        headerAlbumTab.setBackground(new Color(20,20,62));
    }                                           

    private void headerAlbumTabMouseReleased(MouseEvent evt) {                                             
        headerAlbumTab.setBackground(new Color(36,36,78));
        try {
        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs ORDER BY album COLLATE NOCASE");
        	selectPlayingRow();
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }                                            

    private void headerLengthTabMousePressed(MouseEvent evt) {                                             
        headerLengthTab.setBackground(new Color(20,20,62));
    }                                            

    private void headerLengthTabMouseReleased(MouseEvent evt) {                                              
        headerLengthTab.setBackground(new Color(36,36,78));
        try {
        	displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs ORDER BY length");
        	selectPlayingRow();
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }    
    
    private void songTableMousePressed(MouseEvent evt) {
    	String field="";
    	String command="";
		file=null;
    	
    	row = songTable.rowAtPoint(evt.getPoint());
    	col = songTable.columnAtPoint(evt.getPoint());
    	if (row>=0 && col>=0) {
    		field = songTable.getValueAt(row, 1).toString();
    		playingTitle=field;
    		selectedRow=row;
    		command = "SELECT * FROM songs WHERE title='"+field+"'";
    	}
    	displayPlayContent(field, command);
    }

    private void logoTextMouseClicked(MouseEvent evt) {                                      
        openLinks();
    }                                     

    private void logoLabelMouseClicked(MouseEvent evt) {                                       
        openLinks();
    }                                      

    private void nameLabelMouseClicked(MouseEvent evt) {                                       
        openLinks();
    } 
    
    private void field2AutoUploadPaneFileDropped(File file) {  
    	autoField2Icon.setIcon(null);
    	field2AutoDisableAnimation=true;
    	field2AutoUploadPane.setCommand("load");
        try {
        	field2AutoUploadPane.setValidFile(true);
        	field2AutoUploadPane.setTested(true);
    		mp3file = new Mp3File(file);
    	} catch(Exception e) {
    		field2AutoUploadPane.setValidFile(false);
    		e.printStackTrace();}
        field2AutoUploadPane.animate();
        if (field2AutoDisableAnimation==true) {
    		new java.util.Timer().schedule(
    				new java.util.TimerTask() {
    					@Override
    					public void run() {
    						if (field2AutoUploadPane.getValidFile()==true) {
    							autoField2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/success.jpg")));
    						}
    						else {
    							autoField2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/failed.jpg")));
    						}
    						field2AutoDisableAnimation=false;
    						cancel();
    					}},
    				1000    				
    			);
        }
    }      
    
    private void field2UploadPaneFileDropped(File file) { 
    	field2Icon.setIcon(null);
    	field2DisableAnimation=true;
    	field2UploadPane.setCommand("load");
        try {
        	field2UploadPane.setValidFile(true);
        	field2UploadPane.setTested(true);
    		mp3file = new Mp3File(file);
    	} catch(Exception e) {
    		field2UploadPane.setValidFile(false);
    		e.printStackTrace();}
        field2UploadPane.animate();
        if (field2DisableAnimation==true) {
    		new java.util.Timer().schedule(
    				new java.util.TimerTask() {
    					@Override
    					public void run() {
    						if (field2UploadPane.getValidFile()==true) {
    							field2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/success.jpg")));
    						}
    						else {
    							field2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/failed.jpg")));
    						}
    						field2DisableAnimation=false;
    						cancel();
    					}},
    				1000    				
    			);
        }
    } 
    
    private void field3UploadPaneFileDropped(File file) {
    	field3Icon.setIcon(null);
    	field3DisableAnimation=true;
    	field3UploadPane.setCommand("load");
        field3UploadPane.animate();
        try {
        	field3UploadPane.setValidFile(true);
        	field3UploadPane.setTested(true);
        	String extension = "";
        	int i = file.getName().lastIndexOf('.');
        	if (i > 0) {
        	    extension = file.getName().substring(i+1);
        	}
        	if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
        		img = ImageIO.read(file);
        	}
        	else {
        		field3UploadPane.setValidFile(false);
        	}
    	} catch(Exception e) {
    		field3UploadPane.setValidFile(false);
    		e.printStackTrace();}
        field3UploadPane.animate();
        if (field3DisableAnimation==true) {
    		new java.util.Timer().schedule(
    				new java.util.TimerTask() {
    					@Override
    					public void run() {
    						if (field3UploadPane.getValidFile()==true) {
    							field3Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/success.jpg")));
    						}
    						else {
    							field3Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/failed.jpg")));
    						}
    						field3DisableAnimation=false;
    						cancel();
    					}},
    				1000    				
    			);
        }
    } 
    
    private void field2AutoUploadPaneMouseEntered(MouseEvent evt) {
    	if (field2AutoDisableAnimation==false) {
    		field2AutoUploadPane.setCommand("hover");
    		field2AutoUploadPane.animate();
    	}
    }
    
    private void field2UploadPaneMouseEntered(MouseEvent evt) {
    	if (field2DisableAnimation==false) {
    		field2UploadPane.setCommand("hover");
    		field2UploadPane.animate();
    	}
    }  
    
    private void field3UploadPaneMouseEntered(MouseEvent evt) {
    	if (field3DisableAnimation==false) {
    		field3UploadPane.setCommand("hover");
    		field3UploadPane.animate();
    	}
    }  

    private void field2AutoUploadPaneMouseExited(MouseEvent evt) {                                                 
    	if (field2AutoDisableAnimation==false) {
    		field2AutoUploadPane.setCommand("unhover");
    		field2AutoUploadPane.animate();
    	}
    }
    
    private void field2UploadPaneMouseExited(MouseEvent evt) {                                                 
    	if (field2DisableAnimation==false) {
    		field2UploadPane.setCommand("unhover");
    		field2UploadPane.animate();
    	}
    } 
    
    private void field3UploadPaneMouseExited(MouseEvent evt) {                                                 
    	if (field3DisableAnimation==false) {
    		field3UploadPane.setCommand("unhover");
    		field3UploadPane.animate();
    	}
    }                                       

    private void commitButtonAutoMousePressed(MouseEvent evt) {                                              
        commitButtonAuto.setBackground(new Color(20,65,114));
    }                                             

    private void commitButtonAutoMouseReleased(MouseEvent evt) {                                               
        commitButtonAuto.setBackground(new Color(0,187,229));
        Song song = new Song();
        song.set_mp3file(mp3file);
        song.set_title(autoTitleField.getText());
        song.set_artist(autoArtistField.getText());
        song.retrieveSongContent();
        song.retrieveAlbumContent();
        resetFields();
        
        if (song.setTags()) {
        	autoCommitResultIcon.setToolTipText(null);
            autoCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/resultCommitSuccess.jpg")));
        }
        else {
        	autoCommitResultIcon.setToolTipText("Error has occured. Try again.");
            autoCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/resultCommitFailed.jpg")));
        }
        
        song = null;
        
        field2AutoUploadPane.setCommand("restart");
        field2AutoUploadPane.animate();
        field2AutoUploadPane.setTested(false);
        autoField2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
    }                                              

    private void commitButtonMousePressed(MouseEvent evt) {                                          
        commitButton.setBackground(new Color(20,65,114));
    }                                         

    private void commitButtonMouseReleased(MouseEvent evt) {                                           
        commitButton.setBackground(new Color(0,187,229));
        
        Song song = new Song();
        song.set_mp3file(mp3file);
        song.set_image(img);
        try {
	        outputImage = new File("downloaded.jpg");
	        ImageIO.write(img, "jpg", outputImage);
        } catch (Exception e) {e.printStackTrace();}
        song.set_title(titleField.getText());
        song.set_artist(artistField.getText());
        song.set_album(albumField.getText());
        song.set_albumArtist(albumArtistField.getText());
        try {
        	song.set_trackNo(Integer.parseInt(trackField.getText()));
        } catch(NumberFormatException e) {e.printStackTrace();}
        try {
        	song.set_created_year(Integer.parseInt(yearField.getText()));
        } catch(NumberFormatException e) {e.printStackTrace();}
        song.set_genre(genreField.getText());
        
        resetFields();
        if (song.setTags()) {
        	manualCommitResultIcon.setToolTipText(null);
            manualCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/resultCommitSuccess.jpg")));
        }
        else {
        	manualCommitResultIcon.setToolTipText("Error has occured. Try again.");
            manualCommitResultIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/resultCommitFailed.jpg")));
        }
        
        try {
        	outputImage.delete();
        } catch(Exception e) {e.printStackTrace();}
        
        song = null;
        
        field2UploadPane.setCommand("restart");
        field2UploadPane.setTested(false);
        field2UploadPane.animate();
        field2Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
        field3UploadPane.setCommand("restart");
        field3UploadPane.setTested(false);
        field3UploadPane.animate();
        field3Icon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/upload.jpg")));
    }     
    //end of swing ui actionListener methods
    
    //facts animation method
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of homeStatsAnimate method is to animate the stats on homepage.
     * @param: none
	 * @return: none
     * */
    private void homeStatsAnimate() {
    	boolean min=false;
    	it=1;
    	factTopGenreLoad.setPercentageGenre((int)(percentageGenre*1.8));
    	factTopGenreLoad.animate();
    	if (longestTitle.length()>20) {
    		factLongestSongTitle.setText(longestTitle.substring(0,18)+"...");  
    	}
    	else {
    		factLongestSongTitle.setText(longestTitle);
    	}
    	if (longestArtist.length()>20) {
    		factLongestSongArtist.setText(longestArtist.substring(0,18)+"..."); 
    	}
    	else {
    		factLongestSongArtist.setText(longestArtist);
    	}
    	factLongestSongTitle.setToolTipText(longestTitle);
    	factLongestSongArtist.setToolTipText(longestArtist);
    	factTopGenre.setText(topGenre);
    	while (min==false) {
    		if (longestLength.charAt(it)==':') {
    			min=true;			
    		}
    		else {
    			it++;
    		}
    	}
    	
    	final int animationTime = 500;
        int framesPerSecond = 60;
        int delay = 1000 / framesPerSecond;
        final long start = System.currentTimeMillis();
        final Timer timer = new Timer(delay, null);
        Random rand = new Random();
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final long now = System.currentTimeMillis();
                final long elapsed = now - start;

                progress = (float) elapsed / animationTime;

                factNumSongs.setText(String.valueOf((int)(progress*numSongs)));
                factLongestSongTime.setText(String.valueOf((int)(progress*(Double.parseDouble(longestLength.substring(0,it)))))+":"+String.valueOf(rand.nextInt(50)+10));
                factTopGenrePercentage.setText(String.valueOf((int)(progress*percentageGenre))+"%");
                if (elapsed >= animationTime) {
                    timer.stop();
                    factNumSongs.setText(String.valueOf(numSongs));
                    factLongestSongTime.setText(longestLength);
                    factTopGenrePercentage.setText(String.valueOf((int)percentageGenre)+"%");
                }

            }
        });
        timer.start();
    } //------------------------ End of homeStatsAnimate() method ------------------------
    
    //player methods;
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of displayPlayContent method is to display information of
     * the playing title.
     * @param: String, String
	 * @return: none
     * */
    private void displayPlayContent(String field, String command) {
    	ID3v2 tag;
		byte[] imageData;
		BufferedImage img;
		File image;
		File folder = new File(System.getProperty("user.dir") + "//songs");
		Mp3File mp3file=null;
    	try {
	    	Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(command);
			while (rs.next()){
				libTitleLabel.setText(String.valueOf(rs.getObject(1)));
				libArtistLabel.setText(String.valueOf(rs.getObject(2)));
				libAlbumLabel.setText(String.valueOf(rs.getObject(3)));
				libAlbumArtistLabel.setText(String.valueOf(rs.getObject(4)));
				libTrackNoLabel.setText(String.valueOf(rs.getObject(5)));
				libYearLabel.setText(String.valueOf(rs.getObject(6)));
				libGenreLabel.setText(String.valueOf(rs.getObject(7)));
				
				libTitleLabel.setToolTipText(String.valueOf(rs.getObject(1)));
				libArtistLabel.setToolTipText(String.valueOf(rs.getObject(2)));
				libAlbumLabel.setToolTipText(String.valueOf(rs.getObject(3)));
				libAlbumArtistLabel.setToolTipText(String.valueOf(rs.getObject(4)));
			}
			rs.close();
			conn.close();
    	} catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	file = getFile(field);
    	
    	//set current song
    	playingTitle=field;
    	
    	//reset play button and stop playing
    	if (player!=null) {
        	player.close();
        }
    	libPlayPane.setBackground(new Color(112,163,26));
        playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/play.jpg")));
        playing = false;
        
    	mp3 = file;
    	System.out.println(playingTitle);
    	
    	//set up player
    	try {
	    	fis = new FileInputStream(mp3);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
			totalLength = fis.available();
			pause=0;
    	} catch(Exception e) {e.printStackTrace();}
    	
    	//play song
    	MouseEvent evt=null;
    	libPlayPaneMouseReleased(evt);
    	
		try {
			mp3file = new Mp3File(file);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		}
		tag = mp3file.getId3v2Tag();
		if (mp3file.hasId3v2Tag()) {
			  ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			  imageData = id3v2Tag.getAlbumImage();
			  try {
				  if (imageData != null) {
					    img = ImageIO.read(new ByteArrayInputStream(imageData));
					    Image resizedImage = img.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
					    ImageIcon imageIcon = new ImageIcon(resizedImage); 
					    
					    artCover.setIcon(imageIcon);
				  }
			  } catch(IOException e) {e.printStackTrace();}
		}
    } //------------------------ End of displayPlayContent() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of playPrevious method is to play the previous song on the table.
     * @param: none
	 * @return: none
     * */
    private void playPrevious() {
    	if (selectedRow>0) {
    		selectedRow--;
    		songTable.setRowSelectionInterval(0, selectedRow);
    		String title = String.valueOf(((DefaultTableModel) songTable.getModel()).getValueAt(selectedRow,1));
        	String command = "SELECT * FROM songs WHERE title='"+title+"'";
    		displayPlayContent(title, command);
    	} 
    	else {
    		libPlayPane.setBackground(new Color(112,163,26));
            playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/play.jpg")));
            if (player!=null) {
            	player.close();
            }
    	}
    } //------------------------ End of playPrevious() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of playNext method is to play the next song on the table.
     * @param: none
	 * @return: none
     * */
    private void playNext() {
    	selectedRow++;
    	if (selectedRow<((DefaultTableModel) songTable.getModel()).getRowCount()) {
    		songTable.setRowSelectionInterval(0, selectedRow);
    		String title = String.valueOf(((DefaultTableModel) songTable.getModel()).getValueAt(selectedRow,1));
        	String command = "SELECT * FROM songs WHERE title='"+title+"'";
    		displayPlayContent(title, command);
    	}
    	else {
    		libPlayPane.setBackground(new Color(112,163,26));
            playIcon.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/play.jpg")));
            if (player!=null) {
            	player.close();
            }
    	}
    } //------------------------ End of playNext() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of selectPlayingRow method is to highlight/select the playing title on the table.
     * @param: none
	 * @return: none
     * */
    private void selectPlayingRow() {
    	try {
    		int rowNumber=((DefaultTableModel) songTable.getModel()).getRowCount();
    		for (int i=0; i<rowNumber; i++) {
    			if (String.valueOf(((DefaultTableModel) songTable.getModel()).getValueAt(i,1)).equals(playingTitle)) {
    				songTable.setRowSelectionInterval(1, i);
    				selectedRow=i;
    			}
    		}
    	} catch(Exception e) {e.printStackTrace();}
    } //------------------------ End of selectPlayingRow() method ------------------------
    
    //mp3/img usage methods
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of getFile method is to return mp3file based of title name.
     * @param: String
	 * @return: File
     * */
    private File getFile(String field) {
    	File folder = new File(System.getProperty("user.dir") + "//songs");
    	for (File file : folder.listFiles()) {
			if (file.isFile() && (file.getName().endsWith(".mp3")) && file.getName().startsWith(field)) {
				return file;
			}
    	}
    	return null;
    } //------------------------ End of getFile() method ------------------------
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of createImageIcon method is to generate a image that can
     * be displayed when song is played.
     * @param: String, String
	 * @return: ImageIcon
     * */
    protected ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    } //------------------------ End of createImageIcon() method ------------------------
    
    //link to Linkedin and Github methods
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of openLinks method is to open my LinkedIn and Github pages.
     * @param: none
	 * @return: none
     * */
    private void openLinks() {
        try {
            openWebpage(new URI("https://www.github.com/grandpabear/mp3cloud"));
            openWebpage(new URI("https://www.linkedin.com/in/jianying-chiang-863b19151"));
        } catch(Exception e) {e.printStackTrace();}
    } //------------------------ End of openLinks() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of openWebpage methods is to connect to my LinkedIn and Github pages.
     * @param: URI or URL
	 * @return: boolean
     * */
    private boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {}
	    }
	    return false;
	} //------------------------ End of openWebpage() method ------------------------
    
    private boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (Exception e) {}
        return false;
    } //------------------------ End of openWebpage() method ------------------------
    
    //additional table manipulation methods
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of resetTable method is to reset the table by re-importing mp3files from
     * songs folder.
     * @param: none
	 * @return: none
     * */
    private void resetTable() {
    	String command=null;
    	ResultSet rs;
    	try {
    		connector.createTable();
    		connector.truncateTable();
    		connector.populateTable();
    		displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs");
    		Class.forName("org.sqlite.JDBC");
    		Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
    		Statement stat = conn.createStatement();
    		
    		command = "SELECT COUNT(*) FROM songs";
    		rs = stat.executeQuery(command);
    		numSongs=(Integer)rs.getObject(1);

    		command = "SELECT genre, COUNT(*) AS count FROM songs GROUP BY genre ORDER BY count DESC";
    		rs = stat.executeQuery(command);
    		topGenre=String.valueOf(rs.getObject(1));
    		percentageGenre=(Double.parseDouble(String.valueOf(rs.getObject(2))))/ (((DefaultTableModel) songTable.getModel()).getRowCount())*100;
    		
    		command = "SELECT title, artist, length FROM songs ORDER BY length DESC";
    		rs = stat.executeQuery(command);
    		longestTitle=String.valueOf(rs.getObject(1));
    		longestArtist=String.valueOf(rs.getObject(2));
    		longestLength=String.valueOf(rs.getObject(3));
    		
    		rs.close();
    		conn.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    } //------------------------ End of resetTable() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of refreshTable method is to refresh the table based of database mp3cloud.
     * @param: none
	 * @return: none
     * */
    private void refreshTable() {
    	try {
    		displayTable(connector.get_table(), "SELECT rowid, title, artist, album, length FROM songs");
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    } //------------------------ End of refreshTable() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of displayTable method is to display table.
     * @param: JTable, String
	 * @return: none
     * */
    public void displayTable(JTable table, String command) throws ClassNotFoundException, SQLException{
    	((DefaultTableModel) songTable.getModel()).setRowCount(0);
    	Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(command);
		while (rs.next()){
			Object[] row = new Object[5];
			for (int i = 1; i <= 5; i++){
				row[i-1] = rs.getObject(i);
			}
			((DefaultTableModel) songTable.getModel()).insertRow(rs.getRow()-1, row);
		}
		rs.close();
		conn.close();		
	} //------------------------ End of displayTable() method ------------------------
    
    //reset panel methods
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of resetFields method is to reset songs information fields when a new song
     * is selected.
     * @param: none
	 * @return: none
     * */
    private void resetFields() {
    	autoTitleField.setText(null);
    	autoArtistField.setText(null);
    	
    	titleField.setText(null);
    	artistField.setText(null);
    	albumField.setText(null);
    	albumArtistField.setText(null);
    	trackField.setText(null);
    	yearField.setText(null);
    	genreField.setText(null);
    } //------------------------ End of resetFields() method ------------------------
    
    /* Author: Jianying Chiang		Date: 2019-04-02
     * 		The purpose of defaultPanes method is to default panels and set visiblity based of user
     * selection.
     * @param: none
	 * @return: none
     * */
    private void defaultPanes() {
        homePane.setVisible(false);
        manualPane.setVisible(false);
        autoPane.setVisible(false);
        libraryPane.setVisible(false);
        
        homeBar.setBackground(new Color(36,36,78));
        manualBar.setBackground(new Color(36,36,78));
        autoBar.setBackground(new Color(36,36,78));
        libraryBar.setBackground(new Color(36,36,78));
         
        homeTab.setBackground(new Color(36,36,78));
        manualTab.setBackground(new Color(36,36,78));
        autoTab.setBackground(new Color(36,36,78));
        libraryTab.setBackground(new Color(36,36,78));
        
        homePic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/homeOFF.jpg")));
        libraryPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/libraryOFF.jpg")));
        autoPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/autoOFF.jpg")));
        manualPic.setIcon(new ImageIcon(getClass().getResource("/mp3guiimages/manualOFF.jpg")));
               
        libSearchField.setForeground(new Color(153,153,153));
        libSearchField.setText("Search");
        
        onHome = false;
        onLibrary = false;
        onAuto = false;
        onManual = false;
    } //------------------------ End of defaultPanes() method ------------------------
      
    //variable declarations
    
    //ui/ux variables
    private boolean onHome = true;
    private boolean onLibrary = false;
    private boolean onAuto = false;
    private boolean onManual = false;
    private boolean playing = false;
    
    private boolean field2AutoDisableAnimation = false;
    private boolean field2DisableAnimation = false;
    private boolean field3DisableAnimation = false;
    
    //table variables
    private int row;
    private int col;
    
    //mp3/img usage variables
    private BufferedImage img;
    private Mp3File mp3file;
    private File mp3;
    private File outputImage;
    
    //row selection variables
    private String playingTitle;
    private int selectedRow;
    
    //facts animation variables
    private float progress = 0.0f;
    private int it;
    
    //facts variables
    private int numSongs;
    private String longestLength;
    private String longestTitle;
    private String longestArtist;
    private String topGenre;
    private double percentageGenre;
    
    //player variables
    private long totalLength;
    private long pause=0;
    private FileInputStream fis;
    private BufferedInputStream bis;
    private Player player;
    private File file=null;
    
    //swing variable declarations
    private JPanel aboutPanel;
    private javax.swing.JTextField albumArtistField;
    private JLabel albumArtistLabel;
    private javax.swing.JTextField albumField;
    private JLabel albumLabel;
    private JLabel artCover;
    private javax.swing.JTextField artistField;
    private JLabel artistLabel;
    private javax.swing.JTextField autoArtistField;
    private JLabel autoArtistLabel;
    private JPanel autoBar;
    private JLabel autoCommitResultIcon;
    private JPanel autoField1Pane;
    private JLabel autoField2Icon;
    private JLabel autoLabel;
    private JPanel autoPane;
    private JLabel autoPic;
    private JPanel autoTab;
    private javax.swing.JTextField autoTitleField;
    private JLabel closeButton;
    private JPanel commitButton;
    private JPanel commitButtonAuto;
    private JLabel commitLabel;
    private JLabel commitLabelAuto;
    private JLabel delIcon;
    private JLabel factLongestSongArtist;
    private JPanel factLongestSongBar;
    private JLabel factLongestSongLabel;
    private JPanel factLongestSongPane;
    private JLabel factLongestSongTime;
    private JLabel factLongestSongTitle;
    private JLabel factNumSongs;
    private JPanel factNumSongsBar;
    private JLabel factNumSongsLabel;
    private JPanel factNumSongsPane;
    private JLabel factTopGenre;
    private JPanel factTopGenreBar;
    private JLabel factTopGenreLabel;
    private StatsPanel factTopGenreLoad;
    private JPanel factTopGenrePane;
    private JLabel factTopGenrePercentage;
    private JPanel field1AutoBar;
    private JLabel field1AutoLabel;
    private JPanel field1Bar;
    private JLabel field1Label;
    private JPanel field1Pane;
    private UploadPanel field2AutoUploadPane;
    private JPanel field2Bar;
    private JPanel field2Bar1;
    private JLabel field2Icon;
    private JLabel field2Label;
    private JLabel field2Label1;
    private JPanel field2Pane;
    private JPanel field2Pane1;
    private UploadPanel field2UploadPane;
    private JPanel field3Bar;
    private JLabel field3Icon;
    private JLabel field3Label;
    private JPanel field3Pane;
    private UploadPanel field3UploadPane;
    private javax.swing.JTextField genreField;
    private JLabel genreLabel;
    private JLabel headerAlbum;
    private JPanel headerAlbumTab;
    private JLabel headerArtist;
    private JPanel headerArtistTab;
    private JLabel headerLength;
    private JPanel headerLengthTab;
    private JLabel headerRow;
    private JPanel headerRowTab;
    private JLabel headerTitle;
    private JPanel headerTitleTab;
    private JPanel homeBar;
    private JLabel homeLabel;
    private JPanel homePane;
    private JLabel homePic;
    private JPanel homeTab;
    private JPanel libAftPane;
    private JLabel libAlbumArtistLabel;
    private JLabel libAlbumLabel;
    private JPanel libArtPane;
    private JLabel libArtistLabel;
    private JPanel libDelButton;
    private JLabel libGenreLabel;
    private JPanel libPlayPane;
    private JPanel libPrePane;
    private JPanel libRefButton;
    private JPanel libResButton;
    private javax.swing.JTextField libSearchField;
    private JPanel libSearchPane;
    private JLabel libTitleLabel;
    private JLabel libTrackNoLabel;
    private JLabel libYearLabel;
    private JPanel libraryBar;
    private JLabel libraryLabel;
    private JPanel libraryPane;
    private JLabel libraryPic;
    private JPanel libraryTab;
    private JLabel logoLabel;
    private JLabel logoText;
    private JPanel mainWindow;
    private JPanel manualBar;
    private JLabel manualCommitResultIcon;
    private JLabel manualLabel;
    private JPanel manualPane;
    private JLabel manualPic;
    private JPanel manualTab;
    private JLabel minusButton;
    private JLabel nameLabel;
    private JLabel nextIcon;
    private JLabel playIcon;
    private JLabel previousIcon;
    private JLabel refreshIcon;
    private JLabel restartIcon;
    private javax.swing.JScrollPane scrollTable;
    private JPanel sidePane;
    private JTable songTable;
    private JPanel tableHeader;
    private JLabel titleAutoLabel;
    private javax.swing.JTextField titleField;
    private JLabel titleLabel;
    private javax.swing.JTextField trackField;
    private JLabel trackLabel;
    private JLabel welcomeText;
    private JPanel windowBar;
    private javax.swing.JTextField yearField;
    private JLabel yearLabel;
    // End of variables declaration                   
}
