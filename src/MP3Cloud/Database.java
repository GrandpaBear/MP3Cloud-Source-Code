package MP3Cloud;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//mp3agic created by mpatric, external library usage
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/* Author: Jianying Chiang		Date: 2019-04-02
 * 		The purpose of class Database is to create a table to connect with SQLite database.
 * It has the capabilities for creating, truncating, and populating the table songs as needed.
 * */
public class Database {
	//variables
	File f;
	Connection conn;
	PreparedStatement prep;
	private JTable table; 
	private String[] columns = {"#","Title","Artist","Album","Length"};
	
	//get method
	public JTable get_table() {return table;}
	
	//constructor
	public Database(JTable songTable) {
		DefaultTableModel model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
    	Action action = new AbstractAction()
        {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent arg0) {	}
        };
	}
	
	//table manipulation methods
	
	/* Author: Jianying Chiang		Date: 2019-04-02
	 * 		The purpose of createTable method is to create table songs through SQLite.
	 * @param: none
	 * @return: none
	 * */
	public void createTable() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
		Statement stat = conn.createStatement();
		String command = "CREATE TABLE IF NOT EXISTS songs (Title, Artist, Album, Album_Artist, Track_Number, Year, Genre, Length)";
		stat.executeUpdate(command);
		conn.close();
	} //----------------------- End of createTable() method -----------------------
	
	/* Author: Jianying Chiang		Date: 2019-04-02
	 * 		The purpose of truncateTable method is to truncate table songs through SQLite
	 * for reset purposes.
	 * @param: none
	 * @return: none
	 * */
	public void truncateTable() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
		Statement stat = conn.createStatement();
		String command = "DELETE FROM songs";
		stat.executeUpdate(command);
		conn.close();
	} //----------------------- End of truncateTable() method -----------------------
	
	/* Author: Jianying Chiang		Date: 2019-04-02
	 * 		The purpose of populate Table method is to populate table songs through SQLite
	 * using contents of the songs folder.
	 * @param: none
	 * @return: none
	 * */
	public void populateTable() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:mp3cloud.db");
			PreparedStatement prep;
			
			prep = conn.prepareStatement("INSERT INTO songs VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			f = new File(System.getProperty("user.dir") + "//songs");
			ID3v2 tag; 
			for (File file : f.listFiles()) {
				if (file.isFile() && (file.getName().endsWith(".mp3"))) {
					String secs;
					Mp3File mp3file = new Mp3File(file);
					tag = mp3file.getId3v2Tag();
					prep.setString(1, tag.getTitle());
			        prep.setString(2, tag.getArtist());
			        prep.setString(3, tag.getAlbum());
			        prep.setString(4, tag.getAlbumArtist());
			        prep.setString(5, tag.getTrack());
			        prep.setString(6, tag.getYear());
			        prep.setString(7, tag.getGenreDescription());
			        if (Math.round(((double)mp3file.getLengthInSeconds())/100*60%60)<10) {
			        	secs = "0"+String.valueOf(Math.round(((double)mp3file.getLengthInSeconds())/100*60%60));
			        }
			        else {
			        	secs = String.valueOf(Math.round(((double)mp3file.getLengthInSeconds())/100*60%60));
			        }
			        prep.setString(8, String.valueOf(mp3file.getLengthInSeconds()/60) + ":" + secs);
			        prep.addBatch(); 
				}
			}
	        conn.setAutoCommit(false);
	        prep.executeBatch();
	        conn.setAutoCommit(true);
	        conn.close();
		} catch(IOException e) {
			e.getMessage();
		} catch(InvalidDataException e) {
			e.getMessage();
		} catch(UnsupportedTagException e) {
			e.getMessage();
		} catch(SQLException e) {
			e.getMessage();
		} catch(ClassNotFoundException e) {
			e.getMessage();
		}
	} //----------------------- End of populateTable() method -----------------------
} //----------------------------- End of Database class -----------------------------
