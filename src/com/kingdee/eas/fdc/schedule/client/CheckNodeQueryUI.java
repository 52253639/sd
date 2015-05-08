/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.report.client.F7ScheduleReportDialog;
import com.kingdee.eas.fdc.schedule.report.client.ScheduleReportOrgQueryUI;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CheckNodeQueryUI extends AbstractCheckNodeQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(CheckNodeQueryUI.class);
	// 进度报告选择工程项目窗口
	private F7ScheduleReportDialog orgDialog = null;
	private int orgAndProjNameSize = 0; 
	private String[] orgAndProjnames = null; 
	private String[] orgAndProjIds = null;
	private int[] orgAndProjLevel = null;

    /**
     * output class constructor
     */
    public CheckNodeQueryUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public void onLoad() throws Exception {
		initTableColumn();
		super.onLoad();
		this.prmtQueryScope.setEditable(false);
		this.tblMain.setEditable(false);
		//默认开始日期和结束日期为空
		this.pkStartDate.setValue(null);
		this.pkEndDate.setValue(null);
		this.prmtQueryScope.setSelector(new ScheduleRptOrgPromptBox(new FilterInfo()));
	}
	
	public void selectOrgsScope_actionPerformed(ActionEvent e) throws Exception {
		super.selectOrgsScope_actionPerformed(e);
		tblMain.refresh();
		CheckNodeCollection checkNodeCol = new CheckNodeCollection();
		EntityViewInfo view = new EntityViewInfo();
		//按编码排序
		SorterItemCollection sorter  = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		sorter.add(si);
		view.setSorter(sorter);
		if(!view.isNull()){			
			try {
//				checkNodeCol = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeCollection(view);
				checkNodeCol = CheckNodeFactory.getRemoteInstance().getCheckNodeCollection(view);
			} catch (BOSException e1) {
				e1.printStackTrace();
				abort();
			}
		}
		//加载组织和项目数据
		for(int i = 0;i < orgAndProjNameSize; i++){
			if(orgAndProjnames != null && orgAndProjIds !=null ){
				IRow row = tblMain.addRow();
//				row.getCell("project").setValue(orgAndProjnames[i]);
				CellTreeNode treeNode = new CellTreeNode();
				treeNode.setValue(orgAndProjnames[i]);
				treeNode.setTreeLevel(orgAndProjLevel[i]);
				treeNode.isCollapse();
				treeNode.setHasChildren(true);
				treeNode.addClickListener(new NodeClickListener() {
					public void doClick(CellTreeNode source, ICell cell, int type) {
						tblMain.revalidate();
					}
				});
				row.getCell("project").setValue(treeNode);
				if (checkNodeCol.size() > 0) {
					this.tblMain.checkParsed();
					for(int j =0;j < checkNodeCol.size(); j++){
						BOSObjectType objectType = BOSUuid.read(orgAndProjIds[i]).getType();
						//如果是项目id，则插入考核节点任务的计划完成日期和实际完成日期
						if(objectType.toString().equals("F9E5E92B")){							
							//考核节点动态添加列
							String checkNodeName = checkNodeCol.get(j).getName();
//							String checkNodeId = checkNodeCol.get(j).getId().toString();
							String KeyHead = "checkNodeName" + j;	
							//对应任务选择的“考核节点”，所对应的考核节点与项目阶段关联设定—列表界面中相关记录为哪一种“项目阶段”，
							//实际完成日期取最新执行版
							IRowSet ActualEndDate= getEndAndActualEndDateByProjIdNG(orgAndProjIds[i],checkNodeName);
							//计划完成日期取最新考核版
							IRowSet EndDate= getEndAndActualEndDateByProjId(orgAndProjIds[i],checkNodeName);

							if(!(EndDate.size()>0)){
								EndDate= getEndAndActualEndDateByProjIdNG(orgAndProjIds[i],checkNodeName);
							}
							while(ActualEndDate.next() && EndDate.next()){								
								row.getCell(KeyHead + "id").setValue(checkNodeName);
								int valsForStart = 0;
								int valsForEnd = 0;
								
								if(this.pkStartDate.getValue()!= null && ActualEndDate.getDate(1) != null){
									valsForStart= days((Date)EndDate.getDate(1), (Date)this.pkStartDate.getValue());
								}
								if(this.pkEndDate.getValue()!= null && ActualEndDate.getDate(1) != null){
									valsForEnd= days((Date)EndDate.getDate(1), (Date)this.pkEndDate.getValue());
								}
								if(EndDate.getDate(1)!=null && valsForStart <= 0 && valsForEnd >= 0){										
									row.getCell(KeyHead + "end").setValue(new SimpleDateFormat("yyyy-MM-dd").format(EndDate.getDate(1)));
								}
								if(ActualEndDate.getDate(2)!=null && valsForStart <= 0 && valsForEnd >= 0){									
									row.getCell(KeyHead + "actualEndDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(ActualEndDate.getDate(2)));
								}
								if(ActualEndDate.getDate(3)!=null){																		
									row.getCell(KeyHead + "checkDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(ActualEndDate.getDate(3)));
								}
								int vals = 0;
								//这里定位阶段取考核日期，基本都是按时完成哦，，，
//								if(row.getCell(KeyHead + "checkDate").getValue() != null){
//									vals = days((Date)endAndActualEndDate.getDate(3) , (Date)endAndActualEndDate.getDate(1));
//								}else{
								if(row.getCell(KeyHead + "actualEndDate").getValue() != null){
									vals = days((Date)ActualEndDate.getDate(2), (Date)EndDate.getDate(1));
								}
								//延时完成
								if (row.getCell(KeyHead + "actualEndDate").getValue() != null && row.getCell(KeyHead + "checkDate").getValue() != null && vals < 0 ) {
									row.getCell(KeyHead + "actualEndDate").getStyleAttributes().setBackground(Color.ORANGE);
//								//延期15天内完成
//								if (row.getCell(KeyHead + "actualEndDate").getValue() != null && row.getCell(KeyHead + "checkDate").getValue() != null && vals < 0 && vals >= -15) {
//									row.getCell(KeyHead + "actualEndDate").getStyleAttributes().setBackground(Color.ORANGE);
//								//延期15天外完成	
//								}else if(row.getCell(KeyHead + "actualEndDate").getValue() != null && row.getCell(KeyHead + "checkDate").getValue() != null && vals < -15){
//									row.getCell(KeyHead + "actualEndDate").getStyleAttributes().setBackground(Color.RED);
								//提前或按期完成
								}else if(row.getCell(KeyHead + "actualEndDate").getValue() != null && row.getCell(KeyHead + "checkDate").getValue() != null && vals >= 0){
									row.getCell(KeyHead + "actualEndDate").getStyleAttributes().setBackground(Color.GREEN);
								}
							}
						}
					}
				}				
			}
		}
		//加载汇总数据，如果有组织和项目才加载
		if(orgAndProjnames != null && orgAndProjIds !=null ){	
//			IRow rowSplitLine= tblMain.addRow();
//			rowSplitLine.getCell("project").setValue("...");
//			IRow rowHLLSplitLine= tblMain.addRow();
//			rowHLLSplitLine.getCell("project").setValue("...");
			IRow rowEnd = tblMain.addRow();
			rowEnd.getCell("project").setValue("合计—计划完成");
			IRow rowOnTime = tblMain.addRow();
			rowOnTime.getCell("project").setValue("合计—按时完成");
//			IRow rowLateIn15= tblMain.addRow();
//			rowLateIn15.getCell("project").setValue("合计—延迟15天内完成");
//			IRow rowLateOut15= tblMain.addRow();
//			rowLateOut15.getCell("project").setValue("合计—延迟15天外完成");
			IRow rowLate = tblMain.addRow();
			rowLate.getCell("project").setValue("合计—延迟完成");
			IRow rowOnTimeRate= tblMain.addRow();
			rowOnTimeRate.getCell("project").setValue("按时完成率");
			IRow rowLateRate= tblMain.addRow();
			rowLateRate.getCell("project").setValue("延迟完成率");
			KDTMergeManager tblMerge = this.tblMain.getMergeManager();
			//先合计值到KeyHead + "end"再合并,这地方
			
			for(int i =0;i < checkNodeCol.size(); i++){
				String KeyHead = "checkNodeName" + i;
				int endCounts = 0;
				int onTimeCounts = 0;
//				int lateIn15Counts = 0;
//				int lateOut15Counts = 0;
				int lateCounts = 0;
				String onTimeRate=  "0%";
				String lateRate = "0%";
				for(int j = 0; j < rowEnd.getRowIndex(); j++){
					//合计—计划完成:显示当前报表中，“计划完成日期”不为空的单元格个数。
					if(tblMain.getCell(j, KeyHead + "end").getValue() != null){
						endCounts++;
					}
					//合计—按时完成:显示当前报表中，“实际完成日期”不为空，且完成状态为“按时完成”（绿色底色）的单元格个数。
					if(tblMain.getCell(j, KeyHead + "actualEndDate").getValue() != null && tblMain.getCell(j, KeyHead + "actualEndDate").getStyleAttributes().getBackground().equals(Color.GREEN)){
						onTimeCounts++;
					}
					//合计—延迟内完成:显示当前报表中，“实际完成日期”不为空，且完成状态为（橙色底色）的单元格个数。
					if(tblMain.getCell(j, KeyHead + "actualEndDate").getValue() != null && tblMain.getCell(j, KeyHead + "actualEndDate").getStyleAttributes().getBackground().equals(Color.ORANGE)){
						lateCounts++;
					}
//					//合计—延迟15天内完成:显示当前报表中，“实际完成日期”不为空，且完成状态为“延迟15天内完成”（橙色底色）的单元格个数。
//					if(tblMain.getCell(j, KeyHead + "actualEndDate").getValue() != null && tblMain.getCell(j, KeyHead + "actualEndDate").getStyleAttributes().getBackground().equals(Color.ORANGE)){
//						lateIn15Counts++;
//					}
//					//合计—延迟15天外完成:显示当前报表中，“实际完成日期”不为空，且完成状态为“延迟15天外完成”（红色底色）的单元格个数。
//					if(tblMain.getCell(j, KeyHead + "actualEndDate").getValue() != null && tblMain.getCell(j, KeyHead + "actualEndDate").getStyleAttributes().getBackground().equals(Color.RED)){
//						lateOut15Counts++;
//					}
				}
				if(endCounts>0){
					//按时完成率:自动计算显示：＝“合计—按时完成”／“合计—计划完成”ｘ100%，保留两位小数。		
					 java.text.DecimalFormat df=new java.text.DecimalFormat("0.00"); 
					onTimeRate = df.format(new BigDecimal(((float)onTimeCounts / (float)endCounts)).multiply(new BigDecimal(100))) + "%";					
//					lateRate = new BigDecimal((float)((onTimeCounts + lateCounts) / (float)endCounts )).multiply(new BigDecimal(100)).setScale(0) + "%";;					
					//延迟完成率:自动计算显示：＝“合计—延迟完成”／“合计—计划完成”ｘ100%，保留两位小数。
					lateRate = df.format(new BigDecimal(((float)lateCounts / (float) endCounts)).multiply(new BigDecimal(100))) + "%";;		
					
				}
				rowEnd.getCell(KeyHead + "end").setValue(endCounts);
				rowOnTime.getCell(KeyHead + "end").setValue(onTimeCounts);
//				rowLateIn15.getCell(KeyHead + "end").setValue(lateIn15Counts);
//				rowLateOut15.getCell(KeyHead + "end").setValue(lateOut15Counts);
				rowLate.getCell(KeyHead + "end").setValue(lateCounts);
				rowOnTimeRate.getCell(KeyHead + "end").setValue(onTimeRate);
				rowLateRate.getCell(KeyHead + "end").setValue(lateRate);
			}
			//合并单元格
			int j = rowEnd.getRowIndex();
			while(j< rowLateRate.getRowIndex() + 1){
				for(int i =0;i < checkNodeCol.size() ; i++){
					//合并单元格	
					tblMerge.mergeBlock(j, 1 + (i * 4), j, 1 + (i * 4) + 3);
					tblMain.getCell(j, i * 4 + 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
				}
				j++;
			}
		}
	}
	/**
	 * endDate-startDate
	 */
	public static int days(Date startDate,Date endDate){
		long a = truncateDate(endDate).getTime();
		long b = truncateDate(startDate).getTime();
		long c = a - b;
		long y = c/(1000*60*60*24);
		return Integer.parseInt(y + "");
	}
	
	public static Date truncateDate(Date dt)
    {
        if(dt == null)
        {
            return null;
        } else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
            return new Date((cal.getTimeInMillis() / 1000L) * 1000L);
        }
    }
	
	/**
	 * 格式化F7选择数据
	 */
	protected void prmtQueryScope_dataChanged(DataChangeEvent e)
			throws Exception {
//		super.prmtQueryScope_dataChanged(e);
		tblMain.refresh();
		HashMap orgHashMap=new HashMap();
		StringBuffer sb = new StringBuffer(100);
		if(e.getNewValue() != null && (e.getNewValue() instanceof HashMap)){			
			orgHashMap = (HashMap) e.getNewValue();
			Set projectIds = new HashSet();
			Set projectNames = new HashSet();
			Set set = orgHashMap.entrySet();
			
			List<String>  selectedValueForPublicList = ScheduleReportOrgQueryUI.getselectedValueForPublicList();
			//		ScheduleReportOrgQueryUI.setselectedValueForPublicListClear();
			for(Iterator it=set.iterator();it.hasNext();){
				Entry entry = (Entry) it.next();
				Set enset = ((Map)entry.getValue()).entrySet();
				for(Iterator iter = enset.iterator();iter.hasNext();){
					Entry innerEntry = (Entry)iter.next();
					String innerEntryKey = innerEntry.getKey().toString();
					String innerEntryValue = innerEntry.getValue().toString();
					if(innerEntryKey.equals("x_axis_name")){
						projectNames.add(innerEntry.getValue().toString());					
					}
//				else if(innerEntryKey.equals("sorted_select_org_pro")){
//				}else{						
//					projectIds.add(innerEntry.getKey().toString());
//					projectNames.add(innerEntry.getValue().toString());
//				}
					for(int j = 0;j < selectedValueForPublicList.size(); j++){				
						projectNames.add(selectedValueForPublicList.get(j));			
					}
				}
				IRowSet sortedOrgAndProjDataSet = getSortedOrgAndProjNameDataSet(projectNames);
				if(!(sortedOrgAndProjDataSet.size()>0)){
					return ;
				}
				this.orgAndProjNameSize = sortedOrgAndProjDataSet.size();
				int i =0;
				this.orgAndProjnames = new String[orgAndProjNameSize];
				this.orgAndProjIds = new String[orgAndProjNameSize];
				this.orgAndProjLevel = new int[orgAndProjNameSize];
				while(sortedOrgAndProjDataSet.next()){
					this.orgAndProjIds[i]=sortedOrgAndProjDataSet.getString(1);
					this.orgAndProjnames[i]=sortedOrgAndProjDataSet.getString(2);	
					this.orgAndProjLevel[i]=sortedOrgAndProjDataSet.getInt(3);	
					i++;
				}
				StringBuffer StrOrgAndProjnames = new StringBuffer();	
				if(i>0){				
					for(int j=0;j < i;j++){
						if(i-1!=j){
							StrOrgAndProjnames.append(orgAndProjnames[j]).append(";");				
						}else{
							StrOrgAndProjnames.append(orgAndProjnames[j]);
						}
					}
					this.prmtQueryScope.setData(StrOrgAndProjnames.toString());					
				}
//			for(int j = 0;j < selectedValueForPublicList.size(); j++){
//				if(j != selectedValueForPublicList.size()-1){					
//					StrOrgAndProjnames.append(selectedValueForPublicList.get(j)).append(";");	
//				}else{
//					StrOrgAndProjnames.append(selectedValueForPublicList.get(j));
//				}				
//			}
//			this.prmtQueryScope.setData(StrOrgAndProjnames.toString());	
		}
		}
	}
	
	/**
	 * 初始化表列
	 */
	protected void initTableColumn() {
//		GlobalTaskNodeCollection checkNodeCol = new GlobalTaskNodeCollection();
		CheckNodeCollection checkNodeCol = new CheckNodeCollection();

		EntityViewInfo view = new EntityViewInfo();
		//按编码排序
		SorterItemCollection sorter  = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		sorter.add(si);
		view.setSorter(sorter);
		KDTMergeManager tblMerge = this.tblMain.getHeadMergeManager();
		if(!view.isNull()){			
			try {
//				checkNodeCol = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeCollection(view);
				checkNodeCol = CheckNodeFactory.getRemoteInstance().getCheckNodeCollection(view);

			} catch (BOSException e) {
				e.printStackTrace();
				abort();
			}
		}
		if (checkNodeCol.size() > 0) {
			int index = 0;
			this.tblMain.checkParsed();
	
			this.tblMain.getColumn("project").setWidth(150);
			IRow ckHead0 = this.tblMain.getHeadRow(0);
			IRow ckHead1 = this.tblMain.getHeadRow(1);
			for(int i =0;i < checkNodeCol.size(); i++){
				//考核节点动态添加列
				String checkNodeName = checkNodeCol.get(i).getName();
				String KeyHead = "checkNodeName" + i;
				IColumn col = this.tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				ckHead0.getCell(index).setValue(checkNodeName);
				ckHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				//考核日期，，，
				col = this.tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "checkDate");
				col.getStyleAttributes().setHided(true);
				
				col = this.tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "end");
				ckHead1.getCell(index).setValue("计划完成日期");
				
				
				col = this.tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "actualEndDate");
				ckHead1.getCell(index).setValue("实际完成日期");
				tblMerge.mergeBlock(0, 1 + (i * 4), 0, 1 + (i * 4) + 3);
			}
		}
	}
	
	/**
	 *
	 * 获取排好序的项目id
	 * @throws BOSException 
	 */
	public IRowSet getSortedProjectDataSet(Set projectIds) throws BOSException{
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fname_L2,fid,flongnumber "); 
			sql.appendSql(" from T_FDC_CurProject "); 
			sql.appendSql(" where fid is not null  ");
			sql.appendFilter("fid", projectIds,CompareType.INCLUDE );
			sql.appendSql(" ORDER BY  ");
			sql.appendSql(" flongnumber ");
			IRowSet rs = sql.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	
	/**
	 *
	 * 获取排好序的组织id
	 * @throws BOSException 
	 */
	public IRowSet getSortedOrgDataSet(Set projectIds) throws BOSException{
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select distinct baseunit.fid,baseunit.fname_l2,baseunit.flongnumber "); 
			sql.appendSql(" from T_org_baseunit baseunit ");
			sql.appendSql(" left outer join ");
			sql.appendSql(" T_FDC_CurProject project ");
			sql.appendSql(" on baseunit.fid=project.ffullorgunit ");
			sql.appendSql(" where project.fid is not null  ");
			sql.appendFilter("project.fid", projectIds,CompareType.INCLUDE );
			sql.appendSql(" ORDER BY  ");
			sql.appendSql(" baseunit.flongnumber ");
			IRowSet rs = sql.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	
	/**
	 *
	 * 获取排好序的组织和项目id和名称
	 * @throws BOSException 
	 */
	public IRowSet getSortedOrgAndProjNameDataSet(Set projectAndOrgNames) throws BOSException{
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select frelateorgid,fname_l2,flevel "); 
			sql.appendSql(" from T_SCH_ScheduleReportOrg ");
			sql.appendSql(" where fid is not null  ");
			sql.appendFilter("fname_l2", projectAndOrgNames,CompareType.INCLUDE );
//			sql.appendSql(" ORDER BY  ");
//			sql.appendSql(" flongnumber ");
			IRowSet rs = sql.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	
	/**
	 *对应任务选择的“考核节点”，所对应的考核节点与项目阶段关联设定—列表界面中相关记录为哪一种“项目阶段”，
	 *则取该任务在对应“项目阶段”、“考核版”的“计划完成日期”；（即“考核日期”）
	 * 获取排好序的组织和项目id和名称
	 * @throws BOSException 
	 */
	public IRowSet getEndAndActualEndDateByProjId(String projectId, String checkNodeName) throws BOSException{
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select top 1 task.fend,task.factualEndDate,task.fcheckDate "); 
			sql.appendSql(" from T_SCH_FDCScheduleTask task ");
			sql.appendSql(" left outer join t_sch_fdcschedule schedule  ");
			sql.appendSql(" on  ");
			sql.appendSql(" task.fscheduleid=schedule.fid  ");
//			sql.appendSql(" left outer join T_SCH_GlobalTaskNode taskNode ");
			sql.appendSql(" left outer join T_SCH_CheckNode taskNode ");
			sql.appendSql(" on  ");
			sql.appendSql(" taskNode.FNAME_L2=task.FNAME_L2  ");
			sql.appendSql(" where schedule.fprojectid=? ");
			sql.addParam(projectId);
			//是考核节点
			sql.appendSql(" and task.fischecknode=1  ");
			//考核版是设置的
			sql.appendSql(" and schedule.FISCHECKVERSION=1  ");
			sql.appendSql(" and taskNode.FNAME_L2=? ");
			sql.addParam(checkNodeName);
//				sql.appendSql(" and schedule.FProjectStep= ");
//				sql.appendSql(" (select fprojectStep from T_SCH_GlobalTaskNode where fid=? ) ");
//				sql.addParam(CheckNodeId);
//				sql.appendSql(" and schedule.FProjectVersionType='01' ");
//			sql.appendSql(" order by schedule.FVersion desc ");
			sql.appendSql("  and schedule.fisLatestVer=1 ");

			sql.appendSql("  ");
			IRowSet rs = sql.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	/**
	 * 若对应考核节点没有上述要求的考核版本，则取最新考核版本的“计划完成日期”。
	 * 获取排好序的组织和项目id和名称
	 * @throws BOSException 
	 */
	public IRowSet getEndAndActualEndDateByProjIdNG(String projectId, String checkNodeName) throws BOSException{
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select top 1 task.fend,task.factualEndDate,task.fcheckDate "); 
			sql.appendSql(" from T_SCH_FDCScheduleTask task ");
			sql.appendSql(" left outer join t_sch_fdcschedule schedule  ");
			sql.appendSql(" on  ");
			sql.appendSql(" task.fscheduleid=schedule.fid  ");
//			sql.appendSql(" left outer join T_SCH_GlobalTaskNode taskNode ");
			sql.appendSql(" left outer join T_SCH_CheckNode taskNode ");
			sql.appendSql(" on  ");
			sql.appendSql(" taskNode.FNAME_L2=task.FNAME_L2  ");
			sql.appendSql(" where schedule.fprojectid=? ");
			sql.addParam(projectId);
			//TODO 是考核节点
			sql.appendSql(" and task.fischecknode=1  ");
			sql.appendSql(" and taskNode.FNAME_L2=?  ");
			sql.addParam(checkNodeName);
//				sql.appendSql(" and schedule.FProjectVersionType='01' ");
//			sql.appendSql(" order by schedule.FVersion desc ");
			sql.appendSql("  and schedule.fisLatestVer=1 ");
			sql.appendSql("  ");
			IRowSet rs = sql.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
}