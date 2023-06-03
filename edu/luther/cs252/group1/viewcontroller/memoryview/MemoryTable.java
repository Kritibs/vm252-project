package edu.luther.cs252.group1.viewcontroller.memoryview;

import javax.swing.*;
import javax.swing.table.TableModel;

public class MemoryTable extends JTable {

    //
    // Constructor
    //
    // Purpose: Set the resizing and reordering to false for all memory tables
    //
    public MemoryTable(TableModel vm252TableModel, MemoryTableCellRenderer defaultCellRenderer) {
        super(vm252TableModel);
        getTableHeader().setResizingAllowed(false);
        getTableHeader().setReorderingAllowed(false);
        setDefaultRenderer(getColumnClass(0), defaultCellRenderer);

        // Value of the future header name
        int headerValue = 0;
        for (int currentColumnId = 0; currentColumnId < vm252TableModel.getColumnCount(); ++currentColumnId) {
            if (vm252TableModel.getColumnCount() == MemoryTableModel.singleByteTableModelColumns) {
                // Set the header to the current header value
                getTableHeader().getColumnModel().getColumn(currentColumnId).setHeaderValue(headerValue);
                ++headerValue;
            }
            else {
                // Display both of the address indicators in the header using a dash
                getTableHeader().getColumnModel().getColumn(currentColumnId).setHeaderValue(headerValue + "-" + (headerValue+1));
                headerValue += 2;
            }
        }
    }
}
