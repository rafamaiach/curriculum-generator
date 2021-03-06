package br.unesp.rc.curriculumGenerator.DAO;

import br.unesp.rc.curriculumGenerator.model.Access;
import br.unesp.rc.curriculumGenerator.utils.FactoryConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessDAOImpl implements AccessDAO {

    /**
     * @param userId The user's Id to database
     * @return The user's Access
     */
    @Override
    public Access selectAccessByUserId(int userId) {
        Connection con = FactoryConnection.getConnection();
        Access accessReturn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (con != null) {
            try {
                preparedStatement = con.prepareStatement(SELECT_ACCESS_BY_USERID);
                preparedStatement.setInt(1, userId);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // Get Access data
                    accessReturn = new Access();
                    accessReturn.setLogin(resultSet.getString(2));
                    accessReturn.setPassword(resultSet.getString(3));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return accessReturn;
    }

    /**
     * Insert a new Ability to the database
     *
     * @param con    the connection to the database
     * @param access Ability class with the informations that will be inserted to the database
     * @param userId The user's Id that owns the Access
     * @throws SQLException
     */
    @Override
    public void insertAccess(Connection con, Access access, int userId) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = con.prepareStatement(INSERT_ACCESS);

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, access.getLogin());
        preparedStatement.setString(3, access.getPassword());
        preparedStatement.executeUpdate();
    }
}
