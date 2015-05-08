package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import net.sourceforge.ganttproject.GanttCalendar;

import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

/**
 * 在javax.swing.DefaultCellEditor的基础上修改
 * 
 * @author Alan Chung
 * @author Philip Milne
 */

public class ScheduleCellEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

	//
	// Instance Variables
	//

	/** The Swing component being edited. */
	protected JComponent editorComponent;
	
	protected JTable parentTable;
	private int X = -1;
	private int Y = -1;
	/**
	 * The delegate class which handles all methods sent from the
	 * <code>CellEditor</code>.
	 */
	protected EditorDelegate delegate;
	/**
	 * An integer specifying the number of clicks needed to start editing. Even
	 * if <code>clickCountToStart</code> is defined as zero, it will not
	 * initiate until a click occurs.
	 */
	protected int clickCountToStart = 1;
	final private KDTDefaultCellEditor kdCellEditor;

	public ScheduleCellEditor(KDTDefaultCellEditor kdCellEditor,
			JTable parentTable) {
		this.kdCellEditor = kdCellEditor;
		this.parentTable = parentTable;
		editorComponent = (JComponent) kdCellEditor.getComponent();
		this.clickCountToStart = kdCellEditor.getClickCountToStart();
		delegate = new EditorDelegate() {
			public void setValue(Object value) {
				if(value instanceof GanttCalendar){
					value=ScheduleParserHelper.parseGanttCalendarToDate((GanttCalendar)value);
				}
				getKDCellEditor().setValue(value);
			}

			public Object getCellEditorValue() {
				Object obj = getKDCellEditor().getValue();
				if (obj != null && editorComponent instanceof KDDatePicker) {
					// 返回gantt日期
					return ScheduleParserHelper.parseDateToGanttCalendar((Date) obj);
				}
				return obj;
			}
		};

		if (editorComponent instanceof JTextField) {
			((JTextField) editorComponent).addFocusListener(delegate);
			
		}
		
		if (editorComponent instanceof KDDatePicker) {
			((KDDatePicker) editorComponent).addDataChangeListener(delegate);
		}

		if (editorComponent instanceof KDPromptBox) {
			((KDPromptBox) editorComponent).addActionListener(delegate);
		}

		if (editorComponent instanceof KDCheckBox) {
			((KDCheckBox) editorComponent).addActionListener(delegate);
		}

		if (editorComponent instanceof JComboBox) {
			((JComboBox) editorComponent).addActionListener(delegate);
		}
	}

	private KDTDefaultCellEditor getKDCellEditor() {
		return this.kdCellEditor;
	}

	/**
	 * Returns a reference to the editor component.
	 * 
	 * @return the editor <code>Component</code>
	 */
	public Component getComponent() {
		return editorComponent;
	}

	//
	// Modifying
	//

	/**
	 * Specifies the number of clicks needed to start editing.
	 * 
	 * @param count
	 *            an int specifying the number of clicks needed to start editing
	 * @see #getClickCountToStart
	 */
	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	/**
	 * Returns the number of clicks needed to start editing.
	 * 
	 * @return the number of clicks needed to start editing
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

	//
	// Override the implementations of the superclass, forwarding all methods
	// from the CellEditor interface to our delegate.
	//

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#getCellEditorValue
	 */
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#isCellEditable(EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return delegate.isCellEditable(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#shouldSelectCell(EventObject)
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return delegate.shouldSelectCell(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#stopCellEditing
	 */
	public boolean stopCellEditing() {
		this.recordXY();
		try{
			if (editorComponent instanceof JTextField ||
					editorComponent instanceof KDDatePicker) {	/* modified by zhaoqin for R140807-0020 on 2014/08/21 */
				if (X > -1 && Y > -1) {
					Object value = getCellEditorValue();
//					if(X != parentTable.getSelectedRow() || Y != parentTable.getSelectedColumn()){
//						X = parentTable.getSelectedRow();
//						Y = parentTable.getSelectedColumn();
//					}
					parentTable.setValueAt(value, X, Y);
					parentTable.removeEditor();
				}
				return true;
			} else {
				return delegate.stopCellEditing();
			}
		}catch(IllegalArgumentException e){
			if(e.getMessage().equals("FDCScheduleException001")){
				
				FDCMsgBox.showWarning("调整后的开始时间/结束时间超过了可以调整的范围!");
			}else
				Logger.error(e);
			return false;
		}
	}
	
	private void recordXY() {
		X = parentTable.getSelectedRow();
		Y = parentTable.getSelectedColumn();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#cancelCellEditing
	 */
	public void cancelCellEditing() {
		delegate.cancelCellEditing();
	}

	//
	// Implementing the TreeCellEditor Interface
	//

	/** Implements the <code>TreeCellEditor</code> interface. */
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

		delegate.setValue(stringValue);
		return editorComponent;
	}

	//
	// Implementing the CellEditor Interface
	//
	/** Implements the <code>TableCellEditor</code> interface. */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		return editorComponent;
	}

	//
	// Protected EditorDelegate class
	//

	/**
	 * The protected <code>EditorDelegate</code> class.
	 */
	protected class EditorDelegate implements ActionListener,
			DataChangeListener, FocusListener,
			ItemListener, Serializable {

		/** The value of this cell. */
		protected Object value;

		/**
		 * Returns the value of this cell.
		 * 
		 * @return the value of this cell
		 */
		public Object getCellEditorValue() {
			return value;
		}

		/**
		 * Sets the value of this cell.
		 * 
		 * @param value
		 *            the new value of this cell
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Returns true if <code>anEvent</code> is <b>not</b> a
		 * <code>MouseEvent</code>. Otherwise, it returns true if the
		 * necessary number of clicks have occurred, and returns false
		 * otherwise.
		 * 
		 * @param anEvent
		 *            the event
		 * @return true if cell is ready for editing, false otherwise
		 * @see #setClickCountToStart
		 * @see #shouldSelectCell
		 */
		public boolean isCellEditable(EventObject anEvent) {
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
			}
			return true;
		}

		/**
		 * Returns true to indicate that the editing cell may be selected.
		 * 
		 * @param anEvent
		 *            the event
		 * @return true
		 * @see #isCellEditable
		 */
		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}

		/**
		 * Returns true to indicate that editing has begun.
		 * 
		 * @param anEvent
		 *            the event
		 */
		public boolean startCellEditing(EventObject anEvent) {
			recordXY();
			return true;
		}

		/**
		 * Stops editing and returns true to indicate that editing has stopped.
		 * This method calls <code>fireEditingStopped</code>.
		 * 
		 * @return true
		 */
		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		}

		/**
		 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
		 */
		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		/**
		 * When an action is performed, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void actionPerformed(ActionEvent e) {
			ScheduleCellEditor.this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void itemStateChanged(ItemEvent e) {
			ScheduleCellEditor.this.stopCellEditing();
		}


		public void dataChanged(DataChangeEvent eventObj) {
			fireEditingStopped();
		}

		/**
		 * 由于JTextField没有dateChange等方法，用Focus代替
		 * <p>
		 * Focus进入的时候，记录当前编辑的单元格<br>
		 * Focus退出的时候，将当前单元格设置成编辑器的值<br>
		 * 这样做是为了解决修改了JTextField而鼠标点到表格外时，不会触发表格改变事件
		 */
		public void focusGained(FocusEvent e) {
//			recordXY();
		}

		public void focusLost(FocusEvent e) {
//			ScheduleCellEditor.this.stopCellEditing();
		}

	}

} // End of class JCellEditor
