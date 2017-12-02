package com.getee.worldchat.model;

import java.util.Set;

public class Group extends User{
    /*
     * 此类无用纯粹用来在list中显示
     * @see com.getee.worldchat.model.User#toString()
     */
    public String toString() {
        return "" + getNiname() + "";
    }

}
