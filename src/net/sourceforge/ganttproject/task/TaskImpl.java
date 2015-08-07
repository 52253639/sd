package net.sourceforge.ganttproject.task;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.GanttTreeTableModel;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.calendar.AlwaysWorkingTimeCalendarImpl;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.calendar.GPCalendarActivity;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.document.AbstractURLDocument;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.algorithm.AlgorithmCollection;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySlice;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySliceAsDependant;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySliceAsDependee;
import net.sourceforge.ganttproject.task.dependency.TaskDependencySliceImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.StartStartConstraintImpl;
import net.sourceforge.ganttproject.task.hierarchy.TaskHierarchyItem;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDGPConstants;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.fdc.schedule.framework.util.KDGPCommonHelper;
import com.kingdee.util.StringUtils;

/**
 * 修改纪要： 1.recalculateActivities 方法可以重写 2009-09-25 12:37:15 2.myLength、myEnd
 * 字段改为protected 在KDTask里用到 2011-05-18
 * 
 * modify by sxhong
 */
public class TaskImpl implements Task {
	// public GanttCalendar myOldEndDate;
	
	public boolean isFromSuperTask = false;
	
    private int myID;

    private TaskManagerImpl myManager;

    private String myName;

    private String myWebLink = "";

    private boolean isMilestone;

    boolean isProjectTask;

    private int myPriority;

    private GanttCalendar myStart;

    protected GanttCalendar myEnd;

    private GanttCalendar myThird;

    private int myThirdDateConstraint;

    private int myCompletionPercentage;

    protected TaskLength myLength;

    private List myActivities = new ArrayList();

//    private boolean isStartFixed;

//    private boolean isFinishFixed;

    private boolean bExpand;

    // private final TaskDependencyCollection myDependencies = new
    // TaskDependencyCollectionImpl();
    private ResourceAssignmentCollectionImpl myAssignments;

    private TaskDependencySlice myDependencySlice;

    private TaskDependencySlice myDependencySliceAsDependant;

    private TaskDependencySlice myDependencySliceAsDependee;

    private boolean myEventsEnabled;

    private final TaskHierarchyItem myTaskHierarchyItem;

    private ShapePaint myShape;

    private Color myColor;

    private String myNotes;

    private MutatorImpl myMutator;

    private final CustomColumnsValues customValues;

    private boolean critical;

    public final static int NONE = 0;

    public final static int EARLIESTBEGIN = 1;

    protected TaskImpl(TaskManager taskManager, int taskID) {
        myManager = (TaskManagerImpl) taskManager;
        if (taskID == -1) {
            myID = myManager.getMaxID();
            myManager.increaseMaxID();
        } else {
            if (myManager.getTask(taskID) != null) {
                throw new IllegalArgumentException("There is a task with ID="
                        + taskID + " already");
            }
            myID = taskID;

        }
        myAssignments = new ResourceAssignmentCollectionImpl(this, myManager
                .getConfig().getResourceManager());
        myDependencySlice = new TaskDependencySliceImpl(this, myManager
                .getDependencyCollection());
        myDependencySliceAsDependant = new TaskDependencySliceAsDependant(this,
                myManager.getDependencyCollection());
        myDependencySliceAsDependee = new TaskDependencySliceAsDependee(this,
                myManager.getDependencyCollection());
        myPriority = 1;
        myTaskHierarchyItem = myManager.getHierarchyManager().createItem(this);
        myNotes = "";
        bExpand = true;
        myColor = null;

        customValues = new CustomColumnsValues(myManager.getCustomColumnStorage());
    }

    protected TaskImpl(TaskImpl copy, boolean isUnplugged) {
        myManager = copy.myManager;
        if (!isUnplugged) {
            myTaskHierarchyItem = myManager.getHierarchyManager().createItem(
                    this);
        } else {
            myTaskHierarchyItem = null;
        }
        myAssignments = new ResourceAssignmentCollectionImpl(this, myManager
                .getConfig().getResourceManager());
        myAssignments.importData(copy.getAssignmentCollection());
        myID = copy.myID;
        myName = copy.myName;
        myWebLink = copy.myWebLink;
        isMilestone = copy.isMilestone;
        isProjectTask = copy.isProjectTask;
        myPriority = copy.myPriority;
        myStart = copy.myStart;
        myEnd = copy.myEnd;
        myThird = copy.myThird;
        myThirdDateConstraint = copy.myThirdDateConstraint;
        myCompletionPercentage = copy.myCompletionPercentage;
        myLength = copy.myLength;
        myShape = copy.myShape;
        myColor = copy.myColor;
        myNotes = copy.myNotes;
        bExpand = copy.bExpand;
        //
        myDependencySlice = new TaskDependencySliceImpl(this, myManager
                .getDependencyCollection());
        myDependencySliceAsDependant = new TaskDependencySliceAsDependant(this,
                myManager.getDependencyCollection());
        myDependencySliceAsDependee = new TaskDependencySliceAsDependee(this,
                myManager.getDependencyCollection());

        customValues = (CustomColumnsValues) copy.getCustomValues().clone();

        recalculateActivities();
    }

    public Task unpluggedClone() {
        TaskImpl result = new TaskImpl(this, true) {
            public boolean isSupertask() {
                return false;
            }
        };
        return result;
    }

    public TaskMutator createMutator() {
        if (myMutator != null) {
//        	return myMutator;
            throw new RuntimeException("Two mutators have been requested for task="+getName(),
                    myException);
        }
        myMutator = new MutatorImpl();
        myException = new Exception();
        return myMutator;
    }

    public TaskMutator createMutatorFixingDuration() {
        if (myMutator != null) {
            throw new RuntimeException("Two mutators have been requested for task="+getName(),
                    myException);
        }
        myMutator = new MutatorImpl() {
            public void setStart(GanttCalendar start) {
                super.setStart(start);
				TaskImpl.this.myEnd = null;
            }
        };
        myException = new Exception();
        return myMutator;
    }


    private Exception myException;

    // main properties
    public int getTaskID() {
        return myID;
    }

    public String getName() {
        return myName;
    }

    public String getWebLink() {
        return myWebLink;
    }

    public List/*<Document>*/ getAttachments() {
    	if (getWebLink()!= null && !"".equals(getWebLink())) {
	    	return Collections.singletonList(new AbstractURLDocument() {
				public boolean canRead() {
					return true;
				}
				public boolean canWrite() {
					return false;
				}
				public String getDescription() {
					return null;
				}
				public InputStream getInputStream() throws IOException {
					return null;
				}
				public OutputStream getOutputStream() throws IOException {
					return null;
				}
				public String getPath() {
					return null;
				}
				public URI getURI() {
					try {
						return new URI(new URL(getWebLink()).toString());
					}
					catch (URISyntaxException e) {
					}
					catch (MalformedURLException e) {
						File f = new File(getWebLink());
						if (f.exists()) {
							return f.toURI();
						}
					}
					try {
						URL context = myManager.getProjectDocument();
						if (context==null) {
							return null;
						}
						URL relative = new URL(context, getWebLink());
						return new URI(URLEncoder.encode(relative.toString(), "utf-8"));
					}
					catch (URISyntaxException e) {
					}
					catch (MalformedURLException e) {
					}
					catch (UnsupportedEncodingException e) {
					}
					return null;
				}
				public boolean isLocal() {
					return false;
				}
				public boolean isValidForMRU() {
					return false;
				}
				public void write() throws IOException {
				}
	    	});
    	}
    	else {
    		return Collections.EMPTY_LIST;
    	}
    }
    public boolean isMilestone() {
        return isMilestone;
    }

    public int getPriority() {
        return myPriority;
    }

    public GanttCalendar getStart() {
    	// 进度性能优化： 性能瓶颈 by skyiter_wang 2014-06-11 
    	
    	// TODO 后续处理
    	TaskDependency[] dependencys = this.getDependencies().toArray();
		// for (int i = 0; i < dependencys.length; i++) {
		// TaskDependency dependency = dependencys[i];
		// if (dependency.getDependant().getName().equals(myName)) {
		// // System.out.println(dependency.getDependee());
		// // System.out.println(dependency.getConstraint());
		// }
		// }
		if (dependencys.length == 1) {
			TaskDependency dependency = dependencys[0];
			if (dependency.getConstraint() instanceof FinishStartConstraintImpl || dependency.getConstraint() instanceof StartStartConstraintImpl) {
				return myStart;
			}
		}
        if (myMutator != null
                && myMutator.myIsolationLevel == TaskMutator.READ_UNCOMMITED) {
            return myMutator.getStart();
        } else {
            return myStart;
        }
    }

    public GanttCalendar getEnd() {
    	
        GanttCalendar result = null;
        if (myMutator != null
                && myMutator.myIsolationLevel == TaskMutator.READ_UNCOMMITED) {
            result = myMutator.getEnd();
        }
        if (result==null) {
            if (myEnd == null) {
                myEnd = calculateEnd();
            }
            result = myEnd;
        }
        return result;
    }

    GanttCalendar calculateEnd() {
    	if(this instanceof KDTask){
    		KDTask task = (KDTask) this;
    		if(task.getScheduleTaskInfo()!=null
    				&&task.getScheduleTaskInfo().getScheduleBase()!=null
    				&&task.getScheduleTaskInfo().getScheduleBase().containsKey("createDependencyFaile")){
    			return ScheduleParserHelper.parseDateToGanttCalendar(task.getScheduleTaskInfo().getEnd());
    		}
    	}
        GanttCalendar result = getStart().Clone();
		
        
        Date newEnd = shiftDate(result.getTime(), getDuration());
        result.setTime(newEnd);
        return result;
    }

    public GanttCalendar getThird() {
        if (myMutator != null
                && myMutator.myIsolationLevel == TaskMutator.READ_UNCOMMITED) {
            return myMutator.getThird();
        } else {
            return myThird;
        }
    }

    public int getThirdDateConstraint() {
        return myThirdDateConstraint;
    }

    public TaskActivity[] getActivities() {
        List activities = myMutator == null ? null : myMutator.getActivities();
        if (activities == null) {
            activities = myActivities;
        }
        return (TaskActivity[]) activities.toArray(new TaskActivity[activities
                .size()]);
    }

    public TaskLength getDuration() {
        return (myMutator != null && myMutator.myIsolationLevel == TaskMutator.READ_UNCOMMITED) ? myMutator
                .getDuration()
                : myLength;
    }

    public int getCompletionPercentage() {
        return (myMutator != null && myMutator.myIsolationLevel == TaskMutator.READ_UNCOMMITED) ? myMutator
                .getCompletionPercentage()
                : myCompletionPercentage;
    }

    public boolean getExpand() {
        return bExpand;
    }

    public ShapePaint getShape() {
        return myShape;
    }

    public Color getColor() {
        Color result = myColor;
        if (result == null) {
            if (isMilestone() || getNestedTasks().length > 0) {
                result = Color.BLACK;
            } else {
                result = myManager.getConfig().getDefaultColor();
            }
        }
        return result;
    }

    public String getNotes() {
        return myNotes;
    }

    public GanttTaskRelationship[] getPredecessors() {
        return new GanttTaskRelationship[0]; // To change body of implemented
        // methods use Options | File
        // Templates.
    }

    public GanttTaskRelationship[] getSuccessors() {
        return new GanttTaskRelationship[0]; // To change body of implemented
        // methods use Options | File
        // Templates.
    }

    public ResourceAssignment[] getAssignments() {
        return myAssignments.getAssignments();
    }

    public ResourceAssignmentCollection getAssignmentCollection() {
        return myAssignments;
    }

    //
    public Task getSupertask() {
        TaskHierarchyItem container = myTaskHierarchyItem.getContainerItem();
        return container.getTask();
    }

    public Task[] getNestedTasks() {
        TaskHierarchyItem[] nestedItems = myTaskHierarchyItem.getNestedItems();
        Task[] result = new Task[nestedItems.length];
        for (int i = 0; i < nestedItems.length; i++) {
            result[i] = nestedItems[i].getTask();
        }
        return result;
    }

    public void move(Task targetSupertask) {
        TaskImpl supertaskImpl = (TaskImpl) targetSupertask;
        TaskHierarchyItem targetItem = supertaskImpl.myTaskHierarchyItem;
        myTaskHierarchyItem.delete();
        targetItem.addNestedItem(myTaskHierarchyItem);
        myManager.onTaskMoved(this);
    }

    public void delete() {
        getDependencies().clear();
        getAssignmentCollection().clear();
    }

    public TaskDependencySlice getDependencies() {
        return myDependencySlice;
    }

    public TaskDependencySlice getDependenciesAsDependant() {
        return myDependencySliceAsDependant;
    }

    public TaskDependencySlice getDependenciesAsDependee() {
        return myDependencySliceAsDependee;
    }

    public TaskManager getManager() {
        return myManager;
    }

    // TODO: remove this hack. ID must never be changed
    protected void setTaskIDHack(int taskID) {
        myID = taskID;
    }

    private static interface EventSender {
        void enable();

        void fireEvent();
    }

    private class ProgressEventSender implements EventSender {
        private boolean myEnabled;

        public void fireEvent() {
            if (myEnabled) {
                myManager.fireTaskProgressChanged(TaskImpl.this);
            }
            myEnabled = false;
        }

        public void enable() {
            myEnabled = true;
        }

    }

    private class PropertiesEventSender implements EventSender {
        private boolean myEnabled;

        public void fireEvent() {
            if (myEnabled) {
                myManager.fireTaskPropertiesChanged(TaskImpl.this);
            }
            myEnabled = false;
        }

        public void enable() {
            myEnabled = true;
        }
    }

    private static class FieldChange {
        String myFieldName;

        Object myFieldValue;
		Object myOldValue;

        EventSender myEventSender;


        void setValue(Object newValue) {
            myFieldValue = newValue;
            myEventSender.enable();
        }

		public void setOldValue(Object oldValue) {
			myOldValue = oldValue;
		}
    }

    private static class DurationChange extends FieldChange {
        Date getCachedDate(int length) {
            if (myDates == null) {
                return null;
            }
            int index = length - myMinLength;
            if (index < 0 || index >= myDates.size()) {
                return null;
            }
            return (Date) myDates.get(index);
        }

        void cacheDate(Date date, int length) {
            if (myDates == null) {
                myDates = new ArrayList();
            }
            int index = length - myMinLength;
            while (index <= -1) {
                myDates.add(0, null);
                index++;
            }
            while (index > myDates.size()) {
                myDates.add(null);
            }
            if (index == -1) {
                myDates.add(0, date);
            } else if (index == myDates.size()) {
                myDates.add(date);
            } else {
                myDates.set(index, date);
            }
        }

        private int myMinLength = 0;

        private List myDates;

    }

    private class MutatorImpl implements TaskMutator {
        private EventSender myPropertiesEventSender = new PropertiesEventSender();

        private EventSender myProgressEventSender = new ProgressEventSender();

        private FieldChange myCompletionPercentageChange;

        private FieldChange myStartChange;

        private FieldChange myEndChange;

        private FieldChange myThirdChange;

        private DurationChange myDurationChange;

        private List myActivities;

        private final List myCommands = new ArrayList();

        private int myIsolationLevel;

        public void commit() {
            try {
                boolean fireChanges = false;
                if (myStartChange != null) {
                    GanttCalendar start = getStart();
					// TaskImpl.this.myOldEndDate = TaskImpl.this.getEnd();
                    TaskImpl.this.setStart(start);
                }
                if (myDurationChange != null) {
                    TaskLength duration = getDuration();
                    TaskImpl.this.setDuration(duration);
                    myEndChange = null;
                }
                if (myCompletionPercentageChange != null) {
                    int newValue = getCompletionPercentage();
                    TaskImpl.this.setCompletionPercentage(newValue);
                }
                if (myEndChange != null) {
                    GanttCalendar end = getEnd();
                    if (end.getTime().compareTo(TaskImpl.this.getStart().getTime())>=0) {
                    	TaskImpl.this.setEnd(end);
                    }
                }
                if (myThirdChange != null) {
                    GanttCalendar third = getThird();
                    TaskImpl.this.setThirdDate(third);
                }
                for (int i = 0; i < myCommands.size(); i++) {
                    Runnable next = (Runnable) myCommands.get(i);
                    next.run();
                }
                myCommands.clear();
                myPropertiesEventSender.fireEvent();
                myProgressEventSender.fireEvent();
            } finally {
                TaskImpl.this.myMutator = null;
            }
            if (myStartChange!=null && TaskImpl.this.isSupertask()) {
                TaskImpl.this.adjustNestedTasks();
            }
            if ((myStartChange!=null || myEndChange!=null || myDurationChange!=null) && areEventsEnabled()) {
            	GanttCalendar oldStart = (GanttCalendar) (myStartChange==null ? TaskImpl.this.getStart() : myStartChange.myOldValue);
//            	GanttCalendar oldStart = (GanttCalendar) (myStartChange==null ? TaskImpl.this.getStart() : myStartChange.myOldValue);
            	GanttCalendar oldEnd = (GanttCalendar) (myEndChange==null ? TaskImpl.this.getEnd() : myEndChange.myOldValue);
                myManager.fireTaskScheduleChanged(TaskImpl.this, oldStart, oldEnd);
            }

        }

        public GanttCalendar getThird() {
            return myThirdChange == null ? TaskImpl.this.myThird
                    : (GanttCalendar) myThirdChange.myFieldValue;
        }

        public List getActivities() {
            if (myActivities == null && (myStartChange != null)
                    || (myDurationChange != null)) {
                myActivities = new ArrayList();
                TaskImpl.this.recalculateActivities(myActivities, getStart()
                        .getTime(), TaskImpl.this.getEnd().getTime());
            }
            return myActivities;
        }

        public void setName(final String name) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setName(name);
                }
            });
        }

        public void setProjectTask(final boolean projectTask) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setProjectTask(projectTask);
                }
            });
        }

        public void setMilestone(final boolean milestone) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setMilestone(milestone);
                }
            });
        }

        public void setPriority(final int priority) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setPriority(priority);
                }
            });
        }

        public void setStart(final GanttCalendar start) {
        	GanttCalendar currentStart = getStart();
        	if (currentStart!=null && start.equals(currentStart)) {
        		return;
        	}
            if (myStartChange == null) {
                myStartChange = new FieldChange();
                myStartChange.myEventSender = myPropertiesEventSender;
            }
            myStartChange.setOldValue(TaskImpl.this.myStart);
            myStartChange.setValue(start);
            myActivities = null;
        }

		public void setEnd(final GanttCalendar end) {
			GanttCalendar currentEnd = getEnd();
			if (currentEnd != null && end.equals(currentEnd)) {
				return;
			}
			if (TaskImpl.this instanceof KDTask) {
				if (((KDTask) TaskImpl.this).checkIsOutOfMain(end.getTime(),
						null)) {
					myMutator = null;
					return;
				}
			}
			if (myEndChange == null) {
                myEndChange = new FieldChange();
                myEndChange.myEventSender = myPropertiesEventSender;
            }
            myEndChange.setOldValue(TaskImpl.this.myEnd);
            myEndChange.setValue(end);
            myActivities = null;
        }

        /**
		 * 设置结束日期并计算有效工期<br>
		 * 一般是设置有效工期并计算结束日期<br>
		 * 这里相反，用于KDTask构造时调用
		 * 
		 * @author emanon
		 * @param end
		 */
		public long setDateAndCalDur(final ScheduleCalendarImpl calendar,
				final Date start, final Date end) {
			// 设置结束日期
			if (myEndChange == null) {
				myEndChange = new FieldChange();
				myEndChange.myEventSender = myPropertiesEventSender;
			}
			myEndChange.setOldValue(TaskImpl.this.myEnd);
			myEndChange.setValue(new GanttCalendar(end));
			myActivities = null;

			// 改变有效工期
			long dur = calendar.getEffectTimes(start, end);
			TaskLength length = getManager().createLength(dur);
			setDuration(length);
			return length.getLength();
		}
        
        public void setThird(final GanttCalendar third,
                final int thirdDateConstraint) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setThirdDateConstraint(thirdDateConstraint);
                }
            });
            if (myThirdChange == null) {
                myThirdChange = new FieldChange();
                myThirdChange.myEventSender = myPropertiesEventSender;
            }
            myThirdChange.setValue(third);
            myActivities = null;

        }

        public void setDuration(final TaskLength length) {

            // If duration of task was set to 0 or less do not change it
        	//修改支持有效工期1
            if (length.getLength() <= 0-KDGPConstants.EFFECTTIMESBALANCE) {
                return;
            }

            if (myDurationChange == null) {
                myDurationChange = new DurationChange();
                myDurationChange.myEventSender = myPropertiesEventSender;
                myDurationChange.setValue(length);
            } else {
                TaskLength currentLength = (TaskLength) myDurationChange.myFieldValue;
                if (currentLength.getLength() - length.getLength() == 0) {
                    return;
                }
            }
            TaskLength prevLength = (TaskLength) myDurationChange.myFieldValue;
            // System.err.println("new duration="+length+"
            // previous="+prevLength);
            // Date prevEnd =
            // myDurationChange.getCachedDate((int)prevLength.getLength());
            Date prevEnd = null;
            // System.err.println("previously cached shift="+prevEnd);
            myDurationChange.setValue(length);
            GanttCalendar newEnd;
            Date shifted;
            
            if (prevEnd == null) {
                // System.err.println("no prev, length="+length.getLength());
				shifted = shiftDate(getStart().getTime(), length);
            } else {
                // System.err.println("yes prev,
                // length="+(length.getLength()-prevLength.getLength()));
				shifted = shiftDate(prevEnd, getManager().createLength(
						length.getTimeUnit(),
                				length.getLength() - prevLength.getLength()));
            }
            // System.err.println("caching shift="+shifted+" for
            // duration="+length);
            // myDurationChange.cacheDate(shifted, (int)length.getLength());
            newEnd = new GanttCalendar(shifted);
            setEnd(newEnd);
            /****
             * 这里，需要考虑，前置任务的搭接关系为FF的的情况
             * 
             * 如果修改的是搭接时间，根据搭接时间反算结束时间，再反算开始时间
             * 根据开始时间，结束时间，反算搭接时间
             */
//            if (TaskImpl.this.getClass().getName().equals(KDTask.class.getName())){
//            	KDTask task = (KDTask)TaskImpl.this;
//            	TaskDependency[] taskDependencys = (task.getManager().getDependencyCollection().getDependenciesAsDependant(task));
//            	if(taskDependencys!=null&&taskDependencys.length>0&&taskDependencys[0].getConstraint().getID()==3){
//            		if(taskDependencys[0].getDependee() instanceof KDTask){
//            			KDTask preTask = (KDTask) taskDependencys[0].getDependee();
//            			int diffDay = taskDependencys[0].getDifference();
//            			
//            		}
//            	}
//            }
            myActivities = null;
        }

        public void setExpand(final boolean expand) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setExpand(expand);
                }
            });
        }

        public void setCompletionPercentage(final int percentage) {
            if (myCompletionPercentageChange == null) {
                myCompletionPercentageChange = new FieldChange();
                myCompletionPercentageChange.myEventSender = myProgressEventSender;
            }
            myCompletionPercentageChange.setValue(new Integer(percentage));
        }

//        public void setStartFixed(final boolean isFixed) {
//            myCommands.add(new Runnable() {
//                public void run() {
//                    TaskImpl.this.setStartFixed(isFixed);
//                }
//            });
//        }
//
//        public void setFinishFixed(final boolean isFixed) {
//            myCommands.add(new Runnable() {
//                public void run() {
//                    TaskImpl.this.setFinishFixed(isFixed);
//                }
//            });
//
//        }

        public void setCritical(final boolean critical) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setCritical(critical);
                }
            });
        }

        public void setShape(final ShapePaint shape) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setShape(shape);
                }
            });
        }

        public void setColor(final Color color) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setColor(color);
                }
            });
        }

        public void setNotes(final String notes) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.setNotes(notes);
                }
            });
        }

        public void addNotes(final String notes) {
            myCommands.add(new Runnable() {
                public void run() {
                    TaskImpl.this.addNotes(notes);
                }
            });
        }

        public int getCompletionPercentage() {
            return myCompletionPercentageChange == null ? TaskImpl.this.myCompletionPercentage
                    : ((Integer) myCompletionPercentageChange.myFieldValue)
                            .intValue();
        }

        GanttCalendar getStart() {
            return myStartChange == null ? TaskImpl.this.myStart
                    : (GanttCalendar) myStartChange.myFieldValue;
        }

        GanttCalendar getEnd() {
            return myEndChange == null ? null
                    : (GanttCalendar) myEndChange.myFieldValue;
        }

        TaskLength getDuration() {
            return myDurationChange == null ? TaskImpl.this.myLength
                    : (TaskLength) myDurationChange.myFieldValue;
        }

        public void shift(float unitCount) {

            Task result = getPrecomputedShift(unitCount);
            if (result == null) {
                result = TaskImpl.this.shift(unitCount);
                cachePrecomputedShift(result, unitCount);
            }
            // System.err.println("[MutatorImpl] shift(): result="+result);
            setStart(result.getStart());
            setDuration(result.getDuration());
            setEnd(result.getEnd());
        }

        public void shift(TaskLength shift) {
            TaskImpl.this.shift(shift);
        }

        public void setIsolationLevel(int level) {
            myIsolationLevel = level;
        }

        private void cachePrecomputedShift(Task result, float unitCount) {
        }

        private Task getPrecomputedShift(float unitCount) {
            return null;
        }

        private TaskInfo myTaskInfo;

        public TaskInfo getTaskInfo() {
            return myTaskInfo;
        }

        public void setTaskInfo(TaskInfo taskInfo) {
            myTaskInfo = taskInfo;

        }

    }

    /**
	 * 取得当前任务的父任务task
	 * <p>
	 * 级次为0时返回空，否则循环找级次小于当前级次的第一个任务即为父级
	 * 
	 * @return
	 */
	protected KDTask getParentTask() {
		int myTaskID = TaskImpl.this.getTaskID();
		KDTask myTask = (KDTask) myManager.getTask(myTaskID);
		KDTask pTask = null;
		int myLevel = myTask.getScheduleTaskInfo().getLevel();
		if (myLevel == 0) {
			pTask = null;
		} else {
			for (int i = myTaskID; i >= 0; i--) {
				KDTask curTask = (KDTask) myManager.getTask(i);
				if (curTask.getScheduleTaskInfo().getLevel() < myLevel) {
					pTask = curTask;
					break;
				}
			}
		}
		return pTask;
	}

	public void setName(String name) {
        myName = name;
    }

    public void setWebLink(String webLink) {
        myWebLink = webLink;
    }

    public void setMilestone(boolean milestone) {
        isMilestone = milestone;
    }

    public void setPriority(int priority) {
        myPriority = priority;
    }

    public void setStart(GanttCalendar start) {
    	Date closestWorkingStart;
		if (this instanceof KDTask) {
			
			closestWorkingStart = ((KDTask) this).getCalendar()
					.findClosestWorkingTime(start.getTime());
		} else {
//			
//			ScheduleCalendarImpl calImpl = (ScheduleCalendarImpl) TaskImpl.this.myManager.getMyCalendarMap().get(this.myID);
//			closestWorkingStart = calImpl
//					.findClosestWorkingTime(start.getTime());
			KDTask task = (KDTask) myManager.getTask(myID);
			ScheduleCalendarImpl cal = null;
			if(task != null)
			  cal = new ScheduleCalendarImpl(((FDCScheduleTaskInfo)task.getScheduleTaskInfo()).getCalendar());
			if(cal == null){
				cal = ScheduleCalendarImpl.DEFAULT_CALENDAR;
			}
			closestWorkingStart = cal.findClosestWorkingTime(start.getTime());
		}
		closestWorkingStart = FDCDateHelper.getDayBegin(closestWorkingStart);
		start.setTime(closestWorkingStart);
		myStart = start;
		if (!this.isFromSuperTask) {
			afterSetStart();
		}
		recalculateActivities();
	}
    
    public void setStartFromSuper(GanttCalendar start) {
		Date closestWorkingStart;
		if (this instanceof KDTask) {

			closestWorkingStart = ((KDTask) this).getCalendar().findClosestWorkingTime(start.getTime());
		} else {
			//			
			// ScheduleCalendarImpl calImpl = (ScheduleCalendarImpl)
			// TaskImpl.this.myManager.getMyCalendarMap().get(this.myID);
			// closestWorkingStart = calImpl
			// .findClosestWorkingTime(start.getTime());
			KDTask task = (KDTask) myManager.getTask(myID);
			ScheduleCalendarImpl cal = null;
			if (task != null)
				cal = new ScheduleCalendarImpl(((FDCScheduleTaskInfo) task.getScheduleTaskInfo()).getCalendar());
			if (cal == null) {
				cal = ScheduleCalendarImpl.DEFAULT_CALENDAR;
			}
			closestWorkingStart = cal.findClosestWorkingTime(start.getTime());
		}
		closestWorkingStart = FDCDateHelper.getDayBegin(closestWorkingStart);
		start.setTime(closestWorkingStart);
		myStart = start;
		recalculateActivities();
	}
    
    public void afterSetStart() {
		TaskDependency dependency = GanttTreeTableModel.getCurrentTaskDependency(this);
		if (dependency != null && dependency.getConstraint() instanceof FinishFinishConstraintImpl) {
			// 用工期倒排
			Task dependee = dependency.getDependee();// 被依赖
			int diff = dependency.getDifference();
			BigDecimal duration = FDCHelper.ONE;
			ScheduleCalendarInfo cal  = null;
			if (this instanceof KDTask) {
				cal = ((FDCScheduleTaskInfo) ((KDTask) this).getScheduleTaskInfo()).getCalendar();
			}else{
				cal = ((FDCScheduleTaskInfo) ((KDTask) dependee).getScheduleTaskInfo()).getCalendar();	
			}
			
			Date startDate = ScheduleCalendarHelper.getEndDate(dependee.getEnd().getTime(), new BigDecimal(diff), cal);
			duration = ScheduleCalendarHelper.getEffectTimes(myStart.getTime(), startDate, cal);
			myEnd = ScheduleParserHelper.parseDateToGanttCalendar(startDate);
			myLength = myManager.createLength(myLength.getTimeUnit(), duration.intValue());
		} else {
			myEnd = null;
		}
	}

	private void adjustNestedTasks() {
    	if(true){
    		if(!KDGPCommonHelper.isScheduleTask(this)){
    			return;
    		}
    	}
        try {
            AlgorithmCollection algorithmCollection = myManager.getAlgorithmCollection();
            if (algorithmCollection!=null) {
                algorithmCollection.getAdjustTaskBoundsAlgorithm().adjustNestedTasks(this);
            }
        } catch (TaskDependencyException e) {
        	if (!GPLogger.log(e)) {
        		e.printStackTrace(System.err);
        	}
        }
    }

    public boolean isSupertask() {
        return myManager.getTaskHierarchy().hasNestedTasks(this);
    }

    public void setEnd(GanttCalendar end) {
        GanttCalendar oldFinish = myEnd == null ? null : myEnd.Clone();
        Date endDate = end.getTime();
		endDate = FDCDateHelper.getDayBegin(endDate);
        myEnd = end;
        myEnd.setTime(endDate);
        recalculateActivities();
        // System.err.println("we have "+myActivities.size()+" activities");
//        if (areEventsEnabled()) {
//            myManager.fireTaskScheduleChanged(this, myStart.Clone(), oldFinish);
//        }
    }

    public void setThirdDate(GanttCalendar third) {
        GanttCalendar oldThird = myThird == null ? null : myThird.Clone();
        myThird = third;
        // recalculateActivities();
        // if (areEventsEnabled()) {
        // myManager.fireTaskScheduleChanged(this, myThird.Clone(), oldThird);
        // }
    }

    public void setThirdDateConstraint(int thirdDateConstraint) {
        myThirdDateConstraint = thirdDateConstraint;
    }

    public void shift(TaskLength shift) {
        float unitCount = shift.getLength(myLength.getTimeUnit());
        if (unitCount != 0f) {
            Task resultTask = shift(unitCount);
            GanttCalendar oldStart = myStart;
            GanttCalendar oldEnd = myEnd;
            myStart = resultTask.getStart();
            myLength = resultTask.getDuration();
            myEnd = resultTask.getEnd();
            if (areEventsEnabled()) {
                myManager.fireTaskScheduleChanged(this, oldStart, oldEnd);
            }
            recalculateActivities();
        }
    }

    public Task shift(float unitCount) {
       TimeTools.getInstance().msValuePrintln("shift()--------start");
    	Task clone = unpluggedClone();
        if (unitCount > 0) {
        	TimeTools.getInstance().msValuePrintln("shift()--------if");
            TaskLength length = myManager.createLength(myLength.getTimeUnit(),
                    unitCount);
            // clone.setDuration(length);
            Date shiftedDate = RESTLESS_CALENDAR.shiftDate(myStart.getTime(), length);
            clone.setStart(new GanttCalendar(shiftedDate));
            clone.setDuration(myLength);
        } else {
        	TimeTools.getInstance().msValuePrintln("shift()--------else");
            Date newStart = shiftDate(clone.getStart().getTime(),
            		                  getManager().createLength(clone.getDuration().getTimeUnit(), (long) unitCount));
            clone.setStart(new GanttCalendar(newStart));
            clone.setDuration(myLength);
        }
        TimeTools.getInstance().msValuePrintln("shift()--------end");
        return clone;
    }

    public void setDuration(TaskLength length) {
		//TODO 后续处理
		myLength = length;
		
		TaskDependency dependency = GanttTreeTableModel.getCurrentTaskDependency(this);
		if(dependency == null){
			myEnd = null;
			recalculateActivities();
		}else{
			TaskDependencyConstraint constraint = dependency.getConstraint();
				if (dependency.getDependant().getTaskID() == this.getTaskID() && constraint instanceof FinishFinishConstraintImpl) {
				BigDecimal myDuraiton = FDCHelper.toBigDecimal(length.getLength());
				ScheduleCalendarInfo cal = null;
				if (this instanceof KDTask) {
					cal = ((FDCScheduleTaskInfo) ((KDTask) this).getScheduleTaskInfo()).getCalendar();
				} else {
					cal = ((FDCScheduleTaskInfo) ((KDTask) dependency.getDependant()).getScheduleTaskInfo()).getCalendar();
				}

				Date endDate = ScheduleCalendarHelper.getStartDate(myEnd.getTime(), myDuraiton, cal);
					myStart = ScheduleParserHelper.parseDateToGanttCalendar(endDate);
					recalculateActivities(myEnd);
				} else {
					myEnd = null;
					recalculateActivities();
			}
		}

		//		
//            }


    }

    /**
	 * 改成可继承了，由KDTask里根据task对应的日历计算 by emanon
	 * 
	 * @param input
	 * @param duration
	 * @return
	 */
	protected Date shiftDate(Date input, TaskLength duration) {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		if (FDCHelper.isFDCDebug()) {
			TimeTools.getInstance().msValuePrintln("shiftDate()--------start");
			TimeTools.getInstance().msValuePrintln("shiftDate()--------:" + input + ";" + duration);
		}
		// ////////////////////////////////////////////////////////////////////////
		
		return myManager.getConfig().getCalendar().shiftDate(input, duration);
	}

    public TaskLength translateDuration(TaskLength duration) {
        return myManager.createLength(myLength.getTimeUnit(),
                translateDurationValue(duration));
    }

    private float translateDurationValue(TaskLength duration) {
        if (myLength.getTimeUnit().equals(duration.getTimeUnit())) {
            return duration.getValue();
        }
        if (myLength.getTimeUnit().isConstructedFrom(duration.getTimeUnit())) {
            return duration.getValue()
                    / myLength.getTimeUnit().getAtomCount(
                            duration.getTimeUnit());
        }
        if (duration.getTimeUnit().isConstructedFrom(myLength.getTimeUnit())) {
            return duration.getValue()
                    * duration.getTimeUnit().getAtomCount(
                            myLength.getTimeUnit());
        }
        throw new RuntimeException("Can't transalte duration=" + duration
                + " into units=" + myLength.getTimeUnit());
    }

    //改成可以继承了  by sxhong 2009-09-25 12:36:30
    protected void recalculateActivities() {
        if (myLength == null || myManager == null) {
            return;
        }
		recalculateActivities(myActivities, myStart.getTime(), getEnd().getTime());
        int length = 0;
        for (int i = 0; i < myActivities.size(); i++) {
            TaskActivity next = (TaskActivity) myActivities.get(i);
			if (next.getIntensity() > 0) {
				length += next.getDuration().getLength(
						getDuration().getTimeUnit());
			}
		}
		myLength = getManager().createLength(myLength.getTimeUnit(), length);
	}
  
    protected void recalculateActivities(GanttCalendar myNewEnd) {
		if (myNewEnd == null) {
			myNewEnd = getEnd();
		}
		if (myLength == null || myManager == null) {
			return;
		}
		recalculateActivities(myActivities, myStart.getTime(), myNewEnd.getTime());
		int length = 0;
		for (int i = 0; i < myActivities.size(); i++) {
			TaskActivity next = (TaskActivity) myActivities.get(i);
			if (next.getIntensity() > 0) {
				length += next.getDuration().getLength(getDuration().getTimeUnit());
			}
		}
		myLength = getManager().createLength(myLength.getTimeUnit(), length);
	}
    
   //改成可以继承了  by sxhong 2009-09-25 12:36:30
    /**
     * 对getActivities 的要求应该是这样的:
     * 如果有三天,12,13,14,15 其中13,14是周末,应该这样分段:12-12,13-14,15-15
     * @param output
     * @param startDate
     * @param endDate
     */
    protected void recalculateActivities(List output, Date startDate, Date endDate) {
        GPCalendar calendar = myManager.getConfig().getCalendar();
        List activities = calendar.getActivities(startDate, endDate);
        output.clear();
//        TimeTools.getInstance().msValuePrintln("recalculateActivities(List output, Date startDate, Date endDate)--------start");
        for (int i = 0; i < activities.size(); i++) {
            GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities
                    .get(i);
            TaskActivityImpl nextTaskActivity ;
            if (nextCalendarActivity.isWorkingTime()) {
                nextTaskActivity = new TaskActivityImpl(this, nextCalendarActivity.getStart(), nextCalendarActivity.getEnd(), 1.0f, true);
            } else if (i > 0 && i + 1 < activities.size()) {
                nextTaskActivity = new TaskActivityImpl(this, nextCalendarActivity.getStart(), nextCalendarActivity.getEnd(), 0, false);
            } else {
                continue;
            }
            output.add(nextTaskActivity);
        }
//        TimeTools.getInstance().msValuePrintln("recalculateActivities(List output, Date startDate, Date endDate)--------end");
    }

    public void setCompletionPercentage(int percentage) {
        int oldPercentage = myCompletionPercentage;
        myCompletionPercentage = percentage;
        if (oldPercentage != myCompletionPercentage) {
            EventSender progressEventSender = new ProgressEventSender();
            progressEventSender.enable();
            progressEventSender.fireEvent();
        }
    }

//    public void setStartFixed(boolean isFixed) {
//        isStartFixed = isFixed;
//    }

//    public void setFinishFixed(boolean isFixed) {
//        isFinishFixed = isFixed;
//    }

    public void setShape(ShapePaint shape) {
        myShape = shape;
    }

    public void setColor(Color color) {
        myColor = color;
    }

    public void setNotes(String notes) {
        myNotes = notes;
    }

    public void setExpand(boolean expand) {
        bExpand = expand;
    }

    public void addNotes(String notes) {
        myNotes += notes;
    }

    protected void enableEvents(boolean enabled) {
        myEventsEnabled = enabled;
    }

    protected boolean areEventsEnabled() {
        return myEventsEnabled;
    }

    /**
     * Allows to determine, if a special shape is defined for this task.
     *
     * @return true, if this task has its own shape defined.
     */
    public boolean shapeDefined() {
        return (myShape != null);
    }

    /**
     * Allows to determine, if a special color is defined for this task.
     *
     * @return true, if this task has its own color defined.
     */

    public boolean colorDefined() {

        return (myColor != null);

    }

    public String toString() {
        //return myID + ": " + myStart.getTime() + "-" + myLength;
    	return getName();
    }

    public boolean isUnplugged() {
        return myTaskHierarchyItem == null;
    }

    /**
     * Returns the CustomColumnValues.
     *
     * @return The CustomColumnValues.
     */
    public CustomColumnsValues getCustomValues() {
        return customValues;
    }

    /**
     * @inheritDoc
     */
    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    /**
     * @inheritDoc
     */
    public boolean isCritical() {
        return this.critical;
    }

    // TODO: implementation of this method has no correlation with algorithms
    // recalculating schedules,
    // doesn't affect subtasks and supertasks. It is necessary to call this
    // method explicitly from other
    // parts of code to be sure that constraint fulfils
    //
    // Method GanttCalendar.newAdd() assumes that time unit is day
    public void applyThirdDateConstraint() {
        TaskLength length = getDuration();
        if (getThird() != null)
            switch (getThirdDateConstraint()) {
            case EARLIESTBEGIN:
                // TODO: TIME UNIT (assumption about days)
                if (getThird().after(getStart())) {
                    int difference = getThird().diff(getStart());
                    GanttCalendar _start = getStart().newAdd(difference);
                    GanttCalendar _end = getEnd().newAdd(difference);
                    setEnd(_end);
                    setStart(_start);
                    setDuration(length);
                }
                break;
            }
    }

    private TaskInfo myTaskInfo;

    public TaskInfo getTaskInfo() {
        return myTaskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        myTaskInfo = taskInfo;

    }

    public boolean isProjectTask() {
        return isProjectTask;
    }

    public void setProjectTask(boolean projectTask) {
        isProjectTask = projectTask;
    }

    private static final GPCalendar RESTLESS_CALENDAR = new AlwaysWorkingTimeCalendarImpl();

	/**
	 * 校验时间范围是否超过主项
	 * 
	 * @param end
	 *            结束时间(专项的)
	 * @param mainID
	 *            相关主项ID，如果为空，则取当前task相关主项ID
	 * @return
	 * 修改记录：参数取值不对，参数应该取当前工程项目对应的成本中心
	 */
    public boolean checkIsOutOfMain(Date end, String mainID) {
		ActivityCache cache = ActivityCache.getInstance();
		if (this instanceof KDTask) {
			KDTask task = (KDTask) this;
			if (task.getScheduleTaskInfo().get("totalSchedule") != null) {//总进度计划暂不处理
				return false;
			}
			
			String value = "0";
			String orgID = (String) task.getScheduleTaskInfo().get("projectCostCenterID");
			if(!StringUtils.isEmpty(orgID))
			   value = cache.getParam("FDCSH010", orgID);//从缓存取出参数值
			
			// 强管控，校验
			if ("1".equals(value)) {
				FDCScheduleTaskInfo main = ((FDCScheduleTaskInfo) task.getScheduleTaskInfo()).getDependMainTaskID();
				if(main == null){
					return false;
				}else{
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcID", main.getId()));
					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial is null"));
					filter.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS));
					try {
						/*
						 * 如果专项任务落在关联主项任务的时间范围外，给出提示
						 * 主专项关联采用的是srcID关联的，所以要找到最新版本主项的任务，判断其专项时间是否越界
						 */
						if (FDCScheduleTaskFactory.getRemoteInstance().exists(filter)) {
							FDCMsgBox.showWarning(getName() + " 的计划完成日期晚于相关主项任务的计划完成日期！");
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
    }

}
