/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectHeader;
import net.sf.mpxj.mpp.MPPReader;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		计划管理任务列表编辑界面
 *		
 * @author		罗小龙
 * @version		EAS7.0		
 * @createDate	2011-08-04 
 * @see
 */
public class RESchTemplateListUI extends AbstractRESchTemplateListUI
{
	/* 缺省版本号 */
	private static final long serialVersionUID = 1L;
	
	/* 日志 */
    private static final Logger logger = CoreUIObject.getLogger(RESchTemplateListUI.class);
    
    
    public Object parentId = "";
    public static String treeParentId = "";
    public String templateId = "";
    
    private Map idTemplate;
    
    /* 临时变量，删除模板和删除模板任务时给出不同的提示信息 */
    private boolean isTemplateInfoRemove = true;
    
    /**
     * output class constructor
     */
    public RESchTemplateListUI() throws Exception
    {
        super();
    }

    public void onShow() throws Exception {
    	super.onShow();
    	this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
    }
    
    
    public void onLoad() throws Exception {
		// if (SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
		// FDCMsgBox.showError("当前组织不是财务组织，不允计对模板进行操作，请切换到对应的财务组织进行操作！");
		// abort();
		//			
		// }
    	super.onLoad();
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	//页面加载初始华一些控件的显示样式
    	initDipaly();
    	
//    	//填充数据
//    	fillTable();
//    	
//    	//增加“所有任务”行
//    	addFirstRow();
    	
    	
    	
    	this.tblMain.checkParsed();
    	this.tblMain.removeRows(false);

    	this.treeView.getControlPane().removeAll();
		this.treeView.getControlPane().add(new KDLabel("模板分类列表"));
    	
		KDWorkButton btnViewCatagory = new KDWorkButton();
		btnViewCatagory.setIcon(EASResource.getIcon("imgTbtn_view"));
		btnViewCatagory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				RESchTemplateCatagoryInfo catagory = getSelectedCatagory();
				uiContext.put(UIContext.ID, catagory.getId());
				try {
					UIFactory.createUIFactory().create(RESchTemplateCatagoryEditUI.class.getName(), uiContext, null, OprtState.VIEW).show();
					refreshTree();
				} catch (UIException e1) {
					handUIException(e1);
				} catch (Exception e2) {
					handUIException(e2);
				} 
			}
		});

		this.treeView.getControlPane().add(btnViewCatagory);
		
    	KDWorkButton btnAddCatagory = new KDWorkButton();
		btnAddCatagory.setIcon(EASResource.getIcon("imgTbtn_new"));
		btnAddCatagory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				RESchTemplateCatagoryInfo catagory = getSelectedCatagory();
				uiContext.put(UIContext.PARENTNODE, catagory);
				try {
					UIFactory.createUIFactory().create(RESchTemplateCatagoryEditUI.class.getName(), uiContext, null, OprtState.ADDNEW).show();
					treeBuilder.refreshTreeNode(treeMain, getSelectedTreeNode(), getDefaultFilterForTree());
				} catch (UIException e1) {
					handUIException(e1);
				} catch (Exception e2) {
					handUIException(e2);
				}
			}
		});

		this.treeView.getControlPane().add(btnAddCatagory);
    	
		KDWorkButton btnEditCatagory = new KDWorkButton();
		btnEditCatagory.setIcon(EASResource.getIcon("imgTbtn_edit"));
		btnEditCatagory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				RESchTemplateCatagoryInfo catagory = getSelectedCatagory();
				if (!SysContext.getSysContext().getCurrentCtrlUnit().getId().equals(catagory.getCU().getId())) {
					FDCMsgBox.showError("不能操作非本组织的数据！");
					abort();
				}
				uiContext.put(UIContext.ID, catagory.getId());
				try {
					UIFactory.createUIFactory().create(RESchTemplateCatagoryEditUI.class.getName(), uiContext, null, OprtState.EDIT).show();
					refreshTree();
				} catch (UIException e1) {
					handUIException(e1);
				} catch (Exception e2) {
					handUIException(e2);
				}
			}
		});

		this.treeView.getControlPane().add(btnEditCatagory);
		
		KDWorkButton btnRemoveCatagory = new KDWorkButton();
		btnRemoveCatagory.setIcon(EASResource.getIcon("imgTbtn_delete"));
		btnRemoveCatagory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				RESchTemplateCatagoryInfo catagory = getSelectedCatagory();
				if (!SysContext.getSysContext().getCurrentCtrlUnit().getId().equals(catagory.getCU().getId())) {
					FDCMsgBox.showError("不能操作非本组织的数据！");
					abort();
				}
				try {
					if (catagory.getId().toString().equals("HJ8fv+oQR6u916pj4lAedPQl3qM=")
							|| catagory.getId().toString().equals("abEP3m1YRtKOd+VWyY16xPQl3qM=")) {
						FDCMsgBox.showError("系统预设数据不可删除！");
						abort();
					}

					if (!catagory.isIsLeaf()) {
						FDCMsgBox.showError("当前分类已有下级分类，不可删除！");
						abort();
					}
					
					if (tblMain.getRowCount() != 0 || tblMain.getSelectManager().size() != 0){
						FDCMsgBox.showError("当前分类中已经存在计划模板，请先删除！");
						abort();
					}
					
					int result = FDCMsgBox.showConfirm2(RESchTemplateListUI.this, "你确认要进行删除吗？");
					if (FDCMsgBox.CANCEL == result) {
						abort();
					}
					
						
					RESchTemplateCatagoryFactory.getRemoteInstance().delete(new ObjectUuidPK(catagory.getId()));
					refreshTree();
				} catch (EASBizException e1) {
					handUIException(e1);
				} catch (BOSException e1) {
					handUIException(e1);
				} catch (Exception e1) {
					handUIException(e1);
				}

			}
		});

		this.treeView.getControlPane().add(btnRemoveCatagory);
    }
    
    
    /**
     * @discription  <页面加载初始华一些控件的显示样式>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/10> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void initDipaly(){
    	this.windowTitle = "计划模板管理";
    	
    	this.pnlMain.setDividerSize(8);
    	
    	/* 设置树面板标题 */
    	this.treeView.setTitle("模板分类");
    	this.treeView.setVisible(true);
    	this.treeView.setShowControlPanel(true);
    	this.pnlMain.setDividerLocation(260);
    	this.contTemplateList.setEnableActive(false);
//    	
//    	//设置根节点不显示
//    	this.treeMain.setRootVisible(false);
//    	
//    	//设置树节点全展开
//    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
//    	
//    	/* 设置“引入模板”，“导入Project”按扭为显示样式和状态 */
//    	this.btnImportProject.setEnabled(true);
//    	this.btnImportProject.setIcon(EASResource.getIcon("imgTbtn_citetree"));
//    	this.btnImportProject.setVisible(true);
//    	this.btnImportTemplate.setEnabled(true);
//    	this.btnImportTemplate.setIcon(EASResource.getIcon("imgTbtn_citetree"));
//    	this.btnImportTemplate.setVisible(true);
//    	
//    	/* 设置树节点的编辑操作  */
//    	this.btnGroupAddNew.setVisible(true);
//    	this.btnGroupAddNew.setToolTipText("新增");
//    	
//    	this.btnGroupEdit.setVisible(true);
//    	this.btnGroupEdit.setToolTipText("修改");
//    	
//    	this.btnGroupRemove.setVisible(true);
//    	this.btnGroupRemove.setToolTipText("删除");
//    	
//    	this.btnGroupView.setToolTipText("查看");
//    	this.btnGroupView.setVisible(true);
//    	
//    	/* 隐藏查看和工具两个菜单 */
//    	this.menuTool.setVisible(false);
//    	this.menuView.setVisible(false);
//    	this.menuItemGroupAddNew.setVisible(false);
    	this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
    	this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    	this.btnAudit.setEnabled(true);
		this.btnUnAudit.setEnabled(true);
    	setTableDisplayStyle();
    	
    	
    }
    
    /**
     * @discription  <填充表数据>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/11> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @throws BOSException 
     * @see          <相关的类>
     */
    public void fillTable() throws BOSException{
    	tblMain.removeRows(false);
    	RESchTemplateCollection cols = getRESchTemplateCollection();
    	if(cols == null){
    		return;
    	}
    	if(!cols.isEmpty()){
    		tblMain.checkParsed();
    		int size = cols.size();
    		IRow row = null;
    		RESchTemplateInfo info = null;
    		for(int i=0;i<size;i++){
    			info = cols.get(i);
    			row = tblMain.addRow();
    			fillDataToRow(row, info);
    		}
    	}
    	    	
	}

	private void fillDataToRow(IRow row, RESchTemplateInfo info) {
		row.getCell("id").setValue(info.getId().toString());
		row.getCell("templateName").setValue(info.getName());
		row.getCell("templateNumber").setValue(info.getNumber());
		row.getCell("state").setValue(info.getState().getAlias());
		row.getCell("orgUnit").setValue(info.getOrgUnit().getName());
		row.getCell("orgUnit").setUserObject(info.getOrgUnit());
	}

	private RESchTemplateCollection getRESchTemplateCollection() throws BOSException {
		KDTreeNode node = getSelectedTreeNode();
    	if(node == null || node.getUserObject() instanceof String){
    		return null;
    	}
    	FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
    	RESchTemplateCatagoryInfo catagory = (RESchTemplateCatagoryInfo) node.getUserObject();
    	
    	
    	ScheduleTemplateTypeEnum type = catagory.getTemplateType();
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
		// filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber",orgUnit.getLongNumber()+"%",CompareType.LIKE));
		CompanyOrgUnitInfo org = SysContext.getSysContext().getCurrentFIUnit();
		String[] numbers = org.getLongNumber().split("!");
		Set orgSet = new HashSet();
		for (int i = 0; i < numbers.length; i++) {
			orgSet.add(numbers[i]);
		}
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", orgSet, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("catagory.id",catagory.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("templateType",type));
    	view.setFilter(filter);
    	
    	view.getSorter().add(new SorterItemInfo("number"));
    	RESchTemplateCollection cols = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
        return cols;
	}

	private void loadTaskBizType(Map bizTypeDesc, StringBuffer txtDesc, RESchTemplateTaskInfo task) {
		RESchTemplateTaskBizTypeCollection cols = task.getBusinessType();
		txtDesc = new StringBuffer();
		int size = cols.size();
		for (int i = 0; i < size; i++) {
			RESchTemplateTaskBizTypeInfo taskBizType = cols.get(i);
			if (taskBizType != null && taskBizType.getBizType() != null) {
				txtDesc.append(taskBizType.getBizType().getName());
				txtDesc.append(",");
			}
				
		}
		
		int index = txtDesc.lastIndexOf(",");
		if (index > 1) {
			txtDesc.replace(index, index + 1, " ");
		}
		if (!StringUtils.isEmpty(txtDesc.toString())) {
			bizTypeDesc.put(task.getId().toString(), txtDesc.toString());
		}
		
	}
    
    
    /**
     * @discription  <根据模板id，将模板任务信息封装成以任务id为键，以业务类型或前置任务为值的键值对>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/12> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public Map getAllTemplateTaskInfo(String type){
//    	Map map = new HashMap();
//    	KDTreeNode treeNode = this.getSelectedTreeNode();
//    	if(null != treeNode && treeNode.getUserObject() instanceof RESchTemplateInfo){
//    		RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
//    		EntityViewInfo view = new EntityViewInfo();
//    		FilterInfo filter = new FilterInfo();
//    		filter.appendFilterItem("template.id", schTemplateInfo.getId());
//    		view.setFilter(filter);
//    		try {
//    			RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
//    			if(COL_BUSINESS_TYPE.equals(type)){
//    				for(int k = 0; k < schTemplateTaskCollection.size(); k ++){
//        				RESchTemplateTaskInfo schTemplateTaskInfo = schTemplateTaskCollection.get(k);
//        				RESchTemplateTaskBizTypeCollection schTemplateTaskBizTypeCollection = schTemplateTaskInfo.getBusinessType();
//        				StringBuffer businessType = new StringBuffer();
//        				for(int s = 0; s < schTemplateTaskBizTypeCollection.size(); s ++){
//        					RESchTemplateTaskBizTypeInfo schTemplateTaskBizTypeInfo = RESchTemplateTaskBizTypeFactory.getRemoteInstance().getRESchTemplateTaskBizTypeInfo(new ObjectUuidPK(schTemplateTaskBizTypeCollection.get(s).getId()));
//        					ScheduleBizTypeInfo sheduleBizTypeInfo = ScheduleBizTypeFactory.getRemoteInstance().getScheduleBizTypeInfo(new ObjectUuidPK(schTemplateTaskBizTypeInfo.getBizType().getId()));
//        					if(null != sheduleBizTypeInfo && !"".equals(sheduleBizTypeInfo.getName().trim())){
//        						businessType.append(sheduleBizTypeInfo.getName().trim()).append(",");
//        					}
//        				}
//        				if(businessType.lastIndexOf(",") != -1){
//        					map.put(schTemplateTaskInfo.getId().toString(), businessType.substring(0, businessType.lastIndexOf(",")));
//        				}else{
//        					map.put(schTemplateTaskInfo.getId().toString(), businessType);
//        				}
//        				
//        			}
//    			}else{
//    				for(int k = 0; k < schTemplateTaskCollection.size(); k ++){
//        				RESchTemplateTaskInfo schTemplateTaskInfo = schTemplateTaskCollection.get(k);
//        				RESchTemplateTaskPredecessorCollection schTemplateTaskPredecessorCollection = schTemplateTaskInfo.getPredecessors();
//        				StringBuffer pTask = new StringBuffer();
//        				for(int s = 0; s < schTemplateTaskPredecessorCollection.size(); s ++){
//        					RESchTemplateTaskPredecessorInfo schTemplateTaskPredecessorInfo = RESchTemplateTaskPredecessorFactory.getRemoteInstance().getRESchTemplateTaskPredecessorInfo(new ObjectUuidPK(schTemplateTaskPredecessorCollection.get(s).getId()));
//        					RESchTemplateTaskInfo templateTaskInfo = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskInfo(new ObjectUuidPK(schTemplateTaskPredecessorInfo.getPredecessorTask().getId())); 
//        					if(null != templateTaskInfo && !"".equals(templateTaskInfo.getName().trim())){
//        						pTask.append(templateTaskInfo.getName()).append(",");
//        					}
//        				}
//        				if(pTask.lastIndexOf(",") != -1){
//        					map.put(schTemplateTaskInfo.getId().toString(), pTask.substring(0, pTask.lastIndexOf(",")));
//        				}else{
//        					map.put(schTemplateTaskInfo.getId().toString(), pTask);
//        				}
//        			}
//    			}
//			} catch (Exception e) {
//				logger.info("RESchTemplateListUI 283 error！");
//			}
//    	}
//    	return map;
    	return null;
    }
    
    /**
     * @discription  <设置表格显示样式>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/08> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void setTableDisplayStyle(){
//    	this.tblMain.getColumn(COL_NAME).setWidth(365);
//    	this.tblMain.getColumn(COL_PTASK).setWidth(260);
//    	this.tblMain.getColumn(COL_TASK_TYPE).setWidth(150);
//    	this.tblMain.getColumn(COL_BUSINESS_TYPE).setWidth(260);
//    	this.tblMain.getColumn(COL_REFERENCEDAY).setWidth(100);
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionQuery.setVisible(false);
    	if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
    }
    
    protected String[] getLocateNames() {
		return new String[] { "templateName", "templateNumber" };
	}
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//    	setButtonState();
//    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//    	IRow row = this.tblMain.getRow(rowIndex);
//    	if(null == row || null == row.getCell(COL_ID).getValue() || "".equals(row.getCell(COL_ID).getValue().toString().trim())){
//    		return ;
//    	}
////    	provideNumberInfo();
        super.tblMain_tableClicked(e);
    }

    /**
     * @discription  <设置按扭状态>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/09/01> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void setButtonState(){
    	this.btnView.setEnabled(true);
    	this.btnAddNew.setEnabled(true);
    	this.btnEdit.setEnabled(true);
    	this.btnRemove.setEnabled(true);
    }
    
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//    	this.btnView.setEnabled(true);
//    	this.btnAddNew.setEnabled(true);
//    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//    	IRow row = this.tblMain.getRow(rowIndex);
//    	if(null == row || null == row.getCell(COL_ID).getValue() || "".equals(row.getCell(COL_ID).getValue().toString().trim())){
//    		return ;
//    	}
//        super.tblMain_tableSelectChanged(e);

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		FullOrgUnitInfo info = (FullOrgUnitInfo) this.tblMain.getCell(rowIndex, "orgUnit").getUserObject();
		if (info.getId().equals(SysContext.getSysContext().getCurrentFIUnit().getId())) {
			this.actionEdit.setVisible(true);
			this.actionRemove.setVisible(true);
		} else {
			this.actionEdit.setVisible(false);
			this.actionRemove.setVisible(false);
		}
    }

    protected void selectNode() throws Exception {
//    	super.selectNode();
    }
    
    protected void execQuery() {
//    	super.execQuery();
    	try {
			this.fillTable();
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
    public void setSelectFirstRow(KDTable tb) {
//    	super.setSelectFirstRow(tb);
    }
    
    protected FilterInfo getTreeFilter() {
		FilterInfo fitler = new FilterInfo();

		KDTreeNode node = (KDTreeNode) treeMain.getLastSelectedPathComponent();
		Object obj = node.getUserObject();
		if (obj instanceof RESchTemplateInfo) {
			String treeID = ((RESchTemplateInfo) obj).getId().toString();
			fitler.getFilterItems().add(new FilterItemInfo("template.id", treeID));
		}
		return fitler;
	}
    
    RESchTemplateCatagoryInfo getSelectCatagory(){
    	KDTreeNode treeNode = this.getSelectedTreeNode();
    	if(null == treeNode){
    		FDCMsgBox.showInfo("请选择需要新增模板任务的模板！");
    		abort();
    	}
	     if (((RESchTemplateCatagoryInfo) treeNode.getUserObject()).getLevel() == 1) {
			FDCMsgBox.showInfo("不能在顶级模板(如分类树中的主项计划模板和专项计划模板)分类下新增模板！");
			abort();
		}
    	
    	RESchTemplateCatagoryInfo catagory = (RESchTemplateCatagoryInfo) treeNode.getUserObject();
    	return catagory;
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	
//    	setOprtState(OprtState.ADDNEW);
//    	provideNumberInfo();
		if (SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
			// // OUPartFIInfo fiOrgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getPartFI();
			// // fiOrgUnitInfo = OUPartFIFactory.getRemoteInstance().getOUPartFIInfo(new ObjectUuidPK(fiOrgUnitInfo.getId()));
			// // if (!fiOrgUnitInfo.isIsBizUnit()) {
			// FDCMsgBox.showError("当前组织不是实体财务组织，不能新增，请切换到实体财务组织来新增。");
			// abort();
			// }
			super.actionAddNew_actionPerformed(e);
		} else {
			FDCMsgBox.showError("当前组织不是财务组织，不能新增，请切换到财务组织来新增。");
			abort();
		}
//    	treeBuilder.refreshTreeNode(treeMain, treeNode);
//    	this.tblMain.setRowCount(tblMain.getBody().getRows().size());
    }

    
//    /**
//     * @discription  <新增，查看，修改时存入parent.id，以便编辑界面查询同级最大编码>
//     * @author       <Xiaolong Luo>
//     * @createDate   <2011/08/18> <p>
//     * @param        <空>
//     * @return       <返回值描述>
//     * 
//     * modifier      <空> <p>
//     * modifyDate    <空> <p>
//     * modifyInfo	 <空> <p>
//     * @see          <相关的类>
//     */
//	private void provideNumberInfo() throws BOSException {
//		parentId = "";
//		templateId = "";
//		this.getUIContext().put(NUMBERARRAY,"");
//		
//		KDTreeNode treeNode = this.getSelectedTreeNode();
//		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//    	if(rowIndex >= 0){
//    		IRow row = this.tblMain.getRow(rowIndex);
//    		Object idObj = row.getCell(COL_ID).getValue();
//        	if(null != idObj && !"".equals(idObj.toString().trim())){
//        		EntityViewInfo view = new EntityViewInfo();
//            	FilterInfo filter = new FilterInfo();
//            	filter.appendFilterItem("parent.id", idObj);
//            	view.setFilter(filter);
//            	RESchTemplateTaskCollection templateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
//            	//临时变量，取树节点同级编码最大值
//            	int numberInt = 0;
//            	String[] numberArray = new String[templateTaskCollection.size()];
//            	for(int temp = 0; temp < templateTaskCollection.size(); temp ++){
//            		RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo)templateTaskCollection.get(temp);
//            		Pattern tDouble = Pattern.compile("([0-9]{1,20})");
//            		if (tDouble.matcher(schTemplateTaskInfo.getNumber().toString()).matches()) {
//            			if(numberInt < Integer.parseInt(schTemplateTaskInfo.getNumber().toString())){
//            				numberInt = Integer.parseInt(schTemplateTaskInfo.getNumber().toString());
//      	        		}
//            		}
//            		numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
//            	}
//        		this.getUIContext().put(PARENT_ID, idObj);
//        		this.getUIContext().put(NUMBERARRAY, numberArray);
//        		parentId = idObj;
//        	}
//    	}
//    	if(null != treeNode && null != treeNode.getUserObject() && treeNode.getUserObject() instanceof RESchTemplateInfo){
//    		RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
//    		templateId = String.valueOf(null == schTemplateInfo.getId() ? "" : schTemplateInfo.getId().toString());
//    	}
//    	
//    	if(!(OprtState.ADDNEW.equals(getOprtState()))){
//    		parentId = "";
//		}
//	}


    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		String state = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "state").getValue().toString().trim();
		if (state.equals(FDCBillStateEnum.AUDITTING.getAlias()) || state.equals(FDCBillStateEnum.AUDITTED.getAlias())) {
			FDCMsgBox.showError("当前单据状态为" + state + ",不能进行操作！");
			abort();
		}
    	super.actionEdit_actionPerformed(e);
    }
    
    protected boolean isSystemDefaultData(int activeRowIndex) {
    	return false;
    }
    
    protected void refresh(ActionEvent e) throws Exception {
//    	super.refresh(e);
    	fillTable();
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	if(e.getActionCommand().contains("ActionAddNew")||e.getActionCommand().contains("ActionEdit")||e.getActionCommand().contains("ActionView")){
    		uiContext.put("catagory", getSelectCatagory());    		
    	}
//    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//		if (rowIndex >= 0) {
//			IRow row = this.tblMain.getRow(rowIndex);
//			this.getUIContext().put(COL_ID, row.getCell(COL_ID).getValue());
//			this.getUIContext().put(COL_LEVEL, row.getCell(COL_LEVEL).getValue());
//			this.getUIContext().put(COL_ISLEAF, row.getCell(COL_ISLEAF).getValue());
//			Object aaa = this.getUIContext().get(COL_ID);
//			if (null != aaa && !"".equals(aaa.toString().trim())) {
//				parentId = aaa;
//				RESchTemplateTaskInfo schTemplateTaskInfo = null;
//				try {
//					schTemplateTaskInfo = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskInfo(new ObjectUuidPK(aaa.toString()));
//				} catch (EASBizException ex) {
//					logger.error(ex.getMessage());
//					this.tblMain.refresh();
//				} catch (BOSException e1) {
//					logger.error(e1.getMessage());
//					this.tblMain.refresh();
//				}
//				getUIContext().put("treeNode", schTemplateTaskInfo.getTemplate());
//			}else{
//				getUIContext().put("treeNode", getSelectedTreeNode().getUserObject());
//			}
//		}
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		this.tblMain.checkParsed();
//		int result = FDCMsgBox.showConfirm3("是否确认删除？");
//		if (AdvMsgBox.NO_OPTION == result || AdvMsgBox.CANCEL_OPTION == result) {
//			abort();
//		}
		
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		String state = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "state").getValue().toString().trim();
		if (state.equals(FDCBillStateEnum.AUDITTING.getAlias()) || state.equals(FDCBillStateEnum.AUDITTED.getAlias())) {
			FDCMsgBox.showError("当前单据状态为" + state + ",不能进行操作！");
			abort();
		}
		//支持批量删除
		super.actionRemove_actionPerformed(e);
//		ArrayList alist = this.tblMain.getSelectManager().getBlocks();
//		ObjectUuidPK[] arrayPK = new ObjectUuidPK[alist.size()];
//		for(int j=((KDTSelectBlock)alist.get(0)).getTop(); j < alist.size(); j++ ){
//			arrayPK[j]=new ObjectUuidPK(BOSUuid.read(this.tblMain.getCell(j, "id").getValue() + ""));
//		}
//		RESchTemplateFactory.getRemoteInstance().delete(arrayPK);
//		RESchTemplateFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(this.tblMain.getCell(rowIndex, "id").getValue() + "")));
		fillTable();
    }

    /**
	 * 
	 * @description 删除任务时同时清楚与之关联的后置任务关系
	 * @author 杜红明
	 * @createDate 2011-10-27
	 * @param arrays
	 * @throws BOSException
	 * @throws EASBizException void
	 * @version EAS7.0
	 */
	private void deletePreTaskRelateTasks(String[] arrays) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = getPreTaskRelateTasksFilter(arrays, view);
		// 获取所有选择的任务切作为了其他任务的前置任务
		RESchTemplateTaskPredecessorCollection col = RESchTemplateTaskPredecessorFactory.getRemoteInstance()
				.getRESchTemplateTaskPredecessorCollection(view);
		String[] ids = null;
		if (col != null && col.size() > 0) {
			ids = new String[col.size()];
			for (int i = 0; i < col.size(); i++) {
				RESchTemplateTaskInfo task = col.get(i).getTask();
				if (task != null && task.getId() != null) {
					ids[i] = task.getId().toString();
				}
			}
			executeUpdatePreTaskRelateTasks(ids);
			RESchTemplateTaskPredecessorFactory.getRemoteInstance().delete(filter);
		}
	}

	private void executeUpdatePreTaskRelateTasks(String[] ids) throws BOSException {
		if (ids != null && ids.length > 0) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("update T_SCH_RESchTemplateTask set FpredecessorDesc='' where "
					+ FDCSQLBuilder.getInSql("fid", ids) + "");
			builder.executeUpdate();
		}
	}
	private FilterInfo getPreTaskRelateTasksFilter(String[] arrays, EntityViewInfo view) {
		view.getSelector().add("task.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("predecessorTask.id", new HashSet(Arrays.asList(arrays)), CompareType.INCLUDE));
		view.setFilter(filter);
		return filter;
	}
    /**
     * @discription  <为模板赋值>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/25> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void setIdTempalte(){
    	this.idTemplate = new HashMap();
    	try{
    		SelectorItemCollection sic = this.getSelectors();
    		sic.add(new SelectorItemInfo("id"));
    		sic.add(new SelectorItemInfo("template.id"));
    		EntityViewInfo view = new EntityViewInfo();
    		view.setSelector(sic);
    		FilterInfo filter =  new FilterInfo();
    		view.setFilter(filter);
	    	RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
	    	for(int k = 0; k < rESchTemplateTaskCollection.size(); k ++){
	    		RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(k);
	    		this.idTemplate.put(rESchTemplateTaskInfo.getId().toString(), rESchTemplateTaskInfo.getTemplate());
	    	}
    	}catch(Exception e){
    		handUIException(e);
    	}
    }
   
    /**
	 * 
	 * @description 判断是否有子节点
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public boolean hasChild(RESchTemplateTaskInfo guideInfo) throws Exception {
		boolean child = false;
		if (guideInfo != null && guideInfo.getParent() != null) {
			RESchTemplateTaskInfo parentInfo = guideInfo.getParent();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId()));
			view.setFilter(filter);
			RESchTemplateTaskCollection collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			if (collection.size() > 0) {
				child = false;
			} else {
				child = true;
			}
		}
		return child;
	}

    protected boolean confirmRemove() {
    	String message = "";
    	/* 提示不同的信息 */
    	if(isTemplateInfoRemove){
    		if(null != this.getSelectedTreeNode() && this.getSelectedTreeNode().getUserObject() instanceof RESchTemplateInfo){
    			RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)this.getSelectedTreeNode().getUserObject();
    			if(null != schTemplateInfo.getId() && !"".equals(schTemplateInfo.getId().toString().trim())){
    				EntityViewInfo view = new EntityViewInfo();
        			FilterInfo filter = new FilterInfo();
        			filter.appendFilterItem("template.id", schTemplateInfo.getId());
        			view.setFilter(filter);
        			try {
        				RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
        				if(schTemplateTaskCollection.size() > 0){
        					message = "本模板下有模板任务，删除本类模板，模板下的模板任务也会被删除，是否删除模板？";
        				}
					} catch (BOSException e) {
						handUIException(e);
					}
    			}
    			
    		}
    	}
    	if(!"".equals(message)){
    		return MsgBox.isYes(MsgBox.showConfirm2(this, message));
    	}
		return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
    }
    
    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
        
        /* 设置树节点全展开 */
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.setRowCount(tblMain.getBody().getRows().size());
    }
    
    public boolean isLocatePre() {
    	// TODO Auto-generated method stub
    	return false;
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
//    	tblMain.removeRow(0);
        super.actionLocate_actionPerformed(e);
//        addFirstRow();
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

	protected FDCTreeBaseDataInfo getBaseDataInfo() {
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return RESchTemplateCatagoryFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateFactory.getRemoteInstance();
	}

	protected void initTree() throws Exception {
		super.initTree();
		this.treeMain.setRootVisible(false);
		this.treeMain.expandAllNodes(true, (TreeNode) treeMain.getModel().getRoot());
		// this.treeMain.setRootVisible(true);
		// KDTreeNode root = new KDTreeNode("模板分类");
		// root.setText("模板分类");
		//		
		// KDTreeNode mainNode = new KDTreeNode("主项计划模板");
		// mainNode.setText("主项计划模板");
		// ((DefaultTreeModel)this.treeMain.getModel()).setRoot(root);
		// KDTreeNode sNode = new KDTreeNode("专项计划模板");
		// sNode.setText("专项计划模板");
		//        
		// RESchTemplateCatagoryCollection cols = RESchTemplateCatagoryFactory.getRemoteInstance().getRESchTemplateCatagoryCollection();
		//        
		// for(int i=0;i<cols.size();i++){
		// RESchTemplateCatagoryInfo catagory = cols.get(i);
		// RESchTemplateCatagoryInfo sCatagory = (RESchTemplateCatagoryInfo) catagory.clone();
		// catagory.put("scheduleType", ScheduleTemplateTypeEnum.MainPlanTemplate);
		// KDTreeNode nodeMain = new KDTreeNode(catagory);
		// nodeMain.setText(catagory.getName());
		// mainNode.add(nodeMain);
		// sCatagory.put("scheduleType", ScheduleTemplateTypeEnum.OtherPlanTemplate);
		// KDTreeNode nodeSpecial = new KDTreeNode(sCatagory);
		// nodeSpecial.setText(sCatagory.getName());
		// sNode.add(nodeSpecial);
		// }
		// treeMain.setExpandsSelectedPaths(true);
		// root.add(mainNode);
		// root.add(sNode);
		//        
		// this.treeMain.expandAllNodes(true, root);
		// this.treeMain.setRootVisible(false);
		// treeView.setVisible(true);
		// treeView.setTitle("模板分类");
		// treeView.setControlPaneToolTipTextVisible(true);
		
		
	}

	protected String getGroupEditUIName() {
		return RESchTemplateEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		return "template.id";
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		RESchTemplateTaskInfo templateTaskInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			templateTaskInfo = (RESchTemplateTaskInfo) getBizInterface().getValue(detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (templateTaskInfo.getTemplate() == null || templateTaskInfo.getTemplate().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(templateTaskInfo.getTemplate().getId());
	}
	
	protected String getEditUIModal() {
        return UIFactoryName.MODEL;
    }

	public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		/* 如果当前模板存在“模板任务”，提示“当前模板存在任务项，请先删除任务项！”，否则不允许增加子级模板，新增时在选中模板下增加子级模板。 */
    	if(!isLeaf()){
//    		return ;
    	}else{
    		if(this.tblMain.getRowCount() > 1){
    			FDCMsgBox.showInfo("当前模板存在任务项，请先删除任务项！");
    			return ;
    		}
    	}
    	
    	
    	/* 获得模板类型“主项”，“专项” */
    	KDTreeNode treeNode = getSelectedTreeNode();
    	
    	if(null == treeNode){
    		FDCMsgBox.showInfo("请选中树节点！");
    		return;
    	}
    	
    	provideTreeNumberInfo();
    	
    	while(null != treeNode.getParent()){
    		if(treeNode.getLevel() == 1){
    			break ;
    		}
    		treeNode = (KDTreeNode) treeNode.getParent();
    	}
    	String templateType = treeNode + "";
    	
    	/* 检查是否选中节点 */
    	checkTreeNodeSelected(e);

    	UIContext uiContext = new UIContext(this);
    	uiContext.put("ID", null);
    	uiContext.put("GroupOperate", "false");
    	uiContext.put("templateType", templateType);

//    	prepareGroupUIContext(uiContext, e);

//    	IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext, null, OprtState.ADDNEW);
    	uiWindow.show();
    	setActionEvent(e);
//    	if(isDoRefresh(uiWindow, getGroupEditUIModal())){
//    		refresh(e);
//    	}
    	//设置树节点全展开
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	/**
	 * @discription  <树节点新增，查看，修改时存入parent.id，以便编辑界面查询同级最大编码>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/18> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public void provideTreeNumberInfo(){
		treeParentId = "";
		/* 获得模板类型“主项”，“专项” */
    	KDTreeNode treeNode = getSelectedTreeNode();
    	
    	if(null == treeNode){
    		return;
    	}
    	
    	RESchTemplateCatagoryInfo templateInfo = (RESchTemplateCatagoryInfo)treeNode.getUserObject();
    	if(null != templateInfo.getId()){
    		treeParentId = templateInfo.getId().toString();
    	}
	}

	/**
	 * @discription  <判断是不是末节点>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/10> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public boolean isLeaf(){
		TreePath path = this.treeMain.getSelectionPath();
		if(null == path){
			return false;
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)path.getLastPathComponent();
		return node.isLeaf();
	}
	
	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		
		//检查是否选中节点
		checkTreeNodeSelected(e);

		/* 获得模板类型“主项”，“专项” */
    	KDTreeNode treeNode = getSelectedTreeNode();
    	
    	/* 判断是不是预设数据  */
    	if(null != treeNode){
    		RESchTemplateInfo rESchTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
    		if(rESchTemplateInfo.isIsSystem()){
    			FDCMsgBox.showInfo("系统预设数据不能修改！");
    			return ;
    		}
    	}
    	
    	while(null != treeNode.getParent()){
    		if(treeNode.getLevel() == 1){
    			break ;
    		}
    		treeNode = (KDTreeNode) treeNode.getParent();
    	}
    	
    	provideTreeNumberInfo();
    	
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", getSelectedNodeKeyValue());
		uiContext.put("GroupOperate", "false");
		uiContext.put("templateType", treeNode + "");
		uiContext.put("treeParentId", treeParentId);
//
//		prepareGroupUIContext(uiContext, e);
//		IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext, null, OprtState.EDIT);
//		uiWindow.show();
//		setActionEvent(e);
//
//		if(isDoRefresh(uiWindow, getGroupEditUIModal())){
//			refresh(e);
//		}

		//设置树节点全展开
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception {
//		super.actionGroupMoveTree_actionPerformed(e);
	}

	public void actionGroupRemove_actionPerformed(ActionEvent arg0) throws Exception {
		//表示删除模板而不是删除模板任务，此时将给出不同的提示信息
		isTemplateInfoRemove = true;
		KDTreeNode treeNode = this.getSelectedTreeNode();
		
		if(null == treeNode){
			FDCMsgBox.showInfo("请选中要删除的模板！");
			return ;
		}
		
		RESchTemplateInfo rESchTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
		
		/* 判断是不是预设数据  */
    	if(rESchTemplateInfo.isIsSystem()){
    		FDCMsgBox.showInfo("系统预设数据不能删除！");
    		return ;
    	}
		
//		super.actionGroupRemove_actionPerformed(arg0);
		
		removeTaskByNode(rESchTemplateInfo);
		
		//设置树节点全展开
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	/**
	 * @discription  <>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011//> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public void removeTaskByNode(RESchTemplateInfo rESchTemplateInfo){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("template.id", rESchTemplateInfo.getId());
		view.setFilter(filter);
		try {
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			IObjectPK[] pks = new IObjectPK[rESchTemplateTaskCollection.size()]; 
			for(int k = 0; k < rESchTemplateTaskCollection.size(); k ++){
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(k);
				pks[k] = new ObjectUuidPK(rESchTemplateTaskInfo.getId()); 
			}
			RESchTemplateTaskFactory.getRemoteInstance().deleteBatchData(pks);
		} catch (BOSException e) {
			logger.error(e.getMessage());
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
		//检查是否选中节点
		checkTreeNodeSelected(e);

		/* 获得模板类型“主项”，“专项” */
    	KDTreeNode treeNode = getSelectedTreeNode();
    	
    	while(null != treeNode.getParent()){
    		if(treeNode.getLevel() == 1){
    			break ;
    		}
    		treeNode = (KDTreeNode) treeNode.getParent();
    	}
		
    	provideTreeNumberInfo();
    	
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", getSelectedNodeKeyValue());
		uiContext.put("GroupOperate", "false");
		uiContext.put("templateType", treeNode + "");

//		prepareGroupUIContext(uiContext, e);
//		IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext, null, OprtState.VIEW);
//		uiWindow.show();
//		setActionEvent(e);
//		if(isDoRefresh(uiWindow, getGroupEditUIModal())){
//			refresh(e);
//		}
		
		//设置树节点全展开
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	protected String getEditUIName() {
//		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//		if (activeRowIndex == -1) {
//			parentId = null;
//		}
//		if (activeRowIndex >= 0) {
//			IRow selectedRow = this.tblMain.getRow(activeRowIndex);
//			Object value = selectedRow.getCell(COL_ID).getValue();
//			if (value == null) {
//				parentId = null;
//			}
//		}
		return RESchTemplateEditUI.class.getName();
	}

	/**
     * @discription  <导入Project文件>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/15> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
	public void actionImportProject_actionPerformed(ActionEvent e) throws Exception {
		if(null == this.getSelectedTreeNode() || !this.getSelectedTreeNode().isLeaf()){
			FDCMsgBox.showInfo("请在左侧模板中选择明细模板！");
			return ;
		}
		
		/* 判断当前模板id与传过来的模板id是否相同，如果相则，则不执行导入操作 */
		KDTreeNode treeNode = (KDTreeNode)treeMain.getLastSelectedPathComponent();
		if(null != treeNode && treeNode.getUserObject() instanceof RESchTemplateInfo){
			
			RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
			/* 根据templateId获得所有的模板任务数据 */
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("template.id", schTemplateInfo.getId());
			view.setFilter(filter);
			RESchTemplateTaskCollection currentTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			/* 判断该模板下是否有模板任务数据，如果有，就提示“该模板存在模板任务，执行此操作将删除原有模板任务，是否导入新的模板任务？”，如果没有，就直接导入模板任务 */
			if(currentTemplateTaskCollection.size() != 0){
				int result = FDCMsgBox.showConfirm2("此操作将删除当前模板下的所有任务，是否继续");
				/* result==0 删除并新增任务 result==2 不执行导入操作*/
				if(result == 2){
					return ;
				}
			}
			
			/* 
			 * 执行导入project操作
			 */
			JFileChooser chooser = new JFileChooser(new File("c:\\"));
			/* 设置过虑器 */
			chooser.setFileFilter(new FilterType());
			/* 弹出对话框 */
			chooser.showDialog(null, "导入Project");
			/* 判断是否选中文件 */
			if (chooser.getSelectedFile() == null) {
				return;
			}
			/* 获得选取文件路径 */
			String filePath = chooser.getSelectedFile().getPath();
			File file = new File(filePath);
			
			/*判断模板是否正确*/
			if(!judgeTemplateIsRight(file)){
				FDCMsgBox.showInfo("模板不正确！请更正。");
				return ;
			}
			
			/* 取数据 */
			List projectList = RESchMSProjectReader.pasreMSProject(file,
					new IRESchTaskCreator() {
						public IRESchTask createSchTask() {
							return (IRESchTask) new RESchTemplateTaskInfo();
						}
					}, new IRESchTaskPredecessorCreator() {
						public IRESchTaskPredecessor createSchTaskPredecessor() {
							return new RESchTemplateTaskPredecessorInfo();
						}
					}, new IRESchCalendar() {
						public ScheduleCalendarInfo getSchedule() {
							try {
								return ScheduleCalendarFactory
										.getRemoteInstance()
										.getDefaultCal(null);
							} catch (BOSException e) {
								handUIException(e);
							} catch (EASBizException e) {
								handUIException(e);
							}
							return new ScheduleCalendarInfo();
						}
					});
			
			/* 存入以本级任务id为键，本级任务为值。以便后面构建层次结构 */
			Map projectMap = new HashMap();
			for(int k = 0; k < projectList.size(); k ++){
				if(null != projectList.get(k)){
					RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo)projectList.get(k);
					if(null != schTemplateTaskInfo.getMsProjectId()){
						projectMap.put(schTemplateTaskInfo.getMsProjectId().toString(), schTemplateTaskInfo);
					}
				}
			}
			
			/* 存入模板任务数据，并设置层次结构 */
			CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
			for(int k = 0; k < projectList.size(); k ++){
				if(null != projectList.get(k)){
					RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo)projectList.get(k);
					schTemplateTaskInfo.setTemplate(schTemplateInfo);
					Object projectObj = projectMap.get(String.valueOf(null == schTemplateTaskInfo.getMsParentPrjId() ? "" : schTemplateTaskInfo.getMsParentPrjId()));
					if(null != projectObj && !"".equals(projectObj.toString().trim())){
						if(projectObj instanceof RESchTemplateTaskInfo){
							schTemplateTaskInfo.setParent((RESchTemplateTaskInfo)projectObj);
						}
					}
					schTemplateTaskInfo.setSeq(k+1);
					coreBaseCollection.add((CoreBaseInfo)schTemplateTaskInfo);
//					RESchTemplateTaskFactory.getRemoteInstance().addnew(schTemplateTaskInfo);
				}
			}
			RESchTemplateTaskFactory.getRemoteInstance().importTasks(coreBaseCollection, currentTemplateTaskCollection);
		}
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * @discription  <判断project模板是否正确>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/17> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @throws MPXJException 
	 * @see          <相关的类>
	 */
	public boolean judgeTemplateIsRight(File file) throws MPXJException{
		ProjectFile projectFile = new MPPReader().read(file);
		ProjectHeader projectHeader = projectFile.getProjectHeader();
		return true;
	}
	
	/**
     * @discription  <导入计划管理模板>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/15> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
	public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception {
		if(null == this.getSelectedTreeNode()){
			FDCMsgBox.showInfo("请在左边目录树中选择需要引入模板的模板！");
			return ;
		}
		if(!this.getSelectedTreeNode().isLeaf()){
			FDCMsgBox.showInfo("请在左边目录树中选择需要引入模板的模板！");
			return ;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("RESchTemplateListUI", this);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(RESchTemplateTaskListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		/* 刷新页面 */
		this.actionRefresh_actionPerformed(e);
	}
	
	/*
	 * 屏蔽按表头排序
	 * @see com.kingdee.eas.framework.client.ListUI#isCanOrderTable()
	 */
	protected boolean isCanOrderTable() {
		return false;
	}
	
	public void checkBeforeAudit() {
		String orgName = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "orgUnit").getValue().toString();
		if (!orgName.equals(SysContext.getSysContext().getCurrentOrgUnit().getName())) {
			FDCMsgBox.showError("不能操作非当前组织的单据！");
			abort();
		}
	}
	
	/**
	 * 忽略过滤
	 */
	protected FilterInfo getDefaultFilterForTree() {
		return super.getDefaultFilterForTree();
	}
	/**
	 * @discription  <导入计划任务>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/15> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @see          <相关的类>
	 * 无非是复制一个模板的问题，变通解决方式,把要导入的模板ID和当前选择的模板传到后台处理就行了
	 * 
	 */
	public void importTemplate(String templateId ) throws BOSException, EASBizException{
//		RESchTemplateInfo templateInfo = null;
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if(StringUtils.isEmpty(templateId) && node == null){
//	    	 return ;
//	     }
//		
//		if(node.getUserObject() instanceof RESchTemplateInfo){
//	    	templateInfo = (RESchTemplateInfo) node.getUserObject();
//	    }
//		
//		if(templateId.equals(templateInfo.getId().toString())){
//			return ;
//		} 
//		
//	     if(tblMain.getRowCount()>0){
//	    	 int result = FDCMsgBox.showConfirm2("此操作将删除当前模板下的所有任务，是否继续");
//				if(FDCMsgBox.NO == result){
//					abort();
//				}
//	     }
//	     
	     
	     
	     
	     
	     
		
		/* 判断模板id是否有值，有值就开始导入模板任务 */
		if(!"".equals(templateId.trim())){
			/* 判断当前模板id与传过来的模板id是否相同，如果相等，则不执行导入操作 */
			KDTreeNode treeNode = (KDTreeNode)treeMain.getLastSelectedPathComponent();
			if(treeNode.getUserObject() instanceof RESchTemplateInfo){
				RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)treeNode.getUserObject();
				if(null != schTemplateInfo.getId() && !templateId.trim().equals(schTemplateInfo.getId().toString().trim())){
					/* 根据templateId获得所有的模板任务数据 */
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("template.id", templateId.trim());
					view.setFilter(filter);
					SorterItemInfo sii = new SorterItemInfo();
					sii.setPropertyName("longNumber");
					sii.setSortType(SortType.ASCEND);
					view.getSorter().add(sii);
					/**源模板纪录*/
					RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
					filter = new FilterInfo();
					filter.appendFilterItem("template.id", schTemplateInfo.getId());
					view.setFilter(filter);
					/**当前模板纪录*/
					RESchTemplateTaskCollection currentTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
					int result = 0;
					/* 判断该模板下是否有模板任务数据，如果有，就提示“该模板存在模板任务，
					 * 执行此操作将删除原有模板任务，是否导入新的模板任务？”，如果没有，就直接导入模板任务 */
					if(currentTemplateTaskCollection.size() != 0){
						result = FDCMsgBox.showConfirm2("此操作将删除当前模板下的所有任务，是否继续");
						if(result == 2){
							return ;
						}
					}
					
					/* 循环取要导入的模板任务数据并封装成新的对象 */
					Map parentIdMap = new HashMap();
					Map needReplaceId  = new HashMap();
					CoreBaseCollection baseCollection = new CoreBaseCollection();
					for(int k = 0; k < schTemplateTaskCollection.size(); k ++){
						String oldId = "";
						/**当前任务 */
						RESchTemplateTaskInfo schTemplateTaskInfo = schTemplateTaskCollection.get(k);
						oldId = String.valueOf(null == schTemplateTaskInfo.getId() ? "" : schTemplateTaskInfo.getId().toString().trim());
						schTemplateTaskInfo.setId(BOSUuid.create((new RESchTemplateTaskInfo()).getBOSType()));
						schTemplateTaskInfo.setTemplate(schTemplateInfo);
						RESchTemplateTaskBizTypeCollection schTemplateTaskBizTypeCollection = schTemplateTaskInfo.getBusinessType();
						for(int s = 0; s < schTemplateTaskBizTypeCollection.size(); s ++){
							RESchTemplateTaskBizTypeInfo schTemplateTaskBizTypeInfo = schTemplateTaskBizTypeCollection.get(s);
							schTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
						}
						String parentId = String.valueOf(null == schTemplateTaskInfo.getParent() ? "" : schTemplateTaskInfo.getParent().getId().toString());
						if(null != parentIdMap.get(parentId) && !"".equals(parentIdMap.get(parentId).toString().trim())){
							schTemplateTaskInfo.getParent().setId(BOSUuid.read(parentIdMap.get(parentId).toString().trim()));
						}
						
						baseCollection.add((CoreBaseInfo)schTemplateTaskInfo);
						
						RESchTemplateTaskPredecessorCollection rESchTemplateTaskPredecessorCollection = schTemplateTaskInfo.getPredecessors();
						/* 处理前置任务 */
						for(int m = 0; m < rESchTemplateTaskPredecessorCollection.size(); m ++){
							RESchTemplateTaskPredecessorInfo rESchTemplateTaskPredecessorInfo = rESchTemplateTaskPredecessorCollection.get(m);
							rESchTemplateTaskPredecessorInfo.setId(BOSUuid.create(rESchTemplateTaskPredecessorInfo.getBOSType()));
							String newProcID = (String) parentIdMap.get(rESchTemplateTaskPredecessorInfo.getPredecessorTask().getId().toString());
							if(StringUtils.isEmpty(newProcID)){
								needReplaceId.put(rESchTemplateTaskPredecessorInfo.getId().toString(), rESchTemplateTaskPredecessorInfo.getPredecessorTask().getId().toString());
							}else{
								rESchTemplateTaskPredecessorInfo.getPredecessorTask().setId(BOSUuid.read(newProcID));
							}
							rESchTemplateTaskPredecessorInfo.setTask(schTemplateTaskInfo);
							
						}
//						RESchTemplateTaskFactory.getRemoteInstance().addnew(schTemplateTaskInfo);
						String newId = String.valueOf(null == schTemplateTaskInfo.getId() ? "" : schTemplateTaskInfo.getId().toString().trim());
						parentIdMap.put(oldId, newId);
					}
					RESchTemplateTaskFactory.getRemoteInstance().importTasks(baseCollection, currentTemplateTaskCollection);
					
					if(!needReplaceId.isEmpty()){
						FDCSQLBuilder builder = new FDCSQLBuilder();
						String sql = "update T_SCH_RESchTplTaskPredecessor set FPredecessorTaskID = ? where fid = ?";
						Set keySet = needReplaceId.keySet();
						List paramList = new ArrayList();
						for(Iterator it=keySet.iterator();it.hasNext();){
							List valueList = new ArrayList();
							String id = (String) it.next();
							String procId  = (String) parentIdMap.get(needReplaceId.get(id).toString()); 
							valueList.add(procId);
							valueList.add(id);
							paramList.add(valueList);
						}
						builder.executeBatch(sql, paramList);
					}
				}
			}
			
		}
	}
	
	
	/**
	 * 不进行cu隔离
	 */
	protected boolean isIgnoreCUFilter() { 
    	return true;
    }
	// update by libing at 2011-9-13
	public void actionQuery_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionQuery_actionPerformed(arg0);
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if (node != null && node.isLeaf() && node.getUserObject() != null && node.getUserObject() instanceof RESchTemplateInfo) {
//			TreeSelectionEvent e = new TreeSelectionEvent(parentId, null, isTemplateInfoRemove, null, null);
//			treeMain_valueChanged(e);
//		}
	}
	// update by libing at 2011-9-13
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
//		super.treeMain_valueChanged(e);
	
		provideTreeNumberInfo();
		Thread t = new Thread(new Runnable() {

			public void run() {
				try {
					fillTable();
				} catch (BOSException e) {
					handUIException(e);
				}
			}
		});
		SwingUtilities.invokeLater(t);
		
	}

	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforeAudit();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		String id = tblMain.getRow(rowIndex).getCell("id").getValue() + "";
		if (!StringUtils.isEmpty(id)) {
			RESchTemplateFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		fillTable();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforeAudit();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		String id = tblMain.getRow(rowIndex).getCell("id").getValue() + "";
		if (!StringUtils.isEmpty(id)) {
			RESchTemplateFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		}
		fillTable();
	}

	private void refreshTree() throws Exception {
		KDTreeNode rootNode = (KDTreeNode) treeMain.getModel().getRoot();
		treeBuilder.refreshTreeNode(treeMain, rootNode, getDefaultFilterForTree());
		treeMain.expandAllNodes(true, rootNode);
	}

	private RESchTemplateCatagoryInfo getSelectedCatagory() {
		KDTreeNode node = getSelectedTreeNode();
		if (node == null) {
			FDCMsgBox.showError("请选择模板分类后再进行操作！");
			abort();
		}

		RESchTemplateCatagoryInfo catagory = (RESchTemplateCatagoryInfo) node.getUserObject();
		return catagory;
	}
	

}