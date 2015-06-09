package com.zblog.core.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtils{

  public static void close(Connection conn){
    if(conn != null){
      try{
        conn.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static void close(Statement sta){
    if(sta != null){
      try{
        sta.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static void close(ResultSet rs){
    if(rs != null){
      try{
        rs.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static void close(ResultSet rs, Statement sta, Connection conn){
    close(rs);
    close(sta, conn);
  }

  public static void close(Statement sta, Connection conn){
    close(sta);
    close(conn);
  }

  public static void close(ResultSet set, Statement state){
    close(set);
    close(state);
  }

  public static void rollback(Connection conn){
    if(conn != null){
      try{
        conn.rollback();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static void cancleTransaction(Connection conn){
    if(conn != null){
      try{
        conn.setAutoCommit(true);
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

}