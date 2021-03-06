package br.unesp.rc.curriculumGenerator.DAO;

import br.unesp.rc.curriculumGenerator.model.Contact;

import java.sql.Connection;
import java.sql.SQLException;

public interface ContactDAO {
    final String SELECT_CONTACT_BY_USERID =
            "SELECT c.User_idUser, c.cellPhone, c.email, c.github, c.linkedin\n" +
                    "FROM contact c\n" +
                    "WHERE c.User_idUser = ?";

    final String INSERT_CONTACT =
            "INSERT INTO contact(User_idUser, cellPhone, email, github, linkedin) " +
                    "VALUES(?, ?, ?, ?, ?)";

    public abstract Contact selectContactByUserId(int userId);

    public abstract void insertContact(Connection con, Contact contact, int userId) throws SQLException;
}