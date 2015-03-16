/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HouseTypeFactory;
import com.kingdee.eas.fdc.basedata.HouseTypeInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.Utils.UIHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.client.ContractBillEditAuditDateUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.RoomTransferInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomTransferListUI extends AbstractRoomTransferListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomTransferListUI.class);
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected Map revAmount=new HashMap();
    protected Map areaRevAmount=new HashMap();
    public RoomTransferListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = "room.name";
        locateNames[1] = "customer.name";
        return locateNames;
    }
    protected void initTree() throws Exception
	{
    	FDCSysContext.getInstance().checkIsSHEOrg(this);
    	this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	SHEManageHelper.expandAllNodesByBuilding(treeMain, (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
    }
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		building=null;
		buildUnit=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}else if (node.getUserObject() instanceof BuildingInfo){ 
			// 楼栋
			if(node.getUserObject() != null){
				building=(BuildingInfo)node.getUserObject();
				sellProject = building.getSellProject();
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
			// 单元
			if(node.getUserObject() != null){
				buildUnit=(BuildingUnitInfo)node.getUserObject();
				building=buildUnit.getBuilding();
				sellProject = buildUnit.getBuilding().getSellProject();
			}
		}
		this.refresh(null);
	}
    public void onLoad() throws Exception
	{
    	actionQuery.setEnabled(false);
		super.onLoad();
		initTree();
		initControl();
		actionQuery.setEnabled(true);
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("POMadmin")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("导入");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
    protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
    	String path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		importExcelToTable(path);
    }
    public void actionImportExcel_actionPerformed(ActionEvent e)throws Exception {
		String path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		importExcelToTable(path);
	}
	private void importExcelToTable(String fileName) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			FDCMsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
			return;
		}
		if (kdsbook == null) {
			return;
		}
		if(KDSBookToBook.traslate(kdsbook).getSheetCount()>1){
			FDCMsgBox.showWarning(this,"读EXCEL出错,EXCEl Sheet数量不匹配！");
			return;
		}
		Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
    	Map e_colNameMap = new HashMap();
		int e_maxRow = excelSheet.getMaxRowIndex();
		int e_maxColumn = excelSheet.getMaxColIndex();
		for (int col = 0; col <= e_maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			e_colNameMap.put(excelColName, new Integer(col));
		}
		int sus=0;
		for (int rowIndex = 2; rowIndex <= e_maxRow; rowIndex++) {
			Integer colInt = (Integer) e_colNameMap.get("房间名称");
			Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
			String room = cellRawVal.toString();
			
			colInt = (Integer) e_colNameMap.get("结转时间");
			cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
			Date date = cellRawVal.toDate();
			
			RoomInfo roomInfo=RoomFactory.getRemoteInstance().getRoomInfo("select * from where name='"+room+"'");
			if(roomInfo==null) continue;
			
			SignManageCollection signCol=SignManageFactory.getRemoteInstance().getSignManageCollection("select * from where room.id='"+roomInfo.getId().toString()+"' and (bizState='SignApple' or bizState='SignAudit')");
			if(signCol.size()==0) continue;
			
			RoomTransferInfo tran=new RoomTransferInfo();
			tran.setBizState(RoomTransferStateEnum.HASTRANSFER);
			tran.setSourceBillId(signCol.get(0).getId().toString());
			tran.setFiVouchered(false);
			tran.setContractTotalAmount(signCol.get(0).getContractTotalAmount());
			tran.setBuildArea(signCol.get(0).getBulidingArea());
			tran.setBizDate(date);
			tran.setName("系统导入");
			RoomTransferFactory.getRemoteInstance().addnew(tran);
			
			sus=sus+1;
		}
		FDCMsgBox.showInfo(this,"成功导入"+sus+"！");
	}
	protected ICoreBase getBizInterface() throws BOSException {
		return RoomTransferFactory.getRemoteInstance();
	}
    protected void fetchInitData() throws Exception{		
    }
    protected void initControl(){
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.treeMain.setSelectionRow(0);
		
		FDCHelper.formatTableDate(getBillListTable(), "roomTransfer.bizDate");
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"contractTotalAmount","bulidingArea","revAmount","strdBuildingPrice","roomArea","actRoomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomArea","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount"});
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
			}
		});
		tblMain.getColumn("roomTransfer.bizState").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj !=null&&RoomTransferStateEnum.getEnum(obj.toString())!=null){
					return RoomTransferStateEnum.getEnum(obj.toString()).getAlias();
				}else{
					return null;
				}
			}
		});
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_tableAfterDataFill(e);
			}
		});
		this.tblMain.getColumn("revRate").setRenderer(render_scale);
		
        this.actionVoucher.setVisible(true);
        this.actionVoucher.setEnabled(true);
        this.actionDelVoucher.setVisible(true);
        this.actionDelVoucher.setEnabled(true);
        
        this.btnTransfer.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnTransfer.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        
        this.actionAddNew.setVisible(false);
        this.actionAddNew.setEnabled(false);
        this.actionEdit.setVisible(false);
        this.actionEdit.setEnabled(false);
        this.actionRemove.setVisible(false);
        this.actionRemove.setEnabled(false);
        this.actionTraceUp.setVisible(false);
        this.actionTraceUp.setEnabled(false);
        this.actionAttachment.setVisible(false);
        this.actionAttachment.setEnabled(false);
        this.actionCreateTo.setVisible(false);
        this.actionCreateTo.setEnabled(false);
        this.actionView.setVisible(false);
        this.actionView.setEnabled(false);
        this.actionAuditResult.setVisible(false);
        this.actionAuditResult.setEnabled(false);
        this.actionCopyTo.setVisible(false);
        this.actionCopyTo.setEnabled(false);
        
        this.menuFile.setVisible(false);
        this.menuBiz.setVisible(false);
        this.menuWorkFlow.setVisible(false);
        this.menuEdit.setVisible(false);
       
        this.toolBar.removeAllToolBarComponents();
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnTransfer);
        this.toolBar.add(btnUnTransfer);
    }
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
	public void actionDelVoucher_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
    	checkBizState(true);
		super.actionDelVoucher_actionPerformed(e);
	}
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	checkBizState(true);
		super.actionVoucher_actionPerformed(e);
	}
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	checkBizState(true);
		super.actionTraceDown_actionPerformed(e);
	}
	public void getBillIdList(List idList,List entriesList){
		int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i = 0; i < selectRows.length; i++){
			IRow row=this.tblMain.getRow(selectRows[i]);
			if(row.getCell("id").getValue()!=null)
				idList.add(row.getCell("id").getValue().toString());
		}
	}
	public void actionTransfer_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	checkBizState(false);
    	int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
    	List value=new ArrayList();
    	UIContext uiContext = new UIContext(this);
    	List transferDate=new ArrayList();
    	uiContext.put("transferDate", transferDate);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TransferDateUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		if(transferDate.size()==0){
			return;
		}
    	for(int i = 0; i < selectRows.length; i++){
    		IRow row=this.tblMain.getRow(selectRows[i]);
    		Map detail=new HashMap();
    		detail.put("transferDate", transferDate.get(0));
    		detail.put("room.name", row.getCell("room.name").getValue());
    		detail.put("sysCustomer.id", row.getCell("sysCustomer.id").getValue());
    		detail.put("productType.id", row.getCell("productType.id").getValue());
    		detail.put("contractTotalAmount", row.getCell("contractTotalAmount").getValue());
    		detail.put("buildArea", row.getCell("buildArea").getValue());
    		detail.put("bill.id", row.getCell("bill.id").getValue());
    		detail.put("sellProject.id", row.getCell("sellProject.id").getValue());
    		value.add(detail);
    	}
    	RoomTransferFactory.getRemoteInstance().transfer(value);
    	this.refresh(null);
    }
    protected void checkBizState(boolean checkTransfer){
    	int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
    	for(int i = 0; i < selectRows.length; i++){
    		ICell cell = this.tblMain.getRow(selectRows[i]).getCell("id");
    		if(checkTransfer){
        		if(cell.getValue()==null){
        			FDCMsgBox.showWarning(this,"选择的记录中包含未结转房间！");
        			SysUtil.abort();
                }
    		}else{
    			if(cell.getValue()!=null){
        			FDCMsgBox.showWarning(this,"选择的记录中包含已结转房间！");
        			SysUtil.abort();
                }
    		}
        }
    }
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionUnTransfer_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	checkBizState(true);
    	ArrayList id = getSelectedIdValues();
    	RoomTransferFactory.getRemoteInstance().unTransfer(id);
    	this.refresh(null);
    }
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("filter")==null){
				if(node!=null&&node.getUserObject()!=null){
					if (node.getUserObject() instanceof SellProjectInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet()),CompareType.INNER));	
					}else if (node.getUserObject() instanceof BuildingInfo){ 
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
					}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
					} else if (node.getUserObject() instanceof OrgStructureInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
				}
			}else{
				filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
				viewInfo.getSorter().clear();
				viewInfo.getSorter().add(new SorterItemInfo("building.number"));
				viewInfo.getSorter().add(new SorterItemInfo("room.unit"));
				viewInfo.getSorter().add(new SorterItemInfo("room.floor"));
				viewInfo.getSorter().add(new SorterItemInfo("room.number"));
				
				SorterItemInfo type=new SorterItemInfo("type");
				type.setSortType(SortType.DESCEND);
				viewInfo.getSorter().add(type);
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void tblMain_tableAfterDataFill(KDTDataRequestEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		String state=null;
		for (int i = start; i <= end; i++) {
			IRow row = tblMain.getRow(i);
			ICell cell = row.getCell("roomTransfer.bizState");
			if (cell.getValue() == null|| cell.getValue().toString().equals("")) {
				state = RoomTransferStateEnum.NOTTRANSFER.toString();
				cell.setValue(state);
			} else {
				state = cell.getValue().toString();
			}
			if (state.equals(RoomTransferStateEnum.NOTTRANSFER_VALUE)) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_NOSPLIT);
			} else if (state.equals(RoomTransferStateEnum.HASTRANSFER_VALUE)) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_SAVED);
			}
			try {
				String type=(String) row.getCell("type").getValue();
				String roomId=(String) row.getCell("room.id").getValue();
				if(type!=null&&roomId!=null){
					if("面积补差".equals(type)){
						if(areaRevAmount.get(roomId)!=null){
							row.getCell("revAmount").setValue(areaRevAmount.get(roomId));
						}else{
							BigDecimal revamount=getSheRevBill(roomId,true);
							areaRevAmount.put(roomId, revamount);
							row.getCell("revAmount").setValue(revamount);
						}
					}else{
						if(revAmount.get(roomId)!=null){
							row.getCell("revAmount").setValue(revAmount.get(roomId));
						}else{
							BigDecimal revamount=getSheRevBill(roomId,false);
							revAmount.put(roomId, revamount);
							row.getCell("revAmount").setValue(revamount);
						}
					}
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			row.getCell("revRate").setValue(FDCHelper.multiply(FDCHelper.divide(row.getCell("revAmount").getValue(), row.getCell("contractTotalAmount").getValue(), 4, BigDecimal.ROUND_HALF_UP),new BigDecimal(100)));
		}
	}
	private BigDecimal getSheRevBill(String roomId,boolean isArea) throws SQLException, BOSException{
		if(roomId==null) return FDCHelper.ZERO;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) as revAmount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid ");
    	_builder.appendSql(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	_builder.appendSql(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(isArea){
    		_builder.appendSql(" and md.fname_l2='面积补差款'");
    	}else{
    		_builder.appendSql(" and md.fname_l2!='面积补差款'");
    	}
		_builder.appendSql(" and revBill.froomId = '"+roomId+"'");
		IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("revAmount")!=null)
				return rowSet.getBigDecimal("revAmount");
		}
		return FDCHelper.ZERO;
    }
}