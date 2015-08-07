package net.sourceforge.ganttproject.chart;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.GanttGraphicArea.MyChartModelImpl;
import net.sourceforge.ganttproject.calendar.CalendarFactory;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.chart.ChartModelBase.Offset;
import net.sourceforge.ganttproject.chart.GraphicPrimitiveContainer.Line;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.options.model.BooleanOption;
import net.sourceforge.ganttproject.gui.options.model.GPOption;
import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;
import net.sourceforge.ganttproject.task.TaskManagerImpl;
import net.sourceforge.ganttproject.time.TimeFrame;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.gregorian.FramerImpl;

import com.kingdee.eas.fdc.schedule.framework.ext.KDGPConstants;

/**
 * Created by IntelliJ IDEA.
 */
public class ChartGridImpl extends ChartRendererBase implements TimeUnitVisitor {
    private int myPosX;

    private TimeFrame myCurrentFrame;

    private boolean areUnitsAccepted;

    private TimeUnit myCurrentUnit;

    private Date myToday;

    private final BooleanOption myRedlineOption;
    private final BooleanOption myProjectDatesOption;
    
    private GPOptionGroup myOptions;
    
    private Date projectEnd = null;

    private Date projectStart = null;
    
    private FramerImpl myDayFramer = new FramerImpl(Calendar.DAY_OF_MONTH);
    public ChartGridImpl(ChartModelBase chartModel, final UIConfiguration projectConfig) {
        super(chartModel);
        myRedlineOption = projectConfig.getRedlineOption();
        myProjectDatesOption= projectConfig.getProjectBoundariesOption();
        myOptions = new ChartOptionGroup("ganttChartGridDetails", new GPOption[] {myRedlineOption, myProjectDatesOption}, chartModel.getOptionEventDispatcher());
        
    }

    GPOptionGroup getOptions() {
        return myOptions;
    }
    
    public void beforeProcessingTimeFrames() {
        getPrimitiveContainer().clear();
        myPosX = 0;
        myToday = myDayFramer.adjustLeft(CalendarFactory.newCalendar().getTime());
        
        projectStart = getChartModel().getTaskManager().getProjectStart();
        projectEnd = getChartModel().getTaskManager().getProjectEnd();
        
/*		if (projectEnd != null) {
			// 为了支持有效工期为1后可以正常显示项目结束时间,结束
			GregorianCalendar cal = new GregorianCalendar(projectEnd.getYear() + 1900, projectEnd.getMonth(), projectEnd.getDate());
			cal.add(Calendar.DATE, KDGPConstants.EFFECTTIMESBALANCE);
			projectEnd = ((TaskManagerImpl) getChartModel().getTaskManager()).findClosestWorkingTime(cal.getTime());
		}*/
    }

    public void startTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = timeFrame;
    }

    public void endTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = null;
    }

    public void startUnitLine(TimeUnit timeUnit) {
        if (timeUnit == myCurrentFrame.getBottomUnit()) {
            areUnitsAccepted = true;
            myCurrentUnit = timeUnit;
        }
    }

    public void endUnitLine(TimeUnit timeUnit) {
        areUnitsAccepted = false;
        myCurrentUnit = null;
    }

    public void nextTimeUnit(int unitIndex) {
        if (areUnitsAccepted) {
//            if (myRedlineOption.isChecked()
//                    && myCurrentFrame.getUnitStart(myCurrentUnit, unitIndex)
//                            .compareTo(myToday) <= 0
//                    && myCurrentFrame.getUnitFinish(myCurrentUnit, unitIndex)
//                            .compareTo(myToday) > 0) {
//
//            }
            Date unitStart = myCurrentFrame.getUnitStart(myCurrentUnit,
                    unitIndex);            
            DayTypeAlternance[] alternance = getChartModel()
                    .getDayTypeAlternance(myCurrentFrame, myCurrentUnit,
                            unitIndex);
            Offset[] offsets = getChartModel().calculateOffsets(myCurrentFrame, myCurrentUnit, unitStart, getChartModel().getTimeUnitStack().getDefaultTimeUnit(), getChartModel().getBottomUnitWidth());
            int myOffset=0;
            if(getChartModel().getBottomUnitWidth()==26||getChartModel().getBottomUnitWidth()==40||getChartModel().getBottomUnitWidth()==60){
            	myOffset=getChartModel().getBottomUnitWidth();
            }
//            myOffset=getChartModel().getBottomUnitWidth();
            if (myRedlineOption.isChecked()) {
//                drawDateLine(unitStart, unitIndex, myToday, offsets, Color.RED, 2+getChartModel().getBottomUnitWidth());
                int linePoint[] = drawDateLine(unitStart, unitIndex, myToday, offsets, Color.RED, myOffset);
                if(linePoint!=null){
                    if(getChartModel() instanceof MyChartModelImpl){
                    	MyChartModelImpl chartMode=(MyChartModelImpl)getChartModel();
                    	
                    	chartMode.setStartPoint(linePoint[0],linePoint[1]);
                    	chartMode.setEndPoint(linePoint[2],linePoint[3]);
                    }
                }/*else{
                	if(getChartModel() instanceof MyChartModelImpl){
    	            	MyChartModelImpl chartMode=(MyChartModelImpl)getChartModel();
    	            	chartMode.setStartPoint(-1,-1);
    	            	chartMode.setEndPoint(-1,-1);
                	}
                }*/
            }
            if (isProjectBoundariesOptionOn()) {
                drawDateLine(unitStart, unitIndex, projectStart, offsets, Color.BLUE, 0);
                drawDateLine(unitStart, unitIndex, projectEnd, offsets, Color.BLUE, 0+myOffset);
            }
            //int posX = myPosX;

//            float delta = (float) getChartModel().getBottomUnitWidth()
//                    / (float) alternance.length;
//            int width=0;
            int prevOffset = 0;
            //DayTypeAlternance next=null;
            for (int i = 0; i < alternance.length; i++) {
                DayTypeAlternance next = alternance[i];
                int alternanceEndOffset = getChartModel().getBottomUnitWidth();
                for (int j=0; j<offsets.length; j++) {
                    if (offsets[j].getOffsetEnd().equals(next.getEnd())) {
                        alternanceEndOffset = offsets[j].getOffsetPixels();
                        break;
                    }
                }
                //System.err.println("[ChartGridImpl] nextTimeUnit(): lternance="+next);
                //int width = (int)(next.getDuration().getLength(myCurrentUnit)*getChartModel().getBottomUnitWidth()); 
                
//                posX = (int) (myPosX + delta * i);
//                nextPosX = i < alternance.length - 1 ? (int) (myPosX + delta
//                        * (i + 1))
//                        : myPosX + getChartModel().getBottomUnitWidth();
                //width = nextPosX - posX;
                if (GPCalendar.DayType.WEEKEND == next.getDayType()) {
                    //System.err.println("[ChartGridImpl] nextTimeUnit(): prevOffset="+prevOffset+" endOffset="+alternanceEndOffset);
                    //System.err.println("[ChartGridImpl] nextTimeUnit(): end="+next.getEnd()+" offset="+alternanceEndOffset+" bottom width="+getChartModel().getBottomUnitWidth());
                    GraphicPrimitiveContainer.Rectangle r = getPrimitiveContainer()
                            .createRectangle(myPosX+prevOffset, 0, alternanceEndOffset-prevOffset, getHeight());
                    r.setBackgroundColor(getConfig()
                            .getHolidayTimeBackgroundColor());
                    r.setStyle("calendar.holiday");
                    getPrimitiveContainer().bind(r, next.getDayType());
                }

                if (GPCalendar.DayType.HOLIDAY == next.getDayType()) {
                    GraphicPrimitiveContainer.Rectangle r = getPrimitiveContainer()
                            .createRectangle(myPosX+prevOffset, 0, alternanceEndOffset, getHeight());
                    r.setBackgroundColor(getConfig()
                            .getPublicHolidayTimeBackgroundColor());
                    r.setStyle("calendar.holiday");
                    getPrimitiveContainer().bind(r, next.getDayType());
                }
                prevOffset = alternanceEndOffset;
                //posX = myPosX+width;                 
            }
            //
            myPosX += getChartModel().getBottomUnitWidth();
        }
    }

    public void afterProcessingTimeFrames() {
    }

    private int[] drawDateLine(Date unitStart, int unitIndex, Date lineDate, Offset[] offsets, Color color, int horizontalOffset) {
/*        if(lineDate==this.myToday && getChartModel() instanceof MyChartModelImpl&&horizontalOffset>getChartModel().getBottomUnitWidth()){
        	MyChartModelImpl chartMode=(MyChartModelImpl)getChartModel();
        	chartMode.setStartPoint(-1,-1);
        	chartMode.setEndPoint(-1,-1);
        }*/
    	
        int redLineOffset = -1;
        if (unitStart.before(lineDate) && myCurrentFrame.getUnitFinish(myCurrentUnit, unitIndex).after(lineDate)) {
            for (int i=0; i<offsets.length; i++) {
                if (offsets[i].getOffsetEnd().equals(lineDate)) {
                    redLineOffset = offsets[i].getOffsetPixels();
                    break;
                }
            }
        }
        else if (unitStart.equals(lineDate)) {
            redLineOffset=0;
        }
        if (redLineOffset>=0) {
            Line redLine = getPrimitiveContainer().createLine(myPosX + redLineOffset+horizontalOffset,
                    0, myPosX + redLineOffset+horizontalOffset, getHeight());
            redLine.setForegroundColor(color);
            
            return new int[]{myPosX + redLineOffset+horizontalOffset,0,myPosX + redLineOffset+horizontalOffset,getHeight()};
        }
        return null;
    }
    
    private boolean isProjectBoundariesOptionOn() {
        return myProjectDatesOption.isChecked();
    }
    
}
