package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.EventListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.fi.rpt.client.ParamInductor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author Cassiel_peng
 * @see com.kingdee.eas.fdc.finance.client.FDCAcctFormulaWizard
 * @work 
 *       长城提单：目前系统中fdc_acct_actual_pay、fdc_acct_aimcost、fdc_acct_conCost_split函数均通过
 *       “月度”字段进行期间选择，只能获取某一月份的数据，而不能获取年度数据和累计数据， 因此，需要通过“开始期间”和“结束期间”进行过滤来取数。
 *       根据需求需要改造FDCAcctFormulaWizard，但因为该类有许多其他函数引用，不便维护，故再封装一个类来处理该提单需求。
 *       2009-10-10
 */
public class FDCAcctFormulaWizardForR090921_057 extends FormulaWizardUI {
	private static final Logger logger = Logger
			.getLogger(FDCFormulaWizard.class);

	private KDBizPromptBox fdcCompany;

	private KDBizPromptBox fdcProject;
	// 合同F7
	private KDBizPromptBox fdcContractF7;
	// 成本科目
	private KDBizPromptBox costAccountF7;
	// 开始期间
	private KDDatePicker beginPeriod;
	// 结束期间
	private KDDatePicker endPeriod;
	// 显示全部组织
	private KDComboBox displayAllCompany;

	public FDCAcctFormulaWizardForR090921_057() throws Exception {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 显示取数界面
	 */
	protected void onOpen() {
		fdcCompany = (KDBizPromptBox) this.getParamComponent("fdcCompany");

		// 改变显示格式
		final IFormatter formCompany = fdcCompany.getDisplayFormatter();
		IFormatter formatCompany = new IFormatter() {
			public void applyPattern(String pattern) {
				formCompany.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formCompany.valueToString(o) != null) {
					return formCompany.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};
		fdcCompany.setDisplayFormatter(formatCompany);
		fdcCompany.setEnabledMultiSelection(false);

		displayAllCompany = (KDComboBox) this
				.getParamComponent("displayAllCompany");

		fdcProject = (KDBizPromptBox) this.getParamComponent("fdcProject");

		// 改变显示格式
		final IFormatter formProject = fdcProject.getDisplayFormatter();
		IFormatter formatProject = new IFormatter() {
			public void applyPattern(String pattern) {
				formProject.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formProject.valueToString(o) != null) {
					return formProject.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};

		fdcProject.setDisplayFormatter(formatProject);
		fdcProject.setEnabledMultiSelection(false);

		if (paramComponents.containsKey("longNumber")) {
			costAccountF7 = (KDBizPromptBox) this
					.getParamComponent("longNumber");
			costAccountF7.setEnabledMultiSelection(false);
		}
		// 改变显示格式
		final IFormatter formatCostAccountF7 = costAccountF7
				.getDisplayFormatter();
		IFormatter formatCostAccount = new IFormatter() {
			public void applyPattern(String pattern) {
				formatCostAccountF7.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formatCostAccountF7.valueToString(o) != null) {
					return formatCostAccountF7.valueToString(o).replaceAll("!",
							"\\.");
				}
				return null;
			}
		};

		if (paramComponents.containsKey("beginPeriod")) {
			beginPeriod = (KDDatePicker) this.getParamComponent("beginPeriod");
		}
		if (paramComponents.containsKey("endPeriod")) {
			endPeriod = (KDDatePicker) this.getParamComponent("endPeriod");
		}

		// 当"显示所有组织"为否时，必须要公司选出值后，项目才能选.
		// 当"显示所有组织"为是时，则支持选择其他公司项目，并更新公司参数 by Cassiel_peng 2009-10-10
		fdcCompany.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getNewValue() == null||!eventObj.getNewValue().equals(eventObj.getOldValue())) {// 没选值或者是此次选择的值跟上次的不一样，则需要把工程项目置空
					fdcProject.setValue(null);
					fdcProject.setText(null);
					try {
						((KDTextField)((ParamInductor)paramComponents.get("fdcProject")).getBaseComponent()).setText("");
					} catch (Exception e) {
						
					}
					costAccountF7.setText(null);
					costAccountF7.setValue(null);
					try {
						((KDTextField)((ParamInductor)paramComponents.get("longNumber")).getBaseComponent()).setText("");
					} catch (Exception e) {
						
					}
					fdcProject.requestFocus();
					costAccountF7.requestFocus();
					costAccountF7.updateData();
					
					if(fdcContractF7!=null){
						fdcContractF7.setValue(null);
						fdcContractF7.setText(null);
						fdcContractF7.requestFocus();
						try {
							((KDTextField)((ParamInductor)paramComponents.get("fdcConAndNoTextConF7")).getBaseComponent()).setText("");
						} catch (Exception e) {
							
						}
					}
					
					fdcCompany.requestFocus();
				}
				if (displayAllCompany.getSelectedIndex() == 0) {// 否
						if (!(fdcCompany.getValue() instanceof String)) {
							try {
								if (!(fdcCompany.getValue() instanceof String&&fdcCompany.getValue() instanceof CompanyOrgUnitInfo)) {
									setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
								} else if(!(fdcCompany.getValue() instanceof String&&fdcCompany.getValue() instanceof FullOrgUnitInfo)){
									setFDCProject((String) fdcCompany.getValue());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							setFDCProject((String) fdcCompany.getValue());
						}
					}else{
						setFDCProject(null);
					}
				}
		});

		displayAllCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (displayAllCompany.getSelectedIndex() == 1) {// 显示全部组织
					setFDCProject(null);
				} else {
					if (fdcCompany.getValue() != null
							&& !"".equals(fdcCompany.getValue())) {
						try {if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof CompanyOrgUnitInfo) {
							setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
						} else if (!(fdcCompany.getValue() instanceof String && fdcCompany.getValue() instanceof FullOrgUnitInfo)) {
							setFDCProject(((FullOrgUnitInfo) fdcCompany	.getValue()).getId().toString());
						}else if(fdcCompany.getValue() instanceof String){
							setFDCProject((String) fdcCompany.getValue());
						}
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					} 
				}
			}

		});

		if (fdcProject != null) {
			fdcProject.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if(displayAllCompany.getSelectedIndex()==0){//不显示所有组织
						if (fdcCompany.getValue() != null&& !"".equals(fdcCompany.getValue())) {
							if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof CompanyOrgUnitInfo) {
								setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
							} else if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof FullOrgUnitInfo) {
								setFDCProject(((FullOrgUnitInfo) fdcCompany	.getValue()).getId().toString());
							}else if(fdcCompany.getValue() instanceof String){
								setFDCProject((String) fdcCompany.getValue());
							}
						} else {
								FDCMsgBox.showWarning(FDCAcctFormulaWizardForR090921_057.this,"请先选择公司");
								SysUtil.abort();
							}
						}else{//显示所有组织
						
					}
						
					}

			});
			fdcProject.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					EventListener[] listeners = fdcCompany.getListeners(DataChangeListener.class);
					for(int i=0;i<listeners.length;i++){
						fdcCompany.removeDataChangeListener((DataChangeListener)listeners[i]);
					}
					if (fdcProject.getValue() != null
							&& !"".equals(fdcProject.getValue())) {
						CurProjectInfo curProject = (CurProjectInfo) fdcProject
								.getValue();
						if (curProject.getId() != null) {
							String prjId = curProject.getId().toString();
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add("fullOrgUnit.name");
							selector.add("fullOrgUnit.longNumber");
							selector.add("startDate");
							try {
								CurProjectInfo curProjectBill = CurProjectFactory
										.getRemoteInstance()
										.getCurProjectInfo(
												new ObjectUuidPK(BOSUuid
														.read(prjId)), selector);
								fdcCompany.setValue(curProjectBill
										.getFullOrgUnit().getLongNumber());
								fdcCompany.requestFocus();// 当选取了工程项目反填公司值的时候公司的值不会即时更新
															// ，如此处理便可
								beginPeriod.setValue(curProjectBill
										.getStartDate());
								beginPeriod.requestFocus();
								fdcProject.requestFocus();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					for(int i=0;i<listeners.length;i++){
						fdcCompany.addDataChangeListener((DataChangeListener)listeners[i]);
					}
				}
			});
		}
		if (costAccountF7 != null) {
			costAccountF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					try {
						if (fdcProject.getValue() != null
								&& !"".equals(fdcProject.getValue())) {
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCCostAccountFilter(((CurProjectInfo) fdcProject
										.getValue()).getId().toString());
							} else {
								setFDCCostAccountFilter((String) fdcProject
										.getValue());
							}
						} else {
							FDCMsgBox.showWarning(
									FDCAcctFormulaWizardForR090921_057.this,
									"请先选择工程项目");
							SysUtil.abort();
						}
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						logger.error(e1);
					}
				}

			});
		}

		// 合同
		if (paramComponents.containsKey("fdcConAndNoTextConF7")) {
			fdcContractF7 = (KDBizPromptBox) this
					.getParamComponent("fdcConAndNoTextConF7");
			fdcContractF7.setEnabledMultiSelection(false);
		}
		if (fdcContractF7 != null) {
			fdcContractF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					try {
						if (fdcProject.getValue() != null
								&& !"".equals(fdcProject.getValue())) {
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCContractFilter(((CurProjectInfo) fdcProject
										.getValue()).getId().toString());
							} else {
								setFDCContractFilter((String) fdcProject
										.getValue());
							}
						} else {
							FDCMsgBox.showWarning(
									FDCAcctFormulaWizardForR090921_057.this,
									"请先选择工程项目");
							SysUtil.abort();
						}
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						logger.error(e1);
					}
				}

			});
		}
	}

	/**
	 * 根据组织过滤出工程项目
	 */
	private void setFDCProject(String companyId) {
		fdcProject.getQueryAgent().resetRuntimeEntityView();
		if (companyId == null) {
			fdcProject.setValue(null);	
			/** 添加按照当前财务组织对工程项目进行过滤，只能取当前组织下对应的工程项目(begin) -by neo*/
			EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo filter = new FilterInfo();
//	    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",
//	    			SysContext.getSysContext().getCurrentFIUnit().getId()));
	    	// 必须选取明细工程项目
	    	filter.getFilterItems().add(
	    			new FilterItemInfo("isLeaf", Boolean.TRUE));
			view.setFilter(filter);
			fdcProject.setEntityViewInfo(view);
			/**添加按照当前财务组织对工程项目进行过滤，只能取当前组织下对应的工程项目(end) -by neo*/
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", companyId));
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.longNumber", companyId));
			// 必须选取明细工程项目
			filter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			filter.setMaskString("( #0 or #1) and #2");
			view.setFilter(filter);
			fdcProject.getQueryAgent().setEntityViewInfo(view);
		}
	}

	/**
	 * 根据工程项目过滤成本科目
	 */
	private void setFDCCostAccountFilter(String id) throws BOSException,
			EASBizException {
		costAccountF7.getQueryAgent().resetRuntimeEntityView();
		if (id == null) {
			costAccountF7.setValue(null);
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("curProject.id", id));
			filter.getFilterItems()
			.add(new FilterItemInfo("curProject.longNumber", id));
			// filter.getFilterItems().add(new
			// FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.setMaskString(" #0 or #1 ");
			view.setFilter(filter);
			costAccountF7.getQueryAgent().setEntityViewInfo(view);
		}
	}

	/**
	 * 根据工程项目过滤合同
	 */
	private void setFDCContractFilter(String idOrLongNubmber) throws BOSException,
			EASBizException {
		fdcContractF7.getQueryAgent().resetRuntimeEntityView();
		if (idOrLongNubmber == null) {
			fdcContractF7.setValue(null);
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("curProject.id", idOrLongNubmber));
			//如果此时传递过来的已经不是工程项目的ID而是长编码的话
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FID from T_FDC_CurProject where FLongNumber = ? ");
			builder.addParam(idOrLongNubmber);
			IRowSet rowSet = builder.executeQuery();
			try {
				if(rowSet.next()){
					idOrLongNubmber = rowSet.getString("FID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			filter.getFilterItems()
			.add(new FilterItemInfo("curProject.id", idOrLongNubmber));
			filter.setMaskString(" #0 or #1 ");
			// filter.getFilterItems().add(new
			// FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			view.setFilter(filter);
			fdcContractF7.getQueryAgent().setEntityViewInfo(view);
		}
	}
}
