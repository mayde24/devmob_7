package hello;
import java.util.*;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/movies")
    public String movies(){
    	List<String> listMovies = new ArrayList<String>();
	String sqlSelectAllMovies = "SELECT * FROM movies";
	String connectionUrl="jdbc:mysql://127.0.0.1:3306/movies?serverTimezone=UTC";

	try(Connection conn = DriverManager.getConnection(connectionUrl, "root","admin");
		PreparedStatement ps = conn.prepareStatement(sqlSelectAllMovies);
		ResultSet rs = ps.executeQuery()){

		while(rs.next()) {
		System.out.println("ICI");
			String title = rs.getString("title");
		listMovies.add(title);
		}

	 }catch(SQLException throwables){
	 	throwables.printStackTrace();
	 }

	
	    return listMovies.toString();
    }
}
