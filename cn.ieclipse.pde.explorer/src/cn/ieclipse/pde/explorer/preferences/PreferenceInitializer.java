/*
 * Copyright 2010 the original author or authors.
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import cn.ieclipse.pde.explorer.ExplorerPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ExplorerPlugin.getDefault().getPreferenceStore();
		String cmd = "explorer";
		int os = ExplorerPlugin.getOS();
		if (os == ExplorerPlugin.OS_LINUX) {
			cmd = "nautilus";
		}
		else if (os == ExplorerPlugin.OS_MAC) {
		    cmd = "open -R";
		}
		store.setDefault(PreferenceConstants.EXPLORER_CMD, cmd);
		store.setDefault(PreferenceConstants.EXPLORER_TIP, true);
	}

}
