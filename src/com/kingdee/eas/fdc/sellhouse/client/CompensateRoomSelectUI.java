/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CompensateRoomSelectUI extends AbstractCompensateRoomSelectUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8002712530499026783L;
	private static final Logger logger = CoreUIObject
			.getLogger(CompensateRoomSelectUI.class);
	private String uuid = "";

	/**
	 * output class constructor
	 */
	public CompensateRoomSelectUI() throws Exception {
		super();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAreaCompensateFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (this.getUIContext().get("selectCompensateId") != null) {
			uuid = this.getUIContext().get("selectCompensateId").toString();
		}

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionAttachment.setVisible(false);
		
		this.prmtSellProject.setEnabled(false);
		this.txtCustomer.setEnabled(false);
		this.pkPuchaseDate.setEnabled(false);
		this.pkSignDate.setEnabled(false);
		this.txtBuildArea.setEnabled(false);
		this.txtRoomArea.setEnabled(false);
		this.txtRoom.setEnabled(false);
		this.txtPhone.setEnabled(false);
		this.txtPurchaseNumber.setEnabled(false);
		this.txtConNumber.setEnabled(false);
		this.txtActBuildArea.setEnabled(false);
		this.txtActRoomArea.setEnabled(false);
		this.txtSaleMan.setEnabled(false);
		this.prmtPayType.setEnabled(false);
		this.txtSellTotal.setEnabled(false);
		this.txtConBuildArea.setEnabled(false);
		this.txtConRoomArea.setEnabled(false);
		this.txtCompensateNumber.setEnabled(false);
		this.txtScheme.setEnabled(false);
		this.txtReferAmount.setEnabled(false);
		this.txtLastAmount.setEnabled(false);
		this.pkCompensateDate.setEnabled(false);
		this.txtMainAmount.setEnabled(false);
		this.txtActAmount.setEnabled(false);
		this.prmtHandler.setEnabled(false);
		this.txtAttAmount.setEnabled(false);
		this.txtRate.setEnabled(false);
		this.txtDesc.setEnabled(false);

		fillData();
	}

	private void fillData() {

		if (!"".equals(this.uuid)) {
			try {
				IRowSet rs = RoomAreaCompensateFactory.getRemoteInstance()
						.getCompenstateRoomInfo(this.uuid);
				showInfo(rs);
				showPurchaseInfo(this.uuid);
			} catch (EASBizException e) {
				logger.error(e.getMessage() + "得到房间补差信息失败!");
			} catch (BOSException e) {
				logger.error(e.getMessage() + "得到房间补差信息失败!");
			} catch (Exception e) {
				logger.error(e.getMessage() + "得到房间补差信息失败!");
			}
		} else {
			FDCMsgBox.showWarning(this, "传入参数错误,请检查!");
			this.abort();
		}

	}

	private void showPurchaseInfo(String uuid2) throws EASBizException,
			BOSException {
		this.pkPuchaseDate.setValue(null);
		PurchaseManageInfo info = PurchaseManageFactory.getRemoteInstance()
				.getPurchaseManageInfo(
						"select id,createTime,number where room.id='" + uuid2
								+ "'");
		if (info != null) {
			this.txtPurchaseNumber.setText(info.getNumber());
			this.pkPuchaseDate.setValue(info.getCreateTime());
		}
	}

	private void showInfo(IRowSet rs) throws SQLException {
		if (rs.next()) {
			if (rs.getString("sellProjectId") != null) {
				SellProjectInfo sellproject = new SellProjectInfo();
				sellproject.setId(BOSUuid.read(rs.getString("sellProjectId")));
				sellproject.setName(rs.getString("sellProjectName"));
				sellproject.setNumber(rs.getString("sellProjectNumber"));

				this.prmtSellProject.setValue(sellproject);
			}
			if (rs.getString("roomName") != null) {
				this.txtRoom.setText(rs.getString("roomName"));
			}
			if (rs.getString("customerName") != null) {
				this.txtCustomer.setText(rs.getString("customerName"));
			}

			if (rs.getString("customerPhone") != null) {
				this.txtPhone.setText(rs.getString("customerPhone"));
			}
			if (rs.getString("saleManName") != null) {
				this.txtSaleMan.setText(rs.getString("saleManName"));
			}
			if (rs.getString("payTypeId") != null) {
				SHEPayTypeInfo payType = new SHEPayTypeInfo();
				payType.setId(BOSUuid.read(rs.getString("payTypeId")));
				if (rs.getString("payTypeName") != null) {
					payType.setName(rs.getString("payTypeName"));
				}
				if (rs.getString("payTypeNumber") != null) {
					payType.setNumber(rs.getString("payTypeNumber"));
				}
			}

			if (rs.getDate("signDate") != null) {
				this.pkSignDate.setValue(rs.getDate("signDate"));
			}

			if (rs.getString("conNumber") != null) {
				this.txtConNumber.setText(rs.getString("conNumber"));
			}

			if (rs.getBigDecimal("sellAmount") != null) {
				this.txtSellTotal.setValue(rs.getBigDecimal("sellAmount"));
			}
			if (rs.getBigDecimal("buildArea") != null) {
				this.txtBuildArea.setValue(rs.getBigDecimal("buildArea"));
				this.txtConBuildArea.setValue(rs.getBigDecimal("buildArea"));
			}
			if (rs.getBigDecimal("roomArea") != null) {
				this.txtRoomArea.setValue(rs.getBigDecimal("roomArea"));
				this.txtConRoomArea.setValue(rs.getBigDecimal("roomArea"));
			}
			if (rs.getBigDecimal("actBuildArea") != null) {
				this.txtActBuildArea.setValue(rs.getBigDecimal("actBuildArea"));
			}
			if (rs.getBigDecimal("actRoomArea") != null) {
				this.txtActRoomArea.setValue(rs.getBigDecimal("actRoomArea"));
			}
			if (rs.getString("roomAreaNumber") != null) {
				this.txtCompensateNumber
						.setText(rs.getString("roomAreaNumber"));
			}
			if (rs.getDate("compDate") != null) {
				this.pkCompensateDate.setValue(rs.getDate("compDate"));
			}

			if (rs.getString("hanlderId") != null) {
				UserInfo hanlder = new UserInfo();
				hanlder.setId(BOSUuid.read(rs.getString("hanlderId")));
				if (rs.getString("hanlderName") != null) {
					hanlder.setName(rs.getString("hanlderName"));
				}
				if (rs.getString("hanlderNumber") != null) {
					hanlder.setNumber(rs.getString("hanlderNumber"));
				}
				this.prmtHandler.setValue(hanlder);
			}
			
			if (rs.getString("hanlderId") != null) {
				SHEPayTypeInfo hanlder = new SHEPayTypeInfo();
				hanlder.setId(BOSUuid.read(rs.getString("payTypeId")));
				if (rs.getString("payTypeName") != null) {
					hanlder.setName(rs.getString("payTypeName"));
				}
				if (rs.getString("payTypeNumber") != null) {
					hanlder.setNumber(rs.getString("payTypeNumber"));
				}
				this.prmtPayType.setValue(hanlder);
			}
			
			if (rs.getString("schemeNumber") != null) {
				this.txtScheme.setText(rs.getString("schemeNumber"));
			}
			if (rs.getBigDecimal("mainAmount") != null) {
				this.txtMainAmount.setValue(rs.getBigDecimal("mainAmount"));
			}
			if (rs.getBigDecimal("attAmount") != null) {
				this.txtAttAmount.setValue(rs.getBigDecimal("attAmount"));
			}
			if (rs.getBigDecimal("refAmount") != null) {
				this.txtReferAmount.setValue(rs.getBigDecimal("refAmount"));
			}
			if (rs.getBigDecimal("actAmount") != null) {
				this.txtActAmount.setValue(rs.getBigDecimal("actAmount"));
			}
			if(rs.getBigDecimal("lastAmount")!=null){
				this.txtLastAmount.setValue(rs.getBigDecimal("lastAmount"));
			}
			if (rs.getBigDecimal("rate") != null) {
				this.txtRate.setValue(rs.getBigDecimal("rate"));
			}
			if (rs.getString("description") != null) {
				this.txtDesc.setText(rs.getString("description"));
			}
		}
	}

}