package game;


public class Board {
    public Tile[][] board;
    private int grids = 4;
    private int border = 0;
    private int score = 0;

    public Board() {
        this.board = new Tile[4][4];

        for(int i = 0; i < this.board.length; ++i) {
            for(int j = 0; j < this.board[i].length; ++j) {
                this.board[i][j] = new Tile();
            }
        }

    }

    public int getScore() {
        return this.score;
    }

    private void verticalMove(int row, int col, String direction) {
        Tile initial = this.board[this.border][col];
        Tile compare = this.board[row][col];

        if (initial.getValue() != 0 && initial.getValue() != compare.getValue())
        {
            if (direction.equals("down"))
            {
                --this.border;
            } else {
                ++this.border;
            }

            this.verticalMove(row, col, direction);
        }
        else if (row > this.border || direction.equals("down") && row < this.border)
        {
            int addScore = initial.getValue() + compare.getValue();
            if (initial.getValue() != 0)
            {
                this.score += addScore;
            }

            initial.setValue(addScore);
            compare.setValue(0);
        }
    }

    public void up() {
        for(int i = 0; i < this.grids; ++i)
        {
            this.border = 0;

            for(int j = 0; j < this.grids; ++j)
            {
                if (this.board[j][i].getValue() != 0 && this.border <= j)
                {
                    this.verticalMove(j, i, "up");
                }
            }
        }
    }

    public void down() {
        for(int i = 0; i < this.grids; ++i)
        {
            this.border = this.grids - 1;

            for(int j = this.grids - 1; j >= 0; --j)
            {
                if (this.board[j][i].getValue() != 0 && this.border >= j)
                {
                    this.verticalMove(j, i, "down");
                }
            }
        }
    }

    private void horizontalMove(int row, int col, String direction) {
        Tile initial = this.board[row][this.border];
        Tile compare = this.board[row][col];

        if (initial.getValue() != 0 && initial.getValue() != compare.getValue())
        {
            if (direction.equals("right"))
            {
                --this.border;
            } else {
                ++this.border;
            }

            this.horizontalMove(row, col, direction);

        }
        else if (col > this.border || direction.equals("right") && col < this.border)
        {
            int addScore = initial.getValue() + compare.getValue();
            if (initial.getValue() != 0)
            {
                this.score += addScore;
            }

            initial.setValue(addScore);
            compare.setValue(0);
        }
    }

    public void left() {
        for(int i = 0; i < this.grids; ++i) {
            this.border = 0;

            for(int j = 0; j < this.grids; ++j)
            {
                if (this.board[i][j].getValue() != 0 && this.border <= j)
                {
                    this.horizontalMove(i, j, "left");
                }
            }
        }
    }

    public void right() {
        for(int i = 0; i < this.grids; ++i) {
            this.border = this.grids - 1;

            for(int j = this.grids - 1; j >= 0; --j)
            {
                if (this.board[i][j].getValue() != 0 && this.border >= j)
                {
                    this.horizontalMove(i, j, "right");
                }
            }
        }
    }

    public int getHighTile() {
        int high = this.board[0][0].getValue();

        for (Tile[] tiles : this.board) {
            for (Tile tile : tiles) {
                if (tile.getValue() > high) {
                    high = tile.getValue();
                }
            }
        }

        return high;
    }

    public void spawn() {
        boolean empty = true;

        while(empty) {
            int row = (int)(Math.random() * 4.0);
            int col = (int)(Math.random() * 4.0);
            double x = Math.random();

            if (this.board[row][col].getValue() == 0) {
                if (x < 0.2) {
                    this.board[row][col] = new Tile(4);
                } else {
                    this.board[row][col] = new Tile(2);
                }
                empty = false;
            }
        }
    }

    public boolean blackOut() {
        int count = 0;

        for (Tile[] tiles : this.board) {
            for (Tile tile : tiles) {
                if (tile.getValue() > 0) {
                    ++count;
                }
            }
        }

        return count == 16;
    }

    public boolean gameOver() {
        int count = 0;

        for(int i = 0; i < this.board.length; ++i) {
            for(int j = 0; j < this.board[i].length; ++j) {
                if (this.board[i][j].getValue() > 0) {
                    if (i == 0 && j == 0) {
                        if (this.board[i][j].getValue() != this.board[i + 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j + 1].getValue()) {
                            ++count;
                        }
                    } else if (i == 0 && j == 3) {
                        if (this.board[i][j].getValue() != this.board[i + 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j - 1].getValue()) {
                            ++count;
                        }
                    } else if (i == 3 && j == 3) {
                        if (this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j - 1].getValue()) {
                            ++count;
                        }
                    } else if (i == 3 && j == 0) {
                        if (this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j + 1].getValue()) {
                            ++count;
                        }
                    } else if (i == 0 && (j == 1 || j == 2)) {
                        if (this.board[i][j].getValue() != this.board[i + 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j + 1].getValue() && this.board[i][j].getValue() != this.board[i][j - 1].getValue()) {
                            ++count;
                        }
                    } else if (i != 3 || j != 1 && j != 2) {
                        if (j == 0 && (i == 1 || i == 2)) {
                            if (this.board[i][j].getValue() != this.board[i][j + 1].getValue() && this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i + 1][j].getValue()) {
                                ++count;
                            }
                        } else if (j == 3 && (i == 1 || i == 2)) {
                            if (this.board[i][j].getValue() != this.board[i][j - 1].getValue() && this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i + 1][j].getValue()) {
                                ++count;
                            }
                        } else if (this.board[i][j].getValue() != this.board[i][j - 1].getValue() && this.board[i][j].getValue() != this.board[i][j + 1].getValue() && this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i + 1][j].getValue()) {
                            ++count;
                        }
                    } else if (this.board[i][j].getValue() != this.board[i - 1][j].getValue() && this.board[i][j].getValue() != this.board[i][j + 1].getValue() && this.board[i][j].getValue() != this.board[i][j - 1].getValue()) {
                        ++count;
                    }
                }
            }
        }

        return count == 16;
    }
}

