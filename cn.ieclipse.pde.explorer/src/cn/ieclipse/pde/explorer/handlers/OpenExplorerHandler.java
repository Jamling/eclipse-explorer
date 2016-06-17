/*
 * Copyright 2012-2013 Jamling(li.jamling@gmail.com).
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
package cn.ieclipse.pde.explorer.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.team.ui.synchronize.ISynchronizeModelElement;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import cn.ieclipse.pde.explorer.Explorer;
import cn.ieclipse.pde.explorer.ExplorerPlugin;
import cn.ieclipse.pde.explorer.IExplorable;
import cn.ieclipse.pde.explorer.preferences.DialogWithToggle;
import cn.ieclipse.pde.explorer.preferences.Messages;
import cn.ieclipse.pde.explorer.preferences.PreferenceConstants;

/**
 * Open in Explorer for views
 * 
 * @author melord
 *         
 */
public class OpenExplorerHandler extends AbstractHandler {
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil
                .getActiveWorkbenchWindowChecked(event);
        ISelection sel = window.getSelectionService().getSelection();
        if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
            Object obj = ((IStructuredSelection) sel).getFirstElement();
            // ExplorerPlugin.v("obj:" + obj.getClass());
            IResource resource = null;
            IExplorable explor = null;
            // common resource file
            if (obj instanceof IResource) {
                resource = (IResource) obj;
                explor = ExplorerPlugin.getFromResource(resource);
            }
            else if (obj instanceof IExplorable) {
                explor = (IExplorable) explor;
            }
            // explorer team ui resource
            else if (obj instanceof ISynchronizeModelElement) {
                // TODO https://www.eclipse.org/forums/index.php/t/205955/
                resource = ((ISynchronizeModelElement) obj).getResource();
                explor = ExplorerPlugin.getFromResource(resource);
                ExplorerPlugin.v(String.format("from team: %s", explor)); //$NON-NLS-1$
            }
            // try fragment adapter
            else {
                explor = Platform.getAdapterManager().getAdapter(obj,
                        IExplorable.class);
                if (explor != null) {
                    ExplorerPlugin.v("from fragments:" + explor); //$NON-NLS-1$
                }
            }
            // try IAdaptable
            if (explor == null) {
                if (obj instanceof IAdaptable) {
                    resource = ((IAdaptable) obj).getAdapter(IResource.class);
                    if (resource != null) {
                        explor = ExplorerPlugin.getFromResource(resource);
                        if (explor != null) {
                            ExplorerPlugin.v("from adapter:" + explor); //$NON-NLS-1$
                        }
                    }
                }
            }
            // try last
            if (explor == null) {
                explor = getFromReflect(obj);
            }
            
            // process
            if (explor != null) {
                ExplorerPlugin.explorer(explor.getFolder(), explor.getFile());
            }
            else {
                // feedback
                String msg = String.format(Messages.OpenExplorerHandler_dia_msg,
                        obj.getClass().getName(), window.getActivePage()
                                .getActivePart().getClass().getName());
                ExplorerPlugin.w(msg, null);
                IPreferenceStore store = ExplorerPlugin.getDefault()
                        .getPreferenceStore();
                boolean popup = store
                        .getBoolean(PreferenceConstants.EXPLORER_TIP);
                if (popup) {
                    DialogWithToggle.openWarning(window.getShell(),
                            Messages.OpenExplorerHandler_dia_title, msg,
                            Messages.OpenExplorerHandler_dia_prt, !popup, store,
                            PreferenceConstants.EXPLORER_TIP);
                }
            }
        }
        return null;
    }
    
    private IExplorable getFromReflect(Object obj) {
        Explorer explor = null;
        try {
            Method m = obj.getClass().getMethod("getPath"); //$NON-NLS-1$
            Object ret = m.invoke(obj);
            File f = null;
            if (ret instanceof IPath) {
                IPath p = (IPath) ret;
                f = new File(p.toOSString());
                if (f.exists()) {
                    if (f.isDirectory()) {
                        explor = new Explorer(f.getAbsolutePath(), null);
                    }
                    else {
                        explor = new Explorer(f.getAbsolutePath());
                    }
                    ExplorerPlugin.v("from getPath:" + explor); //$NON-NLS-1$
                    return explor;
                }
            }
            m = obj.getClass().getMethod("getLocation"); //$NON-NLS-1$
            ret = m.invoke(obj);
            if (ret instanceof IPath) {
                IPath p = (IPath) ret;
                f = new File(p.toOSString());
                if (f.exists()) {
                    if (f.isDirectory()) {
                        explor = new Explorer(f.getAbsolutePath(), null);
                    }
                    else {
                        explor = new Explorer(f.getAbsolutePath());
                    }
                    ExplorerPlugin.v("from getLocation:" + explor); //$NON-NLS-1$
                    return explor;
                }
            }
        } catch (NoSuchMethodException e) {
            // Do nothing
        } catch (IllegalAccessException e) {
            // Do nothing
        } catch (InvocationTargetException e) {
            // Do nothing
        } catch (Exception e) {
            ExplorerPlugin.w("an error occured", e); //$NON-NLS-1$
        }
        return null;
    }
}
