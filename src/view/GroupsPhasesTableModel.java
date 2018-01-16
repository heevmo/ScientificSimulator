package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import constants.Constants;

/**
 * 
 * @author Alex
 * 
 */
public class GroupsPhasesTableModel extends AbstractTableModel {

	//private static int phaseId = 0;
	
	private int phaseId;

	private List<List<Object>> data;

	private List<String> columnNames;

	public GroupsPhasesTableModel() {
		
		phaseId = 0;
		
		columnNames = new ArrayList<String>();
		columnNames.add(Constants.GROUP);
		columnNames.add(Constants.PHASE + " " + ++phaseId);
		columnNames.add(Constants.RANDOM);
	}

	public void removePhase() {
		if (phaseId > 1) {
			columnNames.remove(columnNames.size() - 1);
			columnNames.remove(columnNames.size() - 1);
			phaseId--;
			this.fireTableStructureChanged();
		}
	}

	public void addPhase() {
		columnNames.add(Constants.PHASE + " " + ++phaseId);
		columnNames.add(Constants.RANDOM);
		this.fireTableStructureChanged();
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column).toString();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int colIndex) {
		 data.get(rowIndex).set(colIndex, value);
		 fireTableCellUpdated(rowIndex, colIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public int getPhaseID() {
		return phaseId;
	}
	
	public void clearColumnNames() {
		columnNames.clear();
		columnNames.add(Constants.GROUP);
		phaseId = 0;
	}
}
