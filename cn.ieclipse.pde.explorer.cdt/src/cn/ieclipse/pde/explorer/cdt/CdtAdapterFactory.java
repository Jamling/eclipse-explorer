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
package cn.ieclipse.pde.explorer.cdt;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IInclude;
import org.eclipse.cdt.core.model.IIncludeFileEntry;
import org.eclipse.core.resources.IResource;

import cn.ieclipse.pde.explorer.AdapterFactory;
import cn.ieclipse.pde.explorer.Explorer;
import cn.ieclipse.pde.explorer.ExplorerPlugin;
import cn.ieclipse.pde.explorer.IExplorable;

/**
 * Adapter factory for c/c++ model
 * 
 * @author Jamling
 *         
 */
public class CdtAdapterFactory extends AdapterFactory {
    
    @Override
    public IExplorable getExplorable(Object adaptableObject) {
        IExplorable ret = null;
        // for c/c++ element
        if (adaptableObject instanceof ICElement) {
            ret = getFromCDT((ICElement) adaptableObject);
        }
        
        if (ret != null) {
            ret.setIdentifier("explorer4cdt");
        }
        return ret;
    }
    
    public IExplorable getFromCDT(ICElement cele) {
        String path = null;
        // project
        if (cele instanceof ICProject) {
            path = ((ICProject) cele).getProject().getLocation().toOSString();
            return new Explorer(path, null);
        }
        else if (cele instanceof IIncludeFileEntry) {
            path = ((IIncludeFileEntry) cele).getFullIncludeFilePath()
                    .toOSString();
            return new Explorer(null, path);
        }
        else if (cele instanceof IInclude) {
            path = ((IInclude) cele).getParent().getPath().toOSString();
            return new Explorer(null, path);
        }
        // other
        else {
            IResource res = cele.getResource();
            if (res != null) {
                return ExplorerPlugin.getFromResource(res);
            }
        }
        return null;
    }
}
