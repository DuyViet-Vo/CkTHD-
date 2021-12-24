package com.example.ckltdd;

public class ValidData {
    public static String IsEmpty(String str) {
        return str.isEmpty() ? "Trường này là bắt buộc!" : "";
    }

    public static String IsName(String str) {
        String pattern = "^[A-Za-z\\u00C0-\\u024F\\u1E00-\\u1EFF]+(\\s?([A-Za-z\\u00C0-\\u024F\\u1E00-\\u1EFF]+)?)+$";
        return str.matches(pattern) ? "" : "Tên chỉ chứa kí tự [A-Za-z] và khoảng trắng!";
    }

    public static String IsSpinnerEmpty(int id) {
        return id != 0 ? "" : "Trường này là bắt buộc!";
    }

    public static String IsNumber(String num, String msg, int lenght) {
        String pattern = "^\\d+$";
        String check = num.matches(pattern) ? "" : msg + " chỉ chứa số!";
        if(check == "" && num.length() < lenght)
            return msg + " gồm " + lenght + " chữ số!";
        return check;
    }

    public static String IsEmail(String email) {
        String pattern = "^([a-zA-Z0-9\\.\\_]+)(\\+([0-9]+))?@([a-zA-Z0-9\\.\\-]+){1,63}\\.[a-zA-Z]{1,5}$";
        return email.matches(pattern) ? "" : "Email không đúng định dạng!";
    }
}
