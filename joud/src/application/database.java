public class database {

	public static Connection connectDB() {

		private final String DB_URL = "jdbc:mysql://localhost:3306/local_bloom_ranad";
		private final String DB_USER = "root";
		private final String DB_PASSWORD = "100398";
		
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}