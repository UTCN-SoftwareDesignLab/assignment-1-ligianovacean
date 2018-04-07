package controller;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;


public class TableProcessing{

    private static TableProcessing instance;

    public static TableProcessing instance() {
        if (instance == null) {
            instance = new TableProcessing();
        }
        return instance;
    }


    public <T> JTable generateTable(List<T> items, String[] columns) {
        Object[][] data = new Object[items.size()][columns.length];
        int rowNumber = 0;
        for (T row : items) {
            String[] rowData = new String[columns.length];
            Field[] fields = row.getClass().getDeclaredFields();
            for(int counter = 0; counter < columns.length; counter++) {
                fields[counter].setAccessible(true);
                Object value;
                try {
                    value = fields[counter].get(row);
                    rowData[counter] = String.valueOf(value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            //model.addRow(rowData);
            data[rowNumber] = rowData;
            rowNumber++;
        }
        JTable table = new JTable(data, columns);
        return table;
    }

}
