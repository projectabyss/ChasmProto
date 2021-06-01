import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieDB {
    private static HashMap<String, Movie> Movies;

    private static void initialize() {
        if(Movies==null){
        		Movies=new HashMap<String, Movie>();
	        	String query="select id,title,year,genre,director,minutes from ratedmovies";
			Connection con=null;
			ResultSet rs=null;
			try{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie", "root", "");
				Statement stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				while(rs.next()){
					Movies.put(rs.getString("id"),(new Movie(rs.getString("id"),rs.getString("title"),rs.getString("year"),
                			rs.getString("genre"),rs.getString("director"),Integer.parseInt(rs.getString("minutes")))));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    	}
    }		
    public static boolean containsID(String id) {
        initialize();
        return Movies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return Movies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return Movies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return Movies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return Movies.get(id);
    }

    public static int getMinutes(String id) {
        initialize();
        return Movies.get(id).getMinutes();
    }

    public static String getDirector(String id) {
        initialize();
        return Movies.get(id).getDirector();
    }

    public static int size() {
        return Movies.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id:Movies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

}