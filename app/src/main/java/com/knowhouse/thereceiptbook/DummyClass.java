package com.knowhouse.thereceiptbook;

public class DummyClass {
    private String name;
    private int dummy_text;
    private int profileImageId;
    private int customerImageId;

    public static final DummyClass[] dummy_users = {
            new DummyClass("" +
                    "Enoch Viewu",R.string.dummy_text,
                    R.drawable.people,R.drawable.ic_launcher_background),

            new DummyClass("" +
                    "Alex Viewu",R.string.dummy_text,
                    R.drawable.people,R.drawable.ic_launcher_background),

            new DummyClass("" +
                    "John Viewu",R.string.dummy_text,
                    R.drawable.people,R.drawable.ic_launcher_background),

            new DummyClass("" +
                    "Michael Viewu",R.string.dummy_text,
                    R.drawable.people,R.drawable.ic_launcher_background)
    };

    public DummyClass(
            String name,int dummy_text,
            int profileImageId,int customerImageId){
        this.name = name;
        this.dummy_text = dummy_text;
        this.profileImageId = profileImageId;
        this.customerImageId = customerImageId;
    }

    public String getName() {
        return name;
    }

    public int getDummy_text() {
        return dummy_text;
    }

    public int getProfileImageId() {
        return profileImageId;
    }

    public int getCustomerImageId() {
        return customerImageId;
    }
}
