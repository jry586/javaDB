package viewDB;

import com.sun.rowset.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;

import util.DerbyConnection;

class ViewDBFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -723914736296337701L;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 200;
	private DerbyConnection dcon;
	private JButton previousButton;
	private JButton nextButton;
	private JButton deleteButton;
	private JButton saveButton;
	private DataPanel dataPanel;
	private Component scrollPane;
	private JComboBox<String> tableNames;
	private CachedRowSet crs;

	public ViewDBFrame() {
		setTitle("ViewDB");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		tableNames = new JComboBox<String>();
		tableNames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showTable((String) tableNames.getSelectedItem());
			}
		});
		add(tableNames, BorderLayout.NORTH);
		dcon = new DerbyConnection();
		try (Connection conn = dcon.getConnection()) {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet mrs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (mrs.next())
				tableNames.addItem(mrs.getString(3));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, e1);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			JOptionPane.showMessageDialog(this, e2);
		}
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		previousButton = new JButton("Previous");
		previousButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showPreviousRow();
			}
		});
		buttonPanel.add(previousButton);
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showNextRow();
			}
		});
		buttonPanel.add(nextButton);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteRow();
			}
		});
		buttonPanel.add(deleteButton);
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveChanges();
			}
		});
		buttonPanel.add(saveButton);
	}

	private void saveChanges() {
		// TODO Auto-generated method stub

	}

	private void deleteRow() {
		// TODO Auto-generated method stub

	}

	private void showNextRow() {
		// TODO Auto-generated method stub

	}

	private void showPreviousRow() {
		// TODO Auto-generated method stub

	}

	private void showTable(String tableName) {
		// TODO Auto-generated method stub
		try {
			try (Connection conn = dcon.getConnection()) {
				Statement stat = conn.createStatement();
				ResultSet rs = stat.executeQuery("select * from " + tableName);
				crs = new CachedRowSetImpl();
				crs.setTableName(tableName);
				crs.populate(rs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (scrollPane != null)
				remove(scrollPane);
			dataPanel = new DataPanel(crs);
			scrollPane = new JScrollPane(dataPanel);
			add(scrollPane, BorderLayout.CENTER);
			validate();
			showNextRow();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e);
		}
	}
}
