package com.runing.example.simpleviewinject_api;

import android.app.Activity;
import android.view.View;

/**
 * Created by runing on 2016/6/18.
 * <p>
 * This file is part of ViewInjectTest.
 * ViewInjectTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * ViewInjectTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with ViewInjectTest.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * findViewById策略
 */
public enum FindStrategy {
    VIEW {
        @Override
        @SuppressWarnings("unchecked") //安全转换
        public <T extends View> T findViewById(Object source, int id) {
            return (T) ((View) source).findViewById(id);
        }
    }, ACTIVITY {
        @Override
        @SuppressWarnings("unchecked") //安全转换
        public <T extends View> T findViewById(Object source, int id) {
            return (T) ((Activity) source).findViewById(id);
        }
    };

    public abstract <T extends View> T findViewById(Object source, int id);

}
