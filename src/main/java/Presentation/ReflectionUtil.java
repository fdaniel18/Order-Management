package Presentation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class ReflectionUtil {

    public static <T> void populateTableFromList(List<T> list, DefaultTableModel tableModel) {
        if (list == null || list.isEmpty()) {
            return;
        }

        Class<?> clazz = list.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }

        tableModel.setColumnIdentifiers(columnNames);

        for (T item : list) {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    rowData[i] = fields[i].get(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableModel.addRow(rowData);
        }
    }
}
