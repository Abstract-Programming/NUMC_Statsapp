package com.neumontmc.stats_app.Controllers;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.neumontmc.api.Controllers.NAPI;
import com.neumontmc.api.Models.User;
import com.neumontmc.api.Models.User_mcmmo;
import com.neumontmc.api.Models.ustats.Ustats;


public class APIController implements Parcelable, Serializable {
    private ArrayList<User> userList = null;
    private ArrayList<User_mcmmo> mcmmoList = null;
    private ArrayList<Ustats> ustats = null;
    private ArrayList<String> datablocks = null;
    private transient final Thread getUsers = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                setUserList(new NAPI().getUserArrayList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private transient final Thread getMcmmo = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                setMcmmoList(new NAPI().getUserArrayListWithMcmmoStats());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private transient final Thread getUstats = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //setUstats(new NAPI().getORMUserStats());
                setDatablocks(new NAPI().getJSONBefhelsterung().mapSerialUserStatsToDataBlocks(new NAPI().getJSONBefhelsterung().getUserStatsDataObj()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    //Parcel constructor
    public APIController(Parcel parcel){
        this.userList = parcel.readArrayList(null);
        this.mcmmoList = parcel.readArrayList(null);
        this.ustats = parcel.readArrayList(null);
    }

    //Default constructor
    public APIController(){}

    /**
     * Get all user obj's from the API and save them to Arrays.
     * @throws InterruptedException if there is an issues with the threads.
     */
    public void init() throws InterruptedException {
        System.out.println("Staring init...");
        getUsers.start();
        getUsers.join();
        System.out.println("Got users.");
        getMcmmo.start();
        getMcmmo.join();
        System.out.println("Got mcmmo stats.");
        getUstats.start();
        getUstats.join();
        System.out.println("Got ustats.");
    }

    //Getters and Setters
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

    public ArrayList<String> getDatablocks() {
        return datablocks;
    }

    public void setDatablocks(ArrayList<String> datablocks) {
        this.datablocks = datablocks;
    }

    //Parcel implementations
    public static final Creator<APIController> CREATOR = new Creator<APIController>() {
        @Override
        public APIController createFromParcel(Parcel in) {
            return new APIController(in);
        }

        @Override
        public APIController[] newArray(int size) {
            return new APIController[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeArray(new ArrayList[]{userList});
            dest.writeArray(new ArrayList[]{mcmmoList});
            dest.writeArray(new ArrayList[]{ustats});
        }catch (RuntimeException e){
            Log.e("FATAL ERROR", "Failed to write to parcel arrays.");
            e.printStackTrace();
        }
    }
}
