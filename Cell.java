public class Cell{
    private int number;
    private boolean mine;
    private boolean flagged;
    private boolean open;
    
    // Cell-let, 
    public Cell(int number, boolean mine, boolean flagged, boolean open){
        this.number = number;
        this.mine = mine;
        this.flagged = flagged;
        this.open = open;
    }
    
    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMine() {
        return this.mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


}