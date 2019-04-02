package MP3Cloud;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.imageio.ImageIO;

//Author of package com.mpatric.mp3agic: mpatric
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

/* Author: Jianying Chiang		Date: 2019-01-09
 * 		The class Song contains attributes of a current song with 
 * multiple methods that can manipulate that can take input, retrieve
 * data on Genius.com, and output information, using .txt files.
 * */
public class Song {
	//current song variables
	Mp3File mp3file;
	private String title="";
	private String artist="";
	private String album="";
	private String albumArtist;
	private int trackNo;
	private String genre;
	private int created_year;
	private String artLink;
	private int length;
	
	//additional variables
	private URL url;
    private InputStream is = null;
    private BufferedReader br;
    private String line=null;
    private boolean URLValidity;
    private BufferedImage img;
	
    //get methods
	public boolean get_URLValidity() {return URLValidity;}
	public String get_artist() {return artist;}
	public String get_title() {return title;}
	public String get_albumArtist() {return albumArtist;}
	public String get_album() {return album;}
	public int get_trackNo() {return trackNo;}
	public String get_genre() {return genre;}
	public int get_created_year() {return created_year;}
	public int get_length() {return length;}
	
	//set methods
	public void set_artist(String A) {artist=A;}
	public void set_title(String A) {title=A;}
	public void set_albumArtist(String A) {albumArtist=A;}
	public void set_album(String A) {album=A;}
	public void set_trackNo(int A) {trackNo=A;}
	public void set_genre(String A) {genre=A;}
	public void set_created_year(int A) {created_year=A;}
	public void set_length(int A) {length=A;}
	public void set_image(BufferedImage A) {img=A;}
	public void set_mp3file(Mp3File A) {mp3file=A;}

	// default constructor
	Song() {}
	
	//methods
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the userInput() method is to ask for user input
	 * for the song title and artist name.
	 * @param: none
	 * @return: none
	 * */
	public void userInput() {
		Scanner in = new Scanner(System.in);
		
    	System.out.print("Enter song title: ");
    	title = in.nextLine();
    	System.out.print("Enter artist name: ");
    	artist = in.nextLine();
    	
    	in.close();
	} //------------------------ End of userInput() method ------------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the generateSongURL() method is to generate and
	 * return a correct formatted url based on Genius.com song webpages,
	 * using the song title and artist name.
	 * @param: none
	 * @return: String
	 * */
	public String generateSongURL() {
	    String song_url;
	    String song_title="";
	    String artist_name="";
	    System.out.println(title+artist);
	    if (!title.equals("") || !artist.equals("")) {
	    	song_title = title.replaceAll("[-+.^:,!?()]","");
	    	song_title = song_title.replaceAll("[\\s]","-");
	    	if (song_title.endsWith("-")) {
		    	song_title = song_title.substring(0,song_title.lastIndexOf(song_title));
		    }
	    	while (song_title.startsWith("-")) {
	    		song_title = song_title.replaceFirst("[-]", "");
		    }
	    	artist_name = artist.replaceAll("[-+.^:,!?()]","");
	    	artist_name = artist_name.replaceAll("[\\s]","-");
	    	while (artist_name.startsWith("-")) {
	    		artist_name = artist_name.replaceFirst("[-]", "");
		    }
	    }
    	
    	song_url = "https://genius.com/" + artist_name + "-" + song_title + "-lyrics";
    	return song_url;
	} //------------------------ End of generateSongURL() method ------------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the generateAlbumURL() method is to generate and
	 * return a correct formatted url based on Genius.com song webpages,
	 * using the artist and album name.
	 * @param: none
	 * @return: String
	 * */
	public String generateAlbumURL() {
	    String album_url;
	    String album_title="";
	    String artist_name="";
	    
	    if (!album.equals("") || !artist.equals("")) {
		    album_title = album.replaceAll("[-+.^:,!?()]","");
		    album_title = album_title.replaceAll("[\\s]","-");
		    if (album_title.endsWith("-")) {
		    	album_title = album_title.substring(0,album_title.lastIndexOf(album_title));
		    }
		    while (album_title.startsWith("-")) {
		    	album_title = album_title.replaceFirst("[-]", "");
		    }
	    	artist_name = artist.replaceAll("[-+.^:,!?()]","");
	    	artist_name = artist_name.replaceAll("[\\s]","-");
	    	while (artist_name.startsWith("-")) {
	    		artist_name = artist_name.replaceFirst("[-]", "");
		    }
	    }
    	
    	album_url = "https://genius.com/albums/" + artist_name + "/" + album_title;
    	return album_url;
	} //------------------------ End of generateAlbumURL() method ------------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the validURL() method is to check if a url is
	 * properly formatted and if a corresponding Genius.com webpage exists
	 * and return this result as a boolean.
	 * @param: String
	 * @return: boolean
	 * */
	public boolean validURL(String link) {
		URLValidity = true;
		try {
	    	System.setProperty("http.agent", "Chrome");
	    	url = new URL(link);
	        is = url.openStream();  // throws an IOException
	        is.close();
		} catch (MalformedURLException e) {
	        System.out.println("Connection failed.");
	        URLValidity=false;
	    } catch (IOException e) {
	    	System.out.println("Connection failed.");
	    	URLValidity=false;
	    }
		if (URLValidity==true) {
			System.out.println("Connection success.");
			System.out.println("Retrieving Data... (source: " + link + ")");
			return true;
		}
		else {
			System.out.println(link);
			return false;			
		}
	} //------------------------ End of validURL() method ------------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the retrieveSongContent() method is to input useful lines
	 * of data from the page source of a specified Genius.com song webpage onto a
	 * .txt file. That .txt file is scanned for tags and its contents are retrieved
	 * to set the current song's variables.  
	 * @param: none
	 * @return: none
	 * */
	public void retrieveSongContent() {
		//local variables
		BufferedImage img;
		URL url;
        File outputImage;
        Scanner in;
        String holder;
		
		//method proceeds if URL is valid
		if (!validURL(generateSongURL())) {
    		return;
    	}
		try {
	    	//input from the corresponding Genius.com song webpage
			PrintWriter out = new PrintWriter(new File("current_song_meta.txt"));
	    	System.setProperty("http.agent", "Chrome");
	    	url = new URL(generateSongURL());
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        int l=1; // line number 
	        while ((line = br.readLine()) != null && l<400) {
	        	//<meta> info exists on line 52 and approximately line 370 on genius song pages
	        	//outputs useful lines onto a .txt file
	        	if (l==53) {
	        		out.print(line);
	        	}
	        	//sets the artLink variable with the corresponding picture link
	        	if (l>=350 && line.length()>=71 && line.substring(0,71).equals("          <img alt=\"Https%3a%2f%2fimages\" class=\"cover_art-image\" src=\"")) {
	        		artLink = line.substring(71, line.indexOf("\"",71)); 
	        		l=400;
	        	}
	        	l++;
	        }
	        out.close();
	        is.close(); 
	        
	        //downloads album art as jpg file
	        url = new URL(artLink);
	        img = ImageIO.read(url);
	        outputImage = new File("downloaded.jpg");
	        ImageIO.write(img, "jpg", outputImage);
	        
	        in = new Scanner(new File("current_song_meta.txt"));
	        System.out.println(in.next());
	        in.useDelimiter(",\r\n|\":\"|\":|\"|,|\n");
	        System.out.println(in.next());
	        //current song variables are set using input from the .txt file
	        while (in.hasNext()) {
	        	holder = in.next();
	        	System.out.println(holder);
	        	if (holder.equals("Primary Tag")) {
	        		genre = in.next();
	        		genreConversion();
	        	}
	        	if (holder.equals("Title")) {
	        		title = in.next();
	        	}
	        	if (holder.equals("Primary Artist")) {
	        		artist = in.next();
	        	}
	        	if (holder.equals("Primary Album")) {
	        		album = in.next();
	        	}
	        	if (holder.equals("created_year")) {
	        		created_year = in.nextInt();
	        	}
	        }
	        out.close();
	        in.close();
	        is.close();      
	        
	    } catch (MalformedURLException e) {
	    	System.out.println("ERROR: URL is malformed.");
	    } catch (IOException e) {
	    	System.out.println("ERROR: Page not found.");
	    }
	} //--------------------- End of retrieveSongContent() method ---------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the genreConversion() method is convert the current song's
	 * genre to English.
	 * @param: none
	 * @return: none
	 * */
	public void genreConversion() {
		if (genre.equals("")) {
			
		}
		else if (genre.equals("")) {
			
		}
	} //--------------------- End of genreConversion() method ---------------------
	
	/* Author: Jianying Chiang		Date: 2019-01-09
	 * 		The purpose of the retrieveAlbumContent() method is to input useful lines
	 * of data from the page source of a specified Genius.com song webpage onto a
	 * .txt file. That .txt file is scanned for tags and its contents are retrieved
	 * to set the current song's variables.  
	 * @param: none
	 * @return: none
	 * */
	public void retrieveAlbumContent() {
		//local variables
		Scanner in;
        String holder;
		
		//method proceeds if URL is valid
		if (!validURL(generateAlbumURL())) {
    		return;
    	}
		try {
			//input from the corresponding Genius.com album webpage
	    	PrintWriter out = new PrintWriter(new File("current_album_meta.txt"));
	    	System.setProperty("http.agent", "Chrome");
	    	url = new URL(generateAlbumURL());
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        int l=1; // line number 
	        while ((line = br.readLine()) != null && l<300) {
	        	//<meta> info exists on line 235 on genius album pages
	        	//outputs useful lines onto a .txt file
	        	if (l>200 && line.length()>=28 && line.substring(0, 28).equals("      <meta content=\"{&quot;")) {
	        		out.print(line);
	        	}
	        	l++;
	        }
	        out.close();
	        is.close();
	        
	        in = new Scanner(new File("current_album_meta.txt"));
	        in.useDelimiter(",\r\n|&quot;:&quot;|&quot;:|&quot;|;|\":\"|\":|\"|,|&|\n");
	        
	      //current song's trackNo is set using input from the .txt file
	        while (in.hasNext() && trackNo==0) {
	        	holder = in.next();
	        	if (holder.equals("track_number")) {
	        		trackNo = Integer.parseInt(in.next());
	        		boolean breakLoop = false;
	        		while (in.hasNext() && breakLoop==false) {
	        			holder = in.next();
	        			if (holder.equals("title")) {
	        				if (!in.next().toLowerCase().equals(title.toLowerCase())) {
	        					trackNo=0;
	        				}
	        				breakLoop = true;
	        			}
	        		}
	        	}
	        }
	        out.close();
	        in.close();
	        is.close();
		} catch (MalformedURLException e) {
			System.out.println("ERROR: URL is malformed.");
	    } catch (IOException e) {
	    	System.out.println("ERROR: Page not found.");
	    }	 
	} //--------------------- End of retrieveAlbumContent() method ---------------------
	
	public boolean fileVerification() {

		return true;
	}
	
	/* Author: Jianying Chiang		Date: 2019-01-22
	 * 		The purpose of setGeniusTags() method is to create a tagged mp3 file using tags
	 * that were previously retrieved from Genius.com.
	 * @param: none
	 * @return: none
	 * */
	public boolean setTags() {
		//local variables
		ID3v2 tag;
		ByteArrayOutputStream out;
		byte[] data;
		InputStream is;
		String mimeType;
		
		//tags mp3 file
		try {
			if (mp3file.hasId3v2Tag()) {
			  tag = mp3file.getId3v2Tag();
			  tag.setTitle(title);
			  tag.setArtist(artist);
			  tag.setAlbum(album);
			  tag.setAlbumArtist(albumArtist);
			  tag.setTrack(Integer.toString(trackNo));
			  tag.setYear(Integer.toString(created_year));
			  tag.setGenreDescription(genre);
			  
			  if(new File("downloaded.jpg").isFile()) { 
				  img = ImageIO.read(new File("downloaded.jpg"));
				  out = new ByteArrayOutputStream();
				  ImageIO.write(img, "jpg", out);
				  data = out.toByteArray();
				  is = new BufferedInputStream(new FileInputStream("downloaded.jpg"));
				  mimeType = URLConnection.guessContentTypeFromStream(is);
				  tag.setAlbumImage(data, mimeType);
				  out.close();
				  is.close();
			  }
			 
			  mp3file.save(title + ".mp3");
			  
			  File file = new File(title+".mp3");
			  file.delete();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
} //----------------------- End of Mp3File class -----------------------
