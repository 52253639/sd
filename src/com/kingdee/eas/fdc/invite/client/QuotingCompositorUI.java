/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 报价排名表
 */
public class QuotingCompositorUI extends AbstractQuotingCompositorUI {
	private static final Logger logger = CoreUIObject
			.getLogger(QuotingCompositorUI.class);
	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";

	private String inviteListingId;

	/**
	 * output class constructor
	 */
	public QuotingCompositorUI() throws Exception {
		super();
		//this.btnExportToDB.setVisible(false);
		//this.btnExportToExcel.setVisible(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		fillTable();
	}
	/***************************************************************************
	 * 中标
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void actionAcceptBid_actionPerformed(ActionEvent e)
			throws EASBizException, BOSException {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		
		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = this.tblMain.getRow(rowIndex);
			if (row == null) {
				return;
			}
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) row
					.getUserObject();
			// already is accepted bid
			if (quoting.getStatus().equals(QuotingPriceStatusEnum.ImportBase)||
					quoting.getStatus().equals(QuotingPriceStatusEnum.ImportToDB)) {
				MsgBox.showInfo(this, EASResource.getString(resourcePath,"alreadyIsAcceptBid"));
				return;
			}
			// if there is another bid has accepted
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				IRow row2 = this.tblMain.getRow(i);
				NewQuotingPriceInfo quoting2 = (NewQuotingPriceInfo) row2
						.getUserObject();
				if (quoting2.getStatus().equals(
						QuotingPriceStatusEnum.ImportBase)
						&& !quoting2.getId().toString().equals(
								quoting.getId().toString())) {
					MsgBox.showInfo(this, EASResource.getString(resourcePath,"thereIsAnotherRowWinTheBid"));
					return;
				}
			}
			String quotingId = quoting.getId().toString();
			NewQuotingPriceFactory.getRemoteInstance().acceptBid(quotingId);
			MsgBox.showInfo(this, EASResource.getString(resourcePath,"succeedInAcceptBid"));
		} else {
			MsgBox.showInfo(this, EASResource.getString(resourcePath,"pleaseSelectOneRow"));
		}
		fillTable();
	}

	/***************************************************************************
	 * 反中标
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void actionUnacceptBid_actionPerformed(ActionEvent e)
			throws EASBizException, BOSException {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = this.tblMain.getRow(rowIndex);
			if (row == null) {
				return;
			}
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) row
					.getUserObject();
			unBid(this,quoting);
			btnAcceptBid.setEnabled(true);
		} else {
			MsgBox.showInfo(this, EASResource.getString(resourcePath,"pleaseSelectOneRow"));
		}
		fillTable();
	}
	
	public static void unBid(CoreUI coreui,NewQuotingPriceInfo quoting) throws EASBizException, BOSException{
		//		 只有中标报价才能反中标
		if (quoting.getStatus().equals(QuotingPriceStatusEnum.ImportBase)
				|| quoting.getStatus().equals(
						QuotingPriceStatusEnum.ImportToDB)) {
			// 已经写入了数据库
			if (quoting.getStatus().equals(
					QuotingPriceStatusEnum.ImportToDB)) {
				MsgBox.showInfo(coreui, EASResource.getString(resourcePath,"alreadyToDBCanntAcceptBid"));
				return;
			}
			// 反中标
			String quotingId = quoting.getId().toString();
			NewQuotingPriceFactory.getRemoteInstance().unacceptBid(
					quotingId);
			MsgBox.showInfo(coreui, EASResource.getString(resourcePath,"succeedInInvalidateBid"));
		} else {
			MsgBox.showInfo(coreui, EASResource.getString(resourcePath,"onlyAcceptedBidCanInvalidate"));
		}
	}

	/**
	 * 导出到数据库
	 */
	public void actionExportToDB_actionPerformed(ActionEvent e)
			throws Exception {
		
		IObjectPK orgPK = getOrgPK(this.actionExportToDB);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_exportDataBase");
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = this.tblMain.getRow(rowIndex);
			if (row == null) {
				return;
			}
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) row	.getUserObject();
			//只有中标报价才能导出到数据库
			
			//由于需求变更，将其改为参与评标的进行报价导入
			if (quoting.getStatus().equals(QuotingPriceStatusEnum.ImportBase)
					|| quoting.getStatus().equals(QuotingPriceStatusEnum.ImportToDB) ){
					//|| quoting.getStatus().equals(QuotingPriceStatusEnum.Evaluated)) {
				if (quoting.getStatus().equals(
						QuotingPriceStatusEnum.ImportToDB)) {
					if (MsgBox.showConfirm2(this, EASResource.getString(resourcePath,"repeatToDB")) == MsgBox.CANCEL) {
						return;
					}
				}
				this.inviteListingId = (String) this.getUIContext().get("inviteListingId");
				String quotingId = quoting.getId().toString();
				NewQuotingPriceFactory.getRemoteInstance().acceptBidExportQuoting(quotingId, inviteListingId);
				MsgBox.showInfo(this, EASResource.getString(resourcePath,"succeedToDB"));
			} else {
				MsgBox.showInfo(this, EASResource.getString(resourcePath,"onlyAcceptedBidCanToDB"));
			}
		} else {
			MsgBox.showInfo(this, EASResource.getString(resourcePath,"pleaseSelectOneRow"));
		}
		fillTable();
	}

	/**
	 * output actionExportToExcel_actionPerformed
	 */
	public void actionExportToExcel_actionPerformed(ActionEvent e)
			throws Exception {
		
		IObjectPK orgPK = getOrgPK(this.actionExportToExcel);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_exportExcel");
		
		File tempFile = File.createTempFile("eastemp", ".xls");
		tblMain.getIOManager().setTempFileDirection(tempFile.getPath());
		tblMain.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();

	}

	public static void showUI(CoreUI ui, String inviteListingId)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("inviteListingId", inviteListingId);
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(QuotingCompositorUI.class.getName(), uiContext, null,
						"VIEW");
		window.show();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.checkParsed();
		tblMain.getColumn("price").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("price").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.inviteListingId = (String) this.getUIContext().get(
				"inviteListingId");
		this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.actionRefresh.setEnabled(true);		
		this.btnAcceptBid.setEnabled(true);
		this.btnAcceptBid.setIcon(EASResource.getIcon("imgTbtn_grantcollocate"));
		this.btnUnacceptBid.setEnabled(true);
		this.btnUnacceptBid.setIcon(EASResource.getIcon("imgTbtn_cancelcollocate"));
		this.btnExportToDB.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.actionExportToDB.setEnabled(true);
		this.btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		this.actionExportToExcel.setEnabled(true);
		fillTable();
		
		this.actionAcceptBid.setVisible(false);
		this.actionUnacceptBid.setVisible(false);
	}

	private void fillTable() throws BOSException {
		this.tblMain.removeRows();
		NewQuotingPriceCollection quotingPrices = this.getQuotingPrice();
		Map map = new TreeMap();
		for (int j = 0; j < quotingPrices.size(); j++) {
			NewQuotingPriceInfo quotingPrice = quotingPrices.get(j);
			BigDecimal value = quotingPrice.getTotoalPrice();
			List sameList = null;
			if (map.containsKey(value)) {
				sameList = (List) map.get(value);
			} else {
				sameList = new ArrayList();
				map.put(value, sameList);
			}
			sameList.add(quotingPrice);
		}
		int compositor = 1;
		Collection valCol = map.values();
		BigDecimal firstPrice = FDCHelper.ZERO;
		for (Iterator iter = valCol.iterator(); iter.hasNext();) {
			List sameList = (List) iter.next();
			if (compositor == 1) {
				firstPrice = ((NewQuotingPriceInfo) sameList.get(0))
						.getTotoalPrice();
			}
			for (int j = 0; j < sameList.size(); j++) {
				NewQuotingPriceInfo quotingPrice = (NewQuotingPriceInfo) sameList
						.get(j);
				IRow row = this.tblMain.addRow();
				row.setUserObject(quotingPrice);
				row.getCell("id").setValue(quotingPrice.getId().toString());
				row.getCell("compositor").setValue(new Integer(compositor));
				row.getCell("bidderNumber").setValue(
						quotingPrice.getSupplier().getNumber());
				row.getCell("bidderName").setValue(
						quotingPrice.getSupplier().getName());
				row.getCell("price").setValue(
						quotingPrice.getTotoalPrice().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				if (firstPrice == null
						|| firstPrice.compareTo(FDCHelper.ZERO) == 0) {
					row.getCell("percent").setValue("报价最近价为0");
				} else {
					BigDecimal percent = quotingPrice.getTotoalPrice()
							.multiply(FDCHelper.ONE_HUNDRED).divide(firstPrice,
									2, BigDecimal.ROUND_HALF_UP);
					row.getCell("percent").setValue(percent.toString() + "%");
				}
				if (quotingPrice.getStatus().equals(
						QuotingPriceStatusEnum.ImportBase)||
						quotingPrice.getStatus().equals(
								QuotingPriceStatusEnum.ImportToDB)) {
					row.getStyleAttributes().setBackground(
							new Color(104, 245, 59));
					if(quotingPrice.getStatus().equals(
							QuotingPriceStatusEnum.ImportBase))
						this.btnAcceptBid.setEnabled(false);
					if(quotingPrice.getStatus().equals(
							QuotingPriceStatusEnum.ImportToDB))
					{
						this.btnAcceptBid.setEnabled(false);
						this.btnExportToDB.setEnabled(false);
						this.btnUnacceptBid.setEnabled(false);
					}
				}
			}
			compositor += sameList.size();
		}
	}

	private NewQuotingPriceCollection getQuotingPrice() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		filter.getFilterItems().add(
				new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(
				new FilterItemInfo("hasEffected", Boolean.TRUE));
		NewQuotingPriceCollection quotingPrices = NewQuotingPriceFactory
				.getRemoteInstance().getNewQuotingPriceCollection(view);
		return quotingPrices;
	}
}