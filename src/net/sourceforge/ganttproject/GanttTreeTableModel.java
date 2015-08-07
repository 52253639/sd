package net.sourceforge.ganttproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.sourceforge.ganttproject.delay.Delay;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.language.GanttLanguage.Event;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskInfo;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySlice;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.StartStartConstraintImpl;

import org.jdesktop.swing.treetable.DefaultTreeTableModel;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * 改写方法以提供锁定支持 isCellEditable
 * 
 * @author xiaohong_shi
 * 
 */
public class GanttTreeTableModel extends DefaultTreeTableModel implements
		TableColumnModelListener, TaskContainmentHierarchyFacade,
		GanttLanguage.Listener {

	private static GanttLanguage language = GanttLanguage.getInstance();
	private GanttTreeTableModelExt ganttTreeTableModelExt = new GanttTreeTableModelExt(
			this);
	public static String strColType = null;

	public static String strColPriority = null;

	public static String strColInfo = null;

	public static String strColName = null;

	public static String strColBegDate = null;

	public static String strColEndDate = null;

	public static String strColDuration = null;

	public static String strColCompletion = null;

	public static String strColCoordinator = null;

	public static String strColPredecessors = null;

	public static String strColID = null;

	/** The colums titles */
	public List titles = null;
	private GanttTreeTable treetable;

	/**
	 * Custom columns list.
	 */
	private Vector customColumns = null;

	/**
	 * Number of columns (presently in the model)
	 */
	private int nbCol = 5;

	/**
	 * Number of columns (at all, even hiden)
	 */
	private int nbColTot = nbCol;

	/**
	 * Creates an instance of GanttTreeTableModel with a root.
	 * 
	 * @param root
	 *            The root.
	 */
	public GanttTreeTableModel(TreeNode root) {
		super(root);
		titles = new ArrayList();
		customColumns = new Vector();
		changeLanguage(language);
	}

	public GanttTreeTable getTreetable() {
		return treetable;
	}

	public void setTreetable(GanttTreeTable treetable) {
		this.treetable = treetable;
	}

	/**
	 * Changes the language.
	 * 
	 * @param ganttLanguage
	 *            New language to use.
	 */
	public void changeLanguage(GanttLanguage ganttLanguage) {
		strColType = language.getText("tableColType");
		strColPriority = language.getText("tableColPriority");
		strColInfo = language.getText("tableColInfo");
		strColName = language.getText("tableColName");
		strColBegDate = language.getText("tableColBegDate");
		strColEndDate = language.getText("tableColEndDate");
		strColDuration = language.getText("tableColDuration");
		strColCompletion = language.getText("tableColCompletion");
		strColCoordinator = language.getText("tableColCoordinator");
		strColPredecessors = language.getText("tableColPredecessors");
		strColID = language.getText("tableColID");
		ganttTreeTableModelExt.afterChangeLanguage(ganttLanguage);

		titles.clear();
		String[] cols = new String[] { strColName, strColBegDate,
				strColEndDate, strColPredecessors,
				strColID };
		for (int i = 0; i < cols.length; i++)
			titles.add(new String(cols[i]));
	}

	/**
	 * Invoked this to insert newChild at location index in parents children.
	 * This will then message nodesWereInserted to create the appropriate event.
	 * This is the preferred way to add children as it will create the
	 * appropriate event.
	 */
	public void insertNodeInto(MutableTreeNode newChild,
			MutableTreeNode parent, int index) {
		parent.insert(newChild, index);

		int[] newIndexs = new int[1];

		newIndexs[0] = index;
		nodesWereInserted(parent, newIndexs);

	}

	/**
	 * Message this to remove node from its parent. This will message
	 * nodesWereRemoved to create the appropriate event. This is the preferred
	 * way to remove a node as it handles the event creation for you.
	 */
	public void removeNodeFromParent(MutableTreeNode node) {
		MutableTreeNode parent = (MutableTreeNode) node.getParent();

		if (parent == null)
			throw new IllegalArgumentException("node does not have a parent.");

		int[] childIndex = new int[1];
		Object[] removedArray = new Object[1];

		childIndex[0] = parent.getIndex(node);
		parent.remove(childIndex[0]);
		removedArray[0] = node;
		nodesWereRemoved(parent, childIndex, removedArray);
	}

	/**
	 * Add a custom column to the model.
	 * 
	 * @param title
	 */
	public void addCustomColumn(String title) {
		customColumns.add(title);
		nbColTot++;
	}

	/**
	 * Delete a custom column.
	 * 
	 * @param title
	 */
	public void deleteCustomColumn(String title) {
		customColumns.remove(title);
		this.columnRemoved(null);
		nbColTot--;
	}

	public void renameCustomColumn(String oldName, String newName) {
		customColumns.set(customColumns.indexOf(oldName), newName);
	}

	// /**
	// * Returns the number of custom columns.
	// * @return
	// */
	// public int getCustomColumnCount()
	// {
	// return customColumns.size();
	// }

	public int getColumnCount() {
		return nbCol;
	}

	public int getColumnCountTotal() {
		return nbColTot;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getColumnClass(int column) {
		String columnName = getColumnName(column);
		if (GanttTreeTableModel.strColType.equals(columnName)
				|| GanttTreeTableModel.strColPriority.equals(columnName)
				|| GanttTreeTableModel.strColInfo.equals(columnName)) {
			return Icon.class;
		} else if (GanttTreeTableModelExt.strColTaskName.equals(columnName)) {
			return hierarchicalColumnClass;
		} else if (GanttTreeTableModel.strColBegDate.equals(columnName)) {
			return GregorianCalendar.class;
		} else if (GanttTreeTableModel.strColEndDate.equals(columnName)) {
			return GregorianCalendar.class;
		} else if (GanttTreeTableModelExt.strColEffectTimes.equals(columnName)) {
			return BigDecimal.class;
		} else if (GanttTreeTableModelExt.strColCompletePercent
				.equals(columnName)) {
			return BigDecimal.class;
		} else if (GanttTreeTableModel.strColID.equals(columnName)) {
			return Integer.class;
		} else {
			TaskNode tn = (TaskNode) this.getRoot();
			Object o = this.getValueAt(tn, column);
			if (o == null) {
				o = "error";
			}
			return o.getClass();
		}
		
	}

	/**
	 * 列号取得列名
	 * <p>
	 * 如果是-1004，说明是做特殊处理的任务名称列
	 */
	public String getColumnName(int column) {
		if (column == -1004) {
			return GanttTreeTableModelExt.strColTaskName;
		}
		if (column < titles.size())
			try {
				return (String) titles.get(column);
			} catch (Exception ex) {
				return (String) titles.get(titles.size() - 1);
			}

		try {
			return (String) customColumns.get(column - titles.size());
		} catch (IndexOutOfBoundsException e) {
			return (String) customColumns.get(column - titles.size() - 1);
		}

	}

	/**
	 * @inheritDoc
	 */
	public boolean isCellEditable(Object node, int column) {
		if (node instanceof TaskNode) {
			Task task = (Task) ((TaskNode) node).getUserObject();
			if (task instanceof KDTask) {
				return ganttTreeTableModelExt.isKDTaskCellEditable(
						(KDTask) task, column);
			} else {
				// Gant控件
				switch (column) {
				case 5:
				case 6:
					return !task.isMilestone();
				case 2:
				case 8:
				case 9:
				case 10:
					return false;
				default:
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 取单元格值用于展示
	 * <p>
	 * 根据列名判断取值方法，做一些特殊处理
	 */
	public Object getValueAt(Object node, int column) {
		Object res = null;
		if (!(node instanceof TaskNode))
			return null;
		TaskNode tn = (TaskNode) node;
		if (!(tn.getUserObject() instanceof KDTask)) {
			return null;
		}
		KDTask task = (KDTask) tn.getUserObject();
		String columnName = getColumnName(column);
		if (GanttTreeTableModel.strColType.equals(columnName)) {
			if (task.isProjectTask()) {
				res = new ImageIcon(getClass().getResource(
						"/icons/mproject.gif"));
			} else if (!tn.isLeaf())
				res = new ImageIcon(getClass().getResource("/icons/mtask.gif"));
			else if (task.isMilestone())
				res = new ImageIcon(getClass()
						.getResource("/icons/meeting.gif"));
			else
				res = new ImageIcon(getClass().getResource("/icons/tasks2.png"));
		} else if (GanttTreeTableModel.strColPriority.equals(columnName)) {
			String path = (task.getPriority() == 0 ? "/icons/task1.gif" : task
					.getPriority() == 1 ? "/icons/task.gif"
					: "/icons/task2.gif");
			res = new ImageIcon(getClass().getResource(path));
		} else if (GanttTreeTableModel.strColInfo.equals(columnName)) {
			TaskInfo info = task.getTaskInfo();
			if (info != null) {
				if (info instanceof Delay) {
					int type = ((Delay) info).getType();
					if (type == Delay.NORMAL)
						res = new ImageIcon(getClass().getResource(
								"/icons/alert1_16.gif"));
					else if (type == Delay.CRITICAL)
						res = new ImageIcon(getClass().getResource(
								"/icons/alert2_16.gif"));
				}
			}
		} else if (GanttTreeTableModelExt.strColWBSNumber.equals(columnName)) {
			String longNumber = task.getScheduleTaskInfo().getLongNumber();
			if (FDCHelper.isEmpty(longNumber)) {
				res = "";
			} else {
				res = longNumber.replaceAll("!", ".");
			}
		} else if (GanttTreeTableModelExt.strColTaskName.equals(columnName)) {
			res = task.getScheduleTaskInfo().getName();
		} else if (GanttTreeTableModel.strColBegDate.equals(columnName)) {
			res = tn.getStart();
			// res = "1月1日啨忽然下起了大雪";
		} else if (GanttTreeTableModel.strColEndDate.equals(columnName)) {
			res = tn.getEnd();
		} else if (GanttTreeTableModelExt.strColPrefixNode.equals(columnName)) {
			// 取前置任务值，此处返回所有前置任务的序号字符串，方便迅速定位
			StringBuffer sb = new StringBuffer();
			TaskDependency[] dep = task.getDependenciesAsDependant().toArray();
			if (dep != null && dep.length > 0) {
				for (int i = 0; i < dep.length; i++) {
					int seq = ((KDTask) dep[i].getDependee())
							.getScheduleTaskInfo().getSeq();
					String simpleName = dep[i].getConstraint().getSimpleName();
					int diff = dep[i].getDifference();
					sb.append(seq);
					if (diff == 0 && simpleName.equals("FS")) {
						sb.append(",");
					} else {
						sb.append(simpleName);
						if (diff > 0) {
							sb.append("+");
							sb.append(diff);
						} else if (diff < 0) {
							sb.append(diff);
						}
						sb.append(",");
					}
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			res = sb.toString();
		} else if (GanttTreeTableModel.strColID.equals(columnName)) {
			// 取序号，此处不取行数+1，因为如果取行数+1的话，大纲级别改变则序号也会跟着变，
			// 不能正确描述所有任务总行数，改为取任务的Seq，所以要保证Info里面这个值正确，千万重要
			// edit by emanon
			res = new Integer(task.getScheduleTaskInfo().getSeq());
		} else {
			res = task.getCustomValues().getValue(columnName);
			if (columnName.equals(GanttTreeTableModelExt.strColNatureTimes)
					&& res instanceof BigDecimal) {
				res = new Integer(((BigDecimal) res).intValue());
			}
			if (columnName.equals(GanttTreeTableModelExt.strColEffectTimes)
					&& res instanceof BigDecimal) {
				res = new Integer(((BigDecimal) res).intValue());
			}
		}
		return res;
	}

	private int getRow(DefaultMutableTreeNode node) {
		return treetable.getTree().getRowForPath(new TreePath(node.getPath()));
	}
	
	private int getCloumnIndexByColName(String colName) {
		int index = -1;
		for (int i = 0; i < getColumnCount(); i++) {
			if (getColumnName(i).equals(colName)) {
				index = i;
			}
		}
		return index;
	}

	public void setValueAt(final Object value, final Object node,
			final int column) {
		String cName = getColumnName(column);
		boolean isNeedSetNull = false;

		if (cName.equals(GanttTreeTableModelExt.stradminPerson) || cName.equals(GanttTreeTableModelExt.strColAdminDept)
				|| cName.equals(GanttTreeTableModelExt.strColQualityEvaPerson) || cName.equals(GanttTreeTableModelExt.strColHelpDep)
				|| cName.equals(GanttTreeTableModelExt.strColHelpPerson) || cName.equals(GanttTreeTableModelExt.strColPlanEvaPerson)) {
			isNeedSetNull = true;
		}
		if (value == null && !isNeedSetNull) {
			return;
		}

		// 在此处加上约束，如果调整工期的时候，FS、SS调完成日期，FF调开始日期
		String notAdjustColName = getNotAdjustColumnName(node);
		if (!notAdjustColName.equals(getColumnName(column))) {
			setValue(value, node, column);
			Mediator.getGanttProjectSingleton().repaint();
			Mediator.getGanttProjectSingleton().setAskForSave(true);
		} 
		
	}
	
	/**
	 * 
	 * 描述：获取当前任务上生效的搭接关系
	 * 
	 * @param task
	 * @return 创建人：yuanjun_lan 创建时间：2012-3-4
	 */
	public static TaskDependency getCurrentTaskDependency(Task task) {
		TaskDependencyConstraint constraint = null;
		TaskDependency[] dependencys = task.getDependencies().toArray();
		TaskDependency dependency = null;
		if (dependencys.length == 1) {
			return dependencys[0];
		} else {
			int baseIndex = -1;
			Date baseDate = null;
			Date nextDate = null;
			int effectCount = 0;
			for (int i = 0; i < dependencys.length; i++) {
				dependency = dependencys[i];
				KDTask tas = (KDTask) dependency.getDependant();
				if (tas.getTaskID() != task.getTaskID()) {
					continue;
				}
				effectCount++;
				int diff = dependency.getDifference();
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) tas.getScheduleTaskInfo();
				constraint = dependency.getConstraint();
				if (constraint instanceof FinishFinishConstraintImpl || constraint instanceof FinishStartConstraintImpl) {// FF
					// &&
					// FS
					nextDate = ScheduleCalendarHelper.getEndDate(dependency.getDependee().getEnd().getTime(), new BigDecimal(diff),
							taskInfo.getCalendar());
				} else if (constraint instanceof StartStartConstraintImpl) {
					nextDate = ScheduleCalendarHelper.getEndDate(taskInfo.getStart(), new BigDecimal(diff), taskInfo.getCalendar());
				}
				if (baseDate == null) {
					baseDate = nextDate;
				}
				if (baseDate.compareTo(nextDate) < 0) {
					baseDate = nextDate;
					baseIndex = i;
				}
			}
			if (effectCount == 1) {
				baseIndex = 0;
			}
			if (baseIndex > -1) {
				return dependencys[baseIndex];
			}
		}
		return null;
	}
	
    /**
	 * 
	 * 描述：根据当前任务及搭接关系来确认那些列不能做修改
	 * 
	 * @param node
	 * @return 创建人：yuanjun_lan 创建时间：2012-3-3
	 */
	private String getNotAdjustColumnName(final Object node) {
		String notAdjustColName = "";
		KDTask kdTask = (KDTask) ((TaskNode) node).getUserObject();
		TaskDependencySlice slice = kdTask.getDependencies();
		TaskDependency[] dependencys = slice.toArray();
		TaskDependency dependency = null;
		if (dependencys.length == 1) {
			TaskDependency dep = dependencys[0];
			if (dep.getDependant().getTaskID() == kdTask.getTaskID()) {
				notAdjustColName = getNotAdjustName(dependencys[0]);
			}
			
		} else {
			int baseIndex = -1;
			Date baseDate = null;
			Date nextDate = null;
			int effectCount = 0;
			for (int i = 0; i < dependencys.length; i++) {
				dependency = dependencys[i];
				KDTask task = (KDTask) dependency.getDependant();
				if (dependency.getDependant().getTaskID() != kdTask.getTaskID()) {
					continue;
				} 
				effectCount++;
				int diff = dependency.getDifference();
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
				TaskDependencyConstraint constraint = dependency.getConstraint();
				if (constraint instanceof FinishFinishConstraintImpl || constraint instanceof FinishStartConstraintImpl ) {// FF
					// &&
					// FS
					// nextDate =
					// ScheduleCalendarHelper.getEndDate(taskInfo.getEnd(), new
					// BigDecimal(diff + ""), taskInfo.getCalendar());
					nextDate = ScheduleCalendarHelper.getEndDate(dependency.getDependee().getEnd().getTime(), new BigDecimal(diff + ""),
							taskInfo.getCalendar());
				} else if (constraint instanceof StartStartConstraintImpl) {
					nextDate = ScheduleCalendarHelper.getEndDate(taskInfo.getStart(), new BigDecimal(diff + ""), taskInfo.getCalendar());
				}
				if (baseDate == null) {
					baseDate = nextDate;
					baseIndex = i;
				}
				if (baseDate.compareTo(nextDate) < 0) {
					baseDate = nextDate;
					baseIndex = i;
				}
			}
			if (effectCount == 1) {
				baseIndex = 0;
			}
			if (baseIndex > -1) {
				notAdjustColName = getNotAdjustName(dependencys[baseIndex]);
			}
			
		}
		return notAdjustColName;
	}

	private String getNotAdjustName(TaskDependency dependency) {
		String notAdjustColName = "";
		TaskDependencyConstraint constraint = dependency.getConstraint();
		if (constraint instanceof FinishFinishConstraintImpl) {// FF
			notAdjustColName = GanttTreeTableModel.strColEndDate;
		} else if (constraint instanceof FinishStartConstraintImpl || constraint instanceof StartStartConstraintImpl) {
			notAdjustColName = GanttTreeTableModel.strColBegDate;
		}
		return notAdjustColName;
	}

	private boolean isCanTaskNodeStart(TaskNode node, GanttCalendar value) {
		boolean isCanAdjustStart = true;
		Task task = (Task) node.getUserObject();
		for (int i = 0; i < node.getChildCount(); i++) {
			TaskNode child = (TaskNode) node.getChildAt(i);
			if (child.getStart().compareTo(value) > 0) {
				return false;
			} else {
				return isCanTaskNodeStart(child, value);
			}
		}
		return isCanAdjustStart;

	}

	/**
	 * Set value in left pane cell
	 * 
	 * @param value
	 * @param node
	 * @param column
	 */
	private void setValue(final Object value, final Object node,
			final int column) {
		String columnName = getColumnName(column);
		KDTask kdTask = (KDTask) ((TaskNode) node).getUserObject();
		
		if (GanttTreeTableModelExt.strColTaskName.equals(columnName)) {
			((TaskNode) node).setName(value.toString());
			kdTask.getScheduleTaskInfo().setName(value.toString());
			kdTask.setName(value.toString());
		} else if (GanttTreeTableModel.strColBegDate.equals(columnName)) {
			if(value instanceof GanttCalendar){
				if (isCanTaskNodeStart((TaskNode) node, (GanttCalendar) value)) {
					((TaskNode) node).setStart((GanttCalendar) value);
					((TaskNode) node).applyThirdDateConstraint();
				} else {
					FDCMsgBox.showInfo("向前调整时，请从最明细节点开始调整。");
				}
			}
		} else if (GanttTreeTableModel.strColEndDate.equals(columnName)) {
			if(value instanceof GanttCalendar){
				((TaskNode) node).setEnd((GanttCalendar) value);
			}
		} else if (GanttTreeTableModelExt.strColEffectTimes.equals(columnName)) {
			if(value instanceof BigDecimal){
				BigDecimal newValue = FDCHelper.toBigDecimal(value);
				if(newValue.compareTo(FDCHelper.toBigDecimal("10000"))>0){
					return;
				}
				kdTask.setLength(((BigDecimal) value).intValue());
			}
		} else if (GanttTreeTableModelExt.strColCompletePercent
				.equals(columnName)) {			
			if(value instanceof Integer)
			((TaskNode) node).setCompletionPercentage(((Integer) value)
					.intValue());
		} else {
			try {
				kdTask.getCustomValues().setValue(columnName, value);
			} catch (CustomColumnsException e) {
				if (!GPLogger.log(e)) {
					e.printStackTrace(System.err);
				}
			}
		}
		this.ganttTreeTableModelExt.afterSetValue(value, node, column);
	}

	public void columnAdded(TableColumnModelEvent arg0) {
		nbCol++;
	}

	public void columnRemoved(TableColumnModelEvent arg0) {
		nbCol--;
	}

	public void columnMoved(TableColumnModelEvent arg0) {

	}
	
	public void columnMarginChanged(ChangeEvent arg0) {
	}

	public void columnSelectionChanged(ListSelectionEvent arg0) {
	}

	public Task[] getNestedTasks(Task container) {
		TaskNode r = (TaskNode) root;
		Enumeration e = r.children();

		Vector v = new Vector();
		while (e.hasMoreElements())
			v.add((TaskNode) e.nextElement());
		Task[] res = new Task[v.size()];
		v.toArray(res);
		return res;

	}

	/**
	 * Purpose: Should return true if this Tasks has any nested subtasks.
	 */
	public boolean hasNestedTasks(Task container) {
		TaskNode r = (TaskNode) root;
		if (r.getChildCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Task getRootTask() {
		return (Task) ((TaskNode) this.getRoot()).getUserObject();
	}

	/**
	 * Returns the corresponding task node according to the given task.
	 * 
	 * @param task
	 *            The task whom tasknode we want to get.
	 * @return The corresponding task node according to the given task.
	 */
	public TaskNode getTaskNodeForTask(Task task) {
		Enumeration enumeration = ((TaskNode) getRoot()).preorderEnumeration();
		while (enumeration.hasMoreElements()) {
			Object next = enumeration.nextElement();
			if (!(next instanceof TaskNode))
				continue;
			TaskNode tn = (TaskNode) next;
			Task t = (Task) tn.getUserObject();
			if (t.equals(task))
				return tn;
		}
		return null;
	}

	public Task getContainer(Task nestedTask) {
		return null;
	}

	public void move(Task whatMove, Task whereMove) {
	}

	public boolean areUnrelated(Task dependant, Task dependee) {
		return false;
	}

	public int getDepth(Task task) {
		return 0;
	}

	public void languageChanged(Event event) {
		changeLanguage(event.getLanguage());
	}

	public int compareDocumentOrder(Task next, Task dependeeTask) {
		throw new UnsupportedOperationException();
	}

	public boolean contains(Task task) {
		throw new UnsupportedOperationException();
	}
	
}