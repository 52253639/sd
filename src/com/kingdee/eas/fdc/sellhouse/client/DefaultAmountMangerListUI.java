/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.event.AncestorEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo;
import com.kingdee.eas.fdc.sellhouse.HoldEnum;
import com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 违约金管理列表界面
 */
public class DefaultAmountMangerListUI extends AbstractDefaultAmountMangerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultAmountMangerListUI.class);
    public DefaultAmountMangerListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionAuditResult.setVisible(false);
		creatMain();
		CalMain();
		CalMain.getStyleAttributes().setLocked(true);
		creatMain.getStyleAttributes().setLocked(true);
		KDTableHelper.setSortedColumn(creatMain, new String[]{"number","projectName","argDate","operator","argformula"});
		KDTableHelper.setSortedColumn(CalMain, new String[]{"number","name","calWay","keepDig","ingWay"});
		
		creatMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		argMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		CalMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.actionCreatDefault.setVisible(false);
		
		CalMain.getColumn("calWay").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
    			if(obj instanceof String){
    				String info = (String)obj;
    				if(CalculateTypedefaultEnum.getEnum(info)==null){
    					return "";
    				}else{
    					return CalculateTypedefaultEnum.getEnum(info).getAlias();
    				}
    			}
    			return super.getText(obj);
    		}
    	});
		CalMain.getColumn("keepDig").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
    			if(obj instanceof String){
    				String info = (String)obj;
    				if(HoldEnum.getEnum(info)==null){
    					return "";
    				}else{
    					return HoldEnum.getEnum(info).getAlias();
    				}
    			}
    			return super.getText(obj);
    		}
    	});
		CalMain.getColumn("ingWay").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
    			if(obj instanceof String){
    				String info = (String)obj;
    				if(IntegerTypeEnum.getEnum(info)==null){
    					return "";
    				}else{
    					return IntegerTypeEnum.getEnum(info).getAlias();
    				}
    			}
    			return super.getText(obj);
    		}
    	});
		
		argMain.getStyleAttributes().setHided(true);
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"contractAmount","argAmount","carryAmount","refDeAmount","subDeAmount"});
	}
    protected ICoreBase getBizInterface() throws Exception {
    	return DefaultAmountMangerFactory.getRemoteInstance();
    }
    
    /**
     * 生成违约金按钮
     */
	public void actionCreatDefault_actionPerformed(ActionEvent e)throws Exception 
	{
		super.actionCreatDefault_actionPerformed(e);
		 DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		 SellProjectInfo sellProject = null; 
		 if (node!=null && node.getUserObject() instanceof SellProjectInfo){
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}
			UIContext context = new UIContext(this);
			context.put("sellProject",sellProject.getId());
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultAmountQueryUI.class.getName(),context,null,OprtState.ADDNEW).show();
		 }
		 else{
			  FDCMsgBox.showInfo(this,"请选择具体销售项目!");
		      SysUtil.abort();
		 }
		
	}
	
	 /**
     * 套打按钮
     */
	public void actionArgPrint_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionArgPrint_actionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			 FDCMsgBox.showInfo(this,"请至少选中一条进行套打!");
		      SysUtil.abort();
		}
	    IRow crow     = tblMain.getRow(rowIndex);
	    String receId = crow.getCell("id").getValue().toString();
		if (receId != null && receId.length() > 0) {
			DefaultAmountMangerPrintDataProvider data = new DefaultAmountMangerPrintDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/default", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
//			super.actionPrint_actionPerformed(e);
		}
	}
	
	 /**
     * 套打预览按钮
     */
	public void actionArgPrintView_actionPerformed(ActionEvent e)throws Exception 
	{
		super.actionArgPrintView_actionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			 FDCMsgBox.showInfo(this,"请至少选中一条进行套打!");
		      SysUtil.abort();
		}
	    IRow crow     = tblMain.getRow(rowIndex);
	    String receId = crow.getCell("id").getValue().toString();
		if (receId != null && receId.length() > 0) {
			DefaultAmountMangerPrintDataProvider data = new DefaultAmountMangerPrintDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/default", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
//			super.actionPrintPreview_actionPerformed(e);
		}
	}
	protected String getTDFileName()
    {
        return "/bim/fdc/sellhouse/default";
    }
	protected String getPrintMoneyName()
	{
	    return "/bim/fdc/sellhouse/default/defaultAmountManger";
	}
	
	protected ArrayList getSelectedIdValues()
	{
	    ArrayList selectList = new ArrayList();
	    int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
	    for(int i=0;i<selectRows.length;i++){
	    	String id= (String)tblMain.getRow(selectRows[i]).getCell("id").getValue();
	    	selectList.add(id);
	    }
	    return selectList;
	}
	DefaultCollectionInfo DCInfo = new DefaultCollectionInfo();
	int row =0;
	/**
     * 计算:CalMain_tableClicked
     */
	protected void CalMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.CalMain_tableClicked(e);
		int rowIndex = CalMain.getSelectManager().getActiveRowIndex();
		row = rowIndex;
	    IRow crow     = CalMain.getRow(rowIndex);
	    String id = (String)crow.getCell("id").getValue();
	    DefaultCollectionInfo dcInfo =DefaultCollectionFactory.getRemoteInstance().getDefaultCollectionInfo(new ObjectUuidPK(id));
	    DCInfo = dcInfo;
	    if(e.getClickCount()==2){
	    	String ids = null;
			int curRows = CalMain.getSelectManager().getActiveRowIndex();//获得当前活动行行号
	        if (CalMain.getRow(curRows)!=null && CalMain.getRow(curRows).getCell("id") != null&& CalMain.getRow(curRows).getCell("id").getValue() != null){
	        	ids = CalMain.getRow(curRows).getCell("id").getValue().toString();
	        }
		    UIContext context = new UIContext(this);
		    context.put("ID",ids);
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultCollectionEditUI.class.getName(),context,null,OprtState.VIEW).show();
		    CalMain();
	    }
	}

	protected void creatMain_tableClicked(KDTMouseEvent e) throws Exception 
	{
		super.creatMain_tableClicked(e);
		if(e.getClickCount()==2){   //判断是否双击
			String id = null;
			int curRow = creatMain.getSelectManager().getActiveRowIndex();//获得当前活动行行号
	        if (creatMain.getRow(curRow)!=null && creatMain.getRow(curRow).getCell("id") != null&& creatMain.getRow(curRow).getCell("id").getValue() != null){
		         id = creatMain.getRow(curRow).getCell("id").getValue().toString();
	        }
		    UIContext context = new UIContext(this);
		    context.put("ID",id);
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultAmountCreatEditUI.class.getName(),context,null,OprtState.VIEW).show();
		    this.refresh(null);
		}
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
	/**
     * 新增违约金计算
     */
	public void actionAddCal_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAddCal_actionPerformed(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		SellProjectInfo sellProject = null; 
		if (node!=null && node.getUserObject() instanceof SellProjectInfo){
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}
			UIContext context = new UIContext(this);
			context.put("sellProject",sellProject.getId());
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultCollectionEditUI.class.getName(),context,null,OprtState.ADDNEW).show();
		    CalMain();
		}
		 else{
			  FDCMsgBox.showInfo(this,"请先选择具体销售项目再新增!");
		      SysUtil.abort();
		 }
	}
	/**
     * 修改违约金计算
     */
	public void actionEditCal_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionEditCal_actionPerformed(e);
		int rowIndex = CalMain.getSelectManager().getActiveRowIndex();
		if(rowIndex ==-1){
			  FDCMsgBox.showInfo(this,"请先选中一条违约金计算公式进行修改!");
		      SysUtil.abort();
		}
		if(DCInfo!=null){
			UIContext context = new UIContext(this);
			context.put("ID",DCInfo.getId());
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultCollectionEditUI.class.getName(),context,null,OprtState.EDIT).show();
		    CalMain();
		}
		
	}
	/**
     * 删除违约金计算
     */
	public void actionRemoveCal_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionRemoveCal_actionPerformed(e);
		int rowIndex = CalMain.getSelectManager().getActiveRowIndex();
		if(rowIndex ==-1){
			  FDCMsgBox.showInfo(this,"请先选中一条违约金计算公式进行删除!");
		      SysUtil.abort();
		}
		if(DCInfo!=null){
			  DefaultCollectionFactory.getRemoteInstance().delete(new ObjectUuidPK(DCInfo.getId()));
			  CalMain();
		}
	}
	/**
     * 树事件
     */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
		creatMain();
		CalMain();
	}
	/**
     * 左树
     */
	 protected void initTree() throws Exception{
		 this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		 this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		 this.treeMain.setSelectionRow(0);
	 }
	 
	 /**
	 * 违约金生成展现
	 */
	 public void creatMain()throws Exception{
		 creatMain.removeRows();
		 DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		 String allSpIdStr = "'null'";
		 if (node.getUserObject() instanceof SellProjectInfo){
			 allSpIdStr=FDCTreeHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)node.getUserObject()));
		 }else if (node.getUserObject() instanceof OrgStructureInfo){
			 allSpIdStr=FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		 }
		 if(node==null||allSpIdStr.trim().length()==0){
			 return;
		 }
		 String cSql=" select  dc.fnumber as 单据编号,sp.fname_l2 as 项目名称, dc.FDefCalDate  as 违约金计算日期," +
		 		     " df.fname_l2 as 违约金公式,ps.fname_l2 as 经办人,dc.Fexplain as 说明,dc.fid as FID" +
		 		     " from T_SHE_DefaultAmountCreat  dc" +
		 		     " left join T_SHE_SellProject  sp on sp.fid = dc.FProjectID" +
		 		     " left join T_SHE_DefaultCollection df on df.fid = dc.FDefCalFormulaID" +
		 		     " left join T_pm_User ps on ps.fid = dc.FCreatorID where dc.FProjectID in ("+allSpIdStr+")";
		 FDCSQLBuilder cdb = new FDCSQLBuilder();
	     cdb.appendSql(cSql);
	     IRowSet crowset = cdb.executeQuery();
	     while(crowset.next()){
	    	 IRow crow = this.creatMain.addRow();
	 	     crow.getCell("projectName").setValue(crowset.getString("项目名称"));
	 	     crow.getCell("argDate").setValue(crowset.getDate("违约金计算日期"));
	 	     crow.getCell("argformula").setValue(crowset.getString("违约金公式"));
	         crow.getCell("operator").setValue(crowset.getString("经办人"));
	         crow.getCell("explain").setValue(crowset.getString("说明"));
	         crow.getCell("id").setValue(crowset.getString("FID"));
	     }
	 }
	 
	 /**
	  * 违约金计算设置
	 */
    public void CalMain()throws Exception{
    	CalMain.removeRows();
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		String allSpIdStr = "'null'";
		if (node.getUserObject() instanceof SellProjectInfo){
			allSpIdStr=FDCTreeHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)node.getUserObject()));
		}else if (node.getUserObject() instanceof OrgStructureInfo){
			allSpIdStr=FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		}
		if(node==null||allSpIdStr.trim().length()==0){
			return;
	    }
    	String sql = " select dc.Fnumber as Fnumber, dc.Fname_l2 as Fname_l2, dc.FCalculateType as FCalculateType," +
    			" dc.FHold as FHold,dc.FIntegerType as FIntegerType, dc.fremark as fremark,dc.FProjectID as FProjectID," +
    			" dc.FID as FID from T_SHE_DefaultCollection dc" +
    			" left join T_SHE_SellProject sp on  dc.FProjectID = sp.FID where dc.FProjectID in ("+allSpIdStr+")";
        FDCSQLBuilder db = new FDCSQLBuilder();
        db.appendSql(sql);
        IRowSet rowset = db.executeQuery();
        while(rowset.next()){
	        IRow row = this.CalMain.addRow();
	        row.getCell("number").setValue(rowset.getString("Fnumber"));
	        row.getCell("name").setValue(rowset.getString("Fname_l2"));
          	row.getCell("calWay").setValue(rowset.getString("FCalculateType"));
         	row.getCell("keepDig").setValue(rowset.getString("FHold"));
        	row.getCell("ingWay").setValue(rowset.getString("FIntegerType"));
        	row.getCell("remark").setValue(rowset.getString("fremark"));
        	row.getCell("projectId").setValue(rowset.getString("FProjectID"));
        	row.getCell("id").setValue(rowset.getString("FID"));
         }
	 }
	 protected String getEditUIName() {
		return DefaultAmountMangerEditUI.class.getName();
	}
    /**
	 * 选中节点时过滤
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
			EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(node==null||allSpIdStr.trim().length()==0){
			allSpIdStr = "'null'"; 
		}
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)node.getUserObject()),CompareType.INCLUDE));	
				}else if (node.getUserObject() instanceof OrgStructureInfo){
					filter.getFilterItems().add(new FilterItemInfo("project.id", allSpIdStr,CompareType.INNER));
				}
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			
		}catch (Exception e)
		{
			handleException(e);
		}
		
		return super.getQueryExecutor(pk, viewInfo);
	}
	
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
//    	argMain.removeRows();
//        int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
//        if(rowIndex==-1){
//        	return;
//        }
//   	    IRow prow= tblMain.getRow(rowIndex);
//   	    String id =prow.getCell("id").getValue().toString();
//   	    DefaultAmountMangerInfo EnterInfo =DefaultAmountMangerFactory.getRemoteInstance().getDefaultAmountMangerInfo(new ObjectUuidPK(id.toString()));
//   	    DefaultAmountMangerEntryCollection EntryColl =EnterInfo.getEntry(); 
//   	    CRMHelper.sortCollection(EntryColl, "seq", true);
//   		for(int i=0;EntryColl!=null&&i<EntryColl.size();i++){
//   			DefaultAmountMangerEntryInfo  EntryInfo =EntryColl.get(i);
//   			if(EntryColl!=null){
//				 EntryInfo =DefaultAmountMangerEntryFactory.getRemoteInstance().getDefaultAmountMangerEntryInfo(new ObjectUuidPK(EntryInfo.getId()));
//				 IRow row = argMain.addRow();
//				 argMain.getStyleAttributes().setLocked(true);
//				 if(EntryInfo.getMoneyDefine()!=null){
//					 MoneyDefineInfo monInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(EntryInfo.getMoneyDefine().getId()));
//					 row.getCell("moneyDefine").setValue(monInfo); 
//				 }
//				 if(EntryInfo.getAppDate()!=null){
//					 row.getCell("appDate").setValue(EntryInfo.getAppDate()); 
//				 }
//				 if(EntryInfo.getAppAmount()!=null){
//					 row.getCell("appAmount").setValue(EntryInfo.getAppAmount()); 
//				 }
//				 if(EntryInfo.getActAmount()!=null){
//					 row.getCell("actAmount").setValue(EntryInfo.getActAmount()); 
//				 }
//				 if(EntryInfo.getArgDays()!=0){
//					 row.getCell("argDays").setValue(Integer.toString(EntryInfo.getArgDays())); 
//				 }
//				 if(EntryInfo.getArgAmount()!=null){
//					 row.getCell("argAmount").setValue(EntryInfo.getArgAmount()); 
//				 }
//				 if(EntryInfo.getReferAmount()!=null){
//					 row.getCell("referAmount").setValue(EntryInfo.getReferAmount()); 
//				 }
//			}else{
//				return;
//			}
//	   }
	}
    protected void refresh(ActionEvent e) throws Exception{
		boolean isSelect=false;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			isSelect=true;
		}
		this.tblMain.removeRows();
        if(this.tblMain.getRowCount()==0){
        	argMain.removeRows();
        }
        if(isSelect){
        	tblMain.getSelectManager().select(0, 0);
        }
        creatMain();
		CalMain();
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
}