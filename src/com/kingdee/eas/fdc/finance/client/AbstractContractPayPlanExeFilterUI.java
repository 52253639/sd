/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractContractPayPlanExeFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractPayPlanExeFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    /**
     * output class constructor
     */
    public AbstractContractPayPlanExeFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractPayPlanExeFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.radioByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblQuarterFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblQuarterTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompany.setName("contCompany");
        this.contPrjoect.setName("contPrjoect");
        this.plDateType.setName("plDateType");
        this.txtCompany.setName("txtCompany");
        this.txtProject.setName("txtProject");
        this.radioByMonth.setName("radioByMonth");
        this.radioByQuarter.setName("radioByQuarter");
        this.radioByYear.setName("radioByYear");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.contYearTo.setName("contYearTo");
        this.spiYearFrom.setName("spiYearFrom");
        this.contYearFrom.setName("contYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.spiYearTo.setName("spiYearTo");
        this.lblQuarterFrom.setName("lblQuarterFrom");
        this.lblQuarterTo.setName("lblQuarterTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.contCurrency.setName("contCurrency");
        this.prmtCurrency.setName("prmtCurrency");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);		
        this.contCompany.setVisible(false);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);		
        this.contPrjoect.setVisible(false);
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // txtProject		
        this.txtProject.setEnabled(false);
        // radioByMonth		
        this.radioByMonth.setText(resHelper.getString("radioByMonth.text"));		
        this.radioByMonth.setSelected(true);
        this.radioByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByQuarter		
        this.radioByQuarter.setText(resHelper.getString("radioByQuarter.text"));
        this.radioByQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByQuarter_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByYear		
        this.radioByYear.setText(resHelper.getString("radioByYear.text"));
        this.radioByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radioByMonth);
        this.kDButtonGroup1.add(this.radioByQuarter);
        this.kDButtonGroup1.add(this.radioByYear);
        // btnCompanySelect		
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));		
        this.btnCompanySelect.setVisible(false);
        this.btnCompanySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCompanySelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));		
        this.btnProjectSelect.setVisible(false);
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // spiYearFrom
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiMonthFrom
        // spiMonthTo
        // spiYearTo
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
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // prmtCurrency		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
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
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(22, 157, 216, 21));
        this.add(contCompany, null);
        contPrjoect.setBounds(new Rectangle(21, 190, 213, 19));
        this.add(contPrjoect, null);
        plDateType.setBounds(new Rectangle(6, 9, 287, 49));
        this.add(plDateType, null);
        btnCompanySelect.setBounds(new Rectangle(248, 188, 69, 21));
        this.add(btnCompanySelect, null);
        btnProjectSelect.setBounds(new Rectangle(246, 157, 69, 19));
        this.add(btnProjectSelect, null);
        contYearTo.setBounds(new Rectangle(12, 85, 180, 19));
        this.add(contYearTo, null);
        contYearFrom.setBounds(new Rectangle(12, 61, 180, 19));
        this.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(215, 61, 49, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(215, 85, 49, 19));
        this.add(spiMonthTo, null);
        lblQuarterFrom.setBounds(new Rectangle(276, 61, 29, 19));
        this.add(lblQuarterFrom, null);
        lblQuarterTo.setBounds(new Rectangle(276, 85, 29, 19));
        this.add(lblQuarterTo, null);
        lblMonthFrom.setBounds(new Rectangle(276, 61, 29, 19));
        this.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(276, 85, 29, 19));
        this.add(lblMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(197, 61, 29, 19));
        this.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(197, 85, 29, 19));
        this.add(lblYearTo, null);
        contCurrency.setBounds(new Rectangle(11, 111, 270, 19));
        this.add(contCurrency, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contPrjoect
        contPrjoect.setBoundEditor(txtProject);
        //plDateType
        plDateType.setLayout(null);        radioByMonth.setBounds(new Rectangle(15, 19, 61, 19));
        plDateType.add(radioByMonth, null);
        radioByQuarter.setBounds(new Rectangle(73, 19, 61, 19));
        plDateType.add(radioByQuarter, null);
        radioByYear.setBounds(new Rectangle(145, 18, 61, 19));
        plDateType.add(radioByYear, null);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);

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
	    return "com.kingdee.eas.fdc.finance.app.ContractPayPlanExeFilterUIHandler";
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
     * output radioByMonth_actionPerformed method
     */
    protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByQuarter_actionPerformed method
     */
    protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByYear_actionPerformed method
     */
    protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ContractPayPlanExeFilterUI");
    }




}