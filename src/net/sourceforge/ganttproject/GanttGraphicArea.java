/***************************************************************************

 GanttGraphicArea.java  -  description

 -------------------

 begin                : dec 2002

 copyright            : (C) 2002 by Thomas Alexandre

 email                : alexthomas(at)ganttproject.org

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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.table.JTableHeader;

import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.calendar.CalendarFactory;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.chart.ChartModel;
import net.sourceforge.ganttproject.chart.ChartModelBase;
import net.sourceforge.ganttproject.chart.ChartModelImpl;
import net.sourceforge.ganttproject.chart.ChartSelection;
import net.sourceforge.ganttproject.chart.DependencyInteractionRenderer;
import net.sourceforge.ganttproject.chart.GanttChart;
import net.sourceforge.ganttproject.chart.Painter;
import net.sourceforge.ganttproject.chart.RenderedChartImage;
import net.sourceforge.ganttproject.chart.RenderedGanttChartImage;
import net.sourceforge.ganttproject.chart.TaskInteractionHintRenderer;
import net.sourceforge.ganttproject.chart.VisibleNodesFilter;
import net.sourceforge.ganttproject.chart.item.ChartItem;
import net.sourceforge.ganttproject.chart.item.TaskBoundaryChartItem;
import net.sourceforge.ganttproject.chart.item.TaskProgressChartItem;
import net.sourceforge.ganttproject.chart.item.TaskRegularAreaChartItem;
import net.sourceforge.ganttproject.font.Fonts;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.model.ChangeValueDispatcher;
import net.sourceforge.ganttproject.gui.options.model.ChangeValueEvent;
import net.sourceforge.ganttproject.gui.options.model.ChangeValueListener;
import net.sourceforge.ganttproject.gui.options.model.GPOptionChangeListener;
import net.sourceforge.ganttproject.gui.scrolling.ScrollingManager;
import net.sourceforge.ganttproject.gui.zoom.ZoomListener;
import net.sourceforge.ganttproject.gui.zoom.ZoomManager;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.CustomColumEvent;
import net.sourceforge.ganttproject.task.CustomColumsListener;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskImpl;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskMutator;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.TaskSelectionManager;
import net.sourceforge.ganttproject.task.algorithm.RecalculateTaskScheduleAlgorithm;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;
import net.sourceforge.ganttproject.task.event.TaskDependencyEvent;
import net.sourceforge.ganttproject.task.event.TaskListenerAdapter;
import net.sourceforge.ganttproject.task.event.TaskScheduleEvent;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.TimeUnitStack;
import net.sourceforge.ganttproject.undo.GPUndoManager;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.util.KDGPCommonHelper;

/**
 * Class for the graphic part of the soft
 */
public class GanttGraphicArea extends ChartComponentBase implements GanttChart,
        CustomColumsListener, ProjectEventListener {

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL cursorResource = GanttGraphicArea.class.getClassLoader()
                .getResource("icons/cursorpercent.gif");
        Image image = toolkit.getImage(cursorResource);
        CHANGE_PROGRESS_CURSOR = toolkit.createCustomCursor(image, new Point(
                10, 5), "CursorPercent");
    }

    private static final Cursor W_RESIZE_CURSOR = new Cursor(
            Cursor.W_RESIZE_CURSOR);

    private static final Cursor E_RESIZE_CURSOR = new Cursor(
            Cursor.E_RESIZE_CURSOR);

    private static final Cursor CHANGE_PROGRESS_CURSOR;

    private static final int HEADER_OFFSET = 44;

    /** Begin of display. */

//    public GanttCalendar date;

    /** Reference to the GanttTree */

    public GanttTree2 tree;

    /** Default color for tasks */

    public static Color taskDefaultColor

    // = new Color( (float) 0.549, (float) 0.713, (float) 0.807);

    = new Color(140, 182, 206);

    /** This value is connected to the GanttTRee Scrollbar to move up or down */

    private int margY;

    /* ! The main application */

    private GanttProject appli;

    private UIConfiguration myUIConfiguration;

    private Color myProjectLevelTaskColor;

    private final ChartModelImpl myChartModel;

    private final TaskManager myTaskManager;

    private GPUndoManager myUndoManager;

    private JTableHeader myTableHeader = null;

    //private List myItemsToConsider;

    private JPanel myPreviewPanel = new ChartOptionsPreviewPanel();

    private TaskTreeImageGenerator myTaskImageGenerator;
    /** Constructor */

    public GanttGraphicArea(GanttProject app, GanttTree2 ttree,
            TaskManager taskManager, ZoomManager zoomManager,
            GPUndoManager undoManager) {
        super((IGanttProject) app, (UIFacade) app, zoomManager);
        this.setBackground(Color.WHITE);
        myTaskManager = taskManager;
        myUndoManager = undoManager;
        //
        myChartModel = new MyChartModelImpl(app,getTaskManager(), app
                .getTimeUnitStack(), app.getUIConfiguration());
        myChartModel.addOptionChangeListener(new GPOptionChangeListener() {
            public void optionsChanged() {
                repaint();

            }
        });
        getViewState().addStateListener(myChartModel);
        getViewState().setStartDate(CalendarFactory.newCalendar().getTime());

        myTaskManager.addTaskListener(new TaskListenerAdapter() {
            private boolean isRepaintPending;

			public void taskScheduleChanged(TaskScheduleEvent e) {
            	boolean needRepaint = !isRepaintPending;
            	isRepaintPending = true;
                adjustDependencies((Task) e.getSource());
                if (needRepaint) {
                	repaint();
                	isRepaintPending = false;
                }
            }

            public void dependencyAdded(TaskDependencyEvent e) {
                adjustDependencies(e.getDependency().getDependee());
                repaint();
            }

            public void dependencyRemoved(TaskDependencyEvent e) {
                repaint();
            }

            private void adjustDependencies(Task task) {
                RecalculateTaskScheduleAlgorithm alg = myTaskManager
                        .getAlgorithmCollection()
                        .getRecalculateTaskScheduleAlgorithm();
                if (!alg.isRunning()) {
                    try {
                        alg.run(task);
                    } catch (TaskDependencyException e) {
                    	if (!GPLogger.log(e)) {
                    		e.printStackTrace(System.err);
                    	}
                    	getUIFacade().showErrorDialog(e);
                    }
                    // appli.setQuickSave (true);
                }
            }
        });

//        date = new GanttCalendar();
//
//        date.setDay(1);

        this.tree = ttree;

        margY = 0;

        appli = app;

        // creation of the different color use to paint

        // arrayColor[0] = new Color((float)0.905,(float)0.905,(float)0.905);
        myTableHeader = tree.getTreeTable().getTable().getTableHeader();

        getProject().getTaskCustomColumnManager().addCustomColumnsListener(this);
        myTaskImageGenerator = new TaskTreeImageGenerator(ttree, app.getUIConfiguration());
    }

    /** Return the color of the task */

    public Color getTaskColor() {
        return myUIConfiguration.getTaskColor();
    }

    /** Change the color of the task */

    public void setProjectLevelTaskColor(Color c) {
    	myUIConfiguration.setProjectLevelTaskColor(c);
    }

    /** The size of the panel. */

    public Dimension getPreferredSize() {

        return new Dimension(465, 600);

    }

    public void paintComponent(Graphics g) {
        myChartModel.setBounds(getSize());
        myChartComponentImpl.paintComponent(g);
    }

    public ChartModelImpl getMyChartModel() {
        return myChartModel;
    }

    public void drawGPVersion(Graphics g) {
        g.setColor(Color.black);
        g.setFont(Fonts.GP_VERSION_FONT);
        g.drawString("GanttProject (" + GanttProject.version + ")", 3,
                getHeight() + 18);
    }

    /** Change the velue connected to the JTree's Scrollbar */

    public void setScrollBar(int v) {

        margY = v;

    }

    /** Return the value of the JTree's Scrollbar */

    public int getScrollBar() {
        return margY;
    }

    public String getName() {
        return GanttLanguage.getInstance().getText("gantt");
    }

    public Date getStartDate() {
        GanttCalendar pstart = new GanttCalendar(getTaskManager()
                .getProjectStart());
        GanttCalendar st = pstart.Clone();
        return st.getTime();
    }

    public Date getEndDate() {
        TaskLength projectLength = getTaskManager().getProjectLength();
        GanttCalendar pstart = new GanttCalendar(getTaskManager()
                .getProjectStart());
        pstart.add((int) projectLength.getLength());
        GanttCalendar end = pstart.Clone();
        return end.getTime();
    }

    /** Return an image with the gantt chart */
    // TODO: 1.11 take into account flags "render this and don't render that"

    public BufferedImage getChart(GanttExportSettings settings) {
    	RenderedChartImage renderedImage = (RenderedChartImage) getRenderedImage(settings);
    	BufferedImage result = renderedImage.getWholeImage();
        repaint();
        return result;
    }

    public RenderedImage getRenderedImage(GanttExportSettings settings) {
        Date dateStart = null;
        Date dateEnd = null;

        List myItemsToConsider = myTaskImageGenerator.getPrintableNodes(settings);
        TimeUnit unit = getViewState().getBottomTimeUnit();

        dateStart = settings.getStartDate() == null ? getStartDate() : settings
                .getStartDate();
        dateEnd = settings.getEndDate() == null ? getEndDate() : settings
                .getEndDate();
		// dateStart = new GregorianCalendar(2005, 2, 25).getTime();

        if (dateStart.after(dateEnd)) {
            Date tmp = (Date) dateStart.clone();
            dateStart = (Date) dateEnd.clone();
            dateEnd = tmp;
        }

		// System.err.println("GanttGraphicArea dateStart=" + dateStart);
		// System.out.println("GanttGraphicArea dateEnd=" + dateEnd);

        TaskLength printedLength = getTaskManager().createLength(unit,
                dateStart, dateEnd);
        BufferedImage taskImage = (BufferedImage) myTaskImageGenerator.createImage(myItemsToConsider);
        int chartWidth = (int) ((printedLength.getLength(getViewState()
                .getBottomTimeUnit()) + 1) * getViewState()
                .getBottomUnitWidth());
        if (chartWidth<getWidth()) {
            chartWidth = getWidth();
        }
        int chartHeight = taskImage.getHeight(null);
        return new RenderedGanttChartImage(myChartModel, myChartComponentImpl, GanttTree2.convertNodesListToItemList(myItemsToConsider), taskImage, chartWidth, chartHeight);
    }

    private GanttTree2 getTree() {

        return this.tree;

    }

    IGanttProject getProject() {
        return appli;
    }

    GPUndoManager getUndoManager() {
        return myUndoManager;
    }

    protected ChartModelBase getChartModel() {
        return myChartModel;
    }

    protected MouseListener getMouseListener() {
        return getChartImplementation().getMouseListener();
    }

    protected MouseMotionListener getMouseMotionListener() {
        return getChartImplementation().getMouseMotionListener();
    }

    private Action[] getPopupMenuActions() {
        return new Action[] { getOptionsDialogAction(),
//                new PublicHolidayDialogAction(getProject(), getUIFacade()) 
        };
        // actions.add(createMenuAction(GanttProject.correctLabel(language
        // .getText("editPublicHolidays")), "));
    }


    protected Component createPreviewComponent() {
        return myPreviewPanel;
    }

    public void repaint() {
        try {
        	if (myChartModel!=null && myTableHeader!=null) {
	            /*myChartModel.setHeaderHeight(myTableHeader.getHeight()
	                    + HEADER_OFFSET);*/
        		myChartModel.setHeaderHeight(myTableHeader.getHeight()+3);
        		tree.getTable().setRowHeight(myChartModel.setRowHeight());
        	}
        } catch (NullPointerException e) {
        	if (!GPLogger.log(e)) {
        		e.printStackTrace(System.err);
        	}
        }
        super.repaint();
    }

    class MouseSupport {
        protected Task findTaskUnderMousePointer(int xpos, int ypos) {
            // int taskID = detectPosition(xpos, ypos, false);
            // return taskID==-1 ? null : getTaskManager().getTask(taskID);
            ChartItem chartItem = myChartModel.getChartItemWithCoordinates(
                    xpos, ypos);
            return chartItem == null ? null : chartItem.getTask();
        }

        protected ChartItem getChartItemUnderMousePoint(int xpos, int ypos) {
            ChartItem result = myChartModel.getChartItemWithCoordinates(xpos,
                    ypos);
            return result;
        }
    }

    abstract class ChangeTaskBoundaryInteraction extends MouseInteractionBase {
        private TaskInteractionHintRenderer myLastNotes;

        private final Task myTask;

        private final float myInitialDuration;

        private GanttCalendar myInitialEnd;

        private GanttCalendar myInitialStart;

        protected ChangeTaskBoundaryInteraction(MouseEvent initiatingEvent,
                TaskBoundaryChartItem taskBoundary) {
            super(initiatingEvent);
            myTask = taskBoundary.getTask();
            myInitialDuration = myTask.getDuration().getLength(
                    getViewState().getBottomTimeUnit());
            myInitialEnd = getTask().getEnd();
            myInitialStart = getTask().getStart();
        }

        public void apply(MouseEvent e) {
            if (myLastNotes == null) {
                myLastNotes = new TaskInteractionHintRenderer("", e.getX(), e.getY());
            }
            float diff = getLengthDiff(e);
            apply(diff);
            myLastNotes.setString(getNotesText());
            myLastNotes.setX(e.getX());
        }

        protected Task getTask() {
            return myTask;
        }

        protected float getInitialDuration() {
            return myInitialDuration;
        }

        public void finish(final TaskMutator mutator) {
            mutator.setIsolationLevel(TaskMutator.READ_COMMITED);

            // if
            // ((!myInitialEnd.equals(getTask().getEnd()))||(!myInitialStart.equals(getTask().getStart())))
            getUndoManager().undoableEdit("Task boundary changed",
                    new Runnable() {
                        public void run() {
                        	try {
                        		doFinish(mutator);
                            }catch(IllegalArgumentException e){
                            	if(e.getMessage().equals("FDCScheduleException001")){
                            		FDCMsgBox.showWarning("调整后的开始时间/结束时间超过了可以调整的范围!");
                            	}else{
                            		if (!GPLogger.log(e)) {
                                		e.printStackTrace(System.err);
                                	}
                                	getUIFacade().showErrorDialog(e);
                            	}
                    		} 
                        }
                    });

        }

        private void doFinish(TaskMutator mutator) {
            mutator.commit();
            mutator=null;
            myLastNotes = null;
            try {
            	
                getTaskManager().getAlgorithmCollection()
                        .getRecalculateTaskScheduleAlgorithm().run();
            }
            catch (TaskDependencyException e) {
            	if (!GPLogger.log(e)) {
            		e.printStackTrace(System.err);
            	}
            	getUIFacade().showErrorDialog(e);
            }
            GanttGraphicArea.this.repaint();
        }

        public void paint(Graphics g) {
            if (myLastNotes != null) {
                myLastNotes.paint(g);
            }
        }

        protected abstract void apply(float diff);

        protected abstract String getNotesText();
    }

    class ChangeTaskEndInteraction extends ChangeTaskBoundaryInteraction
            implements MouseInteraction {
        private TaskMutator myMutator;

        private GanttCalendar myInitialEnd;

        public ChangeTaskEndInteraction(MouseEvent initiatingEvent,
                TaskBoundaryChartItem taskBoundary) {
            super(initiatingEvent, taskBoundary);
            setCursor(E_RESIZE_CURSOR);
            myMutator = getTask().createMutator();
            myInitialEnd = getTask().getEnd();
        }

        protected void apply(float diff) {
            TaskLength newLength = getTaskManager().createLength(
                    getViewState().getBottomTimeUnit(),
                    getInitialDuration() + diff);
            TaskLength translated = getTask().translateDuration(newLength);
            if (translated.getLength() != 0) {
                myMutator.setDuration(translated);
            }
        }

        protected String getNotesText() {
            return getTask().getEnd().toString();
        }

        public void finish() {
            super.finish(myMutator);
        }
    }

    class ChangeTaskStartInteraction extends ChangeTaskBoundaryInteraction
            implements MouseInteraction {
        private TaskLength myInitialLength;

        private TaskMutator myMutator;

        private GanttCalendar myInitialStart;

        ChangeTaskStartInteraction(MouseEvent e,
                TaskBoundaryChartItem taskBoundary) {
            super(e, taskBoundary);
            setCursor(W_RESIZE_CURSOR);
            myInitialLength = getTask().getDuration();
            myMutator = getTask().createMutator();
            myInitialStart = getTask().getStart();
        }

        protected void apply(float diff) {
            TaskLength newLength = getTaskManager().createLength(
                    getViewState().getBottomTimeUnit(),
                    getInitialDuration() + diff);
            TaskLength translated = getTask().translateDuration(newLength);
            int dayDiff = (int) (translated.getValue() - myInitialLength
                    .getValue());
            // System.err.println("[ChangeTaskStart] dayDiff="+dayDiff+"
            // newLength="+newLength+" translated="+translated);
            if (dayDiff != 0) {
                // System.err.println("[ChangeTaskStartInteraction] apply():
                // oldStart="+getTask().getStart());
                GanttCalendar newStart = myInitialStart.newAdd(dayDiff);
				// System.err.println("newStart" + newStart);
                if (newStart.compareTo(getTask().getEnd()) < 0) {
                	myMutator.setStart(newStart);

	                if ((getTask().getThird() != null)
	                        && (getTask().getThirdDateConstraint() == TaskImpl.EARLIESTBEGIN))
	                    myMutator.setEnd(getTask().getEnd().Clone());
                }
                //getTask().applyThirdDateConstraint();

                // mutator.commit();
                // myInitialLength = getTask().getDuration();
            }
        }

        public void finish() {
            super.finish(myMutator);
            getTask().applyThirdDateConstraint();
        }

        protected String getNotesText() {
            return getTask().getStart().toString();
        }
    }

    class ChangeTaskProgressInteraction extends MouseInteractionBase implements
            MouseInteraction {
        private TaskProgressChartItem myTaskProgrssItem;

        private TaskMutator myMutator;

        private TaskInteractionHintRenderer myLastNotes;

        private int myProgressWas;

        private int myProgressIs;

        public ChangeTaskProgressInteraction(MouseEvent e,
                TaskProgressChartItem taskProgress) {
            super(e);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            try {
                setCursor(CHANGE_PROGRESS_CURSOR);
            } catch (Exception exept) {
                setCursor(E_RESIZE_CURSOR);
            }
            myTaskProgrssItem = taskProgress;
            myMutator = myTaskProgrssItem.getTask().createMutator();
            myProgressWas = myTaskProgrssItem.getTask()
                    .getCompletionPercentage();
        }

        public void apply(MouseEvent event) {
            // int deltaProgress =
            // (int)myTaskProgrssItem.getProgressDelta(event.getX());
            float deltaUnits = getLengthDiff(event);
            int deltaPercents = (int) (100 * deltaUnits / myTaskProgrssItem
                    .getTask().getDuration().getLength(
                            getViewState().getBottomTimeUnit()));
            int newProgress = myProgressWas + deltaPercents;
            if (newProgress > 100) {
                newProgress = 100;
            }
            if (newProgress < 0) {
                newProgress = 0;
            }
            myProgressIs = newProgress;
            myMutator.setCompletionPercentage(newProgress);
            myLastNotes = new TaskInteractionHintRenderer(newProgress + "%",
                    event.getX(), event.getY() - 30);
        }

        public void finish() {

            myMutator.setIsolationLevel(TaskMutator.READ_COMMITED);

            getUndoManager().undoableEdit("Task progress changed",
                    new Runnable() {
                        public void run() {
                            doFinish(myMutator);
                        }
                    });
            GanttGraphicArea.this.repaint();
        }

        private void doFinish(TaskMutator mutator) {
            mutator.commit();
            myLastNotes = null;
            try {
                getTaskManager().getAlgorithmCollection()
                        .getRecalculateTaskScheduleAlgorithm().run();
            } catch (TaskDependencyException e) {
            	getUIFacade().showErrorDialog(e);
            }
            if (myProgressIs == myProgressWas) {
                // getUndoManager ().die ();

                myMutator.commit();
                repaint();
                int myProgressIs = myTaskProgrssItem.getTask()
                        .getCompletionPercentage();
                if (myProgressIs != myProgressWas) {
                    // appli.setQuickSave(true);
                    // appli.quickSave ("Task progress changed");

                }

            }
        }

        public void paint(Graphics g) {
            if (myLastNotes != null) {
                myLastNotes.paint(g);
            }
        }
    }

    class DrawDependencyInteraction extends MouseInteractionBase implements
            MouseInteraction {

        private final Task myTask;

        private Point myStartPoint;

        private DependencyInteractionRenderer myArrow;

        private GanttGraphicArea.MouseSupport myMouseSupport;

        private Task myDependant;

        private MouseEvent myLastMouseEvent = null;

        public DrawDependencyInteraction(MouseEvent initiatingEvent,
                TaskRegularAreaChartItem taskArea, MouseSupport mouseSupport) {
            super(initiatingEvent);
            myStartPoint = initiatingEvent.getPoint();
            myTask = taskArea.getTask();
            myArrow = new DependencyInteractionRenderer(myStartPoint.x,
                    myStartPoint.y, myStartPoint.x, myStartPoint.y);
            myMouseSupport = mouseSupport;
        }

        public void apply(MouseEvent event) {
            myArrow.changePoint2(event.getX(), event.getY());
            // myDependant = myMouseSupport.findTaskUnderMousePointer(
            // event.getX(), event.getY());
            myLastMouseEvent = event;
        }

        public void finish() {
            if (myLastMouseEvent != null) {
                myDependant = myMouseSupport.findTaskUnderMousePointer(
                        myLastMouseEvent.getX(), myLastMouseEvent.getY());
                if (myDependant != null) {
                	if(!KDGPCommonHelper.isScheduleTask(myDependant)){
                		//不是本级计划不能指定成为后置任务,即不能调整其他计划的任务
//                		myArrow = new DependencyInteractionRenderer();
//                        repaint();
//                		return;
                	}
                    if (getTaskManager().getDependencyCollection().canCreateDependency(myDependant, myTask)) {
                        getUndoManager().undoableEdit("Draw dependency",
                                new Runnable() {
                                    public void run() {
                                    	try{
                                    		createDependency(myTask);
                                		}catch(IllegalArgumentException e){
                                			if(e.getMessage().equals("FDCScheduleException001")){
//                                				FDCMsgBox.showInfo("调整后的开始时间/结束时间超过了可以调整的范围!");
                                			}else{
                                				throw e;
                                			}
                                		}
                                        
                                    }
                                });
                    }
                } else {
                    myArrow = new DependencyInteractionRenderer();
                    repaint();
                }
            }
        }

		private void createDependency(final Task dependee) {
			TaskDependency dependency=null;
			try {
				if(dependee instanceof KDTask){
            		KDTask task=(KDTask)dependee;
            		ScheduleBaseInfo info = task.getScheduleTaskInfo().getScheduleBase();
            		info.setBoolean("createDependency",true);
            		info.remove("createDependencyFaile");
            	}
				
				dependency=getTaskManager().getDependencyCollection().createDependency(
                		myDependant, dependee, new FinishStartConstraintImpl());
            } catch (TaskDependencyException e) {
            	getUIFacade().showErrorDialog(e);
            }finally{
            	if(dependee instanceof KDTask){
            		KDTask task=(KDTask)dependee;
            		ScheduleBaseInfo info = task.getScheduleTaskInfo().getScheduleBase();
            		if(dependency!=null&&info.getBoolean("createDependencyFaile")){
            			getTaskManager().getDependencyCollection().deleteDependency(dependency);
            			/***
                		 * 在这里再提示
                		 */
            			FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "调整后的开始时间/结束时间超过了可以调整的范围");
            		}
            		
					info.remove("createDependency");
            		info.remove("createDependencyFaile");
            	}
            }
            getProject().setModified();
		}

        
        public void paint(Graphics g) {
            myArrow.paint(g);
        }
    }

    class MoveTaskInteraction extends MouseInteractionBase implements
            MouseInteraction {
        private Task myTask;

        private TaskMutator myMutator;

        private GanttCalendar myInitialStart;

        MoveTaskInteraction(MouseEvent e, Task task) {
            super(e);
            myTask = task;
            myMutator = task.createMutator();
            myInitialStart = myTask.getStart();
        }

        public void apply(MouseEvent event) {
            float diff = getChartModel().calculateLengthNoWeekends(getStartX(),
                    event.getX());
            TaskLength bottomUnitLength = getTaskManager().createLength(
                    getViewState().getBottomTimeUnit(), diff);
            TaskLength taskLength = myTask.translateDuration(bottomUnitLength);
            int dayDiff = (int) (taskLength.getValue());
            // System.err.println("[MoveTaskInteraction] apply():
            // dayDiff="+dayDiff+" bottomUnitLength="+bottomUnitLength+"
            // translated="+taskLength);
            if (dayDiff != 0) {
                myMutator.shift(dayDiff);
            }
        }

        public void finish() {
            myMutator.setIsolationLevel(TaskMutator.READ_COMMITED);
            getUndoManager().undoableEdit("Task moved", new Runnable() {
                public void run() {
                    doFinish();
                }
            });

        }

        private void doFinish() {
            myMutator.commit();
            try {
                getTaskManager().getAlgorithmCollection()
                        .getRecalculateTaskScheduleAlgorithm().run();
            } catch (TaskDependencyException e) {
            	getUIFacade().showErrorDialog(e);
            }
            GanttGraphicArea.this.repaint();
        }

    }

    class MoveTaskInteractions extends MouseInteractionBase implements
            MouseInteraction {

        private List myTasks; // of Task

        private List myMutators; // of TaskMutator

        private List myInitialStarts; // of GanttCalendar

        MoveTaskInteractions(MouseEvent e, List tasks) {
            super(e);
            myTasks = tasks;
            myMutators = new ArrayList(myTasks.size());
            myInitialStarts = new ArrayList(myTasks.size());
            Iterator itTasks = myTasks.iterator();
            while (itTasks.hasNext()) {
                Task t = (Task) itTasks.next();
                myMutators.add(t.createMutator());
                myInitialStarts.add(t.getStart());
            }
        }

        public void apply(MouseEvent event) {
            float diff = getLengthDiff(event);
            TaskLength bottomUnitLength = getTaskManager().createLength(
                    getViewState().getBottomTimeUnit(), diff);

            for (int i = 0; i < myTasks.size(); i++) {
                Task task = (Task) myTasks.get(i);
                TaskLength taskLength = task
                        .translateDuration(bottomUnitLength);
                int dayDiff = (int) (taskLength.getValue());
                if (dayDiff != 0) {
                    ((TaskMutator) myMutators.get(i)).shift(dayDiff);
                }
            }
        }

        public void finish() {
            Iterator itMutators = myMutators.iterator();
            while (itMutators.hasNext())
                ((TaskMutator) itMutators.next())
                        .setIsolationLevel(TaskMutator.READ_COMMITED);

            getUndoManager().undoableEdit("Task moved", new Runnable() {
                public void run() {
                    doFinish();
                }
            });

        }

        private void doFinish() {
            Iterator itMutators = myMutators.iterator();
            while (itMutators.hasNext())
                ((TaskMutator) itMutators.next()).commit();

            try {
                getTaskManager().getAlgorithmCollection()
                        .getRecalculateTaskScheduleAlgorithm().run();
            } catch (TaskDependencyException e) {
            	getUIFacade().showErrorDialog(e);
            }

            Iterator itTasks = myTasks.iterator();
            while (itTasks.hasNext()) {
                Task t = ((Task) itTasks.next());
                t.applyThirdDateConstraint();
            }

            GanttGraphicArea.this.repaint();
        }
    }

    public interface ChartImplementation extends ZoomListener {
        void paintComponent(Graphics g);

        void paintComponent(Graphics g, List/*<Task>*/ visibleTasks);

        MouseListener getMouseListener();

        MouseMotionListener getMouseMotionListener();

        void beginChangeTaskEndInteraction(MouseEvent initiatingEvent,
                TaskBoundaryChartItem taskBoundary);

        MouseInteraction getActiveInteraction();

        void beginChangeTaskStartInteraction(MouseEvent e,
                TaskBoundaryChartItem taskBoundary);

        MouseInteraction finishInteraction();

        void beginChangeTaskProgressInteraction(MouseEvent e,
                TaskProgressChartItem item);

        void beginDrawDependencyInteraction(MouseEvent initiatingEvent,
                TaskRegularAreaChartItem taskArea,
                GanttGraphicArea.MouseSupport mouseSupport);

        void beginMoveTaskInteraction(MouseEvent e, Task task);

        void beginMoveTaskInteractions(MouseEvent e, List tasks);

        void beginScrollViewInteraction(MouseEvent e);

    }

    private class ChartImplementationBase extends AbstractChartImplementation {
        public void beginChangeTaskEndInteraction(MouseEvent initiatingEvent,
                TaskBoundaryChartItem taskBoundary) {
            setActiveInteraction(new ChangeTaskEndInteraction(initiatingEvent,
                    taskBoundary));
        }

        public void beginChangeTaskStartInteraction(MouseEvent e,
                TaskBoundaryChartItem taskBoundary) {
            setActiveInteraction(new ChangeTaskStartInteraction(e, taskBoundary));
        }

        public void beginChangeTaskProgressInteraction(MouseEvent e,
                TaskProgressChartItem taskProgress) {
            setActiveInteraction(new ChangeTaskProgressInteraction(e,
                    taskProgress));
        }

        public void beginDrawDependencyInteraction(MouseEvent initiatingEvent,
                TaskRegularAreaChartItem taskArea,
                GanttGraphicArea.MouseSupport mouseSupport) {
            setActiveInteraction(new DrawDependencyInteraction(initiatingEvent,
                    taskArea, mouseSupport));
        }

        public void beginMoveTaskInteraction(MouseEvent e, Task task) {
            setActiveInteraction(new MoveTaskInteraction(e, task));
        }

        public void beginMoveTaskInteractions(MouseEvent e, List tasks) {
            setActiveInteraction(new MoveTaskInteractions(e, tasks));
        }
    }

    private class NewChartComponentImpl extends ChartImplementationBase
            implements ChartImplementation {

        public void paintComponent(Graphics g, List/*<Task>*/ visibleTasks) {
        	synchronized(ChartModelBase.STATIC_MUTEX) {
	            GanttGraphicArea.super.paintComponent(g);
	            ChartModel model = myChartModel;
	            model.setTaskContainment(appli.getTaskContainment());
	            // model.setBounds(getSize());
	            // System.err.println("[NewChartComponentImpl] paintComponent. unit
	            // width="+getViewState().getBottomUnitWidth());
	            model.setBottomUnitWidth(getViewState().getBottomUnitWidth());
	            model.setRowHeight(((GanttTree2) tree).getTreeTable()
	                    .getRowHeight());
	            model.setTopTimeUnit(getViewState().getTopTimeUnit());
	            model.setBottomTimeUnit(getViewState().getBottomTimeUnit());
	            model.setVisibleTasks(visibleTasks);
	            model.paint(g);
	            if (getActiveInteraction() != null) {
	                getActiveInteraction().paint(g);
	            }
        	}
        }

        public void paintComponent(Graphics g) {
        	synchronized(ChartModelBase.STATIC_MUTEX) {
	            GanttGraphicArea.super.paintComponent(g);
	            ChartModel model = myChartModel;
	            model.setTaskContainment(appli.getTaskContainment());
	            // model.setBounds(getSize());
	            // System.err.println("[NewChartComponentImpl] paintComponent. unit
	            // width="+getViewState().getBottomUnitWidth());
	            model.setBottomUnitWidth(getViewState().getBottomUnitWidth());
	            model.setRowHeight(((GanttTree2) tree).getTreeTable()
	                    .getRowHeight());
	            model.setTopTimeUnit(getViewState().getTopTimeUnit());
	            model.setBottomTimeUnit(getViewState().getBottomTimeUnit());
	            VisibleNodesFilter visibleNodesFilter = new VisibleNodesFilter();
	            List visibleTasks = visibleNodesFilter.getVisibleNodes(tree
	                    .getJTree(), getScrollBar(), getHeight(), tree
	                    .getTreeTable().getRowHeight());
	            model.setVisibleTasks(visibleTasks);
                //myChartModel.setExplicitlyHiddenTasks(tree.getHiddenTasks());
                model.setVerticalOffset(getScrollBar() % tree.getTreeTable().getRowHeight());
	            model.paint(g);
	            if (getActiveInteraction() != null) {
	                getActiveInteraction().paint(g);
	            }
        	}
        }

        public MouseListener getMouseListener() {
            return myMouseListener;
        }

        public MouseMotionListener getMouseMotionListener() {
            return myMouseMotionListener;
        }

		public IStatus canPaste(ChartSelection selection) {
			return Status.OK_STATUS;
		}

		public ChartSelection getSelection() {
			ChartSelectionImpl result = new ChartSelectionImpl() {
				public boolean isEmpty() {
					return false;
				}
				public void startCopyClipboardTransaction() {
					super.startCopyClipboardTransaction();
					tree.copySelectedNode();
				}
				public void startMoveClipboardTransaction() {
					super.startMoveClipboardTransaction();
					tree.cutSelectedNode();
				}
			};
			return result;
		}

		public void paste(ChartSelection selection) {
			tree.pasteNode();
		}


        private OldChartMouseListenerImpl myMouseListener = new OldChartMouseListenerImpl();

        private OldMouseMotionListenerImpl myMouseMotionListener = new OldMouseMotionListenerImpl();


    }

    protected AbstractChartImplementation getImplementation() {
        return (AbstractChartImplementation) getChartImplementation();
    }

    private ChartImplementation getChartImplementation() {
        if (myChartComponentImpl == null) {
            myChartComponentImpl = new NewChartComponentImpl();
        }
        return myChartComponentImpl;
    }

    public Action getScrollCenterAction(ScrollingManager scrollMgr,
            TaskSelectionManager taskSelMgr, String iconSize) {
        if (myScrollCenterAction == null)
            myScrollCenterAction = new ScrollGanttChartCenterAction(scrollMgr,
                    taskSelMgr, iconSize);
        return myScrollCenterAction;
    }

    public void setPreviousStateTasks(ArrayList tasks) {
        int rowHeight = myChartModel.setPreviousStateTasks(tasks);
        ((GanttTree2) appli.getTree()).getTable().setRowHeight(rowHeight);
    }


    private ChartImplementation myChartComponentImpl;

    private ScrollGanttChartCenterAction myScrollCenterAction;

    protected class ScrollGanttChartCenterAction extends GPAction {
        private final ScrollingManager myScrollingManager;

        private final TaskSelectionManager myTaskSelectionManager;

        public ScrollGanttChartCenterAction(ScrollingManager scrollingManager,
                TaskSelectionManager taskSelectionManager, String iconSize) {
            super("ScrollCenter", iconSize);
            myScrollingManager = scrollingManager;
            myTaskSelectionManager = taskSelectionManager;
        }

        public void actionPerformed(ActionEvent e) {
        	getUIFacade().setStatusText(GanttLanguage.getInstance().getText("centerOnSelectedTasks"));
            scroll();
        }

        private void scroll() {
            GanttCalendar min = null;
            GanttCalendar max = null;
            Date scrollDate = null;

            Iterator it = null;
            if (myTaskSelectionManager.getSelectedTasks().isEmpty()) {
                // scrollDate = getTaskManager().getProjectStart();
                it = Arrays.asList(getTaskManager().getTasks()).iterator();
            } else {
                it = myTaskSelectionManager.getSelectedTasks().iterator();
            }
            while (it.hasNext()) {
                Task t = (Task) it.next();
                GanttCalendar dStart = t.getStart();
                GanttCalendar dEnd = t.getEnd();

                min = min == null ? dStart.Clone()
                        : (min.compareTo(dStart) > 0 ? dStart.Clone() : min);
                max = max == null ? dEnd.Clone()
                        : (max.compareTo(dEnd) < 0 ? dEnd.Clone() : max);
            }
//            System.err.println("min: " + min.getTime().toString());
//			System.err.println("max: " + max.getTime().toString());

            //no tasks defined, nothing to do
            if(min == null || max == null)
            	return;

            TimeUnit defaultUnit = getTimeUnitStack().getDefaultTimeUnit();
            final TaskLength selectionLength = getTaskManager().createLength(
                    defaultUnit, min.getTime(), max.getTime());
            final TaskLength viewLength = getChartModel().getVisibleLength();
            float viewLengthInDefaultUnits = viewLength.getLength(defaultUnit);
            // if selection is shorter than view we'll scroll right,
            // otherwise we'll scroll left
            // delta is measured in the bottom line time units
			// final float delta = (selectionLength.getValue() -
			// viewLengthInDefaultUnits) / 2;
			// 天煞的，这个地方好难找。华夏要初始化为开始日期altered by andy_liu 2012-12-20
			final float delta = 0;

            scrollDate = GPCalendar.PLAIN.shiftDate(min.getTime(),
                    getTaskManager().createLength(defaultUnit, delta));

            myScrollingManager.scrollLeft(scrollDate);
        }

        /*
         * (non-Javadoc)
         *
         * @see net.sourceforge.ganttproject.action.GPAction#getIconFilePrefix()
         */
        protected String getIconFilePrefix() {
            return "scrollcenter_";
        }

        protected String getLocalizedName() {
            return super.getLocalizedName();
        }
    }

    private class OldChartMouseListenerImpl extends MouseListenerBase implements
            MouseListener {
        private MouseSupport myMouseSupport = new MouseSupport();

        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Task taskUnderPointer = myMouseSupport
                        .findTaskUnderMousePointer(e.getX(), e.getY());
                if (taskUnderPointer == null) {
                    tree.selectTreeRow(-1);
                }
            }
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
//                if (!appli.isOnlyViewer)
                    appli.propertiesTask();
            }
        }

        protected Action[] getPopupMenuActions() {
            Action[] treeActions = tree.getPopupMenuActions();
            int sep = 0;
            if (treeActions.length != 0) {
                sep = 1;
            }
            Action[] chartActions = GanttGraphicArea.this.getPopupMenuActions();
            Action[] result = new Action[treeActions.length + sep
                    + chartActions.length];
            System.arraycopy(treeActions, 0, result, 0, treeActions.length);
            System.arraycopy(chartActions, 0, result, treeActions.length
                    + sep, chartActions.length);
            return result;
        }

        protected void processLeftButton(MouseEvent e) {
            boolean isMineEvent = true;
            ChartItem itemUnderPoint = myMouseSupport.getChartItemUnderMousePoint(e.getX(), e.getY());
            if (itemUnderPoint instanceof TaskBoundaryChartItem) {
                TaskBoundaryChartItem taskBoundary = (TaskBoundaryChartItem) itemUnderPoint;
                if (taskBoundary.isStartBoundary()) {
                    getChartImplementation().beginChangeTaskStartInteraction(e, taskBoundary);
                }
                else {
                    getChartImplementation().beginChangeTaskEndInteraction(e, taskBoundary);
                }
            }
            else if (itemUnderPoint instanceof TaskProgressChartItem) {
            	//can't change complete by moving by sxhong
                /*getChartImplementation().beginChangeTaskProgressInteraction(
                        e, (TaskProgressChartItem) itemUnderPoint);*/
            }
            else if (itemUnderPoint instanceof TaskRegularAreaChartItem) {
                getChartImplementation().beginDrawDependencyInteraction(
                        e, (TaskRegularAreaChartItem) itemUnderPoint, myMouseSupport);
            }
            else {
                isMineEvent = false;
                super.processLeftButton(e);
            }
            if (isMineEvent) {
                repaint();
                appli.recalculateCriticalPath();
            }
        }

        public void mousePressed(MouseEvent e) {
            tree.stopEditing();
//            if (appli.isOnlyViewer)
//                return;
            Task taskUnderPointer = myMouseSupport.findTaskUnderMousePointer(e
                    .getX(), e.getY());
            if (taskUnderPointer!=null && !Mediator.getTaskSelectionManager().isTaskSelected(
                    taskUnderPointer)) {
                boolean ctrl = (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK;
                tree.selectTask(taskUnderPointer, ctrl);
            }
            super.mousePressed(e);
            if (taskUnderPointer == null) {
                return;
            }
            if (e.getButton() == MouseEvent.BUTTON2) {
                if (!Mediator.getTaskSelectionManager().isTaskSelected(
                        taskUnderPointer))
                    tree.selectTask(taskUnderPointer, false);
                List l = Mediator.getTaskSelectionManager().getSelectedTasks();

                getChartImplementation().beginMoveTaskInteractions(e, l);
            }
        }

    }

    private class OldMouseMotionListenerImpl extends MouseMotionListenerBase {
        private MouseSupport myMouseSupport = new MouseSupport();

        public void mouseDragged(MouseEvent e) {
            if (appli.isOnlyViewer){
            	//查看状态也能滚动查看甘特图
            	getOnlyViewScrollViewInteraction(e).apply(e);
            	GanttGraphicArea.this.repaint();
            	return;
            }
            super.mouseDragged(e);
            /*
             * Add the repaint in order to repaint the treetable when an action
             * occurs on the GraphicArea. here mousedragged because all actions
             * modifying task properties on the graphics are made through
             * mousedragged (I think !)
             */
            // Mediator.getGanttProjectSingleton().repaint();
            // getUIFacade().repaint2();
            if (myUIConfiguration.isCriticalPathOn()) {
                MouseInteraction mi = myChartComponentImpl
                        .getActiveInteraction();
                if ((mi instanceof ChangeTaskBoundaryInteraction)
                        || (mi instanceof MoveTaskInteraction)
                        || (mi instanceof MoveTaskInteractions))
                    appli.recalculateCriticalPath();
            }
            GanttGraphicArea.this.repaint();
            // avant

        }

        // Move the move on the area
        public void mouseMoved(MouseEvent e) {
            ChartItem itemUnderPoint = myMouseSupport
                    .getChartItemUnderMousePoint(e.getX(), e.getY());
            Task taskUnderPoint = itemUnderPoint == null ? null
                    : itemUnderPoint.getTask();
            // System.err.println("[OldMouseMotionListenerImpl] mouseMoved:
            // taskUnderPoint="+taskUnderPoint);
            if (taskUnderPoint == null) {
                setDefaultCursor();
            } else {
                if (itemUnderPoint instanceof TaskBoundaryChartItem) {
                    Cursor cursor = ((TaskBoundaryChartItem) itemUnderPoint)
                            .isStartBoundary() ? W_RESIZE_CURSOR
                            : E_RESIZE_CURSOR;
                    setCursor(cursor);
                }
                // special cursor
                else if (itemUnderPoint instanceof TaskProgressChartItem) {
//                    setCursor(CHANGE_PROGRESS_CURSOR);
                } else {
                    setDefaultCursor();
                }
                // getUIFacade().repaint2();
                appli.repaint();
            }
        }
    }

    public void setTaskManager(TaskManager taskManager) {
        // TODO Auto-generated method stub

    }

    public void reset() {
        // TODO Auto-generated method stub

    }

    public Icon getIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    public void customColumsChange(CustomColumEvent event) {
        repaint();
    }

    public void setUIConfiguration(UIConfiguration configuration) {
        myUIConfiguration = configuration;
    }

    private static class ChartOptionsPreviewPanel extends JPanel implements
            ChangeValueListener {
        Text upText, downText, leftText, rightText;

        TaskBar taskBar;

        public ChartOptionsPreviewPanel() {
            super();
            addToDispatchers();
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(450, 70));

            taskBar = new TaskBar();

            upText = new Text(Text.UP, taskBar);
            downText = new Text(Text.DOWN, taskBar);
            leftText = new Text(Text.LEFT, taskBar);
            rightText = new Text(Text.RIGHT, taskBar);
        }

        void refresh() {

        }
        private void addToDispatchers() {
            List dispatchers = Mediator.getChangeValueDispatchers();
            for (int i = 0; i < dispatchers.size(); i++) {
                ((ChangeValueDispatcher) dispatchers.get(i))
                        .addChangeValueListener(this);
            }
        }

        public void paint(Graphics g) {
            super.paint(g);
            taskBar.paintMe(g);
            upText.paintMe(g);
            downText.paintMe(g);
            leftText.paintMe(g);
            rightText.paintMe(g);
        }

        public void changeValue(ChangeValueEvent event) {
            Object id = event.getID();
            if (id.equals("up")) {
                upText.text = getI18n(event.getNewValue().toString());
            } else if (id.equals("down")) {
                downText.text = getI18n(event.getNewValue().toString());
            } else if (id.equals("left")) {
                leftText.text = getI18n(event.getNewValue().toString());
            } else if (id.equals("right")) {
                rightText.text = getI18n(event.getNewValue().toString());
            }
            repaint();
        }

        static String getI18n(String id) {
            String res = GanttLanguage.getInstance().getText(
                    "optionValue." + id + ".label");
            if (res.startsWith(GanttLanguage.MISSING_RESOURCE)) {
                res = id;
            }
            return res;
        }

        class TaskBar {
            int width, height, x, y;

            Color color;

            TaskBar() {
                width = 100;
                height = 12;
                x = (int) (ChartOptionsPreviewPanel.this.getPreferredSize()
                        .getWidth() / 2 - width / 2);
                y = (int) (ChartOptionsPreviewPanel.this.getPreferredSize()
                        .getHeight() / 2 - height / 2);
                color = new Color(140, 182, 206);
            }

            void paintMe(Graphics g) {
                g.setColor(color);
                g.fillRect(x, y, width, height);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);
            }

        }

        private static class Text {
            static final Font FONT = Fonts.PREVIEW_BAR_FONT;

            static final int LEFT = 0;

            static final int RIGHT = 1;

            static final int UP = 2;

            static final int DOWN = 3;

            static final int MARGIN = 3;

            String text = "";

            int position;

            private int x, y;

            TaskBar taskBar;

            Text(int position, TaskBar refBar) {
                this.position = position;
                this.taskBar = refBar;
            }

            void paintMe(Graphics g) {
                calculateCoordinates(g);
                g.setFont(FONT);
                g.drawString(text, x, y);
            }

            private void calculateCoordinates(Graphics g) {

                int textHeight = g.getFontMetrics(FONT).getHeight();
                int textWidth = g.getFontMetrics(FONT).stringWidth(text);

                switch (position) {
                case UP:
                    y = taskBar.y - MARGIN;
                    x = taskBar.x + taskBar.width / 2 - textWidth / 2;
                    break;
                case DOWN:
                    x = taskBar.x + taskBar.width / 2 - textWidth / 2;
                    y = taskBar.y + taskBar.height + textHeight - MARGIN;
                    break;
                case LEFT:
                    y = taskBar.y + taskBar.height / 2 + textHeight / 2
                            - MARGIN;
                    x = taskBar.x - MARGIN - textWidth;
                    break;
                case RIGHT:
                    y = taskBar.y + taskBar.height / 2 + textHeight / 2
                            - MARGIN;
                    x = taskBar.x + taskBar.width + MARGIN;
                    break;
                }
            }

        }
    }

    public void appendBlankRow() {
        tree.addBlankLine(null, -1);
    }

    public void projectModified() {
        // TODO Auto-generated method stub

    }

    public void projectSaved() {
        // TODO Auto-generated method stub

    }

    public void projectClosed() {
        repaint();
        setProjectLevelTaskColor(null);
        setPreviousStateTasks(null);
    }
    
    public Date minStart = new Date();
	public Date maxEnd = new Date();

	// public Date getMinStart(){
	//
	// if(latestChildEnd.before(fdcChildTask.getEnd())){
	// latestChildEnd = fdcChildTask.getEnd();
	// this.maxEnd = fdcChildTask.getEnd();
	// }
	// if (fdcChildTask.getStart().before((this.minStart))) {
	// this.minStart = fdcChildTask.getStart();
	// }
	// }
	public String getToolTipText(MouseEvent event) {
		ChartItem chartItem = myChartModel.getChartItemWithCoordinates(event.getX(), event.getY());
		if (chartItem != null  && chartItem instanceof TaskRegularAreaChartItem && chartItem.getTask() != null ) {
			Task task = chartItem.getTask();
			if(task instanceof KDTask){
				KDTask kdtask=(KDTask)task;
				ScheduleTaskBaseInfo taskInfo = kdtask.getScheduleTaskInfo();
				// 计算计划完工百分比开始
				ScheduleCalendarInfo calendar = taskInfo.getScheduleBase().getCalendar();
				BigDecimal planSchedule = FDCHelper.ZERO;
				BigDecimal actSchedule = FDCHelper.ZERO;
				GanttTree2 tree = getTree();
				ArrayList childTasks = tree.getAllChildTask(kdtask);
				Date today = new Date();
				if(today.before(taskInfo.getStart())){
					planSchedule = FDCHelper.ZERO;
				}else if(today.after(taskInfo.getEnd())){
					planSchedule = FDCHelper.ONE_HUNDRED;
				}else{
					if(childTasks.size()>0){
						BigDecimal totalEffectTimes = FDCHelper.ZERO;
						BigDecimal total2TodayEffectTimes = FDCHelper.ZERO;
						Date latestChildEnd = new Date();
						for(int i=0;i<childTasks.size();i++){
							KDTask childTask = (KDTask) ((TaskNode) childTasks.get(i)).getUserObject();
							FDCScheduleTaskInfo fdcChildTask = (FDCScheduleTaskInfo) childTask.getScheduleTaskInfo();
							if (latestChildEnd.before(fdcChildTask.getEnd())) {
								latestChildEnd = fdcChildTask.getEnd();
							}
							totalEffectTimes = FDCHelper.add(totalEffectTimes, fdcChildTask.getEffectTimes());
							if(fdcChildTask.getEnd().before(today)){
								total2TodayEffectTimes = FDCHelper.add(total2TodayEffectTimes, 
										ScheduleCalendarHelper.getEffectTimes(fdcChildTask.getStart(), fdcChildTask.getEnd(), calendar));
							}else if(fdcChildTask.getStart().after(today)){
								total2TodayEffectTimes = FDCHelper.add(total2TodayEffectTimes,FDCHelper.ZERO); 
							}else{
								total2TodayEffectTimes = FDCHelper.add(total2TodayEffectTimes, 
										ScheduleCalendarHelper.getEffectTimes(fdcChildTask.getStart(), today, calendar));
							}
						}
						if(latestChildEnd.before(taskInfo.getEnd())){
							totalEffectTimes = FDCHelper.add(totalEffectTimes, 
									ScheduleCalendarHelper.getEffectTimes(latestChildEnd, taskInfo.getEnd(), calendar));
						}
						planSchedule = FDCHelper.divide(FDCHelper.multiply(total2TodayEffectTimes, FDCHelper.ONE_HUNDRED),totalEffectTimes);
					}else{
						BigDecimal toToday = ScheduleCalendarHelper.getEffectTimes(taskInfo.getStart(), today, calendar);
						planSchedule = FDCHelper.divide(FDCHelper.multiply(toToday, FDCHelper.ONE_HUNDRED),taskInfo.getBigDecimal("effectTimes"));
					}
				}
				// 计算计划完工百分比结束

				// 计算实际完工百分比开始
				actSchedule = taskInfo.getComplete() == null ? FDCHelper.ZERO
						: taskInfo.getComplete();
				// 计算实际完工百分比结束
				Date actStartDate = taskInfo.getDate("actualStartDate");				
				Date actEndDate = taskInfo.getDate("actualenddate");
				BigDecimal tempPlan = planSchedule.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 默认的为ROUND_UNNECESSARY， 可能报异常ArithmeticException("Rounding necessary")
				BigDecimal tempAct = actSchedule.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				if (taskInfo.getBigDecimal("effectTimes") != null) {
					BigDecimal temp = taskInfo.getBigDecimal("effectTimes")
							.setScale(2);
					taskInfo.setBigDecimal("effectTimes", temp);
				}
				// 状态(默认待确认)
				int state = Integer.parseInt(FDCHelper.isEmpty(taskInfo
						.getString("state")) ? "-1" : taskInfo
						.getString("state"));
				String stateStr = null;
				switch (state) {
				case 0:
					stateStr = "按时完成";
					break;
				case 1:
					stateStr = "延时完成";
					break;
				case 2:
					stateStr = "待确认";
					break;
				case 3:
					stateStr = "延时且未完成";
					break;
				case -1:
					stateStr = "";
					break;
				}
				String strPlanStart = "";
				if (taskInfo.getStart() != null) {
					strPlanStart = FDCDateHelper.formatDate2(taskInfo
							.getStart());
				}
				String strPlanEnd = "";
				if (taskInfo.getEnd() != null) {
					strPlanEnd = FDCDateHelper.formatDate2(taskInfo.getEnd());
				}
				String strActStart = "";
				if (actStartDate != null) {
					strActStart = FDCDateHelper.formatDate2(actStartDate);
				}
				String strActEnd = "";
				if (actEndDate != null) {
					strActEnd = FDCDateHelper.formatDate2(actEndDate);
				}
				
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body bgcolor=#FFFFCC><table>");
				// sb.append("<tr><td>WBS编码:</td><td>");
				// sb.append(taskInfo.getNumber());
				// sb.append("</td></tr>");
				sb.append("<tr><td>任务名称:</td><td>");
				sb.append(taskInfo.getName());
				sb.append("</td></tr>");
				sb.append("<tr><td>任务状态:</td><td>");
				sb.append(stateStr);
				sb.append("</td></tr>");
				sb.append("<tr><td>计划开始日期:</td><td>");
				sb.append(strPlanStart);
				sb.append("</td></tr>");
				sb.append("<tr><td>计划完成日期:</td><td>");
				sb.append(strPlanEnd);
				sb.append("</td></tr>");
				sb.append("<tr><td>工期:</td><td>");
				sb.append(taskInfo.getBigDecimal("effectTimes").intValue());
				sb.append("</td></tr>");
				sb.append("<tr><td>实际开始日期:</td><td>");
				sb.append(strActStart);
				sb.append("</td></tr>");
				sb.append("<tr><td>实际完成日期:</td><td>");
				sb.append(strActEnd);
				sb.append("</td></tr>");
				// sb.append("<tr><td>计划进度:</td><td>");
				// sb.append(tempPlan);
				// sb.append("%</td></tr>");
				sb.append("<tr><td>完成进度:</td><td>");
				sb.append(tempAct);
				sb.append("%</td></tr>");
				// sb.append("<tr><td>进度偏差:</td><td>");
				// sb.append(FDCHelper.subtract(tempAct, tempPlan));
				// sb.append("%</td></tr>");
				sb.append("</table></body></html>");
				String tips = sb.toString();
				//替换字体
				tips=tips.replaceAll("<td>", "<td><font size=3>");
				tips=tips.replaceAll("</td>", "</font></td>");
				tips = tips.replaceAll("null", "");
				return tips;
			}else{
				String name = task.getName();
				String tips = "<html><B>" + name + 
					"</B><br>开始时间:" + task.getStart() + 
					"<br>结束时间:" + task.getEnd() + 
					"<br>duration:" + task.getDuration().getLength() + 
					"<br>完成:"+ task.getCompletionPercentage() + "% </html>";
				return tips;
			}
		} else {
			return super.getToolTipText();
		}
	}
	
	private int formatPercent(BigDecimal decimal){
		int i = decimal.intValue();
		float f = decimal.floatValue();
		if(f > i){
			return i + 1 > 100 ? 100 : (i + 1); 
		}
		return i > 100 ? 100 : i;
	}
	public String getToolTipText() {
		return super.getToolTipText();
	}
	
	private ScrollViewInteraction onlyViewScrollViewInteraction=null;
	private ScrollViewInteraction getOnlyViewScrollViewInteraction(MouseEvent event){
		if(onlyViewScrollViewInteraction==null){
			onlyViewScrollViewInteraction=new ScrollViewInteraction(event);
		}
		return onlyViewScrollViewInteraction;
	}
	
	public static class MyChartModelImpl extends ChartModelImpl{
		private final GanttCalendar today = new GanttCalendar();
		private final GanttProject ganttProject;
		public MyChartModelImpl(GanttProject ganttProject,TaskManager taskManager,
				TimeUnitStack timeUnitStack, UIConfiguration projectConfig) {
			super(taskManager, timeUnitStack, projectConfig);
			this.ganttProject=ganttProject;
		}
    	
		public void paint(Graphics g) {
			pointMap.clear();
			this.setStartPoint(-1, -1);
			this.setEndPoint(-1, -1);
			super.paint(g);
		}
		protected void paintMainArea(Graphics mainArea, Painter p) {
			super.paintMainArea(mainArea, p);
			paintForwardLine(mainArea);
			
		}

		public void paintForwardLine(Graphics mainArea) {
			if(!ganttProject.isOnlyViewer){
				return;
			}
			if (!getProjectConfig().isRedlineOn()) {
				// 显示今天时才绘制
				return;
			}
			if (startPoint == null || endPoint == null) {
				return;
			}
			Object renderingHint = ((Graphics2D) mainArea).getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			Color color = mainArea.getColor();
			try {
				((Graphics2D) mainArea).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				mainArea.setColor(Color.red);
				int x = startPoint[0];
				int y = startPoint[1];
				Collection values = pointMap.values();
				ArrayList list = new ArrayList(values);
				Collections.sort(list, new Comparator() {
					public int compare(Object arg0, Object arg1) {
						int[] point1 = (int[]) arg0;
						int[] point2 = (int[]) arg1;
						return point1[1] - point2[1];
					}
				});
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					int[] point = (int[]) iter.next();
					if (x == -1 || y == -1) {
						x = point[0];
						y = point[1];
						continue;
					}

					mainArea.drawLine(x, y, point[0], point[1]);
					// 画双线
//					mainArea.drawLine(x + 1, y, point[0], point[1] - 1);
					x = point[0];
					y = point[1];
				}

				mainArea.drawLine(x, y, endPoint[0], endPoint[1]);
				// 画双线
//				mainArea.drawLine(x + 1, y, endPoint[0], endPoint[1] - 1);
				//
			} finally {
				((Graphics2D) mainArea).setRenderingHint(RenderingHints.KEY_ANTIALIASING, renderingHint);
				mainArea.setColor(color);
			}

		}
		
		
		private Map pointMap=new HashMap();
		private int[] startPoint=null,endPoint=null;
		public void setStartPoint(int x,int y){
			if(x==-1||y==-1){
				startPoint=null;
			}else{
				startPoint=new int[]{x,y};
			}
				
		}
		public void setEndPoint(int x,int y){
			if(x==-1||y==-1){
				endPoint=null;
			}else{
				endPoint=new int[]{x,y};
			}
		}
		
		public void addPoint(Task task,int x,int y){
			if(!ganttProject.isOnlyViewer){
				return;
			}
/*			//相同的y,要最后哪一个
			Integer key = new Integer(y);
			int[]point=(int[])pointMap.get(key);
			if(point!=null&&point[0]>x){
				return;
			}*/
			/*if(pointMap.get(task)!=null&&((int[])pointMap.get(task))[0]>x){
				return;
			}*/
			//当前日期之前的100%不连接
			
			if(task.getCompletionPercentage()>=100&&task.getEnd().before(today)){
				return;
			}
			
			//开始时间在today之前的0% 也不连
			if(task.getCompletionPercentage()<=0&&task.getStart().after(today)){
				return;
			}
			pointMap.put(task, new int[]{x,y});
		}
		
    }

}

