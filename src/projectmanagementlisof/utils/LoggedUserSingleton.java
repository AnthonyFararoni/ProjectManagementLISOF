/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.utils;

/**
 *
 * @author edmun
 */
public class LoggedUserSingleton
{
      private static LoggedUserSingleton instance;
      private String userFullName;
      private String userLogin;
      private int userId;

      private LoggedUserSingleton() {}

      public static LoggedUserSingleton getInstance()
      {
            if (instance == null)
            {
                  instance = new LoggedUserSingleton();
            }
            return instance;
      }

      public void setUserData(String userFullName, String userLogin, int userId)
      {
            this.userFullName = userFullName;
            this.userLogin = userLogin;
            this.userId = userId;
      }

      public String getUserFullName()
      {
            return userFullName;
      }

      public String getUserLogin()
      {
            return userLogin;
      }

      public int getUserId()
      {
            return userId;
      }
}
