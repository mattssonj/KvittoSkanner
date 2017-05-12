/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package corp.skaj.foretagskvitton.view;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.PageList;

import java.util.List;

import corp.skaj.foretagskvitton.model.User;
import corp.skaj.foretagskvitton.model.WizardModel;
import corp.skaj.foretagskvitton.services.IData;

public class WizardView extends AbstractWizardModel implements ModelCallbacks {

    private WizardModel model;

    public WizardView(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {

        IData dataHandler = (IData)mContext.getApplicationContext();
        User user = (User) dataHandler.readData(User.class.getName(), User.class);
        List<String> strings = (List<String>) dataHandler.readData("strings", List.class);

        model = new WizardModel(user, this, strings);

        return model.getPages();
    }

    public AbstractWizardModel getWizardModel() {
        return this;
    }
}
