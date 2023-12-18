/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.utils;

/**
 *
 * @author nando
 */
public class UserSingleton
{
      private final static UserSingleton INSTANCE = new UserSingleton();

      private Integer idSelected;

      private UserSingleton() {}

      public static UserSingleton getInstace()
      {
            return INSTANCE;
      }

      public int getIdSelected()
      {
            return idSelected;
      }

      public void setIdSelected(Integer idSelected)
      {
            this.idSelected = idSelected;
      }
}
