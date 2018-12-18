// Untuk membuat logic minesweeper
public class Minesweeper{

    private Cell[][] Cells;
    private boolean[][] bombs;
    private int[][] numbers;
    private int n;
    private double bombPercent;
    private int wins;
    private int losses;

    public Minesweeper(int n, double bombPercent){

        // Array minesweeper final
        this.Cells = new Cell[n][n];

        // Jumlah array di tambah 2 untuk masalah tepian yang dapat menyebabkan array out of index
        this.bombs = new boolean[n+2][n+2];
        this.numbers = new int[n+2][n+2];

        this.n = n;
        this.bombPercent = bombPercent;
        
        Reset();
    }
    
    // Untuk mengisi array dengan bomb
    private void AddBomb(){

        for(int y = 1; y <= this.n; ++y){
            for(int x = 1; x <= this.n; ++x){

                // Untuk memunculkan bomb yang diwakilkan dengan angka -1
                this.bombs[x][y] = RandomBomb();

            }
        }

    }

    // Menghitung kemungkinan muncul bomb
    private boolean RandomBomb(){
        // Math.random mengoutputkan angka 0-1
        return (Math.random() <= this.bombPercent);
    }

    // Menghitung jumlah bomb disekeliling kotak
    private void AddNumber(){
        // Melooping setiap kotak
        for(int y = 1; y <= this.n; ++y){
            for(int x = 1; x <= this.n; ++x){
                this.numbers[y][x] = 0;
                // Mendeteksi kotak disekeliling target
                for (int yy = y - 1; yy <= y + 1; yy++){
                    for (int xx = x - 1; xx <= x + 1; xx++){
                        if (this.bombs[yy][xx]){
                            this.numbers[y][x]++;  
                        }
                    }
                }
            }
        }
    }

    private void ShowCLI(){
        for(int y = 0; y < this.n; ++y){
            for(int x = 0; x < this.n; ++x){
                if(this.Cells[y][x].isMine()){
                    System.out.print("* ");
                }
                else{
                    System.out.print(this.Cells[y][x].getNumber() + " ");
                }
            }
            System.out.println();
        }
    }

    private void ShowCLIOpenStatus(){
        System.out.println();
        for(int y = 0; y < this.n; ++y){
            for(int x = 0; x < this.n; ++x){
                if(this.Cells[y][x].isOpen()){
                    System.out.print("O ");
                }
                else{
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

    public Cell[][] GetCells(){
        return Cells;
    }

    public void Reset(){
        AddBomb();
        AddNumber();
        for(int y = 1; y <= this.n; ++y){
            for(int x = 1; x <= this.n; ++x){
                this.Cells[y-1][x-1] = new Cell(this.numbers[y][x], this.bombs[y][x], false, false);
            }
        }
        // ShowCLI();
    }

    private boolean isValid(int y, int x){
        if(x >= 0 && y >= 0 && x < this.n && y < this.n){
            return true;
        }
        return false;
    }

    public void Select(int yInd, int xInd){
        if(!Cells[yInd][xInd].isFlagged()){
            if(!Cells[yInd][xInd].isOpen()){
                Cells[yInd][xInd].setOpen(true);

                if(Cells[yInd][xInd].getNumber() == 0){
                    for (int yy = yInd - 1; yy <= yInd + 1; yy++){
                        for (int xx = xInd - 1; xx <= xInd + 1; xx++){
                            if(isValid(yy, xx)){
                                if(!bombs[yy+1][xx+1]){
                                    Select(yy, xx);
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public boolean isWin(){
        for(int y = 0; y < this.n; ++y){
            for(int x = 0; x < this.n; ++x){
                if(!this.Cells[y][x].isMine()){
                    if(!this.Cells[y][x].isOpen()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getWin() {
        return this.wins;
    }

    public void addWin() {
        ++this.wins;
    }

    public int getLoss() {
        return this.losses;
    }

    public void addLoss() {
        ++this.losses;
    }

    public void OpenAll(){
        for(int y = 0; y < n; ++y){
            for(int x = 0; x < n; ++x){
                Cells[y][x].setOpen(true);
            }
        }
    }
}