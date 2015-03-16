/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.DeployTypeEnum;
import com.kingdee.eas.fdc.invite.DrawingDepthEnum;
import com.kingdee.eas.fdc.invite.InviteChangeFormInfo;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.DirectAccepterResultEntryInfo;
import com.kingdee.eas.fdc.invite.DirectAccepterResultFactory;
import com.kingdee.eas.fdc.invite.DirectAccepterResultInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.NoPerSupplierStockEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class DirectAccepterResultEditUI extends AbstractDirectAccepterResultEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DirectAccepterResultEditUI.class);
    public DirectAccepterResultEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	if(this.getOprtState() == OprtState.ADDNEW ) {
			this.actionALine_actionPerformed(null);
		}
    	this.txtSpecial.setToolTipText("包括与目标成本、数据库、市场价的差异分析，超目标成本原因，拟中标条件与招标条件的差异（包括付款方式等），未按招标策划中的定标规则定标的原因说明，其他说明等");
    	this.txtScope.setToolTipText("包括与目标成本、数据库、市场价的差异分析，超目标成本原因，拟中标条件与招标条件的差异（包括付款方式等），未按招标策划中的定标规则定标的原因说明，其他说明等");
    }
    
    protected void verifyInputForSave() throws Exception {
    	super.verifyInputForSave();
    	FDCClientVerifyHelper.verifyEmpty(this, this.combRange);
    	FDCClientVerifyHelper.verifyEmpty(this, this.txtSpecial);
    	FDCClientVerifyHelper.verifyEmpty(this, this.txtScope);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"分录不能为空！");
			SysUtil.abort();
		}else {
			for(int i=0; i<kdtEntry.getRowCount(); i++) {
				if(kdtEntry.getCell(i, "supplier").getValue()==null ||kdtEntry.getCell(i, "bidAmount").getValue()==null ||
						kdtEntry.getCell(i, "firstAmount").getValue()==null ) {
					FDCMsgBox.showWarning(this,"第" + (i+1) + "行未有必填项位录入");
					SysUtil.abort();
				}
				if(kdtEntry.getCell(i, "purchaseDoneAmountEx").getValue() == null )
				{
                    FDCMsgBox.showWarning(this, (new StringBuilder("第")).append(i + 1).append("行'已完成采购的单方造价'不能为空.").toString());
                    SysUtil.abort();
                }
			}
		}
	}
    protected void verifyInputForSubmint() throws Exception {
		
		super.verifyInputForSubmint();
	}
    public void storeFields()
	{
		super.storeFields();
		storeOther();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
//		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());

		String prjID = this.editData.getInviteProject().getId().toString();
		String tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"> <Styles> <c:Style id=\"sCol0\"> <c:Protection hidden=\"true\"/> </c:Style> <c:Style id=\"sCol5\"><c:Protection locked=\"true\"/></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\"/></c:Style></Styles> <Table id=\"KDTable\"> <t:Sheet name=\"sheet1\"> <t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"> <t:ColumnGroup> <t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\"/> <t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\"/> <t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\"/> <t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\"/> <t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\"/> <t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\"/> <t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\"/> <t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\"/> </t:ColumnGroup> <t:Head> <t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"> <t:Cell>$Resource{id}</t:Cell> <t:Cell>状态</t:Cell> <t:Cell>单据编码</t:Cell> <t:Cell>编制部门</t:Cell> <t:Cell>编制人</t:Cell> <t:Cell>编制日期</t:Cell> <t:Cell>审批人</t:Cell> <t:Cell>审批日期</t:Cell> </t:Row> </t:Head> </t:Table> <t:SheetOptions> <t:MergeBlocks> <t:Head/> </t:MergeBlocks> </t:SheetOptions> </t:Sheet> </Table> </DocRoot>";
		KDTable table = new KDTable();
		table.setFormatXml(tblBaseInviteStrXML);
		table.checkParsed();
		table.getStyleAttributes().setLocked(true);
		if(table.getColumn("respDept") != null) {
			table.getColumn("respDept").getStyleAttributes().setHided(true);
		}
		
		String fmt = "yyyy-MM-dd";
		table.getColumn("createDate").getStyleAttributes().setLocked(true);
		table.getColumn("auditDate").getStyleAttributes().setLocked(true);
		table.getColumn("createDate").getStyleAttributes().setNumberFormat(fmt);
		table.getColumn("auditDate").getStyleAttributes().setNumberFormat(fmt);
		
		Iterator<BaseInviteInfo> iter=null;
		
		com.kingdee.eas.fdc.invite.InviteChangeFormCollection recordCols=null;
		try {
			recordCols = com.kingdee.eas.fdc.invite.client.InviteChangeFormListUICTEx.getBillList(prjID);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (recordCols != null) {
			iter = recordCols.iterator();
		}
	
		if(iter != null) {
			while (iter.hasNext()) {
				Object obj = iter.next();
				InviteChangeFormInfo info=(InviteChangeFormInfo)obj;
				if (info != null) {
					IRow row = table.addRow();
					row.getCell("id").setValue(info.getId());
					row.getCell("number").setValue(info.getNumber());
					row.getCell("state").setValue(info.getState());
					if (info.getCreator() != null) {
						row.getCell("creator").setValue(info.getCreator().getName());
					}
					row.getCell("createDate").setValue(info.getCreateTime());
					if (info.getAuditor() != null) {
						row.getCell("auditor").setValue(info.getAuditor().getName());
					}
					row.getCell("auditDate").setValue(info.getAuditTime());
					row.setUserObject(info);
				}
			}
		}
		changInviteTypeApplication.setViewportView(table);
		table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
         	        	KDTable table=(KDTable) e.getSource();
                		setCursorOfWair();
         				String id = table.getRow(e.getRowIndex()).getCell("id").getValue().toString();
         				UIContext uiContext = new UIContext(this);
     					uiContext.put(UIContext.ID, id);
         				
     					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteChangeFormEditUI.class.getName(), uiContext, null,"VIEW");
         				uiWindow.show();
         				setCursorOfDefault();
         	      }
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return DirectAccepterResultFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.invitePurchaseMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		sic.add(new SelectorItemInfo("drawingDepth"));
		sic.add(new SelectorItemInfo("deployType"));
		
		return sic;
	}
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/DirectAccepterResult";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.DirectAccepterResultQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		DirectAccepterResultInfo info=new DirectAccepterResultInfo();
		createBaseInvite(info);
//		info.setDrawingDepth(DrawingDepthEnum.SGT);
//		info.setDeployType(DeployTypeEnum.SettleBalances);
		
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new DirectAccepterResultEntryInfo();
	}
	protected void closeDeleteAttachment(){
	}
	protected void initTable() {
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) contEntry.add(this.actionALine);
		btnAddRowinfo.setText(getResource("addRow"));
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText(getResource("insertRow"));
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText(getResource("deleteRow"));
		btnDeleteRowinfo.setSize(new Dimension(140, 19));

		this.kdtEntry.checkParsed();
		
		FDCTableHelper.disableAutoAddLine(kdtEntry);
		
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtEntry.getColumn("hit").setEditor(editor);
		
		KDFormattedTextField price = new KDFormattedTextField();
		price.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		price.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		price.setPrecision(2);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);
		this.kdtEntry.getColumn("bidAmount").setEditor(priceEditor);
		this.kdtEntry.getColumn("bidAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("bidAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("firstAmount").setEditor(priceEditor);
		this.kdtEntry.getColumn("firstAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("firstAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("secondAmount").setEditor(priceEditor);
		this.kdtEntry.getColumn("secondAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("secondAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDDetailedArea desc = new KDDetailedArea(250, 200);
		desc.setMaxLength(50000);
		KDTDefaultCellEditor editorDesc=new KDTDefaultCellEditor(desc);
		
		this.kdtEntry.getColumn("chooseReason").setEditor(editorDesc);
		this.kdtEntry.getColumn("chooseReason").getStyleAttributes().setWrapText(true);
		
		this.kdtEntry.getColumn("supplier").setRequired(true); 
		this.kdtEntry.getColumn("bidAmount").setRequired(true);
		this.kdtEntry.getColumn("firstAmount").setRequired(true); 
		this.kdtEntry.getColumn("purchaseDoneAmountEx").setRequired(true);
	}
	protected void initControl() {
		super.initControl();
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		if( this.editData.getInviteProject().getInvitePurchaseMode().getType() == InvitePurchaseModeEnum .SINGLE ) {//单项采购按组织过滤。。
			Set org=new HashSet();
			FullOrgUnitInfo curFullOrg = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			org.add(OrgConstants.DEF_CU_ID);
			if(curFullOrg.isIsPurchaseOrgUnit()) {
				org.add(curFullOrg.getId().toString());
			}
			
			while(curFullOrg.getParent() !=null && !OrgConstants.DEF_CU_ID.equals(curFullOrg.getParent().getId().toString()) ) {
				FullOrgUnitInfo parent = curFullOrg.getParent();
				try {
					parent = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(parent.getId()));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				
				if(parent.isIsPurchaseOrgUnit()) {
					org.add(parent.getId().toString());
					break;
				}
				curFullOrg = parent;
			}
			
			filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.ELIGIBILITY_VALUE)));
//		filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.TEMP_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
		if(this.editData.getInviteProject().getInviteType()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id",this.editData.getInviteProject().getInviteType().getId().toString()));
		}
	/*	if(this.editData.getInviteProject().getInviteType()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id",this.editData.getInviteProject().getInviteType().getId().toString()));
			filter.setMaskString("(#0 or #1) and #2 and #3");
		}else{
			filter.setMaskString("(#0 or #1) and #2");
		}*/
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtEntry.getColumn("supplier").setEditor(f7Editor);
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException {
		
	}
	
	private HashMap getProviderDataMap() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put(BaseInviteEditUI.INVITEPROJECTID, this.editData.getInviteProject().getId().toString());
		
		return map;
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		DirectAccepterResultPrintDataProvider dataPvd = new DirectAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		DirectAccepterResultPrintDataProvider dataPvd = new DirectAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected void kdtEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		//单据穿透
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 ){
				if(kdtEntry.getColumnKey(e.getColIndex()).equals("chooseReason")){
					this.showDialog(this, kdtEntry, 250, 200, 50000);
				}
	      }
	 }
}