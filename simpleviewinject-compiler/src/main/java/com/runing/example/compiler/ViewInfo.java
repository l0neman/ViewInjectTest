package com.runing.example.compiler;

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
 */

final class ViewInfo {
    private int id;
    private String name;

    ViewInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
