package edu.tangingina.thebackroom.dao.impl;

import com.mysql.cj.exceptions.DataReadException;
import edu.tangingina.thebackroom.dao.RoleDao;
import edu.tangingina.thebackroom.model.Role;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class RoleDaoImpl implements RoleDao {
    @Override
    public HashMap<String, Role> getAllRole() {
        String query = "Select * from role";
        HashMap<String, Role> roleList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                Role role = new Role(rs.getInt("role_id"), rs.getString("name"));
                roleList.put(rs.getString("name"), role);
            }
        }catch(Exception e){

        }
        return roleList;
    }

    @Override
    public void getMediaContributors() {

    }
}
