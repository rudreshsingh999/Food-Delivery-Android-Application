package com.example.app2.Common;
import com.example.app2.Model.User;

public class Common {

    public static User currentUser;

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Order placed.";
        else if(status.equals("1"))
            return "On my Way!";
        else
            return "Order shipped.";
    }
}
