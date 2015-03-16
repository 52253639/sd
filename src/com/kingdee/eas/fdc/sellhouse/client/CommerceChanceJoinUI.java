/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CommerceChanceJoinUI extends AbstractCommerceChanceJoinUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3209251365245562223L;

	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceJoinUI.class);

	private String id = "";

	/**
	 * output class constructor
	 */
	public CommerceChanceJoinUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
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

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSaleMan);
		super.btnConfirm_actionPerformed(e);

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
			FilterInfo newFilter = new FilterInfo();
			newFilter.getFilterItems().add(
					new FilterItemInfo("customer.id", customerInfo
							.getCustomer().getId().toString(),
							CompareType.EQUALS));
			newFilter.getFilterItems().add(
					new FilterItemInfo("saleMan.id", user.getId().toString(),
							CompareType.EQUALS));
			boolean isShare = CommerceChanceFactory.getRemoteInstance().exists(
					newFilter);
			if (isShare) {
				FDCMsgBox.showWarning(this, "此转交人已为此用户建立商机,不能转交!");
				this.abort();
			}
		}

		/*FilterInfo turnFilter = new FilterInfo();
		turnFilter.getFilterItems().add(
				new FilterItemInfo("head", this.id, CompareType.EQUALS));
		turnFilter.getFilterItems().add(
				new FilterItemInfo("member.id", user.getId().toString(),
						CompareType.EQUALS));
		boolean res = CommerceChanceFactory.getRemoteInstance().exists(turnFilter);

		if (res) {
			FDCMsgBox.showWarning(this, "已经转交给此置业顾问，请不要重复操作!");
			this.abort();
		}*/

		// 判断用户是否在当前的营销团队中
		if (isExitsForMarketing(user)) {

			UserInfo loginUser = SysContext.getSysContext()
					.getCurrentUserInfo();

			// 判断当前用户是否是负责人
			if (isDuty(loginUser)) {
				/*if (loginUser.getId().toString()
						.equals(user.getId().toString())) {
					FDCMsgBox.showWarning(this, "不能转交给自己，请重新选择!");
					this.abort();
				}*/

				if (!"".equals(this.id)) {

					try {
						customerInfo = CommerceChanceFactory
								.getRemoteInstance().getCommerceChanceInfo(
										"select id,customer.id,customer.name,customer.number where id='"
												+ this.id + "'");
					} catch (Exception ex) {
						logger.error(ex.getMessage());
					}

					if (customerInfo != null
							&& customerInfo.getCustomer() != null) {
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("customer.id", customerInfo
										.getCustomer().getId().toString(),
										CompareType.EQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("saleMan.id", user.getId()
										.toString(), CompareType.EQUALS));

						FullOrgUnitInfo orgUnit = SysContext.getSysContext()
								.getCurrentOrgUnit().castToFullOrgUnitInfo();

						filter.getFilterItems()
								.add(
										new FilterItemInfo("orgUnit.id",
												orgUnit.getId().toString(),
												CompareType.EQUALS));

						
						
						//删除共享中的当前操作人名下的商机
						commerceChanceDelete();
						
						// 判断此客户是否已经有商机,能否合并
						boolean isJoin = CommerceChanceFactory
								.getRemoteInstance().exists(filter);

						if (isJoin) {
							commerchanceJoinAndActive(customerInfo, user,
									orgUnit);
						} else {
							commerceChanceJoin(customerInfo, user, orgUnit);
						}
					

					} else {
						FDCMsgBox.showWarning(this, "商机转交失败!");
						this.abort();
					}
				}
			} else {
				if (isDuty(user)) {
					FDCMsgBox.showWarning(this, "不能转交给团队负责人，请重新选择!");
					this.abort();
				} else {
					if (loginUser.getId().toString().equals(
							user.getId().toString())) {
						FDCMsgBox.showWarning(this, "不能转交给自己，请重新选择!");
						this.abort();
					}
					// 转交代码
					if (!"".equals(this.id)) {
						try {
							customerInfo = CommerceChanceFactory
									.getRemoteInstance().getCommerceChanceInfo(
											"select id,customer.id,customer.name,customer.number where id='"
													+ this.id + "'");
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}

						if (customerInfo != null
								&& customerInfo.getCustomer() != null) {
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(
									new FilterItemInfo("customer.id",
											customerInfo.getCustomer().getId()
													.toString(),
											CompareType.EQUALS));
							filter.getFilterItems().add(
									new FilterItemInfo("saleMan.id", user
											.getId().toString(),
											CompareType.EQUALS));

							FullOrgUnitInfo orgUnit = SysContext
									.getSysContext().getCurrentOrgUnit()
									.castToFullOrgUnitInfo();

							filter.getFilterItems().add(
									new FilterItemInfo("orgUnit.id", orgUnit
											.getId().toString(),
											CompareType.EQUALS));

							boolean isJoin = CommerceChanceFactory
									.getRemoteInstance().exists(filter);

							if (isJoin) {
								commerchanceJoinAndActive(customerInfo, user,
										orgUnit);
							} else {
								commerceChanceJoin(customerInfo, user, orgUnit);
							}
							
							//删除共享中的当前操作人名下的商机
							commerceChanceDelete();

						} else {
							FDCMsgBox.showWarning(this, "商机转交失败!");
							this.abort();
						}

					} else {
						FDCMsgBox.showWarning(this, "商机转交失败!");
						this.abort();
					}
				}
			}

		} else {
			FDCMsgBox.showWarning(this, "此置业顾问不在营销团队中，请重新选择!");
			this.abort();
		}
	}

	private void commerceChanceDelete() {
		
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		
		try {
			String sql = "delete T_SHE_ShareSaleMan where fheadid='"+this.id+"' and FMemberID='"+user.getId().toString()+"'";
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder.appendSql(sql);
			sqlBuilder.execute();
		} catch (BOSException ex) {
			logger.error(ex.getMessage() + "删除共享商机失败!");
		}
		
	}

	private void commerceChanceJoin(CommerceChanceInfo customerInfo,
			UserInfo user, FullOrgUnitInfo orgUnit) throws BOSException,
			EASBizException {

		CommerceChanceInfo commerceInfo = CommerceChanceFactory
				.getRemoteInstance().getCommerceChanceInfo(
						"select id,effectiveDate,status where id='"
								+ customerInfo.getId().toString() + "'");
		if (commerceInfo != null) {
			try {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("saleMan.id"));
				selector.add(new SelectorItemInfo("status"));
				if (commerceInfo.getStatus().equals(
						CommerceChangeNewStatusEnum.CLOSE)) {
					selector.add(new SelectorItemInfo("effectiveDate"));
				}
				CommerceChanceInfo model = new CommerceChanceInfo();
				model.setId(BOSUuid.read(this.id));
				model.setStatus(CommerceChangeNewStatusEnum.ACTIVE);
				model.setSaleMan(user);
				if (commerceInfo.getStatus().equals(
						CommerceChangeNewStatusEnum.CLOSE)) {
					if (commerceInfo.getEffectiveDate() != null) {
						int number = 0;
						number = getValidateDate();
						model.setEffectiveDate(FDCDateHelper.addDays(
								commerceInfo.getEffectiveDate(), number));
					}
				}
				CommerceChanceFactory.getRemoteInstance().updatePartial(model,
						selector);
				FDCMsgBox.showWarning(this, "商机转交成功!");
				this.uiWindow.close();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				FDCMsgBox.showWarning(this, "商机转交失败!");
				this.abort();
			}
		}
	}

	private void commerchanceJoinAndActive(CommerceChanceInfo customerInfo,
			UserInfo user, FullOrgUnitInfo orgUnit) throws BOSException,
			EASBizException {
		CommerceChanceInfo commerceInfo = CommerceChanceFactory
				.getRemoteInstance().getCommerceChanceInfo(
						"select id,effectiveDate,status where customer.id='"
								+ customerInfo.getCustomer().getId().toString()
								+ "' and saleMan.id='"
								+ user.getId().toString()
								+ "' and orgUnit.id='"
								+ orgUnit.getId().toString() + "'");
		if (commerceInfo != null) {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql
					.appendSql("update T_SHE_CommerceChanceTrack set FCommerceChanceID='"
							+ commerceInfo.getId().toString()
							+ "' where fid='"
							+ this.id + "'");
			sql.execute();
		}
		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("saleMan.id"));
			selector.add(new SelectorItemInfo("status"));
			if (commerceInfo.getStatus().equals(
					CommerceChangeNewStatusEnum.CLOSE)) {
				selector.add(new SelectorItemInfo("effectiveDate"));
			}

			CommerceChanceInfo model = new CommerceChanceInfo();
			model.setId(BOSUuid.read(this.id));
			model.setStatus(CommerceChangeNewStatusEnum.ACTIVE);
			model.setSaleMan(user);
			if (commerceInfo.getStatus().equals(
					CommerceChangeNewStatusEnum.CLOSE)) {
				if (commerceInfo.getEffectiveDate() != null) {
					int number = 0;
					number = getValidateDate();
					model.setEffectiveDate(FDCDateHelper.addDays(commerceInfo
							.getEffectiveDate(), number));
				}
			}
			CommerceChanceFactory.getRemoteInstance().updatePartial(model,
					selector);
			FDCMsgBox.showWarning(this, "商机转交成功!");
			this.uiWindow.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			FDCMsgBox.showWarning(this, "商机转交失败!");
			this.abort();
		}
	}

	private int getValidateDate() {
		int value = 0;
		RoomDisplaySetting set = new RoomDisplaySetting(null,
				SHEParamConstant.PROJECT_SET_MAP);
		SellProjectInfo sellProject = null;
		if (this.getUIContext().get("CommerceChanceProject") != null) {
			sellProject = (SellProjectInfo) this.getUIContext().get(
					"CommerceChanceProject");
		}
		if (sellProject != null) {
			Map detailSet = RoomDisplaySetting.getNewProjectSet(null,sellProject.getId()
					.toString());
			if (detailSet != null) {
				value = ((Integer)detailSet
						.get(SHEParamConstant.T2_COMMERCECHANCE_DAYS)).intValue();
			}
		}
		return value;
	}

	/**
	 * output btnQuit_actionPerformed method
	 */
	protected void btnQuit_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnQuit_actionPerformed(e);
		this.uiWindow.close();
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

		getValidateDate();
		initTransfor();
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