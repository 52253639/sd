/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

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
public abstract class AbstractSupplierStockReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierStockReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combYJS;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combWQHT;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combWJS;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combIsAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combIsAllContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtGrade;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuaLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combIsPass;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    /**
     * output class constructor
     */
    public AbstractSupplierStockReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierStockReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuaLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combYJS = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combWQHT = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combWJS = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combIsAllContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuaLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.combIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contName.setName("contName");
        this.contLevel.setName("contLevel");
        this.contGrade.setName("contGrade");
        this.contQuaLevel.setName("contQuaLevel");
        this.contCurProject.setName("contCurProject");
        this.combYJS.setName("combYJS");
        this.combWQHT.setName("combWQHT");
        this.combWJS.setName("combWJS");
        this.contState.setName("contState");
        this.combIsAll.setName("combIsAll");
        this.contIsPass.setName("contIsPass");
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.combIsAllContract.setName("combIsAllContract");
        this.txtName.setName("txtName");
        this.prmtLevel.setName("prmtLevel");
        this.prmtGrade.setName("prmtGrade");
        this.prmtQuaLevel.setName("prmtQuaLevel");
        this.prmtCurProject.setName("prmtCurProject");
        this.combIsPass.setName("combIsPass");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        // CustomerQueryPanel
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);
        // contGrade		
        this.contGrade.setBoundLabelText(resHelper.getString("contGrade.boundLabelText"));		
        this.contGrade.setBoundLabelLength(100);		
        this.contGrade.setBoundLabelUnderline(true);
        // contQuaLevel		
        this.contQuaLevel.setBoundLabelText(resHelper.getString("contQuaLevel.boundLabelText"));		
        this.contQuaLevel.setBoundLabelLength(100);		
        this.contQuaLevel.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // combYJS		
        this.combYJS.setText(resHelper.getString("combYJS.text"));
        // combWQHT		
        this.combWQHT.setText(resHelper.getString("combWQHT.text"));
        // combWJS		
        this.combWJS.setText(resHelper.getString("combWJS.text"));
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
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
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setBoundLabelUnderline(true);
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // combIsAllContract		
        this.combIsAllContract.setText(resHelper.getString("combIsAllContract.text"));
        // txtName
        // prmtLevel		
        this.prmtLevel.setDisplayFormat("$name$");		
        this.prmtLevel.setEditFormat("$number$");		
        this.prmtLevel.setCommitFormat("$number$");		
        this.prmtLevel.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7LevelSetUpQuery");		
        this.prmtLevel.setEnabledMultiSelection(true);
        // prmtGrade		
        this.prmtGrade.setDisplayFormat("$name$");		
        this.prmtGrade.setEditFormat("$number$");		
        this.prmtGrade.setCommitFormat("$number$");		
        this.prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");		
        this.prmtGrade.setEnabledMultiSelection(true);
        // prmtQuaLevel		
        this.prmtQuaLevel.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7QuaLevelQuery");		
        this.prmtQuaLevel.setCommitFormat("$number$");		
        this.prmtQuaLevel.setEditFormat("$number$");		
        this.prmtQuaLevel.setDisplayFormat("$name$");		
        this.prmtQuaLevel.setEnabledMultiSelection(true);
        // prmtCurProject		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setCommitFormat("$number$");		
        this.prmtCurProject.setEnabledMultiSelection(true);		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setDisplayFormat("$name$");
        // combIsPass		
        this.combIsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
        // pkFromDate
        // pkToDate
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
        this.setBounds(new Rectangle(10, 10, 600, 180));
        this.setLayout(null);
        contName.setBounds(new Rectangle(14, 10, 270, 19));
        this.add(contName, null);
        contLevel.setBounds(new Rectangle(14, 32, 270, 19));
        this.add(contLevel, null);
        contGrade.setBounds(new Rectangle(317, 32, 270, 19));
        this.add(contGrade, null);
        contQuaLevel.setBounds(new Rectangle(317, 10, 270, 19));
        this.add(contQuaLevel, null);
        contCurProject.setBounds(new Rectangle(14, 77, 270, 19));
        this.add(contCurProject, null);
        combYJS.setBounds(new Rectangle(317, 101, 72, 19));
        this.add(combYJS, null);
        combWQHT.setBounds(new Rectangle(114, 101, 82, 19));
        this.add(combWQHT, null);
        combWJS.setBounds(new Rectangle(229, 101, 72, 19));
        this.add(combWJS, null);
        contState.setBounds(new Rectangle(14, 101, 100, 19));
        this.add(contState, null);
        combIsAll.setBounds(new Rectangle(14, 125, 140, 19));
        this.add(combIsAll, null);
        contIsPass.setBounds(new Rectangle(14, 144, 270, 19));
        this.add(contIsPass, null);
        contFromDate.setBounds(new Rectangle(14, 54, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(317, 54, 270, 19));
        this.add(contToDate, null);
        combIsAllContract.setBounds(new Rectangle(317, 144, 140, 19));
        this.add(combIsAllContract, null);
        //contName
        contName.setBoundEditor(txtName);
        //contLevel
        contLevel.setBoundEditor(prmtLevel);
        //contGrade
        contGrade.setBoundEditor(prmtGrade);
        //contQuaLevel
        contQuaLevel.setBoundEditor(prmtQuaLevel);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contIsPass
        contIsPass.setBoundEditor(combIsPass);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);

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
	    return "com.kingdee.eas.fdc.invite.supplier.report.SupplierStockReportFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.report", "SupplierStockReportFilterUI");
    }




}