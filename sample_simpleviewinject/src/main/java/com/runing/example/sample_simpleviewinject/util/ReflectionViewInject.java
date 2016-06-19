package com.runing.example.sample_simpleviewinject.util;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by runing on 2016/6/19.
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
 */

public final class ReflectionViewInject {

    private ReflectionViewInject() {
        throw new AssertionError("no instance.");
    }

    private static final String METHOD_FIND_NAME = "findViewById";

    public static void inject(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ReflectionInject inject = field.getAnnotation(ReflectionInject.class);
            if (inject == null) {
                continue;
            }
            final int id = inject.value();
            try {
                Method method = clazz.getMethod(METHOD_FIND_NAME, int.class);
                Object result = method.invoke(activity, id);
                field.set(activity, result);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
