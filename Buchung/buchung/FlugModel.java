package buchung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * Model-Klassen, hauptsaechlich Funktion ist es mit der DB zu kommunizieren und
 * gegebenfalls Daten auszugeben oder in die DB reinschreiben.
 * 
 * @author Gurparkash Singh, Viktor Mandelbauer
 * @since 26/09/17
 *
 */
public class FlugModel {
	private static Connection conn = null;
	private String userName = "testing";
	private String password = "12345";
	private String hostname = "localhost";
	private int port = 3306;
	HashMap<String, String> countryMap = new HashMap<String, String>();
	HashMap<String, String> airportMap = new HashMap<String, String>();
	// HashMap<String, String> flightMap = new HashMap<String, String>();
	ArrayList<String[]> flightList = new ArrayList<String[]>();
	boolean overbook = false;

	/**
	 * Zum verbinden mit der Datenbank mittels jdbc.
	 * 
	 * @param username
	 * @param password
	 * @param hostname
	 * @param port
	 */
	public void dbCon(String username, String password, String hostname, int port) {
		try {
			// create a mysql database connection
			if (username.isEmpty() || password.isEmpty() || hostname.isEmpty() || port == 0) {
				JOptionPane.showMessageDialog(null, "Bite alle Felder ausfuellen!");
			}
			String url = "jdbc:mysql://" + hostname + ":" + port + "/";
			String dbName = "flightdata";
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			// execute the preparedstatement

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Verbindung fehlgeschlagen pruefen sie Ihre Eingabe!");
			System.err.println("Exception!");
			System.err.println(e.getMessage());
		}

	}


	/**
	 * Konstruktor
	 * 
	 * @param username
	 * @param password
	 * @param hostname
	 * @param port
	 */
	public FlugModel(String username, String password, String hostname, int port) {
		dbCon(username, password, hostname, port);
	}

	/**
	 * Methode die alle Laender aus der DB in die HashMap countryMap hinzufügt.
	 */
	public void selectRecordsFromCountry() {
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM COUNTRIES";
		try {
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				countryMap.put(name, code);
			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					System.err.println("Exception!");
					System.err.println(e.getMessage());
				}
			}

		}

	}

	/**
	 * Beendet die Verbindung mit der DB.
	 */
	public void dbClose() {
		try {
			conn.close();
			System.out.println("Disconnected from the database");
		} catch (Exception e) {
			System.err.println("Exception!");
			System.err.println(e.getMessage());
		}

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
