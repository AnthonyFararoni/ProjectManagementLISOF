/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.utils;

/**
 *
 * @author nando
 */
public class SelectedItemSingleton
{
      private final static SelectedItemSingleton INSTANCE = new SelectedItemSingleton();

      private Integer idSelected;

      private SelectedItemSingleton() {}

      public static SelectedItemSingleton getInstance()
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
