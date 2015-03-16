/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.AccountReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * by ....wancheng
 */
public class AccountReportUI extends AbstractAccountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public AccountReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }

    public void onLoad() throws Exception {
    	isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.btnGenDefault.setIcon(EASResource.getIcon("imgTbtn_post"));
		this.btnOverdue.setIcon(EASResource.getIcon("imgTbtn_post"));
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
    }
    protected void initTree() throws Exception
	{
    	if(this.getUIContext().get("RPTFilter")!=null){
			this.treeView.setVisible(false);
			this.actionQuery.setVisible(false);
			this.actionOverdue.setVisible(false);
			this.actionGenDefault.setVisible(false);
		}else{
			this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
	    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		}
	}
	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new AccountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AccountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public static int diffdates(Date date1, Date date2)
    {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (int)((time2 - time1) / 86400000L);
    }

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		for(int i=0;i<tblMain.getRowCount();i++){
			for(int j=i+1;j<this.tblMain.getRowCount();j++){
				if(tblMain.getRow(i).getCell("id").getValue().toString().equals(tblMain.getRow(j).getCell("id").getValue().toString())){
					tblMain.getRow(j).setTreeLevel(1);
					for(int k=0;k<tblMain.getColumnCount();k++){
						if(!(tblMain.getColumnKey(k).equals("alreadyAmount")||tblMain.getColumnKey(k).equals("revDate")||tblMain.getColumnKey(k).equals("id")
								||tblMain.getColumnKey(k).equals("newID"))){
							tblMain.getRow(j).getCell(k).setValue(null);
						}
					}
				}else{
					tblMain.getRow(j).setTreeLevel(0);
					break;
				}
			}
		}
		try {
			tblMain.getColumn("commercialStage").setRenderer(new ObjectValueRender(){
				public String getText(Object obj) {
					if(obj instanceof String){
						String info = (String)obj;
						if(CommercialStageEnum.getEnum(info)==null){
							return "";
						}else{
							return CommercialStageEnum.getEnum(info).getAlias();
						}
					}
					return super.getText(obj);
				}
			});
			tblMain.getColumn("returnPeriod").setRenderer(new ObjectValueRender(){
				public String getText(Object obj) {
					if(obj instanceof BigDecimal){
						int day = Integer.parseInt(obj.toString());
						if(day>=0&&day<7){
							return PeriodEnum.InnerSeven.getAlias();
						}else if(day>=7&&day<=14){
							return PeriodEnum.SevenToFourteen.getAlias();
						}else if(day>=15&&day<=30){
							return PeriodEnum.FifteenToThirty.getAlias();
						}else if(day>=31&&day<=60){
							return PeriodEnum.thirtyoneToSixty.getAlias();
						}else if(day>60){
							return PeriodEnum.SixtyMore.getAlias();
						}else{
							return "";
						}
					}
					return super.getText(obj);
				}
			});
			getRowSum();
			
			CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"contractTotal","alreadyAmount","actevAmount","notAccept","defaultAmount","notAccept","notproPortion","proPortion"});
			EnterprisePlanEditUI.mergerTable(this.tblMain, new String[]{"newID"},new String[]{"roomName","customerNames","contractTotal","busAdscriptionDate"});
			CRMClientHelper.fmtDate(tblMain, new String[]{"revDate","bizDate","lastTime","applyDate","defCalDate"});
			
			tblMain.getColumn("roomName").getStyleAttributes().setFontColor(Color.BLUE);
			tblMain.getColumn("customerNames").getStyleAttributes().setFontColor(Color.BLUE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		if(isOnLoad){
			try {
				initTree();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null||this.getUIContext().get("RPTFilter")!=null){
    		Window win = SwingUtilities.getWindowAncestor(this);
            LongTimeDialog dialog = null;
            if(win instanceof Frame){
            	dialog = new LongTimeDialog((Frame)win);
            }else if(win instanceof Dialog){
            	dialog = new LongTimeDialog((Dialog)win);
            }
            if(dialog==null){
            	dialog = new LongTimeDialog(new Frame());
            }
            dialog.setLongTimeTask(new ILongTimeTask() {
                public Object exec()throws Exception{
                	RptParams pp=(RptParams)params.clone();
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
        	        
        	        Date getdate=new Date();
        			try {
        				getdate = FDCCommonServerHelper.getServerTime();
        			} catch (BOSException e) {
        				e.printStackTrace();
        			}
        	        while(rs.next()){
    	        		IRow addRow=tblMain.addRow();
            			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
            			Date appDate=(Date)addRow.getCell("applyDate").getValue();
            			if(appDate!=null){
            				int day=diffdates(FDCDateHelper.getDayBegin(appDate),FDCDateHelper.getDayBegin(getdate));
            				if(day>0){
            					BigDecimal notAccept=(BigDecimal)addRow.getCell("notAccept").getValue();
            					if(notAccept!=null){
            						if(notAccept.compareTo(FDCHelper.ZERO)<=0){
            							for(int j=10;j<tblMain.getColumnCount();j++){
            								addRow.getCell(j).getStyleAttributes().setBackground(Color.GREEN);
            							}
            						}else{
            							for(int j=10;j<tblMain.getColumnCount();j++){
            								addRow.getCell(j).getStyleAttributes().setBackground(Color.PINK);
            							}
            						}
            					}
            				}
            			}
        	        }
        	        if(rs.getRowCount() > 0){
        	        	tblMain.getTreeColumn().setDepth(2);
        	        	tblMain.reLayoutAndPaint();
        	        }else{
        	        	tblMain.repaint();
        	        }
        	        tblMain.setRefresh(true);
                }
            }
            );
            dialog.show();
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
	        	RptParams rpt = getRemoteInstance().createTempTable(pp);
	            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
	            KDTableUtil.setHeader(header, tblMain);
	            tblMain.setRowCount(0);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	isQuery=false;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				if(obj instanceof SellProjectInfo){
					params.setObject("psp","'"+SHEManageHelper.getParentSellProject(null, (SellProjectInfo) obj).getId().toString()+"'");
				}else{
					params.setObject("psp",allSpIdStr);
				}
			}else{
				params.setObject("sellProject", null);
				params.setObject("psp",null);
			}
			query();
		}
	}
	/**
	 * 行总计、融合
	 * */
	private void getRowSum(){
		tblMain.setRowCount(tblMain.getRowCount());
		CRMClientHelper.getFootRow(tblMain, new String[]{"alreadyAmount","actevAmount","notAccept","defaultAmount"});
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
    		return;
    	}
	    if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
	    	IRow row   = tblMain.getRow(rowIndex);
	    	if(tblMain.getColumnKey(e.getColIndex()).equals("customerNames")){
	    		SignCustomerEntryCollection col=SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection("select customer.id from where head.id='"+row.getCell("newID").getValue()+"' and isMain=1");	    		
	    		if(col.size()>0){
	    			UIContext uiContext = new UIContext(this);
				    uiContext.put("ID", col.get(0).getCustomer().getId());
				    IUIWindow uiWindow = null;
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(),uiContext,null,OprtState.VIEW);
					uiWindow.show();
	    		}
	    	}else if(tblMain.getColumnKey(e.getColIndex()).equals("roomName")){
		    	UIContext uiContext = new UIContext(this);
			    uiContext.put("ID", row.getCell("newID").getValue());
			    IUIWindow uiWindow = null;
				uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SignManageEditUI.class.getName(),uiContext,null,OprtState.VIEW);
				uiWindow.show();
	    	}
	    }
	}
	
	public void actionOverdue_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this, tblMain);
		List id=getSelectedIdValues();
		if(id.size()>1){
			 FDCMsgBox.showInfo(this,"请选择一条记录!");
	    	 return;
		}
		ArrayList selectList = new ArrayList();
		int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
	    for(int i=0;i<selectRows.length;i++){
	    	String mdid= (String)tblMain.getRow(selectRows[i]).getCell("moneyDefineId").getValue();
	    	selectList.add(mdid);
	    }
	    if(selectList.get(0)==null){
	    	FDCMsgBox.showInfo(this,"请选择应收记录!");
	    	return;
	    }
		UIContext uiContext = new UIContext(this);
		uiContext.put("tranID", id.get(0).toString());
	    IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(OverdueDescribeListUI.class.getName(),uiContext,null,OprtState.ADDNEW);
		uiWindow.show();
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

	public void actionGenDefault_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this, tblMain);
		int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
		
		ArrayList selectList = new ArrayList();
	    for(int i=0;i<selectRows.length;i++){
	    	String mdid= (String)tblMain.getRow(selectRows[i]).getCell("moneyDefineId").getValue();
	    	selectList.add(mdid);
	    }
	    if(selectList.get(0)==null){
	    	FDCMsgBox.showInfo(this,"请选择应收记录!");
	    	return;
	    }
	    
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", "违约金"));
		view.setFilter(filter);
		MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		if(moneyColl.size()==0){
			FDCMsgBox.showInfo(this, "请先定义款项名称为违约金的款项！");
			SysUtil.abort();
		}
		MoneyDefineInfo monyInfo = moneyColl.get(0);
		
		String sellProject=null;
		DefaultAmountCreatInfo createInfo = new DefaultAmountCreatInfo();
		DefaultAmountMangerCollection damColl = new DefaultAmountMangerCollection();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("room.*");
		sic.add("room.productType.*");
		sic.add("customerPhone");
		sic.add("customerNames");
		sic.add("bizDate");
		sic.add("number");
		sic.add("contractTotalAmount");
		sic.add("saleManNames");
		sic.add("sellProject.*");
		sic.add("transactionID");
		sic.add("payType.*");
		
		SelectorItemCollection transic = new SelectorItemCollection();
		transic.add("head.id");
		transic.add("moneyDefine.id");
		transic.add("*");
		Map dam=new HashMap();
		Date getdate=new Date();
		try {
			getdate = FDCCommonServerHelper.getServerTime();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	    for(int i=0;i<selectRows.length;i++){
	    	IRow row = tblMain.getRow(selectRows[i]);
	    	if(row.getCell("newID").getValue()==null||row.getCell("moneyDefineId").getValue()==null) continue;
	    	BigDecimal notAccept=(BigDecimal)row.getCell("notAccept").getValue();
	    	Date appDate=(Date)row.getCell("applyDate").getValue();
	    	Date defCalDate=(Date)row.getCell("defCalDate").getValue();
	    	int day=0;
			if(defCalDate!=null){
				day=diffdates(FDCDateHelper.getDayBegin(defCalDate),FDCDateHelper.getDayBegin(getdate));
			}else{
				day=diffdates(FDCDateHelper.getDayBegin(appDate),FDCDateHelper.getDayBegin(getdate));
			}
	    	if(notAccept==null||day<=0||notAccept.compareTo(FDCHelper.ZERO)<=0){
	    		continue;
	    	}
	    	DefaultAmountCreatEntryInfo entry = new DefaultAmountCreatEntryInfo();
	    	if(i==0||sellProject==null){
	    		sellProject=row.getCell("projectID").getValue().toString();
	    	}else{
	    		if(!sellProject.equals(tblMain.getRow(selectRows[i]).getCell("projectID").getValue().toString())){
	    			 FDCMsgBox.showInfo(this,"请选择同项目记录!");
	    	    	 SysUtil.abort();
	    		}
	    	}
	    	entry.setCustomerNames(row.getCell("customerNames").getValue().toString());
	    	
	    	SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(row.getCell("newID").getValue().toString()),sic);
	    	entry.setRoom(sign.getRoom());
	    	entry.setTelephone(sign.getCustomerPhone());
	    	entry.setBizDate(sign.getBizDate());
	    	entry.setNumber(sign.getNumber());
	    	entry.setContractAmount(sign.getContractTotalAmount());
	    	entry.setArgAmount((BigDecimal)row.getCell("notAccept").getValue());
	    	entry.setSaleManNames(sign.getSaleManNames());
	    	entry.setSubDeAmount(FDCHelper.ZERO);
	    	entry.setRemak(sign.getTransactionID().toString());
	    	
	    	
	    	TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getRemoteInstance().getTranBusinessOverViewInfo(new ObjectUuidPK(row.getCell("id").getValue().toString()),transic);
	    	
			TranBusinessOverViewInfo   tranOveInfo = new TranBusinessOverViewInfo();
			tranOveInfo.setId(BOSUuid.create(tranOveInfo.getBOSType()));
			tranOveInfo.setHead(tran.getHead());
			tranOveInfo.setMoneyDefine(monyInfo);
			tranOveInfo.setBusinessName(monyInfo.getName());
			tranOveInfo.setCurrency(SHEManageHelper.getCurrencyInfo(null));
			tranOveInfo.setType(BusTypeEnum.PAY);
			tranOveInfo.setActRevAmount(FDCHelper.ZERO);
			
	    	SignPayListEntryInfo signEntryInfo = new SignPayListEntryInfo();
	    	signEntryInfo.setId(BOSUuid.create(signEntryInfo.getBOSType()));
	    	signEntryInfo.setHead(sign);
   			signEntryInfo.setMoneyDefine(monyInfo);
   			signEntryInfo.setTanPayListEntryId(tranOveInfo.getId());
   			signEntryInfo.setCurrency(SHEManageHelper.getCurrencyInfo(null));
   			signEntryInfo.setActRevAmount(FDCHelper.ZERO);
   			
   			entry.setTranBusinessOverView(tranOveInfo);
   			entry.setSignPayListEntry(signEntryInfo);
   			
	    	createInfo.setProject(sign.getSellProject());
	    	DefaultAmountMangerInfo amInfo =new DefaultAmountMangerInfo();
	    	if(dam.get(sign.getId())!=null){
	    		amInfo=(DefaultAmountMangerInfo)dam.get(sign.getId());
	    	}else{
	    		amInfo.setId(BOSUuid.create(amInfo.getBOSType()));
	    		dam.put(sign.getId(), amInfo);
	    		amInfo.setProject(sign.getSellProject());
		    	amInfo.setCustomerNames(sign.getCustomerNames());
		    	amInfo.setRoom(sign.getRoom());
		    	amInfo.setTelephone(sign.getCustomerPhone());
		    	amInfo.setBizDate(sign.getBizDate());
		    	amInfo.setContractAmount(sign.getContractTotalAmount());
		    	amInfo.setArgAmount((BigDecimal)row.getCell("notAccept").getValue());
		    	amInfo.setSaleManNames(sign.getSaleManNames());
		    	amInfo.setPayment(sign.getPayType());
		    	amInfo.setBillId(sign.getId().toString());
		    	amInfo.setRemark(sign.getNumber());
		    	amInfo.setRefDeAmount(FDCHelper.ZERO);
		    	amInfo.setCarryAmount(FDCHelper.ZERO);
		    	amInfo.setSubDeAmount(FDCHelper.ZERO);
		    	damColl.add(amInfo);
	    	}
	    	
	    	amInfo.setTransaction(tran.getHead());
	    	
	    	DefaultAmountMangerEntryInfo entryInfo = new DefaultAmountMangerEntryInfo();
	    	entryInfo.setHead(amInfo);
	    	entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
        	entryInfo.setMoneyDefine(tran.getMoneyDefine());
        	entryInfo.setAppDate(tran.getAppDate());
        	entryInfo.setAppAmount(tran.getAppAmount());
        	entryInfo.setActAmount(tran.getActRevAmount());
        	entryInfo.setArgAmount(notAccept);
        	entryInfo.setArgDays(day);
        	entryInfo.setTranOverView(tran);
        	entryInfo.setReferAmount(FDCHelper.ZERO);
        	entryInfo.setCarryAmount(FDCHelper.ZERO);
        	entryInfo.setSubDeAmount(FDCHelper.ZERO);
        	amInfo.getEntry().add(entryInfo);
        	
        	entry.setDefaultAmountMangerEntry(entryInfo);
        	createInfo.getParent().add(entry);
	    }
	    if(createInfo.getParent().size()==0){
	    	FDCMsgBox.showInfo(this, "无违约记录！");
			SysUtil.abort();
	    }
	  
	    filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", sellProject));
		if(!DefaultCollectionFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo(this, "请先定义该项目下的违约金计算公式！");
			SysUtil.abort();
		}
	    UIContext context = new UIContext(this);
	    context.put("createInfo", createInfo);
		context.put("damColl", damColl);
		context.put("SellProject" ,sellProject);
	    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultAmountCreatEditUI.class.getName(),context,null,OprtState.ADDNEW).show();
	    
	    query();
	}
	
}