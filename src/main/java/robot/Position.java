record Position(int x, int y) {
    Position getUp() {
        return new Position(this.x, this.y + 1);
    }

    Position getRight() {
        return new Position(this.x + 1, this.y);
    }

    Position getDown() {
        return new Position(this.x, this.y - 1);
    }

    Position getLeft() {
        return new Position(this.x - 1, this.y);
    }
}
