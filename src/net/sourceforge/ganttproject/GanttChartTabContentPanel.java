/*
 * Created on 22.10.2005
 */
package net.sourceforge.ganttproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

import net.sourceforge.ganttproject.action.task.LinkTasksAction;
import net.sourceforge.ganttproject.action.task.UnlinkTasksAction;
import net.sourceforge.ganttproject.chart.Chart;
import net.sourceforge.ganttproject.gui.TaskTreeUIFacade;
import net.sourceforge.ganttproject.gui.TestGanttRolloverButton;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.language.GanttLanguage;

import org.eclipse.core.runtime.IAdaptable;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;

class GanttChartTabContentPanel implements IAdaptable {
	private JSplitPane mySplitPane;
	private Component myTaskTree;
	private final Component myGanttChart;
	private final TaskTreeUIFacade myTreeFacade;
	private JPanel myTabContentPanel;
	private final IGanttProject myProject;
	private final UIFacade myWorkbenchFacade;
	private JSlider mySlider;
	public com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
	public com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
	public com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
	public com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
	public com.kingdee.bos.ctrl.swing.KDDatePicker pkJumpDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
	
	
	public void setFromDateValue(Date fromDate) {
		pkFromDate.setValue(fromDate);
		pkJumpDate.setMinimumDate(fromDate);

	}

	public void initSlider(int max) {
		mySlider.setMaximum(max);
		mySlider.setMinimum(0);
	}
	
	public void setToDateValue(Date toDate) {
		pkToDate.setValue(toDate);
		pkJumpDate.setMaximumDate(toDate);
	}

	protected GanttChartTabContentPanel(IGanttProject project, UIFacade workbenchFacade, TaskTreeUIFacade treeFacade, Component ganttChart) {
		myProject = project;
		myWorkbenchFacade = workbenchFacade;
		myTreeFacade = treeFacade;
		myTaskTree = treeFacade.getTreeComponent();
		myGanttChart = ganttChart;
	}

	Component getComponent() {
		// JScrollPane sc = new JScrollPane();
		JScrollPane sc = null;
		if (myTabContentPanel == null) {
			JPanel left = new JPanel(new BorderLayout());
			left.add(myTaskTree, BorderLayout.CENTER);
			left.setPreferredSize(new Dimension(600, 600));
			left.setBackground(new Color(102, 153, 153));

			JPanel right = new JPanel(new BorderLayout());

			right.add(myGanttChart, BorderLayout.CENTER);

			JPanel rightBottom = new JPanel(new BorderLayout());
			// 增加甘特图滚动条

			Integer max = Integer.parseInt((myProject.getTaskManager().getProjectEnd().getTime() - myProject.getTaskManager().getProjectStart().getTime()) / (1000 * 60 * 60 * 24) + "");

			mySlider = new JSlider();

			Sllistenner listener = new Sllistenner(pkJumpDate, (ScheduleGanttProject) myProject);
			mySlider.addChangeListener(listener);
			rightBottom.add(mySlider, BorderLayout.CENTER);
			// 跳转日期编辑框

			pkJumpDate.setName("pkJumpDate");

			pkJumpDate.addDataChangeListener(new DataChangeListener() {

				public void dataChanged(DataChangeEvent eventObj) {
					Date jumpDate = (Date) eventObj.getNewValue();
					GanttChartTabContentPanel.this.myWorkbenchFacade.getScrollingManager().scrollLeft(jumpDate);
					// 此处要更新myslider的值,mySlider.endDate-currentDate
					Date startDate = ((ScheduleGanttProject) GanttChartTabContentPanel.this.myProject).getTaskManager().getProjectStart();
					Integer diff = Integer.parseInt((jumpDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + "");
					mySlider.setValue(diff);
				}
			});
			pkJumpDate.setSize(90, 21);
			// 跳转日期container
			com.kingdee.bos.ctrl.swing.KDLabelContainer contJumpDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
			contJumpDate.setName("contJumpDate");
			contJumpDate.setBoundLabelText("跳至指定日期： ");
			contJumpDate.setBoundLabelLength(88);
			contJumpDate.setBoundLabelUnderline(true);
			contJumpDate.setBounds(new Rectangle(10, 10, 275, 19));
			contJumpDate.setBoundLabelUnderline(false);
			rightBottom.add(contJumpDate, BorderLayout.EAST);

			// 确定按钮 com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;

			contJumpDate.setBoundEditor(pkJumpDate);
			right.add(rightBottom, BorderLayout.SOUTH);
			sc = (JScrollPane) ((Container) left.getComponent(0)).getComponent(0);
			mySplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			if (GanttLanguage.getInstance().getComponentOrientation() == ComponentOrientation.LEFT_TO_RIGHT) {
				mySplitPane.setLeftComponent(left);
				mySplitPane.setRightComponent(right);
				mySplitPane.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			} else {
				mySplitPane.setRightComponent(left);
				mySplitPane.setLeftComponent(right);
				mySplitPane.setDividerLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - left.getPreferredSize().getWidth()));
				mySplitPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			}
			mySplitPane.setOneTouchExpandable(true);
			mySplitPane.setPreferredSize(new Dimension(800, 500));
			myTabContentPanel = new JPanel(new BorderLayout());

			myTabContentPanel.add(createButtonPanel(), BorderLayout.NORTH);
			myTabContentPanel.add(mySplitPane, BorderLayout.CENTER);
			sc.setPreferredSize(new Dimension(20, mySplitPane.getHeight()));
			JScrollBar vbar = ((net.sourceforge.ganttproject.GanttTree2) left.getComponent(0)).getTreeTable().getVerticalScrollBar();
			myTabContentPanel.add(vbar, BorderLayout.EAST);
		}

		return myTabContentPanel;

	}

	private Component createButtonPanel() {
		Box buttonBar = Box.createHorizontalBox();
		// JToolBar buttonBar = new JToolBar();
		// buttonBar.setFloatable(false);
		TestGanttRolloverButton unindentButton = new TestGanttRolloverButton(myTreeFacade.getUnindentAction()) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(unindentButton);

		TestGanttRolloverButton indentButton = new TestGanttRolloverButton(myTreeFacade.getIndentAction()) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(indentButton);
		//
		buttonBar.add(Box.createHorizontalStrut(3));
		//
		TestGanttRolloverButton upButton = new TestGanttRolloverButton(myTreeFacade.getMoveUpAction()) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(upButton);
		//
		TestGanttRolloverButton downButton = new TestGanttRolloverButton(myTreeFacade.getMoveDownAction()) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(downButton);
		//
		buttonBar.add(Box.createHorizontalStrut(8));
		Action linkAction = new LinkTasksAction(myProject.getTaskManager(), Mediator.getTaskSelectionManager(), myWorkbenchFacade);
		myTreeFacade.setLinkTasksAction(linkAction);
		TestGanttRolloverButton linkButton = new TestGanttRolloverButton(linkAction) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(linkButton);
		//
		Action unlinkAction = new UnlinkTasksAction(myProject.getTaskManager(), Mediator.getTaskSelectionManager(), myWorkbenchFacade);
		myTreeFacade.setUnlinkTasksAction(unlinkAction);
		TestGanttRolloverButton unlinkButton = new TestGanttRolloverButton(unlinkAction) {
			public String getText() {
				return null;
			}
		};
		buttonBar.add(unlinkButton);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonBar, BorderLayout.WEST);
		// 增加时间范围
		Box TimeBar = Box.createHorizontalBox();

		pkFromDate.setName("pkFromDate");
		pkFromDate.setSize(90, 19);

		contFromDate.setName("contFromDate");
		contFromDate.setBoundLabelText("日期范围： ");
		contFromDate.setBoundLabelLength(63);
		contFromDate.setBoundLabelUnderline(true);
		contFromDate.setBounds(new Rectangle(10, 10, 275, 19));
		contFromDate.setBoundLabelUnderline(false);
		contFromDate.setBoundEditor(pkFromDate);
		contFromDate.setEnabled(false);
		TimeBar.add(contFromDate);

		pkToDate.setName("pkToDate");
		// Date endDate = ((ScheduleGanttProject)
		// GanttChartTabContentPanel.this.myProject).getMyProjectEnd();
		// pkToDate.setValue(endDate);
		pkToDate.setSize(90, 19);

		contToDate.setName("contToDate");
		contToDate.setBoundLabelText(" 至");
		contToDate.setBoundLabelLength(19);
		contToDate.setBoundLabelUnderline(true);
		contToDate.setBounds(new Rectangle(10, 10, 275, 19));
		contToDate.setBoundLabelUnderline(false);
		contToDate.setBoundEditor(pkToDate);
		contToDate.setEnabled(false);
		TimeBar.add(contToDate);
		buttonPanel.add(TimeBar, BorderLayout.EAST);
		return buttonPanel;
	}

	int getDividerLocation() {
		return mySplitPane.getDividerLocation();
	}

	void setDividerLocation(int location) {
		mySplitPane.setDividerLocation(location);
	}

	public Object getAdapter(Class adapter) {
		if (Container.class.equals(adapter)) {
			return getComponent();
		}
		if (Chart.class.equals(adapter)) {
			return myGanttChart;
		}
		return null;
	}
}
