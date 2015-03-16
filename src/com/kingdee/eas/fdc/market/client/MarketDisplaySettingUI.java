/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.framework.util.EntityControlTypeUtil;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CSStringUtils;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroupStandard;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetFactory;
import com.kingdee.eas.fdc.market.MarketDisplaySetInfo;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketDisplaySettingUI extends AbstractMarketDisplaySettingUI
{
	MarketDisplaySetting setting = new MarketDisplaySetting();
	
	private String checkedRadioButton = this.kDRadioSingle.getName();
	
    private static final Logger logger = CoreUIObject.getLogger(MarketDisplaySettingUI.class);
    
    private static final Integer defaultType = new Integer("0");
    
    private static final Integer typeForCustomer = new Integer("1");
    
    protected CtrlUnitInfo curCtrlUnitInfo = SysContext.getSysContext().getCurrentCtrlUnit();
    
//  客商分类和标准
	protected CSSPGroupStandardCollection customerGroupStandards = null;
    
//	 表中必录项颜色
	private static final Color necessaryColor = new Color(0xFC, 0xFF, 0xCE);
	
    public MarketDisplaySettingUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void loadFields() {
    	super.loadFields();
    	
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			MsgBox.showInfo("非集团用户不能修改功能设置!");
			this.abort();
		}
    	setupKDTable();
    	loadGroupData();
    	fillCSGroupTable();
    	this.kDRadioMulti.setVisible(false);
    	this.kDRadioSingle.setVisible(false);
    	String [] repeatItems = new String[]{"强制不允许保存","允许保存,给予提示","允许保存,不提示"};
    	
//    	this.comboNameRepeat.addItem("强制不允许保存");
//		this.comboNameRepeat.addItem("允许保存,给予提示");
//		this.comboNameRepeat.addItem("允许保存,不提示");
		
//		this.comboPhoneRepeat.addItem("强制不允许保存");
//		this.comboPhoneRepeat.addItem("允许保存,给予提示");
//		this.comboPhoneRepeat.addItem("允许保存,不提示");
	
    	this.comboNameRepeat.addItems(repeatItems);
		
		this.comboPhoneRepeat.addItems(repeatItems);
		
		this.comboNameAndPhoneRepeat.addItems(repeatItems);
		
		this.kDComboBox1.addItem("0");
		this.kDComboBox1.addItem("1");
		this.kDComboBox1.addItem("2");
		this.kDComboBox1.addItem("3");
		this.kDComboBox1.addItem("4");
		this.kDComboBox1.addItem("5");
		this.kDComboBox1.addItem("6");
		this.kDComboBox1.addItem("7");
		this.kDComboBox1.addItem("8");
		this.kDComboBox1.addItem("9");
		this.kDComboBox1.addItem("10");
		this.kDComboBox1.addItem("11");
		this.kDComboBox1.addItem("12");
		this.kDComboBox1.addItem("13");
		this.kDComboBox1.addItem("14");
		this.kDComboBox1.addItem("15");
		
		this.kDComboBox2.addItem("0");
		this.kDComboBox2.addItem("1");
		this.kDComboBox2.addItem("2");
		this.kDComboBox2.addItem("3");
		this.kDComboBox2.addItem("4");
		this.kDComboBox2.addItem("5");
		this.kDComboBox2.addItem("6");
		this.kDComboBox2.addItem("7");
		this.kDComboBox2.addItem("8");
		this.kDComboBox2.addItem("9");
		this.kDComboBox2.addItem("10");
		this.kDComboBox2.addItem("11");
		this.kDComboBox2.addItem("12");
		this.kDComboBox2.addItem("13");
		this.kDComboBox2.addItem("14");
		this.kDComboBox2.addItem("15");
		
	
		
		this.comboNameRepeat.setSelectedIndex(setting.getNameRepeat());
		this.comboPhoneRepeat.setSelectedIndex(setting.getPhoneRepeat());
		this.chkMarkInvoice.setSelected(setting.getMarkInvoice());
		this.kDComboBox1.setSelectedIndex(setting.getMobileNumber());
		this.kDComboBox2.setSelectedIndex(setting.getPhoneNumber());
		this.comboNameAndPhoneRepeat.setSelectedIndex(setting.getNameAndPhoneRepeat());
		this.gxcheck.setSelected(setting.getGxcheck());		
		this.kDRadioSingle.setSelected(true);
//		if(setting.getCheckedRadioButton() != null){
//			if(this.kDRadioSingle.getName().equals(setting.getCheckedRadioButton())){
//				this.kDRadioSingle.setSelected(true);
//			}else if(this.kDRadioMulti.getName().equals(setting.getCheckedRadioButton())){
//				this.kDRadioMulti.setSelected(true);
//			}
//		}
//		chkSingleOrMulti();
//		this.kDRadioSingle.addChangeListener(new ChangeListener(){
//
//			public void stateChanged(ChangeEvent e) {
//				// TODO Auto-generated method stub
//				chkSingleOrMulti();
//			}});
    }
    
    private void chkSingleOrMulti(){
    	if(kDRadioSingle.isSelected()){
    		this.conMulti.setEnabled(false);
    		this.contNameRepeat.setEnabled(true);
    		this.contPhoneRepeat.setEnabled(true);
    		this.checkedRadioButton = this.kDRadioSingle.getName();
    	}else {
    		this.contNameRepeat.setEnabled(false);
    		this.contPhoneRepeat.setEnabled(false);
    		this.conMulti.setEnabled(true);
    		this.checkedRadioButton = this.kDRadioMulti.getName();
    	}
    }
    
    /**
	 * 
	 * 描述：设置KDTable监听事件
	 * 
	 * @author:dongpeng 创建时间：2006-6-12
	 *                  <p>
	 */
	private void setupKDTable() {
		this.tblToSysCustomer.checkParsed();
		for (int i = 0, cont = tblToSysCustomer.getRowCount(); i < cont; i++) {
			this.tblToSysCustomer.getRow(i).getCell(0).getStyleAttributes()
					.setLocked(true);
		}
		this.tblToSysCustomer.getColumn(0).getStyleAttributes().setLocked(true);
		this.tblToSysCustomer
				.addKDTActiveCellListener(new CSGroupTableActiveCellHandler());
		this.tblToSysCustomer.getLayoutManager().setVerticalScrollBar(
				new JScrollBar(JScrollBar.VERTICAL));
	}
	
	/**
	 * 
	 * 描述:KDTable事件监听
	 * 
	 * @author dongpeng date:2006-6-12
	 *         <p>
	 * @version EAS5.0
	 */
	class CSGroupTableActiveCellHandler implements KDTActiveCellListener {

		public void activeCellChanged(KDTActiveCellEvent e) {
			if (e.getColumnIndex() == 1) {
				if (e.getRowIndex() > -1) {
					_activeCellChanged(e.getRowIndex());
				}
			}
			// end of if
		}
		// end of public
	}
    
    /*
	 * 装载分类数据
	 * 
	 * @see 创建时间：2006-06-27
	 */
	protected void loadGroupData() {
		try {
			// 得到所有分类标准
			ICSSPGroupStandard iCustomerGroupStandard = CSSPGroupStandardFactory
					.getRemoteInstanceWithObjectContext(getMainOrgContext());
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(EntityControlTypeUtil.getFilterInfoForControlTypeS4(
					curCtrlUnitInfo.getId()
							.toString(),curCtrlUnitInfo.getLongNumber()));
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems()
					.add(
							new FilterItemInfo("type", defaultType,
									CompareType.EQUALS));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("type", typeForCustomer,
							CompareType.EQUALS));
			filterInfo.setMaskString("#0 or #1");
			evi.getFilter().mergeFilter(filterInfo, "and");
			SorterItemInfo sorterByBasic = new SorterItemInfo("isBasic");
			sorterByBasic.setSortType(SortType.DESCEND);
			evi.getSorter().add(sorterByBasic);
			this.customerGroupStandards = iCustomerGroupStandard
					.getCSSPGroupStandardCollection(evi);
			tblToSysCustomer.checkParsed();
			if (customerGroupStandards.size() == 0) {
				tblToSysCustomer.addRow();
			}
			// System.out.println("客户分类数" + customerGroups.size());
		} catch (Exception e) {
			// 无法加载客商分类信息!
			ExceptionHandler.handle(this, EASResource.getString(
					CSStringUtils.CSSP_RESOURCE,
					CSStringUtils.LOAD_CSGROUP_INFO_FAILED)
					+ e.getMessage(), e);
		}
	}
	
	/*
	 * 装载客户供应商分类信息
	 * 
	 * @see 创建时间：2006-06-27
	 */
	protected void fillCSGroupTable() {
		loadCustomerGroupTable(customerGroupStandards);
	}
	
	/**
	 * 客户分类的装载 描述：
	 * 
	 * @param customerGrps
	 * @param customerSales
	 * @author:dongpeng
	 * @see 创建时间：2006-06-27
	 *      <p>
	 */
	protected void loadCustomerGroupTable(
			CSSPGroupStandardCollection standards) {
		// 缓存上次选择的分类
		//CSSPGroupInfo[] tempSaveGroup = null;
		//CSSPGroupStandardInfo[] tempSaveGroupStandard = null;
		//String[] tempSaveGroupStandardFullName = null;
		tblToSysCustomer.removeRows(); // 清空列表
		CSSPGroupStandardInfo stdVO = null;
		Map groupMap = setting.getSortStandardMap();
		CSSPGroupInfo grpVO = null;
		String op ="ADDNEW";
		if (standards != null) {
			for (int i = 0, c = standards.size(); i < c; i++) {
				stdVO = standards.get(i);
				grpVO = getCustomerGroupByStandard(stdVO,groupMap);
				IRow row = tblToSysCustomer.addRow();
//				if (tempSaveGroupStandard[i] != null
//						&& tempSaveGroupStandard[i].equalsPK(stdVO)) {
//					row.getCell(0).setValue(tempSaveGroupStandard[i]);
//					// 新增、编辑时，缓存才起作用
//					if (tempSaveGroup[i] != null
//							&& (op.equals(OprtState.EDIT) || op
//									.equals(OprtState.ADDNEW))|| op
//									.equals(OprtState.ADDNEW))) {
//						row.getCell(1).setValue(tempSaveGroup[i]);
//						row.getCell(2).setValue(
//								tempSaveGroupStandardFullName[i]);
//					} else {
//						row.getCell(1).setValue(null);
//						row.getCell(2).setValue(null);
//					}
//				} else {
					row.getCell(0).setValue(stdVO);
					row.getCell(1).setValue(grpVO);
//				}
				if (stdVO.getIsBasic().equals(StandardTypeEnum.basicStandard)
						) {
					if (op.equals(OprtState.EDIT)
							|| op.equals(OprtState.ADDNEW)) {
//						if (!stdVO.getIsBasic().equals(
//								StandardTypeEnum.creditStandard)) {// 必录项中排除信用分类标准
							row.getCell(1).getStyleAttributes().setBackground(
									necessaryColor);
//						}
					}
				}
				if (  !stdVO.getCU().getId().equals(
								curCtrlUnitInfo
										.getId())) {
					if (op.equals(OprtState.EDIT)
							|| op.equals(OprtState.ADDNEW)) {
						if (!stdVO.getIsBasic().equals(
								StandardTypeEnum.creditStandard)) {// 必录项中排除信用分类标准
							row.getCell(1).getStyleAttributes().setBackground(
									necessaryColor);
						}
					}
				}
			}
		}		
	}
	
	/**
	 * 根据客户分类标准在客户销售信息的分类集合中查找对应的客户分类 描述：
	 * 
	 * @author:dongpeng
	 * @see 创建时间：2006-06-27
	 */
	protected CSSPGroupInfo getCustomerGroupByStandard(
			CSSPGroupStandardInfo standard, Map groupMap) {
		CSSPGroupInfo cssInfo = null;
		if (groupMap != null) {
			if(groupMap.get(standard.getId().toString())!=null)
			{
				cssInfo = (CSSPGroupInfo)groupMap.get(standard.getId().toString());
			}
	}
		return cssInfo;
	}

	/**
	 * 
	 * 描述：KDTable单元格激活事件处理
	 * 
	 * @param rowIndex
	 * @author:dongpeng 创建时间：2006-6-12
	 *                  <p>
	 */
	public void _activeCellChanged(int rowIndex) {
		final int argRowIndex = rowIndex;
		KDBizPromptBox promptBox = new KDBizPromptBox();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		promptBox
				.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerGroupQuery");

		IRow rowCustomer = tblToSysCustomer.getRow(argRowIndex);

		if (!(rowCustomer.getCell(0).getValue() instanceof CSSPGroupStandardInfo)) {
			return;
		}
		CSSPGroupStandardInfo std = (CSSPGroupStandardInfo) rowCustomer
				.getCell(0).getValue();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("groupStandard.id", std.getId().toString(),
						CompareType.EQUALS));

		view.setFilter(filterInfo);
		promptBox.setEntityViewInfo(view);
		promptBox.setEditable(true);
		promptBox.setDisplayFormat("$name$");
		promptBox.setEditFormat("$name$");
		promptBox.setCommitFormat("$number$");
		tblToSysCustomer.getColumn(1).setEditor(
				new KDTDefaultCellEditor(promptBox));
	}

    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnYes_actionPerformed(e);
        setting.setNameRepeat(this.comboNameRepeat.getSelectedIndex());
        setting.setPhoneRepeat(this.comboPhoneRepeat.getSelectedIndex());
        setting.setMarkInvoice(this.chkMarkInvoice.getSelected());
        setting.setGxcheck(this.gxcheck.getSelected());
        setting.setMobileNumber(this.kDComboBox1.getSelectedIndex());
        setting.setPhoneNumber(this.kDComboBox2.getSelectedIndex());
        setting.setNameAndPhoneRepeat(this.comboNameAndPhoneRepeat.getSelectedIndex());
        setting.setCheckedRadioButton(checkedRadioButton);
        int rows = this.tblToSysCustomer.getRowCount();
        Map groupMap = new HashMap();
        if(rows>0)
        {
        	for(int i=0;i<rows;i++)
              {
            	IRow row = this.tblToSysCustomer.getRow(i);
            	CSSPGroupStandardInfo cssInfo = (CSSPGroupStandardInfo)row.getCell(0).getValue();
            	CSSPGroupInfo cssGroupInfo = (CSSPGroupInfo)row.getCell(1).getValue();
            	if(cssGroupInfo!=null)
            	{
            		groupMap.put(cssInfo.getId().toString(),cssGroupInfo);
            	}else
            	{
            		MsgBox.showInfo("第"+(i+1)+"行"+cssInfo+"的分类不能为空!");
            		return;
            	}       		
               }   	
        }
        setting.setSortStandardMap(groupMap);
        setting.save();
        FDCMsgBox.showInfo("保存成功！");
        this.destroyWindow();
    }

    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnNo_actionPerformed(e);
        this.destroyWindow();
    }
}