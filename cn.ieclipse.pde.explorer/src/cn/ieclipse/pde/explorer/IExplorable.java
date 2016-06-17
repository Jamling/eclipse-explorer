/*
 * Copyright 2015 Jamling(li.jamling@gmail.com).
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
package cn.ieclipse.pde.explorer;

import org.eclipse.core.runtime.IAdaptable;

/**
 * The explorable interface, if want your model to explorable, you can do
 * <ul>
 * <li>Implements the {@link IExplorable} interface</li>
 * <li>adapter your model to {@link IExplorable}, see
 * {@link cn.ieclipse.pde.explorer.AdapterFactory}</li>
 * </ul>
 * 
 * @author Jamling
 * @see AdapterFactory
 */
public interface IExplorable extends IAdaptable {
    /**
     * Return the folder to explorer, must be a absolute path
     * 
     * @return folder to explorer
     */
    public String getFolder();
    
    /**
     * Return the selected file under the explored folder
     * 
     * @return file to select, can be empty
     */
    public String getFile();
    
    /**
     * Set the id, it may be saw in "Error log" to identify which fragment
     * return this
     * 
     * @param id
     *            id for the fragment
     */
    public void setIdentifier(String id);
}
