/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionCollection;
import com.kingdee.eas.basedata.assistant.RegionFactory;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 市场调查管理  yinshujuan
 */
public class MarketSurveyListUI extends AbstractMarketSurveyListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5051780918854751723L;
	private static final Logger logger = CoreUIObject.getLogger(MarketSurveyListUI.class);
    /**
     * output class constructor
     */
    public MarketSurveyListUI() throws Exception
    {
        super();
    }

/**
 * 
 * 描述：重载父类方法，处理打开新建界面在页片中打开
 * @author:Liqi
 * @see com.kingdee.eas.framework.client.ListUI#getEditUIModal()
 */
protected String getEditUIModal()
{
	String openModel = UIConfigUtility.getOpenModel();
    if (openModel != null)
    {
        return UIFactoryName.NEWWIN;
    }
    else
    {
        return UIFactoryName.NEWTAB;
    }
}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		initTree();
		expandTreeNodes();
		this.actionAuditResult.setVisible(false);
	}
	/**
	 * 展开树到省级别
	 */
	private void expandTreeNodes(){
		TreeModel tree = treeMain.getModel();
		KDTreeNode root = (KDTreeNode) tree.getRoot();
		treeMain.setSelectionNode(root);        //默认选择根节点
		//如果不是子节点，则展开此节点
		if(root.getChildCount()>0){
			treeMain.expandPath(new TreePath(root.getPath()));
		}
	}

	/**
	 * 创建树
	 * @throws Exception
	 */
	private void initTree() throws Exception{
		ProvinceCollection provinces = ProvinceFactory.getRemoteInstance().getProvinceCollection();
		ProvinceInfo provinceInfo = null;  //省
		BOSUuid provinceID = null;
//		BOSUuid cityID = null;
		CityCollection citys = null;
		CityInfo cityInfo = null;      //市
//		RegionCollection regions = null;
//		RegionInfo regionInfo = null;    //县
		KDTreeNode root = new KDTreeNode("省市");  //树的根节点
		
		KDTreeNode provinceNode = null;     //省节点
		KDTreeNode cityNode = null;       //市节点
//		KDTreeNode regionNode = null;     //县节点
		treeMain.setModel(new DefaultTreeModel(root));
		for(int i = 0;i<provinces.size();i++){
			provinceInfo = provinces.get(i);
			provinceNode = new KDTreeNode(provinceInfo);      //创建省节点
			root.add(provinceNode);
			provinceID = provinceInfo.getId();
			citys = getCitys(provinceID);
			if(citys == null)continue;
			for(int j = 0;j<citys.size();j++){
				cityInfo = citys.get(j);
				if(cityInfo != null){
					cityNode = new KDTreeNode(cityInfo);       //创建市节点
					provinceNode.add(cityNode);
//					cityID = cityInfo.getId();
//					regions = getRegions(cityID);
//					if(regions == null)continue;
//					for(int k = 0;k<regions.size();k++){
//						regionInfo = regions.get(k);
//						if(regionInfo != null){
//							regionNode = new KDTreeNode(regionInfo);     //创建县节点
//							cityNode.add(regionNode);
//						}
//						
//					}
				}
			}
			
		}
	}
	/**
	 * 获取省下的所有城市
	 * @param id
	 * @return
	 * @throws BOSException
	 */
	private CityCollection getCitys(BOSUuid provinceID) throws BOSException{
		CityCollection collection = null;
//		SelectorItemCollection sic = new SelectorItemCollection();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("province.id",provinceID,CompareType.EQUALS));
		evi.setFilter(filterInfo);
		collection = CityFactory.getRemoteInstance().getCityCollection(evi);
		return collection;
	}
	
	/**
	 * 获取市下所有的县
	 * @param cityID
	 * @return
	 * @throws BOSException
	 */
	private RegionCollection getRegions(BOSUuid cityID) throws BOSException{
		RegionCollection collection = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("city.id",cityID,CompareType.EQUALS));
		evi.setFilter(filterInfo);
		collection = RegionFactory.getRemoteInstance().getRegionCollection(evi);
		return collection;
	}
	
	/**
	 * 选择的树节点改变
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		if (node == null) 
		{
			return;
		}
		tblMain.removeRows();
		
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) 
    {
		try 
		{
			viewInfo =  new EntityViewInfo();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if(node != null && node.getUserObject() instanceof RegionInfo){
				RegionInfo region = (RegionInfo)node.getUserObject();
				BOSUuid regionID = region.getId();
				filter.getFilterItems().add(new FilterItemInfo("region.id",regionID,CompareType.EQUALS));
			}
			if(node != null && node.getUserObject() instanceof CityInfo){
				CityInfo city = (CityInfo)node.getUserObject();
				BOSUuid cityID = city.getId();
				filter.getFilterItems().add(new FilterItemInfo("area.id",cityID,CompareType.EQUALS));
			}
			if(node != null && node.getUserObject() instanceof ProvinceInfo){
				ProvinceInfo province = (ProvinceInfo)node.getUserObject();
				BOSUuid provinceID = province.getId();
				filter.getFilterItems().add(new FilterItemInfo("province.id",provinceID,CompareType.EQUALS));
			}
			if (viewInfo.getFilter() != null) 
			{
				try
				{
					viewInfo.getFilter().mergeFilter(filter, "and");
				} catch (BOSException e)
				{
				}
			} else 
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e) 
		{
			MsgBox.showError("出现错误！");
			logger.info("获取查询器失败！");
//			SysUtil.abort(e);
			this.handleException(e);
		}
		IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
		exec.option().isAutoTranslateEnum = false;   //此句的意思是：枚举值不翻译，直接显示数据库中的值
		return exec;
	}
	
	  /**
	   * 将选择的省市县传给editUI
	   */
	  protected void prepareUIContext(UIContext uiContext, ActionEvent e)
		{
			// TODO Auto-generated method stub
			super.prepareUIContext(uiContext, e);
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeMain.getLastSelectedPathComponent();
			ProvinceInfo province = null;
			CityInfo city = null;
			RegionInfo region = null;
			if(node.getUserObject() instanceof CityInfo){
				city = (CityInfo)node.getUserObject();
				DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) node.getParent();
				province = (ProvinceInfo) parentNode.getUserObject();
				uiContext.put("city", city);
				uiContext.put("province", province);
				uiContext.put("nodeType", "city");
			}
			if(node.getUserObject() instanceof RegionInfo){
				region = (RegionInfo)node.getUserObject();
				DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) node.getParent();
				city = (CityInfo) parentNode.getUserObject();
				DefaultMutableTreeNode parentParentNode= (DefaultMutableTreeNode) node.getParent().getParent();
				province = (ProvinceInfo) parentParentNode.getUserObject();
				uiContext.put("region", region);
				uiContext.put("city", city);
				uiContext.put("province", province);
				uiContext.put("nodeType", "region");
			}
			//处理香港澳门等自治区，是省节点，但没有市节点
			if(node.getUserObject() instanceof ProvinceInfo && node.isLeaf()){
				province = (ProvinceInfo)node.getUserObject();
				uiContext.put("province", province);
				uiContext.put("nodeType", "province");
			}
		}
	  /**
	   * 控制只能在集团组织下进行操作
	   */
	  private void setAddCtrl() {
		  OrgUnitInfo  currOrgUnit =  SysContext.getSysContext().getCurrentOrgUnit();
		  String cuid=OrgConstants.DEF_CU_ID;
	    	if(currOrgUnit != null){
		    	if(!cuid.equalsIgnoreCase(currOrgUnit.getCU().getId().toString())) {
		    		MsgBox.showError("在当前组织下不能进行该操作！");
		    		SysUtil.abort();
		    	}
	    	}
	    	
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
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(node == null) {
    		MsgBox.showWarning("请选择市场调查管理所在的市！");
    		return;
    	}
		if(node.getUserObject() instanceof ProvinceInfo && !node.isLeaf()){
			MsgBox.showWarning("请选择市场调查管理所在的市！");
			return;
		}
		if(!(node.getUserObject() instanceof ProvinceInfo || node.getUserObject() instanceof CityInfo || node.getUserObject() instanceof RegionInfo)){
			MsgBox.showWarning("请选择市场调查管理所在的市！");
			return;
		}
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }

    /**
     * output actionTDPrint_actionPerformed
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrint_actionPerformed(e);
    }

    /**
     * output actionTDPrintPreview_actionPerformed
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrintPreview_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.MarketSurveyFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.MarketSurveyInfo objectValue = new com.kingdee.eas.fdc.market.MarketSurveyInfo();
		
        return objectValue;
    }

}
