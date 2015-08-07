package net.sourceforge.ganttproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.delay.Delay;
import net.sourceforge.ganttproject.gui.GanttDialogCustomColumn;
import net.sourceforge.ganttproject.gui.TableHeaderUIFacade;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.language.GanttLanguage.Event;
import net.sourceforge.ganttproject.language.GanttLanguage.Listener;
import net.sourceforge.ganttproject.task.CustomColumEvent;
import net.sourceforge.ganttproject.task.CustomColumn;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.CustomColumsListener;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskNode;

import org.jdesktop.swing.decorator.AlternateRowHighlighter;
import org.jdesktop.swing.decorator.HierarchicalColumnHighlighter;
import org.jdesktop.swing.decorator.Highlighter;
import org.jdesktop.swing.decorator.HighlighterPipeline;
import org.jdesktop.swing.table.TableColumnExt;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;

/**
 * Treetable used to displayed tabular data and hierarchical data.
 * 
 * @author bbaranne
 * @version 1.0 (20050301) (yyyymmdd)
 */
public class GanttTreeTable extends GPTreeTableBase implements
		GanttLanguage.Listener, CustomColumsListener {
	/**
	 * Unique instance of GanttLanguage.
	 */
	private static GanttLanguage language = GanttLanguage.getInstance();

	/**
	 * PopupMenu showed on right click (window, linux) on the table header.
	 */
	private JPopupMenu popupMenu;

	/**
	 * Point where the user has just right clicked on the table header.
	 */
	private Point clickPoint = null;

	/**
	 * model of the treetable.
	 */
	private final GanttTreeTableModel ttModel;

	/**
	 * stores the tableColum associated with there ColumnKeeper. it is used to
	 * retore the column at the same index it has been removed.
	 */
	private final Map mapTableColumnColumnKeeper = new LinkedHashMap();

	private String[] columnsProperty = null;

	/**
	 * Menu item to delete columns.
	 */
	private JMenuItem jmiDeleteColumn;

	DisplayedColumnsList listDisplayedColumns = null;

	private Listener myLanguageListener;

	private final UIFacade myUIfacade;

	private final TableHeaderUIFacade myVisibleFields = new VisibleFieldsImpl();

	private int uiMark = 0;

	/**
	 * Creates an instance of GanttTreeTable with the given TreeTableModel.
	 * 
	 * @param project
	 * 
	 * @param model
	 *            TreeTableModel.
	 */
	public GanttTreeTable(IGanttProject project, UIFacade uifacade,
			GanttTreeTableModel model) {
		super(project, model);
		model.setTreetable(this);
		GanttLanguage.getInstance().addListener(this);
		initTreeTable();
		this.ttModel = model;
		myUIfacade = uifacade;
	}

	void setAction(Action action) {

		addAction(action, (KeyStroke) action.getValue(Action.ACCELERATOR_KEY));

		// Add the action to the component
	}

	void addAction(Action action, KeyStroke keyStroke) {
		InputMap inputMap = getInputMap();
		inputMap.put(keyStroke, action.getValue(Action.NAME));
		getActionMap().put(action.getValue(Action.NAME), action);
	}

	private void updateDisplayedColumnsOrder() {
		Iterator it = this.listDisplayedColumns.iterator();
		while (it.hasNext()) {
			DisplayedColumn dc = (DisplayedColumn) it.next();
			if (dc.isDisplayed()) {
				String id = dc.getID();
				String name = getNameForId(id);
				int viewIndex = getDefaultOrder(name);
				dc.setOrder(viewIndex);
				dc.setWidth(getColumn(name).getPreferredWidth());
			}
		}
	}

	public DisplayedColumnsList getDisplayColumns() {
		updateDisplayedColumnsOrder();
		return this.listDisplayedColumns;
	}

	public void setDisplayedColumns(DisplayedColumnsList displayedColumns) {
		DisplayedColumnsList l = (DisplayedColumnsList) displayedColumns
				.clone();
		displayAllColumns();
		hideAllColumns();
		this.listDisplayedColumns = l;
		Collections.sort(this.listDisplayedColumns);
		// 在displayColumn方法中也对listDisplayedColumns进行了迭代，会导致报错，把本方法中的迭代
		// 改为for循环即可， edit by emanon
		for (int i = 0; i < listDisplayedColumns.size(); i++) {
			DisplayedColumn dc = (DisplayedColumn) listDisplayedColumns.get(i);
			String id = dc.getID();
			String name = getNameForId(id);

			if (dc.displayed)
				displayColumn(name);
			else
				hideColumn(name);
		}
	}

	void reloadColumns() {
		if (true) {
			listDisplayedColumns = new DisplayedColumnsList();
			mapTableColumnColumnKeeper.clear();
			return;
		}
		List columns = Collections.list(getTable().getColumnModel()
				.getColumns());
		for (int i = 0; i < columns.size(); i++) {
			getTable().removeColumn((TableColumn) columns.get(i));
		}
		if (myLanguageListener != null) {
			GanttLanguage.getInstance().removeListener(myLanguageListener);
		}
		final TableColumnExt tce4 = newTableColumnExt(0);
		final TableColumnExt tce5 = newTableColumnExt(1);
		final TableColumnExt tce6 = newTableColumnExt(2);
		final TableColumnExt tce10 = newTableColumnExt(3);
		final TableColumnExt tce11 = newTableColumnExt(4);
		myLanguageListener = new GanttLanguage.Listener() {
			public void languageChanged(Event event) {
				GanttTreeTable.this.ttModel.languageChanged(event);
				tce4.setTitle(GanttTreeTableModel.strColName);
				tce5.setTitle(GanttTreeTableModel.strColBegDate);
				tce6.setTitle(GanttTreeTableModel.strColEndDate);
				tce10.setTitle(GanttTreeTableModel.strColPredecessors);
				tce11.setTitle(GanttTreeTableModel.strColID);
			}
		};

		GanttLanguage.getInstance().addListener(myLanguageListener);
		this.addColumn(tce4);
		this.addColumn(tce5);
		this.addColumn(tce6);
		this.addColumn(tce10);
		this.addColumn(tce11);
		{
			listDisplayedColumns = new DisplayedColumnsList();
			// Name
			DisplayedColumn dc4 = new DisplayedColumn(getIdForName(tce4
					.getTitle()));
			dc4.setDisplayed(true);
			dc4.setOrder(getDefaultOrder(tce4.getTitle()));
			listDisplayedColumns.add(dc4);
			// Begin date
			DisplayedColumn dc5 = new DisplayedColumn(getIdForName(tce5
					.getTitle()));
			dc5.setDisplayed(true);
			dc5.setOrder(getDefaultOrder(tce5.getTitle()));
			dc5.setWidth(tce5.getPreferredWidth());
			listDisplayedColumns.add(dc5);
			// End date
			DisplayedColumn dc6 = new DisplayedColumn(getIdForName(tce6
					.getTitle()));
			dc6.setDisplayed(true);
			dc6.setOrder(getDefaultOrder(tce6.getTitle()));
			dc6.setWidth(tce6.getPreferredWidth());
			listDisplayedColumns.add(dc6);

			// Predecessors
			DisplayedColumn dc10 = new DisplayedColumn(getIdForName(tce10
					.getTitle()));
			dc10.setDisplayed(false);
			dc10.setOrder(getDefaultOrder(tce10.getTitle()));
			dc10.setWidth(tce10.getPreferredWidth());
			listDisplayedColumns.add(dc10);

			// ID
			DisplayedColumn dc11 = new DisplayedColumn(getIdForName(tce11
					.getTitle()));
			dc11.setDisplayed(false);
			dc11.setOrder(getDefaultOrder(tce11.getTitle()));
			dc11.setWidth(tce11.getPreferredWidth());
			listDisplayedColumns.add(dc11);
		}
		{
			this.mapTableColumnColumnKeeper.clear();
			this.mapTableColumnColumnKeeper.put(tce4, new ColumnKeeper(tce4));
			this.mapTableColumnColumnKeeper.put(tce5, new ColumnKeeper(tce5));
			this.mapTableColumnColumnKeeper.put(tce6, new ColumnKeeper(tce6));
			this.mapTableColumnColumnKeeper.put(tce10, new ColumnKeeper(tce10));
		}
		initColumnsAlignements();
		getTable().getColumnExt(GanttTreeTableModel.strColBegDate)
				.setCellEditor(newDateCellEditor());
		getTable().getColumnExt(GanttTreeTableModel.strColEndDate)
				.setCellEditor(newDateCellEditor());
		getTable().getColumnExt(GanttTreeTableModel.strColName).setCellEditor(
				new NameCellEditor());
		// createPopupMenu();
		initDisplay();
		Runnable t = new Runnable() {
			public void run() {
				calculateWidth();
				revalidate();
			}
		};
		SwingUtilities.invokeLater(t);
	}

	public void initDisplay() {
		if (listDisplayedColumns != null) {
			this.setDisplayedColumns(listDisplayedColumns);
		} else {
			this.displayAllColumns();
		}
	}

	/**
	 * Initialize the treetable. Addition of various listeners, tree's icons,
	 */
	void initTreeTable() {
		clickPoint = null;
		getProject().getTaskCustomColumnManager()
				.addCustomColumnsListener(this);
		getTreeTableModel().addTreeModelListener(new TreeModelListener() {
			public void treeNodesChanged(TreeModelEvent arg0) {
			}

			public void treeNodesInserted(TreeModelEvent arg0) {
			}

			public void treeNodesRemoved(TreeModelEvent arg0) {

			}

			public void treeStructureChanged(TreeModelEvent arg0) {
			}

		});
		getTable().setAutoCreateColumnsFromModel(false);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		setShowHorizontalLines(false);

		setOpenIcon(null);
		setClosedIcon(null);
		setCollapsedIcon(new ImageIcon(getClass()
				.getResource("/icons/plus.gif")));
		setExpandedIcon(new ImageIcon(getClass()
				.getResource("/icons/minus.gif")));
		setLeafIcon(null);

		this.setHasColumnControl(false);
		this.getTreeTable().getParent().setBackground(Color.WHITE);
		this.getTreeTable().getParent().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				e.consume();
				if (e.getClickCount() == 1)
					Mediator.getGanttProjectSingleton().getTree()
							.selectTreeRow(-1);
				else if (e.getClickCount() == 2
						&& e.getButton() == MouseEvent.BUTTON1) {
					Mediator.getGanttProjectSingleton().getUndoManager()
							.undoableEdit("New Task by click", new Runnable() {
								public void run() {
									Mediator.getGanttProjectSingleton()
											.newTask();
								}
							});
				}
			}
		});
		{

			InputMap inputMap = getInputMap();
			inputMap.setParent(getTreeTable().getInputMap(
					JComponent.WHEN_FOCUSED));
			getTreeTable().setInputMap(JComponent.WHEN_FOCUSED, inputMap);
			ActionMap actionMap = getActionMap();
			actionMap.setParent(getTreeTable().getActionMap());
			getTreeTable().setActionMap(actionMap);

		}
		{
			getTable().getColumnModel().addColumnModelListener(
					(TableColumnModelListener) this.getTreeTableModel());
			getTable().getModel().addTableModelListener(new ModelListener());
			getTable().getTableHeader().addMouseListener(
					new HeaderMouseListener());
			// The following is used to store the new index of a moved column in
			// order
			// to restore it properly.
			getTable().getColumnModel().addColumnModelListener(
					new TableColumnModelListener() {
						public void columnAdded(TableColumnModelEvent e) {
							// nothing to do
						}

						public void columnRemoved(TableColumnModelEvent e) {
							// nothing to do
						}

						public void columnMoved(TableColumnModelEvent e) {
							DefaultTableColumnModel o = (DefaultTableColumnModel) e
									.getSource();
							TableColumn tc = o.getColumn(e.getFromIndex());
							ColumnKeeper ck = ((ColumnKeeper) mapTableColumnColumnKeeper
									.get(tc));
							if (ck != null)
								ck.setInitIndex(e.getToIndex());
							if (Mediator.getGanttProjectSingleton() != null)
								Mediator.getGanttProjectSingleton()
										.setAskForSave(true);
							updateDisplayedColumnsOrder();
						}

						public void columnMarginChanged(ChangeEvent e) {
							// nothing to do
						}

						public void columnSelectionChanged(ListSelectionEvent e) {
							// nothing to do
						}
					});

		}
		setHighlighters(new HighlighterPipeline(new Highlighter[] {
				AlternateRowHighlighter.quickSilver,
				new HierarchicalColumnHighlighter() }));

		getTable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						onCellSelectionChanged();
					}
				});
		getTable().getColumnModel().getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						onCellSelectionChanged();
					}
				});

		reloadColumns();
	}

	protected void onCellSelectionChanged() {
		// if (!getTable().isEditing()) {
		// int row = getTable().getSelectedRow();
		// int col = getTable().getSelectedColumn();
		// Rectangle rect = getTable().getCellRect(row, col, true);
		// scrollPane.scrollRectToVisible(rect);
		// }
	}

	void addScrollPaneMouseListener(MouseListener ml) {
		this.getTreeTable().getParent().addMouseListener(ml);
	}

	private void initColumnsAlignements() {
		setColumnHorizontalAlignment(GanttTreeTableModel.strColBegDate,
				SwingConstants.CENTER);
		setColumnHorizontalAlignment(GanttTreeTableModel.strColEndDate,
				SwingConstants.CENTER);
		setColumnHorizontalAlignment(GanttTreeTableModel.strColPredecessors,
				SwingConstants.RIGHT);
		setColumnHorizontalAlignment(GanttTreeTableModel.strColID,
				SwingConstants.CENTER);

		// Set the columns widths
		getTable().getColumnExt(GanttTreeTableModel.strColID)
				.setPreferredWidth(
						getDefaultWidth(GanttTreeTableModel.strColID));
		getTable()
				.getColumnExt(GanttTreeTableModelExt.strColWBSNumber)
				.setPreferredWidth(
						getDefaultWidth(GanttTreeTableModelExt.strColWBSNumber));
		getTable()
				.getColumnExt(GanttTreeTableModel.strColPredecessors)
				.setPreferredWidth(
						getDefaultWidth(GanttTreeTableModel.strColPredecessors));
		getTable().getColumnExt(GanttTreeTableModel.strColBegDate)
				.setPreferredWidth(
						getDefaultWidth(GanttTreeTableModel.strColBegDate));
		getTable().getColumnExt(GanttTreeTableModel.strColEndDate)
				.setPreferredWidth(
						getDefaultWidth(GanttTreeTableModel.strColEndDate));
	}

	void calculateWidth() {
		int width = 0;

		int nbCol = getTable().getColumnCount();

		for (int i = 0; i < nbCol; i++) {
			TableColumnExt tce = getTable().getColumnExt(i);
			if (tce.isVisible())
				width += tce.getPreferredWidth();
		}

		getTable().setPreferredScrollableViewportSize(new Dimension(width, 0));
	}

	/**
	 * 创建表头右键菜单，用于显示或隐藏列
	 * <p>
	 * 此处先按order排序<br>
	 * 
	 * @author emanon
	 */
	private void createPopupMenu() {
		List cols = new ArrayList();
		for (Iterator entries = mapTableColumnColumnKeeper.entrySet()
				.iterator(); entries.hasNext();) {
			Map.Entry nextEntry = (Entry) entries.next();
			cols.add(nextEntry);
		}
		Collections.sort(cols, new Comparator() {
			public int compare(Object o1, Object o2) {
				TableColumn col1 = (TableColumn) ((Entry) o1).getKey();
				TableColumn col2 = (TableColumn) ((Entry) o2).getKey();
				int order1 = getDefaultOrder((String) col1.getHeaderValue());
				int order2 = getDefaultOrder((String) col2.getHeaderValue());
				return order1 < order2 ? -1 : 1;
			}
		});
		popupMenu = new JPopupMenu();
		TableColumnModel tcModel = this.getTable().getColumnModel();
		for (int i = 0; i < cols.size(); i++) {
			Map.Entry nextEntry = (Entry) cols.get(i);
			TableColumn column = (TableColumn) nextEntry.getKey();
			JCheckBoxMenuItem jcbmi = new JCheckBoxMenuItem(column
					.getHeaderValue().toString());
			ColumnKeeper ck = (ColumnKeeper) nextEntry.getValue();
			jcbmi.setSelected(ck.isShown);
			jcbmi.addActionListener(ck);
			popupMenu.add(jcbmi);
		}
		popupMenu.addSeparator();

		JMenuItem jmiAddColumn = new JMenuItem(language
				.getText("addCustomColumn"));
		jmiAddColumn.setIcon(new ImageIcon(getClass().getResource(
				"/icons/addCol_16.gif")));
		jmiAddColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myUIfacade.getUndoManager().undoableEdit("PopUpNewColumn",
						new Runnable() {
							public void run() {
								CustomColumn customColumn = new CustomColumn();
								GanttDialogCustomColumn d = new GanttDialogCustomColumn(
										myUIfacade, customColumn);
								d.setVisible(true);
								if (d.isOk()) {
									getProject().getTaskCustomColumnManager()
											.addNewCustomColumn(customColumn);
								}
							}
						});
			}
		});

		JMenuItem jmiDisplayAll = new JMenuItem(language.getText("displayAll"));
		jmiDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getGanttProjectSingleton().getUndoManager()
						.undoableEdit("displayAllColumns", new Runnable() {
							public void run() {
								displayAllColumns();
							}
						});

			}
		});

		/*
		 * To delete a custom column the user has to right click on the column
		 * header. If the colum header match with a custom column the menu item
		 * will be enable. Otherwise it is disable.
		 */
		jmiDeleteColumn = new JMenuItem(language.getText("deleteCustomColumn"));
		jmiDeleteColumn.setIcon(new ImageIcon(getClass().getResource(
				"/icons/removeCol_16.gif")));
		jmiDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myUIfacade.getUndoManager().undoableEdit("deleteCustomColumn",
						new Runnable() {
							public void run() {
								// deleteCustomColumn(getTable().getColumnName(
								// getTable().columnAtPoint(clickPoint)));
								int ind = getTable().columnAtPoint(clickPoint);
								if (ind >= 0) {
									getProject().getTaskCustomColumnManager()
											.deleteCustomColumn(
													getTable().getColumnName(
															ind));
									jmiDeleteColumn.setEnabled(false);
								}
							}
						});

			}
		});
		jmiDeleteColumn.setEnabled(false);

		popupMenu.add(jmiDisplayAll);
		// 删除添加定制列,删除列
		/*
		 * popupMenu.addSeparator(); popupMenu.add(jmiAddColumn);
		 * popupMenu.add(jmiDeleteColumn);
		 */
	}

	/**
	 * Displays all the table columns.
	 */
	private void displayAllColumns() {
		Iterator it = mapTableColumnColumnKeeper.values().iterator();
		while (it.hasNext()) {
			ColumnKeeper ck = (ColumnKeeper) it.next();
			if (!ck.isShown)
				ck.show();
		}
		// createPopupMenu();
	}

	/**
	 * Displays all the table columns.
	 */
	private void hideAllColumns() {
		Iterator it = mapTableColumnColumnKeeper.values().iterator();
		while (it.hasNext()) {
			ColumnKeeper ck = (ColumnKeeper) it.next();
			if (ck.isShown)
				ck.hide();
		}
		// createPopupMenu();
	}

	/**
	 * Display the column whose name is given in parameter.
	 * 
	 * @param name
	 *            Name of the column to display.
	 */
	private void displayColumn(String name) {
		int indexView = -1;
		int width = -1;
		Iterator itDc = this.listDisplayedColumns.iterator();
		while (itDc.hasNext()) {
			DisplayedColumn dc = (DisplayedColumn) itDc.next();
			if (getNameForId(dc.getID()).equals(name)) {
				indexView = dc.getOrder();
				width = dc.getWidth();
				break;
			}
		}

		Iterator it = mapTableColumnColumnKeeper.keySet().iterator();
		while (it.hasNext()) {
			TableColumn c = (TableColumn) it.next();
			String n = (String) c.getHeaderValue();
			if (n.equals(name)) {
				ColumnKeeper ck = (ColumnKeeper) mapTableColumnColumnKeeper
						.get(c);
				if (indexView != -1)
					ck.index = indexView;
				if (!ck.isShown)
					ck.show();
				break;
			}
		}

		// getTable().getColumnExt(name).setPreferredWidth(width);

		// createPopupMenu();
	}

	private void hideColumn(String name) {
		Iterator it = mapTableColumnColumnKeeper.keySet().iterator();
		while (it.hasNext()) {
			TableColumn c = (TableColumn) it.next();
			String n = (String) c.getHeaderValue();
			if (n.equals(name)) {
				ColumnKeeper ck = (ColumnKeeper) mapTableColumnColumnKeeper
						.get(c);
				if (ck.isShown)
					ck.hide();
				break;
			}
		}
		// createPopupMenu();
	}

	/**
	 * Adds a new custom column. The custom column will affect all tasks and
	 * future tasks. Several types are available for the custom columns (string,
	 * date, integer, double, boolean). A default value is also set.
	 */
	public void addNewCustomColumn(CustomColumn customColumn) {
		if (customColumn == null) {
			customColumn = new CustomColumn();
			GanttDialogCustomColumn d = new GanttDialogCustomColumn(myUIfacade,
					customColumn);
			d.setVisible(true);
		}

		if (customColumn.getName() != null) // if something has been entered
		{
			GanttTreeTableModel treeTableModel = (GanttTreeTableModel) getTreeTableModel();
			int nbCol = treeTableModel.getColumnCountTotal(); // +
			// treeTableModel.getCustomColumnCount();
			String newName = customColumn.getName();

			((GanttTreeTableModel) ttModel).addCustomColumn(newName);

			TaskContainmentHierarchyFacade tchf = getProject().getTaskManager()
					.getTaskHierarchy();
			setCustomColumnValueToAllNestedTask(tchf, tchf.getRootTask(),
					customColumn.getName(), customColumn.getDefaultValue());

			TableColumnExt t = null;
			// if (GanttTreeTableModelExt.strColTaskName.equals(newName)) {
			// // 如果是任务名称列，则该列需使用树状编辑器，故单独传入一个特别的数字，此处使用-1004
			// // GanttTreeTableModel类中getColumnName()判断该数字做特别处理
			// // add by emanon
			// t = newTableColumnExt(-1004);
			// } else {
			t = newTableColumnExt(nbCol);
			// }
			t.setMaxWidth(500);
			t.setPreferredWidth(getDefaultWidth(customColumn.getName()));
			t.setHeaderValue(newName);
			getTable().getColumnModel().addColumn(t);
			// try {
			// if (clickPoint != null)
			// getTable().getColumnModel().moveColumn(
			// getTable().getColumnCount() - 1,
			// getTable().columnAtPoint(clickPoint));
			// } catch (IllegalArgumentException e) {
			// if (!GPLogger.log(e)) {
			// e.printStackTrace(System.err);
			// }
			// }
			// int align = SwingConstants.CENTER;
			Class classType = customColumn.getType();
			// if (classType.equals(GregorianCalendar.class)
			// || classType.equals(BigDecimal.class))
			int align = getDefaultAlign(newName);
			// else if (classType.equals(GregorianCalendar.class))
			// align = SwingConstants.RIGHT;
			if (!GanttTreeTableModelExt.strColTaskName.equals(newName)
					&& align != -1) {
				setColumnHorizontalAlignment(newName, align);
			}

			DisplayedColumn dc = new DisplayedColumn(getProject()
					.getCustomColumnsStorage().getIdFromName(newName));
			dc.setOrder(getDefaultOrder(newName));
			dc.setDisplayed(getDefaultDisplay(newName));
			dc.setWidth(getColumn(newName).getPreferredWidth());
			// if (dc.getOrder() > 6) {
			this.listDisplayedColumns.add(dc);
			// }

			if (GregorianCalendar.class.isAssignableFrom(classType))
				getTable().getColumnExt(newName).setCellEditor(
						newDateCellEditor());

			//
			JCheckBoxMenuItem jcbmi = new JCheckBoxMenuItem(newName);
			jcbmi.setSelected(true);
			ColumnKeeper ck = new ColumnKeeper(t);
			jcbmi.addActionListener(ck);
			mapTableColumnColumnKeeper.put(t, ck);
			getProject().setModified();

			// if (!dc.displayed) {
			// hideColumn(newName);
			// }
		}

		Runnable t = new Runnable() {
			public void run() {
				calculateWidth();
				revalidate();
			}
		};
		SwingUtilities.invokeLater(t);

	}

	/**
	 * Delete permanently a custom column.
	 * 
	 * @param name
	 *            Name of the column to delete.
	 */
	public void deleteCustomColumn(CustomColumn column) {

		final String name = column.getName();
		// the column has to be displayed to be removed.
		this.displayColumn(name);

		deleteColumnFromUI(name);
		// Every tasks
		TaskContainmentHierarchyFacade tchf = Mediator
				.getGanttProjectSingleton().getTaskManager().getTaskHierarchy();
		tchf.getRootTask().getCustomValues().removeCustomColumn(name);
		removeCustomColumnToAllNestedTask(tchf, tchf.getRootTask(), name);

		Mediator.getGanttProjectSingleton().setAskForSave(true);
	}

	private void deleteColumnFromUI(String name) {
		// DisplayedColumn toDel = null;
		Iterator it = listDisplayedColumns.iterator();

		while (it.hasNext()) {
			DisplayedColumn dc = (DisplayedColumn) it.next();
			if (getNameForId(dc.getID()).equals(name)) {
				it.remove();
				break;
			}
		}

		int index = getTable().getColumnModel().getColumnIndex(name);
		int modelIndex = getTable().convertColumnIndexToModel(index);
		TableColumnModelEvent tcme = new TableColumnModelEvent(getTable()
				.getColumnModel(), modelIndex, modelIndex);
		getTable().removeColumn(getTable().getColumnExt(name));
		getTable().columnRemoved(tcme);
		/*
		 * TODO There is a problem here : I don't remove the custom column from
		 * the treetablemodel. If I remove it there will be a problem when
		 * deleting a custom column if it isn't the last created.
		 */
		// TreeTableModel
		ttModel.deleteCustomColumn(name);

		// newBB
		Iterator it2 = mapTableColumnColumnKeeper.keySet().iterator();
		while (it2.hasNext()) {
			TableColumn c = (TableColumn) it2.next();
			String n = (String) c.getHeaderValue();
			if (n.equals(name)) {
				mapTableColumnColumnKeeper.remove(c);
				break;
			}
		}
	}

	public void renameCustomcolumn(String name, String newName) {
		this.displayColumn(name);
		TableColumnExt tc = (TableColumnExt) getTable().getColumn(name);
		tc.setTitle(newName);
		tc.setIdentifier(newName);

		TaskContainmentHierarchyFacade tchf = Mediator
				.getGanttProjectSingleton().getTaskManager().getTaskHierarchy();
		tchf.getRootTask().getCustomValues().renameCustomColumn(name, newName);
		renameCustomColumnForAllNestedTask(tchf, tchf.getRootTask(), name,
				newName);
		ttModel.renameCustomColumn(name, newName);

		// newBB
		Iterator it = mapTableColumnColumnKeeper.keySet().iterator();
		while (it.hasNext()) {
			TableColumn c = (TableColumn) it.next();
			String n = (String) c.getHeaderValue();
			if (n.equals(name)) {
				ColumnKeeper ck = (ColumnKeeper) mapTableColumnColumnKeeper
						.get(c);
				((TableColumnExt) c).setTitle(newName);
				break;
			}
		}

		// assert getColumn(newName)!=null;
	}

	// public void changeDefaultValue(String name, Object newDefaultValue)
	// {
	// // this.displayColumn(name);
	// }

	/**
	 * @param facade
	 *            TaskContainmentHierarchyFacade ot retrive nested tasks.
	 * @param root
	 *            Root task to start with.
	 * @param colName
	 *            Name of the new custom column to add to the tasks.
	 * @param value
	 *            Value for this new custom column.
	 */
	private void setCustomColumnValueToAllNestedTask(
			TaskContainmentHierarchyFacade facade, Task root, String colName,
			Object value) {
		try {
			root.getCustomValues().setValue(colName, value);
		} catch (CustomColumnsException e) {
			if (!GPLogger.log(e)) {
				e.printStackTrace(System.err);
			}
		}
		Task[] tt = facade.getNestedTasks(root);
		for (int i = 0; i < tt.length; i++) {
			try {
				tt[i].getCustomValues().setValue(colName, value);
			} catch (CustomColumnsException e) {
				if (!GPLogger.log(e)) {
					e.printStackTrace(System.err);
				}
			}
			setCustomColumnValueToAllNestedTask(facade, tt[i], colName, value);
		}
	}

	/**
	 * Remove permanetly the custom column for the task <code>root</code> and
	 * all its children.
	 * 
	 * @param facade
	 *            TaskContainmentHierarchyFacade ot retrive nested tasks.
	 * @param root
	 *            Root task to start with.
	 * @param colName
	 *            Name of the custom column to remove.
	 */
	private void removeCustomColumnToAllNestedTask(
			TaskContainmentHierarchyFacade facade, Task root, String colName) {
		// root.getCustomValues().removeCustomColumn(colName);

		Task[] tt = facade.getNestedTasks(root);
		for (int i = 0; i < tt.length; i++) {
			tt[i].getCustomValues().removeCustomColumn(colName);
			removeCustomColumnToAllNestedTask(facade, tt[i], colName);
		}
	}

	private void renameCustomColumnForAllNestedTask(
			TaskContainmentHierarchyFacade facade, Task root, String oldName,
			String newName) {
		// root.getCustomValues().renameCustomColumn(oldName,newName);

		Task[] tt = facade.getNestedTasks(root);
		for (int i = 0; i < tt.length; i++) {
			tt[i].getCustomValues().renameCustomColumn(oldName, newName);
			renameCustomColumnForAllNestedTask(facade, tt[i], oldName, newName);
		}
	}

	/**
	 * Changes the language. The table headers change and also the menu items.
	 * For the moment when the user changes the language every columns are
	 * redisplayed (even if they were hidden).
	 * 
	 * @param ganttLanguage
	 */
	public void languageChanged(GanttLanguage.Event changeEvent) {
		TableColumnModel tcm = this.getTable().getColumnModel();
		reloadColumns();
		// this.createPopupMenu();
		// Component tcomp[] = popupMenu.getComponents();
		//
		// // The following is to check in popup menu those columns which were
		// checked
		// // before language change
		// for (int i = 0; i < tcomp.length; i++) {
		// if (tcomp[i] instanceof JCheckBoxMenuItem) {
		// JCheckBoxMenuItem c = (JCheckBoxMenuItem) tcomp[i];
		// // The following isn't well done...
		// // catching an exception to set the menu slected or not is
		// // ugly...
		// // TODO make this more beautiful
		// try {
		// c
		// .setSelected(((ColumnKeeper) mapTableColumnColumnKeeper
		// .get(tcm.getColumn(tcm.getColumnIndex(c
		// .getText())))).isShown);
		// } catch (IllegalArgumentException e) {
		// c.setSelected(false);
		// }
		// }
		// }
	}

	String getIdForName(String colName) {
		String id = null;

		if (colName.equals("序号"))
			id = "taskID";
		else if (colName.equals("状态"))
			id = "state";
		else if (colName.equals("任务类别"))
			id = "taskType";
		else if (colName.equals(GanttTreeTableModelExt.strColWBSNumber))
			id = "number";
		else if (colName.equals(GanttTreeTableModelExt.strColTaskName))
			id = "name";
		else if (colName.equals(GanttTreeTableModelExt.strColEffectTimes))
			id = "effectTimes";
		else if (colName.equals(GanttTreeTableModel.strColBegDate))
			id = "beginDate";
		else if (colName.equals(GanttTreeTableModel.strColEndDate))
			id = "endDate";
		else if (colName.equals("考核日期"))
			id = "checkDate";
		else if (colName.equals(GanttTreeTableModelExt.strColPrefixNode))
			id = "prefixNode";
		else if (colName.equals(GanttTreeTableModelExt.stradminPerson))
			id = "adminPerson";
		else if (colName.equals(GanttTreeTableModelExt.strColAdminDept))
			id = "adminDept";
		else if (colName.equals("业务类型"))
			id = "bizType";
		else if (colName.equals(GanttTreeTableModelExt.strColCompletePercent))
			id = "complete";
		else if (colName.equals("实际开始日期"))
			id = "actualStartDate";
		else if (colName.equals(GanttTreeTableModelExt.strColActualEndDate))
			id = "actualEndDate";
		else if (colName.equals("延迟天数"))
			id = "delayDay";
		else if (colName.equals("累计完工工程量(元)"))
			id = "workLoad";
		else if (colName.equals("成果类别"))
			id = "achievementType";
		else if (colName.equals("任务指引"))
			id = "standardTask";
		else if (colName.equals("进度评价人"))
			id = "planEvaluatePerson";
		else if (colName.equals("质量评价人"))
			id = "qualityEvaluatePerson";
		else if (colName.equals("协助人"))
			id = "helpPerson";
		else if (colName.equals("协助部门"))
			id = "helpDept";

		// if (colName.equals(GanttTreeTableModel.strColType))
		// id = "tpd0";
		// else if (colName.equals(GanttTreeTableModel.strColPriority))
		// id = "tpd1";
		// else if (colName.equals(GanttTreeTableModel.strColInfo))
		// id = "tpd2";
		// else if (colName.equals(GanttTreeTableModel.strColName))
		// id = "tpd3";
		// else if (colName.equals(GanttTreeTableModel.strColBegDate))
		// id = "tpd4";
		// else if (colName.equals(GanttTreeTableModel.strColEndDate))
		// id = "tpd5";
		// else if (colName.equals(GanttTreeTableModel.strColDuration))
		// id = "tpd6";
		// else if (colName.equals(GanttTreeTableModel.strColCompletion))
		// id = "tpd7";
		// else if (colName.equals(GanttTreeTableModel.strColCoordinator))
		// id = "tpd8";
		// else if (colName.equals(GanttTreeTableModel.strColPredecessors))
		// id = "tpd9";
		// else if (colName.equals(GanttTreeTableModel.strColID))
		// id = "tpd10";
		else
			id = getProject().getCustomColumnsStorage().getIdFromName(colName);
		return id;
	}

	private String getNameForId(String id) {
		String name = null;
		if (id.equals("taskID"))
			name = "序号";
		else if (id.equals("state"))
			name = "状态";
		else if (id.equals("taskType"))
			name = "任务类别";
		else if (id.equals("number"))
			name = GanttTreeTableModelExt.strColWBSNumber;
		else if (id.equals("name"))
			name = GanttTreeTableModelExt.strColTaskName;
		else if (id.equals("effectTimes"))
			name = GanttTreeTableModelExt.strColEffectTimes;
		else if (id.equals("beginDate"))
			name = GanttTreeTableModel.strColBegDate;
		else if (id.equals("endDate"))
			name = GanttTreeTableModel.strColEndDate;
		else if (id.equals("checkDate"))
			name = "考核日期";
		else if (id.equals("prefixNode"))
			name = GanttTreeTableModelExt.strColPrefixNode;
		else if (id.equals("adminPerson"))
			name = GanttTreeTableModelExt.stradminPerson;
		else if (id.equals("adminDept"))
			name = GanttTreeTableModelExt.strColAdminDept;
		else if (id.equals("bizType"))
			name = "业务类型";
		else if (id.equals("complete"))
			name = GanttTreeTableModelExt.strColCompletePercent;
		else if (id.equals("actualStartDate"))
			name = "实际开始日期";
		else if (id.equals("actualEndDate"))
			name = GanttTreeTableModelExt.strColActualEndDate;
		else if (id.equals("delayDay"))
			name = "延迟天数";
		else if (id.equals("workLoad"))
			name = "累计完工工程量(元)";
		else if (id.equals("achievementType"))
			name = "成果类别";
		else if (id.equals("standardTask"))
			name = "任务指引";
		else if (id.equals("planEvaluatePerson"))
			name = "进度评价人";
		else if (id.equals("qualityEvaluatePerson"))
			name = "质量评价人";
		else if (id.equals("helpPerson"))
			name = "协助人";
		else if (id.equals("helpDept"))
			name = "协助部门";
		else
			name = getProject().getCustomColumnsStorage().getNameFromId(id);
		return name;
	}

	/**
	 * Returns the JTree used in the treetable.
	 * 
	 * @return The JTree used in the treetable.
	 */
	public JTree getTree() {
		return this.getTreeTable().getTree();
	}

	/**
	 * Returns the vertical scrollbar.
	 * 
	 * @return The vertical scrollbar.
	 */
	public JScrollBar getVerticalScrollBar() {
		return scrollPane.getVerticalScrollBar();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * 
	 * @inheritDoc
	 */
	// public void requestFocus() {
	// if (getDisplayColumns().isDisplayed(GanttTreeTableModel.strColName)) {
	// int c = getTable().convertColumnIndexToView(
	// getColumn(GanttTreeTableModel.strColName).getModelIndex());
	// NameCellEditor ed = (NameCellEditor) getTable()
	// .getCellEditor(-1, c);
	// ed.requestFocus();
	// }
	// }
	public void centerViewOnSelectedCell() {
		// int row = getTable().getSelectedRow();
		// int col = getTable().getEditingColumn();
		// if (col == -1)
		// col = getTable().getSelectedColumn();
		// Rectangle rect = getTable().getCellRect(row, col, true);
		// scrollPane.getHorizontalScrollBar().scrollRectToVisible(rect);
		// scrollPane.getViewport().scrollRectToVisible(rect);

	}

	public void addMouseListener(MouseListener mouseListener) {
		super.addMouseListener(mouseListener);
		getTable().addMouseListener(mouseListener);
		getTree().addMouseListener(mouseListener);
		this.getTreeTable().getParent().addMouseListener(mouseListener);
	}

	/**
	 * Adds the key listener to the Table, the tree and this.
	 */
	public void addKeyListener(KeyListener keyListener) {
		super.addKeyListener(keyListener);
		getTable().addKeyListener(keyListener);
		getTree().addKeyListener(keyListener);
	}

	void setDelay(TaskNode taskNode, Delay delay) {
		// try {
		// int indexInfo = getTable().getColumnModel().getColumnIndex(
		// GanttTreeTableModel.strColInfo);
		// indexInfo = getTable().convertColumnIndexToModel(indexInfo);
		// ttModel.setValueAt(delay, taskNode, indexInfo);
		// } catch (IllegalArgumentException e) {
		// }
	}

	/**
	 * 此处返回一个列名所对应的默认列宽
	 * <p>
	 * 以后列宽应该移到scheduletaskproperty中维护，此处写死
	 * 
	 * @param colName
	 * @return
	 */
	public static int getDefaultWidth(String colName) {
		if (FDCHelper.isEmpty(colName)) {
			return 100;
		}
		ActivityCache cache = ActivityCache.getInstance();
		Map columnsWidth = cache.getcolumnsWidth();
		if (columnsWidth != null) {
			if (columnsWidth.containsKey(colName)) {
				return ((Integer) columnsWidth.get(colName)).intValue();
			} else {
				return 100;
			}
		}
		return 100;

	}

	public static int getDefaultOrder(String colName) {
		if (FDCHelper.isEmpty(colName)) {
			return -1;
		}
		ActivityCache cache = ActivityCache.getInstance();
		Map columnsOrder = cache.getColumnsOrder();
		if (columnsOrder != null) {
			if (columnsOrder.containsKey(colName)) {
				return ((Integer) columnsOrder.get(colName)).intValue();
			} else {
				return -1;
			}
		}
		return -1;
	}
	
	public static int getDefaultAlign(String colName) {
		if (FDCHelper.isEmpty(colName)) {
			return -1;
		}
		ActivityCache cache = ActivityCache.getInstance();
		Map columnsAlign = cache.getColumnsAlign();
		if (columnsAlign != null) {
			if (columnsAlign.containsKey(colName)) {
				return ((Integer) columnsAlign.get(colName)).intValue();
			} else {
				return -1;
			}
		}
		return -1;
	}

	public boolean getDefaultDisplay(String colName) {
		if (FDCHelper.isEmpty(colName)) {
			return false;
		}
		ActivityCache cache = ActivityCache.getInstance();
		Map displayColumns = cache.getDisplayColumns(uiMark);
		if (displayColumns != null) {
			if (displayColumns.containsKey(colName)) {
				return ((Boolean) displayColumns.get(colName)).booleanValue();
			} else {
				return false;
			}
		}
		return false;
	}

	public void setUIMark(int uiMark) {
		this.uiMark = uiMark;
	}

	/*
	 * ----- INNER CLASSES -----
	 */

	public class DisplayedColumnsList extends ArrayList {
		public DisplayedColumnsList() {
			super();
		}

		/**
		 * Returns <code>true</code> if the column name <code>name</code> is
		 * displayed, <code>false</code> otherwise.
		 * 
		 * @param name
		 *            Name of the column to check the display.
		 * @return <code>true</code> if the column name <code>name</code> is
		 *         displayed, <code>false</code> otherwise.
		 */
		public boolean isDisplayed(String name) {
			Iterator it = this.iterator();
			while (it.hasNext()) {
				DisplayedColumn dc = (DisplayedColumn) it.next();
				if (getNameForId(dc.getID()).equals(name))
					return dc.isDisplayed();
			}
			return false;
		}

		public int getOrderForName(String name) {
			Iterator it = this.iterator();
			while (it.hasNext()) {
				DisplayedColumn dc = (DisplayedColumn) it.next();
				if (getNameForId(dc.getID()).equals(name))
					return dc.getOrder();
			}
			return -1;
		}

		public String getNameForOrder(int order) {
			Iterator it = this.iterator();
			while (it.hasNext()) {
				DisplayedColumn dc = (DisplayedColumn) it.next();
				if (dc.getOrder() == order)
					return getNameForId(dc.getID());
			}
			return null;
		}

		public boolean add(Object o) {
			if (o instanceof DisplayedColumn) {
				DisplayedColumn dc1 = (DisplayedColumn) o;
				Iterator it = this.iterator();
				while (it.hasNext()) {
					DisplayedColumn dc = (DisplayedColumn) it.next();
					if (dc.getID().equals(dc1.getID())) {
						this.remove(dc);
						return super.add(dc1);
					}
				}
				return super.add(dc1);

			}
			return false;
		}

		public Object clone() {
			DisplayedColumnsList l = new DisplayedColumnsList();
			Iterator it = this.iterator();
			while (it.hasNext())
				l.add(((DisplayedColumn) it.next()).clone());

			return l;

		}

	}

	public class DisplayedColumn implements Comparable,
			TableHeaderUIFacade.Column {
		private String id = null;

		private boolean displayed = false;

		private int order = -1;

		private int width = 0;

		public DisplayedColumn(String id) {
			this.id = id;
		}

		public void setID(String id) {
			this.id = id;
		}

		public void setDisplayed(boolean disp) {
			this.displayed = disp;
		}

		public boolean isDisplayed() {
			return this.displayed;
		}

		public boolean isVisible() {
			return isDisplayed();
		}

		public String getName() {
			return getNameForId(getID());
		}

		public String getID() {
			return this.id;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public int getOrder() {
			return this.order;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public Object clone() {
			DisplayedColumn dc = new DisplayedColumn(this.id);
			dc.setDisplayed(this.isDisplayed());
			dc.setOrder(this.getOrder());
			dc.setWidth(this.getWidth());
			return dc;
		}

		public String toString() {
			return "[ID = " + id + ", displayed = " + displayed + ", order = "
					+ order + "]";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(Object o) {
			if (o == null)
				return 0;
			if (o instanceof DisplayedColumn) {
				DisplayedColumn dc = (DisplayedColumn) o;
				if (this.order != dc.order)
					return this.order - dc.order;
				return this.id.compareTo(dc.id);
			}
			return 0;
		}
	}

	/**
	 * This actionListener manages the column to be hiden or displayed. It has a
	 * TableColumn and hide it or display it
	 * 
	 * @author bbaranne Mar 1, 2005
	 */
	class ColumnKeeper implements ActionListener {
		/**
		 * the initial index of the table column.
		 */
		private int index;

		/**
		 * True if the table column is displayed, false otherwise.
		 */
		private boolean isShown = true;

		/**
		 * The managed table column.
		 */
		protected TableColumn column;

		/**
		 * Creates a new ColumnKeeper for the given TableColumn.
		 * 
		 * @param tc
		 *            TableColumn to manage.
		 */
		public ColumnKeeper(TableColumn tc) {
			column = tc;
			index = column.getModelIndex();
		}

		/**
		 * Set the initial index of the table column.
		 * 
		 * @param initIndex
		 *            The initial index of the table column.
		 */
		public void setInitIndex(int initIndex) {
			index = initIndex;
		}

		/**
		 * Returns the initial index of the table column.
		 * 
		 * @return The initial index of the table column.
		 */
		public int getInitIndex() {
			return index;
		}

		/**
		 * Hides the table column.
		 */
		void hide() {
			getTable().getColumnModel().removeColumn(column);
			isShown = false;

			String name = (String) column.getHeaderValue();

			String id = getIdForName(name);
			Iterator it = listDisplayedColumns.iterator();
			while (it.hasNext()) {
				DisplayedColumn dc = (DisplayedColumn) it.next();
				if (dc.getID().equals(id)) {
					dc.setDisplayed(false);
					break;
				}
			}
			// Thread t = new Thread(){
			// public void run(){
			calculateWidth();
			revalidate();
			// }
			// };
			// SwingUtilities.invokeLater(t);
		}

		/**
		 * Shows the table column.
		 */
		void show() {
			boolean reloadInfo = false;
			getTable().getColumnModel().addColumn(column);
			try {
				// 计算应该显示的位置，判断在它之前有多少列是显示的
				// edit by emanon
				int columnViewIndexOld = 0;
				int dfOrder = getDefaultOrder((String) column.getIdentifier());
				for (int i = 0; i < listDisplayedColumns.size(); i++) {
					DisplayedColumn dc = (DisplayedColumn) listDisplayedColumns
							.get(i);
					if (dc.order < dfOrder) {
						if (dc.displayed) {
							columnViewIndexOld++;
						}
					} else {
						break;
					}
				}
				int columnModelIndexActual = column.getModelIndex();
				if (column.getIdentifier().equals(
						GanttTreeTableModel.strColInfo))
					reloadInfo = true;
				int columnViewIndexActual = getTable()
						.convertColumnIndexToView(columnModelIndexActual);
				getTable()
						.moveColumn(columnViewIndexActual, columnViewIndexOld);
			} catch (IllegalArgumentException e) {
				index = getTable().getModel().getColumnCount() - 1;
			}
			isShown = true;

			String name = (String) column.getHeaderValue();
			String id = getIdForName(name);
			boolean found = false;
			Iterator it = listDisplayedColumns.iterator();
			while (it.hasNext()) {
				DisplayedColumn dc = (DisplayedColumn) it.next();
				if (dc.getID().equals(id)) {
					dc.setDisplayed(true);
					found = true;
					break;
				}
			}
			if (!found && id != null) {
				DisplayedColumn dc = new DisplayedColumn(id);
				dc.setDisplayed(true);
				listDisplayedColumns.add(dc);
			}

			if (reloadInfo)
				if (Mediator.getDelayManager() != null)
					Mediator.getDelayManager().fireDelayObservation();

			// Thread t = new Thread(){
			// public void run(){
			calculateWidth();
			revalidate();
			// }
			// };
			// SwingUtilities.invokeLater(t);

		}

		public void actionPerformed(ActionEvent e) {
			Mediator.getGanttProjectSingleton().getUndoManager().undoableEdit(
					"HIDE OR SHOW A COLUMN", new Runnable() {
						public void run() {
							if (!isShown)
								show();
							else
								hide();
							getTable().repaint();
						}
					});
		}
	}

	/**
	 * This class handles the mouse actions on the treetable header.
	 * 
	 * @author bbaranne Mar 1, 2005
	 * @version 1.0 Show the popupMenu when popup is triggered.
	 */
	class HeaderMouseListener extends MouseAdapter {
		/**
		 * Creates a new HeaderMouseListener
		 */
		public HeaderMouseListener() {
			super();
		}

		/*
		 * Something ugly !! TODO find a means to display the popupmenu with the
		 * right UI.
		 */
		boolean first = false;

		/**
		 * @inheritDoc Shows the popupMenu to hide/show columns and to add
		 *             custom columns.
		 */
		public void mousePressed(MouseEvent e) {
			handlePopupTrigger(e);
		}

		public void mouseReleased(MouseEvent e) {
			handlePopupTrigger(e);
		}

		private void handlePopupTrigger(MouseEvent e) {
			if (e.isPopupTrigger()) {
				createPopupMenu();
				Component c = (Component) e.getSource();
				// reorderPopuMenu();
				popupMenu.show(c, e.getX(), e.getY());
				clickPoint = e.getPoint();// popupMenu.getLocationOnScreen();
				CustomColumn cc = getProject()
						.getCustomColumnsStorage()
						.getCustomColumn(
								getTable().getColumnName(
										getTable().columnAtPoint(e.getPoint())));
				if (cc != null)
					jmiDeleteColumn.setEnabled(true);
				else
					jmiDeleteColumn.setEnabled(false);
			}
		}
	}

	/**
	 * The class replaces the cell editor used in the hierarchical column of the
	 * tree table.
	 * 
	 * @author bbaranne (Benoit Baranne)
	 */
	public class NameCellEditor extends DefaultCellEditor {
		private JTextField field = null;

		public NameCellEditor() {
			super(new JTextField());
			field = (JTextField) this.editorComponent;
		}

		public Component getTableCellEditorComponent(JTable arg0, Object arg1,
				boolean arg2, int arg3, int arg4) {
			final JTextField result = (JTextField) super
					.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
			result.selectAll();
			// result.addFocusListener(new FocusAdapter() {
			// public void focusGained(FocusEvent arg0) {
			// super.focusGained(arg0);
			// ((JTextComponent)result).selectAll();
			// }
			//
			// public void focusLost(FocusEvent arg0) {
			// // TODO Auto-generated method stub
			// super.focusLost(arg0);
			// }
			//
			// });
			//
			return result;
		}

		//
		public void requestFocus() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					field.requestFocus();
					field.selectAll();
				}
			});
		}
	}

	/**
	 * This class repaints the GraphicArea and the table every time the table
	 * model has been modified. TODO Add the refresh functionnality when
	 * available.
	 * 
	 * @author Benoit Baranne
	 */
	class ModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent e) {
			// Mediator.getGanttProjectSingleton().getArea().repaint();
			// getTable().repaint();
			if (Mediator.getGanttProjectSingleton() != null) {
				Mediator.getGanttProjectSingleton().repaint();
			}
		}
	}

	public void editNewTask(Task t) {
		TreePath selectedPath = getTree().getSelectionPath();
		int modelColumnIndex = getTable().getColumn(
				GanttTreeTableModelExt.strColTaskName).getModelIndex();
		int c = -1;
		TableColumnModel cm = getTable().getColumnModel();
		for (int column = 0; column < getTable().getColumnCount(); column++) {
			if (cm.getColumn(column).getModelIndex() == modelColumnIndex) {
				c = column;
				break;
			}
		}
		// NameCellEditor nameCellEditor = (NameCellEditor)
		// getTable().getCellEditor(-1, c);
		getTreeTable().editCellAt(getTree().getRowForPath(selectedPath), c);
		// nameCellEditor.requestFocus();
	}

	public void customColumsChange(CustomColumEvent event) {
		switch (event.getType()) {
		case CustomColumEvent.EVENT_ADD:
			addNewCustomColumn(event.getColumn());
			break;
		case CustomColumEvent.EVENT_REMOVE:
			deleteCustomColumn(event.getColumn());
			break;
		case CustomColumEvent.EVENT_RENAME:
			renameCustomcolumn(event.getOldName(), event.getColName());
			break;
		}
	}

	public TableHeaderUIFacade getVisibleFields() {
		return myVisibleFields;
	}

	class VisibleFieldsImpl implements TableHeaderUIFacade {
		public void add(String name, int order, int width) {
			DisplayedColumn newColumn = new DisplayedColumn(name);
			newColumn.setOrder(order);
			if (width >= 0) {
				newColumn.setWidth(width);
			}
			newColumn.setDisplayed(true);
			DisplayedColumnsList clone = (DisplayedColumnsList) getDisplayColumns()
					.clone();
			clone.add(newColumn);
			setDisplayedColumns(clone);
		}

		public void clear() {
			setDisplayedColumns(new DisplayedColumnsList());
		}

		public Column getField(int index) {
			return (Column) getDisplayColumns().get(index);
		}

		public int getSize() {
			return getDisplayColumns().size();
		}

		public void importData(TableHeaderUIFacade source) {
			clear();
			DisplayedColumnsList clone = (DisplayedColumnsList) getDisplayColumns()
					.clone();
			clone.clear();
			for (int i = 0; i < source.getSize(); i++) {
				Column nextField = source.getField(i);
				DisplayedColumn newColumn = new DisplayedColumn(nextField
						.getName());
				newColumn.setID(nextField.getID());
				newColumn.setOrder(nextField.getOrder());
				// newColumn.setWidth(nextField.getWidth());
				if (nextField.getWidth() >= 0) {
					newColumn.setWidth(nextField.getWidth());
				}
				newColumn.setDisplayed(getDefaultDisplay(nextField.getID()));
				clone.add(newColumn);
			}
			setDisplayedColumns(clone);
		}
	}
}
