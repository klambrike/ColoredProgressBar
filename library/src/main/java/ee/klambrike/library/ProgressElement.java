package ee.klambrike.library;

public class ProgressElement {
    private int color;
    private int amount;

    public ProgressElement(int color, int amount) {
        this.color = color;
        this.amount = amount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
