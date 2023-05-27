package com.spring.user.management.dao;

import com.spring.user.management.dto.UserResponseDto;
import com.spring.user.management.model.User;
import com.spring.user.management.util.GeneratePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    private void closeConnection(Connection connection , PreparedStatement statement , ResultSet resultSet){

        if(resultSet != null){
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                LOGGER.error("ResultSet Closing Error" , e);
            }
        }

        if(connection != null){
            try {
                connection.close();
            }
            catch (SQLException e) {
                LOGGER.error("Connection Closing Error", e);
            }
        }

        if(statement != null){
            try {
                statement.close();
            }
            catch (SQLException e) {
                LOGGER.error("Prepared Statement Closing Error", e);
            }
        }
    }

    @Override
    public List<UserResponseDto> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;

        ArrayList<UserResponseDto> empty = new ArrayList<>();
          UserResponseDto userResponseDto= new UserResponseDto();
        String sql = "select * from userdetails";

        try {
            connection = dataSource.getConnection();
            statement  = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            ArrayList<UserResponseDto> users = new ArrayList<>();

            while (resultSet.next()){

              //  User user = new User();

                userResponseDto.setUserId(resultSet.getInt("id"));
                userResponseDto.setFirstName(resultSet.getString("firstname"));
                userResponseDto.setLastName(resultSet.getString("lastname"));
                userResponseDto.setMobileNumber(resultSet.getLong("mobilenumber"));
                userResponseDto.setEmailId(resultSet.getString("emailid"));
                userResponseDto.setAddress(resultSet.getString("address"));

                users.add(userResponseDto);
            }
            return users;
        }
        catch (SQLException e) {
            LOGGER.error("Error on Fetching all Details" , e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return empty;
    }

    @Override
    public UserResponseDto getOne(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        UserResponseDto userResponseDto = new UserResponseDto();

        String sql = "select * from userdetails where id=?";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()){

               // User user = new User();

                userResponseDto.setUserId(resultSet.getInt("id"));
                userResponseDto.setFirstName(resultSet.getString("firstname"));
                userResponseDto.setLastName(resultSet.getString("lastname"));
                userResponseDto.setMobileNumber(resultSet.getLong("mobilenumber"));
                userResponseDto.setEmailId(resultSet.getString("emailid"));
                userResponseDto.setAddress(resultSet.getString("address"));

                userResponseDto.setStatus(true);
                userResponseDto.setSuccessMessage("Fetched User Details Successfully");
            } else {

                userResponseDto.setStatus(false);
                userResponseDto.setSuccessMessage("Data not Fetched ");
            }
        }
        catch (SQLException e) {
            LOGGER.error("Error on fetching single user" , e);
        }
        finally {
            closeConnection(connection , statement , resultSet);
        }
        return userResponseDto;
    }

    @Override
    public UserResponseDto addUser(User user) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        UserResponseDto objUserResponseDto=new UserResponseDto();
        GeneratePassword generatePassword=new GeneratePassword();
        String sql = "insert into userdetails ( firstname , lastname , mobilenumber, emailid , address, password) values (?,?,?,?,?,?)";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setLong(3, user.getMobileNumber());
            statement.setString(4, user.getEmailId());
            statement.setString(5,user.getAddress());
            statement.setString(6,generatePassword.passwordGeneration());
            count = statement.executeUpdate();

            if(count > 0){
                objUserResponseDto.setStatus(true);
                objUserResponseDto.setSuccessMessage("user created successfully");

            }else{
                objUserResponseDto.setStatus(false);
                objUserResponseDto.setSuccessMessage("user created unsuccessful");
            }
        }
        catch (SQLException e) {
            LOGGER.error("Error on Adding an User", e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return objUserResponseDto;
    }

    @Override
    public UserResponseDto modifyUser(User user, int userId) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement= null;
        String sql = "update userdetails set firstname=? , lastname=? , mobilenumber=? , emailid=? , address=? where id=?";
        UserResponseDto userResponseDto = new UserResponseDto();

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setLong(3,user.getMobileNumber());
            statement.setString(4,user.getEmailId());
            statement.setString(5,user.getAddress());

            statement.setInt(6,userId);

            count = statement.executeUpdate();

            if(count > 0){
                userResponseDto.setStatus(true);
                userResponseDto.setSuccessMessage("User Data Modified Successfully");
            }else{
                userResponseDto.setStatus(false);
                userResponseDto.setSuccessMessage("User Data Not Modified");
            }
        }
        catch (SQLException e) {
            LOGGER.error("Error on Updating Details of an User" , e);
        }
        finally {
            closeConnection(connection , statement ,null);
        }
        return userResponseDto;
    }

    @Override
    public UserResponseDto deleteUser(int userId) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from userdetails where id=?";
        UserResponseDto userResponseDto = new UserResponseDto();

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,userId);

            count = statement.executeUpdate();

            if(count > 0){
                userResponseDto.setStatus(true);
                userResponseDto.setSuccessMessage("User Data Deleted Successfully");
            }else{
                userResponseDto.setStatus(false);
                userResponseDto.setSuccessMessage("User Data not Deleted");
            }
        }
        catch (SQLException e){
            LOGGER.error("Error on Deleting a User" , e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return userResponseDto;
    }
}
