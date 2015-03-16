/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractAgingScheduleFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAgingScheduleFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conType;
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAttach;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpEndDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox overdueType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radDay;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected ActionBtnSave actionBtnSave = null;
    protected ActionBtnDel actionBtnDel = null;
    /**
     * output class constructor
     */
    public AbstractAgingScheduleFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAgingScheduleFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionBtnSave
        this.actionBtnSave = new ActionBtnSave(this);
        getActionManager().registerAction("actionBtnSave", actionBtnSave);
         this.actionBtnSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnDel
        this.actionBtnDel = new ActionBtnDel(this);
        getActionManager().registerAction("actionBtnDel", actionBtnDel);
         this.actionBtnDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.conBeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblQuarterFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblQuarterTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator1 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.isAttach = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.dpBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.overdueType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.radDay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.conBeginDate.setName("conBeginDate");
        this.conEndDate.setName("conEndDate");
        this.conType.setName("conType");
        this.plDateType.setName("plDateType");
        this.kDLabel1.setName("kDLabel1");
        this.kDTable1.setName("kDTable1");
        this.btnSave.setName("btnSave");
        this.btnDel.setName("btnDel");
        this.contYearFrom.setName("contYearFrom");
        this.contYearTo.setName("contYearTo");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblQuarterFrom.setName("lblQuarterFrom");
        this.lblQuarterTo.setName("lblQuarterTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.kDSeparator1.setName("kDSeparator1");
        this.isAttach.setName("isAttach");
        this.dpBeginDate.setName("dpBeginDate");
        this.dpEndDate.setName("dpEndDate");
        this.overdueType.setName("overdueType");
        this.radDay.setName("radDay");
        this.radMonth.setName("radMonth");
        this.radQuarter.setName("radQuarter");
        this.radYear.setName("radYear");
        this.spiYearFrom.setName("spiYearFrom");
        this.spiYearTo.setName("spiYearTo");
        // CustomerQueryPanel
        // conBeginDate		
        this.conBeginDate.setBoundLabelText(resHelper.getString("conBeginDate.boundLabelText"));		
        this.conBeginDate.setBoundLabelLength(100);		
        this.conBeginDate.setBoundLabelUnderline(true);
        // conEndDate		
        this.conEndDate.setBoundLabelText(resHelper.getString("conEndDate.boundLabelText"));		
        this.conEndDate.setBoundLabelLength(100);		
        this.conEndDate.setBoundLabelUnderline(true);
        // conType		
        this.conType.setBoundLabelText(resHelper.getString("conType.boundLabelText"));		
        this.conType.setBoundLabelLength(100);		
        this.conType.setBoundLabelUnderline(true);		
        this.conType.setVisible(false);		
        this.conType.setEnabled(false);
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"caption\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fate\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{caption}</t:Cell><t:Cell>$Resource{fate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // spiMonthFrom
        // spiMonthTo
        // lblQuarterFrom		
        this.lblQuarterFrom.setText(resHelper.getString("lblQuarterFrom.text"));
        // lblQuarterTo		
        this.lblQuarterTo.setText(resHelper.getString("lblQuarterTo.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // kDSeparator1
        // isAttach		
        this.isAttach.setText(resHelper.getString("isAttach.text"));
        // dpBeginDate
        this.dpBeginDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dpBeginDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // dpEndDate
        this.dpEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dpEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // overdueType		
        this.overdueType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.AgingScheduleEnum").toArray());
        // radDay		
        this.radDay.setText(resHelper.getString("radDay.text"));		
        this.radDay.setSelected(true);
        this.radDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radDay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radMonth		
        this.radMonth.setText(resHelper.getString("radMonth.text"));
        this.radMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radQuarter		
        this.radQuarter.setText(resHelper.getString("radQuarter.text"));
        this.radQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radQuarter_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radYear		
        this.radYear.setText(resHelper.getString("radYear.text"));
        this.radYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radDay);
        this.kDButtonGroup1.add(this.radMonth);
        this.kDButtonGroup1.add(this.radQuarter);
        this.kDButtonGroup1.add(this.radYear);
        // spiYearFrom
        // spiYearTo
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(null);
        conBeginDate.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(conBeginDate, null);
        conEndDate.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(conEndDate, null);
        conType.setBounds(new Rectangle(214, 174, 270, 19));
        this.add(conType, null);
        plDateType.setBounds(new Rectangle(10, 66, 327, 102));
        this.add(plDateType, null);
        kDLabel1.setBounds(new Rectangle(10, 209, 100, 19));
        this.add(kDLabel1, null);
        kDTable1.setBounds(new Rectangle(10, 254, 412, 132));
        this.add(kDTable1, null);
        btnSave.setBounds(new Rectangle(244, 230, 78, 19));
        this.add(btnSave, null);
        btnDel.setBounds(new Rectangle(334, 230, 78, 19));
        this.add(btnDel, null);
        contYearFrom.setBounds(new Rectangle(10, 10, 180, 19));
        this.add(contYearFrom, null);
        contYearTo.setBounds(new Rectangle(10, 37, 180, 19));
        this.add(contYearTo, null);
        spiMonthFrom.setBounds(new Rectangle(218, 10, 49, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(218, 37, 49, 19));
        this.add(spiMonthTo, null);
        lblQuarterFrom.setBounds(new Rectangle(274, 10, 26, 19));
        this.add(lblQuarterFrom, null);
        lblQuarterTo.setBounds(new Rectangle(274, 37, 27, 19));
        this.add(lblQuarterTo, null);
        lblMonthFrom.setBounds(new Rectangle(274, 37, 20, 19));
        this.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(274, 10, 21, 19));
        this.add(lblMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(196, 10, 17, 19));
        this.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(196, 37, 17, 19));
        this.add(lblYearTo, null);
        kDSeparator1.setBounds(new Rectangle(141, 343, 100, 10));
        this.add(kDSeparator1, null);
        isAttach.setBounds(new Rectangle(12, 178, 140, 19));
        this.add(isAttach, null);
        //conBeginDate
        conBeginDate.setBoundEditor(dpBeginDate);
        //conEndDate
        conEndDate.setBoundEditor(dpEndDate);
        //conType
        conType.setBoundEditor(overdueType);
        //plDateType
        plDateType.setLayout(null);        radDay.setBounds(new Rectangle(28, 34, 62, 30));
        plDateType.add(radDay, null);
        radMonth.setBounds(new Rectangle(97, 34, 58, 30));
        plDateType.add(radMonth, null);
        radQuarter.setBounds(new Rectangle(162, 34, 60, 30));
        plDateType.add(radQuarter, null);
        radYear.setBounds(new Rectangle(240, 34, 58, 30));
        plDateType.add(radYear, null);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.AgingScheduleFilterUIHandler";
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
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output dpBeginDate_dataChanged method
     */
    protected void dpBeginDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output dpEndDate_dataChanged method
     */
    protected void dpEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output radDay_actionPerformed method
     */
    protected void radDay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radMonth_actionPerformed method
     */
    protected void radMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radQuarter_actionPerformed method
     */
    protected void radQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output radYear_actionPerformed method
     */
    protected void radYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionBtnSave_actionPerformed method
     */
    public void actionBtnSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnDel_actionPerformed method
     */
    public void actionBtnDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBtnSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnSave() {
    	return false;
    }
	public RequestContext prepareActionBtnDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnDel() {
    	return false;
    }

    /**
     * output ActionBtnSave class
     */     
    protected class ActionBtnSave extends ItemAction {     
    
        public ActionBtnSave()
        {
            this(null);
        }

        public ActionBtnSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAgingScheduleFilterUI.this, "ActionBtnSave", "actionBtnSave_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnDel class
     */     
    protected class ActionBtnDel extends ItemAction {     
    
        public ActionBtnDel()
        {
            this(null);
        }

        public ActionBtnDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAgingScheduleFilterUI.this, "ActionBtnDel", "actionBtnDel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "AgingScheduleFilterUI");
    }




}