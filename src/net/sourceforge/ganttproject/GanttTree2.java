/***************************************************************************
 * GanttTree.java  -  description
 * -------------------
 * begin                : dec 2002
 * copyright            : (C) 2002 by Thomas Alexandre
 * email                : alexthomas(at)ganttproject.org
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

package net.sourceforge.ganttproject;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.action.NewTaskAction;
import net.sourceforge.ganttproject.delay.Delay;
import net.sourceforge.ganttproject.delay.DelayObserver;
import net.sourceforge.ganttproject.font.Fonts;
import net.sourceforge.ganttproject.gui.TableHeaderUIFacade;
import net.sourceforge.ganttproject.gui.TaskTreeUIFacade;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.language.GanttLanguage.Event;
import net.sourceforge.ganttproject.task.BlankLineNode;
import net.sourceforge.ganttproject.task.ResourceAssignment;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskManagerImpl;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.TaskSelectionManager;
import net.sourceforge.ganttproject.task.TaskSelectionManager.Listener;
import net.sourceforge.ganttproject.task.algorithm.AdjustTaskBoundsAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.RecalculateTaskScheduleAlgorithm;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySlice;
import net.sourceforge.ganttproject.undo.GPUndoManager;

import org.apache.log4j.Logger;

import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleBaseEditUI;
import com.kingdee.eas.fdc.schedule.framework.DeleteParentDependency;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleBaseUI;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

/**
 * Class that generate the JTree
 */
public class GanttTree2 extends JPanel implements DragSourceListener,
        DragGestureListener, DelayObserver, ProjectEventListener, TaskTreeUIFacade {
	private static Logger logger = Logger.getLogger("net.sourceforge.ganttproject.GanttTree2");
    /** The root node of the Tree */
    private TaskNode rootNode;

    /** The model for the JTableTree */
    private GanttTreeTableModel treeModel;

    private UIFacade myUIFacade;

    /** The GanttTreeTable. */
    private GanttTreeTable treetable;

    /** Pointer on graphic area */
    private GanttGraphicArea area = null;

    /** Pointer on application */
    private GanttProject appli;

    /** An array for expansion */
    // private ArrayList expand = new ArrayList();
    private static final int AUTOSCROLL_MARGIN = 12;

    /** The vertical scrollbar on the JTree */
    private JScrollBar vbar;

    /** The horizontal scrollbar on the JTree */
    private JScrollBar hbar;

    /** The language use */
    private GanttLanguage language = GanttLanguage.getInstance();

    /** Number of tasks on the tree. */
    private int nbTasks = 0;

    private TreePath dragPath = null;

    private BufferedImage ghostImage = null; // The 'drag image'

    private Point offsetPoint = new Point(); // Where, in the drag image, the

    // mouse was clicked

    private final TaskManager myTaskManager;
    private final TaskSelectionManager mySelectionManager;

    private final GPAction myIndentAction = new GPAction() {
        protected String getIconFilePrefix() {
            return "indent_";
        }

        public void actionPerformed(ActionEvent e) {
            indentCurrentNodes();
        }
        protected String getLocalizedName() {
            return getI18n("indentTask");
        }
    };
    private NewTaskAction myNewTaskAction = null;
    public NewTaskAction getNewTaskAction(){
    	if(myNewTaskAction==null)
    		myNewTaskAction = new NewTaskAction(this.appli);
    	return this.myNewTaskAction;
    }
    
    private final GPAction myDedentAction = new GPAction() {

        protected String getIconFilePrefix() {
            return "unindent_";
        }

        public void actionPerformed(ActionEvent e) {
            dedentCurrentNodes();
        }
        protected String getLocalizedName() {
            return getI18n("dedentTask");
        }
    };
    private final GPAction myMoveUpAction = new GPAction() {
        protected String getIconFilePrefix() {
            return "up_";
        }
        public void actionPerformed(ActionEvent e) {
            upCurrentNodes();
        }
        protected String getLocalizedName() {
            return getI18n("upTask");
        }
    };
    private final GPAction myMoveDownAction = new GPAction() {
        protected String getIconFilePrefix() {
            return "down_";
        }
        public void actionPerformed(ActionEvent e) {
            downCurrentNodes();
        }
        protected String getLocalizedName() {
            return getI18n("downTask");
        }
    };
    private Action myLinkTasksAction;
    private Action myUnlinkTasksAction;

    private class AutoscrollingTree extends JTree implements Autoscroll {

        public AutoscrollingTree(DefaultTreeModel treeModel) {
            super(treeModel);
        }

        // Calculate the insets for the *JTREE*, not the viewport
        // the tree is in. This makes it a bit messy.
        public Insets getAutoscrollInsets() {
            Rectangle raOuter = getBounds();
            Rectangle raInner = getParent().getBounds();
            return new Insets(raInner.y - raOuter.y + AUTOSCROLL_MARGIN,
                    raInner.x - raOuter.x + AUTOSCROLL_MARGIN, raOuter.height
                            - raInner.height - raInner.y + raOuter.y
                            + AUTOSCROLL_MARGIN, raOuter.width - raInner.width
                            - raInner.x + raOuter.x + AUTOSCROLL_MARGIN);
        }

        public void autoscroll(Point pt) {
            // Figure out which row we锟絜 on.
            int nRow = this.getClosestRowForLocation(pt.x, pt.y);

            // If we are not on a row then ignore this autoscroll request
            if (nRow < 0)
                return;

            Rectangle raOuter = getBounds();
            // Now decide if the row is at the top of the screen or at the
            // bottom. We do this to make the previous row (or the next
            // row) visible as appropriate. If we锟絜 at the absolute top or
            // bottom, just return the first or last row respectively.

            nRow = (pt.y + raOuter.y <= AUTOSCROLL_MARGIN) // Is row at top of
                    // screen?
                    ? (nRow <= 0 ? 0 : nRow - 1) // Yes, scroll up one row
                    : (nRow < this.getRowCount() - 1 ? nRow + 1 : nRow); // No,
            // scroll
            // down
            // one
            // row

            this.scrollRowToVisible(nRow);
        }

    }

    /**
     * Constructor.
     * @param selectionManager TODO
     * @param facade
     */
    public GanttTree2(final GanttProject app, TaskManager taskManager,
            TaskSelectionManager selectionManager, UIFacade uiFacade) {

        super();
        app.getProject().addProjectEventListener(this);
        myUIFacade = uiFacade;

        myTaskManager = taskManager;
        mySelectionManager = selectionManager;
        this.appli = app;

        // Create the root node
        initRootNode();

        treeModel = new GanttTreeTableModel(rootNode);
        treeModel.addTreeModelListener(new GanttTreeModelListener());
        FDCScheduleBaseEditUI ui = (FDCScheduleBaseEditUI) ((ScheduleGanttProject) appli)
				.getScheduleUIFacede();
        // Create the JTree
		treetable = new GanttTreeTable(app.getProject(), uiFacade, treeModel);
        treetable.getActionMap().put(myIndentAction.getValue(Action.NAME), myIndentAction);
        treetable.getActionMap().put(myDedentAction.getValue(Action.NAME), myDedentAction);
        treetable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), myIndentAction.getValue(Action.NAME));
        treetable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK), myDedentAction.getValue(Action.NAME));
//        treetable.getActionMap().put("newTask", new AbstractAction() {
//			public void actionPerformed(ActionEvent e) {
//                if (getSelectedTask() != null)
//                    setEditingTask(getSelectedTask());
//                Mediator.getGanttProjectSingleton().getUndoManager()
//                        .undoableEdit("New Task", new Runnable() {
//                            public void run() {
//                                Task t = Mediator
//                                        .getGanttProjectSingleton()
//                                        .newTask();
//
//                                setEditingTask(t);
//                            }
//                        });
//			}
//        });
        treetable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(GPAction.getKeyStroke("newArtifact.shortcut"), "newTask");
        treetable.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_DOWN_MASK), "cutTask");
        treetable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (false==treetable.getTable().isEditing()) {
					app.keyPressed(e);
				}
			}
        }); // callback for keyboard pressed
        treetable.getTree().addTreeSelectionListener(
                new TreeSelectionListener() {

                    public void valueChanged(TreeSelectionEvent e) {
                        Mediator.getTaskSelectionManager().clear();
                        TaskNode tn[] = (TaskNode[]) getSelectedTaskNodes();
                        if (tn != null)
                            for (int i = 0; i < tn.length; i++)
                                Mediator.getTaskSelectionManager().addTask(
                                        (Task) tn[i].getUserObject());
                    }
                });

        treetable.setBackground(new Color(1.0f, 1.0f, 1.0f));
        treetable.getTree().addTreeExpansionListener(
                new GanttTreeExpansionListener());

        ToolTipManager.sharedInstance().registerComponent(treetable);

        // Add The tree on a Scrollpane
        JScrollPane scrollpane = new JScrollPane();
        setLayout(new BorderLayout());
        add(scrollpane, BorderLayout.CENTER);
        scrollpane.getViewport().add(treetable);
        scrollpane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        vbar = treetable.getVerticalScrollBar();
        final JPanel jp = new JPanel(new BorderLayout());
        jp.add(vbar, BorderLayout.CENTER);
        jp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jp.setVisible(true);
        vbar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (treetable.getSize().getHeight() - 20 < e.getAdjustable()
                        .getMaximum())
                    jp.setVisible(true);
                else
                    jp.setVisible(true);
                repaint();
            }
        });

		// this.add(jp, BorderLayout.WEST);
        hbar = scrollpane.getHorizontalScrollBar();
        vbar.addAdjustmentListener(new GanttAdjustmentListener());

        mySelectionManager.addSelectionListener(new Listener() {
            public void selectionChanged(List currentSelection) {
            }
			public void userInputConsumerChanged(Object newConsumer) {
                if (treetable.getTable().isEditing()) {
                    treetable.getTable().editingStopped(new ChangeEvent(treetable.getTreeTable()));
                }
			}
        });
        treetable.getTree().addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				mySelectionManager.setUserInputConsumer(this);
			}
        });
        // A listener on mouse click (menu)
        MouseListener ml = new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                handlePopupTrigger(e);
            }

            public void mousePressed(MouseEvent e) {
                handlePopupTrigger(e);
            }

            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount()==2 && e.getButton() == MouseEvent.BUTTON1) {
                    TreePath selPath = treetable.getTreeTable().getPathForLocation(e.getX(), e.getY());
                    if (selPath!=null) {
                        e.consume();
                        appli.propertiesTask();
                    }
                }
                else  {
                    handlePopupTrigger(e);
                }
            }

            private void handlePopupTrigger(MouseEvent e) {
                if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    TreePath selPath = treetable.getTreeTable().getPathForLocation(e.getX(), e.getY());
                    if (selPath!=null) {
                        TreePath[] currentSelection = treetable.getTree()
                                .getSelectionPaths();

                        if (currentSelection == null
                                || currentSelection.length == 0)
                            treetable.getTree().setSelectionPath(selPath);
                        else {
                            boolean contains = false;
                            for (int i = 0; i < currentSelection.length
                                    && !contains; i++)
                                if (currentSelection[i] == selPath)
                                    contains = true;
                            if (!contains)
                                treetable.getTree().setSelectionPath(
                                        selPath);
                        }
                        createPopupMenu(e.getX(), e.getY(), true);
                    }
                    else {
                        treetable.getTree().setSelectionPath(null);
                        createPopupMenu(e.getX(), e.getY(), false);
                    }
                    e.consume();
                }

            }
        };
        if (!app.isOnlyViewer)
            treetable.addMouseListener(ml);

        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(treetable,
                DnDConstants.ACTION_COPY_OR_MOVE, this);
        dragSource.addDragSourceListener(this);
        DropTarget dropTarget = new DropTarget(treetable,
                new GanttTreeDropListener());
        dropTarget.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);

        getTreeTable().setToolTipText("aze");
        getTreeTable().getTreeTable().setToolTipText("rty");

    }

    public void setActions() {
//        treetable.setAction(appli.getCopyAction());
//        treetable.setAction(appli.getPasteAction());
//        treetable.setAction(appli.getCutAction());
        treetable.addAction(myIndentAction, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        treetable.addAction(myDedentAction, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK));
    }

    /**
     * Edits the <code>t</code> task name in the treetable.
     */
    public void setEditingTask(Task t) {
        selectTask(t, false);
//        TreePath tp = new TreePath(getSelectedTaskNode().getPath());
		// int c = getTable().convertColumnIndexToView(
		// getTable().getColumn(GanttTreeTableModel.strColName)
		// .getModelIndex());
        treetable.getTreeTable().editingStopped(
                new ChangeEvent(treetable.getTreeTable()));
        treetable.editNewTask(t);
//        treetable.getTreeTable().editCellAt(
//                treetable.getTree().getRowForPath(tp), c);
//        treetable.requestFocus();
        treetable.centerViewOnSelectedCell();

    }

    public void stopEditing() {
        treetable.getTable().editingCanceled(
                new ChangeEvent(treetable.getTreeTable()));
        treetable.getTreeTable().editingCanceled(
                new ChangeEvent(treetable.getTreeTable()));
    }

    public void changeLanguage(GanttLanguage ganttLanguage) {
        this.language = ganttLanguage;
        //this.treetable.changeLanguage(language);
    }

    private void initRootNode() {
        getTaskManager().getRootTask().setName("root");
        rootNode = new TaskNode(getTaskManager().getRootTask());
    }

    public Action[] getPopupMenuActions() {
    	// do by sxhong 隐藏一些按钮
        List actions = new ArrayList();
        if(appli instanceof ScheduleGanttProject){
        	ScheduleGanttProject scheduleGP = (ScheduleGanttProject)appli;
        	if(scheduleGP.getOprtState()!=null&&("ADDNEW".equals(scheduleGP.getOprtState())||"EDIT".equals(scheduleGP.getOprtState()))){
        		if(!scheduleGP.isAdjustSchedule()){
        			actions.add(this.myNewTaskAction);
        		}
        		
                if (!Mediator.getTaskSelectionManager().getSelectedTasks().isEmpty()) {
                    actions.add(getTaskPropertiesAction());
                    // }
                    actions.add(createMenuAction(GanttProject.correctLabel(language
                            .getText("deleteTask")), "/icons/delete_16.gif"));
					// actions.add(myIndentAction);
					// actions.add(myDedentAction);
                    if(!scheduleGP.isAdjustSchedule()){
                    	actions.add(null);
                        actions.add(getMoveUpAction());
                        actions.add(getMoveDownAction());
                        actions.add(null);
						// actions.add(getUnlinkTasksAction());
						// actions.add(getLinkTasksAction());
                    }
                    
        /*            actions.add(null);
                    actions.add(appli.getCutAction());
                    actions.add(appli.getCopyAction());
                    actions.add(appli.getPasteAction());*/
                    /*
                    actions.add(null);
                    actions.add(createMenuAction(language.getText("hideTask"),
                    "/icons/hide_16.gif"));
                    actions.add(createMenuAction(language.getText("displayHiddenTasks"),
                            "/icons/show_16.gif"));
                            */
                }
        	}
//        	 if (!Mediator.getTaskSelectionManager().getSelectedTasks().isEmpty()) {
//        		 if(!actions.isEmpty()){
//        			 actions.add(null);
//        		 }
			// actions.add(new GotoStartPopupAction());
			// actions.add(new GotoFinishPopupAction());
//        	 }
        }
        return (Action[]) actions.toArray(new Action[0]);
    }

    private Action createMenuAction(String label, String iconPath) {
        AbstractAction result = new AbstractAction(label, new ImageIcon(
                getClass().getResource(iconPath))) {
            public void actionPerformed(ActionEvent e) {
                appli.actionPerformed(e);
            }
        };
        return result;

    }

    /** Create a popup menu when mous click */
    private void createPopupMenu(int x, int y, boolean all) {
        JPopupMenu menu = new JPopupMenu();
        Action[] popupMenuActions = getPopupMenuActions();
        myUIFacade.showPopupMenu(this, popupMenuActions, x - hbar.getValue()
                + (vbar.isVisible() ? vbar.getWidth() : 0), y - vbar.getValue()
                + 20);
    }

    /** Change grpahic part */
    public void setGraphicArea(GanttGraphicArea area) {
        this.area = area;
    }
    /** add an object with the expand information */
    public DefaultMutableTreeNode addObjectWithExpand(Object child,
            DefaultMutableTreeNode parent) {

        DefaultMutableTreeNode childNode = new TaskNode((Task) child);

        if (parent == null)
            parent = rootNode;

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        //forwardScheduling();

        Task task = (Task) (childNode.getUserObject());

        boolean res = true;

        if (parent == null)
            res = false;

        // test for expantion
        while (parent != null) {
            Task taskFather = (Task) (parent.getUserObject());
            if (!taskFather.getExpand()) {
                res = false;
                break;
            }
            parent = (DefaultMutableTreeNode) (parent.getParent());
        }

        treetable.getTree().scrollPathToVisible(
                new TreePath(childNode.getPath()));
        if (!res && parent!=null) {
            treetable.getTree().collapsePath(new TreePath(parent.getPath()));
        }
//        else
//            task.setExpand(false);

        nbTasks++;
        appli.refreshProjectInfos();
        Integer taskid=new Integer(((Task)child).getTaskID());
        getTaskId2TreeNodeMap().put(taskid,childNode);
        return childNode;
    }

    public void addBlankLine(final DefaultMutableTreeNode select,
            final int index) {
        treeModel.insertNodeInto(new BlankLineNode(),
                select == null ? getRoot() : (DefaultMutableTreeNode) select
                        .getParent(), index == -1 ? getRoot().getChildCount()
                        : index);

        appli.setAskForSave(true);

    }

    /** Add a sub task. */
    public TaskNode addObject(Object child, MutableTreeNode parent, int index) {
        TaskNode childNode = new TaskNode((Task) child);

        if (parent == null)
            parent = rootNode;

        // GanttTask tmpTask = (GanttTask)(childNode.getUserObject());
        // tmpTask.indentID((String)(((GanttTask)(parent.getUserObject())).getID()));

        treeModel.insertNodeInto(childNode, parent, index == -1 ? parent
                .getChildCount() : index);

        treetable.getTree().scrollPathToVisible(
                new TreePath(childNode.getPath()));

        nbTasks++;
        appli.refreshProjectInfos();
        Integer taskid=new Integer(((Task)child).getTaskID());
        getTaskId2TreeNodeMap().put(taskid,childNode);
        return childNode;
    }

    /** Return the selected task */
    private GanttTask getSelectedTask() {
        DefaultMutableTreeNode node = getSelectedTaskNode();
        if (node == null)
            return null;
        return (GanttTask) (node.getUserObject());
    }

    /** Return the selected node */
    public DefaultMutableTreeNode getSelectedNode() {
        TreePath currentSelection = treetable.getTree().getSelectionPath();
        if (currentSelection == null) {
            return null;
        }
        DefaultMutableTreeNode dmtnselected = (DefaultMutableTreeNode) currentSelection
                .getLastPathComponent();
        return dmtnselected;
    }

    /** Return the selected node */
    private DefaultMutableTreeNode getSelectedTaskNode() {
        TreePath currentSelection = treetable.getTree().getSelectionPath();
        if (currentSelection == null
                || !(currentSelection.getLastPathComponent() instanceof TaskNode)) {
            return null;
        }
        DefaultMutableTreeNode dmtnselected = (DefaultMutableTreeNode) currentSelection
                .getLastPathComponent();
        return dmtnselected;
    }

    public TaskNode[] getSelectedTaskNodes() {
        TreePath[] currentSelection = treetable.getTree().getSelectionPaths();

        if (currentSelection == null || currentSelection.length == 0)
            return null;

        DefaultMutableTreeNode[] dmtnselected = new DefaultMutableTreeNode[currentSelection.length];
        for (int i = 0; i < currentSelection.length; i++)
            dmtnselected[i] = (DefaultMutableTreeNode) currentSelection[i]
                    .getLastPathComponent();

        TaskNode[] res = getOnlyTaskNodes(dmtnselected);

        return res;
    }

    /** @return the list of the selected nodes. */
    public DefaultMutableTreeNode[] getSelectedNodes() {
        TreePath[] currentSelection = treetable.getTree().getSelectionPaths();

        if (currentSelection == null || currentSelection.length == 0) // no
            // elements
            // are
            // selectionned
            return null;

        DefaultMutableTreeNode[] dmtnselected = new DefaultMutableTreeNode[currentSelection.length];
        for (int i = 0; i < currentSelection.length; i++)
            dmtnselected[i] = (DefaultMutableTreeNode) currentSelection[i]
                    .getLastPathComponent();

        DefaultMutableTreeNode[] res = dmtnselected;

        return res;
    }

    private TaskNode[] getOnlyTaskNodes(DefaultMutableTreeNode[] array) {
        List resAsList = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            DefaultMutableTreeNode next = array[i];
            if (next instanceof TaskNode)
                resAsList.add(next);
        }
        return (TaskNode[]) resAsList.toArray(new TaskNode[0]);
    }

    /** Return the DefaultMutableTreeNode with the name name. */
    public DefaultMutableTreeNode getNode(int id /* String name */) {
        return (DefaultMutableTreeNode)getTaskId2TreeNodeMap().get(new Integer(id));
    }

    public static List convertNodesListToItemList(List nodesList) {
        List res = new ArrayList(nodesList.size());
        Iterator itNodes = nodesList.iterator();
        while (itNodes.hasNext()) {
            res.add(((DefaultMutableTreeNode) itNodes.next()).getUserObject());
        }
        return res;
    }

    /** Return tru if the Project has tasks and false is no tasks on the project */
    public boolean hasTasks() {
        Enumeration e = (rootNode).preorderEnumeration();
        while (e.hasMoreElements()) {
            Object next = e.nextElement();

            if (rootNode != (DefaultMutableTreeNode) next
                    && (next instanceof TaskNode))
                return true;
        }
        return false;
    }

    /** Returnan ArrayList with all tasks. */
    public ArrayList getAllTasks() {
        ArrayList res = new ArrayList();
        Enumeration enumeration = rootNode.preorderEnumeration();
        while (enumeration.hasMoreElements()) {
            Object o = enumeration.nextElement();
            if (o instanceof TaskNode) {
                res.add(o);
            }
        }
        return res;
//        return Collections.list(rootNode.preorderEnumeration());
    }

    public List getAllVisibleNodes() {
        List res = new ArrayList();
        Enumeration enumeration = rootNode.preorderEnumeration();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode o = (DefaultMutableTreeNode) enumeration
                    .nextElement();
            if (getTreeTable().getTree().isVisible(new TreePath(o.getPath())))
                res.add(o);
        }
        return res;
    }

    /** Return all sub task for the tree node base */
    public ArrayList getAllChildTask(Task task) {
        ArrayList res = new ArrayList();
        if (task == null)
            return null;
        DefaultMutableTreeNode base = (DefaultMutableTreeNode) getNode(task
                .getTaskID());
        if (base == null)
            return res;
        Enumeration e = base.children();
        while (e.hasMoreElements()) {
            res.add(e.nextElement());
        }
        return res;
    }

    /** Return all sub task for the tree node base */
    public ArrayList getAllChildTask(DefaultMutableTreeNode base) {
        ArrayList res = new ArrayList();
        if (base == null || !(base instanceof TaskNode))
            return res;
        Enumeration e = base.children();
        while (e.hasMoreElements()) {
            Object next = e.nextElement();
            if (next instanceof TaskNode)
                res.add(next);
        }
        return res;
    }

    /** Return the last default tree node */
    public DefaultMutableTreeNode getLastNode() {
        return rootNode.getLastLeaf();
    }

    /** Remove the current node.
     * @param current */
   public void removeCurrentNode(DefaultMutableTreeNode currentNode) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) (currentNode
                    .getParent());
            Task task = (Task) currentNode.getUserObject();
			getTaskManager().deleteTask(task);
			//remove by sxhong
            getTaskId2TreeNodeMap().remove(new Integer(task.getTaskID()));
            if (parent != null) {
                ((GanttTreeTableModel) treeModel)
                        .removeNodeFromParent(currentNode);
                forwardScheduling();
                nbTasks--;
                appli.refreshProjectInfos();
                return;
            }
    }

    /** Clear the JTree. */
    public void clearTree() {
        // expand.clear();
        rootNode.removeAllChildren();
        initRootNode();
        treeModel.setRoot(rootNode);
        treeModel.reload();
        nbTasks = 0;
        hiddenTask.clear();
        getTaskId2TreeNodeMap().clear();
    }

    /** Select the row of the tree */
    public void selectTreeRow(int row) {
        treetable.getTree().setSelectionRow(row);
    }

    public void selectTasks(List tasksList) {
        boolean multi = false;
        Iterator it = tasksList.iterator();
        if (it.hasNext())
            selectTask((Task) it.next(), false);
        while (it.hasNext())
            selectTask((Task) it.next(), true);
    }

    public void selectTask(Task task, boolean multipleSelection) {
        DefaultMutableTreeNode taskNode = null;
        for (Enumeration nodes = rootNode.preorderEnumeration(); nodes
                .hasMoreElements();) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) nodes
                    .nextElement();
            if (!(nextNode instanceof TaskNode))
                continue;
            if (nextNode.getUserObject().equals(task)) {
                taskNode = nextNode;
                break;
            }
        }
        if (taskNode != null) {
            TreePath taskPath = new TreePath(taskNode.getPath());
            if (multipleSelection)
                if (treetable.getTree().getSelectionModel().isPathSelected(
                        taskPath))
                    treetable.getTree().getSelectionModel()
                            .removeSelectionPath(taskPath);
                else
                    treetable.getTree().getSelectionModel().addSelectionPath(
                            taskPath);
            else
                treetable.getTree().getSelectionModel().setSelectionPath(
                        taskPath);
        }

        TreePath paths[] = treetable.getTree().getSelectionModel()
                .getSelectionPaths();
        Mediator.getTaskSelectionManager().clear();
        if (paths != null)
            for (int i = 0; i < paths.length; i++) {
                TaskNode tn = (TaskNode) paths[i].getLastPathComponent();
                if (!tn.isRoot())
                    Mediator.getTaskSelectionManager().addTask(
                            (Task) tn.getUserObject());
            }
    }

    /** Returne the mother task. */
    public DefaultMutableTreeNode getFatherNode(Task node) {
        if (node == null) {
            return null;
        }
        DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) getNode(node
                .getTaskID());
        if (tmp == null) {
            return null;
        }

        return (DefaultMutableTreeNode) tmp.getParent();
    }

    /** Returne the mother task. */
    public DefaultMutableTreeNode getFatherNode(DefaultMutableTreeNode node) {
        if (node == null) {
            return null;
        }
        return (DefaultMutableTreeNode) node.getParent();
    }

    /** Return the JTree. */
    public JTree getJTree() {
        return treetable.getTree();
    }

    public JTable getTable() {
        return treetable.getTable();
    }

    public GanttTreeTable getTreeTable() {
        return treetable;
    }

    class HiddenTask implements Comparable {
        private DefaultMutableTreeNode node = null;

        private DefaultMutableTreeNode parent = null;

        private int index = -1;

        public HiddenTask(DefaultMutableTreeNode node,
                DefaultMutableTreeNode parent, int index) {
            this.node = node;
            this.parent = parent;
            this.index = index;
            // this.node.setParent(this.parent);
        }

        public DefaultMutableTreeNode getNode() {
            return node;
        }

        public DefaultMutableTreeNode getParent() {
            return parent;
        }

        public int getIndex() {
            return index;
        }

        /**
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Object o) {
            if (o == null)
                return 0;
            if (o instanceof HiddenTask) {
                HiddenTask ht = (HiddenTask) o;
                return this.index - ht.index;
            }
            return 0;
        }

    }

    private List hiddenTask = new ArrayList();

    public Set/*<Task>*/ getHiddenTasks() {
        HashSet result = new HashSet();
        for (int i=0; i<hiddenTask.size(); i++) {
            HiddenTask next = (HiddenTask) hiddenTask.get(i);
            result.add(next.getNode().getUserObject());
        }
        return result;
    }
    /**
     * Hides the selected tasks.
     */
    public void hideSelectedNodes() {
        DefaultMutableTreeNode[] t = getSelectedNodes();
        for (int i = 0; i < t.length; i++) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) t[i]
                    .getParent();
            hiddenTask.add(new HiddenTask(t[i], parent, parent.getIndex(t[i])));
        }

        for (int i = 0; i < hiddenTask.size(); i++) {
            HiddenTask ht = (HiddenTask) hiddenTask.get(i);
            TreeNode parent = ht.node.getParent();
            if (parent != null)
                ((GanttTreeTableModel) getTreeTable().getTreeTableModel())
                        .removeNodeFromParent(((HiddenTask) hiddenTask.get(i))
                                .getNode());
        }

    }


    /**
     * Displays the hidden tasks.
     */
    public void displayHiddenTasks() {
        for (int i = 0; i < hiddenTask.size(); i++) {
            HiddenTask ht = (HiddenTask) hiddenTask.get(i);

            DefaultMutableTreeNode node = ht.getNode();
            DefaultMutableTreeNode parent = ht.getParent();
            node.setParent(parent);
        }
        Collections.sort(hiddenTask);
        for (int i = 0; i < hiddenTask.size(); i++) {
            HiddenTask ht = (HiddenTask) hiddenTask.get(i);

            DefaultMutableTreeNode node = ht.getNode();
            DefaultMutableTreeNode parent = ht.getParent();
            int index = ht.getIndex();
            node.setParent(null);
            ((GanttTreeTableModel) getTreeTable().getTreeTableModel())
                    .insertNodeInto(node, parent, index);

            if (node instanceof TaskNode)
                restoreExpand((TaskNode) node);
        }

        hiddenTask.clear();

    }

    /**
     * Retores the expand state of the node and its children.
     *
     * @param node
     */
    private void restoreExpand(TaskNode node) {
        Task task = (Task) node.getUserObject();
        boolean expand = task.getExpand();

        Enumeration enumeration = node.children();
        while (enumeration.hasMoreElements())
            restoreExpand((TaskNode) enumeration.nextElement());

        if (expand)
            getTreeTable().getTree().expandPath(new TreePath(node.getPath()));
        else
            getTreeTable().getTree().collapsePath(new TreePath(node.getPath()));
        task.setExpand(expand);
    }

    /** Return the root node */
    public DefaultMutableTreeNode getRoot() {
        return rootNode;
    }

    /** Function to put up the selected tasks */
    public void upCurrentNodes() {

        final DefaultMutableTreeNode[] cdmtn = getSelectedNodes();
        if (cdmtn == null) {
            myUIFacade.setStatusText(language.getText("msg21"));
            return;
        }
        final GanttTree2 gt2 = this;
        appli.getUndoManager().undoableEdit("Up", new Runnable() {
            public void run()
            {
                for (int i = 0; i < cdmtn.length; i++)
                {                	
                	if(cdmtn[i].getUserObject() instanceof KDTask){
                    	KDTask taskInfo = (KDTask)cdmtn[i].getUserObject();
                    	if(!taskInfo.isEditable()){
                    		continue;
                    	}
                    }
                    DefaultMutableTreeNode father = gt2.getFatherNode(cdmtn[i]);                    
                    int index = father.getIndex((TreeNode) cdmtn[i]);

                    index--;

                    Task task = (Task) cdmtn[i].getUserObject();

                    if (index >= 0)
                    {
                    	DefaultMutableTreeNode [] child = new DefaultMutableTreeNode[cdmtn[i].getChildCount()];

                    	if(task.getExpand())
                    	{
                    		for(int j=0; j<cdmtn[i].getChildCount(); j++)
                    		{
                    			child[j] = (DefaultMutableTreeNode)cdmtn[i].getChildAt(j);
                    		}

                    		for(int j=0; j<child.length; j++)
                    		{
                    			child[j].removeFromParent();
                    			treeModel.nodesWereRemoved(cdmtn[i],
                    					new int[] { 0 },
                    					new Object[] { child });
                    		}
                    	}

                    	cdmtn[i].removeFromParent();
                        treeModel.nodesWereRemoved(father,
                                        new int[] { index + 1 },
                                        new Object[] { cdmtn });

                        father.insert(cdmtn[i], index);
                        treeModel.nodesWereInserted(father, new int[] {index});

                        if(task.getExpand())
                        {
                        	for(int j=0; j<child.length; j++)
                        	{
                        		cdmtn[i].insert(child[j], j);
                        		treeModel.nodesWereInserted(cdmtn[i], new int[] {j});
                        	}
                        }
                        forwardScheduling();
                    }
                }
               
            }
        });
       resetTask((ScheduleGanttProject) appli, null, 0);

        area.repaint();
    }

    /** Function to put down the selected tasks */
    public void downCurrentNodes() {

        final DefaultMutableTreeNode[] cdmtn = getSelectedNodes();
        if (cdmtn == null) {
            myUIFacade.setStatusText(language.getText("msg21"));
            return;
        }

        //final TreePath[] selectedPaths = new TreePath[cdmtn.length];

        // Parse in reverse mode because tasks are sorted from top to bottom.
        // appli.setQuickSave (false);
        final GanttTree2 gt2 = this;
        appli.getUndoManager().undoableEdit("Down", new Runnable(){
            public void run()
            {	
                for (int i = cdmtn.length - 1; i >= 0; i--)
                {
                	if(cdmtn[i].getUserObject() instanceof KDTask){
                    	KDTask taskInfo = (KDTask)cdmtn[i].getUserObject();
                    	if(!taskInfo.isEditable()){
                    		continue;
                    	}
                    }
                    DefaultMutableTreeNode father = gt2.getFatherNode(cdmtn[i]);
                    int index = father.getIndex((TreeNode) cdmtn[i]);
                    index++;

                    Task task = (Task) cdmtn[i].getUserObject();

                    // New position
                    if ((index < father.getChildCount()))
                    {
                    	DefaultMutableTreeNode [] child = new DefaultMutableTreeNode[cdmtn[i].getChildCount()];

                    	if(task.getExpand())
                    	{
                    		for(int j=0; j<cdmtn[i].getChildCount(); j++)
                    		{
                    			child[j] = (DefaultMutableTreeNode)cdmtn[i].getChildAt(j);
                    		}

                    		for(int j=0; j<child.length; j++)
                    		{
                    			child[j].removeFromParent();
                    			treeModel.nodesWereRemoved(cdmtn[i],
                    					new int[] { 0 },
                    					new Object[] { child });
                    		}
                    	}

                    	cdmtn[i].removeFromParent();
                        treeModel.nodesWereRemoved(father,
                                        new int[] { index - 1 },
                                        new Object[] { cdmtn });

                        father.insert(cdmtn[i], index);
                        treeModel.nodesWereInserted(father, new int[] {index});

                        if(task.getExpand())
                        {
                        	for(int j=0; j<child.length; j++)
                        	{
                        		cdmtn[i].insert(child[j], j);
                        		treeModel.nodesWereInserted(cdmtn[i], new int[] {j});
                        	}
                        }
                        forwardScheduling();
                    }
                }        
            }
        });

        resetTask((ScheduleGanttProject) appli, null, 0);
        area.repaint();
    }

    public DefaultMutableTreeNode removeChild(DefaultMutableTreeNode node)
    {
    	DefaultMutableTreeNode nodeCopy = new DefaultMutableTreeNode(node.getUserObject());
    	Enumeration children = node.children();
    	while(children.hasMoreElements())
    	{
    		DefaultMutableTreeNode child = (DefaultMutableTreeNode)children.nextElement();

    		DefaultMutableTreeNode childCopy = this.removeChild(child);
    		nodeCopy.add(childCopy);

    		child.removeFromParent();
    		treeModel.nodesWereRemoved(node,
                    new int[] { 0 },
                    new Object[] { child });
    	}

    	return nodeCopy;
    }

    public void insertChild(DefaultMutableTreeNode node, DefaultMutableTreeNode copy)
    {
    	Enumeration children = copy.children();

    	int index = 0;
    	while(children.hasMoreElements())
    	{
    		DefaultMutableTreeNode childCopy = (DefaultMutableTreeNode)children.nextElement();
    		DefaultMutableTreeNode child = new DefaultMutableTreeNode(childCopy.getUserObject());
    		node.insert(child, index);
    		treeModel.nodesWereInserted(node, new int[] {index});
    		this.insertChild(child, childCopy);
    		index++;
    	}
    }
    /**
     * 判断两个节点是否存在递归的前置或后置依赖。 
     * @param dependant
     * @param dependee
     * @return 
     */
    private boolean existDepend(Task dependant, Task dependee){		
    	String parentTaskId = ((KDTask)dependant).getScheduleTaskInfo().getId().toString();
    	TaskDependencySlice dependees = dependee.getDependenciesAsDependee();
    	if(dependees != null){
	    	TaskDependency[] depends = dependees.toArray();
	    	String currentTaskId = null;
	    	for(int i = 0; i < depends.length; ++i ){
	    		currentTaskId = ((KDTask)depends[i].getDependant()).getScheduleTaskInfo().getId().toString();
	    		if(parentTaskId.equals(currentTaskId)){
	    			return true;
	    		}
	    		if(existDepend(dependant, depends[i].getDependant())){
	    			return true;
	    		}
	    	}
    	}
    	return false;
    }
    /**
     * Indent several nodes that are selected. Based on the IndentCurrentNode
     * method.
     */
    private void indentCurrentNodes() {

        final DefaultMutableTreeNode[] cdmtn = getSelectedTaskNodes();
        if (cdmtn == null||cdmtn.length==0) {
            myUIFacade.setStatusText(language.getText("msg21"));
            return;
        }
        /***
         * 必须选择的所有任务在同一级
         */
        int level = cdmtn[0].getLevel();
        for (int i = 0; i < cdmtn.length; i++) {
            if(level != cdmtn[i].getLevel())
        	{
            	FDCMsgBox.showInfo(this, "所选任务不是同一级次，不能进行此操作");
            	return;
        	}
            DefaultMutableTreeNode newFather = cdmtn[i].getPreviousSibling();
            if(newFather == null && cdmtn[i] instanceof TaskNode){
            	Task nextTask = (Task) cdmtn[i].getUserObject();
            	FDCMsgBox.showInfo(this, "所选任务["+nextTask.getName()+"]已经是第一个任务，不能降级");
            	return;
            }
            // If there is no more indentation possible we must stop
            if (!(newFather instanceof TaskNode)) {
                continue;
            }
            if (cdmtn[i] instanceof TaskNode && newFather instanceof TaskNode) {
            	Task nextTask = (Task) cdmtn[i].getUserObject();
            	Task container = (Task) newFather.getUserObject();
            	if (existDepend(nextTask, container) || existDepend(container, nextTask)) {
            		FDCMsgBox.showInfo(this, "所选任务:["+container.getName()+"]与["+nextTask.getName()+"]存在搭接关系，不能执行此操作");
                	return;
            	}
            }
            DeleteParentDependency deletor = new DeleteParentDependency((KDTask) newFather.getUserObject());
			Thread deleteThread = new Thread(deletor);
			deleteThread.start();
        }  
        getUndoManager().undoableEdit("Indent", new Runnable() {
            public void run() {
                for (int i = 0; i < cdmtn.length; i++) {
                    DefaultMutableTreeNode newFather = cdmtn[i].getPreviousSibling();
                    if (!(newFather instanceof TaskNode)) {
                        continue;
                    }
                    if(cdmtn[i].getUserObject() instanceof KDTask){
                    	KDTask taskInfo = (KDTask)cdmtn[i].getUserObject();
                    	if(!taskInfo.isEditable()){
                    		continue;
                    	}
                    }
                    if (cdmtn[i] instanceof TaskNode && newFather instanceof TaskNode) {
                    	Task nextTask = (Task) cdmtn[i].getUserObject();
                    	Task container = (Task) newFather.getUserObject();
                    	getTaskManager().getTaskHierarchy().move(nextTask, container);
                    }
                }  
                // ((ScheduleGanttProject)appli).reCalculateCode(parentWbsId, true);
                //reFillTaskByTaskMap(wbsIdList, "indent");
				resetTask((ScheduleGanttProject) appli, null, 0);
                area.repaint();
                appli.repaint2();
//                appli.setAskForSave(true);
            }
        });

    }

  
    /**
     * Function to dedent selected task this will change the parent child
     * relationship. This Code is based on the UP/DOWN Coder I found in here
     * barmeier
     */
    /** Unindent the selected nodes. */
    public void dedentCurrentNodes() {
        final DefaultMutableTreeNode[] cdmtn = getSelectedTaskNodes();
        if (cdmtn == null) {
            myUIFacade.setStatusText(language.getText("msg21"));
            return;
        }
        int level = cdmtn[0].getLevel();
        for (int i = 0; i < cdmtn.length; i++) {
            if(level != cdmtn[i].getLevel())
        	{
            	FDCMsgBox.showInfo(this, "所选任务不是同一级次，不能进行此操作");
            	return;
        	}
        }  
        final GanttTree2 gt2 = this;
        getUndoManager().undoableEdit("Dedent", new Runnable() {
            public void run() {
                TreePath[] selectedPaths = new TreePath[cdmtn.length];

                // Information about previous node is needed to determine if current node had sibling that was moved.
                DefaultMutableTreeNode previousFather = new DefaultMutableTreeNode();
                DefaultMutableTreeNode father = new DefaultMutableTreeNode();

                HashSet targetContainers = new HashSet();
                for (int i = 0; i < cdmtn.length; i++) {
                	if(cdmtn[i].getUserObject() instanceof KDTask){
                    	KDTask taskInfo = (KDTask)cdmtn[i].getUserObject();
                    	if(!taskInfo.isEditable()){
                    		continue;
                    	}
                    }

                    // We use information about previous father to determine new index of the node in the tree.
                    if (i > 0){
                        previousFather = father;
                    }
                    
                    
                    
                    father = gt2.getFatherNode(cdmtn[i]);

                    // Getting the fathers father !? The grandpa I think  :)
                    DefaultMutableTreeNode newFather = gt2.getFatherNode(father);
                    // If no grandpa is available we must stop.
                    if (newFather == null) {
                        return;
                    }

                    int oldIndex = father.getIndex((TreeNode) cdmtn[i]);

                    cdmtn[i].removeFromParent();
                    treeModel.nodesWereRemoved(father, new int[] {oldIndex }, new Object[] { cdmtn });

                    targetContainers.add((Task)father.getUserObject());
                    // If node and previous node were siblings add current node after its previous sibling
                    int newIndex;
                    if (i > 0 && father.equals(previousFather) ) {
                        newIndex = newFather.getIndex((TreeNode) cdmtn[i-1]) + 1;
                    } else {
                        newIndex = newFather.getIndex((TreeNode) father) + 1;
                    }

                    ((GanttTreeTableModel) treeModel).insertNodeInto(
                            (MutableTreeNode) cdmtn[i],
                            (MutableTreeNode) newFather, newIndex);

                    // Select tagain this node
                    TreeNode[] treepath = cdmtn[i].getPath();
                    TreePath path = new TreePath(treepath);
                    // tree.setSelectionPath(path);
                    selectedPaths[i] = path;

                    // refresh the father date
                    // Task current = (Task)(cdmtn[i].getUserObject());
                    // refreshAllFather(current.toString());

                    expandRefresh(cdmtn[i]);

                    if (father.getChildCount() == 0)
                        ((Task) father.getUserObject()).setProjectTask(false);

					/***
					 * 更新taskInfo的level
					 */
                    DefaultMutableTreeNode movedNode = cdmtn[i];
                    
                    Object whereMove = newFather.getUserObject();
            		if(movedNode instanceof TaskNode&&movedNode.getUserObject() instanceof KDTask){
            			KDTask whatMove = (KDTask)cdmtn[i].getUserObject();
            			ScheduleTaskBaseInfo movedTaskInfo = whatMove.getScheduleTaskInfo();
            			
            			if(cdmtn[i]!=null&&movedTaskInfo!=null){
            				movedTaskInfo.setLevel(movedNode.getLevel());
            				if(movedTaskInfo instanceof FDCScheduleTaskInfo){
            					if(whereMove!=null&&whereMove instanceof KDTask){
            						ScheduleTaskBaseInfo targetTaskInfo = ((KDTask)whereMove).getScheduleTaskInfo();
            						((FDCScheduleTaskInfo)movedTaskInfo).setParent(((FDCScheduleTaskInfo)targetTaskInfo));
            					}
            					else{
            						((FDCScheduleTaskInfo)movedTaskInfo).setParent(null);
            					}
            				}
            				
            			}
            		}
                }
                getTaskManager().getAlgorithmCollection().getAdjustTaskBoundsAlgorithm().run(
                		(Task[])targetContainers.toArray(new Task[0]));
                forwardScheduling();
                treetable.getTree().setSelectionPaths(selectedPaths);

                area.repaint();
                resetTask((ScheduleGanttProject) appli, null, 0);
				appli.repaint2();
            }
        });
    }


	private static FDCScheduleTaskInfo getSCHTask(DefaultMutableTreeNode node) {
		if (node != null && node.getUserObject() instanceof KDTask) {
			return (FDCScheduleTaskInfo) ((KDTask) node.getUserObject()).getScheduleTaskInfo();
		}
		return null;
	}

	/**
	 * 重算Task的属性，包括长编码、级次、是否明细、taskID（序号）等
	 * 
	 * @param ganttProject
	 * @param parentNode
	 * @param seq
	 * @param numberIndex
	 */
	public static int resetTask(ScheduleGanttProject ganttProject,
			DefaultMutableTreeNode parentNode, int seq, int numberIndex) {
		if (parentNode == null) {
			parentNode = ganttProject.getTree().getRoot();
			seq = 0;
		}
		FDCScheduleTaskInfo parentTask = getSCHTask(parentNode);
		int childCount = parentNode.getChildCount();
		if (parentTask != null && childCount > 0) {
			parentTask.setIsLeaf(false);
		}
		for (int i = 0; i < childCount; ++i) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) parentNode.getChildAt(i);
			FDCScheduleTaskInfo task = getSCHTask(node);
			if (i < 99) {
				task.setNumber(FDCSCHUtils.formatTaskNumber(i + 1));
			} else {
				task.setNumber(String.valueOf(i + 1));
			}
			task.setIsLeaf(node.getChildCount() == 0);
			int curTaskID = ++seq;
			task.setSeq(curTaskID);
			// if (node.getUserObject() instanceof KDTask) {
			// ((KDTask) node.getUserObject()).setTaskID(curTaskID);
			// }
			if (parentTask == null) {
				task.setLongNumber(task.getNumber());
				task.setLevel(0);
			} else {
				task.setLongNumber(parentTask.getLongNumber() + "!" + task.getNumber());
				task.setLevel(parentTask.getLevel() + 1);
			}
			//ganttProject.getTree().getModel().setValueAt(task.getLongNumber().
			// replace('!', '.'), node, numberIndex);
			seq = resetTask(ganttProject, node, seq, numberIndex);
		}
		return seq;
	}
	public static void resetTask(ScheduleGanttProject ganttProject, DefaultMutableTreeNode parentNode, int seq) {
		// 由于隐藏了编码列，这里不再需要传编码所在列修改界面了，暂时保留index参数，防止以后又要加上
		// edit by emanon
		int numberIndex = -1;
		resetTask(ganttProject, parentNode, seq, numberIndex);
	}
	
	
    // update 21/03/2003
    /** Refresh the expantion (recursive function) */
    public void expandRefresh(DefaultMutableTreeNode moved) {
        if (moved instanceof TaskNode) {
            Task movedTask = (Task) moved.getUserObject();
            // if (expand.contains(new Integer(movedTask.getTaskID()))) {
            if (movedTask.getExpand())
                treetable.getTree().expandPath(new TreePath(moved.getPath()));

            Enumeration children = moved.children();
            while (children.hasMoreElements()) {
                expandRefresh((DefaultMutableTreeNode) children.nextElement());
            }
        }
    }
    
    /**
     * 提供ganttTree展开到第N层的方法。(N层以下的都收缩)
     * @param level 需要展开到的层数
     */
    public void expandOnLevel(int level) {
		if (rootNode == null)
			return;

		expandNodeOnLevel((DefaultMutableTreeNode)rootNode, level);
	}
    
    public void expandNodeOnLevel(DefaultMutableTreeNode node, int level) {
		TreeNode[] pathtoRoot = node.getPath();
		int depth = pathtoRoot.length;
		TreePath thepath = new TreePath(pathtoRoot);
		//对每个可能要展开的结点，首先判断其深度depth是否超过了展开级别level
		if (depth - 1 >= level)
		{
			//如果该结点的深度已达到或超过level，则需要将该节点收缩并返回
			treetable.getTree().collapsePath(thepath);
			return;
		}
		else
		// 如果没有超过，则：
		{
			// 先展开本结点
			treetable.getTree().expandPath(thepath);

			// 然后对本结点的所有子节点递归调用本方法
			int childCount = node.getChildCount();
			for (int i = 0; i < childCount; i++)
			{
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
				expandNodeOnLevel(child, level);
			}

		}
	}
    
    /**
     * 提供ganttTree全展开的方法
     */
    public void expandAllNode() {
    	if (rootNode == null)
			return;
    	expandAllNode(rootNode);
    }
    
    public void expandAllNode(DefaultMutableTreeNode node) {
		TreeNode[] pathtoRoot = node.getPath();
		TreePath thepath = new TreePath(pathtoRoot);
		treetable.getTree().expandPath(thepath);

		// 然后对本结点的所有子节点递归调用本方法
		int childCount = node.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			expandAllNode(child);
		}
	}

    /**
     * In elder version, this function refresh all the related tasks to the
     * taskToMove. In the new version, this function is same as
     * forwardScheduling(). It will refresh all the tasks.
     *
     * @param taskToMove
     */
    public void refreshAllChild(String taskToMove) {
        forwardScheduling();
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Listener when scrollbar move
     */
    public class GanttAdjustmentListener implements AdjustmentListener {
        public void adjustmentValueChanged(AdjustmentEvent e) {
            if (area != null) {
            	
            	/***
            	 * 
            	 * 动态初始化
            	 */
                int v = e.getValue();
                
                area.setScrollBar(v);
                area.repaint();
                JScrollBar scrollBar = (JScrollBar)e.getSource();   
                BoundedRangeModel model = scrollBar.getModel();
                if(model.getValue()+model.getExtent() >= 2*model.getMaximum()/3){
                	if(appli instanceof ScheduleGanttProject){
            			ScheduleGanttProject scheduleGP = (ScheduleGanttProject)appli;
            			ScheduleBaseInfo editData = scheduleGP.getEditData();
            			if(editData != null && !editData.isReadOnly()) return;
            			Map kdTaskMap = scheduleGP.getKDTaskMaps();
            			TaskManager taskManager = getTaskManager();
            			if(editData==null)
            				return;
//            			if(!editData.getBoolean("hasInit"))
//            				return;
        				int initIndex = editData.getInt("initIndex");
        				if(editData instanceof FDCScheduleInfo){
        					FDCScheduleInfo schedule = (FDCScheduleInfo)editData;
        			        Map dependBaseKeyMap = null;
        			        if(schedule.containsKey("dependBaseKeyMap")){
        			        	dependBaseKeyMap = (HashMap)schedule.get("dependBaseKeyMap");
        			        }else{
        			        	dependBaseKeyMap = new HashMap();
        			        	schedule.put("dependBaseKeyMap",dependBaseKeyMap);
        			        }
        					int size = schedule.getTaskEntrys().size();
        					logger.info("load task(adjustmentValueChanged), size: "+size+"; initIndex:"+initIndex);
        					for(int i = initIndex+1;i < size && i < initIndex + 11; i++){
        						FDCScheduleTaskInfo scheduleTaskInfo = schedule.getTaskEntrys().get(i);
        						//scheduleTaskInfo 的信息在实例化得时候就已经创建，并复制到Task
//        						GanttTask task = taskManager.createTask();
        						KDTask task=new KDTask(taskManager,scheduleTaskInfo);
        						taskManager.registerTask(task);
        				        TaskContainmentHierarchyFacade taskHierarchy = taskManager.getTaskHierarchy();
        				        //将当前节点放到他的父节点下面        		
        				        if(task.getScheduleTaskInfo().getObjectValue("parent")!=null){
        				        	String parentId=task.getScheduleTaskInfo().getObjectValue("parent").getString("id");
            						Task parentTask = (KDTask)kdTaskMap.get(parentId);
            						taskHierarchy.move(task, parentTask);
        				        }
        				        else{
        				        	addObject(task, getRoot(),-1);
        				        }
        				        
        				        
        				        kdTaskMap.put(scheduleTaskInfo.getId().toString(), task);
        				        
        				        ScheduleTaskDependCollection depends = scheduleTaskInfo.getDepends();
        				        for(Iterator iter=depends.iterator();iter.hasNext();){
        				        	ScheduleTaskDependInfo dependInfo = (ScheduleTaskDependInfo)iter.next();
        				        	if(dependInfo.getDependTaskBase()!=null&&dependBaseKeyMap!=null){
        				        		List dependeeList = null;
        				        		if(dependBaseKeyMap.containsKey(dependInfo.getDependTaskBase().getId().toString())){
        				        			dependeeList = (ArrayList)dependBaseKeyMap.get(dependInfo.getDependTaskBase().getId().toString());
        				        		}else{
        				        			dependeeList = new ArrayList();
        				        			dependBaseKeyMap.put(dependInfo.getDependTaskBase().getId().toString(), dependeeList);
        				        		}
        				        		dependeeList.add(dependInfo);
        				        	}
        				        	TaskLinkTypeEnum type = dependInfo.getType();
        				        	FDCScheduleTaskInfo dependantInfo = (FDCScheduleTaskInfo)dependInfo.getDependTaskBase();
        				        	Task dependant = (KDTask)kdTaskMap.get(dependantInfo.getId().toString());
        				        	if(dependant!=null){
        				        		int difference= dependInfo.getDifference();
            				        	TaskDependency dependency;
    									try {
    										dependency = getTaskManager().getDependencyCollection().createDependency(
    												dependant, task, ScheduleParserHelper.getTaskDependencyConstraintByLinkType(type));
    										dependency.setDifference(difference);
    									} catch (TaskDependencyException e1) {
    										e1.printStackTrace();
    									}
        				        	}
        				        }
        				        if(dependBaseKeyMap!=null&&dependBaseKeyMap.containsKey(scheduleTaskInfo.getId().toString())){
        				        	List dependeeList = (ArrayList)dependBaseKeyMap.get(scheduleTaskInfo.getId().toString());
        				        	for(Iterator it = dependeeList.iterator();it.hasNext();){
        				        		ScheduleTaskDependInfo dependInfo = (ScheduleTaskDependInfo)it.next();
        				        		TaskLinkTypeEnum type = dependInfo.getType();
            				        	FDCScheduleTaskInfo dependantInfo = (FDCScheduleTaskInfo)dependInfo.getTaskBase();
            				        	Task dependant = (KDTask)kdTaskMap.get(dependantInfo.getId().toString());
            				        	int difference= dependInfo.getDifference();
            				        	TaskDependency dependency;
    									try {
    										dependency = getTaskManager().getDependencyCollection().createDependency(
    												task, dependant, ScheduleParserHelper.getTaskDependencyConstraintByLinkType(type));
    										dependency.setDifference(difference);
    									} catch (TaskDependencyException e1) {
    										e1.printStackTrace();
    									}
        				        	}
        				        }
        				        schedule.setInt("initIndex", i);
        					}
        					if( initIndex+1 >= size){
        						scheduleUI.load2GanttAfter(editData);
        					}
        					scheduleUI.showMsg4LoadData();
        				}
        				
            			
                	}
                }
            }
        }
    }
    
    

    // ////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Class for expansion and collapse of node
     */
    public class GanttTreeExpansionListener implements TreeExpansionListener {
        /** Expansion */
        public void treeExpanded(TreeExpansionEvent e) {
            if (area != null) {
                area.repaint();
            }
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getPath()
                    .getLastPathComponent());
            Task task = (Task) node.getUserObject();

            /*
             * if(!expand.contains(new Integer(task.getTaskID()))) {
             * expand.add(new Integer(task.getTaskID()));
             * appli.setAskForSave(true); }
             */

            task.setExpand(true);
            appli.setAskForSave(true);

        }

        /** Collapse */
        public void treeCollapsed(TreeExpansionEvent e) {
            if (area != null) {
                area.repaint();
            }

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getPath()
                    .getLastPathComponent());
            Task task = (Task) node.getUserObject();

            /*
             * int index = expand.indexOf(new Integer(task.getTaskID())); if
             * (index >= 0) { expand.remove(index); appli.setAskForSave(true); }
             */

            task.setExpand(false);
            appli.setAskForSave(true);
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Listener to generate modification on the model
     */
    public class GanttTreeModelListener implements TreeModelListener {
        /** modify a node */
        public void treeNodesChanged(TreeModelEvent e) {
            if (area != null) {
                area.repaint();
            }
        }

        /** Insert a new node. */
        public void treeNodesInserted(TreeModelEvent e) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e
                    .getTreePath().getLastPathComponent());
            Task task = (Task) node.getUserObject();

            /*
             * if (!expand.contains(new Integer(task.getTaskID()))) {
             * expand.add(new Integer(task.getTaskID())); }
             */

            // task.setExpand(true);
            if (area != null)
                area.repaint();
        }

        /** Delete a node. */
        public void treeNodesRemoved(TreeModelEvent e) {
            if (area != null) {
                area.repaint();
            }
        }

        /** Structur change. */
        public void treeStructureChanged(TreeModelEvent e) {
            if (area != null) {
                area.repaint();
            }
        }
    }

    private class GanttTreeDropListener implements DropTargetListener {
        private TreePath lastPath = null;

        private Rectangle2D cueLineRect = new Rectangle2D.Float();

        private Rectangle2D ghostImageRect = new Rectangle2D.Float();

        private Color cueLineColor;

        private Point lastEventPoint = new Point();

        private Timer hoverTimer;

        public GanttTreeDropListener() {
            cueLineColor = new Color(SystemColor.controlShadow.getRed(),
                    SystemColor.controlShadow.getGreen(),
                    SystemColor.controlShadow.getBlue(), 64);

            // Set up a hover timer, so that a node will be automatically
            // expanded or collapsed
            // if the user lingers on it for more than a short time
            hoverTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!treetable.getTree().isExpanded(lastPath)) {
                        treetable.getTree().expandPath(lastPath);
                    }
                }
            });
            // Set timer to one-shot mode - it will be restartet when the
            // cursor is over a new node
            hoverTimer.setRepeats(false);
        }

        public void dragEnter(DropTargetDragEvent dtde) {
            if (ghostImage == null) {
                // In case if you drag a file from out and it's not an
                // acceptable, and it can crash if the image is null
                ghostImage = new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB_PRE);
            }
            if (!isDragAcceptable(dtde))
                dtde.rejectDrag();
            else
                dtde.acceptDrag(dtde.getDropAction());
        }

        public void dragOver(DropTargetDragEvent dtde) {
            if (!isDragAcceptable(dtde))
                dtde.rejectDrag();
            else
                dtde.acceptDrag(dtde.getDropAction());

            // Even if the mouse is not moving, this method is still invoked
            // 10 times per second
            Point pt = dtde.getLocation();
            if (pt.equals(lastEventPoint))
                return;

            lastEventPoint = pt;

            Graphics2D g2 = (Graphics2D) treetable.getGraphics();

            // If a drag image is not supported by the platform, then draw our
            // own drag image
            if (!DragSource.isDragImageSupported()) {
                // Rub out the last ghost image and cue line
                treetable.paintImmediately(ghostImageRect.getBounds());
                // And remember where we are about to draw the new ghost image
                ghostImageRect.setRect(pt.x - offsetPoint.x, pt.y
                        - offsetPoint.y, ghostImage.getWidth(), ghostImage
                        .getHeight());
                g2.drawImage(ghostImage, AffineTransform.getTranslateInstance(
                        ghostImageRect.getX(), ghostImageRect.getY()), null);
            } else {
                // Just rub out the last cue line
                treetable.paintImmediately(cueLineRect.getBounds());
            }

            TreePath path = treetable.getTree().getClosestPathForLocation(pt.x,
                    pt.y);
            if (!(path == lastPath)) {
                lastPath = path;
                hoverTimer.restart();
            }

            // In any case draw (over the ghost image if necessary) a cue line
            // indicating where a drop will occur
            Rectangle raPath = treetable.getTree().getPathBounds(path);
            if (raPath == null)
                raPath = new Rectangle(1, 1);
            cueLineRect.setRect(0, raPath.y + (int) raPath.getHeight(),
                    getWidth(), 2);

            g2.setColor(cueLineColor);
            g2.fill(cueLineRect);

            // And include the cue line in the area to be rubbed out next time
            ghostImageRect = ghostImageRect.createUnion(cueLineRect);

        }

        public void dropActionChanged(DropTargetDragEvent dtde) {
            if (!isDragAcceptable(dtde))
                dtde.rejectDrag();
            else
                dtde.acceptDrag(dtde.getDropAction());
        }

        public void drop(DropTargetDropEvent dtde) {
            if (!isDropAcceptable(dtde)) {
                dtde.rejectDrop();
                return;
            }

            // Prevent hover timer from doing an unwanted expandPath or
            // collapsePath
            hoverTimer.stop();

            dtde.acceptDrop(dtde.getDropAction());

            Transferable transferable = dtde.getTransferable();

            DataFlavor[] flavors = transferable.getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                DataFlavor flavor = flavors[i];
                if (flavor
                        .isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
                    try {
                        Point pt = dtde.getLocation();
                        DefaultMutableTreeNode target = (DefaultMutableTreeNode) treetable
                                .getTree()
                                .getClosestPathForLocation(pt.x, pt.y)
                                .getLastPathComponent();
                        TreePath pathSource = (TreePath) transferable
                                .getTransferData(flavor);
                        DefaultMutableTreeNode source = (DefaultMutableTreeNode) pathSource
                                .getLastPathComponent();

                        TreePath pathNewChild = null;

                        TreeNode sourceFather = source.getParent();
                        int index = sourceFather.getIndex(source);
                        source.removeFromParent();

                        treeModel.nodesWereRemoved(sourceFather,
                                new int[] { index }, new Object[] { source });

                        ((GanttTreeTableModel) treeModel).insertNodeInto(
                                source, target, 0);

                        pathNewChild = new TreePath(
                                ((DefaultMutableTreeNode) pathSource
                                        .getLastPathComponent()).getPath());

                        if (pathNewChild != null) {
                            treetable.getTree().setSelectionPath(pathNewChild); // Mark
                            // this
                            // as
                            // the
                            // selected
                            // path
                            // in
                            // the
                            // tree
                        }

                        // refreshAllFather(source.getUserObject().toString());

                        expandRefresh(source);

                        forwardScheduling();

                        area.repaint();

                        appli.setAskForSave(true);

                        break; // No need to check remaining flavors
                    } catch (UnsupportedFlavorException ufe) {
                        System.out.println(ufe);
                        dtde.dropComplete(false);
                        return;
                    } catch (IOException ioe) {
                        System.out.println(ioe);
                        dtde.dropComplete(false);
                        return;
                    }
                }
            }
            dtde.dropComplete(true);
        }

        public void dragExit(DropTargetEvent dte) {
            if (!DragSource.isDragImageSupported()) {
                repaint(ghostImageRect.getBounds());
            }
            treetable.repaint();
        }

        public boolean isDragAcceptable(DropTargetDragEvent e) {
            // Only accept COPY or MOVE gestures (ie LINK is not supported)
            if ((e.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
                return false;
            }

            // Only accept this particular flavor
            if (!e
                    .isDataFlavorSupported(GanttTransferableTreePath.TREEPATH_FLAVOR)) {
                return false;
            }

            // Do not accept dropping on the source node
            Point pt = e.getLocation();
            TreePath path = treetable.getTree().getClosestPathForLocation(pt.x,
                    pt.y);
            if (dragPath.isDescendant(path)) {
                return false;
            }
            if (path.equals(dragPath)) {
                return false;
            }

            // Check if the task is a milestone task
            Task task = (Task) (((DefaultMutableTreeNode) path
                    .getLastPathComponent()).getUserObject());
            if (task.isMilestone())
                return false;

            return true;
        }

        public boolean isDropAcceptable(DropTargetDropEvent e) {
            // Only accept COPY or MOVE gestures (ie LINK is not supported)
            if ((e.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
                return false;
            }

            // Only accept this particular flavor
            if (!e
                    .isDataFlavorSupported(GanttTransferableTreePath.TREEPATH_FLAVOR)) {
                return false;
            }

            // prohibit dropping onto the drag source
            Point pt = e.getLocation();
            TreePath path = treetable.getTree().getClosestPathForLocation(pt.x,
                    pt.y);
            if (path.equals(dragPath)) {
                return false;
            }
            return true;
        }

    }

    private static class GanttTransferableTreePath implements Transferable {
        // The type of DnD object being dragged...
        public static final DataFlavor TREEPATH_FLAVOR = new DataFlavor(
                DataFlavor.javaJVMLocalObjectMimeType, "TreePath");

        private TreePath _path;

        private DataFlavor[] _flavors = { TREEPATH_FLAVOR };

        /**
         * Constructs a transferrable tree path object for the specified path.
         */
        public GanttTransferableTreePath(TreePath path) {
            _path = path;
        }

        // Transferable interface methods...
        public DataFlavor[] getTransferDataFlavors() {
            return _flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return java.util.Arrays.asList(_flavors).contains(flavor);
        }

        public synchronized Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (flavor.isMimeTypeEqual(TREEPATH_FLAVOR.getMimeType())) // DataFlavor.javaJVMLocalObjectMimeType))
                return _path;
            else
                throw new UnsupportedFlavorException(flavor);
        }

    }

    /**
     * Render the cell of the tree
     */
    public class GanttTreeCellRenderer extends DefaultTreeCellRenderer // JLabel-CL
            implements TreeCellRenderer {

        public GanttTreeCellRenderer() {
            setOpaque(true);
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            Task task = (Task) ((DefaultMutableTreeNode) value).getUserObject();// getTask(value.toString());
            if (task == null) {
                return this;
            }
            int type = 0;

            setFont(Fonts.GANTT_TREE_FONT);

            if (task.isMilestone()) {
                setIcon(new ImageIcon(getClass().getResource(
                        "/icons/meeting.gif")));
                type = 1;
            } else if (leaf) {
                if (task.getPriority() == GanttTask.LOW) {
                    setIcon(new ImageIcon(getClass().getResource(
                            "/icons/task1.gif")));
                } else if (task.getPriority() == GanttTask.NORMAL) {
                    setIcon(new ImageIcon(getClass().getResource(
                            "/icons/task.gif")));
                } else if (task.getPriority() == GanttTask.HIGHT) {
                    setIcon(new ImageIcon(getClass().getResource(
                            "/icons/task2.gif")));
                }
                type = 2;
            } else {
                setIcon(new ImageIcon(getClass()
                        .getResource("/icons/mtask.gif")));
                setFont(Fonts.GANTT_TREE_FONT2);
            }

            setText(task.toString());
            setToolTipText(getToolTip(task, type));
            setBackground(selected ? new Color((float) 0.290, (float) 0.349,
                    (float) 0.643) : row % 2 == 0 ? Color.white : new Color(
                    (float) 0.933, (float) 0.933, (float) 0.933));
            setForeground(selected ? Color.white : Color.black);
            return this;
        }

        public String getToolTip(Task task, int type) {
            String res = "<html><body bgcolor=#EAEAEA>";
            res += "<b>" + task.toString() + "</b>" + "<br>" + task.getStart();
            if (type != 1) {
                res += "  -  " + task.getEnd();
            }
            if (type == 1) {
                res += "<br>" + language.getText("meetingPoint");
            }

            res += "<br><b>Pri</b> "
                    + (task.getPriority() == 0 ? language.getText("low") : task
                            .getPriority() == 1 ? language.getText("normal")
                            : language.getText("hight"));

            ResourceAssignment[] assignments = task.getAssignments();
            if (assignments.length > 0) {
                res += "<br><b>Assign to </b><br>";
                for (int j = 0; j < assignments.length; j++) {
                    res += "&nbsp;&nbsp;"
                            + assignments[j].getResource().getName() + "<br>";
                }
            }

            if (task.getNotes() != null && !task.getNotes().equals("")) {
                String notes = task.getNotes();
                res += "<br><b>Notes </b>: ";
                int maxLength = 150;
                if (notes.length() > maxLength) {
                    notes = notes.substring(0, maxLength);
                    int i = maxLength - 1;
                    for (; i >= 0 && notes.charAt(i) != ' '; i--)
                        ;
                    notes = notes.substring(0, i);
                    notes += " <b>( . . . )</b>";
                }
                notes = notes.replaceAll("\n", "<br>");
                res += "<font size=\"-2\">" + notes + "</font>";
            }

            res += "</body></html>";
            return res;
        }

    }

    // ----------Newly Added Code--------------//CL
    /** Temporary treeNode for copy and paste */
    private DefaultMutableTreeNode cpNode;

    private ArrayList cpNodesArrayList;

    private ArrayList allNodes;

    private ArrayList cpDependencies;

    // private ArrayList copyID;
    // private ArrayList pasteID;

    private Map mapOriginalIDCopyID;

    /** Cut the current selected tree node */
    public void cutSelectedNode() {
        final TreePath currentSelection = treetable.getTree()
                .getSelectionPath();
        final DefaultMutableTreeNode[] cdmtn = getSelectedNodes();
        if (currentSelection != null) {
            getUndoManager().undoableEdit("Cut", new Runnable() {
                public void run() {
                    cpNodesArrayList = new ArrayList();
                    cpAllDependencies(cdmtn);
                    GanttTask taskFather = null;
                    DefaultMutableTreeNode father = null;
                    DefaultMutableTreeNode current = null;
                    for (int i = 0; i < cdmtn.length; i++) {
                        current = getSelectedTaskNode();
                        if (current != null) {
                            cpNodesArrayList
                                    .add((DefaultMutableTreeNode) cdmtn[i]);
                            father = getFatherNode(current/* ttask */);
                            where = father.getIndex(current);
                            removeCurrentNode(current);
                            current.setParent(father);
                            taskFather = (GanttTask) father.getUserObject();
                            AdjustTaskBoundsAlgorithm alg = getTaskManager()
                                    .getAlgorithmCollection()
                                    .getAdjustTaskBoundsAlgorithm();
                            alg.run(taskFather);
                            // taskFather.refreshDateAndAdvancement(this);
                            father.setUserObject(taskFather);
                        }
                    }
                    if (father.getChildCount() == 0)
                        ((Task) father.getUserObject()).setProjectTask(false);
                    if (taskFather != null) {
                        selectTask(taskFather, false);
                    }
                    area.repaint();
                }
            });
            appli.repaint();
        }
    }

    int where = -1;

	private Action myTaskPropertiesAction;



    /** Copy the current selected tree node */
    public void copySelectedNode() {
        TreePath currentSelection = treetable.getTree().getSelectionPath();
        cpNodesArrayList = new ArrayList();
        if (currentSelection != null) {
            final DefaultMutableTreeNode[] cdmtn = getSelectedNodes();
            cpAllDependencies(cdmtn);
            for (int i = 0; i < cdmtn.length; i++) {
                boolean isNodeDescendant = false;
                for (int j = 0; j < cpNodesArrayList.size(); j++) {
                    if (((DefaultMutableTreeNode) cdmtn[i])
                            .isNodeDescendant((DefaultMutableTreeNode) cpNodesArrayList
                                    .get(j))) {
                        isNodeDescendant = true;
                        // if a selected node is a descendant of an other
                        // selected one he doesn't have to be copied
                    }
                }
                if (!isNodeDescendant) {
                    cpNodesArrayList.add((DefaultMutableTreeNode) cdmtn[i]);
                }
            }
        }
    }

    /** Paste the node and its child node to current selected position */
    public void pasteNode() {
        if (cpNodesArrayList != null) {
            final GanttTree2 gt2 = this;
            getUndoManager().undoableEdit("Paste", new Runnable() {
                public void run() {
                    TaskNode current = (TaskNode) treetable.getTree()
                            .getLastSelectedPathComponent();
                    List tasksList = new ArrayList();
                    if (current == null) {
                        current = rootNode;
                    }

                    boolean isAProjectTaskChild = false;
                    DefaultMutableTreeNode father = (DefaultMutableTreeNode) current
                            .getParent();
                    // if the task as a projectTask parent
                    while (father != null) {
                        if (((Task) father.getUserObject()).isProjectTask()) {
                            isAProjectTaskChild = true;
                        }
                        father = (DefaultMutableTreeNode) father.getParent();
                    }
                    mapOriginalIDCopyID = new HashMap();
                    // copyID = new ArrayList ();
                    // pasteID = new ArrayList ();

                    TaskManagerImpl tmi = (TaskManagerImpl) getTaskManager();
                    int MaxID = tmi.getMaxID();
                    // for(int i=0; i < cpNodesArrayList.size(); i++) {
                    for (int i = cpNodesArrayList.size() - 1; i >= 0; i--) {

                        if (isAProjectTaskChild)
                            ((Task) ((TaskNode) cpNodesArrayList.get(i))
                                    .getUserObject()).setProjectTask(false);
                        /*
                         * this will add new custom columns to the newly
                         * created task.
                         */
                        TreeNode sel = getSelectedTaskNode();
                        TreeNode parent = null;
                        if (sel != null) {
                            parent = sel.getParent();
                            if (parent != null)
                                where = parent.getIndex(sel);
                        }
                        tasksList.add((Task) insertClonedNode(
                                current == rootNode ? current
                                        : (DefaultMutableTreeNode) current
                                                .getParent(),
                                (DefaultMutableTreeNode) cpNodesArrayList
                                        .get(i), where + 1, true)
                                .getUserObject());
                        nbTasks++;
                    }
                    if(cpDependencies!=null)
                    {
                    	for (int i = 0; i < cpDependencies.size(); i++) {
                    		// for(int i=cpDependencies.size()-1; i >= 0; i--) {
                    		TaskDependency td = ((TaskDependency) cpDependencies
                    				.get(i));
                    		Task dependee = td.getDependee();
                    		Task dependant = td.getDependant();
                    		TaskDependencyConstraint constraint = td
                                	.getConstraint();
                    		for (int j = 0; j < allNodes.size(); j++) {
                    			for (int k = 0; k < allNodes.size(); k++) {
                    				if ((dependant
                    						.equals((Task) (((DefaultMutableTreeNode) allNodes
                    								.get(j)).getUserObject())))
                    								&& (dependee
                    										.equals((Task) (((DefaultMutableTreeNode) allNodes
                    												.get(k))
                    												.getUserObject())))) {
                    					try {
                    						TaskDependency newDependency = getTaskManager()
                                                	.getDependencyCollection()
                                                	.createDependency(
                                                			getTaskManager()
                                                                	.getTask(
                                                                        ((Integer) mapOriginalIDCopyID
                                                                                .get(new Integer(
                                                                                        dependant
                                                                                                .getTaskID())))
                                                                                .intValue()),
                                                        getTaskManager()
                                                                .getTask(
                                                                        ((Integer) mapOriginalIDCopyID
                                                                                .get(new Integer(
                                                                                        dependee
                                                                                                .getTaskID())))
                                                                                .intValue()),
                                                        getTaskManager()
                                                                .createConstraint(
                                                                        constraint
                                                                                .getID()));
                                        	newDependency.setDifference(td
                                                	.getDifference());
                                        	newDependency.setHardness(td.getHardness());
                                    	} catch (TaskDependencyException e) {
                                        	myUIFacade.showErrorDialog(e);
                                    	}
                                	}
                            	}
                        	}
                    	}
                    }
                    selectTasks(tasksList);
                }
            });
            appli.refreshProjectInfos();
        }

    }

    /** Insert the cloned node and its children */
    private TaskNode insertClonedNode(DefaultMutableTreeNode parent,
            DefaultMutableTreeNode child, int location, boolean first) {
        if (parent == null) {
            return null; // it is the root node
        }
        if (first) {
            GanttTask _t = (GanttTask) (parent.getUserObject());
            if (_t.isMilestone()) {
                _t.setMilestone(false);
                GanttTask _c = (GanttTask) (child.getUserObject());
                _t.setLength(_c.getLength());
                _t.setStart(_c.getStart());
            }
        }

        GanttTask originalTask = (GanttTask) child.getUserObject();
        GanttTask newTask = originalTask.Clone();

        newTask.setName((first ? language.getText("copy2") + "_" : "")
                + newTask.toString());

        TaskManagerImpl tmi = (TaskManagerImpl) getTaskManager();
        newTask.setTaskID(tmi.getMaxID() + 1);
        mapOriginalIDCopyID.put(new Integer(originalTask.getTaskID()),
                new Integer(newTask.getTaskID()));
        // copyID.add (new Integer (originalTask.getTaskID()));
        // pasteID.add (new Integer (newTask.getTaskID()));

        ResourceAssignment[] assignment = newTask.getAssignments();
        for (int i = 0; i < assignment.length; i++) {
            assignment[i].delete();
        }

        getTaskManager().registerTask(newTask);

        DefaultMutableTreeNode cloneChildNode = new TaskNode(newTask);

        for (int i = 0; i < child.getChildCount(); i++) {
            insertClonedNode(cloneChildNode, (DefaultMutableTreeNode) child
                    .getChildAt(i), i, false);
        }
        // if(child.getParent() != null)
        // if(!child.getParent().equals(parent))
        // parent = (DefaultMutableTreeNode)parent.getParent();
        //
        //
        if (parent == null)
            location = 0;
        if (parent.getChildCount() < location)
            location = parent.getChildCount();

        ((GanttTreeTableModel) treeModel).insertNodeInto(cloneChildNode,
                parent, location);

        treetable.getTree().scrollPathToVisible(
                new TreePath(cloneChildNode.getPath()));

        // Remove the node from the expand list
        /*
         * int index = expand.indexOf(new
         * Integer(newTask.getTaskID())cloneChildNode.toString()); if (index >=
         * 0) expand.remove(index);
         */
        newTask.setExpand(false);
        return (TaskNode) cloneChildNode;

    }

    public void forwardScheduling() {
        RecalculateTaskScheduleAlgorithm alg = getTaskManager()
                .getAlgorithmCollection().getRecalculateTaskScheduleAlgorithm();
        try {
            alg.run();
        } catch (TaskDependencyException e) {
        	myUIFacade.showErrorDialog(e);
        }
    }


    // /////////////////////////////////////////////////////////////////
    // --End CL 15-May-2003
    private TaskManager getTaskManager() {
        return myTaskManager;
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragGestureRecognized(DragGestureEvent dge) {

        Point ptDragOrigin = dge.getDragOrigin();
        TreePath path = treetable.getTree().getPathForLocation(ptDragOrigin.x,
                ptDragOrigin.y);
        if (path == null) {
            return;
        }

        // Work out the offset of the drag point from the TreePath bounding
        // rectangle origin
        Rectangle raPath = treetable.getTree().getPathBounds(path);
        offsetPoint.setLocation(ptDragOrigin.x - raPath.x, ptDragOrigin.y
                - raPath.y);

        // Get the cell renderer (which is a JLabel) for the path being dragged
        // JLabel lbl = (JLabel)
        // treetable.getTree().getCellRenderer().getTreeCellRendererComponent
        // (
        // treetable, // tree
        // path.getLastPathComponent(), // value
        // false, // isSelected (dont want a colored background)
        // treetable.getTree().isExpanded(path), // isExpanded
        // ((DefaultTreeTableModel)treetable.getModel()).isLeaf(path.getLastPathComponent()),
        // // isLeaf
        // 0, // row (not important for rendering)
        // false // hasFocus (dont want a focus rectangle)
        // );
		JLabel lbl = new JLabel();
        lbl.setSize((int) raPath.getWidth(), (int) raPath.getHeight()); // <--
        // The
        // layout
        // manager
        // would
        // normally
        // do
        // this

        // Get a buffered image of the selection for dragging a ghost image
        ghostImage = new BufferedImage((int) raPath.getWidth(), (int) raPath
                .getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2 = ghostImage.createGraphics();

        // Ask the cell renderer to paint itself into the BufferedImage
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f)); // Make
        // the
        // image
        // ghostlike
        lbl.paint(g2);

        // Now paint a gradient UNDER the ghosted JLabel text (but not under the
        // icon if any)
        // Note: this will need tweaking if your icon is not positioned to the
        // left of the text
        Icon icon = lbl.getIcon();
        int nStartOfText = (icon == null) ? 0 : icon.getIconWidth()
                + lbl.getIconTextGap();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER,
                0.5f)); // Make the gradient ghostlike
        g2.setPaint(new GradientPaint(nStartOfText, 0,
                SystemColor.controlShadow, getWidth(), 0, new Color(255, 255,
                        255, 0)));
        g2.fillRect(nStartOfText, 0, getWidth(), ghostImage.getHeight());

        g2.dispose();

        treetable.getTree().setSelectionPath(path); // Select this path in the
        // tree

        // Wrap the path being transferred into a Transferable object
        Transferable transferable = new GanttTransferableTreePath(path);

        // Remember the path being dragged (because if it is being moved, we
        // will have to delete it later)
        dragPath = path;

        // We pass our drag image just in case it IS supported by the platform
        dge.startDrag(null, ghostImage, new Point(5, 5), transferable, this);
    }

    public void cpAllDependencies(DefaultMutableTreeNode[] cdmtn) {
        // to get all the dependencies who need to be paste.
        cpDependencies = new ArrayList();
        allNodes = new ArrayList();
        for (int i = 0; i < cdmtn.length; i++) {
            getAllNodes(cdmtn[i]);
        }
        TaskDependency[] dependencies = getTaskManager()
                .getDependencyCollection().getDependencies();
        for (int i = 0; i < dependencies.length; i++) {
            Task dependant = dependencies[i].getDependant();
            Task dependee = dependencies[i].getDependee();
            for (int j = 0; j < allNodes.size(); j++) {
                for (int k = 0; k < allNodes.size(); k++)
                    if (((Task) (((DefaultMutableTreeNode) allNodes.get(j))
                            .getUserObject())).equals(dependant)
                            && ((Task) (((DefaultMutableTreeNode) allNodes
                                    .get(k)).getUserObject())).equals(dependee)) {
                        cpDependencies.add(dependencies[i]);
                    }
            }
        }
    }

    // public int cpId (int index) {
    // System.out.println("index = " + index + " -> " +
    // ((Integer)pasteID.get(index)).intValue());
    // return ((Integer)pasteID.get(index)).intValue();
    // }

    public void getAllNodes(DefaultMutableTreeNode dmt) {
        // get all the nodes the parent and all his descendance
        if (!allNodes.contains(dmt))
            allNodes.add(dmt);
        for (int i = 0; i < dmt.getChildCount(); i++) {
            getAllNodes((DefaultMutableTreeNode) dmt.getChildAt(i));
        }
    }

    public boolean hasAProjectTaskDescendant(DefaultMutableTreeNode node) {
        ArrayList child = getAllChildTask(node);
        for (int i = 0; i < child.size(); i++) {
            if (((Task) ((DefaultMutableTreeNode) child.get(i)).getUserObject())
                    .isProjectTask())
                return true;
            if (hasAProjectTaskDescendant((DefaultMutableTreeNode) child.get(i)))
                return true;
        }
        return false;
    }

    public ArrayList getProjectTasks() {
        ArrayList projectTasks = new ArrayList();
        // for (int i = 0 ; i < getAllTasks().size() ; i++) {
        // TaskNode node = (TaskNode)getAllTasks().get(i);
        // if (((Task)node.getUserObject()).isProjectTask())
        // projectTasks.add (node);
        // }
        getProjectTasks(this.rootNode, projectTasks);
        return projectTasks;
    }

    public void getProjectTasks(DefaultMutableTreeNode node, ArrayList list) {
        ArrayList child = getAllChildTask(node);
        for (int i = 0; i < child.size(); i++) {
            DefaultMutableTreeNode taskNode = (DefaultMutableTreeNode) child
                    .get(i);
            if (((Task) taskNode.getUserObject()).isProjectTask())
                list.add(taskNode);
            else
                getProjectTasks(taskNode, list);
        }
    }

    public void setDelay(final Task task, final Delay delay) {
    	//这个方法有用吗？ 去掉先
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                TaskNode taskNode = (TaskNode) getNode(task.getTaskID());
//                if (taskNode != null)
//                    treetable.setDelay(taskNode, delay);
//            }
//        });
    }

    public GanttTreeTableModel getModel() {
        return treeModel;
    }

    private GPUndoManager getUndoManager() {
        return myUIFacade.getUndoManager();
    }
    ///////////////////////////////////////////////////////////////////////////
    // ProjectEventListener
    public void projectModified() {
        // TODO Auto-generated method stub

    }

    public void projectSaved() {
        // TODO Auto-generated method stub

    }

    public void projectClosed() {
        clearTree();
        getTreeTable().reloadColumns();
    }

    ////////////////////////////////////////////////////////////////////////
    //TaskTreeUIFacade
    public Component getTreeComponent() {
        return this;
    }
    public Action getIndentAction() {
        return myIndentAction;
    }
    public Action getUnindentAction() {
        return myDedentAction;
    }

    public Action getMoveDownAction() {
        return myMoveUpAction;
    }

    public Action getMoveUpAction() {
        return myMoveDownAction;
    }

    public void setLinkTasksAction(Action action) {
        myLinkTasksAction = action;
    }
    public Action getLinkTasksAction() {
        return myLinkTasksAction;
    }
    //
    public void setUnlinkTasksAction(Action action) {
        myUnlinkTasksAction = action;
    }
    public Action getUnlinkTasksAction() {
        return myUnlinkTasksAction;
    }

    public void setTaskPropertiesAction(Action action) {
    	myTaskPropertiesAction = action;
        treetable.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_DOWN_MASK), action.getValue(Action.NAME));
        treetable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_DOWN_MASK), action.getValue(Action.NAME));
        treetable.getActionMap().put(action.getValue(Action.NAME), action);
    }

    private Action getTaskPropertiesAction() {
    	return myTaskPropertiesAction;
    }

	public TableHeaderUIFacade getVisibleFields() {
		return treetable.getVisibleFields();
	}
	
    private Map myTaskId2treeNode = new HashMap();
    //TODO 以后再优化
//    private Map myTask2treeNode = new HashMap();
//    private Map myTask2index = new LinkedHashMap();

    public Map getTaskId2TreeNodeMap(){
    	return myTaskId2treeNode;
    }
/*    public Map getTask2TreeNodeMap(){
    	return myTask2treeNode;
    }
    public Map getTask2IndexMap(){
    	return myTask2index;
    }*/
    
    private class GotoStartPopupAction extends AbstractAction implements   GanttLanguage.Listener{
		public void languageChanged(Event event) {
	        setText(event.getLanguage());
		}
	
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode treeNode = getSelectedNode();
			if(treeNode != null){
				Object temp = treeNode.getUserObject();
				if(temp instanceof KDTask){
					((ScheduleGanttProject)appli).gotoDate(((KDTask)temp).getScheduleTaskInfo().getStart());
				}
			}
		} 
		private void setText(GanttLanguage language) {
	        this.putValue(AbstractAction.NAME, "开始时间");
	    }
    	
    }
    private class GotoFinishPopupAction extends AbstractAction implements   GanttLanguage.Listener{
		public void languageChanged(Event event) {
	        setText(event.getLanguage());
		}
	
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode treeNode = getSelectedNode();
			if(treeNode != null){
				Object temp = treeNode.getUserObject();
				if(temp instanceof KDTask){
					((ScheduleGanttProject)appli).gotoDate(((KDTask)temp).getScheduleTaskInfo().getEnd());
				}
			}
		} 
		private void setText(GanttLanguage language) {
	        this.putValue(AbstractAction.NAME, "结束时间");
	    }
    	
    }
   
    public void setScheduleUI(ScheduleBaseUI scheduleUI){
    	this.scheduleUI = scheduleUI;
    }
    private ScheduleBaseUI scheduleUI;
    
    
}
