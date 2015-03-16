/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractMarketSupplierStockReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierStockReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combWQHT;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combWJS;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combYJS;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combIsAllContract;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combIsAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox3;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDComboBox1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox Visibility;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtGrade;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combIsPass;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierStockReportFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierStockReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combWQHT = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combWJS = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combYJS = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combIsAllContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDCheckBox1 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox2 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox3 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox4 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDCheckBox5 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDBizPromptBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDBizPromptBox2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDBizPromptBox3 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDComboBox1 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.Visibility = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.combIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.combWQHT.setName("combWQHT");
        this.combWJS.setName("combWJS");
        this.combYJS.setName("combYJS");
        this.combIsAllContract.setName("combIsAllContract");
        this.combIsAll.setName("combIsAll");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.txtName.setName("txtName");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDCheckBox1.setName("kDCheckBox1");
        this.kDCheckBox2.setName("kDCheckBox2");
        this.kDCheckBox3.setName("kDCheckBox3");
        this.kDCheckBox4.setName("kDCheckBox4");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDCheckBox5.setName("kDCheckBox5");
        this.kDBizPromptBox1.setName("kDBizPromptBox1");
        this.kDBizPromptBox2.setName("kDBizPromptBox2");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.kDDatePicker2.setName("kDDatePicker2");
        this.kDBizPromptBox3.setName("kDBizPromptBox3");
        this.kDComboBox1.setName("kDComboBox1");
        this.Visibility.setName("Visibility");
        this.prmtGrade.setName("prmtGrade");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.prmtCurProject.setName("prmtCurProject");
        this.combIsPass.setName("combIsPass");
        // CustomerQueryPanel
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // combWQHT		
        this.combWQHT.setText(resHelper.getString("combWQHT.text"));
        // combWJS		
        this.combWJS.setText(resHelper.getString("combWJS.text"));
        // combYJS		
        this.combYJS.setText(resHelper.getString("combYJS.text"));
        // combIsAllContract		
        this.combIsAllContract.setText(resHelper.getString("combIsAllContract.text"));
        // combIsAll		
        this.combIsAll.setText(resHelper.getString("combIsAll.text"));
        this.combIsAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combIsAll_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setText(resHelper.getString("txtName.text"));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDCheckBox1		
        this.kDCheckBox1.setText(resHelper.getString("kDCheckBox1.text"));
        // kDCheckBox2		
        this.kDCheckBox2.setText(resHelper.getString("kDCheckBox2.text"));
        // kDCheckBox3		
        this.kDCheckBox3.setText(resHelper.getString("kDCheckBox3.text"));
        // kDCheckBox4		
        this.kDCheckBox4.setText(resHelper.getString("kDCheckBox4.text"));
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDCheckBox5		
        this.kDCheckBox5.setText(resHelper.getString("kDCheckBox5.text"));
        this.kDCheckBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combIsAll_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDBizPromptBox1		
        this.kDBizPromptBox1.setDisplayFormat("$name$");		
        this.kDBizPromptBox1.setEditFormat("$number$");		
        this.kDBizPromptBox1.setCommitFormat("$number$");		
        this.kDBizPromptBox1.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7LevelSetUpQuery");		
        this.kDBizPromptBox1.setEnabledMultiSelection(true);
        // kDBizPromptBox2		
        this.kDBizPromptBox2.setDisplayFormat("$name$");		
        this.kDBizPromptBox2.setEditFormat("$number$");		
        this.kDBizPromptBox2.setCommitFormat("$number$");		
        this.kDBizPromptBox2.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.kDBizPromptBox2.setEnabledMultiSelection(true);
        // kDDatePicker1
        // kDDatePicker2
        // kDBizPromptBox3		
        this.kDBizPromptBox3.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.kDBizPromptBox3.setCommitFormat("$number$");		
        this.kDBizPromptBox3.setEnabledMultiSelection(true);		
        this.kDBizPromptBox3.setEditFormat("$number$");		
        this.kDBizPromptBox3.setDisplayFormat("$name$");
        // kDComboBox1		
        this.kDComboBox1.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
        // Visibility		
        this.Visibility.setDisplayFormat("$name$");		
        this.Visibility.setEditFormat("$number$");		
        this.Visibility.setCommitFormat("$number$");		
        this.Visibility.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.VisibilityQuery");		
        this.Visibility.setEnabledMultiSelection(true);
        // prmtGrade		
        this.prmtGrade.setDisplayFormat("$name$");		
        this.prmtGrade.setEditFormat("$number$");		
        this.prmtGrade.setCommitFormat("$number$");		
        this.prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.prmtGrade.setEnabledMultiSelection(true);
        // pkFromDate
        // pkToDate
        // prmtCurProject		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setCommitFormat("$number$");		
        this.prmtCurProject.setEnabledMultiSelection(true);		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setDisplayFormat("$name$");
        // combIsPass		
        this.combIsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 620, 180));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(22, 17, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer9.setBounds(new Rectangle(329, 17, 270, 19));
        this.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(22, 43, 270, 19));
        this.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(22, 69, 270, 19));
        this.add(kDLabelContainer11, null);
        kDLabelContainer12.setBounds(new Rectangle(329, 69, 270, 19));
        this.add(kDLabelContainer12, null);
        kDLabelContainer13.setBounds(new Rectangle(329, 43, 270, 19));
        this.add(kDLabelContainer13, null);
        kDLabelContainer14.setBounds(new Rectangle(22, 93, 100, 19));
        this.add(kDLabelContainer14, null);
        combWQHT.setBounds(new Rectangle(123, 93, 82, 19));
        this.add(combWQHT, null);
        combWJS.setBounds(new Rectangle(223, 93, 72, 19));
        this.add(combWJS, null);
        combYJS.setBounds(new Rectangle(329, 93, 72, 19));
        this.add(combYJS, null);
        combIsAllContract.setBounds(new Rectangle(329, 139, 140, 19));
        this.add(combIsAllContract, null);
        combIsAll.setBounds(new Rectangle(22, 118, 140, 19));
        this.add(combIsAll, null);
        kDLabelContainer15.setBounds(new Rectangle(22, 139, 270, 19));
        this.add(kDLabelContainer15, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDBizPromptBox1);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kDBizPromptBox2);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDDatePicker1);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(kDDatePicker2);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(kDBizPromptBox3);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(kDComboBox1);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(Visibility);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(prmtGrade);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(pkFromDate);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(pkToDate);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(prmtCurProject);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(combIsPass);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.markesupplier.rpt.MarketSupplierStockReportFilterUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output combIsAll_itemStateChanged method
     */
    protected void combIsAll_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }



    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.rpt", "MarketSupplierStockReportFilterUI");
    }




}