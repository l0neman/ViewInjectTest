package com.runing.example.simpleviewinject_api;

import android.app.Activity;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

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
 * View注入工具
 */

public final class SimpleViewInjector {

    private SimpleViewInjector() {
        throw new AssertionError("no instance!");
    }

    /**
     * 缓存注解器对象
     */
    private static final Map<Class<?>, AbstractInjector<Object>> INJECTORS =
            new LinkedHashMap<>();

    /**
     * 注入AActivity
     */
    public static void inject(Activity activity) {
        AbstractInjector<Object> injector = findInjector(activity);
        injector.inject(FindStrategy.ACTIVITY, activity, activity);
    }

    /**
     * 注入ViewHolder
     *
     * @param target 目标VH
     * @param view   父View
     */
    public static void inject(Object target, View view) {
        AbstractInjector<Object> injector = findInjector(target);
        injector.inject(FindStrategy.VIEW, target, view);
    }

    @SuppressWarnings("unchecked")
    private static AbstractInjector<Object> findInjector(Object target) {
        Class<?> clazz = target.getClass();
        AbstractInjector<Object> injector = INJECTORS.get(clazz);
        if (injector == null) {
            try {
                Class<?> injectorClazz = Class.forName(clazz.getName()
                        + "$$Proxy");
                injector = (AbstractInjector<Object>) injectorClazz.newInstance();
                INJECTORS.put(clazz, injector);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return injector;
    }

}
