package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

	private String tittle;
	private String description;

	private int id = 0;

	public Exercise(String tittle, String description) {
		this.tittle = tittle;
		this.description = description;
	}

	public Exercise() {
		super();
	}

	@Override
	public String toString() {
		return "Tytuł zadania: " + tittle + ", a jego id to: " + id + ". Treść zadania: " + description;

	}

	public void saveToDb(Connection conn) throws SQLException {

		if (this.id == 0) {
			String updateQuery = "INSERT INTO exercise(tittle,description) VALUES (?,?)";
			String[] autoGenerated = new String[] { "id" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(updateQuery, autoGenerated);
			preparedStatement.setString(1, this.tittle);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet set = preparedStatement.getGeneratedKeys();
			while (set.next()) {
				this.id = set.getInt(1);
			}
		} else {
			String sql = "UPDATE	exercise	SET	tittle=?,description=? where	id	=	?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.tittle);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
		}

	}

	public static Exercise loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT	*	FROM	exercise	where	id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.tittle = resultSet.getString("tittle");
			loadedExercise.description = resultSet.getString("description");
			return loadedExercise;
		}
		return null;
	}

	static public Exercise[] loadAllExercise(Connection conn) throws SQLException {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT	*	FROM	exercise";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.tittle = resultSet.getString("tittle");
			loadedExercise.description = resultSet.getString("description");
			exercises.add(loadedExercise);
		}
		Exercise[] eArray = new Exercise[exercises.size()];
		eArray = exercises.toArray(eArray);
		return eArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE	FROM	exercise	WHERE	id=	?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

}
