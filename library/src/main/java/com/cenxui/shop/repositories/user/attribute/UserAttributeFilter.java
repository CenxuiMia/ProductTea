package com.cenxui.shop.repositories.user.attribute;

public class UserAttributeFilter {
    private static final int limit = 100;

    public static boolean checkBirthday(String birthday) {
        if (birthday == null) {
            return true;
        }

        if (birthday.length() > limit ) {
            return false;
        }

        //todo
        return true;
    }

    public static boolean checkPhone(String phone) {
        if (phone == null) {
            return true;
        }

        if (phone.length() > limit) {
            return false;
        }

        //todo
        return true;
    }

    public static boolean checkAddress(String address) {
        if (address == null) {
            return true;
        }

        if (address.length() > limit) {
            return false;
        }
        //todo
        return true;
    }

    public static boolean checkMail(String mail) {
        if (mail == null) {
            return true;
        }

        if (mail.length() > limit) {
            return false;
        }
        //todo
        return true;
    }

    public static boolean checkFirstName(String firstName) {
        if (firstName == null) {
            return true;
        }

        if (firstName.length() > limit) {
            return false;
        }
        //todo
        return true;
    }

    public static boolean checkLastName(String lastName) {
        if (lastName == null) {
            return true;
        }

        if (lastName.length() > limit) {
            return false;
        }
        //todo
        return true;
    }
}
