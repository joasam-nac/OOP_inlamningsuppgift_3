public class Tile {
    private final int value;
    private final int[] position = new int[2]; //(x,y)

    public Tile(int value, int[] position){
        this.value = value;
        this.position[0] = position[0];
        this.position[1] = position[1];
    }

    public int getValue(){
        return value;
    }

    public int getY(){
        return position[0];
    }

    public int getX(){
        return position[1];
    }

    public void setY(int y){
        position[1] = y;
    }

    public void setX(int x){
        position[0] = x;
    }

    @Override
    public String toString(){
        return "value:" + getValue() + ", X:" + getX() + ", Y:" + getY();
    }

}
