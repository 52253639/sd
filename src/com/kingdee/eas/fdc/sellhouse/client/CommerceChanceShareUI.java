/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.achievementmgmt.UserTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSaleManFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CommerceChanceShareUI extends AbstractCommerceChanceShareUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2101988741722139900L;
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceShareUI.class);

	private String id = "";

	/**
	 * output class constructor
	 */
	public CommerceChanceShareUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.actionAddNew.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.prmtSaleMan.setRequired(true);

		if (this.getUIContext().get("CommerceChanceID") != null) {
			this.id = this.getUIContext().get("CommerceChanceID").toString();
		}

		initTransfor();
	}

	private void initTransfor() throws EASBizException, BOSException {
		/*if(this.getUIContext().get("CommerceChanceProject")!=null){
			SellProjectInfo  info = (SellProjectInfo)this.getUIContext().get("CommerceChanceProject");
			EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(info,false);
			this.prmtSaleMan.setEntityViewInfo(view);	
		}*/
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("type", String.valueOf(UserType.PERSON_VALUE),
						CompareType.EQUALS));
		
		filterInfo.getFilterItems().add(
				new FilterItemInfo("type", String.valueOf(UserType.OTHER_VALUE),
						CompareType.EQUALS));
		
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isDelete", Boolean.FALSE,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isLocked", Boolean.FALSE,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isForbidden", Boolean.FALSE,
						CompareType.EQUALS));
		String queryInfo = "com.kingdee.eas.base.permission.app.F7UserQuery";
		filterInfo.setMaskString("(#0 or #1) and #2 and #3 and #4");
		SHEHelper.initF7(this.prmtSaleMan, queryInfo, filterInfo);
	}	

	
	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSaleMan);
		CommerceChanceInfo customerInfo = null;
		UserInfo user = (UserInfo) this.prmtSaleMan.getValue();

		try {
			customerInfo = CommerceChanceFactory.getRemoteInstance()
					.getCommerceChanceInfo(
							"select customer.id,customer.name,customer.number where id='"
									+ this.id + "'");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		if (customerInfo != null && customerInfo.getCustomer() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("customer.id", customerInfo
							.getCustomer().getId().toString(),
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("saleMan.id", user.getId().toString(),
							CompareType.EQUALS));
			boolean isShare = CommerceChanceFactory.getRemoteInstance().exists(
					filter);
			if (isShare) {
				FDCMsgBox.showWarning(this, "此共享人已为此用户建立商机,不能共享!");
				this.abort();
			}
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head", this.id, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("member.id", user.getId().toString(),
						CompareType.EQUALS));
		boolean res = ShareSaleManFactory.getRemoteInstance().exists(filter);

		if (res) {
			FDCMsgBox.showWarning(this, "已经共享给此置业顾问，请不要重复操作!");
			this.abort();
		}
		super.btnConfirm_actionPerformed(e);

		// 判断用户是否在当前的营销团队中
		if (isExitsForMarketing(user)) {

			UserInfo loginUser = SysContext.getSysContext()
					.getCurrentUserInfo();

			// 判断当前用户是否是负责人
			if (isDuty(loginUser)) {
				if (loginUser.getId().toString()
						.equals(user.getId().toString())) {
					FDCMsgBox.showWarning(this, "不能共享给自己，请重新选择!");
					this.abort();
				}
				// 共享代码
				if (!"".equals(this.id)) {
					try {
						String uuid = BOSUuid.create("8E71290F").toString();
						FDCSQLBuilder sql = new FDCSQLBuilder();
						sql
								.appendSql("insert into T_SHE_ShareSaleMan (fid,fheadid,FMemberID) values(?,?,?)");
						sql.addParam(uuid);
						sql.addParam(this.id);
						sql.addParam(user.getId().toString());
						sql.execute();
						FDCMsgBox.showWarning(this, "商机共享成功!");
						this.uiWindow.close();
					} catch (Exception ex) {
						logger.error(ex.getMessage());
						FDCMsgBox.showWarning(this, "商机共享失败!");
						this.abort();
					}

				} else {
					FDCMsgBox.showWarning(this, "商机共享失败!");
					this.abort();
				}
			} else {
				// 判断用户是否
				if (isDuty(user)) {
					FDCMsgBox.showWarning(this, "不能共享给团队负责人，请重新选择!");
					this.abort();
				} else {
					if (loginUser.getId().toString().equals(
							user.getId().toString())) {
						FDCMsgBox.showWarning(this, "不能共享给自己，请重新选择!");
						this.abort();
					}
					// 共享代码
					if (!"".equals(this.id)) {
						try {
							String uuid = BOSUuid.create("8E71290F").toString();
							FDCSQLBuilder sql = new FDCSQLBuilder();
							sql
									.appendSql("insert into T_SHE_ShareSaleMan (fid,fheadid,FMemberID) values(?,?,?)");
							sql.addParam(uuid);
							sql.addParam(this.id);
							sql.addParam(user.getId().toString());
							sql.execute();
							FDCMsgBox.showWarning(this, "商机共享成功!");
							this.uiWindow.close();
						} catch (Exception ex) {
							logger.error(ex.getMessage());
							FDCMsgBox.showWarning(this, "商机共享失败!");
							this.abort();
						}

					} else {
						FDCMsgBox.showWarning(this, "商机共享失败!");
						this.abort();
					}
				}
			}

		} else {
			FDCMsgBox.showWarning(this, "此置业顾问不在营销团队中，请重新选择!");
			this.abort();
		}

	}

	protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
		super.btnQuit_actionPerformed(e);
		this.uiWindow.close();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	/**
	 * 判断传入的用户是否在当前用户的营销团队中
	 * 
	 * @param user
	 */
	private boolean isExitsForMarketing(UserInfo user) {

		boolean isExits = false;
		UserInfo loginUser = SysContext.getSysContext().getCurrentUserInfo();
		try {
			MarketingUnitMemberCollection marketCol = MarketingUnitFactory
					.getRemoteInstance().getMarketUnitCollMember(loginUser);
			for (int j = 0; j < marketCol.size(); j++) {
				MarketingUnitMemberInfo memInfo = marketCol.get(j);
				UserInfo userInfo = memInfo.getMember();
				if (userInfo.getId().toString().equals(user.getId().toString())) {
					isExits = true;
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return isExits;
	}

	/**
	 * 判断当前用户是否营销团队中的负责人
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean isDuty(UserInfo user) throws BOSException, EASBizException {
		boolean isDuty = false;
		MarketingUnitMemberCollection marketCol = MarketingUnitFactory
				.getRemoteInstance().getMarketUnitCollMember(user);

		for (int j = 0; j < marketCol.size(); j++) {
			MarketingUnitMemberInfo memInfo = marketCol.get(j);
			UserInfo userInfo = memInfo.getMember();
			if (userInfo.getId().toString().equals(user.getId().toString())
					&& memInfo.isIsDuty()) {
				isDuty = true;
			}
		}
		return isDuty;
	}

}