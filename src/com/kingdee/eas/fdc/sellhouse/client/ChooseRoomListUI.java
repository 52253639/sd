/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetListener;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IChooseRoom;
import com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
/**
 * output class name
 */
public class ChooseRoomListUI extends AbstractChooseRoomListUI
{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1552965167523174966L;


	private static final Logger logger = CoreUIObject.getLogger(ChooseRoomListUI.class);
    
    CoreUIObject detailUI = null;

    CoreUIObject TransListUI = null;
    
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	
	RoomDisplaySetting setting = new RoomDisplaySetting();

	Map sellProAndBuildingMap = new HashMap();
	
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	
	RoomDisplaySetting set = new RoomDisplaySetting(new String[]{RoomDisplaySetting.CHOOSE_ROOM_SET_MAP});
	
	boolean hasOnLoadFlag = false;

	public static int selledCount = 0;
	public static int affirmCount = 0;
	public static int unconfirmedCount = 0;
	public static int noSellhouseCount = 0;
	

	
    /**
     * output class constructor
     */
    public ChooseRoomListUI() throws Exception
    {
        super();

		
      
    }

  
    /**
     *output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }
    
    
    /**
     * output actionCancelChooseRoom_actionPerformed method
     */
    public void actionCancelChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {	
    	int index = this.kDTabbedPane1.getSelectedIndex();
    	if(index == 0){
    		EntityViewInfo view =new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		RoomInfo room = this.getSelectRoom();
    		filter.getFilterItems().add(
    				new FilterItemInfo("room.id", room.getId().toString()));
    		view.setFilter(filter);
    		IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
    		ChooseRoomCollection chooseRoomCol=ichooseRoom.getChooseRoomCollection(view);
    		if(null!=chooseRoomCol&&chooseRoomCol.size()>0){//加校验,有有效的选房单，就打开编辑界面，没有就新增
    			boolean flag =false;
    			for(int i = 0 ; i<chooseRoomCol.size();i++){
    				if(null!=chooseRoomCol.get(i).getId()){
    	    	    	
    	    	    }else{
    	    	    	FDCMsgBox.showWarning("不存在本单据!");
    	    	    	SysUtil.abort();
    	    	    }
    				if((ChooseRoomStateEnum.Affirm.equals(chooseRoomCol.get(i).getChooseRoomStateEnum()))){
    					 flag= true;
    		    			((IChooseRoom)ChooseRoomFactory.getRemoteInstance()).cancelChooseRoom(chooseRoomCol.get(i).getId().toString());
        	    	    	
    		    		}else{
    		    			if(flag){
    		    				FDCMsgBox.showWarning("请选择选房确认状态下的单据，进行取消选房操作!");
        		    			SysUtil.abort();
    		    			}
    		    			
    		    		}
    			
    			
    		}
    		}
    	}else if (1==index){
    		int rowNum = this.tblMain1.getSelectManager().getActiveRowIndex();
    		IRow row = this.tblMain1.getRow(rowNum);
    		String strId = row.getCell("id").getValue().toString();
    		ChooseRoomInfo chooseRoom = ChooseRoomFactory.getRemoteInstance().getChooseRoomInfo(new ObjectUuidPK(BOSUuid.read(strId)));
    		if(!(ChooseRoomStateEnum.Affirm.equals(chooseRoom.getChooseRoomStateEnum()))){
    			FDCMsgBox.showWarning("请选择选房确认状态下的单据，进行取消选房操作!");
    			SysUtil.abort();
    		}
    		
    		ChooseRoomFactory.getRemoteInstance().cancelChooseRoom(strId);
	    }
    	FDCMsgBox.showWarning("已成功取消选房！");
    	this.refresh(e);
    }
   
    public void onLoad() throws Exception {
    	this.tblMain1.checkParsed();
			initControl();
			super.onLoad();
			
			//不用销售组织判断,改为售楼组织
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(null==orgMap.get(idStr)){
				this.actionPrePurchase.setEnabled(false);
				this.actionSignContract.setEnabled(false);
				this.actionPurchase.setEnabled(false);
				this.actionCancelChooseRoom.setEnabled(false);
			}
//			if (!saleOrg.isIsBizUnit()) {
//				this.actionPurchase.setEnabled(false);
//				this.actionSignContract.setEnabled(false);
//				this.actionPurchase.setEnabled(false);
//				this.actionCancelChooseRoom.setEnabled(false);
//			}

			hasOnLoadFlag = true;
			DisplayChooseRoomStateViewUI.insertUIToScrollPanel(this.kDScrollPane1);

			// 此处是为了解决选择平面图时，选中区域有延迟的问题
			KDTViewManager dkmanager = this.tblMain.getViewManager();
			BasicView view = dkmanager.getView(dkmanager.getDefaultActiveViewIndex());
			view.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {
					reflashRoomInfoByPlanisphere();
				}

				public void mouseClicked(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}
			});
		
	}
	
    public void initControl() {
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionImportData.setVisible(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.treeView.setShowControlPanel(true);
		
//		this.btnPrePurchase.setEnabled(false);
//		this.btnPurchase.setEnabled(false);
//		this.btnSignContract.setEnabled(false);
		
		this.btnPrint.setText("选房打印确认书");
		this.btnPrint.setText("选房打印确认书预览");
	}
    
   
	private void setSellStateCountByColor() throws BOSException {
		//颜色对应计算
		selledCount = 0;
		affirmCount = 0;
		unconfirmedCount = 0;
		noSellhouseCount = 0;
		ChooseRoomDisplaySetting setting = new ChooseRoomDisplaySetting();
		Set roomId=new HashSet();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			IRow row = this.tblMain.getRow(j);
			for (int i = 0; i < this.tblMain.getColumnCount(); i++) {
				if(row.getCell(i).getUserObject()!=null&&row.getCell(i).getUserObject() instanceof RoomInfo){
					RoomInfo room=(RoomInfo) row.getCell(i).getUserObject();
					if(roomId.contains(room.getId())){
						continue;
					}else{
						roomId.add(room.getId());
					}
				    ICell cell = row.getCell(i);
				    if (cell != null) {
//				    	if (setting.getNoSellhouseColor().equals(cell.getStyleAttributes().getBackground())) {
//						  noSellhouseCount++;
//					  }
				    	 if (setting.getChooseRoomSelled().equals(cell.getStyleAttributes().getBackground())) {
						  selledCount++;
					  } else if (setting.getChooseRoomUnconfirmed().equals(cell.getStyleAttributes().getBackground())) {
						  unconfirmedCount++;
					  } else if (setting.getChooseRoomAffirm().equals(cell.getStyleAttributes().getBackground())) {
						  affirmCount++;
					  } 
						
				   }
				}
			}
		}
		DisplayChooseRoomStateViewUI.insertUIToScrollPanel(this.kDScrollPane1);
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
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

  
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
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
    	int tableIndex = this.kDTabbedPane1.getSelectedIndex();
		String id = null;
		if (tableIndex == 1) {
			int index = this.tblMain1.getSelectManager().getActiveRowIndex();

			IRow row = this.tblMain1.getRow(index);
			if (null != row && null != row.getCell("id").getValue()) {
				id = row.getCell("id").getValue().toString();
			}
		} else {
			String roomId = null;

			if (null != this.getUIContext().get("croomID")) {
				roomId = this.getUIContext().get("croomID").toString();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", roomId));
				//    			
				// filter.getFilterItems().add(
				// new FilterItemInfo("chooseRoomStateEnum",ChooseRoomStateEnum.
				// AFFIRM_VALUE));
				view.setFilter(filter);

				IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
				ChooseRoomCollection chooseRoomCol = ichooseRoom.getChooseRoomCollection(view);
				if (null != chooseRoomCol && chooseRoomCol.size() > 0) {// 加校验,
																		// 有有效的选房单
																		// ，
																		// 就打开编辑界面
																		// ，
																		// 没有就新增
					for (int i = 0; i < chooseRoomCol.size(); i++) {

						if (null != chooseRoomCol.get(i).getId().toString()) {
							id = chooseRoomCol.get(i).getId().toString();
						}
					}
				}
			} else {

				KDTSelectBlock block = this.tblMain.getSelectManager().get();
				if (block == null) {
					SysUtil.abort();
				}
				int left = block.getLeft();
				int top = block.getTop();
				ICell cell = this.tblMain.getCell(top, left);
				if (cell == null) {
					SysUtil.abort();
				}
				RoomInfo roomq = null;
				if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo) {
					roomq = (RoomInfo) cell.getUserObject();
					if (roomq == null) {
						SysUtil.abort();
					}

					roomId = roomq.getId().toString();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));

					filter.getFilterItems().add(new FilterItemInfo("chooseRoomStateEnum", ChooseRoomStateEnum.AFFIRM_VALUE));
					view.setFilter(filter);

					IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
					ChooseRoomCollection chooseRoomCol = ichooseRoom.getChooseRoomCollection(view);
					if (null != chooseRoomCol && chooseRoomCol.size() > 0) {// 加校验
																			// ,
																			// 有有效的选房单
																			// ，
																			// 就打开编辑界面
																			// ，
																			// 没有就新增
						for (int i = 0; i < chooseRoomCol.size(); i++) {

							if (null != chooseRoomCol.get(i).getId().toString()) {
								id = chooseRoomCol.get(i).getId().toString();
							}
						}
					}

				}

			}

		}
    	if(id==null){
    		FDCMsgBox.showWarning("请选择已选房间或者一条选房记录单据！");
    		SysUtil.abort();
    	}
		ChooseRoomDataProvider data = new ChooseRoomDataProvider(
				id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {

		int tableIndex = this.kDTabbedPane1.getSelectedIndex();
		String id = null;
		if (tableIndex == 1) {
			int index = this.tblMain1.getSelectManager().getActiveRowIndex();

			IRow row = this.tblMain1.getRow(index);
			if (null != row && null != row.getCell("id").getValue()) {
				id = row.getCell("id").getValue().toString();
			}
		} else {
			String roomId = null;

			if (null != this.getUIContext().get("croomID")) {
				roomId = this.getUIContext().get("croomID").toString();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", roomId));
				//    			
				// filter.getFilterItems().add(
				// new FilterItemInfo("chooseRoomStateEnum",ChooseRoomStateEnum.
				// AFFIRM_VALUE));
				view.setFilter(filter);

				IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
				ChooseRoomCollection chooseRoomCol = ichooseRoom.getChooseRoomCollection(view);
				if (null != chooseRoomCol && chooseRoomCol.size() > 0) {// 加校验,
																		// 有有效的选房单
																		// ，
																		// 就打开编辑界面
																		// ，
																		// 没有就新增
					for (int i = 0; i < chooseRoomCol.size(); i++) {

						if (null != chooseRoomCol.get(i).getId().toString()) {
							id = chooseRoomCol.get(i).getId().toString();
						}
					}
				}
			} else {

				KDTSelectBlock block = this.tblMain.getSelectManager().get();
				if (block == null) {
					SysUtil.abort();
				}
				int left = block.getLeft();
				int top = block.getTop();
				ICell cell = this.tblMain.getCell(top, left);
				if (cell == null) {
					SysUtil.abort();
				}
				RoomInfo roomq = null;
				if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo) {
					roomq = (RoomInfo) cell.getUserObject();
					if (roomq == null) {
						SysUtil.abort();
					}

					roomId = roomq.getId().toString();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));

					filter.getFilterItems().add(new FilterItemInfo("chooseRoomStateEnum", ChooseRoomStateEnum.AFFIRM_VALUE));
					view.setFilter(filter);

					IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
					ChooseRoomCollection chooseRoomCol = ichooseRoom.getChooseRoomCollection(view);
					if (null != chooseRoomCol && chooseRoomCol.size() > 0) {// 加校验
																			// ,
																			// 有有效的选房单
																			// ，
																			// 就打开编辑界面
																			// ，
																			// 没有就新增
						for (int i = 0; i < chooseRoomCol.size(); i++) {

							if (null != chooseRoomCol.get(i).getId().toString()) {
								id = chooseRoomCol.get(i).getId().toString();
							}
						}
					}

				}

			}

		}
		if (id == null) {
			FDCMsgBox.showWarning("请选择已选房间或者一条选房记录单据！");
			SysUtil.abort();
		}
		ChooseRoomDataProvider data = new ChooseRoomDataProvider(id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/ChooseRoom";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ChooseRoomPrintQuery");
		
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
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
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
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }



   protected void addChooseRoomRecord(BuildingInfo buildNode,BuildingUnitInfo unit ) throws BOSException, EASBizException {
	 //非平面图显示时

		
	   KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setEditable(false);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor cellEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain1.removeRows();
		this.tblMain1.getColumn("chooser").setEditor(cellEditor);
		this.tblMain1.getColumn("chooseNo").setEditor(cellEditor);
		this.tblMain1.getColumn("chooseRoomState").setEditor(cellEditor);
		this.tblMain1.getColumn("room").setEditor(cellEditor);
//		this.tblMain1.getColumn("customer").setEditor(cellEditor);
//		this.tblMain1.getColumn("sincerityPurchase").setEditor(cellEditor);
		this.tblMain1.getColumn("linkPhone").setEditor(cellEditor);
		this.tblMain1.getColumn("bizDate").setEditor(cellEditor);
		this.tblMain1.getColumn("saleMan").setEditor(cellEditor);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("room.*");
		selector.add("room.building.*");
		selector.add("salesMan.*");
		selector.add("sincerityPurchase.*");
		selector.add("*");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.building.id",buildNode.getId().toString()));
		if(null!=unit){
			filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id",unit.getId().toString()));
		}
		view.setFilter(filter);
		view.setSelector(selector);
		ChooseRoomCollection chooseRoomRd = ChooseRoomFactory.getRemoteInstance().getChooseRoomCollection(view);
		if (null != chooseRoomRd && chooseRoomRd.size() > 0) {
			for (int i = 0; i < chooseRoomRd.size(); i++) {
				if(null!=chooseRoomRd.get(i).getRoom()){
					if(buildNode.getId().equals(chooseRoomRd.get(i).getRoom().getBuilding().getId())){
						
						IRow row = this.tblMain1.addRow();
						 row.getStyleAttributes().setLocked(true);
						row.setUserObject(chooseRoomRd.get(i));
					row.getCell("chooser").setValue(chooseRoomRd.get(i).getChooser());
					row.getCell("chooseNo").setValue(chooseRoomRd.get(i).getChooseNo());
					row.getCell("chooseRoomState").setValue(chooseRoomRd.get(i).getChooseRoomStateEnum());
					row.getCell("room").setValue(chooseRoomRd.get(i).getRoom().getName());
//					String customer=null;
//					for(int j = 0 ; j<chooseRoomRd.get(i).getCustomerEntry().size() ; j ++){
//						if(null!=((ChooseRoomCusEntryInfo)chooseRoomRd.get(i).getCustomerEntry().get(j)).getCustomer()){
//							customer = customer +((ChooseRoomCusEntryInfo)chooseRoomRd.get(i).getCustomerEntry().get(j)).getCustomer().getName()+";";
//						}
//						
//					}
//					row.getCell("customer").setValue(chooseRoomRd.get(i).getCusStr());
					
//					if (null != chooseRoomRd.get(i).getSincerityPurchase()) {
//						row.getCell("sincerityPurchase").setValue(chooseRoomRd.get(i).getSincerityPurchase().getName());
//					}
					row.getCell("chooser").setValue(chooseRoomRd.get(i).getChooser());
					row.getCell("linkPhone").setValue(chooseRoomRd.get(i).getLinkPhone());
					row.getCell("bizDate").setValue(chooseRoomRd.get(i).getBizDate());
					if (null != chooseRoomRd.get(i).getSalesMan()) {
						row.getCell("saleMan").setValue(chooseRoomRd.get(i).getSalesMan().getName());
					}
					row.getCell("id").setValue(chooseRoomRd.get(i).getId());
					boolean colorFlag = ChooseRoomFactory.getRemoteInstance().isValid(chooseRoomRd.get(i).getId().toString());
					if(!ChooseRoomStateEnum.CancelChoose.equals(chooseRoomRd.get(i).getChooseRoomStateEnum())){
						if(!colorFlag){
							row.getStyleAttributes().setBackground(Color.PINK);
						}
					}
					}
				}
			
				}
			
				
				
		}

	}

 

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		
		if (e.getButton() == 1) {
			RoomInfo room = this.getSelectRoom();
			
			if (e.getClickCount() == 2) {
				if (room != null) {
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", room.getId().toString());

					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomSourceEditUI.class.getName(), uiContext, null, "VIEW");
					uiWindow.show();
				}

				reflashProOrBuildByPlanisphere();
			}
		}
	}

	/**
	 *设置选中房间的颜色
	 */
	private void SetSelectRoomColor(KDTSelectBlock block) throws Exception {
		if (block == null) {
			return;
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return;
		}
		RoomInfo roomInfo = (RoomInfo) cell.getUserObject();
		if (roomInfo == null)
			return;
		roomInfo = RoomFactory.getRemoteInstance().getRoomInfo("select isForSHE,sellState where id ='" + roomInfo.getId() + "'");
		SHEHelper.getAllChooseRoomIdSet();
		cell.getStyleAttributes().setBackground(SHEHelper.getRoomColorBySys(roomInfo, MoneySysTypeEnum.SalehouseSys, setting));
		SHEHelper.releaseChooseRoomIdSet();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {

		//非平面图显示时
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node!=null && !(node.getUserObject() instanceof PlanisphereInfo)) SetSelectRoomColor(e.getPrevSelectBlock());
		
		
		//this.actionShowRoomDetail.actionPerformed(new ActionEvent(this.menuItemShowRoomDetail, 0, "Clicked"));

		
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			SysUtil.abort();
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			SysUtil.abort();
		}
		RoomInfo roomq = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo){
			roomq = (RoomInfo) cell.getUserObject();
			if (roomq == null) {
				SysUtil.abort();
			}
			
			//传ID
		ICell activeCell = this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), tblMain.getSelectManager().getActiveColumnIndex());
		EntityViewInfo view =new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomq.getId().toString()));
		
	
		filter.getFilterItems().add(
				new FilterItemInfo("chooseRoomStateEnum",ChooseRoomStateEnum.AFFIRM_VALUE));
		view.setFilter(filter);
	
		IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
		ChooseRoomCollection chooseRoomCol=ichooseRoom.getChooseRoomCollection(view);
		if(null!=chooseRoomCol&&chooseRoomCol.size()>0){//加校验,有有效的选房单，就打开编辑界面，没有就新增
		for(int i = 0 ; i<chooseRoomCol.size();i++){
			this.getUIContext().put("croomID", chooseRoomCol.get(0).getId().toString());
		}
		}else{
			this.getUIContext().put("croomID", null);
		}
		
		
	
		if (roomq != null) {
			if (detailUI == null) {
				UIContext uiContext = new UIContext(ui);
				uiContext.put("DownPaneUI",ChooseRoomEditUI.class.getName());
				uiContext.put(UIContext.ID, roomq.getId().toString());
				//根据颜色判断，找不到传什么，选房单上，只能用房间信息来穿，还要找选房单，这需求坑爹啊
				uiContext.put("background",cell.getStyleAttributes().getBackground());
				detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
				
				// detailUI.setDataObject(room);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				// return;
			} else {
			
				detailUI.getUIContext().put(UIContext.ID, roomq.getId().toString());
				detailUI.getUIContext().put("background",cell.getStyleAttributes().getBackground());
				detailUI.getUIContext().put("DownPaneUI",ChooseRoomEditUI.class.getName());
				detailUI.onLoad();
			}
			// detailUI.setDataObject(detailUI.onLoad());
			// detailUI.loadFields();
		}		
		}
	}
	
	protected boolean checkBeforeWindowClosing() {
		return super.checkBeforeWindowClosing();
	}
		
	/**
	 * 如果是点击的是平面图的 房间 则选中房间轮廓
	 */
	private void reflashRoomInfoByPlanisphere() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null || !(node.getUserObject() instanceof PlanisphereInfo)) return;
			
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null)
			return;

		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null)
			return;
		if (cell.getUserObject() == null)
			return;

		if (cell.getUserObject() instanceof RoomInfo) {
			setRoomSelected((RoomInfo) cell.getUserObject());
		}
	}

	/**
	 * 如果是双击的是平面图的 项目 或 楼栋元素，则刷新树上的响应节点
	 */
	private void reflashProOrBuildByPlanisphere() {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null)
			return;

		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null)
			return;
		if (cell.getUserObject() == null)
			return;

		if (cell.getUserObject() instanceof BuildingInfo || cell.getUserObject() instanceof SellProjectInfo) {
			if (sellProAndBuildingMap.size() == 0) {
				sellProAndBuildingMap = FDCTreeHelper.getAllObjectIdMap((TreeNode) this.treeMain.getModel().getRoot(), "SellProject");
				sellProAndBuildingMap.putAll(FDCTreeHelper.getAllObjectIdMap((TreeNode) this.treeMain.getModel().getRoot(), "Building"));
			}
			FDCDataBaseInfo databaseInfo = (FDCDataBaseInfo) cell.getUserObject();
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) this.sellProAndBuildingMap.get(databaseInfo.getId().toString());
			if (thisNode != null)
				this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) thisNode);
		}

	}

	private void setRoomSelected(RoomInfo room) {
		if (room != null) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node != null && node.getUserObject() instanceof PlanisphereInfo) {
				EventListener[] listeners = this.tblMain.getListeners(KDTSelectListener.class);
				for (int i = 0; i < listeners.length; i++)
					this.tblMain.removeKDTSelectListener((KDTSelectListener) listeners[i]);

				PlanisphereInfo phInfo = (PlanisphereInfo) node.getUserObject();
				PlanisphereElementEntryCollection eleEntryColl = phInfo.getElementEntry();
				for (int i = 0; i < eleEntryColl.size(); i++) {
					RoomInfo roomInfo = eleEntryColl.get(i).getRoomEntry();
					if (roomInfo != null && roomInfo.getId().equals(room.getId())) {
						TableDrawManager thisPicTable = new TableDrawManager();
						thisPicTable.setTable(this.tblMain);
						thisPicTable.setSelected(CommerceHelper.ByteArrayToListObject(eleEntryColl.get(i).getOutLineLocationData()), room);
					}
				}

				for (int i = 0; i < listeners.length; i++)
					this.tblMain.addKDTSelectListener((KDTSelectListener) listeners[i]);
			}
		}
	}



	/**
	 * 获得选中的房间
	 * 
	 * @param reQuery
	 *            是否根据选中房间的ID重新查询,获得更多的字段值
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private ICell selCell =null;
	public RoomInfo getSelectRoom(boolean reQuery) throws EASBizException, BOSException, UuidException {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			return null;
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		//未确认状态下不能使用
		if(cell!=null){
			ChooseRoomDisplaySetting setting = new ChooseRoomDisplaySetting();
			if(setting.getChooseRoomAffirm().equals(cell.getStyleAttributes().getBackground())){
				this.actionPrePurchase.setEnabled(true);
				this.actionSignContract.setEnabled(true);
				this.actionPurchase.setEnabled(true);
				this.actionCancelChooseRoom.setEnabled(true);
			}else{
				this.actionPrePurchase.setEnabled(false);
				this.actionSignContract.setEnabled(false);
				this.actionPurchase.setEnabled(false);
				this.actionCancelChooseRoom.setEnabled(false);
			}
		}
		if (cell == null) {
			return null;
		}
		if(selCell!=null){
			selCell.getStyleAttributes().setBorderColor(Position.LEFT, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.TOP, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.RIGHT, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.BOTTOM, Color.BLACK);
			selCell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
		}
		selCell=cell;
		
		selCell.getStyleAttributes().setBorderColor(Position.LEFT, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.TOP, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.RIGHT, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.BOTTOM, Color.BLUE);
		selCell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.DOUBLE_LINE_A);
		RoomInfo room = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
			room = (RoomInfo) cell.getUserObject();
		
		if (room == null) {
			return null;
		}

		// 为了效率从userObject中只放了一个ID，所以需要再查一遍
		if (reQuery) {
			room = SHEHelper.queryRoomInfo(room.getId().toString());
			cell.setUserObject(room);
		}
		return room;
	}

	public RoomInfo getSelectRoom() throws EASBizException, BOSException, UuidException {
		return getSelectRoom(true);
	}

	protected void setActionState() {
		// super.setActionState();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
//		 this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad));
		//根据参数过滤楼栋节点写法  by tim_gao 2011-07-11 写的真的恶心
		this.treeMain.setModel(SHEHelper.getPlanisphereForChooseRoomParam(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys,set));
//		this.treeMain.setModel(SHEHelper.getPlanisphere(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		// this.treeMain.expandAllNodes(true, (TreeNode)
		// this.treeMain.getModel().getRoot());
		this.treeMain.expandOnLevel(3);

		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		refresh(null);
		//非平面图显示时
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if( node.getUserObject() instanceof BuildingInfo){
			addChooseRoomRecord((BuildingInfo)node.getUserObject(),null);
		}else if ( node.getUserObject() instanceof BuildingUnitInfo){
			addChooseRoomRecord((BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject(),(BuildingUnitInfo)node.getUserObject());
		}
		
	}

	

	protected void refresh(ActionEvent e) throws BOSException {
//		try {
//			cancelRoom();
//		} catch (EASBizException e1) {
//			e1.printStackTrace();
//		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

//		if (!hasOnLoadFlag) { // 防止第一次打开时默认选择根节点导致房间数量过大，报10000行错误
//			this.tblMain.removeColumns();
//			this.tblMain.removeHeadRows();
//			this.tblMain.removeRows();
//			return;
//		}

		// logger.info("构建房间图---- "+ System.currentTimeMillis() );

		// 由于平面图树是在单元树的基础上增加平面图节点生成的，会对单元树的depth产生影响,并影响房间表头的显示，
		// 因此需把所选节点克隆一份，并除去所有的平面图节点，然后再传递进去构建房间列表
		if (node.getUserObject() instanceof PlanisphereInfo) {
			// 显示平面图
			PlanisphereInfo phInfo = (PlanisphereInfo) node.getUserObject();
			if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
				this.kDSplitPane1.setDividerLocation(550);
				this.kDScrollPane1.setVisible(true);
			} else {
				this.kDSplitPane1.setDividerLocation(this.kDSplitPane1.getWidth());
				this.kDScrollPane1.setVisible(false);
			}
			SHEHelper.showPlanisphereTable(this.tblMain, phInfo, setting);
		} else {
			this.kDSplitPane1.setDividerLocation(550);
			this.kDScrollPane1.setVisible(true);
			DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node.clone();
			CommerceHelper.cloneTree(newNode, node);
			newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // 调用接口时会查询其父节点的对象
			// 　
			CommerceHelper.removePlanisphereNode(newNode);
			//我嘞个去，根据房间状态，选房单确认情况，自己写一个选房单3种状态的图像实现！！！累死
			try {
				SHEHelper.getAllChooseRoomIdSet();
				SHEHelper.fillRoomTableByNode(this.tblMain, newNode, MoneySysTypeEnum.SalehouseSys, null, setting);
				SHEHelper.releaseChooseRoomIdSet();
			} catch (EASBizException e1) {
				// TODO Auto-generated catch block
				logger.warn("加载房屋图像失效"+e1.getMessage());
			}
		}

//		 logger.info("构建房间图---- "+ System.currentTimeMillis());
		
		setSellStateCountByColor();
		if(node.getUserObject() instanceof BuildingInfo){
			BuildingInfo build = (BuildingInfo) node.getUserObject();
			try {
				addChooseRoomRecord(build,null);
			} catch (EASBizException e1) {
				// TODO Auto-generated catch block
				FDCMsgBox.showWarning("选房信息加载失败！");
				logger.warn("选房信息加载失败+++++++++++++++++++++="+e1.getMessage());
				SysUtil.abort();
			}
		}
		this.getUIContext().put("croomID", null);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	
	private Object [] getChooseRoomCustomer(RoomInfo room) throws BOSException{
		Object []value=new Object [2];
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("customerEntry.customer.*");
		selector.add("customerEntry.customer.certificateType.*");
		EntityViewInfo view =new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId()));

		filter.getFilterItems().add(
				new FilterItemInfo("chooseRoomStateEnum",ChooseRoomStateEnum.CancelChoose,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(selector);
		IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
		ChooseRoomCollection chooseRoomCol=ichooseRoom.getChooseRoomCollection(view);
		
		if(chooseRoomCol.size()==0){
			MsgBox.showWarning("无选房单据！");
	        SysUtil.abort();
		}
        
		ArrayList customer=new ArrayList();
		for(int i=0;i<chooseRoomCol.get(0).getCustomerEntry().size();i++){
			customer.add(chooseRoomCol.get(0).getCustomerEntry().get(i).getCustomer());
		}
		value[0]=chooseRoomCol.get(0).getId();
		value[1]=customer;
		return value;
	}

	public void actionPurchase_actionPerformed(ActionEvent e) throws Exception {
		if(kDSplitPane1.isShowing()){
			RoomInfo room = this.getSelectRoom();
			if (room == null) {
				MsgBox.showWarning("请选择房间！");
	            SysUtil.abort();
			}
			Object [] value=getChooseRoomCustomer(room);
			SHEManageHelper.toTransactionBill((BOSUuid)value[0],room.getId(), this,PurchaseManageEditUI.class.getName(),(List)value[1]);
		}else{
			if (tblMain1.getRowCount() == 0 || tblMain1.getSelectManager().size() == 0){
	            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
	            SysUtil.abort();
	        }
			int rowIndex = this.tblMain1.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain1.getRow(rowIndex);
			String id = row.getCell("id").getValue().toString();
			ChooseRoomInfo info=ChooseRoomFactory.getRemoteInstance().getChooseRoomInfo(new ObjectUuidPK(id));
			ArrayList customer=new ArrayList();
    		for(int i=0;i<info.getCustomerEntry().size();i++){
    			customer.add(info.getCustomerEntry().get(i).getCustomer());
    		}
			SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,PurchaseManageEditUI.class.getName(), customer);
		}
	}
	
	public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception {
		if(kDSplitPane1.isShowing()){
			RoomInfo room = this.getSelectRoom();
			if (room == null) {
				MsgBox.showWarning("请选择房间！");
	            SysUtil.abort();
			}
			Object [] value=getChooseRoomCustomer(room);
			SHEManageHelper.toTransactionBill((BOSUuid)value[0],room.getId(), this,PrePurchaseManageEditUI.class.getName(),(List)value[1]);
		}else{
			if (tblMain1.getRowCount() == 0 || tblMain1.getSelectManager().size() == 0){
	            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
	            SysUtil.abort();
	        }
			int rowIndex = this.tblMain1.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain1.getRow(rowIndex);
			String id =  row.getCell("id").getValue().toString();
			ChooseRoomInfo info=ChooseRoomFactory.getRemoteInstance().getChooseRoomInfo(new ObjectUuidPK(id));
			ArrayList customer=new ArrayList();
    		for(int i=0;i<info.getCustomerEntry().size();i++){
    			customer.add(info.getCustomerEntry().get(i).getCustomer());
    		}
			SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,PrePurchaseManageEditUI.class.getName(), customer);
		}
	}

	public void actionSignContract_actionPerformed(ActionEvent e) throws Exception {
		if(kDSplitPane1.isShowing()){
			RoomInfo room = this.getSelectRoom();
			if (room == null) {
				MsgBox.showWarning("请选择房间！");
	            SysUtil.abort();
			}
			Object [] value=getChooseRoomCustomer(room);
			SHEManageHelper.toTransactionBill((BOSUuid)value[0],room.getId(), this,SignManageEditUI.class.getName(),(List)value[1]);
		}else{
			if (tblMain1.getRowCount() == 0 || tblMain1.getSelectManager().size() == 0){
	            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
	            SysUtil.abort();
	        }
			int rowIndex = this.tblMain1.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain1.getRow(rowIndex);
			String id =  row.getCell("id").getValue().toString();
			ChooseRoomInfo info=ChooseRoomFactory.getRemoteInstance().getChooseRoomInfo(new ObjectUuidPK(id));
			ArrayList customer=new ArrayList();
    		for(int i=0;i<info.getCustomerEntry().size();i++){
    			customer.add(info.getCustomerEntry().get(i).getCustomer());
    		}
			SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,SignManageEditUI.class.getName(), customer);
		}
	}

	private void showEditSignContract(RoomSignContractInfo sign) throws UIException {
		BuildingInfo building = sign.getRoom().getBuilding();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", sign.getId().toString());
		uiContext.put("building", building);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	private void showAddSignContract(RoomInfo room) throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("room", room);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof Integer) {
			Integer unit = (Integer) node.getUserObject();
			uiContext.put("unit", unit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("building", building);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
				uiContext.put("building", building);
				uiContext.put("unit", new Integer(0));
			}
		}else{
			BuildingInfo building = room.getBuilding();
			uiContext.put("building", building);
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("sincerityState",SincerityPurchaseStateEnum.ARRANGENUM_VALUE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("room.building.*");
		sels.add("room.roomModel.*");
		sels.add("room.building.sellProject.name");
		sels.add("sellProject.*");
		view.setSelector(sels);
		SincerityPurchaseCollection sinColl =null;
		try {
			sinColl = SincerityPurchaseFactory
					.getRemoteInstance().getSincerityPurchaseCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(sinColl!=null && sinColl.size()>0){
			uiContext.put("sincerity", sinColl.get(0));
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
	}

	


	

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room != null) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", room.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "VIEW");
			uiWindow.show();
		}
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}




	
	private boolean checkPurchaseFundComp(String purchaseId){
		
		boolean result = true;
		try{
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select ");		
			
			builder.appendSql(" sum(case when FisEarnestInHouseAmount=1   ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
			builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FAppAmount        ");
			builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
			builder.appendSql("                                                           then payListEntry.FAppAmount        ");
			builder.appendSql("          else 0 end) as apAmount,		                                                     ");
			builder.appendSql(" sum(case when FisEarnestInHouseAmount=1                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
			builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FActrevAmount    ");
			builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
			builder.appendSql("                                                           then payListEntry.FActrevAmount    ");
			builder.appendSql("          else 0 end) as actPayAmount                                                         ");
			builder.appendSql(" FROM T_SHE_Purchase   PURCHASE ");
			builder.appendSql(" left join T_SHE_PurchasePayListEntry   payListEntry  ");
			builder.appendSql(" on payListEntry.FHeadID=PURCHASE.FID  ");
			builder.appendSql(" left join T_SHE_MoneyDefine moneyDefine on  ");
			builder.appendSql(" moneyDefine.fid=payListEntry.FMoneyDefineID   ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_Room ROOM ");
			builder.appendSql(" ON PURCHASE.FRoomID = ROOM.FID ");
			builder.appendSql(" INNER JOIN T_SHE_Building BUILDING ");
			builder.appendSql(" ON ROOM.FBuildingID = BUILDING.FID ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_BuildingUnit BUILDUNIT ");
			builder.appendSql(" ON ROOM.FBuildUnitID = BUILDUNIT.FID ");
			builder.appendSql(" INNER JOIN T_SHE_SellProject SELLPROJECT ");
			builder.appendSql(" ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_Subarea SUBAREA ");
			builder.appendSql(" ON BUILDING.FSubareaID = SUBAREA.FID ");
			builder.appendSql(" where  1=1 ");
			builder.appendSql(" and PURCHASE.fid='"+purchaseId+"'");
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				builder.appendSql(" and buildUnit.fid='" + buildUnit.getId() + "'  ");
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				builder.appendSql(" and building.fid='" + building.getId() + "'  ");
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProInfo = (SellProjectInfo) node.getUserObject();
				builder.appendSql(" and sellProject.fid='" + sellProInfo.getId() + "'  ");
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subAreaInfo = (SubareaInfo) node.getUserObject();
				builder.appendSql(" and subarea.fid='" + subAreaInfo.getId() + "'  ");
			} 
		
			IRowSet termQuitSellSet = builder.executeQuery();
			if (termQuitSellSet.next()) {
				BigDecimal apAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("apAmount"));
				BigDecimal payAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("actPayAmount"));		
				if (payAmount.compareTo(apAmount) >= 0&&payAmount.compareTo(FDCHelper.ZERO)>0) {
					//FDCMsgBox.showWarning(this,"所有款项已经收齐，不能变更!");
					//SysUtil.abort();
					result = false;
				}
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		return result;
		
	}

    /**
     * output tblMain1_tableClicked method
     */
    protected void tblMain1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//    	if (tblMain1.getRowCount() == 0 || tblMain1.getSelectManager().size() == 0){
//            
//            SysUtil.abort();
//        }
//		int rowIndex = this.tblMain1.getSelectManager().getActiveRowIndex();
//		IRow row = this.tblMain1.getRow(rowIndex);
//		String id = (String) row.getCell("id").getValue().toString();
//		ChooseRoomInfo info=ChooseRoomFactory.getRemoteInstance().getChooseRoomInfo(new ObjectUuidPK(id));
//		
//		this.btnCancelChooseRoom.setEnabled(false);
//		this.btnPrePurchase.setEnabled(false);
//		this.btnPurchase.setEnabled(false);
//		this.btnSignContract.setEnabled(false);
//		//未确认状态下不能使用
//		if(ChooseRoomStateEnum.Affirm.equals(info.getChooseRoomStateEnum())){
//			this.btnCancelChooseRoom.setEnabled(true);
//			this.btnPrePurchase.setEnabled(true);
//			this.btnPurchase.setEnabled(true);
//			this.btnSignContract.setEnabled(true);}
//	
//    	super.tblMain1_tableClicked(e);
   
//    	addChooseRoomRecord();
    }
	 /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {	
    	super.kDTabbedPane1_stateChanged(e);
    }
}