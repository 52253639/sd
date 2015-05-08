/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class TasksIn2MStoneListUI extends AbstractTasksIn2MStoneListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TasksIn2MStoneListUI.class);
    
    /**
     * output class constructor
     */
    public TasksIn2MStoneListUI() throws Exception
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
		super.onLoad();
		execQuery();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", getUIContext().get("fromTask"), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", getUIContext().get("toTask"), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("project.id", getUIContext().get("project"), CompareType.EQUALS));
//		SorterItemCollection sorter  = new SorterItemCollection();
//		SorterItemInfo si = new SorterItemInfo("longNumber");
//		sorter.add(si);
//		view.setSorter(sorter);
		view.setFilter(filter);
		
        return super.getQueryExecutor(queryPK, view);
    }
    
	protected void execQuery() {
		try {
	   	    super.execQuery();
			initStateCell();
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			handUIException(e);
		}
	}
	//TODO 好复杂，先搬过来。。。保持一致。。。
	/*
	 * 展示的时候，循环表格行，根据该隐藏列的值，设置显示的stateLogo列的展示方式。<br>
	 * 
	 * 注意：<br> 1、打钩的单元格需要设置字体为粗体，否则钩太细，画圈的则不需要；<br>
	 * 2、颜色未使用标准的Color.Green和Color.Orange，而使用了新构建的近似颜色代替，<br>
	 * 是因为纯色太亮，容易刺眼，大量使用时需减少亮度。
	 */
	public void initStateCell() throws BOSException {
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// 勾
		String achieve = EASResource.getString(rsPath, "achieve");
		// 圈
		String pending = EASResource.getString(rsPath, "pending");
		// 红
		Color red = new Color(245, 0, 0);
		// 绿
		Color green = new Color(10, 220, 10);
		// 橙
		Color orange = new Color(220, 180, 0);
		boolean isNeedEvaluation =  getUIContext().get("isNeedEvaluation").equals(true);
		// 取出当前所有里程碑任务的SRCID
		Set<String> srcIDs = new HashSet<String>();
		Map evaluationMap = null;
		IRow row = null;
		if (isNeedEvaluation) {
			evaluationMap = new HashMap();
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				row = tblMain.getRow(i);
				if (row.getCell("srcid").getValue() != null) {
					srcIDs.add(row.getCell("srcid").getValue() + "");
				}
			}

			EntityViewInfo view = new EntityViewInfo();
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("evaluationResult.evaluationPass");
			view.setSelector(sic);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", srcIDs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("evaluationType", TaskEvaluationTypeEnum.SCHEDULE));
			
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo si = new SorterItemInfo("lastupdatetime");
			si.setSortType(SortType.DESCEND);
			view.setSorter(sorter);
			view.setFilter(filter);

			try {
				TaskEvaluationBillCollection cols = TaskEvaluationBillFactory.getRemoteInstance().getTaskEvaluationBillCollection(view);
				TaskEvaluationBillInfo evaInfo = null;
				TaskEvaluationBillInfo otherInfo = null;
				for (int i = 0; i < cols.size(); i++) {
					evaInfo = cols.get(i);
					// if (!evaluationMap.containsKey(bill.getSrcRelateTask()))
					// evaluationMap.put(bill.getSrcRelateTask(),
					// bill.getEvaluationResult().isEvaluationPass());
					
					if (evaInfo.getEvaluationResult().isEvaluationPass()) {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) == null) {
							evaluationMap.put(evaInfo.getSrcRelateTask(), evaInfo);
						}
					} else {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) != null) {
							otherInfo = (TaskEvaluationBillInfo) evaluationMap.get(evaInfo.getSrcRelateTask());
							if (DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(), otherInfo.getLastUpdateTime()) < 0) {
								evaluationMap.remove(evaInfo.getSrcRelateTask());
								//evaMap.put(evaInfo.getSrcRelateTask(),evaInfo)
								// ;
							}

						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}

		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			if (row != null) {
				if (null == row.getCell("complete").getValue()) {
					row.getCell("complete").setValue("0");
				}
				int state = ((Integer) row.getCell("hiddenStatus").getValue()).intValue();
				int complete = new BigDecimal(row.getCell("complete").getValue().toString()).intValue();
				String srcId = "";
				String evaluationType = "";
				if (null != row.getCell("srcid").getValue()) {
					srcId = row.getCell("srcid").getValue().toString();
				}

			
				/*
				 * 参数启用时，完工百分比等于100%，并且经过进度评价，才能确认任务的完成状态 延时未完成对于两者来说是一样的
				 * 1、完工百分比小于100%，并且计划完成日期在当前日期之前 2、完工百分比等于100%是需要根据参数来区别对待
				 * a、完工并且已进行了进度评价 b、完工未进行进度评价
				 */

				row.getCell("status").getStyleAttributes().setBold(true);
				if (state == 3) {
					Date end = (Date) row.getCell("end").getValue();
					Date currDate = FDCDateHelper.getServerTimeStamp();
					if (DateUtils.compareUpToDay(currDate, end) > 0) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(3);
					} else {
						row.getCell("hiddenStatus").setValue(2);
					}
					continue;
				}
				
				
				if (isNeedEvaluation) {// 需要进行评价
					if (evaluationMap.containsKey(srcId) && complete == 100) {
						boolean isPass = ((TaskEvaluationBillInfo) evaluationMap.get(srcId)).getEvaluationResult().isEvaluationPass();
						if (isPass) {
							if (state == 0) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
							if (state == 1) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(red);
								row.getCell("hiddenStatus").setValue(new Integer(1));
								// update by libing
								// 同一天，按照时分秒来精确的话，可能存在问题，这里手动改一下
								if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
									int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
									if (finalstatus <= 0) {
										row.getCell("status").getStyleAttributes().setFontColor(green);
										row.getCell("hiddenStatus").setValue(new Integer(0));
									}
								}
							}
						} else {
							row.getCell("status").setValue(pending);
							row.getCell("status").getStyleAttributes().setFontColor(orange);
						}
						
					} else if (!evaluationMap.containsKey(srcId) && complete == 100) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(orange);
						// row.getCell("hiddenStatus").setValue(new Integer(2));
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
							if (finalstatus <= 0) {
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					} else if (complete < 100) {
						
					}

				} else {// 不需要进行评价
					if (state == 0) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(green);
						row.getCell("hiddenStatus").setValue(new Integer(0));
					}
					if (state == 1) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(new Integer(1));
						// update by libing 同一天，按照时分秒来精确的话，可能存在问题，这里手动改一下
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
							if (finalstatus <= 0) {
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					}
				}
			}
		}
	}
	
	protected FDCDataBaseInfo getBaseDataInfo() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
	}

}