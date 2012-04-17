import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

class JarRenderer extends DefaultListCellRenderer {
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      setText(((Entity) value).getName()); 
      
      return c;
   }
}