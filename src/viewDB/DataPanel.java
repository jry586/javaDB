package viewDB;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.rowset.*;

class DataPanel extends JPanel {
	private ArrayList<JTextField> fields;
	public DataPanel(RowSet rs) throws SQLException{
		fields=new ArrayList<JTextField>();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridwidth=1;
		gbc.gridheight=1;
		ResultSetMetaData rsmd=rs.getMetaData();
		for(int i=1;i<=rsmd.getColumnCount();i++){
			gbc.gridy=i-1;
			String columnName=rsmd.getColumnLabel(i);
			gbc.gridx=0;
			gbc.anchor=GridBagConstraints.EAST;
			add(new JLabel(columnName),gbc);
			int columnWidth=rsmd.getColumnDisplaySize(i);
			JTextField tf=new JTextField(columnWidth);
			if(!rsmd.getColumnClassName(i).equals("java.lang.String"))tf.setEditable(false);
			fields.add(tf);
			gbc.gridx=1;
			gbc.anchor=GridBagConstraints.WEST;
			add(tf,gbc);
		}
	}
	
	public void showRow(RowSet rs) throws SQLException{
		for(int i=1;i<=fields.size();i++){
			String field=rs.getString(i);
			JTextField tf=fields.get(i-1);
			tf.setText(field);
		}
	}
	
	public void setRow(RowSet rs) throws SQLException{
		for(int i=1;i<fields.size();i++){
			String field=rs.getString(i);
			JTextField tf=fields.get(i-1);
			if(!field.equals(tf.getText()))	rs.updateString(i, tf.getText());
		}
		rs.updateRow();
	}

}
