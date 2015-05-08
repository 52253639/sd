/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		主(专)项计划编制与调整--<导入计划模板管理
 *		
 * @author		罗小龙
 * @version		EAS7.0		
 * @createDate	2011-08-20 
 * @see
 */
public class REScheduleImportPlanTemplateListUI extends AbstractREScheduleImportPlanTemplateListUI
{
    /** 缺省字符串 */
	private static final long serialVersionUID = 1L;
	
	/** 日志文件 */
	private static final Logger logger = CoreUIObject.getLogger(REScheduleImportPlanTemplateListUI.class);
	
	/** 名称列 */
	private static final String COL_NAME = "name";
	
	/** 长编码列 */
	private static final String COL_LONGNUMBER = "longNumber";
	
	/** 主键列 */
	private static final String COL_ID = "id";
	
	/** 级次列 */
	private static final String COL_LEVEL = "level";
	
	/** 是否是子节点列 */
	private static final String COL_ISLEAF = "isLeaf";
	
	/** 是否是子节点列 */
	private static final String COL_PREDECESSOR_DESC = "predecessorDesc";
	
	/** 是否是子节点列 */
	private static final String COL_TASK_TYPE = "taskType";
	
	/** 是否是子节点列 */
	private static final String COL_BUSINESSTYPE_DESC = "businessTypeDesc";
	
	/** 是否是子节点列 */
	private static final String COL_REFERENCEDAY = "referenceDay";
	
    
    /**
     * output class constructor
     */
    public REScheduleImportPlanTemplateListUI() throws Exception
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

    protected ICoreBase getBizInterface() throws Exception {
    	return RESchTemplateTaskFactory.getRemoteInstance();
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	
    	/* 设置确认、取消两按扭不显示 */
    	this.btnConfirm.setEnabled(false);
    	this.btnClose.setEnabled(false);
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	
    	/* 设置工具栏不显示 */
    	this.toolBar.setVisible(false);
    	this.toolBar.setSize(0, 0);
    	getUICustomToolBar().removeAll();
    	
    	/* 设置表格列显示宽度 */
    	this.tblMain.getColumn(COL_NAME).setWidth(200);
    	
    	/* 设置弹出计划模板树 */
//    	RESchTemplateTreeSelector selector = new RESchTemplateTreeSelector();
//		this.prmpPlanTemplate.setSelector(selector);
//		
//		/* 为计划模板F7添加监听 */
//		this.prmpPlanTemplate.addDataChangeListener(new DataChangeListener(){
//			public void dataChanged(DataChangeEvent arg0) {
//				GetAllData();
//			}
//		});
    	Object ownerObj = getUIContext().get("Owner");
		if(null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(ownerObj instanceof MainScheduleEditUI){
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.MAINPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","主项计划模板",CompareType.NOTEQUALS));
			}else{
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.OTHERPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","专项计划模板",CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			this.prmtCatagory.setEntityViewInfo(view);
		}
		/* 清空表数据，只有在选择树节点的时候才填充数据 */
		this.tblMain.removeRows(false);
		
		/* 设置确认、取消两按钮不灰显 */
		this.btnConfirm.setEnabled(true);
		this.btnClose.setEnabled(true);
    }
    /**
     * output prmtCatagory_dataChanged method
     */
    protected void prmtCatagory_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	this.combTemplate.removeAllItems();
//    	this.combTemplate
        super.prmtCatagory_dataChanged(e);
        if(e.getNewValue()!=null && e.getNewValue() instanceof RESchTemplateCatagoryInfo){
        	RESchTemplateCollection cols=getRESchTemplate(((RESchTemplateCatagoryInfo)e.getNewValue()).getId().toString());
        	for(int i =0;i < cols.size();i++){
//        		this.combTemplate.addItem((cols.get(i).getName()));
        		this.combTemplate.addItem(cols.get(i));
        	}        	
        }
    }
    public  RESchTemplateCollection getRESchTemplate(String catagory) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("name");
    	sic.add("number");
    	sic.add("state");
    	sic.add("id");
    	sic.add("orgUnit.id");
    	sic.add("orgUnit.name");
    	sic.add("orgUnit.number");
    	sic.add("orgUnit.longNumber");
		view.setSelector(sic);
    	FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo org = SysContext.getSysContext().getCurrentFIUnit();
		String[] numbers = org.getLongNumber().split("!");
		Set orgSet = new HashSet();
		for (int i = 0; i < numbers.length; i++) {
			orgSet.add(numbers[i]);
		}
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", orgSet, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("catagory.id",catagory));
    	
    	/* modified by zhaoqin for R140923-0183 on 2015/01/14 */
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);
		view.setFilter(filter);
		view.setSorter(sorter);    

		RESchTemplateCollection cols = null;
		try {
			cols = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
    /**
     * output combTemplate_itemStateChanged method
     */
    protected void combTemplate_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	GetAllData();
    }
	/**
	 * @discription  <根据选中树的节点动态改变表里面的数据>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/20> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	 private void GetAllData() {
		/* 如果选中节点无值，则程序终止 */
//		if (null == this.prmpPlanTemplate.getValue() || "".equals(this.prmpPlanTemplate.getValue().toString().trim())) {
//			return;
//		}
		if (null == this.prmtCatagory.getValue() || "".equals(this.prmtCatagory.getValue().toString().trim())) {
			return;
		}
		if (null == this.combTemplate.getSelectedItem() || "".equals(this.combTemplate.getSelectedItem().toString().trim())) {
			return;
		}
//		if (prmpPlanTemplate.getValue() instanceof String) {
//			return;
//		}
		if (prmtCatagory.getValue() instanceof String) {
			return;
		}
		if (combTemplate.getSelectedItem() instanceof String) {
			return;
		}
//		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.prmpPlanTemplate.getValue();
		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.combTemplate.getSelectedItem();

		List taskList = new ArrayList();
		if (null != tmpInfo) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
			view.getSelector().add(new SelectorItemInfo("businessType.id"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.id"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.name"));
			view.getSelector().add(new SelectorItemInfo("businessType.bizType.number"));
			
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("template.id", tmpInfo.getId().toString()));
			view.setFilter(filter);
			/* 按长编码排序 */
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo seq = new SorterItemInfo(COL_LONGNUMBER);
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			seq = new SorterItemInfo("businessType.bizType.number");
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			view.setSorter(sorter);
			RESchTemplateTaskCollection collection = new RESchTemplateTaskCollection();
			try {
				collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
				RESchTemplateTaskInfo taskInfo = new RESchTemplateTaskInfo();
				for (int i = 0; i < collection.size(); i++) {
					taskInfo = collection.get(i);
					taskList.add(taskInfo);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}

		}
		setTblMainData(taskList);
	 }
	 
	 private String getBizTypeDesc(RESchTemplateTaskBizTypeCollection cols) {
		StringBuffer str = new StringBuffer();
		RESchTemplateTaskBizTypeInfo info = null;
		int size = cols.size();
		for (int i = 0; i < size; i++) {
			info = cols.get(i);
			if (info.getBizType() != null) {
				str.append(info.getBizType().getName());
				if (i < size - 1) {
					str.append(",");
				}
			}

		}
		if (!StringUtils.isEmpty(str.toString())) {
			return str.toString();
		}
		return null;
	}
	 
	 /**
	  * @discription  <根据选择树节点的不同，动态为表赋值>
	  * @author       <Xiaolong Luo>
	  * @createDate   <2011/08/20> <p>
	  * @param        <空>
	  * @return       <返回值描述>
	  * 
	  * modifier      <空> <p>
	  * modifyDate    <空> <p>
	  * modifyInfo	 <空> <p>
	  * @see          <相关的类>
	  */
	 private void setTblMainData(List taskList) {
		/* 清空表数据 */
		tblMain.removeRows(false);
		tblMain.addRows(taskList.size());
		tblMain.setRowCount(taskList.size() + 1);
		/* 循环为表的各列赋值 */
		for (int k = 0; k < taskList.size(); k ++) {
			RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) taskList.get(k);
			IRow row = this.tblMain.getRow(k);
			/* 主键列 */
			row.getCell(COL_ID).setValue(taskInfo.getId().toString());
			/* 任务名称列 */
			row.getCell(COL_NAME).setValue(taskInfo.getName());
			/* 前置任务列 */
			row.getCell(COL_PREDECESSOR_DESC).setValue(taskInfo.getPredecessorDesc());
			/* 类型名称列 */
			row.getCell(COL_TASK_TYPE).setValue(taskInfo.getTaskType());
			/* 业务类型列 */
			String bizDesc = getBizTypeDesc(taskInfo.getBusinessType());
			if (bizDesc != null) {
				row.getCell(COL_BUSINESSTYPE_DESC).setValue(bizDesc);
			}
		
			/* 参考工期列 */
			row.getCell(COL_REFERENCEDAY).setValue(taskInfo.getReferenceDay() + "");
			/* 是否是子节点列 */
			row.getCell(COL_ISLEAF).setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
			/* 级次列 */
			row.getCell(COL_LEVEL).setValue(taskInfo.getLevel() + "");
			/* 长编码列 */
			row.getCell(COL_LONGNUMBER).setValue(taskInfo.getLongNumber());
			CellTreeNode treeNode = new CellTreeNode();
			
			/* 显示的值，此处是‘任务名称’字符串 */
			treeNode.setValue(taskInfo.getName());
			
			/* 级次，从0开始，此处为任务的树级次 */
			treeNode.setTreeLevel(taskInfo.getLevel());
			
			treeNode.setHasChildren(!taskInfo.isIsLeaf());
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell(COL_NAME).getStyleAttributes().setLocked(false);
			
			/* 设置前面构建的单元格树到表 */
			row.getCell(COL_NAME).setValue(treeNode);

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell(COL_NAME).setValue("所有任务");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
	}
	 
   /**
	* @discription  <确认导入模板>
	* @author       <Xiaolong Luo>
	* @createDate   <2011/08/20> <p>
	* @param        <空>
	* @return       <返回值描述>
	* 
	* modifier      <空> <p>
	* modifyDate    <空> <p>
	* modifyInfo	 <空> <p>
	* @see          <相关的类>
	*/	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
//		Object templateObj = this.prmpPlanTemplate.getValue();
		Object templateObj = this.combTemplate.getSelectedItem();
		if(null == templateObj){
			FDCMsgBox.showInfo("请选择计划模板！");
			return ;
		}
		
		if(!(templateObj instanceof RESchTemplateInfo)){
			FDCMsgBox.showInfo("选择的模板类型不正确！");
			return ;
		}
		
		RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)templateObj;
		FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI)this.getUIContext().get("Owner");
		Date planStateDate = new Date();
		if(null != this.dpStartDate.getValue() && !"".equals(this.dpStartDate.getValue().toString())){
			planStateDate = (Date)this.dpStartDate.getValue();
		}
		
		/* 调用导入计划模板方法 */
		fDCScheduleBaseEditUI.importPlanTemplate(schTemplateInfo,planStateDate);
		
		/* 关闭窗体 */
		destroyWindow();
	}

	/**
	* @discription  <点击取消>
	* @author       <Xiaolong Luo>
	* @createDate   <2011/08/20> <p>
	* @param        <空>
	* @return       <返回值描述>
	* 
	* modifier      <空> <p>
	* modifyDate    <空> <p>
	* modifyInfo	 <空> <p>
	* @see          <相关的类>
	*/	
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}
 
	
	public void onLoad() throws Exception {
		super.onLoad();
//		prmpPlanTemplate
//				.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7RESchTemplateQuery");
//		prmpPlanTemplate.setCommitFormat("$name$");
//		prmpPlanTemplate.setDisplayFormat("$name$");
//		prmpPlanTemplate.setEditFormat("$name$");

	}

	/**
	 * 点表头不排序，双击表格不弹出编辑界面，避免中断
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);
	}
	
	protected String getEditUIName() {
		return null;
	}

	/**
	 *不显示工具栏
	 */
	public void initUIToolBarLayout() {

	}
	
	/*
	 * 屏蔽按表头排序
	 * @see com.kingdee.eas.framework.client.ListUI#isCanOrderTable()
	 */
	protected boolean isCanOrderTable() {
		return false;
	}
}