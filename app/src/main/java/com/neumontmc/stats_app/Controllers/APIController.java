package com.neumontmc.stats_app.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Controllers.NAPI;
import Models.User;
import Models.User_mcmmo;
import Models.ustats.Ustats;

public class APIController {
    private ArrayList<User> userList = null;
    private ArrayList<User_mcmmo> mcmmoList = null;
    private ArrayList<Ustats> ustats = null;
    private Thread getUsers = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                setUserList(new NAPI().getUserArrayList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private Thread getMcmmo = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                setMcmmoList(new NAPI().getUserArrayListWithMcmmoStats());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private Thread getUstats = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                setUstats(new NAPI().getORMUserStats());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    /**
     * Get all user obj's from the API and save them to Arrays.
     * @throws InterruptedException if there is an issues with the threads.
     */
    public void init() throws InterruptedException {
        getUsers.start();
        getUsers.join();
        getMcmmo.start();
        getMcmmo.join();
        getUstats.start();
        getUstats.join();
    }

    public ArrayList<Ustats> getUstats() {
        return ustats;
    }

    public void setUstats(ArrayList<Ustats> ustats) {
        this.ustats = ustats;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<User_mcmmo> getMcmmoList() {
        return mcmmoList;
    }

    public void setMcmmoList(ArrayList<User_mcmmo> mcmmoList) {
        this.mcmmoList = mcmmoList;
    }
}
