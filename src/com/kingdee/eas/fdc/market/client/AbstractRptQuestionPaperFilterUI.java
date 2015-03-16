/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractRptQuestionPaperFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptQuestionPaperFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuestion1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByDate;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnBySeason;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByYear;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowAll;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuestion2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuestion1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem3;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuestion2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem3;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem4;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup btnGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblSeasonFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblSeasonTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSeasonFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSeasonTo;
    /**
     * output class constructor
     */
    public AbstractRptQuestionPaperFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRptQuestionPaperFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contPaper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuestion1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnByDate = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnBySeason = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.chkIsShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contQuestion2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contItem4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contItem5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contItem6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPaper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuestion1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtItem3 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuestion2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtItem5 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtItem6 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contItem1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtItem1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contItem2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtItem2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contItem3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtItem4 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblSeasonFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblSeasonTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiSeasonFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiSeasonTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1.setName("kDLabel1");
        this.contPaper.setName("contPaper");
        this.contQuestion1.setName("contQuestion1");
        this.dpDateFrom.setName("dpDateFrom");
        this.dpDateTo.setName("dpDateTo");
        this.kDPanel1.setName("kDPanel1");
        this.btnByDate.setName("btnByDate");
        this.btnByMonth.setName("btnByMonth");
        this.btnBySeason.setName("btnBySeason");
        this.btnByYear.setName("btnByYear");
        this.chkIsShowAll.setName("chkIsShowAll");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.contQuestion2.setName("contQuestion2");
        this.contItem4.setName("contItem4");
        this.contItem5.setName("contItem5");
        this.contItem6.setName("contItem6");
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.prmtPaper.setName("prmtPaper");
        this.prmtQuestion1.setName("prmtQuestion1");
        this.prmtItem3.setName("prmtItem3");
        this.prmtQuestion2.setName("prmtQuestion2");
        this.prmtItem5.setName("prmtItem5");
        this.prmtItem6.setName("prmtItem6");
        this.contItem1.setName("contItem1");
        this.prmtItem1.setName("prmtItem1");
        this.contItem2.setName("contItem2");
        this.prmtItem2.setName("prmtItem2");
        this.contItem3.setName("contItem3");
        this.prmtItem4.setName("prmtItem4");
        this.contYearTo.setName("contYearTo");
        this.contYearFrom.setName("contYearFrom");
        this.spiYearFrom.setName("spiYearFrom");
        this.spiYearTo.setName("spiYearTo");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.lblSeasonFrom.setName("lblSeasonFrom");
        this.lblSeasonTo.setName("lblSeasonTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.spiSeasonFrom.setName("spiSeasonFrom");
        this.spiSeasonTo.setName("spiSeasonTo");
        // CustomerQueryPanel
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // contPaper		
        this.contPaper.setBoundLabelText(resHelper.getString("contPaper.boundLabelText"));		
        this.contPaper.setBoundLabelLength(100);		
        this.contPaper.setBoundLabelUnderline(true);
        // contQuestion1		
        this.contQuestion1.setBoundLabelText(resHelper.getString("contQuestion1.boundLabelText"));		
        this.contQuestion1.setBoundLabelLength(100);		
        this.contQuestion1.setBoundLabelUnderline(true);
        // dpDateFrom
        // dpDateTo
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(128,128,128),1), resHelper.getString("kDPanel1.border.title")));
        // btnByDate		
        this.btnByDate.setText(resHelper.getString("btnByDate.text"));
        this.btnByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByDate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnByMonth		
        this.btnByMonth.setText(resHelper.getString("btnByMonth.text"));
        this.btnByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnBySeason		
        this.btnBySeason.setText(resHelper.getString("btnBySeason.text"));
        this.btnBySeason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBySeason_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnByYear		
        this.btnByYear.setText(resHelper.getString("btnByYear.text"));
        this.btnByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsShowAll		
        this.chkIsShowAll.setText(resHelper.getString("chkIsShowAll.text"));
        this.chkIsShowAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkIsShowAll_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(128,128,128),1), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(128,128,128),1), resHelper.getString("kDPanel3.border.title")));
        // contQuestion2		
        this.contQuestion2.setBoundLabelText(resHelper.getString("contQuestion2.boundLabelText"));		
        this.contQuestion2.setBoundLabelLength(100);		
        this.contQuestion2.setBoundLabelUnderline(true);
        // contItem4		
        this.contItem4.setBoundLabelText(resHelper.getString("contItem4.boundLabelText"));		
        this.contItem4.setBoundLabelLength(100);		
        this.contItem4.setBoundLabelUnderline(true);
        // contItem5		
        this.contItem5.setBoundLabelText(resHelper.getString("contItem5.boundLabelText"));		
        this.contItem5.setBoundLabelLength(100);		
        this.contItem5.setBoundLabelUnderline(true);
        // contItem6		
        this.contItem6.setBoundLabelText(resHelper.getString("contItem6.boundLabelText"));		
        this.contItem6.setBoundLabelLength(100);		
        this.contItem6.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // prmtPaper
        this.prmtPaper.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPaper_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtQuestion1
        this.prmtQuestion1.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtQuestion1_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtQuestion1.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtQuestion1_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtItem3
        // prmtQuestion2
        this.prmtQuestion2.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtQuestion2_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtQuestion2.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtQuestion2_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtItem5
        // prmtItem6
        // contItem1		
        this.contItem1.setBoundLabelText(resHelper.getString("contItem1.boundLabelText"));		
        this.contItem1.setBoundLabelLength(100);		
        this.contItem1.setBoundLabelUnderline(true);
        // prmtItem1
        // contItem2		
        this.contItem2.setBoundLabelText(resHelper.getString("contItem2.boundLabelText"));		
        this.contItem2.setBoundLabelLength(100);		
        this.contItem2.setBoundLabelUnderline(true);
        // prmtItem2
        // contItem3		
        this.contItem3.setBoundLabelText(resHelper.getString("contItem3.boundLabelText"));		
        this.contItem3.setBoundLabelLength(100);		
        this.contItem3.setBoundLabelUnderline(true);
        // prmtItem4
        // btnGroup
        this.btnGroup.add(this.btnByDate);
        this.btnGroup.add(this.btnByMonth);
        this.btnGroup.add(this.btnBySeason);
        this.btnGroup.add(this.btnByYear);
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiYearFrom
        // spiYearTo
        // spiMonthFrom
        // spiMonthTo
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // lblSeasonFrom		
        this.lblSeasonFrom.setText(resHelper.getString("lblSeasonFrom.text"));
        // lblSeasonTo		
        this.lblSeasonTo.setText(resHelper.getString("lblSeasonTo.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // spiSeasonFrom
        // spiSeasonTo
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 550, 450));
        this.setLayout(null);
        kDLabel1.setBounds(new Rectangle(10, 10, 100, 19));
        this.add(kDLabel1, null);
        kDPanel1.setBounds(new Rectangle(10, 94, 352, 83));
        this.add(kDPanel1, null);
        chkIsShowAll.setBounds(new Rectangle(16, 179, 178, 19));
        this.add(chkIsShowAll, null);
        kDPanel2.setBounds(new Rectangle(7, 205, 478, 113));
        this.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(9, 329, 476, 93));
        this.add(kDPanel3, null);
        contDateFrom.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(contDateTo, null);
        contYearTo.setBounds(new Rectangle(10, 60, 180, 19));
        this.add(contYearTo, null);
        contYearFrom.setBounds(new Rectangle(10, 35, 180, 19));
        this.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(213, 35, 49, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(213, 60, 49, 19));
        this.add(spiMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(195, 35, 29, 19));
        this.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(195, 60, 29, 19));
        this.add(lblYearTo, null);
        lblSeasonFrom.setBounds(new Rectangle(274, 35, 29, 19));
        this.add(lblSeasonFrom, null);
        lblSeasonTo.setBounds(new Rectangle(274, 60, 29, 19));
        this.add(lblSeasonTo, null);
        lblMonthFrom.setBounds(new Rectangle(274, 35, 29, 19));
        this.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(274, 60, 29, 19));
        this.add(lblMonthTo, null);
        spiSeasonFrom.setBounds(new Rectangle(213, 35, 49, 19));
        this.add(spiSeasonFrom, null);
        spiSeasonTo.setBounds(new Rectangle(213, 60, 49, 19));
        this.add(spiSeasonTo, null);
        //kDPanel1
        kDPanel1.setLayout(null);        btnByDate.setBounds(new Rectangle(12, 30, 61, 19));
        kDPanel1.add(btnByDate, null);
        btnByMonth.setBounds(new Rectangle(97, 31, 50, 19));
        kDPanel1.add(btnByMonth, null);
        btnBySeason.setBounds(new Rectangle(178, 31, 56, 19));
        kDPanel1.add(btnBySeason, null);
        btnByYear.setBounds(new Rectangle(262, 31, 71, 19));
        kDPanel1.add(btnByYear, null);
        //kDPanel2
        kDPanel2.setLayout(null);        contItem4.setBounds(new Rectangle(255, 46, 211, 19));
        kDPanel2.add(contItem4, null);
        contItem5.setBounds(new Rectangle(15, 79, 211, 19));
        kDPanel2.add(contItem5, null);
        contItem6.setBounds(new Rectangle(254, 77, 211, 19));
        kDPanel2.add(contItem6, null);
        contItem1.setBounds(new Rectangle(15, 16, 211, 19));
        kDPanel2.add(contItem1, null);
        contItem2.setBounds(new Rectangle(255, 14, 211, 19));
        kDPanel2.add(contItem2, null);
        contItem3.setBounds(new Rectangle(15, 47, 211, 19));
        kDPanel2.add(contItem3, null);
        //contItem4
        contItem4.setBoundEditor(prmtItem4);
        //contItem5
        contItem5.setBoundEditor(prmtItem5);
        //contItem6
        contItem6.setBoundEditor(prmtItem6);
        //contItem1
        contItem1.setBoundEditor(prmtItem1);
        //contItem2
        contItem2.setBoundEditor(prmtItem2);
        //contItem3
        contItem3.setBoundEditor(prmtItem3);
        //kDPanel3
        kDPanel3.setLayout(null);        contPaper.setBounds(new Rectangle(15, 15, 211, 19));
        kDPanel3.add(contPaper, null);
        contQuestion1.setBounds(new Rectangle(15, 48, 211, 19));
        kDPanel3.add(contQuestion1, null);
        contQuestion2.setBounds(new Rectangle(254, 48, 211, 19));
        kDPanel3.add(contQuestion2, null);
        //contPaper
        contPaper.setBoundEditor(prmtPaper);
        //contQuestion1
        contQuestion1.setBoundEditor(prmtQuestion1);
        //contQuestion2
        contQuestion2.setBoundEditor(prmtQuestion2);
        //contDateFrom
        contDateFrom.setBoundEditor(dpDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(dpDateTo);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);

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
	    return "com.kingdee.eas.fdc.market.app.RptQuestionPaperFilterUIHandler";
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
     * output btnByDate_actionPerformed method
     */
    protected void btnByDate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByMonth_actionPerformed method
     */
    protected void btnByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnBySeason_actionPerformed method
     */
    protected void btnBySeason_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByYear_actionPerformed method
     */
    protected void btnByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsShowAll_itemStateChanged method
     */
    protected void chkIsShowAll_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtPaper_dataChanged method
     */
    protected void prmtPaper_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion1_willCommit method
     */
    protected void prmtQuestion1_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion1_willShow method
     */
    protected void prmtQuestion1_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion2_willShow method
     */
    protected void prmtQuestion2_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion2_willCommit method
     */
    protected void prmtQuestion2_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "RptQuestionPaperFilterUI");
    }




}