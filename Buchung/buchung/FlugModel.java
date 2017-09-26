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

	public FlugModel() {

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
	 * Methode die alle Flughafen vom gegebenen Land aus der DB in die HashMap
	 * airportMap hinzufügt.
	 * 
	 * @param countrycode
	 *            dient zum bestimmen der Flughaefen.
	 */
	public void selectAirpotFromCountry(String countrycode) {
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM AIRPORTS WHERE COUNTRY = \"" + countrycode + "\"";

		try {
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String code = rs.getString("airportcode");
				String name = rs.getString("name");
				airportMap.put(name, code); // Key: Airportname
											// Value:Airportcode
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
	 * Zeigt alle Fluege zwischen 2 Flughaefen an, die aus der DB ausgelesen
	 * werden, fuegt die Werte in ein Array(einzelne Daten zum Flug) und danach
	 * in eine ArrayList(Flug).
	 * 
	 * @param airportcode1
	 * @param airportcode2
	 */
	public void selectFlight(String airportcode1, String airportcode2) {
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM FLIGHTS WHERE DEPARTURE_AIRPORT = \"" + airportcode1
				+ "\" AND DESTINATION_AIRPORT = \"" + airportcode2 + "\"";
		try {
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String code = rs.getString("airline");
				String name = rs.getString("flightnr");

				String depTime = rs.getString("departure_time");
				String desTime = rs.getString("destination_time");
				String[] arr = { code, name, depTime, desTime };
				flightList.add(arr);
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
	 * Fuegt einen Passagier zu einem Flug in die DB hinzu.
	 * 
	 * @param vname
	 *            Vorname
	 * @param nname
	 *            Nachname
	 * @param airline
	 *            Airline
	 * @param flightnr
	 *            Flugnummer
	 * @param row
	 *            Sitzreihe
	 * @param seat
	 *            Sitzposition
	 */
	public void addPassagenerToFlight(String vname, String nname, String airline, String flightnr, int row,
			String seat) {
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT ID FROM passengers ORDER BY ID DESC LIMIT 1";
		int lastID = 0;
		try {
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				lastID = Integer.parseInt(rs.getString("id"));
			}
			String query = "INSERT INTO PASSENGERS(ID,FIRSTNAME,LASTNAME,AIRLINE,FLIGHTNR,ROWNR,SEATPOSITION)"
					+ "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, lastID + 1);
			preparedStmt.setString(2, vname);
			preparedStmt.setString(3, nname);
			preparedStmt.setString(4, airline);
			preparedStmt.setString(5, flightnr);
			preparedStmt.setInt(6, row);
			preparedStmt.setString(7, seat);
			preparedStmt.execute();

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
	 * Prueft ob der Sitz zum gegebenen Flug schon besetzt ist!
	 * 
	 * @param flightnr
	 *            Flugnummer
	 * @param rownr
	 *            Sitzreihe
	 * @param seatp
	 *            Sitzposition
	 * @return <true>Der Sitz ist schon belegt.</true><false>Der Sitz ist nicht
	 *         belegt.</false>
	 */
	public boolean overBook(String flightnr, String rownr, String seatp) {
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM PASSENGERS WHERE FLIGHTNR=\"" + flightnr + "\"AND ROWNR=\"" + rownr
				+ "\" AND SEATPOSITION=\"" + seatp + "\"";
		overbook = false;
		try {
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				overbook = true;
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
		return overbook;
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

