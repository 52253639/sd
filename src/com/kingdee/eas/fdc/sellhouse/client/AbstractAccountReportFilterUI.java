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
public abstract class AbstractAccountReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAccountReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromNotproPortion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToNotproPortion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromRevDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToRevDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFromDays;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToDays;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFromNotproPortion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToNotproPortion;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromRevDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToRevDate;
    /**
     * output class constructor
     */
    public AbstractAccountReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAccountReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromNotproPortion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToNotproPortion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromRevDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToRevDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtFromDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFromNotproPortion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToNotproPortion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkFromRevDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToRevDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contRoom.setName("contRoom");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contProject.setName("contProject");
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contFromDays.setName("contFromDays");
        this.contToDays.setName("contToDays");
        this.contFromNotproPortion.setName("contFromNotproPortion");
        this.contToNotproPortion.setName("contToNotproPortion");
        this.contFromRevDate.setName("contFromRevDate");
        this.contToRevDate.setName("contToRevDate");
        this.prmtRoom.setName("prmtRoom");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.prmtProject.setName("prmtProject");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.txtFromDays.setName("txtFromDays");
        this.txtToDays.setName("txtToDays");
        this.txtFromNotproPortion.setName("txtFromNotproPortion");
        this.txtToNotproPortion.setName("txtToNotproPortion");
        this.pkFromRevDate.setName("pkFromRevDate");
        this.pkToRevDate.setName("pkToRevDate");
        // CustomerQueryPanel
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contFromDays		
        this.contFromDays.setBoundLabelText(resHelper.getString("contFromDays.boundLabelText"));		
        this.contFromDays.setBoundLabelLength(100);		
        this.contFromDays.setBoundLabelUnderline(true);
        // contToDays		
        this.contToDays.setBoundLabelText(resHelper.getString("contToDays.boundLabelText"));		
        this.contToDays.setBoundLabelLength(100);		
        this.contToDays.setBoundLabelUnderline(true);
        // contFromNotproPortion		
        this.contFromNotproPortion.setBoundLabelText(resHelper.getString("contFromNotproPortion.boundLabelText"));		
        this.contFromNotproPortion.setBoundLabelLength(100);		
        this.contFromNotproPortion.setBoundLabelUnderline(true);
        // contToNotproPortion		
        this.contToNotproPortion.setBoundLabelText(resHelper.getString("contToNotproPortion.boundLabelText"));		
        this.contToNotproPortion.setBoundLabelLength(100);		
        this.contToNotproPortion.setBoundLabelUnderline(true);
        // contFromRevDate		
        this.contFromRevDate.setBoundLabelText(resHelper.getString("contFromRevDate.boundLabelText"));		
        this.contFromRevDate.setBoundLabelLength(100);		
        this.contFromRevDate.setBoundLabelUnderline(true);
        // contToRevDate		
        this.contToRevDate.setBoundLabelText(resHelper.getString("contToRevDate.boundLabelText"));		
        this.contToRevDate.setBoundLabelLength(100);		
        this.contToRevDate.setBoundLabelUnderline(true);
        // prmtRoom		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFilterQuery");		
        this.prmtRoom.setEnabledMultiSelection(true);		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setEditFormat("$number$");		
        this.prmtRoom.setCommitFormat("$number$");
        this.prmtRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtMoneyDefine		
        this.prmtMoneyDefine.setEnabledMultiSelection(true);		
        this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtMoneyDefine.setCommitFormat("$number$");		
        this.prmtMoneyDefine.setEditFormat("$number$");		
        this.prmtMoneyDefine.setDisplayFormat("$name$");
        this.prmtMoneyDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMoneyDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
        this.prmtProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkFromDate
        // pkToDate
        // txtFromDays		
        this.txtFromDays.setSupportedEmpty(true);
        // txtToDays		
        this.txtToDays.setSupportedEmpty(true);
        // txtFromNotproPortion		
        this.txtFromNotproPortion.setDataType(1);		
        this.txtFromNotproPortion.setPrecision(2);		
        this.txtFromNotproPortion.setSupportedEmpty(true);
        // txtToNotproPortion		
        this.txtToNotproPortion.setSupportedEmpty(true);		
        this.txtToNotproPortion.setPrecision(2);		
        this.txtToNotproPortion.setDataType(1);
        // pkFromRevDate
        // pkToRevDate
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
        this.setBounds(new Rectangle(10, 10, 620, 200));
        this.setLayout(null);
        contRoom.setBounds(new Rectangle(29, 53, 270, 19));
        this.add(contRoom, null);
        contMoneyDefine.setBounds(new Rectangle(323, 53, 270, 19));
        this.add(contMoneyDefine, null);
        contProject.setBounds(new Rectangle(29, 31, 270, 19));
        this.add(contProject, null);
        contFromDate.setBounds(new Rectangle(29, 75, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(323, 75, 270, 19));
        this.add(contToDate, null);
        contFromDays.setBounds(new Rectangle(29, 119, 270, 19));
        this.add(contFromDays, null);
        contToDays.setBounds(new Rectangle(323, 119, 270, 19));
        this.add(contToDays, null);
        contFromNotproPortion.setBounds(new Rectangle(29, 141, 270, 19));
        this.add(contFromNotproPortion, null);
        contToNotproPortion.setBounds(new Rectangle(323, 141, 270, 19));
        this.add(contToNotproPortion, null);
        contFromRevDate.setBounds(new Rectangle(29, 97, 270, 19));
        this.add(contFromRevDate, null);
        contToRevDate.setBounds(new Rectangle(323, 97, 270, 19));
        this.add(contToRevDate, null);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contFromDays
        contFromDays.setBoundEditor(txtFromDays);
        //contToDays
        contToDays.setBoundEditor(txtToDays);
        //contFromNotproPortion
        contFromNotproPortion.setBoundEditor(txtFromNotproPortion);
        //contToNotproPortion
        contToNotproPortion.setBoundEditor(txtToNotproPortion);
        //contFromRevDate
        contFromRevDate.setBoundEditor(pkFromRevDate);
        //contToRevDate
        contToRevDate.setBoundEditor(pkToRevDate);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.AccountReportFilterUIHandler";
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
     * output prmtRoom_dataChanged method
     */
    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtMoneyDefine_dataChanged method
     */
    protected void prmtMoneyDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_dataChanged method
     */
    protected void prmtProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "AccountReportFilterUI");
    }




}