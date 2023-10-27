package sausedemotests;

public enum Products {
    BACKPACK("Sauce Labs Backpack"),
    SHIRT("Sauce Labs Bolt T-Shirt");

    private String title;

    Products(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
