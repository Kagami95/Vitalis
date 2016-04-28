/*
 * The MIT License (MIT)

Copyright (c) 2015 tr7zw

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.teamvitalis.vitalis.utils;

import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        bukkitItem = item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    public void setString(String key, String value) {
        bukkitItem = NBTUtil.setString(bukkitItem, key, value);
    }

    public String getString(String key) {
        return NBTUtil.getString(bukkitItem, key);
    }

    public void setInteger(String key, int value) {
        bukkitItem = NBTUtil.setInt(bukkitItem, key, value);
    }

    public Integer getInteger(String key) {
        return NBTUtil.getInt(bukkitItem, key);
    }

    public void setDouble(String key, double value) {
        bukkitItem = NBTUtil.setDouble(bukkitItem, key, value);
    }

    public double getDouble(String key) {
        return NBTUtil.getDouble(bukkitItem, key);
    }

    public void setBoolean(String key, boolean value) {
        bukkitItem = NBTUtil.setBoolean(bukkitItem, key, value);
    }

    public boolean getBoolean(String key) {
        return NBTUtil.getBoolean(bukkitItem, key);
    }

    public boolean hasKey(String key) {
        return NBTUtil.hasKey(bukkitItem, key);
    }

}