/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
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
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商序时薄
 *		
 * @author		杜余
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class SupplierStockListUI extends AbstractSupplierStockListUI
	{
		/**   */
	private static final long serialVersionUID = -5002989020704597247L;
		private static final Logger logger = CoreUIObject.getLogger(SupplierStockListUI.class);
		/*
		 * 通用查询窗口
		 */
		private CommonQueryDialog commonQueryDialog = null;
	
	/**
	 * output class constructor
	 */
	public SupplierStockListUI() throws Exception
	{
	    super();
	}
	 private String getResource(String msg) {
			return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
	{
		
		tblMain.getSelectManager();
		if(e.getType() == 1 && e.getButton() == 1)
        {
            if(e.getType() == 0){
            	return;
            }
            /*
             * 超链接.....
             */
            if(e.getColIndex()==7){
            	/*
            	 * 打开入围历史前,判断权限
            	 */
        		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
        				new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client.SupplierStockListUI"),
        				new MetaDataPK("ActionEnterHis") )){
        			MsgBox.showInfo(this,getResource("orgEnterHis")+SysContext.getSysContext().getCurrentOrgUnit().getName()+getResource("orgEnterHis2"));
        			abort();
        		}
            	openEnterHisWindow();
            }else if(e.getColIndex()==8){
            	/*
            	 * 打开签约历史前,判断权限
            	 */
            	if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
        				new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client.SupplierStockListUI"),
        				new MetaDataPK("ActionConstractHis") )){
        			MsgBox.showInfo(this,getResource("orgEnterHis")+SysContext.getSysContext().getCurrentOrgUnit().getName()+getResource("orgConHis"));
        			abort();
        		}
            	openConstractHisWindow();
            }else if(e.getColIndex()==9) {
				/*
				 * 打开评审历史前,判断权限
				 */
            	if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
        				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
        				new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client.SupplierStockListUI"),
        				new MetaDataPK("ActionAppraiseHis") )){
        			MsgBox.showInfo(this,getResource("orgEnterHis")+SysContext.getSysContext().getCurrentOrgUnit().getName()+getResource("orgAppHis"));
        			abort();
        		}
            	openAppHisWindow();
			}else if (e.getClickCount() == 2){
				/*
				 * 如果双击其它地方,则掉用其原有的双击打开本条信息的编辑界面
				 */
				ActionEvent evt = new ActionEvent(btnView, 0, "Double Clicked");
	            ItemAction actView = getActionFromActionEvent(evt);
	            if(actView.isEnabled()){
	            	 actView.actionPerformed(evt);
	             }
			}
        }
	}

	/**
	 * 
	 * @description 资格考察
	 *              <p>
	 *              修复Bug：每次打开供应商资格审查都会新增记录，不能查看和修改之前的记录。By Owen_wen 2011-02-23
	 * @author 杜余
	 * @createDate 2010-12-9
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionReview_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionReview_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
	    uiContext.put("SupplierInfo", info);
	    
	    IUIWindow uiWindow = null;
	    EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("Supplier.ID", info.getId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED, CompareType.NOTEQUALS));
		view.setFilter(filter);
		FDCSplQualificationAuditBillCollection splQualificationAuditBillInfoColl = FDCSplQualificationAuditBillFactory.getRemoteInstance()
				.getFDCSplQualificationAuditBillCollection(view);
		if (splQualificationAuditBillInfoColl.size() > 0) {
			uiContext.put(UIContext.ID, splQualificationAuditBillInfoColl.get(0).getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierQuaReviewEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		} else {
			uiContext.put(UIContext.ID, null);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierQuaReviewEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		}
		uiWindow.show();
	}

	/**
	 * 
	 * @description 履约评估
	 *              <p>
	 *              修复Bug：每次打开履约评估都会新增记录，不能查看和修改之前的记录。By Owen_wen 2011-02-28
	 * @author 杜余
	 * @createDate 2010-12-9
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionConstract_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionConstract_actionPerformed(ActionEvent e) throws Exception
	{
	    checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    if(!checkIsGrade(info)){
	    	KDWorkButton kw = (KDWorkButton) e.getSource();
	    	MsgBox.showWarning(this,getResource("reviewPass")+kw.getText()+getResource("sure"));
	    	abort();
	    } 
	    
	    UIContext uiContext = new UIContext(this);
		uiContext.put("SupplierInfo", info);
		
		IUIWindow uiWindow = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("Supplier.ID", info.getId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED, CompareType.NOTEQUALS));
		view.setFilter(filter);
		FDCSplKeepContractAuditBillCollection splKeepContractAuditBillInfoColl = FDCSplKeepContractAuditBillFactory.getRemoteInstance()
				.getFDCSplKeepContractAuditBillCollection(view);
		if (splKeepContractAuditBillInfoColl.size() > 0) {
			uiContext.put(UIContext.ID, splKeepContractAuditBillInfoColl.get(0).getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierPerformEvalulationEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		} else {
			uiContext.put(UIContext.ID, null);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierPerformEvalulationEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		}
		uiWindow.show();
	}

	/**
	 * 
	 * @description 定期评审 修复Bug：每次打开定期评审都会新增记录，不能查看和修改之前的记录。By Owen_wen
	 *              2011-02-28
	 * @author 杜余
	 * @createDate 2010-12-9
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionAppraise_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAppraise_actionPerformed(ActionEvent e) throws Exception
	{
	    checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    if(!checkIsGrade(info)){
	    	KDWorkButton kw = (KDWorkButton) e.getSource();
	    	MsgBox.showWarning(this,getResource("reviewPass")+kw.getText()+getResource("sure"));
	    	abort();
	    }
	    
	    UIContext uiContext = new UIContext(this);
		uiContext.put("SupplierInfo", info);
		
		IUIWindow uiWindow = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("Supplier.ID", info.getId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED, CompareType.NOTEQUALS));
		view.setFilter(filter);
		FDCSplPeriodAuditBillCollection splPeriodAuditBillColl = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillCollection(view);
		if (splPeriodAuditBillColl.size() > 0) {
			uiContext.put(UIContext.ID, splPeriodAuditBillColl.get(0).getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierEvaluateEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		} else {
			uiContext.put(UIContext.ID, null);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			uiWindow = uiFactory.create(SupplierEvaluateEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		}
		uiWindow.show();
	}
	
	/**
	 * 
	 * @description		入围历史
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionEnterHis_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionEnterHis_actionPerformed(ActionEvent e) throws Exception
	{
	    openEnterHisWindow();
	}
	
	private void openEnterHisWindow() throws EASBizException, BOSException{
		checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
			IUIWindow uiWindow = uiFactory.create(EnterHistoryUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 
	 * @description		签约历史
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionConstractHis_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionConstractHis_actionPerformed(ActionEvent e) throws Exception
	{
		openConstractHisWindow();
	}
	
	private void openConstractHisWindow() throws EASBizException, BOSException{
		checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
			IUIWindow uiWindow = uiFactory.create(SignHistoryUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 
	 * @description		评审历史
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionAppraiseHis_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAppraiseHis_actionPerformed(ActionEvent e) throws Exception
	{
		openAppHisWindow();
	}
	
	private void openAppHisWindow() throws EASBizException, BOSException{
		checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
			IUIWindow uiWindow = uiFactory.create(AppraiseHistoryUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 
	 * @description		材料信息
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionMaterialInfo_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionMaterialInfo_actionPerformed(ActionEvent e) throws Exception
	{
	    super.actionMaterialInfo_actionPerformed(e);
	    checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
			IUIWindow uiWindow = uiFactory.create(MaterialInfoListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	/**
	 * 
	 * @description		信息变更
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionInfoChange_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionInfoChange_actionPerformed(ActionEvent e) throws Exception
	{
	    checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
			IUIWindow uiWindow = uiFactory.create(SupplierStockInfoEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 
	 * @description		变更历史
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#actionChangeHis_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionChangeHis_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
	    SupplierStockInfo info = getSelectedInfo();
	    UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("SupplierInfo", info);		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
 		IUIWindow uiWindow = uiFactory.create(SupplierStockInfoFullUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierStockInfo();
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierStockFactory.getRemoteInstance();
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected String getEditUIName() {
		return NewSupplierStockEditUI.class.getName();
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		SupplierTypeTreeBuilder.builderTree(kDSupplierTypeTree);
		initButtion();
		initStateButtion();
		initTable();
		unitTable();
		setHisZero();
	}
	public void loadFields() {
		super.loadFields();
		setHisZero();
	}
	/**
	 * 
	 * @description		初始按钮
	 * @author			杜余		
	 * @createDate		2010-12-9
	 */
	private void initButtion() {
		this.review.setEnabled(true);
		this.constract.setEnabled(true);
		this.appraise.setEnabled(true);
		this.infoChange.setEnabled(true);
		this.enterHis.setEnabled(true);
		this.constractHis.setEnabled(true);
		this.appraiseHis.setEnabled(true);
		this.materialInfo.setEnabled(true);
		this.menuBiz.setVisible(false);
		this.changeHis.setEnabled(true);
		this.changeHis.setVisible(true);
		this.kDMenuReview.setEnabled(true);
		this.kDMenuReview.setIcon(EASResource.getIcon("imgTbtn_demandcollateresult"));
		this.kDMenuConstract.setEnabled(true);
		this.kDMenuConstract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		
		this.kDMenuAppraise.setEnabled(true);
		this.kDMenuAppraise.setIcon(EASResource.getIcon("imgTbtn_alreadycollate"));
		
		this.kDMenuInfoChange.setEnabled(true);
		this.kDMenuInfoChange.setIcon(EASResource.getIcon("imgTbtn_cancelcollocate"));
		
		this.kDMenuEnterHis.setEnabled(true);
		this.kDMenuEnterHis.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
		
		this.kDMenuConstractHis.setEnabled(true);
		this.kDMenuConstractHis.setIcon(EASResource.getIcon("imgTbtn_estatesetting"));
		
		this.kDMenuAppraiseHis.setEnabled(true);
		this.kDMenuAppraiseHis.setIcon(EASResource.getIcon("imgTbtn_makeknown"));
		
		this.kDMenuMaterialInfo.setEnabled(true);
		this.kDMenuMaterialInfo.setIcon(EASResource.getIcon("imgTbtn_copyline"));
		
		this.kDMenuChangeHis.setEnabled(true);
		this.kDMenuChangeHis.setIcon(EASResource.getIcon("imgTbtn_listaccount"));
		
		this.kDMenuState.setEnabled(true);
		this.kDMenuState.setIcon(EASResource.getIcon("imgTbtn_hiderule"));
	}
	/**
	 * 
	 * @description		得到所选supplierStock对象字段字,供传到其它页面调用
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @return SupplierStockInfo
	 * @throws EASBizException
	 * @throws BOSException 
	 */
	protected SupplierStockInfo getSelectedInfo() throws EASBizException, BOSException
	{
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
	
		if(infoId != null)
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));	        
	        view.getSelector().add(new SelectorItemInfo("province.*"));      
	        view.getSelector().add(new SelectorItemInfo("city.*"));
	        view.getSelector().add(new SelectorItemInfo("supplierType.*"));
	        view.getSelector().add(new SelectorItemInfo("linkPerson.*"));    
	        view.getSelector().add(new SelectorItemInfo("yearSale.*"));
	        view.getSelector().add(new SelectorItemInfo("aptitudeFile.*"));
	        view.getSelector().add(new SelectorItemInfo("achievement.*"));
	        view.getSelector().add(new SelectorItemInfo("supplierServiceType.*"));
	        view.getSelector().add(new SelectorItemInfo("supplierServiceType.serviceType.*"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", infoId.toString()));
	
			view.setFilter(filter);
	
			SupplierStockCollection supInfo = SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
	
			if(supInfo.size() > 0)
			{
				return supInfo.get(0);
			}
		}
	
		return null ;
	}
	/**
	 * 
	 * @description		获取所选树节点
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param tree	kdtree
	 * @return DefaultKingdeeTreeNode	树节点
	 */
	private DefaultKingdeeTreeNode getSelectNode(KDTree tree){
		if(tree.getLastSelectedPathComponent() != null){
			return (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @description		树节点变化得到过滤filter
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @return	FilterInfo	filter
	 * @throws Exception 
	 */
	private FilterInfo getTreeFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode TypeNode = getSelectNode(kDSupplierTypeTree);
		Object TypeInfo = null;
		/*
		 * 是否选中
		 */
		if(TypeNode != null && TypeNode.getUserObject() != null){
			TypeInfo = TypeNode.getUserObject();
			kDSupplierCont.setTitle(TypeNode.getText());
		}
		/*
		 * 选择,按longnumber过滤 
		 */
		if (TypeInfo instanceof CSSPGroupInfo) {
		    String longNumber = ((CSSPGroupInfo)TypeInfo).getLongNumber();
		    filter.getFilterItems().add(new FilterItemInfo("supplierType.longNumber", longNumber+"!%",
					CompareType.LIKE));
		    filter.getFilterItems().add(new FilterItemInfo("supplierType.longNumber", longNumber));
		    filter.setMaskString("#0 or #1");
		}
		return filter;
	}
	/**
	 * 
	 * @description		查询
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param queryPK
	 * @param viewInfo
	 * @return									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.framework.client.ListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK, com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		/*
		 * 树filter
		 */
		FilterInfo treeFilter = null;
		/*
		 * 状态filter
		 */
		FilterInfo stateFilter = null;
		viewInfo.clear();
		/*
		 * 如果是通用查询则把树过滤和其它过滤清了
		 */
		if(initCommonQueryDialog() !=null){
			if(commonQueryDialog.getCommonFilter() != null && commonQueryDialog.getCommonFilter().getFilterItems().size()!=0){
				FilterInfo filterInfo = commonQueryDialog.getCommonFilter();
				FilterInfo cuFilterInfo = new FilterInfo();
				cuFilterInfo.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
				cuFilterInfo.getFilterItems().add( // 还要显示下级CU中的数据 By Owen_wen 2011-02-23
						new FilterItemInfo("CU.longNumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber() + "!%", CompareType.LIKE));
				cuFilterInfo.setMaskString("#0 or #1");
				stateFilter = getStateFilter();
					try {
                        if(stateFilter!=null){
							filterInfo.mergeFilter(stateFilter, "AND");
						}
                        /*
                         * 一层一层向外merge
                         */
						filterInfo.mergeFilter(cuFilterInfo, "AND");
					} catch (BOSException e) {
						handUIException(e);
					}
					viewInfo.setFilter(filterInfo);
				return super.getQueryExecutor(queryPK, viewInfo); 
			}
		}
		try {
			if(getTreeFilter()!=null){
				treeFilter = getTreeFilter();
			}
		} catch (Exception e1) {
			handUIException(e1);
		}
		/*
		 * CU隔离过滤
		 */

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		filter.getFilterItems().add( // 还要显示下级CU中的数据 By Owen_wen 2011-02-23
				new FilterItemInfo("CU.longNumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber() + "!%", CompareType.LIKE));
		filter.setMaskString("#0 or #1");
		stateFilter = getStateFilter();
		try {
			filter.mergeFilter(stateFilter, "AND");
		} catch (BOSException e) {
			handUIException(e);
		}
		if(null != treeFilter){
			try {
				filter.mergeFilter(treeFilter, "AND");
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		viewInfo.setFilter(filter);
		/*
		 * 初始状态下拉菜单值,防止后面重复引用
		 */
		stataValue = null ;
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	/**
	 * 
	 * @description		树选择值改变后,重新初始filter,执行查询
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#kDSupplierTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void kDSupplierTypeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.kDSupplierTypeTree_valueChanged(e);
		if(null != initCommonQueryDialog().getCommonFilterPanel()){
			initCommonQueryDialog().getCommonFilterPanel().clear();
		}
		tblMain.removeRows();
		unitTable();
	}
	public void onShow() throws Exception {
		super.onShow();
		/*
		 * 去掉启用禁用这两个东西,在其它地方隐藏不了
		 */
		this.btnCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
		unitTable();
		setHisZero();
	}
	/**
	 * @description		
	 * @author			wangweiqiang	
	 * @createDate		2011-11-25				
	 */
	private void setHisZero() {
		if (tblMain.getRowCount() > 0) {
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				IRow row = tblMain.getRow(i);
				Object enterHis = row.getCell("enterHis").getValue();
				Object constractHis = row.getCell("constractHis").getValue();
				if (enterHis == null) {
					row.getCell("enterHis").setValue("0");
				}
				if (constractHis == null) {
					row.getCell("constractHis").setValue("0");
				}
			}
		}
	}
	/**
	 * 
	 * @description		初始工具栏上的状态下拉迎迎按钮
	 * @author			杜余		
	 * @createDate		2010-12-9
	 */
	private void initStateButtion() {
		try {
			GradeSetUpCollection sc = null;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
		    FilterInfo info = new FilterInfo();
		    info.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		    view.setFilter(info);
		    sc = GradeSetUpFactory.getRemoteInstance().getGradeSetUpCollection(view);
		    /*
		     * 因菜单栏与工具栏必须都加上menuitem,各用一个,否则只会显示在最后一次add里
		     */
		    KDMenuItem stateMenuItem = new KDMenuItem();
		    KDMenuItem stateMenuItem2 = new KDMenuItem();
		    stateMenuItem.setText(getResource("approve"));
		    stateMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					KDMenuItem km = (KDMenuItem)e.getSource();
					stataValue = km.getText();
					tblMain.removeRows();
					unitTable();
				}});
		    stateMenuItem2.setText(getResource("approve"));
		    stateMenuItem2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					KDMenuItem km = (KDMenuItem)e.getSource();
					stataValue = km.getText();
					tblMain.removeRows();
					unitTable();
				}});
		    kDMenuState.add(stateMenuItem);
			btnSupplierState.addAssistMenuItem(stateMenuItem2);
			if(sc.size()>0){
				for(int i = 0 ; i<sc.size() ; i++){
					KDMenuItem kk = new KDMenuItem();
					KDMenuItem kk1 = new KDMenuItem();
					kk1.setText(sc.get(i).getName());
					kk1.setName(sc.get(i).getName());
					kk1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							KDMenuItem km = (KDMenuItem)e.getSource();
							stataValue = km.getText();
							tblMain.removeRows();
							unitTable();
						}});
					
					kk.setText(sc.get(i).getName());
					kk.setName(sc.get(i).getName());
					kk.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							KDMenuItem km = (KDMenuItem)e.getSource();
							stataValue = km.getText();
							tblMain.removeRows();
							unitTable();
						}});
					kDMenuState.add(kk1);
					btnSupplierState.addAssistMenuItem(kk);
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}
	/*
	 * 状态值,触发下拉按钮时赋值,并传给filter,过滤右边的结果
	 */
	protected static String stataValue = null;
	/**
	 * 
	 * @description		取得状态过滤filter
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @return FilterInfo
	 */
	private FilterInfo getStateFilter() {
		if(null == stataValue || stataValue.equals("")){
			return null;
		}else{
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("supplierServiceType.state",stataValue));
			/*
			 * 清空状态值
			 */
			stataValue = null;
			return filter;
		}
	}
	/**
	 * 
	 * @description		初始表格
	 * @author			杜余		
	 * @createDate		2010-12-9
	 */
	private void initTable() {
		tblMain.checkParsed();
		/*
		 * 搞超链接...  数字靠右. 数字格式
		 */
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		tblMain.getColumn("constractHis").getStyleAttributes().setNumberFormat("#0");
		tblMain.getColumn("constractHis").getStyleAttributes().setUnderline(true);
		tblMain.getColumn("constractHis").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("constractHis").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		tblMain.getColumn("enterHis").getStyleAttributes().setNumberFormat("#0");
		tblMain.getColumn("enterHis").getStyleAttributes().setUnderline(true);
		tblMain.getColumn("enterHis").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("enterHis").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		
		tblMain.getColumn("appraiseHis").getStyleAttributes().setNumberFormat("#0");
		tblMain.getColumn("appraiseHis").getStyleAttributes().setUnderline(true);
		tblMain.getColumn("appraiseHis").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("appraiseHis").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		KDTextField text = new KDTextField();
//		text.setToolTipText("AAAAAA");
//		tblMain.getColumn("appraiseHis").setEditor(new KDTDefaultCellEditor(text));
	}
	public void unitTable() {
		KDTMergeManager mm =tblMain.getMergeManager();
		int longth = tblMain.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的行,按id来区分是否为相同记录
			 */
			  row = tblMain.getRow(i);
			  String type = (String)row.getCell("id").getValue();
			  if(map.get(type) == null){
				  map.put(type, Boolean.TRUE);
			  }
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();
			int ben =getStartEnd( -1 , -1 , type, longth)[0];
			int end =getStartEnd( -1 , -1 , type, longth)[1];
            if(ben<end){
			  mm.mergeBlock(ben,0,end,0,KDTMergeManager.SPECIFY_MERGE);
            }
		}
	}
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的行
			 */
			  row = tblMain.getRow(i);
			  String Str = (String)row.getCell("id").getValue();
			  if(Str.equals(type)){
				  if(ben < 0){
					  ben = i ;   
				  }else{
					  end = i ; 
				  }
			  }
 
		} 
       int obj [] = new int[2];
       obj[0]=ben;
       obj[1]=end;
       return obj;
	}
	/**
	 * 
	 * @description		依然是合并
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockListUI#tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		unitTable();
	}
	/**
	 * 
	 * @description		刷新后重新合并
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.framework.client.ListUI#refresh(java.awt.event.ActionEvent)
	 */
	protected void refresh(ActionEvent e) throws Exception {
	super.refresh(e);
	unitTable();
	setHisZero();
	}
	/**
	 * 
	 * @description		供其它界面调用,关闭界面后合并刷新本界面
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param	
	 * @return
	 */
	public void getTblMain() {
		execQuery();
		setHisZero();
		/*
		 * 重新合并表格
		 */
		unitTable();
	}
	/**
	 * 
	 * @description		得到通用查询
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param	
	 * @return	CommonQueryDialog 通用查询				
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (null != commonQueryDialog) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		return commonQueryDialog;
	}
	/**
	 * 
	 * @description		检查一条供应商中的服务类型中是否有一条是合格的,合格的才能进行履约评估与定期评审
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param info	供应商信息
	 * @return boolean 能否进行履约评估与定期评审
	 */
	private boolean checkIsGrade(SupplierStockInfo info) {
		
		// 获取全部的合格供应商登记
		HashMap gradesMap = new HashMap();
		GradeSetUpCollection grades = null;
		try {
			grades = GradeSetUpFactory.getRemoteInstance().getGradeSetUpCollection(new EntityViewInfo());
		} catch (BOSException e) {
			logger.info(e.getMessage());
			handUIException(e);
		}
		GradeSetUpInfo grade = null;
		if (grades == null || grades.size() <= 0) {
			return false;
		}
		for (Iterator it = grades.iterator(); it.hasNext();) {
			grade = (GradeSetUpInfo) it.next();
			if (grade != null) {
//				gradesMap.put(grade.getName(), grade.getIsGrade());
			}
		}
		
		//获取最新的资格审查记录，看该记录是否合格
		FDCSplQualificationAuditBillCollection sc = null;
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("supplier.id", info.getId()));
	    view.setFilter(filter);
	    SorterItemCollection sortor = new SorterItemCollection();
	    SorterItemInfo sortitem = new SorterItemInfo("createtime");
	    sortitem.setSortType(SortType.DESCEND);
	    sortor.add(sortitem);
	    view.setSorter(sortor);
	    SelectorItemCollection selector = new SelectorItemCollection();
	    selector.add("id");
	    selector.add("auditResult.id");
	    selector.add("auditResult.isAudit");
	    selector.add("auditResult.grade");
	    view.setSelector(selector);
	    
	    try {
			sc = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillCollection(view);
		} catch (BOSException e) {
			logger.info(e.getMessage());
			handUIException(e);
		}
		
		if (sc == null || sc.size() <= 0) {
			return false;
		}
		
		FDCSplQualificationAuditBillInfo qualificationInfo = sc.get(0);
		FDCSplQualificationAuditEntryCollection entrys = qualificationInfo.getAuditResult();
		if (entrys != null) {
			for (Iterator it = entrys.iterator(); it.hasNext();) {
				FDCSplQualificationAuditEntryInfo entry = (FDCSplQualificationAuditEntryInfo) it.next();
				if (entry.isIsAudit()) {
					IsGradeEnum isGrade = (IsGradeEnum) gradesMap.get(entry.getGrade());
					if (isGrade != null && IsGradeEnum.ELIGIBILITY.equals(isGrade)) {
						return true;
					}
				}
			}
		}
		
		return false;
//		/*
//		 * 当前供应商服务类型状态集合
//		 */
//		java.util.List nameList = new ArrayList();
//		/*
//		 * 等级设置中合格的等级状态集合
//		 */
//		List Fname = new ArrayList();
//		/*
//		 * 如果当前供应商存在服务类型,则循环搞出每条服务类型的状态(等级)加到集合中,否则直接返回不能履约评估与评审
//		 */
//		if(null!=info.getSupplierServiceType() && info.getSupplierServiceType().size()>0 ){
//			FDCSupplierServiceTypeCollection sst = info.getSupplierServiceType();
//			for(int i = 0 ; i < sst.size() ; i++){
//				nameList.add(sst.get(i).getState());
//			}
//		}else{
//			return false;
//		}
//		/*
//		 * 过滤出等级为合格的,搞出合格的状态的名字,装到集合中
//		 */
//		GradeSetUpCollection sc = null;
//		EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().add(new SelectorItemInfo("*"));
//	    FilterInfo filter = new FilterInfo();
//	    filter.getFilterItems().add(new FilterItemInfo("isGrade",IsGradeEnum.ELIGIBILITY));
//	    view.setFilter(filter);
//	    int Flag = 0;
//	    try {
//			sc = GradeSetUpFactory.getRemoteInstance().getGradeSetUpCollection(view);
//		} catch (BOSException e) {
//			logger.info(e.getMessage());
//			handUIException(e);
//		}
//		if(null != sc && sc.size() > 0){
//			for(int i = 0 ; i < sc.size() ; i++){
//				Fname.add(sc.get(i).getName());
//			}
//		}
//		/*
//		 * 如果存在一个类型是合格返回true
//		 */
//		for(int i = 0 ; i< nameList.size() ; i++){
//			if(Fname.contains(nameList.get(i))){
//				Flag++;
//				break;
//			}
//		}
//		if(Flag!=0){
//			return true;
//		}else{
//			return false;
//		}
		
	}
	/**
	 * 
	 * @description		CU隔离
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @return									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.framework.client.ListUI#isIgnoreCUFilter()
	 */
	protected boolean isIgnoreCUFilter() { 
    	return true;
    }
}