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
package cn.ieclipse.pde.explorer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IEditorPart;

import cn.ieclipse.pde.explorer.ExplorerPlugin;
import cn.ieclipse.pde.explorer.IExplorable;

/**
 * Open in Explorer for editor
 * 
 * @author Jamling
 *         
 */
public class OpenExplorerHandler2 extends AbstractHandler {
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
     * ExecutionEvent)
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            IEditorPart editorPart = ExplorerPlugin.getDefault().getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor();
            IResource res = editorPart.getEditorInput()
                    .getAdapter(IResource.class);
            if (res != null) {
                IExplorable explor = ExplorerPlugin.getFromResource(res);
                ExplorerPlugin.v("from editor:" + explor);
                ExplorerPlugin.explorer(explor.getFolder(), explor.getFile());
            }
        } catch (Exception e) {
            ExplorerPlugin.w("an error occured", e);
        }
        return null;
    }
    
}
