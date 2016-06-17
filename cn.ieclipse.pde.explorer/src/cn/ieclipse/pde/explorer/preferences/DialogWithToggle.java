/*
 * Copyright 2014-2015 ieclipse.cn.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ieclipse.pde.explorer.preferences;

import java.net.URL;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.commands.ICommandService;

/**
 * Dialog with an toggle button
 * 
 * @author Jamling
 *         
 */
public class DialogWithToggle extends org.eclipse.jface.dialogs.Dialog {
    private String title;
    private String message;
    boolean init;
    String key;
    private String label;
    private IPreferenceStore store;
    private Button chkHide;
    
    protected DialogWithToggle(Shell parentShell, String title, String message,
            String label, boolean init, IPreferenceStore store, String key) {
        super(parentShell);
        this.title = title;
        this.message = message;
        this.init = init;
        this.key = key;
        this.label = label;
        this.store = store;
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        
        Label lblMsg = new Label(composite, SWT.WRAP);
        lblMsg.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        lblMsg.setText(message);
        
        Link link = new Link(composite, SWT.NONE);
        link.setText(
                "You can: <a>Install</a> fragment or <a>develop</a> fragment to enhance the function\n"
                        + "\n<a>Submit an issue</a>");
        link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String text = e.text.toLowerCase();
                if ("install".equals(text)) {
                    openCommand(
                            "org.eclipse.epp.mpc.ui.command.showMarketplaceWizard");
                }
                else if ("submit an issue".equals(text)) {
                    openBrowser(
                            "https://github.com/Jamling/eclipse-explorer/issues");
                }
                else if ("develop".equals(text)) {
                    openBrowser(
                            "https://ieclipse.cn/p/eclipse-explorer/develop.html");
                }
            }
            
            private void openBrowser(String url) {
                IWorkbenchBrowserSupport support = PlatformUI.getWorkbench()
                        .getBrowserSupport();
                IWebBrowser browser;
                try {
                    browser = support.getExternalBrowser();
                    browser.openURL(new URL(url));
                    close();
                } catch (PartInitException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            
            private void openCommand(String id) {
                try {
                    ICommandService commandService = (ICommandService) PlatformUI
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActivePart().getSite()
                            .getService(ICommandService.class);
                            
                    commandService.getCommand(id)
                            .executeWithChecks(new ExecutionEvent(null,
                                    Collections.EMPTY_MAP, null, null));
                    close();
                } catch (Exception ex) {
                
                }
            }
        });
        
        chkHide = new Button(composite, SWT.CHECK);
        chkHide.setText(label);
        chkHide.setSelection(init);
        chkHide.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                init = chkHide.getSelection();
            }
        });
        
        chkHide.forceFocus();
        
        return composite;
    }
    
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setImage(getSWTImage(SWT.ICON_WARNING));
        newShell.setText(title);
        int width = newShell.getMonitor().getClientArea().width;
        // int height = newShell.getMonitor().getClientArea().height;
        int w = Math.min(600, width >> 1);
        // int h = Math.min(300, height >> 1);
        newShell.setMinimumSize(w, 0);
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
    }
    
    @Override
    protected void okPressed() {
        if (store != null && key != null) {
            store.setValue(key, !init);
        }
        super.okPressed();
    }
    
    /**
     * Get an <code>Image</code> from the provide SWT image constant.
     *
     * @param imageID
     *            the SWT image constant
     * @return image the image
     */
    private Image getSWTImage(final int imageID) {
        Shell shell = getShell();
        final Display display;
        if (shell == null || shell.isDisposed()) {
            shell = getParentShell();
        }
        if (shell == null || shell.isDisposed()) {
            display = Display.getCurrent();
            // The dialog should be always instantiated in UI thread.
            // However it was possible to instantiate it in other threads
            // (the code worked in most cases) so the assertion covers
            // only the failing scenario. See bug 107082 for details.
            Assert.isNotNull(display,
                    "The dialog should be created in UI thread"); //$NON-NLS-1$
        }
        else {
            display = shell.getDisplay();
        }
        
        final Image[] image = new Image[1];
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                image[0] = display.getSystemImage(imageID);
            }
        });
        
        return image[0];
        
    }
    
    public static void openWarning(Shell shell, String title, String msg,
            String chkLabel, boolean init, IPreferenceStore store, String key) {
        DialogWithToggle dialog = new DialogWithToggle(shell, title, msg,
                chkLabel, init, store, key);
        dialog.open();
    }
}
